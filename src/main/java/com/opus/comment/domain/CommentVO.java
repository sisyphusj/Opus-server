package com.opus.comment.domain;

import lombok.Getter;

import java.util.Date;

@Getter
public class CommentVO {

    private Integer cId;

    private Integer pId;

    private String nick;

    private Integer parentCommentId;

    private int level;

    private String content;

    private String createdDate;
}
