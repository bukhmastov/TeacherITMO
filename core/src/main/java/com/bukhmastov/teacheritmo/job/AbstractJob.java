package com.bukhmastov.teacheritmo.job;

import com.bukhmastov.teacheritmo.event.ConfigReloadedEvent;
import com.bukhmastov.teacheritmo.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

public abstract class AbstractJob implements Runnable {

    @PostConstruct
    public void init() {
        start(true);
    }

    @EventListener
    public void onConfigReloadedEvent(ConfigReloadedEvent event) {
        if (!Objects.equals(getCronExpression(), currentCronExpression)) {
            restart();
        }
    }

    @Override
    public void run() {
        try {
            isRunningJob = true;
            try {
                execute();
            } catch (Exception exception) {
                log.error("Failed to execute job", exception);
            }
            isRunningJob = false;
            if (isRestartJobOnFinish) {
                isRestartJobOnFinish = false;
                restart();
            }
        } catch (Exception exception) {
            log.error("Failed to execute inner job", exception);
        }
    }

    private void start(boolean isFirstTime) {
        try {
            currentCronExpression = getCronExpression();
            if (StringUtils.isBlank(currentCronExpression)) {
                log.info("Cron expression not defined, job not started");
                return;
            }
            log.info("Job is about to {}", isFirstTime ? "start" : "restart");
            if (isShouldRunAtStartup()) {
                scheduler.execute(this);
            }
            scheduledFuture = scheduler.schedule(this, new CronTrigger(currentCronExpression));
        } catch (Exception exception) {
            log.error("Error occurred while job startup", exception);
        }
    }

    private void restart() {
        if (isRunningJob) {
            isRestartJobOnFinish = true;
        } else {
            scheduledFuture.cancel(false);
            start(false);
        }
    }

    protected abstract void execute() throws Exception;

    protected abstract String getCronExpression();

    protected boolean isShouldRunAtStartup() {
        return false;
    }

    @Autowired
    ThreadPoolTaskScheduler scheduler;

    private String currentCronExpression;
    private ScheduledFuture<?> scheduledFuture;
    private boolean isRunningJob = false;
    private boolean isRestartJobOnFinish = false;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
}
