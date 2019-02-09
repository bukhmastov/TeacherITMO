package com.bukhmastov.teacheritmo.service.impl;

import com.bukhmastov.teacheritmo.config.AppConfig;
import com.bukhmastov.teacheritmo.dao.ReviewDAO;
import com.bukhmastov.teacheritmo.dao.ReviewLockDAO;
import com.bukhmastov.teacheritmo.dao.TeacherDAO;
import com.bukhmastov.teacheritmo.exception.FailedDependencyException;
import com.bukhmastov.teacheritmo.exception.BadRequestException;
import com.bukhmastov.teacheritmo.exception.TooManyRequestsException;
import com.bukhmastov.teacheritmo.model.Review;
import com.bukhmastov.teacheritmo.model.ReviewList;
import com.bukhmastov.teacheritmo.model.ReviewLock;
import com.bukhmastov.teacheritmo.model.ReviewSummary;
import com.bukhmastov.teacheritmo.model.Teacher;
import com.bukhmastov.teacheritmo.service.ReviewService;
import com.bukhmastov.teacheritmo.struct.Response;
import com.bukhmastov.teacheritmo.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

public class ReviewServiceImpl implements ReviewService {

    @Override
    public Response<ReviewList> reviewListForTeacher(Integer teacherExtId) {
        if (teacherExtId == null) {
            return Response.error(new BadRequestException("TeacherExtId not specified"));
        }
        List<Review> reviews = reviewDAO.findReviewsForTeacher(teacherExtId);
        return Response.ok(new ReviewList(reviews));
    }

    @Override
    public Response<ReviewSummary> reviewSummaryForTeacher(Integer teacherExtId) {
        if (teacherExtId == null) {
            return Response.error(new BadRequestException("TeacherExtId not specified"));
        }
        Teacher teacher = teacherDAO.findTeacherByExtId(teacherExtId);
        List<Review> reviews = reviewDAO.findReviewsForTeacher(teacherExtId);
        ReviewSummary reviewSummary = makeReviewSummary(teacher, reviews);
        return Response.ok(reviewSummary);
    }

    @Override
    public Response createReview(Review review) {
        Response response = validateReview(review);
        if (response != null) {
            return response;
        }

        if (isBlockedForTeacher(review.getTeacherExtId())) {
            return Response.error(new TooManyRequestsException("Too many reviews for provided teacher"));
        }

        Teacher teacher = teacherDAO.findTeacherByExtId(review.getTeacherExtId());
        if (teacher == null) {
            return Response.error(new FailedDependencyException("Teacher not exists"));
        }

        review.setId(null);
        review.setCreated(new Timestamp(System.currentTimeMillis()));
        reviewDAO.createReview(review);
        reviewLockDAO.create(makeReviewLock(review.getTeacherExtId()));
        logCreateAction(review.getTeacherExtId());
        return Response.ok();
    }

    private boolean isBlockedForTeacher(Integer teacherExtId) {
        if (config.data().getLockReviewHours() < 1) {
            return false;
        }
        String userIp = request.getRemoteAddr();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(config.data().getLockReviewHours()));
        int locks = reviewLockDAO.count(userIp, teacherExtId, timestamp);
        return locks >= config.data().getLockReviewLimit();
    }

    private ReviewLock makeReviewLock(Integer teacherExtId) {
        String userIp = request.getRemoteAddr();
        ReviewLock reviewLock = new ReviewLock();
        reviewLock.setIp(userIp);
        reviewLock.setTeacherExtId(teacherExtId);
        reviewLock.setCreated(new Timestamp(System.currentTimeMillis()));
        return reviewLock;
    }

    private void logCreateAction(Integer teacherExtId) {
        String userIp = request.getRemoteAddr();
        log.info("User with ip={} left review on the teacher={}", userIp, teacherExtId);
    }

    private Response validateReview(Review review) {
        if (review == null) {
            return Response.error(new BadRequestException("Review not specified"));
        }
        if (review.getTeacherExtId() == null) {
            return Response.error(new BadRequestException("Review.teacherId not specified"));
        }
        if (isCriteriaInvalid(review.getCriteria1())) {
            return Response.error(new BadRequestException("Review.getCriteria1 is invalid: " + review.getCriteria1()));
        }
        if (isCriteriaInvalid(review.getCriteria2())) {
            return Response.error(new BadRequestException("Review.getCriteria2 is invalid: " + review.getCriteria2()));
        }
        if (isCriteriaInvalid(review.getCriteria3())) {
            return Response.error(new BadRequestException("Review.getCriteria3 is invalid: " + review.getCriteria3()));
        }
        if (isCriteriaInvalid(review.getCriteria4())) {
            return Response.error(new BadRequestException("Review.getCriteria4 is invalid: " + review.getCriteria4()));
        }
        if (isCriteriaInvalid(review.getCriteria5())) {
            return Response.error(new BadRequestException("Review.getCriteria5 is invalid: " + review.getCriteria5()));
        }
        return null;
    }

    private boolean isCriteriaInvalid(int criteria) {
        return criteria < 0 || criteria > 5;
    }

    private ReviewSummary makeReviewSummary(Teacher teacher, List<Review> reviews) {
        AverageValue criteria1 = new AverageValue();
        AverageValue criteria2 = new AverageValue();
        AverageValue criteria3 = new AverageValue();
        AverageValue criteria4 = new AverageValue();
        AverageValue criteria5 = new AverageValue();
        List<String> comments = new ArrayList<>();
        for (Review review : reviews) {
            addSumIfNeeded(criteria1, review.getCriteria1());
            addSumIfNeeded(criteria2, review.getCriteria2());
            addSumIfNeeded(criteria3, review.getCriteria3());
            addSumIfNeeded(criteria4, review.getCriteria4());
            addSumIfNeeded(criteria5, review.getCriteria5());
            if (StringUtils.isNotBlank(review.getComment())) {
                comments.add(review.getComment());
            }
        }
        ReviewSummary reviewSummary = new ReviewSummary();
        reviewSummary.setTeacher(teacher);
        reviewSummary.setCriteria1(criteria1.get());
        reviewSummary.setCriteria2(criteria2.get());
        reviewSummary.setCriteria3(criteria3.get());
        reviewSummary.setCriteria4(criteria4.get());
        reviewSummary.setCriteria5(criteria5.get());
        reviewSummary.setComments(comments);
        reviewSummary.setCommentsSize(comments.size());
        reviewSummary.setTotal(reviews.size());
        return reviewSummary;
    }

    private void addSumIfNeeded(AverageValue averageValue, Integer integer) {
        if (integer != null) {
            averageValue.sum += (double) integer;
            averageValue.count += 1.0;
        }
    }

    private class AverageValue {
        private double sum = 0.0;
        private double count = 0.0;
        private Double get() {
            return count == 0.0 ? null : sum / count;
        }
    }

    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    TeacherDAO teacherDAO;
    @Autowired
    ReviewLockDAO reviewLockDAO;
    @Autowired
    HttpServletRequest request;
    @Autowired
    AppConfig config;

    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);
}
