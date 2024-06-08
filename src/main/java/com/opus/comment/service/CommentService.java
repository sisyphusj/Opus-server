package com.opus.comment.service;

import com.opus.auth.SecurityUtil;
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
    public void saveComment(int pinId, CommentDTO commentDTO) {

        Comment comment = Comment.builder()
                .pinId(pinId)
                .memberId(SecurityUtil.getCurrentUserId())
                .topLevelCommentId(commentDTO.getTopLevelCommentId())
                .level(commentDTO.getLevel())
                .content(commentDTO.getContent())
                .parentNickname(commentDTO.getParentNickname())
                .build();

        commentMapper.saveComment(comment);
    }

    // 게시글에 달린 댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentVO> getCommentsByPinId(int pinId) {
        return commentMapper.getCommentsByPinId(pinId);
    }

    // 내가 쓴 댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentVO> getMyComments() {
        return commentMapper.getMyComments(SecurityUtil.getCurrentUserId());
    }

    // 댓글 수정
    public void updateComment(int pinId, int commentId, CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .commentId(commentId)
                .pinId(pinId)
                .memberId(SecurityUtil.getCurrentUserId())
                .topLevelCommentId(commentDTO.getTopLevelCommentId())
                .parentNickname(commentDTO.getParentNickname())
                .level(commentDTO.getLevel())
                .content(commentDTO.getContent())
                .build();

        commentMapper.updateComment(comment);
    }

    // 댓글 삭제
    public void deleteComment(int commentId) {
        Comment comment = Comment.builder()
                .commentId(commentId)
                .memberId(SecurityUtil.getCurrentUserId())
                .build();
        commentMapper.deleteComment(comment);
    }

}
