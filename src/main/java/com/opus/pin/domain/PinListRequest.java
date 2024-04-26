package com.opus.pin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PinListRequest {

    private Integer mId;

    private Integer amount;

    private Integer offset;
}
