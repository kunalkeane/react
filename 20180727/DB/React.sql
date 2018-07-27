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
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`id`,`text`,`user`,`updatetime`,`edited`) VALUES 
 (124,'#ReactJS I started loving it!!','kuaggarw','2018-07-15 10:51:04',NULL),
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
 (155,'#FIFA Messi is on the verge of retirement!!','kuaggarw','2018-07-15 21:27:21',NULL),
 (156,'#FoodieFriday wish we\'re also invited for treats!! @Test team pls make a note :)','kuaggarw','2018-07-15 21:28:14',NULL),
 (161,'#FIFA France is leading with 2-1 against Croatia!!','kuaggarw','2018-07-15 21:42:32',NULL),
 (165,'#Techfiesta was a great learning experience. Got a chance to meet different people!!','kuaggarw','2018-07-16 20:29:53',NULL),
 (166,'#teCHFiesta Hi Kunal','kuaggarw','2018-07-20 12:24:15',NULL),
 (167,'techfiesta Hi Gopi','kuaggarw','2018-07-20 12:24:50',NULL),
 (180,'#FIFA #Techfiesta Testing Testing','kuaggarw','2018-07-21 00:18:26',NULL),
 (181,'ffdfdf\nfdgfg','tomcat','2018-07-21 18:50:35',NULL),
 (182,'','tomcat','2018-07-21 18:53:22',NULL),
 (183,'','tomcat','2018-07-21 18:53:24',NULL),
 (184,'','tomcat','2018-07-21 18:53:56',NULL),
 (186,'Lets write #something here','tomcat','2018-07-21 18:59:20',NULL),
 (188,'#SOMETHing  to test the case senstivity....','tomcat','2018-07-21 21:29:20',NULL),
 (189,'#SqueaKY Not bad!!','tomcat','2018-07-21 21:29:45',NULL),
 (190,'#ReactJS hey kunal!! you really stink at CSS and esthetics :-()','kuaggarw','2018-07-22 09:34:12',NULL),
 (191,'#Bootstrap is really cool!!','kuaggarw','2018-07-22 10:12:48',NULL),
 (192,'#Bootstrap this is the largest possible comment the system will accept. Longer will be curtailed!!!!','kuaggarw','2018-07-22 11:26:47',NULL),
 (196,'#Twelve hash tag number 12!!','kuaggarw','2018-07-24 22:21:02',NULL),
 (198,'checking without hashtag','kuaggarw','2018-07-25 09:18:15',NULL),
 (200,'#PPP...','kuaggarw','2018-07-26 20:36:09',NULL),
 (202,'#Cache testing','kuaggarw','2018-07-26 23:37:36',NULL),
 (205,'#AI #Infoquest Abhishek took a wonderful session on AI..','kuaggarw','2018-07-27 10:25:07',NULL),
 (206,'#FIFA France is leading with 2-1 against Croatia!','kuaggarw','2018-07-27 10:25:49',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hashtag`
--

/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
INSERT INTO `hashtag` (`id`,`comment_id`,`hashtag`) VALUES 
 (113,124,'ReactJS'),
 (117,128,'ReactJS'),
 (118,129,'Infoquest'),
 (120,131,'Squeaky'),
 (122,133,'Squeaky'),
 (125,142,'FIFA'),
 (132,149,'Summer'),
 (133,150,'Onemore'),
 (138,155,'FIFA'),
 (139,156,'FoodieFriday'),
 (144,161,'FIFA'),
 (148,165,'Techfiesta'),
 (149,166,'teCHFiesta'),
 (166,180,'FIFA'),
 (167,180,'Techfiesta'),
 (168,186,'something'),
 (170,188,'SOMETHing'),
 (171,189,'SqueaKY'),
 (172,190,'ReactJS'),
 (173,191,'Bootstrap'),
 (174,192,'Bootstrap'),
 (180,196,'Twelve'),
 (183,200,'PPP...'),
 (185,202,'Cache'),
 (190,205,'AI'),
 (191,205,'Infoquest'),
 (192,206,'FIFA');
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;


--
-- Definition of table `likes`
--

DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes` (
  `comment_id` int(10) unsigned NOT NULL,
  `user` varchar(45) NOT NULL,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `likes`
--

/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` (`comment_id`,`user`,`updatetime`) VALUES 
 (161,'kuaggarw','0000-00-00 00:00:00'),
 (161,'kuaggarw','0000-00-00 00:00:00'),
 (166,'kuaggarw','0000-00-00 00:00:00'),
 (156,'kuaggarw','0000-00-00 00:00:00'),
 (149,'kuaggarw','0000-00-00 00:00:00'),
 (168,'kuaggarw','0000-00-00 00:00:00'),
 (167,'kuaggarw','0000-00-00 00:00:00'),
 (169,'kuaggarw','0000-00-00 00:00:00'),
 (169,'tomcat','0000-00-00 00:00:00'),
 (205,'tomcat','0000-00-00 00:00:00'),
 (180,'kuaggarw','0000-00-00 00:00:00'),
 (180,'tomcat','0000-00-00 00:00:00'),
 (149,'tomcat','0000-00-00 00:00:00'),
 (186,'tomcat','0000-00-00 00:00:00'),
 (189,'tomcat','0000-00-00 00:00:00'),
 (138,'kuaggarw','0000-00-00 00:00:00'),
 (190,'kuaggarw','0000-00-00 00:00:00'),
 (189,'kuaggarw','0000-00-00 00:00:00'),
 (181,'kuaggarw','0000-00-00 00:00:00'),
 (205,'kuaggarw','0000-00-00 00:00:00'),
 (196,'kuaggarw','2018-07-24 20:08:17'),
 (198,'kuaggarw','2018-07-24 22:21:19'),
 (192,'kuaggarw','2018-07-25 09:18:18'),
 (200,'kuaggarw','2018-07-26 23:38:30');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;


--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `userid` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`userid`,`email`) VALUES 
 (NULL,'kunal.aggarwal@capgemini.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
