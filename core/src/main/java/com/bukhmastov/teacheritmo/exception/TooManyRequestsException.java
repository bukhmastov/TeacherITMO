package com.bukhmastov.teacheritmo.exception;

import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends HttpStatusException {

    public TooManyRequestsException(String reason) {
        super(HttpStatus.TOO_MANY_REQUESTS, reason);
    }

    public TooManyRequestsException(String reason, Throwable cause) {
        super(HttpStatus.TOO_MANY_REQUESTS, reason, cause);
    }
}
