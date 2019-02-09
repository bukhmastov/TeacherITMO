package com.bukhmastov.teacheritmo.mapper;

import com.bukhmastov.teacheritmo.model.ReviewLock;
import com.bukhmastov.teacheritmo.util.NetworkUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewLockMapper extends BaseMapper<ReviewLock> {

    @Override
    public String getTableName() {
        return "ReviewLock";
    }

    @Override
    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("ip");
        fields.add("teacher_ext_id");
        fields.add("created");
        return fields;
    }

    @Override
    public List<Object> getFieldValues(ReviewLock entity) {
        List<Object> values = new ArrayList<>();
        values.add(entity.getId());
        values.add(NetworkUtils.host2hex(entity.getIp()));
        values.add(entity.getTeacherExtId());
        values.add(entity.getCreated());
        return values;
    }

    @Override
    public ReviewLock mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReviewLock entity = new ReviewLock();
        entity.setId(getLong(rs, "id"));
        entity.setIp(NetworkUtils.hex2host(getString(rs, "ip")));
        entity.setTeacherExtId(getInt(rs, "teacher_ext_id"));
        entity.setCreated(getTimestamp(rs, "created"));
        return entity;
    }
}
