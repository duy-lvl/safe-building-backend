package com.safepass.safebuilding.common.batch_job.job;

import com.safepass.safebuilding.common.batch_job.entity.CustomerInfoBatchJob;
import com.safepass.safebuilding.common.batch_job.jobUtils.BilllJobUtils;
import com.safepass.safebuilding.bill.repository.BillRepository;
import com.safepass.safebuilding.common.firebase.entity.NotificationMessage;
import com.safepass.safebuilding.common.firebase.service.FirebaseMessagingService;
import com.safepass.safebuilding.common.meta.BillStatus;
import com.safepass.safebuilding.common.service.MailSenderService;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Log4j2
@Component
public class BillBatchJob implements Job {
    @Autowired
    BillRepository billRepository;
    @Autowired
    BilllJobUtils billlJobUtils;
    String subject;
    String mainBody;
    String wholeBody;
    String endBody = "\nThank you for spending time reading this letter.\nHave a good day.\nRegards,\nSafe Building Admin Team";
    NotificationMessage message;
    CustomerInfoBatchJob customerInfo;
    List<CustomerInfoBatchJob> customerInfos;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //Find bill that is unpaid
        customerInfos = billlJobUtils.getCustomerInfoForBillWithBillStatus(BillStatus.UNPAID);
        subject = "Unpaid bill reminder";
        mainBody = "Our system notices that you have bill(s) that is unpaid so please check your information and pay the bill as soon as possible.";
        if (customerInfos != null) {
            log.info("Processing bill batch job.");
            for (int i = 0; i < customerInfos.size(); i++) {
                customerInfo = customerInfos.get(i);
                wholeBody = "Dear " + customerInfo.getFullname() + ",\n" + mainBody + endBody;
                message = new NotificationMessage(customerInfo.getPhoneToken(), subject, wholeBody, new HashMap<String, String>());

                mailSenderService.sendMail(customerInfo.getEmail(), subject, wholeBody);
                firebaseMessagingService.sendNotificationByToken(message);
            }
        }
    }


}
