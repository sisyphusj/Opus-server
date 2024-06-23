package com.opus.component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.opus.exception.BusinessException;
import com.opus.utils.SecurityUtil;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
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

		if (!clients.containsKey(pinId))
			return;

		List<ClientInfo> clientList = clients.get(pinId);

		clientList.remove(clientInfo); // 해당 pin의 클라이언트 리스트가 존재하면 현재 클라이언트만 삭제

		if (clientList.isEmpty()) {
			clients.remove(pinId); // 클라이언트 리스트가 없다면 해당 pin에 대한 전체 삭제
		}
	}

	/**
	 * 클라이언트(사용자)가 구독 취소 요청을 보내면 구독자 목록에서 삭제
	 */
	public void unsubscribe(int pinId) {

		if (!clients.containsKey(pinId))
			return;

		List<ClientInfo> clientList = clients.get(pinId);

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

		if (!clients.containsKey(pinId))
			throw new BusinessException("구독 중인 클라이언트가 없습니다.");

		List<ClientInfo> clientList = clients.get(pinId);

		for (ClientInfo clientInfo : clientList) {
			try {
				clientInfo.getEmitter()
					.send(SseEmitter.event()
						.name("like-update")
						.data(Map.of("pinId", pinId, "likeCount", likeCount)));

				clientInfo.updateLastActiveTime();

			} catch (IOException e) {
				log.error("error : ", e);
				clientInfo.getEmitter().completeWithError(e);
				clientList.remove(clientInfo);
			}
		}
	}

	/**
	 * 중복으로 구독을 요청하는 경우에 대한 메소드
	 * 중복이면 참을 반환
	 */
	private boolean checkDuplicateSubscribe(List<ClientInfo> clientInfoList, int memberId) {

		if (clientInfoList == null) {
			return false;
		}

		return clientInfoList.stream()
			.map(ClientInfo::getMemberId)
			.anyMatch(id -> id == memberId);
	}

	/**
	 * 주기적으로 모든 구독 클라이언트에게 핑 메시지 전송
	 * 브라우저는 클라이언트-서버 간 연결이 45초 이상 유지되지 않으면 연결을 끊음
	 */
	public void sendPingMessages() {
		clients.forEach((pinId, clientList) -> {
			for (ClientInfo clientInfo : clientList) {
				try {
					clientInfo.getEmitter().send(SseEmitter.event().name("ping").data("ping"));
				} catch (IOException e) {
					log.error("핑 전송 실패", e);
					unsubscribeClient(pinId, clientInfo);
				}
			}
		});
	}

	/**
	 * 주기적으로 클라이언트를 정리하는 메소드
	 */
	public void cleanUpClients() {

		Instant now = Instant.now();
		clients.forEach((pinId, clientList) -> {
			clientList.removeIf(client -> client.getLastActiveTime().isBefore(now.minusSeconds(600)));

			if (clientList.isEmpty()) {
				clients.remove(pinId);
			}
		});
	}

}
