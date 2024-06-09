package com.opus.pin.mapper;

import com.opus.pin.domain.PinListRequestVO;
import com.opus.pin.domain.PinVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PinMapper {

    void savePin(PinVO pin);

    List<PinVO> getPinList(PinListRequestVO pinListRequestVO);

    List<PinVO> getPinListByKeyword(PinListRequestVO pinListRequestVO);

    List<PinVO> getMyPinList(PinListRequestVO pinListRequestVO);

    PinVO getPinByPinId(int pid);

    int getTotalCount();

    int getTotalCountByKeyword(String keyword);

    void deletePin(int pinId, int memberId);
}
