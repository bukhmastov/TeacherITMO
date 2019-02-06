package com.bukhmastov.teacheritmo.job;

import com.bukhmastov.teacheritmo.config.TeacherItmoConfig;
import com.bukhmastov.teacheritmo.dict.EnSource;
import com.bukhmastov.teacheritmo.model.Teacher;
import com.bukhmastov.teacheritmo.model.itmo.ItmoTeacher;
import com.bukhmastov.teacheritmo.model.itmo.ItmoTeacherList;
import com.bukhmastov.teacheritmo.service.TeacherService;
import com.bukhmastov.teacheritmo.struct.Response;
import com.bukhmastov.teacheritmo.util.CollectionUtils;
import com.bukhmastov.teacheritmo.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TeacherSyncBackgroundJob implements Runnable {

    @PostConstruct
    public void init() {
        try {
            String cron = config.data().getSchedulerCron();
            if (StringUtils.isBlank(cron)) {
                log.info("Teachers sync not enabled, teachers sync not started");
                return;
            }
            scheduler.execute(this);
            scheduler.schedule(this, new CronTrigger(cron));
        } catch (Throwable throwable) {
            log.error("Error occurred while teachers init", throwable);
        }
    }

    @Override
    public void run() {
        try {
            log.info("Teachers sync has started");
            long timeStart = System.currentTimeMillis();
            RestTemplate restTemplate = new RestTemplate();
            loadTeachers(restTemplate, 0);
            long timeEnd = System.currentTimeMillis();
            log.info("Teachers sync has ended | exec time = {}ms", timeEnd - timeStart);
        } catch (Throwable throwable) {
            log.error("Error occurred while teachers sync", throwable);
        }
    }

    private void loadTeachers(RestTemplate restTemplate, int offset) {
        log.debug("Load teachers with offset={}", offset);
        ItmoTeacherList teacherList = restTemplate.getForObject(ITMO_API_URL + offset, ItmoTeacherList.class);
        if (teacherList == null) {
            throw new IllegalStateException("Failed to get teachers from api with offset=" + offset);
        }
        if (CollectionUtils.isEmpty(teacherList.getTeacherList())) {
            return;
        }
        proceedTeachers(teacherList.getTeacherList(), offset);
        int limit = teacherList.getLimit();
        int count = teacherList.getCount();
        int nextOffset = offset + limit;
        if (nextOffset > count) {
            return;
        }
        loadTeachers(restTemplate, nextOffset);
    }

    private void proceedTeachers(List<ItmoTeacher> itmoTeacherList, int offset) {
        List<Teacher> teacherList = itmoTeacherList.stream()
                .map(this::convertTeacher)
                .collect(Collectors.toList());
        List<Integer> teacherExtIdList = teacherList.stream()
                .map(Teacher::getExtId)
                .collect(Collectors.toList());
        List<Teacher> storedTeachers = new ArrayList<>();
        Response<List<Teacher>> response = teacherService.findTeachersByExtIds(teacherExtIdList);
        if (response.isOk()) {
            storedTeachers.addAll(response.getData());
        }
        int updatedCount = 0, createdCount = 0;
        for (Teacher teacher : teacherList) {
            Teacher storedTeacher = findTeacherByExtId(storedTeachers, teacher.getExtId());
            if (storedTeacher != null) {
                teacher.setId(storedTeacher.getId());
                teacherService.updateTeacher(teacher, EnSource.SYSTEM);
                updatedCount++;
            } else {
                teacherService.createTeacher(teacher, EnSource.SYSTEM);
                createdCount++;
            }
        }
        log.debug("Load teachers with offset={} | updated={} | created={}", offset, updatedCount, createdCount);
    }

    private Teacher convertTeacher(ItmoTeacher itmoTeacher) {
        Teacher teacher = new Teacher();
        teacher.setExtId(itmoTeacher.getPid());
        teacher.setName(itmoTeacher.getPerson());
        teacher.setPost(itmoTeacher.getPost());
        return teacher;
    }

    private Teacher findTeacherByExtId(List<Teacher> teachers, Integer extId) {
        for (Teacher teacher : teachers) {
            if (Objects.equals(teacher.getExtId(), extId)) {
                return teacher;
            }
        }
        return null;
    }

    @Autowired
    ThreadPoolTaskScheduler scheduler;
    @Autowired
    TeacherItmoConfig config;
    @Autowired
    TeacherService teacherService;

    private static final String ITMO_API_URL = "http://mountain.ifmo.ru/api.ifmo.ru/public/v1/schedule_person?lastname=&offset=";
    private static final Logger log = LoggerFactory.getLogger(TeacherSyncBackgroundJob.class);
}
