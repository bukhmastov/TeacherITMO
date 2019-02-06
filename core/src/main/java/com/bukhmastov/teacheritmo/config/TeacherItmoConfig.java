package com.bukhmastov.teacheritmo.config;

import java.io.IOException;
import java.util.Properties;

public class TeacherItmoConfig extends AbstractConfig {

    private final Data data;

    public TeacherItmoConfig(String filepath) throws IOException {
        data = new Data(loadProperties(filepath));
    }

    public Data data() {
        return data;
    }

    public class Data {

        private final String jdbcDriver;
        private final String jdbcUrl;
        private final String jdbcUser;
        private final String jdbcPassword;
        private final Integer schedulerThreadCount;
        private final String schedulerCron;

        public Data(Properties properties) {
            jdbcDriver = properties.getProperty("jdbc.driver");
            jdbcUrl = properties.getProperty("jdbc.url");
            jdbcUser = properties.getProperty("jdbc.user");
            jdbcPassword = properties.getProperty("jdbc.password");
            schedulerThreadCount = Integer.parseInt(properties.getProperty("scheduler.thread.count"));
            schedulerCron = properties.getProperty("scheduler.cron");
        }

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

        public Integer getSchedulerThreadCount() {
            return schedulerThreadCount;
        }

        public String getSchedulerCron() {
            return schedulerCron;
        }
    }
}
