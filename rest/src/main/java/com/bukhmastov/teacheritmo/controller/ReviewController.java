package com.bukhmastov.teacheritmo.controller;

import com.bukhmastov.teacheritmo.exception.BadRequestException;
import com.bukhmastov.teacheritmo.model.ResponseSuccess;
import com.bukhmastov.teacheritmo.model.Review;
import com.bukhmastov.teacheritmo.model.ReviewCriteriaDescription;
import com.bukhmastov.teacheritmo.model.ReviewDescription;
import com.bukhmastov.teacheritmo.model.ReviewList;
import com.bukhmastov.teacheritmo.model.ReviewSummary;
import com.bukhmastov.teacheritmo.service.ReviewService;
import com.bukhmastov.teacheritmo.struct.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ReviewController extends BaseController {

    @GetMapping(path = "/reviews/{pid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ReviewSummary getReviews(@PathVariable("pid") String pid) {
        log.debug("getReviews(pid={})", pid);
        int teacherExtId = string2int(pid, "TeacherExtId");
        Response<ReviewSummary> response = reviewService.reviewSummaryForTeacher(teacherExtId);
        throwIfError(response);
        return response.getData();
    }

    @GetMapping(path = "/reviews/all/{pid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ReviewList getReviewsAll(@PathVariable("pid") String pid) {
        log.debug("getReviewsAll(pid={})", pid);
        int teacherExtId = string2int(pid, "TeacherExtId");
        Response<ReviewList> response = reviewService.reviewListForTeacher(teacherExtId);
        throwIfError(response);
        return response.getData();
    }

    @PostMapping(path = "/review/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseSuccess> createReview(@RequestBody Review review) {
        log.debug("createReview(review={})", review);
        if (review == null) {
            throw new BadRequestException("Review not specified");
        }
        Response response = reviewService.createReview(review);
        throwIfError(response);
        return makeSuccessResponse(HttpStatus.CREATED);
    }

    @GetMapping(path = "/review/help", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ReviewDescription getReviewDescription() {
        ReviewDescription desc = new ReviewDescription();
        desc.setCriteria1(new ReviewCriteriaDescription("Изложение материала", "Ничего не понятно", "Отличное изложение"));
        desc.setCriteria2(new ReviewCriteriaDescription("Пунктуальность", "Постоянные опоздания", "Вовремя"));
        desc.setCriteria3(new ReviewCriteriaDescription("Требовательность", "Требования отступают от плана", "Спрашивает согласно плану"));
        desc.setCriteria4(new ReviewCriteriaDescription("Сложность сдачи экзамена/зачета", "Халява", "Спросит все"));
        desc.setCriteria5(new ReviewCriteriaDescription("Личностные качества", "Полностью не интересно", "Очень интересно"));
        return desc;
    }

    @Autowired
    ReviewService reviewService;

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
}
