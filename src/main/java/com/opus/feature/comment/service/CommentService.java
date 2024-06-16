package com.opus.feature.comment.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.comment.domain.CommentInsertDTO;
import com.opus.feature.comment.domain.CommentResponseDTO;
import com.opus.feature.comment.domain.CommentUpdateDTO;
import com.opus.feature.comment.domain.CommentVO;
import com.opus.feature.comment.mapper.CommentMapper;
import com.opus.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

	private final CommentMapper commentMapper;

	@Transactional
	public void addComment(CommentInsertDTO commentInsertDTO) {

		CommentVO comment = CommentVO.builder()
			.pinId(commentInsertDTO.getPinId())
			.memberId(SecurityUtil.getCurrentUserId())
			.topLevelCommentId(commentInsertDTO.getTopLevelCommentId())
			.level(commentInsertDTO.getLevel())
			.content(commentInsertDTO.getContent())
			.parentNickname(commentInsertDTO.getParentNickname())
			.build();

		commentMapper.insertComment(comment);
	}

	@Transactional(readOnly = true)
	public List<CommentResponseDTO> getCommentListByPinId(int pinId) {
		List<CommentVO> comments = commentMapper.selectCommentsByPinId(pinId);
		return comments.stream()
			.map(CommentResponseDTO::of)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<CommentResponseDTO> getMyCommentList() {
		List<CommentVO> comments = commentMapper.selectCommentsByMemberId(
			SecurityUtil.getCurrentUserId());
		return comments.stream()
			.map(CommentResponseDTO::of)
			.toList();
	}

	public void editComment(CommentUpdateDTO commentDTO) {
		CommentVO comment = CommentVO.builder()
			.commentId(commentDTO.getCommentId())
			.pinId(commentDTO.getPinId())
			.memberId(SecurityUtil.getCurrentUserId())
			.topLevelCommentId(commentDTO.getTopLevelCommentId())
			.parentNickname(commentDTO.getParentNickname())
			.level(commentDTO.getLevel())
			.content(commentDTO.getContent())
			.build();

		commentMapper.updateComment(comment);
	}

	@Transactional
	public void removeComment(int commentId) {

		commentMapper.selectCommentByCommentId(commentId)
			.orElseThrow(() -> new NoSuchElementException("해당 댓글이 존재하지 않습니다."));

		if (commentMapper.countChildCommentsByCommentId(commentId) > 0) {
			throw new BusinessException("자식 댓글이 존재하는 댓글은 삭제할 수 없습니다.");
		}

		// TODO 삭제 권한 검증 추가

		CommentVO comment = CommentVO.builder()
			.commentId(commentId)
			.memberId(SecurityUtil.getCurrentUserId())
			.build();
		commentMapper.deleteComment(comment);
	}
}
