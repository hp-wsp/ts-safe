#--------------------------------------------------
# 测试员的测试任务
#--------------------------------------------------
CREATE TABLE IF NOT EXISTS c_task_of_member (
  id INT(11) NOT NULL AUTO_INCREMENT,
  mem_id CHAR(32) NOT NULL COMMENT '检查员编号',
  task_id CHAR(32) NOT NULL COMMENT '任务编号',
  status ENUM('WAIT','FINISH') NOT NULL DEFAULT 'WAIT' COMMENT '状态',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_mem_id_task_id (mem_id, task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;