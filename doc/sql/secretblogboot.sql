/*
Navicat MySQL Data Transfer

Source Server         : Test
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : secretblogboot

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-05-17 20:08:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章标题',
  `title` varchar(100) DEFAULT NULL,
  `summary` varchar(500) DEFAULT NULL COMMENT '摘要',
  `content` text COMMENT '内容',
  `cover_url` varchar(200) DEFAULT NULL COMMENT '封面地址',
  `status` int(11) DEFAULT NULL COMMENT '状态:0已发布,1,定时发布,2.保存草稿',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `istop` int(11) DEFAULT '1' COMMENT '置顶,0是,1否',
  `ispublic` int(11) DEFAULT NULL COMMENT '是否公开,0是,1否',
  `category_id` int(11) DEFAULT NULL COMMENT '类别id',
  `favor_num` int(11) DEFAULT '0' COMMENT '点赞数',
  `read_num` int(11) DEFAULT '0' COMMENT '阅读数',
  `collect_num` int(11) DEFAULT '0' COMMENT '收藏数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', '添加标题', '添加内容', '# 添加内容', 'http://localhost/upload/article_images/20200517180632776.jpg', '0', null, null, null, '0', '0', '1', '0', '0', '0');

-- ----------------------------
-- Table structure for `article_category`
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT '' COMMENT '文章类别名称',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='文章分类表';

-- ----------------------------
-- Records of article_category
-- ----------------------------
INSERT INTO `article_category` VALUES ('1', '后端技术', null, null);
INSERT INTO `article_category` VALUES ('2', '前端技术', null, null);

-- ----------------------------
-- Table structure for `article_label`
-- ----------------------------
DROP TABLE IF EXISTS `article_label`;
CREATE TABLE `article_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '标签名称',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='文章标签表';

-- ----------------------------
-- Records of article_label
-- ----------------------------
INSERT INTO `article_label` VALUES ('1', 'java', null, null);
INSERT INTO `article_label` VALUES ('2', 'linux', null, null);

-- ----------------------------
-- Table structure for `article_label_key`
-- ----------------------------
DROP TABLE IF EXISTS `article_label_key`;
CREATE TABLE `article_label_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL COMMENT '文章id',
  `label_id` int(11) DEFAULT NULL COMMENT '类别id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='文章-标签关联表';

-- ----------------------------
-- Records of article_label_key
-- ----------------------------
INSERT INTO `article_label_key` VALUES ('1', '1', '1');
INSERT INTO `article_label_key` VALUES ('2', '1', '2');

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
INSERT INTO `persistent_logins` VALUES ('secretC', '+Yij040aDk4xOYrTqEPZOQ==', 'IFIe20zAoB4OhhUjb/e0uQ==', '2020-05-17 19:12:51');
INSERT INTO `persistent_logins` VALUES ('secretC', 'caA0ZYQLHFirIEqYeQF67g==', 'Ec/1ulquA5Nzt1dZCpVeXQ==', '2020-04-13 21:03:49');
INSERT INTO `persistent_logins` VALUES ('secretC', 'RO3rMrs8CMrtDmOIrEbn/A==', 'IPYZuYKCcYn2AUoQHqdVCg==', '2020-05-07 21:36:47');
INSERT INTO `persistent_logins` VALUES ('secretC', 'vLUoPrI5mzJGFHw9qgfRqw==', 'yARhLt0Dt/XA57nj+1fpig==', '2020-04-12 22:46:19');
INSERT INTO `persistent_logins` VALUES ('secretC', 'xtws+T/A8glplfrTUQxF1A==', 'bFLIhaQuzFdg2r30OFtyZg==', '2020-05-17 18:06:19');

-- ----------------------------
-- Table structure for `photo`
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(100) DEFAULT NULL COMMENT '图片标题',
  `url` varchar(100) DEFAULT NULL COMMENT '图片路径',
  `sm_url` varchar(100) DEFAULT NULL COMMENT '缩略图路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of photo
-- ----------------------------
INSERT INTO `photo` VALUES ('2', '飞燕', 'http://localhost/upload/photo/20200412195838091.jpg', 'http://localhost/upload/sm/photo/20200412195838091.jpg');
INSERT INTO `photo` VALUES ('3', '上传测试', 'http://localhost/upload/photo/20200517174746564.jpg', 'http://localhost/upload/sm/photo/20200517174746564.jpg');
INSERT INTO `photo` VALUES ('4', '上传测试', 'http://localhost/upload/photo/20200517174920374.jpg', 'http://localhost/upload/sm/photo/20200517174920374.jpg');

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', '系统名称', 'system', 'systemName', 'secretBlog', '网站的名称', '2020-03-26 20:26:03', '2020-03-26 20:26:08');
INSERT INTO `sys_config` VALUES ('2', '文件上传方式', 'upload', 'storageType', 'local', '本地:local/七牛云:qiniu/阿里云:oos', '2020-03-26 21:25:23', '2020-03-28 18:03:42');
INSERT INTO `sys_config` VALUES ('3', '文件上传-使用nginx', 'upload', 'useNginx', '1', null, '2020-03-28 18:06:45', '2020-04-12 19:53:19');
INSERT INTO `sys_config` VALUES ('7', 'nginx地址', 'upload', 'nginxUrl', 'http://localhost/upload/', null, '2020-03-28 18:07:43', '2020-03-28 19:56:36');
INSERT INTO `sys_config` VALUES ('8', '上传服务器地址', 'upload', 'serverUrl', 'localhost:8086/', null, '2020-03-28 18:08:50', '2020-03-28 19:11:13');
INSERT INTO `sys_config` VALUES ('9', 'uuidName', 'upload', 'uuidName', '1', null, '2020-03-28 18:09:32', '2020-03-28 18:09:32');
INSERT INTO `sys_config` VALUES ('10', '文件地址前缀', 'upload', 'rootPath', 'E:/upload/secretBlog/', null, '2020-03-28 18:11:33', '2020-03-28 18:11:33');
INSERT INTO `sys_config` VALUES ('11', '开启缩略图', 'upload', 'useSm', '1', null, '2020-03-28 18:11:57', '2020-03-28 18:11:57');
INSERT INTO `sys_config` VALUES ('12', '相册上传目录', 'photo', 'dir', '/photo/', null, '2020-04-05 11:04:49', '2020-04-05 11:08:37');
INSERT INTO `sys_config` VALUES ('13', '文章图片上传目录', 'article', 'dir', '/article_images/', null, '2020-05-17 15:37:21', '2020-05-17 15:37:21');

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '概要类型：0为菜单 1为功能',
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `href` varchar(255) DEFAULT NULL COMMENT '权限路径',
  `code` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `font_family` varchar(255) DEFAULT NULL COMMENT '字体库',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `spread` tinyint(1) DEFAULT NULL COMMENT '是否展开，1：true,0:false',
  `status` int(11) DEFAULT '0' COMMENT '权限状态：0 为正常 1为禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

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
INSERT INTO `sys_permission` VALUES ('8', '2', '0', '系统配置', '进入系统配置页面', '/system/setup', 'system', '', '&#xe62e;', '0', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '系统最高权限,无所不能', '0', null);
INSERT INTO `sys_role` VALUES ('7', '测试角色', '用于测试功能', '0', null);

-- ----------------------------
-- Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('9', '7', '1');
INSERT INTO `sys_role_permission` VALUES ('10', '7', '2');

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
  `login_num` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL COMMENT '外键:角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'secretC', '$2a$10$u00C/3gAW6EBBpZjw2mwIeRFicjpp9EwwxNBqjyq7sEJRdX9Z2pte', '0', null, '24', null, null, '1');
INSERT INTO `sys_user` VALUES ('3', 'test', '$2a$10$znvvW6D1Cx9Pi5SiRyx4j.Vhdlsjhq8tWfLvMR4rKX/N6At1Zivym', '0', '2873518017@qq.com', '0', null, null, '1');
