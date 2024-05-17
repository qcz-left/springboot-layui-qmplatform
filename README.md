# qmplatform

## 介绍
<br/>1、该项目是一个简单的单体应用，就没有比它更简洁的画面了，基于springboot3.2.5、mybatis-plus、sa-token、layui、freemarker<br/>
2、集成websocket，定时检测会话，自动跳转登录页，自动检测系统通知提醒<br/>
3、简易可动态添加的定时任务<br/>
4、集成Jenkins持续部署配置，提交代码自动部署流程。<br/>
5、Mybatis 拦截器实现使用注解控制数据权限，简化xml动态sql编写方式
如：<br/>
```xml
<if test="name != null and name != ''">
    and name = #{name}
</if>
```
可转化为：<br/>
```xml
[[ and name = #name# ]]
```
<br/>
6、自定义登录策略（验证码、锁定策略）<br/>
7、密码参数RSA加解密（前端公钥加密，后端私钥解密），防止密码被窃取 <br/>
8、已接入短信云平台：华为、百度、腾讯、阿里（没有引入第三方SDK，避免打出来的包体积过大）

## 软件架构
软件架构说明<br/>
后端：springboot+mybatis-plus+sa-token<br/>
前端：layui<br/>
模板引擎：freemartker<br/>
数据库：postgresql、mysql<br/>

没什么高大上的东西，用来练手还是可以的


## 安装教程

哪有啥安装教程，clone下来导入maven工程就开干，记得先调好自己的数据库哈，sql文件也有了

## 文件目录说明
shell：需要执行Linux shell脚本的文件夹，默认存放于与执行jar的同级目录下<br/>	
&emsp;db_bak_recover.sh   数据库恢复备份脚本<br/>	
sql：数据库初始化脚本文件

## 注意事项
1、正式打包 ：mvn clean package -P pro，会自动引用application-pro.yml配置文件，且日志输出为文件
2、application-pro.yml没有打入jar内，需要将该文件放入一份与qmplatform.jar平级的地方<br/>

## 功能说明

系统管理<br>
&emsp;组织机构管理<br>
&emsp;角色管理<br>
&emsp;菜单管理<br>
&emsp;操作日志<br>
档案中心<br>
&emsp;文件管理<br>
运维管理<br>
&emsp;登录错误记录<br>
&emsp;系统运行状态<br>
&emsp;数据库监控<br>
&emsp;字典管理<br>
&emsp;数据备份与恢复<br>
&emsp;短信配置<br>
...

## 部分截图
![组织机构管理](https://images.gitee.com/uploads/images/2021/0630/084640_d280694c_1324727.png "屏幕截图.png")
![菜单管理](https://images.gitee.com/uploads/images/2021/0630/084718_d9fb0fab_1324727.png "屏幕截图.png")
![角色管理](https://images.gitee.com/uploads/images/2021/0630/084748_1fead9b7_1324727.png "屏幕截图.png")
![数据备份与恢复](https://images.gitee.com/uploads/images/2021/0630/084810_0fc6f7d8_1324727.png "屏幕截图.png")

