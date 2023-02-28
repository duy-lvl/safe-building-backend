package com.safepass.safebuilding.notification.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import org.springframework.http.ResponseEntity;

public interface NotificationService {
    ResponseEntity<ResponseObject> getNotiList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;
}
