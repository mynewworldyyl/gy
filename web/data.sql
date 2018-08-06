-- MySQL dump 10.10
--
-- Host: localhost    Database: hy
-- ------------------------------------------------------
-- Server version	5.0.15-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_account`
--

DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` bigint(20) NOT NULL,
  `addr_l1` varchar(64) default NULL,
  `addr_l2` varchar(64) default NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(128) default NULL,
  `email` varchar(64) default NULL,
  `home_phone` varchar(64) default NULL,
  `icon_url` varchar(128) default NULL,
  `mobile` varchar(64) default NULL,
  `nick_name` varchar(64) default NULL,
  `office_phone` varchar(64) default NULL,
  `pwd` varchar(64) default NULL,
  `remark` varchar(128) default NULL,
  `status` varchar(64) default NULL,
  `type_code` varchar(12) NOT NULL,
  `updated_on` datetime NOT NULL,
  `acct_name` varchar(64) default NULL,
  `client_id` varchar(8) NOT NULL,
  `integral` int(11) default NULL,
  `level` int(11) default NULL,
  `level_name` varchar(255) default NULL,
  `openid` varchar(255) default NULL,
  `qq` varchar(255) default NULL,
  `registed` bit(1) default NULL,
  `total_integral` int(11) default NULL,
  `unionid` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_10y3sqkbms3tmyv69xp9qs9nv` (`client_id`),
  CONSTRAINT `FK_10y3sqkbms3tmyv69xp9qs9nv` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_account`
--


