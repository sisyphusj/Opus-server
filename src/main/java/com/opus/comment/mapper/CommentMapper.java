package com.opus.comment.mapper;

import com.opus.comment.domain.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {

  void insertComment(CommentVO comment);

  List<CommentVO> selectCommentsByPinId(int pinId);

  List<CommentVO> selectCommentsByMemberId(int memberId);

  Optional<CommentVO> selectCommentByCommentId(int commentId);

  int countChildCommentsByCommentId(int commentId);

  void updateComment(CommentVO comment);

  void deleteComment(CommentVO comment);
}
