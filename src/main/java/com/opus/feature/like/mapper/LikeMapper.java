package com.opus.feature.like.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.opus.feature.like.domain.LikeVO;

@Mapper
public interface LikeMapper {

	void insertPinLike(LikeVO likeVO);

	void insertCommentLike(LikeVO likeVO);

	int countPinLike(int pinId);

	int countCommentLike(int commentId);

	int countPinLikeByMemberId(int memberId, int pinId);

	int countCommentLikeByMemberId(int memberId, int commentId);

	void deletePinLike(int memberId, int pinId);

	void deleteCommentLike(int memberId, int commentId);
}
