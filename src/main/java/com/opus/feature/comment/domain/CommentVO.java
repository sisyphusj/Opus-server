package com.opus.feature.comment.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentVO {

	private Integer commentId;

	private int pinId;

	private int memberId;

	private Integer topLevelCommentId;

	private int level;

	private String content;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private LocalDateTime deletedDate;

	private String parentNickname;

	private String nickname;

}
