package com.bukhmastov.teacheritmo.dao.impl;

import com.bukhmastov.teacheritmo.dao.ReviewLockDAO;
import com.bukhmastov.teacheritmo.mapper.BaseMapper;
import com.bukhmastov.teacheritmo.model.ReviewLock;
import com.bukhmastov.teacheritmo.util.NetworkUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import javax.sql.DataSource;

public class ReviewLockDAOImpl extends BaseDAO<ReviewLock> implements ReviewLockDAO {

    @Autowired
    public ReviewLockDAOImpl(BaseMapper<ReviewLock> mapper) {
        super(mapper);
    }

    @Autowired
    public void init(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public boolean create(ReviewLock reviewLock) {
        return insert(reviewLock);
    }

    @Override
    public int count(String ip, Integer teacherExtId, Timestamp timestamp) {
        return count("ip = ? AND teacher_ext_id = ? AND created >= ?",
                NetworkUtils.host2hex(ip), teacherExtId, timestamp);
    }

    @Override
    public int removeBeforeDate(Timestamp timestamp) {
        return delete("created < ?", timestamp.toString());
    }
}
