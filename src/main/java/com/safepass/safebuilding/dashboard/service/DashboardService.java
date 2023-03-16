package com.safepass.safebuilding.dashboard.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

public interface DashboardService {
    ResponseEntity<ResponseObject> rentContractStatistics(int year);
    ResponseEntity<ResponseObject> billStatistics(int year);

    ResponseEntity<ResponseObject> rentContractStatistics();
    ResponseEntity<ResponseObject> billStatistics();

    ResponseEntity<ResponseObject> serviceStatistics(int year, int month);

}
