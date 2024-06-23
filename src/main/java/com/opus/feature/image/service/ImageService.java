package com.opus.feature.image.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

	// 카카오 API 키
	@Value("${kakao.accessKey}")
	private String accessKey;

	// 카카오 API URL
	private static final String BASE_URL = "https://api.kakaobrain.com/v2/inference/karlo/t2i";

	// RestTemplate
	private final RestTemplate restTemplate;

	// S3 서비스
	private final S3Service s3Service;

	/**
	 * 이미지 생성
	 */
	public List<ImageDetailDTO> generateImage(ImageGenerateDTO imageGenerateDTO) {

		// URI 생성
		URI uri = UriComponentsBuilder
			.fromUriString(BASE_URL)
			.encode()
			.build()
			.toUri();

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "KakaoAK " + accessKey);

		log.info("uri = {}", uri);

		// 요청 객체 생성
		RequestEntity<ImageGenerateDTO> requestEntity = RequestEntity
			.post(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.headers(headers)
			.body(imageGenerateDTO);

		// rest template을 이용하여 요청
		ResponseEntity<ImageResponseDTO> responseEntity = restTemplate.exchange(requestEntity, ImageResponseDTO.class);

		// 응답에 문제가 있는 경우 예외 처리
		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			throw new BusinessException("이미지 생성을 실패하였습니다.");
		}

		// 이미지 업로드 후 응답
		return processImages(responseEntity);
	}

	/**
	 * 이미지를 업로드하고 이미지 URL 리스트를 반환
	 */
	private List<ImageDetailDTO> processImages(ResponseEntity<ImageResponseDTO> responseEntity) {

		// 응답이 null 인 경우 예외 처리
		if (responseEntity.getBody() == null) {
			log.error("error : responseEntity.getBody is Null");
			throw new BusinessException("이미지를 생성할 수 없습니다.");
		}

		ImageResponseDTO responseDTO = responseEntity.getBody();
		List<ImageDetailDTO> imageDetailDTOList = new ArrayList<>();

		// imageDetails를 순회하며 이미지를 업로드
		for (ImageDetailDTO imageDetailDTO : responseDTO.getImageDetails()) {
			String originalUrl = imageDetailDTO.getImageUrl();
			imageDetailDTO.updateImageUrl(s3Service.uploadFileFromUrl(originalUrl));
			imageDetailDTOList.add(imageDetailDTO);
		}

		// aws 주소로 변경된 이미지 URL 리스트 반환
		return imageDetailDTOList;
	}
}
