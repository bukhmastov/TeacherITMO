package com.bukhmastov.teacheritmo.controller.error;

import com.bukhmastov.teacheritmo.model.ResponseError;

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
}
