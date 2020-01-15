package com.ts.server.safe.company.dao;

import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.company.domain.SpePerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 特殊作业人员和证书数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SpePersonDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<SpePerson> mapper = (r, i) -> {
        SpePerson t = new SpePerson();

        t.setId(r.getString("id"));
        t.setCompId(r.getString("comp_id"));
        t.setName(r.getString("name"));
        t.setNum(r.getString("num"));
        t.setType(r.getString("type"));
        t.setFromDate(r.getString("from_date"));
        t.setToDate(r.getString("to_date"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return  t;
    };

    @Autowired
    public SpePersonDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(dataSource, "seq_spe_person", e -> String.format("%07d", e));
    }

    public String insert(SpePerson t){
        final String id = idGenerator.generate();
        final String sql = "INSERT INTO c_spe_person (id, comp_id, name, num, type, from_date, to_date, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, id, t.getCompId(), t.getName(), t.getNum(), t.getType(), t.getFromDate(), t.getToDate());
        return id;
    }

    public boolean update(SpePerson t){
        final String sql = "UPDATE c_spe_person SET name = ?, num = ?, type = ?, from_date = ?, to_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getNum(), t.getType(), t.getFromDate(), t.getToDate(), t.getId()) > 0;
    }

    public SpePerson findOne(String id){
        final String sql = "SELECT * FROM c_spe_person WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_spe_person WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Long count(String compId, String name, String type){
        final String sql = "SELECT COUNT(id) FROM c_spe_person WHERE comp_id LIKE ? AND name LIKE ? AND type LIKE ?";

        final String compIdLike = DaoUtils.blankLike(compId);
        final String nameLike = DaoUtils.like(name);
        final String typeLike = DaoUtils.like(type);

        return jdbcTemplate.queryForObject(sql, new Object[]{compIdLike, nameLike, typeLike}, Long.class);
    }

    public List<SpePerson> find(String compId, String name, String type, int offset, int limit){
        final String sql = "SELECT * FROM c_spe_person WHERE comp_id LIKE ? AND name LIKE ? AND type LIKE ? " +
                "ORDER BY create_time LIMIT ? OFFSET ?";

        final String compIdLike = DaoUtils.blankLike(compId);
        final String nameLike = DaoUtils.like(name);
        final String typeLike = DaoUtils.like(type);

        return jdbcTemplate.query(sql, new Object[]{compIdLike, nameLike, typeLike, limit, offset}, mapper);
    }
}
