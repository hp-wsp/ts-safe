package com.ts.server.safe.channel.dao;

import com.ts.server.safe.channel.domain.Professor;
import com.ts.server.safe.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 专家数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ProfessorDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Professor> mapper = (r, i) ->{
        Professor t = new Professor();

        t.setId(r.getString("id"));
        t.setChannelId(r.getString("channel_id"));
        t.setName(r.getString("name"));
        t.setPhone(r.getString("phone"));
        t.setProfession(r.getString("profession"));
        t.setBirthday(r.getString("birthday"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ProfessorDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Professor t){
        final String sql = "INSERT INTO c_professor (id, channel_id, name, phone, profession, birthday, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, t.getId(), t.getChannelId(), t.getName(), t.getPhone(), t.getProfession(), t.getBirthday());
    }

    public boolean update(Professor t){
        final String sql = "UPDATE c_professor SET name = ?, phone = ?, profession = ?, birthday = ? WHERE id = ? AND channel_id = ?";
        return jdbcTemplate.update(sql, t.getName(), t.getPhone(), t.getProfession(), t.getBirthday(), t.getId(), t.getChannelId()) > 0;
    }

    public boolean delete(String id, String channelId){
        final String sql = "DELETE FROM c_professor WHERE id = ? AND channel_id = ?";
        return jdbcTemplate.update(sql, id, channelId) > 0;
    }

    public Professor findOne(String id){
        final String sql = "SELECT * FROM c_professor WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String channelId, String name, String phone, String profession){
        final String sql = "SELECT COUNT(id) FROM c_professor " +
                "WHERE channelId = ? AND name LIKE ? AND phone LIKE ? AND profession LIKE ?";

        String nameLike = DaoUtils.like(name);
        String phoneLike = DaoUtils.like(phone);
        String professionLike = DaoUtils.like(profession);

        return jdbcTemplate.queryForObject(sql, new Object[]{channelId, nameLike, phoneLike, professionLike}, Long.class);
    }

    public List<Professor> find(String channelId, String name, String phone, String profession, int offset, int limit){
        final String sql = "SELECT * FROM c_professor " +
                "WHERE channelId = ? AND name LIKE ? AND phone LIKE ? AND profession LIKE ?" +
                "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        String nameLike = DaoUtils.like(name);
        String phoneLike = DaoUtils.like(phone);
        String professionLike = DaoUtils.like(profession);

        return jdbcTemplate.query(sql, new Object[]{channelId, nameLike, phoneLike, professionLike, limit, offset}, mapper);
    }
}
