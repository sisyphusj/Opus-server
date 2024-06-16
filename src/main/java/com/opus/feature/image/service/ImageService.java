package com.opus.feature.image.service;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.opus.aws.S3Service;
import com.opus.exception.BusinessException;
import com.opus.feature.image.domain.ImageDetailDTO;
import com.opus.feature.image.domain.ImageGenerateDTO;
import com.opus.feature.image.domain.ImageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

	@Value("${kakao.accessKey}")
	private String accessKey;
	private final RestTemplate restTemplate;
	private final S3Service s3Service;

	public List<ImageDetailDTO> generateImage(ImageGenerateDTO imageGenerateDTO) {
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

		ResponseEntity<ImageResponseDTO> responseEntity = restTemplate.exchange(requestEntity, ImageResponseDTO.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return processImages(responseEntity);
		} else {
			throw new BusinessException("이미지 생성을 실패하였습니다.");
		}
	}

	public List<ImageDetailDTO> processImages(ResponseEntity<ImageResponseDTO> responseEntity) {
		// api에서 절대 null을 반환하지 않는다.
		ImageResponseDTO responseDTO = Objects.requireNonNull(responseEntity.getBody(), "이미지를 생성할 수 없습니다.");

		return responseDTO.getImageDetails().stream()
			.map(
				imageDetailDTO -> {
					String originalUrl = imageDetailDTO.getImageUrl();
					imageDetailDTO.updateImageUrl(s3Service.uploadFileFromUrl(originalUrl));

					return imageDetailDTO;
				}
			)
			.toList();
	}
}
