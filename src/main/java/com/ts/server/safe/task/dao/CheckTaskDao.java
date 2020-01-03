package com.ts.server.safe.task.dao;

import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.task.domain.CheckTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 检查任务数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CheckTaskDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CheckTask> mapper = (r, i) -> {
        CheckTask t = new CheckTask();

        t.setId(r.getString("id"));
        t.setChannelId(r.getString("channel_id"));
        t.setServiceId(r.getString("service_id"));
        t.setServiceName(r.getString("service_name"));
        t.setCompId(r.getString("comp_id"));
        t.setCompName(r.getString("comp_name"));
        t.setCheckTimeFrom(r.getDate("check_time_from"));
        t.setCheckTimeTo(r.getDate("check_time_to"));
        t.setCheckUserIds(DaoUtils.toArray(r.getString("check_user_ids")));
        t.setCheckUserNames(DaoUtils.toArray(r.getString("check_user_names")));
        t.setCheckTableIds(DaoUtils.toArray("check_table_ids"));
        t.setCheckTableNames(DaoUtils.toArray("check_table_names"));
        t.setStatus(CheckTask.Status.valueOf(r.getString("status")));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public CheckTaskDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(CheckTask t){
        final String sql = "INSERT INTO c_check_task (id, channel_id, service_id, service_name, comp_id, comp_name, " +
                "check_time_from, check_time_to, check_user_ids, check_user_names, check_table_ids, " +
                "check_table_names, status, update_time, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getChannelId(), t.getServiceId(), t.getServiceName(), t.getCompId(),
                t.getCompName(), t.getCheckTimeFrom(), t.getCheckTimeTo(), DaoUtils.join(t.getCheckUserIds()),
                DaoUtils.join(t.getCheckUserNames()), DaoUtils.join(t.getCheckTableIds()), DaoUtils.join(t.getCheckTableNames()),
                t.getStatus().name());
    }

    public boolean update(CheckTask t){
        final String sql = "UPDATE c_check_task SET service_id = ?, service_name = ?, comp_id = ?, comp_name = ?," +
                "check_time_from = ?, check_time_to = ?, check_user_ids = ?, check_user_names = ?, check_table_ids = ?," +
                "check_table_names = ?, status = ?, update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, t.getServiceId(), t.getServiceName(), t.getCompId(), t.getCompName(),
                t.getCheckTimeFrom(), t.getCheckTimeTo(), DaoUtils.join(t.getCheckUserIds()),
                DaoUtils.join(t.getCheckUserNames()), DaoUtils.join(t.getCheckTableIds()),
                DaoUtils.join(t.getCheckTableNames()), t.getStatus().name(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_check_task WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public CheckTask findOne(String id){
        final String sql = "SELECT * FROM c_check_task WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean updateStatus(String id, CheckTask.Status status){
        final String sql = "UPDATE c_check_task SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status.name(), id) > 0;
    }

    public Long count(String channelId, String compName, String checkUserName,
                      CheckTask.Status status, Date checkTimeFrom, Date checkTimeTo){

        String sql = "SELECT COUNT(id) FROM c_check_task WHERE channel_id LIKE ? AND comp_name LIKE ?" +
                " AND check_user_name LIKE ? AND status LIKE ? ";
        int len = 4;
        if(checkTimeFrom != null){
            sql = sql + " AND check_time_from < ?";
            ++len;
        }
        if(checkTimeTo != null){
            sql = sql + " AND check_time_to < ? ";
            ++len;
        }

        int index = 0;
        Object[] params = new Object[len];
        params[index++] = DaoUtils.like(channelId);
        params[index++] = DaoUtils.like(compName);
        params[index++] = DaoUtils.like(checkUserName);
        params[index++] = DaoUtils.like(status.name());
        if(checkTimeFrom != null){
            params[index++] =  checkTimeFrom;
        }
        if(checkTimeTo != null){
            params[index] = checkTimeTo;
        }

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public List<CheckTask> query(String channelId, String compName, String checkUserName,
                                 CheckTask.Status status, Date checkTimeFrom, Date checkTimeTo, int offset, int limit){
        String sql = "SELECT * FROM c_check_task WHERE channel_id LIKE ? AND comp_name LIKE ? " +
                "AND check_user_name LIKE ? AND status LIKE ? ";
        int len = 6;
        if(checkTimeFrom != null){
            sql = sql + " AND check_time_from < ?";
            ++len;
        }
        if(checkTimeTo != null){
            sql = sql + " AND check_time_to < ? ";
            ++len;
        }
        sql = sql + "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        int index = 0;
        Object[] params = new Object[len];
        params[index++] = DaoUtils.like(channelId);
        params[index++] = DaoUtils.like(compName);
        params[index++] = DaoUtils.like(checkUserName);
        params[index++] = DaoUtils.like(status.name());
        if(checkTimeFrom != null){
            params[index++] =  checkTimeFrom;
        }
        if(checkTimeTo != null){
            params[index++] = checkTimeTo;
        }
        params[index++] = limit;
        params[index] = offset;

        return jdbcTemplate.query(sql, params, mapper);
    }
}
