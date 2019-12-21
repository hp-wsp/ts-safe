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
        t.setSupId("sup_id");
        t.setContent("content");
        t.setConDetail("con_detail");
        t.setLawName("law_name");
        t.setLawItem("law_item");
        t.setLawContent("law_content");
        t.setCheckType(r.getInt("check_type"));
        t.setShowOrder(r.getInt("show_order"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public UniCheckTableDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(UniCheckTable t){
        final String sql = "INSERT INTO b_check_table (id, sup_id, content, con_detail, law_name, law_item, law_content, " +
                "check_type, show_order, update_time, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getSupId(), t.getContent(), t.getConDetail(), t.getLawName(), t.getLawItem(),
                t.getLawContent(), t.getCheckType(), t.getShowOrder());
    }

    public boolean update(UniCheckTable t){
        final String sql = "UPDATE b_check_table SET content = ?, con_detail = ?, law_name = ?, law_item = ?, " +
                "law_content = ?, check_type = ?, show_order = ?, update_time = now() WHERE id = ?";
        return jdbcTemplate.update(sql, t.getContent(), t.getConDetail(), t.getLawName(), t.getLawItem(),
                t.getLawContent(), t.getCheckType(), t.getShowOrder(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM b_check_table WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public UniCheckTable findOne(String id){
        final String sql = "SELECT * FROM b_check_table WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String supId, String name, String content, String lawContent, Integer checkType){
        String sql = "SELECT COUNT(id) FROM b_check_table WHERE supId LIKE ? AND name LIKE ? AND content LIKE ? " +
                "AND law_content LIKE ? ";
        if(checkType != null){
            sql = sql + "AND check_type = ?";
        }

        Object[] params = buildParams(supId, name, content, lawContent, checkType);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    private Object[] buildParams(String supId, String name, String content, String lawContent, Integer checkType){
        Object[] params = checkType == null? new Object[4]: new Object[5];

        params[0] = DaoUtils.like(supId);
        params[1] = DaoUtils.like(name);
        params[2] = DaoUtils.like(content);
        params[3] = DaoUtils.like(lawContent);
        if(checkType != null){
            params[4] = checkType;
        }

        return params;
    }

    public List<UniCheckTable> find(String supId, String name, String content,
                                    String lawContent, Integer checkType, int offset, int limit){

        String sql = "SELECT COUNT(id) FROM b_check_table WHERE supId LIKE ? AND name LIKE ? AND content LIKE ? " +
                "AND law_content LIKE ? ";
        if(checkType != null){
            sql = sql + "AND check_type = ?";
        }
        sql = sql + " ORDER BY show_order ASC, create_time ASC LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParams(supId, name, content, lawContent, checkType), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }
}
