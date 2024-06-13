package com.opus.pin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class PinVO {

  private int pinId;

  private int memberId;

  private String prompt;

  private String negativePrompt;

  @Setter
  private String imagePath;

  private String width;

  private String height;

  private String seed;

  public static PinVO of(PinInsertDTO pinInsertDTO, Integer memberId) {
    return PinVO.builder()
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
