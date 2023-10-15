DROP TABLE IF EXISTS `base_login_account_auth`;
CREATE TABLE `base_login_account_auth` (
  `sid` bigint(16) unsigned NOT NULL AUTO_INCREMENT,
  `user_account_sid` bigint(16) DEFAULT NULL COMMENT '用户uid',
  `login_id` varchar(50) NOT NULL  COMMENT '登录账户',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `login_type` smallint(2) DEFAULT NULL COMMENT '账户登录类型：1-自定义账号+密码；2-手机号+密码；3-手机号+验证码；4-邮箱密码；5-二维码；6-微信openId；',
  `login_status` smallint(2) DEFAULT NULL COMMENT '账户状态（暂时没用）',
  `del_flag` smallint(2) NOT NULL COMMENT '删除标志\r\n            0 - 正常\r\n            1 - 删除',
  `version` int(10) NOT NULL COMMENT '版本号',
  `created_by` varchar(50) NOT NULL COMMENT '创建人\r\n            登录帐号',
  `create_date` datetime NOT NULL,
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人\r\n            登录的帐号',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(255) DEFAULT NULL,
  `current_version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `idx_user_account_sid` (`user_account_sid`),
  KEY `idx_login_id` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=356716 DEFAULT CHARSET=utf8 COMMENT='用户登录鉴权表';

DROP TABLE IF EXISTS `base_user_account`;
CREATE TABLE `base_user_account` (
  `sid` bigint(16) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` varchar(32) NOT NULL COMMENT '用户账号ID，业务主键，默认生成规则：UUID',
  `account_status` smallint(2) NOT NULL COMMENT '账户状态（暂时没用）',
  `secret_key` varchar(32) DEFAULT NULL,
  `account_type` smallint(2) DEFAULT '0' COMMENT '账号类型 0普通账号 1-访客账号',
  `del_flag` smallint(2) NOT NULL COMMENT '删除标志 0 - 正常 1 - 删除',
  `version` int(10) NOT NULL COMMENT '版本号',
  `created_by` varchar(50) NOT NULL COMMENT '创建人\r\n            登录帐号',
  `create_date` datetime NOT NULL,
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人\r\n            登录的帐号',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(255) DEFAULT NULL,
  `current_version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `idx_account_id` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1060091 DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `base_user_profile`;
CREATE TABLE `base_user_profile` (
  `sid` bigint(16) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID  即SID',
  `user_account_sid` bigint(16) DEFAULT NULL COMMENT '用户账户sid',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_level` smallint(2) DEFAULT NULL COMMENT '用户级别',
  `sexuality` smallint(2) DEFAULT NULL COMMENT '性别',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `id_type` smallint(2) DEFAULT NULL COMMENT '证件类型',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件',
  `mobile_phone` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `id_no` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `age` smallint(3) DEFAULT NULL COMMENT '年龄',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `del_flag` smallint(2) NOT NULL COMMENT '删除标志 0 - 正常 1 - 删除',
  `version` int(10) NOT NULL COMMENT '版本号',
  `created_by` varchar(50) NOT NULL COMMENT '创建人 登录帐号',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人  登录的帐号',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `idx_user_account_sid` (`user_account_sid`),
  KEY `idx_mobile_phone` (`mobile_phone`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1058500 DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';

DROP TABLE IF EXISTS `base_user_atributes`;
CREATE TABLE `base_user_atributes` (
  `sid` bigint(16) unsigned NOT NULL AUTO_INCREMENT,
  `user_sid` bigint(16) NOT NULL COMMENT 'base_user_profile表的sid',
  `attribute_code` varchar(50) NOT NULL ,
  `attribute_value` varchar(350) DEFAULT NULL,
  `del_flag` smallint(2) NOT NULL COMMENT '删除标志 0 - 正常 1 - 删除',
  `version` int(10) NOT NULL COMMENT '版本号',
  `created_by` varchar(50) NOT NULL COMMENT '创建人 登录帐号',
  `create_date` datetime NOT NULL,
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人 登录的帐号',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`sid`),
  KEY `idx_user_sid` (`user_sid`),
  KEY `idx_attribute_value_attribute_code` (`attribute_value`,`attribute_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4444578391 DEFAULT CHARSET=utf8mb4 COMMENT='用户属性表';
