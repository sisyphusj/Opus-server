package com.opus.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SseClientScheduler {

	private final LikeProvider likeProvider;

	/**
	 * 10분 간격으로 cleanUpClients 실행
	 */
	@Scheduled(fixedRate = 600000)
	public void cleanupScheduler() {
		likeProvider.cleanUpClients();
	}

	/**
	 * 30초 간격으로 sendPingMessages 실행
	 */
	@Scheduled(fixedRate = 30000)
	public void sendPingMessages() {
		likeProvider.sendPingMessages();
	}
}