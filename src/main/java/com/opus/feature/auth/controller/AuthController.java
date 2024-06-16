package com.opus.feature.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.feature.auth.domain.LoginDTO;
import com.opus.feature.auth.domain.TokenDTO;
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

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
		return ApiResponse.of(authService.login(loginDTO));
	}

	// 엑세스 토큰 재발급
	@PostMapping("/reissue-token")
	public ResponseEntity<TokenDTO> reissueToken(@Valid @RequestBody TokenDTO requestTokenDTO) {
		return ApiResponse.of(authService.reissueToken(requestTokenDTO));
	}

	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		authService.logout();
		return ApiResponse.of("OK");
	}

}
