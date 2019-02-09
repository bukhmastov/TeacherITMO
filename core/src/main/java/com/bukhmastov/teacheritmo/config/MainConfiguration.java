package com.bukhmastov.teacheritmo.config;

import com.bukhmastov.teacheritmo.Application;
import com.bukhmastov.teacheritmo.dao.ReviewDAO;
import com.bukhmastov.teacheritmo.dao.ReviewLockDAO;
import com.bukhmastov.teacheritmo.dao.TeacherDAO;
import com.bukhmastov.teacheritmo.dao.impl.ReviewDAOImpl;
import com.bukhmastov.teacheritmo.dao.impl.ReviewLockDAOImpl;
import com.bukhmastov.teacheritmo.dao.impl.TeacherDAOImpl;
import com.bukhmastov.teacheritmo.job.ConfigReloadJob;
import com.bukhmastov.teacheritmo.job.LockAutoCleanerJob;
import com.bukhmastov.teacheritmo.job.TeacherSyncBackgroundJob;
import com.bukhmastov.teacheritmo.mapper.ReviewLockMapper;
import com.bukhmastov.teacheritmo.mapper.ReviewMapper;
import com.bukhmastov.teacheritmo.mapper.TeacherMapper;
import com.bukhmastov.teacheritmo.service.ReviewService;
import com.bukhmastov.teacheritmo.service.TeacherService;
import com.bukhmastov.teacheritmo.service.impl.ReviewServiceImpl;
import com.bukhmastov.teacheritmo.service.impl.TeacherServiceImpl;
import com.bukhmastov.teacheritmo.util.config.ConfigException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableAspectJAutoProxy
public class MainConfiguration {

    @Bean
    public Application getApplication() {
        return new Application();
    }

    @Bean
    public AppConfig getAppConfig() throws ConfigException {
        return new AppConfig();
    }

    @Bean
    public DataSource getDataSource(@Autowired AppConfig config) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                config.data().getJdbcUrl(),
                config.data().getJdbcUser(),
                config.data().getJdbcPassword()
        );
        dataSource.setDriverClassName(config.data().getJdbcDriver());
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(@Autowired DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/changelog-cumulative.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @Bean
    public ThreadPoolTaskScheduler getThreadPoolTaskScheduler(@Autowired AppConfig config) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(config.data().getJobThreadCount());
        return scheduler;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    //* DAO *//

    @Bean
    public ReviewDAO getReviewDAO(@Autowired ReviewMapper mapper) {
        return new ReviewDAOImpl(mapper);
    }

    @Bean
    public TeacherDAO getTeacherDAO(@Autowired TeacherMapper mapper) {
        return new TeacherDAOImpl(mapper);
    }

    @Bean
    public ReviewLockDAO getReviewLockDAO(@Autowired ReviewLockMapper mapper) {
        return new ReviewLockDAOImpl(mapper);
    }

    //* Mapper *//

    @Bean
    public TeacherMapper getTeacherMapper() {
        return new TeacherMapper();
    }

    @Bean
    public ReviewMapper getReviewMapper() {
        return new ReviewMapper();
    }

    @Bean
    public ReviewLockMapper getReviewLockMapper() {
        return new ReviewLockMapper();
    }

    //* Service *//

    @Bean
    public ReviewService getReviewService() {
        return new ReviewServiceImpl();
    }

    @Bean
    public TeacherService getTeacherService() {
        return new TeacherServiceImpl();
    }

    //* Job *//

    @Bean
    public TeacherSyncBackgroundJob getTeacherSyncBackgroundJob() {
        return new TeacherSyncBackgroundJob();
    }

    @Bean
    public ConfigReloadJob getConfigReloadJob() {
        return new ConfigReloadJob();
    }

    @Bean
    public LockAutoCleanerJob getLockAutoCleanerJob() {
        return new LockAutoCleanerJob();
    }
}
