package com.safepass.safebuilding.rent_contract.controller;

import com.safepass.safebuilding.rent_contract.service.FileStorageService;
import com.safepass.safebuilding.rent_contract.service.RentContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/api/v1/rent-contract")
public class RentContractController {
    @Autowired
    private RentContractService rentContractService;

    @Autowired
    FileStorageService fileStorageService;

    private Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    @GetMapping(value = "/progress")
    public SseEmitter eventEmitter() throws IOException {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        UUID guid = UUID.randomUUID();
        sseEmitters.put(guid.toString(), sseEmitter);
        sseEmitter.send(SseEmitter.event().name("GUID").data(guid));
        sseEmitter.onCompletion(() -> sseEmitters.remove(guid.toString()));
        sseEmitter.onTimeout(() -> sseEmitters.remove(guid.toString()));
        return sseEmitter;
    }

    @PostMapping(value = "/upload-contract")
    public ResponseEntity<String> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("guid") String guid) throws IOException
    {
        String message = "";
        try {
            fileStorageService.save(file, sseEmitters.get(guid), guid, description);
            sseEmitters.remove(guid);
            message = "Upload file successful: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename();
            sseEmitters.remove(guid);
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
}
