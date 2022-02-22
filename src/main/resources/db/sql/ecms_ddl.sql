SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`
(
    `id`           varchar(35)   NOT NULL,
    `column_id`    varchar(35)   NOT NULL COMMENT '所属栏目id',
    `content`      longtext      NULL COMMENT '内容',
    `title`        varchar(255)  NULL     DEFAULT NULL COMMENT '标题',
    `subtitle`     varchar(255)  NULL     DEFAULT NULL COMMENT '副标题',
    `attachment`   varchar(255)  NULL     DEFAULT NULL COMMENT '附件',
    `link_url`     varchar(255)  NULL     DEFAULT NULL COMMENT '文章跳转链接地址',
    `keyword`      varchar(255)  NULL     DEFAULT NULL COMMENT '关键字',
    `remarks`      varchar(400)  NULL     DEFAULT NULL COMMENT '描述',
    `img`          varchar(1000) NULL     DEFAULT NULL COMMENT '文章图片',
    `source`       varchar(255)  NULL     DEFAULT NULL COMMENT '文章来源',
    `author`       varchar(255)  NULL     DEFAULT NULL COMMENT '文章作者',
    `type`         varchar(255)  NULL     DEFAULT NULL COMMENT '类型',
    `status`       tinyint(4)    NULL     DEFAULT NULL COMMENT '状态',
    `sort`         int(11)       NULL     DEFAULT NULL COMMENT '自定义顺序',
    `hit_nums`     int(11)       NULL     DEFAULT 0 COMMENT '点击次数',
    `created_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20)   NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20)   NOT NULL COMMENT '更新人',
    `deleted`      bit(1)        NOT NULL COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '文章表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_rule
-- ----------------------------
DROP TABLE IF EXISTS `article_rule`;
CREATE TABLE `article_rule`
(
    `id`           varchar(35)  NOT NULL,
    `rule_id`      varchar(35)  NOT NULL COMMENT '规则id',
    `column_id`    varchar(35)  NOT NULL COMMENT '栏目id',
    `article_id`   varchar(35)  NOT NULL COMMENT '文章id',
    `imgNum`       int(11)      NOT NULL COMMENT '采集统计',
    `status`       varchar(255) NOT NULL COMMENT '状态',
    `created_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20)  NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20)  NOT NULL COMMENT '更新人',
    `deleted`      bit(1)       NOT NULL COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '采集信息统计表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- 权限代码表
-- ----------------------------
DROP TABLE IF EXISTS `permit`;
CREATE TABLE `permit`
(
    `id`           varchar(35) NOT NULL,
    `code`         varchar(20) NOT NULL COMMENT '权限代码',
    `name`         varchar(20) NOT NULL COMMENT '权限名称',
    `created_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20) NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20) NOT NULL COMMENT '更新人',
    `deleted`      bit(1)      NOT NULL COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '权限表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- 采集规则表
