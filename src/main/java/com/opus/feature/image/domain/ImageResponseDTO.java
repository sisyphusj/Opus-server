package com.opus.feature.image.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ImageResponseDTO {

	@JsonProperty("images")
	private List<ImageDetailDTO> imageDetails;
}
