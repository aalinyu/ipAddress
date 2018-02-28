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
        for (int i = 0; i < 10; i++) {
            IpLookupResult result = DefaultIpLookup.getInstance().lookup(randomIp(), false);
            System.out.println(result);
        }
        //testBaiduInterface();
    }

    private static void testBaiduInterface(){
        IpLookupExecutor executor = IpLookupProvider.getInstance().get(IpLookupEnum.BAIDU);
        IpAddress lookup = executor.lookup(randomIp());
        System.out.printf(lookup.toString());
    }


}
