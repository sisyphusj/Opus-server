package com.opus.pin.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class PinVO {
    @NotNull
    private int memberId;

    @NotNull
    private int pinId;

    private String nickname;

    @Setter
    private String imagePath;

    private String prompt;

    private String negativePrompt;

    private String width;

    private String height;

    private String seed;

    public static PinVO of(PinDTO pinDTO, Integer memberId) {
        return PinVO.builder()
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
