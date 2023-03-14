package com.safepass.safebuilding.batch_job.service;

import com.safepass.safebuilding.batch_job.info.TimerInfo;
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

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(final Class jobClass, final TimerInfo info){
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, info);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, info);

        try{
            scheduler.scheduleJob(jobDetail, trigger);
        }catch(SchedulerException e){
            log.error(e.getMessage());
        }
    }

    public List<TimerInfo> getAllRunningTimers(){
        try{
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
        } catch (SchedulerException e){
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public TimerInfo getRunningTimer(String timerId){
        try{
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if(jobDetail == null){
                return null;
            }
            return (TimerInfo) jobDetail.getJobDataMap().get(timerId);
        } catch (SchedulerException e){
            log.error(e.getMessage());
            return null;
        }
    }

    public void updateTimer(final String timerId, final TimerInfo info){

    }

    @PostConstruct
    public void init(){
        try{
            scheduler.start();
        } catch(SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void preDestroy(){
        try {
            scheduler.shutdown();
        } catch(SchedulerException e){
            log.error(e.getMessage());
        }
    }
}
