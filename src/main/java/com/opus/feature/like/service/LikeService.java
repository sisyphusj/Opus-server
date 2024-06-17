package com.opus.feature.like.service;

import org.springframework.stereotype.Service;

import com.opus.exception.BusinessException;
import com.opus.feature.like.domain.CommentLikeDTO;
import com.opus.feature.like.domain.LikeVO;
import com.opus.feature.like.domain.PinLikeDTO;
import com.opus.feature.like.mapper.LikeMapper;
import com.opus.utils.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeMapper likeMapper;

	public void addPinLike(PinLikeDTO pinLikeDTO) {
		if (likeMapper.countPinLikeByMemberId(SecurityUtil.getCurrentUserId(), pinLikeDTO.getPinId()) > 0) {
			throw new BusinessException("이미 좋아요를 눌렀습니다.");
		}

		likeMapper.insertPinLike(LikeVO.of(SecurityUtil.getCurrentUserId(), pinLikeDTO));
	}

	public void addCommentLike(CommentLikeDTO commentLikeDTO) {
		if (likeMapper.countCommentLikeByMemberId(SecurityUtil.getCurrentUserId(), commentLikeDTO.getCommentId()) > 0) {
			throw new BusinessException("이미 좋아요를 눌렀습니다.");
		}

		likeMapper.insertCommentLike(LikeVO.of(SecurityUtil.getCurrentUserId(), commentLikeDTO));
	}

	public Integer countPinLike(int pinId) {
		return likeMapper.countPinLike(pinId);
	}

	public Integer countCommentLike(int commentId) {
		return likeMapper.countCommentLike(commentId);
	}

	public void removePinLike(int pinId) {
		if (likeMapper.countPinLikeByMemberId(SecurityUtil.getCurrentUserId(), pinId) == 0) {
			throw new BusinessException("좋아요를 누르지 않았습니다.");
		}

		likeMapper.deletePinLike(SecurityUtil.getCurrentUserId(), pinId);
	}

	public void removeCommentLike(int commentId) {
		if (likeMapper.countCommentLikeByMemberId(SecurityUtil.getCurrentUserId(), commentId) == 0) {
			throw new BusinessException("좋아요를 누르지 않았습니다.");
		}

		likeMapper.deleteCommentLike(SecurityUtil.getCurrentUserId(), commentId);
	}
}