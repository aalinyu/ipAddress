### ip库查询工具

提供ip查询地理位置能力

基于新浪、百度ip开放查询接口实现，在网络不通情况提供本地ip库查询

查询顺序如下：

1. [百度接口](http://lbsyun.baidu.com/index.php?title=webapi/ip-api)
2. [新浪接口](http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.192.3.42)
3. 本地库 查询耗时小于2ms，非常高效，在无法访问外网情况推荐

新浪、百度接口http超时时间设置的默认为200ms

### 2. 配置

1. 依赖

    ```
    <dependency>
      <groupId>org.aalin.common</groupId>
      <artifactId>ipaddress</artifactId>
      <version>1.0</version>
    </dependency>
    ```

### 3. 使用说明
1. 使用方法
   `IpLookupResult result = DefaultIpLookup.newInstance().lookup("11.23.56.34");`
2. 接口展示：

         /**
             * 顺序查询ip地理位置
             *
             * <p>顺序查询 任何一个接口查询到数据就返回 1、新浪接口 超时时间200ms 2、百度接口 超时时间200ms 3、本地库
             *
             * @param ip 格式如："113.204.226.234"
             * @return IpLookupResult country:国家 province:省 city:城市 district:地区
             * country、city、district返回值有可能为空
             */
            IpLookupResult lookup(String ip);

            /**
             * 只查询本地库
             *
             * @param ip          格式如："113.204.226.234"
             * @return IpLookupResult country:国家 province:省 city:城市 district:地区
             * country、city、district返回值有可能为空
             */
            IpLookupResult lookupLocal(String ip);

   
   




   