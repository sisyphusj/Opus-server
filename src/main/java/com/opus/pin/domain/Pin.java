package com.opus.pin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pin {
    private Integer mId;
    private Integer pId;
    private String image_path;
    private String tag;
}
