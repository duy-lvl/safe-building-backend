package com.safepass.safebuilding.transaction.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface TransactionService {
    ResponseEntity<ResponseObject> getTransaction(UUID billId, int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;
}
