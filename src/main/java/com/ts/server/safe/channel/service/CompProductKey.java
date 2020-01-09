package com.ts.server.safe.channel.service;

/**
 * 企业信息key
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public enum  CompProductKey {
    KRBFCCS("krbfccs", 0, "涉及可燃爆粉作业场所"),
    PTCS("ptcs", 0, "喷涂作业场所"),
    YXCS("yxcs", 0, "有限作业场所"),
    SAZNQY("saznqy", 0, "涉氨制冷企业"),
    CPWXQY("cpwxqy", 0, "船舶维修企业"),
    YJQY("zjqy", 0, "冶金企业"),
    WHXP("whxp", 0, "危化学品"),
    YHBZQY("yhbzqy", 0, "烟花爆竹企业"),
    KSQY("ksqy", 0, "矿山企业"),
    SJZDAQSCYH("sjzdaqscyh", 0, "是否涉及重大安全生产隐患");

    private final String key;
    private final int defValue;
    private final String remark;

    CompProductKey(String key, int defValue, String remark) {
        this.key = key;
        this.defValue = defValue;
        this.remark = remark;
    }

    public String getKey() {
        return key;
    }

    public int getDefValue() {
        return defValue;
    }

    public String getRemark() {
        return remark;
    }
}
