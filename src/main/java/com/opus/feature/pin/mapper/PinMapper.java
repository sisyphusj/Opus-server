package com.opus.feature.pin.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.opus.feature.pin.domain.PinListReqVO;
import com.opus.feature.pin.domain.PinListVO;
import com.opus.feature.pin.domain.PinVO;

@Mapper
public interface PinMapper {

	void insertPin(PinVO pin);

	List<PinListVO> selectPinsByKeyword(PinListReqVO pinListReqVO);

	List<PinListVO> selectPinsByMemberId(PinListReqVO pinListReqVO);

	Optional<PinVO> selectPinByPinId(int pid);

	int countPinsByKeyword(String keyword);

	void deletePin(int pinId, int memberId);
}
