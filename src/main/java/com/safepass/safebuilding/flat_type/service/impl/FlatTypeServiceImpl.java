package com.safepass.safebuilding.flat_type.service.impl;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.flat_type.dto.FlatTypeCreateDTO;
import com.safepass.safebuilding.flat_type.entity.FlatType;
import com.safepass.safebuilding.flat_type.repository.FlatTypeRepository;
import com.safepass.safebuilding.flat_type.service.FlatTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlatTypeServiceImpl implements FlatTypeService {
    @Autowired
    private FlatTypeRepository flatTypeRepository;

    private ModelMapperCustom modelMapper = new ModelMapperCustom();

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

    @Override
    public ResponseEntity<ResponseObject> createFlatType(FlatTypeCreateDTO flatTypeDTO) {
        FlatType flatType = modelMapper.map(flatTypeDTO, FlatType.class);
        flatType.setId(UUID.randomUUID());
        flatTypeRepository.save(flatType);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }
}
