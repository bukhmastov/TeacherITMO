package com.bukhmastov.teacheritmo.dao.impl;

import com.bukhmastov.teacheritmo.mapper.BaseMapper;
import com.bukhmastov.teacheritmo.marker.HasId;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.Arrays;
import java.util.List;

public abstract class BaseDAO<T extends HasId> extends JdbcDaoSupport {

    protected final BaseMapper<T> mapper;

    public BaseDAO(BaseMapper<T> mapper) {
        this.mapper = mapper;
    }

    protected T getByQuery(String query, Object...params) {
        String sql = mapper.makeSelectSql(query);
        try {
            return getJdbcTemplateSafely().queryForObject(sql, params, mapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    protected List<T> listByQuery(String query, Object...params) {
        String sql = mapper.makeSelectSql(query);
        return getJdbcTemplateSafely().query(sql, params, mapper);
    }

    protected boolean insert(T entity) {
        entity.setId(null);
        String sql = mapper.makeInsertSql();
        Object[] params = mapper.getFieldValues(entity).toArray();
        return getJdbcTemplateSafely().update(sql, params) == 1;
    }

    protected boolean update(T entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Unable to update, id not set");
        }
        String sql = mapper.makeUpdateSql("id = ?");
        Object[] params = mapper.getFieldValues(entity).toArray();
        params = Arrays.copyOfRange(params, 1, params.length + 1);
        params[params.length - 1] = entity.getId();
        return getJdbcTemplateSafely().update(sql, params) == 1;
    }

    protected int delete(String query, Object...params) {
        String sql = mapper.makeDeleteSql(query);
        return getJdbcTemplateSafely().update(sql, params);
    }

    protected int count(String query, Object...params) {
        String sql = mapper.makeCountSql(query);
        return getJdbcTemplateSafely().queryForObject(sql, params, Integer.class);
    }

    protected JdbcTemplate getJdbcTemplateSafely() {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new IllegalStateException("JdbcTemplate not set");
        }
        return jdbcTemplate;
    }
}
