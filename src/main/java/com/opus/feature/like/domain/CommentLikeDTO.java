package com.opus.feature.like.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentLikeDTO {

	@NotNull
	private Integer commentId;

}
