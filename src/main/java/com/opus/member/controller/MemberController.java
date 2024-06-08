package com.opus.member.controller;

import com.opus.auth.SecurityUtil;
import com.opus.common.ResponseCode;
import com.opus.member.domain.*;
import com.opus.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<ResponseCode> saveMember(@Valid @RequestBody MemberDTO memberDTO) {
        log.info("addMember = {} {} {} {}", memberDTO.getId(), memberDTO.getPw(), memberDTO.getNick(), memberDTO.getEmail());
        memberService.saveMember(memberDTO);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 아이디 중복 체크
    @GetMapping("/check/id/{id}")
    public ResponseEntity<Boolean> checkDuplicateId(@PathVariable @NotEmpty String id) {
        log.info("checkDuplicateId = {}", id);
        return ResponseEntity.ok(memberService.checkDuplicateId(id));
    }

    // 닉네임 중복 체크
    @GetMapping("/check/nick/{nick}")
    public ResponseEntity<Boolean> checkDuplicateNick(@PathVariable @NotEmpty String nick) {
        log.info("checkDuplicateNick = {}", nick);
        return ResponseEntity.ok(memberService.checkDuplicateNick(nick));
    }

    // 이메일 중복 체크
    @GetMapping("/check/email/{email}")
    public ResponseEntity<Boolean> checkDuplicateEmail(@PathVariable @NotEmpty String email) {
        log.info("checkDuplicateEmail = {}", email);
        return ResponseEntity.ok(memberService.checkDuplicateEmail(email));
    }

    // 로그인
    @PostMapping("/login")
    public TokenDTO login(@Valid @RequestBody LoginDTO loginDTO) {
        return memberService.login(loginDTO);
    }

    // 엑세스 토큰 재발급
    @PostMapping("/reissue")
    public TokenDTO reissue(@Valid @RequestBody TokenDTO requestTokenDTO) {
        return memberService.reissue(requestTokenDTO);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<ResponseCode> logout() {
        memberService.logout(String.valueOf(SecurityUtil.getCurrentUserId()));
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<MemberVO> getMyProfile() {
        MemberVO memberVO = memberService.getMyProfile(SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(memberVO);
    }

    // 프로필 수정
    @PutMapping("/update")
    public ResponseEntity<ResponseCode> updateMember(@Valid @RequestBody MemberDTO memberDTO) {
        log.info("updateMember = {} {} {} {}", memberDTO.getId(), memberDTO.getPw(), memberDTO.getNick(), memberDTO.getEmail());
        memberService.updateMember(memberDTO, SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 회원 탈퇴
    @DeleteMapping("/resign")
    public ResponseEntity<ResponseCode> deleteMember() {
        memberService.deleteMember(SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }
}
