package com.opus.pin.domain;

import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Pin {
    private Integer memberId;
    private Integer pinId;
    private String imagePath;
    private String prompt;
    private String negativePrompt;

    private String width;
    private String height;
    private String seed;

    public static Pin of(PinDTO pinDTO, Integer memberId) {
        return new Pin(memberId, null, pinDTO.getImagePath(), pinDTO.getPrompt(), pinDTO.getNegativePrompt(), pinDTO.getWidth(), pinDTO.getHeight(), pinDTO.getSeed());
    }
}
