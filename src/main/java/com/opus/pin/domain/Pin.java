package com.opus.pin.domain;

import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Pin {
    private Integer mId;
    private Integer pId;
    private String imagePath;
    private String tag;

    public static Pin of(PinDTO pinDTO, Integer mId) {
        return new Pin(mId, null, pinDTO.getImagePath(), pinDTO.getTag());
    }
}
