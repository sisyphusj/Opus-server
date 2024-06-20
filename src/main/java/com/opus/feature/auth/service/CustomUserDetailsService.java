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

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final AuthMapper authMapper;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return authMapper.selectAuthByUsername(username)
			.map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	private UserDetails createUserDetails(AuthVO auth) {

		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");

		return new User(
			String.valueOf(auth.getMemberId()), // memberId (primary key)
			auth.getPassword(),
			Collections.singleton(grantedAuthority)
		);
	}
}
