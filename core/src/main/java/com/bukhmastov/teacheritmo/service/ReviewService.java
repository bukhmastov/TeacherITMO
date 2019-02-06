package com.bukhmastov.teacheritmo.service;

import com.bukhmastov.teacheritmo.model.Review;
import com.bukhmastov.teacheritmo.model.ReviewList;
import com.bukhmastov.teacheritmo.model.ReviewSummary;
import com.bukhmastov.teacheritmo.struct.Response;

public interface ReviewService {

    Response<ReviewList> reviewListForTeacher(Integer teacherExtId);

    Response<ReviewSummary> reviewSummaryForTeacher(Integer teacherExtId);

    Response createReview(Review review);
}
