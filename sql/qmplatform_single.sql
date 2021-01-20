/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1-pgsql
 Source Server Type    : PostgreSQL
 Source Server Version : 110010
 Source Host           : 127.0.0.1:5432
 Source Catalog        : qmplatform_single
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 110010
 File Encoding         : 65001

 Date: 20/01/2021 08:44:40
*/


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
-- Table structure for ope_data_bak
-- ----------------------------
DROP TABLE IF EXISTS "public"."ope_data_bak";
CREATE TABLE "public"."ope_data_bak" (
  "bak_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "bak_name" varchar(50) COLLATE "pg_catalog"."default",
  "bak_path" varchar(100) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "remark" varchar(500) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."ope_data_bak"."bak_id" IS '备份主键id';
COMMENT ON COLUMN "public"."ope_data_bak"."bak_name" IS '备份名称';
COMMENT ON COLUMN "public"."ope_data_bak"."bak_path" IS '备份路径';
COMMENT ON COLUMN "public"."ope_data_bak"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ope_data_bak"."remark" IS '备注';
COMMENT ON TABLE "public"."ope_data_bak" IS '数据备份';

-- ----------------------------
-- Records of ope_data_bak
-- ----------------------------
INSERT INTO "public"."ope_data_bak" VALUES ('4c42b74a-5f00-4f32-8a2b-35f2580ab13d', 'qmplatform_single20210103223026.dump', '/opt/web/bak/database/qmplatform_single20210103223026.dump', '2021-01-03 22:30:26.712', NULL);
INSERT INTO "public"."ope_data_bak" VALUES ('e25325da-230d-46c6-b57b-092844e49d24', 'qmplatform_single20210104010000.dump', '/opt/web/bak/database/qmplatform_single20210104010000.dump', '2021-01-04 01:00:00.144', NULL);
INSERT INTO "public"."ope_data_bak" VALUES ('9904d28b-47c1-4fe6-b208-38c9970a51b7', 'qmplatform_single20210105010000.dump', '/opt/web/bak/database/qmplatform_single20210105010000.dump', '2021-01-05 01:00:00.294', NULL);
INSERT INTO "public"."ope_data_bak" VALUES ('ac91ef3b-67e7-4188-890d-3c6f8e97104d', 'qmplatform_single20210106010000.dump', '/opt/web/bak/database/qmplatform_single20210106010000.dump', '2021-01-06 01:00:00.033', NULL);
INSERT INTO "public"."ope_data_bak" VALUES ('35cedc30-3b85-4674-946c-ea146f329cc1', 'qmplatform_single20210107010000.dump', '/opt/web/bak/database/qmplatform_single20210107010000.dump', '2021-01-07 01:00:00.129', NULL);

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
INSERT INTO "public"."sys_button" VALUES ('9ceffdf2-420e-40dc-9199-ab4ff049737b', '删除用户', 'ef07f63f-4ec1-4053-bb53-db7891359339', 'user-delete', 1, NULL);
INSERT INTO "public"."sys_button" VALUES ('4489ccf9-fec5-4f81-b817-0c1cd2c7a171', '保存用户', 'ef07f63f-4ec1-4053-bb53-db7891359339', 'user-save', 0, '');
INSERT INTO "public"."sys_button" VALUES ('985cf497-f3f0-4bea-8844-70fdc3ab4cde', '保存角色', '042a5960-785f-442e-a76a-576f7eb389c4', 'role-save', 0, NULL);
INSERT INTO "public"."sys_button" VALUES ('a9238df4-ca79-4ddb-afc9-74e01b808200', '删除角色', '042a5960-785f-442e-a76a-576f7eb389c4', 'role-delete', 1, NULL);
INSERT INTO "public"."sys_button" VALUES ('dd0268c3-1f3b-44f0-b94b-5d8df387019f', '分配角色', '042a5960-785f-442e-a76a-576f7eb389c4', 'role-allot', 2, NULL);
INSERT INTO "public"."sys_button" VALUES ('074f7b51-cb8c-4452-a188-e7afd4505930', '保存菜单', '82241eeb-ed7c-44f3-b9f0-56e986363907', 'menu-save', 0, NULL);
INSERT INTO "public"."sys_button" VALUES ('529279d9-6b31-4ed7-b61b-127f4257810e', '删除菜单', '82241eeb-ed7c-44f3-b9f0-56e986363907', 'menu-delete', 1, NULL);
INSERT INTO "public"."sys_button" VALUES ('117c2896-d27c-445d-bc88-240b8ac62032', '保存组织机构', '4af487ea-9903-4116-970e-d82dce9d49ce', 'org-save', 0, '');
INSERT INTO "public"."sys_button" VALUES ('e45cdf2e-692b-4ba3-aa65-464d8a9da97b', '删除组织机构', '4af487ea-9903-4116-970e-d82dce9d49ce', 'org-delete', 1, '');
INSERT INTO "public"."sys_button" VALUES ('b7564f6a-5ce5-49d8-9d15-e8fe396eecaa', '上传文件', 'f2a871d8-c1dd-4775-ad0d-8b8b3f53eede', 'file-upload', 1, '');
INSERT INTO "public"."sys_button" VALUES ('2e2e994a-da4f-4cb5-84fc-0324efae29f6', '删除文件', 'f2a871d8-c1dd-4775-ad0d-8b8b3f53eede', 'file-delete', 2, '');
INSERT INTO "public"."sys_button" VALUES ('1d1f59eb-69d8-4894-abfd-54805f36b7df', '保存字典', '428493a8-5e23-4e64-a24c-78e61b3ed854', 'dict-save', 0, '');
INSERT INTO "public"."sys_button" VALUES ('d0ceae95-12d9-40ea-abe3-03df0b44dd2e', '删除字典', '428493a8-5e23-4e64-a24c-78e61b3ed854', 'dict-delete', 0, '');
INSERT INTO "public"."sys_button" VALUES ('44ca4945-356e-499b-af2e-08035d39a619', '保存字典属性', '428493a8-5e23-4e64-a24c-78e61b3ed854', 'dict-attr-save', 0, '');
INSERT INTO "public"."sys_button" VALUES ('318e6adb-e225-4636-9546-6b132b715c24', '删除字典属性', '428493a8-5e23-4e64-a24c-78e61b3ed854', 'dict-attr-delete', 0, '');
INSERT INTO "public"."sys_button" VALUES ('bc8a4903-3b5c-4b7e-a3e1-3c851603ff8b', '定义字典属性', '428493a8-5e23-4e64-a24c-78e61b3ed854', 'dict-allot', 0, '');
INSERT INTO "public"."sys_button" VALUES ('cc2cbb01-f42f-4272-b02a-4c74484ca62a', '保存备份策略', '0ec94352-4ea3-4f59-961c-38ca6779d714', 'data-bak-strategy-save', 0, '');
INSERT INTO "public"."sys_button" VALUES ('37778f44-615d-4d69-bf8a-99ff0bafef40', '立即备份', '0ec94352-4ea3-4f59-961c-38ca6779d714', 'data-bak-save', 0, '');
INSERT INTO "public"."sys_button" VALUES ('8c9768b7-3574-471b-bd51-6bd6188f24b5', '删除备份', '0ec94352-4ea3-4f59-961c-38ca6779d714', 'data-bak-delete', 0, '');
INSERT INTO "public"."sys_button" VALUES ('44d656f2-86eb-4326-ad12-935ca160551d', '恢复备份', '0ec94352-4ea3-4f59-961c-38ca6779d714', 'data-bak-recover', 0, '');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict";
CREATE TABLE "public"."sys_dict" (
  "dict_id" varchar COLLATE "pg_catalog"."default" NOT NULL,
  "dict_name" varchar(12) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "sort" int2,
  "dict_code" varchar(20) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_dict"."dict_id" IS '字典主键id';
COMMENT ON COLUMN "public"."sys_dict"."dict_name" IS '字典名称';
COMMENT ON COLUMN "public"."sys_dict"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dict"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_dict"."dict_code" IS '字典编码';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO "public"."sys_dict" VALUES ('97e67908-5489-41f1-ae3a-dbc6b4752907', '性别', '用户管理-性别', 1, 'user-sex');
INSERT INTO "public"."sys_dict" VALUES ('148038cb-a0d5-4c62-9c8f-373a7c4f16ba', '用户状态', '用户管理-状态', 2, 'user-status');
INSERT INTO "public"."sys_dict" VALUES ('04202f98-521d-4e1e-895a-a7c8156a7f3e', '权限类型', '菜单管理-类型', 3, 'permission');
INSERT INTO "public"."sys_dict" VALUES ('a500f79b-5e4c-4dfb-8dae-d837587847d9', '菜单是否显示', '菜单管理-是否显示', 4, 'display');
INSERT INTO "public"."sys_dict" VALUES ('778223bf-498d-4625-a1f2-6cf9769291c8', '操作类型', '操作日志-操作类型', 5, 'operate-type');
INSERT INTO "public"."sys_dict" VALUES ('8ab45963-e454-4aed-a76e-1104fa97e1fa', '操作状态', '操作日志-操作状态', 10, 'operate-status');
INSERT INTO "public"."sys_dict" VALUES ('bdcef397-ddad-4656-8044-8d96774fd639', '短信提供商', '短信配置', 20, 'sms-provider');

-- ----------------------------
-- Table structure for sys_dict_attr
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_attr";
CREATE TABLE "public"."sys_dict_attr" (
  "attr_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "attr_value" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "attr_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_dict_attr"."attr_name" IS '属性名称';
COMMENT ON COLUMN "public"."sys_dict_attr"."attr_value" IS '属性值';
COMMENT ON COLUMN "public"."sys_dict_attr"."dict_id" IS '对应sys_dict的dict_id';
COMMENT ON COLUMN "public"."sys_dict_attr"."attr_id" IS '字典属性主键id';

-- ----------------------------
-- Records of sys_dict_attr
-- ----------------------------
INSERT INTO "public"."sys_dict_attr" VALUES ('男', '1', '97e67908-5489-41f1-ae3a-dbc6b4752907', 'f4ad8e47-040e-4e46-a8d5-2e16758cceca');
INSERT INTO "public"."sys_dict_attr" VALUES ('女', '2', '97e67908-5489-41f1-ae3a-dbc6b4752907', '5283f8c6-0a87-41cc-a1ae-2f99e7b207a2');
INSERT INTO "public"."sys_dict_attr" VALUES ('太监', '0', '97e67908-5489-41f1-ae3a-dbc6b4752907', '1555209e-b398-47d5-bf2b-bf4f0b072f92');
INSERT INTO "public"."sys_dict_attr" VALUES ('菜单', '1', '04202f98-521d-4e1e-895a-a7c8156a7f3e', '546a6dd4-ff78-4665-abd8-88e2f0d42466');
INSERT INTO "public"."sys_dict_attr" VALUES ('按钮', '2', '04202f98-521d-4e1e-895a-a7c8156a7f3e', '4a5008d2-dc75-4be8-b6b2-abfa82da575b');
INSERT INTO "public"."sys_dict_attr" VALUES ('显示', '1', 'a500f79b-5e4c-4dfb-8dae-d837587847d9', '31f03a5a-9938-4b4f-af24-f5f7c3d7e743');
INSERT INTO "public"."sys_dict_attr" VALUES ('隐藏', '0', 'a500f79b-5e4c-4dfb-8dae-d837587847d9', '1421293d-d1f2-4c48-b89e-cbbdc0d27890');
INSERT INTO "public"."sys_dict_attr" VALUES ('正常', '0', '148038cb-a0d5-4c62-9c8f-373a7c4f16ba', 'ff703593-116d-4ce6-97f3-2bf403eb73f0');
INSERT INTO "public"."sys_dict_attr" VALUES ('锁定', '1', '148038cb-a0d5-4c62-9c8f-373a7c4f16ba', '6966baf2-8284-4126-9af0-ab35615a570c');
INSERT INTO "public"."sys_dict_attr" VALUES ('退出系统', '-1', '778223bf-498d-4625-a1f2-6cf9769291c8', 'c277f016-f060-4ee6-a4d3-7e521bcd0e31');
INSERT INTO "public"."sys_dict_attr" VALUES ('进入系统', '1', '778223bf-498d-4625-a1f2-6cf9769291c8', 'ecd9ea03-a262-44b6-9e71-d36e13335c9b');
INSERT INTO "public"."sys_dict_attr" VALUES ('新增', '3', '778223bf-498d-4625-a1f2-6cf9769291c8', '2d5cdc58-8b6d-47be-a886-6cc165ec784c');
INSERT INTO "public"."sys_dict_attr" VALUES ('修改', '4', '778223bf-498d-4625-a1f2-6cf9769291c8', '7b41d694-6c33-4468-80c2-010010d9a789');
INSERT INTO "public"."sys_dict_attr" VALUES ('删除', '5', '778223bf-498d-4625-a1f2-6cf9769291c8', '74a6164c-3c74-42ef-a016-06028fd2d615');
INSERT INTO "public"."sys_dict_attr" VALUES ('成功', '1', '8ab45963-e454-4aed-a76e-1104fa97e1fa', '0e26086e-e284-4c60-9caf-d112ad9aa7da');
INSERT INTO "public"."sys_dict_attr" VALUES ('失败', '0', '8ab45963-e454-4aed-a76e-1104fa97e1fa', 'fa408d46-918c-4663-84ad-aacf48cae2ff');
INSERT INTO "public"."sys_dict_attr" VALUES ('腾讯云', '1', 'bdcef397-ddad-4656-8044-8d96774fd639', '14413e2e-fd8d-4e42-8c31-4a30d74dc539');
INSERT INTO "public"."sys_dict_attr" VALUES ('阿里云', '2', 'bdcef397-ddad-4656-8044-8d96774fd639', '4f203b9b-1bb8-4cdd-849c-54208048856c');

-- ----------------------------
-- Table structure for sys_ini
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_ini";
CREATE TABLE "public"."sys_ini" (
  "section" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "property" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "value" varchar(100) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_ini"."section" IS '属性组';
COMMENT ON COLUMN "public"."sys_ini"."property" IS '属性名';
COMMENT ON COLUMN "public"."sys_ini"."value" IS '属性值';
COMMENT ON TABLE "public"."sys_ini" IS '系统属性配置';

-- ----------------------------
-- Records of sys_ini
-- ----------------------------
INSERT INTO "public"."sys_ini" VALUES ('DataBak', 'EnableBak', '1');
INSERT INTO "public"."sys_ini" VALUES ('DataBak', 'Period', '127');
INSERT INTO "public"."sys_ini" VALUES ('DataBak', 'LimitDiskSpace', '20');
INSERT INTO "public"."sys_ini" VALUES ('DataBak', 'SaveDays', '30');

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
INSERT INTO "public"."sys_menu" VALUES ('0ec94352-4ea3-4f59-961c-38ca6779d714', '数据备份与恢复', 40, 'data-bak', 'layui-icon-list', '/operation/data-bak/dataBakListPage', '326ad00b-2e0f-4941-9191-a573ada65410', 1);
INSERT INTO "public"."sys_menu" VALUES ('1', '系统管理', 0, 'system', 'layui-icon-set', '', '', 1);
INSERT INTO "public"."sys_menu" VALUES ('b555a066-c76d-4b8f-a834-4998cc000711', '测试404', 70, 'ceshi404', 'layui-icon-404', '/error/404', '', 1);
INSERT INTO "public"."sys_menu" VALUES ('e36040a7-f7cb-42f8-a55f-0a6f6e138958', '测试500', 80, 'ceshi500', 'layui-icon-face-cry', '/error/500', '', 1);
INSERT INTO "public"."sys_menu" VALUES ('ec2d5dbf-ace1-4fe8-b85e-70fc84bf517e', '操作日志', 60, 'operate', 'layui-icon-log', '/operate-log/logListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('bfba142d-6a80-417b-9e58-a3671283f2fb', '图标管理', 40, 'icon', '', '/icon/iconListPage', '1', 0);
INSERT INTO "public"."sys_menu" VALUES ('ef07f63f-4ec1-4053-bb53-db7891359339', '用户管理', 0, 'user', 'layui-icon-user', '/user/userListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('82241eeb-ed7c-44f3-b9f0-56e986363907', '菜单管理', 50, 'menu', 'layui-icon-more', '/menu/menuListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('042a5960-785f-442e-a76a-576f7eb389c4', '角色管理', 1, 'role', 'layui-icon-auz', '/role/roleListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('4af487ea-9903-4116-970e-d82dce9d49ce', '组织机构管理', 30, 'org', 'layui-icon-component', '/organization/organizationListPage', '1', 1);
INSERT INTO "public"."sys_menu" VALUES ('f2a871d8-c1dd-4775-ad0d-8b8b3f53eede', '文件管理', 10, 'file', 'layui-icon-file', '/archive/attachment/attachmentListPage', '9ac8ef82-57c9-461e-9ee0-e2c892e8bcac', 1);
INSERT INTO "public"."sys_menu" VALUES ('9ac8ef82-57c9-461e-9ee0-e2c892e8bcac', '档案中心', 1, 'archive', 'layui-icon-app', '', '', 1);
INSERT INTO "public"."sys_menu" VALUES ('326ad00b-2e0f-4941-9191-a573ada65410', '运维管理', 30, 'operation', 'layui-icon-survey', '', '', 1);
INSERT INTO "public"."sys_menu" VALUES ('e843dfa8-e868-4a83-aa35-215cbfbfd643', '数据库监控', 3, 'data-monitor', 'layui-icon-about', '/druid', '326ad00b-2e0f-4941-9191-a573ada65410', 1);
INSERT INTO "public"."sys_menu" VALUES ('80345dc4-cc15-405b-a371-82e50b64fcd9', '系统运行状态', 1, 'system-status', 'layui-icon-website', '/server/infoPage', '326ad00b-2e0f-4941-9191-a573ada65410', 1);
INSERT INTO "public"."sys_menu" VALUES ('428493a8-5e23-4e64-a24c-78e61b3ed854', '字典管理', 4, 'dict', 'layui-icon-tips', '/operation/dict/dictListPage', '326ad00b-2e0f-4941-9191-a573ada65410', 1);
INSERT INTO "public"."sys_menu" VALUES ('bd8cae46-b3bc-48e1-bc6d-c623493fe64c', '短信配置', 50, 'sms', '', '/notify/smsConfigPage', '326ad00b-2e0f-4941-9191-a573ada65410', 1);

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
INSERT INTO "public"."sys_operate_log" VALUES ('b143027f-f2db-452c-8e2f-336c5e3df096', 1, '登录系统', NULL, NULL, '2020-12-08 19:59:41.675', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('96654d88-eea5-4500-a37f-3cce49567305', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:00:31.121', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a53caf77-cc12-4e17-8bb7-dfba06338938', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:00:44.353', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f8013bbb-eebc-41da-944e-b5dd102e5bfa', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:17:55.505', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7ae4901a-6c7e-4337-b793-75d1e715b84d', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:19:43.33', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('357230cb-5da5-4342-b0c4-edbd5a15081d', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:21:47.23', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('723e7df7-c164-4d17-ada8-85ec320cf4f7', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:28:25.55', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9448421c-1106-459b-ad15-03e27162aaa5', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 21:07:20.434', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b8ed50f7-9c23-4fcf-8981-a006214f886d', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 21:08:25.446', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9a4ec5fc-5e25-4bfc-b178-ab793217dac9', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 21:09:44.412', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('54167913-2625-4f64-96da-22aaa57900dc', 5, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 13:39:37.598', '/user/delUser', '删除用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('49ddd7c5-02dd-48bd-bea4-784a15847020', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 14:49:42.587', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3ae137a1-56b9-423a-8abb-f91e054100bc', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:41:44.673', '/operation/data-bak/exeBackup', '立即备份', '127.0.0.1', 0, 'java.lang.Exception:org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 16
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:77)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)');
INSERT INTO "public"."sys_operate_log" VALUES ('11840fa7-83e0-4cce-94ea-c81655503241', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:45:30.179', '/operation/data-bak/exeBackup', '立即备份', '127.0.0.1', 0, 'java.lang.Exception:java.lang.NullPointerException
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:77)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocati');
INSERT INTO "public"."sys_operate_log" VALUES ('e4663a6d-d1b1-4d45-b389-03a51e317838', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:46:15.33', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7c830ee8-c16d-4faa-9d0b-a0cd329e6b5c', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:46:16.817', '/operation/data-bak/exeBackup', '立即备份', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c38ea9cc-731a-4cae-9c8c-70b6f0c2055c', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:47:17.654', '/operation/data-bak/exeBackup', '立即备份', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c8d90aee-7a7c-4601-8e2b-de8cb857ed22', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:47:24.397', '/operation/data-bak/exeBackup', '立即备份', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0d2c62a5-32ee-4373-9e38-1a880c88fb24', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:48:24.069', '/operation/data-bak/exeBackup', '立即备份', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e7296eb0-247d-4cd3-8982-2b762923f382', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:49:53.42', '/operation/data-bak/exeBackup', '立即备份', '127.0.0.1', 0, 'java.lang.Exception:cn.hutool.core.io.IORuntimeException: IOException: Cannot run program "pg_dump": CreateProcess error=2, 系统找不到指定的文件。
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:77)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAf');
INSERT INTO "public"."sys_operate_log" VALUES ('de363f06-5722-48cc-8077-a2e2cf008422', 1, '登录系统', NULL, NULL, '2020-12-31 10:31:46.977', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e5f61c11-ae5a-401f-aeff-50f9cc790835', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:33:48.707', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7e64d5d5-bf57-4d8f-b784-bfad4d2ada24', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:33:59.918', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3a92f286-28ea-4caa-a75a-cc08ac097121', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:35:33.488', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8a14236e-192a-4f1e-9bf1-26766e920893', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:35:41.14', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6a0e96a6-d646-48bb-a841-b0c5d4561712', 1, '登录系统', NULL, NULL, '2020-12-31 11:10:11.908', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fd1c5304-192e-4604-b338-7ba56d8cd5c0', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:00:56.493', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5e34edb5-29a8-44e3-97e9-66440d485ba5', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:18:20.024', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fba4e501-7f58-4e7c-834d-8cddb779a791', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:21:06.172', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9f4ab9cd-50b3-452e-93db-b5a606d7eb8a', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 20:23:25.78', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
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
INSERT INTO "public"."sys_operate_log" VALUES ('f7aeed32-b377-4945-8e5a-987b18318d8e', 1, '登录系统', NULL, NULL, '2020-12-08 21:38:54.21', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('91b78eb4-f166-4121-9147-8aa0ef40e47b', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 21:39:12.748', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('91bbc136-1bd2-4cbb-a881-e30a64685436', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 21:44:08.244', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a521506f-3c94-4c2a-8e4c-246a25729431', 3, '组织机构管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 21:52:18.209', '/organization/addOrg', '新增组织机构', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d007e2d1-4428-4882-a5d5-7110671ae7d1', 3, '组织机构管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 21:52:39.967', '/organization/addOrg', '新增组织机构', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e0a1a33e-4655-4c19-88fd-cbb709c24b1a', 5, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 22:09:02.841', '/user/delUser', '删除用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('935eb6dd-8eb0-4d63-be9f-5cd33429362c', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 22:09:39.571', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('364206b3-9285-42a4-950f-6b7d71196479', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 22:09:48.333', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3969a274-eb05-4d43-b249-84af99880274', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 22:09:56.663', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('58d118cd-3de8-417b-b277-c6af15e96c10', -1, '退出系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-08 22:17:45.552', '/logout', '屈长忠 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bb1d6c2b-11d0-4fbf-bba9-6f0989d10482', 1, '登录系统', NULL, NULL, '2020-12-08 22:17:47.456', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b4e40a7c-8e10-47ca-bf57-20ef95c05cb6', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-08 22:19:08.014', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f12483bd-b151-46a2-ab9e-cc1a2fbd2ce4', 1, '登录系统', NULL, NULL, '2020-12-09 11:38:12.308', '/login', '系统管理员 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f7fd27a0-c7c4-4be0-ae59-847e6046d1cd', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 11:38:30.918', '/user/uploadUserImg', '上传用户图像', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ccc5054d-86ee-4ba7-af77-14c8c5449c4d', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 11:39:29.694', '/logout', '系统管理员 退出了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7c9b8e61-101f-42c9-af7e-7f700f1924c7', 1, '登录系统', NULL, NULL, '2020-12-09 11:42:25.723', NULL, '系统管理员 进入了系统', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('29a51774-e449-4b2f-b886-1733a0668faf', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 11:42:39.47', NULL, '上传用户图像', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('84467a36-10dc-41d9-a60b-bf54409600f6', 1, '登录系统', NULL, NULL, '2020-12-09 11:50:24.341', '/login', '系统管理员 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b9254688-2b67-43bc-ba08-7815aa70926e', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 11:50:30.955', '/user/uploadUserImg', '上传用户图像', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bfc4d3c6-ed90-4a3c-9936-3c61398edbcd', 1, '登录系统', NULL, NULL, '2020-12-09 13:56:41.462', '/login', '系统管理员 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('23774832-ed0d-43d1-a15d-a3b247220f31', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 13:56:41.531', NULL, '修改用户', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('38c5a85a-d071-4647-b0c3-8ee347e502f0', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 13:56:45.662', '/user/updateUser', '修改用户', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a2df69dd-342f-40f6-af79-f44820b23ac9', 1, '登录系统', NULL, NULL, '2020-12-09 14:16:29.293', '/login', '屈长忠 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9d2eb898-b8fb-4152-b52e-78fc39cd396c', -1, '退出系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-09 14:16:55.389', '/logout', '屈长忠 退出了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0bad6f1f-e314-47a8-8a6e-84c8d94dc30d', 1, '登录系统', NULL, NULL, '2020-12-09 14:17:06.226', NULL, '系统管理员 进入了系统', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bd220423-a525-43a8-a96b-ef12572c5f6f', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 14:17:20.7', '/user/uploadUserImg', '上传用户图像', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('57a84557-683b-4fe2-9f49-e1c2ea47df2f', 1, '登录系统', NULL, NULL, '2020-12-09 14:24:13.141', '/login', '系统管理员 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c1cb056e-c77b-4cec-8c5b-8dae5b5bb8a2', 1, '登录系统', NULL, NULL, '2020-12-09 14:29:59.402', '/login', '系统管理员 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c6894932-d480-48cc-ab14-3163dd171969', 1, '登录系统', NULL, NULL, '2020-12-09 15:01:32.135', '/login', '系统管理员 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('94e77ee6-cd7d-4e65-b473-465cc0e38b79', 1, '登录系统', NULL, NULL, '2020-12-09 15:12:34.406', '/login', '系统管理员 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b74e4818-d1db-4c2b-a76c-d4e4c915e9e1', 1, '登录系统', NULL, NULL, '2020-12-09 15:31:25.237', '/login', '系统管理员 进入了系统', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bb145e2e-194b-4270-9fad-60c231977c72', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 15:52:41.801', '/user/uploadUserImg', '上传用户图像', '183.15.178.42', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e7b95716-4f19-45a8-bcd0-0dce48fa5efa', 1, '登录系统', NULL, NULL, '2020-12-09 17:40:02.435', NULL, '系统管理员 进入了系统', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b7be59ed-fbc1-4003-8f6b-d58059039f31', 1, '登录系统', NULL, NULL, '2020-12-09 20:08:15.848', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e8b67f45-9d26-49bc-b042-330c7d659718', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 20:08:49.712', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 0, 'java.lang.Exception:java.io.IOException: 文件名、目录名或卷标语法不正确。
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:83)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodI');
INSERT INTO "public"."sys_operate_log" VALUES ('9d30dde0-8b2a-44b6-901c-6826831e842b', 1, '登录系统', NULL, NULL, '2020-12-09 20:11:37.659', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e6497b5d-b850-4743-a017-69c274e4e7a6', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-09 20:11:54.481', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('90adb433-2804-402e-8a2c-7766d7cfdcc2', 1, '登录系统', NULL, NULL, '2020-12-09 20:13:49.509', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('efba7b98-5457-4577-96d2-fe21bb643e9f', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 20:13:55.296', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9f7b1e9c-d94f-4f1a-bfdf-2b68b8a0cb5e', 1, '登录系统', NULL, NULL, '2020-12-09 20:16:35.033', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f29677cc-a0d8-45b5-9d3f-b8f9294f4211', 1, '登录系统', NULL, NULL, '2020-12-09 20:35:33.413', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1e62e583-547a-465c-8129-999362bdceaa', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 21:31:06.684', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7b5db378-796e-400f-ae57-ea2027ab7002', 1, '登录系统', NULL, NULL, '2020-12-09 21:31:08.109', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a78dbd82-8e85-4333-8791-0d29f1b6fbd8', 1, '登录系统', NULL, NULL, '2020-12-09 21:31:45.63', NULL, '系统管理员 进入了系统', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('40ab3934-bd45-4fa1-a46f-a433ad59cb7d', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 21:31:59.034', NULL, '上传用户图像', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ddb4d85a-4a6d-431b-98fc-d4718c0a5b5d', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-09 21:32:58.95', '/logout', '系统管理员 退出了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7a2204f9-270a-4af1-bf6b-924eb56c9c2e', 1, '登录系统', NULL, NULL, '2020-12-09 21:33:03.539', NULL, '屈长忠 进入了系统', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('354a5b58-b349-44ba-97cf-d340f29c21bf', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-09 21:33:58.15', NULL, '修改用户', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2271e6db-52ca-424a-b968-d9f59b8c6b8e', 1, '登录系统', NULL, NULL, '2020-12-09 22:35:55.946', NULL, '系统管理员 进入了系统', NULL, 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('dee789b5-f236-4a24-b38d-67a3b2441c48', 1, '登录系统', NULL, NULL, '2020-12-09 22:49:25.24', '/login', '屈长忠 进入了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('222e9f43-e36e-4cd8-9783-836803a87f9d', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-09 22:49:33.056', '/user/uploadUserImg', '上传用户图像', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0eb89974-4b56-40e4-af8b-d61ff53414e3', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-09 22:54:44.247', '/user/updateUser', '修改用户', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e772eb5e-9a85-480a-b3fe-c246d560a144', 1, '登录系统', NULL, NULL, '2020-12-09 23:11:42.145', '/login', '屈长忠 进入了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('001d37f7-22ed-4ec3-9b73-963b6b80bf33', 1, '登录系统', NULL, NULL, '2020-12-09 23:13:48.259', '/login', '屈长忠 进入了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2ed3ee6a-8cce-4349-b936-d19675fe4e47', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-09 23:16:15.269', '/user/saveCurrentUserInfo', '修改当前用户基本资料', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1f008da1-ce6b-487d-a27d-39414d79b81b', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-09 23:16:24.622', '/user/saveCurrentUserInfo', '修改当前用户基本资料', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3773685b-ab2d-44ae-821c-55a837c71a3b', 1, '登录系统', NULL, NULL, '2020-12-10 08:12:47.895', '/login', '屈长忠 进入了系统', '116.24.67.55', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4379ab13-b5c3-4eef-8a4b-ab080af7a3ce', 1, '登录系统', NULL, NULL, '2020-12-10 20:22:04.495', '/login', '系统管理员 进入了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4f6eaff4-80de-4df8-993a-8a67dd69d1a1', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-10 20:22:58.163', '/user/updateUser', '修改用户', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ac5daa02-6fe5-4909-a9c1-5f818fc54c45', 1, '登录系统', NULL, NULL, '2020-12-10 20:32:30.525', '/login', '系统管理员 进入了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('43c6c21c-5ad3-4d25-8903-24191232badd', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-10 20:32:36.349', '/logout', '系统管理员 退出了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1f1bd5b5-3e2d-4033-a53c-2f5fc3380640', 1, '登录系统', NULL, NULL, '2020-12-10 20:32:39.797', '/login', '屈长忠 进入了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d60f13ef-0fa8-4f38-a42b-dfbf073ae0f5', -1, '退出系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-10 20:32:48.858', '/logout', '屈长忠 退出了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9f102c1b-02c1-4acf-b6be-4c8530873d0a', 1, '登录系统', NULL, NULL, '2020-12-10 20:32:50.025', '/login', '系统管理员 进入了系统', '119.123.134.147', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('86e8ae24-438e-429a-b817-0e5e9cee2ea9', 1, '登录系统', NULL, NULL, '2020-12-10 20:50:00.284', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fbc50886-9a3d-4e55-b595-5e460a32294e', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-10 20:50:11.026', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9d6839d5-2afd-4895-8d94-4d129c1c929b', -1, '退出系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-10 20:54:09.697', '/logout', '屈长忠 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6d76d1ca-f473-4497-9060-aab066b1eb4f', 1, '登录系统', NULL, NULL, '2020-12-10 20:57:03.472', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0f3cc1a3-1e63-4f19-9f13-feadc84d6eea', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 14:50:58.609', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('859ff3aa-7146-4f71-bb0a-d9bef0bcf469', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 15:51:15.329', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b0af5e49-57a2-46ce-b3a0-fc608ee600f3', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:32:15.848', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e3a9468b-e227-4701-af9b-86a1701fa74e', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:33:12.024', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('436e6bcd-aa1f-443f-8d9a-f75b6d23f36b', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:34:33.459', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5d4c81db-2d06-4cc6-93a4-718386859e15', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:39:09.757', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7db79fe6-c2dd-4c69-bc1a-6dbf57e4f70f', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:39:16.444', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a24a949a-c932-412a-9203-e53c24781590', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:39:28.244', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f22e7920-5746-4281-b3ed-063878248441', 1, '登录系统', NULL, NULL, '2020-12-31 11:32:58.923', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b2fed0c1-a9e7-4caf-abb1-c99a5c660fe2', 1, '登录系统', NULL, NULL, '2020-12-31 13:52:14.827', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3027b75e-859f-4a3b-9f9f-20a79b25f63e', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 13:56:56.517', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b350568d-fd4d-40cc-a13f-6236e631d009', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 17:31:18.997', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('77a0d1b9-dd98-431c-984b-53378ab31365', 1, '登录系统', NULL, NULL, '2020-12-31 21:59:40.546', '/login', '系统管理员 进入了系统', '27.46.46.43', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e0dc4557-29d6-491b-9525-5c298a3489b7', 1, '登录系统', NULL, NULL, '2020-12-31 22:15:53.655', '/login', '系统管理员 进入了系统', '27.46.46.43', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('aa5c3896-a935-4f23-96bc-ace4c960b4e0', 1, '登录系统', NULL, NULL, '2020-12-31 22:47:25.985', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6ae52522-a2d0-4437-af75-f8cbb3f7fba0', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 14:00:01.945', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('557f7a11-1c42-4d9a-b16b-28f0c6cb3c38', 1, '登录系统', NULL, NULL, '2021-01-01 15:45:14.915', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('243a55a0-557e-40f8-b861-408fc9a1651d', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:48:19.638', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a8535595-ba46-4d04-b9a9-6d06e0d9f263', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:52:36.467', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d27c9aa2-392e-4ff5-ae3e-0ff6a2f248ce', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:53:03.509', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3ee2dbc8-e6e8-4939-9ec2-21570d5b6679', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:53:11.612', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9dfb593d-29fd-4495-a008-04c7f529bc06', 1, '登录系统', NULL, NULL, '2021-01-01 15:53:15.457', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e510257e-dfd7-4866-99a9-b4d29103739e', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:54:46.27', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('71be32e0-7b0d-4e37-b96e-4a64f370b265', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:55:35.25', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7821c7e5-6978-4529-9860-72b356f4b399', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:57:59.059', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('89f475f5-4329-4a69-90c3-7dbaeb0f708a', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:58:11.279', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4af7072f-9890-42d6-a6d7-256bf49f7142', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:01:03.137', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('91a72fba-7fb1-4be3-b73a-25d868bf1661', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:01:31.639', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('12276d5a-d17e-45e9-b9f2-a0716e512350', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:06:53.259', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('917f9ecf-7b10-4cf0-98fd-f48b882549ef', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:06:56.658', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('404cb9fd-133b-4c2e-ae92-4d86397be5fd', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:08:12.771', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ef469254-ef12-4c76-8ab0-ff5d22099d69', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:08:27.188', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5728ff59-0832-4822-87de-9ccd1f63cc38', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:08:28.101', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7e6f1c1a-ecc8-42ae-8d56-29f36e72cecd', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:08:28.713', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d1977738-62f4-44b9-ab83-1a0197ed8703', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:08:32.224', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('65b19f00-10ad-4975-84e0-0e820f7b6b8a', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:08:35.505', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e7859ac7-8744-4463-9f3a-6f6b5395c02e', 1, '登录系统', NULL, NULL, '2021-01-01 16:08:36.465', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('57fb7cd1-9614-4076-8a56-6eaa3c4163cd', 1, '登录系统', NULL, NULL, '2021-01-01 16:15:36.881', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a16d1bc6-1495-4736-8ab3-eacad3d0fc4d', 1, '登录系统', NULL, NULL, '2021-01-01 17:28:04.556', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c71b40e8-5e6f-40d5-a93b-e606e7106e87', 1, '登录系统', NULL, NULL, '2020-12-13 15:08:33.856', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7704c192-cd44-449d-bd3e-65201dc29d50', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-10 20:57:17.092', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('77eb7f5d-6610-4334-bac7-8c050d8ddc4c', 1, '登录系统', NULL, NULL, '2020-12-10 20:59:10.611', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9a48dabe-7535-4011-9c98-3153e57a7a0e', 1, '登录系统', NULL, NULL, '2020-12-10 21:19:30.357', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('30cecc08-def5-4858-9e63-fa0bd7c2869e', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-10 21:19:53.083', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('53864c76-2d88-4850-97a5-47d46df90a6a', 1, '登录系统', NULL, NULL, '2020-12-10 21:22:04.46', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('78803e1d-c773-4b8a-b31b-460855194643', 1, '登录系统', NULL, NULL, '2020-12-10 21:24:54.131', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b9d97386-29b4-4552-a298-a62b80ce9bf8', 1, '登录系统', NULL, NULL, '2020-12-11 22:39:53.622', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5f9a685b-5f4c-410c-a0cb-75d4fe3a1021', 3, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-11 22:42:10.93', '/user/addUser', '新增用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7cffbe3b-55e6-40e9-afbe-6cb3dcd39d51', 3, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-11 22:45:39.349', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b401abac-0fba-4683-a9f7-cbac1799c7ba', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-11 22:46:14.422', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cdc39d40-3860-47bc-876a-c580e282c0e3', 1, '登录系统', NULL, NULL, '2020-12-12 12:22:23.32', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('60f99bd4-cf08-4b04-800f-22ebdd4c6f9e', 0, NULL, '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-12 12:29:52.689', '/operate-log/getLogList', NULL, '127.0.0.1', 0, 'org.springframework.jdbc.BadSqlGrammarException:
### Error querying database.  Cause: org.postgresql.util.PSQLException: ERROR: column "operateusername" does not exist
  建议：Perhaps you meant to reference the column "sys_operate_log.operate_user_name".
  位置：44
### The error may exist in com/qcz/qmplatform/module/system/mapper/OperateLogMapper.java (best guess)
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: SELECT count(0) FROM sys_operate_log WHERE operateUserName LIKE ?
### Cause: org.postgresql.util.PSQLException: ERROR: column "operateusername" does not exist
  建议：Perhaps you meant to reference the column "sys_operate_log.operate_user_name".
  位置：44
; bad SQL grammar []; nested exception is org.postgresql.util.PSQLException: ERROR: column "operateusername" does not exist
  建议：Perhaps you meant to reference the column "sys_operate_log.operate_user_name".
  位置：44
	org.springframework.jdbc.support.SQLStateSQLExceptionTransla');
INSERT INTO "public"."sys_operate_log" VALUES ('0151f700-dcee-4e13-a0be-be0739ba3ee7', 3, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-12 12:52:56.776', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('39adcaf8-88a9-4689-a9e1-471ef75bd1f0', 4, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-12 12:53:03.574', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('dbbbcc36-e183-493d-87a8-3111642dd06e', 1, '登录系统', NULL, NULL, '2020-12-12 16:53:47.402', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2d8b24b1-0eba-4631-959c-eb51bbbef739', 1, '登录系统', NULL, NULL, '2020-12-12 21:54:00.218', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('32dd7ec4-13ad-4159-ac2f-3935812aa333', 4, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 21:54:45.058', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('06cdae30-f630-4f76-9a7b-be1fd09926ad', 1, '登录系统', NULL, NULL, '2020-12-12 22:27:39.688', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('da0ec8c1-6e51-44c4-a8b6-866ba69d919d', 1, '登录系统', NULL, NULL, '2020-12-12 22:37:13.035', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('49a13e9e-36b8-4f15-a698-f11db59041c0', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 22:37:26.603', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('176ca233-bb30-4553-b620-294dc4071aa9', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 22:40:00.756', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7d714c71-287a-479e-9a9d-5e0a9b1abf76', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 22:41:38.325', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e6f13079-ae4a-4404-aa1f-bd091a306a93', 1, '登录系统', NULL, NULL, '2020-12-12 22:44:43.936', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4c0bc47d-c6cc-4d36-8925-ce1efb8dd5ff', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 22:45:00.569', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f551f250-5b46-43fc-8013-e208b7f6f37a', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 22:45:50.105', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f86d30f6-3641-4418-bbd1-33527175f74e', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 22:50:04.063', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b7922b66-35fa-41ab-b783-35ca3306c98d', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 22:51:24.382', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b5631a49-9b74-4242-be05-289ba41909c3', 1, '登录系统', NULL, NULL, '2020-12-12 23:15:13.998', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('872175b2-4f46-4019-86d7-bedaed428b55', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 23:15:22.412', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 0, 'java.lang.Exception:java.io.FileNotFoundException: File does not exist: \file\20201212103726_个人笔记.txt
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
org.spr');
INSERT INTO "public"."sys_operate_log" VALUES ('8bed4bda-2e10-4982-ac95-434c07269d63', 1, '登录系统', NULL, NULL, '2020-12-13 14:56:16.124', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('07d55d59-4814-4e6b-982f-9a6f5cfa7fc2', 1, '登录系统', NULL, NULL, '2020-12-13 15:00:25.004', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('29d5d44d-87aa-440d-81a5-391a0b5f3db1', 1, '登录系统', NULL, NULL, '2020-12-13 15:03:35.66', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('69e308c5-f417-4e6e-bdea-cd8a6f07690c', 1, '登录系统', NULL, NULL, '2020-12-13 15:04:17.903', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ed44f1c8-f610-4c2d-b94a-1b9e2a12566e', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 23:16:02.171', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 0, 'java.lang.Exception:java.io.FileNotFoundException: File does not exist: \opt\web\file\file\20201212103726_个人笔记.txt
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:84)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.jav');
INSERT INTO "public"."sys_operate_log" VALUES ('392110ce-8be2-40b5-968a-4b17b0683ca5', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 23:32:14.338', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 0, 'java.lang.Exception:java.io.FileNotFoundException: File does not exist: \opt\web\file\20201212103726_个人笔记.txt
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:84)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)');
INSERT INTO "public"."sys_operate_log" VALUES ('50657bf6-f9a7-48ba-8284-a8a2b42abba0', 1, '登录系统', NULL, NULL, '2020-12-12 23:37:04.396', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('29aaa452-f45a-4884-b72d-5263010bd90e', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 23:37:11.667', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c012adcf-8eda-406c-957c-695aa7f9886c', 1, '登录系统', NULL, NULL, '2020-12-12 23:54:06.866', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('adbff715-dc08-44a7-88d4-0001f019ac1a', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-12 23:54:23.282', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('29527978-6348-4af9-907e-9ad5428d03c7', 1, '登录系统', NULL, NULL, '2020-12-13 12:42:43.774', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('903cac47-2bfc-48c5-85e6-6639389d46b2', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:42:58.352', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('680bea1a-a3ce-4587-9255-31fd37802d80', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:43:25.277', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8a52ee1c-0da3-4e30-998a-766e429a70c0', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:44:38.568', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f201cc41-b622-402f-8d78-9ca9240a96d7', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:46:44.892', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('45c0e013-80a2-463e-81af-380cf1f1b9db', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:46:58.932', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a2f56686-7443-4249-95c1-1bbbe1baae23', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:47:17.245', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('52edace1-ce54-4619-83ad-1adbdb48e717', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:47:22.242', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a03719ac-634f-4d02-93e8-bcdef306f71d', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:48:41.285', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2f559d3c-e09b-472d-8229-4d4b513d72b3', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:49:30.23', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1d9a1f47-ce0d-4c60-8148-e244aadbc4d9', 1, '登录系统', NULL, NULL, '2020-12-13 12:53:38.771', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('92e6f902-42e4-4270-8073-29d3983d5dd7', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:53:52.08', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c13d6f46-0295-46ef-9b51-b9d51e3f402d', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:53:54.693', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('988ec3eb-fbc0-4113-acb6-258fac67b632', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:54:03.351', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('860f9b82-66dd-4cac-84e1-76b980dba653', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:54:34.18', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('170d9e1e-6163-4e17-809f-50684b619034', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 12:55:33.196', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('65b0630d-e342-499f-b39e-34ab1b4d5afe', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 13:10:22.13', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6b97afae-8695-4874-871d-e4d06277018e', 1, '登录系统', NULL, NULL, '2020-12-13 14:06:31.164', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e3088ee3-b3d1-465d-8ca4-e8fc58827da2', 1, '登录系统', NULL, NULL, '2020-12-13 14:17:51.279', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0791915d-ce2a-4d04-8750-7c608dd7d73d', 1, '登录系统', NULL, NULL, '2020-12-13 14:18:32.723', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('03d44e87-dff5-40b0-a4d4-6e8b59aa009d', 1, '登录系统', NULL, NULL, '2020-12-13 14:22:20.505', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b2134971-69f7-4264-a5c7-493daef92940', 1, '登录系统', NULL, NULL, '2020-12-13 14:23:47.23', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1bed19a6-69be-4951-a87a-687d5950b6a4', 1, '登录系统', NULL, NULL, '2020-12-13 14:27:32.689', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cb072c6f-cc89-45d0-8661-78f0a10c5800', 1, '登录系统', NULL, NULL, '2020-12-13 14:30:41.405', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cb8c00a8-3c11-4e21-9ee7-ea7a1f49a684', 1, '登录系统', NULL, NULL, '2020-12-13 14:33:53.335', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2a9a7ec3-5363-4366-a2d5-7df0255de755', 1, '登录系统', NULL, NULL, '2020-12-13 14:45:30.97', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4c710a58-bc19-42f8-953a-4490977f180a', 1, '登录系统', NULL, NULL, '2020-12-13 15:09:15.84', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9248b0dc-1b9b-460d-ad63-58a3f7f91da9', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:17:04.393', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c241ef40-6af0-411e-9f1c-9566d495fe22', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:18:24.101', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('18d4f0ae-62ed-4510-bc6d-5532625d6b5c', 3, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:18:57.956', '/archive/attachment/uploadAttachment', '上传文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4367c0c7-d819-44fd-9f1e-1085181b4b69', 5, '文件管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:19:33.204', '/archive/attachment/delAttachment', '删除文件', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ac9c6b8f-73b1-4a92-a227-aacfcc73b1cf', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:19:44.094', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0e4ee66c-e474-4186-b268-b19f5b034b29', 1, '登录系统', NULL, NULL, '2020-12-13 15:19:51.041', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('48639dcf-47b0-40c2-9e7f-5cc8d4b11760', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:20:35.926', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4af8f04f-abd7-4e12-98bf-8f50d8645152', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:20:40.236', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5089c9ef-b7fb-4f33-aca2-0ba1c1ecd971', 1, '登录系统', NULL, NULL, '2020-12-13 15:20:46.151', '/login', '张三 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6d1f6f83-8cd5-4bfb-8b02-6b650aff55da', -1, '退出系统', '14', '张三', '2020-12-13 15:21:56.852', '/logout', '张三 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6ff5b761-e02b-425f-a2a4-7846164494eb', 1, '登录系统', NULL, NULL, '2020-12-13 15:22:01.209', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('df400e26-ec9d-4554-b900-a56ed23d51f7', 4, '用户管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-13 15:22:11.343', '/user/uploadUserImg', '上传用户图像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b0b5cd3e-0492-470b-a8ec-34c53a352d35', -1, '退出系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-13 15:23:09.298', '/logout', '屈长忠 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('937e0849-5c33-40ea-9122-0a8c46c6f14f', 1, '登录系统', NULL, NULL, '2020-12-13 15:23:13.39', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ac5baf5a-127a-421c-9b8d-1170b4a656e4', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:23:54.804', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('51584220-cb0b-4b6b-8116-fadd0c48e52f', 1, '登录系统', NULL, NULL, '2020-12-13 15:23:58.427', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5d8b8678-c3aa-4f39-9f93-a57a605f20eb', -1, '退出系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-13 15:24:47.59', '/logout', '屈长忠 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2142fe51-0060-4799-8d14-33e9bfe217a3', 1, '登录系统', NULL, NULL, '2020-12-13 15:24:49.001', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cc072bdc-c444-477e-b0b8-c9d37d6dcd9b', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:24:57.572', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d350235c-eb97-490d-a7fe-a85496ffd438', 1, '登录系统', NULL, NULL, '2020-12-13 15:24:58.526', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('122ae94a-92f4-407d-9af8-ca9aa39569ca', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:25:11.539', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8262a989-f7ef-4a33-94b2-aac9b62787ae', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:26:01.609', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e376541c-a1c0-4351-bedb-fd6deb904976', 1, '登录系统', NULL, NULL, '2020-12-13 15:26:02.878', '/login', 'admin 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b9b14faf-df77-405d-a9a0-0305c34fd12c', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', 'admin', '2020-12-13 15:27:12.638', '/logout', 'admin 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0d6ffb8c-c0b6-4db8-96ea-7c5a2fd77903', 1, '登录系统', NULL, NULL, '2020-12-13 15:27:13.881', '/login', 'admin 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bc3fd16d-1ad3-46f4-9b47-693a167cfdea', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', 'admin', '2020-12-13 15:28:07.967', '/logout', 'admin 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f24745d3-e7c6-43b4-a14f-01131f8e2222', 1, '登录系统', NULL, NULL, '2020-12-13 15:28:08.892', '/login', 'admin 尝试登录系统，但失败了', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e5e8ede0-dea4-4c32-a592-50e0b1e898fc', 1, '登录系统', NULL, NULL, '2020-12-13 15:28:10.597', '/login', 'admin 尝试登录系统，但失败了', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('962b8f3b-8d15-43e5-9e24-7af024d4a986', 1, '登录系统', NULL, NULL, '2020-12-13 15:28:13.573', '/login', 'admin 尝试登录系统，但失败了', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7b4eec02-82b3-4861-a17f-538cc108729c', 1, '登录系统', NULL, NULL, '2020-12-13 15:28:25.853', '/login', ' 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('57e65a3f-48c2-4b59-af62-33f9a96771af', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '', '2020-12-13 15:28:46.541', '/user/saveCurrentUserInfo', '修改当前用户基本资料', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('896c72c4-1a8c-4bfa-88df-5866e1c8a698', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:29:02.339', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('21391cee-950e-4038-ac28-0771a32c4407', 1, '登录系统', NULL, NULL, '2020-12-13 15:52:12.92', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e99416e3-483b-41a7-8a85-6893c0c1897b', 1, '登录系统', NULL, NULL, '2020-12-13 15:53:41.163', '/login', 'sfd 尝试登录系统，但失败了', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('986c8209-42a6-4879-9c3a-302372a5d7fe', 1, '登录系统', NULL, NULL, '2020-12-13 15:53:44.708', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e1f11754-adac-476b-aa31-167a326a045f', -1, '退出系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-13 15:59:09.012', '/logout', '屈长忠 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ebb061ad-797f-432b-b90a-8ce5995ef85a', 1, '登录系统', NULL, NULL, '2020-12-13 15:59:12.97', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e9aff720-3e91-409b-b581-d99ff9ce715f', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:59:28.903', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6182a2e1-bf25-412a-9b86-e6d20cd8c026', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 15:59:30.357', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('aa905bcc-6852-4a33-8160-3e302bf1068c', 1, '登录系统', NULL, NULL, '2020-12-13 15:59:32.991', '/login', 'test 尝试登录系统，但失败了', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a0889f25-cb1e-4b3f-80d9-24eb60c5fdae', 1, '登录系统', NULL, NULL, '2020-12-13 15:59:36.468', '/login', '测试 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0e8076b1-6382-40aa-8745-c00af511c53d', -1, '退出系统', '2185d74c-df5c-4125-9d3b-9fd048118f73', '测试', '2020-12-13 15:59:59.098', '/logout', '测试 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('23147f48-6a7a-43d1-b64d-95004164579e', 1, '登录系统', NULL, NULL, '2020-12-13 16:00:00.182', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('60bf83f4-89fd-4332-bffa-ed56ee6e4a27', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 16:00:08.634', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a1123263-bbdb-45f7-92f3-3b42879c5d8f', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 16:00:10.777', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('25faa8f1-f969-4f1a-90b1-ccbe46771a72', 1, '登录系统', NULL, NULL, '2020-12-13 16:00:15.365', '/login', '测试 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('868f0e8f-2d7d-466a-9340-92c00857ff01', 1, '登录系统', NULL, NULL, '2020-12-13 16:22:01.588', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a9f851e0-bba6-4e30-8864-ca8c39b5064a', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 16:22:08.046', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('051d3f25-93d9-40ac-a948-2a9a4938a42d', 1, '登录系统', NULL, NULL, '2020-12-13 16:22:14.719', '/login', '测试 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('40bdf5b8-a1bd-4416-afdc-cdc104a56e65', 1, '登录系统', NULL, NULL, '2020-12-13 17:13:35.003', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('41dbc688-024a-4216-a945-6dfae8abcf8b', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 17:13:48.845', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('47562bd0-bebe-4d28-86a7-b837aca3a15a', 1, '登录系统', NULL, NULL, '2020-12-13 17:13:53.269', '/login', '测试 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1240465f-3663-4b8b-ab8c-544b1581c660', 1, '登录系统', NULL, NULL, '2020-12-13 17:16:47.545', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3f44ecc4-b9c8-4489-9570-436dc58ff7e2', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 17:16:51.601', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('aabf945e-568f-4d67-b5ee-8be1dc427fcd', 1, '登录系统', NULL, NULL, '2020-12-13 17:16:54.388', '/login', 'test 尝试登录系统，但失败了', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('41bec114-7015-49d0-bfe0-584e3c006a6b', 1, '登录系统', NULL, NULL, '2020-12-13 17:16:59.217', '/login', '测试 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('44fe9b32-c0f6-4059-9e3c-28c7185bf8e3', 1, '登录系统', NULL, NULL, '2020-12-13 18:25:43.68', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ea56e933-7571-4263-89d9-1b4f1b572fbc', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 18:26:30.638', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('172fe8a8-81a4-437e-9016-8049c1e4c3e3', 1, '登录系统', NULL, NULL, '2020-12-13 18:26:36.109', '/login', '测试 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('17472e8d-7d74-476f-adff-608842a5862a', 1, '登录系统', NULL, NULL, '2020-12-13 23:51:13.832', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('aebfca4d-e3b0-4b43-8c9e-a28d41c270e8', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 23:51:32.259', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('de86e4b6-11e2-42ca-82d3-b2abe7158e36', 1, '登录系统', NULL, NULL, '2020-12-13 23:51:38.381', '/login', '测试 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('15ff0010-f3e8-44c0-a9b3-b056e6e9f2a9', -1, '退出系统', '2185d74c-df5c-4125-9d3b-9fd048118f73', '测试', '2020-12-13 23:55:39.956', '/logout', '测试 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5c439f8c-a0c4-4b3f-a981-0bab687959b8', 1, '登录系统', NULL, NULL, '2020-12-13 23:55:41.754', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1f2e7c94-dbde-4533-84d1-c1e5560b9fb1', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-13 23:55:46.965', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4b520ade-8040-4fb9-b26d-d9271ac459ac', 1, '登录系统', NULL, NULL, '2020-12-13 23:55:50.462', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d21432c4-a42b-4a45-825a-3ea5e25f9d3d', 4, '角色管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-13 23:56:12.515', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('626b9ed5-2089-4bac-8249-b4096c731b8e', 3, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-13 23:56:56.449', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('dfb43e15-7a45-4eee-b3a0-ae2f5291c00d', 3, '菜单管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-13 23:57:25.336', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1076e864-e074-487e-966a-a34a95ee57b0', 4, '角色管理', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2020-12-13 23:57:40.045', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a44745e1-cee1-4fbb-bab3-2324189db7f9', 1, '登录系统', NULL, NULL, '2020-12-15 22:17:17.313', '/login', '系统管理员 进入了系统', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('98d86379-ba08-428d-9696-211a1badba41', 1, '登录系统', NULL, NULL, '2020-12-15 22:23:09.332', '/login', '系统管理员 进入了系统', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('663ce8fa-489a-473d-8aad-3c4b1c899ca8', 1, '登录系统', NULL, NULL, '2020-12-15 22:24:40.787', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b68b97b8-9bf2-4001-b0a7-45e3bcda2c14', 1, '登录系统', NULL, NULL, '2020-12-15 23:00:15.201', '/login', '系统管理员 进入了系统', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6ca243c7-1ea9-4f35-aeec-94490d4d436c', 1, '登录系统', NULL, NULL, '2020-12-15 23:10:27.737', '/login', '系统管理员 进入了系统', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('85ef7aff-8c92-43d7-995f-e765f20c2d42', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-15 23:10:56.637', '/user/uploadUserImg', '更换用户头像', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('609394e9-9cee-471b-aab0-c1c4193fd57f', 1, '登录系统', NULL, NULL, '2020-12-15 23:21:19.933', '/login', '系统管理员 进入了系统', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6f78050c-c801-4843-bbcd-3b2268b36442', 4, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-15 23:23:49.754', '/menu/updatePermission', '修改一个菜单或权限', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d666dc50-289b-4975-b916-a2c29983d054', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-15 23:27:59.74', '/user/updateUser', '修改用户', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d601840d-ffd7-4ecc-8e4c-3766dac2b8ea', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-15 23:28:25.03', '/logout', '系统管理员 退出了系统', '119.123.134.242', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('80254050-fa9a-454f-a2de-0bace24f297f', 1, '登录系统', NULL, NULL, '2020-12-18 13:01:08.894', '/login', '系统管理员 进入了系统', '183.15.176.40', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('04c97e48-9807-4255-a2c3-aa790993d4d1', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-18 13:01:28.99', '/logout', '系统管理员 退出了系统', '183.15.176.40', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4a8eb24a-bbe0-4b49-adcb-ef85d6371295', 1, '登录系统', NULL, NULL, '2020-12-18 14:12:45.842', '/login', '系统管理员 进入了系统', '183.15.176.40', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('96fcaaab-e410-4990-b8f9-07a60e60a9bf', 1, '登录系统', NULL, NULL, '2020-12-19 11:15:31.568', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('427cadf9-3163-4489-b48a-5a5f0104c003', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-19 11:53:40.622', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('75034da1-e99e-4fe6-95e2-62563475d66e', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-19 11:55:16.84', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7c8364d8-17dc-419e-a083-62cff5185528', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-19 12:01:28.692', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ee29a233-baf7-45ed-b13a-89d5f265c527', 1, '登录系统', NULL, NULL, '2020-12-19 14:37:49.875', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5c35b7a0-478b-4b81-8e49-62492436d82c', 1, '登录系统', NULL, NULL, '2021-01-03 22:30:29.115', '/login', '系统管理员 进入了系统', '119.123.73.112', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0f545445-769c-4230-afe3-b282a038b721', 4, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-19 14:38:12.554', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fd7856e6-f7d6-44ce-8c21-09979ee4a38e', 1, '登录系统', NULL, NULL, '2020-12-19 14:54:56.965', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5721a278-13b8-4bea-bf68-3601042cd59b', 1, '登录系统', NULL, NULL, '2020-12-19 15:06:37.072', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('dde8e874-671b-4bc0-b5f9-9c840bc4cd5a', 1, '登录系统', NULL, NULL, '2020-12-19 16:00:52.255', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e48af494-f8be-42f6-acd0-e4d979f4c439', 1, '登录系统', NULL, NULL, '2020-12-19 16:39:32.058', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6a2dfc45-e09a-4cb7-9d69-a0e237c0b8fc', 1, '登录系统', NULL, NULL, '2020-12-19 23:25:54.779', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f63303f6-603e-4840-8b99-d3902f6998ad', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-19 23:26:41.935', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9781a41c-8b97-4ffe-b14b-cab5dbad7373', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-19 23:27:53.389', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4bba4734-1314-43c2-9912-223bd39bebd2', 1, '登录系统', NULL, NULL, '2020-12-19 23:27:54.531', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('778c49a4-c1e9-41e3-90ff-293f7717d452', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-19 23:29:52.073', '/operation/dict/addDict', '新增字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('05a27b1f-0a20-48f3-a072-c4a780dd6f73', 1, '登录系统', NULL, NULL, '2020-12-20 10:49:11.879', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4c5b637c-6e39-48e4-bba8-4a18a0880556', 5, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 10:49:19.337', '/operation/dict/deleteDict', '删除字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f4c1c5e7-4c36-4315-9c7d-1056f1337dea', 5, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 10:50:02.573', '/operation/dict/deleteDict', '删除字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('09c24e78-2d10-4c51-aaf1-fd80ac5c1682', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 10:50:13.621', '/operation/dict/addDict', '新增字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bcbc8812-4951-4d55-887c-94fc0369c8b6', 1, '登录系统', NULL, NULL, '2020-12-20 11:44:09.197', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d097525f-99d8-4153-8e07-ab4c95f158ff', 1, '登录系统', NULL, NULL, '2020-12-20 11:50:29.44', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('23cde1cd-c7d3-4cc4-99ed-db6c83458d9a', 1, '登录系统', NULL, NULL, '2020-12-20 12:06:55.902', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9e6df100-629e-4391-9044-65f8f3efbd98', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:08:02.109', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4231d7df-2537-48fb-9380-b63031d2c728', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:08:04.294', '/operation/dict-attr/getDictAttrList/97e67908-5489-41f1-ae3a-dbc6b4752907', NULL, '127.0.0.1', 0, 'org.springframework.jdbc.BadSqlGrammarException:
### Error querying database.  Cause: org.postgresql.util.PSQLException: ERROR: column "sort" does not exist
  位置：89
### The error may exist in com/qcz/qmplatform/module/operation/mapper/DictAttrMapper.java (best guess)
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: SELECT attr_value, attr_name, dict_id FROM sys_dict_attr WHERE dict_id LIKE ? order by sort asc LIMIT ?
### Cause: org.postgresql.util.PSQLException: ERROR: column "sort" does not exist
  位置：89
; bad SQL grammar []; nested exception is org.postgresql.util.PSQLException: ERROR: column "sort" does not exist
  位置：89
	org.springframework.jdbc.support.SQLStateSQLExceptionTranslator.doTranslate(SQLStateSQLExceptionTranslator.java:101)
org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)
org.springframework.jdbc.support.AbstractFallbackSQLExcepti');
INSERT INTO "public"."sys_operate_log" VALUES ('256865f1-98a0-4283-8321-771c65028a13', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:08:55.744', '/operation/dict-attr/getDictAttrOne/97e67908-5489-41f1-ae3a-dbc6b4752907/1', NULL, '127.0.0.1', 0, 'org.springframework.jdbc.BadSqlGrammarException:
### Error querying database.  Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name and argument types. You might need to add explicit type casts.
  位置：72
### The error may exist in com/qcz/qmplatform/module/operation/mapper/DictAttrMapper.java (best guess)
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: SELECT attr_value,attr_name,dict_id FROM sys_dict_attr WHERE attr_value=?
### Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name and argument types. You might need to add explicit type casts.
  位置：72
; bad SQL grammar []; nested exception is org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name and argument types. You ');
INSERT INTO "public"."sys_operate_log" VALUES ('6a83a76e-b1d8-4d7a-b78a-0df2e0c86e05', 1, '登录系统', NULL, NULL, '2020-12-20 12:15:12.82', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8b548f96-fdb6-4646-b6f4-db0ebeb5020f', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:15:20.434', '/operation/dict-attr/getDictAttrOne/97e67908-5489-41f1-ae3a-dbc6b4752907/1', NULL, '127.0.0.1', 0, 'org.springframework.jdbc.BadSqlGrammarException:
### Error querying database.  Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name and argument types. You might need to add explicit type casts.
  位置：96
### The error may exist in com/qcz/qmplatform/module/operation/mapper/DictAttrMapper.java (best guess)
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: SELECT  attr_value,attr_name,dict_id  FROM sys_dict_attr     WHERE dict_id = ? AND attr_value = ?
### Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name and argument types. You might need to add explicit type casts.
  位置：96
; bad SQL grammar []; nested exception is org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name ');
INSERT INTO "public"."sys_operate_log" VALUES ('a9aa9d05-1a27-4f4d-8588-26692d0a92fd', 1, '登录系统', NULL, NULL, '2020-12-20 12:16:55.606', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6d96db79-b182-4c3d-a6d8-6ce692ec62f2', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:17:47.17', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f9c55c48-2d47-441a-a1cb-b0f83c024661', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:18:20.242', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('27e4ab30-d6a4-4ec1-94db-0f9151c696fd', 1, '登录系统', NULL, NULL, '2020-12-20 12:24:44.407', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e88bd371-99bb-4661-ba2c-26899efc005a', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:10:25.928', '/operation/dict/addDict', '新增字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('33334085-af28-4ee7-99c2-58d88859f6e5', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:25:30.427', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 0, 'java.lang.Exception:org.springframework.jdbc.BadSqlGrammarException: 
### Error updating database.  Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name and argument types. You might need to add explicit type casts.
  位置：65
### The error may exist in com/qcz/qmplatform/module/operation/mapper/DictAttrMapper.java (best guess)
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: DELETE FROM sys_dict_attr     WHERE dict_id = ? AND attr_value IN (?)
### Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name and argument types. You might need to add explicit type casts.
  位置：65
; bad SQL grammar []; nested exception is org.postgresql.util.PSQLException: ERROR: operator does not exist: smallint = character varying
  建议：No operator matches the given name and arg');
INSERT INTO "public"."sys_operate_log" VALUES ('dcf75af0-a029-4812-9da8-134da86b12e7', 1, '登录系统', NULL, NULL, '2020-12-20 12:27:28.723', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('29278ed4-a0bd-4420-8d22-1e516f83df9b', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:27:34.697', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6fd7102b-7082-474f-b2a9-daf6b355bc83', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:28:58.906', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('99a00405-ba48-4692-b958-c2a10fafe8ed', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:30:20.116', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f6a40de2-5ff4-4ecf-8ef1-955c0e292d27', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:35:24.395', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('63fea880-3eba-441a-89dc-5ea00a067a3c', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:35:44.381', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bd21b624-496d-44e0-8e88-e41aca9921d9', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:36:40.386', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('198f601e-a648-42a1-bffa-2fefef1d7439', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:38:31.334', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3c915a44-8a3e-41a6-9e0d-34ff2f7ca6a9', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:38:37.18', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1e4e4d1f-3e33-418e-a046-0ee993502479', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:40:15.228', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3169f981-2f5d-4ab9-8a95-d82d3ec20775', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:40:20.532', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('61583795-40e9-4d59-9545-d3586fdb0e5c', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:40:47.985', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6f3358cd-78ac-4d08-8a11-dc690339a18a', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:40:57.803', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('aa0b3bc4-ff67-4f01-aca2-1b4d91f6c42a', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:41:36.374', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8779eb73-4412-4bd1-8b70-26dfe7685d77', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:41:40.802', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e5734166-649d-403c-8391-0647e0c31ff0', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:41:53.91', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a2136b04-1e96-4d24-a367-8f04ba815ea7', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:42:04.755', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b0cf3a71-af0d-4c1c-863a-80d131a790f5', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:44:31.393', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('993c7ca1-506a-472f-9713-a1d4f5c79523', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:44:37.778', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('af98e9a9-c1d0-4360-9341-95ce942c3aec', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:58:08.155', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e34967eb-7175-41a9-8abc-108c7adce15a', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:58:13.027', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('72400b92-6062-40de-9ae4-1e1308bdf856', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 12:59:57.325', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fed736ba-ee91-4cf6-b84d-3fcf11025595', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:00:02.188', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('723c9047-a4fd-4533-8c83-aa00afc9376a', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:00:53.398', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6af5dbd8-7396-4114-b12e-04947ca3c580', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:00:57.573', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('32253e50-f4b9-45ce-bb6a-8c3ad925a4b7', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:01:26.558', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('52f32a96-dd15-42c7-bce3-cb7c1ede6953', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:01:31.11', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('dde5d486-3c94-4432-8f74-70806593d25a', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:07:20.958', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2dfc04d4-115b-4e66-ab19-90c75d26a15a', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:07:26.004', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5185ef93-ffdc-4c1d-a478-8bcef447d5f2', 1, '登录系统', NULL, NULL, '2020-12-20 13:28:20.537', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('773cd3e4-8521-4b50-8968-275ff1ed28d4', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:09:12.792', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b73a542f-49e5-4200-93c5-44db322ae158', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:09:42.478', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2ab78444-228f-4ba2-96e2-424ae4e6337b', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:09:48.541', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a2f07fbb-eeb0-4d93-8a68-20d062223c5e', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:28:55.081', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 0, 'java.lang.Exception:org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.reflection.ReflectionException: Could not set property ''attrId'' of ''class com.qcz.qmplatform.module.operation.domain.DictAttr'' with value ''1340529591370461185'' Cause: java.lang.IllegalArgumentException: argument type mismatch
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:77)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice');
INSERT INTO "public"."sys_operate_log" VALUES ('f2f1093a-bc8d-4c54-9228-1f9774ddbc45', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:30:15.848', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 0, 'java.lang.Exception:org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.reflection.ReflectionException: Could not set property ''attrId'' of ''class com.qcz.qmplatform.module.operation.domain.DictAttr'' with value ''1340529930140200961'' Cause: java.lang.IllegalArgumentException: argument type mismatch
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:77)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice');
INSERT INTO "public"."sys_operate_log" VALUES ('e3a2ff8b-6db8-4bd3-add7-a5fcd8a9e4cc', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:30:59.57', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cdc505f7-468a-41a0-b0ca-abeda16cbc74', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:31:14.903', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2ce0bdd0-a7d7-44a9-8693-b33b922dac11', 1, '登录系统', NULL, NULL, '2020-12-20 13:32:45.223', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('23c3de40-5335-4c12-96d4-3499798c7b64', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:33:10.896', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('476a1d68-b78f-4f69-8cf4-4663d62b6fe6', 1, '登录系统', NULL, NULL, '2020-12-20 13:42:17.721', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bdcc893a-6689-42e2-9c87-5420e6459119', 3, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:42:36.546', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('86927309-43e5-4d93-9181-86329425a221', 5, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:42:43.406', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9f5a7ede-b157-48ad-bb75-6a3ffe634879', 4, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:42:54.765', '/operation/dict-attr/updateDictAttr', '修改字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b4cb0cda-5a67-449f-a1c3-2bcc7646af0b', 4, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:44:30.988', '/operation/dict-attr/updateDictAttr', '修改字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9163e63d-7b0f-450d-82eb-657b48279c82', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:45:10.341', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b7858227-fb31-4cca-bfcf-186612ce756c', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:47:16.674', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('63248413-1241-4842-b138-3cdf0a1a8cd7', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:47:44.235', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('942e78fd-5db4-4abd-8a41-d1521b1883f3', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:48:23.714', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c4cf2425-503a-4d5f-ac0e-daed069117da', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:48:52.095', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bd08410e-c841-4c8c-bc8c-c3102e9f8dc5', 1, '登录系统', NULL, NULL, '2020-12-20 13:53:48.016', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3db62519-3355-4986-ab11-6b216bd6cc52', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:54:28.496', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('496ca33a-2453-478c-890a-ea581e0b36e0', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:56:47.604', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('88935547-68af-454e-aba8-a346a54cc7cf', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 13:58:26.792', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e9cde79c-1146-4ade-bca2-ce1ef420f5e9', 4, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:04:08.397', '/operation/dict/updateDict', '修改字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5a66fddb-a717-4a9e-9bb1-bd0d11fd9844', 4, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:05:32.202', '/operation/dict/updateDict', '修改字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1711ef7d-64e6-4539-83e6-a1f39e4f83eb', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:06:04.727', '/operation/dict/addDict', '新增字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('37119430-8c96-4f7c-a4c6-2ebb7154f32b', 4, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:06:08.774', '/operation/dict/updateDict', '修改字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5da75837-d1d6-4b12-bc2d-f6da2e8fa386', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:07:05.567', '/operation/dict/addDict', '新增字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b14403a8-826c-453c-b6f5-99c073c4dcb4', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:07:50.046', '/operation/dict/addDict', '新增字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('942dac70-08f4-4f89-94bf-ef952659de9b', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:08:13.997', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('83882904-eb4d-4dea-8196-bb8259ee6789', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:08:19.372', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4889ae7a-5a25-43ca-bc06-f43360c79dba', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:09:05.326', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d2577a14-cade-4df1-be68-ab1142af6235', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:11:01.822', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('13e8c6b5-c7c4-48ab-af9f-06b9e6c86d8a', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:11:08.999', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('33a98180-6ad1-4ec0-ab1c-a12b47a25941', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:11:17.017', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5bef2635-9d32-4fec-b16d-0327b0efc328', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:11:27.418', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fffc2b72-89f4-4aeb-b0c6-685486db0acc', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:11:34.282', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3465ca97-3826-4142-90d1-304f5b95c010', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 14:11:41.462', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('507a8ebe-7189-48f5-afbd-1c637a6f2c4b', 1, '登录系统', NULL, NULL, '2020-12-20 15:30:34.347', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ef03cf5c-1b5d-4109-b7f8-2675a865b429', 1, '登录系统', NULL, NULL, '2020-12-20 15:58:55.855', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bac76f87-2138-4372-b319-a47b37f2755c', 1, '登录系统', NULL, NULL, '2020-12-20 16:09:46.922', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4dd250c7-e519-46db-8b6c-d5c8bc4ca81b', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 16:28:17.504', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bfd8b3d4-a1ce-464f-8fc4-c63d0a6e34e4', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 16:28:23.59', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('00f0a8bd-e2df-4d88-bf7d-eaa514ea0272', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 16:29:48.142', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c09a9908-175c-41da-a6ee-081b66bc4aac', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 16:30:01.191', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9156ab0b-d593-480c-ae54-f52a86a37c87', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 16:31:19.569', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('75154233-d99c-4f1a-a8fd-f33161e6bb00', 1, '登录系统', NULL, NULL, '2020-12-20 16:58:47.02', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7d715702-1452-4a16-8d42-50f46e915599', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 16:58:49.512', '/operate-log/getLogList', NULL, '127.0.0.1', 0, 'org.springframework.jdbc.BadSqlGrammarException:
### Error querying database.  Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist: timestamp without time zone >= character varying
  建议：No operator matches the given name and argument types. You might need to add explicit type casts.
  位置：385
### The error may exist in file [D:\my-workspace\qmplatform\target\classes\mapper\system\OperateLogMapper.xml]
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: SELECT count(0) FROM sys_operate_log sol LEFT JOIN (SELECT attr_value, attr_name FROM v_sys_dict_attr WHERE dict_code = ''operate-type'') va1 ON va1.attr_value = CAST(sol.operate_type AS varchar) LEFT JOIN (SELECT attr_value, attr_name FROM v_sys_dict_attr WHERE dict_code = ''operate-status'') va2 ON va2.attr_value = CAST(sol.operate_status AS varchar) WHERE 1 = 1 AND sol.operate_time >= ?
### Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist');
INSERT INTO "public"."sys_operate_log" VALUES ('dd3eb04d-9139-4926-9eab-3597b7117ff0', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 16:58:59.443', '/operate-log/getLogList', NULL, '127.0.0.1', 0, 'org.springframework.jdbc.BadSqlGrammarException:
### Error querying database.  Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist: timestamp without time zone >= character varying
  建议：No operator matches the given name and argument types. You might need to add explicit type casts.
  位置：385
### The error may exist in file [D:\my-workspace\qmplatform\target\classes\mapper\system\OperateLogMapper.xml]
### The error may involve defaultParameterMap
### The error occurred while setting parameters
### SQL: SELECT count(0) FROM sys_operate_log sol LEFT JOIN (SELECT attr_value, attr_name FROM v_sys_dict_attr WHERE dict_code = ''operate-type'') va1 ON va1.attr_value = CAST(sol.operate_type AS varchar) LEFT JOIN (SELECT attr_value, attr_name FROM v_sys_dict_attr WHERE dict_code = ''operate-status'') va2 ON va2.attr_value = CAST(sol.operate_status AS varchar) WHERE 1 = 1 AND sol.operate_time >= ?
### Cause: org.postgresql.util.PSQLException: ERROR: operator does not exist');
INSERT INTO "public"."sys_operate_log" VALUES ('5f0bc6d9-13ee-460f-89d4-f51fb883773d', 1, '登录系统', NULL, NULL, '2020-12-20 17:01:21.86', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a4d828de-c1f5-4800-ad23-0be5e2346d8f', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 17:03:57.86', '/operation/dict/addDict', '新增字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('021f4732-c5ea-4ccd-9760-1920dc197855', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 17:04:08.121', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d0debcb7-27ef-40f9-9b92-c715a767918d', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 17:04:14.002', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('74531b99-0a56-495f-9228-c1b18784425e', 1, '登录系统', NULL, NULL, '2020-12-20 17:44:21.325', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1936165f-09a8-4de3-9744-27d4d6ed0d20', 4, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 18:11:25.448', '/operation/dict/updateDict', '修改字典', '127.0.0.1', 0, 'java.lang.Exception:org.springframework.dao.DuplicateKeyException: 
### Error updating database.  Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "uq_dict_code"
  详细：Key (dict_code)=(user-status) already exists.
### The error may exist in com/qcz/qmplatform/module/operation/mapper/DictMapper.java (best guess)
### The error may involve com.qcz.qmplatform.module.operation.mapper.DictMapper.updateById-Inline
### The error occurred while setting parameters
### SQL: UPDATE sys_dict  SET dict_code=?, dict_name=?, remark=?, sort=?  WHERE dict_id=?
### Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "uq_dict_code"
  详细：Key (dict_code)=(user-status) already exists.
; ERROR: duplicate key value violates unique constraint "uq_dict_code"
  详细：Key (dict_code)=(user-status) already exists.; nested exception is org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint ');
INSERT INTO "public"."sys_operate_log" VALUES ('cbb36f20-eac0-461f-937f-f4b6d19f5092', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 23:28:42.741', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ce07cbb8-a83e-4f1d-8383-a44130249fb7', 1, '登录系统', NULL, NULL, '2020-12-27 00:04:27.007', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0c3ee308-6478-482d-bb0c-8d789061ca26', 4, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 18:14:38.191', '/operation/dict/updateDict', '修改字典', '127.0.0.1', 0, 'java.lang.Exception:org.springframework.dao.DuplicateKeyException: 
### Error updating database.  Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "uq_dict_code"
  详细：Key (dict_code)=(user-status) already exists.
### The error may exist in com/qcz/qmplatform/module/operation/mapper/DictMapper.java (best guess)
### The error may involve com.qcz.qmplatform.module.operation.mapper.DictMapper.updateById-Inline
### The error occurred while setting parameters
### SQL: UPDATE sys_dict  SET dict_code=?, dict_name=?, remark=?, sort=?  WHERE dict_id=?
### Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint "uq_dict_code"
  详细：Key (dict_code)=(user-status) already exists.
; ERROR: duplicate key value violates unique constraint "uq_dict_code"
  详细：Key (dict_code)=(user-status) already exists.; nested exception is org.postgresql.util.PSQLException: ERROR: duplicate key value violates unique constraint ');
INSERT INTO "public"."sys_operate_log" VALUES ('926a8a88-8aba-47e4-8137-4ef0f769fd82', 1, '登录系统', NULL, NULL, '2020-12-20 18:14:57.458', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('291a5da8-e93f-4767-a19e-160843f49628', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 18:16:21.503', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0fecb776-a59c-4325-b7e0-840173d3898c', 5, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 18:16:39.095', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('65ace7ab-8253-453a-a224-93def4f65de2', 1, '登录系统', NULL, NULL, '2020-12-20 18:45:14.872', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ea07f813-99f9-48d0-a7fe-ab0fde330dab', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-20 18:45:30.436', '/user/uploadUserImg', '更换用户头像', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a9e8cd46-aea9-4947-8d28-3243bba1257f', 1, '登录系统', NULL, NULL, '2020-12-21 09:04:38.339', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('07e269f5-5e7c-4f9b-9cb4-a4e476d0fb55', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-21 09:09:14.595', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('790e0537-3d91-4c73-914b-2405bdf300fc', 1, '登录系统', NULL, NULL, '2020-12-21 09:09:15.723', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('26c143f6-c04f-4e0d-ac9e-a76129dd08be', 1, '登录系统', NULL, NULL, '2020-12-22 11:43:22.405', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6c05d6c1-59c4-40df-9bbd-686a1d4a144b', 1, '登录系统', NULL, NULL, '2020-12-23 22:12:34.466', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bc952827-1fa8-4bc7-b3c5-d6f97f29947a', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-23 22:14:36.613', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3fd42ac7-d8ca-4c49-929d-86b1b9b2bc72', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-23 22:16:04.622', '/user/saveCurrentUserInfo', '修改当前用户基本资料', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('379d2682-1f43-4aff-9b22-65f24f33f82a', 1, '登录系统', NULL, NULL, '2020-12-26 12:56:23.524', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d1c28dea-a9a3-480e-a70d-fd422f94d11d', 1, '登录系统', NULL, NULL, '2020-12-26 13:29:08.285', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('77aa8812-aab1-453f-b3a9-fae522ab55fc', 1, '登录系统', NULL, NULL, '2020-12-26 14:21:11.539', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2add38d1-e997-4c43-8c3c-cf718b2bd6ee', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 14:25:58.753', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('58d97be7-eb1b-4470-8300-54cdb52daeb7', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 14:26:06.793', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a98bbba9-d094-4839-a46e-e9e389d72c82', 1, '登录系统', NULL, NULL, '2020-12-26 15:40:31.755', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('66b2958e-b8e4-4dd5-aa16-2d5340e8952a', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 15:42:29.284', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ee6471eb-355b-4b05-8580-dcd755560baf', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 15:44:40.585', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('01f4b471-c59d-4dcd-9e7f-4c953904fe1e', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 15:45:18.599', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7d46e6fb-d492-43b6-8f01-30da1b8e36c3', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 15:45:49.053', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('757e29c9-c661-46c1-acbf-9ece8691d6f0', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 15:48:02.286', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7d2532f9-c098-4f39-9efa-3aa9630e7d7f', 4, '角色管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 15:50:08.84', '/role/saveRolePermission', '分配角色权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1881851a-7c6b-4ffb-83b6-7aa2e1f4e64d', 5, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 15:59:16.518', '/operation/dict-attr/deleteDictAttr', '删除字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('091ca4bb-0348-453e-8235-2768760f0b2e', 1, '登录系统', NULL, NULL, '2020-12-26 19:49:03.505', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('27a86050-d1b5-4aed-9eff-13108e8e5a9a', 1, '登录系统', NULL, NULL, '2020-12-26 22:23:17.085', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('80d8f405-af7d-4562-9e20-fee91ff130ec', 1, '登录系统', NULL, NULL, '2020-12-26 23:18:28.514', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0441e106-9882-42e0-accb-d4810fd94d78', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 23:18:31.711', '/operation/data-bak/getDataBakStrategy', NULL, '127.0.0.1', 0, 'java.lang.NumberFormatException:null
	java.lang.Integer.parseInt(Integer.java:542)
java.lang.Integer.parseInt(Integer.java:615)
com.qcz.qmplatform.module.operation.controller.DataBakController.getDataBakStrategy(DataBakController.java:78)
com.qcz.qmplatform.module.operation.controller.DataBakController$$FastClassBySpringCGLIB$$aa947ae9.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)
org.springframework.aop.framework.Re');
INSERT INTO "public"."sys_operate_log" VALUES ('0551ee8a-5156-405d-9325-c9ac110dbd93', 1, '登录系统', NULL, NULL, '2020-12-26 23:24:48.627', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3678c3df-324a-444c-b17e-fe10278e14e3', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-26 23:24:57.789', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('10bfa903-ec5f-4ab7-b8d4-8058abbb1309', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 00:04:47.623', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b2908cb4-3f43-4f29-a5b6-86418c8b2770', 1, '登录系统', NULL, NULL, '2020-12-27 12:41:12.199', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f7f36c7f-e6f4-4626-9b40-03be8eea4307', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 12:41:34.096', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c22cc091-9508-4f36-aec0-40d340575fee', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 12:44:00.329', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('02efb893-89cc-40c4-bac9-6d8b1b49cf79', 1, '登录系统', NULL, NULL, '2020-12-27 12:45:13.683', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bb6b2091-f00d-4d80-a6ea-f76c72ed7210', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 12:45:20.366', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e2802205-b47e-4c7b-b565-12ac2a42f540', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 12:45:44.217', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0ed90fe1-e90d-499b-90d9-da84104de374', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 12:45:47.905', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8db4dc89-0740-4bf9-b11d-44651998070f', 1, '登录系统', NULL, NULL, '2020-12-27 12:51:24.759', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('991d0f74-5965-424d-a137-6dd7573e57d0', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 12:51:30.115', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6bc901bc-3407-43c3-9e27-039b87432e35', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 12:51:52.07', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9cdfba59-9aa3-4ae3-b432-036d681d73e4', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 12:52:01.145', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2038acaa-a32a-4e8c-aed3-c5c0905930c6', 5, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-27 13:12:22.193', '/user/delUser', '删除用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9cb54bf7-0520-48be-a5aa-1ed5ddd0d9fb', 1, '登录系统', NULL, NULL, '2020-12-27 15:41:40.206', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('59d7b493-6325-4141-af46-b886b683e2b7', 1, '登录系统', NULL, NULL, '2020-12-31 08:49:36.535', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('17fd1d08-0002-441a-8f35-20ddde00c097', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:33:26.503', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1f3ecead-7cd5-4a62-9d55-072c649e3797', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 10:34:22.479', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f61822f6-0ff4-429e-b7c6-2c10cf7606c7', 1, '登录系统', NULL, NULL, '2020-12-31 11:02:23.407', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('227dd171-3394-4afa-99a7-3ac6d766a075', 1, '登录系统', NULL, NULL, '2020-12-31 11:54:26.304', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a4b0188b-38b5-4e5c-a8a1-2100453bf230', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 13:53:05.962', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('83baa931-c231-446b-b9f5-4dc6c0f89990', 1, '登录系统', NULL, NULL, '2020-12-31 17:30:11.74', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f8023f2c-602e-4b2b-a472-0fbd739a621b', 1, '登录系统', NULL, NULL, '2020-12-31 19:01:46.031', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0a6ec98b-dd82-4927-b831-c06814eb6e42', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 19:06:51.511', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5ee2a0e8-ea86-41fe-9a3e-444d8d329e9b', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2020-12-31 22:00:25.773', '/server/infoPage', NULL, '27.46.46.43', 0, 'java.lang.NoClassDefFoundError:oshi/SystemInfo
	com.qcz.qmplatform.common.utils.SystemUtils.<clinit>(SystemUtils.java:27)
com.qcz.qmplatform.module.operation.controller.ServerController.infoPage(ServerController.java:25)
com.qcz.qmplatform.module.operation.controller.ServerController$$FastClassBySpringCGLIB$$9cf63c34.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)
org.springframework.aop.framework.ReflectiveM');
INSERT INTO "public"."sys_operate_log" VALUES ('98eac05e-73a8-4ef5-923d-868d04443470', 1, '登录系统', NULL, NULL, '2020-12-31 22:20:19.501', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bbb4a57d-8332-4a4f-8618-60a82e59c0d2', 1, '登录系统', NULL, NULL, '2021-01-01 13:59:28.429', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a0d5aa23-a7ee-4fd7-902e-cec308f754dd', 1, '登录系统', NULL, NULL, '2021-01-01 14:47:42.218', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('386c4c87-8575-4d7c-a18a-c8a377d9d1ca', 1, '登录系统', NULL, NULL, '2021-01-01 15:48:04.397', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0e8d0d4d-7881-4cdb-b9c5-9a11ba4e74cb', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:53:18.463', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a51e4176-9c61-40a0-a518-660077035a4d', 1, '登录系统', NULL, NULL, '2021-01-01 15:53:19.421', '/login', 'admin 尝试登录系统，但失败了', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e621f15c-6c7d-460a-be45-c2add54581f6', 1, '登录系统', NULL, NULL, '2021-01-01 15:53:23.761', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a5f965a4-d5b6-403c-8752-948190df0ef2', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:53:40.525', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b209be76-da53-4e58-992b-3d211fe8cde1', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 15:53:42.19', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('107b85b6-dc14-4b67-b2de-f228162339cb', 1, '登录系统', NULL, NULL, '2021-01-01 15:53:43.152', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b2cdb749-30ce-44a8-ba3e-d52645b1de6a', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:05:05.073', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0229195b-bfdc-4ab0-a041-280bebd44176', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 16:06:37.367', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b4ff76ef-396e-4b53-813e-40f1f1a0a3ed', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 17:46:29.221', '/menu/addPermission', '新增一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a6021a63-04fb-47a1-b16d-9f4eb578834f', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-01 17:55:38.389', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9950df1c-efaa-4c9c-bf66-e1830eba9d2a', 1, '登录系统', NULL, NULL, '2021-01-01 17:55:39.542', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cae8ac21-9e6c-450a-8dcb-cfefcd88ae5e', 1, '登录系统', NULL, NULL, '2021-01-01 17:55:58.509', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d4d5ba9c-4bbf-42d5-b47f-5815da1c5ef3', 1, '登录系统', NULL, NULL, '2021-01-02 12:54:34.698', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4f1a3efb-56e2-40ff-9ad9-81ffbfb22670', 1, '登录系统', NULL, NULL, '2021-01-02 13:11:22.026', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6bd2e9c6-6431-47cb-abea-ea484606ba90', 4, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 13:11:46.315', '/menu/updatePermission', '修改一个菜单或权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3609d91d-977d-42bb-abec-2700bd9ab39d', 1, '登录系统', NULL, NULL, '2021-01-02 13:13:26.308', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('07f40824-d847-4f59-a97c-6c713a5e0736', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 13:19:17.042', '/operation/dict/addDict', '新增字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ef297ff2-fe2d-4a48-a7b2-92f116d03bc6', 4, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 13:19:45.793', '/operation/dict/updateDict', '修改字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f7dc16ae-ffb8-4ea3-b6f6-a1e51b99e6ac', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 13:20:07.112', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9b6027c7-1cf5-4d22-ae2e-88c27e0741ea', 1, '登录系统', NULL, NULL, '2021-01-02 14:11:52.241', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('68fe2759-0007-4bdc-9ad2-b45ae6b4f48d', 1, '登录系统', NULL, NULL, '2021-01-02 14:35:57.226', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('eac0d5ee-5f5b-4dab-b2a8-148a9a1305ba', 1, '登录系统', NULL, NULL, '2021-01-02 14:46:33.595', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('568ee82b-bdc4-4cb5-b908-5ee92010b823', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 14:47:15.202', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b272a763-4556-4d09-8926-142301bf7137', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 14:47:24.954', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('41105379-d98e-494e-9622-58dd1e0bfc83', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 14:48:31.189', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9276bf80-207b-4972-8d66-27bbe84d1516', 1, '登录系统', NULL, NULL, '2021-01-02 14:48:32.14', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('442638c0-5012-4807-987b-c843f5d604ad', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 14:48:55.908', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('60ea0fdb-f266-47f9-a875-0d3e4d1813a4', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 14:48:58.364', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a0be8f0a-1aa6-43ce-8764-4682996d4fbc', 1, '登录系统', NULL, NULL, '2021-01-02 14:48:59.369', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('053432c9-6261-4ebc-885c-b5f9b1e72789', 1, '登录系统', NULL, NULL, '2021-01-02 15:04:23.161', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('34612d60-4a24-434c-b446-51c7aa1b28a9', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:04:51.157', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7b13e22a-8a58-4d08-b54d-8f5b6f4b41c4', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:04:52.85', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8b102180-b36b-4fd1-b43d-113053d57110', 5, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:09:39.153', '/operation/data-bak/deleteDataBak', '删除备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e213bcad-2afb-4c4d-8fbd-a76d36f2258e', 1, '登录系统', NULL, NULL, '2021-01-02 15:12:37.122', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('66b125f3-5d8e-4287-ad17-e4f482c12ef0', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:12:42.472', '/notify/getSmsConfig', NULL, '27.46.47.99', 0, 'java.lang.NullPointerException:null
	com.qcz.qmplatform.module.notify.controller.NotifyController.getSmsConfig(NotifyController.java:38)
com.qcz.qmplatform.module.notify.controller.NotifyController$$FastClassBySpringCGLIB$$eac69a48.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.as');
INSERT INTO "public"."sys_operate_log" VALUES ('48797552-a2f5-4367-8157-2b663c002249', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:12:53.651', '/notify/getSmsConfig', NULL, '27.46.47.99', 0, 'java.lang.NullPointerException:null
	com.qcz.qmplatform.module.notify.controller.NotifyController.getSmsConfig(NotifyController.java:38)
com.qcz.qmplatform.module.notify.controller.NotifyController$$FastClassBySpringCGLIB$$eac69a48.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.as');
INSERT INTO "public"."sys_operate_log" VALUES ('17625727-e89f-49f8-8896-11499c2f5f4f', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 12:12:52.057', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('70ce49e3-e922-4cec-833b-04f921e4c129', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 12:51:40.392', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b63a1ee4-995f-4f49-9fa0-d360f8a03dc8', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 12:53:46.359', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e87d98eb-c600-4dc0-aa8d-20ea5b429d8b', 1, '登录系统', NULL, NULL, '2021-01-03 13:33:50.963', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fec6afae-4db2-4348-97ea-493badc66468', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:13:30.082', '/notify/getSmsConfig', NULL, '27.46.47.99', 0, 'java.lang.NullPointerException:null
	com.qcz.qmplatform.module.notify.controller.NotifyController.getSmsConfig(NotifyController.java:38)
com.qcz.qmplatform.module.notify.controller.NotifyController$$FastClassBySpringCGLIB$$eac69a48.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.as');
INSERT INTO "public"."sys_operate_log" VALUES ('1934861b-8598-4c23-9e30-574ddebf78d6', 1, '登录系统', NULL, NULL, '2021-01-02 15:23:54.735', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d10901ac-e5cd-4898-b077-cb1c0ad26523', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:28:30.622', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4ecb5c17-16a9-4dc2-9b44-323c47f34590', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:29:57.059', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('92b9d560-11ba-4bfc-883a-cc5093866b3e', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:30:35.154', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1940a7b9-f75c-42cf-92fd-3a64cb223cab', 5, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:34:03.883', '/operation/data-bak/deleteDataBak', '删除备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a7dde21b-2777-403c-9194-7617bf8b98db', 1, '登录系统', NULL, NULL, '2021-01-02 15:40:53.997', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0c9df561-e3b3-434b-909b-89c5b11ca59e', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:43:38.652', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ad65fee3-78bf-45ae-8be1-6b227135e7ab', 1, '登录系统', NULL, NULL, '2021-01-02 15:52:14.912', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ed22ed0b-0ce7-481d-8e6a-20e3dd6fab4c', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:52:17.318', '/logout', '系统管理员 退出了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cd5524ba-98a0-4d3e-bea6-8e147958fcf8', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:52:22.379', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e0c9b1bd-cc38-495e-a521-b5b632caf186', 1, '登录系统', NULL, NULL, '2021-01-02 15:52:27.941', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2cc3d845-2362-4f26-828c-3a44cfd81030', 5, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:52:32.95', '/operation/data-bak/deleteDataBak', '删除备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4676981d-cede-4825-a3ea-151126db53dc', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:52:39.151', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c4e609c8-f52a-4ef8-96d1-657696c9af77', 1, '登录系统', NULL, NULL, '2021-01-02 15:57:31.674', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('16c2d742-3bec-4abd-ba6d-ec782dc56075', 5, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:57:37.298', '/operation/data-bak/deleteDataBak', '删除备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ffdd7633-ea9f-40f2-8463-e1cc88594f5f', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:57:39.09', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1024b937-ef49-434d-9f36-c8dd6a46965b', 5, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 15:57:56.177', '/operation/data-bak/deleteDataBak', '删除备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8b408af4-80cc-4bc9-9a48-37dcbd520072', 1, '登录系统', NULL, NULL, '2021-01-02 16:03:11.52', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e4a45440-e587-48d3-a4c2-bd652f2d9a85', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 16:03:21.824', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1abb1256-6f6b-4368-a4d3-c848e230db72', 1, '登录系统', NULL, NULL, '2021-01-02 16:06:22.024', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f4775293-2c36-44d2-ac9b-267760431a67', 5, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 16:06:31.784', '/operation/data-bak/deleteDataBak', '删除备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0ff157b4-188b-4725-8aca-35a0ce217e22', 3, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 16:06:34.665', '/operation/data-bak/exeBackup', '立即备份', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('760cd810-3b77-443a-9494-38f16c213c39', 1, '登录系统', NULL, NULL, '2021-01-02 19:28:38.121', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('01a498db-2504-4ded-b37d-127e90f133fa', 1, '登录系统', NULL, NULL, '2021-01-02 22:58:37.853', '/login', '系统管理员 进入了系统', '27.46.47.99', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('16e00588-2bf9-4c3a-a25c-bb3d2cfd2a71', 1, '登录系统', NULL, NULL, '2021-01-02 22:59:04.878', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e0852547-8284-428b-9225-5b720561e940', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 22:59:20.938', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('eff29c37-77d0-4661-b1de-afc1874b0ca0', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 23:01:09.945', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a4521937-8eff-4782-84ff-a51b3d57b6e6', 1, '登录系统', NULL, NULL, '2021-01-02 23:05:01.739', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('34b18290-dfa2-43fe-b8be-a4b8c16ece99', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-02 23:05:16.775', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c4b69bb9-22ff-4964-ba8d-5058d2ffe681', 1, '登录系统', NULL, NULL, '2021-01-03 12:04:46.98', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1ee3d246-fe06-402e-817a-a25e4e856896', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 12:05:02.676', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('96b4e1ea-46a9-43c4-beeb-2e985f104eb2', 1, '登录系统', NULL, NULL, '2021-01-03 12:08:19.867', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f7383b57-d724-473b-b819-52a1e429916f', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 12:08:29.176', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8a046fbc-7bb3-49c9-b07d-ee04c3ee0e87', 1, '登录系统', NULL, NULL, '2021-01-03 12:12:47.451', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7c298ed9-960b-4fde-a6b4-cc0bd776a132', 1, '登录系统', NULL, NULL, '2021-01-03 12:50:22.157', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fcb719b0-f5e9-4bc8-9ff7-01fb76e5c3c4', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 13:34:27.356', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3b4097de-0c01-45fc-a5e6-9535e14c4304', 1, '登录系统', NULL, NULL, '2021-01-03 15:33:35.887', '/login', '系统管理员 进入了系统', '27.46.44.246', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('45496597-8b09-4c72-81e3-bc7b40db0586', 1, '登录系统', NULL, NULL, '2021-01-03 16:16:49.448', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7e50dc65-1c0e-416a-b224-45acb55dc818', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 16:17:01.8', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1809a585-63ab-44f0-8fcb-94fdd9be6420', 1, '登录系统', NULL, NULL, '2021-01-03 16:21:01.927', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7d50572d-3c8c-4347-a671-e13f04473e30', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 16:21:04.793', '/operation/data-bak/getDataBakStrategy', NULL, '127.0.0.1', 0, 'java.lang.NumberFormatException:null
	java.lang.Integer.parseInt(Integer.java:542)
java.lang.Integer.parseInt(Integer.java:615)
com.qcz.qmplatform.module.operation.controller.DataBakController.getDataBakStrategy(DataBakController.java:85)
com.qcz.qmplatform.module.operation.controller.DataBakController$$FastClassBySpringCGLIB$$aa947ae9.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88)
com.qcz');
INSERT INTO "public"."sys_operate_log" VALUES ('42ee7cab-1c99-4dec-9850-e44ea8bdf60f', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 16:22:31.821', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('232a79e6-546e-4fc2-8c61-ef5ea29c417e', 1, '登录系统', NULL, NULL, '2021-01-03 16:30:23.611', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('44a295bf-8614-4dfc-98db-58558092f10e', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 16:31:30.415', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('251621b7-d7eb-4b46-baa9-5d6048028124', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 16:31:39.545', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('be7153ed-847c-453f-807c-654fda7237cf', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 16:31:42.884', '/operation/data-bak/saveDataBakStrategy', '调整数据备份策略', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b85e38bf-f87a-45f9-b938-ab68f6bcbf8d', 3, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 16:41:24.204', '/user/addUser', '新增用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a02b9194-e7d6-40d0-9428-ff9ceb05575f', 5, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 16:42:18.88', '/user/delUser', '删除用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e307a186-56ef-49fd-bef1-9abfbafc79bc', 1, '登录系统', NULL, NULL, '2021-01-03 17:37:48.874', '/login', '系统管理员 进入了系统', '27.38.254.240', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('18278c1f-77bb-47f2-85d6-72ad205ab6f2', 5, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 17:37:51.866', '/operation/data-bak/deleteDataBak', '删除备份', '27.38.254.240', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b1ce7dc4-d4ca-47a7-8e72-009528cb7316', 1, '登录系统', NULL, NULL, '2021-01-03 22:03:16.943', '/login', '系统管理员 进入了系统', '119.123.73.112', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ec7b41bd-c9b1-4a32-9b2e-34d9c37c7485', 5, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 22:20:45.596', '/user/delUser', '删除用户', '119.123.73.112', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('193f5930-48a7-40ca-8969-28ce94a13968', 4, '数据备份与恢复', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 22:20:49.711', '/operation/data-bak/recoverDataBak/264f62a7-2a2c-4b49-9f9b-6bf02f850b3c', '恢复备份', '119.123.73.112', 0, 'java.lang.Exception:java.lang.NullPointerException
	com.qcz.qmplatform.common.aop.aspect.OperateLogAspect.operateLogAround(OperateLogAspect.java:77)
sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
java.lang.reflect.Method.invoke(Method.java:498)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)
org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)
org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)
org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:62)
org.springframework.aop.framework.ReflectiveMethodInvocati');
INSERT INTO "public"."sys_operate_log" VALUES ('d0468b1a-c9c1-4789-aae4-609f44892f06', 5, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-03 22:24:23.428', '/user/delUser', '删除用户', '119.123.73.112', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f7700a8c-ca88-4b73-9758-35f31496138e', 1, '登录系统', NULL, NULL, '2021-01-04 19:37:50.32', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('69f2bd1f-e9cc-467d-8d3f-f28487e88a14', 1, '登录系统', NULL, NULL, '2021-01-04 20:18:43.544', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c2bb3b93-6bfa-4b3f-8302-e118615b2f1a', 1, '登录系统', NULL, NULL, '2021-01-04 22:11:29.767', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2d174774-63b3-4de8-8416-49838ed0ff5d', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-04 22:11:37.421', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1c01f350-3aa2-427a-981e-add40c93836b', 1, '登录系统', NULL, NULL, '2021-01-04 22:11:38.572', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5ccd6388-168d-4b69-91c7-5e339e626248', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-04 22:11:44.453', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('11b0af94-3956-48a2-8b65-57448dba4502', 1, '登录系统', NULL, NULL, '2021-01-04 22:11:45.68', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('14b1d66a-8b17-42b9-8489-c8898dcbd575', 1, '登录系统', NULL, NULL, '2021-01-04 23:29:56.819', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b656a1c1-8cea-49e2-b988-433ad8523e88', 1, '登录系统', NULL, NULL, '2021-01-04 23:36:00.102', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8592face-17e3-4050-badd-d010d239bd61', 1, '登录系统', NULL, NULL, '2021-01-04 23:38:25.016', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cb1f145d-731e-4c40-8e25-db446b1c0074', 1, '登录系统', NULL, NULL, '2021-01-04 23:40:31.483', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('428544cf-b5a3-4ff3-b97c-6221daeba6c8', 1, '登录系统', NULL, NULL, '2021-01-04 23:42:11.99', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e27c3e14-d1f6-491a-93b5-a4c468407260', 1, '登录系统', NULL, NULL, '2021-01-04 23:45:10.058', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b87384be-b8b3-4088-b93c-c2e83bf1bbb3', 1, '登录系统', NULL, NULL, '2021-01-04 23:47:16.575', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('28ced844-73a3-4d54-9dd3-fd5939650d23', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-04 23:48:04.317', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('81a0d400-64d1-4df2-9858-3158681ea0a3', 1, '登录系统', NULL, NULL, '2021-01-04 23:48:05.571', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c219ecb1-28aa-4463-bd88-e41bb440230a', 1, '登录系统', NULL, NULL, '2021-01-05 00:05:29.943', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a1e8a7db-0ce9-464a-97ab-cd418174ce48', 1, '登录系统', NULL, NULL, '2021-01-05 00:07:22.547', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6cb9c2a8-24f4-46f9-ad5a-5e87199aba5c', 1, '登录系统', NULL, NULL, '2021-01-05 00:14:20.878', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('24423a73-9aa7-44a1-82b0-1ed3ead7de42', 1, '登录系统', NULL, NULL, '2021-01-05 00:20:20.135', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e294b306-9af7-4ad8-bd96-1ec0805a3ba7', 1, '登录系统', NULL, NULL, '2021-01-05 00:21:45.989', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ce5f80f5-f80a-4440-b174-529b35d530f2', 1, '登录系统', NULL, NULL, '2021-01-05 00:22:51.982', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('77f6f217-eb18-4391-81a0-c81657dd1a59', 1, '登录系统', NULL, NULL, '2021-01-05 00:24:48.57', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9d11e010-0082-4058-b1ab-43e7200f0d3b', 1, '登录系统', NULL, NULL, '2021-01-05 00:25:21.395', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('57bf4220-d3a6-4b3c-8d92-0a8dde7c9b38', 1, '登录系统', NULL, NULL, '2021-01-05 00:26:25.52', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('304a89d3-e3ba-483e-9cc3-8499d6136795', 1, '登录系统', NULL, NULL, '2021-01-05 00:27:24.102', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7c01f19b-07ad-4dd1-9ed8-9106b94f66bc', 1, '登录系统', NULL, NULL, '2021-01-05 00:28:02.374', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('20a0a8c9-ab41-4730-b8da-f12706462cab', 1, '登录系统', NULL, NULL, '2021-01-05 00:30:41.799', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cee898fa-7600-47c5-859a-590ad28ac648', 1, '登录系统', NULL, NULL, '2021-01-05 00:32:16.956', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('63bbb2f8-59f2-4fa1-b3fa-4be368258a02', 1, '登录系统', NULL, NULL, '2021-01-05 00:33:04.187', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('bfffaf74-4c69-4492-8e54-41d301f5c6ed', 1, '登录系统', NULL, NULL, '2021-01-05 00:36:48.757', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a26d8e1b-6b2a-4d53-a8e9-32a6774f9974', 1, '登录系统', NULL, NULL, '2021-01-05 08:33:43.696', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b65f4789-2efc-4cce-94f4-4d9a67596fc6', 1, '登录系统', NULL, NULL, '2021-01-05 08:37:56.481', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8e2b88b3-5c54-4a6f-86da-a5731d6609d9', 1, '登录系统', NULL, NULL, '2021-01-05 08:39:49.526', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('17d27bc1-ca08-40c3-b06f-bcef9db45d96', 1, '登录系统', NULL, NULL, '2021-01-05 08:41:40.706', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3228f4e6-a629-48b2-be5b-2456f56ab4a3', 1, '登录系统', NULL, NULL, '2021-01-05 08:48:31.098', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b725894d-6e1c-415b-92e4-b9e8376e8429', 1, '登录系统', NULL, NULL, '2021-01-05 09:01:27.005', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9bfad509-01d3-4bb1-9595-2345c12c744b', 1, '登录系统', NULL, NULL, '2021-01-05 11:30:39.066', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a92620c6-d3c4-4a2a-b742-fe599aad0918', 1, '登录系统', NULL, NULL, '2021-01-05 11:31:18.21', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('753e6f4e-0e6b-4bd9-b93c-60e6f0915051', 1, '登录系统', NULL, NULL, '2021-01-05 12:31:49.452', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('52e459fe-246c-4d22-a365-d123249b827e', 1, '登录系统', NULL, NULL, '2021-01-05 12:32:07.149', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('414d91ce-2c49-4fdb-b5d5-1bf44221a36f', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-05 12:35:19.943', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7af596af-ab14-473a-b09b-26fe2bc24114', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-05 12:43:18.907', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4404aad4-126a-4b16-bdac-6a7c83907cae', 1, '登录系统', NULL, NULL, '2021-01-05 12:43:20.052', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('4d187126-7b26-4978-ba56-7a072acfc9fc', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-05 12:46:08.247', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('5cf1f2d2-4402-4e15-a44d-380e6cce7b33', 1, '登录系统', NULL, NULL, '2021-01-05 12:46:18.51', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c18f06ef-2f3f-4de7-80c1-a785e1ca076f', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-05 12:50:09.972', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b4951cff-0678-478e-9c18-df8ce118d92d', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-05 12:50:15.723', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('969b2bce-b563-465a-99a7-a938fb0cb857', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-05 12:50:16.701', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a681ed66-5bc5-4190-b002-f4a7e8a9f698', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-05 12:50:21.855', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e6679a5d-7b78-4fcf-ab0a-e7e5355830f3', 1, '登录系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2021-01-05 12:50:24.029', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('924243e8-c68b-4d20-a2bd-9caa076cb414', -1, '退出系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2021-01-05 12:50:27.543', '/logout', '屈长忠 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9eb46ad8-fcd9-4064-bece-eaa5f76d8709', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-05 12:50:29.264', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('40a465f5-5129-4f1f-95ef-e5ee73e02090', 1, '登录系统', NULL, NULL, '2021-01-05 16:14:54.362', '/login', '系统管理员 进入了系统', '61.141.65.25', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('aff0b0f1-d0ed-4182-a1d3-5da34475cff6', 1, '登录系统', NULL, NULL, '2021-01-06 08:42:46.699', '/login', '系统管理员 进入了系统', '183.15.177.47', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c5423262-e712-4713-aaaf-c1b7379ada1f', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 08:53:07.337', '/login', '系统管理员 进入了系统', '183.15.177.47', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('8c670869-e59c-4364-a84a-8da4f451a012', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 10:21:58.087', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('e04b087f-7c27-4304-b37e-775426c0bc47', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 10:22:06.349', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3b6b5dcb-e3c3-4a10-9c2e-c0cfe4f8a9b2', 4, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 10:22:17.416', '/operation/dict/updateDict', '修改字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('57143a35-7733-47a5-9c24-af48324f2c25', 4, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 10:22:29.223', '/operation/dict/updateDict', '修改字典', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('47dc08d4-044d-47da-8acc-5d196b6ff90f', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 10:22:35.704', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c77f3b2f-7378-4379-8db2-c2ddd643e08b', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 20:15:28.302', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2792ac0f-6fb4-4ff1-92db-c08b05a3f28c', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 20:15:44.699', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('31920812-1a2f-4e5c-baf5-0001d48b4fa9', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:50:16.943', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6879d7c8-610a-4ed6-b6af-8f7190495b75', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:50:16.943', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('da7ec949-74ab-4492-9aa7-523458953fa8', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:50:16.942', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('96ffef22-1cd4-42fe-9947-a179f2545532', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:50:49.171', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('eb1420ee-d825-4911-9f2e-4b44b530cf0b', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:50:51.563', '/logout', '系统管理员 退出了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('48f882c9-99e4-475a-95f8-d4042b7dc4c5', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:50:56.907', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('778e59c4-b595-4904-b69b-126664ea13d7', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:50:58.865', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0c89d8c6-1319-4652-b7ff-71c06c9b73ab', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:50:59.947', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('47cf651a-c5ae-457a-bad4-12777733121e', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:51:14.495', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d38d4521-2a00-4449-8c77-e7d7e6e54582', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:51:21.348', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('108bada8-3615-4400-b782-18e623cdd51b', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:58:29.486', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f1dd0b75-1c7d-407d-a55b-1e5b9bca057a', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:58:33.706', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1ba07847-d482-44e7-b335-7f2212fbd1ed', 3, '字典管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 21:59:40.275', '/operation/dict-attr/addDictAttr', '新增字典属性', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('1494adb0-cfd9-4300-abc3-e6d3d4a16506', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 22:00:05.117', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f2db597d-19d4-42aa-a9c2-4d74cace0d14', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 22:00:09.027', '/login', '系统管理员 进入了系统', '113.87.130.236', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('44a13317-eb2a-4990-aff9-295c62111f09', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 22:01:40.998', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('9875051a-ff88-4e07-ab98-18732c21cac7', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-06 22:01:47.308', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ec032098-c0dc-4c1f-bce1-4e9cd2647572', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 08:28:05.773', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('fbeb759c-f40b-40c8-a107-ad6a20542013', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 08:53:40.499', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('2edde831-6641-4df0-bb4e-c11b1c74ffed', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 08:54:20.747', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d8051694-0b19-475e-9dd1-062042f7ebf6', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 14:11:34.809', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('66e5011e-b7f4-402a-a1b6-d032036cc624', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 14:11:48.205', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('66382cf3-fa64-4f27-994e-ad154391bc7c', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 14:11:57.944', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('7a8b02ee-0780-4c9a-982a-672e36493782', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 14:12:22.199', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('411d02ab-b63e-4db6-a5ad-ce946df76f3a', 0, NULL, NULL, NULL, '2021-01-07 14:48:01.092', '/user/noNeedLogin/getValidateCode', NULL, '127.0.0.1', 0, 'java.lang.NullPointerException:null
	com.qcz.qmplatform.module.system.controller.UserController.getValidateCode(UserController.java:284)
com.qcz.qmplatform.module.system.controller.UserController$$FastClassBySpringCGLIB$$6ac3e070.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88)
com.qcz.qmplatform.common.aop.aspect.RequestAspect.requestAround(RequestAspect.java:51)
sun.reflect.NativeMethodAcc');
INSERT INTO "public"."sys_operate_log" VALUES ('f334f9e9-1e88-4895-aa8c-caa1b90f781e', 0, NULL, NULL, NULL, '2021-01-07 14:56:52.271', '/user/noNeedLogin/getValidateCode', NULL, '127.0.0.1', 0, 'java.lang.IllegalArgumentException:[Assertion failed] - this String argument must have text; it must not be null, empty, or blank
	cn.hutool.core.lang.Assert.notBlank(Assert.java:207)
cn.hutool.core.lang.Assert.notBlank(Assert.java:225)
com.qcz.qmplatform.module.system.controller.UserController.getValidateCode(UserController.java:284)
com.qcz.qmplatform.module.system.controller.UserController$$FastClassBySpringCGLIB$$6ac3e070.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.aspectj.Meth');
INSERT INTO "public"."sys_operate_log" VALUES ('01a6fea7-8184-4045-9333-9bcce82b6b1a', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 15:08:48.506', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('767e8db4-bfd1-49a3-8ebd-3c371fefd22c', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 15:10:45.18', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('19a2220f-b155-43ed-b1a9-e9f8dc1d72bf', 4, '用户管理', NULL, NULL, '2021-01-07 16:15:01.088', '/user/noNeedLogin/changeUserPwd', '找回密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0e0d3fd0-9d1f-4ab2-afe3-1ebfc1161709', 4, '用户管理', NULL, NULL, '2021-01-07 16:17:53.306', '/user/noNeedLogin/changeUserPwd', '找回密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f7865be5-8e32-42c5-911f-4d4db0099efe', 4, '用户管理', NULL, NULL, '2021-01-07 16:22:15.588', '/user/noNeedLogin/changeUserPwd', '找回密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('382c6e30-4cff-4ac5-a95b-76b4f3688430', 1, '登录系统', NULL, NULL, '2021-01-07 16:22:22.518', '/login', 'admin 尝试登录系统，但失败了', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ad30eab7-f7fd-450c-8c98-00d3c5751a85', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 16:22:25.396', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0f1bdad8-02b3-4432-a62d-298642eeebb0', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 16:22:34.93', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('46cdd8bd-aa3e-46cf-9796-b0b0593a3166', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 16:22:38.912', '/user/changeCurrentUserPwd', '修改当前用户密码', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('cb3db696-df5d-405e-9de9-7c04f9be849c', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 16:22:40.954', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c9ff7103-c80c-4b73-8be4-e8016c2ecbc6', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-07 16:22:41.962', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('149cda0e-e061-4f1b-bd4a-6905c051f5e5', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 09:47:49.997', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('3e343513-e5f8-413a-bcc7-bdb1beed37cf', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 09:48:03.511', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('484aaffa-365e-40aa-972b-cd9d3f74a09f', 4, '用户管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 09:48:20.282', '/user/updateUser', '修改用户', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a71fdf24-db04-4a25-8fcc-28e1db69bc7c', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 10:27:31.373', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('c2f40b08-5ecc-4a9b-8170-c856fc20f7ab', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 10:51:19.66', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('65b6d003-f239-4f39-8efc-937114f9e07e', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 10:51:49.414', '/menu/addPermission', '新增权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('518e3311-d810-442e-a3ca-050ce42a3716', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 10:52:39.47', '/menu/addPermission', '新增权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('6017c5f7-785a-4226-a8ac-20934b3b9848', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 10:54:42.981', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f7e83c6d-4cfe-4bb5-9813-9d9c13fdfcc1', 3, '菜单管理', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 10:54:53.876', '/menu/addPermission', '新增权限', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('0070f8ba-a126-4584-b1d6-a2ce4b76d8c0', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 14:56:40.126', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('14d65b59-6f7a-44a1-9efb-212fa6cf21ed', 1, '登录系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2021-01-08 14:57:31.319', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('f7c85bd7-3657-43f9-be9c-5b5488f62c30', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 14:57:32.96', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a6289c6d-3c5a-4405-882e-5375f79633a1', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 15:05:54.949', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('d7261c11-fa60-4cea-a028-fbd210176246', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 15:05:56.777', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('55f587be-de58-4914-9cf8-254e34d17b20', -1, '退出系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-08 15:06:02.168', '/logout', '系统管理员 退出了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('b8d2ccd4-6626-48be-95c7-e882ffd70900', 1, '登录系统', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '屈长忠', '2021-01-08 15:06:05.084', '/login', '屈长忠 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('73e648d8-3910-4947-b229-cafd7438d195', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-13 14:53:46.472', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('a8a26e15-fa02-4626-a9e6-3762b3df2bcb', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-19 13:46:33.324', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);
INSERT INTO "public"."sys_operate_log" VALUES ('ddb9f6e8-4817-4eff-94b3-64c09b3349ce', 0, NULL, '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-19 13:46:37.534', '/notify/getSmsConfig', NULL, '127.0.0.1', 0, 'java.lang.ClassCastException:com.qcz.qmplatform.module.notify.bean.SmsConfig cannot be cast to com.qcz.qmplatform.module.notify.vo.SmsConfigVO
	com.qcz.qmplatform.module.notify.controller.NotifyController.getSmsConfig(NotifyController.java:43)
com.qcz.qmplatform.module.notify.controller.NotifyController$$FastClassBySpringCGLIB$$eac69a48.invoke(<generated>)
org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:56)
org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88)
com.qc');
INSERT INTO "public"."sys_operate_log" VALUES ('6798198e-4326-47f7-93ee-4ce3e887da59', 1, '登录系统', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '系统管理员', '2021-01-19 14:58:14.183', '/login', '系统管理员 进入了系统', '127.0.0.1', 1, NULL);

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
INSERT INTO "public"."sys_role_permission" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', 'b555a066-c76d-4b8f-a834-4998cc000711');
INSERT INTO "public"."sys_role_permission" VALUES ('67d86434-c302-4924-a75a-dfe5fbc4affd', 'e36040a7-f7cb-42f8-a55f-0a6f6e138958');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '326ad00b-2e0f-4941-9191-a573ada65410');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '80345dc4-cc15-405b-a371-82e50b64fcd9');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', 'e843dfa8-e868-4a83-aa35-215cbfbfd643');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '428493a8-5e23-4e64-a24c-78e61b3ed854');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '1d1f59eb-69d8-4894-abfd-54805f36b7df');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', 'd0ceae95-12d9-40ea-abe3-03df0b44dd2e');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '44ca4945-356e-499b-af2e-08035d39a619');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '318e6adb-e225-4636-9546-6b132b715c24');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', 'bc8a4903-3b5c-4b7e-a3e1-3c851603ff8b');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '0ec94352-4ea3-4f59-961c-38ca6779d714');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '44d656f2-86eb-4326-ad12-935ca160551d');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', 'cc2cbb01-f42f-4272-b02a-4c74484ca62a');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '37778f44-615d-4d69-bf8a-99ff0bafef40');
INSERT INTO "public"."sys_role_permission" VALUES ('3e8461bf-537d-4b7d-91db-9676a03fb6fb', '8c9768b7-3574-471b-bd51-6bd6188f24b5');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', '1');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', 'ef07f63f-4ec1-4053-bb53-db7891359339');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', '4489ccf9-fec5-4f81-b817-0c1cd2c7a171');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', '9ceffdf2-420e-40dc-9199-ab4ff049737b');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', 'bfba142d-6a80-417b-9e58-a3671283f2fb');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', 'b555a066-c76d-4b8f-a834-4998cc000711');
INSERT INTO "public"."sys_role_permission" VALUES ('test_tv', 'e36040a7-f7cb-42f8-a55f-0a6f6e138958');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '1');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'ef07f63f-4ec1-4053-bb53-db7891359339');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '4489ccf9-fec5-4f81-b817-0c1cd2c7a171');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '9ceffdf2-420e-40dc-9199-ab4ff049737b');
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
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '9ac8ef82-57c9-461e-9ee0-e2c892e8bcac');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'f2a871d8-c1dd-4775-ad0d-8b8b3f53eede');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'b7564f6a-5ce5-49d8-9d15-e8fe396eecaa');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '2e2e994a-da4f-4cb5-84fc-0324efae29f6');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '326ad00b-2e0f-4941-9191-a573ada65410');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '80345dc4-cc15-405b-a371-82e50b64fcd9');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'e843dfa8-e868-4a83-aa35-215cbfbfd643');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '428493a8-5e23-4e64-a24c-78e61b3ed854');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '1d1f59eb-69d8-4894-abfd-54805f36b7df');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'd0ceae95-12d9-40ea-abe3-03df0b44dd2e');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '44ca4945-356e-499b-af2e-08035d39a619');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '318e6adb-e225-4636-9546-6b132b715c24');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'bc8a4903-3b5c-4b7e-a3e1-3c851603ff8b');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '0ec94352-4ea3-4f59-961c-38ca6779d714');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '44d656f2-86eb-4326-ad12-935ca160551d');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'cc2cbb01-f42f-4272-b02a-4c74484ca62a');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '37778f44-615d-4d69-bf8a-99ff0bafef40');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', '8c9768b7-3574-471b-bd51-6bd6188f24b5');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'b555a066-c76d-4b8f-a834-4998cc000711');
INSERT INTO "public"."sys_role_permission" VALUES ('a77a83b4-abd7-4f3f-ac22-e914bb68ad5c', 'e36040a7-f7cb-42f8-a55f-0a6f6e138958');

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
INSERT INTO "public"."sys_user" VALUES ('bb9f8a5a-41fe-4907-a8ac-d97b781a377c', '707730986aac34a3efdd6bfe4d437050', '程序员', 'java', '1', 'java', '2020-12-05 14:51:48.374', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '18675867653', 'qq@qq.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('17', '$2a$10$u8Gb7oKS6.e.9A4UGCFXr.ptOSjzHzYxriwRlG9x10ZYGqMnLHO9W', '东哥', 'asf', '1', 'as', NULL, NULL, '13245675432', '222@as.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('5a4363f6-a31c-4859-bde4-8a38d9b0897d', 'ae86cb449cafddc0a5e4de0143d7fafd', '我试试', '为首的发生', '2', '', '2020-12-05 16:08:22.729', '20a008c3-bc54-42a6-9822-ea4674aa5d1f', '18787653745', 'asfsf@d.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('c915ae31-eb33-4107-b77e-a37fc0e6e776', 'a9b3def6f72a246ef718032362bacd3f', '王麻子', 'wangmz', '2', '', '2020-12-11 22:42:10.813', '6b663de3-e983-4c8a-b900-dc0b66b2a801', '16577653453', '123@qq.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('4cee17bb-68f9-42a9-9b24-713c40b74d30', '$2a$10$BuoOFBeYXaFuPSFjWhUkU.42p8ACcjRR8UC2RjRxaJuci2OICyxIK', '隔壁老王', 'wang', '1', '', NULL, NULL, '13198989892', 'qcz_dfsdf@ll.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('14', '0b91250def393a6c250f2c6c49d6d337', '张三', 'sfd', '2', 'asfdsdf电饭锅', NULL, NULL, '13187679076', 'safs@com.asdf', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('6b663de3-e983-4c8a-b900-dc0b66b2a801', '1bb99b4c656b2d5ad5deea94ffe54d3e', '屈长忠', 'qcz', '1', '管理员1大概', '2020-10-01 12:51:42.935', NULL, '18677776666', 'qcz_left@163.com', 0, '/file/20201213032211_charon.png');
INSERT INTO "public"."sys_user" VALUES ('15', '$2a$10$TYLiWGsoIdcngzCc6H2gwesbaDOLrkZNmn5JNn53plgfYU7ZogFQu', '李四', 'afsdf', '1', 'afsdf', NULL, NULL, '13187679076', 'asfsf@sdf.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('10', '$2a$10$qfxTDehc7vdSKG3zafs9tOR14DfVZdwjmQ6/zTfuIM9PZeg2bwle.', '华为', 'sfdf', '1', 'sfasdf', NULL, NULL, '13123456678', '222@222.c', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('105a8170-39eb-4c00-9061-9ac6dc915165', '$2a$10$V/Q6.69PWTRJ1XETKOoSPeRc7P8aO6dajko8CTM26BFlj7ZntWNE.', 'asdfaasdf', 'asdfsadfd', '0', '', NULL, NULL, '13123232323', 'sadfsdf@sfd.dd', 1, NULL);
INSERT INTO "public"."sys_user" VALUES ('16', '$2a$10$OEZSm9DhiW7pHxPeAnRiBOqz2oAKzC13EwLcwG1PF2lKlDC4eJKrq', '我是DJ', 'ass', '1', '', NULL, NULL, '13187679076', 'asfsf@df.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', 'df655ad8d3229f3269fad2a8bab59b6c', '系统管理员', 'admin', '1', '', NULL, NULL, '18675864534', 'qcz_left@163.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('12', '$2a$10$fGTnoqoIWd3mr86BqDJQOOHr3DiyVO0QrVo7zFVD247NiCEGJV3Y2', '打老虎', '456', '1', 'sfasfs', NULL, NULL, '18676767689', 'asdfasf@sfds.com', 0, NULL);
INSERT INTO "public"."sys_user" VALUES ('2185d74c-df5c-4125-9d3b-9fd048118f73', '9949b92f352ae8c5fab80b57b29332fe', '测试', 'test', '1', '可爱', '2020-10-03 13:33:58.702', NULL, '18787878787', '123r23@dd.com', 0, NULL);

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
INSERT INTO "public"."sys_user_organization" VALUES ('14', '784c2e87-b88a-46b8-8cd3-871e4646df36');
INSERT INTO "public"."sys_user_organization" VALUES ('6b663de3-e983-4c8a-b900-dc0b66b2a801', '11f67a26-22da-49c1-86e8-78ee2a55ac7f');
INSERT INTO "public"."sys_user_organization" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', '526f57b8-6b09-407d-9099-96fa38719814');
INSERT INTO "public"."sys_user_organization" VALUES ('105a8170-39eb-4c00-9061-9ac6dc915165', '526f57b8-6b09-407d-9099-96fa38719814');
INSERT INTO "public"."sys_user_organization" VALUES ('16', '526f57b8-6b09-407d-9099-96fa38719814');
INSERT INTO "public"."sys_user_organization" VALUES ('12', '526f57b8-6b09-407d-9099-96fa38719814');
INSERT INTO "public"."sys_user_organization" VALUES ('2185d74c-df5c-4125-9d3b-9fd048118f73', '23c124bb-ce39-4ee1-acb1-bf007a681188');

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
INSERT INTO "public"."sys_user_role" VALUES ('bb9f8a5a-41fe-4907-a8ac-d97b781a377c', 'a77a83b4-abd7-4f3f-ac22-e914bb68ad5c');
INSERT INTO "public"."sys_user_role" VALUES ('6b663de3-e983-4c8a-b900-dc0b66b2a801', 'a77a83b4-abd7-4f3f-ac22-e914bb68ad5c');
INSERT INTO "public"."sys_user_role" VALUES ('c915ae31-eb33-4107-b77e-a37fc0e6e776', '3e8461bf-537d-4b7d-91db-9676a03fb6fb');
INSERT INTO "public"."sys_user_role" VALUES ('4cee17bb-68f9-42a9-9b24-713c40b74d30', 'test_tv');
INSERT INTO "public"."sys_user_role" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', '3e8461bf-537d-4b7d-91db-9676a03fb6fb');
INSERT INTO "public"."sys_user_role" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', 'a77a83b4-abd7-4f3f-ac22-e914bb68ad5c');
INSERT INTO "public"."sys_user_role" VALUES ('20a008c3-bc54-42a6-9822-ea4674aa5d1f', '67d86434-c302-4924-a75a-dfe5fbc4affd');
INSERT INTO "public"."sys_user_role" VALUES ('105a8170-39eb-4c00-9061-9ac6dc915165', 'test_tv');
INSERT INTO "public"."sys_user_role" VALUES ('16', 'test_tv');
INSERT INTO "public"."sys_user_role" VALUES ('2185d74c-df5c-4125-9d3b-9fd048118f73', '67d86434-c302-4924-a75a-dfe5fbc4affd');

-- ----------------------------
-- Table structure for tbl_attachment
-- ----------------------------
DROP TABLE IF EXISTS "public"."tbl_attachment";
CREATE TABLE "public"."tbl_attachment" (
  "attachment_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "attachment_name" varchar(50) COLLATE "pg_catalog"."default",
  "attachment_url" varchar(100) COLLATE "pg_catalog"."default",
  "upload_user_id" varchar(50) COLLATE "pg_catalog"."default",
  "upload_user_name" varchar(10) COLLATE "pg_catalog"."default",
  "upload_time" timestamp(6),
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."tbl_attachment"."attachment_id" IS '附件ID，主键';
COMMENT ON COLUMN "public"."tbl_attachment"."attachment_name" IS '附件名称';
COMMENT ON COLUMN "public"."tbl_attachment"."attachment_url" IS '附件路径';
COMMENT ON COLUMN "public"."tbl_attachment"."upload_user_id" IS '上传人id';
COMMENT ON COLUMN "public"."tbl_attachment"."upload_user_name" IS '上传人名称';
COMMENT ON COLUMN "public"."tbl_attachment"."upload_time" IS '上传时间';
COMMENT ON COLUMN "public"."tbl_attachment"."description" IS '附件说明';
COMMENT ON TABLE "public"."tbl_attachment" IS '文件';

-- ----------------------------
-- Records of tbl_attachment
-- ----------------------------

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
-- View structure for v_sys_dict_attr
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_sys_dict_attr";
CREATE VIEW "public"."v_sys_dict_attr" AS  SELECT sd.dict_code,
    sda.attr_name,
    sda.attr_value,
    sda.dict_id,
    sda.attr_id
   FROM (sys_dict sd
     LEFT JOIN sys_dict_attr sda ON (((sd.dict_id)::text = (sda.dict_id)::text)));

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."sys_user_id_seq"', 22, true);

-- ----------------------------
-- Primary Key structure for table ope_data_bak
-- ----------------------------
ALTER TABLE "public"."ope_data_bak" ADD CONSTRAINT "ope_data_bak_pkey" PRIMARY KEY ("bak_id");

-- ----------------------------
-- Primary Key structure for table sys_button
-- ----------------------------
ALTER TABLE "public"."sys_button" ADD CONSTRAINT "sys_button_pkey" PRIMARY KEY ("button_id");

-- ----------------------------
-- Uniques structure for table sys_dict
-- ----------------------------
ALTER TABLE "public"."sys_dict" ADD CONSTRAINT "uq_dict_code" UNIQUE ("dict_code");

-- ----------------------------
-- Primary Key structure for table sys_dict
-- ----------------------------
ALTER TABLE "public"."sys_dict" ADD CONSTRAINT "sys_dict_pkey" PRIMARY KEY ("dict_id");

-- ----------------------------
-- Uniques structure for table sys_dict_attr
-- ----------------------------
ALTER TABLE "public"."sys_dict_attr" ADD CONSTRAINT "uk_sys_dict_attr" UNIQUE ("dict_id", "attr_value");

-- ----------------------------
-- Primary Key structure for table sys_dict_attr
-- ----------------------------
ALTER TABLE "public"."sys_dict_attr" ADD CONSTRAINT "sys_dict_attr_pkey" PRIMARY KEY ("attr_id");

-- ----------------------------
-- Uniques structure for table sys_ini
-- ----------------------------
ALTER TABLE "public"."sys_ini" ADD CONSTRAINT "uk_sys_ini_sec_pro" UNIQUE ("section", "property");

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
-- Uniques structure for table sys_role_permission
-- ----------------------------
ALTER TABLE "public"."sys_role_permission" ADD CONSTRAINT "uk_sys_role_permission" UNIQUE ("role_id", "permission_id");

-- ----------------------------
-- Uniques structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "unique_loginname" UNIQUE ("loginname");
COMMENT ON CONSTRAINT "unique_loginname" ON "public"."sys_user" IS '登录名唯一';

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_user_organization
-- ----------------------------
ALTER TABLE "public"."sys_user_organization" ADD CONSTRAINT "uk_sys_user_organization" UNIQUE ("user_id", "organization_id");

-- ----------------------------
-- Uniques structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "uk_sys_user_role" UNIQUE ("user_id", "role_id");

-- ----------------------------
-- Primary Key structure for table tbl_attachment
-- ----------------------------
ALTER TABLE "public"."tbl_attachment" ADD CONSTRAINT "attachment_pkey" PRIMARY KEY ("attachment_id");
