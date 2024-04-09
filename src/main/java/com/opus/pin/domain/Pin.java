package com.opus.pin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pin {
    private Integer m_id;
    private Integer p_id;
    private String image_path;
    private String tag;
}
