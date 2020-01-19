package com.ts.server.safe.task.dao;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.task.domain.CheckTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 检查任务数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CheckTaskDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final IdGenerator<String> numIdGenerator;
    private final ObjectMapper objectMapper;
    private final RowMapper<CheckTask> mapper;

    @Autowired
    public CheckTaskDao(DataSource dataSource, ObjectMapper objectMapper){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.objectMapper = objectMapper;
        this.mapper = new CheckTaskMapper(objectMapper);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.numIdGenerator = IdGenerators.seqId(dataSource, "seq_check_task_num",
                e -> String.format("%05d", (e % 100000)));
    }

    public String genNum(){
        return numIdGenerator.generate();
    }

    public void insert(CheckTask t){
        final String sql = "INSERT INTO c_check_task (id, num, channel_id, service_id, service_name, comp_id, comp_name, " +
                "check_time_from, check_time_to, check_users, check_ind_ctgs, is_review, status, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getNum(), t.getChannelId(), t.getServiceId(), t.getServiceName(),
                t.getCompId(), t.getCompName(), t.getCheckTimeFrom(), t.getCheckTimeTo(),
                toJson(t.getCheckUsers()), toJson(t.getCheckIndCtgs()), t.isReview(), t.getStatus().name());
    }

    private String toJson(List<?> values){
        try{
            return values == null || values.isEmpty()? "{}": objectMapper.writeValueAsString(values);
        }catch (Exception e){
            throw new RuntimeException("Object to json fail");
        }
    }

    public boolean update(CheckTask t){
        final String sql = "UPDATE c_check_task SET service_id = ?, service_name = ?, comp_id = ?, comp_name = ?," +
                "check_time_from = ?, check_time_to = ?, check_users = ?, check_ind_ctgs = ?, is_review = ?, " +
                "update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, t.getServiceId(), t.getServiceName(), t.getCompId(), t.getCompName(),
                t.getCheckTimeFrom(), t.getCheckTimeTo(), toJson(t.getCheckUsers()), toJson(t.getCheckIndCtgs()),
                t.isReview(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_check_task WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public CheckTask findOne(String id){
        final String sql = "SELECT * FROM c_check_task WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Optional<CheckTask> findLast(String serviceId){
        final String sql = "SELECT * FROM c_check_task WHERE service_id = ? ORDER BY create_time DESC";
        List<CheckTask> tasks = jdbcTemplate.query(sql, new Object[]{serviceId}, mapper);
        return tasks.isEmpty()? Optional.empty(): Optional.of(tasks.get(0));
    }

    public boolean updateStatus(String id, CheckTask.Status status){
        final String sql = "UPDATE c_check_task SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status.name(), id) > 0;
    }

    public List<CheckTask> findByServiceId(String serviceId){
        final String sql = "SELECT * FROM c_check_task WHERE service_id = ? ORDER BY create_time DESC";
        return jdbcTemplate.query(sql, new Object[]{serviceId}, mapper);
    }

    public Long count(String channelId, String compName, CheckTask.Status status, Date checkTimeFrom, Date checkTimeTo){

        String sql = "SELECT COUNT(id) FROM c_check_task WHERE channel_id LIKE ? AND comp_name LIKE ? ";
        int len = 2;
        if(checkTimeFrom != null){
            sql = sql + " AND check_time_from < ?";
            ++len;
        }
        if(checkTimeTo != null){
            sql = sql + " AND check_time_to < ? ";
            ++len;
        }
        if(status != null){
            sql= sql + " AND status = ? ";
        }

        int index = 0;
        Object[] params = new Object[len];
        params[index++] = DaoUtils.blankLike(channelId);
        params[index++] = DaoUtils.like(compName);
        if(checkTimeFrom != null){
            params[index++] =  checkTimeFrom;
        }
        if(checkTimeFrom != null){
            params[index++] =  checkTimeFrom;
        }
        if(checkTimeTo != null){
            params[index] = checkTimeTo;
        }

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public List<CheckTask> find(String channelId, String compName, CheckTask.Status status,
                                Date checkTimeFrom, Date checkTimeTo, int offset, int limit){
        String sql = "SELECT * FROM c_check_task WHERE channel_id LIKE ? AND comp_name LIKE ? ";
        int len = 4;
        if(checkTimeFrom != null){
            sql = sql + " AND check_time_from < ?";
            ++len;
        }
        if(checkTimeTo != null){
            sql = sql + " AND check_time_to < ? ";
            ++len;
        }
        if(status != null){
            sql= sql + " AND status = ? ";
        }
        sql = sql + "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        int index = 0;
        Object[] params = new Object[len];
        params[index++] = DaoUtils.blankLike(channelId);
        params[index++] = DaoUtils.like(compName);
        if(status!= null){
            params[index++] = status.name();
        }
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

    public List<CheckTask> findIn(List<String> ids){
        final String sql = "SELECT * FROM c_check_task WHERE id IN (:ids)";
        Map<String, List<String>> params = new LinkedHashMap<>(1,1);
        params.put("ids", ids);
        return namedJdbcTemplate.query(sql, params, mapper);
    }

    static class CheckTaskMapper implements RowMapper<CheckTask>{
        private final ObjectMapper objectMapper;
        private final JavaType usersType;
        private final JavaType indCtgsType;

        CheckTaskMapper(ObjectMapper objectMapper){
            this.objectMapper = objectMapper;
            this.usersType = objectMapper.getTypeFactory().constructParametricType(List.class, CheckTask.CheckUser.class);
            this.indCtgsType = objectMapper.getTypeFactory().constructParametricType(List.class, CheckTask.CheckIndCtg.class);
        }

        @Override
        public CheckTask mapRow(ResultSet r, int i) throws SQLException {
            CheckTask t = new CheckTask();

            t.setId(r.getString("id"));
            t.setNum(r.getString("num"));
            t.setChannelId(r.getString("channel_id"));
            t.setServiceId(r.getString("service_id"));
            t.setServiceName(r.getString("service_name"));
            t.setCompId(r.getString("comp_id"));
            t.setCompName(r.getString("comp_name"));
            t.setCheckTimeFrom(r.getDate("check_time_from"));
            t.setCheckTimeTo(r.getDate("check_time_to"));
            t.setCheckUsers(toObject(r.getString("check_users"), usersType));
            t.setCheckIndCtgs(toObject(r.getString("check_ind_ctgs"), indCtgsType));
            t.setReview(r.getBoolean("is_review"));
            t.setStatus(CheckTask.Status.valueOf(r.getString("status")));
            t.setUpdateTime(r.getTimestamp("update_time"));
            t.setCreateTime(r.getTimestamp("create_time"));

            return t;
        }

        private <T> List<T> toObject(String v, JavaType javaType){
            try{
                return objectMapper.readValue(v, javaType);
            }catch (Exception e){
                throw new RuntimeException("To object fail value " + v);
            }
        }
    }
}
