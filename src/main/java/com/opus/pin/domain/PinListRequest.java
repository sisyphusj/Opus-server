package com.opus.pin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinListRequest {

    private Integer memberId;

    private Integer amount;

    private Integer offset;

    private String keyword;

    public static PinListRequest of(Integer memberId, int offset, int amount) {
        return new PinListRequest(memberId, offset, amount, null);
    }

    public static PinListRequest of(int offset, int amount, String keyword) {
        return new PinListRequest(null, offset, amount, keyword);
    }
}
