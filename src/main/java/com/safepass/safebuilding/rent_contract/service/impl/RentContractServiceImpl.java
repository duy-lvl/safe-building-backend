package com.safepass.safebuilding.rent_contract.service.impl;

import com.google.gson.Gson;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.firebase.entity.NotificationMessage;
import com.safepass.safebuilding.common.firebase.service.FirebaseMessagingService;
import com.safepass.safebuilding.common.firebase.service.IImageService;
import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.common.service.MailSenderService;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.customer.dto.CustomerInfo;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.repository.CustomerRepository;
import com.safepass.safebuilding.customer.service.CustomerService;
import com.safepass.safebuilding.flat.service.FlatService;
import com.safepass.safebuilding.rent_contract.dto.RentContractDTO;
import com.safepass.safebuilding.rent_contract.dto.RequestObjectForCreate;
import com.safepass.safebuilding.rent_contract.dto.RequestObjectForUpdate;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.rent_contract.jdbc.RentContractJDBC;
import com.safepass.safebuilding.rent_contract.repository.RentContractRepository;
import com.safepass.safebuilding.rent_contract.service.RentContractService;
import com.safepass.safebuilding.rent_contract.validation.RentContractValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentContractServiceImpl implements RentContractService {
    @Autowired
    private IImageService imageService;
    @Autowired
    private RentContractJDBC rentContractJDBC;
    @Autowired
    private PaginationValidation paginationValidation;
    @Autowired
    private FlatService flatService;
    @Autowired
    private RentContractValidation rentContractValidation;
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @Autowired
    private RentContractRepository rentContractRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MailSenderService mailSenderService;
    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ResponseEntity<ResponseObject> uploadFile(MultipartFile[] files, String customerId, String rentContractId, String flatId) throws IOException {
        String url = imageService.create(files);

        String query = RentContractServiceUtil.constructQuery(rentContractId, customerId, flatId, url);
        boolean result = rentContractJDBC.uploadRentContract(query);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Failed", null, null));
        }

    }




    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ResponseEntity<ResponseObject> getList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {
        paginationValidation.validatePageSize(page, size);

        String queryTotalRow = RentContractServiceUtil.contructQueryGetAllTotalRow();
        long totalRow = rentContractJDBC.getTotalRow(queryTotalRow);
        int totalPage = (int) Math.ceil(1.0 * totalRow / size);
        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);
        String queryGetList = RentContractServiceUtil.contructQueryGetAll(page - 1, size);
        List<RentContractDTO> rentContracts = rentContractJDBC.getList(queryGetList);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, rentContracts));

    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class, IOException.class})
    public ResponseEntity<ResponseObject> createContract(MultipartFile[] files, String requestObject, String[] deviceTokens)
            throws IOException, SQLException, InvalidDataException {
        Gson gson = new Gson();
        RequestObjectForCreate rentContractRequest = gson.fromJson(requestObject, RequestObjectForCreate.class);

        rentContractValidation.creationValidation(rentContractRequest);

        String url = imageService.create(files);
        String query = RentContractServiceUtil.queryInsert(rentContractRequest, url);
        boolean checkInsert = rentContractJDBC.insertContract(query);
        if (!checkInsert) {
            throw new SQLException("Insert contract failed");
        }


        flatService.updateFlatStatus(UUID.fromString(rentContractRequest.getFlatId()), FlatStatus.UNAVAILABLE);
        Customer customer = customerRepository.findById(UUID.fromString(rentContractRequest.getCustomerId())).get();
        String title = "Safe Building Notification";
        String body = "Dear " + customer.getFullname() + ",\n" +
                "Our system notices that you have bill(s) that is unpaid so please check your information and pay the bill as soon as possible." +
                "\nThank you for spending time reading this letter.\nHave a good day.\nRegards,\nSafe Building Admin Team";
        for (String deviceToken: deviceTokens) {
            NotificationMessage notificationMessage = new NotificationMessage(deviceToken, title, body, new HashMap<String, String>());
            firebaseMessagingService.sendNotificationByToken(notificationMessage);

        }
        if (customer.getEmail() != null) {
            mailSenderService.sendMail(customer.getEmail(), title, body);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteContract(String contractId) throws NoSuchDataException {
        Optional<RentContract> rctemp = rentContractRepository.findRentContractById(UUID.fromString(contractId));
        if (rctemp.isPresent()) {
            RentContract rentContract = rctemp.get();
            rentContract.setStatus(RentContractStatus.DELETED);
            rentContractRepository.save(rentContract);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Contract is deleted successfully", null, null));
        }
        throw new NoSuchDataException("Contract not found");
    }
    @Override
    public ResponseEntity<ResponseObject> updateContract(MultipartFile[] files, String requestObject)
            throws IOException, SQLException, InvalidDataException {
        Gson gson = new Gson();
        RequestObjectForUpdate rentContractRequest = gson.fromJson(requestObject, RequestObjectForUpdate.class);

        rentContractValidation.editValidation(rentContractRequest);
        String url = rentContractRequest.getOldLink();
        if (rentContractRequest.isChange()) {
            //edit flat status
            imageService.delete(url);
            url = imageService.create(files);
        }

        String query = RentContractServiceUtil.queryUpdate(rentContractRequest, url);
        boolean checkInsert = rentContractJDBC.insertContract(query);
        if (!checkInsert) {
            throw new SQLException("Update contract failed");
        }

        flatService.updateFlatStatus(UUID.fromString(rentContractRequest.getOldFlatId()), FlatStatus.AVAILABLE);
        flatService.updateFlatStatus(UUID.fromString(rentContractRequest.getFlatId()), FlatStatus.UNAVAILABLE);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }

    @Override
    public ResponseEntity<ResponseObject> getContractById(String id) {
        String query = RentContractServiceUtil.contructQueryGetById(id);
        RentContractDTO rentContractDTO = rentContractJDBC.getList(query).get(0);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, rentContractDTO));
    }


}
