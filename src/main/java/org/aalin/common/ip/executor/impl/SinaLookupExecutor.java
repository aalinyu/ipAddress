package org.aalin.common.ip.executor.impl;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Throwables;
import org.aalin.common.ip.enums.IpLookupEnum;
import org.aalin.common.ip.executor.IpLookupExecutor;
import org.aalin.common.ip.model.IpAddress;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author shuijing
 */
@IpLookupExecutor.IpLookupType(type = IpLookupEnum.SINA)
public class SinaLookupExecutor implements IpLookupExecutor {

    private final Logger logger = LoggerFactory.getLogger(SinaLookupExecutor.class);
    public static String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json";

    private static class SinaLookupHolder {
        private static SinaLookupExecutor INSTANCE = new SinaLookupExecutor();
    }

    private SinaLookupExecutor() {
    }

    public static SinaLookupExecutor getInstance() {
        return SinaLookupHolder.INSTANCE;
    }

    @Override
    public IpAddress lookup(String ip) {

        String requestUrl = url + "&ip=" + ip;
        try {
            String msg =
                    HttpRequest.get(requestUrl).connectTimeout(TIME_OUT).readTimeout(TIME_OUT).body();
            if (msg == null) {
                logger.info("查询新浪ip库接口返回null，IP：{},statusCode:{}", ip, msg);
            } else {
                if (msg.contains("{") || msg.contains("}")) {
                    Map body = (Map) JSON.parse(msg);
                    Integer ret = (Integer) body.get("ret");
                    if ("-1".equals(ret)) {
                        return null;
                    }
                    String country = (String) body.get("country");
                    String province = (String) body.get("province");
                    String city = (String) body.get("city");
                    String district = (String) body.get("district");
                    String ips = (String) body.get("ips");

                    IpAddress ipAddress = new IpAddress();
                    ipAddress.setProvince(province);
                    ipAddress.setCity(city);
                    ipAddress.setDistrict(district);
                    ipAddress.setCountry(country);
                    logger.info("查询新浪接口成功，IP：{},info:{}", ip, ipAddress);
                    return ipAddress;
                }
            }
        } catch (Exception e) {
            Throwable throwable = Throwables.getRootCause(e);
            if (throwable instanceof ConnectTimeoutException
                    || throwable instanceof java.net.SocketTimeoutException
                    || throwable instanceof ConnectException) {
                logger.error("查询新浪接口，查询IP:{}返回状态码:{}", ip, e.getMessage());
            } else if (throwable instanceof UnknownHostException) {
                logger.error("查询新浪接口，查询IP:{}java.net.UnknownHostException:{}", ip, e.getMessage());
            } else {
                logger.error("查询新浪接口失败", e);
            }
        }
        return null;
    }
}
