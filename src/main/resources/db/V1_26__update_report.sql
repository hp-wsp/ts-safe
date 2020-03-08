#-----------------------------------------------------------------------
#删除初检报表表
#-----------------------------------------------------------------------
DROP TABLE IF EXISTS r_initial;
DROP TABLE IF EXISTS r_item;
DROP TABLE IF EXISTS ck_of_member;
DROP TABLE IF EXISTS ck_item;

#-----------------------------------------------------------------------
#创建检查报表
#-----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ck_report (
  id CHAR(32) NOT NULL COMMENT '编号',
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  service_id CHAR(32) NOT NULL COMMENT '服务系统编号',
  service_name VARCHAR(30) NOT NULL COMMENT '服务名称',
  comp_id CHAR(32) NOT NULL COMMENT '企业编号',
  comp_name VARCHAR(30) NOT NULL COMMENT '企业名称',
  task_id CHAR(32) NOT NULL COMMENT '检查任务编号',
  is_initial TINYINT DEFAULT 0 COMMENT '是否初检服务',
  content TEXT NOT NULL COMMENT '报表内容，报表JSON结构数据',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_channel_id (channel_id),
  UNIQUE KEY idx_task_id (task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#------------------------------------------------------------------
#修改报表检查项目
#------------------------------------------------------------------
ALTER TABLE c_task_content RENAME AS ck_item;
ALTER TABLE ck_item DROP item_id;
ALTER TABLE ck_item DROP item_name;
ALTER TABLE ck_item ADD con_id CHAR(32) NOT NULL COMMENT '合同编号';
ALTER TABLE ck_item ADD comp_id CHAR(32) NOT NULL COMMENT '公司编号';
ALTER TABLE ck_item ADD is_initial TINYINT DEFAULT 0 COMMENT '是否初检服务';
ALTER TABLE ck_item ADD INDEX idx_con_id_comp_id (con_id, comp_id);
ALTER TABLE ck_item ADD UNIQUE KEY idx_taks_id (task_id);