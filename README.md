<h1>
  <img src="https://tva1.sinaimg.cn/large/e6c9d24ely1h3ymi9kbkjj201q01pq2p.jpg" align="left" height="44px" alt="service plus logo"/> 
  <span>Service Plus</span>
</h1>

Service Plus 致力于打造一个分布式、强一致性 服务+ 的一站式解决方案。用作于分布式 K/V 存储系统、服务注册与发现、服务配置中心、分布式锁等。
目前处于个人研发阶段，现阶段适用于探索、研究。感兴趣的朋友可以与我交流一起共建。如果你想关注这个项目的进展，欢迎
star、fork、watch 三连！！！

# 快速上手

- 启动 Storage

  在 storage 模块找到 StorageStartUp 启动类并启动。
- 启动 Broker

  在 broker 模块找到 BrokerApplication 启动类并启动。
- 发布 KV
```java
curl --location --request POST 'http://localhost:8080/serviceplus/kv/v1/put' \
--form 'key="user_name"' \
--form 'value="张三"'
```

- 获取 KV

```java
curl --location --request GET 'http://localhost:8080/serviceplus/kv/v1/get?key=user_name'
```