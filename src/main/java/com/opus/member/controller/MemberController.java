package com.opus.member.controller;

import com.opus.common.ErrorResponse;
import com.opus.common.ResponseCode;
import com.opus.config.exception.BusinessExceptionHandler;
import com.opus.member.domain.LoginDTO;
import com.opus.member.domain.Member;
import com.opus.member.domain.MemberDTO;
import com.opus.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;

    // 예외처리해야함
    @PostMapping("/signup")
    public void addMember(@Valid @RequestBody MemberDTO memberDTO) {
        log.info("addMember = {}", memberDTO);
        memberService.saveMember(memberDTO);
    }

    @GetMapping("/signup/check")
    public ResponseEntity<ResponseCode> checkDuplicate(@RequestParam(required = false) String id,
                                                       @RequestParam(required = false) String nickname,
                                                       @RequestParam(required = false) String email) {

        if (id != null && nickname == null && email == null && !id.trim().isEmpty()) {
            return memberService.checkDuplicate(0, id);
        } else if (nickname != null && id == null && email == null && !nickname.trim().isEmpty()) {
            return memberService.checkDuplicate(1, nickname);
        } else if (email != null && id == null && nickname == null && !email.trim().isEmpty()) {
            return memberService.checkDuplicate(2, email);
        } else {
            throw new BusinessExceptionHandler(ResponseCode.INVALID_INPUT_VALUE);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ErrorResponse> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {

        Integer memberId = memberService.login(loginDTO);
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", memberId);


        ErrorResponse response = ErrorResponse.builder()
                .responseCode(ResponseCode.USER_LOGIN_SUCCESS)
                .build();

        return ResponseEntity.ok().body(response);
    }

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


    @GetMapping("/profile")
    public ResponseEntity<Member> findById(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute("loginMember");

        Member member = memberService.findById(memberId);
        log.info("findById = {}", member.getM_id());
        return ResponseEntity.ok(member);
    }

    @PutMapping
    public ResponseEntity<ResponseCode> updateMember(@Valid @RequestBody MemberDTO memberDTO, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute("loginMember");

        memberService.updateMember(memberDTO, memberId);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    @DeleteMapping
    public ResponseEntity<ResponseCode> deleteMember(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute("loginMember");

        memberService.deleteMember(memberId);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

}
