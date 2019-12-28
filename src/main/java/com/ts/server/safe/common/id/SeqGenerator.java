package com.ts.server.safe.common.id;

import com.ts.server.safe.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.function.Function;

/**
 * My sql sequence
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SeqGenerator<T> implements IdGenerator<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeqGenerator.class);

    private final JdbcTemplate jdbcTemplate;
    private final String tableName;
    private final String incKey;
    private final Function<Integer, T> convertFun;
    private final String selectSql;
    private final String updateSql;

    /**
     * 构造{@link SeqGenerator}
     *
     * @param dataSource {@link DataSource}
     * @param incKey 自增KEY
     * @param convertFun 转换序列值
     */
    public SeqGenerator(DataSource dataSource, String incKey, Function<Integer, T> convertFun){
        this(dataSource, "s_sequence", incKey, convertFun);
    }

    /**
     * 构造{@link SeqGenerator}
     *
     * @param dataSource {@link DataSource}
     * @param tableName 自增值表
     * @param incKey 自增KEY
     * @param convertFun 转换序列值
     */
    public SeqGenerator(DataSource dataSource, String tableName, String incKey,
                        Function<Integer, T> convertFun) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.tableName = tableName;
        this.incKey = incKey;
        this.convertFun = convertFun;
        this.selectSql = String.format("SELECT cur_value FROM %s WHERE seq_key = ? FOR UPDATE", tableName);
        this.updateSql = String.format("UPDATE %s SET cur_value = ? WHERE seq_key = ?", tableName);
    }

    @Override
    public T generate() {
        Integer c = incSeq(tableName, incKey);
        return convertFun.apply(c);
    }

    /**
     * 增加序列值
     *
     * @param tableName 自增值表
     * @param incKey 自增KEY
     * @return 自增值
     */
    protected Integer incSeq(String tableName, String incKey){
        Integer value = jdbcTemplate.queryForObject(selectSql, new Object[]{incKey}, Integer.class);
        int inc = value == null? 1: value + 1;
        LOGGER.debug("Inc seq table={}, key={}, inc={}", tableName, incKey, inc);
        if(!(jdbcTemplate.update(updateSql, inc, incKey) > 0)){
            throw new BaseException("增加序列失败");
        }
        return inc;
    }
}
