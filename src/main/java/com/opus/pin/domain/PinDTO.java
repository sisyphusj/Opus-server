package com.opus.pin.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PinDTO {

    @NotBlank
    private String imagePath;

    private String prompt;

    private String negativePrompt;

    @NotBlank
    private String width;

    @NotBlank
    private String height;

    @NotBlank
    private String seed;
}
