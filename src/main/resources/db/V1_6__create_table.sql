#----------------------------------------------
# 检查任务内容增加检查结果
#----------------------------------------------
ALTER TABLE c_check_content ADD check_result ENUM('WAIT','PASS', 'NOT_PASS') NOT NULL DEFAULT 'WAIT' COMMENT '检查结果';
ALTER TABLE c_check_content ADD dan_level ENUM('NONE','COMMON', 'GREAT') NOT NULL DEFAULT 'NONE' COMMENT '隐患级别';
ALTER TABLE c_check_content ADD dan_describe VARCHAR(500) COMMENT '隐患描述';
ALTER TABLE c_check_content ADD dan_suggest VARCHAR(500) COMMENT '整改建议';
ALTER TABLE c_check_content ADD images VARCHAR(500) COMMENT '隐患图片';