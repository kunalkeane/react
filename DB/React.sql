-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.51-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema react
--

CREATE DATABASE IF NOT EXISTS react;
USE react;

--
-- Definition of table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `text` varchar(450) NOT NULL,
  `user` varchar(45) NOT NULL,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `edited` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`id`,`text`,`user`,`updatetime`,`edited`) VALUES 
 (124,'#ReactJS I started loving it!!','kuaggarw','2018-07-15 10:51:04',NULL),
 (127,'#Techfiesta was a great learning experience. Got a chance to meet different people!','kuaggarw','2018-07-15 10:52:38',NULL),
 (128,'Need to explore more on #ReactJS ... any body up for the task?? ','kuaggarw','2018-07-15 10:53:23',NULL),
 (129,'#Infoquest it\'s awesome!! Love it!!','kuaggarw','2018-07-15 10:53:59',NULL),
 (131,'#Squeaky this is positioned somewhere between facebook and twitter!!','kuaggarw','2018-07-15 10:56:12',NULL),
 (133,'#Squeaky a platform for chatteratis!! Hope ya enjoyed it','kuaggarw','2018-07-15 10:57:57',NULL),
 (136,'hey kunal!! you really stink at CSS and esthetics :-(','kuaggarw','2018-07-15 16:40:34',NULL),
 (137,'dsdds','kuaggarw','2018-07-15 16:41:20',NULL),
 (138,'klklklk','kuaggarw','2018-07-15 16:42:01',NULL),
 (142,'#FIFA France vs Croatia','kuaggarw','2018-07-15 17:03:55',NULL),
 (149,'#Summer of 69!','kuaggarw','2018-07-15 17:17:06',NULL),
 (150,'#Onemore some comment','kuaggarw','2018-07-15 17:17:37',NULL),
 (151,'#ReactJS hey kunal!! you really stink at CSS and esthetics :-(','kuaggarw','2018-07-15 17:19:38',NULL),
 (155,'#FIFA Messi is on the verge of retirement!!','kuaggarw','2018-07-15 21:27:21',NULL),
 (156,'#FoodieFriday wish we\'re also invited for treats!! @Test team pls make a note :)','kuaggarw','2018-07-15 21:28:14',NULL),
 (161,'#FIFA France is leading with 2-1 against Croatia!!','kuaggarw','2018-07-15 21:42:32',NULL),
 (162,'#FIFA France is leading with 2-1 against Croatia!!','kuaggarw','2018-07-15 21:42:50',NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;


--
-- Definition of table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
CREATE TABLE `hashtag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `comment_id` int(10) unsigned NOT NULL,
  `hashtag` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hashtag`
--

/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
INSERT INTO `hashtag` (`id`,`comment_id`,`hashtag`) VALUES 
 (113,124,'ReactJS'),
 (116,127,'Techfiesta'),
 (117,128,'ReactJS'),
 (118,129,'Infoquest'),
 (120,131,'Squeaky'),
 (122,133,'Squeaky'),
 (125,142,'FIFA'),
 (132,149,'Summer'),
 (133,150,'Onemore'),
 (134,151,'ReactJS'),
 (138,155,'FIFA'),
 (139,156,'FoodieFriday'),
 (144,161,'FIFA'),
 (145,162,'FIFA');
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
