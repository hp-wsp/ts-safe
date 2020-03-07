package com.ts.server.safe.contract.dao;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.contract.domain.Contract;
import com.ts.server.safe.common.utils.DaoUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
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
    private final ObjectMapper objectMapper;
    private final RowMapper<Contract> mapper;
    private final RowMapper<Contract> queryMapper;

    @Autowired
    public ContractDao(DataSource dataSource, ObjectMapper objectMapper){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.objectMapper = objectMapper;
        this.mapper = new ContractMapper(objectMapper, true);
        this.queryMapper = new ContractMapper(objectMapper, false);
    }

    public void insert(Contract t){
        final String sql = "INSERT INTO oa_contract (id, channel_id, num, name, con_project, ent_comp_type, " +
                "ent_comp_name, ser_companies, ser_address, sig_con_date, pro_address, ser_con_date_from, ser_con_date_to, " +
                "amount, own_person, own_phone, sig_person, sig_company, is_complete, attaches, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getChannelId(), t.getNum(), t.getName(), t.getConProject(),
                t.getEntCompType(), t.getEntCompName(), toJson(t.getSerCompanies()), t.getSerAddress(), t.getSigConDate(),
                t.getProAddress(), t.getSerConDateFrom(), t.getSerConDateTo(), t.getAmount(), t.getOwnPerson(),
                t.getOwnPhone(), t.getSigPerson(), t.getSigCompany(), DaoUtils.join(t.getAttaches()));
    }

    private String toJson(Object object){
        try{
            return Objects.isNull(object)? "{}": objectMapper.writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException("Object to json fail");
        }
    }

    public boolean update(Contract t){
        final String sql = "UPDATE oa_contract SET num = ?, name = ?, con_project = ?, ent_comp_type = ?, " +
                "ent_comp_name = ?, ser_companies = ?, ser_address = ?, sig_con_date = ?, pro_address = ?, " +
                "ser_con_date_from = ?, ser_con_date_to = ?, amount = ?, own_person = ?, own_phone = ?, sig_person = ?, " +
                "sig_company = ?, attaches = ?, update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, t.getNum(), t.getName(), t.getConProject(), t.getEntCompType(),
                t.getEntCompName(), toJson(t.getSerCompanies()), t.getSerAddress(), t.getSigConDate(),
                t.getProAddress(), t.getSerConDateFrom(), t.getSerConDateTo(), t.getAmount(), t.getOwnPerson(),
                t.getOwnPhone(), t.getSigPerson(), t.getSigCompany(), DaoUtils.join(t.getAttaches()), t.getId()) > 0;
    }

    public boolean updateComplete(String id, boolean isComplete){
        final String sql = "UPDATE oa_contract SET is_complete = ? WHERE id = ? AND is_delete = false";
        return jdbcTemplate.update(sql, isComplete, id) > 0;
    }

    public boolean hasNum(String channelId, String num){
        final String sql = "SELECT COUNT(id) FROM oa_contract WHERE channel_id = ? AND num = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{channelId, num}, Integer.class);
        return count != null && count > 0;
    }

    public boolean delete(String id){
        final String sql = "UPDATE oa_contract SET num = CONCAT(num,?), is_delete = true  WHERE id = ?";
        String code = "@" + RandomUtils.nextInt(0, 10000000);
        return jdbcTemplate.update(sql, code, id) > 0;
    }

    public Contract findOne(String id){
        final String sql = "SELECT * FROM oa_contract WHERE id = ? AND is_delete = false";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String channelId, String name, String num, String entCompName, String proAddress, Integer entCompType){
        String sql = "SELECT COUNT(id) FROM oa_contract WHERE channel_id LIKE ? AND name LIKE ? AND num LIKE ? " +
                "AND ent_comp_name LIKE ? AND pro_address LIKE ? AND is_delete = false ";
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

        String sql = "SELECT * FROM oa_contract WHERE channel_id LIKE ? AND name LIKE ? AND num LIKE ? " +
                "AND ent_comp_name LIKE ? AND pro_address LIKE ? AND is_delete = false ";
        if(Objects.nonNull(entCompType)){
            sql = sql + " AND ent_comp_type  = ?";
        }

        sql = sql + " ORDER BY update_time DESC, create_time DESC LIMIT ? OFFSET ?";

        final Object[] params = DaoUtils.appendOffsetLimit(buildParams(channelId, name, num, entCompName, proAddress, entCompType), offset, limit);

        return jdbcTemplate.query(sql, params, queryMapper);
    }

    private static class ContractMapper implements RowMapper<Contract> {
        private final ObjectMapper objectMapper;
        private final JavaType serCompType;
        private final boolean isParseSerCompanies;

        ContractMapper(ObjectMapper objectMapper, boolean isParseSerCompanies) {
            this.objectMapper = objectMapper;
            this.serCompType = objectMapper.getTypeFactory().constructParametricType(List.class, Contract.SerCompany.class);
            this.isParseSerCompanies = isParseSerCompanies;
        }

        @Override
        public Contract mapRow(ResultSet r, int i) throws SQLException {
            Contract t = new Contract();

            t.setId(r.getString("id"));
            t.setChannelId(r.getString("channel_id"));
            t.setNum(r.getString("num"));
            t.setName(r.getString("name"));
            t.setConProject(r.getString("con_project"));
            t.setEntCompType(r.getInt("ent_comp_type"));
            t.setEntCompName(r.getString("ent_comp_name"));
            if(isParseSerCompanies){
                t.setSerCompanies(toSerCompanies(r.getString("ser_companies")));
            }else{
                t.setSerCompanies(Collections.emptyList());
            }
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
            t.setComplete(r.getBoolean("is_complete"));
            t.setAttaches(DaoUtils.toList(r.getString("attaches")));
            t.setUpdateTime(r.getTimestamp("update_time"));
            t.setCreateTime(r.getTimestamp("create_time"));

            return t;
        }

        List<Contract.SerCompany> toSerCompanies(String v){
            if(StringUtils.isBlank(v)){
                return Collections.emptyList();
            }

            try{
                return objectMapper.readValue(v, serCompType);
            }catch (Exception e){
                throw new RuntimeException("Json to service companies fail");
            }
        }
    }

}
