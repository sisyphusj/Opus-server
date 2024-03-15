package com.opus.pin.service;

import com.opus.pin.controller.PinController;
import com.opus.pin.domain.Pin;
import com.opus.pin.mapper.PinMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PinService {

    private final PinMapper pinMapper;

    @Transactional
    public void savePin(Pin pin) {
        pinMapper.savePin(pin);
    }

    @Transactional(readOnly = true)
    public Pin findById(int pid) {
        return pinMapper.findById(pid);
    }

}
