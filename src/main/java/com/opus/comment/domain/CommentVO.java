package com.opus.comment.domain;

import lombok.Getter;

@Getter
public class CommentVO {

    private Integer cId;

    private Integer pId;

    private String nick;

    private Integer topLevelCommentId;

    private String parentNick;

    private int level;

    private String content;

    private String createdDate;

    private String updatedDate;
}