/*!40000 ALTER TABLE `t_account` DISABLE KEYS */;
LOCK TABLES `t_account` WRITE;
INSERT INTO `t_account` VALUES (0,'addr1','addr2','2015-03-05 19:42:53','admin',NULL,'1234','icon','1111','admin','1111','888888','','Active','Admin','2015-03-05 19:42:53','Admin','2',NULL,NULL,NULL,NULL,NULL,'',NULL,NULL),(1,NULL,NULL,'2015-03-05 20:23:52',NULL,NULL,NULL,'/bbs/file/download?fileId=5515571aab0f5f840c4ed9e4','13266878991','6636125',NULL,'UsaeOlczEIGCMzHE5p0/Lg==',NULL,'Normal','Normal','2015-03-05 20:23:52','13266878991','2',NULL,NULL,NULL,NULL,NULL,'',NULL,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_account` ENABLE KEYS */;

--
-- Table structure for table `t_attachment`
--

DROP TABLE IF EXISTS `t_attachment`;
CREATE TABLE `t_attachment` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(12) NOT NULL,
  `file_name` varchar(12) NOT NULL,
  `remark` varchar(12) NOT NULL,
  `status` varchar(12) NOT NULL,
  `type_code` varchar(12) NOT NULL,
  `updated_on` datetime NOT NULL,
  `url` varchar(12) NOT NULL,
  `acct_name` varchar(12) NOT NULL,
  `client_id` varchar(8) NOT NULL,
  `feedback_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_p67jw66mmdprruuvvoofnp522` (`client_id`),
  KEY `FK_7um12bg23fusjmuih4h2uqgve` (`feedback_id`),
  CONSTRAINT `FK_7um12bg23fusjmuih4h2uqgve` FOREIGN KEY (`feedback_id`) REFERENCES `t_feedback` (`id`),
  CONSTRAINT `FK_p67jw66mmdprruuvvoofnp522` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_attachment`
--


/*!40000 ALTER TABLE `t_attachment` DISABLE KEYS */;
LOCK TABLES `t_attachment` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_attachment` ENABLE KEYS */;

--
-- Table structure for table `t_attr`
--

DROP TABLE IF EXISTS `t_attr`;
CREATE TABLE `t_attr` (
  `id` bigint(20) NOT NULL,
  `belong_id` bigint(20) NOT NULL,
  `created_on` datetime NOT NULL,
  `model` varchar(32) default NULL,
  `name` varchar(128) default NULL,
  `type` varchar(32) default NULL,
  `updated_on` datetime NOT NULL,
  `value` varchar(128) default NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_tbff9uqpi7upp0xib22edr6rj` (`client_id`),
  KEY `FK_4eeh7q6839gwx1ter71h5x8e2` (`created_by`),
  KEY `FK_cfgqm0iwq7plie4rj5qs0voa` (`updated_by`),
  CONSTRAINT `FK_4eeh7q6839gwx1ter71h5x8e2` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_cfgqm0iwq7plie4rj5qs0voa` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_tbff9uqpi7upp0xib22edr6rj` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_attr`
--


/*!40000 ALTER TABLE `t_attr` DISABLE KEYS */;
LOCK TABLES `t_attr` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_attr` ENABLE KEYS */;

--
-- Table structure for table `t_client`
--

DROP TABLE IF EXISTS `t_client`;
CREATE TABLE `t_client` (
  `id` varchar(8) NOT NULL,
  `created_on` datetime NOT NULL,
  `description` varchar(255) default NULL,
  `name` varchar(64) default NULL,
  `remark` varchar(255) default NULL,
  `updated_on` datetime NOT NULL,
  `parent_id` varchar(8) default NULL,
  `typecode` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_4lcyrn61901opm9q4tpkqo8iv` (`name`),
  KEY `FK_9k01kdwcjjc8ca5oajrhn1hxj` (`parent_id`),
  KEY `FK_15uyuj58ik116mijyi3797h63` (`typecode`),
  CONSTRAINT `FK_15uyuj58ik116mijyi3797h63` FOREIGN KEY (`typecode`) REFERENCES `t_client_type` (`id`),
  CONSTRAINT `FK_9k01kdwcjjc8ca5oajrhn1hxj` FOREIGN KEY (`parent_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_client`
--


/*!40000 ALTER TABLE `t_client` DISABLE KEYS */;
LOCK TABLES `t_client` WRITE;
INSERT INTO `t_client` VALUES ('1','2015-03-05 19:42:53','Admin','Admin','Admin','2015-03-05 19:42:53',NULL,'1'),('2','2015-03-05 19:42:53','Common','Common','Common','2015-03-05 19:42:53',NULL,'2');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_client` ENABLE KEYS */;

--
-- Table structure for table `t_client_type`
--

DROP TABLE IF EXISTS `t_client_type`;
CREATE TABLE `t_client_type` (
  `id` varchar(64) NOT NULL,
  `created_on` datetime NOT NULL,
  `description` varchar(255) default NULL,
  `name` varchar(64) NOT NULL,
  `typecode` varchar(20) NOT NULL,
  `updated_on` datetime NOT NULL,
  `created_by` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_8vhce252daqshc0a1v44wupi3` (`typecode`),
  KEY `FK_jk2uyofpeolie9454q885ac8y` (`created_by`),
  KEY `FK_b447d7507xdlo9abbe4bn4u19` (`updated_by`),
  CONSTRAINT `FK_b447d7507xdlo9abbe4bn4u19` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_jk2uyofpeolie9454q885ac8y` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_client_type`
--


/*!40000 ALTER TABLE `t_client_type` DISABLE KEYS */;
LOCK TABLES `t_client_type` WRITE;
INSERT INTO `t_client_type` VALUES ('1','2015-03-05 19:42:53','Admin','Admin','Admin','2015-03-05 19:42:53',NULL,NULL),('2','2015-03-05 19:42:53','Common','Common','Common','2015-03-05 19:42:53',NULL,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_client_type` ENABLE KEYS */;

--
-- Table structure for table `t_device`
--

DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `id` bigint(20) NOT NULL,
  `battery_fact` varchar(64) default NULL,
  `battery_type` varchar(64) default NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(64) default NULL,
  `ip` varchar(64) default NULL,
  `mac_addr` varchar(64) default NULL,
  `manufacture` varchar(64) default NULL,
  `max_duration` varchar(64) default NULL,
  `min_battery` varchar(64) default NULL,
  `model` varchar(64) default NULL,
  `name` varchar(64) default NULL,
  `power_in` varchar(64) default NULL,
  `power_out` varchar(64) default NULL,
  `produce_date` varchar(64) default NULL,
  `qr_code` varchar(64) default NULL,
  `remark` varchar(64) default NULL,
  `seria_num` varchar(64) default NULL,
  `sim_num` varchar(64) default NULL,
  `status` varchar(64) default NULL,
  `updated_on` datetime NOT NULL,
  `work_mode` varchar(64) default NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `device_type` bigint(20) NOT NULL,
  `manager_id` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  `is_delete` bit(1) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_9oe86brd5cfs3urmlxuq7m25j` (`client_id`),
  KEY `FK_pyuimkmkuxlbpxbu8t4hnniak` (`created_by`),
  KEY `FK_kcyy0my3ymvvy5ajgypw5x4mt` (`device_type`),
  KEY `FK_gqottasbqlr2ehqonxih9t7ur` (`manager_id`),
  KEY `FK_5ijl3m0isc0cetnv4m5fbonih` (`updated_by`),
  CONSTRAINT `FK_5ijl3m0isc0cetnv4m5fbonih` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_9oe86brd5cfs3urmlxuq7m25j` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`),
  CONSTRAINT `FK_gqottasbqlr2ehqonxih9t7ur` FOREIGN KEY (`manager_id`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_kcyy0my3ymvvy5ajgypw5x4mt` FOREIGN KEY (`device_type`) REFERENCES `t_device_type` (`id`),
  CONSTRAINT `FK_pyuimkmkuxlbpxbu8t4hnniak` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_device`
--


/*!40000 ALTER TABLE `t_device` DISABLE KEYS */;
LOCK TABLES `t_device` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_device` ENABLE KEYS */;

--
-- Table structure for table `t_device_bander`
--

DROP TABLE IF EXISTS `t_device_bander`;
CREATE TABLE `t_device_bander` (
  `id` bigint(20) NOT NULL,
  `birthday` varchar(255) default NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(128) default NULL,
  `height` int(11) default NULL,
  `icon_url` varchar(64) default NULL,
  `locat_frq` varchar(64) default NULL,
  `name` varchar(64) NOT NULL,
  `nick_name` varchar(64) default NULL,
  `remark` varchar(128) default NULL,
  `school_age` varchar(255) default NULL,
  `sex` varchar(1) default NULL,
  `updated_on` datetime NOT NULL,
  `weight` int(11) default NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `device_id` bigint(20) NOT NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_9qc3e8wynvj3f0w7d8fwxionu` (`client_id`),
  KEY `FK_escmf967rk51o9e15pqtxllkj` (`created_by`),
  KEY `FK_mi4ayhdtxfjd411dw7awn5gtu` (`device_id`),
  KEY `FK_fi087b2by81mppy2171sc9b32` (`updated_by`),
  CONSTRAINT `FK_9qc3e8wynvj3f0w7d8fwxionu` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`),
  CONSTRAINT `FK_escmf967rk51o9e15pqtxllkj` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_fi087b2by81mppy2171sc9b32` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_mi4ayhdtxfjd411dw7awn5gtu` FOREIGN KEY (`device_id`) REFERENCES `t_device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_device_bander`
--


/*!40000 ALTER TABLE `t_device_bander` DISABLE KEYS */;
LOCK TABLES `t_device_bander` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_device_bander` ENABLE KEYS */;

--
-- Table structure for table `t_device_locat_data`
--

DROP TABLE IF EXISTS `t_device_locat_data`;
CREATE TABLE `t_device_locat_data` (
  `id` bigint(20) NOT NULL,
  `alt_coor` varchar(32) default NULL,
  `collect_time` varchar(32) default NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(128) default NULL,
  `lat_coor` varchar(32) default NULL,
  `lon_coor` varchar(32) default NULL,
  `name` varchar(64) default NULL,
  `remark` varchar(128) default NULL,
  `updated_on` datetime NOT NULL,
  `client_id` varchar(8) default NULL,
  `created_by` bigint(20) default NULL,
  `device_id` bigint(20) NOT NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_jjjere7aheyg2c05lfdckgfvd` (`client_id`),
  KEY `FK_98kjutf6endvfxx7uafyq0lbv` (`created_by`),
  KEY `FK_6kdna5oacgp3e2xnv4keb40iw` (`device_id`),
  KEY `FK_81v8cckoybmk6t0w2xpsex3eo` (`updated_by`),
  CONSTRAINT `FK_6kdna5oacgp3e2xnv4keb40iw` FOREIGN KEY (`device_id`) REFERENCES `t_device` (`id`),
  CONSTRAINT `FK_81v8cckoybmk6t0w2xpsex3eo` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_98kjutf6endvfxx7uafyq0lbv` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_jjjere7aheyg2c05lfdckgfvd` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_device_locat_data`
--


/*!40000 ALTER TABLE `t_device_locat_data` DISABLE KEYS */;
LOCK TABLES `t_device_locat_data` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_device_locat_data` ENABLE KEYS */;

--
-- Table structure for table `t_device_msg`
--

DROP TABLE IF EXISTS `t_device_msg`;
CREATE TABLE `t_device_msg` (
  `id` bigint(20) NOT NULL,
  `content` varchar(32) default NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(128) default NULL,
  `msg_type` varchar(32) default NULL,
  `remark` varchar(128) default NULL,
  `resp_content` varchar(32) default NULL,
  `scene_type` varchar(32) default NULL,
  `send_time` datetime default NULL,
  `status` varchar(32) default NULL,
  `updated_on` datetime NOT NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `device_id` bigint(20) NOT NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_trmhdfmg1c0c07q2dro3latbu` (`client_id`),
  KEY `FK_e36k7lsv8omfs83bbr8cskkbk` (`created_by`),
  KEY `FK_95t2jtu97wrotlrx5eewvjcye` (`device_id`),
  KEY `FK_hoepdjyhu6ct3dovfbf90unqu` (`updated_by`),
  CONSTRAINT `FK_95t2jtu97wrotlrx5eewvjcye` FOREIGN KEY (`device_id`) REFERENCES `t_device` (`id`),
  CONSTRAINT `FK_e36k7lsv8omfs83bbr8cskkbk` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_hoepdjyhu6ct3dovfbf90unqu` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_trmhdfmg1c0c07q2dro3latbu` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_device_msg`
--


/*!40000 ALTER TABLE `t_device_msg` DISABLE KEYS */;
LOCK TABLES `t_device_msg` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_device_msg` ENABLE KEYS */;

--
-- Table structure for table `t_device_type`
--

DROP TABLE IF EXISTS `t_device_type`;
CREATE TABLE `t_device_type` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(128) default NULL,
  `name` varchar(32) default NULL,
  `remark` varchar(128) default NULL,
  `type_code` varchar(8) default NULL,
  `updated_on` datetime NOT NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `parent_id` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_c7ykfpicwmedihy1vskssees0` (`client_id`),
  KEY `FK_k28asqsnysar3ckxdw1hsppac` (`created_by`),
  KEY `FK_j8lcot10i2kh2b1s59h030icm` (`parent_id`),
  KEY `FK_2xmxc3ql9nrd4ehdodvv2bvat` (`updated_by`),
  CONSTRAINT `FK_2xmxc3ql9nrd4ehdodvv2bvat` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_c7ykfpicwmedihy1vskssees0` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`),
  CONSTRAINT `FK_j8lcot10i2kh2b1s59h030icm` FOREIGN KEY (`parent_id`) REFERENCES `t_device_type` (`id`),
  CONSTRAINT `FK_k28asqsnysar3ckxdw1hsppac` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_device_type`
--


/*!40000 ALTER TABLE `t_device_type` DISABLE KEYS */;
LOCK TABLES `t_device_type` WRITE;
INSERT INTO `t_device_type` VALUES (1,'2015-03-05 19:42:54','Common','Common','Common','Common','2015-03-05 19:42:54','2',0,NULL,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_device_type` ENABLE KEYS */;

--
-- Table structure for table `t_feedback`
--

DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback` (
  `id` bigint(20) NOT NULL,
  `content` varchar(8) default NULL,
  `desc0` varchar(8) default NULL,
  `email` varchar(8) default NULL,
  `nick_name` varchar(8) default NULL,
  `remark` varchar(8) default NULL,
  `status` varchar(8) default NULL,
  `type_code` varchar(8) default NULL,
  `acct_id` bigint(20) NOT NULL,
  `client_id` varchar(8) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_ro5qw25rns3fv98otqewjwqk0` (`acct_id`),
  KEY `FK_f3d2y3hiwgxrk4osvyvhfx533` (`client_id`),
  CONSTRAINT `FK_f3d2y3hiwgxrk4osvyvhfx533` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`),
  CONSTRAINT `FK_ro5qw25rns3fv98otqewjwqk0` FOREIGN KEY (`acct_id`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_feedback`
--


/*!40000 ALTER TABLE `t_feedback` DISABLE KEYS */;
LOCK TABLES `t_feedback` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_feedback` ENABLE KEYS */;

--
-- Table structure for table `t_ferif_code`
--

DROP TABLE IF EXISTS `t_ferif_code`;
CREATE TABLE `t_ferif_code` (
  `id` bigint(20) NOT NULL,
  `code` varchar(64) default NULL,
  `created_on` datetime NOT NULL,
  `mobile` varchar(64) default NULL,
  `remark` varchar(64) default NULL,
  `status` varchar(64) default NULL,
  `type_code` varchar(64) default NULL,
  `updated_on` datetime NOT NULL,
  `client_id` varchar(8) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_90aduyyjw1ydrqyjx3ihn59ct` (`client_id`),
  CONSTRAINT `FK_90aduyyjw1ydrqyjx3ihn59ct` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_ferif_code`
--


/*!40000 ALTER TABLE `t_ferif_code` DISABLE KEYS */;
LOCK TABLES `t_ferif_code` WRITE;
INSERT INTO `t_ferif_code` VALUES (1,'240533','2015-03-05 19:43:22','15818790696',NULL,'1001','1001','2015-03-05 19:43:22','2'),(101,'726373','2015-03-05 20:22:06','13266878991',NULL,'1001','1001','2015-03-05 20:22:06','2'),(256,'116074','2015-03-08 20:01:15','13266878991',NULL,'1001','1002','2015-03-08 20:01:15','2');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_ferif_code` ENABLE KEYS */;

--
-- Table structure for table `t_file`
--

DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` varchar(64) NOT NULL,
  `contentType` varchar(255) NOT NULL,
  `created_on` datetime NOT NULL,
  `data` longblob,
  `name` varchar(255) NOT NULL,
  `updated_on` datetime NOT NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  `mineType` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_87i2qknkjqt7kfpitafi5ycwd` (`client_id`),
  KEY `FK_og4dgd3jwtythrdt160517k3y` (`created_by`),
  KEY `FK_2h1qxq183woq69f01fyj3shp1` (`updated_by`),
  CONSTRAINT `FK_2h1qxq183woq69f01fyj3shp1` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_87i2qknkjqt7kfpitafi5ycwd` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`),
  CONSTRAINT `FK_og4dgd3jwtythrdt160517k3y` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_file`
--


/*!40000 ALTER TABLE `t_file` DISABLE KEYS */;
LOCK TABLES `t_file` WRITE;
INSERT INTO `t_file` VALUES ('012','File','2015-03-05 21:49:42','eyJjb25maWciOiJ7XCJmaXJzdFRpbWVcIjpcIjpdXCIsXCJwcm94eU1vZGVcIjpcImRpcmVjdFwiLFwiYXV0b1BhY1NjcmlwdFBhdGhcIjpcIjptZW1vcnk6XCIsXCJydWxlTGlzdFVybFwiOlwiaHR0cHM6Ly9hdXRvcHJveHktZ2Z3bGlzdC5nb29nbGVjb2RlLmNvbS9zdm4vdHJ1bmsvZ2Z3bGlzdC50eHRcIixcInJ1bGVMaXN0UmVsb2FkXCI6XCI3MjBcIixcInJ1bGVMaXN0UHJvZmlsZUlkXCI6XCJHb0FnZW50XCIsXCJydWxlTGlzdEF1dG9Qcm94eVwiOnRydWUsXCJzd2l0Y2hSdWxlc1wiOnRydWUsXCJydWxlTGlzdEVuYWJsZWRcIjp0cnVlLFwicGFjU2NyaXB0RGF0YVwiOlwiXCIsXCJwcm94eVNlcnZlclwiOlwiXCIsXCJxdWlja1N3aXRjaFwiOmZhbHNlLFwicXVpY2tTd2l0Y2hUeXBlXCI6XCJiaW5hcnlcIixcInJlYXBwbHlTZWxlY3RlZFByb2ZpbGVcIjp0cnVlLFwiY29uZmlybURlbGV0aW9uXCI6ZmFsc2UsXCJydWxlc0ZpcnN0VGltZVwiOlwiO11cIixcIm1vbml0b3JQcm94eUNoYW5nZXNcIjpmYWxzZSxcInByZXZlbnRQcm94eUNoYW5nZXNcIjpmYWxzZSxcImxhc3RMaXN0VXBkYXRlXCI6XCJXZWQgQXVnIDE0IDIwMTMgMjA6NDE6MDUgR01UKzA4MDAgKOS4reWbveagh+WHhuaXtumXtClcIixcInJlZnJlc2hUYWJcIjpmYWxzZSxcInN0YXJ0dXBQcm9maWxlSWRcIjpcIlwiLFwicHJveHlFeGNlcHRpb25zXCI6XCJcIixcInByb3h5Q29uZmlnVXJsXCI6XCJcIn0iLCJkZWZhdWx0UnVsZSI6IntcImlkXCI6XCJkZWZhdWx0UnVsZVwiLFwibmFtZVwiOlwiRGVmYXVsdCBSdWxlXCIsXCJ1cmxQYXR0ZXJuXCI6XCJcIixcInBhdHRlcm5UeXBlXCI6XCJ3aWxkY2FyZFwiLFwicHJvZmlsZUlkXCI6XCJkaXJlY3RcIn0iLCJwcm9maWxlcyI6IntcIkdvQWdlbnRcIjp7XCJuYW1lXCI6XCJHb0FnZW50XCIsXCJwcm94eU1vZGVcIjpcIm1hbnVhbFwiLFwicHJveHlIdHRwXCI6XCIxMjcuMC4wLjE6ODA4N1wiLFwidXNlU2FtZVByb3h5XCI6dHJ1ZSxcInByb3h5SHR0cHNcIjpcIlwiLFwicHJveHlGdHBcIjpcIlwiLFwicHJveHlTb2Nrc1wiOlwiXCIsXCJzb2Nrc1ZlcnNpb25cIjo0LFwicHJveHlFeGNlcHRpb25zXCI6XCJsb2NhbGhvc3Q7IDEyNy4wLjAuMTsgPGxvY2FsPlwiLFwicHJveHlDb25maWdVcmxcIjpcIlwiLFwiY29sb3JcIjpcImJsdWVcIixcImlkXCI6XCJHb0FnZW50XCJ9fSIsInF1aWNrU3dpdGNoUHJvZmlsZXMiOiJbXCJkaXJlY3RcIl0iLCJydWxlcyI6IntcIk5ldyBSdWxlMlwiOntcIm5hbWVcIjpcImdvb2dsZVwiLFwidXJsUGF0dGVyblwiOlwiKjovLyouZ29vZ2xlLmNvbS8qXCIsXCJwYXR0ZXJuVHlwZVwiOlwid2lsZGNhcmRcIixcInByb2ZpbGVJZFwiOlwiR29BZ2VudFwiLFwiaWRcIjpcIk5ldyBSdWxlMlwifSxcIk5ldyBSdWxlM1wiOntcIm5hbWVcIjpcImdvb2dsZVwiLFwidXJsUGF0dGVyblwiOlwiKjovLyouZ29vZ2xlLmNvbS4qLypcIixcInBhdHRlcm5UeXBlXCI6XCJ3aWxkY2FyZFwiLFwicHJvZmlsZUlkXCI6XCJHb0FnZW50XCIsXCJpZFwiOlwiTmV3IFJ1bGUzXCJ9LFwiTmV3IFJ1bGU1XCI6e1wibmFtZVwiOlwiZ29vZ2xlIGFuYWx5dGljc1wiLFwidXJsUGF0dGVyblwiOlwiKjovLyouZ29vZ2xlLWFuYWx5dGljcy5jb20vKlwiLFwicGF0dGVyblR5cGVcIjpcIndpbGRjYXJkXCIsXCJwcm9maWxlSWRcIjpcIkdvQWdlbnRcIixcImlkXCI6XCJOZXcgUnVsZTVcIn0sXCJOZXcgUnVsZTZcIjp7XCJuYW1lXCI6XCJnb29nbGUgYXBpc1wiLFwidXJsUGF0dGVyblwiOlwiKjovLyouZ29vZ2xlYXBpcy5jb20vKlwiLFwicGF0dGVyblR5cGVcIjpcIndpbGRjYXJkXCIsXCJwcm9maWxlSWRcIjpcIkdvQWdlbnRcIixcImlkXCI6XCJOZXcgUnVsZTZcIn0sXCJOZXcgUnVsZVwiOntcIm5hbWVcIjpcImdvb2dsZSBjb2RlXCIsXCJ1cmxQYXR0ZXJuXCI6XCIqOi8vKi5nb29nbGVjb2RlLmNvbS8qXCIsXCJwYXR0ZXJuVHlwZVwiOlwid2lsZGNhcmRcIixcInByb2ZpbGVJZFwiOlwiR29BZ2VudFwiLFwiaWRcIjpcIk5ldyBSdWxlXCJ9LFwiTmV3IFJ1bGU0XCI6e1wibmFtZVwiOlwiZ29vZ2xlIHVzZXJjb250ZW50XCIsXCJ1cmxQYXR0ZXJuXCI6XCIqOi8vKi5nb29nbGV1c2VyY29udGVudC5jb20vKlwiLFwicGF0dGVyblR5cGVcIjpcIndpbGRjYXJkXCIsXCJwcm9maWxlSWRcIjpcIkdvQWdlbnRcIixcImlkXCI6XCJOZXcgUnVsZTRcIn0sXCJOZXcgUnVsZTdcIjp7XCJuYW1lXCI6XCJuZXdzbXRoIGF0dFwiLFwidXJsUGF0dGVyblwiOlwiKjovL2F0dC5uZXdzbXRoLm5ldC8qXCIsXCJwYXR0ZXJuVHlwZVwiOlwid2lsZGNhcmRcIixcInByb2ZpbGVJZFwiOlwiR29BZ2VudFwiLFwiaWRcIjpcIk5ldyBSdWxlN1wifSxcImdvby5nbFwiOntcIm5hbWVcIjpcInNob3J0IGxpbmtcIixcInVybFBhdHRlcm5cIjpcIio6Ly9nb28uZ2wvKlwiLFwicGF0dGVyblR5cGVcIjpcIndpbGRjYXJkXCIsXCJwcm9maWxlSWRcIjpcIkdvQWdlbnRcIixcImlkXCI6XCJnb28uZ2xcIn0sXCJzaG9ydCBsaW5rXCI6e1wibmFtZVwiOlwic2hvcnQgbGlua1wiLFwidXJsUGF0dGVyblwiOlwiKjovLyouZmVlZHNwb3J0YWwuY29tLypcIixcInBhdHRlcm5UeXBlXCI6XCJ3aWxkY2FyZFwiLFwicHJvZmlsZUlkXCI6XCJHb0FnZW50XCIsXCJpZFwiOlwic2hvcnQgbGlua1wifSxcIk5ldyBSdWxlOFwiOntcIm5hbWVcIjpcIndpa2lwZWRpYVwiLFwidXJsUGF0dGVyblwiOlwiKjovLyoud2lraXBlZGlhLm9yZy8qXCIsXCJwYXR0ZXJuVHlwZVwiOlwid2lsZGNhcmRcIixcInByb2ZpbGVJZFwiOlwiR29BZ2VudFwiLFwiaWRcIjpcIk5ldyBSdWxlOFwifX0iLCJzZWxlY3RlZFByb2ZpbGUiOiJ7XCJuYW1lXCI6XCJb55u05o6l6L+e5o6lXVwiLFwicHJveHlNb2RlXCI6XCJkaXJlY3RcIixcInByb3h5SHR0cFwiOlwiXCIsXCJ1c2VTYW1lUHJveHlcIjp0cnVlLFwicHJveHlIdHRwc1wiOlwiXCIsXCJwcm94eUZ0cFwiOlwiXCIsXCJwcm94eVNvY2tzXCI6XCJcIixcInNvY2tzVmVyc2lvblwiOjQsXCJwcm94eUV4Y2VwdGlvbnNcIjpcIlwiLFwicHJveHlDb25maWdVcmxcIjpcIlwiLFwiY29sb3JcIjpcImluYWN0aXZlXCIsXCJpZFwiOlwiZGlyZWN0XCJ9In0=','SwitchyOptions.bak','2015-03-05 21:49:42','2',1,1,'');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_file` ENABLE KEYS */;

--
-- Table structure for table `t_file_attr`
--

DROP TABLE IF EXISTS `t_file_attr`;
CREATE TABLE `t_file_attr` (
  `id` varchar(64) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) default NULL,
  `client_id` varchar(8) NOT NULL,
  `file_id` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_7ifkaokfpxannuj9ya783umvw` (`client_id`),
  KEY `FK_bnrdp2upx90qm60qro44p267q` (`file_id`),
  CONSTRAINT `FK_7ifkaokfpxannuj9ya783umvw` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`),
  CONSTRAINT `FK_bnrdp2upx90qm60qro44p267q` FOREIGN KEY (`file_id`) REFERENCES `t_file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_file_attr`
--


/*!40000 ALTER TABLE `t_file_attr` DISABLE KEYS */;
LOCK TABLES `t_file_attr` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_file_attr` ENABLE KEYS */;

--
-- Table structure for table `t_footprint`
--

DROP TABLE IF EXISTS `t_footprint`;
CREATE TABLE `t_footprint` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(64) default NULL,
  `name` varchar(8) default NULL,
  `remark` varchar(64) default NULL,
  `status` varchar(8) default NULL,
  `type_code` varchar(8) default NULL,
  `updated_on` datetime NOT NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `device_id` bigint(20) NOT NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_hyg2rb1qy47v4kjpc13iu35x1` (`client_id`),
  KEY `FK_qy3gumno4vtpfe1i7sfjri6wd` (`created_by`),
  KEY `FK_etr09mrhmmkfkk3w1klhb8tva` (`device_id`),
  KEY `FK_un6v5t78i4r2n3fx9i6v5026` (`updated_by`),
  CONSTRAINT `FK_etr09mrhmmkfkk3w1klhb8tva` FOREIGN KEY (`device_id`) REFERENCES `t_device` (`id`),
  CONSTRAINT `FK_hyg2rb1qy47v4kjpc13iu35x1` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`),
  CONSTRAINT `FK_qy3gumno4vtpfe1i7sfjri6wd` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_un6v5t78i4r2n3fx9i6v5026` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_footprint`
--


/*!40000 ALTER TABLE `t_footprint` DISABLE KEYS */;
LOCK TABLES `t_footprint` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_footprint` ENABLE KEYS */;

--
-- Table structure for table `t_footprint_locat`
--

DROP TABLE IF EXISTS `t_footprint_locat`;
CREATE TABLE `t_footprint_locat` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime NOT NULL,
  `desc0` varchar(64) default NULL,
  `remark` varchar(64) default NULL,
  `updated_on` datetime NOT NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `dld_id` bigint(20) default NULL,
  `footprint_id` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_pwxpnp7nqt8aadorwft5vdr7f` (`client_id`),
  KEY `FK_lk5p3k3vs5c0qm1kfs9lmk4f7` (`created_by`),
  KEY `FK_qbyblfq42v0h9004j5shs6nba` (`dld_id`),
  KEY `FK_8nb9qig7ktp5av0rus1sc7jxt` (`footprint_id`),
  KEY `FK_hkq4baak9xecgepjlr4gbmgq1` (`updated_by`),
  CONSTRAINT `FK_8nb9qig7ktp5av0rus1sc7jxt` FOREIGN KEY (`footprint_id`) REFERENCES `t_footprint` (`id`),
  CONSTRAINT `FK_hkq4baak9xecgepjlr4gbmgq1` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_lk5p3k3vs5c0qm1kfs9lmk4f7` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_pwxpnp7nqt8aadorwft5vdr7f` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`),
  CONSTRAINT `FK_qbyblfq42v0h9004j5shs6nba` FOREIGN KEY (`dld_id`) REFERENCES `t_device_locat_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_footprint_locat`
