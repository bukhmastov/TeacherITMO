package com.bukhmastov.teacheritmo.controller;

import com.bukhmastov.teacheritmo.dict.EnSource;
import com.bukhmastov.teacheritmo.exception.BadRequestException;
import com.bukhmastov.teacheritmo.model.ResponseSuccess;
import com.bukhmastov.teacheritmo.model.Teacher;
import com.bukhmastov.teacheritmo.model.TeacherList;
import com.bukhmastov.teacheritmo.service.TeacherService;
import com.bukhmastov.teacheritmo.struct.Response;
import com.bukhmastov.teacheritmo.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeacherController extends BaseController {

    @GetMapping(path = "/teachers/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TeacherList getTeachers(@PathVariable("name") String name) {
        log.debug("getTeachers(name={})", name);
        if (StringUtils.isBlank(name)) {
            throw new BadRequestException("Name not specified");
        }
        Response<TeacherList> response = teacherService.findTeachers(name);
        throwIfError(response);
        return response.getData();
    }

    @PostMapping(path = "/teacher/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseSuccess> createReview(@RequestBody Teacher teacher) {
        log.debug("createReview(teacher={})", teacher);
        if (teacher == null) {
            throw new BadRequestException("Teacher not specified");
        }
        Response response = teacherService.createTeacher(teacher, EnSource.EXTERNAL);
        throwIfError(response);
        return makeSuccessResponse(HttpStatus.CREATED);
    }

    @Autowired
    TeacherService teacherService;

    private static final Logger log = LoggerFactory.getLogger(TeacherController.class);
}
