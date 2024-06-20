package com.opus.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SseClientCleaner {

	private final LikeProvider likeProvider;

	/**
	 * 10분 간격으로 cleanUpClients 실행
	 */
	@Scheduled(fixedRate = 600000)
	public void cleanupScheduler() {
		likeProvider.cleanUpClients();
	}
}
