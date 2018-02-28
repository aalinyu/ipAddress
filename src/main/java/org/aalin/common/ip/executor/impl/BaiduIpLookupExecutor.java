package org.aalin.common.ip.executor.impl;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import org.aalin.common.ip.executor.IpLookupExecutor;
import org.aalin.common.ip.enums.IpLookupEnum;
import org.aalin.common.ip.model.IpAddress;
import lombok.Data;
import org.apache.http.conn.ConnectTimeoutException;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shuijing
 */
@Data
@IpLookupExecutor.IpLookupType(type = IpLookupEnum.BAIDU)
public class BaiduIpLookupExecutor implements IpLookupExecutor {

    private final Logger logger = LoggerFactory.getLogger(BaiduIpLookupExecutor.class);
    public static final String url = "http://api.map.baidu.com/location/ip";
    public static final String AK = "ZTDZTGQ7lOpXbL2CuGmk9IoGUHTHAK9f";

    private static class BaiduIpLookupHolder {
        private static BaiduIpLookupExecutor INSTANCE = new BaiduIpLookupExecutor();
    }

    private BaiduIpLookupExecutor() {
    }

    public static BaiduIpLookupExecutor getInstance() {
        return BaiduIpLookupHolder.INSTANCE;
    }

    @Override
    public IpAddress lookup(String ip) {
        Map<String, String> para = Maps.newHashMap();
        para.put("ak", AK);
        para.put("ip", ip);
        try {
            String msg =
                    HttpRequest.post(url, para, false).connectTimeout(TIME_OUT).readTimeout(TIME_OUT).body();
            if (msg == null) {
                logger.info("查询百度ip库接口返回空，IP：{}", ip);
            } else {
                Map body = (Map) JSON.parse(msg);
                if ((Integer) body.get("status") == 0) {
                    Map cotent = ((Map) body.get("content"));
                    Map address_detail = (Map) (cotent).get("address_detail");
                    String province = (String) address_detail.get("province");
                    String city = (String) address_detail.get("city");
                    String district = (String) address_detail.get("district");
                    IpAddress ipAddress = new IpAddress();
                    ipAddress.setProvince(province);
                    ipAddress.setCity(city);
                    ipAddress.setDistrict(district);
                    logger.info("查询百度ip库接口成功，IP：{},info{} ", ip, ipAddress);
                    return ipAddress;
                } else if ((Integer) body.get("status") == 1) {
                    logger.info("查询百度ip库接口，查询IP:{}返回:{}", ip, (String) body.get("message"));
                }
            }
        } catch (Exception e) {
            Throwable throwable = Throwables.getRootCause(e);
            if (throwable instanceof ConnectTimeoutException
                    || throwable instanceof java.net.SocketTimeoutException
                    || throwable instanceof ConnectException) {
                logger.error("查询百度ip库接口，查询IP:{}返回:{}", ip, e.getMessage());
            } else if (throwable instanceof UnknownHostException) {
                logger.error("查询百度ip库接口，查询IP:{}java.net.UnknownHostException:{}", ip, e.getMessage());
            } else {
                logger.error("查询百度ip库接口失败", e);
            }
        }
        return null;
    }
}
