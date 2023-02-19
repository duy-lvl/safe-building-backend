package com.safepass.safebuilding.rent_contract.service.impl;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.firebase.service.IImageService;
import com.safepass.safebuilding.rent_contract.jdbc.RentContractJDBC;
import com.safepass.safebuilding.rent_contract.service.RentContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class RentContractServiceImpl implements RentContractService {
    @Autowired
    private IImageService imageService;
    @Autowired
    private RentContractJDBC rentContractJDBC;

    @Override
    public ResponseEntity<ResponseObject> uploadFile(MultipartFile[] files, String customerId, String rentContractId, String flatId) throws IOException {
        String url = create(files);

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

    public String create(MultipartFile[] files) throws IOException {

        String imageUrl = "";
        for (MultipartFile file : files) {
            String fileName = imageService.save(file);
            imageUrl = imageService.getImageUrl(fileName);
        }

        return imageUrl;
    }
}
