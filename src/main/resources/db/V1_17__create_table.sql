#-----------------------------------------------------------------------
#修改检查报表字段
#-----------------------------------------------------------------------
ALTER TABLE c_check_report CHANGE bz_detail report_detail VARCHAR(5000) COMMENT '编制说明';
ALTER TABLE c_check_report CHANGE check_date check_time_from VARCHAR(20) COMMENT '检查开始日期';
ALTER TABLE c_check_report ADD check_time_to VARCHAR(20) COMMENT '检查结束日期';