-- ----------------------------
DROP TABLE IF EXISTS `collection_rule`;
CREATE TABLE `collection_rule`
(
    `id`                       varchar(35)   NOT NULL,
    `column_id`                varchar(35)   NOT NULL COMMENT '所属栏目id',
    `name`                     varchar(55)   NOT NULL COMMENT '节点名称',
    `collection_path`          varchar(2000) NULL     DEFAULT NULL COMMENT '采集地址',
    `isContent`                bit(1)        NULL     DEFAULT NULL COMMENT '采集页为直接内容',
    `contentPageAddressPrefix` varchar(100)  NULL     DEFAULT NULL COMMENT '内容页面地址前缀',
    `imgAddressPrefix`         varchar(100)  NULL     DEFAULT NULL COMMENT '图片/FLASH地址前缀',
    `subTitle`                 varchar(255)  NULL     DEFAULT NULL COMMENT '副标题',
    `startDate`                datetime      NULL     DEFAULT NULL COMMENT '开始时间',
    `endDate`                  datetime      NULL     DEFAULT NULL COMMENT '结束时间',
    `remarks`                  varchar(1000) NULL     DEFAULT NULL COMMENT '备注',
    `infoPage`                 varchar(1000) NULL     DEFAULT NULL COMMENT '信息页链接正则',
    `titlePictureReg`          varchar(1000) NULL     DEFAULT NULL COMMENT '标题图片正则',
    `titleAddressPrefix`       varchar(255)  NULL     DEFAULT NULL COMMENT '图片地址前缀',
    `isSaveLocal`              bit(1)        NULL     DEFAULT NULL COMMENT '保存本地',
    `titleReg`                 varchar(1000) NULL     DEFAULT NULL COMMENT '标题正则',
    `subTitleReg`              varchar(1000) NULL     DEFAULT NULL COMMENT '副标题正则',
    `publishTime`              varchar(1000) NULL     DEFAULT NULL COMMENT '发布时间正则',
    `contentIntroductionReg`   varchar(1000) NULL     DEFAULT NULL COMMENT '内容简介正则',
    `authorReg`                varchar(1000) NULL     DEFAULT NULL COMMENT '作者正则',
    `created_time`             datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`             datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`               varchar(20)   NOT NULL COMMENT '创建人',
    `updated_by`               varchar(20)   NOT NULL COMMENT '更新人',
    `deleted`                  bit(1)        NOT NULL COMMENT '逻辑删除',
    `status`                   varchar(255)  NULL     DEFAULT NULL COMMENT '状态',
    `content`                  varchar(255)  NULL     DEFAULT NULL COMMENT '正文正则',
    `description`              varchar(255)  NULL     DEFAULT NULL COMMENT '内容简介正则',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '采集规则表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for column_setting
-- ----------------------------
DROP TABLE IF EXISTS `column_setting`;
CREATE TABLE `column_setting`
(
    `id`           varchar(35)   NOT NULL,
    `name`         varchar(35)   NULL     DEFAULT NULL COMMENT '栏目名称',
    `type_id`      varchar(35)   NOT NULL COMMENT '栏目类型id',
    `path`         varchar(255)  NULL     DEFAULT NULL COMMENT '栏目路径',
    `sort`         int(11)       NULL     DEFAULT NULL COMMENT '顺序',
    `remarks`      varchar(500)  NULL     DEFAULT NULL COMMENT '描述',
    `thumb`        varchar(2000) NULL     DEFAULT NULL COMMENT '缩略图',
    `parent_id`    varchar(20)   NULL     DEFAULT NULL COMMENT '父栏目id',
    `created_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20)   NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20)   NOT NULL COMMENT '更新人',
    `deleted`      bit(1)        NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '栏目设置表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for column_type_setting
-- ----------------------------
DROP TABLE IF EXISTS `column_type_setting`;
CREATE TABLE `column_type_setting`
(
    `id`           varchar(35) NOT NULL,
    `name`         varchar(35) NULL     DEFAULT NULL COMMENT '栏目类型名称',
    `created_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20) NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20) NOT NULL COMMENT '更新人',
    `deleted`      bit(1)      NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '栏目类型设置表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`
(
    `id`           varchar(35) NOT NULL,
    `name`         varchar(35) NOT NULL COMMENT '名称',
    `path`         varchar(50) NOT NULL COMMENT '路径',
    `type`         varchar(20) NOT NULL COMMENT '文件类型',
    `created_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20) NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20) NOT NULL COMMENT '更新人',
    `deleted`      bit(1)      NOT NULL COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '文件资源表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`        varchar(35)  NOT NULL,
    `name`      varchar(20)  NOT NULL COMMENT '菜单名称',
    `parent_id` varchar(35)  NULL DEFAULT NULL COMMENT '父菜单id',
    `path`      varchar(100) NULL DEFAULT NULL COMMENT '菜单path',
    `deleted`   bit(1)       NOT NULL COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '菜单表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`
(
    `id`      varchar(35) NOT NULL,
    `role_id` varchar(30) NOT NULL COMMENT '角色id',
    `menu_id` varchar(35) NOT NULL COMMENT '菜单id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '菜单角色权限表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`           varchar(35) NOT NULL,
    `code`         varchar(20) NOT NULL COMMENT '角色代码',
    `name`         varchar(20) NOT NULL COMMENT '角色名称',
    `created_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20) NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20) NOT NULL COMMENT '更新人',
    `deleted`      bit(1)      NOT NULL COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '角色表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_authority
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority`
(
    `id`           varchar(35) NOT NULL,
    `role_id`      varchar(35) NOT NULL COMMENT '角色id',
    `authority_id` varchar(35) NOT NULL COMMENT '权限id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '角色权限表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- 账号表
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`
(
    `id`           varchar(35) NOT NULL,
    `account`     varchar(20) NOT NULL COMMENT '用户名称',
    `password`     varchar(20) NOT NULL COMMENT '密码',
    `user_info_id` varchar(35) NULL     DEFAULT NULL COMMENT '用户信息表id',
    `created_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20) NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20) NOT NULL COMMENT '更新人',
    `deleted`      bit(1)      NOT NULL COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '账号表'  ROW_FORMAT = Dynamic;

-- ----------------------------
-- 用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    `id`           varchar(35) NOT NULL,
    `phone`        varchar(20) NULL     DEFAULT NULL COMMENT 'phone',
    `role_id`      varchar(35) NULL     DEFAULT NULL COMMENT '角色id',
    `created_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `created_by`   varchar(20) NOT NULL COMMENT '创建人',
    `updated_by`   varchar(20) NOT NULL COMMENT '更新人',
    `deleted`      bit(1)      NOT NULL COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '用户信息表'  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
