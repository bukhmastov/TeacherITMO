package com.bukhmastov.teacheritmo.mapper;

import com.bukhmastov.teacheritmo.util.StringUtils;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMapper<T> implements RowMapper<T> {

    public abstract String getTableName();

    public abstract List<String> getFields();

    public abstract List<Object> getFieldValues(T entity);

    public String makeSelectSql(String where) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" ");
        sb.append(String.join(", ", getFields()));
        sb.append(" FROM ").append(getTableName());
        if (StringUtils.isNotBlank(where)) {
            sb.append(" WHERE ").append(where);
        }
        sb.append(";");
        return sb.toString();
    }

    public String makeInsertSql() {
        List<String> fields = getFields();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(getTableName());
        sb.append(" (");
        sb.append(String.join(", ", fields));
        sb.append(") VALUES (");
        sb.append(fields.stream()
                .map(field -> "?")
                .collect(Collectors.joining(", ")));
        sb.append(");");
        return sb.toString();
    }

    public String makeUpdateSql(String where) {
        List<String> fields = getFields();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(getTableName());
        sb.append(" SET ");
        for (int i = 1; i < fields.size(); i++) {
            sb.append(fields.get(i));
            sb.append(" = ?");
            if (i < fields.size() - 1) {
                sb.append(", ");
            }
        }
        if (StringUtils.isNotBlank(where)) {
            sb.append(" WHERE ").append(where);
        }
        sb.append(";");
        return sb.toString();
    }

    protected Integer getInt(ResultSet rs, String columnLabel) throws SQLException {
        Integer value = rs.getInt(columnLabel);
        if (rs.wasNull()) {
            value = null;
        }
        return value;
    }

    protected Long getLong(ResultSet rs, String columnLabel) throws SQLException {
        Long value = rs.getLong(columnLabel);
        if (rs.wasNull()) {
            value = null;
        }
        return value;
    }

    protected String getString(ResultSet rs, String columnLabel) throws SQLException {
        String value = rs.getString(columnLabel);
        if (rs.wasNull()) {
            value = null;
        }
        return value;
    }

    protected Timestamp getTimestamp(ResultSet rs, String columnLabel) throws SQLException {
        Timestamp value = rs.getTimestamp(columnLabel);
        if (rs.wasNull()) {
            value = null;
        }
        return value;
    }
}
