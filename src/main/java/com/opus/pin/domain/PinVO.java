package com.opus.pin.domain;

import lombok.Getter;

@Getter
public class PinVO {
    private Integer pId;
    private String nick;
    private String imagePath;
    private String tag;
    private String nTag;

    private String width;
    private String height;
    private String seed;
}
