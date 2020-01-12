package com.ts.server.safe.base.dao;

import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.base.domain.UniIndCtg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 统一监管分类数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class UniIndCtgDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<UniIndCtg> mapper = (r, i) -> {
        UniIndCtg t = new UniIndCtg();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setNum(r.getString("num"));
        t.setLevel(r.getInt("level"));
        t.setParentId(r.getString("parent_id"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniIndCtgDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(
                dataSource, "seq_u_ind_ctg", e -> String.format("%05d", e));
    }

    public String insert(UniIndCtg t){
        String id = idGenerator.generate();
        final String sql = "INSERT INTO b_ind_ctg (id, name, num, level, parent_id, remark, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, id, t.getName(), t.getNum(), t.getLevel(), t.getParentId(), t.getRemark());
        return id;
    }

    public boolean update(UniIndCtg t){
        final String sql = "UPDATE b_ind_ctg SET name = ?, num = ?, level = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getNum(), t.getLevel(), t.getRemark(), t.getId()) >0;
    }

    public boolean delete (String id){
        final String sql = "DELETE FROM b_ind_ctg WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean has(String id){
        final String sql = "SELECT COUNT(id) FROM b_ind_ctg WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    public UniIndCtg findOne(String id){
        final String sql = "SELECT * FROM b_ind_ctg WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public List<UniIndCtg> findChildren(String parentId){
        final String sql = "SELECT * FROM b_ind_ctg WHERE parent_id = ? ORDER BY create_time";
        return jdbcTemplate.query(sql, new Object[]{parentId}, mapper);
    }

    public Long count(String parentId, String name, String num){
        final String sql = "SELECT COUNT(id) FROM b_ind_ctg WHERE parent_id LIKE ? AND name LIKE ? AND num LIKE ?";

        String parentIdLike = DaoUtils.blankLike(parentId);
        String nameLike = DaoUtils.like(name);
        String numLike = DaoUtils.like(num);

        return jdbcTemplate.queryForObject(sql, new Object[]{parentIdLike, nameLike, numLike}, Long.class);
    }

    public List<UniIndCtg> find(String parentId, String name, String num, int offset, int limit){
        final String sql = "SELECT * FROM b_ind_ctg WHERE parent_id LIKE ? AND name LIKE ? AND num LIKE ? " +
                "ORDER BY create_time LIMIT ? OFFSET ?";

        String parentIdLike = DaoUtils.blankLike(parentId);
        String nameLike = DaoUtils.like(name);
        String numLike = DaoUtils.like(num);

        return jdbcTemplate.query(sql, new Object[]{parentIdLike, nameLike, numLike, limit, offset}, mapper);
    }
}
