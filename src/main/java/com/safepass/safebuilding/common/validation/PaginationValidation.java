package com.safepass.safebuilding.common.validation;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
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

    public void validateMaxPageNumber(Pagination pagination) throws MaxPageExceededException, NoSuchDataException {
        if (pagination.getTotalPage() == 0) {
            throw new NoSuchDataException("No data found!");
        }
        if (pagination.getPage() > pagination.getTotalPage()) {
            throw new MaxPageExceededException("Page number exceeds total pages!");
        }
    }
}
