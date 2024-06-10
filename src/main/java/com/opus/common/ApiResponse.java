package com.opus.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private int status;
    private T data;

    public static <T> ResponseEntity<T> of(T data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
