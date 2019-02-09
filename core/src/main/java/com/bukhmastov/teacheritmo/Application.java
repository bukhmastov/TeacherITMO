package com.bukhmastov.teacheritmo;

import com.bukhmastov.teacheritmo.config.AppConfig;
import com.bukhmastov.teacheritmo.event.ConfigReloadedEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class Application {

    @EventListener
    public void onConfigReloadedEvent(ConfigReloadedEvent event) {
        scheduler.setPoolSize(config.data().getJobThreadCount());
    }

    @Autowired
    ThreadPoolTaskScheduler scheduler;
    @Autowired
    AppConfig config;
}
