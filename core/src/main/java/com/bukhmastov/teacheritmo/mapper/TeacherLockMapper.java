package com.bukhmastov.teacheritmo.mapper;

import com.bukhmastov.teacheritmo.model.TeacherLock;
import com.bukhmastov.teacheritmo.util.NetworkUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherLockMapper extends BaseMapper<TeacherLock> {

    @Override
    public String getTableName() {
        return "TeacherLock";
    }

    @Override
    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("ip");
        fields.add("created");
        return fields;
    }

    @Override
    public List<Object> getFieldValues(TeacherLock entity) {
        List<Object> values = new ArrayList<>();
        values.add(entity.getId());
        values.add(NetworkUtils.host2hex(entity.getIp()));
        values.add(entity.getCreated());
        return values;
    }

    @Override
    public TeacherLock mapRow(ResultSet rs, int rowNum) throws SQLException {
        TeacherLock entity = new TeacherLock();
        entity.setId(getLong(rs, "id"));
        entity.setIp(NetworkUtils.hex2host(getString(rs, "ip")));
        entity.setCreated(getTimestamp(rs, "created"));
        return entity;
    }
}
