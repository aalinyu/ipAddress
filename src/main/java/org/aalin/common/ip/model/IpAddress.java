package org.aalin.common.ip.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shuijing
 */
@Data
public class IpAddress implements Serializable {

    private static final long serialVersionUID = 1;
    /**
     * IP所在的国家
     */
    private String country;

    /**
     * IP所在的省
     */
    private String province;

    /**
     * IP所在的城市
     */
    private String city;

    /**
     * IP所在的地区
     */
    private String district;
}
