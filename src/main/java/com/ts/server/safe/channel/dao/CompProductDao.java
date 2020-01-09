package com.ts.server.safe.channel.dao;

import com.ts.server.safe.channel.domain.CompProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 企业生产信息数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CompProductDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CompProduct> mapper = (r, i) -> {
        CompProduct t = new CompProduct();

        t.setId(r.getInt("id"));
        t.setChannelId(r.getString("channel_id"));
        t.setCompId(r.getString("comp_id"));
        t.setProKey(r.getString("pro_key"));
        t.setProValue(r.getInt("pro_value"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public CompProductDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(CompProduct t){
        final String sql = "INSERT INTO c_comp_product (channel_id, comp_id, pro_key, pro_value, create_time) " +
                "VALUES (?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getChannelId(), t.getCompId(), t.getProKey(), t.getProValue());
    }

    public void deleteByCompId(String compId){
        final String sql = "DELETE FROM c_comp_product WHERE comp_id = ?";
        jdbcTemplate.update(sql, compId);
    }

    public List<CompProduct> find(String compId){
        final String sql = "SELECT * FROM c_comp_product WHERE comp_id = ?";
        return jdbcTemplate.query(sql, new Object[]{compId}, mapper);
    }
}
