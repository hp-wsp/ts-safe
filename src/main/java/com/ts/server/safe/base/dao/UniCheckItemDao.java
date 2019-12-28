package com.ts.server.safe.base.dao;

import com.ts.server.safe.base.domain.UniCheckItem;
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
 * 检查项目数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class UniCheckItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<UniCheckItem> mapper = (r, i) -> {
        UniCheckItem t = new UniCheckItem();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setTypeId(r.getString("type_id"));
        t.setTypeName(r.getString("type_name"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniCheckItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(
                dataSource, "seq_check_item", e -> String.format("%03d", e));
    }

    public String insert(UniCheckItem t){
        String id = idGenerator.generate();
        final String sql = "INSERT INTO b_check_item (id, type_id, type_name, name, remark, create_time) " +
                "VALUES (?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, id, t.getTypeId(), t.getTypeName(), t.getName(), t.getRemark());
        return id;
    }

    public boolean update(UniCheckItem t){
        final String sql = "UPDATE b_check_item SET type_id = ?, type_name = ?, name = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getTypeId(), t.getTypeName(), t.getName(), t.getRemark(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM b_check_item WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean hasType(String typeId){
        final String sql = "SELECT COUNT(id) FROM b_check_item WHERE type_id = ?";
        Integer count =  jdbcTemplate.queryForObject(sql, new Object[]{typeId}, Integer.class);
        return count!= null && count > 0;
    }

    public boolean has(String id){
        final String sql = "SELECT COUNT(id) FROM b_check_item WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    public UniCheckItem findOne(String id){
        final String sql = "SELECT * FROM b_check_item WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String typeName, String name){
        final String sql = "SELECT COUNT(id) FROM b_check_item WHERE type_name LIKE ? AND name LIKE ?";

        String typeNameLike = DaoUtils.blankLike(typeName);
        String nameLike = DaoUtils.like(name);

        return jdbcTemplate.queryForObject(sql, new Object[]{typeNameLike, nameLike}, Long.class);
    }

    public List<UniCheckItem> find(String typeName, String name, int offset, int limit){
        final String sql = "SELECT * FROM b_check_item WHERE type_name LIKE ? AND name LIKE ? " +
                "ORDER BY create_time ASC LIMIT ? OFFSET ?";

        String typeNameLike = DaoUtils.blankLike(typeName);
        String nameLike = DaoUtils.like(name);

        return jdbcTemplate.query(sql, new Object[]{typeNameLike, nameLike, limit, offset}, mapper);
    }

    public List<UniCheckItem> findOfType(String typeId){
        final String sql = "SELECT * FROM b_check_item WHERE type_id = ? ORDER BY id ASC";
        return jdbcTemplate.query(sql, new Object[]{typeId}, mapper);
    }
}
