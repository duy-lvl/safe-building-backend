package com.safepass.safebuilding.notification.service.impl;

import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.notification.entity.Notification;
import com.safepass.safebuilding.notification.repository.NotificationRepository;
import com.safepass.safebuilding.notification.service.NotificationService;
import com.safepass.safebuilding.service.dto.MobileServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private PaginationValidation paginationValidation;
    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Override
    public ResponseEntity<ResponseObject> getNotiList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {
        paginationValidation.validatePageSize(page, size);
        Pageable pageRequest = PageRequest.of(page - 1, size);
        Page<Notification> servicePage = notificationRepository.findAll(pageRequest);
        int totalPage = servicePage.getTotalPages();
        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);

        List<Notification> notifications = servicePage.getContent();

        ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, notifications));
        return responseEntity;
    }
}
