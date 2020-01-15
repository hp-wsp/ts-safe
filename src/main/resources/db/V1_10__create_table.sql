#-----------------------------------------------------------------------
# 企业特种作业人员和证书
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_spe_person (
  id CHAR(7) NOT NULL COMMENT '编号',
  comp_id CHAR(32) NOT NULL COMMENT '企业编号',
  name VARCHAR(30) NOT NULL COMMENT '姓名',
  type VARCHAR(20) COMMENT '工种',
  num VARCHAR(50) COMMENT '证书编号',
  from_date VARCHAR(20) DEFAULT '' COMMENT '证书有效开始时间',
  to_date VARCHAR(20) DEFAULT '' COMMENT '证书有效结束时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_comp_id (comp_id),
  INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;