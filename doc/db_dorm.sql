/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : db_dorm

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-05-15 23:16:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for stored_items
-- ----------------------------
DROP TABLE IF EXISTS `stored_items`;
CREATE TABLE `stored_items` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `stu_num` bigint(255) DEFAULT NULL COMMENT '学生编号',
  `strored_in_time` varchar(255) DEFAULT NULL COMMENT '存放时间',
  `strored_out_time` varchar(255) DEFAULT NULL COMMENT '取走时间',
  `strored_status` tinyint(2) DEFAULT NULL COMMENT '状态',
  `stored_remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stored_items
-- ----------------------------

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `adminId` bigint(255) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'admin', 'admin', 'beyond', '男', '1828888888');

-- ----------------------------
-- Table structure for t_dorm
-- ----------------------------
DROP TABLE IF EXISTS `t_dorm`;
CREATE TABLE `t_dorm` (
  `dormId` int(11) NOT NULL AUTO_INCREMENT,
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(20) DEFAULT NULL,
  `dormType` varchar(20) DEFAULT NULL,
  `dormNumber` int(11) DEFAULT NULL,
  `dormTel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dormId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dorm
-- ----------------------------
INSERT INTO `t_dorm` VALUES ('1', '1', '220', '男', '6', '110');

-- ----------------------------
-- Table structure for t_dormbuild
-- ----------------------------
DROP TABLE IF EXISTS `t_dormbuild`;
CREATE TABLE `t_dormbuild` (
  `dormBuildId` int(11) NOT NULL AUTO_INCREMENT,
  `dormBuildName` varchar(20) DEFAULT NULL,
  `dormBuildDetail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dormBuildId`)
) ENGINE=InnoDB AUTO_INCREMENT=559 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dormbuild
-- ----------------------------
INSERT INTO `t_dormbuild` VALUES ('1', '明知居', '明知居信息介绍');
INSERT INTO `t_dormbuild` VALUES ('6', '德语居', '德语居信息介绍');
INSERT INTO `t_dormbuild` VALUES ('13', '芳华苑', '女子专用宿舍楼');
INSERT INTO `t_dormbuild` VALUES ('556', '知行苑', '知行苑');
INSERT INTO `t_dormbuild` VALUES ('557', '知德苑', '知德苑');

