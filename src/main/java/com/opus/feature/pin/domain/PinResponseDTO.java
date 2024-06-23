package com.opus.feature.pin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 핀 응답 DTO
 */

@Slf4j
@Getter
@Builder
public class PinResponseDTO {

	private Integer pinId;

	private String nickname;

	private String imagePath;

	private String prompt;

	private String negativePrompt;

	private String width;

	private String height;

	private String seed;

	public static PinResponseDTO of(PinVO pinVO) {

		return PinResponseDTO.builder()
			.pinId(pinVO.getPinId())
			.imagePath(pinVO.getImagePath())
			.prompt(pinVO.getPrompt())
			.negativePrompt(pinVO.getNegativePrompt())
			.width(pinVO.getWidth())
			.height(pinVO.getHeight())
			.seed(pinVO.getSeed())
			.build();
	}
}
