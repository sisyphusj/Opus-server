package com.opus.member.service;

import com.opus.member.domain.Member;
import com.opus.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public void saveMember(Member member) {
        memberMapper.saveMember(member);
    }

    @Transactional
    public Member login(Member member) {
        return memberMapper.login(member);
    }

    @Transactional
    public Member findById(int mId) {
        log.info("findById inS = {}", mId);
        log.info("findById inS = {}", memberMapper.findById(mId));
        return memberMapper.findById(mId);
    }

    @Transactional
    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }

    @Transactional
    public void deleteMember(int mId) {
        memberMapper.deleteMember(mId);
    }
}
