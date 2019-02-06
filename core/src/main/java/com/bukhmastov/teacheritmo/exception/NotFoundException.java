package com.bukhmastov.teacheritmo.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpStatusException {

    public NotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public NotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
