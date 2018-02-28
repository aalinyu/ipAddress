package org.aalin.common.ip.enums;

import org.aalin.common.ip.result.Messageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回结果状态。
 */
public enum Status implements Messageable {

    /**
     * 成功
     */
    SUCCESS("success", "成功"),

    /**
     * 失败
     */
    FAIL("fail", "失败"),

    /**
     * 处理中
     */
    PROCESSING("processing", "处理中");

    /**
     * 枚举值码
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    /**
     * 构建一个 Status 。
     *
     * @param code    枚举值码。
     * @param message 枚举描述。
     */
    private Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 得到枚举值码。
     *
     * @return 枚举值码。
     */
    public String getCode() {
        return code;
    }

    /**
     * 得到枚举描述。
     *
     * @return 枚举描述。
     */
    public String getMessage() {
        return message;
    }

    /**
     * 得到枚举值码。
     *
     * @return 枚举值码。
     */
    public String code() {
        return code;
    }

    /**
     * 得到枚举描述。
     *
     * @return 枚举描述。
     */
    public String message() {
        return message;
    }

    /**
     * 通过枚举值码查找枚举值。
     *
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
     */
    public static Status findStatus(String code) {
        for (Status status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("ResultInfo Status not legal:" + code);
    }

    public static Status getByCode(String code) {
        return findStatus(code);
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<Status> getAllStatus() {
        List<Status> list = new ArrayList<Status>();
        for (Status status : values()) {
            list.add(status);
        }
        return list;
    }

    /**
     * 获取全部枚举值码。
     *
     * @return 全部枚举值码。
     */
    public static List<String> getAllStatusCode() {
        List<String> list = new ArrayList<String>();
        for (Status status : values()) {
            list.add(status.code());
        }
        return list;
    }

    /**
     * 判断给定的枚举，是否在列表中
     *
     * @param values 列表
     * @return
     */
    public boolean isInList(Status... values) {
        for (Status e : values) {
            if (this == e) {
                return true;
            }
        }
        return false;
    }
}
