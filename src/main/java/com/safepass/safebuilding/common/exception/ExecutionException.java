package com.safepass.safebuilding.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ExecutionException extends Exception {
    public ExecutionException(String message) {
        super(message);
    }
}
