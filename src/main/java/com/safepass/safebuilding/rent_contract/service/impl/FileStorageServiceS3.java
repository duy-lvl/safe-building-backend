package com.safepass.safebuilding.rent_contract.service.impl;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.safepass.safebuilding.rent_contract.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@Primary
@Slf4j
public class FileStorageServiceS3 implements FileStorageService {
    @Value("${amazon.s3.bucket}")
    private String bucket;

    @Value("${amazon.region}")
    private String region_str;

    @Autowired
    TransferManager transferManager;

    @Override
    public void save(MultipartFile file, SseEmitter sseEmitter, String guid, String desc) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            long totalBytes = file.getSize();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(totalBytes);
            metadata.setUserMetadata(Map.of("desc", desc));
            final PutObjectRequest request = new PutObjectRequest(
                    bucket, file.getOriginalFilename(), file.getInputStream(), metadata
            );
            request.setGeneralProgressListener(new ProgressListener() {
                long accumulatedByteTransfered = 0;
                @Override
                public void progressChanged(ProgressEvent progressEvent) {
                    accumulatedByteTransfered += progressEvent.getBytesTransferred();
                    long progress = 100 * accumulatedByteTransfered / totalBytes;
                    try {
                        sseEmitter.send(SseEmitter.event().name(guid).data(progress));
                    } catch (IOException e) {
                        log.info(e.getMessage());
                    }
                }
            });
            transferManager.upload(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
