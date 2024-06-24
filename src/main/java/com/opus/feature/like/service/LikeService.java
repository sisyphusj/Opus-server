package com.opus.feature.like.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.exception.BusinessException;
import com.opus.feature.comment.domain.CommentVO;
import com.opus.feature.comment.mapper.CommentMapper;
import com.opus.feature.like.domain.CommentLikeReqDTO;
import com.opus.feature.like.domain.LikeVO;
import com.opus.feature.like.domain.PinLikeReqDTO;
import com.opus.feature.like.mapper.LikeMapper;
import com.opus.feature.pin.domain.PinVO;
import com.opus.feature.pin.mapper.PinMapper;
import com.opus.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeMapper likeMapper;

	private final PinMapper pinMapper;

	private final CommentMapper commentMapper;

	/**
	 * 핀에 대한 좋아요 추가
	 */
	@Transactional
	public void addPinLike(PinLikeReqDTO pinLikeReqDTO) {

		checkPinIsPresent(pinLikeReqDTO.getPinId());

		// 이미 좋아요를 한 상태인지 확인
		if (likeMapper.countPinLikeByMemberId(SecurityUtil.getCurrentUserId(), pinLikeReqDTO.getPinId()) > 0) {
			throw new BusinessException("이미 좋아요를 눌렀습니다.");
		}

		likeMapper.insertPinLike(LikeVO.of(pinLikeReqDTO));
	}

	/**
	 * 핀에 좋아요를 한 상태인지 확인
	 * 이미 좋아요를 한 상태라면 true
	 */
	@Transactional(readOnly = true)
	public boolean checkPinLike(int pinId) {

		checkPinIsPresent(pinId);

		return likeMapper.countPinLikeByMemberId(SecurityUtil.getCurrentUserId(), pinId) > 0;
	}

	/**
	 * 댓글에 좋아요 추가
	 */
	@Transactional
	public void addCommentLike(CommentLikeReqDTO commentLikeReqDTO) {

		checkCommentIsPresent(commentLikeReqDTO.getCommentId());

		// 이미 좋아요를 한 상태인지 확인
		if (likeMapper.countCommentLikeByMemberId(SecurityUtil.getCurrentUserId(), commentLikeReqDTO.getCommentId())
			> 0) {
			throw new BusinessException("이미 좋아요를 눌렀습니다.");
		}

		likeMapper.insertCommentLike(LikeVO.of(commentLikeReqDTO));
	}

	/**
	 * 댓글에 좋아요를 한 상태인지 확인
	 * 이미 좋아료를 한 상태라면 true
	 */
	@Transactional(readOnly = true)
	public boolean checkCommentLike(int commentId) {

		checkCommentIsPresent(commentId);

		return likeMapper.countCommentLikeByMemberId(SecurityUtil.getCurrentUserId(), commentId) > 0;
	}

	/**
	 * 해당 핀에 대한 좋아요 개수를 반환
	 */
	@Transactional(readOnly = true)
	public int countPinLike(int pinId) {

		checkPinIsPresent(pinId);

		return likeMapper.countPinLike(pinId);
	}

	/**
	 * 해당 댓글에 대한 좋아요 개수를 반환
	 */
	@Transactional(readOnly = true)
	public int countCommentLike(int commentId) {

		checkCommentIsPresent(commentId);

		return likeMapper.countCommentLike(commentId);
	}

	/**
	 * 핀에 대한 좋아요 취소
	 */
	@Transactional
	public void removePinLike(int pinId) {

		checkPinIsPresent(pinId);

		// 좋아요를 한 상태가 아닌지 확인
		if (likeMapper.countPinLikeByMemberId(SecurityUtil.getCurrentUserId(), pinId) == 0) {
			throw new BusinessException("좋아요를 누르지 않았습니다.");
		}

		likeMapper.deletePinLike(SecurityUtil.getCurrentUserId(), pinId);
	}

	/**
	 * 댓글에 대한 좋아요 취소
	 */
	@Transactional
	public void removeCommentLike(int commentId) {

		checkCommentIsPresent(commentId);

		// 좋아요를 한 상태가 아닌지 확인
		if (likeMapper.countCommentLikeByMemberId(SecurityUtil.getCurrentUserId(), commentId) == 0) {
			throw new BusinessException("좋아요를 누르지 않았습니다.");
		}

		likeMapper.deleteCommentLike(SecurityUtil.getCurrentUserId(), commentId);
	}

	/**
	 * Pin이 존재하는지 확인
	 */
	private void checkPinIsPresent(int pinId) {
		Optional<PinVO> pinVO = pinMapper.selectPinByPinId(pinId);

		if (pinVO.isEmpty()) {
			throw new BusinessException("Pin이 존재하지 않습니다.");
		}
	}

	/**
	 * Comment가 존재하는지 확인
	 */
	private void checkCommentIsPresent(int commentId) {
		Optional<CommentVO> commentVO = commentMapper.selectCommentByCommentId(commentId);

		if (commentVO.isEmpty()) {
			throw new BusinessException("댓글이 존재하지 않습니다.");
		}
	}
}
