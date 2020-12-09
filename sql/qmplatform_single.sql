-- ----------------------------
-- Sequence structure for sys_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."sys_user_id_seq";
CREATE SEQUENCE "public"."sys_user_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 3
CACHE 1;

-- ----------------------------
-- Table structure for sys_button
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_button";
CREATE TABLE "public"."sys_button" (
  "button_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "button_name" varchar(20) COLLATE "pg_catalog"."default",
  "menu_id" varchar(50) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "iorder" int4,
  "icon" varchar(50) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_button"."button_id" IS '按钮id';
COMMENT ON COLUMN "public"."sys_button"."button_name" IS '按钮名称';
COMMENT ON COLUMN "public"."sys_button"."menu_id" IS '所属菜单id';
COMMENT ON COLUMN "public"."sys_button"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_button"."iorder" IS '排序';
COMMENT ON COLUMN "public"."sys_button"."icon" IS '图标';
COMMENT ON TABLE "public"."sys_button" IS '按钮';

-- ----------------------------
-- Records of sys_button
-- ----------------------------
INSERT INTO "public"."sys_button" VALUES ('8', 'button1', '6', '6', 8, NULL);
INSERT INTO "public"."sys_button" VALUES ('3ccc109b-d75f-487e-a801-64e93465b118', 'name4的按钮', '4', 'name-btn', 0, NULL);
INSERT INTO "public"."sys_button" VALUES ('9ceffdf2-420e-40dc-9199-ab4ff049737b', '删除用户', 'ef07f63f-4ec1-4053-bb53-db7891359339', 'user-delete', 1, NULL);
INSERT INTO "public"."sys_button" VALUES ('4489ccf9-fec5-4f81-b817-0c1cd2c7a171', '保存用户', 'ef07f63f-4ec1-4053-bb53-db7891359339', 'user-save', 0, '');
INSERT INTO "public"."sys_button" VALUES ('985cf497-f3f0-4bea-8844-70fdc3ab4cde', '保存角色', '042a5960-785f-442e-a76a-576f7eb389c4', 'role-save', 0, NULL);
INSERT INTO "public"."sys_button" VALUES ('a9238df4-ca79-4ddb-afc9-74e01b808200', '删除角色', '042a5960-785f-442e-a76a-576f7eb389c4', 'role-delete', 1, NULL);
INSERT INTO "public"."sys_button" VALUES ('dd0268c3-1f3b-44f0-b94b-5d8df387019f', '分配角色', '042a5960-785f-442e-a76a-576f7eb389c4', 'role-allot', 2, NULL);
INSERT INTO "public"."sys_button" VALUES ('074f7b51-cb8c-4452-a188-e7afd4505930', '保存菜单', '82241eeb-ed7c-44f3-b9f0-56e986363907', 'menu-save', 0, NULL);
INSERT INTO "public"."sys_button" VALUES ('529279d9-6b31-4ed7-b61b-127f4257810e', '删除菜单', '82241eeb-ed7c-44f3-b9f0-56e986363907', 'menu-delete', 1, NULL);
INSERT INTO "public"."sys_button" VALUES ('117c2896-d27c-445d-bc88-240b8ac62032', '保存组织机构', '4af487ea-9903-4116-970e-d82dce9d49ce', 'org-save', 0, '');
INSERT INTO "public"."sys_button" VALUES ('e45cdf2e-692b-4ba3-aa65-464d8a9da97b', '删除组织机构', '4af487ea-9903-4116-970e-d82dce9d49ce', 'org-delete', 1, '');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_menu";
CREATE TABLE "public"."sys_menu" (
  "menu_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_name" varchar(100) COLLATE "pg_catalog"."default",
  "iorder" int4,
  "code" varchar(50) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "link_url" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" varchar(50) COLLATE "pg_catalog"."default",
  "display" int2 DEFAULT 1
)
;
COMMENT ON COLUMN "public"."sys_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "public"."sys_menu"."iorder" IS '排序';
COMMENT ON COLUMN "public"."sys_menu"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_menu"."icon" IS '图标';
COMMENT ON COLUMN "public"."sys_menu"."link_url" IS '访问路径';
COMMENT ON COLUMN "public"."sys_menu"."parent_id" IS '父菜单id';
COMMENT ON COLUMN "public"."sys_menu"."display" IS '是否显示';
COMMENT ON TABLE "public"."sys_menu" IS '菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO "public"."sys_menu" VALUES ('1', '系统管理', 0, 'system', 'layui-icon-set', '', '', 1);
INSERT INTO "public"."sys_menu" VALUES ('b555a066-c76d-4b8f-a834-4998cc000711', '测试404', 70, 'ceshi404', 'layui-icon-404', '/error/404', '', 1);
INSERT INTO "public"."sys_menu" VALUES ('e36040a7-f7cb-42f8-a55f-0a6f6e138958', '测试500', 80, 'ceshi500', 'layui-icon-face-cry', '/error/500', '', 1);
INSERT INTO "public"."sys_menu" VALUES ('ec2d5dbf-ace1-4fe8-b85e-70fc84bf517e', '操作日志', 60, 'operate', 'layui-icon-log', '/operate-log/logListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('bfba142d-6a80-417b-9e58-a3671283f2fb', '图标管理', 40, 'icon', '', '/icon/iconListPage', '1', 0);
INSERT INTO "public"."sys_menu" VALUES ('ef07f63f-4ec1-4053-bb53-db7891359339', '用户管理', 0, 'user', 'layui-icon-user', '/user/userListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('82241eeb-ed7c-44f3-b9f0-56e986363907', '菜单管理', 50, 'menu', 'layui-icon-more', '/menu/menuListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('042a5960-785f-442e-a76a-576f7eb389c4', '角色管理', 1, 'role', 'layui-icon-auz', '/role/roleListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('4af487ea-9903-4116-970e-d82dce9d49ce', '组织机构管理', 30, 'org', 'layui-icon-component', '/organization/organizationListPage', '1', 1);

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_operate_log";
CREATE TABLE "public"."sys_operate_log" (
  "log_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "operate_type" int2,
  "operate_module" varchar(100) COLLATE "pg_catalog"."default",
  "operate_user_id" varchar(50) COLLATE "pg_catalog"."default",
  "operate_user_name" varchar(50) COLLATE "pg_catalog"."default",
  "operate_time" timestamp(6),
  "request_url" varchar(255) COLLATE "pg_catalog"."default",
  "description" text COLLATE "pg_catalog"."default",
  "ip_addr" varchar(30) COLLATE "pg_catalog"."default",
  "operate_status" int2 NOT NULL,
  "exp_msg" text COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_operate_log"."log_id" IS '日志id，主键唯一标识';
COMMENT ON COLUMN "public"."sys_operate_log"."operate_type" IS '操作类型（-1：退出系统，1：进入系统，2：查询，3：新增，4：修改，5：删除） 需要其他类型，请自添加并注释';
COMMENT ON COLUMN "public"."sys_operate_log"."operate_module" IS '操作模块';
COMMENT ON COLUMN "public"."sys_operate_log"."operate_user_id" IS '操作人ID';
COMMENT ON COLUMN "public"."sys_operate_log"."operate_user_name" IS '操作人名称';
COMMENT ON COLUMN "public"."sys_operate_log"."operate_time" IS '操作时间';
COMMENT ON COLUMN "public"."sys_operate_log"."request_url" IS '请求路径';
COMMENT ON COLUMN "public"."sys_operate_log"."description" IS '描述内容';
COMMENT ON COLUMN "public"."sys_operate_log"."ip_addr" IS 'IP地址';
COMMENT ON COLUMN "public"."sys_operate_log"."operate_status" IS '操作状态（0：失败，1：成功）';
COMMENT ON COLUMN "public"."sys_operate_log"."exp_msg" IS '异常信息';

-- ----------------------------
-- Records of sys_operate_log
-- ----------------------------
INSERT INTO "public"."sys_operate_log" VALUES ('569d4f86-be65-4161-a9a9-fa7c1f2dceb4', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-06 23:05:46.065', '/logout', '', '0:0:0:0:0:0:0:1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('a723cd47-9393-4875-9802-97ec43a7a802', 1, '登录系统', NULL, NULL, '2020-12-06 23:12:46.781', '/login', '', '0:0:0:0:0:0:0:1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('df4f0eab-216c-4b6c-adcb-0fc714f76b9d', 1, '登录系统', NULL, NULL, '2020-12-06 23:17:24', '/login', '', '0:0:0:0:0:0:0:1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('3e495801-942c-4126-8be4-08ca277d2bc6', 3, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-06 23:17:33.235', '/user/saveUser', '保存用户', '0:0:0:0:0:0:0:1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('f86534b6-2a12-45da-9177-047606fbc30c', 1, '登录系统', NULL, NULL, '2020-12-06 23:34:57.772', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('b2c5cbc2-d3b5-486e-ae58-cae9021eed2e', 1, '登录系统', NULL, NULL, '2020-12-07 18:38:43.547', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('95b731dc-47c9-4f81-b42e-9bf77ef0ef69', 1, '登录系统', NULL, NULL, '2020-12-07 19:30:19.688', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('8751473e-468c-46c8-ab68-b263c78b3754', 3, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 19:30:43.372', '/user/saveUser', '保存用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('5754ad55-adc2-4510-8433-5ec7efc5a3af', 3, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 19:34:13.881', '/user/saveUser', '保存用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('30a50b20-672d-4280-a6e8-800442933fea', 3, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 19:35:10.351', '/user/saveUser', '保存用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('1d089dbe-5c8b-41c2-a627-ba2b93c17280', 3, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 19:35:30.029', '/user/saveUser', '保存用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('71cab18e-8ba3-431d-9c75-106273ce226a', 1, '登录系统', NULL, NULL, '2020-12-07 19:49:49.648', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('f1b9dd91-e3c8-4e68-938e-cedc5f3e06e6', 1, '登录系统', NULL, NULL, '2020-12-07 20:34:59.529', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('30c2ac34-e62d-4fc9-bb5a-0d3c20b3c542', 1, '登录系统', NULL, NULL, '2020-12-07 20:53:19.3', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('931671fb-5fa0-4737-b026-af24334065fe', 1, '登录系统', NULL, NULL, '2020-12-07 21:03:20.303', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('d9fdb23c-2d94-414d-a802-9a7e9516dbbe', 1, '登录系统', NULL, NULL, '2020-12-07 21:20:16.01', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('60bb3b5e-1a87-4024-932b-140d53e09b38', 1, '登录系统', NULL, NULL, '2020-12-07 21:25:00.472', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('fc29a684-e6ec-445b-a2e7-650a727e7333', 1, '登录系统', NULL, NULL, '2020-12-07 21:29:55.824', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('94361dd1-28cf-4e96-b534-c01a37a692a6', 1, '登录系统', NULL, NULL, '2020-12-07 21:34:12.923', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('dcef0b28-1dd2-4085-b739-bf08311f0169', 1, '登录系统', NULL, NULL, '2020-12-07 21:40:29.523', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('b9286a88-1acd-416d-8d46-54f262f9469c', 1, '登录系统', NULL, NULL, '2020-12-07 21:42:50.242', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('f016e135-9a9c-4a25-b6c5-1fd94d7547d7', 1, '登录系统', NULL, NULL, '2020-12-07 21:45:56.083', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('97c1202c-8edd-42a6-8c75-81cb87b77ff0', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 21:46:15.513', '/logout', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('be731e07-6ddf-471d-a1cf-572238568310', -1, '退出系统', NULL, NULL, '2020-12-07 21:46:34.902', '/logout', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('0737767e-3230-4e45-aaae-a0f516803d88', 1, '登录系统', NULL, NULL, '2020-12-07 21:46:39.973', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('d035b9b8-941c-41e3-a2d5-6665a6703fe5', 1, '登录系统', NULL, NULL, '2020-12-07 21:48:20.525', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('df2e7fd7-bb81-441d-8905-23ac8fa71a89', 1, '登录系统', NULL, NULL, '2020-12-07 22:17:57.233', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('4fa8a674-9778-48ad-bc91-28338d2e96e4', 1, '登录系统', NULL, NULL, '2020-12-07 22:20:09.628', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('2c057297-d871-4bf4-8a36-3b544725e970', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 22:20:56.355', '/user/updateUser', '修改用户', '127.0.0.1', 0, 'java.lang.Exception:org.springframework.dao.DuplicateKeyException:
### Error updating database.  Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "unique_loginname"
  详细：Key (loginname)=(为首的发生) already exists.
### The error may exist in com/qcz/qmplatform/module/system/mapper/UserMapper.java (best guess)
### The error may involve com.qcz.qmplatform.module.system.mapper.UserMapper.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO sys_user  ( id, password, username, loginname, user_sex, remark, create_time, create_user_id, phone, email_addr, locked )  VALUES  ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
### Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "unique_loginname"
  详细：Key (loginname)=(为首的发生) already exists.
; ERROR: duplicate key value violates unique constraint "unique_loginname"
  详细：Key (loginname)=(为首的发生) already exists.; nested exception is org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "unique_loginname"
  详细：Key (loginname)=(为首的发生) already exists.
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:84)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor$1.proceed(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:82)
org.apache.shiro.authz.aop.AuthorizingMethodInterceptor.invoke(AuthorizingMethodInterceptor.java:39)
org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor.invoke(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:115)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)
com.qcz.qmplatform.module.system.controller.UserController$$EnhancerBySpringCGLIB$$8891b3d3.updateUser(<generated>)
com.qcz.qmplatform.module.system.controller.UserController$$FastClassBySpringCGLIB$$6ac3e070.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor$1.proceed(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:82)
org.apache.shiro.authz.aop.AuthorizingMethodInterceptor.invoke(AuthorizingMethodInterceptor.java:39)
org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor.invoke(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:115)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)
com.qcz.qmplatform.module.system.controller.UserController$$EnhancerBySpringCGLIB$$83d0f777.updateUser(<generated>)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)
org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)
org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)
org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:892)
org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797)
org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1039)
org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)
org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005)
org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:908)
javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:882)
javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)
org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)
org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)
org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)
org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)
org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)
org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)
org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)
org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)
org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)
org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:383)
org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)
org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
com.qcz.qmplatform.filter.XSSFilter.doFilter(XSSFilter.java:26)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:92)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)
org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490)
org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)
org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)
org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)
org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:853)
org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1587)
org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
java.lang.Thread.run(Thread.java:748)
');
INSERT INTO "public"."sys_operate_log" VALUES ('2521dcbe-0e28-4388-a9a0-daa7b0dac3b0', 1, '登录系统', NULL, NULL, '2020-12-07 22:23:31.469', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('bc0539dc-7424-4586-a8bc-6bbbc933b892', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 22:23:43.319', '/user/updateUser', '修改用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('1a06534d-8463-409d-871d-026c9e693282', 1, '登录系统', NULL, NULL, '2020-12-07 22:32:39.481', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('845d57b4-ce1c-4916-a309-e15908b421d9', 1, '登录系统', NULL, NULL, '2020-12-07 23:20:16.547', '/login', '', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('0e117ad3-3a68-48e6-a128-a6c9089e9548', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 23:20:59.789', '/user/updateUser', '修改用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('8041a01c-8369-4a09-b07d-4d2df330063c', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 23:23:18.582', '/user/updateUser', '修改用户', '127.0.0.1', 0, 'java.lang.Exception:org.springframework.transaction.TransactionTimedOutException: Transaction timed out: deadline was Mon Dec 07 23:22:41 CST 2020
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:82)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor$1.proceed(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:82)
org.apache.shiro.authz.aop.AuthorizingMethodInterceptor.invoke(AuthorizingMethodInterceptor.java:39)
org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor.invoke(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:115)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)
com.qcz.qmplatform.module.system.controller.UserController$$EnhancerBySpringCGLIB$$bc402976.updateUser(<generated>)
com.qcz.qmplatform.module.system.controller.UserController$$FastClassBySpringCGLIB$$6ac3e070.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor$1.proceed(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:82)
org.apache.shiro.authz.aop.AuthorizingMethodInterceptor.invoke(AuthorizingMethodInterceptor.java:39)
org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor.invoke(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:115)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)
com.qcz.qmplatform.module.system.controller.UserController$$EnhancerBySpringCGLIB$$edeca250.updateUser(<generated>)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)
org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)
org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)
org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:892)
org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797)
org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1039)
org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)
org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005)
org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:908)
javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:882)
javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)
org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)
org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)
org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)
org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)
org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)
org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)
org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)
org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)
org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)
org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:383)
org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)
org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
com.qcz.qmplatform.filter.XSSFilter.doFilter(XSSFilter.java:26)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:92)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)
org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:109)
org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)
org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490)
org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)
org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)
org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)
org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:853)
org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1587)
org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
java.lang.Thread.run(Thread.java:748)
');
INSERT INTO "public"."sys_operate_log" VALUES ('7bf932c9-30e5-4b41-9f9a-ee69f86d3673', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 23:24:31.559', '/user/updateUser', '修改用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('1efe9b94-9c5d-42ce-b104-213c2b4ab37e', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 23:28:47.571', '/user/updateUser', '修改用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('a55f3a8b-ffc4-496d-9af1-34c07dfa4edf', 5, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-07 23:31:54.375', '/user/delUser', '删除用户', '127.0.0.1', 1, '');
INSERT INTO "public"."sys_operate_log" VALUES ('ec16e459-3f98-4714-ac20-ecfa6c241171', 1, '登录系统', NULL, NULL, '2020-12-08 19:01:13.59', '/login', NULL, '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9c4d6118-eeb8-4927-a3f6-aec4bd61a82f', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 19:01:32.432', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('23503675-b991-4907-acda-b99849a3f7dd', 1, '登录系统', NULL, NULL, '2020-12-08 19:01:33.679', '/login', NULL, '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2ea22782-b47e-4a1e-910c-e3c67bd1e7f8', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 19:44:50.228', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('afc2134c-afea-451a-a85f-6b000ad5188f', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 19:48:45.063', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cd618470-cac9-498f-8454-d67a8db0348c', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 19:49:01.546', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e7bf7332-6576-45d7-80a6-be884966a424', 1, '登录系统', NULL, NULL, '2020-12-08 19:54:22.212', '/login', NULL, '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d93a2f60-ccf0-47be-ad51-6a2bcf8e9818', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 19:57:08.859', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4681a42b-9382-4474-87f1-8277749ffefc', 1, '登录系统', NULL, NULL, '2020-12-08 19:59:18.08', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b187ad3d-bb11-4b68-8941-7239d7c4e58b', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 19:59:35.118', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bd42ee33-34bf-4bc4-aa81-adf512234635', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 19:59:38.406', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fd026ef1-5148-4ae7-9fdd-33d227c27ef1', 1, '登录系统', NULL, NULL, '2020-12-08 21:12:41.818', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c77657a4-3200-4497-ae61-bde72e7a763c', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 21:12:52.07', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6fb62910-52fb-4138-9ba9-ffd8d816b2c2', 1, '登录系统', NULL, NULL, '2020-12-08 21:15:10.062', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ad92bd8c-d9bb-46c0-8289-a4d8261a57c9', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 21:15:15.447', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('79a9db7f-756b-4677-a19e-3eaa93ff5cf7', 1, '登录系统', NULL, NULL, '2020-12-08 21:18:24.9', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('050cb8df-cfcd-41f8-9a4f-5b1618ba32c8', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 21:18:29.499', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('89d563ab-a0fd-4e0f-982e-916cb048f588', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 21:19:10.375', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f4c26f62-79e7-421b-9e56-9213e229083c', 1, '登录系统', NULL, NULL, '2020-12-08 21:29:16.624', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2805fc95-895a-4a11-9f51-7891288f87e1', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 21:29:32.752', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d07d83fa-a1ac-4f08-a475-0e4dd345019a', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 21:30:55.79', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('261db234-a367-4b0a-a159-cb32c3ccbadb', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 21:31:42.836', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cb3a3cd6-308d-4f82-95fb-d6cdde2371db', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 21:38:49.946', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_organization";
CREATE TABLE "public"."sys_organization" (
  "organization_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "organization_name" varchar(50) COLLATE "pg_catalog"."default",
  "organization_code" varchar(100) COLLATE "pg_catalog"."default",
  "parent_id" varchar(50) COLLATE "pg_catalog"."default",
  "remark" varchar(1000) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "iorder" int4
)
;
COMMENT ON COLUMN "public"."sys_organization"."organization_id" IS '组织机构id';
COMMENT ON COLUMN "public"."sys_organization"."organization_name" IS '组织机构名称';
COMMENT ON COLUMN "public"."sys_organization"."organization_code" IS '组织机构编码';
COMMENT ON COLUMN "public"."sys_organization"."parent_id" IS '父id';
COMMENT ON COLUMN "public"."sys_organization"."remark" IS '机构描述';
COMMENT ON COLUMN "public"."sys_organization"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_organization"."iorder" IS '排序';
COMMENT ON TABLE "public"."sys_organization" IS '组织机构';

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO "public"."sys_organization" VALUES ('6afd2fc3-6619-4a05-b98c-8ca4d66dbc75', '管理部2', '2', '11f67a26-22da-49c1-86e8-78ee2a55ac7f', '2', '2020-11-01 16:24:07.543', 0);
INSERT INTO "public"."sys_organization" VALUES ('e087310b-b26f-4148-8fde-9d6c6ddf4f6e', '开发部2', 'develop2', '8b5afd90-02aa-4362-9ce6-751cf68ff41b', '', '2020-11-01 20:16:42.063', 0);
INSERT INTO "public"."sys_organization" VALUES ('526f57b8-6b09-407d-9099-96fa38719814', '某某信息科技有限公司', 'root', '', '根部门', '2020-11-06 21:01:03.995', 0);
INSERT INTO "public"."sys_organization" VALUES ('cbf0f864-9d11-4924-9351-e089a89f0db3', '人事部', 'renshi', '526f57b8-6b09-407d-9099-96fa38719814', '', '2020-11-06 21:00:25.746', 0);
INSERT INTO "public"."sys_organization" VALUES ('11f67a26-22da-49c1-86e8-78ee2a55ac7f', '管理部', 'manage', '526f57b8-6b09-407d-9099-96fa38719814', 'tt', '2020-11-01 16:03:54.036', 0);
INSERT INTO "public"."sys_organization" VALUES ('8b5afd90-02aa-4362-9ce6-751cf68ff41b', '开发部', 'develop', '526f57b8-6b09-407d-9099-96fa38719814', '', '2020-11-01 16:04:50.268', 1);
INSERT INTO "public"."sys_organization" VALUES ('784c2e87-b88a-46b8-8cd3-871e4646df36', '财务部', 'caiwu', '526f57b8-6b09-407d-9099-96fa38719814', 'sss', '2020-11-01 16:05:44.46', 2);
INSERT INTO "public"."sys_organization" VALUES ('6fcad8e4-ffde-4a9c-92f0-6af13c298ed6', '销售部', 'sale', '526f57b8-6b09-407d-9099-96fa38719814', '', '2020-12-08 21:52:18.136', NULL);
INSERT INTO "public"."sys_organization" VALUES ('23c124bb-ce39-4ee1-acb1-bf007a681188', '产品部', 'product', '526f57b8-6b09-407d-9099-96fa38719814', '', '2020-12-08 21:52:39.922', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "role_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "role_name" varchar(255) COLLATE "pg_catalog"."default",
  "role_sign" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "public"."sys_role" IS '角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "public"."sys_role" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '系统管理员', 'system-admin', '最高长官');
INSERT INTO "public"."sys_role" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', '测试人员', 'ceshi', '测试人员');
INSERT INTO "public"."sys_role" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '运维人员', 'web-operations', '运维人员');
INSERT INTO "public"."sys_role" VALUES ('test_tv', 'test_tv', 'test', '');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_permission";
CREATE TABLE "public"."sys_role_permission" (
  "role_id" varchar(50) COLLATE "pg_catalog"."default",
  "permission_id" varchar(50) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "public"."sys_role_permission" IS '角色-权限对应关系';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', 'ec2d5dbf-ace1-4fe8-b85e-70fc84bf517e');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '1');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'ef07f63f-4ec1-4053-bb53-db7891359339');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '4489ccf9-fec5-4f81-b817-0c1cd2c7a171');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '9ceffdf2-420e-40dc-9199-ab4ff049737b');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '042a5960-785f-442e-a76a-576f7eb389c4');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '985cf497-f3f0-4bea-8844-70fdc3ab4cde');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'a9238df4-ca79-4ddb-afc9-74e01b808200');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'dd0268c3-1f3b-44f0-b94b-5d8df387019f');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '4af487ea-9903-4116-970e-d82dce9d49ce');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '117c2896-d27c-445d-bc88-240b8ac62032');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'e45cdf2e-692b-4ba3-aa65-464d8a9da97b');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'bfba142d-6a80-417b-9e58-a3671283f2fb');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '82241eeb-ed7c-44f3-b9f0-56e986363907');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '074f7b51-cb8c-4452-a188-e7afd4505930');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '529279d9-6b31-4ed7-b61b-127f4257810e');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'ec2d5dbf-ace1-4fe8-b85e-70fc84bf517e');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'b555a066-c76d-4b8f-a834-4998cc000711');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'e36040a7-f7cb-42f8-a55f-0a6f6e138958');
INSERT INTO "public"."sys_role_permission" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', '1');
INSERT INTO "public"."sys_role_permission" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', 'ef07f63f-4ec1-4053-bb53-db7891359339');
INSERT INTO "public"."sys_role_permission" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', '4489ccf9-fec5-4f81-b817-0c1cd2c7a171');
INSERT INTO "public"."sys_role_permission" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', '9ceffdf2-420e-40dc-9199-ab4ff049737b');
INSERT INTO "public"."sys_role_permission" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', 'b555a066-c76d-4b8f-a834-4998cc000711');
INSERT INTO "public"."sys_role_permission" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', 'e36040a7-f7cb-42f8-a55f-0a6f6e138958');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', '1');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', 'ef07f63f-4ec1-4053-bb53-db7891359339');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', '4489ccf9-fec5-4f81-b817-0c1cd2c7a171');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', '9ceffdf2-420e-40dc-9199-ab4ff049737b');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', 'bfba142d-6a80-417b-9e58-a3671283f2fb');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar COLLATE "pg_catalog"."default",
  "username" varchar(50) COLLATE "pg_catalog"."default",
  "loginname" varchar(50) COLLATE "pg_catalog"."default",
  "user_sex" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(1000) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user_id" varchar(50) COLLATE "pg_catalog"."default",
  "phone" varchar(20) COLLATE "pg_catalog"."default",
  "email_addr" varchar(50) COLLATE "pg_catalog"."default",
  "locked" int2 DEFAULT 0,
  "photo_path" varchar(100) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_user"."password" IS '密码';
COMMENT ON COLUMN "public"."sys_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."sys_user"."loginname" IS '登录名';
COMMENT ON COLUMN "public"."sys_user"."user_sex" IS '用户性别';
COMMENT ON COLUMN "public"."sys_user"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user"."create_user_id" IS '创建人id';
COMMENT ON COLUMN "public"."sys_user"."phone" IS '手机号码';
COMMENT ON COLUMN "public"."sys_user"."email_addr" IS '邮箱地址';
COMMENT ON COLUMN "public"."sys_user"."locked" IS '锁定';
COMMENT ON COLUMN "public"."sys_user"."photo_path" IS '头像文件路径';
COMMENT ON TABLE "public"."sys_user" IS '用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "public"."sys_user" VALUES ('17', '$2a$10$u8Gb7oKS6.e.9A4UGCFXr.ptOSjzHzYxriwRlG9x10ZYGqMnLHO9W', '东哥', 'asf', '1', 'as', NULL, NULL, '13245675432', '222@as.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('e5487ce7-b4ff-4ce5-bc94-931a0b908ea3', '$2a$10$3fuih7nUxniMynu9US.ZfuUdyChTd6aJ/AyluDjdNcKuogZYLv.3u', 'asf', '123', '1', '', NULL, NULL, '13123232323', 'qcz@df.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('105a8170-39eb-4c00-9061-9ac6dc915165', '$2a$10$V/Q6.69PWTRJ1XETKOoSPeRc7P8aO6dajko8CTM26BFlj7ZntWNE.', 'asdfaasdf', 'asdfsadfd', '2', '', NULL, NULL, '13123232323', 'sadfsdf@sfd.dd', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('16', '$2a$10$OEZSm9DhiW7pHxPeAnRiBOqz2oAKzC13EwLcwG1PF2lKlDC4eJKrq', '我是DJ', 'ass', '1', '', NULL, NULL, '13187679076', 'asfsf@df.com', NULL, NULL);
INSERT INTO "public"."sys_user" VALUES ('12', '$2a$10$fGTnoqoIWd3mr86BqDJQOOHr3DiyVO0QrVo7zFVD247NiCEGJV3Y2', '打老虎', '456', '1', 'sfasfs', NULL, NULL, '18676767689', 'asdfasf@sfds.com', NULL, NULL);
INSERT INTO "public"."sys_user" VALUES ('10', '$2a$10$qfxTDehc7vdSKG3zafs9tOR14DfVZdwjmQ6/zTfuIM9PZeg2bwle.', '华为', 'sfdf', '1', 'sfasdf', NULL, NULL, '13123456678', '222@222.c', NULL, NULL);
INSERT INTO "public"."sys_user" VALUES ('15', '$2a$10$TYLiWGsoIdcngzCc6H2gwesbaDOLrkZNmn5JNn53plgfYU7ZogFQu', '李四', 'afsdf', '1', 'asd', NULL, NULL, '13187679076', 'asfsf@sdf.com', NULL, NULL);
INSERT INTO "public"."sys_user" VALUES ('1', '$2a$10$AITPnfou2evfgte63PP5mu.O3.ry0gsWLZqy/iOrPYtJmHrdhRWmu', 'user_1', 'user_1', '1', '备注', '2020-09-03 22:31:43', NULL, '18677776666', 'qcz_left@163.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('bb9f8a5a-41fe-4907-a8ac-d97b781a377c', '707730986aac34a3efdd6bfe4d437050', '程序员', 'java', '1', 'java', '2020-12-05 14:51:48.374', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '18675867653', 'qq@qq.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('4cee17bb-68f9-42a9-9b24-713c40b74d30', '$2a$10$BuoOFBeYXaFuPSFjWhUkU.42p8ACcjRR8UC2RjRxaJuci2OICyxIK', '隔壁老王', 'wang', '1', '', NULL, NULL, '13198989892', 'qcz_dfsdf@ll.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('2185d74c-df5c-4125-9d3b-9fd048118f73', '$2a$10$t91AUBlWbg4yWZzpWTOA6u6QvX7j2MmW9kukbvWcXweSVL1bZa8uy', '测试', 'test', '1', '可爱', '2020-10-03 13:33:58.702', NULL, '18787878787', '123r23@dd.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', 'df655ad8d3229f3269fad2a8bab59b6c', '系统管理员', 'admin', '2', '', NULL, NULL, '18677776666', 'qcz_left@163.com', 0, '/file/20201208093055_1607432807(1).jpg');
INSERT INTO "public"."sys_user" VALUES ('14', '$2a$10$a4F1A3GCjnFK7t3ZItnBf.l3bG9LEwiCCY56Pqki.Cf3hRhg.vHn.', '张三', 'sfd', '2', 'asfdsdf电饭锅', NULL, NULL, '13187679076', 'safs@com.asdf', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('5a4363f6-a31c-4859-bde4-8a38d9b0897d', 'ae86cb449cafddc0a5e4de0143d7fafd', '12323', '为首的发生', '1', '', '2020-12-05 16:08:22.729', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '18787653745', 'asfsf@d.com', 0, NULL);

-- ----------------------------
-- Table structure for sys_user_organization
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_organization";
CREATE TABLE "public"."sys_user_organization" (
  "user_id" varchar(50) COLLATE "pg_catalog"."default",
  "organization_id" varchar(50) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_user_organization"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."sys_user_organization"."organization_id" IS '机构id';
COMMENT ON TABLE "public"."sys_user_organization" IS '用户-机构对应关系';

-- ----------------------------
-- Records of sys_user_organization
-- ----------------------------
INSERT INTO "public"."sys_user_organization" VALUES ('16', '784c2e87-b88a-46b8-8cd3-871e4646df36');
INSERT INTO "public"."sys_user_organization" VALUES ('12', '6afd2fc3-6619-4a05-b98c-8ca4d66dbc75');
INSERT INTO "public"."sys_user_organization" VALUES ('bb9f8a5a-41fe-4907-a8ac-d97b781a377c', '8b5afd90-02aa-4362-9ce6-751cf68ff41b');
INSERT INTO "public"."sys_user_organization" VALUES ('4cee17bb-68f9-42a9-9b24-713c40b74d30', '11f67a26-22da-49c1-86e8-78ee2a55ac7f');
INSERT INTO "public"."sys_user_organization" VALUES ('2185d74c-df5c-4125-9d3b-9fd048118f73', '8b5afd90-02aa-4362-9ce6-751cf68ff41b');
INSERT INTO "public"."sys_user_organization" VALUES ('638f4342-bba9-49c3-97aa-1145c829b5c1', 'e087310b-b26f-4148-8fde-9d6c6ddf4f6e');
INSERT INTO "public"."sys_user_organization" VALUES ('4c659b6b-63c3-4754-82ca-5b2375e83e34', 'e087310b-b26f-4148-8fde-9d6c6ddf4f6e');
INSERT INTO "public"."sys_user_organization" VALUES ('5a4363f6-a31c-4859-bde4-8a38d9b0897d', '526f57b8-6b09-407d-9099-96fa38719814');
INSERT INTO "public"."sys_user_organization" VALUES ('e5487ce7-b4ff-4ce5-bc94-931a0b908ea3', '784c2e87-b88a-46b8-8cd3-871e4646df36');
INSERT INTO "public"."sys_user_organization" VALUES ('105a8170-39eb-4c00-9061-9ac6dc915165', '8b5afd90-02aa-4362-9ce6-751cf68ff41b');
INSERT INTO "public"."sys_user_organization" VALUES ('1', '8b5afd90-02aa-4362-9ce6-751cf68ff41b');
INSERT INTO "public"."sys_user_organization" VALUES ('1', '11f67a26-22da-49c1-86e8-78ee2a55ac7f');
INSERT INTO "public"."sys_user_organization" VALUES ('6b663de3-e983-4c8a-b900-dc0b66b2a801', '8b5afd90-02aa-4362-9ce6-751cf68ff41b');
INSERT INTO "public"."sys_user_organization" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', '11f67a26-22da-49c1-86e8-78ee2a55ac7f');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_role";
CREATE TABLE "public"."sys_user_role" (
  "user_id" varchar(50) COLLATE "pg_catalog"."default",
  "role_id" varchar(50) COLLATE "pg_catalog"."default"
)
;
COMMENT ON TABLE "public"."sys_user_role" IS '用户角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO "public"."sys_user_role" VALUES ('27b276c1-0faf-4090-9b29-bc4688c88d58', 'a77a83b4-abd7-4f3f-ac22-e914bb68ad5c');
INSERT INTO "public"."sys_user_role" VALUES ('bb9f8a5a-41fe-4907-a8ac-d97b781a377c', 'a77a83b4-abd7-4f3f-ac22-e914bb68ad5c');
INSERT INTO "public"."sys_user_role" VALUES ('2185d74c-df5c-4125-9d3b-9fd048118f73', '67d86434-c302-4924-a75a-dfe5fbc4affd');
INSERT INTO "public"."sys_user_role" VALUES ('638f4342-bba9-49c3-97aa-1145c829b5c1', 'test_tv');
INSERT INTO "public"."sys_user_role" VALUES ('4c659b6b-63c3-4754-82ca-5b2375e83e34', 'test_tv');
INSERT INTO "public"."sys_user_role" VALUES ('2b88e1c2-f97b-4c53-99e7-20ee351395f0', 'test_tv');
INSERT INTO "public"."sys_user_role" VALUES ('5a4363f6-a31c-4859-bde4-8a38d9b0897d', 'test_tv');
INSERT INTO "public"."sys_user_role" VALUES ('1', 'test_tv');
INSERT INTO "public"."sys_user_role" VALUES ('6b663de3-e983-4c8a-b900-dc0b66b2a801', 'a77a83b4-abd7-4f3f-ac22-e914bb68ad5c');
INSERT INTO "public"."sys_user_role" VALUES ('6b663de3-e983-4c8a-b900-dc0b66b2a801', '67d86434-c302-4924-a75a-dfe5fbc4affd');
INSERT INTO "public"."sys_user_role" VALUES ('6b663de3-e983-4c8a-b900-dc0b66b2a801', '3e8461bf-537d-4b7d-91db-9676a03fb6fb');
INSERT INTO "public"."sys_user_role" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', 'a77a83b4-abd7-4f3f-ac22-e914bb68ad5c');
INSERT INTO "public"."sys_user_role" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', '67d86434-c302-4924-a75a-dfe5fbc4affd');
INSERT INTO "public"."sys_user_role" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', '3e8461bf-537d-4b7d-91db-9676a03fb6fb');

-- ----------------------------
-- View structure for v_sys_permission
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_sys_permission";
CREATE VIEW "public"."v_sys_permission" AS  SELECT sys_menu.menu_id AS permission_id,
    sys_menu.menu_name AS permission_name,
    sys_menu.icon,
    sys_menu.code,
    sys_menu.iorder,
    sys_menu.parent_id,
    1 AS permission_type,
    sys_menu.link_url,
    sys_menu.display
   FROM sys_menu
UNION ALL
 SELECT sb.button_id AS permission_id,
    sb.button_name AS permission_name,
    sb.icon,
    sb.code,
    sb.iorder,
    sb.menu_id AS parent_id,
    2 AS permission_type,
    NULL::character varying AS link_url,
    1 AS display
   FROM sys_button sb
  WHERE (EXISTS ( SELECT 1
           FROM sys_menu sm
          WHERE ((sb.menu_id)::text = (sm.menu_id)::text)));
COMMENT ON VIEW "public"."v_sys_permission" IS 'permission_type（1：菜单，2：按钮）';

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."sys_user_id_seq"', 21, true);

-- ----------------------------
-- Primary Key structure for table sys_button
-- ----------------------------
ALTER TABLE "public"."sys_button" ADD CONSTRAINT "sys_button_pkey" PRIMARY KEY ("button_id");

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "sys_permission_pkey" PRIMARY KEY ("menu_id");

-- ----------------------------
-- Primary Key structure for table sys_operate_log
-- ----------------------------
ALTER TABLE "public"."sys_operate_log" ADD CONSTRAINT "sys_operate_log_pkey" PRIMARY KEY ("log_id");

-- ----------------------------
-- Primary Key structure for table sys_organization
-- ----------------------------
ALTER TABLE "public"."sys_organization" ADD CONSTRAINT "sys_organization_pkey" PRIMARY KEY ("organization_id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("role_id");

-- ----------------------------
-- Uniques structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "unique_loginname" UNIQUE ("loginname");
COMMENT ON CONSTRAINT "unique_loginname" ON "public"."sys_user" IS '登录名唯一';

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");
