package com.bukhmastov.teacheritmo.service.impl;

import com.bukhmastov.teacheritmo.dao.TeacherDAO;
import com.bukhmastov.teacheritmo.dict.EnSource;
import com.bukhmastov.teacheritmo.exception.BadRequestException;
import com.bukhmastov.teacheritmo.exception.NotFoundException;
import com.bukhmastov.teacheritmo.model.Teacher;
import com.bukhmastov.teacheritmo.model.TeacherList;
import com.bukhmastov.teacheritmo.service.TeacherService;
import com.bukhmastov.teacheritmo.struct.Response;
import com.bukhmastov.teacheritmo.util.CollectionUtils;
import com.bukhmastov.teacheritmo.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherDAO teacherDAO;

    @Override
    public Response<TeacherList> findTeachers(String name) {
        if (StringUtils.isBlank(name)) {
            return Response.error(new BadRequestException("Name not specified"));
        }
        List<Teacher> teachers = teacherDAO.findTeachers(name);
        return Response.ok(new TeacherList(teachers));
    }

    @Override
    public Response<Teacher> findTeacher(String name) {
        if (StringUtils.isBlank(name)) {
            return Response.error(new BadRequestException("Name not specified"));
        }
        Teacher teacher = teacherDAO.findTeacherByName(name);
        if (teacher == null) {
            return Response.error(new NotFoundException("Teacher for name '" + name + "' not found"));
        }
        return Response.ok(teacher);
    }

    @Override
    public Response<Teacher> findTeacher(Integer teacherExtId) {
        if (teacherExtId == null) {
            return Response.error(new BadRequestException("TeacherExtId not specified"));
        }
        Teacher teacher = teacherDAO.findTeacherByExtId(teacherExtId);
        if (teacher == null) {
            return Response.error(new NotFoundException("Teacher for extId '" + teacherExtId + "' not found"));
        }
        return Response.ok(teacher);
    }

    @Override
    public Response<List<Teacher>> findTeachersByExtIds(List<Integer> teacherExtIds) {
        if (CollectionUtils.isEmpty(teacherExtIds)) {
            return Response.error(new BadRequestException("TeacherExtId list not specified"));
        }
        List<Teacher> teacherList = teacherDAO.findTeachersByExtIds(teacherExtIds);
        if (teacherList == null) {
            return Response.error(new NotFoundException("Teachers for extIds '" + teacherExtIds + "' not found"));
        }
        return Response.ok(teacherList);
    }

    @Override
    public Response createTeacher(Teacher teacher, EnSource source) {
        Response response = validateTeacher(teacher);
        if (response != null) {
            return response;
        }

        teacher.setId(null);
        teacher.setSource(source);
        teacher.setCreated(new Timestamp(System.currentTimeMillis()));
        teacherDAO.createTeacher(teacher);
        return Response.ok();
    }

    @Override
    public Response<Integer> updateTeachers(List<Teacher> teacherList, EnSource source) {
        if (CollectionUtils.isEmpty(teacherList)) {
            return Response.error(new BadRequestException("TeacherList not specified"));
        }

        int updatedCount = 0;

        for (Teacher teacher : teacherList) {
            Response response = updateTeacher(teacher, source);
            if (response.isOk()) {
                updatedCount++;
            }
        }

        return Response.ok(updatedCount);
    }

    @Override
    public Response updateTeacher(Teacher teacher, EnSource source) {
        Response response = validateTeacher(teacher);
        if (response != null) {
            return response;
        }

        if (teacher.getId() == null) {
            return Response.error(new BadRequestException("Teacher.id not specified"));
        }

        teacher.setSource(source);
        teacher.setCreated(new Timestamp(System.currentTimeMillis()));
        teacherDAO.updateTeacher(teacher);
        return Response.ok();
    }

    private Response validateTeacher(Teacher teacher) {
        if (teacher == null) {
            return Response.error(new BadRequestException("Teacher not specified"));
        }
        if (teacher.getExtId() == null) {
            return Response.error(new BadRequestException("Teacher.extId not specified"));
        }
        if (StringUtils.isBlank(teacher.getName())) {
            return Response.error(new BadRequestException("Teacher.name not specified"));
        }
        if (StringUtils.isBlank(teacher.getPost())) {
            return Response.error(new BadRequestException("Teacher.post not specified"));
        }
        return null;
    }
}
