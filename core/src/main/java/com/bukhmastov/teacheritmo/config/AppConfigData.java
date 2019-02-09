package com.bukhmastov.teacheritmo.config;

import com.bukhmastov.teacheritmo.util.config.ConfigException;
import com.bukhmastov.teacheritmo.util.config.PropertiesWrapper;

public class AppConfigData {

    public AppConfigData(PropertiesWrapper wrapper) throws ConfigException {
        jdbcDriver = wrapper.getPropertyRequired("jdbc.driver");
        jdbcUrl = wrapper.getPropertyRequired("jdbc.url");
        jdbcUser = wrapper.getPropertyRequired("jdbc.user");
        jdbcPassword = wrapper.getPropertyRequired("jdbc.password");
        jobThreadCount = wrapper.getProperty("job.thread.count", Integer.class);
        jobTeacherCron = wrapper.getPropertyRequired("job.cron.teacher");
        jobConfigCron = wrapper.getPropertyRequired("job.cron.config");
        jobLockAutoCleanerCron = wrapper.getPropertyRequired("job.cron.lock.cleaner");
        lockReviewLimit = wrapper.getPropertyRequired("lock.review.limit", Integer.class);
        lockReviewHours = wrapper.getPropertyRequired("lock.review.hours", Integer.class);
    }

    private final String jdbcDriver;
    private final String jdbcUrl;
    private final String jdbcUser;
    private final String jdbcPassword;
    private final Integer jobThreadCount;
    private final String jobTeacherCron;
    private final String jobConfigCron;
    private final String jobLockAutoCleanerCron;
    private final Integer lockReviewLimit;
    private final Integer lockReviewHours;

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public Integer getJobThreadCount() {
        return jobThreadCount;
    }

    public String getJobTeacherCron() {
        return jobTeacherCron;
    }

    public String getJobConfigCron() {
        return jobConfigCron;
    }

    public String getJobLockAutoCleanerCron() {
        return jobLockAutoCleanerCron;
    }

    public Integer getLockReviewLimit() {
        return lockReviewLimit;
    }

    public Integer getLockReviewHours() {
        return lockReviewHours;
    }
}
