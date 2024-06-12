package com.opus.auth.controller;

import com.opus.auth.domain.LoginDTO;
import com.opus.auth.service.AuthService;
import com.opus.auth.domain.TokenDTO;
import com.opus.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/auth")
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
