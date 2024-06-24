package com.opus.feature.pin.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 핀 리스트 응답 DTO
 */

@Slf4j
@Getter
@Builder
public class PinListResDTO {

	private Integer pinId;

	private String nickname;

	private String imagePath;

	private String prompt;

	private String negativePrompt;

	private String width;

	private String height;

	private String seed;

	public static PinListResDTO of(PinListVO pinListVO) {

		return PinListResDTO.builder()
			.pinId(pinListVO.getPinId())
			.nickname(pinListVO.getNickname())
			.imagePath(pinListVO.getImagePath())
			.prompt(pinListVO.getPrompt())
			.negativePrompt(pinListVO.getNegativePrompt())
			.width(pinListVO.getWidth())
			.height(pinListVO.getHeight())
			.seed(pinListVO.getSeed())
			.build();
	}

	public static List<PinListResDTO> of(List<PinListVO> pinListVO) {

		return pinListVO.stream()
			.map(PinListResDTO::of)
			.toList();
	}
}