-- ----------------------------
-- Table structure for t_dormmanager
-- ----------------------------
DROP TABLE IF EXISTS `t_dormmanager`;
CREATE TABLE `t_dormmanager` (
  `dormManId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`dormManId`)
) ENGINE=InnoDB AUTO_INCREMENT=45253 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dormmanager
-- ----------------------------
INSERT INTO `t_dormmanager` VALUES ('52', '测试账号1', '123456', '0', '测试1', '男', '18245697896');
INSERT INTO `t_dormmanager` VALUES ('3244', '测试账号', '123', '1', '测试账号', '女', '123');
INSERT INTO `t_dormmanager` VALUES ('5252', '测试账号3', '123', '13', '测试3', '男', '18245627896');
INSERT INTO `t_dormmanager` VALUES ('8242', '123456', '123456', '6', 'beyond', '男', '18212346589');
INSERT INTO `t_dormmanager` VALUES ('45252', '测试账号2', '123', '0', '测试2', '男', '123');

-- ----------------------------
-- Table structure for t_dorm_room
-- ----------------------------
DROP TABLE IF EXISTS `t_dorm_room`;
CREATE TABLE `t_dorm_room` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `dorm_build_id` varchar(255) DEFAULT NULL,
  `dorm_build_name` varchar(255) DEFAULT NULL,
  `dorm_room_number` varchar(255) DEFAULT NULL,
  `dorm_room_name` varchar(255) DEFAULT NULL,
  `dorm_room_tel` varchar(255) DEFAULT NULL,
  `dorm_room_max` tinyint(2) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dorm_room
-- ----------------------------
INSERT INTO `t_dorm_room` VALUES ('1', '6', '德语居', '1113', '小居室', '15612434951', null, '暧昧');
INSERT INTO `t_dorm_room` VALUES ('2', '6', '德语居', '1112', '小居室隔壁', '15612434951', null, '上进');
INSERT INTO `t_dorm_room` VALUES ('6', '1', '明知居', '1245', '测试寝室', '15612434951', null, '测试寝室2');
INSERT INTO `t_dorm_room` VALUES ('8', '6', '德语居', '1114', '非主流', '15612434951', null, '非主流文化起源地');
INSERT INTO `t_dorm_room` VALUES ('9', '1', '明知居', '1246', '就是爱小孩', '15612434951', null, '我们是孩奴');

-- ----------------------------
-- Table structure for t_dorm_room_rating
-- ----------------------------
DROP TABLE IF EXISTS `t_dorm_room_rating`;
CREATE TABLE `t_dorm_room_rating` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `dorm_build_id` varchar(255) DEFAULT NULL,
  `dorm_build_name` varchar(255) DEFAULT NULL,
  `dorm_room_number` varchar(255) DEFAULT NULL,
  `score_clean` int(255) DEFAULT NULL,
  `score_culture` int(255) DEFAULT NULL,
  `score_obey` int(255) DEFAULT NULL,
  `rating_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dorm_room_rating
-- ----------------------------
INSERT INTO `t_dorm_room_rating` VALUES ('11', '1', '明知居', '1245', '3', '3', '3', '2018-05-15 22:05:19', '你们很厉害');
INSERT INTO `t_dorm_room_rating` VALUES ('12', '6', '德语居', '1112', '3', '3', '5', '2018-05-15 22:39:44', '我也喜欢非主流2');

-- ----------------------------
-- Table structure for t_record
-- ----------------------------
DROP TABLE IF EXISTS `t_record`;
CREATE TABLE `t_record` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT,
  `studentNumber` varchar(20) DEFAULT NULL,
  `studentName` varchar(30) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `detail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_record
-- ----------------------------
INSERT INTO `t_record` VALUES ('1', '002', '李四', '4', '120', '2014-01-01', '123');
INSERT INTO `t_record` VALUES ('3', '007', '测试1', '1', '221', '2014-08-11', '123');

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `studentId` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `stuNum` varchar(20) DEFAULT NULL COMMENT '学生编号',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `dormBuildId` int(11) DEFAULT NULL,
  `dormName` varchar(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL COMMENT '专业',
  `status` tinyint(2) DEFAULT NULL COMMENT '在校状态',
  `creattime` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `class` varchar(255) DEFAULT NULL COMMENT '班级',
  PRIMARY KEY (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('2', '002', '123', '李四', '6', '120', '男', '32', null, null, null, null);
INSERT INTO `t_student` VALUES ('9', '007', '123', '测试1', '1', '221', '男', '123', null, null, null, null);

-- ----------------------------
-- Table structure for t_visitor
-- ----------------------------
DROP TABLE IF EXISTS `t_visitor`;
CREATE TABLE `t_visitor` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `vis_name` varchar(255) DEFAULT NULL COMMENT '访问者姓名',
  `vis_sex` tinyint(2) DEFAULT NULL COMMENT '访问者性别',
  `vis_id_card` varchar(255) DEFAULT NULL,
  `vis_phone` varchar(255) DEFAULT NULL COMMENT '访问者手机号',
  `vis_dorm_build` varchar(255) DEFAULT NULL,
  `vis_dorm_build_name` varchar(255) DEFAULT NULL,
  `vis_dorm_build_room` varchar(255) DEFAULT NULL,
  `vis_in_time` datetime DEFAULT NULL COMMENT '访问入舍时间',
  `vis_out_time` datetime DEFAULT NULL COMMENT '访问出舍时间',
  `checked_id` varchar(255) DEFAULT NULL COMMENT '登记者（宿舍管理员）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_visitor
-- ----------------------------
INSERT INTO `t_visitor` VALUES ('11', '王晓婉', '1', '51152316621302', '18565221654', '13', null, '1231', '2018-05-11 03:03:04', '2018-05-11 03:03:04', 'admin', '送水');
INSERT INTO `t_visitor` VALUES ('12', '张大富', '1', '51152316621302', '18565232665', '14', null, '3014', '2018-05-11 16:12:50', '2018-05-11 16:12:50', 'admin', '送外卖');
INSERT INTO `t_visitor` VALUES ('13', '王文霞', '0', '51152316621302', '18565232665', '6', '德语居', '2310', '2018-05-12 21:16:10', '2018-05-12 21:16:10', '123456', '母亲访问');
INSERT INTO `t_visitor` VALUES ('14', '王大幅', '1', '51152316621302', '18565232665', '6', '德语居', '123123', '2018-05-11 03:03:04', '2018-05-12 00:00:00', '123456', '送外卖');
INSERT INTO `t_visitor` VALUES ('18', '李小萌', '0', '51152316621302', '18565232665', '6', '德语居', '5102', '2018-05-27 02:09:04', '2018-05-12 20:03:03', '123456', '维修宿舍电器设备');
INSERT INTO `t_visitor` VALUES ('19', '毛利', '1', '51152316621302', '18565232665', '6', '德语居', '3014', '2018-05-12 00:00:00', '2018-05-26 00:00:00', '123456', '送水');
SET FOREIGN_KEY_CHECKS=1;
