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

  public static PinListResponseDTO of(PinListVO pinListVO) {
    return PinListResponseDTO.builder()
        .pinId(pinListVO.getPinId())
        .nickname(pinListVO.getNickname())
        .imagePath(pinListVO.getImagePath())
        .prompt(pinListVO.getPrompt())
        .negativePrompt(pinListVO.getNegativePrompt())
        .width(pinListVO.getWidth())
        .height(pinListVO.getHeight())
        .seed(pinListVO.getSeed())
        .build();
  }

  public static List<PinListResponseDTO> of(List<PinListVO> pinListVO) {
    return pinListVO.stream()
        .map(PinListResponseDTO::of)
        .toList();
  }
}
