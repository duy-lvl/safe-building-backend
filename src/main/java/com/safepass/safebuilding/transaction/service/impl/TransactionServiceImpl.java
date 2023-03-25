package com.safepass.safebuilding.transaction.service.impl;

import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.transaction.dto.TransactionDTO;
import com.safepass.safebuilding.transaction.entity.Transaction;
import com.safepass.safebuilding.transaction.repository.TransactionRepository;
import com.safepass.safebuilding.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Autowired
    private PaginationValidation paginationValidation;
    @Override
    public ResponseEntity<ResponseObject> getTransaction(UUID billId, int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        paginationValidation.validatePageSize(page, size);
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Transaction> transactionPage = transactionRepository.findTransactionByBillIdOrderByDateDesc(billId, pageable);
        int totalPage = transactionPage.getTotalPages();
        Pagination pagination = new Pagination(page,size,totalPage);
        paginationValidation.validateMaxPageNumber(pagination);
        List<Transaction> transactions = transactionPage.getContent();
        List<TransactionDTO> transactionDTOS = modelMapper.mapList(transactions, TransactionDTO.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, transactionDTOS));
    }
}
