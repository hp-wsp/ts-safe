package com.ts.server.safe.company.dao;

import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.company.domain.SpeEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 特种设备数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class SpeEquipmentDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<SpeEquipment> mapper = (r, i) -> {
        SpeEquipment t = new SpeEquipment();

        t.setId(r.getString("id"));
        t.setCompId(r.getString("comp_id"));
        t.setName(r.getString("name"));
        t.setNum(r.getString("num"));
        t.setDevDanger(r.getString("dev_danger"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public SpeEquipmentDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(dataSource, "seq_spe_equipment", e -> String.format("%07d", e));
    }

    public String insert(SpeEquipment t){
        String id = idGenerator.generate();
        final String sql = "INSERT INTO c_spe_equipment (id, comp_id, name, num, dev_danger, remark, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, id, t.getCompId(), t.getName(), t.getNum(), t.getDevDanger(), t.getRemark());
        return id;
    }

    public boolean update(SpeEquipment t){
        final String sql = "UPDATE c_spe_equipment SET name = ?, num = ?, dev_danger = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getNum(), t.getDevDanger(), t.getRemark(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_spe_equipment WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public SpeEquipment findOne(String id){
        final String sql = "SELECT * FROM c_spe_equipment WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String compId, String name){
        final String sql = "SELECT COUNT(id) FROM c_spe_equipment WHERE comp_id LIKE ? AND name LIKE ?";
        String compIdLike = DaoUtils.blankLike(compId);
        String nameLike = DaoUtils.like(name);
        return jdbcTemplate.queryForObject(sql, new Object[]{compIdLike, nameLike}, Long.class);
    }

    public List<SpeEquipment> find(String compId, String name, int offset, int limit){
        final String sql = "SELECT * FROM c_spe_equipment WHERE comp_id LIKE ? AND name LIKE ? " +
                "ORDER BY create_time ASC LIMIT ? OFFSET ?";

        String compIdLike = DaoUtils.blankLike(compId);
        String nameLike = DaoUtils.like(name);
        return jdbcTemplate.query(sql, new Object[]{compIdLike, nameLike, limit, offset}, mapper);
    }
}
