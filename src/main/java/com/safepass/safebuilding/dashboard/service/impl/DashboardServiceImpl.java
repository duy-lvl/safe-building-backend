package com.safepass.safebuilding.dashboard.service.impl;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.dashboard.dto.BillDTO;
import com.safepass.safebuilding.dashboard.dto.MonthStatistics;
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
        List<String> rentContracts = dashboardJdbc.getContractStartDate(query);
        for (String s: rentContracts) {
            int month = getMonth(s);
            yearStatitics.get(month-1).setValue(yearStatitics.get(month-1).getValue() + 1);
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
        List<BillDTO> bills = dashboardJdbc.getBillDate(query);
        for (BillDTO b: bills) {
            int month = getMonth(b.getDate());
            yearStatitics.get(month-1).setValue(yearStatitics.get(month-1).getValue() + b.getValue());
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
        List<String> thisMonthRentContracts = dashboardJdbc.getContractStartDate(thisMonthQuery);
        List<String> lastMonthRentContracts = dashboardJdbc.getContractStartDate(lastMonthQuery);
        int thisMonthTotal = thisMonthRentContracts.size();
        int lastMonthTotal = lastMonthRentContracts.size();
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
        List<BillDTO> thisMonthBills = dashboardJdbc.getBillDate(thisMonthQuery);
        List<BillDTO> lastMonthBills = dashboardJdbc.getBillDate(lastMonthQuery);
        int thisMonthTotal = 0;
        int lastMonthTotal = 0;
        for (BillDTO b: thisMonthBills) {
            thisMonthTotal += b.getValue();
        }
        for (BillDTO b: lastMonthBills) {
            lastMonthTotal += b.getValue();
        }
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
    

    public int getMonth(String date) {

        return LocalDate.parse(date.substring(0, 10)).getMonthValue();

    }
}
