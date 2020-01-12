package com.ts.server.safe.channel.dao;

import com.ts.server.safe.channel.domain.CheckContent;
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
 * 检查内容数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CheckContentDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<CheckContent> mapper = (r, i) -> {
        CheckContent t = new CheckContent();

        t.setId(r.getString("id"));
        t.setChannelId(r.getString("channel_id"));
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
    public CheckContentDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(
                dataSource, "seq_u_check_content", e -> String.format("%07d", e));
    }

    public String insert(CheckContent t){
        String id = idGenerator.generate();
        final String sql = "INSERT INTO c_check_content (id, channel_id, type_id, type_name, item_id, item_name, " +
                "content, con_detail, law_item, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now())";

        jdbcTemplate.update(sql, id, t.getChannelId(), t.getTypeId(), t.getTypeName(),
                t.getItemId(), t.getItemName(), t.getContent(), t.getConDetail(), t.getLawItem());

        return id;
    }

    public boolean update(CheckContent t){
        final String sql = "UPDATE c_check_content SET type_id = ?, type_name = ?, item_id = ?, item_name = ?, " +
                "content = ?, con_detail = ?, law_item = ? WHERE id = ?";

        return jdbcTemplate.update(sql, t.getTypeId(), t.getTypeName(), t.getItemId(), t.getItemName(), t.getContent(),
                t.getConDetail(), t.getLawItem(), t.getId()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_check_content WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public CheckContent findOne(String id){
        final String sql = "SELECT * FROM c_check_content WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String channelId, String typeName, String itemName, String content, String lawItem){
        final String sql = "SELECT COUNT(id) FROM c_check_content WHERE channel_id LIKE ? AND type_name LIKE ? " +
                "AND item_name LIKE ? AND content LIKE ? AND law_item LIKE ? ";

        Object[] params = buildParams(channelId, typeName, itemName, content, lawItem);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    private Object[] buildParams(String channelId, String typeName, String itemName, String content, String lawItem){
        Object[] params = new Object[5];

        params[0] = DaoUtils.blankLike(channelId);
        params[1] = DaoUtils.like(typeName);
        params[2] = DaoUtils.like(itemName);
        params[3] = DaoUtils.like(content);
        params[4] = DaoUtils.like(lawItem);

        return params;
    }

    public List<CheckContent> find(String channelId, String typeName, String itemName, String content, String lawItem, int offset, int limit){

        final String sql = "SELECT * FROM c_check_content WHERE channel_id LIKE ? AND type_name LIKE ? AND item_name LIKE ? " +
                "AND content LIKE ? AND law_item LIKE ? ORDER BY id LIMIT ? OFFSET ?";

        Object[] params = DaoUtils.appendOffsetLimit(buildParams(channelId, typeName, itemName, content, lawItem), offset, limit);
        return jdbcTemplate.query(sql, params, mapper);
    }

    public List<CheckContent> findOfItem(String channelId, String itemId){
        final String sql = "SELECT * FROM c_check_content WHERE channel_id = ? AND item_id = ? ORDER BY id ASC";
        return jdbcTemplate.query(sql, new Object[]{channelId, itemId}, mapper);
    }
}
