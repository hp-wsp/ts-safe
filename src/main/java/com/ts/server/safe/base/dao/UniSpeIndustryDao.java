package com.ts.server.safe.base.dao;

import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.base.domain.UniSpeIndustry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 统一特种行业数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class UniSpeIndustryDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<UniSpeIndustry> mapper = (r, i) -> {
        UniSpeIndustry t = new UniSpeIndustry();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniSpeIndustryDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(
                dataSource, "seq_u_spe_industry", e -> String.format("%05d", e));
    }

    public String insert(UniSpeIndustry t){
        String id = idGenerator.generate();
        final String sql = "INSERT INTO b_spe_industry (id, name, remark, create_time) VALUES (?, ?, ?, now())";
        jdbcTemplate.update(sql, id, t.getName(), t.getRemark());
        return id;
    }

    public boolean update(UniSpeIndustry t){
        final String sql = "UPDATE b_spe_industry SET name = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getRemark(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM b_spe_industry WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public UniSpeIndustry findOne(String id){
        final String sql = "SELECT * FROM b_spe_industry WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String name){
        final String sql = "SELECT COUNT(id) FROM b_spe_industry WHERE name LIKE ?";
        String nameLike = DaoUtils.like(name);
        return jdbcTemplate.queryForObject(sql, new Object[]{nameLike}, Long.class);
    }

    public List<UniSpeIndustry> find(String name, int offset, int limit){
        final String sql = "SELECT * FROM b_spe_industry WHERE name LIKE ? ORDER BY create_time LIMIT ? OFFSET ?";
        String nameLike = DaoUtils.like(name);
        return jdbcTemplate.query(sql, new Object[]{nameLike, limit, offset},mapper);
    }
}
