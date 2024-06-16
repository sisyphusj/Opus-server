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

	private LocalDateTime deletedDate;

	private String parentNickname;

	private String nickname;

	public static CommentVO of(CommentInsertDTO commentInsertDTO) {
		return CommentVO.builder()
			.pinId(commentInsertDTO.getPinId())
			.memberId(SecurityUtil.getCurrentUserId())
			.topLevelCommentId(commentInsertDTO.getTopLevelCommentId())
			.level(commentInsertDTO.getLevel())
			.content(commentInsertDTO.getContent())
			.parentNickname(commentInsertDTO.getParentNickname())
			.build();
	}

	public static CommentVO of(CommentUpdateDTO commentUpdateDTO) {
		return CommentVO.builder()
			.commentId(commentUpdateDTO.getCommentId())
			.pinId(commentUpdateDTO.getPinId())
			.memberId(SecurityUtil.getCurrentUserId())
			.topLevelCommentId(commentUpdateDTO.getTopLevelCommentId())
			.parentNickname(commentUpdateDTO.getParentNickname())
			.level(commentUpdateDTO.getLevel())
			.content(commentUpdateDTO.getContent())
			.build();
	}

}
