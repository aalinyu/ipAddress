package org.aalin.common.ip;

import org.aalin.common.ip.model.IpLookupResult;

/**
 * @Filename IpLookupService.java @Description 查询ip地理位置 @Version 1.0 @Author shuijing @Email
 * @History <li>Author: shuijing
 * <li>Date: 2016-07-15
 * <li>Version: 1.0
 * <li>Content: create
 */
public interface IpLookup {
    /**
     * 顺序查询ip地理位置
     * <p>
     * <p>顺序查询 任何一个接口查询到数据就返回 1、新浪接口 超时时间200ms 2、百度接口 超时时间200ms 3、本地库
     *
     * @param ip 格式如："113.204.226.234"
     * @return IpLookupResult country ip所在国家 province ip所在省 city ip所在城市 district ip所在地区
     * country、city、district返回值有可能为空
     */
    IpLookupResult lookup(String ip);

    /**
     * ip地址本地库查询
     *
     * @param ip                格式如："113.204.226.234"
     * @param isOnlyLookupLocal true 表示只查询本地库 false 顺序查询 1、新浪接口 超时时间200ms 2、百度接口 超时时间200ms 3、本地库
     * @return IpLookupResult country ip所在国家 province ip所在省 city ip所在城市 district ip所在地区
     * country、city、district返回值有可能为空
     */
    IpLookupResult lookup(String ip, boolean isOnlyLookupLocal);
}
