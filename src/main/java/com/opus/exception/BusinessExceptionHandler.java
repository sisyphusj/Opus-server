package com.opus.config.exception;

import com.opus.common.ResponseCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessExceptionHandler extends RuntimeException {

    private final ResponseCode responseCode;

    @Builder
    public BusinessExceptionHandler(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    @Builder
    public BusinessExceptionHandler(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.responseCode = responseCode;
    }
}
