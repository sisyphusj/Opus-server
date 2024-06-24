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
import com.opus.feature.like.domain.PinLikeReqDTO;
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

	/**
	 * 게시글 좋아요 구독
	 */
	@GetMapping(value = "/subscribe/{pinId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribeToPinLikes(@PathVariable int pinId) {
		return ApiResponse.of(likeProvider.subscribe(pinId));
	}

	/**
	 * 구독한 클라이언트들에게 좋아요 카운트를 브로드캐스트
	 */
	@PostMapping("/broadcast")
	public void updateLikeCount(@Valid @RequestBody PinLikeReqDTO pinLikeReqDTO) {
		likeProvider.updateLatestLikeCount(pinLikeReqDTO.getPinId(),
			likeService.countPinLike(pinLikeReqDTO.getPinId()));
	}

	// 구독 취소
	@DeleteMapping("/unsubscribe/{pinId}")
	public ResponseEntity<String> unsubscribe(@PathVariable int pinId) {

		likeProvider.unsubscribe(pinId);
		return ApiResponse.of("OK");
	}
}
