#-------------------------------------------------------------------------
#上传资源
#-------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_resource (
  id CHAR(32) NOT NULL,
  file_name VARCHAR(30) NOT NULL COMMENT '文件名',
  file_size INT NOT NULL COMMENT '文件大小',
  content_type VARCHAR(64) NOT NULL COMMENT '文件类型',
  path VARCHAR(200) NOT NULL COMMENT '保存路径',
  username VARCHAR(30) COMMENT '用户名',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;