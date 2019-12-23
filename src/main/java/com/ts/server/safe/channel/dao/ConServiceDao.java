package com.ts.server.safe.channel.dao;

import com.ts.server.safe.channel.domain.ConService;
import com.ts.server.safe.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 合同服务数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ConServiceDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ConService> mapper = (r, i) -> {
        ConService t = new ConService();

        t.setId(r.getString("id"));
        t.setChannelId(r.getString("channel_id"));
        t.setName(r.getString("name"));
        t.setConId(r.getString("con_id"));
        t.setConName(r.getString("con_name"));
        t.setCompId(r.getString("comp_id"));
        t.setCompName(r.getString("comp_name"));
        t.setLeaId(r.getString("lea_id"));
        t.setLeaName(r.getString("lea_name"));
        t.setStatus(ConService.Status.valueOf(r.getString("status")));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ConServiceDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ConService t){
        final String sql = "INSERT INTO c_con_service (id, channel_id, name, con_id, con_name, comp_id, comp_name," +
                "lea_id, lea_name, status, update_create, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getChannelId(), t.getName(), t.getConId(), t.getConName(), t.getCompId(),
                t.getCompName(), t.getLeaId(), t.getLeaName(), t.getStatus().name());
    }

    public boolean update(ConService t){
        final String sql = "UPDATE c_con_service SET name = ?, con_id = ?, con_name = ?, comp_id = ?, comp_name = ?," +
                "lea_id = ?, lea_name = ?, update_time = now() WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getConId(), t.getConName(), t.getCompId(), t.getCompName(),
                t.getLeaId(), t.getLeaName(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_con_service WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean updateStatus(String id, ConService.Status status){
        final String sql = "UPDATE c_con_service SET status = ? WHERE id";
        return jdbcTemplate.update(sql, status.name(), id) > 0;
    }

    public ConService findOne(String id){
        final String sql = "SELECT * FROM c_con_service WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String channelId, String name, String compName, ConService.Status status){
        final String sql = "SELECT COUNT(id) FROM c_con_service WHERE channel_id LIKE ? " +
                "AND name LIKE ? AND comp_name LIKE ? AND status LIKE ?";

        Object[] params = buildParams(channelId, name, compName, status);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public List<ConService> find(String channelId, String name, String compName, ConService.Status status,
                                 int offset, int limit){
        final String sql = "SELECT * FROM c_con_service WHERE channel_id LIKE ? " +
                "AND name LIKE ? AND comp_name LIKE ? AND status LIKE ? " +
                "ORDER BY update_time DESC, create_time DESC LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParams(channelId, name, compName, status), offset, limit);

        return jdbcTemplate.query(sql, params, mapper);
    }

    private Object[] buildParams(String channelId, String name, String compName, ConService.Status status){
        final Object[] params = new Object[4];

        params[0] = DaoUtils.blankLike(channelId);
        params[1] = DaoUtils.like(name);
        params[2] = DaoUtils.like(compName);
        params[3] = status == null? "%" : status.name();

        return params;
    }
}
