package com.bukhmastov.teacheritmo.factory;

import com.bukhmastov.teacheritmo.config.TeacherItmoConfig;
import com.bukhmastov.teacheritmo.dao.ReviewDAO;
import com.bukhmastov.teacheritmo.dao.TeacherDAO;
import com.bukhmastov.teacheritmo.dao.impl.ReviewDAOImpl;
import com.bukhmastov.teacheritmo.dao.impl.TeacherDAOImpl;
import com.bukhmastov.teacheritmo.job.TeacherSyncBackgroundJob;
import com.bukhmastov.teacheritmo.mapper.ReviewMapper;
import com.bukhmastov.teacheritmo.mapper.TeacherMapper;
import com.bukhmastov.teacheritmo.service.ReviewService;
import com.bukhmastov.teacheritmo.service.TeacherService;
import com.bukhmastov.teacheritmo.service.impl.ReviewServiceImpl;
import com.bukhmastov.teacheritmo.service.impl.TeacherServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.io.IOException;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableAspectJAutoProxy
public class MainConfiguration {

    @Bean
    TeacherItmoConfig getTeacherItmoConfig() throws IOException {
        return new TeacherItmoConfig("/teacheritmo.properties");
    }

    @Bean
    DataSource getDataSource(@Autowired TeacherItmoConfig config) {
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
    ThreadPoolTaskScheduler getThreadPoolTaskScheduler(@Autowired TeacherItmoConfig config) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(config.data().getSchedulerThreadCount());
        return scheduler;
    }

    //* DAO *//

    @Bean
    ReviewDAO getReviewDAO(@Autowired ReviewMapper mapper) {
        return new ReviewDAOImpl(mapper);
    }

    @Bean
    TeacherDAO getTeacherDAO(@Autowired TeacherMapper mapper) {
        return new TeacherDAOImpl(mapper);
    }

    //* Mapper *//

    @Bean
    TeacherMapper getTeacherMapper() {
        return new TeacherMapper();
    }

    @Bean
    ReviewMapper getReviewMapper() {
        return new ReviewMapper();
    }

    //* Service *//

    @Bean
    ReviewService getReviewService() {
        return new ReviewServiceImpl();
    }

    @Bean
    TeacherService getTeacherService() {
        return new TeacherServiceImpl();
    }

    //* Job *//

    @Bean
    TeacherSyncBackgroundJob getTeacherSyncBackgroundJob() {
        return new TeacherSyncBackgroundJob();
    }
}
