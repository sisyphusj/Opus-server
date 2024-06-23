package com.opus.common;

/**
 * PermittedUrls - 허용 URL 클래스
 */

public class PermittedUrls {

	private PermittedUrls() {
	}

	public static final String[] PERMITTED_URLS = {
		"/api/auth/login", "/api/auth/logout",
		"/api/auth/reissue-token", "/api/member/register",
		"/api/member/check/**", "/api/pins/total",
		"/api/pins",
		"/api/pins/comments/list", "/error"};

}
