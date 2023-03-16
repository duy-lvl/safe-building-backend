package com.safepass.safebuilding.dashboard.service.impl;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.dashboard.dto.MonthStatistics;
import com.safepass.safebuilding.dashboard.dto.ServiceStatistics;
import com.safepass.safebuilding.dashboard.dto.Statistics;
import com.safepass.safebuilding.dashboard.dto.YearStatitics;
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
    private final LocalDate date = LocalDate.now();
    private final int year = date.getYear();
    private final int month = date.getMonthValue();
    private List<YearStatitics> yearStatitics = List.of(
            new YearStatitics("Jan", 0),
            new YearStatitics("Feb", 0),
            new YearStatitics("Mar", 0),
            new YearStatitics("Apr", 0),
            new YearStatitics("May", 0),
            new YearStatitics("Jun", 0),
            new YearStatitics("Jul", 0),
            new YearStatitics("Aug", 0),
            new YearStatitics("Sep", 0),
            new YearStatitics("Oct", 0),
            new YearStatitics("Nov", 0),
            new YearStatitics("Dec", 0)
    );
    @Override
    public ResponseEntity<ResponseObject> rentContractStatistics(int year) {

        for (YearStatitics ys: yearStatitics) {
            ys.setValue(0);
        }
        String query = DashboardServiceUtils.getContract(year);
        List<Statistics> rentContracts = dashboardJdbc.getContractStartDate(query);
        for (Statistics rc: rentContracts) {
            yearStatitics.get(rc.getKey() - 1).setValue(rc.getValue());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, yearStatitics));
    }

    @Override
    public ResponseEntity<ResponseObject> billStatistics(int year) {

        for (YearStatitics ys: yearStatitics) {
            ys.setValue(0);
        }
        String query = DashboardServiceUtils.getBill(year);
        List<Statistics> bills = dashboardJdbc.getBillDate(query);

        for (Statistics b : bills) {
            yearStatitics.get(b.getKey() - 1).setValue(b.getValue());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, yearStatitics));
    }

    @Override
    public ResponseEntity<ResponseObject> rentContractStatistics() {
        String thisMonthQuery = DashboardServiceUtils.getContract(year, month);
        String lastMonthQuery;
        if (month == 1) {
            lastMonthQuery = DashboardServiceUtils.getContract(year-1, 12);
        }
        else {
            lastMonthQuery = DashboardServiceUtils.getContract(year, month-1);
        }
        MonthStatistics monthStatistics = new MonthStatistics();
        List<Statistics> thisMonthContracts = dashboardJdbc.getBillDate(thisMonthQuery);
        List<Statistics> lastMonthContracts = dashboardJdbc.getBillDate(lastMonthQuery);
        int thisMonthTotal = thisMonthContracts.get(0).getValue();
        int lastMonthTotal = lastMonthContracts.get(0).getValue();
        monthStatistics.setTotal(thisMonthTotal);
        String status;
        int percent;
        if (thisMonthTotal < lastMonthTotal) {
            percent = 100 - (int) (1.0 * thisMonthTotal / lastMonthTotal * 100);
            status = "Decrease";
        } else if (thisMonthTotal == lastMonthTotal) {
            percent = 0;
            status = "Same";
        } else {
            percent = 100 - (int) (1.0 * lastMonthTotal / thisMonthTotal * 100);
            status = "Increase";
        }
        monthStatistics.setStatus(status);
        monthStatistics.setPercent(percent);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, monthStatistics));
    }

    @Override
    public ResponseEntity<ResponseObject> billStatistics() {
        
        String thisMonthQuery = DashboardServiceUtils.getBill(year, month);
        String lastMonthQuery;

        if (month == 1) {
            lastMonthQuery = DashboardServiceUtils.getBill(year-1, 12);
        }
        else {
            lastMonthQuery = DashboardServiceUtils.getBill(year, month-1);
        }
        MonthStatistics monthStatistics = new MonthStatistics();
        List<Statistics> thisMonthBills = dashboardJdbc.getBillDate(thisMonthQuery);
        List<Statistics> lastMonthBills = dashboardJdbc.getBillDate(lastMonthQuery);
        int thisMonthTotal = thisMonthBills.get(0).getValue();
        int lastMonthTotal = lastMonthBills.get(0).getValue();
        String status;
        int percent;
        if (thisMonthTotal < lastMonthTotal) {
            percent = 100 - (int) (1.0 * thisMonthTotal / lastMonthTotal * 100);
            status = "Decrease";
        } else if (thisMonthTotal == lastMonthTotal) {
            percent = 0;
            status = "Same";
        } else {
            percent = 100 - (int) (1.0 * lastMonthTotal / thisMonthTotal * 100);
            status = "Increase";
        }
        monthStatistics.setTotal(thisMonthTotal);
        monthStatistics.setStatus(status);
        monthStatistics.setPercent(percent);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, monthStatistics));
    }

    @Override
    public ResponseEntity<ResponseObject> serviceStatistics(int year, int month) {
        String query = DashboardServiceUtils.getService(year, month);
        List<ServiceStatistics> serviceStatistics = dashboardJdbc.getService(query);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, serviceStatistics));
    }


    public int getMonth(String date) {

        return LocalDate.parse(date.substring(0, 10)).getMonthValue();

    }
}
