package com.ts.server.safe.base.dao;

import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.base.domain.UniRiskChemical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 统一危化品目录数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class UniRiskChemicalDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<UniRiskChemical> mapper = (r, i) -> {
        UniRiskChemical t = new UniRiskChemical();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setAlias(r.getString("alias"));
        t.setCas(r.getString("cas"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniRiskChemicalDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(
                dataSource, "seq_risk_chemical", e -> String.format("%05d", e));
    }

    public String insert(UniRiskChemical t){
        String id = idGenerator.generate();
        final String sql = "INSERT INTO b_risk_chemistry (id, name, alias, cas, remark, create_time) VALUES (?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, id, t.getName(), t.getAlias(), t.getCas(), t.getRemark());
        return id;
    }

    public boolean update(UniRiskChemical t){
        final String sql = "UPDATE b_risk_chemistry SET name = ?, alias = ?, cas = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getAlias(), t.getCas(), t.getRemark(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM b_risk_chemistry WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public UniRiskChemical findOne(String id){
        final String sql = "SELECT * FROM b_risk_chemistry WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String name, String alias, String cas){
        final String sql = "SELECT COUNT(id) FROM b_risk_chemistry WHERE name LIKE ? AND alias LIKE ? AND cas LIKE ?";
        String nameLike = DaoUtils.like(name);
        String aliasLike = DaoUtils.like(alias);
        String casLike = DaoUtils.like(cas);
        return jdbcTemplate.queryForObject(sql, new Object[]{nameLike, aliasLike, casLike}, Long.class);
    }

    public List<UniRiskChemical> find(String name, String alias, String cas, int offset, int limit){
        final String sql = "SELECT * FROM b_risk_chemistry WHERE name LIKE ? AND alias LIKE ? AND cas LIKE ? " +
                "ORDER BY create_time LIMIT ? OFFSET ?";

        String nameLike = DaoUtils.like(name);
        String aliasLike = DaoUtils.like(alias);
        String casLike = DaoUtils.like(cas);
        return jdbcTemplate.query(sql, new Object[]{nameLike, aliasLike, casLike, limit, offset}, mapper);
    }
}
