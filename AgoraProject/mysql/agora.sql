/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : agora

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-04-19 13:57:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `id` char(32) NOT NULL COMMENT '主键',
  `content` varchar(1000) NOT NULL COMMENT '公告的内容',
  `sec_id` char(32) NOT NULL COMMENT '对应课程的id',
  `title` varchar(50) NOT NULL COMMENT '课程的名字',
  `publish_time` varchar(100) NOT NULL COMMENT '发布公告的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES ('ba8f748be75a430481e0525a8a4daef6', 'fuck you', 'b1d346ca629e4ae09df3ab3f79b883ee', 'JAVA放弃之路', '2019-01-26 00:53:49');
INSERT INTO `announcement` VALUES ('f3683638e0fb4bb4bfa8968a69d2affb', '请问', 'b1d346ca629e4ae09df3ab3f79b883eg', 'java高级教程', '2019-03-20 10:00:48');

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `bill_id` char(32) NOT NULL COMMENT 'bill的主键',
  `out_biz_no` varchar(64) NOT NULL COMMENT '订单编号',
  `user_id` char(32) NOT NULL COMMENT '支付用户的id',
  `pay_type` char(32) NOT NULL COMMENT '支付的类型：微信/支付宝',
  `price` double(10,2) NOT NULL COMMENT '支付的价格',
  `finish_time` datetime(6) NOT NULL,
  `sec_id` char(32) NOT NULL,
  `state` varchar(4) NOT NULL COMMENT '标记状态：0为未付款，1为付款成功，2为付款失败',
  `detail` varchar(60) NOT NULL COMMENT '详细的交易信息',
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bill
-- ----------------------------
INSERT INTO `bill` VALUES ('121f9a48cf304683ade80442b4d82b8e', '2019317151821552807082340', '5ff89e9e3d6d4e0aae5128ac4ddc9b18', 'c9349741b71e4f798722745ce8bbe47b', '0.10', '2019-03-17 07:18:02.340000', 'b1d346ca629e4ae09df3ab3f79b883ef', '0', '');
INSERT INTO `bill` VALUES ('3bf7b39be3804b7ea36976b229c1f3c6', '20193221922271553253747379', '7e376f3e55c74898a23881a967aae0cf', 'c9349741b71e4f798722745ce8bbe47b', '0.10', '2019-03-22 11:22:27.380000', 'b1d346ca629e4ae09df3ab3f79b883ef', '0', '');
INSERT INTO `bill` VALUES ('5028bc5de8ae4fd78537eb63c90c69ba', '20193171523301552807410219', 'd2e227be541f4085b9275e2f8085178e', '839bdbd940354a48adfa242736be5356', '0.10', '2019-03-17 07:23:30.219000', 'b1d346ca629e4ae09df3ab3f79b883ef', '0', '');
INSERT INTO `bill` VALUES ('915d9a1f287745a0a458ffbf53becdfe', '2019128045491548607549868', '7e376f3e55c74898a23881a967aae0cf', '839bdbd940354a48adfa242736be5356', '0.10', '2019-01-27 16:45:49.869000', 'b1d346ca629e4ae09df3ab3f79b883ee', '0', '');
INSERT INTO `bill` VALUES ('c596419d21c940daba1fba8ccbf93bdd', '20193171519101552807150993', '5ff89e9e3d6d4e0aae5128ac4ddc9b18', '839bdbd940354a48adfa242736be5356', '0.10', '2019-03-17 07:19:10.993000', 'b1d346ca629e4ae09df3ab3f79b883ef', '0', '');
INSERT INTO `bill` VALUES ('c8679a11d6dc4040839f5f53a305785b', '20193221922321553253752282', '7e376f3e55c74898a23881a967aae0cf', '839bdbd940354a48adfa242736be5356', '0.10', '2019-03-22 11:22:32.282000', 'b1d346ca629e4ae09df3ab3f79b883ef', '0', '');
INSERT INTO `bill` VALUES ('d7e551e461904a31afb60e6e35591441', '201912804711548607621122', '7e376f3e55c74898a23881a967aae0cf', '839bdbd940354a48adfa242736be5356', '0.10', '2019-01-27 16:47:01.123000', 'b1d346ca629e4ae09df3ab3f79b883ee', '0', '');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` char(32) NOT NULL COMMENT '主码',
  `title` varchar(50) NOT NULL COMMENT '课程名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('481ff6767eee4dd2b877686154d895f5', 'c++');
INSERT INTO `course` VALUES ('eec7ecdb2e7349839785da9692f72e6d', 'java');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` char(32) NOT NULL,
  `content` varchar(1000) NOT NULL COMMENT '通告消息内容',
  `sender_id` char(32) NOT NULL COMMENT '发送者的id',
  `sender_name` varchar(30) NOT NULL COMMENT '发送者的用户名',
  `receiver_id` char(32) NOT NULL COMMENT '接收者的id',
  `receiver_name` varchar(30) NOT NULL COMMENT '接收者的用户名',
  `readed` tinyint(1) NOT NULL COMMENT '是否阅读消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('0b4bb3dd180944a7a90e423cc4a86076', '19:15  mark', 'd2e227be541f4085b9275e2f8085178e', 'admin', '5ff89e9e3d6d4e0aae5128ac4ddc9b18', 'student', '1');
