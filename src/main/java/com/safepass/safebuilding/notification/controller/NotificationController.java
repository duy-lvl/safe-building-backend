package com.safepass.safebuilding.notification.controller;

import com.safepass.safebuilding.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/web/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
}
