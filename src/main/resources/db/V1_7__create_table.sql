#--------------------------------------------------------
# 修改检查任务表名
#--------------------------------------------------------
ALTER TABLE c_check_content RENAME AS c_task_content;

#-----------------------------------------------------------------------
#服务商检查内容
#-----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_check_content (
  id CHAR(5) NOT NULL COMMENT '编号',
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  type_id CHAR(3) NOT NULL COMMENT '检查类型编号',
  type_name VARCHAR(40) NOT NULL COMMENT '检查类型名称',
  item_id CHAR(3) NOT NULL COMMENT '检查项目编号',
  item_name VARCHAR(50) NOT NULL COMMENT '检查项目名称',
  content VARCHAR(200) NOT NULL DEFAULT '' COMMENT '检查内容',
  con_detail VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '检查内容明细',
  law_item VARCHAR(200) NOT NULL DEFAULT '' COMMENT '法律依据',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_channel_id (channel_id),
  INDEX idx_type_id (type_id),
  INDEX idx_item_id (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;