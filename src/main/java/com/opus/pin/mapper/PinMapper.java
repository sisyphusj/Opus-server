package com.opus.pin.mapper;

import com.opus.pin.domain.Pin;
import com.opus.pin.domain.PinListRequest;
import com.opus.pin.domain.PinVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PinMapper {

    void savePin(Pin pin);

    List<PinVO> pinList(PinListRequest pinListRequest);

    List<PinVO> pinListByKeyword(PinListRequest pinListRequest);

    List<PinVO> pinListById(PinListRequest pinListRequest);

    int getTotalCount();

    int getTotalCountByKeyword(String keyword);

    void updatePin(Pin pin);

    void deletePin(int pid, int mid);
}
