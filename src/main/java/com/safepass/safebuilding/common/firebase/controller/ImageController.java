package com.safepass.safebuilding.common.firebase.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.firebase.service.IImageService;
import com.safepass.safebuilding.rent_contract.exception.MaxSizeUploadExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file-upload")
public class ImageController {
    @Autowired
    IImageService imageService;

    private String imageUrl;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestParam(name = "file") MultipartFile[] files) {

        for (MultipartFile file : files) {

            try {

                String fileName = imageService.save(file);

                imageUrl = imageService.getImageUrl(fileName);
                //System.out.println("URL: " + imageUrl);

            } catch (IOException e) {

                ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
                return responseEntity;
            }
        }

        ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, imageUrl));
        return responseEntity;
    }

}
