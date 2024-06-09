package com.opus.comment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentVO {

    private Integer commentId;

    private int pinId;

    private int memberId;

    private String nickname;

    private Integer topLevelCommentId;

    private String parentNickname;

    private int level;

    private String content;

    private String createdDate;

    private String updatedDate;
}
