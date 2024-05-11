package com.opus.comment.controller;

import com.opus.auth.SecurityUtil;
import com.opus.comment.domain.CommentDTO;
import com.opus.comment.domain.CommentVO;
import com.opus.comment.service.CommentService;
import com.opus.common.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<ResponseCode> addComment(@Valid @RequestBody CommentDTO commentDTO){

        commentService.addComment(commentDTO, SecurityUtil.getCurrentUserId());

        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    @GetMapping("/list/{pid}")
    public List<CommentVO> getCommentList(@PathVariable int pid) {

       return commentService.getCommentList(pid, SecurityUtil.getCurrentUserId());
    }
}
