/*
 Navicat Premium Data Transfer

 Source Server         : maria
 Source Server Type    : MariaDB
 Source Server Version : 100503
 Source Host           : localhost:3307
 Source Schema         : financial_info

 Target Server Type    : MariaDB
 Target Server Version : 100503
 File Encoding         : 65001

 Date: 20/10/2023 22:13:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `balance` decimal(19, 2) NULL DEFAULT NULL,
  `client_id` bigint(20) NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `client_id`(`client_id`) USING BTREE,
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (6, 2000.00, 4, b'1', 'Ahorros');
INSERT INTO `account` VALUES (7, 100.00, 5, b'1', 'Corriente');
INSERT INTO `account` VALUES (8, 0.00, 6, b'1', 'Ahorros');
INSERT INTO `account` VALUES (9, 540.00, 5, b'1', 'Ahorros');
INSERT INTO `account` VALUES (10, 1000.00, 4, b'1', 'Corriente');

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `client_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `dni` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES (4, 'Otavalo sn y principal', 33, '9045863525', 'M', 'Jose Lema', '098254785', '1234', b'1');
INSERT INTO `client` VALUES (5, 'Amazonas y NNUU', 25, '9736545211', 'F', 'Marionela Montalvo', '097548965', '5678', b'1');
INSERT INTO `client` VALUES (6, '13 junio y Esquinoccial', 34, '8956211556', 'M', 'Julio Osorio', '098874587', '1245', b'1');

-- ----------------------------
-- Table structure for movement
-- ----------------------------
DROP TABLE IF EXISTS `movement`;
CREATE TABLE `movement`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `balance` decimal(19, 2) NULL DEFAULT NULL,
  `create_at` datetime(0) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` decimal(19, 2) NULL DEFAULT NULL,
  `account_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKoemeananv9w9qnbcoccbl70a0`(`account_id`) USING BTREE,
  CONSTRAINT `FKoemeananv9w9qnbcoccbl70a0` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movement
-- ----------------------------
INSERT INTO `movement` VALUES (4, 1425.00, '2023-09-04 22:09:37', 'Ahorros', 575.00, 6);

SET FOREIGN_KEY_CHECKS = 1;
