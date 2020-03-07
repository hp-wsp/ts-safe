package com.ts.server.safe.report.dao;

import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.report.domain.ReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 报表检查项目数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com>WangWei</a>
 */
@Repository
public class ReportItemDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReportItem> mapper = (r, i) -> {
        ReportItem t = new ReportItem();

        t.setId(r.getString("id"));
        t.setCompId(r.getString("comp_id"));
        t.setReportId(r.getString("report_id"));
        t.setReportType(ReportItem.ReportType.valueOf(r.getString("report_type")));
        t.setTypeId(r.getString("type_id"));
        t.setTypeName(r.getString("type_name"));
        t.setContent(r.getString("content"));
        t.setDetail(r.getString("detail"));
        t.setLawItem(r.getString("law_item"));
        t.setCheckResult(ReportItem.CheckResult.valueOf(r.getString("check_result")));
        t.setDanDescribe(r.getString("dan_describe"));
        t.setDanSuggest(r.getString("dan_suggest"));
        t.setImages(DaoUtils.toArray(r.getString("images")));
        t.setInsResult(r.getString("ins_result"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public ReportItemDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertBatch(List<ReportItem> items){
        final String sql = "INSERT INTO r_item (id, comp_id, report_id, report_type, type_id, type_name, content, detail, " +
                "law_item, check_result, dan_describe, dan_suggest, images, ins_result, create_time) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";

        jdbcTemplate.batchUpdate(sql, items, items.size(), (ps, t) -> {
            ps.setString(0, t.getId());
            ps.setString(1, t.getCompId());
            ps.setString(2, t.getReportId());
            ps.setString(3, t.getReportType().name());
            ps.setString(4, t.getTypeId());
            ps.setString(5, t.getTypeName());
            ps.setString(6, t.getContent());
            ps.setString(7, t.getDetail());
            ps.setString(8, t.getLawItem());
            ps.setString(9, t.getCheckResult().name());
            ps.setString(10, t.getDanDescribe());
            ps.setString(11, t.getDanSuggest());
            ps.setString(12, DaoUtils.join(t.getImages()));
            ps.setString(13, t.getInsResult());
        });
    }

    public void delete(String reportId){
        final String sql = "DELETE FROM r_item WHERE report_id = ?";
        jdbcTemplate.update(sql, reportId);
    }

    public List<ReportItem> find(String reportId){
        final String sql = "SELECT * FROM r_item WHERE report_id = ? ORDER BY create_time";
        return jdbcTemplate.query(sql, new Object[]{reportId}, mapper);
    }
}
