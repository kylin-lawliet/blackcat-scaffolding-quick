# blackcat-scaffolding-quick

#### 介绍
blackcat-scaffolding-quick是一个基于Spring Boot & MyBatis的种子项目，用于快速构建中小型API、RESTful API项目。
下载代码后，可直接添加业务业务模块代码，快速开启SpringBoot单体项目。

#### 特征&提供
- 全局异常处理
- 自定义异常输出
- 统一响应结果封装
- 操作日志记录注解
- 防止XSS攻击的过滤器
- 添加HTML过滤器，用于去除XSS漏洞隐患
- 集成jasypt，实现自定义加密方式（已实现SM4加密）加密配置文件敏感信息
- 集成MyBatis Plus、通用Mapper插件、PageHelper分页插件，实现单表业务零SQL

#### 核心依赖
- Spring Boot 2.7.18
- Mybatis Plus 3.5.6
- jasypt 3.0.4