package com.opus.feature.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.feature.like.domain.CommentLikeDTO;
import com.opus.feature.like.domain.PinLikeDTO;
import com.opus.feature.like.service.LikeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	// 게시글에 좋아요 추가
	@PostMapping("/pin")
	public ResponseEntity<String> addPinLike(@Valid @RequestBody PinLikeDTO pinLikeDTO) {
		likeService.addPinLike(pinLikeDTO);
		return ApiResponse.of("OK");
	}

	// 댓글에 좋아요 추가
	@PostMapping("/comment")
	public ResponseEntity<String> addCommentLike(@Valid @RequestBody CommentLikeDTO commentLikeDTO) {
		likeService.addCommentLike(commentLikeDTO);
		return ApiResponse.of("OK");
	}

	// 게시글에 대한 좋아요 수
	@GetMapping(value = "/pin/{pinId}")
	public ResponseEntity<Integer> countPinLike(@PathVariable int pinId) {
		return ApiResponse.of(likeService.countPinLike(pinId));
	}

	// 댓글에 대한 좋아요 수
	@GetMapping(value = "/comment/{commentId}")
	public ResponseEntity<Integer> countCommentLike(@PathVariable int commentId) {
		return ApiResponse.of(likeService.countCommentLike(commentId));
	}

	// 게시글에 대한 좋아요 삭제
	@DeleteMapping("/pin/{pinId}")
	public ResponseEntity<String> removePinLike(@PathVariable int pinId) {
		likeService.removePinLike(pinId);
		return ApiResponse.of("OK");
	}

	// 댓글에 대한 좋아요 삭제
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<String> removeCommentLike(@PathVariable int commentId) {
		likeService.removeCommentLike(commentId);
		return ApiResponse.of("OK");
	}
}
