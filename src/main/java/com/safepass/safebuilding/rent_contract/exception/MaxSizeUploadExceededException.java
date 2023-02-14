package com.safepass.safebuilding.rent_contract.exception;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class MaxSizeUploadExceededException {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseObject> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "File upload cannot exceed 8MB", null, null));
        return responseEntity;
    }
}
