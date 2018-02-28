## 1. 组件介绍

此组件提供ip查询地理位置功能

## 2. 配置

1) 增加组件依赖

    ```
    <dependency>
      <groupId>org.aalin.common</groupId>
      <artifactId>ipaddress</artifactId>
      <version>1.0</version>
    </dependency>
    ```

## 3.使用

  1. [接口详情](https://gitee.com/aalinyu/ipaddress/blob/master/src/main/java/org/aalin/common/ip/IpLookup.java)
  2. 使用方法
   `IpLookupResult result = DefaultIpLookup.getInstance().lookup("11.23.56.34",false);`
   
## 4.注意
1. 新浪接口 超时时间100ms
2. 百度接口 超时时间100ms
3. 本地库 查询时间<2ms
4. 如果不能访问外网，可只查询本地库，isOnlyLookupLocal=ture
    
    ```
    /**
    	 * ip地址本地库查询
    	 * @param ip
    	 *        格式如："113.204.226.234"
    	 * @param isOnlyLookupLocal
    	 *        true  表示只查询本地库
    	 *        false 顺序查询
    	 * 				1、新浪接口 超时时间200ms
    	 * 				2、百度接口 超时时间200ms
    	 * 				3、本地库
    	 * @return  IpLookupResult
    	 *          country ip所在国家
    	 *          province ip所在省
    	 *          city ip所在城市
    	 *          district ip所在地区
    	 *          country、city、district返回值有可能为空
    	 * */
    	IpLookupResult lookup(String ip,boolean isOnlyLookupLocal);
    ```
   
   
   




   