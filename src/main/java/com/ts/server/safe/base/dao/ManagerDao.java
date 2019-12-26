package com.ts.server.safe.base.dao;

import com.ts.server.safe.base.domain.Manager;
import com.ts.server.safe.common.utils.DaoUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 管理员数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ManagerDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Manager> mapper= (r, i) -> {
        Manager t = new Manager();

        t.setId(r.getString("id"));
        t.setUsername(r.getString("username"));
        t.setName(r.getString("name"));
        t.setPhone(r.getString("phone"));
        t.setEmail(r.getString("email"));
        t.setPassword(r.getString("password"));
        t.setRoles(DaoUtils.toArray(r.getString("roles")));
        t.setForbid(r.getBoolean("is_forbid"));
        t.setRoot(r.getBoolean("is_root"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ManagerDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Manager t){
        final String sql = "INSERT INTO b_manager (id, username, name, phone, email, password, roles, " +
                "is_forbid, is_root, update_time, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getUsername(), t.getName(), t.getPhone(), t.getEmail(),
                t.getPassword(), DaoUtils.join(t.getRoles()), t.isForbid(), false);
    }

    public boolean update(Manager t){
        final String sql = "UPDATE b_manager SET name = ?, phone = ?, email = ?, roles = ?, is_forbid = ?, update_time = now() " +
                "WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, t.getName(), t.getPhone(), t.getEmail(), DaoUtils.join(t.getRoles()), t.isForbid(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "UPDATE b_manager SET username = CONCAT(username,'@', ?), is_delete = true, update_time = now() WHERE id = ?";
        String random = RandomStringUtils.randomAscii(5);
        return jdbcTemplate.update(sql, random, id) > 0;
    }

    public boolean updatePassword(String id, String password){
        final String sql = "UPDATE b_manager SET password = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, password, id) > 0;
    }

    public Manager findOne(String id){
        final String sql = "SELECT * FROM b_manager WHERE id = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Manager findOneByUsername(String username){
        final String sql = "SELECT * FROM b_manager WHERE username = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, mapper);
    }

    public boolean hasUsername(String username){
        final String sql = "SELECT COUNT(id) FROM b_manager WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }

    public Long count(String username){
        final String sql = "SELECT COUNT(id) FROM b_manager WHERE username LIKE ? AND is_delete = false";

        return jdbcTemplate.queryForObject(sql, new Object[]{DaoUtils.like(username)}, Long.class);
    }

    public List<Manager> find(String username, int offset, int limit){
        final String sql = "SELECT * FROM b_manager WHERE username LIKE ? AND is_delete = false " +
                "ORDER BY create_time LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new Object[]{DaoUtils.like(username), limit, offset}, mapper);
    }
}
