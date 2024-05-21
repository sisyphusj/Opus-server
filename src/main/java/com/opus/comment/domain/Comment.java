package com.opus.comment.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {

    private Integer cId;

    private Integer pId;

    private Integer mId;

    private Integer parentCommentId;

    private int level;

    private String content;
}