package com.opus.component;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SseClientCleaner {

	private final Map<Integer, List<ClientInfo>> clients;

	public SseClientCleaner(LikeProvider likeProvider) {
		this.clients = likeProvider.getClients();
	}

	/**
	 * 10분 간격으로 SSE 클라이언트 삭제
	 */
	@Scheduled(fixedRate = 600000)
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
