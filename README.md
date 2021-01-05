# qmplatform

## 介绍
该项目是一个简单的单体应用，就没有比它更简洁的画面了，基于springboot2.16、mybatis-plus、shiro、layui、freemarker，实现了maven打包jar加密，防止反编译，加载外部第三方库、加载外部配置文件，缩减jar包体积，高效部署，集成Jenkins持续部署配置，提交代码自动部署流程。小系统暂未用到什么缓存、消息队列啥的，因为现在用不着，等要用到了再说吧。

## 软件架构
软件架构说明
后端：springboot+mybatis-plus+shiro
前端：layui
模板引擎：freemartker
数据库：postgresql

没什么高大上的东西，用来练手还是可以的


## 安装教程

哪有啥安装教程，clone下来导入maven工程就开干，记得先调好自己的数据库哈，sql文件也有了

## 功能说明

系统管理<br>
&emsp;用户管理<br>
&emsp;角色管理<br>
&emsp;组织机构管理<br>
&emsp;菜单管理<br>
&emsp;操作日志<br>
档案中心<br>
&emsp;文件管理<br>
运维管理<br>
&emsp;系统运行状态<br>
&emsp;数据库监控<br>
&emsp;字典管理<br>
&emsp;数据备份与恢复<br>
&emsp;短信配置<br>
...

## 部分截图
![用户管理](https://images.gitee.com/uploads/images/2021/0105/161613_0cc93fb9_1324727.png "屏幕截图.png")
![菜单管理](https://images.gitee.com/uploads/images/2021/0105/161659_4018e099_1324727.png "屏幕截图.png")
![字典管理](https://images.gitee.com/uploads/images/2020/1220/184611_301f6b03_1324727.png "屏幕截图.png")
![数据备份与恢复](https://images.gitee.com/uploads/images/2021/0105/161746_276dc720_1324727.png "屏幕截图.png")

## Jenkins持续集成
### 下载Jenkins
到官方网站下载war包（我使用的版本是2.254）：https://get.jenkins.io/war/，下载后执行java -jar jenkins.war启动Jenkins，默认端口是80，web访问地址就是你的服务器ip<br/>
启动完成后会在生成一个admin秘钥文件，执行：cat /root/.jenkins/secrets/initialAdminPassword查看，初始登录web时需要这个秘钥，进去后自己创建一个账号<br/>
### Jenkins配置
安装插件列表：<br/>	
    Publish Over SSH（连接部署远程服务器用的）<br/>	
    Oracle Java SE Development Kit Installer Plugin<br/>	
    Localization: Chinese （国际化）(Simplified)<br/>	
    Gitee Plugin（我使用的是从gitee上拉取文件，你如果使用的GitHub就不用这个了）<br/>	
    Git plugin<br/>	
    
到码云官方网站个人设置中申请一个私人令牌<br/>

系统管理-系统配置：<br/>
![gitee配置](https://images.gitee.com/uploads/images/2020/1218/150113_2bb02172_1324727.png "屏幕截图.png")
![远程服务器ssh配置](https://images.gitee.com/uploads/images/2020/1218/150306_12cf0216_1324727.png "屏幕截图.png")
系统管理-全局工具配置：<br/>
![maven setting文件配置](https://images.gitee.com/uploads/images/2020/1218/150411_f0483ad2_1324727.png "屏幕截图.png")
![jdk、git、maven安装目录配置](https://images.gitee.com/uploads/images/2020/1218/150643_2ab8fab6_1324727.png "屏幕截图.png")
新建一个自由风格的项目：<br/>
![新建项目](https://images.gitee.com/uploads/images/2020/1218/150906_e40606e1_1324727.png "屏幕截图.png")
Jenkins服务器上创建一个jenkins账号：useradd jenkins，并生成公钥和私钥：ssh-keygen -t rsa -C "git@gitee.com"，执行后默认会到/home/qcz/.ssh目录下生成一个私钥：id_rsa，和一个公钥：id_rsa.pub，私钥用于在jenkins web中创建连接gitee链接用的，公钥需要配置到gitee官方网站中
![公钥](https://images.gitee.com/uploads/images/2020/1218/152220_e453b4bc_1324727.png "屏幕截图.png")
![项目的gitee配置](https://images.gitee.com/uploads/images/2020/1218/152701_e67105b5_1324727.png "屏幕截图.png")
![私钥账号配置](https://images.gitee.com/uploads/images/2020/1218/152919_f022dfd5_1324727.png "屏幕截图.png")
构建触发器选择 Gitee webhook 触发构建：<br/>
![webhook](https://images.gitee.com/uploads/images/2020/1218/153339_9a584d47_1324727.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1218/153435_4f7fd2c3_1324727.png "屏幕截图.png")
远程ssh服务器配置：<br/>
![远程ssh服务器配置](https://images.gitee.com/uploads/images/2020/1218/154040_dd220a4f_1324727.png "屏幕截图.png")
## 其它
微服务版本正在开发中...（springcloud、mybatis-plus、oauth2、rabbitmq、vue、element-ui啥的，都是市面上主流的，先列几个关键字吧，搞出来了再发出来请各位看官抽鞭）
一个人搞心累但快乐着，自勉。
