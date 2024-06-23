package com.opus.feature.image.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.opus.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * S3 서비스 클래스
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

	private final AmazonS3 s3Client;

	// S3 버킷 이름
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	/**
	 * URL로부터 이미지 파일을 다운로드하여 S3에 업로드
	 */
	public String uploadFileFromUrl(String imageUrl) {

		try {
			// URL 객체 생성
			URL url = new URL(imageUrl);

			// 파일 이름을 UUID로 생성
			String uniqueFileName = UUID.randomUUID() + ".jpg";

			// URL로부터 이미지 데이터를 다운로드
			byte[] imageData = downloadImageData(url);
			long contentLength = imageData.length;
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);

			// S3에 이미지 파일 업로드
			uploadToS3(uniqueFileName, byteArrayInputStream, contentLength);

			// S3에 업로드한 이미지 파일 URL 반환
			return s3Client.getUrl(bucketName, uniqueFileName).toString();

		} catch (IOException e) {
			log.error("error : ", e);
			throw new BusinessException("파일 업로드 실패");
		}
	}

	/**
	 * 이미지 파일을 S3에 업로드
	 */
	private void uploadToS3(String fileName, InputStream inputStream, long contentLength) {

		// ObjectMetadata 생성 및 설정
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/jpeg");

		// 파일 크기를 미리 설정하여 업로드
		metadata.setContentLength(contentLength);

		// PutObjectRequest 생성
		PutObjectRequest request = new PutObjectRequest(bucketName, fileName, inputStream, metadata);

		// S3에 파일 업로드
		s3Client.putObject(request);
	}

	/**
	 * URL로부터 이미지 데이터를 다운로드
	 */
	private byte[] downloadImageData(URL url) {

		// InputStream을 이용하여 이미지 데이터를 다운로드
		try (InputStream inputStream = url.openStream();
			 ByteArrayOutputStream buffer = new ByteArrayOutputStream(8192)) {

			byte[] data = new byte[4096];
			int bytesRead;

			// byteRead 값이 -1이 될 때까지 이미지 데이터를 읽어들임
			while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, bytesRead);
			}

			return buffer.toByteArray();
		} catch (IOException e) {
			throw new BusinessException("파일 업로드 실패");
		}
	}
}
