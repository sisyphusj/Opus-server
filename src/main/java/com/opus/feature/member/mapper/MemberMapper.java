package com.opus.feature.member.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.opus.feature.member.domain.MemberVO;

@Mapper
public interface MemberMapper {

	void insertMember(MemberVO member);

	int selectCountByUsername(String userName);

	int selectCountByNickname(String nickname);

	int selectCountByEmail(String email);

	Optional<MemberVO> selectMemberByMemberId(int memberId);

	void updateMember(MemberVO member);

	void deleteMember(int memberId);
}
