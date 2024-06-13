package com.opus.common;

public class PermittedUrls {

  private PermittedUrls() {
  }

  public static final String[] PERMITTED_URLS = {
      "/api/auth/login", "/api/auth/logout",
      "/api/auth/reissue-token", "/api/member/register",
      "/api/member/check/**", "/api/pins", "/api/pins/total",
      "/api/pins/comments/list", "/error"};

}
