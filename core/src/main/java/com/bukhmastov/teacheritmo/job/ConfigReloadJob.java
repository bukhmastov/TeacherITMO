package com.bukhmastov.teacheritmo.job;

import com.bukhmastov.teacheritmo.config.AppConfig;
import com.bukhmastov.teacheritmo.event.ConfigReloadedEvent;
import com.bukhmastov.teacheritmo.util.config.ConfigException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class ConfigReloadJob extends AbstractJob {

    @Override
    public void run() {
        try {
            if (config.checkAndReload()) {
                log.info("App config has been reloaded");
                eventPublisher.publishEvent(new ConfigReloadedEvent(this));
            }
        } catch (ConfigException e) {
            log.error("Failed to check and reload app config", e);
        }
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getCronExpression() {
        return config.data().getJobConfigCron();
    }

    @Autowired
    AppConfig config;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    private static final Logger log = LoggerFactory.getLogger(ConfigReloadJob.class);
}
