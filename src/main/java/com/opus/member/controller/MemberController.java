package com.opus.member.controller;

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
    public ResponseEntity<Void> saveMember(@Valid @RequestBody MemberDTO memberDTO) {
        memberService.saveMember(memberDTO);
        return ResponseEntity.ok().build();
    }

    // 아이디 중복 체크
    @GetMapping("/check/user-id/{userId}")
    public ResponseEntity<Boolean> checkDuplicateId(@PathVariable @NotEmpty String userId) {
        return ResponseEntity.ok(memberService.checkDuplicateId(userId));
    }

    // 닉네임 중복 체크
    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<Boolean> checkDuplicateNickname(@PathVariable @NotEmpty String nickname) {
        return ResponseEntity.ok(memberService.checkDuplicateNickname(nickname));
    }

    // 이메일 중복 체크
    @GetMapping("/check/email/{email}")
    public ResponseEntity<Boolean> checkDuplicateEmail(@PathVariable @NotEmpty String email) {
        return ResponseEntity.ok(memberService.checkDuplicateEmail(email));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(memberService.login(loginDTO));
    }

    // 엑세스 토큰 재발급
    @PostMapping("/reissue-token")
    public ResponseEntity<TokenDTO> reissueToken(@Valid @RequestBody TokenDTO requestTokenDTO) {
        return ResponseEntity.ok(memberService.reissueToken(requestTokenDTO));
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        memberService.logout();
        return ResponseEntity.ok().build();
    }

    // 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<MemberVO> getMyProfile() {
        return ResponseEntity.ok(memberService.getMyProfile());
    }

    // 프로필 수정
    @PutMapping
    public ResponseEntity<Void> updateMember(@Valid @RequestBody MemberDTO memberDTO) {
        memberService.updateMember(memberDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteMember() {
        memberService.deleteMember();
        return ResponseEntity.ok().build();
    }

}
