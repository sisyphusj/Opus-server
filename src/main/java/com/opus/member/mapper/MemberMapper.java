package com.opus.member.mapper;

import com.opus.member.domain.MemberResponseDTO;
import com.opus.member.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void saveMember(MemberVO member);

    int checkDuplicateUserName(String userName);

    int checkDuplicateNickname(String nickname);

    int checkDuplicateEmail(String email);

    MemberVO findByMemberId(int memberId);

    void updateMember(MemberVO member);

    void deleteMember(int memberId);
}
