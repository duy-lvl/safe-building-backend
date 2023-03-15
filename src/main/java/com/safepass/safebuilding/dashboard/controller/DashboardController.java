package com.safepass.safebuilding.dashboard.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    @GetMapping("/contracts/{year}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> contractStatisticsByYear(@PathVariable int year) {
        return dashboardService.rentContractStatistics(year);
    }

    @GetMapping("/revenue/{year}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> revenueByYear(@PathVariable int year) {
        return dashboardService.billStatistics(year);
    }

    @GetMapping("/contracts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> contractStatisticsThisMonth() {
        return dashboardService.rentContractStatistics();
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> revenueThisMonth() {
        return dashboardService.billStatistics();
    }
}
