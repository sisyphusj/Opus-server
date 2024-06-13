package com.opus.pin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class PinListVO {

  private int pinId;

  private int memberId;

  private String prompt;

  private String negativePrompt;

  @Setter
  private String imagePath;

  private String width;

  private String height;

  private String seed;

  private String nickname;

  public static PinListVO of(PinDTO pinDTO, Integer memberId) {
    return PinListVO.builder()
        .memberId(memberId)
        .imagePath(pinDTO.getImagePath())
        .prompt(pinDTO.getPrompt())
        .negativePrompt(pinDTO.getNegativePrompt())
        .width(pinDTO.getWidth())
        .height(pinDTO.getHeight())
        .seed(pinDTO.getSeed())
        .build();
  }
}
