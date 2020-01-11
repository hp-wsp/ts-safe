package com.ts.server.safe.task.dao;

import com.ts.server.safe.task.domain.CheckTask;
import com.ts.server.safe.task.domain.CheckTaskOfMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * {@link CheckTaskOfMember}数据操作
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CheckTaskOfMemberDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CheckTaskOfMemberDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<CheckTaskOfMember> mapper = (r, i) -> {
        CheckTaskOfMember t = new CheckTaskOfMember();

        t.setId(r.getInt("id"));
        t.setMemId(r.getString("mem_id"));
        t.setTaskId(r.getString("task_id"));
        t.setStatus(CheckTask.Status.valueOf(r.getString("status")));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    public void insert(CheckTaskOfMember t){
        final String sql = "INSERT INTO c_task_of_member (mem_id, task_id, status, create_time) VALUES (?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getMemId(), t.getTaskId(), t.getStatus().name());
    }

    public void deleteOfTask(String taskId){
        final String sql = "DELETE FROM c_task_of_member WHERE task_id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    public void updateStatus(String taskId, CheckTask.Status status){
        final String sql = "UPDATE c_task_of_member SET status = ? WHERE task_id = ?";
        jdbcTemplate.update(sql, status.name(), taskId);
    }

    public List<CheckTaskOfMember> query(String memId, CheckTask.Status status, int offset, int limit){
        String sql = "SELECT * FROM c_task_of_member WHERE mem_id = ? ";
        if(status != null){
            sql = sql + "AND status = ? ";
        }
        sql = sql + "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        Object[] params = status == null? new Object[3]: new Object[4];
        int index = 0;
        params[index++] = memId;
        if(status != null){
            params[index++] = status;
        }
        params[index++] = limit;
        params[index] = offset;

        return jdbcTemplate.query(sql, params, mapper);
    }
}
