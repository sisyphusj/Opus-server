package com.opus.member.mapper;

import com.opus.member.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void insertMember(MemberVO member);

    // 같은 username인 회원 수 조회
    int selectCountByUsername(String userName);

    // 같은 nickname인 회원 수 조회
    int selectCountByNickname(String nickname);

    // 같은 email인 회원 수 조회
    int selectCountByEmail(String email);

    MemberVO selectMemberByMemberId(int memberId);

    void updateMember(MemberVO member);

    void deleteMember(int memberId);
}
