package com.opus.feature.pin.domain;

import com.opus.utils.SecurityUtil;

import lombok.Builder;
import lombok.Getter;

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

	public static PinVO of(PinInsertDTO pinInsertDTO) {

		return PinVO.builder()
			.memberId(SecurityUtil.getCurrentUserId())
			.imagePath(pinInsertDTO.getImagePath())
			.prompt(pinInsertDTO.getPrompt())
			.negativePrompt(pinInsertDTO.getNegativePrompt())
			.width(pinInsertDTO.getWidth())
			.height(pinInsertDTO.getHeight())
			.seed(pinInsertDTO.getSeed())
			.build();
	}

	public void updateImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
