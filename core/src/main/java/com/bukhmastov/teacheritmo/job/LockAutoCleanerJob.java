package com.bukhmastov.teacheritmo.job;

import com.bukhmastov.teacheritmo.config.AppConfig;
import com.bukhmastov.teacheritmo.dao.ReviewLockDAO;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class LockAutoCleanerJob extends AbstractJob {

    @Override
    protected void execute() throws Throwable {
        if (config.data().getLockReviewHours() < 1) {
            return;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(config.data().getLockReviewHours()));
        int count = reviewLockDAO.removeBeforeDate(timestamp);
        if (count > 0) {
            log.debug("Removed {} outdated review locks", count);
        }
    }

    @Override
    protected Class<? extends AbstractJob> getChildClass() {
        return LockAutoCleanerJob.class;
    }

    @Override
    protected String getCronExpression() {
        return config.data().getJobLockAutoCleanerCron();
    }

    @Autowired
    AppConfig config;
    @Autowired
    ReviewLockDAO reviewLockDAO;
}
