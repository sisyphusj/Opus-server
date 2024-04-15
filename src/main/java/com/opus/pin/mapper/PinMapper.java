package com.opus.pin.mapper;

import com.opus.pin.domain.Pin;
import com.opus.pin.domain.PinListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PinMapper {

    void savePin(Pin pin);

    List<Pin> findById(PinListDTO pinListDTO);

    List<Pin> pinList(PinListDTO pinListDTO);

    int getTotalCount();

    void updatePin(Pin pin);

    void deletePin(int pid);
}
