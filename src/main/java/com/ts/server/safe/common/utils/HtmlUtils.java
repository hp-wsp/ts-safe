package com.ts.server.safe.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * HTML操作工具类
 *
 * @author WangWei
 */
public class HtmlUtils {
    private static final Pattern HTML_TAG_PATTER = Pattern.compile("<[^>]*>");
    private static final Pattern HTML_A_TAG_PATTER = Pattern.compile("<a href[^>]*>");
    private static final Pattern HTML_IMG_TAG_PATTER = Pattern.compile("<img[^>]*/>");
    private static final Pattern HTML_TRANSFER_PATTER = Pattern.compile("&[a-zA-Z]{1,10}");

    /**
     * 删除字符串中HTML标签、空格和回车
     *
     * @param v 传入字符串
     * @return  删除标签后的字符串
     */
    public static String removeHtmlTag(String v){
        String c = HTML_TAG_PATTER.matcher(v).replaceAll("");
        return StringUtils.removePattern(c, "([\\r\\n])[\\s]+");
    }

    /**
     * 生成跳转页面HTML
     *
     * @param url 跳转地址
     * @return 页面
     */
    public static String redirectHtml(String url){
        return String.format("<!DOCTYPE html>"
                + "<html lang='zh-CN'>"
                + "<body><script type='text/javascript'>window.location.href= '%s';</script></body>"
                + "</html>", url);
    }

    /**
     * 得到传入内容text文本
     *
     * @param content html内容
     * @param max     text文本最大长度
     * @return
     */
    public static String text(String content, int max){
        if(StringUtils.isBlank(content)){
            return "";
        }

        String c = HTML_A_TAG_PATTER.matcher(content).replaceAll("");
        c = HTML_IMG_TAG_PATTER.matcher(c).replaceAll("");
        c = HTML_TRANSFER_PATTER.matcher(c).replaceAll("");
        c = removeHtmlTag(c);
        return c.length() > max ? c.substring(0, max) : c;
    }
}
