package com.opus.comment.service;

import com.opus.comment.domain.Comment;
import com.opus.comment.domain.CommentDTO;
import com.opus.comment.domain.CommentVO;
import com.opus.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentMapper commentMapper;

    @Transactional
    public void addComment(CommentDTO commentDTO, int currentUserId) {

        Comment comment = Comment.builder()
                .pId(commentDTO.getPId())
                .mId(currentUserId)
                .parentCommentId(commentDTO.getParentCommentId())
                .level(commentDTO.getLevel())
                .content(commentDTO.getContent())
                .build();

        log.info("{}", commentDTO.getPId());
        commentMapper.addComment(comment);
    }

    // 게시글에 달린 댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentVO> getComments(int pid) {
        return commentMapper.getComments(pid);
    }

    // 내가 쓴 댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentVO> getMyComments(int currentUserId) {

        return commentMapper.getMyComments(currentUserId);
    }
}
