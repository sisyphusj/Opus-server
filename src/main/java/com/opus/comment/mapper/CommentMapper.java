package com.opus.comment.mapper;

import com.opus.comment.domain.Comment;
import com.opus.comment.domain.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void addComment(Comment comment);

    List<CommentVO> getComments(int pId);

    List<CommentVO> getMyComments(int mId);

}
