package com.ts.server.safe.base.dao;

import com.ts.server.safe.base.domain.UniCheckItem;
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

    private final RowMapper<UniCheckItem> mapper = (r, i) -> {
        UniCheckItem t = new UniCheckItem();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setTypeId(r.getString("type_id"));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniCheckItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(UniCheckItem t){
        final String sql = "INSERT INTO b_check_item (id, type_id, name, remark, create_time) " +
                "VALUES (?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getId(), t.getTypeId(), t.getName(), t.getRemark());
    }

    public boolean update(UniCheckItem t){
        final String sql = "UPDATE b_check_item SET name = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getRemark(), t.getId()) > 0;
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

    public List<UniCheckItem> find(String typeId, String name){
        final String sql = "SELECT * FROM b_check_item WHERE type_id LIKE ? AND name LIKE ? ORDER BY create_time ASC";

        String typeIdLike = DaoUtils.blankLike(typeId);
        String nameLike = DaoUtils.like(name);

        return jdbcTemplate.query(sql, new Object[]{typeIdLike, nameLike}, mapper);
    }
}
