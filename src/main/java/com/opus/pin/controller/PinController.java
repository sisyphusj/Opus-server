package com.opus.pin.controller;

import com.opus.common.ApiResponse;
import com.opus.pin.domain.PinDTO;
import com.opus.pin.domain.PinListResponseDTO;
import com.opus.pin.service.PinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ApiResponse<String> addPin(@Valid @RequestBody PinDTO pinDTO) {
        pinService.addPin(pinDTO);
        return ApiResponse.of("OK");
    }

    // pin 전체 리스트
    @GetMapping
    public ApiResponse<List<PinListResponseDTO>> getPinList(@RequestParam int offset,
                                                            @RequestParam int amount,
                                                            @RequestParam(required = false) String keyword) {
        return ApiResponse.of(pinService.getPinList(offset, amount, keyword));
    }

    // 사용자 pin 리스트
    @GetMapping("/my-pins")
    public ApiResponse<List<PinListResponseDTO>> getMyPinList(@RequestParam int offset,
                                                            @RequestParam int amount) {
        return ApiResponse.of(pinService.getMyPinList(offset, amount));
    }

    // pinId 로 pin 조회
    @GetMapping("/{pinId}")
    public ApiResponse<PinListResponseDTO> getPinByPinId(@PathVariable int pinId) {
        return ApiResponse.of(pinService.getPinByPinId(pinId));
    }

    // 전체 pin 개수
    @GetMapping("/total")
    public ApiResponse<Integer> countPins(@RequestParam(required = false) String keyword) {
        return ApiResponse.of(pinService.countPins(keyword));
    }

    // pin 삭제
    @DeleteMapping("/{pinId}")
    public ApiResponse<String> removePin(@PathVariable int pinId) {
        pinService.removePin(pinId);
        return ApiResponse.of("OK");
    }
}
