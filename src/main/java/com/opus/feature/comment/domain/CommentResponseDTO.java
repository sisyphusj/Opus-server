package com.opus.feature.comment.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDTO {

	private Integer commentId;

	private Integer pinId;

	private String nickname;

	private Integer topLevelCommentId;

	private String parentNickname;

	private int level;

	private String content;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private LocalDateTime deletedDate;

	public static CommentResponseDTO of(CommentVO comment) {
		return CommentResponseDTO.builder()
			.commentId(comment.getCommentId())
			.pinId(comment.getPinId())
			.nickname(comment.getNickname())
			.topLevelCommentId(comment.getTopLevelCommentId())
			.parentNickname(comment.getParentNickname())
			.level(comment.getLevel())
			.content(comment.getContent())
			.createdDate(comment.getCreatedDate())
			.updatedDate(comment.getUpdatedDate())
			.deletedDate(comment.getDeletedDate())
			.build();
	}

	public static List<CommentResponseDTO> of(List<CommentVO> commentVOList) {

		return commentVOList.stream()
			.map(CommentResponseDTO::of)
			.toList();
	}
}
