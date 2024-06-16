package com.opus.image.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.opus.image.domain.ImageGenerateDTO;
import com.opus.image.domain.ImageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

	@Value("${kakao.accessKey}")
	private String accessKey;
	private final RestTemplate restTemplate;

	@Transactional
	public ImageResponseDTO generateImage(ImageGenerateDTO imageGenerateDTO) {
		URI uri = UriComponentsBuilder
			.fromUriString("https://api.kakaobrain.com/v2/inference/karlo/t2i")
			.encode()
			.build()
			.toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "KakaoAK " + accessKey);

		log.info("uri = {}", uri);

		RequestEntity<ImageGenerateDTO> requestEntity = RequestEntity
			.post(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.headers(headers)
			.body(imageGenerateDTO);

		log.info("RequestEntity Headers: {}", requestEntity.getHeaders());
		log.info("RequestEntity Body: {}", requestEntity.getBody());

		ResponseEntity<ImageResponseDTO> responseEntity = restTemplate.exchange(requestEntity, ImageResponseDTO.class);

		log.info("카카오 응답: {}", responseEntity);

		return responseEntity.getBody();
	}
}
