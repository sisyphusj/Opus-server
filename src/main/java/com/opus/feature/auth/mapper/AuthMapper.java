package com.opus.feature.auth.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.opus.feature.auth.domain.AuthVO;

@Mapper
public interface AuthMapper {

	Optional<AuthVO> selectAuthByUsername(String username);

}