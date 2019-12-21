package com.ts.server.safe.logger.dao;

import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.logger.domain.OptLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * 操作日志数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class OptLogDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<OptLog> mapper = (r, i) -> {
        OptLog  t = new OptLog();

        t.setId(r.getLong("id"));
        t.setName(r.getString("t_name"));
        t.setType(r.getString("t_type"));
        t.setDetail(r.getString("detail"));
        t.setUsername(r.getString("username"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public OptLogDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(OptLog t){
        final String sql = "INSERT INTO l_operator (t_name, detail, t_type, username, create_time) VALUES (?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getName(), t.getDetail(), t.getType(), t.getUsername());
    }

    public Long count(String type, String name, String detail, String username, Date fromDate, Date toDate){
        String typeLike = DaoUtils.blankLike(type);
        String nameLike = DaoUtils.like(name);
        String detailLike = DaoUtils.like(detail);
        String usernameLike = DaoUtils.blankLike(username);

        final String sql = "SELECT COUNT(id) FROM l_operator " +
                "WHERE t_type LIKE ? AND t_name LIKE ? AND detail LIKE ? AND username LIKE ? AND create_time BETWEEN ? AND ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{typeLike, nameLike, detailLike, usernameLike, fromDate, toDate}, Long.class);
    }

    public List<OptLog> find(String type, String name, String detail, String username, Date fromDate, Date toDate, int offset, int limit){
        String typeLike = DaoUtils.blankLike(type);
        String nameLike = DaoUtils.like(name);
        String detailLike = DaoUtils.like(detail);
        String usernameLike = DaoUtils.blankLike(username);

        final String sql = "SELECT * FROM l_operator " +
                "WHERE t_type LIKE ? AND t_name LIKE ? AND detail LIKE ? AND username LIKE ? AND create_time BETWEEN ? AND ? " +
                "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new Object[]{typeLike, nameLike, detailLike, usernameLike, fromDate, toDate, limit, offset}, mapper);
    }
}
