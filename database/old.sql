/*
 Navicat Premium Data Transfer

 Source Server         : 青藤门下
 Source Server Type    : MySQL
 Source Server Version : 50740

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 02/06/2023 17:34:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for old_dept
-- ----------------------------
DROP TABLE IF EXISTS `old_dept`;
CREATE TABLE `old_dept`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
                             `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '父部门id',
                             `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '祖级列表',
                             `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
                             `order_num` int(4) NOT NULL DEFAULT 0 COMMENT '显示顺序',
                             `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
                             `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
                             `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `dept_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '部门状态（0停用 1正常）',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                             `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'old' COMMENT '创建用户',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                             `update_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_dept
-- ----------------------------
INSERT INTO `old_dept` VALUES (100, 0, '0', '总公司', 0, 'old', '130', NULL, 1, '2023-04-11 15:33:40', -1, 'root', '2023-04-11 15:34:32', NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, 'old', '130', 'old@qq.com', 1, '2023-04-11 15:37:34', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, 'old', '130', 'old@qq.com', 1, '2023-04-11 15:37:35', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, 'old', '130', 'old@qq.com', 1, '2023-04-11 15:37:35', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, 'old', '130', 'old@qq.com', 1, '2023-04-11 15:37:35', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, 'old', '130', 'old@qq.com', 1, '2023-04-11 15:37:36', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, 'old', '130', 'old@qq.com', 0, '2023-04-11 15:37:36', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, 'old', '130', 'old@qq.com', 1, '2023-04-11 15:37:37', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, 'old', '130', 'old@qq.com', 0, '2023-04-11 15:37:37', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, 'old', '130', 'old@qq.com', 1, '2023-04-11 15:37:38', -1, 'root', NULL, NULL, NULL, 0);
INSERT INTO `old_dept` VALUES (110, 101, '0,100,101', '研发部门2', 1, '123', '13013001300', 'xm@qq.com', 0, '2023-05-15 17:14:18', 1, 'old', '2023-05-19 20:56:17', 1, 'old', 1);
INSERT INTO `old_dept` VALUES (111, 100, '0,100', '123', 123, '123', '13013001300', 'xm@qq.com', 1, '2023-05-15 17:44:02', 1, 'old', '2023-05-29 11:51:01', 1, 'old', 1);
INSERT INTO `old_dept` VALUES (112, 111, '0,100,111', '12312312', 1, '12312311123', '13013001300', 'xm@qq.com', 1, '2023-05-19 11:52:08', 1, 'old', '2023-05-19 20:56:09', 1, 'old', 1);
INSERT INTO `old_dept` VALUES (113, 112, '0,100,111,112', '555', 1, '5555', '13013001300', 'xm@qq.com', 1, '2023-05-19 11:56:39', 1, 'old', '2023-05-19 20:56:00', 1, 'old', 1);

-- ----------------------------
-- Table structure for old_file
-- ----------------------------
DROP TABLE IF EXISTS `old_file`;
CREATE TABLE `old_file`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `original_file_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原文件名',
                             `file_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型',
                             `file_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
                             `file_server` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件服务器地址',
                             `file_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件路径',
                             `x80` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '80%的文件（压缩了20%）压缩后的文件路径名：原文件名-x80，下面的以此类推',
                             `x50` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '50%的文件（压缩了50%）',
                             `x20` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '20%的文件（压缩了80%）',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                             `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'old' COMMENT '创建用户',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                             `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_file
-- ----------------------------
INSERT INTO `old_file` VALUES (1, 'Snipaste_2022-08-13_12-44-44.jpg', 'jpg', '2', 'http://localhost:9004', '/oldUploadFile/file/2', NULL, NULL, NULL, '2023-05-26 17:07:12', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (2, 'Snipaste_2022-08-13_12-44-44.jpg', 'jpg', '2023-05-26_17-15-04.jpg', '', 'D:\\java\\Demo\\gitee\\old-platform\\temp\\upload;null', NULL, NULL, NULL, '2023-05-26 17:13:25', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (3, 'Snipaste_2022-08-13_12-44-44.jpg', 'jpg', '2023-05-26_18-27-06.jpg', 'http://localhost:9004', 'D:\\java\\Demo\\gitee\\old-platform\\temp\\upload/null', NULL, NULL, NULL, '2023-05-26 18:25:26', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (4, 'Snipaste_2022-08-13_12-44-44.jpg', 'jpg', '2023-05-26_18-30-49-686.jpg', 'http://localhost:9004', '/uploadFile/null', NULL, NULL, NULL, '2023-05-26 18:29:09', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (5, 'Snipaste_2022-08-13_12-44-44.jpg', 'jpg', '2023-05-26_18-31-28-953.jpg', 'http://localhost:9004', '/uploadFile/2023-05-26_18-31-28-953.jpg', NULL, NULL, NULL, '2023-05-26 18:29:49', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (6, 'Snipaste_2022-08-13_12-44-44.jpg', 'jpg', '4', 'http://localhost:9004', '/oldUploadFile/file/4', NULL, NULL, NULL, '2023-06-02 10:20:30', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (7, 'Snipaste_2022-08-13_12-44-44.jpg', 'jpg', '5', 'http://localhost:9004', '/oldUploadFile/file/5', NULL, NULL, NULL, '2023-06-02 10:22:14', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (8, 'Snipaste_2022-08-13_12-44-44.jpg', 'jpg', '6', 'http://localhost:9004', '/oldUploadFile/file/6', NULL, NULL, NULL, '2023-06-02 10:25:08', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (9, 't.txt', 'txt', '7', 'http://localhost:9004', '/oldUploadFile/file/7', NULL, NULL, NULL, '2023-06-02 10:26:52', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (10, 't.txt', 'txt', '8', 'http://localhost:9004', '/oldUploadFile/file/8', NULL, NULL, NULL, '2023-06-02 10:28:21', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (11, 'Snipaste_2022-08-13_15-58-20.jpg', 'jpg', '9', 'http://localhost:9004', '/oldUploadFile/file/9', NULL, NULL, NULL, '2023-06-02 10:28:36', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (12, 'Snipaste_2022-08-13_15-58-20.jpg', 'jpg', '10', 'http://localhost:9004', '/oldUploadFile/file/10', NULL, NULL, NULL, '2023-06-02 10:31:03', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (13, 't.txt', 'txt', '11', 'http://localhost:9004', '/oldUploadFile/file/11', NULL, NULL, NULL, '2023-06-02 10:31:12', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (14, 'Snipaste_2022-08-13_15-58-20.jpg', 'jpg', '12', 'http://localhost:9004', '/oldUploadFile/file/12', NULL, NULL, NULL, '2023-06-02 10:40:20', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (15, 't.txt', 'txt', '13', 'http://localhost:9004', '/oldUploadFile/file/13', NULL, NULL, NULL, '2023-06-02 10:40:28', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (16, 'Snipaste_2022-08-13_15-58-20.jpg', 'jpg', '14', 'http://localhost:9004', '/oldUploadFile/file/14', NULL, NULL, NULL, '2023-06-02 10:42:13', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_file` VALUES (17, 't.txt', 'txt', '15', 'http://localhost:9004', '/oldUploadFile/file/15', NULL, NULL, NULL, '2023-06-02 10:42:26', 1, 'old', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for old_file_rel
-- ----------------------------
DROP TABLE IF EXISTS `old_file_rel`;
CREATE TABLE `old_file_rel`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `file_id` int(11) NOT NULL COMMENT '用户 id',
                                 `rel_id` int(11) NOT NULL COMMENT '引用 id',
                                 `rel_type` smallint(3) NOT NULL COMMENT '引用类型，以区分是哪个表',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
                                 `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户',
                                 `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                                 `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                                 `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 INDEX `rel_index`(`rel_id`, `rel_type`, `file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件引用表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_file_rel
-- ----------------------------

-- ----------------------------
-- Table structure for old_menu
-- ----------------------------
DROP TABLE IF EXISTS `old_menu`;
CREATE TABLE `old_menu`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                             `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
                             `parent_id` int(11) NULL DEFAULT 0 COMMENT '父菜单ID',
                             `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
                             `menu_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
                             `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
                             `menu_query` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由参数',
                             `is_frame` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为外链（0是 1否）',
                             `is_cache` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
                             `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
                             `menu_visible` smallint(1) NOT NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
                             `menu_status` smallint(1) NOT NULL DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
                             `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
                             `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
                             `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                             `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `update_user_id` int(11) NULL DEFAULT NULL COMMENT '修改用户id',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2051 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_menu
-- ----------------------------
INSERT INTO `old_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 0, 1, 'M', 0, 0, '', 'system', 'admin', '2023-04-02 04:33:16', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, '', 0, 1, 'M', 0, 0, '', 'monitor', 'admin', '2023-04-02 04:33:17', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, '', 0, 1, 'M', 0, 0, '', 'tool', 'admin', '2023-04-02 04:33:19', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 0, 1, 'C', 0, 0, 'system:user:list', 'user', 'admin', '2023-04-02 04:33:23', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 0, 1, 'C', 0, 0, 'system:role:list', 'peoples', 'admin', '2023-04-02 04:33:25', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 0, 1, 'C', 0, 0, 'system:menu:list', 'tree-table', 'admin', '2023-04-02 04:33:28', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 0, 1, 'C', 0, 0, 'system:dept:list', 'tree', 'admin', '2023-04-02 04:33:30', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 0, 1, 'C', 0, 0, 'system:post:list', 'post', 'admin', '2023-04-02 04:33:32', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 0, 1, 'C', 0, 0, 'system:notice:list', 'message', 'admin', '2023-04-02 04:33:38', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 0, 1, 'M', 0, 0, '', 'log', 'admin', '2023-04-02 04:33:39', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 0, 1, 'C', 0, 0, 'monitor:online:list', 'online', 'admin', '2023-04-02 04:33:41', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', 0, 1, 'C', 0, 0, 'tool:build:list', 'build', 'admin', '2023-04-02 04:33:52', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 0, 1, 'C', 0, 0, 'monitor:logininfor:list', 'logininfor', 'admin', '2023-04-02 04:33:59', -1, 'old', '2023-05-20 13:51:45', 1, 0);
INSERT INTO `old_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', 0, 1, 'F', 0, 0, 'system:user:query', '#', 'admin', '2023-04-02 04:34:01', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', 0, 1, 'F', 0, 0, 'system:user:add', '#', 'admin', '2023-04-02 04:34:03', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', 0, 1, 'F', 0, 0, 'system:user:edit', '#', 'admin', '2023-04-02 04:34:04', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', 0, 1, 'F', 0, 0, 'system:user:remove', '#', 'admin', '2023-04-02 04:34:06', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', 0, 1, 'F', 0, 0, 'system:user:export', '#', 'admin', '2023-04-02 04:34:08', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', 0, 1, 'F', 0, 0, 'system:user:import', '#', 'admin', '2023-04-02 04:34:10', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', 0, 1, 'F', 0, 0, 'system:user:resetPwd', '#', 'admin', '2023-04-02 04:34:11', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', 0, 1, 'F', 0, 0, 'system:role:query', '#', 'admin', '2023-04-02 04:34:13', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', 0, 1, 'F', 0, 0, 'system:role:add', '#', 'admin', '2023-04-02 04:34:15', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', 0, 1, 'F', 0, 0, 'system:role:edit', '#', 'admin', '2023-04-02 04:34:17', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', 0, 1, 'F', 0, 0, 'system:role:remove', '#', 'admin', '2023-04-02 04:34:18', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', 0, 1, 'F', 0, 0, 'system:role:export', '#', 'admin', '2023-04-02 04:34:20', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', 0, 1, 'F', 0, 0, 'system:menu:query', '#', 'admin', '2023-04-02 04:34:22', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', 0, 1, 'F', 0, 0, 'system:menu:add', '#', 'admin', '2023-04-02 04:34:24', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', 0, 1, 'F', 0, 0, 'system:menu:edit', '#', 'admin', '2023-04-02 04:34:26', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', 0, 1, 'F', 0, 0, 'system:menu:remove', '#', 'admin', '2023-04-02 04:34:28', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1016, '部门查询', 103, 1, '', '', '', 0, 1, 'F', 0, 0, 'system:dept:query', '#', 'admin', '2023-04-02 04:34:29', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1017, '部门新增', 103, 2, '', '', '', 0, 1, 'F', 0, 0, 'system:dept:add', '#', 'admin', '2023-04-02 04:34:31', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1018, '部门修改', 103, 3, '', '', '', 0, 1, 'F', 0, 0, 'system:dept:edit', '#', 'admin', '2023-04-02 04:34:33', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1019, '部门删除', 103, 4, '', '', '', 0, 1, 'F', 0, 0, 'system:dept:remove', '#', 'admin', '2023-04-02 04:34:35', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', 0, 1, 'F', 0, 0, 'system:post:query', '#', 'admin', '2023-04-02 04:34:37', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', 0, 1, 'F', 0, 0, 'system:post:add', '#', 'admin', '2023-04-02 04:34:38', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', 0, 1, 'F', 0, 0, 'system:post:edit', '#', 'admin', '2023-04-02 04:34:40', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', 0, 1, 'F', 0, 0, 'system:post:remove', '#', 'admin', '2023-04-02 04:34:42', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', 0, 1, 'F', 0, 0, 'system:post:export', '#', 'admin', '2023-04-02 04:34:44', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', 0, 1, 'F', 0, 0, 'system:notice:query', '#', 'admin', '2023-04-02 04:35:04', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', 0, 1, 'F', 0, 0, 'system:notice:add', '#', 'admin', '2023-04-02 04:35:05', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', 0, 1, 'F', 0, 0, 'system:notice:edit', '#', 'admin', '2023-04-02 04:35:07', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', 0, 1, 'F', 0, 0, 'system:notice:remove', '#', 'admin', '2023-04-02 04:35:09', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', 0, 1, 'F', 0, 0, 'monitor:logininfor:query', '#', 'admin', '2023-04-02 04:35:16', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', 0, 1, 'F', 0, 0, 'monitor:logininfor:remove', '#', 'admin', '2023-04-02 04:35:18', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', 0, 1, 'F', 0, 0, 'monitor:logininfor:export', '#', 'admin', '2023-04-02 04:35:20', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', 0, 1, 'F', 0, 0, 'monitor:logininfor:unlock', '#', 'admin', '2023-04-02 04:35:22', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', 0, 1, 'F', 0, 0, 'monitor:online:query', '#', 'admin', '2023-04-02 04:35:23', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', 0, 1, 'F', 0, 0, 'monitor:online:batchLogout', '#', 'admin', '2023-04-02 04:35:25', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', 0, 1, 'F', 0, 0, 'monitor:online:forceLogout', '#', 'admin', '2023-04-02 04:35:27', -1, '', NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2005, '分配角色', 1, 1, '/system/user-auth/role/:userId(\\d+)', 'system/user/authRole', NULL, 0, 0, 'C', 1, 0, 'system:user:edit', '#', 'old', '2023-05-20 13:19:39', 1, 'old', '2023-05-20 13:25:17', 1, 0);
INSERT INTO `old_menu` VALUES (2006, '分配用户', 1, 3, '/system/role-auth/user/:roleId(\\d+)', 'system/role/authUser', NULL, 0, 1, 'C', 1, 0, 'system:role:edit', '#', 'old', '2023-05-20 13:30:20', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2007, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', 0, 0, 'monitor:cache:list', 'redis', 'old', '2023-05-20 15:44:11', 1, 'old', '2023-05-20 16:06:42', 1, 0);
INSERT INTO `old_menu` VALUES (2008, '服务监控', 2, 2, 'http://localhost:9003/wallboard', NULL, NULL, 1, 1, 'C', 0, 0, NULL, 'server', 'old', '2023-05-20 15:46:42', 1, 'old', '2023-05-20 16:06:57', 1, 0);
INSERT INTO `old_menu` VALUES (2039, '测试生成管理', 1, 1, 'testGen', 'user/testGen/index', '', 0, 1, 'C', 0, 0, 'user:testGen:list', '#', 'old', '2023-06-02 09:48:05', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2040, '测试生成查询', 2039, 1, 'testGen', '', '', 0, 1, 'F', 0, 0, 'user:testGen:query', '#', 'old', '2023-06-02 09:48:08', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2041, '测试生成新增', 2039, 2, 'testGen', '', '', 0, 1, 'F', 0, 0, 'user:testGen:add', '#', 'old', '2023-06-02 09:48:09', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2042, '测试生成修改', 2039, 3, 'testGen', '', '', 0, 1, 'F', 0, 0, 'user:testGen:edit', '#', 'old', '2023-06-02 09:48:10', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2043, '测试生成删除', 2039, 4, 'testGen', '', '', 0, 1, 'F', 0, 0, 'user:testGen:remove', '#', 'old', '2023-06-02 09:48:11', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2044, '测试生成导出', 2039, 5, 'testGen', '', '', 0, 1, 'F', 0, 0, 'user:testGen:export', '#', 'old', '2023-06-02 09:48:12', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2045, '测试树生成管理', 1, 1, 'testTreeGen', 'user/testTreeGen/index', '', 0, 1, 'C', 0, 0, 'user:testTreeGen:list', '#', 'old', '2023-06-02 10:55:55', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2046, '测试树生成查询', 2045, 1, 'testTreeGen', '', '', 0, 1, 'F', 0, 0, 'user:testTreeGen:query', '#', 'old', '2023-06-02 10:55:57', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2047, '测试树生成新增', 2045, 2, 'testTreeGen', '', '', 0, 1, 'F', 0, 0, 'user:testTreeGen:add', '#', 'old', '2023-06-02 10:55:57', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2048, '测试树生成修改', 2045, 3, 'testTreeGen', '', '', 0, 1, 'F', 0, 0, 'user:testTreeGen:edit', '#', 'old', '2023-06-02 10:55:58', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2049, '测试树生成删除', 2045, 4, 'testTreeGen', '', '', 0, 1, 'F', 0, 0, 'user:testTreeGen:remove', '#', 'old', '2023-06-02 10:55:59', 1, NULL, NULL, NULL, 0);
INSERT INTO `old_menu` VALUES (2050, '测试树生成导出', 2045, 5, 'testTreeGen', '', '', 0, 1, 'F', 0, 0, 'user:testTreeGen:export', '#', 'old', '2023-06-02 10:55:59', 1, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for old_menu_path
-- ----------------------------
DROP TABLE IF EXISTS `old_menu_path`;
CREATE TABLE `old_menu_path`  (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `ancestor` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '祖先节点',
                                  `descendant` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '后代节点',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                                  `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'old' COMMENT '创建用户',
                                  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                                  `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新用户',
                                  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单层级关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_menu_path
-- ----------------------------

-- ----------------------------
-- Table structure for old_notice
-- ----------------------------
DROP TABLE IF EXISTS `old_notice`;
CREATE TABLE `old_notice`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
                               `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
                               `notice_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告类型（通知 公告）',
                               `notice_content` longblob NULL COMMENT '公告内容',
                               `notice_status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'NORMAL' COMMENT '公告状态（0正常 1关闭）',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                               `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'old' COMMENT '创建用户',
                               `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                               `update_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                               `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_notice
-- ----------------------------
INSERT INTO `old_notice` VALUES (1, '123123123', 'NOTICE', 0x3C703E66617364666166206A6B68686B6A686B6A686B683C2F703E, 'CLOSE', '2023-05-20 11:50:28', 1, 'old', '2023-05-20 11:58:41', 1, 'old', 1);
INSERT INTO `old_notice` VALUES (2, 'sadfas', 'NOTICE', 0x3C703E61736466617364663C2F703E, 'NORMAL', '2023-05-20 12:32:01', 1, 'old', '2023-05-20 12:40:08', 1, 'old', 1);
INSERT INTO `old_notice` VALUES (3, 'asdfasdfasdf', 'ANNOUNCEMENT', 0x3C703E61736466617364663C2F703E, 'CLOSE', '2023-05-20 12:32:09', 1, 'old', '2023-05-20 12:40:08', 1, 'old', 1);
INSERT INTO `old_notice` VALUES (4, 'cvxzv', 'NOTICE', 0x3C703E7A7863767A7863763C2F703E, 'NORMAL', '2023-05-20 12:32:16', 1, 'old', '2023-05-20 12:40:16', 1, 'old', 1);
INSERT INTO `old_notice` VALUES (5, 'asfasfd', 'NOTICE', 0x3C703E61736466617364663C2F703E, 'NORMAL', '2023-05-20 12:40:28', 1, 'old', '2023-05-20 12:40:36', 1, 'old', 1);
INSERT INTO `old_notice` VALUES (6, 'asfasfd', 'NOTICE', 0x3C703E61736466617364663C2F703E, 'NORMAL', '2023-05-20 12:40:29', 1, 'old', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for old_order
-- ----------------------------
DROP TABLE IF EXISTS `old_order`;
CREATE TABLE `old_order`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `user_type` int(11) NULL DEFAULT NULL COMMENT '用户类型',
                              `order_status` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '订单状态',
                              `ext_json` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '扩展 json',
                              `price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '订单价格',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                              `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'old' COMMENT '创建用户',
                              `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                              `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新用户',
                              `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_order
-- ----------------------------

-- ----------------------------
-- Table structure for old_pay
-- ----------------------------
DROP TABLE IF EXISTS `old_pay`;
CREATE TABLE `old_pay`  (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `pay_id` int(11) NOT NULL COMMENT '支付id',
                            `pay_amount` decimal(10, 2) NOT NULL COMMENT '支付金额',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                            `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'old' COMMENT '创建用户',
                            `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                            `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新用户',
                            `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_pay
-- ----------------------------

-- ----------------------------
-- Table structure for old_post
-- ----------------------------
DROP TABLE IF EXISTS `old_post`;
CREATE TABLE `old_post`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
                             `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
                             `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
                             `post_sort` int(4) NOT NULL COMMENT '显示顺序',
                             `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `post_status` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
                             `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                             `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'old' COMMENT '创建用户',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                             `update_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_post
-- ----------------------------
INSERT INTO `old_post` VALUES (1, 'ceo', '董事长', 1, NULL, '2023-04-13 15:10:43', 'ENABLE', 1, 'old', '2023-05-20 10:19:41', 1, 'old', 0);
INSERT INTO `old_post` VALUES (2, 'se', '项目经理', 2, NULL, '2023-04-13 15:10:45', 'ENABLE', 1, 'old', '2023-05-20 10:19:41', 1, 'old', 0);
INSERT INTO `old_post` VALUES (3, 'hr', '人力资源', 3, NULL, '2023-04-13 15:10:47', 'ENABLE', 1, 'old', '2023-05-20 10:19:41', 1, 'old', 0);
INSERT INTO `old_post` VALUES (4, 'user', '普通员工', 4, NULL, '2023-04-13 15:10:49', 'ENABLE', 1, 'old', '2023-05-20 10:19:41', 1, 'old', 0);
INSERT INTO `old_post` VALUES (5, 'adsf ', '324234', 1, NULL, '2023-05-20 10:31:55', 'ENABLE', 1, 'old', '2023-05-20 10:42:12', 1, 'old', 1);
INSERT INTO `old_post` VALUES (6, '123123', '12312123123', 7, '啊打发法', '2023-05-20 10:39:42', 'ENABLE', 1, 'old', '2023-05-20 10:42:12', 1, 'old', 1);
INSERT INTO `old_post` VALUES (7, '花木成畦手自栽', '茜基材', 2, NULL, '2023-05-20 10:42:54', 'ENABLE', 1, 'old', '2023-05-20 10:42:59', 1, 'old', 1);

-- ----------------------------
-- Table structure for old_role
-- ----------------------------
DROP TABLE IF EXISTS `old_role`;
CREATE TABLE `old_role`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `role_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名',
                             `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色权限字符串',
                             `role_sort` int(4) NOT NULL COMMENT '显示顺序',
                             `data_scope` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'ALL' COMMENT '数据范围（ALL：全部数据权限 CUSTOMIZE：自定数据权限 CURRENT_DEPT：本部门数据权限 CURRENT_DEPT_AND_BELOW：本部门及以下数据权限 INDIVIDUAL：个人）',
                             `menu_check_strictly` tinyint(1) NOT NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
                             `dept_check_strictly` tinyint(1) NOT NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
                             `role_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '角色状态 false 停用，true 使用',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                             `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'old' COMMENT '创建用户',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                             `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新用户',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_role
-- ----------------------------
INSERT INTO `old_role` VALUES (1, 'admin', '*', 1, 'ALL', 1, 1, 1, '2023-04-02 14:02:20', 1, 'old', '2023-06-02 10:58:14', 1, 'old', 0);
INSERT INTO `old_role` VALUES (2, '普通角色', '123123123', 2, 'CURRENT_DEPT', 1, 1, 1, '2023-04-13 15:58:21', 1, 'old', '2023-05-14 16:09:42', 1, 'old', 0);

-- ----------------------------
-- Table structure for old_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `old_role_dept`;
CREATE TABLE `old_role_dept`  (
                                  `role_id` int(11) NOT NULL COMMENT '角色ID',
                                  `dept_id` int(11) NOT NULL COMMENT '部门ID',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                                  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'old' COMMENT '创建用户',
                                  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for old_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `old_role_menu_rel`;
CREATE TABLE `old_role_menu_rel`  (
                                      `role_id` int(10) NOT NULL COMMENT '角色 id',
                                      `menu_id` int(10) NOT NULL COMMENT '菜单 id',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                                      `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'old' COMMENT '创建用户',
                                      PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_role_menu_rel
-- ----------------------------
INSERT INTO `old_role_menu_rel` VALUES (1, 1, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 3, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 100, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 101, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 102, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 103, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 104, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 107, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 108, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 109, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 115, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 501, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1000, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1001, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1002, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1003, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1004, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1005, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1006, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1007, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1008, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1009, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1010, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1011, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1012, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1013, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1014, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1015, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1016, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1017, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1018, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1019, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1020, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1021, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1022, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1023, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1024, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1035, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1036, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1037, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1038, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1042, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1043, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1044, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1045, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1046, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1047, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 1048, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2005, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2006, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2007, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2008, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2039, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2040, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2041, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2042, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2043, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2044, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2045, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2046, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2047, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2048, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2049, '2023-06-02 10:58:14', 1, 'old');
INSERT INTO `old_role_menu_rel` VALUES (1, 2050, '2023-06-02 10:58:14', 1, 'old');

-- ----------------------------
-- Table structure for old_upload_file
-- ----------------------------
DROP TABLE IF EXISTS `old_upload_file`;
CREATE TABLE `old_upload_file`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `db_file` mediumblob NOT NULL COMMENT '文件',
                                    `file_type` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件类型',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_upload_file
-- ----------------------------

-- ----------------------------
-- Table structure for old_user
-- ----------------------------
DROP TABLE IF EXISTS `old_user`;
CREATE TABLE `old_user`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
                             `user_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
                             `user_status` smallint(6) NOT NULL DEFAULT 0 COMMENT '用户状态：0：启用，1：禁用',
                             `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户昵称',
                             `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
                             `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户邮箱',
                             `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '手机号码',
                             `sex` smallint(6) NOT NULL DEFAULT 2 COMMENT '用户性别（0男 1女 2未知）',
                             `password` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
                             `profile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
                             `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后登录IP',
                             `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                             `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'old' COMMENT '创建用户',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                             `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新用户',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_user
-- ----------------------------
INSERT INTO `old_user` VALUES (1, 101, 'old', 0, 'old', '00', '1854@163.com', '13013001300', 0, 'old', '/oldUploadFile/file/3', NULL, NULL, '2023-04-11 15:02:49', -1, 'root', '2023-05-29 13:23:01', 1, 'old', 0);
INSERT INTO `old_user` VALUES (5, 101, 'test', 0, 'test', '00', '1854@163.com', '13013001300', 1, '123123', NULL, NULL, NULL, '2023-04-11 15:02:49', -1, 'root', '2023-04-17 15:34:38', 1, 'old', 0);
INSERT INTO `old_user` VALUES (6, 103, 'add', 0, 'add', '00', '130@163.com', '13013001300', 0, 'admin123', NULL, NULL, NULL, '2023-04-14 15:52:32', 1, 'old', '2023-04-20 14:15:54', 1, 'old', 0);
INSERT INTO `old_user` VALUES (8, 100, '132', 0, '132', '00', '132@163.com', '13213221322', 0, '132132', NULL, NULL, NULL, '2023-04-19 10:26:24', 1, 'old', '2023-04-19 11:33:21', 1, 'old', 1);

-- ----------------------------
-- Table structure for old_user_post
-- ----------------------------
DROP TABLE IF EXISTS `old_user_post`;
CREATE TABLE `old_user_post`  (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `user_id` int(11) NOT NULL COMMENT '用户ID',
                                  `post_id` int(11) NOT NULL COMMENT '岗位ID',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                                  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'old' COMMENT '创建用户',
                                  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                                  `update_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                                  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_user_post
-- ----------------------------
INSERT INTO `old_user_post` VALUES (1, 5, 1, '2023-04-14 15:26:40', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_user_post` VALUES (2, 5, 2, '2023-04-14 15:26:40', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `old_user_post` VALUES (3, 6, 1, '2023-04-14 15:52:32', 1, 'old', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for old_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `old_user_role_rel`;
CREATE TABLE `old_user_role_rel`  (
                                      `user_id` int(11) NOT NULL COMMENT '用户 id',
                                      `role_id` int(11) NOT NULL COMMENT '角色 id',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `create_user_id` int(11) NOT NULL DEFAULT 1 COMMENT '创建用户id',
                                      `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'old' COMMENT '创建用户',
                                      PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of old_user_role_rel
-- ----------------------------
INSERT INTO `old_user_role_rel` VALUES (1, 1, '2023-04-02 14:16:55', 1, 'old');
INSERT INTO `old_user_role_rel` VALUES (5, 2, '2023-04-13 15:59:44', 1, 'old');
INSERT INTO `old_user_role_rel` VALUES (6, 2, '2023-05-14 16:05:07', 1, 'old');

-- ----------------------------
-- Table structure for test_gen
-- ----------------------------
DROP TABLE IF EXISTS `test_gen`;
CREATE TABLE `test_gen`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `input` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入框',
                             `textarea` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文本域',
                             `pull_down` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下拉',
                             `checkbox` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复选',
                             `radio` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单选',
                             `time_range` date NULL DEFAULT NULL COMMENT '时间',
                             `image` mediumblob NULL COMMENT '图片',
                             `upload_file` mediumblob NULL COMMENT '文件',
                             `rich_text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '富文本',
                             `not_equal_to` int(11) NULL DEFAULT NULL COMMENT '不等于',
                             `greater_than` int(11) NULL DEFAULT NULL COMMENT '大于',
                             `less_than` int(11) NULL DEFAULT NULL COMMENT '小于',
                             `str_like` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模糊',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
                             `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                             `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                             `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试生成' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_gen
-- ----------------------------
INSERT INTO `test_gen` VALUES (1, '123', '123', 'TWO', NULL, 'ONE', '2023-05-15', NULL, NULL, NULL, NULL, NULL, NULL, '123', '2023-05-28 15:14:17', 1, 'old', '2023-05-28 15:15:30', 1, 'old', 1);
INSERT INTO `test_gen` VALUES (2, '123', '123', 'TWO', NULL, 'TWO', '2023-05-22', NULL, NULL, '<p>123123</p>', 123, 123, 123, '123', '2023-05-28 15:15:43', 1, 'old', '2023-05-28 15:24:24', 1, 'old', 0);
INSERT INTO `test_gen` VALUES (3, '123', '123', 'TWO', 'TWO,ONE', 'ONE', '2023-05-22', NULL, NULL, '<p>1231231313</p>', 123, 13, 123, '123', '2023-05-28 16:14:40', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `test_gen` VALUES (4, '234', '234', 'TWO', 'TWO,ONE', 'ONE', '2023-05-22', NULL, NULL, NULL, 123, 12, 12, '123', '2023-05-28 16:28:37', 1, 'old', '2023-06-02 10:33:30', 1, 'old', 1);
INSERT INTO `test_gen` VALUES (5, '上传', '上传', 'TWO', 'ONE,TWO', 'ONE', '2023-06-06', 0x687474703A2F2F6C6F63616C686F73743A393030342F6F6C6455706C6F616446696C652F66696C652F3130, 0x687474703A2F2F6C6F63616C686F73743A393030342F6F6C6455706C6F616446696C652F66696C652F3131, '<p>上传</p>', 1, 1, 1, '上传', '2023-06-02 10:33:04', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `test_gen` VALUES (6, '上传', '上传', 'TWO', 'TWO', 'TWO', '2023-06-13', 0x2F6F6C6455706C6F616446696C652F66696C652F3134, '', '<p>上传</p>', 1, 1, 1, '1', '2023-06-02 10:44:19', 1, 'old', '2023-06-02 10:44:54', 1, 'old', 0);

-- ----------------------------
-- Table structure for test_tree_gen
-- ----------------------------
DROP TABLE IF EXISTS `test_tree_gen`;
CREATE TABLE `test_tree_gen`  (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `parent_id` int(11) NOT NULL COMMENT '父id',
                                  `tree_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                                  `input` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入框',
                                  `textarea` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文本域',
                                  `pull_down` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下拉',
                                  `checkbox` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复选',
                                  `radio` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单选',
                                  `time_range` date NULL DEFAULT NULL COMMENT '时间',
                                  `image` mediumblob NULL COMMENT '图片',
                                  `upload_file` mediumblob NULL COMMENT '文件',
                                  `rich_text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '富文本',
                                  `not_equal_to` int(11) NULL DEFAULT NULL COMMENT '不等于',
                                  `greater_than` int(11) NULL DEFAULT NULL COMMENT '大于',
                                  `less_than` int(11) NULL DEFAULT NULL COMMENT '小于',
                                  `str_like` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模糊',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
                                  `create_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户',
                                  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户id',
                                  `update_user` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
                                  `delete_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标识',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试树生成' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_tree_gen
-- ----------------------------
INSERT INTO `test_tree_gen` VALUES (1, 0, '123', '12', '123', 'ONE', 'ONE,TWO', 'TWO', '2023-05-22', NULL, NULL, '<p>123</p>', 1, 1, 1, '123', '2023-05-28 21:54:48', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `test_tree_gen` VALUES (2, 1, '2', '2', '2', 'TWO', 'TWO', 'TWO', '2023-05-22', NULL, NULL, '<p>2</p>', 2, 2, 2, '2', '2023-05-28 21:55:04', 1, 'old', NULL, NULL, NULL, 0);
INSERT INTO `test_tree_gen` VALUES (3, 2, '3', '3', '3', 'ONE', 'ONE', 'ONE', '2023-05-23', NULL, NULL, '<p>3</p>', 3, 3, 3, '3', '2023-05-29 10:31:37', 1, 'old', '2023-05-29 10:38:44', 1, 'old', 1);
INSERT INTO `test_tree_gen` VALUES (4, 0, '234234', '2342', '234', 'ONE', 'ONE,TWO', 'TWO', '2023-06-12', NULL, NULL, '<p>234234</p>', 234, 234, 234, '234', '2023-06-02 10:59:49', 1, 'old', '2023-06-02 10:59:53', 1, 'old', 1);

SET FOREIGN_KEY_CHECKS = 1;
