package com.safepass.safebuilding.common.firebase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleDeviceNotification {
    private String title;
    private String body;
    private Map<String, String> data;
    private List<UUID> customerIdList;
}
