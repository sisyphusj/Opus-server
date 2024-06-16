package com.opus.image.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.image.domain.ImageGenerateDTO;
import com.opus.image.domain.ImageResponseDTO;
import com.opus.image.service.ImageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/images")
@RequiredArgsConstructor

public class ImageController {

	private final ImageService imageService;

	@PostMapping
	public ResponseEntity<ImageResponseDTO> generateImage(@Valid @RequestBody ImageGenerateDTO imageGenerateDTO) {
		return ApiResponse.of(imageService.generateImage(imageGenerateDTO));
	}

}
