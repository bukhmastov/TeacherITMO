package com.bukhmastov.teacheritmo.dao.impl;

import com.bukhmastov.teacheritmo.dao.ReviewDAO;
import com.bukhmastov.teacheritmo.mapper.BaseMapper;
import com.bukhmastov.teacheritmo.model.Review;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import javax.sql.DataSource;

public class ReviewDAOImpl extends BaseDAO<Review> implements ReviewDAO {

    @Autowired
    public ReviewDAOImpl(BaseMapper<Review> mapper) {
        super(mapper);
    }

    @Autowired
    public void init(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public List<Review> findReviewsForTeacher(int teacherExtId) {
        return listByQuery("teacher_ext_id = ?", teacherExtId);
    }

    @Override
    public boolean createReview(Review review) {
        return insert(review);
    }
}
