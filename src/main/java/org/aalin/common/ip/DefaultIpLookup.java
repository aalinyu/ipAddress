package org.aalin.common.ip;

import org.aalin.common.ip.enums.IPResultEnum;
import org.aalin.common.ip.enums.IpLookupEnum;
import org.aalin.common.ip.enums.Status;
import org.aalin.common.ip.executor.IpLookupExecutor;
import org.aalin.common.ip.executor.IpLookupProvider;
import org.aalin.common.ip.model.IpAddress;
import org.aalin.common.ip.model.IpLookupResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shuijing
 */
@Slf4j
public class DefaultIpLookup implements IpLookup {

    private IpLookupProvider ipLookupProvider;
    public static final String SUCCESS_DESCRIPTION = "查询成功";
    public static final String FAIL_DESCRIPTION = "查询失败";
    public static final String PARAM_NULL_DESCRIPTION = "参数ip为空";

    private static class IpLookupImplHolder {
        private static DefaultIpLookup INSTANCE = new DefaultIpLookup();
    }

    private DefaultIpLookup() {
    }

    public static DefaultIpLookup newInstance() {
        return IpLookupImplHolder.INSTANCE;
    }

    @Override
    public IpLookupResult lookup(String ip) {
        return lookupLocal(ip, false);
    }

    @Override
    public IpLookupResult lookupLocal(String ip) {
        return lookupLocal(ip, true);
    }

    protected IpLookupResult lookupLocal(String ip, boolean lookupLocal) {
        IpLookupResult ipLookupResult = new IpLookupResult();
        try {
            if (ip == null || "".equals(ip)) {
                ipLookupResult.setStatus(Status.FAIL);
                ipLookupResult.setDescription(PARAM_NULL_DESCRIPTION);
                ipLookupResult.setCode(IPResultEnum.LOOKUP_FAILED.getCode());
                return ipLookupResult;
            }
            if (ipLookupProvider == null) {
                ipLookupProvider = IpLookupProvider.getInstance();
            }
            if (lookupLocal) {
                ipLookupResult = getLocalLookupResult(ipLookupResult, ip);
            } else {
                /** 查询顺序 1、新浪接口 超时时间100ms 2、百度接口 超时时间100ms 3、本地库 */
                log.info("收到ip查询请求,ip:{}", ip);

                long st = System.currentTimeMillis();
                IpLookupExecutor sinaLookup = ipLookupProvider.get(IpLookupEnum.BAIDU);
                IpAddress ipAddress = sinaLookup.lookup(ip);
                if (ipAddress != null) {
                    ipLookupResult = ipIntoToipResult(ipAddress);
                } else {
                    IpLookupExecutor baiduLookup = ipLookupProvider.get(IpLookupEnum.SINA);
                    ipAddress = baiduLookup.lookup(ip);
                    if (ipAddress != null) {
                        ipLookupResult = ipIntoToipResult(ipAddress);
                    } else {
                        ipLookupResult = getLocalLookupResult(ipLookupResult, ip);
                    }
                }
                long et = System.currentTimeMillis();
                log.info("ip查询请求完成,消耗 {} ms,ip:{},result:{}", (et - st), ip, ipLookupResult);
            }
        } catch (Exception e) {
            log.error("ip查询请求失败", e);
            ipLookupResult.setStatus(Status.FAIL);
            ipLookupResult.setDescription(FAIL_DESCRIPTION);
            ipLookupResult.setCode(IPResultEnum.LOOKUP_FAILED.getCode());
        }
        return ipLookupResult;
    }

    private IpLookupResult getLocalLookupResult(IpLookupResult ipLookupResult, String ip) {
        IpLookupExecutor localLookup = ipLookupProvider.get(IpLookupEnum.LOCAL);
        IpAddress ipAddress = localLookup.lookup(ip);
        if (ipAddress != null) {
            ipLookupResult = ipIntoToipResult(ipAddress);
        } else {
            ipLookupResult.setStatus(Status.FAIL);
            ipLookupResult.setDescription(FAIL_DESCRIPTION);
            ipLookupResult.setCode(IPResultEnum.LOOKUP_FAILED.getCode());
        }
        return ipLookupResult;
    }

    private IpLookupResult ipIntoToipResult(IpAddress ipAddress) {
        if (ipAddress == null) return null;
        IpLookupResult ipLookupResult = new IpLookupResult();
        ipLookupResult.setIpAddress(ipAddress);
        ipLookupResult.setStatus(Status.SUCCESS);
        ipLookupResult.setDescription(SUCCESS_DESCRIPTION);
        ipLookupResult.setCode(IPResultEnum.EXECUTE_SUCCESS.getCode());
        return ipLookupResult;
    }
}
