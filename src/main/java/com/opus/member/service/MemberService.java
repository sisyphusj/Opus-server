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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;

    public void saveMember(MemberDTO memberDTO) {
        Member member = Member.of(memberDTO);
        memberMapper.saveMember(member);
    }

    public ResponseEntity<ResponseCode> checkDuplicate(int type, String arg) {
        switch (type) {
            case 0:
                if (memberMapper.checkDuplicateId(arg)) {
                    throw DuplicateEntryException.builder()
                            .message("아이디가 중복됩니다.")
                            .build();
                } else {
                    return ResponseEntity.ok(ResponseCode.SUCCESS);
                }

            case 1:
                if (memberMapper.checkDuplicateNickname(arg)) {
                    throw DuplicateEntryException.builder()
                            .message("닉네임이 중복됩니다.")
                            .build();
                } else {
                    log.info("checkDuplicate inS = {}", memberMapper.checkDuplicateNickname(arg));
                    return ResponseEntity.ok(ResponseCode.SUCCESS);
                }

            case 2:
                if (memberMapper.checkDuplicateEmail(arg)) {
                    throw DuplicateEntryException.builder()
                            .message("이메일이 중복됩니다.")
                            .build();
                } else {
                    return ResponseEntity.ok(ResponseCode.SUCCESS);
                }
            default:
                throw new BusinessExceptionHandler(ResponseCode.BUSINESS_ERROR, "Invalid Input Value");
        }
    }

    public Integer login(LoginDTO loginDTO) {
        Member member = Member.of(loginDTO);

        if (memberMapper.login(member) == null) {
            throw new BusinessExceptionHandler(ResponseCode.NOT_FOUND_ERROR, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return memberMapper.login(member);
    }

    public Member findById(int mId) {

        if (memberMapper.findById(mId) == null) {
            throw new BusinessExceptionHandler(ResponseCode.NOT_FOUND_ERROR);
        }

        return memberMapper.findById(mId);
    }

    public void updateMember(MemberDTO memberDTO, int mId) {
        
        Member member = Member.of(memberDTO, mId);
        memberMapper.updateMember(member);
    }


    public void deleteMember(int mId) {
        memberMapper.deleteMember(mId);
    }
}
