package com.ts.server.safe.channel.dao;

import com.ts.server.safe.channel.domain.Channel;
import com.ts.server.safe.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 单位数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ChannelDao {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Channel> mapper = (r, i) -> {
        Channel t = new Channel();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setProvince(r.getString("province"));
        t.setCity(r.getString("city"));
        t.setAddress(r.getString("address"));
        t.setRegAddress(r.getString("reg_address"));
        t.setPhone(r.getString("phone"));
        t.setMobile(r.getString("mobile"));
        t.setContact(r.getString("contact"));
        t.setBusScope(r.getString("bus_scope"));
        t.setStatus(Channel.Status.valueOf(r.getString("status")));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ChannelDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Channel t){
        final String sql = "INSERT INTO c_channel (id, name, province, city, address, reg_address, phone, " +
                "mobile, contact, bus_scope status, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getName(), t.getProvince(), t.getCity(), t.getAddress(), t.getRegAddress(),
                t.getPhone(), t.getMobile(), t.getContact(), t.getBusScope(), t.getStatus().name());
    }

    public boolean update(Channel t){
        final String sql = "UPDATE c_channel SET name = ?, province = ?, city = ?, address = ?, reg_address = ?, " +
                "phone = ?, mobile = ?, contact = ?, bus_scope = ?, update_time = now() " +
                "WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, t.getName(), t.getProvince(), t.getCity(), t.getAddress(), t.getRegAddress(),
                t.getPhone(), t.getMobile(), t.getContact(), t.getBusScope(), t.getId()) > 0;
    }

    public boolean updateStatus(String id, Channel.Status status){
        final String sql = "UPDATE c_channel SET status = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, status.name(), id) <= 0;
    }

    public boolean has(String id){
        final String sql = "SELECT COUNT(id) FROM c_channel WHERE id = ? AND is_delete = false";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    public Channel findOne(String id){
        final String sql = "SELECT * FROM c_channel WHERE id =? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql = "UPDATE c_channel SET is_delete = true WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Long count(String name, Channel.Status status){
        final String sql = "SELECT COUNT(id) FROM c_channel WHERE name LIKE ? AND status LIKE ? AND is_delete = false";

        String nameLike = DaoUtils.like(name);
        String statusLike = status ==null? "%": status.name();
        return jdbcTemplate.queryForObject(sql, new Object[]{nameLike, statusLike}, Long.class);
    }

    public List<Channel> find(String name, Channel.Status status, int offset, int limit){
        final String sql = "SELECT * FROM c_channel WHERE name LIKE ? AND status LIKE ? AND is_delete = false " +
                "ORDER BY create_time ASC LIMIT ? OFFSET ?";

        String nameLike = DaoUtils.like(name);
        String statusLike = status ==null? "%": status.name();
        return jdbcTemplate.query(sql, new Object[]{nameLike, statusLike, limit, offset}, mapper);
    }
}
