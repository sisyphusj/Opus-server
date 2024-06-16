package com.opus.feature.auth.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.opus.feature.auth.domain.RefreshTokenVO;

@Mapper
public interface RefreshTokenMapper {

	void insertRefreshToken(RefreshTokenVO refreshTokenVO);

	Optional<RefreshTokenVO> selectRefreshTokenByToken(String value);

	void updateRefreshToken(RefreshTokenVO refreshTokenVO);

	void deleteRefreshToken(int key);
}
