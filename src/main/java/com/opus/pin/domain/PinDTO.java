package com.opus.pin.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PinDTO {

    @NotBlank
    private Integer pId;

    @NotBlank
    private String imagePath;

    @NotBlank
    private String tag;
}
