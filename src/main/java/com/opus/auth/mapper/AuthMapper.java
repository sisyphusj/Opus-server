package com.opus.auth.mapper;

import com.opus.auth.domain.AuthVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AuthMapper {

  Optional<AuthVO> selectAuthByUsername(String username);

}