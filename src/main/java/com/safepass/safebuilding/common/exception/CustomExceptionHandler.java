package com.safepass.safebuilding.common.exception;

import com.safepass.safebuilding.common.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.management.RuntimeErrorException;
import java.util.Date;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler extends RuntimeException {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotfoundException(Exception exception, WebRequest request) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.toString(), exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse AccessDeniedException(Exception exception, WebRequest request) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), "Access Denied!");
    }

}
