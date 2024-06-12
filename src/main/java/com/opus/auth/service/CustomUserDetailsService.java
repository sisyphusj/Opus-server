package com.opus.auth.service;

import com.opus.auth.domain.AuthVO;
import com.opus.auth.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthMapper authMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("call loadUserByUsername, username={}", username);

        return authMapper.selectAuthByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(AuthVO auth) {
        log.info("call createUserDetails, member={}", auth);

        // 사용자 권한 설정 필요. 현재는 USER 권한만 부여
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");

        return new User(
                String.valueOf(auth.getMemberId()), // memberId (primary key)
                auth.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
