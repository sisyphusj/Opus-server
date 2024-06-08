package com.opus.comment.controller;

import com.opus.auth.SecurityUtil;
import com.opus.comment.domain.CommentDTO;
import com.opus.comment.domain.CommentVO;
import com.opus.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/pins/{pinId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 추가
    @PostMapping
    public ResponseEntity<Void> saveComment(@PathVariable int pinId, @Valid @RequestBody CommentDTO commentDTO) {
        commentService.saveComment(pinId, commentDTO);
        return ResponseEntity.ok().build();
    }

    // 댓글 리스트
    @GetMapping
    public ResponseEntity<List<CommentVO>> getCommentsByPinId(@PathVariable int pinId) {
        return ResponseEntity.ok(commentService.getCommentsByPinId(pinId));
    }

    // 내 댓글 리스트
    @GetMapping("/my-comments")
    public ResponseEntity<List<CommentVO>> getMyComments() {
        return ResponseEntity.ok(commentService.getMyComments());
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable int pinId, @PathVariable int commentId, @Valid @RequestBody CommentDTO commentDTO) {
        commentService.updateComment(pinId, commentId, commentDTO);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

}
