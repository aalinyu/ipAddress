package org.aalin.common.ip.result;

import org.aalin.common.ip.exception.ResultException;
import org.aalin.common.ip.enums.Status;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shuijing
 */
@Data
public class StandardResultInfo {

    private static final long serialVersionUID = -656083826615770161L;
    public static final String ORDER_CHECK_FAIL_CODE = "ORDER_CHECK_FAIL_CODE";

    /**
     * 结果状态
     */
    protected Status status;

    /**
     * 信息码
     */
    protected String code;

    /**
     * 描述
     */
    protected String description;

    /**
     * 结果转换的异常
     */
    protected ResultException resultException;

    /**
     * 参数map
     */
    protected Map<Object, Object> params = new LinkedHashMap<Object, Object>();

    /**
     * 构建一个 StandardResultInfo 。
     */
    public StandardResultInfo() {
    }

    /**
     * 构建一个 StandardResultInfo 。
     *
     * @param status 结果状态。
     */
    public StandardResultInfo(Status status) {
        this(status, null, null);
    }

    /**
     * 构建一个 StandardResultInfo 。
     *
     * @param status 结果状态。
     * @param code   信息码。
     */
    public StandardResultInfo(Status status, String code) {
        this(status, code, null);
    }

    /**
     * 构建一个 StandardResultInfo 。
     *
     * @param status      结果状态。
     * @param code        信息码。
     * @param description 描述。
     */
    public StandardResultInfo(Status status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }
}
