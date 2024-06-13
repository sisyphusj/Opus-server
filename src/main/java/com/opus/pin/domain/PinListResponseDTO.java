package com.opus.pin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Builder
public class PinListResponseDTO {

  private Integer pinId;

  private String nickname;

  private String imagePath;

  private String prompt;

  private String negativePrompt;

  private String width;

  private String height;

  private String seed;

  public static PinListResponseDTO of(PinVO pinVO) {
    return PinListResponseDTO.builder()
        .pinId(pinVO.getPinId())
        .nickname(pinVO.getNickname())
        .imagePath(pinVO.getImagePath())
        .prompt(pinVO.getPrompt())
        .negativePrompt(pinVO.getNegativePrompt())
        .width(pinVO.getWidth())
        .height(pinVO.getHeight())
        .seed(pinVO.getSeed())
        .build();
  }

  public static List<PinListResponseDTO> of(List<PinVO> pinVOList) {
    return pinVOList.stream()
        .map(PinListResponseDTO::of)
        .toList();
  }
}
