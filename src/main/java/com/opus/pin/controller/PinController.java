package com.opus.pin.controller;

import com.opus.auth.SecurityUtil;
import com.opus.common.ResponseCode;
import com.opus.pin.domain.PinDTO;
import com.opus.pin.domain.PinListRequestDTO;
import com.opus.pin.domain.PinVO;
import com.opus.pin.service.PinService;
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

public class PinController {

    private final PinService pinService;

    // pin 추가
    @PostMapping
    public ResponseEntity<ResponseCode> savePin(@RequestBody PinDTO pinDTO) {

        log.info("addPin = {} {} {} {}", pinDTO.getTag(), pinDTO.getImagePath(), pinDTO.getNTag(), pinDTO.getSeed());
        return pinService.savePin(pinDTO, SecurityUtil.getCurrentUserId());
    }

    // pin 전체 리스트
    @PostMapping("/list")
    public List<PinVO> getPinList(@RequestBody PinListRequestDTO pinListRequestDTO) {

        log.info( "getPinList = {} {} {} ", pinListRequestDTO.getOffset(), pinListRequestDTO.getAmount(), pinListRequestDTO.getKeyword());
        return pinService.pinList(pinListRequestDTO);
    }

    // 사용자 pin 리스트
    @PostMapping("/myPins")
    public List<PinVO> getPinListById(@RequestBody PinListRequestDTO pinListRequestDTO) {

        log.info( "getPinListById = {} {} {} ", pinListRequestDTO.getOffset(), pinListRequestDTO.getAmount(), pinListRequestDTO.getKeyword());
        return pinService.pinListById(pinListRequestDTO, SecurityUtil.getCurrentUserId());
    }

    // 전체 pin 개수
    @GetMapping("/total")
    public int getTotalAmount(@RequestParam(required = false) String keyword) {

        if(keyword != null) {
            log.info("getTotalAmount Keyword = {}", keyword);
        }

        return pinService.getTotalCount(keyword);
    }

    // pid 로 pin 리스트 조회
    @GetMapping("/{pid}")
    public PinVO getPinByPId(@PathVariable int pid) {
        log.info("getPin = {}", pid);
        return pinService.getPinByPId(pid);
    }

    // pin 삭제
    @DeleteMapping("/{pid}")
    public void deletePin(@PathVariable int pid) {

        log.info("deletePin = {}", pid);
        pinService.deletePin(pid, SecurityUtil.getCurrentUserId());
    }
}
