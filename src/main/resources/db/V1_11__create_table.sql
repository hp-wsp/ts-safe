#-----------------------------------------------------------------------
# 添加是否复查以前查出的隐患字段
#------------------------------------------------------------------------
ALTER TABLE c_check_task ADD is_review  TINYINT DEFAULT 0 NOT NULL COMMENT '是否复查以前查出的隐患';
ALTER TABLE c_check_task ADD num CHAR(32) NOT NULL COMMENT '检查任务编号';
CREATE UNIQUE INDEX idx_num ON c_check_task(num);

#-----------------------------------------------------------------------
#用户添加职务和手机号
#-----------------------------------------------------------------------
ALTER TABLE c_member ADD mobile VARCHAR(20) COMMENT '手机';
ALTER TABLE c_member ADD job VARCHAR(20) COMMENT '职务';