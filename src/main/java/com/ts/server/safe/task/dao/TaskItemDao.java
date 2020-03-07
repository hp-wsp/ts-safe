package com.ts.server.safe.task.dao;

import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.task.domain.TaskItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ts.server.safe.task.domain.TaskItem.*;

/**
 * 检查内容数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class TaskItemDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<TaskItem> mapper = (r, i) -> {
        TaskItem t = new TaskItem();

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
    public TaskItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(TaskItem t){
        final String sql = "INSERT INTO ck_item (id, task_id, content_id, type_id, type_name, item_id, item_name," +
                "content, con_detail, law_item, update_time, create_time)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getTaskId(), t.getContentId(), t.getTypeId(), t.getTypeName(), t.getItemId(),
                t.getItemName(), t.getContent(), t.getConDetail(), t.getLawItem());
    }

    public boolean update(TaskItem t){
        final String sql = "UPDATE ck_item SET content_id = ?, type_id = ?, type_name = ?, item_id = ?, item_name = ?," +
                "content = ?, con_detail = ?, law_item = ?, update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, t.getContentId(), t.getTypeId(), t.getTypeName(), t.getItemId(), t.getItemName(),
                t.getContent(), t.getConDetail(), t.getLawItem(), t.getId()) > 0;
    }

    public boolean updateResult(String id, TaskItem.CheckResult result, TaskItem.DangerLevel danLevel,
                                String danDesc, String danSuggest, String[] images){

        final String sql = "UPDATE ck_item SET check_result =?, dan_level=?, " +
                "dan_describe = ?, dan_suggest = ?, images = ?, update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, result.name(), danLevel.name(), danDesc, danSuggest, DaoUtils.join(images), id) > 0;
    }

    public boolean has(String taskId, String contentId){
        final String sql = "SELECT COUNT(id) FROM ck_item WHERE task_id = ? AND content_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{taskId, contentId}, Integer.class);
        return count != null && count > 0;
    }

    public TaskItem findOne(String id){
        final String sql = "SELECT * FROM ck_item WHERE id =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public TaskItem findOne(String taskId, String contentId){
        final String sql = "SELECT * FROM ck_item WHERE task_id = ? AND content_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId, contentId}, mapper);
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM ck_item WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public void deleteTask(String taskId){
        final String sql = "DELETE FROM ck_item WHERE task_id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    public List<TaskItem> find(String taskId){
        final String sql = "SELECT * FROM ck_item WHERE task_id = ? ORDER BY update_time ASC";
        return jdbcTemplate.query(sql, new Object[]{taskId}, mapper);
    }
}
