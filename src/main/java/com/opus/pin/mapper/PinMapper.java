package com.opus.pin.mapper;

import com.opus.pin.domain.PinListRequestVO;
import com.opus.pin.domain.PinVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PinMapper {

  void insertPin(PinVO pin);

  List<PinVO> selectPinsByKeyword(PinListRequestVO pinListRequestVO);

  List<PinVO> selectPinsByMemberId(PinListRequestVO pinListRequestVO);

  Optional<PinVO> selectPinByPinId(int pid);

  int countPinsByKeyword(String keyword);

  void deletePin(int pinId, int memberId);
}
