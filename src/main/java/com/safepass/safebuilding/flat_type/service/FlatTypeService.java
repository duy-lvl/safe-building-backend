package com.safepass.safebuilding.flat_type.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface FlatTypeService {
    /**
     * Get all flat types
     *
     * @return ResResponseEntity<ResponseObject>
     *
     */
    ResponseEntity<ResponseObject> getFlatType();
}
