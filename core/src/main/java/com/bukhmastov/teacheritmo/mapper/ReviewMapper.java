package com.bukhmastov.teacheritmo.mapper;

import com.bukhmastov.teacheritmo.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewMapper extends BaseMapper<Review> {

    @Override
    public String getTableName() {
        return "Review";
    }

    @Override
    public List<String> getFields() {
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("teacher_ext_id");
        fields.add("comment");
        fields.add("criteria1");
        fields.add("criteria2");
        fields.add("criteria3");
        fields.add("criteria4");
        fields.add("criteria5");
        fields.add("created");
        return fields;
    }

    @Override
    public List<Object> getFieldValues(Review review) {
        List<Object> values = new ArrayList<>();
        values.add(review.getId());
        values.add(review.getTeacherExtId());
        values.add(review.getComment());
        values.add(review.getCriteria1());
        values.add(review.getCriteria2());
        values.add(review.getCriteria3());
        values.add(review.getCriteria4());
        values.add(review.getCriteria5());
        values.add(review.getCreated());
        return values;
    }

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        review.setId(getLong(rs, "id"));
        review.setTeacherExtId(getInt(rs, "teacher_ext_id"));
        review.setComment(getString(rs, "comment"));
        review.setCriteria1(getInt(rs, "criteria1"));
        review.setCriteria2(getInt(rs, "criteria2"));
        review.setCriteria3(getInt(rs, "criteria3"));
        review.setCriteria4(getInt(rs, "criteria4"));
        review.setCriteria5(getInt(rs, "criteria5"));
        review.setCreated(getTimestamp(rs, "created"));
        return review;
    }
}
