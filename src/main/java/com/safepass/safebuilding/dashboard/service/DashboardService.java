package com.safepass.safebuilding.dashboard.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

public interface DashboardService {
    ResponseEntity<ResponseObject> contractStatistic(int year);
}
