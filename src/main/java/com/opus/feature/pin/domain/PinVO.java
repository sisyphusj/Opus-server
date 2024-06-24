package com.opus.feature.pin.domain;

import com.opus.utils.SecurityUtil;

import lombok.Builder;
import lombok.Getter;

/**
 * 핀 VO
 */

@Builder
@Getter
public class PinVO {

	private int pinId;

	private int memberId;

	private String prompt;

	private String negativePrompt;

	private String imagePath;

	private String width;

	private String height;

	private String seed;

	public static PinVO of(PinReqDTO pinReqDTO) {

		return PinVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.imagePath(pinReqDTO.getImagePath())
			.prompt(pinReqDTO.getPrompt())
			.negativePrompt(pinReqDTO.getNegativePrompt())
			.width(pinReqDTO.getWidth())
			.height(pinReqDTO.getHeight())
			.seed(pinReqDTO.getSeed())
			.build();
	}

	public void updateImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
