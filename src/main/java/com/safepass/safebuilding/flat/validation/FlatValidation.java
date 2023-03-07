package com.safepass.safebuilding.flat.validation;

import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.building.repository.BuildingRepository;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.flat.dto.RequestFlat;
import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.flat.repository.FlatRepository;
import com.safepass.safebuilding.flat_type.entity.FlatType;
import com.safepass.safebuilding.flat_type.repository.FlatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class FlatValidation {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private FlatTypeRepository flatTypeRepository;
    @Autowired
    private FlatRepository flatRepository;
    public Flat validateCreate(RequestFlat requestFlat) throws InvalidDataException {
        if (requestFlat.getPrice() < 0) {
            throw new InvalidDataException("Price must be a positive integer");
        }
        String status = requestFlat.getStatus().toUpperCase();
        FlatStatus[] flatStatuses = FlatStatus.values();
        boolean check = false;
        for (FlatStatus fs: flatStatuses) {
            if (fs.toString().equals(status)) {
                check = true;
                break;
            }
        }
        if (!check) {
            throw new InvalidDataException("Invalid flat status");
        }
        int roomNumber = requestFlat.getRoomNumber();
        if (roomNumber < 101 || roomNumber > 9999) {
            throw new InvalidDataException("Invalid room number. Room number must be in range [101-9999]");
        }
        String flatTypeId = requestFlat.getFlatTypeId();
        if (flatTypeId == null || flatTypeId.isBlank() || flatTypeId.matches("")) {
            throw new InvalidDataException("Flat type cannot be null");
        }
        String buildingId = requestFlat.getBuildingId();
        if (buildingId == null || buildingId.isBlank() || buildingId.matches("")) {
            throw new InvalidDataException("Building cannot be null");
        }

        Optional<Building> btemp = buildingRepository.findBuildingById(UUID.fromString(requestFlat.getBuildingId()));
        if (btemp.isPresent()) {
            Building building = btemp.get();
            int currentNumberOfFlat = flatRepository.countFlatByBuildingId(building.getId());
            if (currentNumberOfFlat >= building.getCapacity()) {
                throw new InvalidDataException("Building max capacity exceeded (Max capacity: " + building.getCapacity() + ")");
            }
            Optional<Flat> ftemp = flatRepository.findFlatByRoomNumberAndBuildingId(requestFlat.getRoomNumber(), building.getId());
            if (ftemp.isPresent()) {
                throw new InvalidDataException("Room number duplicated");
            }
            Optional<FlatType> fttemp = flatTypeRepository.findFlatTypeById(UUID.fromString(requestFlat.getFlatTypeId()));
            if (fttemp.isPresent()) {
                FlatType flatType = fttemp.get();

                return Flat.builder()
                        .id(UUID.randomUUID())
                        .status(FlatStatus.valueOf(requestFlat.getStatus()))
                        .detail(requestFlat.getDetail())
                        .roomNumber(requestFlat.getRoomNumber())
                        .price(requestFlat.getPrice())
                        .building(building)
                        .flatType(flatType)
                        .build();
            }
            throw new InvalidDataException("Flat type does not exist");
        }
        throw new InvalidDataException("Building does not exist");
    }
}
