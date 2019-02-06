package com.bukhmastov.teacheritmo.exception;

import org.springframework.http.HttpStatus;

public class InternalErrorException extends HttpStatusException {

    public InternalErrorException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public InternalErrorException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
