package com.opus.auth.mapper;

import com.opus.auth.domain.RefreshTokenVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {

    Optional<RefreshTokenVO> findByKey(String key);

    void save(RefreshTokenVO refreshTokenVO);

    void update(RefreshTokenVO refreshTokenVO);

    void delete(String key);
}
