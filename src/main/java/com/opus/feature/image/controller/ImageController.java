package com.opus.feature.image.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.feature.image.domain.ImageDetailResDTO;
import com.opus.feature.image.domain.ImageGenerateReqDTO;
import com.opus.feature.image.service.ImageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	/**
	 * 이미지 생성
	 */
	@PostMapping
	public ResponseEntity<List<ImageDetailResDTO>> generateImage(
		@Valid @RequestBody ImageGenerateReqDTO imageGenerateReqDTO) {
		return ApiResponse.of(imageService.generateImage(imageGenerateReqDTO));
	}

}
