package com.opus.comment.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {

    private Integer commentId;

    private Integer pinId;

    private Integer memberId;

    private Integer topLevelCommentId;

    private String parentNickname;

    private int level;

    private String content;

}