package com.safepass.safebuilding.customer.service.impl;

import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.customer.dto.CustomerDTO;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.jdbc.CustomerJDBC;
import com.safepass.safebuilding.customer.repository.CustomerRepository;
import com.safepass.safebuilding.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PaginationValidation paginationValidation;
    @Autowired
    private CustomerJDBC customerJDBC;
    public ResponseEntity<ResponseObject> getAllCustomer(int page, int size) {
        try {
            paginationValidation.validatePageSize(page, size);
            System.out.println("get all");
            Pageable pageRequest = PageRequest.of(page - 1, size);

            String queryTotalRow = CustomerServiceUtil.constructQueryForGetTotalRowGetAllCustomer();
            Long totalRow = customerJDBC.getTotalRow(queryTotalRow);

            int totalPage = (int)Math.ceil(1.0*totalRow/size);
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            String queryGetAll = CustomerServiceUtil.constructQueryForGetAllCustomer(page - 1, size);
            List<CustomerDTO> customerDTOs = customerJDBC.getAllCustomer(queryGetAll);

            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, customerDTOs));
            return responseEntity;
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
            return responseEntity;
        }
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }
}
