package com.safepass.safebuilding.common.batch_job.jobUtils;

import com.safepass.safebuilding.common.batch_job.entity.CustomerInfoBatchJob;
import com.safepass.safebuilding.common.meta.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BilllJobUtils {
    private final RowMapper<CustomerInfoBatchJob> rowMapper = (rs, row) -> {
        CustomerInfoBatchJob customerInfoBatchJob = new CustomerInfoBatchJob();
        customerInfoBatchJob.setCustomerId(UUID.fromString(rs.getString("customer_id")));
        customerInfoBatchJob.setFullname(rs.getString("fullname"));
        customerInfoBatchJob.setEmail(rs.getString("email"));
        customerInfoBatchJob.setPhoneToken(rs.getString("token"));
        customerInfoBatchJob.setFlatId(UUID.fromString(rs.getString("flat_id")));
        customerInfoBatchJob.setContractId(UUID.fromString(rs.getString("contract_id")));
        customerInfoBatchJob.setBillId(UUID.fromString(rs.getString("bill_id")));
        return customerInfoBatchJob;
    };
    String queryForDueContract = "SELECT DISTINCT rc.id AS contract_id, rc.flat_id, c.id AS customer_id, c.fullname, c.email, d.token, b.id AS bill_id \n" +
            "FROM rent_contract rc \n" +
            "INNER JOIN customer c ON c.id = rc.customer_id\n" +
            "INNER JOIN device d ON rc.customer_id = d.customer_id\n" +
            "INNER JOIN bill b ON b.rent_contract_id = rc.id\n" +
            "WHERE \n" +
            "b.status LIKE ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CustomerInfoBatchJob> getCustomerInfoForBillWithBillStatus(BillStatus status) {
        if (status != null) {
            return jdbcTemplate.query( queryForDueContract + " '" + status+"'", rowMapper);
        }
        return null;
    }
}
