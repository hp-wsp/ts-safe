package com.ts.server.safe.base.dao;

import com.ts.server.safe.base.domain.UniCheckType;
import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 检查类型数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class UniCheckTypeDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<UniCheckType> mapper = (r, i) -> {
        UniCheckType t = new UniCheckType();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniCheckTypeDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(
                dataSource, "seq_u_check_type", e -> String.format("%03d", e));
    }

    public String insert(UniCheckType t){
        String id = idGenerator.generate();
        final String sql = "INSERT INTO b_check_type (id, name, remark, create_time) VALUES (?, ?, ?, now())";
        jdbcTemplate.update(sql, id, t.getName(), t.getRemark());
        return id;
    }

    public boolean update(UniCheckType t){
        final String sql = "UPDATE b_check_type SET name = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getRemark(), t.getId()) > 0;
    }

    public UniCheckType findOne(String id){
        final String sql = "SELECT * FROM b_check_type WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM b_check_type WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Long count(String name){
        final String sql = "SELECT COUNT(id) FROM b_check_type WHERE name LIKE ?";
        String nameLike = DaoUtils.like(name);
        return jdbcTemplate.queryForObject(sql, new Object[]{nameLike}, Long.class);
    }

    public List<UniCheckType> find(String name, int offset, int limit){
        final String sql = "SELECT * FROM b_check_type WHERE name LIKE ? ORDER BY id ASC LIMIT ? OFFSET ?";
        String nameLike = DaoUtils.like(name);
        return jdbcTemplate.query(sql, new Object[]{nameLike, limit, offset}, mapper);
    }

    public List<UniCheckType> findAll(){
        final String sql = "SELECT * FROM b_check_type ORDER BY id ASC";
        return jdbcTemplate.query(sql, mapper);
    }
}
