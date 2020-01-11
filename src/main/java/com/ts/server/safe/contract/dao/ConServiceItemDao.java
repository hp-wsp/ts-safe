package com.ts.server.safe.contract.dao;

import com.ts.server.safe.contract.domain.ConServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 合同服务项目数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ConServiceItemDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ConServiceItem> mapper = (r, i) -> {
        ConServiceItem t = new ConServiceItem();

        t.setId(r.getString("id"));
        t.setServiceId(r.getString("service_id"));
        t.setItemId(r.getInt("item_id"));
        t.setItemName(r.getString("item_name"));
        t.setItemValue(r.getString("item_value"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ConServiceItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(ConServiceItem t){
        final String sql = "INSERT INTO c_service_item (id, service_id, item_id, item_name, item_value, create_time) " +
                "VALUES (?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getId(), t.getServiceId(), t.getItemId(), t.getItemName(), t.getItemValue());
    }

    public void deleteOfService(String serviceId){
        final String sql = "DELETE FROM c_service_item WHERE service_id = ?";
        jdbcTemplate.update(sql, serviceId);
    }

    public List<ConServiceItem> find(String serviceId){
        final String sql = "SELECT * FROM c_service_item WHERE service_id = ? ORDER BY id ASC";
        return jdbcTemplate.query(sql, new Object[]{serviceId}, mapper);
    }
}
