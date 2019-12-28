package com.ts.server.safe.base.dao;

import com.ts.server.safe.base.domain.UniCheckTable;
import com.ts.server.safe.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 统一检查表数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class UniCheckTableDao {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<UniCheckTable> mapper = (r, i) -> {
        UniCheckTable t = new UniCheckTable();

        t.setId(r.getString("id"));
        t.setTypeId(r.getString("type_id"));
        t.setTypeName(r.getString("type_name"));
        t.setItemId(r.getString("item_id"));
        t.setItemName(r.getString("item_name"));
        t.setContent(r.getString("content"));
        t.setConDetail(r.getString("con_detail"));
        t.setLawItem(r.getString("law_item"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniCheckTableDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(UniCheckTable t){
        final String sql = "INSERT INTO b_check_table (id, type_id, type_name, item_id, item_name, content, con_detail, law_item, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())";

        jdbcTemplate.update(sql, t.getId(), t.getTypeId(), t.getTypeName(),
                t.getItemId(), t.getItemName(), t.getContent(), t.getConDetail(), t.getLawItem());
    }

    public boolean update(UniCheckTable t){
        final String sql = "UPDATE b_check_table SET type_id = ?, type_name = ?, item_id = ?, item_name = ?," +
                " content = ?, con_detail = ?, law_item = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getTypeId(), t.getTypeName(), t.getItemId(), t.getItemName(),
                t.getContent(), t.getConDetail(), t.getLawItem(),  t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM b_check_table WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public UniCheckTable findOne(String id){
        final String sql = "SELECT * FROM b_check_table WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String typeName, String itemName, String content, String lawItem){
        final String sql = "SELECT COUNT(id) FROM b_check_table WHERE type_name LIKE ? AND item_name LIKE ? " +
                "AND content LIKE ? AND law_item LIKE ? ";

        Object[] params = buildParams(typeName, itemName, content, lawItem);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    private Object[] buildParams(String typeName, String itemName, String content, String lawItem){
        Object[] params = new Object[4];

        params[0] = DaoUtils.like(typeName);
        params[1] = DaoUtils.like(itemName);
        params[2] = DaoUtils.like(content);
        params[3] = DaoUtils.like(lawItem);

        return params;
    }

    public List<UniCheckTable> find(String typeName, String itemName, String content, String lawItem, int offset, int limit){

        final String sql = "SELECT * FROM b_check_table WHERE type_name LIKE ? AND item_name LIKE ? " +
                "AND content LIKE ? AND law_item LIKE ? ORDER BY create_time LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParams(typeName, itemName, content, lawItem), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }

    public List<UniCheckTable> findOfItem(String itemId){
        final String sql = "SELECT * FROM b_check_table WHERE item_id = ? ORDER BY create_time ASC";
        return jdbcTemplate.query(sql, new Object[]{itemId}, mapper);
    }
}
