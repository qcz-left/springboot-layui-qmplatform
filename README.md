# qmplatform

## 介绍
<br/>1、该项目是一个简单的单体应用，就没有比它更简洁的画面了，基于springboot2.16、mybatis-plus、shiro、layui、freemarker<br/>
2、实现了maven打包jar加密，防止反编译，加载外部第三方库、加载外部配置文件，缩减jar包体积，高效部署，<br/>
3、集成websocket，定时检测会话，自动跳转登录页，自动检测系统通知提醒<br/>
4、简易可动态添加的定时任务<br/>
5、集成Jenkins持续部署配置，提交代码自动部署流程。<br/>
6、Mybatis 拦截器实现使用注解控制数据权限<br/>
7、自定义登录策略（验证码、锁定策略）<br/>
8、密码参数RSA加解密（前端公钥加密，后端私钥解密），防止密码被窃取 <br/>
9、支持文件上传预览（需要安装OpenOffice4，不使用可以在application*.yml中关闭开关）

## 软件架构
软件架构说明<br/>
后端：springboot+mybatis-plus+shiro<br/>
前端：layui<br/>
模板引擎：freemartker<br/>
数据库：postgresql<br/>

没什么高大上的东西，用来练手还是可以的


## 安装教程

哪有啥安装教程，clone下来导入maven工程就开干，记得先调好自己的数据库哈，sql文件也有了

## 文件目录说明
/etc/init.d/qmplatform: 服务启动脚本（service qmplatform stop/start/status），直接放到/etc/init.d下使用<br/>
security/qmplatform.key: 启动文件秘钥（打出来的jar是加了密的，具体加密方式在pom.xml里面，这个文件用来解密的）<br/>	
shell：需要执行Linux shell脚本的文件夹，默认存放于与执行jar的同级目录下<br/>	
&emsp;db_bak_recover.sh   数据库恢复备份脚本<br/>	
sql：数据库初始化脚本文件

## 注意事项
1、目前只支持打jar包方式，使用java -jar启动，打成war放入Tomcat还有点问题，有兴趣的可自行研究<br/>
2、部署方式采取单jar部署，不会包含所有依赖jar，这样是为了缩小每次部署的包体积大小，第一次部署，打成war包，提取war内WEB-INF/lib里面的jar包放入lib目录中<br/>
3、除了/etc/init.d/qmplatform启动脚本文件，其它配置文件（shell、security、config、lib等）均和qmplatform.jar平级<br/>
4、application-pro.yml没有打入jar内，需要将该文件放入一份与qmplatform.jar平级的地方<br/>
5、OpenOffice4插件在Centos7以下可能会有docx、txt中文乱码的问题，建议使用7或7以上版本的Centos<br/>
6、yml文件内需要加密的文字可以使用TestJasypt.java程序运行test方法得出密文，密文会打印在控制台
![部署目录截图](https://images.gitee.com/uploads/images/2021/1008/204507_e8f763fd_1324727.png "屏幕截图.png")

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

## Jenkins持续集成
### 下载Jenkins
到官方网站下载war包（我使用的版本是2.316）：https://get.jenkins.io/war/，下载后执行java -jar jenkins.war启动Jenkins，默认端口是8080，web访问地址就是你的服务器ip+端口<br/>
启动完成后会在生成一个admin秘钥文件，执行：cat /root/.jenkins/secrets/initialAdminPassword查看，初始登录web时需要这个秘钥，进去后自己创建一个账号<br/>
### Jenkins配置
安装插件列表：<br/>	
    Publish Over SSH（连接部署远程服务器用的）<br/>		
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
Jenkins服务器上创建一个jenkins账号：useradd jenkins，切换账号su jenkins，并生成公钥和私钥：ssh-keygen -t rsa -C "git@gitee.com"，执行后默认会到/home/jenkins/.ssh目录下生成一个私钥：id_rsa，和一个公钥：id_rsa.pub，私钥用于在jenkins web中创建连接gitee链接用的，公钥需要配置到gitee官方网站中
![公钥](https://images.gitee.com/uploads/images/2020/1218/152220_e453b4bc_1324727.png "屏幕截图.png")
![项目的gitee配置](https://images.gitee.com/uploads/images/2020/1218/152701_e67105b5_1324727.png "屏幕截图.png")
![私钥账号配置](https://images.gitee.com/uploads/images/2020/1218/152919_f022dfd5_1324727.png "屏幕截图.png")
构建触发器选择 Gitee webhook 触发构建：<br/>
![webhook](https://images.gitee.com/uploads/images/2020/1218/153339_9a584d47_1324727.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/1018/115150_4add1ea0_1324727.png "屏幕截图.png")
远程ssh服务器配置：<br/>
![远程ssh服务器配置](https://images.gitee.com/uploads/images/2020/1218/154040_dd220a4f_1324727.png "屏幕截图.png")
## 其它
微服务版本正在开发中...
