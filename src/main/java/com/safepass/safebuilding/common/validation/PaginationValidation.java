package com.safepass.safebuilding.common.validation;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import org.springframework.stereotype.Service;

@Service
public class PaginationValidation {

    public static final int MINIMUM_VALUE_OF_PAGE_NUMBER = 1;
    public static final int MINIMUM_VALUE_OF_SIZE = 1;

    public void validatePageSize(int page, int size) throws InvalidPageSizeException {
        if (page < MINIMUM_VALUE_OF_PAGE_NUMBER) {
            throw new InvalidPageSizeException("Page number must be a positive integer");
        }
        if (size < MINIMUM_VALUE_OF_SIZE) {
            throw new InvalidPageSizeException("Page size must be a positive integer");
        }
    }
}
