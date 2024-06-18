package com.opus.component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.opus.exception.BusinessException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LikeProvider {

	private final Map<Integer, List<ClientInfo>> clients = new ConcurrentHashMap<>();

	@Getter
	private static class ClientInfo {

		private final SseEmitter emitter;

		private final int memberId;

		private Instant lastActiveTime;

		public ClientInfo(SseEmitter emitter, int memberId) {
			this.emitter = emitter;
			this.memberId = memberId;
			this.lastActiveTime = Instant.now();
		}

		public void updateLastActiveTime() {
			this.lastActiveTime = Instant.now();
		}
	}

	// 클라이언트(사용자)가 구독 요청을 보내면 구독자 목록에 추가
	public SseEmitter subscribe(int pinId, int memberId) {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

		ClientInfo clientInfo = new ClientInfo(emitter, memberId);

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

	// 클라이언트(사용자)가 구독 취소 요청을 보내면 구독자 목록에서 삭제
	public void unsubscribe(int pinId, int memberId) {
		List<ClientInfo> clientList = clients.get(pinId);

		if (clientList != null) {
			clientList.removeIf(client -> client.getMemberId() == memberId);
			if (clientList.isEmpty()) {
				clients.remove(pinId);
			}
		}
	}

	// 주기적으로 최신 좋아요 수를 설정
	public void updateLatestLikeCount(int pinId, int likeCount) {
		sendPinLikeUpdate(pinId, likeCount);
	}

	/**
	 * 클라이언트(사용자)에게 좋아요 수를 전송
	 */
	private void sendPinLikeUpdate(int pinId, int likeCount) {
		List<ClientInfo> clientList = clients.get(pinId);

		if (clientList == null) {
			log.error("error : Emitter가 존재하지 않습니다. {}", pinId);
			throw new BusinessException("데이터를 불러오는데 실패하였습니다.");
		}

		for (ClientInfo clientInfo : clientList) {
			try {
				clientInfo.getEmitter()
					.send(SseEmitter.event().name("like-update")
						.data(Map.of("pinId", pinId, "likeCount", likeCount)));

				clientInfo.updateLastActiveTime();

			} catch (IOException e) {
				log.error("error : ", e);
				clientList.remove(clientInfo);
			}
		}
	}

	/**
	 * 10분 간격으로 클라이언트 청소
	 */
	@Scheduled(fixedRate = 600000)
	private void cleanUpClients() {
		Instant now = Instant.now();
		clients.forEach((pinId, clientList) -> {
			clientList.removeIf(client -> client.getLastActiveTime().isBefore(now.minusSeconds(600)));

			if (clientList.isEmpty()) {
				clients.remove(pinId);
			}
		});
	}

}
