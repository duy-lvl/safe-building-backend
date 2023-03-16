package com.safepass.safebuilding.batch_job.job;

import com.safepass.safebuilding.batch_job.entity.CustomerInfoBatchJob;
import com.safepass.safebuilding.batch_job.jobUtils.ContractJobUtils;
import com.safepass.safebuilding.common.firebase.entity.NotificationMessage;
import com.safepass.safebuilding.common.firebase.service.FirebaseMessagingService;
import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.common.service.MailSenderService;
import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.flat.repository.FlatRepository;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.rent_contract.repository.RentContractRepository;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Component
public class ContractBatchJob implements Job {
    List<CustomerInfoBatchJob> customerInfoBatchJobs;
    NotificationMessage message;
    CustomerInfoBatchJob customerInfo;
    RentContract rentContract;
    Flat flat;
    String subject;
    String mainBody;
    String wholeBody;
    String endBody="\nThank you for spending time reading this letter.\nHave a good day.\nRegards,\nSafe Building Admin Team";
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    @Autowired
    private RentContractRepository rentContractRepository;
    @Autowired
    private FlatRepository flatRepository;
    @Autowired
    ContractJobUtils contractJobUtils;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //get list customer info that has contract due today
        customerInfoBatchJobs = contractJobUtils.getCustomerInfoForDueContract(0);
        subject = "Due contract reminder";
        mainBody = "Our system notices that you have contract(s) due at the end of today so please check your information and contact us for the checkout process.";
        if (customerInfoBatchJobs.size() > 0) {
            log.info("Processing contract batch job 1.");
            for (int i = 0; i < customerInfoBatchJobs.size(); i++) {
                customerInfo = customerInfoBatchJobs.get(i);
                wholeBody = "Dear " + customerInfo.getFullname() + ",\n" + mainBody+endBody;
                message = new NotificationMessage(customerInfo.getPhoneToken(), subject, wholeBody, new HashMap<String, String>());

                mailSenderService.sendMail(customerInfo.getEmail(), subject, wholeBody);
                firebaseMessagingService.sendNotificationByToken(message);
            }
        }

        //get list customer info that has contract due in 4 days
        customerInfoBatchJobs = contractJobUtils.getCustomerInfoForDueContract(4);
        mainBody = "Our system notices that you have contract(s) due in 4 days so please check your information and contact us for the checkout process.";
        if (customerInfoBatchJobs.size() > 0) {
            log.info("Processing contract batch job 2.");
            for (int i = 0; i < customerInfoBatchJobs.size(); i++) {
                customerInfo = customerInfoBatchJobs.get(i);
                wholeBody = "Dear " + customerInfo.getFullname() + ",\n" + mainBody+endBody;
                message = new NotificationMessage(customerInfo.getPhoneToken(), subject, wholeBody, new HashMap<String, String>());

                mailSenderService.sendMail(customerInfo.getEmail(), subject, wholeBody);
                firebaseMessagingService.sendNotificationByToken(message);
            }
        }

        //get list customer info that has contract due in a week
        customerInfoBatchJobs = contractJobUtils.getCustomerInfoForDueContract(7);
        mainBody = "Our system notices that you have contract(s) due in a week so please check your information and contact us for the checkout process.";
        if (customerInfoBatchJobs.size() > 0) {
            log.info("Processing contract batch job 3.");
            for (int i = 0; i < customerInfoBatchJobs.size(); i++) {
                customerInfo = customerInfoBatchJobs.get(i);
                wholeBody = "Dear " + customerInfo.getFullname() + ",\n" + mainBody+endBody;
                message = new NotificationMessage(customerInfo.getPhoneToken(), subject, wholeBody, new HashMap<String, String>());

                mailSenderService.sendMail(customerInfo.getEmail(), subject, wholeBody);
                firebaseMessagingService.sendNotificationByToken(message);
            }
        }

        //update contract's status to INVALID, flat's status to PENDING when due
        subject = "Due contract reminder";
        mainBody = "Our system notices that you have contract(s) due today so please check your information and contact us for the checkout process.";
        customerInfoBatchJobs = contractJobUtils.getCustomerInfoForDueContract(-1);
        if (customerInfoBatchJobs.size() > 0) {
            log.info("Processing contract batch job 4.");
            for (int i = 0; i < customerInfoBatchJobs.size(); i++) {
                customerInfo = customerInfoBatchJobs.get(i);
                wholeBody = "Dear " + customerInfo.getFullname() + ",\n" + mainBody+endBody;
                message = new NotificationMessage(customerInfo.getPhoneToken(), subject, wholeBody, new HashMap<String, String>());

                mailSenderService.sendMail(customerInfo.getEmail(), subject, wholeBody);
                firebaseMessagingService.sendNotificationByToken(message);

                flat = flatRepository.findById(customerInfo.getFlatId()).get();
                if (flat != null) {
                    flat.setStatus(FlatStatus.PENDING);
                    flatRepository.save(flat);
                }

                rentContract = rentContractRepository.findById(customerInfo.getContractId()).get();
                if (rentContract != null) {
                    rentContract.setStatus(RentContractStatus.EXPIRED);
                    rentContractRepository.save(rentContract);
                }
            }
        }

    }
}
