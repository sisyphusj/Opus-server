package com.opus.comment.domain;

import com.opus.common.ResponseCode;
import com.opus.exception.BusinessExceptionHandler;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class CommentResponseDTO {
    private Integer commentId;

    private Integer pinId;

    private String nickname;

    private Integer topLevelCommentId;

    private String parentNickname;

    private int level;

    private String content;

    private String createdDate;

    private String updatedDate;

    public static CommentResponseDTO of(CommentVO comment) {
        return CommentResponseDTO.builder()
                .commentId(comment.getCommentId())
                .pinId(comment.getPinId())
                .nickname(comment.getNickname())
                .topLevelCommentId(comment.getTopLevelCommentId())
                .parentNickname(comment.getParentNickname())
                .level(comment.getLevel())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .updatedDate(comment.getUpdatedDate())
                .build();
    }

    public static List<CommentResponseDTO> of(List<CommentVO> commentVOList) {

        if (commentVOList == null) {
            return Collections.emptyList();
        }

        try {
            return commentVOList.stream()
                    .map(CommentResponseDTO::of)
                    // steram().toList는 Java 16부터 사용 가능
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ResponseCode.BUSINESS_ERROR, "CommentResponseDTO 변환 중 오류가 발생했습니다.");

        }
    }
}
