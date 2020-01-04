package com.ts.server.safe.task.dao;

import com.ts.server.safe.task.domain.CheckContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 检查内容数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CheckContentDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CheckContent> mapper = (r, i) -> {
        CheckContent t = new CheckContent();

        t.setId(r.getString("id"));
        t.setTaskId(r.getString("task_id"));
        t.setContentId(r.getString("content_id"));
        t.setTypeId(r.getString("type_id"));
        t.setTypeName(r.getString("type_name"));
        t.setItemId(r.getString("item_id"));
        t.setItemName(r.getString("item_name"));
        t.setContent(r.getString("content"));
        t.setConDetail(r.getString("con_detail"));
        t.setLawItem(r.getString("law_item"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public CheckContentDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(CheckContent t){
        final String sql = "INSERT INTO c_check_content (id, content_id, table_id, type_id, type_name, item_id, item_name," +
                "content, con_detail, law_item, update_time, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getTaskId(), t.getContentId(), t.getTypeId(), t.getTypeName(), t.getItemId(),
                t.getItemName(), t.getContent(), t.getConDetail(), t.getLawItem());
    }

    public boolean update(CheckContent t){
        final String sql = "UPDATE c_check_content SET content_id = ?, type_id = ?, type_name = ?, item_id = ?, item_name = ?," +
                "content = ?, con_detail = ?, law_item = ?, update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, t.getContentId(), t.getTypeId(), t.getTypeName(), t.getItemId(), t.getItemName(),
                t.getContent(), t.getConDetail(), t.getLawItem(), t.getId()) > 0;
    }

    public boolean has(String taskId, String cotentId){
        final String sql = "SELECT COUNT(id) FROM c_check_content WHERE task_id = ? AND content_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{taskId, cotentId}, Integer.class);
        return count != null && count > 0;
    }

    public CheckContent findOne(String id){
        final String sql = "SELECT * FROM c_check_content WHERE id =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public CheckContent findOne(String taskId, String contentId){
        final String sql = "SELECT * FROM c_check_content WHERE task_id = ? AND content_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId, contentId}, mapper);
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_check_content WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public void deleteTask(String taskId){
        final String sql = "DELETE FROM c_check_content WHERE task_id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    public List<CheckContent> find(String taskId){
        final String sql = "SELECT * FROM c_check_content WHERE task_id = ? ORDER BY table_id ASC";
        return jdbcTemplate.query(sql, new Object[]{taskId}, mapper);
    }
}
