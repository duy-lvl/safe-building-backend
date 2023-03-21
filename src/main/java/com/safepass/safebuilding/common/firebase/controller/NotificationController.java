package com.safepass.safebuilding.common.firebase.controller;

import com.safepass.safebuilding.common.firebase.entity.NotificationMessage;
import com.safepass.safebuilding.common.firebase.service.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    @PostMapping("/send-notification")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage) {
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String sendNotificationByTokenForListCustomer(
            @RequestBody NotificationMessage message,
            @RequestBody List<UUID> customerIds
    ){
        return firebaseMessagingService.sendNotificationByToken(message, customerIds);
    }
}
