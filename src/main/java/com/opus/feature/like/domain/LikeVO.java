package com.opus.feature.like.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeVO {

	// 사용자 id
	private int memberId;

	// pinId/commentId
	private int id;

	public static LikeVO of(int memberId, PinLikeDTO pinLikeDTO) {
		return LikeVO.builder()
			.memberId(memberId)
			.id(pinLikeDTO.getPinId())
			.build();
	}

	public static LikeVO of(int memberId, CommentLikeDTO commentLikeDTO) {
		return LikeVO.builder()
			.memberId(memberId)
			.id(commentLikeDTO.getCommentId())
			.build();
	}

}
