package org.aalin.common.ip.enums;

/**
 * @author shuijing
 */
public enum IpLookupEnum {
    BAIDU("BAIDU", "百度查询"),
    SINA("SINA", "新浪查询"),
    LOCAL("LOCAL", "本地查询");

    private String code;
    private String msg;

    IpLookupEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
