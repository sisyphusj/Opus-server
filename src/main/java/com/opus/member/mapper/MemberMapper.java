package com.opus.member.mapper;

import com.opus.member.domain.Member;
import com.opus.member.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void saveMember(Member member);

    int checkDuplicateId(String id);

    int checkDuplicateNick(String nick);

    int checkDuplicateEmail(String email);

    MemberVO findById(int mId);

    Optional<Member> findByUserId(String userId);

    void updateMember(Member member);

    void deleteMember(int mId);
}
