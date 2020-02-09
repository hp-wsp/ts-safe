#-----------------------------------------------------------------------
#修改编号长度
#-----------------------------------------------------------------------
ALTER TABLE c_check_report CHANGE id id CHAR(32) NOT NULL COMMENT '编号';
ALTER TABLE c_check_report CHANGE check_date check_date CHAR(50) NOT NULL COMMENT '检查日期';