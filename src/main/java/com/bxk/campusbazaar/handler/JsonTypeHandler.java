package com.bxk.campusbazaar.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 作用：
 *   1. 将 Java 对象转换为 JSON 字符串并存储到数据库中
 *   2. 从数据库中读取 JSON 字符串并转换为 Java 对象
 */
public class JsonTypeHandler extends BaseTypeHandler<Object> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将 Java 对象转换为 JSON 字符串并存储到数据库中
     *
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            try {
                String json = objectMapper.writeValueAsString(parameter);  // 将对象转换为 JSON 字符串
                ps.setString(i, json);
            } catch (JsonProcessingException e) {
                throw new SQLException("Error converting object to JSON", e);
            }
        } else {
            ps.setNull(i, jdbcType.TYPE_CODE);
        }
    }

    /**
     * 从数据库中读取 JSON 字符串并转换为 Java 对象
     */
    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        try {
            return json != null ? objectMapper.readValue(json, Map.class) : null;  // 将 JSON 字符串转换为 Java 对象
        } catch (Exception e) {
            throw new SQLException("Error converting JSON to object", e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        try {
            return json != null ? objectMapper.readValue(json, Map.class) : null;
        } catch (Exception e) {
            throw new SQLException("Error converting JSON to object", e);
        }
    }

    @Override
    public Object getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        try {
            return json != null ? objectMapper.readValue(json, Map.class) : null;
        } catch (Exception e) {
            throw new SQLException("Error converting JSON to object", e);
        }
    }
}
