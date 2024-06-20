package com.opus.feature.like.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.opus.common.ApiResponse;
import com.opus.component.LikeProvider;
import com.opus.feature.like.domain.PinLikeDTO;
import com.opus.feature.like.service.LikeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/like-subscribe")
@RequiredArgsConstructor
public class SubscribeController {

	private final LikeProvider likeProvider;

	private final LikeService likeService;

	// pin 좋아요 수 실시간 구독
	@GetMapping(value = "/subscribe/{pinId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribeToPinLikes(@PathVariable int pinId) {
		return ApiResponse.of(likeProvider.subscribe(pinId));
	}

	// 각 구독 클라이언트들의 정보 최신화 적용
	@PostMapping("/broadcast")
	public void updateLikeCount(@Valid @RequestBody PinLikeDTO pinLikeDTO) {
		likeProvider.updateLatestLikeCount(pinLikeDTO.getPinId(), likeService.countPinLike(pinLikeDTO.getPinId()));
	}

	// 구독 취소
	@DeleteMapping("/unsubscribe/{pinId}")
	public ResponseEntity<String> unsubscribe(@PathVariable int pinId) {

		likeProvider.unsubscribe(pinId);
		return ApiResponse.of("OK");
	}
}
