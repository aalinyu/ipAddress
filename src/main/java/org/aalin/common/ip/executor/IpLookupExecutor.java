package org.aalin.common.ip.executor;

import java.lang.annotation.*;

import org.aalin.common.ip.enums.IpLookupEnum;

import org.aalin.common.ip.model.IpAddress;

/**
 * Created by shuijing on 2016/7/14
 */
public interface IpLookupExecutor {

    /**
     * 查询接口超时时间
     */
    int TIME_OUT = 200;

    IpAddress lookup(String ip);

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface IpLookupType {
        IpLookupEnum type();
    }
}
