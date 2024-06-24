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
import com.opus.feature.like.domain.ClientInfo;
import com.opus.utils.SecurityUtil;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * LikeProvider - 좋아요 수를 실시간으로 전송하는 클래스
 */

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

		// 중복 구독 체크
		if (checkDuplicateSubscribe(clients.get(pinId), SecurityUtil.getCurrentUserId())) {
			throw new BusinessException("이미 구독중입니다.");
		}

		// 새 SseEmitter 객체 생성
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

		// 클라이언트 정보 객체 생성
		ClientInfo clientInfo = new ClientInfo(emitter, SecurityUtil.getCurrentUserId());

		// pinId가 clients 리스트에 미등록인 경우는 람다 표현식 실행
		// pinId가 clients 리스트에 등록된 경우는 해당 리스트에 추가
		clients.computeIfAbsent(pinId, k -> new CopyOnWriteArrayList<>()).add(clientInfo);

		// 연결을 끊었을 때 실행할 작업
		Runnable removeClientTask = () -> unsubscribeClient(pinId, clientInfo);

		emitter.onCompletion(removeClientTask);
		emitter.onTimeout(removeClientTask);

		return emitter;
	}

	/**
	 * 클라이언트 제거 로직
	 */
	private void unsubscribeClient(int pinId, ClientInfo clientInfo) {

		if (!clients.containsKey(pinId)) {
			return;
		}

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

		// pinId에 해당하는 클라이언트 리스트가 없으면 종료
		if (!clients.containsKey(pinId))
			return;

		// pinId에 해당하는 클라이언트 리스트를 가져옴
		List<ClientInfo> clientList = clients.get(pinId);

		// 현재 사용자의 memberId와 일치하는 클라이언트를 삭제
		clientList.removeIf(client -> client.getMemberId() == SecurityUtil.getCurrentUserId());

		// 해당 pin 에 해당하는 클라이언트 리스트가 비어있으면 리스트 삭제
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

		// pinId에 해당하는 클라이언트 리스트가 없으면 예외 발생
		if (!clients.containsKey(pinId))
			throw new BusinessException("구독 중인 클라이언트가 없습니다.");

		// pinId에 해당하는 클라이언트 리스트를 가져옴
		List<ClientInfo> clientList = clients.get(pinId);

		// 클라이언트에게 좋아요 수를 전송
		sendLikeUpdateToClients(pinId, likeCount, clientList);
	}

	/**
	 * 클라이언트(사용자)에게 좋아요 수를 전송
	 */
	private void sendLikeUpdateToClients(int pinId, int likeCount, List<ClientInfo> clientList) {

		for (ClientInfo clientInfo : clientList) {
			try {
				// 클라이언트에게 전송할 데이터, 맵 형태로 생성
				Map<String, Integer> eventData = Map.of("pinId", pinId, "likeCount", likeCount);

				// 클라이언트에게 전송할 이벤트 빌더 생성
				SseEmitter.SseEventBuilder eventBuilder = SseEmitter.event()
					.name("like-update")
					.data(eventData);

				// 클라이언트에게 이벤트 전송
				SseEmitter emitter = clientInfo.getEmitter();
				emitter.send(eventBuilder);

				// 클라이언트의 최근 활동 시간을 업데이트
				clientInfo.updateLastActiveTime();

			} catch (IOException e) {
				log.error("error : ", e);

				// 클라이언트에게 에러를 전송하고 클라이언트를 구독 해제
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

		// 클라이언트 리스트가 null 이면 거짓 반환
		if (clientInfoList == null) {
			return false;
		}

		// 클라이언트 리스트에서 memberId와 일치하는 것이 있으면 참 반환
		return clientInfoList.stream()
			.map(ClientInfo::getMemberId)
			.anyMatch(id -> id == memberId);
	}

	/**
	 * 주기적으로 모든 구독 클라이언트에게 핑 메시지 전송
	 * 브라우저는 클라이언트-서버 간 연결이 45초 이상 유지되지 않으면 연결을 끊음
	 */
	public void sendPingMessages() {

		// 클라이언트 리스트를 순회하며 핑 메시지 전송
		clients.forEach(this::sendPingToClient);
	}

	/**
	 * 클라이언트에게 핑 메시지 전송
	 */
	private void sendPingToClient(int pinId, List<ClientInfo> clientList) {

		// 클라이언트 리스트를 순회하며 핑 메시지 전송
		for (ClientInfo clientInfo : clientList) {

			try {
				clientInfo.getEmitter().send(SseEmitter.event().name("ping").data("ping"));
			} catch (IOException e) {
				log.error("핑 전송 실패", e);
				unsubscribeClient(pinId, clientInfo);
			}
		}
	}

	/**
	 * 주기적으로 클라이언트를 정리하는 메소드
	 */
	public void cleanUpClients() {

		// 현재 시간을 가져옴
		Instant now = Instant.now();

		// 클라이언트 리스트를 순회하며 10분 이상 활동하지 않은 클라이언트를 삭제
		clients.forEach((pinId, clientList) -> {
			clientList.removeIf(client -> client.getLastActiveTime().isBefore(now.minusSeconds(600)));

			if (clientList.isEmpty()) {
				clients.remove(pinId);
			}
		});
	}

}
