package com.opus.pin.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PinDTO {

    @JsonProperty("imagePath")
    private String imagePath;

    private String prompt;

    @JsonProperty("negativePrompt")
    private String negativePrompt;

    private String width;

    private String height;

    private String seed;
}
