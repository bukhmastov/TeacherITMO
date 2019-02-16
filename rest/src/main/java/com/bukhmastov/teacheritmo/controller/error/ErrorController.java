package com.bukhmastov.teacheritmo.controller.error;

import com.bukhmastov.teacheritmo.model.ResponseError;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class ErrorController extends Base {

    @RequestMapping(path = "/error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseError> handle(HttpServletRequest request) {
        int statusCode = (int) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        String reason = (String) request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
        Throwable throwable = (Throwable) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        logAction(httpStatus, reason, throwable);
        return makeErrorResponse(httpStatus, reason);
    }
}
