package com.safepass.safebuilding.rent_contract.validation;

import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.rent_contract.dto.RequestObjectForCreate;
import com.safepass.safebuilding.rent_contract.dto.RequestObjectForUpdate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
@Service
public class RentContractValidation {
    
    public void creationValidation(RequestObjectForCreate requestObject) throws InvalidDataException {
        if (requestObject.getFlatId() == null || requestObject.getCustomerId() == null 
        || requestObject.getExpiryDate() == null || requestObject.getValue() <= 0) {
            throw new InvalidDataException("Field cannot be null");
        }
        if (Date.valueOf(requestObject.getExpiryDate()).before(Date.valueOf(LocalDate.now()))) {
            throw new InvalidDataException("Contract expired");
        }
    }

    public void editValidation(RequestObjectForUpdate requestObject) throws InvalidDataException {
        if (requestObject.getFlatId() == null || requestObject.getCustomerId() == null
                || requestObject.getExpiryDate() == null || requestObject.getValue() <= 0) {
            throw new InvalidDataException("Field cannot be null");
        }
        if (Date.valueOf(requestObject.getExpiryDate()).before(Date.valueOf(LocalDate.now()))) {
            throw new InvalidDataException("Contract expired");
        }
    }
}
