package com.ts.server.safe.channel.service;

import com.ts.server.safe.channel.dao.ConServiceItemDao;
import com.ts.server.safe.channel.domain.ConServiceItem;
import com.ts.server.safe.common.id.IdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 合同服务项目业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ConServiceItemService {
    private final ConServiceItemDao dao;

    @Autowired
    public ConServiceItemService(ConServiceItemDao dao) {
        this.dao = dao;
    }

    /**
     * 保存服务项目
     *
     * @param serviceId 服务编号
     * @param items 服务项目集合
     * @return 返回服务项目集合
     */
    public List<ConServiceItem> save(String serviceId, List<ConServiceItem> items){
        dao.deleteOfService(serviceId);
        for(ConServiceItem item: items){
            item.setId(IdGenerators.uuid());
            dao.insert(item);
        }

        return dao.find(serviceId);
    }

    /**
     * 查询服务项目
     *
     * @param serviceId 服务编号
     * @return 服务项目集合
     */
    public List<ConServiceItem> query(String serviceId){
        return dao.find(serviceId);
    }
}
