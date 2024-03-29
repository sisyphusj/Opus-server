package com.opus.member.controller;

import com.opus.member.domain.Member;
import com.opus.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
