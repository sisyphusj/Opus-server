package com.opus.pin.mapper;

import com.opus.pin.domain.Pin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PinMapper {

    void savePin(Pin pin);

    Pin findById(int pid);
}
