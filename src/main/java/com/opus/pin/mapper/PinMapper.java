package com.opus.pin.mapper;

import com.opus.pin.domain.Pin;
import com.opus.pin.domain.PinListRequest;
import com.opus.pin.domain.PinVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PinMapper {

    void savePin(Pin pin);

    List<PinVO> getPinList(PinListRequest pinListRequest);

    List<PinVO> getPinListByKeyword(PinListRequest pinListRequest);

    List<PinVO> getMyPinList(PinListRequest pinListRequest);

    PinVO getPinByPinId(int pid);

    int getTotalCount();

    int getTotalCountByKeyword(String keyword);

    void deletePin(int pid, int mid);
}
