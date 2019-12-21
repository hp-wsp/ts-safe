package com.ts.server.safe.security.kaptcha.code;

import com.ts.server.safe.BaseException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

/**
 * 通过数据实现验证码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Transactional(readOnly = true)
public class JdbcKaptchaCodeService implements KaptchaCodeService {
    private final JdbcTemplate jdbcTemplate;
    private final int expireSeconds;

    /**
     * 构造{@link JdbcKaptchaCodeService}
     *
     * @param dataSource {@link DataSource}
     * @param expireSeconds 验证码过期时间(秒)
     */
    public JdbcKaptchaCodeService(DataSource dataSource, int expireSeconds){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.expireSeconds = expireSeconds;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String codeKey, String code) {
        final String sql = "INSERT INTO l_kaptcha (code_key, code_value, create_time) VALUES (?, ?, now())";
        try{
            jdbcTemplate.update(sql, codeKey, code);
        }catch (DataAccessException e){
            throw new BaseException("验证码KEY已经存在");
        }
    }

    @Override
    public Optional<String> get(String codeKey) {
        final String sql = "SELECT code_value FROM l_kaptcha WHERE code_key = ?";
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{codeKey}, String.class));
        }catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String codeKey) {
        final String sql = "DELETE FROM l_kaptcha WHERE code_key = ?";
        jdbcTemplate.update(sql, codeKey);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void clearExpired() {
        LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(expireSeconds * -1);
        final String sql = "DELETE FROM l_kaptcha WHERE create_time < ?";
        jdbcTemplate.update(sql, expiredTime);
    }
}
