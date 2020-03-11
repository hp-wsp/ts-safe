package com.ts.server.safe.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;

/**
 * 中文帮助工具类
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ZhCnUtils {
    private static final String[] ZH_NUMBERS = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    private static final String[] ZH_UIT = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

    /**
     * 阿拉伯数字转换成中文数字
     *
     * @param number 数字
     * @return 中文字符数字
     */
    public static String toChinese(int number) {

        if(number == 0){
            return "零";
        }

        if(number == 10){
            return "十";
        }

        String str = String.valueOf(number);
        StringBuilder result = new StringBuilder();
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result.append(ZH_NUMBERS[num]).append(ZH_UIT[n - 2 - i]);
            } else {
                result.append(ZH_NUMBERS[num]);
            }
        }

        String s = result.toString();

        return StringUtils.endsWith(s, "零")? StringUtils.left(s, StringUtils.length(s) -1): s;
    }

    private static final String[] ZH_DATE_NUMBERS = { "0", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

    public static String getCurrentCNDate() {

        Calendar cal = Calendar.getInstance(); // 使用日历类
        String year = String.valueOf(cal.get(Calendar.YEAR)); // 得到年
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1); // 得到月，因为从0开始的，所以要加1
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)); // 得到天

        StringBuilder cnDate = new StringBuilder(20);
        year.chars().forEach(e -> cnDate.append(ZH_DATE_NUMBERS[e - 48]));
        cnDate.append("年");

        month.chars().forEach(e -> cnDate.append(ZH_DATE_NUMBERS[e - 48]));
        cnDate.append("月");

        char[] dayChars = day.toCharArray();
        if(dayChars.length == 2){
            if(dayChars[1] - 48 != 1){
                cnDate.append(ZH_DATE_NUMBERS[dayChars[1] - 48]);
            }
            cnDate.append("十");
            if(dayChars[0] - 48 != 0){
                cnDate.append(ZH_DATE_NUMBERS[dayChars[0] - 48]);
            }
        }else{
            cnDate.append(ZH_DATE_NUMBERS[dayChars[0] - 48]);
        }
        cnDate.append("日");

        return cnDate.toString();
    }
}
