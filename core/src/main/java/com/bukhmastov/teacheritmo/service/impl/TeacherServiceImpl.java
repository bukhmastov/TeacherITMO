package com.bukhmastov.teacheritmo.service.impl;

import com.bukhmastov.teacheritmo.config.AppConfig;
import com.bukhmastov.teacheritmo.dao.TeacherDAO;
import com.bukhmastov.teacheritmo.dao.TeacherLockDAO;
import com.bukhmastov.teacheritmo.dict.EnSource;
import com.bukhmastov.teacheritmo.exception.BadRequestException;
import com.bukhmastov.teacheritmo.exception.NotFoundException;
import com.bukhmastov.teacheritmo.exception.TooManyRequestsException;
import com.bukhmastov.teacheritmo.model.Teacher;
import com.bukhmastov.teacheritmo.model.TeacherList;
import com.bukhmastov.teacheritmo.model.TeacherLock;
import com.bukhmastov.teacheritmo.service.TeacherService;
import com.bukhmastov.teacheritmo.struct.Response;
import com.bukhmastov.teacheritmo.util.CollectionUtils;
import com.bukhmastov.teacheritmo.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

public class TeacherServiceImpl implements TeacherService {

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

        if (isBlocked()) {
            return Response.error(new TooManyRequestsException("Too many requests for teacher create action. " +
                    "Retry in " + config.data().getLockTeacherHours() + " hours."));
        }

        Teacher teacherF = teacherDAO.findTeacherByExtId(teacher.getExtId());
        if (teacherF != null) {
            return Response.error(new BadRequestException("Teacher with extId '" + teacher.getExtId() +
                    "' already exists: '" + teacherF.getName() + "'"));
        }

        teacher.setId(null);
        teacher.setSource(source);
        teacher.setCreated(new Timestamp(System.currentTimeMillis()));
        teacherDAO.createTeacher(teacher);
        if (source == EnSource.EXTERNAL) {
            createTeacherLock();
            logCreateAction(teacher);
        }
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
            return Response.error(new BadRequestException("Teacher.id(internal) not specified"));
        }

        Teacher teacherF = teacherDAO.findTeacherByExtId(teacher.getExtId());
        if (teacherF == null) {
            return Response.error(new BadRequestException("Teacher with extId '" + teacher.getExtId() + "' not exists"));
        }

        teacher.setSource(source);
        teacher.setCreated(new Timestamp(System.currentTimeMillis()));
        teacherDAO.updateTeacher(teacher);
        if (source == EnSource.EXTERNAL) {
            logUpdateAction(teacher);
        }
        return Response.ok();
    }

    @Override
    public Response<Integer> countTeachers() {
        return Response.ok(teacherDAO.count());
    }

    private boolean isBlocked() {
        if (config.data().getLockTeacherHours() < 1) {
            return false;
        }
        String userIp = request.getRemoteAddr();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(config.data().getLockTeacherHours()));
        int locks = teacherLockDAO.count(userIp, timestamp);
        return locks >= config.data().getLockTeacherLimit();
    }

    private void createTeacherLock() {
        if (config.data().getLockTeacherHours() < 1) {
            return;
        }
        String userIp = request.getRemoteAddr();
        TeacherLock lock = new TeacherLock();
        lock.setIp(userIp);
        lock.setCreated(new Timestamp(System.currentTimeMillis()));
        teacherLockDAO.create(lock);
    }

    private Response validateTeacher(Teacher teacher) {
        if (teacher == null) {
            return Response.error(new BadRequestException("Teacher not specified"));
        }
        if (teacher.getExtId() == null) {
            return Response.error(new BadRequestException("Teacher.id not specified"));
        }
        if (StringUtils.isBlank(teacher.getName())) {
            return Response.error(new BadRequestException("Teacher.name not specified"));
        }
        if (StringUtils.isBlank(teacher.getPost())) {
            return Response.error(new BadRequestException("Teacher.post not specified"));
        }
        return null;
    }

    private void logCreateAction(Teacher teacher) {
        String userIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        log.trace("{} - [{}, {}, {}] - new teacher ({})",
                userIp, teacher.getExtId(), teacher.getName(), teacher.getPost(), userAgent);
    }

    private void logUpdateAction(Teacher teacher) {
        String userIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        log.trace("{} - [{}, {}, {}] - update teacher ({})",
                userIp, teacher.getExtId(), teacher.getName(), teacher.getPost(), userAgent);
    }

    @Autowired
    TeacherDAO teacherDAO;
    @Autowired
    TeacherLockDAO teacherLockDAO;
    @Autowired
    HttpServletRequest request;
    @Autowired
    AppConfig config;

    private static final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);
}
