CREATE DATABASE IF NOT EXISTS mcdonalism;
USE mcdonalism;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) COLLATE utf8_bin NOT NULL,
  `password` varchar(60) COLLATE utf8_bin NOT NULL,
  `gender` varchar(60) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1112 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

BEGIN;
INSERT INTO `user` VALUES (1, 'Ronald_McDonald', 'LONG_LIVE_MACDONALD', 'male');
INSERT INTO `user` VALUES (2, 'Colonel_Sanders', 'KFC_CRAZY_THURSDAY_VME50', 'male');
INSERT INTO `user` VALUES (3, 'Meo', '20031214', 'helicopter');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
