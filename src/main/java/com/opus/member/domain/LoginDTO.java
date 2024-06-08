package com.opus.member.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class LoginDTO {

    @NotBlank
    private String userId;

    @NotBlank
    @Setter
    private String pw;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, pw);
    }
}
