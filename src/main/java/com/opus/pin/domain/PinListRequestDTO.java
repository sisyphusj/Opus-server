package com.opus.pin.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PinListRequestDTO {
    @NotBlank
    private Integer amount;

    @NotBlank
    private Integer offset;
}
