package com.opus.comment.domain;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentDTO {

    private Integer commentId;

    @NotBlank
    private Integer pinId;

    private Integer topLevelCommentId;

    private String parentNickname;

    @Max(1)
    private int level;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
