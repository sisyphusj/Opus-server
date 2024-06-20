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

	public static LikeVO of(PinLikeDTO pinLikeDTO) {

		return LikeVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.id(pinLikeDTO.getPinId())
			.build();
	}

	public static LikeVO of(CommentLikeDTO commentLikeDTO) {

		return LikeVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.id(commentLikeDTO.getCommentId())
			.build();
	}

}
