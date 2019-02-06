package com.bukhmastov.teacheritmo.dao.impl;

import com.bukhmastov.teacheritmo.dao.TeacherDAO;
import com.bukhmastov.teacheritmo.mapper.BaseMapper;
import com.bukhmastov.teacheritmo.model.Teacher;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

public class TeacherDAOImpl extends BaseDAO<Teacher> implements TeacherDAO {

    @Autowired
    public TeacherDAOImpl(BaseMapper<Teacher> mapper) {
        super(mapper);
    }

    @Autowired
    public void init(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public Teacher findTeacherById(long id) {
        return getByQuery("id = ?", id);
    }

    @Override
    public Teacher findTeacherByExtId(long id) {
        return getByQuery("ext_id = ?", id);
    }

    @Override
    public Teacher findTeacherByName(String name) {
        return getByQuery("name = ?", name);
    }

    @Override
    public List<Teacher> findTeachers(String name) {
        return listByQuery("name LIKE ?", "%" + name + "%");
    }

    @Override
    public List<Teacher> findTeachersByExtIds(List<Integer> extIds) {
        String q = extIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
        return listByQuery("ext_id in (" + q + ")", extIds.toArray());
    }

    @Override
    public boolean createTeacher(Teacher teacher) {
        return insert(teacher);
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return update(teacher);
    }
}
