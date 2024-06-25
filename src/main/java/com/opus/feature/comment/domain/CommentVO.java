package com.opus.feature.comment.domain;

import java.time.LocalDateTime;

import com.opus.utils.SecurityUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentVO {

	private Integer commentId;

	private int pinId;

	private int memberId;

	private Integer topLevelCommentId;

	private int level;

	private String content;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private String parentNickname;

	private String nickname;

	public static CommentVO of(CommentReqDTO commentReqDTO) {

		return CommentVO.builder()
			.pinId(commentReqDTO.getPinId())
			.memberId(SecurityUtil.getCurrentUserId())
			.topLevelCommentId(commentReqDTO.getTopLevelCommentId())
			.level(commentReqDTO.getLevel())
			.content(commentReqDTO.getContent())
			.parentNickname(commentReqDTO.getParentNickname())
			.build();
	}

	public static CommentVO of(CommentUpdateReqDTO commentUpdateReqDTO) {

		return CommentVO.builder()
			.commentId(commentUpdateReqDTO.getCommentId())
			.pinId(commentUpdateReqDTO.getPinId())
			.memberId(SecurityUtil.getCurrentUserId())
			.topLevelCommentId(commentUpdateReqDTO.getTopLevelCommentId())
			.parentNickname(commentUpdateReqDTO.getParentNickname())
			.level(commentUpdateReqDTO.getLevel())
			.content(commentUpdateReqDTO.getContent())
			.build();
	}

}
