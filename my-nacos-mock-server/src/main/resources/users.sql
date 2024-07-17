/*
 Navicat Premium Data Transfer

 Source Server         : 贵州银行market_risk
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : 192.168.22.201:3307
 Source Schema         : market_risk

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 15/02/2023 16:03:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `USER_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `PASSWORD` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL,
  `EXPIRE_TIME` datetime(0) NULL DEFAULT NULL,
  `DEPARTMENT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ROLE_ID` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ORGANIZATION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ID_INSTITUTION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION` decimal(8, 0) NULL DEFAULT NULL COMMENT '版本',
  `CD_PHONE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `USER_FLAG` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CD_FROZEN_STATE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冻结状态',
  `CD_FROZEN_STATE2` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '统一门户登录',
  `DT_LOGIN` date NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO `market_risk`.`users`(`USER_ID`, `USER_NAME`, `EMAIL`, `PASSWORD`, `CREATE_TIME`, `EXPIRE_TIME`, `DEPARTMENT`, `ROLE_ID`, `ORGANIZATION`, `ID_INSTITUTION`, `VERSION`, `CD_PHONE`, `USER_FLAG`, `CD_FROZEN_STATE`, `CD_FROZEN_STATE2`, `DT_LOGIN`) VALUES ('sl', 'sdf', 'sdf', 'sunlong567', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'N', 'N', '2023-02-03');


CREATE TABLE `users_roles`  (
  `USER_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `VERSION` decimal(10, 0) NULL DEFAULT NULL COMMENT '版本',
  `TIMESTAMP` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  PRIMARY KEY (`USER_ID`, `ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

CREATE TABLE `permissions`  (
  `PERMISSION_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限ID',
  `RESOURCE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ACTION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION` decimal(10, 0) NULL DEFAULT NULL COMMENT '版本',
  `TIMESTAMP` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  `ID_PARENT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父id',
  `NBR_LEVEL` int(5) NULL DEFAULT NULL COMMENT '层级',
  `NBR_ORDER` bigint(65) NULL DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`PERMISSION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限' ROW_FORMAT = DYNAMIC;



CREATE TABLE `roles`  (
  `ROLE_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `ROLE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `VERSION` decimal(10, 0) NULL DEFAULT NULL COMMENT '版本',
  `ROLE_DESC` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `TIMESTAMP` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

CREATE TABLE `roles_permissions`  (
  `ROLE_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限ID',
  `VERSION` decimal(10, 0) NULL DEFAULT NULL COMMENT '版本',
  `TIMESTAMP` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  PRIMARY KEY (`ROLE_ID`, `PERMISSION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关联' ROW_FORMAT = DYNAMIC;