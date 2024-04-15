package com.opus.pin.controller;

import com.opus.member.domain.Member;
import com.opus.pin.domain.Pin;
import com.opus.pin.domain.PinListDTO;
import com.opus.pin.service.PinService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pin")
@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:3000")


public class PinController {

    private final PinService pinService;

    // pin 추가
    @PostMapping
    public ResponseEntity<String> addPin(@RequestBody Pin pin, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Integer memberId = (Integer) session.getAttribute("loginMember");
        pin.setM_id(memberId);
        log.info("addPin = {}", pin);
        return pinService.savePin(pin);
    }

    // pin 리스트 요청
    @PostMapping("/list")
    public List<Pin> getPinList(@RequestBody PinListDTO pinListDTO) {
        log.info("getPinList = {}", pinListDTO);
        return pinService.pinList(pinListDTO);
    }

    // id로 pin 찾기
    @PostMapping("/myPins")
    public List<Pin> findById(@RequestBody PinListDTO pinListDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        Integer memberId = (Integer) session.getAttribute("loginMember");

        if (memberId == null) {
            log.error("memberId is null");
            throw new IllegalStateException("서버 오류");
        }

        pinListDTO.setM_id(memberId);
        log.info("findById = {}", pinListDTO);

        return pinService.findById(pinListDTO);

    }

    // 전체 pin 개수
    @GetMapping("/total")
    public int getTotalAmount() {
        int a = pinService.getTotalCount();
        log.info(String.valueOf(a));
        return pinService.getTotalCount();
    }


    @PutMapping
    public void updatePin(@RequestBody Pin pin) {
        log.info("updatePin = {}", pin);
        pinService.updatePin(pin);
    }

    @DeleteMapping("/{pid}")
    public void deletePin(@PathVariable int pid) {
        log.info("deletePin = {}", pid);
        pinService.deletePin(pid);
    }
}
