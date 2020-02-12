#-----------------------------------------------------------------------
#修改名称长度
#-----------------------------------------------------------------------
ALTER TABLE b_risk_chemistry CHANGE name name VARCHAR(200) NOT NULL COMMENT '名称';
ALTER TABLE b_risk_chemistry CHANGE alias alias VARcHAR(100) NOT NULL DEFAULT '' COMMENT '别名';

#-----------------------------------------------------------------------
#创建危险识别
#-----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_risk (
  id CHAR(3) NOT NULL COMMENT '编号',
  name VARCHAR(50) NOT NULL COMMENT '名称',
  remark VARCHAR(200)  COMMENT '检查项目',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;