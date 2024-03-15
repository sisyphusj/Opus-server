package com.opus.pin.controller;

import com.opus.pin.domain.Pin;
import com.opus.pin.service.PinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pin")
@RequiredArgsConstructor
public class PinController {

    private final PinService pinService;

    @PostMapping
    public void addPin(@RequestBody Pin pin) {
        log.info("addPin = {}", pin);
        pinService.savePin(pin);
    }

    @GetMapping("/{pid}")
    public Pin findById(@PathVariable int pid) {
        log.info("findById = {}", pid);
        Pin pin = pinService.findById(pid);
        return pin;
    }
}
