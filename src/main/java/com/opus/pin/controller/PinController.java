package com.opus.pin.controller;

import com.opus.pin.domain.Pin;
import com.opus.pin.service.PinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pin")
@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:3000")


public class PinController {

    private final PinService pinService;

    @PostMapping
    public ResponseEntity<String> addPin(@RequestBody Pin pin) {
        log.info("addPin = {}", pin);
        return pinService.savePin(pin);
    }

    @GetMapping("/{pid}")
    public Pin findById(@PathVariable int pid) {
        log.info("findById = {}", pid);
        Pin pin = pinService.findById(pid);
        return pin;
    }

    @PutMapping
    public void updatePin(@RequestBody Pin pin) {
        log.info("updatePin = {}", pin);
        pinService.updatePin(pin);
    }

    @DeleteMapping("/{pid}")
    public void deletePin(@PathVariable int pid) {
        log.info("deletePin = {}", pid);
        pinService.deletePin(pid);
    }
}
