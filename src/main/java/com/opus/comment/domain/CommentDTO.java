package com.opus.comment.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentDTO {

    @JsonProperty("cId")
    private Integer cId;

    @NotNull
    @JsonProperty("pId")
    private Integer pId;

    private Integer mId;

    @JsonProperty("topLevelCommentId")
    private Integer topLevelCommentId;

    @JsonProperty("parentNick")
    private String parentNick;

    @Max(1)
    private int level;

    private String content;
}
