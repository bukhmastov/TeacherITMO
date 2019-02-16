package com.bukhmastov.teacheritmo.dao.impl;

import com.bukhmastov.teacheritmo.dao.TeacherLockDAO;
import com.bukhmastov.teacheritmo.mapper.BaseMapper;
import com.bukhmastov.teacheritmo.model.TeacherLock;
import com.bukhmastov.teacheritmo.util.NetworkUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import javax.sql.DataSource;

public class TeacherLockDAOImpl extends BaseDAO<TeacherLock> implements TeacherLockDAO {

    @Autowired
    public TeacherLockDAOImpl(BaseMapper<TeacherLock> mapper) {
        super(mapper);
    }

    @Autowired
    public void init(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public boolean create(TeacherLock teacherLock) {
        return insert(teacherLock);
    }

    @Override
    public int count(String ip, Timestamp timestamp) {
        return count("ip = ? AND created >= ?", NetworkUtils.host2hex(ip), timestamp);
    }

    @Override
    public int removeBeforeDate(Timestamp timestamp) {
        return delete("created < ?", timestamp.toString());
    }
}
