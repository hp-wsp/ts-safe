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
        t.setCheckType(r.getString("check_type"));
        t.setCheckItem(r.getString("check_item"));
        t.setContent(r.getString("content"));
        t.setConDetail(r.getString("con_detail"));
        t.setLawItem(r.getString("law_item"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniCheckTableDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(UniCheckTable t){
        final String sql = "INSERT INTO b_check_table (id, check_type, check_item, content, con_detail, law_item, " +
                "update_time, create_time) VALUES (?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getCheckType(), t.getCheckItem(), t.getContent(), t.getConDetail(), t.getLawItem());
    }

    public boolean update(UniCheckTable t){
        final String sql = "UPDATE b_check_table SET content = ?, con_detail = ?, law_item = ?, " +
                "check_type = ?, check_item, update_time = now() WHERE id = ?";
        return jdbcTemplate.update(sql, t.getContent(), t.getConDetail(),
                t.getLawItem(), t.getCheckType(), t.getCheckItem(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM b_check_table WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public UniCheckTable findOne(String id){
        final String sql = "SELECT * FROM b_check_table WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String checkType, String checkItem, String content, String lawItem){
        final String sql = "SELECT COUNT(id) FROM b_check_table WHERE check_type LIKE ? " +
                "AND check_item LIKE ? AND content LIKE ? AND law_item LIKE ? ";

        Object[] params = buildParams(checkType, checkItem, content, lawItem);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    private Object[] buildParams(String checkType, String checkItem, String content, String lawItem){
        Object[] params = new Object[4];

        params[0] = DaoUtils.like(checkType);
        params[1] = DaoUtils.like(checkItem);
        params[2] = DaoUtils.like(content);
        params[3] = DaoUtils.like(lawItem);

        return params;
    }

    public List<UniCheckTable> find(String checkType, String checkItem, String content, String lawItem, int offset, int limit){

        String sql = "SELECT * FROM b_check_table WHERE check_type LIKE ? " +
                "AND check_item LIKE ? AND content LIKE ? AND law_item LIKE ? " +
                "ORDER BY show_order ASC, create_time ASC LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParams(checkType, checkItem, content, lawItem), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }
}
