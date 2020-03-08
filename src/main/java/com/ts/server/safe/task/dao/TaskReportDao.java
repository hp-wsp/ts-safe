package com.ts.server.safe.task.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.task.domain.TaskReport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 检查报表数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class TaskReportDao<T> {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final RowMapper<TaskReport<T>> mapper;

    public TaskReportDao(DataSource dataSource, ObjectMapper objectMapper, Class<T> contentType) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.objectMapper = objectMapper;
        this.mapper = new ReportMapper<>(objectMapper, contentType);
    }

    public void insert(TaskReport<T> t){
        final String sql = "INSERT INTO ck_report (id, channel_id, service_id, service_name, comp_id, " +
                "comp_name, task_id, is_initial, content, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getChannelId(), t.getServiceId(), t.getServiceId(), t.getCompId(),
                t.getCompName(), t.getTaskId(), t.isInitial(), toJson(t.getContent()));
    }

    private String toJson(Object object){
        try{
            return Objects.isNull(object)? "{}": objectMapper.writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException("Object to json fail");
        }
    }

    public boolean updateContent(String taskId, T content){
        final String sql = "UPDATE ck_report SET content = ?, update_time = now() WHERE task_id = ?";
        return jdbcTemplate.update(sql, toJson(content), taskId) > 0;
    }

    public boolean has(String taskId){
        final String sql = "SELECT COUNT(id) FROM ch_report WHERE task_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{taskId}, Integer.class);
        return count != null && count > 0;
    }

    public TaskReport<T> findOne(String taskId){
        final String sql = "SELECT *  FROM ck_report WHERE task_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId}, mapper);
    }

    public boolean delete(String taskId){
        final String sql = "DELETE FROM ck_report WHERE id = ?";
        return jdbcTemplate.update(sql, taskId) > 0;
    }

    private static class ReportMapper<T> implements RowMapper<TaskReport<T>>{
        private final ObjectMapper objectMapper;
        private final Class<T> contentType;

        public ReportMapper(ObjectMapper objectMapper, Class<T> contentType) {
            this.objectMapper = objectMapper;
            this.contentType = contentType;
        }

        @Override
        public TaskReport<T> mapRow(ResultSet r, int i) throws SQLException {
            TaskReport<T> t = new TaskReport<>();

            t.setId(r.getString("id"));
            t.setChannelId(r.getString("channel_id"));
            t.setServiceId(r.getString("service_id"));
            t.setServiceName(r.getString("service_name"));
            t.setCompId(r.getString("comp_id"));
            t.setCompName(r.getString("comp_name"));
            t.setTaskId(r.getString("task_id"));
            t.setInitial(r.getBoolean("is_initial"));
            t.setContent(toContent(r.getString("content")));
            t.setUpdateTime(r.getTimestamp("update_time"));
            t.setCreateTime(r.getTimestamp("create_time"));

            return t;
        }

        private T toContent(String v){
            if(StringUtils.isBlank(v)){
                return null;
            }
            try{
                return objectMapper.readValue(v, contentType);
            }catch (Exception e){
                throw new RuntimeException("Json to report content fail");
            }
        }
    }
}
