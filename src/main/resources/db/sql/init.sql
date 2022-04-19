set global log_bin_trust_function_creators=TRUE;
DROP FUNCTION IF EXISTS getRootId;
CREATE FUNCTION getRootId(pid BIGINT(20))
    RETURNS BIGINT
BEGIN
 DECLARE i BIGINT(20) DEFAULT 0;
 DECLARE rootId BIGINT(20) DEFAULT  pid;

WHILE pid is not null and pid != '' DO
   SET i = (SELECT parent_id FROM cms_column WHERE id = pid);
    IF i is not null and i != ''  THEN
      set rootId = i;
      set pid = i;
ELSE
        set pid = i;
END IF;
END WHILE;
return rootId;
END;



INSERT INTO `cms_column` VALUES ((select (floor(rand()*10000) + unix_timestamp(now()))), NULL, 'major-units', '各大单位', '各大单位', NULL, NULL, '/wxpd', 0, 'wxpd', 'http://baidu.com', now(), 1, NULL, NULL, now(), now(), '1', '1', b'0');



DROP TABLE IF EXISTS `cms_notice`;
CREATE TABLE `cms_notice`
(
    `id`            bigint(20) UNSIGNED NOT NULL,
    `title`         varchar(255)        NOT NULL COMMENT '文章标题',
    `content`       longtext            NOT NULL COMMENT '内容',
    `essential`     longtext COMMENT '摘要',
    `publisher_id`  bigint(20)                   DEFAULT NULL COMMENT '发布者用户编号',
    `publisher`     varchar(20)                  DEFAULT NULL COMMENT '发布者',
    `publish_unit`  bigint(20)                   DEFAULT NULL COMMENT '发布单位',
    `publish_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `attachment_id` bigint(20)                   DEFAULT NULL COMMENT '附件',
    `remarks`       varchar(255)                 DEFAULT NULL COMMENT '描述',
    `status`        tinyint(1)                   DEFAULT NULL COMMENT '文章状态：0-草稿,1-发布',
    `is_show`       tinyint(1)                   DEFAULT '1' COMMENT '是否显示：0-不显示，1-显示',
    `sort_order`    int(11)                      DEFAULT NULL COMMENT '自定义顺序',
    `hit_nums`      int(11)                      DEFAULT NULL COMMENT '点击次数',
    `is_deleted`    tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT ='通知公告表';
DROP TABLE IF EXISTS `cms_memorial`;

CREATE TABLE `cms_memorial`
(
    `id`            bigint(20) UNSIGNED NOT NULL,
    `title`         varchar(255)                 DEFAULT NULL COMMENT '标题',
    `content`       longtext COMMENT '内容',
    `essential`     longtext COMMENT '摘要',
    `cover_img_url` varchar(255)                 DEFAULT NULL COMMENT '标题图片',
    `compiler`      varchar(20)                  DEFAULT NULL COMMENT '编制人',
    `compiler_id`   bigint(20)                   DEFAULT NULL COMMENT '编制人用户编号',
    `compiler_unit` varchar(255)                 DEFAULT NULL COMMENT '编制单位',
    `remarks`       varchar(255)                 DEFAULT NULL COMMENT '描述',
    `status`        tinyint(1)                   DEFAULT NULL COMMENT '状态：0-草稿,1-已发布',
    `event_time`    datetime                     DEFAULT NULL COMMENT '事件时间',
    `publish_time`  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `sort_order`    int(11)                      DEFAULT '255' COMMENT '自定义顺序',
    `hit_nums`      int(11)                      DEFAULT 0 COMMENT '点击次数',
    `is_deleted`    tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT ='大事记表';


