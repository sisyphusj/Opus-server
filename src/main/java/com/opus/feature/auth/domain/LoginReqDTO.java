package com.opus.feature.auth.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginReqDTO {

	@NotBlank
	private String username;

	@NotBlank
	@Setter
	private String password;

	// UsernamePasswordAuthenticationToken으로 반환
	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(username, password);
	}
}
