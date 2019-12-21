package com.ts.server.safe.common.utils;

import org.apache.commons.lang3.StringUtils;

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
}
