package com.bukhmastov.teacheritmo.dao.impl;

import com.bukhmastov.teacheritmo.dao.ReviewLockDAO;
import com.bukhmastov.teacheritmo.mapper.BaseMapper;
import com.bukhmastov.teacheritmo.model.ReviewLock;
import com.bukhmastov.teacheritmo.util.net.NetworkUtils;

import java.sql.Timestamp;

public class ReviewLockDAOImpl extends BaseDAO<ReviewLock> implements ReviewLockDAO {

    public ReviewLockDAOImpl(BaseMapper<ReviewLock> mapper) {
        super(mapper);
    }

    @Override
    public boolean create(ReviewLock reviewLock) {
        return insert(reviewLock);
    }

    @Override
    public int count(String ip, Integer teacherExtId, Timestamp timestamp) {
        return count("ip = x'?' AND teacher_ext_id = ? AND created >= '?'",
                new String(NetworkUtils.host2binary(ip)), teacherExtId, timestamp);
    }

    @Override
    public int removeBeforeDate(Timestamp timestamp) {
        return delete("created < '?'", timestamp.toString());
    }
}
