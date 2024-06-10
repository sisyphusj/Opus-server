package com.opus.pin.mapper;

import com.opus.pin.domain.PinListRequestVO;
import com.opus.pin.domain.PinVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PinMapper {

    void insertPin(PinVO pin);

    List<PinVO> selectPins(PinListRequestVO pinListRequestVO);

    List<PinVO> selectPinsByKeyword(PinListRequestVO pinListRequestVO);

    List<PinVO> selectPinsByMemberId(PinListRequestVO pinListRequestVO);

    PinVO selectPinByPinId(int pid);

    int countAllPins();

    int countPinsByKeyword(String keyword);

    void deletePin(int pinId, int memberId);
}
