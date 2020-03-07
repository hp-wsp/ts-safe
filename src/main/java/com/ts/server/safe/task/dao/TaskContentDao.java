package com.ts.server.safe.task.dao;

import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.task.domain.TaskContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ts.server.safe.task.domain.TaskContent.*;

/**
 * 检查内容数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class TaskContentDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<TaskContent> mapper = (r, i) -> {
        TaskContent t = new TaskContent();

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
        t.setCheckResult(CheckResult.valueOf(r.getString("check_result")));
        t.setDanLevel(DangerLevel.valueOf(r.getString("dan_level")));
        t.setDanDescribe(r.getString("dan_describe"));
        t.setDanSuggest(r.getString("dan_suggest"));
        t.setImages(DaoUtils.toArray(r.getString("images")));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public TaskContentDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(TaskContent t){
        final String sql = "INSERT INTO c_task_content (id, task_id, content_id, type_id, type_name, item_id, item_name," +
                "content, con_detail, law_item, update_time, create_time)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getTaskId(), t.getContentId(), t.getTypeId(), t.getTypeName(), t.getItemId(),
                t.getItemName(), t.getContent(), t.getConDetail(), t.getLawItem());
    }

    public boolean update(TaskContent t){
        final String sql = "UPDATE c_task_content SET content_id = ?, type_id = ?, type_name = ?, item_id = ?, item_name = ?," +
                "content = ?, con_detail = ?, law_item = ?, update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, t.getContentId(), t.getTypeId(), t.getTypeName(), t.getItemId(), t.getItemName(),
                t.getContent(), t.getConDetail(), t.getLawItem(), t.getId()) > 0;
    }

    public boolean updateResult(String id, TaskContent.CheckResult result, TaskContent.DangerLevel danLevel,
                                String danDesc, String danSuggest, String[] images){

        final String sql = "UPDATE c_task_content SET check_result =?, dan_level=?, " +
                "dan_describe = ?, dan_suggest = ?, images = ?, update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, result.name(), danLevel.name(), danDesc, danSuggest, DaoUtils.join(images), id) > 0;
    }

    public boolean has(String taskId, String contentId){
        final String sql = "SELECT COUNT(id) FROM c_task_content WHERE task_id = ? AND content_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{taskId, contentId}, Integer.class);
        return count != null && count > 0;
    }

    public TaskContent findOne(String id){
        final String sql = "SELECT * FROM c_task_content WHERE id =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public TaskContent findOne(String taskId, String contentId){
        final String sql = "SELECT * FROM c_task_content WHERE task_id = ? AND content_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId, contentId}, mapper);
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_task_content WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public void deleteTask(String taskId){
        final String sql = "DELETE FROM c_task_content WHERE task_id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    public List<TaskContent> find(String taskId){
        final String sql = "SELECT * FROM c_task_content WHERE task_id = ? ORDER BY update_time ASC";
        return jdbcTemplate.query(sql, new Object[]{taskId}, mapper);
    }
}
