package com.bukhmastov.teacheritmo.job;

import com.bukhmastov.teacheritmo.event.ConfigReloadedEvent;
import com.bukhmastov.teacheritmo.util.StringUtils;

import org.slf4j.Logger;
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
            scheduledFuture.cancel(false);
            start(false);
        }
    }

    private void start(boolean isFirstTime) {
        try {
            currentCronExpression = getCronExpression();
            if (StringUtils.isBlank(currentCronExpression)) {
                getLogger().info("Cron expression not defined, job not started");
                return;
            }
            if (isShouldRunAtStartup()) {
                scheduler.execute(this);
            }
            scheduledFuture = scheduler.schedule(this, new CronTrigger(currentCronExpression));
            getLogger().info("Job has been {}", isFirstTime ? "started" : "restarted");
        } catch (Throwable throwable) {
            getLogger().error("Error occurred while job startup", throwable);
        }
    }

    protected abstract Logger getLogger();

    protected abstract String getCronExpression();

    protected boolean isShouldRunAtStartup() {
        return false;
    }

    @Autowired
    ThreadPoolTaskScheduler scheduler;

    private String currentCronExpression;
    private ScheduledFuture<?> scheduledFuture;
}
