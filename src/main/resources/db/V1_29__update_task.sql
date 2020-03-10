#-----------------------------------------------------------------------
#添加负责人字段
#-----------------------------------------------------------------------
ALTER TABLE ck_task ADD lea_id CHAR(32) NOT NULL COMMENT '负责人编号';
ALTER TABLE ck_task ADD lea_name VARCHAR(30) NOT NULL COMMENT '负责人名称';