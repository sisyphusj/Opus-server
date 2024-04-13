package com.opus.member.controller;

import com.opus.member.domain.Member;
import com.opus.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public void addMember(@RequestBody Member member) {
        log.info("addMember = {}", member.getM_id());
        memberService.saveMember(member);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.info("login = {}", bindingResult.getAllErrors());
        }

        Member loginMember = memberService.login(member);
        if(loginMember == null) {
            bindingResult.rejectValue("m_id", "login", "로그인 실패");
            log.info("login = {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body("로그인 실패");
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember.getM_id());

        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
            log.info("logout logout");
        }
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/profile")
    public ResponseEntity<Member> findById(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Retrieve the member ID from the session
        Integer memberId = (Integer) session.getAttribute("loginMember");

        if (memberId == null) {
            // No member ID in session, return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Find the member by ID
        Member member = memberService.findById(memberId);

        if (member == null) {
            // No member found with the given ID
            return ResponseEntity.notFound().build();
        }

        log.info("findById = {}", member.getM_id());
        return ResponseEntity.ok(member);
    }

    @PutMapping
    public void updateMember(@RequestBody Member member , HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // Retrieve the member ID from the session
        Integer memberId = (Integer) session.getAttribute("loginMember");

        if (memberId == null) {
            // No member ID in session, return unauthorized response
            log.error("updateMember = {}", member);
            throw new IllegalStateException("서버 오류");
        }

        member.setM_id(memberId);
        memberService.updateMember(member);
        log.info("updateMember = {}", member);
    }

    @DeleteMapping("/{mId}")
    public void deleteMember(@PathVariable int mId) {
        memberService.deleteMember(mId);
    }

}
