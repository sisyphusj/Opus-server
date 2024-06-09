package com.opus.comment.mapper;

import com.opus.comment.domain.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void saveComment(CommentVO comment);

    List<CommentVO> getCommentsByPinId(int pinId);

    List<CommentVO> getMyComments(int memberId);

    void updateComment(CommentVO comment);

    void deleteComment(CommentVO comment);
}
