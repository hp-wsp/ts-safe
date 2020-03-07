#--------------------------------------------------
#修改合同索引
#--------------------------------------------------
DROP INDEX idx_con_id ON oa_service;
ALTER TABLE oa_service ADD INDEX idx_con_id (con_id);