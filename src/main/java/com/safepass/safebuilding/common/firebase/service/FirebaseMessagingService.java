package com.safepass.safebuilding.common.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.safepass.safebuilding.common.firebase.entity.MultipleDeviceNotification;
import com.safepass.safebuilding.common.firebase.entity.NotificationMessage;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     * @param multipleDeviceNotification
     * @return String
     */
    public String sendNotificationByToken(MultipleDeviceNotification multipleDeviceNotification) {
        Notification notification = Notification.builder()
                .setTitle(multipleDeviceNotification.getTitle())
                .setBody(multipleDeviceNotification.getBody())
                .build();
        Message message ;
        List<UUID> customerIds = multipleDeviceNotification.getCustomerIdList();
        List<Device> devices = new ArrayList<>();
        try {
            if(customerIds.size() > 0){
                for (int i = 0; i < customerIds.size(); i++) {
                    devices.addAll(deviceRepository.findDeviceByCustomerId(customerIds.get(i)));
                }
            }
            if(devices.size() > 0){
                for (int i = 0; i < devices.size(); i++) {
                        message = Message.builder()
                                .setToken(devices.get(i).getToken())
                                .setNotification(notification)
                                .putAllData(multipleDeviceNotification.getData())
                                .build();
                        firebaseMessaging.send(message);
                }
            }
            return "Send notification successfully";
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
