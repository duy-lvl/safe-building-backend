package com.safepass.safebuilding.common.exception;

import com.safepass.safebuilding.common.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler extends RuntimeException {

    @ExceptionHandler({ResourceNotFoundException.class, NoSuchDataException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotfoundException(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.toString(), "Resource not found.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse accessDeniedException(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), "Access Denied.");
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerException(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Something wrong has happened.");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse nullPointerException(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Data can not be null.");
    }

    @ExceptionHandler(ExecutionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse executeException(ExecutionException exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage());
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse signatureException(ExecutionException exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), "Invalid token.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse malformedJwtException(ExecutionException exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), "Invalid token.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse expiredJwtException(ExecutionException exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.FORBIDDEN.toString(), "Expired token.");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse unsupportedJwtException(ExecutionException exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.FORBIDDEN.toString(), "Unsupported token.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse illegalArgumentException(ExecutionException exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Illegal argument.");
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(ExecutionException exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.toString(), "Bad request.");
    }

    @ExceptionHandler({InvalidDataException.class, InvalidPageSizeException.class, MaxPageExceededException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse invalidData(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.NOT_ACCEPTABLE.toString(), exception.getMessage());
    }


    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exception(Exception exception) {
        exception.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
    }

}
