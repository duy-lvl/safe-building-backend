package com.safepass.safebuilding.batch_job.jobUtils;

import com.safepass.safebuilding.batch_job.entity.CustomerInfoBatchJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ContractJobUtils {
    private final RowMapper<CustomerInfoBatchJob> rowMapper = (rs, row) -> {
        CustomerInfoBatchJob customerInfoBatchJob = new CustomerInfoBatchJob();
        customerInfoBatchJob.setContractId(UUID.fromString(rs.getString("contract_id")));
        customerInfoBatchJob.setFlatId(UUID.fromString(rs.getString("flat_id")));
        customerInfoBatchJob.setCustomerId(UUID.fromString(rs.getString("customer_id")));
        customerInfoBatchJob.setFullname(rs.getString("fullname"));
        customerInfoBatchJob.setEmail(rs.getString("email"));
        customerInfoBatchJob.setPhoneToken(rs.getString("token"));
        return customerInfoBatchJob;
    };
    String queryForDueContract = "SELECT DISTINCT rc.id AS contract_id, rc.flat_id, c.id AS customer_id, c.fullname, c.email, d.token \n" +
            "FROM rent_contract rc \n" +
            "INNER JOIN customer c ON c.id = rc.customer_id\n" +
            "INNER JOIN device d ON rc.customer_id = d.customer_id\n" +
            "WHERE \n" +
            "rc.status LIKE 'VALID' \n" +
            "AND YEAR(rc.expiry_date) = YEAR(CURDATE()) \n" +
            "AND MONTH(rc.expiry_date)=MONTH(CURDATE()) \n" +
            "AND DAY(rc.expiry_date)-DAY(CURDATE()) = ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CustomerInfoBatchJob> getCustomerInfoForDueContract(int dayLeft) {
        return jdbcTemplate.query( queryForDueContract + " " + dayLeft, rowMapper);
    }
}
