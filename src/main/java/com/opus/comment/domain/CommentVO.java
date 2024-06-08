package com.opus.comment.domain;

import lombok.Getter;

@Getter
public class CommentVO {

    private Integer commentId;

    private Integer pinId;

    private String nickname;

    private Integer topLevelCommentId;

    private String parentNickname;

    private int level;

    private String content;

    private String createdDate;

    private String updatedDate;
}
