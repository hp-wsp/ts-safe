package com.ts.server.safe.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * 安全工具类
 *
 * @author WangWei
 */
public class SecurityUtils {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);
    private static final String RANDOM_CONTENT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * 得到随机生成字符串
     *
     * @param len 字符串长度
     * @return 随机字符传
     */
    public static String randomStr(int len){
        return RandomStringUtils.random(len, RANDOM_CONTENT);
    }

    /**
     * SHA-1签证字符串
     *
     * @param c 签名字符串
     * @return 签名字符串
     */
    public static String sha1(String c){
        return sha(c, "", "SHA-1");
    }

    /**
     * SHA-1签证字符串
     *
     * @param c 签名字符串
     * @param p 签名密码
     * @return 签名字符串
     */
    public static String sha1(String c, String p){
        return sha(c, p, "SHA-1");
    }

    /**
     * SHA签名
     *
     * @param c    要签名字符串
     * @param p    签名密码
     * @param alg  签名算
     * @return 签名字符串
     */
    private static String sha(String c, String p, String alg){
        try{
            MessageDigest digest = MessageDigest.getInstance(alg);
            if(StringUtils.isBlank(p)){
                digest.update(p.getBytes());
            }
            byte[] ds = digest.digest(c.getBytes());
            StringBuilder builder = new StringBuilder();
            for(byte d : ds){
                String s = Integer.toHexString(d & 0xFF);
                if(s.length() < 2){
                    builder.append(0);
                }
                builder.append(s);
            }
            String s = builder.toString();
            logger.debug("Receive message sha is {}", s);
            return s;
        }catch(Exception e){
            logger.error("Receive message sha error, error is {}", e.getMessage());
            return "";
        }
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * SHA-256签证字符串
     *
     * @param c 签名字符串
     * @return 签名字符串
     */
    public static String sha256(String c){
        return sha256(c, "");
    }

    /**
     * SHA-256签证字符串
     *
     * @param c 签名字符串
     * @param p 加密密码
     * @return 签名字符串
     */
    public static String sha256(String c, String p){
        return sha(c, p, "SHA-256");
    }

    /**
     * MD5编码
     *
     * @param c           md5编码内容
     * @param charsetName 字符集
     * @return
     */
    public static String md5(String c, String charsetName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            charsetName = StringUtils.isBlank(charsetName) ? "UTF-8" : charsetName;
            StringBuilder sb = new StringBuilder();
            byte[] bytes = md.digest(c.getBytes(charsetName));
            for (byte b : bytes){
                sb.append(byteToHexString(b));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("MD5 encode is fail, error is {}", e.getMessage());
            return "";
        }
    }

    /**
     * MD5编码,缺省编码字符集是UTF-8
     *
     * @param c  md5编码内容
     * @return md5加密字符串
     */
    public static String md5(String c){
        return md5(c , "UTF-8");
    }

}
