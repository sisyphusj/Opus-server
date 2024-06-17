package com.opus.feature.like.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PinLikeDTO {

	@NotBlank
	private Integer pinId;

}
