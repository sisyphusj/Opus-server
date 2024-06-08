package com.opus.pin.domain;

import lombok.Getter;

@Getter
public class PinVO {
    private Integer pinId;
    private String nickname;
    private String imagePath;
    private String prompt;
    private String negativePrompt;

    private String width;
    private String height;
    private String seed;
}
