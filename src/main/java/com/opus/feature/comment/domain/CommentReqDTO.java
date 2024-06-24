package com.opus.feature.comment.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentReqDTO {

	@NotNull
	private Integer pinId;

	private Integer topLevelCommentId;

	private String parentNickname;

	@Max(1)
	private int level;

	@NotBlank
	private String content;
}
