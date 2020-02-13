package com.ts.server.safe.company.dao;

import com.ts.server.safe.common.id.IdGenerator;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.company.domain.OccDiseaseJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * 职业病岗位数据操作
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Repository
public class OccDiseaseJobDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator<String> idGenerator;

    private final RowMapper<OccDiseaseJob> mapper = (r, i) -> {
        OccDiseaseJob t = new OccDiseaseJob();

        t.setId(r.getString("id"));
        t.setCompId(r.getString("comp_id"));
        t.setJob(r.getString("job"));
        t.setJobWay(r.getString("job_way"));
        t.setJobFormal(r.getString("job_formal"));
        t.setRisks(DaoUtils.toArray(r.getString("risks")));
        t.setRemark(r.getString("remark"));
        t.setCreateTime(r.getTimestamp("create_time"));

        return t;
    };

    @Autowired
    public OccDiseaseJobDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.idGenerator = IdGenerators.seqId(dataSource, "seq_occ_disease_job", e -> String.format("%02d", e));
    }

    public String insert(OccDiseaseJob t){
        final String sql = "INSERT INTO c_occ_disease_job (id, comp_id, job, job_way, job_formal, risks, remark, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, now())";
        String id = idGenerator.generate();
        jdbcTemplate.update(sql, id, t.getCompId(), t.getJob(), t.getJobWay(), t.getJobFormal(), DaoUtils.join(t.getRisks()), t.getRemark());

        return id;
    }

    public boolean update(OccDiseaseJob t){
        final String sql = "UPDATE c_occ_disease_job SET job = ?, job_way = ?, job_formal = ?, risks = ?, remark = ? WHERE id = ?";
        return jdbcTemplate.update(sql, t.getJob(), t.getJobWay(), t.getJobFormal(), DaoUtils.join(t.getRisks()), t.getRemark()) > 0;
    }

    public boolean delete(String id){
        final String sql = "DELETE FROM c_occ_disease_job WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public OccDiseaseJob findOne(String id){
        final String sql = "SELECT * FROM c_occ_disease_job WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, mapper);
    }

    public Long count(String compId, String job){
        final String sql = "SELECT COUNT(id) FROM c_occ_disease_job WHERE comp_id = ? AND job LIKE ?";
        String jobLike = DaoUtils.like(job);
        return jdbcTemplate.queryForObject(sql, new Object[]{compId, jobLike}, Long.class);
    }

    public List<OccDiseaseJob> find(String compId, String job, int offset, int limit){
        final String sql = "SELECT * FROM c_occ_disease_job WHERE comp_id = ? AND job LIKE ORDER BY id ASC LIMIT ? OFFSET ?";
        String jobLike = DaoUtils.like(job);
        return jdbcTemplate.query(sql, new Object[]{compId, jobLike, limit, offset}, mapper);
    }
}
