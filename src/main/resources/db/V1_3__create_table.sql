#-------------------------------------------------------------------------
#企业生产信息
#-------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_comp_product (
  id INT(11) NOT NULL AUTO_INCREMENT,
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  comp_id CHAR(32) NOT NULL COMMENT '公司编号',
  pro_key VARCHAR(20) NOT NULL COMMENT '生产信息KEY',
  pro_value TINYINT DEFAULT 0 NOT NULL COMMENT '生产信息值',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_comp_id (comp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;