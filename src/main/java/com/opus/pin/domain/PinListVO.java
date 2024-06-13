package com.opus.pin.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PinListVO {

  private int pinId;

  private int memberId;

  private String prompt;

  private String negativePrompt;

  private String imagePath;

  private String width;

  private String height;

  private String seed;

  private String nickname;

  public static PinListVO of(PinInsertDTO pinInsertDTO, Integer memberId) {
    return PinListVO.builder()
        .memberId(memberId)
        .imagePath(pinInsertDTO.getImagePath())
        .prompt(pinInsertDTO.getPrompt())
        .negativePrompt(pinInsertDTO.getNegativePrompt())
        .width(pinInsertDTO.getWidth())
        .height(pinInsertDTO.getHeight())
        .seed(pinInsertDTO.getSeed())
        .build();
  }
}
