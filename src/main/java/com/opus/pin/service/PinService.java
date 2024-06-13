package com.opus.pin.service;

import com.opus.utils.SecurityUtil;
import com.opus.pin.domain.*;
import com.opus.pin.mapper.PinMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
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
  public void addPin(PinInsertDTO pinInsertDTO) {
    PinVO pinVO = PinVO.of(pinInsertDTO, SecurityUtil.getCurrentUserId());

    try {
      // URL에서 이미지를 다운로드
      URL url = new URL(pinVO.getImagePath());
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
      pinVO.updateImagePath(urlPath);
      pinMapper.insertPin(pinVO);

      // 로깅 및 성공 응답 반환
      log.info("File saved successfully with name = {}", fileName);

    } catch (MalformedURLException e) {
      log.error("Invalid URL: {}", pinVO.getImagePath(), e);
      throw new IllegalArgumentException("Invalid URL: " + pinVO.getImagePath());

    } catch (IOException e) {
      log.error("IO Exception occurred while processing the image", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Transactional(readOnly = true)
  public List<PinListResponseDTO> getPinList(int offset, int amount, String keyword) {
    PinListRequestVO pinListRequestVO = PinListRequestVO.builder()
        .offset(offset)
        .amount(amount)
        .keyword(keyword)
        .build();

    return PinListResponseDTO.of(pinMapper.selectPinsByKeyword(pinListRequestVO));
  }

  @Transactional(readOnly = true)
  public List<PinListResponseDTO> getMyPinList(int offset, int amount) {
    PinListRequestVO pinListRequestVO = PinListRequestVO.builder()
        .memberId(SecurityUtil.getCurrentUserId())
        .offset(offset)
        .amount(amount)
        .build();

    return PinListResponseDTO.of(pinMapper.selectPinsByMemberId(pinListRequestVO));
  }

  @Transactional
  public PinResponseDTO getPinByPinId(int pinId) {
    return pinMapper.selectPinByPinId(pinId)
        .map(PinResponseDTO::of)
        .orElseThrow(() -> new NoSuchElementException("해당 핀을 찾을 수 없습니다."));
  }

  @Transactional(readOnly = true)
  public int countPins(String keyword) {
    return pinMapper.countPinsByKeyword(keyword);
  }

  @Transactional
  public void removePin(int pinId) {
    pinMapper.selectPinByPinId(pinId)
        .orElseThrow(() -> new NoSuchElementException("해당 핀을 찾을 수 없습니다."));

    pinMapper.deletePin(pinId, SecurityUtil.getCurrentUserId());
  }
}

