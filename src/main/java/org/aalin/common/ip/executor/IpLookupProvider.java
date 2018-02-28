package org.aalin.common.ip.executor;

import java.util.EnumMap;

import org.aalin.common.ip.executor.impl.BaiduIpLookupExecutor;
import org.aalin.common.ip.executor.impl.LocalRepositoryLookupExecutor;
import org.aalin.common.ip.enums.IpLookupEnum;
import org.aalin.common.ip.exception.AppException;
import org.aalin.common.ip.executor.impl.SinaLookupExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author shuijing
 */
public class IpLookupProvider {

    private static Logger logger = LoggerFactory.getLogger(IpLookupProvider.class);
    private static EnumMap<IpLookupEnum, IpLookupExecutor> queryMap =
            new EnumMap<IpLookupEnum, IpLookupExecutor>(IpLookupEnum.class);

    static {
        BaiduIpLookupExecutor baiduIpLookup = BaiduIpLookupExecutor.getInstance();
        LocalRepositoryLookupExecutor localRepositoryLookup =
                LocalRepositoryLookupExecutor.getInstance();
        SinaLookupExecutor sinaLookup = SinaLookupExecutor.getInstance();
        queryMap.put(
                baiduIpLookup.getClass().getAnnotation(IpLookupExecutor.IpLookupType.class).type(),
                baiduIpLookup);
        queryMap.put(
                localRepositoryLookup.getClass().getAnnotation(IpLookupExecutor.IpLookupType.class).type(),
                localRepositoryLookup);
        queryMap.put(
                sinaLookup.getClass().getAnnotation(IpLookupExecutor.IpLookupType.class).type(),
                sinaLookup);
    }

    private static class IpLookupProviderHolder {
        private static IpLookupProvider INSTANCE = new IpLookupProvider();
    }

    private IpLookupProvider() {
    }

    public static IpLookupProvider getInstance() {
        return IpLookupProviderHolder.INSTANCE;
    }

    public IpLookupExecutor get(IpLookupEnum ipLookupEnum) {
        Assert.notNull(ipLookupEnum, "ip查询类型不能为空");
        IpLookupExecutor ipLookupExecutor = queryMap.get(ipLookupEnum);
        if (ipLookupExecutor == null) {
            throw new AppException(ipLookupEnum + "ip查询实例不存在");
        }
        return ipLookupExecutor;
    }
}
