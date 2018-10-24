/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 127.0.0.1
 Source Database       : zd_deploy

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : utf-8

 Date: 10/24/2018 11:47:03 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `op_app`
-- ----------------------------
DROP TABLE IF EXISTS `op_app`;
CREATE TABLE `op_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `service_name` varchar(255) DEFAULT NULL,
  `deploy_path` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `group` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `op_app`
-- ----------------------------
BEGIN;
INSERT INTO `op_app` VALUES
('1', 'zd-opms', 'zd-opms.jar', 'zd-opms', '/var/lib/zd-opms/zd-opms.jar', 'root', 'root'),
('2', 'zd-deploy', 'zd-deploy.jar', 'zd-deploy', '/var/lib/zd-deploy/zd-deploy.jar', 'root', 'root'),
('3', 'zd-site', 'zd-site.jar', 'zd-site', '/var/lib/zd-site/zd-site.jar', 'root', 'root'),
('4', 'zd-sfd', 'zd-sfd.jar', 'zd-sfd', '/var/lib/zd-sfd/zd-sfd.jar', 'root', 'root'),
('5', 'zd-wxapi', 'zd-wxapi.jar', 'zd-wxapi', '/var/lib/zd-wxapi/zd-wxapi.jar', 'root', 'root'),
('6', 'zd-yunprint', 'zd-yunprint.jar', 'zd-yunprint', '/var/lib/zd-yunprint/zd-yunprint.jar', 'root', 'root');
COMMIT;

-- ----------------------------
--  Table structure for `op_app_node`
-- ----------------------------
DROP TABLE IF EXISTS `op_app_node`;
CREATE TABLE `op_app_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL,
  `node_id` int(11) NOT NULL,
  `del_flag` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `app_id` (`app_id`),
  KEY `node_id` (`node_id`),
  KEY `app_id_2` (`app_id`),
  KEY `node_id_2` (`node_id`),
  KEY `app_id_3` (`app_id`),
  KEY `node_id_3` (`node_id`),
  KEY `app_id_4` (`app_id`),
  KEY `node_id_4` (`node_id`),
  KEY `app_id_index` (`app_id`),
  KEY `node_id_index` (`node_id`),
  KEY `app_id_index_fg` (`app_id`) USING BTREE,
  KEY `node_id_index_fg` (`node_id`) USING BTREE,
  KEY `app_id_node_id_index_fg` (`app_id`,`node_id`) USING HASH,
  KEY `node_id_5` (`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `op_app_node`
-- ----------------------------
BEGIN;
INSERT INTO `op_app_node` VALUES
('1', '1', '1', '0'),
('2', '0', '2', '1'),
('3', '2', '2', '0'),
('4', '3', '1', '0'),
('5', '5', '1', '0'),
('6', '6', '1', '0'),
('7', '6', '4', '1');
COMMIT;

-- ----------------------------
--  Table structure for `op_log`
-- ----------------------------
DROP TABLE IF EXISTS `op_log`;
CREATE TABLE `op_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) NOT NULL,
  `is_success` int(11) NOT NULL,
  `operator_name` varchar(255) NOT NULL,
  `service_name` varchar(255) NOT NULL,
  `service_version` varchar(255) NOT NULL,
  `time` datetime DEFAULT NULL,
  `login_ip` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `op_node`
-- ----------------------------
DROP TABLE IF EXISTS `op_node`;
CREATE TABLE `op_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) NOT NULL,
  `host_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `op_node`
-- ----------------------------
BEGIN;
INSERT INTO `op_node` VALUES
('1', '192.186.0.10', 'work1'),
('2', '192.186.0.11', 'work2'),
('3', '192.186.0.12', 'work3'),
('4', '192.186.0.13', 'work4');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
