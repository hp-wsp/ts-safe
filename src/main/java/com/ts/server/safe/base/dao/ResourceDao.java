package com.ts.server.safe.base.dao;

import com.ts.server.safe.base.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * 资源数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ResourceDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Resource> mapper = (r, i) -> {
        Resource t = new Resource();

        t.setId(r.getString("id"));
        t.setFileName(r.getString("file_name"));
        t.setFileSize(r.getInt("file_size"));
        t.setContentType(r.getString("content_type"));
        t.setPath(r.getString("path"));
        t.setUsername(r.getString("username"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ResourceDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Resource t){
        final String sql = "INSERT INTO b_resource (id, file_name, file_size, content_type, path, username, create_time) " +
                "VALUES(?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getId(), t.getFileName(), t.getFileSize(), t.getContentType(), t.getPath(), t.getUsername());
    }

    public Resource findOne(String id){
        final String sql = "SELECT * FROM b_resource WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }
}
