package org.aalin.common.ip;

import org.aalin.common.ip.enums.IpLookupEnum;
import org.aalin.common.ip.executor.IpLookupExecutor;
import org.aalin.common.ip.executor.IpLookupProvider;
import org.aalin.common.ip.model.IpAddress;
import org.aalin.common.ip.model.IpLookupResult;

import static org.aalin.common.ip.executor.impl.LocalRepositoryLookupExecutor.randomIp;

/**
 * @author shuijing
 */
public class IPTest {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            IpLookupResult result = DefaultIpLookup.newInstance().lookup(randomIp());
            System.out.println(result);
        }
        testBaiduInterface();
        testLocalInterface();
        testSinaInterface();
    }

    private static void testBaiduInterface() {
        IpLookupExecutor executor = IpLookupProvider.getInstance().get(IpLookupEnum.BAIDU);
        IpAddress lookup = executor.lookup(randomIp());
        System.out.printf(lookup == null ? "返回空" : lookup.toString());
    }

    private static void testSinaInterface() {
        IpLookupExecutor executor = IpLookupProvider.getInstance().get(IpLookupEnum.SINA);
        IpAddress lookup = executor.lookup("47.100.217.13");
        System.out.printf(lookup == null ? "返回空" : lookup.toString());
    }

    private static void testLocalInterface() {
        //IpLookupExecutor executor = IpLookupProvider.getInstance().get(IpLookupEnum.LOCAL);
        long startTime = System.currentTimeMillis();
        IpLookupResult ipLookupResult = DefaultIpLookup.newInstance().lookupLocal(randomIp());
        //IpAddress lookup = executor.lookup(randomIp());
        System.out.println("花费时间： " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println(ipLookupResult == null ? "返回空" : ipLookupResult.toString());
    }
}
