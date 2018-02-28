package org.aalin.common.ip.model;

import org.aalin.common.ip.result.StandardResultInfo;
import lombok.Data;

/**
 * @author shuijing
 */
@Data
public class IpLookupResult extends StandardResultInfo {

    private IpAddress ipAddress;

    public IpAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IpAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
