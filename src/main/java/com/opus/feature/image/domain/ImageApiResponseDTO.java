package com.opus.feature.image.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ImageApiResponseDTO {

	// 이미지 생성 결과
	@JsonProperty("images")
	private List<ImageDetailResDTO> imageDetails;
}
