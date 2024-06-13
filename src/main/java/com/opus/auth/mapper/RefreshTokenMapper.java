package com.opus.auth.mapper;

import com.opus.auth.domain.RefreshTokenVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {

  void insertRefreshToken(RefreshTokenVO refreshTokenVO);

  Optional<RefreshTokenVO> selectRefreshToken(int key);

  void updateRefreshToken(RefreshTokenVO refreshTokenVO);

  void deleteRefreshToken(int key);
}
