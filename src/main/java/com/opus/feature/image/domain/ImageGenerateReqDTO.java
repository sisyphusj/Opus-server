package com.opus.feature.image.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageGenerateReqDTO {

	@NotBlank
	private String version;

	// 프롬프트
	@NotBlank
	private String prompt;

	// 부정 프롬프트
	@JsonProperty("negative_prompt")
	private String negativePrompt;

	@NotNull
	private Integer width;

	@NotNull
	private Integer height;

	// 이미지 스타일 시드 값
	// 설정 시 배열의 크기는 samples와 동일해야 한다.
	private Integer[] seed;

	// 이미지 품질
	@JsonProperty("num_inference_steps")
	private Integer numInferenceSteps;

	// 프롬프트와 얼마나 가까운 결과를 도출하는지 설정하는 값
	@JsonProperty("guidance_scale")
	private Double guidanceScale;

	// 이미지 생성 개수
	private Integer samples;

}
