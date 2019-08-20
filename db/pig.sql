/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost
 Source Database       : pig

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : utf-8

 Date: 02/01/2019 20:51:15 PM
*/
DROP DATABASE IF EXISTS `pig`;
CREATE DATABASE  `pig` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

USE `pig`;
-- ----------------------------
--  Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- ----------------------------
--  Records of `sys_dept`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES ('1', '山东农信', null, '2018-01-22 19:00:23', '2018-09-13 01:46:29', '0', '0'), ('2', '沙县国际', null, '2018-01-22 19:00:38', '2018-09-13 01:46:30', '0', '0'), ('3', '潍坊农信', null, '2018-01-22 19:00:44', '2018-09-13 01:46:31', '0', '1'), ('4', '高新农信', null, '2018-01-22 19:00:52', '2018-10-06 10:41:52', '0', '3'), ('5', '院校农信', null, '2018-01-22 19:00:57', '2018-10-06 10:42:51', '0', '4'), ('6', '潍院农信', null, '2018-01-22 19:01:06', '2019-01-09 10:58:18', '1', '5'), ('7', '山东沙县', null, '2018-01-22 19:01:57', '2018-09-13 01:46:42', '0', '2'), ('8', '潍坊沙县', null, '2018-01-22 19:02:03', '2018-09-13 01:46:43', '0', '7'), ('9', '高新沙县', null, '2018-01-22 19:02:14', '2018-09-13 01:46:44', '1', '8'),  ('10', '院校沙县', null, '2018-12-10 21:19:26', null, '0', '8');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dept_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_relation`;
CREATE TABLE `sys_dept_relation` (
  `ancestor` int(11) NOT NULL COMMENT '祖先节点',
  `descendant` int(11) NOT NULL COMMENT '后代节点',
  PRIMARY KEY (`ancestor`,`descendant`),
  KEY `idx1` (`ancestor`),
  KEY `idx2` (`descendant`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='部门关系表';

-- ----------------------------
--  Records of `sys_dept_relation`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept_relation` VALUES ('1', '1'), ('1', '3'), ('1', '4'), ('1', '5'), ('2', '2'), ('2', '7'), ('2', '8'), ('2', '11'), ('3', '3'), ('3', '4'), ('3', '5'), ('4', '4'), ('4', '5'), ('5', '5'), ('7', '7'), ('7', '8'), ('7', '11'), ('8', '8'), ('8', '11'), ('10', '10'), ('11', '11');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` int(10) NOT NULL COMMENT '排序（升序）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- ----------------------------
--  Records of `sys_dict`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES ('1', '9', '异常', 'log_type', '日志异常', '1', '2018-07-09 06:16:14', '2018-11-24 07:25:11', '日志异常', '0'), ('2', '0', '正常', 'log_type', '正常', '0', '2018-07-09 06:15:40', '2018-11-24 07:25:14', '正常', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='日志表';

-- ----------------------------
--  Records of `sys_log`
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES ('51', '0', '添加角色', 'test', 'admin', '2019-01-24 20:56:43', null, '0:0:0:0:0:0:0:1', 'PostmanRuntime/7.6.0', '/role', 'POST', 'Authorization=%5B%5D', '65', '0', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `name` varchar(32) NOT NULL COMMENT '菜单名称',
  `permission` varchar(32) DEFAULT NULL COMMENT '菜单权限标识',
  `path` varchar(128) DEFAULT NULL COMMENT '前端URL',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(32) DEFAULT NULL COMMENT '图标',
  `component` varchar(64) DEFAULT NULL COMMENT 'VUE页面',
  `sort` int(11) DEFAULT '1' COMMENT '排序值',
  `keep_alive` char(1) DEFAULT '0' COMMENT '0-开启，1- 关闭',
  `type` char(1) DEFAULT NULL COMMENT '菜单类型 （0菜单 1按钮）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1000, '帐号管理', 'sys_account_manage', '', -1, '', '', 10, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1100, '内部角色管理', 'sys_role', '', 1000, '', '', 10, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1101, '内部帐号管理', 'sys_account', '', 1000, '', '', 20, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1102, '代理帐号管理', 'sys_agent', '', 1000, '', '', 30, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1103, '商品管理', 'goods_manage', '', -1, '', '', 20, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1200, '商品列表', 'goods_list', '', 1103, '', '', 10, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1201, '物流管理', 'sale_logistics_list', '', 1103, '', '', 20, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1202, '采购物流', 'agent_logistics_list', '', 1103, '', '', 30, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1203, '设备管理', 'machine_list', '', 1103, '', '', 40, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1300, '商品类名', 'goods_category_list', '', 1103, '', '', 50, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1301, '分组名称', 'goods_group_list', '', 1103, '', '', 60, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1302, '用户中心', 'user_manage', '', -1, '', '', 30, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1303, '用户查询', 'user_list', '', 1302, '', '', 10, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1304, '数据查询', 'data_manage', '', -1, '', '', 40, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1400, '订单详情', 'sale_order_list', '', 1304, '', '', 10, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1401, '代理采购', 'agent_order_list', '', 1304, '', '', 20, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1402, '审核管理', 'audit_manage', '', -1, '', '', 50, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (1403, '发展奖励审核', 'develop_audit_list', '', 1402, '', '', 10, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2000, '代理审核', 'agent_audit_list', '', 1402, '', '', 20, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2100, '结算审核', 'settlement_audit_list', '', 1402, '', '', 30, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2101, '信息修改申请', 'agent_update_audit_list', '', 1402, '', '', 40, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2200, '提现申请', 'withdrawal_apply_audit_list', '', 1402, '', '', 50, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2201, '退货申请', 'return_order_audit_list', '', 1402, '', '', 60, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2202, '设置', 'setting_manage', '', -1, '', '', 60, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2203, '激活套餐', 'combo_list', '', 2202, '', '', 10, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2300, '商城分润', 'share_profit_setting_form', '', 2202, '', '', 20, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2400, '公告管理', 'notice_list', '', 2202, '', '', 30, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2401, '发展奖励', 'develop_audit_setting_form', '', 2202, '', '', 40, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2402, '代金券管理', 'voucher_manage', '', -1, '', '', 70, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2403, '代金券', 'voucher_list', '', 2402, '', '', 10, '0', '0', NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (2500, '发放记录', 'voucher_grant_record_list', '', 2402, '', '', 20, '0', '0', NULL, NULL, '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details` (
  `client_id` varchar(32) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='终端信息表';

-- ----------------------------
--  Records of `sys_oauth_client_details`
-- ----------------------------
BEGIN;
INSERT INTO `sys_oauth_client_details` VALUES ('agent_app', null, 'agent_app', 'server', 'password,refresh_token', null, null, null, null, '{"userType":"agent"}', 'true'), ('shadow', null, 'shadow', 'server', 'password,refresh_token', null, null, null, null, null, 'true'), ('gen', null, 'gen', 'server', 'password,refresh_token', null, null, null, null, null, 'true'), ('pig', null, 'pig', 'server', 'password,refresh_token,authorization_code,client_credentials', '', null, null, null, '{"userType":"inner"}', 'true')
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `role_code` varchar(64) COLLATE utf8mb4_bin NOT NULL,
  `role_desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_idx1_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统角色表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '管理员', 'ROLE_ADMIN', '管理员', null,'2017-10-29 15:45:51', '2018-12-26 14:09:11', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` int(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与部门对应关系';

-- ----------------------------
--  Records of `sys_role_dept`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_dept` VALUES ('1', '1', '8');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单表';

-- ----------------------------
--  Records of `sys_role_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 1000);
INSERT INTO `sys_role_menu` VALUES (1, 1100);
INSERT INTO `sys_role_menu` VALUES (1, 1101);
INSERT INTO `sys_role_menu` VALUES (1, 1102);
INSERT INTO `sys_role_menu` VALUES (1, 1103);
INSERT INTO `sys_role_menu` VALUES (1, 1200);
INSERT INTO `sys_role_menu` VALUES (1, 1201);
INSERT INTO `sys_role_menu` VALUES (1, 1202);
INSERT INTO `sys_role_menu` VALUES (1, 1203);
INSERT INTO `sys_role_menu` VALUES (1, 1300);
INSERT INTO `sys_role_menu` VALUES (1, 1301);
INSERT INTO `sys_role_menu` VALUES (1, 1302);
INSERT INTO `sys_role_menu` VALUES (1, 1303);
INSERT INTO `sys_role_menu` VALUES (1, 1304);
INSERT INTO `sys_role_menu` VALUES (1, 1400);
INSERT INTO `sys_role_menu` VALUES (1, 1401);
INSERT INTO `sys_role_menu` VALUES (1, 1402);
INSERT INTO `sys_role_menu` VALUES (1, 1403);
INSERT INTO `sys_role_menu` VALUES (1, 2000);
INSERT INTO `sys_role_menu` VALUES (1, 2100);
INSERT INTO `sys_role_menu` VALUES (1, 2101);
INSERT INTO `sys_role_menu` VALUES (1, 2200);
INSERT INTO `sys_role_menu` VALUES (1, 2201);
INSERT INTO `sys_role_menu` VALUES (1, 2202);
INSERT INTO `sys_role_menu` VALUES (1, 2203);
INSERT INTO `sys_role_menu` VALUES (1, 2300);
INSERT INTO `sys_role_menu` VALUES (1, 2400);
INSERT INTO `sys_role_menu` VALUES (1, 2401);
INSERT INTO `sys_role_menu` VALUES (1, 2402);
INSERT INTO `sys_role_menu` VALUES (1, 2403);
INSERT INTO `sys_role_menu` VALUES (1, 2500);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `operator_id` int(11) comment '操作人',
  `show_name` varchar(128) comment '显示用户名',
  `usertype` varchar(64) COLLATE utf8mb4_bin COMMENT '用户类型，inner/agent',
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `salt` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '随机盐',
  `phone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '简介',
  `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `lock_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，9-锁定',
  `del_flag` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，1-删除',
  `wx_openid` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '微信openid',
  `qq_openid` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'QQ openid',
  `is_locked` int(1) default 0 comment '是否锁定 0 正常 1 锁定',
  PRIMARY KEY (`user_id`),
  KEY `user_wx_openid` (`wx_openid`),
  KEY `user_qq_openid` (`qq_openid`),
  KEY `user_idx1_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', null ,'inner','$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC', null, '17034642999', '', '1', '2018-04-20 07:15:18', '2019-01-31 14:29:07', '0', '0', 'o_0FT0uyg_H1vVy2H0JpSwlVGhWQ', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('1', '1'), ('2', '2');
COMMIT;

-- ----------------------------
--  Table structure for `zipkin_spans`
-- ----------------------------
CREATE TABLE IF NOT EXISTS zipkin_spans (
  `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` BIGINT NOT NULL,
  `id` BIGINT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `parent_id` BIGINT,
  `debug` BIT(1),
  `start_ts` BIGINT COMMENT 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
  `duration` BIGINT COMMENT 'Span.duration(): micros used for minDuration and maxDuration query'
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_spans ADD UNIQUE KEY(`trace_id_high`, `trace_id`, `id`) COMMENT 'ignore insert on duplicate';
ALTER TABLE zipkin_spans ADD INDEX(`trace_id_high`, `trace_id`, `id`) COMMENT 'for joining with zipkin_annotations';
ALTER TABLE zipkin_spans ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTracesByIds';
ALTER TABLE zipkin_spans ADD INDEX(`name`) COMMENT 'for getTraces and getSpanNames';
ALTER TABLE zipkin_spans ADD INDEX(`start_ts`) COMMENT 'for getTraces ordering and range';

-- ----------------------------
--  Table structure for `zipkin_annotations`
-- ----------------------------
CREATE TABLE IF NOT EXISTS zipkin_annotations (
  `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` BIGINT NOT NULL COMMENT 'coincides with zipkin_spans.trace_id',
  `span_id` BIGINT NOT NULL COMMENT 'coincides with zipkin_spans.id',
  `a_key` VARCHAR(255) NOT NULL COMMENT 'BinaryAnnotation.key or Annotation.value if type == -1',
  `a_value` BLOB COMMENT 'BinaryAnnotation.value(), which must be smaller than 64KB',
  `a_type` INT NOT NULL COMMENT 'BinaryAnnotation.type() or -1 if Annotation',
  `a_timestamp` BIGINT COMMENT 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
  `endpoint_ipv4` INT COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_ipv6` BINARY(16) COMMENT 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
  `endpoint_port` SMALLINT COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_service_name` VARCHAR(255) COMMENT 'Null when Binary/Annotation.endpoint is null'
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_annotations ADD UNIQUE KEY(`trace_id_high`, `trace_id`, `span_id`, `a_key`, `a_timestamp`) COMMENT 'Ignore insert on duplicate';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`, `span_id`) COMMENT 'for joining with zipkin_spans';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTraces/ByIds';
ALTER TABLE zipkin_annotations ADD INDEX(`endpoint_service_name`) COMMENT 'for getTraces and getServiceNames';
ALTER TABLE zipkin_annotations ADD INDEX(`a_type`) COMMENT 'for getTraces and autocomplete values';
ALTER TABLE zipkin_annotations ADD INDEX(`a_key`) COMMENT 'for getTraces and autocomplete values';
ALTER TABLE zipkin_annotations ADD INDEX(`trace_id`, `span_id`, `a_key`) COMMENT 'for dependencies job';

-- ----------------------------
--  Table structure for `zipkin_dependencies`
-- ----------------------------
CREATE TABLE IF NOT EXISTS zipkin_dependencies (
  `day` DATE NOT NULL,
  `parent` VARCHAR(255) NOT NULL,
  `child` VARCHAR(255) NOT NULL,
  `call_count` BIGINT,
  `error_count` BIGINT
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

ALTER TABLE zipkin_dependencies ADD UNIQUE KEY(`day`, `parent`, `child`);

SET FOREIGN_KEY_CHECKS = 1;
