package com.bukhmastov.teacheritmo.dao;

import com.bukhmastov.teacheritmo.model.TeacherLock;

import java.sql.Timestamp;

public interface TeacherLockDAO {

    boolean create(TeacherLock teacherLock);

    int count(String ip, Timestamp timestamp);

    int removeBeforeDate(Timestamp timestamp);
}
