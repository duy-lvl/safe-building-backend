package com.safepass.safebuilding.common.firebase.controller;

import com.safepass.safebuilding.common.firebase.entity.MultipleDeviceNotification;
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
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    @PostMapping("/notification")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage) {
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String sendNotificationByTokenForListCustomer(
            @RequestBody MultipleDeviceNotification multipleDeviceNotification){
        return firebaseMessagingService.sendNotificationByToken(multipleDeviceNotification);
    }
}
