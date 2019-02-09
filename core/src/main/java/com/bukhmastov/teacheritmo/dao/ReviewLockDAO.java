package com.bukhmastov.teacheritmo.dao;

import com.bukhmastov.teacheritmo.model.ReviewLock;

import java.sql.Timestamp;

public interface ReviewLockDAO {

    boolean create(ReviewLock reviewLock);

    int count(String ip, Integer teacherExtId, Timestamp timestamp);

    int removeBeforeDate(Timestamp timestamp);
}
