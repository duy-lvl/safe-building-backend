package com.safepass.safebuilding.common.firebase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class NotificationMessage {
    private String token;
    private String title;
    private String body;
    private Map<String, String> data;
}
