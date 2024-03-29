package com.opus.pin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pin {
    private int m_id;
    private int p_id;
    private String image_path;
    private String tag;
}
