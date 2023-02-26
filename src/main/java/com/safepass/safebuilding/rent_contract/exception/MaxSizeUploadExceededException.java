package com.safepass.safebuilding.rent_contract.exception;

import com.safepass.safebuilding.common.dto.ErrorResponse;
import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Date;

@RestControllerAdvice
public class MaxSizeUploadExceededException {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return new ErrorResponse(new Date(), HttpStatus.NOT_ACCEPTABLE.toString(), "File upload cannot exceed " + maxSize);
    }
}
