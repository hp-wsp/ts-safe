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
CREATE TABLE IF NOT EXISTS b_supervise (
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

#-----------------------------------------------------------------------
#检查类型
#-----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_check_type (
  id CHAR(32) NOT NULL COMMENT '编号',
  name VARCHAR(50) NOT NULL COMMENT '检查类别',
  remark VARCHAR(200)  COMMENT '检查项目',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#-----------------------------------------------------------------------
#检查项目
#-----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_check_item (
  id CHAR(32) NOT NULL COMMENT '编号',
  type_id CHAR(32) NOT NULL COMMENT '检查类型编号',
  name VARCHAR(50) NOT NULL COMMENT '检查项目名称',
  remark VARCHAR(200)  COMMENT '检查项目',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_type_id (type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#-----------------------------------------------------------------------
#检查表
#-----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS b_check_table (
  id CHAR(32) NOT NULL COMMENT '编号',
  type_id CHAR(32) NOT NULL COMMENT '检查类型编号',
  type_name VARCHAR(40) NOT NULL COMMENT '检查类型名称',
  item_id CHAR(32) NOT NULL COMMENT '检查项目编号',
  item_name VARCHAR(50) NOT NULL COMMENT '检查项目名称',
  content VARCHAR(200) NOT NULL DEFAULT '' COMMENT '检查内容',
  con_detail VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '检查内容明细',
  law_item VARCHAR(200) NOT NULL DEFAULT '' COMMENT '法律依据',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_type_id (type_id),
  INDEX idx_item_id (item_id)
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
  mobile VARCHAR(20) DEFAULT '' COMMENT '手机',
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
  profession VARCHAR(200) COMMENT '擅长领域',
  birthday VARCHAR(20) COMMENT '生日',
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

#------------------------------------------------------------------------
#合同
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_contact (
  id CHAR(32) NOT NULL,
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  num VARCHAR(64) NOT NULL COMMENT '合同编号',
  name VARCHAR(30) COMMENT '合同名称',
  con_project VARCHAR(30) DEFAULT '' COMMENT '咨询服务项目',
  ent_comp_type TINYINT NOT NULL COMMENT '委托单位形式',
  ent_comp_name VARCHAR(40) NOT NULL DEFAULT '' COMMENT '委托单位',
  ser_comp_id CHAR(32) NOT NULL DEFAULT '' COMMENT '服务单位编号',
  ser_comp_name VARCHAR(30) NOT NULL DEFAULT '' COMMENT '服务单位名称',
  ser_address VARCHAR(100) DEFAULT '' COMMENT '委托服务地址',
  sig_con_date DATE NOT NULL COMMENT '签约合同时间',
  pro_address VARCHAR(100) DEFAULT '' COMMENT '项目属地',
  ser_con_date_from DATETIME COMMENT '合同约定服务周期开始时间',
  ser_con_date_to DATETIME COMMENT '合同约定服务周期结束时间',
  amount INT NOT NULL COMMENT '合同金额',
  own_person VARCHAR(30) COMMENT '甲方联系人',
  own_phone  VARCHAR(20) COMMENT '甲方联系方式',
  sig_person VARCHAR(30) COMMENT '我方签单人',
  sig_phone  VARCHAR(20) COMMENT '我方签单单位',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_num (num),
  INDEX idx_channel_id (channel_id),
  INDEX idx_ser_comp_name (ent_comp_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#------------------------------------------------------------------------
#合同服务
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_service (
  id CHAR(32) NOT NULL,
  channel_id CHAR(32) NOT NULL COMMENT '服务商编号',
  name VARCHAR(30) NOT NULL COMMENT '服务名称',
  con_id CHAR(32) NOT NULL COMMENT '合同编号',
  con_name VARCHAR(50) NOT NULL COMMENT '合同名称',
  comp_id CHAR(32) NOT NULL COMMENT '服务企业编号',
  comp_name VARCHAR(50) NOT NULL COMMENT '服务企业名称',
  lea_id CHAR(32) NOT NULL COMMENT '负责人编号',
  lea_name VARCHAR(30) NOT NULL COMMENT '负责人名称',
  status ENUM('WAIT','FINISH') NOT NULL DEFAULT 'WAIT' COMMENT '状态',
  update_time DATETIME NOT NULL COMMENT '修改时间',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY idx_con_id (con_id),
  INDEX idx_channel_id (channel_id),
  INDEX idx_lea_id (lea_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

#------------------------------------------------------------------------
#合同服务项目
#------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS c_service_item (
  id CHAR(32) NOT NULL COMMENT '编号',
  service_id CHAR(32) NOT NULL COMMENT '服务编号',
  item_id CHAR(30) NOT NULL COMMENT '项目编号',
  item_name VARCHAR(200) NOT NULL DEFAULT '' COMMENT '项目名称',
  item_value VARCHAR(20) NOT NULL DEFAULT '' COMMENT '项目值',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX idx_service_id (service_id),
  INDEX idx_item_id (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;