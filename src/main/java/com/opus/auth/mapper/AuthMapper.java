package com.opus.auth.mapper;

import com.opus.auth.domain.AuthVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AuthMapper {
    // 사용자 id 로 회원 정보 조회
    Optional<AuthVO> findByUserName(String userName);

}
