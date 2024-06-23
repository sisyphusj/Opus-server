package com.opus.feature.comment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.feature.comment.domain.CommentRequestDTO;
import com.opus.feature.comment.domain.CommentResponseDTO;
import com.opus.feature.comment.domain.CommentUpdateDTO;
import com.opus.feature.comment.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/pins/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	/**
	 * 댓글 추가
	 */
	@PostMapping
	public ResponseEntity<String> addComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO) {

		commentService.addComment(commentRequestDTO);
		return ApiResponse.of("OK");
	}

	/**
	 * 댓글 리스트 조회
	 */
	@GetMapping("/list")
	public ResponseEntity<List<CommentResponseDTO>> getCommentListByPinId(@RequestParam int pinId) {
		return ApiResponse.of(commentService.getCommentListByPinId(pinId));
	}

	/**
	 * 내 댓글 리스트 조회
	 */
	@GetMapping("/my-comments")
	public ResponseEntity<List<CommentResponseDTO>> getMyCommentList() {
		return ApiResponse.of(commentService.getMyCommentList());
	}

	/**
	 * 댓글 수정
	 */
	@PutMapping
	public ResponseEntity<String> editComment(@Valid @RequestBody CommentUpdateDTO commentDTO) {

		commentService.editComment(commentDTO);
		return ApiResponse.of("OK");
	}

	/**
	 * 댓글 삭제
	 */
	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> removeComment(@PathVariable int commentId) {

		commentService.removeComment(commentId);
		return ApiResponse.of("OK");
	}

}
