package com.bukhmastov.teacheritmo.controller.error;

import com.bukhmastov.teacheritmo.exception.HttpStatusException;
import com.bukhmastov.teacheritmo.model.ResponseError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AdviceController extends Base {

    @ExceptionHandler(HttpStatusException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleException(HttpStatusException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();
        String reason = exception.getReason();
        logAction(httpStatus, reason, exception);
        return makeErrorResponse(httpStatus, reason);
    }
}
