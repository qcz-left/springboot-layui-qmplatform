SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_icon
-- ----------------------------
DROP TABLE IF EXISTS `sys_icon`;
CREATE TABLE `sys_icon`  (
  `icon_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `icon_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标编码',
  `icon_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标名称',
  `icon_font_class` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标类名',
  PRIMARY KEY (`icon_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-图标' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_icon
-- ----------------------------
INSERT INTO `sys_icon` VALUES ('1', '&#xe679;', '验证码', 'layui-icon layui-icon-vercode');
INSERT INTO `sys_icon` VALUES ('2', '&#xe677;', '微信', 'layui-icon layui-icon-login-wechat');
INSERT INTO `sys_icon` VALUES ('3', '&#xe676;', 'QQ', 'layui-icon layui-icon-login-qq');
INSERT INTO `sys_icon` VALUES ('4', '&#xe66f;', '用户名', 'layui-icon layui-icon-username');
INSERT INTO `sys_icon` VALUES ('5', '&#xe68e;', '主页', 'layui-icon layui-icon-home');
INSERT INTO `sys_icon` VALUES ('6', '&#xe716;', '设置-空心', 'layui-icon layui-icon-set');
INSERT INTO `sys_icon` VALUES ('7', '&#xe770;', '用户', 'layui-icon layui-icon-user');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单ID',
  `PARENT_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '父ID',
  `PARENT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父菜单名称',
  `MENU_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `ICON` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `SORT` bigint(10) NULL DEFAULT 100 COMMENT '排序',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '', '系统管理', '', 'layui-icon layui-icon-set-fill', 99);
INSERT INTO `sys_menu` VALUES ('2', '1', '系统管理', '菜单管理', 'menu/menuListPage', 'layui-icon layui-icon-more', 100);
INSERT INTO `sys_menu` VALUES ('2e3f11c5-5a71-44ed-a6d1-b825f38ef495', '0', '', '测试500', 'department/departmentListPage', 'layui-icon layui-icon-vercode', 1122);
INSERT INTO `sys_menu` VALUES ('3', '0', '', '操作日志', 'sysOperateLog/sysOperateLogListPage', '', 100);
INSERT INTO `sys_menu` VALUES ('3171af1b-ae58-4ab2-8600-9bc748105f5d', '1', '系统管理', '用户管理', 'user/userListPage', 'layui-icon layui-icon-user', 1);
INSERT INTO `sys_menu` VALUES ('4', '1', '系统管理', '角色管理', 'role/roleListPage', NULL, 100);
INSERT INTO `sys_menu` VALUES ('6', '0', '', '主页', 'main/homePage', 'layui-icon layui-icon-home', 98);
INSERT INTO `sys_menu` VALUES ('f3ca4c56-a2d6-4159-adf9-208e5df99d55', '0', '', '测试404', 'test/404', 'layui-icon layui-icon-vercode', 111);

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log`  (
  `log_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志id',
  `operate_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `operate_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作名称',
  `operate_user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人id',
  `operate_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人名称',
  `operate_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `update_params` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '更新参数',
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法路径',
  `table_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求ip地址',
  `operate_status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作状态',
  `operate_system` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统名',
  `operate_module` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作模块',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `ROLE_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色ID',
  `ROLE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `ROLE_SIGN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `REMARK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', '超级管理员~~');
INSERT INTO `sys_role` VALUES ('745fcee9-74d3-4063-a95d-8fe416b98ce5', '测试', 'test', '备注：');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `ROLE_MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ROLE_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MENU_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ROLE_MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('4', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('5', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('50ef2e22-267e-406c-9573-284e09c15273', '745fcee9-74d3-4063-a95d-8fe416b98ce5', '3171af1b-ae58-4ab2-8600-9bc748105f5d');
INSERT INTO `sys_role_menu` VALUES ('6', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('7', '1', '3171af1b-ae58-4ab2-8600-9bc748105f5d');
INSERT INTO `sys_role_menu` VALUES ('77383f8e-94ff-4389-9b4a-7aeb2214475c', '745fcee9-74d3-4063-a95d-8fe416b98ce5', '1');
INSERT INTO `sys_role_menu` VALUES ('8', '1', 'f3ca4c56-a2d6-4159-adf9-208e5df99d55');
INSERT INTO `sys_role_menu` VALUES ('9', '1', '2e3f11c5-5a71-44ed-a6d1-b825f38ef495');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户ID',
  `USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `CREATE_USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `UPDATE_TIME` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人ID',
  `UPDATE_USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
  `LOGIN_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `LOGIN_PASSWORD` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `USER_SEX` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别（M：男；F：女）',
  `OCCUPATION` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `REMARK` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `USER_IMAGE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `LOCKED` int(1) NULL DEFAULT 1 COMMENT '锁定状态（0：锁定，1：正常）',
  `THEME_COLOR` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题颜色',
  PRIMARY KEY (`USER_ID`) USING BTREE,
  INDEX `USER_ID_INDEX`(`USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0ce1e783-962b-48e5-a988-6461883cfa8b', 'qq', NULL, '', '', '2019-01-16 16:06:21', 'admin', '管理员', 'qqqq', '137fa95a33e1a48e7842f10db756e6f4', 'F', NULL, '', '', 1, NULL);
INSERT INTO `sys_user` VALUES ('38c91f58-5858-4dac-8547-bdbb445d235a', 'ww', NULL, 'admin', '管理员', '2018-11-24 20:05:31', 'admin', '管理员', 'ww', '', 'M', NULL, '', '', 1, NULL);
INSERT INTO `sys_user` VALUES ('714ce926-cfca-4ca3-9746-2df2ca8a2643', 'qq', NULL, NULL, NULL, NULL, NULL, NULL, 'qqq', NULL, 'M', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES ('7e9810d2-0b4a-4f57-b2d7-c14a1d99cc63', 'qcz', '2019-01-16 16:18:25', 'admin', '管理员', '2019-01-16 16:18:25', 'admin', '管理员', 'qcz', 'f6c690eb0919796ee5cab5c2d1b47369', 'M', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES ('8dc44685-519a-483c-a39a-f8cbcfad9653', 'test5', NULL, '', '', '2018-12-02 15:13:01', 'admin', '管理员', 'test', 'test5', 'M', NULL, '', '', 0, NULL);
INSERT INTO `sys_user` VALUES ('a7a74381-b759-42bd-9453-af82f9a320c6', 'qq', NULL, NULL, NULL, NULL, NULL, NULL, 'qq', NULL, 'M', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES ('admin', '管理员', '2018-11-13 15:07:45', '', '', '2018-12-01 20:55:17', 'admin', '管理员', 'admin', '83976aece2b2d3ec1eb0caa78bbc43d7', 'M', NULL, '', '', 1, '');
INSERT INTO `sys_user` VALUES ('b0061be8-7aa2-425e-b49e-de38fd57a2a9', 'www', NULL, '', '', '2019-03-08 21:02:53', 'b0061be8-7aa2-425e-b49e-de38fd57a2a9', 'www', 'www', '05b6837f6cf010be52688b53d6045a87', 'M', NULL, '', '', 1, '#4e4747');
INSERT INTO `sys_user` VALUES ('d06fc615-371e-433c-9982-383dfddad7fb', '测试用户01', '2018-12-08 11:51:51', 'admin', '管理员', '2018-12-08 11:51:51', 'admin', '管理员', 'test01', 'b12193c759c2a5eaed86ca3eb058c724', 'M', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES ('e862dedb-5602-45a3-a3da-5f3c12a35ec5', 'qq', '2018-11-24 20:10:31', 'admin', '管理员', '2018-11-24 20:10:31', 'admin', '管理员', 'qqqqq', NULL, 'M', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_user` VALUES ('e8c6c37a-f145-4ded-941c-5d83f89d7c53', 'ee', '2018-11-24 19:56:58', 'admin', '管理员', '2018-11-24 19:56:58', 'admin', '管理员', 'ee', NULL, 'M', NULL, NULL, NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `USER_ROLE_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `USER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ROLE_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('067c7fdb-88ca-4221-aa6b-d4a68434485c', '0ce1e783-962b-48e5-a988-6461883cfa8b', '745fcee9-74d3-4063-a95d-8fe416b98ce5');
INSERT INTO `sys_user_role` VALUES ('1500904b-da21-4364-9224-6708e8481722', 'admin', '745fcee9-74d3-4063-a95d-8fe416b98ce5');
INSERT INTO `sys_user_role` VALUES ('157560de-806f-4165-b7ea-d48505ec56c9', '8887538c-9e0f-4564-bbce-b32ef62f9a02', '1');
INSERT INTO `sys_user_role` VALUES ('2022578e-55d5-4ee0-bb30-85136e35cc47', '57f1b8da-bf85-4e85-8945-0bf709ac58b9', '745fcee9-74d3-4063-a95d-8fe416b98ce5');
INSERT INTO `sys_user_role` VALUES ('2a300973-3036-4bd2-97cb-f025ed008e21', '8dc44685-519a-483c-a39a-f8cbcfad9653', '745fcee9-74d3-4063-a95d-8fe416b98ce5');
INSERT INTO `sys_user_role` VALUES ('30be6eed-a2be-4dde-88f2-7ade1f3be3dd', '0ce1e783-962b-48e5-a988-6461883cfa8b', '1');
INSERT INTO `sys_user_role` VALUES ('35ab71cd-aeb0-4c96-843b-870800bcb3b1', 'dffeac25-a1d3-44e5-b5a9-98059e9a08c6', '745fcee9-74d3-4063-a95d-8fe416b98ce5');
INSERT INTO `sys_user_role` VALUES ('3d7ebf1a-a9d8-4653-876a-8d636ce735d8', '1d6befdd-9e90-4f54-8d1f-447d479606f9', '745fcee9-74d3-4063-a95d-8fe416b98ce5');
INSERT INTO `sys_user_role` VALUES ('6d607a3b-5d56-4c37-b737-888924ff97cf', '03ec73e9-51c4-4b05-a822-88dfc33e30fe', '1');
INSERT INTO `sys_user_role` VALUES ('7ba24658-4a8f-4c54-9e99-62f60623a6c7', 'd06fc615-371e-433c-9982-383dfddad7fb', '1');
INSERT INTO `sys_user_role` VALUES ('89f631a7-8799-44a0-918d-83b5536a4642', 'd06fc615-371e-433c-9982-383dfddad7fb', '745fcee9-74d3-4063-a95d-8fe416b98ce5');
INSERT INTO `sys_user_role` VALUES ('d32aa568-de0f-42b9-93c7-b32c1bf31951', 'admin', '1');

SET FOREIGN_KEY_CHECKS = 1;
