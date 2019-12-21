package com.ts.server.safe.security.kaptcha;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix = "kaptcha")
public class KaptchaProperties {
    private int width = 125;
    private int height = 45;
    private boolean border = true;
    private String borderColor = "105,179,90";
    private String fontColor = "blue";
    private int fontSize = 45;
    private int charLength = 4;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getCharLength() {
        return charLength;
    }

    public void setCharLength(int charLength) {
        this.charLength = charLength;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("width", width)
                .append("height", height)
                .append("border", border)
                .append("borderColor", borderColor)
                .append("fontColor", fontColor)
                .append("fontSize", fontSize)
                .append("charLength", charLength)
                .toString();
    }
}
