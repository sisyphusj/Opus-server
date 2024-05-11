package com.opus.comment.domain;

import lombok.Getter;

@Getter
public class CommentVO {

    private Integer cId;

    private Integer pId;

    private String nick;

    private Integer parentCommentId;

    private int level;

    private String content;
}
