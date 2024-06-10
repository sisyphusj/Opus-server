package com.opus.pin.domain;

import com.opus.common.ResponseCode;
import com.opus.exception.BusinessExceptionHandler;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PinListResponseDTO {
    private Integer pinId;
    private String nickname;
    private String imagePath;
    private String prompt;
    private String negativePrompt;

    private String width;
    private String height;
    private String seed;

    public static PinListResponseDTO of(PinVO pinVO) {
        return PinListResponseDTO.builder()
                .pinId(pinVO.getPinId())
                .nickname(pinVO.getNickname())
                .imagePath(pinVO.getImagePath())
                .prompt(pinVO.getPrompt())
                .negativePrompt(pinVO.getNegativePrompt())
                .width(pinVO.getWidth())
                .height(pinVO.getHeight())
                .seed(pinVO.getSeed())
                .build();
    }

    public static List<PinListResponseDTO> of(List<PinVO> pinVOList) {

        if (pinVOList == null) {
            return Collections.emptyList();
        }

        try {
            return pinVOList.stream()
                    .map(PinListResponseDTO::of)
                    // steram().toList는 Java 16부터 사용 가능
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ResponseCode.BUSINESS_ERROR, "PinResponseDTO 변환 중 오류가 발생했습니다.");
        }
    }
}
