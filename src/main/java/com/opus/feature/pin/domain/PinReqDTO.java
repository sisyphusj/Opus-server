package com.opus.feature.pin.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 핀 생성 요청 DTO
 */

@Getter
public class PinReqDTO {

	@NotBlank
	private String imagePath;

	@NotBlank
	private String prompt;

	private String negativePrompt;

	@NotBlank
	private String width;

	@NotBlank
	private String height;

	@NotBlank
	private String seed;
}
