package com.opus.pin.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PinListRequestVO {

  private Integer memberId;

  private Integer amount;

  private Integer offset;

  private String keyword;

}
