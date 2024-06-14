package com.opus.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.opus.exception.BusinessException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFileFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String originalFileName = extractFileNameFromUrl(url);
            String uniqueFileName = generateUniqueFileName(originalFileName);

            try (InputStream inputStream = url.openStream()) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType("image/jpeg");

                PutObjectRequest request = new PutObjectRequest(bucketName, uniqueFileName, inputStream, metadata);
                s3Client.putObject(request);
            }

            return s3Client.getUrl(bucketName, uniqueFileName).toString();

        } catch (IOException e) {
            log.error("error : ", e);
            throw new BusinessException("파일 업로드 실패");
        }
    }

    private String extractFileNameFromUrl(URL url) {
        String path = url.getPath();
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        if (fileName.isEmpty()) {
            log.warn("URL에서 파일 이름을 추출할 수 없습니다. 기본 파일 이름 'image' 을 사용합니다.");
            return "image";
        }
        return fileName;
    }

    private String generateUniqueFileName(String originalFileName) {
        String randomName = UUID.randomUUID().toString();
        if (originalFileName != null && !originalFileName.isEmpty()) {
            int extensionIndex = originalFileName.lastIndexOf('.');
            if (extensionIndex != -1) {
                return randomName + originalFileName.substring(extensionIndex);
            } else {
                log.warn("파일 이름에 확장자가 없습니다. 기본 확장자 '.jpg'을 사용합니다.");
                return randomName + ".jpg";
            }
        } else {
            log.error("파일 이름이 존재하지 않습니다.");
            throw new BusinessException("파일 업로드 실패");
        }
    }
}
