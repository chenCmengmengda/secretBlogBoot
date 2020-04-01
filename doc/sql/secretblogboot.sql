/*
Navicat MySQL Data Transfer

Source Server         : Test
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : secretblogboot

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-03-31 22:51:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `persistent_logins`
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_name` varchar(255) DEFAULT NULL COMMENT '配置名称',
  `config_type` varchar(50) DEFAULT NULL COMMENT '配置类型',
  `config_code` varchar(50) DEFAULT NULL COMMENT '配置键',
  `config_value` varchar(1000) DEFAULT NULL COMMENT '配置值',
  `config_desc` varchar(5000) DEFAULT NULL COMMENT '配置说明',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', '系统名称', 'system', 'systemName', 'secretBlog', '网站的名称', '2020-03-26 20:26:03', '2020-03-26 20:26:08');
INSERT INTO `sys_config` VALUES ('2', '文件上传方式', 'upload', 'storageType', 'local', '本地:local/七牛云:qiniu/阿里云:oos', '2020-03-26 21:25:23', '2020-03-28 18:03:42');
INSERT INTO `sys_config` VALUES ('3', '文件上传-使用nginx', 'upload', 'useNginx', '1', null, '2020-03-28 18:06:45', '2020-03-28 19:11:04');
INSERT INTO `sys_config` VALUES ('7', 'nginx地址', 'upload', 'nginxUrl', 'http://localhost/upload/', null, '2020-03-28 18:07:43', '2020-03-28 19:56:36');
INSERT INTO `sys_config` VALUES ('8', '上传服务器地址', 'upload', 'serverUrl', 'localhost:8086/', null, '2020-03-28 18:08:50', '2020-03-28 19:11:13');
INSERT INTO `sys_config` VALUES ('9', 'uuidName', 'upload', 'uuidName', '1', null, '2020-03-28 18:09:32', '2020-03-28 18:09:32');
INSERT INTO `sys_config` VALUES ('10', '文件地址前缀', 'upload', 'rootPath', 'E:/upload/secretBlog/', null, '2020-03-28 18:11:33', '2020-03-28 18:11:33');
INSERT INTO `sys_config` VALUES ('11', '开启缩略图', 'upload', 'useSm', '1', null, '2020-03-28 18:11:57', '2020-03-28 18:11:57');

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL,
  `pid` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '概要类型：0为菜单 1为功能',
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `href` varchar(255) DEFAULT NULL COMMENT '权限路径',
  `code` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `fontFamily` varchar(255) DEFAULT NULL COMMENT '字体库',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `spread` tinyint(1) DEFAULT NULL COMMENT '是否展开，1：true,0:false',
  `status` int(11) DEFAULT '0' COMMENT '权限状态：0 为正常 1为禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', null, '0', '首页', null, '/index', 'index', null, null, null, '0');
INSERT INTO `sys_permission` VALUES ('2', null, '0', '系统管理', null, null, null, null, null, '0', '0');
INSERT INTO `sys_permission` VALUES ('3', '2', '0', '用户管理', null, '/system/user', 'user', null, null, null, '0');
INSERT INTO `sys_permission` VALUES ('4', '2', '0', '角色管理', null, '/system/role', 'role', null, null, null, '0');
INSERT INTO `sys_permission` VALUES ('5', '2', '0', '权限管理', null, '/system/permission', 'permission', null, null, null, '0');
INSERT INTO `sys_permission` VALUES ('6', '3', '1', '查询用户', null, '/sysUser/list', 'user:list', null, null, null, '0');
INSERT INTO `sys_permission` VALUES ('7', '3', '1', '添加用户', null, '/sysUser/add', 'user:add', null, null, null, '0');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `status` int(11) DEFAULT '0' COMMENT '状态,0,正常,1禁用',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '系统最高权限,无所不能', '0', null);

-- ----------------------------
-- Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `status` int(1) DEFAULT '0' COMMENT '状态:0:正常，1禁用',
  `email` varchar(20) DEFAULT NULL,
  `login_num` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL COMMENT '外键:角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'secretC', '$2a$10$u00C/3gAW6EBBpZjw2mwIeRFicjpp9EwwxNBqjyq7sEJRdX9Z2pte', '0', null, null, null, null, '1');
