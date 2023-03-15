package com.safepass.safebuilding.dashboard.service.impl;


import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.dashboard.dto.RentContractStatitics;
import com.safepass.safebuilding.dashboard.jdbc.DashboardJdbc;
import com.safepass.safebuilding.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;


@org.springframework.stereotype.Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardJdbc dashboardJdbc;
    @Override
    public ResponseEntity<ResponseObject> contractStatistic(int year) {

        List<RentContractStatitics> rentContractStatitics = List.of(
                new RentContractStatitics("Jan", 0),
                new RentContractStatitics("Feb", 0),
                new RentContractStatitics("Mar", 0),
                new RentContractStatitics("Apr", 0),
                new RentContractStatitics("May", 0),
                new RentContractStatitics("Jun", 0),
                new RentContractStatitics("Jul", 0),
                new RentContractStatitics("Aug", 0),
                new RentContractStatitics("Sep", 0),
                new RentContractStatitics("Oct", 0),
                new RentContractStatitics("Nov", 0),
                new RentContractStatitics("Dec", 0)
        );

        String query = DashboardServiceUtils.getContract(year);
        List<String> rentContracts = dashboardJdbc.getContractStartDate(query);
        for (String s: rentContracts) {
            int month = getMonth(s);
            rentContractStatitics.get(month-1).setValue(rentContractStatitics.get(month-1).getValue() + 1);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, rentContractStatitics));
    }

    public int getMonth(String date) {

        return LocalDate.parse(date.substring(0, 10)).getMonthValue();

    }
}
