package com.bukhmastov.teacheritmo.controller.error;

import com.bukhmastov.teacheritmo.model.ResponseError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

abstract class Base {

    ResponseEntity<ResponseError> makeErrorResponse(HttpStatus httpStatus, String reason) {
        ResponseError response = new ResponseError();
        response.setStatus(httpStatus.value());
        response.setDescription(httpStatus.getReasonPhrase());
        response.setReason(reason);
        return new ResponseEntity<>(response, httpStatus);
    }

    void logAction(HttpStatus httpStatus, String reason, Throwable throwable) {
        if (httpStatus != null && httpStatus.is5xxServerError()) {
            log.error("Failed to process request | status=" + httpStatus + " | reason=" + reason, throwable);
        } else {
            log.info("Failed to process request | status=" + httpStatus + " | reason=" + reason, throwable);
        }
    }

    private final Logger log = LoggerFactory.getLogger(getClass());
}
