package com.bukhmastov.teacheritmo.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpStatusException {

    public BadRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public BadRequestException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
