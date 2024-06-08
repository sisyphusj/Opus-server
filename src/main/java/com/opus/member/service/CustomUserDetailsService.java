package com.opus.member.service;

import com.opus.member.domain.Member;
import com.opus.member.mapper.MemberMapper;
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

    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("call loadUserByUsername, username={}", userId);
        return memberMapper.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Member member) {
        log.info("call createUserDetails, member={}", member);

        // 사용자 권한 설정 필요. 현재는 USER 권한만 부여
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");

        return new User(
                String.valueOf(member.getMemberId()), // memberId (primary key)
                member.getPw(),
                Collections.singleton(grantedAuthority)
        );
    }

}
