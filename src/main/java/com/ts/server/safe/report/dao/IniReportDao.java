package com.ts.server.safe.report.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.report.domain.IniReport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * 检查报表数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class IniReportDao {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final RowMapper<IniReport> mapper;
    private final RowMapper<IniReport> fullMapper;

    @Autowired
    public IniReportDao(DataSource dataSource, ObjectMapper objectMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.objectMapper = objectMapper;
        this.mapper = new CheckReportMapper();
        this.fullMapper = new CheckReportFullMapper(objectMapper);
    }

    public void insert(IniReport t){
        final String sql = "INSERT INTO r_initial (id, channel_id, channel_name, comp_id, comp_name, task_id, task_detail, " +
                "service_id, service_name, cycle_name, ent_comp_type, industry, ent_scale, area, check_time_from, check_time_to, " +
                "report_detail, comp_base_info, safe_product, print_detail, update_time, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())";

        jdbcTemplate.update(sql, t.getId(), t.getChannelId(), t.getChannelName(), t.getCompId(), t.getCompName(), t.getTaskId(),
                t.getTaskDetail(), t.getServiceId(), t.getServiceName(), t.getCycleName(), t.getEntCompType(), t.getIndustry(),
                t.getEntScale(), t.getArea(), t.getCheckTimeFrom(), t.getCheckTimeTo(), toJson(t.getReportDetail()),
                toJson(t.getCompBaseInfo()), toJson(t.getSafeProduct()), t.getPrintDetail());
    }

    private String toJson(Object object){
        try{
            return Objects.isNull(object)? "{}": objectMapper.writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException("Object to json fail");
        }
    }

    public boolean update(IniReport t){
        final String sql = "UPDATE r_initial SET comp_id = ?, comp_name = ?, task_id = ?, task_detail = ?, service_id = ?," +
                "service_name = ?, cycle_name = ?, ent_comp_type = ?, industry = ?, ent_scale = ?,area = ?, check_time_from = ?, " +
                "check_time_to = ?, report_detail = ?, comp_base_info = ?, safe_product = ?, print_detail=?, update_time = now() WHERE id = ?";

        return jdbcTemplate.update(sql, t.getCompId(), t.getCompName(), t.getTaskId(), t.getTaskDetail(), t.getServiceId(),
                t.getServiceName(), t.getCycleName(), t.getEntCompType(), t.getIndustry(), t.getEntScale(),
                t.getArea(), t.getCheckTimeFrom(), t.getCheckTimeFrom(), toJson(t.getReportDetail()), toJson(t.getCompBaseInfo()),
                toJson(t.getSafeProduct()), t.getPrintDetail(), t.getId()) > 0;
    }

    public IniReport findOne(String id){
        final String sql = "SELECT *  FROM r_initial WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, fullMapper);
    }

    public boolean hasCycleName(String channelId, String cycleName){
        final String sql = "SELECT COUNT(id) FROM r_initial WHERE channel_id = ? AND cycle_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{channelId, cycleName}, Integer.class);
        return count != null && count > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM r_initial WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public Long count(String channelId, String compName, String cycleName){
        final String sql = "SELECT COUNT(id) FROM r_initial WHERE channel_id LIKE ? AND comp_name LIKE ? AND cycle_name LIKE ?";

        final String channelIdLike = DaoUtils.blankLike(channelId);
        final String compNameLike = DaoUtils.like(compName);
        final String cycleNameLike = DaoUtils.like(cycleName);

        return jdbcTemplate.queryForObject(sql, new Object[]{channelIdLike, compNameLike, cycleNameLike}, Long.class);
    }

    public List<IniReport> find(String channelId, String compName, String cycleName, int offset, int limit){
        final String sql = "SELECT * FROM r_initial WHERE channel_id LIKE ? AND comp_name LIKE ? AND cycle_name LIKE ? " +
                "ORDER BY update_time DESC LIMIT ? OFFSET ?";

        final String channelIdLike = DaoUtils.blankLike(channelId);
        final String compNameLike = DaoUtils.like(compName);
        final String cycleNameLike = DaoUtils.like(cycleName);

        return jdbcTemplate.query(sql, new Object[]{channelIdLike, compNameLike, cycleNameLike, limit, offset}, mapper);
    }

    public static class CheckReportMapper implements RowMapper<IniReport>{

        @Override
        public IniReport mapRow(ResultSet r, int i) throws SQLException {
            IniReport t = new IniReport();

            t.setId(r.getString("id"));
            t.setChannelId(r.getString("channel_id"));
            t.setChannelName(r.getString("channel_name"));
            t.setCompId(r.getString("comp_id"));
            t.setCompName(r.getString("comp_name"));
            t.setTaskId(r.getString("task_id"));
            t.setTaskDetail(r.getString("task_detail"));
            t.setServiceId(r.getString("service_id"));
            t.setServiceName(r.getString("service_name"));
            t.setCycleName(r.getString("cycle_name"));
            t.setEntCompType(r.getInt("ent_comp_type"));
            t.setIndustry(r.getString("industry"));
            t.setEntScale(r.getInt("ent_scale"));
            t.setArea(r.getString("area"));
            t.setCheckTimeFrom(r.getString("check_time_from"));
            t.setCheckTimeTo(r.getString("check_time_to"));
            t.setPrintDetail(r.getString("print_detail"));
            t.setUpdateTime(r.getTimestamp("update_time"));
            t.setCreateTime(r.getTimestamp("create_time"));

            return t;
        }
    }

    public static class CheckReportFullMapper extends CheckReportMapper{
        private final ObjectMapper objectMapper;

        public CheckReportFullMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public IniReport mapRow(ResultSet r, int i) throws SQLException {
            IniReport t = super.mapRow(r, i);

            t.setReportDetail(toObject(r.getString("report_detail"), IniReport.ReportDetail.class, new IniReport.ReportDetail()));
            t.setCompBaseInfo(toObject(r.getString("comp_base_info"), IniReport.CompBaseInfo.class, new IniReport.CompBaseInfo()));
            t.setSafeProduct(toObject(r.getString("safe_product"), IniReport.SafeProduct.class, new IniReport.SafeProduct()));

            return t;
        }

        private <T> T toObject(String v, Class<T> clazz, T defaultObject){
            if(StringUtils.isBlank(v)){
                return defaultObject;
            }
            try{
                return objectMapper.readValue(v, clazz);
            }catch (Exception e){
                throw new RuntimeException("Json to list ind ctg fail");
            }
        }
    }
}