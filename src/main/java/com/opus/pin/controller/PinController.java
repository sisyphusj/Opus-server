package com.opus.pin.controller;

import com.opus.common.ResponseCode;
import com.opus.pin.domain.PinDTO;
import com.opus.pin.domain.PinVO;
import com.opus.pin.service.PinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/pins")
@RequiredArgsConstructor

public class PinController {

    private final PinService pinService;

    // pin 추가
    @PostMapping
    public ResponseEntity<ResponseCode> savePin(@RequestBody PinDTO pinDTO) {
        return pinService.savePin(pinDTO);
    }

    // pin 전체 리스트
    @GetMapping
    public ResponseEntity<List<PinVO>> getPinList(@RequestParam int offset,
                                  @RequestParam int amount,
                                  @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(pinService.getPinList(offset, amount, keyword));
    }

    // 사용자 pin 리스트
    @GetMapping("/my-pins")
    public ResponseEntity<List<PinVO>> getMyPinList(@RequestParam int offset,
                                    @RequestParam int amount) {
        return ResponseEntity.ok(pinService.getMyPinList(offset, amount));
    }

    // pinId 로 pin 조회
    @GetMapping("/{pinId}")
    public ResponseEntity<PinVO> getPinByPinId(@PathVariable int pinId) {
        return ResponseEntity.ok(pinService.getPinByPinId(pinId));
    }

    // 전체 pin 개수
    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalAmount(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(pinService.getTotalCount(keyword));
    }

    // pin 삭제
    @DeleteMapping("/{pinId}")
    public ResponseEntity<Void> deletePin(@PathVariable int pinId) {
        pinService.deletePin(pinId);
        return ResponseEntity.ok().build();
    }
}
