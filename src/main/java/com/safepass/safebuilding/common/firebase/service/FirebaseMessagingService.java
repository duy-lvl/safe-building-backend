package com.safepass.safebuilding.common.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.safepass.safebuilding.common.firebase.entity.NotificationMessage;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FirebaseMessagingService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * send push notification for 1 customer at the same time.
     * @param notificationMessage
     * @return String
     */
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

    /**
     * send push notification for multiple customers at the same time.
     * @param notificationMessage
     * @param customerIds
     * @return String
     */
    public String sendNotificationByToken(NotificationMessage notificationMessage, List<UUID> customerIds) {
        Device device;
        Notification notification = Notification.builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .build();
        Message message ;

        try {
            if(customerIds.size() > 0){
                for (int i = 0; i < customerIds.size(); i++) {
                    device = deviceRepository.findById(customerIds.get(i)).get();
                    if(device != null){
                        message = Message.builder()
                                .setToken(device.getToken())
                                .setNotification(notification)
                                .putAllData(notificationMessage.getData())
                                .build();
                        firebaseMessaging.send(message);
                    }
                }
            }
            return "Send notification successfully";
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
