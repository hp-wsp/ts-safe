package com.ts.server.safe.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * HTTP工具类
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class HttpUtils {

    /**
     * 获取客户端访问地址
     *
     * @param request {@link HttpServletRequest}
     * @return 客户端地址
     */
    public static String getHostname(HttpServletRequest request){
        String[] httpHeads = new String[]{"X-Real-IP","X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"};

        String hostname = "";

        for(String head: httpHeads){
            String v = request.getHeader(head);
            if(StringUtils.isNotBlank(v) && !StringUtils.equalsIgnoreCase(v, "unknown")){
                hostname = v;
                break;
            }
        }

        if(StringUtils.isBlank(hostname)){
            hostname = request.getRemoteAddr();
        }

        return StringUtils.isBlank(hostname)? "0.0.0.0" : hostname;
    }

    /**
     * 设置Http header Content-Disposition
     *
     * @param response {@link HttpServletResponse}
     * @param filename 文件名
     * @param suffer 后缀名
     */
    public static void setContentDisposition(HttpServletResponse response, String filename, String suffer){
        setContentDisposition(response, String.format("%s.%s", filename, suffer));
    }

    /**
     * 设置Http header Content-Disposition
     *
     * @param response {@link HttpServletResponse}
     * @param filename 文件名
     */
    public static void setContentDisposition(HttpServletResponse response, String filename){
        try{
            final String charset = "UTF-8";
            String name =  charset + "''"+ URLEncoder.encode(filename, charset);
            response.setHeader("Content-Disposition", "attachment; filename*=" + name);
        }catch (UnsupportedEncodingException e){
            //none instance
        }
    }
}
