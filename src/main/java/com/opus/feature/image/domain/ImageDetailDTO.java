package com.opus.feature.image.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ImageDetailDTO {

	// 이미지 고유의 id
	private String id;

	// 이미지의 형성 seed 값
	private long seed;

	// 이미지 url
	@JsonProperty("image")
	private String imageUrl;

	public void updateImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
