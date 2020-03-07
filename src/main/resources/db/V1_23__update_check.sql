#-----------------------------------------------------------------------
#添加初检字段
#-----------------------------------------------------------------------
ALTER TABLE oa_service ADD is_initial TINYINT DEFAULT 0 COMMENT '是否初检服务';

#-----------------------------------------------------------------------
#修改检查表名
#-----------------------------------------------------------------------
ALTER TABLE c_check_task RENAME AS ck_task;
ALTER TABLE c_task_content RENAME AS ck_item;
ALTER TABLE c_task_of_member RENAME AS ck_of_member;

#-----------------------------------------------------------------------
#添加初检字段
#-----------------------------------------------------------------------
ALTER TABLE ck_task ADD is_initial TINYINT DEFAULT 0 COMMENT '是否初检服务';

