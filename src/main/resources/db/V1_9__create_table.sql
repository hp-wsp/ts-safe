#-----------------------------------------------------------------
#--修检查内容编号长度
#-----------------------------------------------------------------
ALTER TABLE b_check_content CHANGE id id CHAR(7) NOT NULL COMMENT '编号';
ALTER TABLE c_check_content CHANGE id id CHAR(7) NOT NULL COMMENT '编号';