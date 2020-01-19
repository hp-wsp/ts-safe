package com.ts.server.safe.company.dao;

import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.company.domain.RiskChemical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 危化品存储数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class RiskChemicalDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<RiskChemical> mapper = (r, i) -> {
        RiskChemical t = new RiskChemical();

        t.setId(r.getString("id"));
        t.setCompId(r.getString("comp_id"));
        t.setName(r.getString("name"));
        t.setCas(r.getString("cas"));
        t.setAlias(r.getString("alias"));
        t.setMaxStore(r.getString("max_store"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public RiskChemicalDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(
                dataSource, "seq_risk_chemistry", e -> String.format("%07d", e));
    }

    public String insert(RiskChemical t){
        String id = idGenerator.generate();
        final String sql = "INSERT INTO c_risk_chemistry (id, comp_id, name, cas, alias, max_store, remark, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, id, t.getCompId(), t.getName(), t.getCas(), t.getAlias(), t.getMaxStore(), t.getRemark());
        return id;
    }

    public boolean update(RiskChemical t){
        final String sql = "UPDATE c_risk_chemistry SET name = ?, cas = ?, alias = ?, max_store = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getCas(), t.getAlias(), t.getMaxStore(), t.getRemark(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_risk_chemistry WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public RiskChemical findOne(String id){
        final String sql = "SELECT * FROM c_risk_chemistry WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public List<RiskChemical> findByCompId(String compId){
        final String sql = "SELECT * FROM c_risk_chemistry WHERE comp_id = ? ORDER BY create_time ASC";
        return jdbcTemplate.query(sql, new Object[]{compId}, mapper);
    }

    public Long count(String compId, String name, String cas){
        final String sql = "SELECT COUNT(id) FROM c_risk_chemistry WHERE comp_id LIKE ? AND name LIKE ? AND cas LIKE ?";

        String compIdLike = DaoUtils.blankLike(compId);
        String nameLike = DaoUtils.like(name);
        String casLike = DaoUtils.like(cas);

        return jdbcTemplate.queryForObject(sql, new Object[]{compIdLike, nameLike, casLike}, Long.class);
    }

    public List<RiskChemical> find(String compId, String name, String cas, int offset, int limit){
        final String sql = "SELECT * FROM c_risk_chemistry WHERE comp_id LIKE ? AND name LIKE ? AND cas LIKE ? " +
                "ORDER BY create_time ASC LIMIT ? OFFSET ?";

        String compIdLike = DaoUtils.blankLike(compId);
        String nameLike = DaoUtils.like(name);
        String casLike = DaoUtils.like(cas);

        return jdbcTemplate.query(sql, new Object[]{compIdLike, nameLike, casLike, limit, offset}, mapper);
    }

}
