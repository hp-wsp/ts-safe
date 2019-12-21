package com.ts.server.safe.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据操作工具类
 * 
 * @author WangWei
 */
public class DaoUtils {
	public static final String DATA_SEPARATOR = "$";
	
	/**
	 * 列表变成字符串
	 * 
	 * @param list 集合
	 * @return 使用"$"分割字符串
	 */
	public static String join(List<? extends Object> list){
		return StringUtils.join(list, DATA_SEPARATOR);
	}
	
	/**
	 * 列表变成字符串
	 * 
	 * @param list 集合
	 * @return 使用"$"分割字符串
	 */
	public static <T extends Object> String join(T[] list){
		return StringUtils.join(list, DATA_SEPARATOR);
	}
	
	/**
	 * 字符串变成集合
	 * 
	 * @param v 字符串
	 * @return 字符串集合
	 */
	public static List<String> toList(String v){
		return Arrays.asList(toArray(v));
	}
	
	/**
	 * 字符串变成数组
	 * 
	 * @param v 字符串
	 * @return 字符串数组
	 */
	public static String[] toArray(String v){
		return StringUtils.isBlank(v) ? new String[0] : StringUtils.split(v, DATA_SEPARATOR);
	}
	
	/**
	 * 字符串变成数字集合
	 * 
	 * @param v 值
	 * @return 整形集合
	 */
	public static List<Integer> toIntList(String v){
		return toList(v).stream().map(Integer::valueOf).collect(Collectors.toList());
	}
	
	/**
	 * 字符串转换成数字数组
     *
	 * @param v 值
	 * @return 整形数组
	 */
	public static int[] toIntArray(String v){
		List<Integer> list = toIntList(v);
		return list.stream().mapToInt(Integer::intValue).toArray();
	}
	
	/**
	 * 模糊查询
	 * 
	 * @param v 查询条件
	 * @return 加"%"变成模糊查询
	 */
	public static String like(String v){
		return StringUtils.isBlank(v) ? "%" : "%" + v + "%";
	}
	
	/**
	 * 为空时模糊查询
	 * 
	 * @param v 查询条件
	 * @return 为空返回"%"
	 */
	public static String blankLike(String v){
		return StringUtils.isBlank(v) ? "%" : v;
	}
	
	/**
	 * 把数字转换指定长度第字符串，长度不够用0填补
	 * 
	 * @param n    转换数字
	 * @param len  转换后字符串长度
	 * @return 字符串
	 */
	public static String zeroFill(Integer n, Integer len){
		String number = String.valueOf(n);
        int l = number.length() >= len ? 0 : number.length();
        return StringUtils.repeat("0", len - l) + number;
    }
	
	/**
	 * 设置操作为utf8mb4编码
	 * 
	 * @param jdbcTemplate
	 */
	public static void setUtf8mb4(JdbcTemplate jdbcTemplate){
		jdbcTemplate.execute("set names utf8mb4");
	}
	
	/**
	 * 换行成java.sql.Date
	 * 
	 * @param date java.util.date日期
	 * @return
	 */
	public static Date date(java.util.Date date){
		return date == null ? null: new Date(date.getTime());
	}
	
	/**
	 * 转换为java.sql.Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp timestamp(java.util.Date date){
		return date == null? null: new Timestamp(date.getTime());
	}

    /**
     * 添加查询参数偏移量
     *
     * @param params  查询参数
     * @param offset  查询偏移量
     * @param limit   查询记录数
     * @return 查询参数
     */
	public static Object[] appendOffsetLimit(Object[] params, int offset, int limit){
	    int len = params.length + 2;
        Object[] newParams = Arrays.copyOf(params, len);
        newParams[len - 2] = limit;
        newParams[len - 1] = offset;
        return newParams;
    }
}
