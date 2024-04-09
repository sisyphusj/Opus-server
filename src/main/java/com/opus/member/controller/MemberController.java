package com.opus.member.controller;

import com.opus.member.domain.Member;
import com.opus.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:3000")

public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public void addMember(@RequestBody Member member) {
        log.info("addMember = {}", member.getM_id());
        memberService.saveMember(member);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member, HttpServletRequest request, BindingResult bindingResult) {

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
        session.setAttribute("loginMember", loginMember);

        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/{mId}")
    public Member findById(@PathVariable int mId) {
        Member member = memberService.findById(mId);
        log.info("findById = {}", member.getM_id());
        return member;
    }

    @PutMapping
    public void updateMember(@RequestBody Member member) {
        memberService.updateMember(member);
        log.info("updateMember = {}", member);
    }

    @DeleteMapping("/{mId}")
    public void deleteMember(@PathVariable int mId) {
        memberService.deleteMember(mId);
    }

}
