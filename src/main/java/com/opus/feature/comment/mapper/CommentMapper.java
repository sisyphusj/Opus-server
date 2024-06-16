package com.opus.feature.comment.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.opus.feature.comment.domain.CommentVO;

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
