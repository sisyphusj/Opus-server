package com.opus.feature.auth.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opus.feature.auth.domain.AuthVO;
import com.opus.feature.auth.mapper.AuthMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security 에서 사용자 인증을 위한 UserDetailsService 구현체
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final AuthMapper authMapper;

	/**
	 * 사용자 정보를 데이터베이스에서 조회하여 UserDetails 객체로 반환
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 사용자 정보를 데이터베이스에서 조회
		return authMapper.selectAuthByUsername(username)
			.map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	/**
	 * 사용자 정보를 기반으로 UserDetails 객체를 생성
	 */
	private UserDetails createUserDetails(AuthVO auth) {

		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");

		return new User(
			String.valueOf(auth.getMemberId()), // memberId (primary key)
			auth.getPassword(),
			Collections.singleton(grantedAuthority)
		);
	}
}
