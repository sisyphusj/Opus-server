package com.opus.feature.pin.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * 핀 리스트 조회 응답 VO
 */

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

	public static PinListVO of(PinReqDTO pinReqDTO, Integer memberId) {

		return PinListVO.builder()
			.memberId(memberId)
			.imagePath(pinReqDTO.getImagePath())
			.prompt(pinReqDTO.getPrompt())
			.negativePrompt(pinReqDTO.getNegativePrompt())
			.width(pinReqDTO.getWidth())
			.height(pinReqDTO.getHeight())
			.seed(pinReqDTO.getSeed())
			.build();
	}
}
