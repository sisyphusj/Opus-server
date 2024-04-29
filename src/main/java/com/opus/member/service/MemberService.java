package com.opus.member.service;

import com.opus.common.ResponseCode;
import com.opus.config.exception.BusinessExceptionHandler;
import com.opus.config.exception.DuplicateEntryException;
import com.opus.member.domain.LoginDTO;
import com.opus.member.domain.Member;
import com.opus.member.domain.MemberDTO;
import com.opus.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public void saveMember(MemberDTO memberDTO) {
        Member member = Member.of(memberDTO);
        memberMapper.saveMember(member);
    }

    @Transactional(readOnly = true)
    public void checkDuplicateId(String id) {
        if (memberMapper.checkDuplicateId(id) > 0 ) {
            throw new DuplicateEntryException(ResponseCode.DATA_ALREADY_EXIST, "이미 존재하는 아이디입니다.");
        }
    }

    @Transactional(readOnly = true)
    public void checkDuplicateNick(String nick) {
        if (memberMapper.checkDuplicateNick(nick) > 0) {
            throw new DuplicateEntryException(ResponseCode.DATA_ALREADY_EXIST, "이미 존재하는 닉네임입니다.");
        }
    }

    @Transactional(readOnly = true)
    public void checkDuplicateEmail(String email) {
        if (memberMapper.checkDuplicateEmail(email) > 0) {
            throw new DuplicateEntryException(ResponseCode.DATA_ALREADY_EXIST, "이미 존재하는 이메일입니다.");
        }
    }

    @Transactional(readOnly = true)
    public Integer login(LoginDTO loginDTO) {
        Member member = Member.of(loginDTO);

        Optional<Integer> mId = Optional.of(memberMapper.login(member));
        return mId.orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.NOT_FOUND_ERROR, "아이디 또는 비밀번호가 일치하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Member findById(int mId) {

        Optional<Member> member = Optional.of(memberMapper.findById(mId));
        return member.orElseThrow(() -> new BusinessExceptionHandler(ResponseCode.NOT_FOUND_ERROR, "해당 회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void updateMember(MemberDTO memberDTO, int mId) {
        Member member = Member.of(memberDTO, mId);
        memberMapper.updateMember(member);
    }

    @Transactional
    public void deleteMember(int mId) {
        memberMapper.deleteMember(mId);
    }
}
