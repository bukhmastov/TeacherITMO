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
    }

    private final String jdbcDriver;
    private final String jdbcUrl;
    private final String jdbcUser;
    private final String jdbcPassword;
    private final Integer jobThreadCount;
    private final String jobTeacherCron;
    private final String jobConfigCron;

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
}
