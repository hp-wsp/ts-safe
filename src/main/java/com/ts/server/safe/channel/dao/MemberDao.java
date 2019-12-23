package com.ts.server.safe.channel.dao;

import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.common.utils.DaoUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 服务渠道商用户数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> mapper = (r, i) -> {
        Member t = new Member();

        t.setId(r.getString("id"));
        t.setChannelId(r.getString("channel_id"));
        t.setUsername(r.getString("username"));
        t.setName(r.getString("name"));
        t.setBirthday(r.getString("birthday"));
        t.setProfession(r.getString("profession"));
        t.setPhone(r.getString("phone"));
        t.setPassword(r.getString("password"));
        t.setRoot(r.getBoolean("is_root"));
        t.setRoles(DaoUtils.toArray(r.getString("roles")));
        t.setStatus(Member.Status.valueOf(r.getString("status")));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public MemberDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Member t){
        final String sql = "INSERT INTO c_member (id, username, name, password, phone, channel_id, " +
                "birthday, profession, is_root, roles, status, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getUsername(), t.getName(), t.getPassword(), t.getPhone(),
                t.getChannelId(), t.getBirthday(), t.getProfession(), t.isRoot(), DaoUtils.join(t.getRoles()),
                t.getStatus().name());
    }

    public boolean update(Member t){
        final String sql = "UPDATE c_member SET name = ?, phone = ?, birthday = ?, profession = ?, roles = ?, " +
                "status = ?, update_time = now() WHERE id = ? AND is_delete = false";

        return jdbcTemplate.update(sql, t.getName(), t.getPhone(), t.getBirthday(), t.getProfession(),
                DaoUtils.join(t.getRoles()), t.getStatus().name(), t.getId()) > 0;
    }

    public boolean notHasMember(String channelId){
        final String sql = "SELECT COUNT(id) FROM c_member WHERE channel_id = ? AND is_delete = false";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{channelId}, Integer.class);
        return count != null && count == 0;
    }

    public boolean delete(String id){
        final String sql = "UPDATE c_member SET username = CONCAT(username,'@', ?), is_delete = true WHERE id = ? AND is_root = false";

        String random = RandomStringUtils.randomAlphabetic(16);
        return jdbcTemplate.update(sql, random, id) > 0;
    }

    public void deleteMembers(String channelId){
        final String sql = "DELETE FROM c_member WHERE channel_id = ?";
        jdbcTemplate.update(sql, channelId);
    }

    public void activeMembers(String channelId, boolean isActive){
        final String sql = "UPDATE c_member SET status = ? WHERE channel_id = ? AND is_delete = false";
        Member.Status status = isActive? Member.Status.ACTIVE: Member.Status.INACTIVE;
        jdbcTemplate.update(sql, status.name(), channelId);
    }

    public boolean updatePassword(String id, String password){
        final String sql = "UPDATE c_member SET password = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, password, id) > 0;
    }

    public boolean forbid(String id, boolean isForbid){
        final String sql = "UPDATE c_member SET status = ? WHERE id = ? AND is_delete = false";
        Member.Status  status = isForbid? Member.Status.FORBID: Member.Status.ACTIVE;
        return jdbcTemplate.update(sql, status.name(), id) > 0;
    }

    public Member findOne(String id){
        final String sql = "SELECT * FROM c_member WHERE id = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Member findOneByUsername(String username){
        final String sql = "SELECT * FROM c_member WHERE username = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, mapper);
    }

    public Member findRoot(String channelId){
        final String sql = "SELECT * FROM c_member WHERE channel_id = ? AND is_root = true AND is_delete = false";
        return  jdbcTemplate.queryForObject(sql, new Object[]{channelId}, mapper);
    }

    public boolean hasUsername(String username){
        final String sql = "SELECT COUNT(id) FROM c_member WHERE username = ? AND is_delete = false";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);

        return count != null && count > 0;
    }

    public List<Member> findActiveMembers(String channelId){
        final String sql = "SELECT * FROM c_member WHERE channel_id = ? AND status = ? AND is_delete = false";
        return jdbcTemplate.query(sql, new Object[]{channelId, Member.Status.ACTIVE}, mapper);
    }

    public Long count(String channelId, String username, String phone){
        final String sql = "SELECT COUNT(id) FROM c_member WHERE channel_id LIKE ? AND username LIKE ? AND phone LIKE ? AND is_delete = false";

        String channelIdLike = DaoUtils.blankLike(channelId);
        String usernameLike = DaoUtils.like(username);
        String phoneLike = DaoUtils.like(phone);

        return jdbcTemplate.queryForObject(sql, new Object[]{channelIdLike, usernameLike, phoneLike}, Long.class);
    }

    public List<Member> find(String channelId, String username, String phone, int offset, int limit){
        final String sql = "SELECT * FROM c_member WHERE channel_id LIKE ? AND username LIKE ? AND phone LIKE ? AND is_delete = false " +
                "ORDER BY create_time DESC LIMIT ? OFFSET ?";

        String channelIdLike = DaoUtils.blankLike(channelId);
        String usernameLike = DaoUtils.like(username);
        String phoneLike = DaoUtils.like(phone);

        return jdbcTemplate.query(sql, new Object[]{channelIdLike, usernameLike, phoneLike, limit, offset}, mapper);
    }
}
