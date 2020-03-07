#-----------------------------------------------------------------------
#修改合同关联服务企业
#-----------------------------------------------------------------------
ALTER TABLE c_contract DROP ser_comp_id;
ALTER TABLE c_contract DROP ser_comp_name;
ALTER TABLE c_contract ADD ser_companies VARCHAR(1500) DEFAULT '' COMMENT '服务企业';
ALTER TABLE c_contract ADD is_complete TINYINT DEFAULT false COMMENT '合同是否完成';
ALTER TABLE c_contract ADD attaches VARCHAR(1000) DEFAULT '' COMMENT '合同附件集合';

#-----------------------------------------------------------------------
#修改OA系统表名
#-----------------------------------------------------------------------
ALTER TABLE c_service RENAME AS oa_service;
ALTER TABLE c_service_item RENAME AS oa_ser_item;
ALTER TABLE c_contract RENAME AS oa_contract;