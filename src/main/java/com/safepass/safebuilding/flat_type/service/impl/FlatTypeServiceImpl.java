package com.safepass.safebuilding.flat_type.service.impl;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.flat_type.entity.FlatType;
import com.safepass.safebuilding.flat_type.repository.FlatTypeRepository;
import com.safepass.safebuilding.flat_type.service.FlatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatTypeServiceImpl implements FlatTypeService {
    @Autowired
    private FlatTypeRepository flatTypeRepository;

    /**
     * {@inheritDoc}
     *
     * */
    @Override
    public ResponseEntity<ResponseObject> getFlatType() {
        List<FlatType> flatTypes = flatTypeRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, flatTypes));

    }
}
