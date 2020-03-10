package com.ts.server.safe.company.dao;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.company.domain.CompInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

/**
 * 公司信息数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class CompInfoDao {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final JavaType javaType;

    @Autowired
    public CompInfoDao(DataSource dataSource, ObjectMapper objectMapper){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.objectMapper = objectMapper;
        this.javaType = objectMapper.getTypeFactory().constructParametricType(List.class, CompInfo.IndCtg.class);
    }

    public void insert(CompInfo t){
        final String sql = "INSERT INTO c_comp_info (id, name, channel_id, province_id, province, city_id, city, country_id, " +
                "country, address, reg_address, reg_date, bus_status, legal_person, legal_phone, legal_mobile, safe_person, " +
                "safe_phone, safe_mobile, contact, phone, mobile, credit_code, post_code, ind_ctgs, ind_phone, ent_scale, " +
                "ind_of_company, mem_fun, man_product, profile, images, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getName(), t.getChannelId(), t.getProvinceId(), t.getProvince(), t.getCityId(),
                t.getCity(), t.getCountryId(), t.getCountry(), t.getAddress(), t.getRegAddress(), t.getRegDate(),
                t.getBusStatus(), t.getLegalPerson(), t.getLegalPhone(), t.getLegalMobile(), t.getSafePerson(),
                t.getSafePhone(), t.getSafeMobile(), t.getContact(), t.getPhone(), t.getMobile(), t.getCreditCode(),
                t.getPostCode(), toJson(t.getIndCtgs()), t.getIndPhone(), t.getEntScale(), t.getIndOfCompany(), t.getMemFun(),
                t.getManProduct(), t.getProfile(), DaoUtils.join(t.getImages()));
    }

    private String toJson(List<CompInfo.IndCtg> indCtgs){
        try{
            return indCtgs == null || indCtgs.isEmpty()? "{}": objectMapper.writeValueAsString(indCtgs);
        }catch (Exception e){
            throw new RuntimeException("IndCtg to json fail");
        }
    }

    public boolean update(CompInfo t){
        final String sql = "UPDATE c_comp_info SET name = ?, province_id = ?, province = ?, city_id = ?, city = ?, " +
                "country_id = ?, country = ?, address = ?, reg_address = ?, reg_date = ?, bus_status = ?, legal_person = ?, " +
                "legal_phone = ?, legal_mobile = ?, safe_person = ?, safe_phone = ?, safe_mobile = ?, contact = ?, " +
                "phone = ?, mobile = ?, credit_code = ?, post_code = ?, ind_ctgs = ?, ind_phone = ?, ent_scale = ?, " +
                "ind_of_company = ?, mem_fun = ?, man_product = ?, profile = ?, images = ?, update_time = now() " +
                "WHERE id = ? AND channel_id = ?";

        return jdbcTemplate.update(sql, t.getName(), t.getProvinceId(), t.getProvince(), t.getCityId(), t.getCity(), t.getCountryId(),
                t.getCountry(), t.getAddress(), t.getRegAddress(), t.getRegDate(), t.getBusStatus(), t.getLegalPerson(),
                t.getLegalPhone(), t.getLegalMobile(), t.getSafePerson(), t.getSafePhone(), t.getSafeMobile(), t.getContact(),
                t.getPhone(), t.getMobile(), t.getCreditCode(), t.getPostCode(), toJson(t.getIndCtgs()), t.getIndPhone(),
                t.getEntScale(), t.getIndOfCompany(), t.getMemFun(), t.getManProduct(), t.getProfile(),
                DaoUtils.join(t.getImages()), t.getId(), t.getChannelId()) > 0;
    }

    public boolean delete (String id, String channelId){
        final String sql = "DELETE FROM c_comp_info WHERE id = ? AND channel_id = ?";
        return jdbcTemplate.update(sql, id, channelId) > 0;
    }

    public boolean hasName(String channelId, String name){
        final String sql = "SELECT COUNT(id) FROM c_comp_info WHERE channel_id = ? AND name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{channelId, name}, Integer.class);
        return count != null && count > 0;
    }

    public CompInfo findOne(String id){
        final String sql = "SELECT * FROM c_comp_info WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, mapper);
    }

    public Long count(String channelId, String name, String province,
                      String city, String country, String contact, String mobile){

        final String sql = "SELECT COUNT(id) FROM c_comp_info WHERE channel_id = ? AND name LIKE ? AND province LIKE ? AND city LIKE ? " +
                "AND country LIKE ? AND contact LIKE ? AND mobile LIKE ?";

        String nameLike = DaoUtils.like(name);
        String provinceLike = DaoUtils.like(province);
        String cityLike = DaoUtils.like(city);
        String countryLike = DaoUtils.like(country);
        String contactLike = DaoUtils.like(contact);
        String mobileLike = DaoUtils.like(mobile);

        return jdbcTemplate.queryForObject(sql, new Object[]{channelId, nameLike, provinceLike, cityLike,
                countryLike, contactLike, mobileLike}, Long.class);
    }

    public List<CompInfo> find(String channelId, String name, String province, String city,
                               String country, String contact, String mobile, int offset, int limit){

        final String sql = "SELECT * FROM c_comp_info WHERE channel_id = ? AND name LIKE ? AND province LIKE ? AND city LIKE ? " +
                "AND country LIKE ? AND contact LIKE ? AND mobile LIKE ? ORDER BY create_time DESC LIMIT ? OFFSET ?";

        String nameLike = DaoUtils.like(name);
        String provinceLike = DaoUtils.like(province);
        String cityLike = DaoUtils.like(city);
        String countryLike = DaoUtils.like(country);
        String contactLike = DaoUtils.like(contact);
        String mobileLike = DaoUtils.like(mobile);

        return jdbcTemplate.query(sql, new Object[]{channelId, nameLike, provinceLike, cityLike,
                countryLike, contactLike, mobileLike, limit, offset}, mapper);
    }

    private final RowMapper<CompInfo> mapper = (r, i) -> {
        CompInfo t = new CompInfo();

        t.setId(r.getString("id"));
        t.setName(r.getString("name"));
        t.setChannelId(r.getString("channel_id"));
        t.setProvinceId(r.getString("province_id"));
        t.setProvince(r.getString("province"));
        t.setCityId(r.getString("city_id"));
        t.setCity(r.getString("city"));
        t.setCountryId(r.getString("country_id"));
        t.setCountry(r.getString("country"));
        t.setAddress(r.getString("address"));
        t.setRegAddress(r.getString("reg_address"));
        t.setRegDate(r.getString("reg_date"));
        t.setBusStatus(r.getInt("bus_status"));
        t.setLegalPerson(r.getString("legal_person"));
        t.setLegalPhone(r.getString("legal_phone"));
        t.setLegalMobile(r.getString("legal_mobile"));
        t.setSafePerson(r.getString("safe_person"));
        t.setSafePhone(r.getString("safe_phone"));
        t.setSafeMobile(r.getString("safe_mobile"));
        t.setContact(r.getString("contact"));
        t.setPhone(r.getString("phone"));
        t.setMobile(r.getString("mobile"));
        t.setCreditCode(r.getString("credit_code"));
        t.setPostCode(r.getString("post_code"));
        t.setIndCtgs(toIndCtgs(r.getString("ind_ctgs")));
        t.setIndPhone(r.getString("ind_phone"));
        t.setEntScale(r.getInt("ent_scale"));
        t.setIndOfCompany(r.getString("ind_of_company"));
        t.setMemFun(r.getString("mem_fun"));
        t.setManProduct(r.getString("man_product"));
        t.setProfile(r.getString("profile"));
        t.setImages(DaoUtils.toList(r.getString("images")));
        t.setUpdateTime(r.getTimestamp("update_time"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return  t;
    };

    private List<CompInfo.IndCtg> toIndCtgs(String v){
        if(StringUtils.isBlank(v)){
            return Collections.emptyList();
        }

        try{
            return objectMapper.readValue(v, javaType);
        }catch (Exception e){
            throw new RuntimeException("Json to list ind ctg fail");
        }
    }
}
