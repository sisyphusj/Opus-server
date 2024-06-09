package com.opus.pin.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PinListRequestVO {

    private Integer memberId;

    private Integer amount;

    private Integer offset;

    private String keyword;

    public static PinListRequestVO of(Integer memberId, int offset, int amount) {
        return new PinListRequestVO(memberId, offset, amount, null);
    }

    public static PinListRequestVO of(int offset, int amount, String keyword) {
        return new PinListRequestVO(null, offset, amount, keyword);
    }
}
