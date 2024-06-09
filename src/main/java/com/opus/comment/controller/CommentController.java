package com.opus.comment.controller;

import com.opus.comment.domain.CommentDTO;
import com.opus.comment.domain.CommentResponseDTO;
import com.opus.comment.service.CommentService;
import com.opus.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ApiResponse<String> saveComment(@Valid @RequestBody CommentDTO commentDTO) {
        commentService.saveComment(commentDTO);
        return ApiResponse.of("OK");
    }

    // 댓글 리스트
    @GetMapping
    public ApiResponse<List<CommentResponseDTO>> getCommentsByPinId(@RequestParam int pinId) {
        return ApiResponse.of(commentService.getCommentsByPinId(pinId));
    }

    // 내 댓글 리스트
    @GetMapping("/my-comments")
    public ApiResponse<List<CommentResponseDTO>> getMyComments() {
        return ApiResponse.of(commentService.getMyComments());
    }

    // 댓글 수정
    @PutMapping
    public ApiResponse<String> updateComment(@Valid @RequestBody CommentDTO commentDTO) {
        commentService.updateComment(commentDTO);
        return ApiResponse.of("OK");
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ApiResponse<String> deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.of("OK");
    }

}
