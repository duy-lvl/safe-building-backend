package com.safepass.safebuilding.common.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.safepass.safebuilding.common.firebase.entity.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;
    public String sendNotificationByToken(NotificationMessage notificationMessage) {
        Notification notification = Notification.builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .build();
        Message message = Message.builder()
                .setToken(notificationMessage.getToken())
                .setNotification(notification)
                .putAllData(notificationMessage.getData())
                .build();

        try {
            firebaseMessaging.send(message);
            return "Send notification successfully";
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
