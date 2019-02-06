package com.bukhmastov.teacheritmo.exception;

import org.springframework.http.HttpStatus;

public class FailedDependencyException extends HttpStatusException {

    public FailedDependencyException(String reason) {
        super(HttpStatus.FAILED_DEPENDENCY, reason);
    }

    public FailedDependencyException(String reason, Throwable cause) {
        super(HttpStatus.FAILED_DEPENDENCY, reason, cause);
    }
}
