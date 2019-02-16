package com.bukhmastov.teacheritmo.job;

import com.bukhmastov.teacheritmo.config.AppConfig;
import com.bukhmastov.teacheritmo.dao.ReviewLockDAO;
import com.bukhmastov.teacheritmo.dao.TeacherLockDAO;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class LockAutoCleanerJob extends AbstractJob {

    @Override
    protected void execute() throws Exception {
        clearReviewLocks();
        clearTeacherLocks();
    }

    private void clearReviewLocks() {
        if (config.data().getLockReviewHours() < 1) {
            return;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(config.data().getLockReviewHours()));
        int count = reviewLockDAO.removeBeforeDate(timestamp);
        if (count > 0) {
            log.debug("Removed {} outdated review locks", count);
        }
    }

    private void clearTeacherLocks() {
        if (config.data().getLockTeacherHours() < 1) {
            return;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(config.data().getLockTeacherHours()));
        int count = teacherLockDAO.removeBeforeDate(timestamp);
        if (count > 0) {
            log.debug("Removed {} outdated teacher locks", count);
        }
    }

    @Override
    protected String getCronExpression() {
        return config.data().getJobLockAutoCleanerCron();
    }

    @Autowired
    AppConfig config;
    @Autowired
    ReviewLockDAO reviewLockDAO;
    @Autowired
    TeacherLockDAO teacherLockDAO;
}
