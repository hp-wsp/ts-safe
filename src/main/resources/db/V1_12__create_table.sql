#------------------------------------------------------------------------
# 检查报告
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_check_report (
  id CHAR(7) NOT NULL COMMENT '编号',
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  channel_name VARCHAR(30) NOT NULL COMMENT '服务商名称',
  comp_id CHAR(32) NOT NULL COMMENT '企业编号',
  comp_name VARCHAR(30) NOT NULL COMMENT '企业名称',
  task_id CHAR(32) NOT NULL COMMENT '检查任务编号',
  task_detail VARCHAR(60) NOT NULL COMMENT '检查任务描述',
  cycle_name VARCHAR(30) NOT NULL COMMENT '周期名称',
  ent_comp_type TINYINT NOT NULL COMMENT '委托单位形式',
  industry VARCHAR(30) COMMENT '所属行业',
  ent_scale TINYINT NOT NULL COMMENT '企业规模',
  area VARCHAR(200) COMMENT '所属区域',
  check_date VARCHAR(20) COMMENT '检查日期',
  bz_detail VARCHAR(5000) COMMENT '编制说明',
  comp_base_info VARCHAR(5000) COMMENT '受检企业基本情况',
  safe_product VARCHAR(5000) COMMENT '安全生产社会化检查服务',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_channel_id (channel_id),
  INDEX idx_comp_name (comp_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#-----------------------------------------------------------------------
#修改检查任务
#-----------------------------------------------------------------------
ALTER TABLE c_check_task ADD num CHAR(32) NOT NULL COMMENT '检查任务编号';
CREATE UNIQUE INDEX idx_num ON c_check_task(num);