INSERT INTO `message` VALUES ('1af7cd93d3724aaf917544cb56eedd33', 'test', 'd2e227be541f4085b9275e2f8085178e', 'admin', '5ff89e9e3d6d4e0aae5128ac4ddc9b18', 'student', '1');
INSERT INTO `message` VALUES ('2c635c8f6efa4c439bd309c7add88a48', '直播被启用！您现在可正常直播了！', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('2de91e7afe2541ed821471bdeca5aac5', '19:15  mark', 'd2e227be541f4085b9275e2f8085178e', 'admin', '7e376f3e55c74898a23881a967aae0cf', 'rover', '1');
INSERT INTO `message` VALUES ('5e35d224823c451aad212842ca4a7ab5', '您已被管理员禁播！！详情请咨询管理员。', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('6b2261417a5e4d86998fa4200d6f4ced', '您的课程已被授权！', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('7ad3f7b794824feca6ddca361b3d2e72', 'hahahaha', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('80a8fc1d4fba4f1d926324ab842048d0', 'test hzc', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('861f0aec24a140b094219e651aaaa8c4', 'hahah ', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('90c6970b855b4311959403ca2edc3061', '您的课程已被授权！', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('91f731cc591641c08bee92ec0a0ecdab', '您的课程已被授权！', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('998024276d234703abd32f14db995591', '您的课程已被授权！', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '0');
INSERT INTO `message` VALUES ('a3ecff93dd614acd8303f9a916c0d0ab', '你好啊', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('a557b550f0ff4792a8c54d99e7cb9ec2', 'test', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('a611c3aa2131483ea75da5e2151e0b74', '19:15  mark', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('b239fa44ba284185bb5718122d87de95', '教师身份已通过管理员审核！您现在可以开课啦！', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '0');
INSERT INTO `message` VALUES ('cb5e68eb897c4d6e9d3e6fd878d1c033', '您的课程已被授权！', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '0');
INSERT INTO `message` VALUES ('d9c53e67d6124efabd8125630dfbf701', 'test', 'd2e227be541f4085b9275e2f8085178e', 'admin', '7e376f3e55c74898a23881a967aae0cf', 'rover', '1');
INSERT INTO `message` VALUES ('db670ba8737f4dd6a8dcc7dd10455776', 'test 老师', 'd2e227be541f4085b9275e2f8085178e', 'admin', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1');
INSERT INTO `message` VALUES ('e2f9533ea5f64da29d556a007faf7124', '教师身份已通过管理员审核！您现在可以开课啦！', 'd2e227be541f4085b9275e2f8085178e', 'admin', '5ff89e9e3d6d4e0aae5128ac4ddc9b18', 'student', '0');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` varchar(30) NOT NULL COMMENT '权限名',
  `sign` varchar(50) NOT NULL COMMENT '权限标识,程序中判断使用',
  `description` varchar(256) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_name` (`name`),
  UNIQUE KEY `permission_sign` (`sign`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` varchar(30) NOT NULL COMMENT '角色名',
  `sign` varchar(50) NOT NULL COMMENT '角色标识,程序中判断使用,如"admin"',
  `description` varchar(256) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`name`),
  UNIQUE KEY `role_sign` (`sign`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('2b7f203b6e5b4816b9c108cd8e3a11aa', 'student', '5675ac07886642a0aa7fa3b7a79d769f', '学生，上课的用户');
INSERT INTO `role` VALUES ('57de603aea9e4f7e86ed30e9d548ff83', 'admin', 'c10a03dfe3224d719d6d3e64cb51de16', '管理者，拥有最高的执行权限');
INSERT INTO `role` VALUES ('c6f6ff356cdb4603892551c427d415b2', 'teacher', '921f26af94904a3a8509de04a733508b', '老师，授课的用户');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` char(32) NOT NULL COMMENT '角色主键',
  `permission_id` char(32) NOT NULL COMMENT '权限主键',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for section
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
  `sec_id` char(32) NOT NULL COMMENT '主键',
  `cour_id` char(32) NOT NULL,
  `tea_id` char(32) NOT NULL COMMENT '上课老师的编号',
  `start_date` date NOT NULL COMMENT '开始时间',
  `class_time` varchar(255) NOT NULL COMMENT '具体的上课时间',
  `price` double NOT NULL COMMENT '这系列课程的价格',
  `authorized` tinyint(1) NOT NULL DEFAULT '0' COMMENT '管理员授权上课标识',
  `upper_limit` int(11) NOT NULL COMMENT '最大人数上限',
  `cur_amount` int(11) NOT NULL DEFAULT '0' COMMENT '当前课程报名的人数',
  `purchased` tinyint(1) NOT NULL DEFAULT '0' COMMENT '购买的权限',
  `room_number` char(32) NOT NULL COMMENT '课程的房间编号',
  `title` varchar(50) NOT NULL COMMENT '课程的名字',
  `frequency` int(11) NOT NULL COMMENT '上课次数',
  `tea_name` varchar(32) NOT NULL COMMENT '老师的用户名',
  `attended` int(10) NOT NULL COMMENT '已上课的次数',
  `judgement` varchar(30) NOT NULL COMMENT '用于后台的判断，星期几上课（格式为：1,2,3表示想起一，二，三都有课）',
  `paid` tinyint(1) NOT NULL DEFAULT '0' COMMENT '这门课的工资有没有结算',
  `introduction` text NOT NULL COMMENT '课程介绍',
  `proportion` double(5,2) DEFAULT NULL COMMENT '老师工资所占的百分比',
  `actual_amount` double(8,2) NOT NULL DEFAULT '0.00' COMMENT '实际支付老师的工资',
  `in_class` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否正在上课',
  `prohibited` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁止上课，教师点击上课按钮之前进行判断',
  PRIMARY KEY (`sec_id`),
  KEY `tea_username` (`tea_id`),
  KEY `section_ibfk_2` (`cour_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of section
-- ----------------------------
INSERT INTO `section` VALUES ('2a87cb75c4a44f74a1b7673a1db2bd64', '481ff6767eee4dd2b877686154d895f5', '3b514ec2838849b6a8708f78ca27dcc1', '2019-03-29', '星期日 20:33:17 20:33:20', '12', '1', '12', '0', '1', '3817fc8dbadc4599b0d923efb0e61e1d', '123', '12', 'hzc', '0', '7,', '0', '123', '80.00', '0.00', '0', '0');
INSERT INTO `section` VALUES ('2eafc51106524add9efa8e45d2a7e424', '481ff6767eee4dd2b877686154d895f5', '3b514ec2838849b6a8708f78ca27dcc1', '2019-03-16', '星期六 15:33:07 15:33:08', '0.1', '1', '12', '0', '1', '2eb4caba4b7b4aa0bfe423837f608857', 'ad', '12', 'hzc', '0', '6,', '0', '11111', '95.00', '0.00', '0', '0');
INSERT INTO `section` VALUES ('5d75e30f6ed34ec9bd4c9aea2f01664e', '481ff6767eee4dd2b877686154d895f5', '3b514ec2838849b6a8708f78ca27dcc1', '2019-03-18', '星期一 11:14:11 11:14:12', '0.1', '1', '12', '0', '1', 'b07fa767c70e45ccab1fe9fa52268d25', 'CPP程序设计', '12', 'hzc', '0', '1,', '0', 'CPP程序设计之五子棋、树莓派', '70.00', '0.00', '0', '0');
INSERT INTO `section` VALUES ('b1d346ca629e4ae09df3ab3f79b883ee', 'eec7ecdb2e7349839785da9692f72e6d', '3b514ec2838849b6a8708f78ca27dcc1', '2019-01-22', '星期一 02:00:00 08:00:00,星期一 04:00:00 05:00:00,星期四 20:36:15 20:36:16,星期五 20:36:08 20:36:57,星期六 20:36:02 20:36:04', '0.1', '1', '10', '1', '0', '3fa42b8b52fe46c0bd069f6daf2e11e3', 'JAVA放弃之路', '10', 'hzc', '10', '1,1,4,5,6,', '0', '哈哈哈，案说法覅哦啊放假哦哎啊烦恼的附件哦发送覅按附件啊发啊发啊啊啊】安抚案件四分按时间发书法家挨罚  发hi是否阿苏富有是否故意萨格垡是飞洒服啊是个服啊发啊发啊发啊沙发上说\n\n按时到i大i\n', '80.00', '0.08', '0', '0');
INSERT INTO `section` VALUES ('b1d346ca629e4ae09df3ab3f79b883ef', '481ff6767eee4dd2b877686154d895f5', '3b514ec2838849b6a8708f78ca27dcc1', '2019-02-10', '星期一 02:00:00 08:00:00,星期一 04:00:00 05:00:00,星期四 20:36:15 20:36:16,星期五 20:36:08 20:36:57,星期六 20:36:02 20:36:04', '0.1', '1', '10', '0', '1', '3fa42b8b52fe46c0bd069f6daf2e11e4', 'c++入门', '10', 'hzc', '0', '1,1,4,5,6,', '0', 'test', '10.00', '0.00', '0', '0');
INSERT INTO `section` VALUES ('b1d346ca629e4ae09df3ab3f79b883eg', 'eec7ecdb2e7349839785da9692f72e6d', '3b514ec2838849b6a8708f78ca27dcc1', '2019-02-11', '星期一 02:00:00 08:00:00,星期一 04:00:00 05:00:00,星期四 20:36:15 20:36:16,星期五 20:36:08 20:36:57,星期六 20:36:02 20:36:04', '0.1', '1', '10', '0', '1', '3fa42b8b52fe46c0bd069f6daf2e11e5', 'java高级教程', '10', 'hzc', '0', '1,1,4,5,6,', '0', 'test java', '80.00', '0.00', '0', '0');
INSERT INTO `section` VALUES ('e7fd1d39303847648b01929d648fd5b6', '481ff6767eee4dd2b877686154d895f5', '3b514ec2838849b6a8708f78ca27dcc1', '2019-03-19', '星期一 11:14:11 11:14:12', '0.1', '1', '12', '0', '1', 'bc45529ddec0430b97af20e7dd70e9ee', 'CPP程序设计', '12', 'hzc', '0', '1,', '0', 'CPP程序设计之五子棋', '70.00', '0.00', '0', '0');
INSERT INTO `section` VALUES ('f95d090e35564e089001d7847972e973', 'eec7ecdb2e7349839785da9692f72e6d', '3b514ec2838849b6a8708f78ca27dcc1', '2019-03-11', '星期三 21:12:24 21:12:25', '12', '1', '12', '0', '1', 'd627acfa2ae04be0bc838b23fe448794', '123', '1', 'hzc', '0', '3,', '0', '123', '80.00', '0.00', '0', '0');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `task_id` char(32) NOT NULL,
  `stu_id` char(32) NOT NULL,
  `sec_id` char(32) NOT NULL,
  `tea_id` char(32) NOT NULL,
  `cour_id` char(32) NOT NULL,
  `room_number` char(32) NOT NULL,
  `title` varchar(50) NOT NULL COMMENT '课程名字',
  `start_date` date NOT NULL COMMENT '课程开始时间',
  `classtime` varchar(255) NOT NULL COMMENT '具体的上课时间',
  `tea_name` varchar(30) NOT NULL COMMENT '老师名字',
  `stu_name` varchar(30) NOT NULL COMMENT '学生名字',
  PRIMARY KEY (`task_id`),
  KEY `stu_username` (`stu_id`),
  KEY `task_ibfk_2` (`sec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('b1d346ca629e4ae09df3ab3f79b883ee', '7e376f3e55c74898a23881a967aae0cf', 'b1d346ca629e4ae09df3ab3f79b883ee', '3b514ec2838849b6a8708f78ca27dcc1', 'eec7ecdb2e7349839785da9692f72e6d', '3fa42b8b52fe46c0bd069f6daf2e11e3', 'JAVA放弃之路', '2019-01-07', '每周的星期一下午3~5点', 'hzc', 'rover');
INSERT INTO `task` VALUES ('d2e227be541f4085b9275e2f8085178f', '5ff89e9e3d6d4e0aae5128ac4ddc9b18', 'b1d346ca629e4ae09df3ab3f79b883ee', '3b514ec2838849b6a8708f78ca27dcc1', 'eec7ecdb2e7349839785da9692f72e6d', '3fa42b8b52fe46c0bd069f6daf2e11e3', 'JAVA放弃之路2019-01-07', '2019-01-07', '每周的星期一下午3~5点', 'hzc', 'student');

-- ----------------------------
-- Table structure for tea_authentication
-- ----------------------------
DROP TABLE IF EXISTS `tea_authentication`;
CREATE TABLE `tea_authentication` (
  `id` char(32) NOT NULL COMMENT '主键',
  `phone_number` varchar(20) NOT NULL COMMENT '手机号',
  `user_id` char(32) NOT NULL COMMENT '申请用户的id',
  `user_name` varchar(50) NOT NULL COMMENT '申请用户的用户名',
  `authorized` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否通过授权',
  `resume` varchar(1000) NOT NULL COMMENT '用户的简历',
  `alipay_account` varchar(50) NOT NULL COMMENT '支付宝的账号，可能为手机或者其他邮箱之类的',
  `ID_number` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tea_authentication
-- ----------------------------
INSERT INTO `tea_authentication` VALUES ('9a46fc19fdd04c8581f25647c366a94a', '12345678900', '7e376f3e55c74898a23881a967aae0cf', 'rover', '0', '123', '12345678900', '440182199711090632');
INSERT INTO `tea_authentication` VALUES ('f6e4f0ae88c648f68fb648b94352d898', '17665156970', '3b514ec2838849b6a8708f78ca27dcc1', 'hzc', '1', '17665156970', '17665156970', null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` char(32) NOT NULL COMMENT '主键',
  `email` varchar(50) NOT NULL COMMENT '邮件地址',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `real_name` varchar(10) DEFAULT NULL COMMENT '真实姓名,认证后填充',
  `actived` tinyint(1) DEFAULT '0' COMMENT ' 是否已激活,默认为0,表示未激活,1表示已激活.激活后才能登录',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `create_time` datetime(6) NOT NULL COMMENT '创建时间',
  `uid` varchar(32) NOT NULL COMMENT '用户的uid，用标识用户的视频流',
  `is_changing_pw` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户是否在申请忘记密码',
  `changing_pw_sign` varchar(255) DEFAULT '' COMMENT '忘记密码链接携带的签名',
  `prohibited` tinyint(1) NOT NULL DEFAULT '0' COMMENT '禁止用户登录字段，每次登陆之前，需要进行判断',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3b514ec2838849b6a8708f78ca27dcc1', '1129743671@qq.com', 'hzc', '07f33953d48658a50ea76877915278ff', '侯智聪', '1', '男', '2019-01-17 07:11:34.985000', '6c3793f289374911aac9103944db7598', '1', '81634f9e392e4943800a714a8b8d968f', '0');
INSERT INTO `user` VALUES ('5ff89e9e3d6d4e0aae5128ac4ddc9b18', 'vvvvv@163.com', 'student', 'a6e10f0244bfd2ae5cd82b882001a500', 'hzc', '1', 'female', '2019-01-25 07:11:52.623000', 'dce98bec02944a96bf50dc1b593d8880', '0', null, '0');
INSERT INTO `user` VALUES ('7e376f3e55c74898a23881a967aae0cf', 'ZhicongHou@163.com', 'rover', '35bc9bab39b306a3f6e0d91d38ca25f4', 'hzc', '1', 'male', '2019-01-25 14:49:03.235000', '7d06e493ccd84b819987ad163fba233a', '0', null, '0');
INSERT INTO `user` VALUES ('d2e227be541f4085b9275e2f8085178e', '444444@qq.com', 'admin', '260c621e487548609d81ccebbe96bd5c', 'admin', '1', '男', '2019-01-07 19:33:46.000000', 'ad05e72e8fbb4ed5a10d0a7d75d066c3', '0', '', '0');

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message` (
  `userid` char(32) NOT NULL,
  `mid` char(32) NOT NULL,
  PRIMARY KEY (`userid`,`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_message
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` char(32) NOT NULL COMMENT '�û�����',
  `role_id` char(32) NOT NULL COMMENT '��ɫ����',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('3b514ec2838849b6a8708f78ca27dcc1', 'c6f6ff356cdb4603892551c427d415b2');
INSERT INTO `user_role` VALUES ('d2e227be541f4085b9275e2f8085178e', '57de603aea9e4f7e86ed30e9d548ff83');
