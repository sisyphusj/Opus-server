package com.opus.pin.controller;

import com.opus.common.ResponseCode;
import com.opus.common.SessionConst;
import com.opus.pin.domain.Pin;
import com.opus.pin.domain.PinDTO;
import com.opus.pin.domain.PinListRequestDTO;
import com.opus.pin.service.PinService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<ResponseCode> addPin(@Valid @RequestBody PinDTO pinDTO, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute(SessionConst.LOGIN_SESSION);

        log.info("addPin = {}", pinDTO);
        return pinService.savePin(pinDTO, memberId);
    }

    // pin 리스트 요청
    @PostMapping("/list")
    public List<Pin> getPinList(@RequestBody PinListRequestDTO pinListRequestDTO) {

        log.info("getPinList = {}", pinListRequestDTO);
        return pinService.pinList(pinListRequestDTO);
    }

    // id로 pin 찾기
    @PostMapping("/myPins")
    public List<Pin> getPinListById(@RequestBody PinListRequestDTO pinListRequestDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute(SessionConst.LOGIN_SESSION);

        log.info("findById = {}", pinListRequestDTO);
        return pinService.pinListById(pinListRequestDTO, memberId);
    }

    // 전체 pin 개수
    @GetMapping("/total")
    public int getTotalAmount() {
        int a = pinService.getTotalCount();
        log.info(String.valueOf(a));
        return pinService.getTotalCount();
    }


    @PutMapping
    public void updatePin(@Valid @RequestBody PinDTO pinDTO, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute(SessionConst.LOGIN_SESSION);

        log.info("updatePin = {}", pinDTO);
        pinService.updatePin(pinDTO, memberId);
    }

    @DeleteMapping("/{pid}")
    public void deletePin(@PathVariable int pid, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer memberId = (Integer) session.getAttribute(SessionConst.LOGIN_SESSION);
        log.info("deletePin = {}", pid);
        pinService.deletePin(pid, memberId);
    }
}
