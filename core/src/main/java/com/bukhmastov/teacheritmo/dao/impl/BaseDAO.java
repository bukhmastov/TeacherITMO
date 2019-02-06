package com.bukhmastov.teacheritmo.dao.impl;

import com.bukhmastov.teacheritmo.mapper.BaseMapper;
import com.bukhmastov.teacheritmo.marker.HasId;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.Arrays;
import java.util.List;

public abstract class BaseDAO<T extends HasId> extends JdbcDaoSupport {

    private final BaseMapper<T> mapper;

    public BaseDAO(BaseMapper<T> mapper) {
        this.mapper = mapper;
    }

    protected T getByQuery(String query, Object...params) {
        String sql = mapper.makeSelectSql(query);
        return getJdbcTemplateSafely().queryForObject(sql, params, mapper);
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

    protected JdbcTemplate getJdbcTemplateSafely() {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        if (jdbcTemplate == null) {
            throw new IllegalStateException("JdbcTemplate not set");
        }
        return jdbcTemplate;
    }
}
