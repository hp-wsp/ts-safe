#-----------------------------------------------------------------------
#增加是否删除字段
#-----------------------------------------------------------------------
ALTER TABLE oa_contract ADD is_delete TINYINT DEFAULT 0 COMMENT '是否删除';
ALTER TABLE oa_contract DROP service_id;