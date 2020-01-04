package com.ts.server.safe.base.dao;

import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.base.domain.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 行政区域数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class DistrictDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<District> mapper = (r, i) -> {
        District t = new District();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setFullName(r.getString("full_name"));
        t.setLocation(DaoUtils.toArray(r.getString("location")));
        t.setParentId(r.getString("parent_id"));
        t.setLevel(r.getInt("level"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public DistrictDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(District t){
        final String sql = "INSERT INTO b_district (id, name, full_name, location, parent_id, level, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getId(), t.getName(), t.getFullName(), DaoUtils.join(t.getLocation()), t.getParentId(), t.getLevel());
    }

    public boolean update(District t){
        final String sql = "UPDATE b_district name = ?, full_name = ?, location = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getFullName(), DaoUtils.join(t.getLocation()), t.getId()) > 0;
    }

    public boolean has(String id){
        final String sql = "SELECT COUNT(id) FROM b_district WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    public List<District> find(String parentId){
        final String sql = "SELECT * FROM b_district WHERE parent_id = ? ORDER BY create_time";
        return jdbcTemplate.query(sql, new Object[]{parentId}, mapper);
    }
}
