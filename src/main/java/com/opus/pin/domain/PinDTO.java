package com.opus.pin.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PinDTO {

    private String imagePath;

    private String tag;

    @JsonProperty("nTag")
    private String nTag;

    private String width;

    private String height;

    private String seed;
}
