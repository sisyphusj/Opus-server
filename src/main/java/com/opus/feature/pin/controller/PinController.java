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
import com.opus.feature.pin.domain.PinListResDTO;
import com.opus.feature.pin.domain.PinReqDTO;
import com.opus.feature.pin.domain.PinResDTO;
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

	/**
	 * 핀 등록
	 */
	@PostMapping("/register")
	public ResponseEntity<String> addPin(@Valid @RequestBody PinReqDTO pinReqDTO) {

		pinService.addPin(pinReqDTO);
		return ApiResponse.of("OK");
	}

	/**
	 * 핀 리스트
	 */
	@GetMapping
	public ResponseEntity<List<PinListResDTO>> getPinList(@RequestParam int offset,
		@RequestParam int amount,
		@RequestParam(required = false) String keyword) {

		return ApiResponse.of(pinService.getPinList(offset, amount, keyword));
	}

	/**
	 * 내 핀 리스트
	 */
	@GetMapping("/my-pins")
	public ResponseEntity<List<PinListResDTO>> getMyPinList(@RequestParam int offset,
		@RequestParam int amount) {
		return ApiResponse.of(pinService.getMyPinList(offset, amount));
	}

	/**
	 * pinId 로 핀 조회
	 */
	@GetMapping("/{pinId}")
	public ResponseEntity<PinResDTO> getPinByPinId(@PathVariable int pinId) {
		return ApiResponse.of(pinService.getPinByPinId(pinId));
	}

	/**
	 * 핀 개수 조회
	 */
	@GetMapping("/total")
	public ResponseEntity<Integer> countPins(@RequestParam(required = false) String keyword) {
		return ApiResponse.of(pinService.countPins(keyword));
	}

	/**
	 * 핀 삭제
	 */
	@DeleteMapping("/{pinId}")
	public ResponseEntity<String> removePin(@PathVariable int pinId) {

		pinService.removePin(pinId);
		return ApiResponse.of("OK");
	}
}
