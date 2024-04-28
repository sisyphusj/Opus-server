package com.opus.pin.mapper;

import com.opus.pin.domain.Pin;
import com.opus.pin.domain.PinListRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PinMapper {

    void savePin(Pin pin);

    List<Pin> pinList(PinListRequest pinListRequest);

    List<Pin> pinListById(PinListRequest pinListRequest);

    int getTotalCount();

    void updatePin(Pin pin);

    void deletePin(int pid, int mid);
}
