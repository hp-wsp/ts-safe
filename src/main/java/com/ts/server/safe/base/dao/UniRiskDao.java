package com.ts.server.safe.base.dao;

import com.ts.server.safe.base.domain.UniRisk;
import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.SeqGenerator;
import com.ts.server.safe.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 风险识别数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class UniRiskDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<UniRisk> mapper = (r, i) -> {
        UniRisk t = new UniRisk();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniRiskDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = new SeqGenerator<>(dataSource, "seq_u_risk", i -> String.format("%03d", i));
    }

    public String insert(UniRisk t){
        final String sql = "INSERT INTO b_risk (id, name, remark, create_time) VALUES (?, ?, ?, now())";
        String id = idGenerator.generate();
        jdbcTemplate.update(sql, id, t.getName(), t.getRemark());
        return id;
    }

    public boolean update(UniRisk t){
        final String sql = "UPDATE b_risk SET name = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getRemark(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM b_risk WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public UniRisk findOne(String id){
        final String sql = "SELECT * FROM b_risk WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String name){
        String nameLike = DaoUtils.like(name);
        final String sql = "SELECT COUNT(id) FROM b_risk WHERE name LIKE ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{nameLike}, Long.class);
    }

    public List<UniRisk> find(String name, int offset, int limit){
        String nameLike = DaoUtils.like(name);
        final String sql = "SELECT * FROM b_risk WHERE name LIKE ? ORDER BY id ASC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{nameLike, limit, offset}, mapper);
    }
}
