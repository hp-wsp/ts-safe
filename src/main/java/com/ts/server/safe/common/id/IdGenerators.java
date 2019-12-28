package com.ts.server.safe.common.id;

import javax.sql.DataSource;
import java.util.function.Function;

/**
 * 自动生成ID工具类
 * 
 * @author WangWei
 */
public class IdGenerators {
	private static IdGenerator<String> uuIdGenerator = new UUIDGenerator();
	
	/**
	 * 通过UUID生成ID
	 * 
	 * @return 编号
	 */
	public static String uuid(){
		return uuIdGenerator.generate();
	}

	/**
	 * 通过Sequence生成ID
	 *
	 * @param dataSource {@link DataSource}
	 * @param incKey 自增KEY
	 * @param convertFun 转换ID方法
	 * @param <T> ID数据类型
	 * @return {@link IdGenerator}
	 */
	public static <T> IdGenerator<T> seqId(DataSource dataSource, String incKey, Function<Integer, T> convertFun){
		return new SeqGenerator<>(dataSource, incKey, convertFun);
	}
}
