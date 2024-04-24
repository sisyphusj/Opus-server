package com.opus.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private T result;

    private int code;

    private String msg;

    @Builder
    public ApiResponse(final T result, final int code, final String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }
}
