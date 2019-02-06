package com.bukhmastov.teacheritmo.mapper;

import com.bukhmastov.teacheritmo.dict.EnSource;
import com.bukhmastov.teacheritmo.model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherMapper extends BaseMapper<Teacher> {

    @Override
    public String getTableName() {
        return "Teacher";
    }

    @Override
    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("ext_id");
        fields.add("name");
        fields.add("post");
        fields.add("source");
        fields.add("created");
        return fields;
    }

    @Override
    public List<Object> getFieldValues(Teacher teacher) {
        List<Object> values = new ArrayList<>();
        values.add(teacher.getId());
        values.add(teacher.getExtId());
        values.add(teacher.getName());
        values.add(teacher.getPost());
        values.add(teacher.getSource().getId());
        values.add(teacher.getCreated());
        return values;
    }

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(getLong(rs, "id"));
        teacher.setExtId(getInt(rs, "ext_id"));
        teacher.setName(getString(rs, "name"));
        teacher.setPost(getString(rs, "post"));
        teacher.setSource(EnSource.byId(getInt(rs, "source")));
        teacher.setCreated(getTimestamp(rs, "created"));
        return teacher;
    }
}
