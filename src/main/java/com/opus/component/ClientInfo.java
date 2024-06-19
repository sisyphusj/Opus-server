package com.opus.component;

import java.time.Instant;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.Getter;

@Getter
public class ClientInfo {

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
