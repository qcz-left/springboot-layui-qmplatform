# slv-init-manage
  基于springboot的初始化管理平台
## 简介：
  slv-init-manage是一个极简模式的后台管理。就像我的简介这么极简_^^_
## 后端技术：
  springboot2.0，mybatis，redis，shiro
## 前端技术：
  layui，vue
## 模板引擎：
  thymeleaf
## 图表组件：
  echarts
## 项目结构：
-- src\
--- main\
---- java\
----- com\
------ qcz\
------- qmplatform\
-------- common          		              // 公共类目录\
-------- main         		                // 登录、登出等\
-------- moudle                           // 业务模块\
-------- QmplatformApplication.java 	    // 项目加载入口类\
-------- *Configurer.java                 // 各种配置文件\
--- resources                             // 资源文件目录\
---- mapper                               // mybatis数据库映射文件\
---- static                              	// 静态文件\
---- application-dev.yml                 	// 开发环境配置文件\
---- application-pro.yml                 	// 正式环境配置文件\
---- application.yml                     	// 项目主配置文件\
---- logback-spring.xml                   // logback日志配置\
-- pom.xml
## 主要功能（极简）
  - 首页
  - 系统管理
    - 用户管理
    - 菜单管理
    - 角色管理
  - 操作日志
## 功能截图
  ![image](https://github.com/qcz-left/slv-init-manage/raw/master/picture/login.jpg)
  ![image](https://github.com/qcz-left/slv-init-manage/raw/master/picture/index.jpg)
  ![image](https://github.com/qcz-left/slv-init-manage/raw/master/picture/user-list.jpg)
  ![image](https://github.com/qcz-left/slv-init-manage/raw/master/picture/sysOperateLog-list.jpg)
