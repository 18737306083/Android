/*
Navicat MySQL Data Transfer

Source Server         : cai
Source Server Version : 50612
Source Host           : 127.0.0.1:3306
Source Database       : order

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2016-09-30 16:43:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for allorder
-- ----------------------------
DROP TABLE IF EXISTS `allorder`;
CREATE TABLE `allorder` (
  `tablenum` int(4) NOT NULL,
  `ordercount` double(4,0) NOT NULL,
  `orderprogress` varchar(10) NOT NULL,
  `orderdate` datetime(4) NOT NULL,
  PRIMARY KEY (`tablenum`,`orderdate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of allorder
-- ----------------------------
INSERT INTO `allorder` VALUES ('11', '207', '未做', '2016-09-27 19:34:39.0000');
INSERT INTO `allorder` VALUES ('23', '815', '开始做', '2016-09-27 19:27:28.0000');

-- ----------------------------
-- Table structure for already
-- ----------------------------
DROP TABLE IF EXISTS `already`;
CREATE TABLE `already` (
  `goodsname` varchar(20) NOT NULL,
  `tablenum` int(4) NOT NULL,
  `num` int(4) NOT NULL,
  PRIMARY KEY (`goodsname`,`tablenum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of already
-- ----------------------------
INSERT INTO `already` VALUES ('炸蛎黄', '11', '3');
INSERT INTO `already` VALUES ('糖醋鱼', '23', '5');
INSERT INTO `already` VALUES ('韩国5°海特黑啤酒', '23', '5');

-- ----------------------------
-- Table structure for cook
-- ----------------------------
DROP TABLE IF EXISTS `cook`;
CREATE TABLE `cook` (
  `phone` varchar(11) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cook
-- ----------------------------
INSERT INTO `cook` VALUES ('18737306083', '1111');
INSERT INTO `cook` VALUES ('2222', '2222');

-- ----------------------------
-- Table structure for cookecooking
-- ----------------------------
DROP TABLE IF EXISTS `cookecooking`;
CREATE TABLE `cookecooking` (
  `phone` varchar(11) NOT NULL,
  `tablenum` int(4) NOT NULL,
  PRIMARY KEY (`phone`,`tablenum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cookecooking
-- ----------------------------

-- ----------------------------
-- Table structure for ingredient
-- ----------------------------
DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE `ingredient` (
  `goodsname` varchar(20) NOT NULL,
  `goodssource` varchar(20) NOT NULL,
  `buyindate` date NOT NULL,
  `price` double(10,0) NOT NULL,
  `num` int(4) NOT NULL,
  PRIMARY KEY (`goodsname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ingredient
-- ----------------------------
INSERT INTO `ingredient` VALUES ('木耳', '新乡百货', '2016-08-15', '25', '20');
INSERT INTO `ingredient` VALUES ('洋葱', '新乡易购', '2016-08-15', '25', '20');
INSERT INTO `ingredient` VALUES ('海参', '新乡胖东来', '2016-08-12', '39', '20');
INSERT INTO `ingredient` VALUES ('羊肉', '河南新乡商贸城', '2016-08-12', '40', '15');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `phone` varchar(11) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('1111111111', '2222');
INSERT INTO `staff` VALUES ('123456789', 'beibei521');
INSERT INTO `staff` VALUES ('13423556245', 'dfadf134');
INSERT INTO `staff` VALUES ('15736975362', 'caiqiufang');
INSERT INTO `staff` VALUES ('15738308889', '1994wobuzhidao');

-- ----------------------------
-- Table structure for table_vs
-- ----------------------------
DROP TABLE IF EXISTS `table_vs`;
CREATE TABLE `table_vs` (
  `goodsname` varchar(20) NOT NULL,
  `goodsdesc` varchar(100) DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `path` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`goodsname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_vs
-- ----------------------------
INSERT INTO `table_vs` VALUES ('九转大肠', '山东省汉族传统名菜，属于鲁菜系', '122', 'jiuzhuandachang.jpg');
INSERT INTO `table_vs` VALUES ('地三鲜', '东北汉族传统家常菜,永远的经典菜，百吃不厌', '111', 'disanxian.jpg');
INSERT INTO `table_vs` VALUES ('炸蛎黄', '软炸蛎黄是一道色香味俱全的汉族名菜，经常作为威海喜庆筵席上的头道菜。', '69', 'zhalihuang.jpg');
INSERT INTO `table_vs` VALUES ('糖醋鱼', '胸鳍竖起，鱼肉嫩美，带有蟹味，肉滋别具特色', '98', 'tangcuyu.jpg');
INSERT INTO `table_vs` VALUES ('红烧海螺', '徽州地区经典的汉族名菜，属于徽菜系', '123', 'hongshaohailuo.jpg');
INSERT INTO `table_vs` VALUES ('葱扒海参', '口味鲜咸醇香，质地软嫩，葱油味香浓', '23', 'congbahaishen.jpg');
INSERT INTO `table_vs` VALUES ('葱爆羊肉', '羊肉滑嫩、鲜香不膻、汪油包汁、食后回味无穷', '125', 'congbaoyangrou.jpg');
INSERT INTO `table_vs` VALUES ('锅烧肘子', '外焦内嫩，肉香可口，肥而不腻', '123', 'guoshaozhouzi.jpg');

-- ----------------------------
-- Table structure for table_win
-- ----------------------------
DROP TABLE IF EXISTS `table_win`;
CREATE TABLE `table_win` (
  `goodsname` varchar(30) NOT NULL,
  `goodsdesc` varchar(50) DEFAULT NULL,
  `price` varchar(60) NOT NULL,
  `path` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_win
-- ----------------------------
INSERT INTO `table_win` VALUES ('白啤酒', '1664白啤酒', '224', '1164.jpg');
INSERT INTO `table_win` VALUES ('扳倒井小三星', ' 水、高粱、大米、糯米、小麦、小米、玉米', '345', 'jingxiaosanxing.jpg');
INSERT INTO `table_win` VALUES ('韩国5°海特黑啤酒', '韩潮来袭,海特黑啤,我要,我要,思密哒', '65', 'hanguo.jpg');
INSERT INTO `table_win` VALUES ('美国深蓝伏特加', '美国深蓝伏特加-洋酒', '55', 'futejia.jpg');
INSERT INTO `table_win` VALUES ('金六福', '几十种不同档次、不同口味，满足不同区域', '100', 'jinliufu.jpg');
INSERT INTO `table_win` VALUES ('雪花啤酒', '清凉你的餐桌、拉近你的友谊', '20', 'xuehua.jpg');
INSERT INTO `table_win` VALUES ('泸州老窖', '泸州老窖酒传统酿制技艺', '67', 'luzhoulaojiao.jpg');

-- ----------------------------
-- Table structure for vs_ingredient
-- ----------------------------
DROP TABLE IF EXISTS `vs_ingredient`;
CREATE TABLE `vs_ingredient` (
  `vsname` varchar(20) NOT NULL,
  `ingrename` varchar(20) NOT NULL,
  `num` int(4) NOT NULL,
  PRIMARY KEY (`vsname`,`ingrename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vs_ingredient
-- ----------------------------
INSERT INTO `vs_ingredient` VALUES ('猪肉炖粉条', '猪肉', '23');
INSERT INTO `vs_ingredient` VALUES ('葱爆羊肉', '洋葱', '6');
INSERT INTO `vs_ingredient` VALUES ('葱爆羊肉', '羊肉', '3');
INSERT INTO `vs_ingredient` VALUES ('葱爆肉丝', '猪肉', '2');
