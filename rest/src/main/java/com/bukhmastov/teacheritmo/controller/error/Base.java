package com.bukhmastov.teacheritmo.controller.error;

import com.bukhmastov.teacheritmo.model.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

abstract class Base {

    ResponseEntity<ErrorResponse> makeErrorResponse(HttpStatus httpStatus, String reason) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(httpStatus.value() + " " + httpStatus.getReasonPhrase());
        errorResponse.setReason(reason);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
