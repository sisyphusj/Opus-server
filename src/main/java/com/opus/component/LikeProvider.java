package com.opus.component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.opus.exception.BusinessException;
import com.opus.utils.SecurityUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
@Component
public class LikeProvider {

	// clients 맵에 대한 getter 메서드 추가
	private final Map<Integer, List<ClientInfo>> clients = new ConcurrentHashMap<>();

	/**
	 *  클라이언트(사용자)가 구독 요청을 보내면 구독자 목록에 추가
	 */
	public SseEmitter subscribe(int pinId) {
		if (checkDuplicateSubscribe(clients.get(pinId), SecurityUtil.getCurrentUserId())) {
			throw new BusinessException("이미 구독중입니다.");
		}

		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

		ClientInfo clientInfo = new ClientInfo(emitter, SecurityUtil.getCurrentUserId());

		// pinId가 clients 리스트에 미등록인 경우는 람다 표현식 실행
		// pinId가 clients 리스트에 등록된 경우는 해당 리스트에 추가
		clients.computeIfAbsent(pinId, k -> new CopyOnWriteArrayList<>()).add(clientInfo);

		Runnable removeClientTask = () -> unsubscribeClient(pinId, clientInfo);

		emitter.onCompletion(removeClientTask);
		emitter.onTimeout(removeClientTask);

		return emitter;
	}

	/**
	 * 클라이언트 제거 로직
	 */
	private void unsubscribeClient(int pinId, ClientInfo clientInfo) {
		List<ClientInfo> clientList = clients.get(pinId);

		if (clientList != null) {
			clientList.remove(clientInfo); // 해당 pin의 클라이언트 리스트가 존재하면 현재 클라이언트만 삭제
			if (clientList.isEmpty()) {
				clients.remove(pinId); // 클라이언트 리스트가 없다면 해당 pin에 대한 전체 삭제
			}
		}
	}

	/**
	 * 클라이언트(사용자)가 구독 취소 요청을 보내면 구독자 목록에서 삭제
	 */
	public void unsubscribe(int pinId) {
		List<ClientInfo> clientList = Optional.of(clients.get(pinId))
			.orElseThrow(() -> {
				log.error("error : 해당 핀을 구독한 클라이언트가 없습니다.");
				return new BusinessException("구독 중이지 않습니다.");
			});

		clientList.removeIf(client -> client.getMemberId() == SecurityUtil.getCurrentUserId());

		if (clientList.isEmpty()) {
			clients.remove(pinId);
		}
	}

	/**
	 * 주기적으로 최신 좋아요 수를 설정
	 */
	public void updateLatestLikeCount(int pinId, int likeCount) {
		sendPinLikeUpdate(pinId, likeCount);
	}

	/**
	 * 클라이언트(사용자)에게 좋아요 수를 전송
	 */
	private void sendPinLikeUpdate(int pinId, int likeCount) {
		List<ClientInfo> clientList = Optional.of(clients.get(pinId))
			.orElseThrow(() -> {
				log.error("error : 해당 핀을 구독한 클라이언트가 없습니다.");
				return new BusinessException("구독 중이지 않습니다.");
			});

		for (ClientInfo clientInfo : clientList) {
			try {
				clientInfo.getEmitter()
					.send(SseEmitter.event()
						.name("like-update")
						.data(Map.of("pinId", pinId, "likeCount", likeCount)));

				clientInfo.updateLastActiveTime();

			} catch (IOException e) {
				log.error("error : ", e);
				clientList.remove(clientInfo);
			}
		}
	}

	private boolean checkDuplicateSubscribe(List<ClientInfo> clientInfoList, int memberId) {
		if (clientInfoList == null) {
			return false;
		}

		return clientInfoList.stream()
			.map(ClientInfo::getMemberId)
			.anyMatch(id -> id == memberId);
	}

}
