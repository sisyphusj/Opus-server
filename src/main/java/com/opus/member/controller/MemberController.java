package com.opus.member.controller;

import com.opus.common.ErrorResponse;
import com.opus.common.ResponseCode;
import com.opus.common.SessionConst;
import com.opus.config.exception.BusinessExceptionHandler;
import com.opus.member.domain.LoginDTO;
import com.opus.member.domain.Member;
import com.opus.member.domain.MemberDTO;
import com.opus.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public void addMember(@Valid @RequestBody MemberDTO memberDTO) {
        log.info("addMember = {}", memberDTO);
        memberService.saveMember(memberDTO);
    }

    // 아이디 중복 체크
    @GetMapping("/signup/check-id/{id}")
    public ResponseEntity<ErrorResponse> checkDuplicateId(@PathVariable @NotEmpty String id) {
           memberService.checkDuplicateId(id);
           return ResponseEntity.ok().body(ErrorResponse.of(ResponseCode.SUCCESS));
    }

    // 닉네임 중복 체크
    @GetMapping("/signup/check-nick/{nick}")
    public ResponseEntity<ErrorResponse> checkDuplicateNick(@PathVariable @NotEmpty String nick) {
           memberService.checkDuplicateNick(nick);
           return ResponseEntity.ok().body(ErrorResponse.of(ResponseCode.SUCCESS));
    }

    // 이메일 중복 체크
    @GetMapping("/signup/check-email/{email}")
    public ResponseEntity<ErrorResponse> checkDuplicateEmail(@PathVariable @NotEmpty String email) {
           memberService.checkDuplicateEmail(email);
           return ResponseEntity.ok().body(ErrorResponse.of(ResponseCode.SUCCESS));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ErrorResponse> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {

        Integer memberId = memberService.login(loginDTO);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_SESSION, memberId);

        return ResponseEntity.ok().body(ErrorResponse.of(ResponseCode.USER_LOGIN_SUCCESS));
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<ErrorResponse> logout(HttpServletRequest request) throws AuthenticationException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("logout");
            return ResponseEntity.ok().body(ErrorResponse.of(ResponseCode.USER_LOGOUT_SUCCESS));
        } else {
            throw new AuthenticationException();
        }
    }

    // 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<Member> findById(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute(SessionConst.LOGIN_SESSION);

        Member member = memberService.findById(memberId);
        log.info("findById = {}", member.getMId());
        return ResponseEntity.ok(member);
    }

    // 프로필 수정
    @PutMapping
    public ResponseEntity<ResponseCode> updateMember(@Valid @RequestBody MemberDTO memberDTO, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute(SessionConst.LOGIN_SESSION);

        memberService.updateMember(memberDTO, memberId);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<ResponseCode> deleteMember(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute(SessionConst.LOGIN_SESSION);

        memberService.deleteMember(memberId);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

}
