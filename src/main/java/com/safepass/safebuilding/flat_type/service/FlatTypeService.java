package com.safepass.safebuilding.flat_type.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.flat_type.dto.FlatTypeCreateDTO;
import com.safepass.safebuilding.flat_type.entity.FlatType;
import org.springframework.http.ResponseEntity;

public interface FlatTypeService {
    /**
     * Get all flat types
     *
     * @return ResResponseEntity<ResponseObject>
     *
     */
    ResponseEntity<ResponseObject> getFlatType();

    /**
     * Create a flat type
     * @param flatType {@code FlatType}
     * @return ResResponseEntity<ResponseObject>
     *
     */
    ResponseEntity<ResponseObject> createFlatType(FlatTypeCreateDTO flatType);
}
