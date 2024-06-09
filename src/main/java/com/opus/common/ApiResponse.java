package com.opus.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private int status;
    private T data;

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(200, data);
    }
}
