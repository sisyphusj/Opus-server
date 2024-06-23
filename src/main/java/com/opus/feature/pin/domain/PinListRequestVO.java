package com.opus.feature.pin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 핀 리스트 조회 요청 VO
 */

@Getter
@Builder
@AllArgsConstructor
public class PinListRequestVO {

	private Integer memberId;

	private Integer amount;

	private Integer offset;

	private String keyword;

}
