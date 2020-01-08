package com.ts.server.safe.channel.dao;

import com.ts.server.safe.channel.domain.Contract;
import com.ts.server.safe.common.utils.DaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

/**
 * 合同数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class ContractDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Contract> mapper = (r, i) -> {
        Contract t = new Contract();

        t.setId(r.getString("id"));
        t.setChannelId(r.getString("channel_id"));
        t.setNum(r.getString("num"));
        t.setName(r.getString("name"));
        t.setConProject(r.getString("con_project"));
        t.setEntCompType(r.getInt("ent_comp_type"));
        t.setEntCompName(r.getString("ent_comp_name"));
        t.setSerCompId(r.getString("ser_comp_id"));
        t.setSerCompName(r.getString("ser_comp_name"));
        t.setSerAddress(r.getString("ser_address"));
        t.setSigConDate(r.getDate("sig_con_date"));
        t.setProAddress(r.getString("pro_address"));
        t.setSerConDateFrom(r.getDate("ser_con_date_from"));
        t.setSerConDateTo(r.getDate("ser_con_date_to"));
        t.setAmount(r.getInt("amount"));
        t.setOwnPerson(r.getString("own_person"));
        t.setOwnPhone(r.getString("own_phone"));
        t.setSigPerson(r.getString("sig_person"));
        t.setSigCompany(r.getString("sig_company"));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ContractDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Contract t){
        final String sql = "INSERT INTO c_contract (id, channel_id, service_id, num, name, con_project, ent_comp_type, " +
                "ent_comp_name, ser_comp_id, ser_comp_name, ser_address, sig_con_date, pro_address, ser_con_date_from," +
                "ser_con_date_to, amount, own_person, own_phone, sig_person, sig_company, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getChannelId(), t.getServiceId(), t.getNum(), t.getName(), t.getConProject(),
                t.getEntCompType(), t.getEntCompName(), t.getSerCompId(), t.getSerCompName(), t.getSerAddress(),
                t.getSigConDate(), t.getProAddress(), t.getSerConDateFrom(), t.getSerConDateTo(), t.getAmount(),
                t.getOwnPerson(), t.getOwnPhone(), t.getSigPerson(), t.getSigCompany());
    }

    public boolean update(Contract t){
        final String sql = "UPDATE c_contract SET num = ?, name = ?, con_project = ?, ent_comp_type = ?, " +
                "ent_comp_name = ?, ser_comp_id = ?, ser_comp_name = ?, ser_address = ?, sig_con_date = ?, " +
                "pro_address = ?, ser_con_date_from = ?, ser_con_date_to = ?, amount = ?, own_person = ?, " +
                "own_phone = ?, sig_person = ?, sig_company = ?, update_time = now()" +
                "WHERE id = ?";

        return jdbcTemplate.update(sql, t.getNum(), t.getName(), t.getConProject(), t.getEntCompType(),
                t.getEntCompName(), t.getSerCompId(), t.getSerCompName(), t.getSerAddress(), t.getSigConDate(),
                t.getProAddress(), t.getSerConDateFrom(), t.getSerConDateTo(), t.getAmount(), t.getOwnPerson(),
                t.getOwnPhone(), t.getSigPerson(), t.getSigCompany(), t.getId()) > 0;
    }

    public boolean hasNum(String channelId, String num){
        final String sql = "SELECT COUNT(id) FROM c_contract WHERE channel_id = ? AND num = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{channelId, num}, Integer.class);
        return count != null && count > 0;
    }

    public boolean updateService(String id, String serviceId){
        final String sql = "UPDATE c_contract SET service_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, serviceId, id) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_contract WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Contract findOne(String id){
        final String sql = "SELECT * FROM c_contract WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String channelId, String name, String num, String entCompName, String proAddress, Integer entCompType){
        String sql = "SELECT COUNT(id) FROM c_contract WHERE channel_id LIKE ? AND name LIKE ? AND num LIKE ? " +
                "AND ent_comp_name LIKE ? AND pro_address LIKE ? ";

        if(Objects.nonNull(entCompType)){
            sql = sql + " AND ent_comp_type  = ?";
        }

        final Object[] params = buildParams(channelId, name, num, entCompName, proAddress, entCompType);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    private Object[] buildParams(String channelId, String name, String num, String entCompName, String proAddress, Integer entCompType){
        final Object[] params = entCompType == null? new Object[5]: new Object[6];
        params[0] = DaoUtils.blankLike(channelId);
        params[1] = DaoUtils.like(name);
        params[2] = DaoUtils.like(num);
        params[3] = DaoUtils.blankLike(entCompName);
        params[4] = DaoUtils.like(proAddress);

        if(Objects.nonNull(entCompType)){
            params[5] = entCompType;
        }

        return params;
    }

    public List<Contract> find(String channelId, String name, String num, String entCompName, String proAddress,
                               Integer entCompType, int offset, int limit){
        String sql = "SELECT * FROM c_contract WHERE channel_id LIKE ? AND name LIKE ? AND num LIKE ? " +
                "AND ent_comp_name LIKE ? AND pro_address LIKE ? ";

        if(Objects.nonNull(entCompType)){
            sql = sql + " AND ent_comp_type  = ?";
        }

        sql = sql + " ORDER BY update_time DESC, create_time DESC LIMIT ? OFFSET ?";

        final Object[] params = DaoUtils.appendOffsetLimit(buildParams(channelId, name, num, entCompName, proAddress, entCompType), offset, limit);

        return jdbcTemplate.query(sql, params, mapper);
    }

    public List<Contract> findNoneAlloc(String channelId){
        final String sql = "SELECT * FROM c_contract WHERE channel_id = ? AND service_id = '' ORDER BY create_time DESC";
        return jdbcTemplate.query(sql, new Object[]{channelId}, mapper);
    }

}
