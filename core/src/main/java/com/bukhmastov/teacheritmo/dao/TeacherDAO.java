package com.bukhmastov.teacheritmo.dao;

import com.bukhmastov.teacheritmo.model.Teacher;

import java.util.List;

public interface TeacherDAO {

    Teacher findTeacherById(long id);

    Teacher findTeacherByExtId(long id);

    Teacher findTeacherByName(String name);

    List<Teacher> findTeachers(String name);

    List<Teacher> findTeachersByExtIds(List<Integer> extIds);

    boolean createTeacher(Teacher teacher);

    boolean updateTeacher(Teacher teacher);

    int count();
}
