package com.bukhmastov.teacheritmo.dao;

import com.bukhmastov.teacheritmo.model.Review;

import java.util.List;

public interface ReviewDAO {

    List<Review> findReviewsForTeacher(int teacherExtId);

    boolean createReview(Review review);
}
