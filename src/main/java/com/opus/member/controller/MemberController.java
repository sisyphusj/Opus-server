package com.opus.member.controller;

import com.opus.common.ResponseCode;
import com.opus.config.exception.BusinessExceptionHandler;
import com.opus.member.domain.LoginDTO;
import com.opus.member.domain.Member;
import com.opus.member.domain.SignupDTO;
import com.opus.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public void addMember(@Valid @RequestBody SignupDTO signupDTO) {
        log.info("addMember = {}", signupDTO);
        memberService.saveMember(signupDTO);
    }

    @GetMapping("/signup/check")
    public ResponseEntity<ResponseCode> checkDuplicate(@RequestParam(required = false) String id,
                                                       @RequestParam(required = false) String nickname,
                                                       @RequestParam(required = false) String email) {

        if (id != null && nickname == null && email == null && !id.trim().isEmpty()){
            return memberService.checkDuplicate(0, id);
        } else if (nickname != null && id == null && email == null && !nickname.trim().isEmpty()) {
            return memberService.checkDuplicate(1, nickname);
        } else if (email != null && id == null && nickname == null && !email.trim().isEmpty()){
            return memberService.checkDuplicate(2, email);
        } else {
            throw new BusinessExceptionHandler(ResponseCode.INVALID_INPUT_VALUE);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("login = {}", bindingResult.getAllErrors());
        }

        Integer memberId = memberService.login(loginDTO);

        if (memberId == null) {
            bindingResult.rejectValue("m_id", "login", "로그인 실패");
            log.info("login = {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body("로그인 실패");
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", memberId);

        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
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
    public void updateMember(@RequestBody Member member, HttpServletRequest request) {
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
