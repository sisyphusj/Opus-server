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

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

	private final AmazonS3 s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public String uploadFileFromUrl(String imageUrl) {

		try {
			URL url = new URL(imageUrl);
			String uniqueFileName = UUID.randomUUID() + ".jpg";

			byte[] imageData = downloadImageData(url);
			long contentLength = imageData.length;
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);

			uploadToS3(uniqueFileName, byteArrayInputStream, contentLength);
			return s3Client.getUrl(bucketName, uniqueFileName).toString();

		} catch (IOException e) {
			log.error("error : ", e);
			throw new BusinessException("파일 업로드 실패");
		}
	}

	private void uploadToS3(String fileName, InputStream inputStream, long contentLength) {

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/jpeg");
		metadata.setContentLength(contentLength);

		PutObjectRequest request = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
		s3Client.putObject(request);
	}

	private byte[] downloadImageData(URL url) {

		try (InputStream inputStream = url.openStream();
			 ByteArrayOutputStream buffer = new ByteArrayOutputStream(8192)) {

			byte[] data = new byte[4096];
			int bytesRead;

			while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, bytesRead);
			}

			return buffer.toByteArray();
		} catch (IOException e) {
			throw new BusinessException("파일 업로드 실패");
		}
	}
}
