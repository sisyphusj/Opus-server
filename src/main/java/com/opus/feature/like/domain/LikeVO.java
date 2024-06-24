package com.opus.feature.like.domain;

import com.opus.utils.SecurityUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeVO {

	// 사용자 id
	private int memberId;

	// pinId/commentId
	private int id;

	public static LikeVO of(PinLikeReqDTO pinLikeReqDTO) {

		return LikeVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.id(pinLikeReqDTO.getPinId())
			.build();
	}

	public static LikeVO of(CommentLikeReqDTO commentLikeReqDTO) {

		return LikeVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.id(commentLikeReqDTO.getCommentId())
			.build();
	}

}
