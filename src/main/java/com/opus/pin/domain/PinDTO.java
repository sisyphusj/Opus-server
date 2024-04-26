package com.opus.pin.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PinDTO {

    @NotBlank
    private Integer pId;

    @NotBlank
    private String image_path;

    @NotBlank
    private String tag;
}
