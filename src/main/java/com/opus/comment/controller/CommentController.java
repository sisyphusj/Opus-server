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
    public List<CommentVO> getComments(@PathVariable int pid) {

       return commentService.getComments(pid);
    }

    @GetMapping("/list/my-comments")
    public List<CommentVO> getMyComments() {

        return commentService.getMyComments(SecurityUtil.getCurrentUserId());
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseCode> updateComment(@Valid @RequestBody CommentDTO commentDTO) {

        log.info("{} {} {}", commentDTO.getPId(), commentDTO.getCId(), commentDTO.getContent());

        commentService.updateComment(commentDTO, SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    @DeleteMapping("/delete/{cid}")
    public ResponseEntity<ResponseCode> deleteComment(@PathVariable int cid) {
        commentService.deleteComment(cid, SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }
}
