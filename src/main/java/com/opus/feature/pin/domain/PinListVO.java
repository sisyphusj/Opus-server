package com.opus.feature.pin.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PinListVO {

	private int pinId;

	private int memberId;

	private String prompt;

	private String negativePrompt;

	private String imagePath;

	private String width;

	private String height;

	private String seed;

	private String nickname;

	public static PinListVO of(PinRequestDTO pinRequestDTO, Integer memberId) {

		return PinListVO.builder()
			.memberId(memberId)
			.imagePath(pinRequestDTO.getImagePath())
			.prompt(pinRequestDTO.getPrompt())
			.negativePrompt(pinRequestDTO.getNegativePrompt())
			.width(pinRequestDTO.getWidth())
			.height(pinRequestDTO.getHeight())
			.seed(pinRequestDTO.getSeed())
			.build();
	}
}
