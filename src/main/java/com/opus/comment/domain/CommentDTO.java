package com.opus.comment.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentDTO {

    private Integer cId;

    @NotNull
    @JsonProperty("pId")
    private Integer pId;

    private Integer mId;

    @JsonProperty("parentCommentId")
    private Integer parentCommentId;

    private int level;

    private String content;

}
