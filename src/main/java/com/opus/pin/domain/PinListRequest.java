package com.opus.pin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinListRequest {

    private Integer mId;

    private Integer amount;

    private Integer offset;

    public static PinListRequest of(PinListRequestDTO pinListRequestDTO, Integer mId) {
        return new PinListRequest(mId, pinListRequestDTO.getAmount(), pinListRequestDTO.getOffset());
    }

    public static PinListRequest of(PinListRequestDTO pinListRequestDTO) {
        return new PinListRequest(null, pinListRequestDTO.getAmount(), pinListRequestDTO.getOffset());
    }
}
