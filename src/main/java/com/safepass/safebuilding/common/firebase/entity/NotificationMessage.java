package com.safepass.safebuilding.common.firebase.entity;

import lombok.Data;

import java.util.Map;

@Data
public class NotificationMessage {
    private String token;
    private String title;
    private String body;
    private Map<String, String> data;
}
