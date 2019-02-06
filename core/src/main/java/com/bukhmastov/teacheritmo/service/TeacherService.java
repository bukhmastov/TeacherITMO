package com.bukhmastov.teacheritmo.service;

import com.bukhmastov.teacheritmo.dict.EnSource;
import com.bukhmastov.teacheritmo.model.Teacher;
import com.bukhmastov.teacheritmo.model.TeacherList;
import com.bukhmastov.teacheritmo.struct.Response;

import java.util.List;

public interface TeacherService {

    Response<TeacherList> findTeachers(String name);

    Response<Teacher> findTeacher(String name);

    Response<Teacher> findTeacher(Integer teacherExtId);

    Response<List<Teacher>> findTeachersByExtIds(List<Integer> teacherExtIds);

    Response createTeacher(Teacher teacher, EnSource source);

    Response<Integer> updateTeachers(List<Teacher> teacherList, EnSource source);

    Response updateTeacher(Teacher teacher, EnSource source);
}
