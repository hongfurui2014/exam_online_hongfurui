/*
SQLyog Ultimate v12.08 (32 bit)
MySQL - 5.6.45 : Database - exam_hfr
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`exam_hfr` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `exam_hfr`;

/*Table structure for table `hfr_grade` */

DROP TABLE IF EXISTS `hfr_grade`;

CREATE TABLE `hfr_grade` (
  `grade_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `grade_name` varchar(20) DEFAULT NULL COMMENT '班级名称',
  PRIMARY KEY (`grade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_grade` */

/*Table structure for table `hfr_info` */

DROP TABLE IF EXISTS `hfr_info`;

CREATE TABLE `hfr_info` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info_username` varchar(20) DEFAULT NULL COMMENT '后台操作用户',
  `info_visittime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `info_ip` varchar(20) DEFAULT NULL COMMENT '访问ip',
  `info_uri` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `info_describe` varchar(30) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_info` */

/*Table structure for table `hfr_rights` */

DROP TABLE IF EXISTS `hfr_rights`;

CREATE TABLE `hfr_rights` (
  `rights_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '顶级权限的父id为0',
  `rights_authname` varchar(30) DEFAULT NULL COMMENT '权限名/菜单名',
  `rights_path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `rights_level` tinyint(11) DEFAULT NULL COMMENT '层级 1,2,3',
  `fk_p_rights_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `rights_icon` varchar(100) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`rights_id`),
  KEY `fk_p_rights_id` (`fk_p_rights_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_rights` */

insert  into `hfr_rights`(`rights_id`,`rights_authname`,`rights_path`,`rights_level`,`fk_p_rights_id`,`rights_icon`) values (1,'系统管理',NULL,1,0,'el-icon-setting'),(2,'考试管理',NULL,1,0,'el-icon-document'),(3,'学校操作',NULL,1,0,'el-icon-bank-card\r\n'),(4,'系统日志',NULL,1,0,'el-icon-cloudy'),(5,'后台用户','/system/user',2,1,'el-icon-monitor'),(6,'考试用户','/system/userQ',2,1,'el-icon-tickets'),(7,'角色权限','/system/role',2,1,'el-icon-lock'),(8,'试题管理','/test/topic',2,2,'el-icon-notebook-1'),(9,'试卷管理','/test/test',2,2,'el-icon-reading'),(10,'考试统计','/test/statistics',2,2,'el-icon-coin'),(11,'班级管理','/school/grade',2,3,'el-icon-bangzhu'),(12,'科目管理','/school/subject',2,3,'el-icon-sunset'),(13,'重要日志','/info/InfoList',2,4,'el-icon-connection'),(14,'添加后台用户','user/user/addUser',3,5,NULL),(15,'修改后台用户','user/user/updateUser',3,5,NULL),(16,'删除后台用户','user/user/delUserById',3,5,NULL),(17,'分页查看后台用户','user/user/findUsersByPage',3,5,NULL),(18,'添加考试用户','user/userQ/addUserQ',3,6,NULL),(19,'修改考试用户','user/userQ/updateUserQ',3,6,NULL),(20,'删除考试用户','user/userQ/delUserQById',3,6,NULL),(21,'分页查看考试用户','user/userQ/findUserQsByPage',3,6,NULL),(22,'添加角色','user/role/addRole',3,7,NULL),(23,'修改角色','user/role/updateRole',3,7,NULL),(24,'删除角色','user/role/delRoleById',3,7,NULL),(25,'修改角色对应的权限','user/roleRights/updateRights',3,7,NULL),(26,'分页查看角色','user/role/findRolesByPage',3,7,NULL),(27,'excel导入试题','test/topic/upload',3,8,NULL),(28,'下载excel试题导入模板','test/topic/downloadExcel',3,8,NULL),(29,'修改试题','test/topic/updateTopic',3,8,NULL),(30,'删除试题','test/topic/delTopicById',3,8,NULL),(31,'分页查看试题','test/topic/findTopicsByPage',3,8,NULL),(32,'自动抽题出卷','test/test/addTest',3,9,NULL),(33,'分页查案试卷','test/test/findTestsByPage',3,9,NULL),(34,'分页查看考试统计','test/testUserQ/findTestUserQsByPage',3,10,NULL),(35,'添加班级','school/grade/addGrade',3,11,NULL),(36,'修改班级','school/grade/updateGrade',3,11,NULL),(37,'删除班级','school/grade/delGradeById',3,11,NULL),(38,'分页查看班级','school/grade/findGradesByPage',3,11,NULL),(39,'添加科目','school/subject/addSubject',3,12,NULL),(40,'修改科目','school/subject/updateSubject',3,12,NULL),(41,'删除科目','school/subject/delSubjectById',3,12,NULL),(42,'分页查看科目','school/subject/findSubjectsByPage',3,12,NULL),(43,'查看后台操作日志','user/info/findInfosByPage',3,13,NULL),(44,'查询所有权限列表','user/rights/findAll',3,7,NULL);

/*Table structure for table `hfr_role` */

DROP TABLE IF EXISTS `hfr_role`;

CREATE TABLE `hfr_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_role` */

insert  into `hfr_role`(`role_id`,`role_name`) values (13,'演示角色');

/*Table structure for table `hfr_role_rights` */

DROP TABLE IF EXISTS `hfr_role_rights`;

CREATE TABLE `hfr_role_rights` (
  `role_right_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fk_role_id` bigint(20) DEFAULT NULL,
  `fk_rights_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`role_right_id`),
  KEY `fk_role_id` (`fk_role_id`),
  KEY `fk_rights_id` (`fk_rights_id`),
  CONSTRAINT `hfr_role_rights_ibfk_1` FOREIGN KEY (`fk_role_id`) REFERENCES `hfr_role` (`role_id`),
  CONSTRAINT `hfr_role_rights_ibfk_2` FOREIGN KEY (`fk_rights_id`) REFERENCES `hfr_rights` (`rights_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1043 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_role_rights` */

insert  into `hfr_role_rights`(`role_right_id`,`fk_role_id`,`fk_rights_id`) values (999,13,1),(1000,13,5),(1001,13,14),(1002,13,15),(1003,13,16),(1004,13,17),(1005,13,6),(1006,13,18),(1007,13,19),(1008,13,20),(1009,13,21),(1010,13,7),(1011,13,22),(1012,13,23),(1013,13,24),(1014,13,25),(1015,13,26),(1016,13,44),(1017,13,2),(1018,13,8),(1019,13,27),(1020,13,28),(1021,13,29),(1022,13,30),(1023,13,31),(1024,13,9),(1025,13,32),(1026,13,33),(1027,13,10),(1028,13,34),(1029,13,3),(1030,13,11),(1031,13,35),(1032,13,36),(1033,13,37),(1034,13,38),(1035,13,12),(1036,13,39),(1037,13,40),(1038,13,41),(1039,13,42),(1040,13,4),(1041,13,13),(1042,13,43);

/*Table structure for table `hfr_subject` */

DROP TABLE IF EXISTS `hfr_subject`;

CREATE TABLE `hfr_subject` (
  `subject_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '科目id',
  `subject_name` varchar(20) DEFAULT NULL COMMENT '科目名称',
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_subject` */

/*Table structure for table `hfr_test` */

DROP TABLE IF EXISTS `hfr_test`;

CREATE TABLE `hfr_test` (
  `test_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '试卷id',
  `test_name` varchar(20) DEFAULT NULL COMMENT '试卷标题',
  `test_beforetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最早可考试开始时间',
  `test_aftertime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最晚可提交试卷结束时间',
  `test_timesum` int(11) DEFAULT NULL COMMENT '考试时长，单位：分钟',
  `test_topicsum` int(11) DEFAULT NULL COMMENT '试题总数，计算得出',
  `test_scores` int(11) DEFAULT NULL COMMENT '试卷总分值，计算得出',
  `test_pass` int(11) DEFAULT NULL COMMENT '试卷及格分数线',
  `test_addtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '试卷添加时间',
  `fk_test_grade_id` bigint(20) DEFAULT NULL COMMENT '外键：试卷所属班级',
  `fk_test_subject_id` bigint(20) DEFAULT NULL COMMENT '外键：试卷所属科目',
  `test_select_one_sum` int(11) DEFAULT NULL COMMENT '单选题数',
  `test_select_more_sum` int(11) DEFAULT NULL COMMENT '多选题数',
  `test_judge_sum` int(11) DEFAULT NULL COMMENT '判断题数',
  `test_simple_sum` int(11) DEFAULT NULL COMMENT '简单题数',
  `test_middle_sum` int(11) DEFAULT NULL COMMENT '中等题数',
  `test_diff_sum` int(11) DEFAULT NULL COMMENT '高难题数',
  PRIMARY KEY (`test_id`),
  KEY `fk_test_subject_id` (`fk_test_subject_id`),
  KEY `fk_test_grade_id` (`fk_test_grade_id`),
  CONSTRAINT `hfr_test_ibfk_1` FOREIGN KEY (`fk_test_subject_id`) REFERENCES `hfr_subject` (`subject_id`),
  CONSTRAINT `hfr_test_ibfk_2` FOREIGN KEY (`fk_test_grade_id`) REFERENCES `hfr_grade` (`grade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_test` */

/*Table structure for table `hfr_test_topic` */

DROP TABLE IF EXISTS `hfr_test_topic`;

CREATE TABLE `hfr_test_topic` (
  `test_topic_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fk_test_id` bigint(20) DEFAULT NULL COMMENT 'test表外键',
  `fk_topic_id` bigint(20) DEFAULT NULL COMMENT 'topic表外键',
  PRIMARY KEY (`test_topic_id`),
  KEY `fk_test_id` (`fk_test_id`),
  KEY `fk_topic_id` (`fk_topic_id`),
  CONSTRAINT `hfr_test_topic_ibfk_1` FOREIGN KEY (`fk_test_id`) REFERENCES `hfr_test` (`test_id`),
  CONSTRAINT `hfr_test_topic_ibfk_2` FOREIGN KEY (`fk_topic_id`) REFERENCES `hfr_topic` (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_test_topic` */

/*Table structure for table `hfr_test_user_q` */

DROP TABLE IF EXISTS `hfr_test_user_q`;

CREATE TABLE `hfr_test_user_q` (
  `test_user_q_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `test_user_q_lasttime` int(11) DEFAULT NULL COMMENT '考试剩余时间，单位 秒',
  `test_user_q_starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '该用户开始考试时间',
  `test_user_q_committime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '该用户交卷时间',
  `test_user_q_score` int(11) DEFAULT NULL COMMENT '该用户考试成绩',
  `fk_test_id` bigint(20) DEFAULT NULL COMMENT '试卷',
  `fk_user_q_id` bigint(20) DEFAULT NULL COMMENT '用户',
  PRIMARY KEY (`test_user_q_id`),
  KEY `fk_test_id` (`fk_test_id`),
  KEY `fk_user_q_id` (`fk_user_q_id`),
  CONSTRAINT `hfr_test_user_q_ibfk_1` FOREIGN KEY (`fk_test_id`) REFERENCES `hfr_test` (`test_id`),
  CONSTRAINT `hfr_test_user_q_ibfk_2` FOREIGN KEY (`fk_user_q_id`) REFERENCES `hfr_user_q` (`user_q_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_test_user_q` */

/*Table structure for table `hfr_topic` */

DROP TABLE IF EXISTS `hfr_topic`;

CREATE TABLE `hfr_topic` (
  `topic_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '试题id',
  `topic_title` varchar(500) DEFAULT NULL COMMENT '试题题目',
  `topic_type` tinyint(1) DEFAULT NULL COMMENT '试题类型，0：单选，1：多选，2：判断',
  `topic_select_a` varchar(200) DEFAULT NULL COMMENT '选择题选项a描述',
  `topic_select_b` varchar(200) DEFAULT NULL COMMENT '选择题选项b描述',
  `topic_select_c` varchar(200) DEFAULT NULL COMMENT '选择题选项c描述',
  `topic_select_d` varchar(200) DEFAULT NULL COMMENT '选择题选项d描述',
  `topic_yesanswer` varchar(1000) DEFAULT NULL COMMENT '正确答案',
  `topic_score` int(11) DEFAULT NULL COMMENT '分值',
  `topic_level` tinyint(1) DEFAULT NULL COMMENT '难易程度，0：简单，1：中等，2：高难',
  `topic_addtime` timestamp NULL DEFAULT NULL COMMENT '添加时间',
  `fk_topic_subject_id` bigint(20) DEFAULT NULL COMMENT '外键：试题所属科目',
  PRIMARY KEY (`topic_id`),
  KEY `fk_topic_subject_id` (`fk_topic_subject_id`),
  CONSTRAINT `hfr_topic_ibfk_1` FOREIGN KEY (`fk_topic_subject_id`) REFERENCES `hfr_subject` (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_topic` */

/*Table structure for table `hfr_user` */

DROP TABLE IF EXISTS `hfr_user`;

CREATE TABLE `hfr_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_account` varchar(30) DEFAULT NULL COMMENT '登录账号',
  `user_realname` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `user_password` varchar(60) DEFAULT NULL COMMENT '登录密码',
  `user_salt` varchar(60) DEFAULT NULL COMMENT '盐值',
  `user_addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `fk_user_role_id` bigint(20) DEFAULT NULL COMMENT '外键：所属角色',
  PRIMARY KEY (`user_id`),
  KEY `fk_user_role_id` (`fk_user_role_id`),
  CONSTRAINT `hfr_user_ibfk_2` FOREIGN KEY (`fk_user_role_id`) REFERENCES `hfr_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_user` */

insert  into `hfr_user`(`user_id`,`user_account`,`user_realname`,`user_password`,`user_salt`,`user_addtime`,`fk_user_role_id`) values (28,'demo_user','演示账户','8ddda30932eb975cc4e6204ea474a691','10266614a2eb4d4eafd0ac05ccb4acad','2020-03-29 20:39:19',13);

/*Table structure for table `hfr_user_q` */

DROP TABLE IF EXISTS `hfr_user_q`;

CREATE TABLE `hfr_user_q` (
  `user_q_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_q_account` varchar(30) DEFAULT NULL COMMENT '登录账号',
  `user_q_realname` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `user_q_password` varchar(60) DEFAULT NULL COMMENT '登录密码',
  `user_q_salt` varchar(60) DEFAULT NULL COMMENT '盐值',
  `user_q_addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `fk_user_q_grade_id` bigint(20) DEFAULT NULL COMMENT '外键：所属班级',
  PRIMARY KEY (`user_q_id`),
  KEY `fk_user_grade_id` (`fk_user_q_grade_id`),
  CONSTRAINT `hfr_user_q_ibfk_1` FOREIGN KEY (`fk_user_q_grade_id`) REFERENCES `hfr_grade` (`grade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12332 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_user_q` */

/*Table structure for table `hfr_user_q_topic` */

DROP TABLE IF EXISTS `hfr_user_q_topic`;

CREATE TABLE `hfr_user_q_topic` (
  `user_q_topic_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_q_topic_useranswer` varchar(10) DEFAULT NULL COMMENT '用户在该题的答案',
  `fk_topic_id` bigint(20) DEFAULT NULL,
  `fk_test_id` bigint(20) DEFAULT NULL,
  `fk_user_q_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_q_topic_id`),
  KEY `fk_topic_id` (`fk_topic_id`),
  KEY `fk_test_id` (`fk_test_id`),
  KEY `fk_user_q_id` (`fk_user_q_id`),
  CONSTRAINT `hfr_user_q_topic_ibfk_1` FOREIGN KEY (`fk_topic_id`) REFERENCES `hfr_topic` (`topic_id`),
  CONSTRAINT `hfr_user_q_topic_ibfk_2` FOREIGN KEY (`fk_test_id`) REFERENCES `hfr_test` (`test_id`),
  CONSTRAINT `hfr_user_q_topic_ibfk_3` FOREIGN KEY (`fk_user_q_id`) REFERENCES `hfr_user_q` (`user_q_id`)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8;

/*Data for the table `hfr_user_q_topic` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
