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






