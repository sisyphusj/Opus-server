package com.opus.comment.mapper;

import com.opus.comment.domain.Comment;
import com.opus.comment.domain.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void saveComment(Comment comment);

    List<CommentVO> getCommentsByPinId(int pinId);

    List<CommentVO> getMyComments(int memberId);

    void updateComment(Comment comment);

    void deleteComment(Comment comment);
}
