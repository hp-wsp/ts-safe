#-----------------------------------------------------------------------
# 存储危化品
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_risk_chemistry (
  id CHAR(7) NOT NULL COMMENT '编号',
  comp_id CHAR(32) NOT NULL COMMENT '企业编号',
  name VARCHAR(30) NOT NULL COMMENT '名称',
  alias VARCHAR(30) COMMENT '别名',
  cas VARCHAR(32) DEFAULT '' COMMENT 'CAS编号',
  max_store VARCHAR(50) COMMENT '最大存储数',
  remark VARCHAR(200) DEFAULT '' COMMENT '描述',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_comp_id (comp_id),
  INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#-----------------------------------------------------------------------
# 企业特殊设备
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_spe_equipment (
  id CHAR(7) NOT NULL COMMENT '编号',
  comp_id CHAR(32) NOT NULL COMMENT '企业编号',
  name VARCHAR(30) NOT NULL COMMENT '设备名称',
  num VARCHAR(50) COMMENT '出厂编号',
  dev_danger VARCHAR(500) DEFAULT '' COMMENT '设备隐患',
  remark VARCHAR(200) DEFAULT '' COMMENT '描述',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_comp_id (comp_id),
  INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;