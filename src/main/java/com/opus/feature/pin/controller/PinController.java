package com.opus.feature.pin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.feature.pin.domain.PinInsertDTO;
import com.opus.feature.pin.domain.PinListResponseDTO;
import com.opus.feature.pin.domain.PinResponseDTO;
import com.opus.feature.pin.service.PinService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/pins")
@RequiredArgsConstructor

public class PinController {

	private final PinService pinService;

	// pin 추가
	@PostMapping("/register")
	public ResponseEntity<String> addPin(@Valid @RequestBody PinInsertDTO pinInsertDTO) {
		pinService.addPin(pinInsertDTO);
		return ApiResponse.of("OK");
	}

	// pin 전체 리스트
	@GetMapping
	public ResponseEntity<List<PinListResponseDTO>> getPinList(@RequestParam int offset,
		@RequestParam int amount,
		@RequestParam(required = false) String keyword) {
		return ApiResponse.of(pinService.getPinList(offset, amount, keyword));
	}

	// 사용자 pin 리스트
	@GetMapping("/my-pins")
	public ResponseEntity<List<PinListResponseDTO>> getMyPinList(@RequestParam int offset,
		@RequestParam int amount) {
		return ApiResponse.of(pinService.getMyPinList(offset, amount));
	}

	// pinId 로 pin 조회
	@GetMapping("/{pinId}")
	public ResponseEntity<PinResponseDTO> getPinByPinId(@PathVariable int pinId) {
		return ApiResponse.of(pinService.getPinByPinId(pinId));
	}

	// 전체 pin 개수
	@GetMapping("/total")
	public ResponseEntity<Integer> countPins(@RequestParam(required = false) String keyword) {
		return ApiResponse.of(pinService.countPins(keyword));
	}

	// pin 삭제
	@DeleteMapping("/{pinId}")
	public ResponseEntity<String> removePin(@PathVariable int pinId) {
		pinService.removePin(pinId);
		return ApiResponse.of("OK");
	}
}