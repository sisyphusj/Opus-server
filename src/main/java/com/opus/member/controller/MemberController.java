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
@RequestMapping("/member")
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseCode> saveMember(@Valid @RequestBody MemberDTO memberDTO) {

        log.info("addMember = {} {} {} {}", memberDTO.getId(), memberDTO.getPw(), memberDTO.getNick(), memberDTO.getEmail());

        String rawPw = memberDTO.getPw();
        String encPw = passwordEncoder.encode(rawPw);
        memberDTO.setPw(encPw);

        memberService.saveMember(memberDTO);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 아이디 중복 체크
    @GetMapping("/signup/check-id/{id}")
    public ResponseEntity<ResponseCode> checkDuplicateId(@PathVariable @NotEmpty String id) {

        log.info("checkDuplicateId = {}", id);

        memberService.checkDuplicateId(id);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 닉네임 중복 체크
    @GetMapping("/signup/check-nick/{nick}")
    public ResponseEntity<ResponseCode> checkDuplicateNick(@PathVariable @NotEmpty String nick) {

        log.info("checkDuplicateNick = {}", nick);

        memberService.checkDuplicateNick(nick);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 이메일 중복 체크
    @GetMapping("/signup/check-email/{email}")
    public ResponseEntity<ResponseCode> checkDuplicateEmail(@PathVariable @NotEmpty String email) {

        log.info("checkDuplicateEmail = {}", email);

        memberService.checkDuplicateEmail(email);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
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
    public ResponseEntity<MemberVO> findById() {
        MemberVO memberVO = memberService.findById(SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(memberVO);
    }

    // 프로필 수정
    @PutMapping
    public ResponseEntity<ResponseCode> updateMember(@Valid @RequestBody MemberDTO memberDTO) {

        log.info("updateMember = {} {} {} {}", memberDTO.getId(), memberDTO.getPw(), memberDTO.getNick(), memberDTO.getEmail());

        String rawPw = memberDTO.getPw();
        String encPw = passwordEncoder.encode(rawPw);
        memberDTO.setPw(encPw);

        memberService.updateMember(memberDTO, SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<ResponseCode> deleteMember() {
        memberService.deleteMember(SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

}
