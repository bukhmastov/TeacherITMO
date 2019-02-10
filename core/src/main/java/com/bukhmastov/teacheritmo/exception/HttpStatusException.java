package com.bukhmastov.teacheritmo.exception;

import org.springframework.http.HttpStatus;

public abstract class HttpStatusException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String reason;

    HttpStatusException(HttpStatus httpStatus, String reason) {
        super(reason);
        this.httpStatus = httpStatus;
        this.reason = reason;
    }

    HttpStatusException(HttpStatus httpStatus, String reason, Throwable cause) {
        super(reason, cause);
        this.httpStatus = httpStatus;
        this.reason = reason;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "message='" + getLocalizedMessage() + '\'' +
                ", httpStatus='" + httpStatus + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
