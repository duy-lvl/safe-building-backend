//package com.safepass.safebuilding.common.service;
//
//import com.safepass.safebuilding.common.dto.Pagination;
//import com.safepass.safebuilding.common.dto.ResponseObject;
//import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
//import com.safepass.safebuilding.common.exception.MaxPageExceededException;
//import com.safepass.safebuilding.common.utils.ModelMapperCustom;
//import com.safepass.safebuilding.common.validation.PaginationValidation;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.UUID;
//@org.springframework.stereotype.Service
//public class Service {
//
//    private PaginationValidation paginationValidation;
//    private ModelMapperCustom modelMapper;
//
//    public <R extends JpaRepository<E, UUID>, E, D> ResponseEntity<ResponseObject> getAll(int page, int size, R repository, Class<D> entityDTO) throws InvalidPageSizeException, MaxPageExceededException {
//
//        try {
//            paginationValidation.validatePageSize(page, size);
//            Pageable pageRequest = PageRequest.of(page - 1, size);
//            Page<E> entityPage = repository.findAll(pageRequest);
//            int totalPage = entityPage.getTotalPages();
//            Pagination pagination = new Pagination(page, size, totalPage);
//            paginationValidation.validateMaxPageNumber(pagination);
//
//            List<E> entities = entityPage.getContent();
//            List<D> entityDTOs = modelMapper.mapList(entities, entityDTO);
//
//            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, entityDTOs));
//            return responseEntity;
//        } catch (InvalidPageSizeException | MaxPageExceededException e) {
//            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
//            return responseEntity;
//        }
//    }
//}
