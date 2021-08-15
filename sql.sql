/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.20 : Database - course
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`course` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `course`;

/*Table structure for table `course` */

CREATE TABLE `course` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `startlesson` int DEFAULT NULL,
  `endlesson` int DEFAULT NULL,
  `startweek` int DEFAULT NULL,
  `endweek` int DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `course` */

insert  into `course`(`cid`,`name`,`score`,`startlesson`,`endlesson`,`startweek`,`endweek`) values 
(1,'数据结构',4,1,4,1,12),
(2,'工科数学分析',6,5,8,1,16),
(3,'操作系统',4,3,4,1,12),
(5,'计算机网络',3,1,2,1,12),
(6,'软件项目管理',4,3,4,1,16),
(8,'大物',4,1,4,9,16),
(9,'商务英语',1.5,1,2,1,16),
(10,'职场英语',2,3,4,9,16);

/*Table structure for table `grade` */

CREATE TABLE `grade` (
  `gid` int NOT NULL AUTO_INCREMENT,
  `cid` int DEFAULT NULL,
  `sid` int DEFAULT NULL,
  `tid` int DEFAULT NULL,
  `score` float DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `grade` */

insert  into `grade`(`gid`,`cid`,`sid`,`tid`,`score`) values 
(7,2,1,8,87.5),
(17,2,5,8,100),
(18,7,1,4,0),
(19,5,1,15,0),
(20,6,1,16,0),
(21,9,1,4,50);

/*Table structure for table `mail` */

CREATE TABLE `mail` (
  `uid` int NOT NULL,
  `mail` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `mail` */

insert  into `mail`(`uid`,`mail`) values 
(1,'2236913183@qq.com'),
(2,NULL),
(3,'1540534243@qq.com'),
(4,'2927345821@qq.com'),
(5,'1044138200@qq.com'),
(7,'835165646@qq.com'),
(8,'2657443323@qq.com'),
(14,'1255983725@qq.com');

/*Table structure for table `student` */

CREATE TABLE `student` (
  `sid` int NOT NULL,
  `sname` varchar(40) DEFAULT NULL,
  `grade` varchar(40) DEFAULT NULL,
  `classMsg` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `student` */

insert  into `student`(`sid`,`sname`,`grade`,`classMsg`) values 
(1,'zhr','大三','软1802班'),
(3,'lxy','大三','软1802班'),
(5,'gyx','大三','软1802班'),
(7,'zqq','大三','软1802班'),
(17,'zzz','大四','软1802班');

/*Table structure for table `teacher` */

CREATE TABLE `teacher` (
  `tid` int NOT NULL,
  `tname` varchar(40) DEFAULT NULL,
  `office` varchar(40) DEFAULT NULL,
  `station` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `teacher` */

insert  into `teacher`(`tid`,`tname`,`office`,`station`) values 
(4,'yxw','综合楼4楼407','20'),
(8,'xzk','综合楼4楼407','30'),
(14,'xf','综合楼4楼408','60');

/*Table structure for table `user` */

CREATE TABLE `user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(40) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `power` int DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`uid`,`uname`,`password`,`power`) values 
(1,'zhr','zhr',3),
(2,'admin','admin',1),
(3,'lxy','lxy',3),
(4,'yxw','yxw',2),
(5,'gyx','gyx',3),
(7,'zqq','zqq',3),
(8,'xzk','xzk',2),
(14,'xf','xf',2),
(17,'zzz','zzz',3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
