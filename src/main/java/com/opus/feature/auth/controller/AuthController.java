package com.opus.feature.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.feature.auth.domain.LoginReqDTO;
import com.opus.feature.auth.domain.ReissueTokenReqDTO;
import com.opus.feature.auth.domain.TokenResDTO;
import com.opus.feature.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/**
	 * 로그인
	 */
	@PostMapping("/login")
	public ResponseEntity<TokenResDTO> login(@Valid @RequestBody LoginReqDTO loginReqDTO) {
		return ApiResponse.of(authService.login(loginReqDTO));
	}

	/**
	 * 토큰 재발급
	 */
	@PostMapping("/reissue-token")
	public ResponseEntity<TokenResDTO> reissueToken(@Valid @RequestBody ReissueTokenReqDTO reissueTokenReqDTO) {
		return ApiResponse.of(authService.reissueToken(reissueTokenReqDTO));
	}

	/**
	 * 로그아웃
	 */
	@PostMapping("/logout")
	public ResponseEntity<String> logout() {

		authService.logout();
		return ApiResponse.of("OK");
	}

}
