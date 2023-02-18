package com.safepass.safebuilding.flat.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import org.springframework.http.ResponseEntity;

public interface FlatService {
    ResponseEntity<ResponseObject> getFlatList(int page, int size);
}
