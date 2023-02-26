package com.safepass.safebuilding.customer.validation;

import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.customer.dto.RequestObjectForCreateCustomer;
import com.safepass.safebuilding.customer.dto.RequestObjectForUpdateCustomer;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoValidation {
    public static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
    public static final String PHONE_REGEX_PATTERN = "(^$|[0-9]{10})";

    public void validateCreate(RequestObjectForCreateCustomer requestObj) throws InvalidDataException {
        if (!requestObj.getPhone().matches(PHONE_REGEX_PATTERN)) {
            throw new InvalidDataException("Phone number is invalid");
        }
        if (requestObj.getEmail() != null) {
            if (!requestObj.getEmail().matches(EMAIL_REGEX_PATTERN)) {
                throw new InvalidDataException("Email is invalid");
            }
        }
        if (requestObj.getPassword() == null || requestObj.getExpiryDay() == null || requestObj.getFullName() == null) {
            throw new InvalidDataException("Field cannot be null");
        }
        if (requestObj.getValue() <= 0) {
            throw new InvalidDataException("Value must be greater than 0");
        }
    }

    public void validateUpdate(RequestObjectForUpdateCustomer requestObj) throws InvalidDataException {
        if (!requestObj.getPhone().matches(PHONE_REGEX_PATTERN)) {
            throw new InvalidDataException("Phone number is invalid");
        }
        if (requestObj.getEmail() != null) {
            if (!requestObj.getEmail().matches(EMAIL_REGEX_PATTERN)) {
                throw new InvalidDataException("Email is invalid");
            }
        }

    }
}
