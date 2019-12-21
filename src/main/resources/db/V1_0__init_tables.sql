#------------------------------------------------------------------------
#操作日志
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS l_operator (
  id INT(20) NOT NULL AUTO_INCREMENT,
  t_name VARCHAR(32) NOT NULL DEFAULT '' COMMENT '日志名称',
  t_type VARCHAR(20) NOT NULL DEFAULT '' COMMENT '日志类型',
  detail VARCHAR(200) NOT NULL DEFAULT '' COMMENT '描述',
  username VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户名',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_name (t_name),
  INDEX idx_type (t_type),
  INDEX idx_username (username),
  INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#---------------------------------------------------------------------
#特种行业
#---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_spe_industry (
  id CHAR(32) NOT NULL COMMENT '编号',
  name VARCHAR(32) NOT NULL COMMENT '名称',
  remark VARCHAR(200) DEFAULT '' COMMENT '描述',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#-----------------------------------------------------------------------
# 危化品目录
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_risk_chemistry (
  id CHAR(32) NOT NULL COMMENT '编号',
  name VARCHAR(30) NOT NULL COMMENT '名称',
  alias VARCHAR(30) COMMENT '别名',
  cas VARcHAR(32) NOT NULL DEFAULT '' COMMENT 'CAS编号',
  remark VARCHAR(200) DEFAULT '' COMMENT '描述',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_cas (cas)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#-----------------------------------------------------------------------
#监管分类
#-----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_supervise_ctg (
  id CHAR(32) NOT NULL COMMENT '编号',
  num VARCHAR(40) NOT NULL COMMENT '监管分类编号',
  name VARCHAR(30) NOT NULL COMMENT '名称',
  level TINYINT NOT NULL DEFAULT 0 COMMENT '类别级别',
  parent_id CHAR(32) NOT NULL DEFAULT '' COMMENT '上级分类编号',
  remark VARCHAR(200) DEFAULT '' COMMENT '描述',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_num (num),
  INDEX idx_name (name),
  INDEX idx_parent_id (parent_Id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#------------------------------------------------------------------------
#系统管理员表
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_manager (
  id CHAR(32) NOT NULL,
  username VARCHAR(30) NOT NULL COMMENT '用户名',
  password VARCHAR(50) NOT NULL COMMENT '密码',
  name VARCHAR(30) COMMENT '姓名',
  phone VARCHAR(20) DEFAULT '' COMMENT '联系电话',
  email VARCHAR(50) DEFAULT '' COMMENT '邮件地址',
  roles VARCHAR(200) DEFAULT '' NOT NULL COMMENT '权限角色',
  is_root TINYINT DEFAULT 0 NOT NULL COMMENT '1=超级用户',
  is_forbid TINYINT DEFAULT 1 NOT NULL COMMENT '1=禁用',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '1=删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#初始化系统管理员
INSERT INTO b_manager(id, username, password, name, roles, is_root, is_forbid, is_delete, update_time, create_time)
VALUES ('1', 'admin', '12345678', 'admin', "ROLE_SYS", 1, 0, 0, now(), now());

#------------------------------------------------------------------------
#服务商公司
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_channel (
  id CHAR(32) NOT NULL,
  name VARCHAR(30) NOT NULL COMMENT '名称',
  province VARCHAR(60) NOT NULL DEFAULT '' COMMENT '省份',
  city VARCHAR(60) NOT NULL DEFAULT '' COMMENT '城市',
  address VARCHAR(100) NOT NULL DEFAULT '' COMMENT '公司地址',
  reg_address VARCHAR(100) NOT NULL DEFAULT '' COMMENT '注册公司地址',
  phone VARCHAR(20) NOT NULL DEFAULT '' COMMENT '联系电话',
  mobile VARCHAR(20) NOT NULL DEFAULT '' COMMENT '手机',
  contact VARCHAR(15) NOT NULL DEFAULT '' COMMENT '法人',
  bus_scope VARCHAR(2000)  COMMENT '经营范围',
  status ENUM('WAIT','OPEN','CLOSED') NOT NULL DEFAULT 'WAIT' COMMENT '状态',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '1=删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#------------------------------------------------------------------------
#服务商用户
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_member (
  id CHAR(32) NOT NULL,
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  username VARCHAR(30) NOT NULL COMMENT '用户名',
  password VARCHAR(50) NOT NULL COMMENT '密码',
  name VARCHAR(30) COMMENT '姓名',
  phone VARCHAR(20) DEFAULT '' COMMENT '联系电话',
  is_root TINYINT DEFAULT 0 NOT NULL COMMENT '1=超级用户',
  roles VARCHAR(200) DEFAULT '' NOT NULL COMMENT '权限角色',
  status ENUM('INACTIVE','ACTIVE','FORBID') NOT NULL DEFAULT 'ACTIVE' COMMENT '状态',
  is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '1=删除',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_username (username),
  INDEX idx_channel_id (channel_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#------------------------------------------------------------------------
#专家
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_professor (
  id CHAR(32) NOT NULL,
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  name VARCHAR(30) NOT NULL COMMENT '姓名',
  phone VARCHAR(20) NOT NULL DEFAULT '' COMMENT '联系电话',
  profession VARCHAR(200) COMMENT '擅长领域',
  birthday VARCHAR(20) COMMENT '生日',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_channel_id (channel_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#--------------------------------------------------------------------------
#公司基础信息
#--------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_comp_info (
  id CHAR(32) NOT NULL,
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  name VARCHAR(30) NOT NULL COMMENT '姓名',
  province VARCHAR(60) COMMENT '省',
  city  VARCHAR(60) COMMENT '城市',
  country VARCHAR(60) COMMENT '区县',
  address VARCHAR(80) COMMENT '地址',
  reg_address VARCHAR(60) COMMENT '注册地址',
  reg_date VARCHAR(20) COMMENT '注册日志',
  bus_status TINYINT NOT NULL DEFAULT 0 COMMENT '经营状态' ,
  legal_person VARCHAR(20) NOT NULL DEFAULT '' COMMENT '法人',
  legal_phone VARCHAR(20) COMMENT '法人联系电话',
  legal_mobile VARCHAR(20) COMMENT '法人手机',
  safe_person VARCHAR(20) COMMENT '安全员',
  safe_phone VARCHAR(20) COMMENT '安全员联系电话',
  safe_mobile VARCHAR(20) COMMENT '安全员手机',
  contact VARCHAR(20) NOT NULL DEFAULT '' COMMENT '联系人',
  phone VARCHAR(20) NOT NULL DEFAULT '' COMMENT '联系电话',
  mobile VARCHAR(20) NOT NULL DEFAULT '' COMMENT '手机',
  credit_code VARCHAR(64) NOT NULL DEFAULT '' COMMENT '社会信用代码',
  post_code VARCHAR(20)  COMMENT '邮政代码',
  ind_ctg_ids VARCHAR(400) COMMENT '行业分类编号集合',
  ind_ctg_names VARCHAR(400) COMMENT '行业分类名称集合',
  ind_phone VARCHAR(20) COMMENT '联系电话',
  ent_scale TINYINT NOT NULL DEFAULT 0 COMMENT '企业规模',
  ind_of_company VARCHAR(60) COMMENT '行业主管单位',
  mem_fun VARCHAR(20) NOT NULL DEFAULT '' COMMENT '隶属关系',
  man_product VARCHAR(600) COMMENT '经营产品',
  profile VARCHAR(600) COMMENT '企业简介',
  images VARCHAR(400) COMMENT '附图',
  update_time  DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_channel_id (channel_id),
  INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;