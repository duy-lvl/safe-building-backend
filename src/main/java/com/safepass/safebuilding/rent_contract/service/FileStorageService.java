package com.safepass.safebuilding.rent_contract.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface FileStorageService {
    public void save(MultipartFile file, SseEmitter sseEmitter, String uuid, String desc);
}
