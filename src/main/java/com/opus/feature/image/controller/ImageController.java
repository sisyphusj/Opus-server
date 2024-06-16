package com.opus.feature.image.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.feature.image.domain.ImageDetailDTO;
import com.opus.feature.image.domain.ImageGenerateDTO;
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

	@PostMapping
	public ResponseEntity<List<ImageDetailDTO>> generateImage(@Valid @RequestBody ImageGenerateDTO imageGenerateDTO) {
		return ApiResponse.of(imageService.generateImage(imageGenerateDTO));
	}

}
