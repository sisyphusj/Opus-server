package com.opus.pin.service;

import com.opus.exception.BusinessExceptionHandler;
import com.opus.utils.SecurityUtil;
import com.opus.common.ResponseCode;
import com.opus.pin.domain.*;
import com.opus.pin.mapper.PinMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    public void savePin(PinDTO pinDTO) {
        PinVO pin = PinVO.of(pinDTO, SecurityUtil.getCurrentUserId());

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

        } catch (MalformedURLException e) {
            log.error("Invalid URL: {}", pin.getImagePath(), e);
            throw new IllegalArgumentException("Invalid URL: " + pin.getImagePath());

        } catch (IOException e) {
            log.error("IO Exception occurred while processing the image", e);
            throw new BusinessExceptionHandler(ResponseCode.IO_EXCEPTION,"IO Exception occurred while processing the image");
        }
    }

    @Transactional(readOnly = true)
    public List<PinListResponseDTO> getPinList(int offset, int amount, String keyword) {
        PinListRequestVO pinListRequestVO = PinListRequestVO.of(offset, amount, keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return PinListResponseDTO.of(pinMapper.getPinList(pinListRequestVO));
        }
        return PinListResponseDTO.of(pinMapper.getPinListByKeyword(pinListRequestVO));
    }

    @Transactional(readOnly = true)
    public List<PinListResponseDTO> getMyPinList(int offset, int amount) {
        PinListRequestVO pinListRequestVO = PinListRequestVO.of(SecurityUtil.getCurrentUserId(), offset, amount);
        return PinListResponseDTO.of(pinMapper.getMyPinList(pinListRequestVO));
    }

    public PinListResponseDTO getPinByPinId(int pinId) {
        return PinListResponseDTO.of(pinMapper.getPinByPinId(pinId));
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

