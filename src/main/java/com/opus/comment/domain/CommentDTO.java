package com.opus.comment.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentDTO {

    private Integer memberId;

    @JsonProperty("topLevelCommentId")
    private Integer topLevelCommentId;

    @JsonProperty("parentNickname")
    private String parentNickname;

    @Max(1)
    private int level;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
