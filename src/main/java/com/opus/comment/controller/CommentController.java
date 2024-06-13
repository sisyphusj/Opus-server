package com.opus.comment.controller;

import com.opus.comment.domain.CommentInsertDTO;
import com.opus.comment.domain.CommentResponseDTO;
import com.opus.comment.domain.CommentUpdateDTO;
import com.opus.comment.service.CommentService;
import com.opus.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/pins/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  // 댓글 추가
  @PostMapping
  public ResponseEntity<String> addComment(@Valid @RequestBody CommentInsertDTO commentInsertDTO) {
    commentService.addComment(commentInsertDTO);
    return ApiResponse.of("OK");
  }

  // 댓글 리스트
  @GetMapping("/list")
  public ResponseEntity<List<CommentResponseDTO>> getCommentListByPinId(@RequestParam int pinId) {
    return ApiResponse.of(commentService.getCommentListByPinId(pinId));
  }

  // 내 댓글 리스트
  @GetMapping("/my-comments")
  public ResponseEntity<List<CommentResponseDTO>> getMyCommentList() {
    return ApiResponse.of(commentService.getMyCommentList());
  }

  // 댓글 수정
  @PutMapping
  public ResponseEntity<String> editComment(@Valid @RequestBody CommentUpdateDTO commentDTO) {
    commentService.editComment(commentDTO);
    return ApiResponse.of("OK");
  }

  // 댓글 삭제
  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> removeComment(@PathVariable int commentId) {
    commentService.removeComment(commentId);
    return ApiResponse.of("OK");
  }

}
