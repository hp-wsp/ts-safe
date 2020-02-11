#-----------------------------------------------------------------------
#修改名称长度
#-----------------------------------------------------------------------
ALTER TABLE b_risk_chemistry CHANGE name name VARCHAR(200) NOT NULL COMMENT '名称';
ALTER TABLE b_risk_chemistry CHANGE alias alias VARcHAR(100) NOT NULL DEFAULT '' COMMENT '别名';