package com.opus.pin.service;

import com.opus.auth.SecurityUtil;
import com.opus.common.ResponseCode;
import com.opus.pin.domain.*;
import com.opus.pin.mapper.PinMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class PinService {

    @Value("${local.url}")
    private String imageStoragePath;

    @Value("${local.url1}")
    private String imageAccessPath;

    private final PinMapper pinMapper;

    private String generateFileName() {
        int random = ThreadLocalRandom.current().nextInt(1, 1001);
        return String.format("image%d.jpg", random);
    }

    @Transactional
    public ResponseEntity<ResponseCode> savePin(PinDTO pinDTO) {
        Pin pin = Pin.of(pinDTO, SecurityUtil.getCurrentUserId());

        try {
            // URL에서 이미지를 다운로드
            URL url = new URL(pin.getImagePath());
            String fileName = generateFileName();
            Path tempFilePath = Files.createTempFile("tempfile", ".jpg");

            // 이미지 다운로드 및 임시 파일에 저장
            try (InputStream inputStream = url.openStream()) {
                Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // 임시 파일을 최종 디렉토리로 이동
            Path destinationPath = Path.of(imageStoragePath, fileName);
            Files.move(tempFilePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // 이미지 경로 업데이트 및 저장
            String urlPath = imageAccessPath + fileName;
            pin.setImagePath(urlPath);
            pinMapper.savePin(pin);

            // 로깅 및 성공 응답 반환
            log.info("File saved successfully with name = {}", fileName);
            return ResponseEntity.ok(ResponseCode.SUCCESS);

        } catch (MalformedURLException e) {
            log.error("Invalid URL: {}", pin.getImagePath(), e);
            return ResponseEntity.badRequest().body(ResponseCode.INVALID_INPUT_VALUE);
        } catch (IOException e) {
            log.error("IO Exception occurred while processing the image", e);
            return ResponseEntity.badRequest().body(ResponseCode.IO_EXCEPTION);
        }
    }

    @Transactional(readOnly = true)
    public List<PinVO> getPinList(int offset, int amount, String keyword) {
        PinListRequest pinListRequest = PinListRequest.of(offset, amount, keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return pinMapper.getPinList(pinListRequest);
        }
        return pinMapper.getPinListByKeyword(pinListRequest);
    }

    @Transactional(readOnly = true)
    public List<PinVO> getMyPinList(int offset, int amount) {
        PinListRequest pinListRequest = PinListRequest.of(SecurityUtil.getCurrentUserId(), offset, amount);
        return pinMapper.getMyPinList(pinListRequest);
    }

    public PinVO getPinByPinId(int pinId) {
        return pinMapper.getPinByPinId(pinId);
    }

    @Transactional(readOnly = true)
    public int getTotalCount(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return pinMapper.getTotalCount();
        }
        return pinMapper.getTotalCountByKeyword(keyword);
    }

    @Transactional
    public void deletePin(int pinId) {
        pinMapper.deletePin(pinId, SecurityUtil.getCurrentUserId());
    }

}

