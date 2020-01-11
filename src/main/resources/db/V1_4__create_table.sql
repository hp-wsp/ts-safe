#--------------------------------------------------------
# 修改监管分类改成行业分类
#--------------------------------------------------------
ALTER TABLE b_supervise RENAME AS b_ind_ctg;

#--------------------------------------------------------
# 修改企业行业分类
#--------------------------------------------------------
ALTER TABLE c_comp_info CHANGE ind_ctg_ids ind_ctgs VARCHAR(500) COMMENT '行业分类集合';
ALTER TABLE c_comp_info DROP COLUMN ind_ctg_names;

#---------------------------------------------------------
# 修改检查任务检查员和检查行业分类
#---------------------------------------------------------
ALTER TABLE c_check_task CHANGE check_user_ids check_users VARCHAR(500) COMMENT '检查员集合';
ALTER TABLE c_check_task DROP COLUMN check_user_names;
ALTER TABLE c_check_task CHANGE check_table_ids check_ind_ctgs VARCHAR(500) COMMENT '检查行业分类集合';
ALTER TABLE c_check_task DROP COLUMN check_table_names;
