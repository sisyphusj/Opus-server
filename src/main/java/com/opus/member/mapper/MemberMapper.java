package com.opus.member.mapper;

import com.opus.member.domain.Member;
import com.opus.member.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void saveMember(Member member);

    int checkDuplicateId(String userId);

    int checkDuplicateNickname(String nick);

    int checkDuplicateEmail(String email);

    MemberVO findByMemberId(int memberId);

    // 사용자 id 로 회원 정보 조회
    Optional<Member> findByUserId(String userId);

    void updateMember(Member member);

    void deleteMember(int memberId);
}
