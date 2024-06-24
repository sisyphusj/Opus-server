package com.opus.feature.like.domain;

import java.time.Instant;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.Getter;

/**
 *  ClientInfo - SSE에 사용되는 클라이언트 정보 클래스
 */

@Getter
public class ClientInfo {

	// SSE emitter
	private final SseEmitter emitter;

	// 회원 ID
	private final int memberId;

	// 마지막 활동 시간
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
