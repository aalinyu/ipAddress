package org.aalin.common.ip;

import org.aalin.common.ip.model.IpLookupResult;

/**
 * 查询接口
 *
 * @author shuijing
 */
public interface IpLookup {
    /**
     * 顺序查询ip地理位置
     *
     * <p>顺序查询 任何一个接口查询到数据就返回 1、新浪接口 超时时间200ms 2、百度接口 超时时间200ms 3、本地库
     *
     * @param ip 格式如："113.204.226.234"
     * @return IpLookupResult country:国家 province:省 city:城市 district:地区
     * country、city、district返回值有可能为空
     */
    IpLookupResult lookup(String ip);

    /**
     * 只查询本地库
     *
     * @param ip          格式如："113.204.226.234"
     * @return IpLookupResult country:国家 province:省 city:城市 district:地区
     * country、city、district返回值有可能为空
     */
    IpLookupResult lookupLocal(String ip);
}
