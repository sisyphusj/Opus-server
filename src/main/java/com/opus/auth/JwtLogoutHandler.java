package com.opus.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Component
public class JwtLogoutHandler implements LogoutHandler {

  @Override
  public void logout(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
      Authentication authentication) {

    SecurityContextHolder.clearContext();

    request.getSession().invalidate();

    response.setStatus(HttpServletResponse.SC_OK);

    try {
      response.getWriter().write("로그아웃 성공");
    } catch (IOException e) {
      log.error("error : ", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