--


/*!40000 ALTER TABLE `t_footprint_locat` DISABLE KEYS */;
LOCK TABLES `t_footprint_locat` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_footprint_locat` ENABLE KEYS */;

--
-- Table structure for table `t_good`
--

DROP TABLE IF EXISTS `t_good`;
CREATE TABLE `t_good` (
  `id` varchar(255) NOT NULL,
  `created_on` datetime NOT NULL,
  `sell_batch_id` tinyblob,
  `sell_price` float default NULL,
  `serial_id` tinyblob,
  `status` varchar(255) default NULL,
  `type_id` tinyblob,
  `updated_on` datetime NOT NULL,
  `buyer_id` bigint(20) default NULL,
  `created_by` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_9h1loa75f9irn470xevbxkrx3` (`buyer_id`),
  KEY `FK_c7x2oe1dgs1a9j86ak0b3gp9v` (`created_by`),
  KEY `FK_a557iain04j94fn7p8r9brywq` (`updated_by`),
  CONSTRAINT `FK_9h1loa75f9irn470xevbxkrx3` FOREIGN KEY (`buyer_id`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_a557iain04j94fn7p8r9brywq` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_c7x2oe1dgs1a9j86ak0b3gp9v` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_good`
--


/*!40000 ALTER TABLE `t_good` DISABLE KEYS */;
LOCK TABLES `t_good` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_good` ENABLE KEYS */;

--
-- Table structure for table `t_id_assignment`
--

DROP TABLE IF EXISTS `t_id_assignment`;
CREATE TABLE `t_id_assignment` (
  `id` bigint(20) NOT NULL auto_increment,
  `CLIENT_ID` varchar(255) default NULL,
  `ENTITY_ID` varchar(255) default NULL,
  `ID_INIT_VALUE` bigint(20) default NULL,
  `ID_STEP_LEN` int(11) default NULL,
  `ID_VALUE` bigint(20) default NULL,
  `ID_VALUE_TYPE` varchar(255) default NULL,
  `PREFIX_VALUE` int(11) default NULL,
  `PREFIX_VALUE_LEN` int(11) default NULL,
  `SERVER_ID` varchar(255) default NULL,
  `STATU` varchar(255) default NULL,
  `TABLE_NAME` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_id_assignment`
--


/*!40000 ALTER TABLE `t_id_assignment` DISABLE KEYS */;
LOCK TABLES `t_id_assignment` WRITE;
INSERT INTO `t_id_assignment` VALUES (1,'0','com.hy.entity.Feedback',1,1,1,'long',0,4,'0','using',''),(2,'0','com.hy.entity.Account',1,1,101,'long',0,4,'0','using',''),(3,'0','com.hy.entity.DeviceMessage',1,1,1,'long',0,4,'0','using',''),(4,'0','com.hy.base.ClientType',1,1,1,'string',0,4,'0','using',''),(5,'0','com.hy.entity.DeviceBander',1,1,1,'long',0,4,'0','using',''),(6,'0','com.hy.base.Client',1,1,1,'string',0,4,'0','using',''),(7,'0','com.hy.entity.DeviceType',1,1,1,'long',0,4,'0','using',''),(8,'0','com.hy.entity.VerificationCode',1,1,301,'long',0,4,'0','using',''),(9,'0','com.hy.entity.FootprintLocation',1,1,1,'long',0,4,'0','using',''),(10,'0','com.hy.entity.DeviceLocationData',1,1,1,'long',0,4,'0','using',''),(11,'0','com.hy.entity.Footprint',1,1,1,'long',0,4,'0','using',''),(12,'0','com.hy.base.profile.Profile',1,1,1,'string',0,4,'0','using',''),(13,'0','com.hy.entity.Device',1,1,1,'long',0,4,'0','using',''),(14,'0','com.hy.base.file.DbFile',1,1,13,'string',0,4,'0','using',''),(15,'0','com.hy.entity.SmsRecord',1,1,1,'long',0,4,'0','using',''),(16,'0','com.hy.entity.Attachment',1,1,1,'long',0,4,'0','using',''),(17,'0','com.hy.usercenter.ACAccount',1,1,155,'string',0,0,'0','using',''),(18,'0','com.hy.base.file.FileAttr',1,1,1,'string',0,4,'0','using',''),(19,'0','com.hy.bbs.Topic',1,1,601,'long',0,4,'0','using',''),(20,'0','com.hy.bbs.TopicType',1,1,301,'long',0,4,'0','using',''),(21,'0','com.hy.bbs.Note',1,1,501,'long',0,4,'0','using',''),(22,'0','com.hy.bbs.Attr',1,1,1,'long',0,4,'0','using',''),(23,'0','com.hy.base.mall.Good',1,1,1,'string',0,4,'0','using',''),(24,'0','com.hy.base.mall.Order',1,1,1,'string',0,4,'0','using',''),(25,'0','com.hy.entity.UserDeviceAsso',1,1,1,'long',0,4,'0','using',''),(26,'0','com.hy.base.mall.OrderNumId',1,1,1,'string',0,4,'0','using',''),(27,'0','com.hy.base.mall.GoodType',1,1,1,'string',0,4,'0','using',''),(28,'0','com.hy.base.mall.GoodSerial',1,1,1,'string',0,4,'0','using',''),(29,'0','com.hy.base.mall.Payment',1,1,1,'string',0,4,'0','using',''),(30,'0','com.hy.base.mall.SellBatch',1,1,1,'string',0,4,'0','using','');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_id_assignment` ENABLE KEYS */;

--
-- Table structure for table `t_note`
--

DROP TABLE IF EXISTS `t_note`;
CREATE TABLE `t_note` (
  `id` bigint(20) NOT NULL default '0',
  `content` varchar(1000) default NULL,
  `created_on` datetime default NULL,
  `updated_on` datetime default NULL,
  `client_id` varchar(8) default NULL,
  `updated_by` bigint(20) default NULL,
  `created_by` bigint(20) default NULL,
  `topic_id` bigint(20) default NULL,
  `title` varchar(128) default NULL,
  `oppose_num` int(11) default NULL,
  `seq` int(11) default NULL,
  `support_num` int(11) default NULL,
  `note_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `client_id` (`client_id`),
  KEY `updated_by` (`updated_by`),
  KEY `created_by` (`created_by`),
  KEY `topic_id` (`topic_id`),
  KEY `FK_nkf69tahs84mm6lwip7diiqkr` (`note_id`),
  CONSTRAINT `FK_nkf69tahs84mm6lwip7diiqkr` FOREIGN KEY (`note_id`) REFERENCES `t_note` (`id`),
  CONSTRAINT `t_note_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_note_ibfk_2` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_note_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_note_ibfk_4` FOREIGN KEY (`topic_id`) REFERENCES `t_topic` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_note`
--


/*!40000 ALTER TABLE `t_note` DISABLE KEYS */;
LOCK TABLES `t_note` WRITE;
INSERT INTO `t_note` VALUES (1,'<p>good1</p>','2015-03-19 11:23:36','2015-03-19 11:23:36','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(2,'<p>good2</p>','2015-03-19 11:24:34','2015-03-19 11:24:34','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(3,'<p>good2</p>','2015-03-19 11:24:45','2015-03-19 11:24:45','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(4,'<p>good3</p>','2015-03-19 11:36:00','2015-03-19 11:36:00','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(5,'<p>good4</p>','2015-03-19 11:37:38','2015-03-19 11:37:38','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(6,'<p>good5</p>','2015-03-19 11:40:50','2015-03-19 11:40:50','2',1,1,101,NULL,13,NULL,1,NULL),(7,'<p>good6</p>','2015-03-19 11:44:41','2015-03-19 11:44:41','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(8,'<p>good7</p>','2015-03-19 11:44:53','2015-03-19 11:44:53','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(9,'<p>good8</p>','2015-03-19 11:46:10','2015-03-19 11:46:10','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(10,'<p>good9</p>','2015-03-19 12:23:02','2015-03-19 12:23:02','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(11,'<p>如何实现刷新当前页面呢？借助js你将无所不能。</p><p><strong>1，reload 方法，该方法强迫浏览器刷新当前页面。<br/></strong>语法：location.reload([bForceGet])&nbsp;&nbsp; <br/>参数： bForceGet， 可选参数， 默认为 false，从客户端缓存里取当前页。true, 则以 GET 方式，从服务端取最新的页面, 相当于客户端点击 F5(&quot;刷新&quot;)</p><p><strong>2，replace 方法，该方法通过指定URL替换当前缓存在历史里（客户端）的项目，因此当使用replace方法之后，你不能通过“前进”和“后退”来访问已经被替换的URL。<br/></strong>语法： location.replace(URL)&nbsp;&nbsp; <br/>通常使用： location.reload() 或者是 history.go(0) 来做。</p><p><br/></p>','2015-03-19 12:23:54','2015-03-19 12:23:54','2',1,1,101,NULL,NULL,NULL,NULL,NULL),(101,'<p>good</p>','2015-03-20 13:49:14','2015-03-20 13:49:14','2',1,1,256,NULL,6,1,4,NULL),(102,'<p>good2</p>','2015-03-20 13:49:23','2015-03-20 13:49:23','2',1,1,256,NULL,4,2,4,NULL),(256,'<p>good</p>','2015-03-21 04:04:47','2015-03-21 04:04:47','2',1,1,301,NULL,3,1,1,NULL),(257,'<p>good</p>','2015-03-21 04:04:52','2015-03-21 04:04:52','2',1,1,301,NULL,0,2,3,NULL),(258,'<p>testdddd</p>','2015-03-21 12:15:29','2015-03-21 12:15:29','2',1,1,301,NULL,0,3,0,NULL),(259,'<p>good</p>','2015-03-21 14:25:31','2015-03-21 14:25:31','2',1,1,258,NULL,0,1,0,NULL),(260,'<p>good2</p>','2015-03-21 14:25:40','2015-03-21 14:25:40','2',1,1,258,NULL,0,2,0,NULL),(301,'<p>fdsafdsa</p>','2015-03-22 10:01:04','2015-03-22 10:01:04','2',1,1,402,NULL,0,1,0,NULL),(302,'<p>dsafdsa</p>','2015-03-22 10:01:28','2015-03-22 10:01:28','2',1,1,403,NULL,0,1,0,NULL),(303,'<p>www</p>','2015-03-22 13:35:23','2015-03-22 13:35:23','2',1,1,259,NULL,2,1,2,NULL),(401,'<p><img src=\"/bbs/file/download?fileId=551559ad8c153d4c15fdf75f\" title=\"2013-06-01_14-32-19_7341427462572940.jpg\" alt=\"\"/></p>','2015-03-27 13:23:01','2015-03-27 13:23:01','2',1,1,513,NULL,0,1,0,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_note` ENABLE KEYS */;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) default NULL,
  `amount` float default NULL,
  `created_on` datetime NOT NULL,
  `currency` varchar(32) default NULL,
  `mobile` longtext,
  `order_num` varchar(40) default NULL,
  `remark` varchar(255) default NULL,
  `status` varchar(32) default NULL,
  `typecode` varchar(16) default NULL,
  `updated_on` datetime NOT NULL,
  `created_by` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_9shxe8wpk7qrcw75yjcq5pwsu` (`created_by`),
  KEY `FK_1jd0qtidbmyy86lhjfophos2s` (`updated_by`),
  CONSTRAINT `FK_1jd0qtidbmyy86lhjfophos2s` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_9shxe8wpk7qrcw75yjcq5pwsu` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_order`
--


/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
LOCK TABLES `t_order` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;

--
-- Table structure for table `t_payment`
--

DROP TABLE IF EXISTS `t_payment`;
CREATE TABLE `t_payment` (
  `id` varchar(255) NOT NULL,
  `amount` float default NULL,
  `created_on` datetime NOT NULL,
  `currency` varchar(32) default NULL,
  `mobile` longtext,
  `order_time` datetime NOT NULL,
  `timeout` datetime NOT NULL,
  `pay_id` varchar(255) default NULL,
  `remark` varchar(255) default NULL,
  `status` varchar(32) default NULL,
  `trans_type` varchar(255) default NULL,
  `typecode` varchar(16) default NULL,
  `updated_on` datetime NOT NULL,
  `created_by` bigint(20) default NULL,
  `order_id` varchar(255) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_qks0y479iffnqq172dlvx7d7o` (`created_by`),
  KEY `FK_nk4lg4y65b4f719dijhsyg45j` (`order_id`),
  KEY `FK_k3oevh9dqp1xso6frp9ok7p7h` (`updated_by`),
  CONSTRAINT `FK_k3oevh9dqp1xso6frp9ok7p7h` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_nk4lg4y65b4f719dijhsyg45j` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`),
  CONSTRAINT `FK_qks0y479iffnqq172dlvx7d7o` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_payment`
--


/*!40000 ALTER TABLE `t_payment` DISABLE KEYS */;
LOCK TABLES `t_payment` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_payment` ENABLE KEYS */;

--
-- Table structure for table `t_profile`
--

DROP TABLE IF EXISTS `t_profile`;
CREATE TABLE `t_profile` (
  `id` varchar(64) NOT NULL,
  `avai_values` varchar(255) default NULL,
  `content` longtext,
  `created_on` datetime NOT NULL,
  `data` longblob,
  `data_type` varchar(24) NOT NULL,
  `def_value` varchar(255) default NULL,
  `ext0` varchar(64) default NULL,
  `ext1` varchar(64) default NULL,
  `ext2` varchar(128) default NULL,
  `value_key` varchar(64) NOT NULL,
  `max_value` double default NULL,
  `min_value` double default NULL,
  `model_id` varchar(126) NOT NULL,
  `providers` varchar(255) default NULL,
  `typecode` varchar(255) default NULL,
  `ui_type` varchar(255) default NULL,
  `updated_on` datetime NOT NULL,
  `validators` varchar(255) default NULL,
  `value` varchar(250) default NULL,
  `account_id` bigint(20) NOT NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_g99icdfl8sxd33n5a3k0w8ksx` (`account_id`),
  KEY `FK_oxmowvx5qv6bkcpyopa17riw6` (`client_id`),
  KEY `FK_hhs900db1i2ut8f1aulpl380q` (`created_by`),
  KEY `FK_56dl65vdk7td3db5mppt5wqhx` (`updated_by`),
  CONSTRAINT `FK_56dl65vdk7td3db5mppt5wqhx` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_g99icdfl8sxd33n5a3k0w8ksx` FOREIGN KEY (`account_id`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_hhs900db1i2ut8f1aulpl380q` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_oxmowvx5qv6bkcpyopa17riw6` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_profile`
--


/*!40000 ALTER TABLE `t_profile` DISABLE KEYS */;
LOCK TABLES `t_profile` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_profile` ENABLE KEYS */;

--
-- Table structure for table `t_sms_record`
--

DROP TABLE IF EXISTS `t_sms_record`;
CREATE TABLE `t_sms_record` (
  `id` bigint(20) NOT NULL,
  `content` longtext,
  `created_on` datetime NOT NULL,
  `desc0` varchar(128) default NULL,
  `receiver` varchar(32) default NULL,
  `remark` varchar(128) default NULL,
  `send_time` varchar(12) default NULL,
  `sender` varchar(32) default NULL,
  `status` varchar(12) default NULL,
  `type_code` bigint(20) default NULL,
  `updated_on` datetime NOT NULL,
  `user_name` varchar(32) default NULL,
  `client_id` varchar(8) NOT NULL,
  `created_by` bigint(20) default NULL,
  `updated_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_l4c8bxgd457ul60ld2tnmvn9d` (`client_id`),
  KEY `FK_joxxs0e1bpwsctw34inrsew2i` (`created_by`),
  KEY `FK_f6bl11v3xq71aoc5x5km7w3uw` (`updated_by`),
  CONSTRAINT `FK_f6bl11v3xq71aoc5x5km7w3uw` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_joxxs0e1bpwsctw34inrsew2i` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`),
  CONSTRAINT `FK_l4c8bxgd457ul60ld2tnmvn9d` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_sms_record`
--


/*!40000 ALTER TABLE `t_sms_record` DISABLE KEYS */;
LOCK TABLES `t_sms_record` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_sms_record` ENABLE KEYS */;

--
-- Table structure for table `t_topic`
--

DROP TABLE IF EXISTS `t_topic`;
CREATE TABLE `t_topic` (
  `id` bigint(20) NOT NULL default '0',
  `title` varchar(200) default NULL,
  `content` varchar(3000) default NULL,
  `created_on` datetime default NULL,
  `updated_on` datetime default NULL,
  `client_id` varchar(8) default NULL,
  `updated_by` bigint(20) default NULL,
  `created_by` bigint(20) default NULL,
  `topic_type` bigint(20) default NULL,
  `first_topic` bit(1) default NULL,
  `locked` bit(1) default NULL,
  `note_num` int(11) default NULL,
  `read_num` int(11) default NULL,
  `resolved` bit(1) default NULL,
  `top_seq` int(11) default NULL,
  `recall` bit(1) default NULL,
  PRIMARY KEY  (`id`),
  KEY `client_id` (`client_id`),
  KEY `updated_by` (`updated_by`),
  KEY `created_by` (`created_by`),
  KEY `topic_type` (`topic_type`),
  CONSTRAINT `t_topic_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_topic_ibfk_2` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_topic_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_topic_ibfk_4` FOREIGN KEY (`topic_type`) REFERENCES `t_topic_type` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_topic`
--


/*!40000 ALTER TABLE `t_topic` DISABLE KEYS */;
LOCK TABLES `t_topic` WRITE;
INSERT INTO `t_topic` VALUES (101,'最高检：李春城、蒋洁敏被提起公诉','<p><strong>湖北检察机关依法对蒋洁敏涉嫌受贿、巨额财产来源不明、国有公司人员滥用职权案提起公诉</strong></p><p>国务院\n国有资产监督管理委员会原主任、党委副书记蒋洁敏涉嫌受贿、巨额财产来源不明、国有公司人员滥用职权一案，由最高人民检察院侦查终结，经依法指定管辖，移\n送湖北省人民检察院汉江分院审查起诉。3月19日，湖北省人民检察院汉江分院已向湖北省汉江中级人民法院提起公诉。</p><p><br/></p>','2015-03-19 11:23:02','2015-03-19 11:23:02','2',1,1,2,NULL,NULL,NULL,58,NULL,NULL,'\0'),(256,'中石油曾经副处级以上人手一套团购房','<p>原标题：【盘点】中石油以前的日子过得有多好？</p><p>3月16日，中纪委发布消息称，中石油总经理廖永远涉嫌严重违纪违法被调查。昨日(3月19日)，媒体曝出，廖永远在北京有一个秘密据点，与他亲近的人可去纵情声色。</p><p>近来，中石油高官频频落马，人们对在这个排名世界五百强前十位的国有企业里挖出几个蛀虫似乎并不感到惊讶。因为一直以来，社会上都流传着很多关于这个“土豪”国企如何“有钱任性”的传说。</p><p>流言虽不可信，但从已经证实的例子里，我们也可以窥见中石油以前的日子过得真的不错。</p><p>一、食堂女服务员个个模样端正</p><p>人家的食堂绝不是普通人想象的那样，打饭师傅穿着油渍渍围裙腆着大肚子，打菜的时候勺子“深入浅出”，临了还故意给你抖掉几片肉。害你不得不厚着脸皮赔笑，“多给点呗。”</p><p>据中石油员工透露，大力反腐前，食堂的女服务员个个年轻、模样端正，基本可以肯定，她们是经过专门挑选的。</p><p><br/></p>','2015-03-20 13:48:56','2015-03-20 13:49:23','2',1,1,2,'\0','\0',2,25,'\0',1,'\0'),(257,'未满16岁男生涉“李光耀被去世”遭调查','<p>据联合早报网消息，一名未满16岁的新加坡籍男生在协助警方调查有关建国总理李光耀情况的造假总理公署文告案。</p><p>新加坡警察部队今天发文告说，本月18日晚上10时10分，警方接获消息，网络流传一张看似总理公署网站文告的截图。警方在24小时内确认了嫌犯身份。初步调查显示，嫌犯在总理公署网站上的2010年文告上动手脚，并发出这个篡改的内容的照片。</p><p>警方说嫌犯是一名未满16岁的新加坡籍男性学生，相信是单独行动。</p><p>警方相信，这名男学生相信是独自行动。他目前涉嫌触犯滥用电脑法令。若罪成，将可能面对不超过5万元的罚款，或面对长达10年的监禁，或两者兼施。</p><p><strong>早前报道：假网站截图称李光耀去世 新加坡官方报警</strong></p><p>18日晚间，网上流传一个冒充新加坡总理公署网站文告的截图，称新加坡总理李光耀已经逝世。新加坡亚洲新闻台及联合早报向总理公署查证，它未发布任何新信息。</p><p>据联合早报消息，总理公署呼吁公众查证消息来源的链接。与此同时，总理公署已经就这起假文告事件报警。</p><p><br/></p>','2015-03-20 15:18:51','2015-03-20 15:18:51','2',1,1,3,'\0','\0',0,2,'\0',1,'\0'),(258,'住建部部长陈政高：增加公积金贷款额度','<p>住房城乡建设部部长陈政高在对住房公积金管理工作作出重要部署时强调，住房公积金是重大的民生工程，对于改善中低收入家庭住房条件、促进房地产市场\n平稳健康发展具有重要意义，各地要进一步提高认识，统一思想，增强工作的主动性，加大工作力度，发掘住房公积金的巨大潜力，用好用足住房公积金。</p><p>要\n提高资金使用效率，使住房公积金在支持居民基本住房需求、改善民生方面发挥更大作用。希望各地更多发挥主动性，采取多种措施，用好用足住房公积金。要进一\n步降低门槛，增加公积金贷款额度，简化手续，建立方便快捷的业务流程，缩短办理时限，采取公积金跨省、跨市异地支取使用的措施。</p><p>住房城乡\n建设部进一步完善住房公积金信息披露制度。城市每年3月底、省(区)每年4月底、全国每年5月底，通过政府公报、政府网站、住房公积金中心网站、新闻发布\n会以及报刊、广播、电视等渠道，对机构概况、业务运行、财务数据、资产风险、效益分析以及需要披露的其它重要事项进行定期披露。<span class=\"ifengLogo\"><a href=\"http://www.ifeng.com/\" target=\"_blank\"><img src=\"http://y1.ifengimg.com/e01ed39fc2da5d4a/2013/1122/Logo.gif\" height=\"17\" width=\"15\"/></a></span></p><p><br/></p>','2015-03-20 15:19:28','2015-03-21 14:25:40','2',1,1,101,'\0','\0',2,4,'\0',1,'\0'),(259,'缅方称中缅正检验炸弹型号以查明事件真相','<p>【环球网综合报道】据缅甸媒体《标准时间》报道，缅甸总统发言人吴耶图3月17日对媒体表示，为了查明炸弹落入中方境内造成平民伤亡事件的真相，中\n缅两国正合作检验炸弹型号，并调查该炸弹是从何处投掷。17日，中缅联合调查组已经到达事发地，获取了炸弹碎片样本，经过检验就能辨别是空军的炸弹，还是\n迫击炮。</p><p>据此前报道，自13日炸弹落入中国临沧境内造成边民伤亡事件发生后，临沧市对边境村寨群众的情绪进行安抚疏导，保证生产生活正常\n开展。军警部队在边境群众生产生活较为集中的时段和地段进行武装巡逻。目前，经省市医疗专家和抢救组成员的多次会诊治疗，8名受伤人员中，仍有1名重伤人\n员未脱离生命危险，2名重伤人员已脱离危险，伤情有所好转；5名轻伤人员已治愈出院。</p><p><br/></p>','2015-03-20 15:20:06','2015-03-22 13:35:23','2',1,1,3,'\0','\0',1,9,'\0',1,'\0'),(301,'河北遵化1000多名车主疑似“被超速” 交警回应','<p>3月18日，唐山遵化市部分机动车车主再次来到该市交警大队反映情况。这些车主怀疑该市雷达测速装置存在问题从而导致自己“被超速”。</p><p>据车主李先生介绍，截至目前已有1600多名疑似“被超速”的车主在相关部门进行了申诉登记，他们的违章记录多是发生在2月14日至26日之间的遵化张南洼、北三环、城南高速路口、上港路段等位置。</p><p>按照雷达测速设备的认定，这些车主在不顾生命安全地“狂飙”：</p><p>一\n位65岁的老太太在雪天驾车以91公里/小时的速度通过了限速60公里/小时的遵化北三环路段；一辆坐满了人的面包车被测速装置认定，在路面有积雪的情况\n下在遵化北三环以72公里/小时的速度转弯；一位车主驾驶一辆2008年产的面包车在限速80公里/小时的上港路段实施了“超速50%以上不足80%的违\n法行为”，也就是说他当时的时速最低也要120公里，而司机说由于车况不佳，平时的行车速度也就是75公里/小时……</p><p><br/></p>','2015-03-21 04:04:35','2015-03-21 12:15:29','2',1,1,101,'\0','\0',3,21,'\0',1,'\0'),(401,'这是一个测试贴','<p>这是一个测试贴</p>','2015-03-22 09:01:00','2015-03-22 09:01:00','2',1,1,2,'\0','\0',0,1,'\0',1,'\0'),(402,'dafdsa','<p>dfafd<br/></p>','2015-03-22 10:00:56','2015-03-22 10:01:04','2',1,1,2,'\0','\0',1,2,'\0',1,'\0'),(403,'afdsa','<p>fdsafdsa</p>','2015-03-22 10:01:21','2015-03-22 10:01:28','2',1,1,2,'\0','\0',1,2,'\0',1,'\0'),(512,'MongoDB CRUD Introduction','<p>MongoDB stores data in the form of <em>documents</em>, which are JSON-like\nfield and value pairs. Documents are analogous to structures in\nprogramming languages that associate keys with values (e.g.\ndictionaries, hashes, maps, and associative arrays). Formally, MongoDB\ndocuments are <a class=\"reference internal\" href=\"http://docs.mongodb.org/manual/reference/glossary/#term-bson\"><em class=\"xref std std-term\">BSON</em></a> documents. BSON is a binary representation\nof <a class=\"reference internal\" href=\"http://docs.mongodb.org/manual/reference/glossary/#term-json\"><em class=\"xref std std-term\">JSON</em></a> with additional type information. In the documents, the\nvalue of a field can be any of the BSON data types, including other\ndocuments, arrays, and arrays of documents. For more information, see<a class=\"reference internal\" href=\"http://docs.mongodb.org/manual/core/document/\"><em>Documents</em></a>.</p><p><img alt=\"A MongoDB document.\" src=\"http://docs.mongodb.org/manual/_images/crud-annotated-document.png\"/></p><p>MongoDB stores all documents in <a class=\"reference internal\" href=\"http://docs.mongodb.org/manual/reference/glossary/#term-collection\"><em class=\"xref std std-term\">collections</em></a>. A\ncollection is a group of related documents that have a set of shared\ncommon indexes. Collections are analogous to a table in relational\ndatabases.</p><p><br/></p>','2015-03-25 12:38:52','2015-03-25 12:38:52','2',1,1,2,'\0','\0',0,5,'\0',1,'\0'),(513,'Database Operations','<h3>Query<a class=\"headerlink\" href=\"http://docs.mongodb.org/manual/core/crud-introduction/#query\" title=\"Permalink to this headline\"></a></h3><p>In MongoDB a query targets a specific collection of documents. Queries\nspecify criteria, or conditions, that identify the documents that\nMongoDB returns to the clients. A query may include a <em>projection</em> that\nspecifies the fields from the matching documents to return. You can\noptionally modify queries to impose limits, skips, and sort orders.</p><p>In the following diagram, the query process specifies a query criteria\nand a sort modifier:</p><p><img alt=\"The stages of a MongoDB query with a query criteria and a sort modifier.\" src=\"http://docs.mongodb.org/manual/_images/crud-query-stages.png\"/></p><p><br/></p>','2015-03-25 12:44:00','2015-03-27 13:23:01','2',1,1,2,'\0','\0',1,5,'\0',1,'\0');
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_topic` ENABLE KEYS */;

--
-- Table structure for table `t_topic_type`
--

DROP TABLE IF EXISTS `t_topic_type`;
CREATE TABLE `t_topic_type` (
  `id` bigint(20) NOT NULL default '0',
  `description` varchar(128) NOT NULL,
  `title` varchar(128) default NULL,
  `created_on` datetime default NULL,
  `updated_on` datetime default NULL,
  `client_id` varchar(8) default NULL,
  `updated_by` bigint(20) default NULL,
  `created_by` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `client_id` (`client_id`),
  KEY `updated_by` (`updated_by`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `t_topic_type_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `t_client` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_topic_type_ibfk_2` FOREIGN KEY (`updated_by`) REFERENCES `t_account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `t_topic_type_ibfk_3` FOREIGN KEY (`created_by`) REFERENCES `t_account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_topic_type`
--


/*!40000 ALTER TABLE `t_topic_type` DISABLE KEYS */;
LOCK TABLES `t_topic_type` WRITE;
INSERT INTO `t_topic_type` VALUES (2,'产品测评相关贴','产品测评','2015-03-17 14:37:49','2015-03-17 14:37:49',NULL,NULL,NULL),(3,'热门活动类别','热门活动','2015-03-17 14:48:50','2015-03-17 14:48:50',NULL,NULL,NULL),(101,'问题交流相关','问题交流','2015-03-18 13:18:55','2015-03-18 13:18:55','2',NULL,NULL),(256,'版主问题相关','版主问题','2015-03-22 09:01:40','2015-03-22 09:01:40','2',NULL,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_topic_type` ENABLE KEYS */;

--
-- Table structure for table `t_user_device`
--

DROP TABLE IF EXISTS `t_user_device`;
CREATE TABLE `t_user_device` (
  `device_id` bigint(20) NOT NULL,
  `acccount_id` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `relativedName` varchar(32) default NULL,
  PRIMARY KEY  (`device_id`,`acccount_id`),
  KEY `FK_ealkvc4jkbwl2wcx5jkue19yx` (`acccount_id`),
  CONSTRAINT `FK_9ay7koef0fiijk5a3e2v1bctj` FOREIGN KEY (`device_id`) REFERENCES `t_device` (`id`),
  CONSTRAINT `FK_ealkvc4jkbwl2wcx5jkue19yx` FOREIGN KEY (`acccount_id`) REFERENCES `t_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_user_device`
--


/*!40000 ALTER TABLE `t_user_device` DISABLE KEYS */;
LOCK TABLES `t_user_device` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `t_user_device` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

