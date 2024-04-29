package com.opus.member.mapper;

import com.opus.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void saveMember(Member member);

    int checkDuplicateId(String id);

    int checkDuplicateNick(String nick);

    int checkDuplicateEmail(String email);

    Integer login(Member member);

    Member findById(int mId);

    void updateMember(Member member);

    void deleteMember(int mId);
}
