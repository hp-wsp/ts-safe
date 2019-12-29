package com.ts.server.safe.security.kaptcha;

import com.google.code.kaptcha.text.TextProducer;

import java.security.SecureRandom;

/**
 * 创建验证码代码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SecureTextProducer implements TextProducer {
    private static final int len = 4;
    private final SecureRandom random;
    private final char[] chars;
    private final int charsLen;

    public SecureTextProducer(){
        this.random = newRandom();
        this.chars = "abcde2345678gfynmnpwx".toCharArray();
        this.charsLen = chars.length;
    }

    private SecureRandom newRandom(){
        try{
            return SecureRandom.getInstance("SHA1PRNG");
        }catch (Exception e){
            throw new RuntimeException("Create random fail....");
        }
    }

    @Override
    public String getText() {
        char[] c = new char[len];
        for(int i = 0; i < len; i++){
            int index = Math.abs(random.nextInt() % charsLen);
            c[i] = chars[index];
        }
        return new String(c);
    }
}
