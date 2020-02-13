#-----------------------------------------------------------------------
#创建危险识别
#-----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_occ_disease_job (
  id CHAR(3) NOT NULL COMMENT '编号',
  comp_id CHAR(32) NOT NULL COMMENT '公司编号',
  job VARCHAR(50) NOT NULL COMMENT '岗位',
  job_way VARCHAR(50) COMMENT '作业方式',
  job_formal VARCHAR(50) COMMENT '作业形式',
  risks VARCHAR(30) NOT NULL COMMENT '存在职业病危害因素',
  remark VARCHAR(200)  COMMENT '备注',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_comp_id (comp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;