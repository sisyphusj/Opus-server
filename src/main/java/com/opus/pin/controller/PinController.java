package com.opus.pin.controller;

import com.opus.auth.SecurityUtil;
import com.opus.common.ResponseCode;
import com.opus.pin.domain.Pin;
import com.opus.pin.domain.PinDTO;
import com.opus.pin.domain.PinListRequestDTO;
import com.opus.pin.domain.PinVO;
import com.opus.pin.service.PinService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ResponseCode> addPin(@RequestBody PinDTO pinDTO) {

        log.info("addPin = {} {}", pinDTO.getImagePath(), pinDTO.getTag());
        return pinService.savePin(pinDTO, SecurityUtil.getCurrentUserId());
    }

    // pin 리스트 요청
    @PostMapping("/list")
    public List<PinVO> getPinList(@RequestBody PinListRequestDTO pinListRequestDTO) {
        log.info("getPinList = {}", pinListRequestDTO);
        return pinService.pinList(pinListRequestDTO);
    }

    // id로 pin 찾기
    @PostMapping("/myPins")
    public List<PinVO> getPinListById(@RequestBody PinListRequestDTO pinListRequestDTO) {

        log.info("findById = {}", pinListRequestDTO);
        return pinService.pinListById(pinListRequestDTO, SecurityUtil.getCurrentUserId());
    }

    // 전체 pin 개수
    @GetMapping("/total")
    public int getTotalAmount(@RequestParam(required = false) String keyword) {
        return pinService.getTotalCount(keyword);
    }

    // pin 수정
    @PutMapping
    public void updatePin(@Valid @RequestBody PinDTO pinDTO) {

        log.info("updatePin = {}", pinDTO);
        pinService.updatePin(pinDTO, SecurityUtil.getCurrentUserId());
    }

    // pin 삭제
    @DeleteMapping("/{pid}")
    public void deletePin(@PathVariable int pid) {

        log.info("deletePin = {}", pid);
        pinService.deletePin(pid, SecurityUtil.getCurrentUserId());
    }
}
