package com.safepass.safebuilding.batch_job.service;

import com.safepass.safebuilding.batch_job.info.TimerInfo;
import com.safepass.safebuilding.batch_job.job.BillBatchJob;
import com.safepass.safebuilding.batch_job.job.ContractBatchJob;
import com.safepass.safebuilding.batch_job.utils.TimerUtils;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SchedulerService {
    private final Scheduler scheduler;
    private final String RUN_EVERYDAY_CRON_EXPRESSION = "0 0 0 ? * *";

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(final Class jobClass, final TimerInfo info) {
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, info);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, info);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    public Trigger triggerBuild(String identityName, String group, String cronSchedule) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(identityName, group)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
                .build();
    }

    public List<TimerInfo> getAllRunningTimers() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            log.error(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public TimerInfo getRunningTimer(String timerId) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                return null;
            }
            return (TimerInfo) jobDetail.getJobDataMap().get(timerId);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void updateTimer(final String timerId, final TimerInfo info) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                log.error("Fail to find timer with id: " + timerId);
                return;
            }
            jobDetail.getJobDataMap().put(timerId, info);
        } catch (final SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    public void deleteTimer(final String timerId) {
        try {
            scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
//            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));

            JobDetail contractBatchJob = JobBuilder.newJob(ContractBatchJob.class).withIdentity("contractBatchJob").build();
            Trigger contractTrigger = triggerBuild("contractTrigger", "contractGroup", RUN_EVERYDAY_CRON_EXPRESSION);

            JobDetail billBatchJob = JobBuilder.newJob(BillBatchJob.class).withIdentity("billBatchJob").build();
            Trigger billTrigger = triggerBuild("billTrigger", "billGroup", RUN_EVERYDAY_CRON_EXPRESSION);

            scheduler.scheduleJob(contractBatchJob, contractTrigger);
            scheduler.scheduleJob(billBatchJob, billTrigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }
}
