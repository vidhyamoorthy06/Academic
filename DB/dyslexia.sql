# Host: localhost  (Version: 5.0.24a-community-nt)
# Date: 2016-03-21 14:18:40
# Generator: MySQL-Front 5.2  (Build 5.106)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

DROP DATABASE IF EXISTS `dyslexia`;
CREATE DATABASE `dyslexia` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dyslexia`;

#
# Source for table "childaccdetails"
#

DROP TABLE IF EXISTS `childaccdetails`;
CREATE TABLE `childaccdetails` (
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `dob` varchar(255) default NULL,
  `staff` varchar(255) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "childaccdetails"
#

INSERT INTO `childaccdetails` VALUES ('ram','ram','11/02/98','siva'),('said','sa','11/11/11','saran'),('s','s','11/11/11','siva');

#
# Source for table "childdetails"
#

DROP TABLE IF EXISTS `childdetails`;
CREATE TABLE `childdetails` (
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "childdetails"
#


#
# Source for table "childmarks"
#

DROP TABLE IF EXISTS `childmarks`;
CREATE TABLE `childmarks` (
  `username` varchar(255) default NULL,
  `p` varchar(255) default '0',
  `q` varchar(255) default '0',
  `b` varchar(255) default '0',
  `d` varchar(255) default '0',
  `m` varchar(255) default '0',
  `w` varchar(255) default '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "childmarks"
#

INSERT INTO `childmarks` VALUES ('ram','5','4','5','5','0','0'),('said','2','2','0','0','0','0'),('s','4','4','2','2','0','0');

#
# Source for table "staffaccdetails"
#

DROP TABLE IF EXISTS `staffaccdetails`;
CREATE TABLE `staffaccdetails` (
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `dob` varchar(255) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "staffaccdetails"
#

INSERT INTO `staffaccdetails` VALUES ('siva','siva','11/11/92'),('ram','ram','11/12/1992'),('ragu','ragu','11/11/11'),('saran','saran','27/01/94');

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
