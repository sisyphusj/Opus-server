package com.opus.feature.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opus.common.ApiResponse;
import com.opus.feature.member.domain.MemberInsertDTO;
import com.opus.feature.member.domain.MemberResponseDTO;
import com.opus.feature.member.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// 회원가입
	@PostMapping("/register")
	public ResponseEntity<String> registerMember(
		@Valid @RequestBody MemberInsertDTO memberInsertDTO) {
		memberService.registerMember(memberInsertDTO);
		return ApiResponse.of("OK");
	}

	// 아이디 중복 체크
	@GetMapping("/check/username/{username}")
	public ResponseEntity<Boolean> isUsernameDuplicated(@PathVariable @NotEmpty String username) {
		return ApiResponse.of(memberService.isUsernameDuplicated(username));
	}

	// 닉네임 중복 체크
	@GetMapping("/check/nickname/{nickname}")
	public ResponseEntity<Boolean> isNicknameDuplicated(@PathVariable @NotEmpty String nickname) {
		return ApiResponse.of(memberService.isNicknameDuplicated(nickname));
	}

	// 이메일 중복 체크
	@GetMapping("/check/email/{email}")
	public ResponseEntity<Boolean> isEmailDuplicated(@PathVariable @NotEmpty String email) {
		return ApiResponse.of(memberService.isEmailDuplicated(email));
	}

	// 프로필 조회
	@GetMapping("/profile")
	public ResponseEntity<MemberResponseDTO> getMyProfile() {
		return ApiResponse.of(memberService.getMyProfile());
	}

	// 프로필 수정
	@PutMapping
	public ResponseEntity<String> editMyProfile(@Valid @RequestBody MemberInsertDTO memberInsertDTO) {
		memberService.editMyProfile(memberInsertDTO);
		return ApiResponse.of("OK");
	}

	// 회원 탈퇴
	@DeleteMapping
	public ResponseEntity<String> removeMyProfile() {
		memberService.removeMyProfile();
		return ApiResponse.of("OK");
	}

}
