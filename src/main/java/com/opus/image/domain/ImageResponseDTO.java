package com.opus.image.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ImageResponseDTO {

	private List<Image> images;

	@Getter
	public static class Image {

		// 이미지 고유의 id
		private String id;

		// 이미지의 형성 seed 값
		private long seed;

		// 이미지 url
		@JsonProperty("image")
		private String imageUrl;

	}

}
