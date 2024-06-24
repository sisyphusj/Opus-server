package com.opus.feature.like.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentLikeReqDTO {

	@NotNull
	private Integer commentId;

}
