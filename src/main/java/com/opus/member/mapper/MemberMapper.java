package com.opus.member.mapper;

import com.opus.member.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

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
