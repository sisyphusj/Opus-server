package com.opus.feature.comment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.comment.domain.CommentRequestDTO;
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

	/**
	 * 댓글 추가
	 */
	@Transactional
	public void addComment(CommentRequestDTO commentRequestDTO) {

		CommentVO comment = CommentVO.of(commentRequestDTO);
		commentMapper.insertComment(comment);
	}

	/**
	 * 댓글 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<CommentResponseDTO> getCommentListByPinId(int pinId) {

		List<CommentVO> comments = commentMapper.selectCommentsByPinId(pinId);

		// 댓글 리스트를 CommentResponseDTO로 변환
		return comments.stream()
			.map(CommentResponseDTO::of)
			.toList();
	}

	/**
	 * 내 댓글 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<CommentResponseDTO> getMyCommentList() {

		List<CommentVO> comments = commentMapper.selectCommentsByMemberId(
			SecurityUtil.getCurrentUserId());

		// 댓글 리스트를 CommentResponseDTO로 변환
		return comments.stream()
			.map(CommentResponseDTO::of)
			.toList();
	}

	/**
	 * 댓글 수정
	 */
	public void editComment(CommentUpdateDTO commentUpdateDTO) {

		CommentVO comment = CommentVO.of(commentUpdateDTO);
		commentMapper.updateComment(comment);
	}

	/**
	 * 댓글 삭제
	 */
	@Transactional
	public void removeComment(int commentId) {

		// 댓글이 존재하지 않으면 예외 발생
		Optional<CommentVO> commentVO = commentMapper.selectCommentByCommentId(commentId);

		if (commentVO.isEmpty()) {
			throw new BusinessException("해당 댓글이 존재하지 않습니다.");
		}

		// 자식 댓글이 존재하면 예외 발생
		if (commentMapper.countChildCommentsByCommentId(commentId) > 0) {
			throw new BusinessException("대댓글이 존재하는 댓글은 삭제할 수 없습니다.");
		}

		CommentVO comment = CommentVO.builder()
			.commentId(commentId)
			.memberId(SecurityUtil.getCurrentUserId())
			.build();
		commentMapper.deleteComment(comment);
	}
}
