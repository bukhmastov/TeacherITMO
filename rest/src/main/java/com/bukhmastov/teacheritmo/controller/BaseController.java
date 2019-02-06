package com.bukhmastov.teacheritmo.controller;

import com.bukhmastov.teacheritmo.exception.BadRequestException;
import com.bukhmastov.teacheritmo.exception.HttpStatusException;
import com.bukhmastov.teacheritmo.exception.InternalErrorException;
import com.bukhmastov.teacheritmo.struct.Response;

import org.springframework.web.bind.annotation.ValueConstants;

import java.util.Objects;

abstract class BaseController {

    void throwIfError(Response response) {
        if (response.isOk()) {
            return;
        }
        if (response.getException() == null) {
            throw new InternalErrorException("Failed to proceed request");
        }
        if (response.getException() instanceof HttpStatusException) {
            throw (HttpStatusException) response.getException();
        }
        throw new InternalErrorException("Failed to proceed request", response.getException());
    }

    int string2int(String string, String field) {
        if (string == null || Objects.equals(string, ValueConstants.DEFAULT_NONE)) {
            throw new BadRequestException(field + " not specified");
        }
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new BadRequestException(field + " is not a number");
        }
    }
}
