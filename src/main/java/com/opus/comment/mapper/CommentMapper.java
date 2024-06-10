package com.opus.comment.mapper;

import com.opus.comment.domain.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertComment(CommentVO comment);

    List<CommentVO> selectCommentsByPinId(int pinId);

    List<CommentVO> selectCommentsByMemberId(int memberId);

    void updateComment(CommentVO comment);

    void deleteComment(CommentVO comment);
}
