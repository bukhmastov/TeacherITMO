package com.bukhmastov.teacheritmo.job;

import com.bukhmastov.teacheritmo.config.AppConfig;
import com.bukhmastov.teacheritmo.event.ConfigReloadedEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class ConfigReloadJob extends AbstractJob {

    @Override
    protected void execute() throws Exception {
        if (config.checkAndReload()) {
            log.info("App config has been reloaded");
            eventPublisher.publishEvent(new ConfigReloadedEvent(this));
        }
    }

    @Override
    protected String getCronExpression() {
        return config.data().getJobConfigCron();
    }

    @Autowired
    AppConfig config;
    @Autowired
    ApplicationEventPublisher eventPublisher;
}
