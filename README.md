#### 核心依赖 


依赖 | 版本
---|---
Consul  |  1.4.4
Spring Boot |  2.0.8.RELEASE  
Spring Cloud | Finchley.SR3   
Spring Security OAuth2 | 2.3.3
Mybatis Plus | 3.1.0
hutool | 4.5.0
   


#### 模块说明
```lua
pig
├── pig-ui -- 前端工程[8080]
├── pig-auth -- 授权服务提供[3000]
└── pig-common -- 系统公共模块 
     ├── pig-common-core -- 公共工具类核心包
     ├── pig-common-log -- 日志服务
     └── pig-common-security -- 安全工具类
├── pig-gateway -- Spring Cloud Gateway网关[9999]
└── pig-upms -- 通用用户权限管理模块
     └── pigx-upms-api -- 通用用户权限管理系统公共api模块
     └── pigx-upms-biz -- 通用用户权限管理系统业务处理模块[4000]
└── pigx-visual  -- 图形化模块 
     ├── pigx-monitor -- Spring Boot Admin监控 [5001]
     └── pigx-codegen -- 图形化代码生成[5003]

pig-admin 疯象乐园总部管理系统[3001]
pig-agent 疯象乐园[3002]