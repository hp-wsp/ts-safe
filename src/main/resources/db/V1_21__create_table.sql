#------------------------------------------------------------
#创建报表检查项目
#------------------------------------------------------------
CREATE TABLE IF NOT EXISTS r_item (
  id CHAR(32) NOT NULL COMMENT '编号',
  report_id CHAR(32) NOT NULL COMMENT '报表编号',
  comp_id CHAR(32) NOT NULL COMMENT '公司编号',
  report_type ENUM('INITIAL','INSPECT') NOT NULL COMMENT '报表类型',
  type_id CHAR(3) NOT NULL COMMENT '检查类型编号',
  type_name VARCHAR(40) NOT NULL COMMENT '检查类型名称',
  content VARCHAR(200) NOT NULL DEFAULT '' COMMENT '检查内容',
  detail VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '检查内容明细',
  law_item VARCHAR(200) NOT NULL DEFAULT '' COMMENT '法律依据',
  check_result ENUM('WAIT','PASS', 'NOT_PASS') NOT NULL DEFAULT 'WAIT' COMMENT '检查结果',
  dan_describe VARCHAR(500) COMMENT '隐患描述',
  dan_suggest VARCHAR(500) COMMENT '整改建议',
  images VARCHAR(500) COMMENT '隐患图片',
  ins_result VARCHAR(500) COMMENT '复查结果',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_report_id (report_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#-----------------------------------------------------------------------
#修改公司字段
#-----------------------------------------------------------------------
ALTER TABLE c_comp_info CHANGE phone phone VARCHAR(20) COMMENT '联系电话';