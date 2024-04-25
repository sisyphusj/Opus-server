package com.opus.config.exception;

import com.opus.common.ResponseCode;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEntryException extends RuntimeException {

    ResponseCode responseCode;

    @Builder
    public DuplicateEntryException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    @Builder
    public DuplicateEntryException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.responseCode = responseCode;
    }
}
