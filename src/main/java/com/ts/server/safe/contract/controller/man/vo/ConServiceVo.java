package com.ts.server.safe.contract.controller.man.vo;

import com.ts.server.safe.contract.domain.ConService;
import com.ts.server.safe.contract.domain.ConServiceItem;

import java.util.List;

/**
 * 合同服务输出数据对象
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ConServiceVo extends ConService {
    private List<ConServiceItem> items;

    public ConServiceVo(ConService t, List<ConServiceItem> items){
        this.setId(t.getId());
        setChannelId(t.getChannelId());
        setConId(t.getConId());
        setConName(t.getConName());
        setCompId(t.getCompId());
        setCompName(t.getCompName());
        setLeaId(t.getLeaId());
        setLeaName(t.getLeaName());
        setStatus(t.getStatus());
        setInitial(t.isInitial());
        setCreateTime(t.getCreateTime());
        this.items = items;
    }

    public List<ConServiceItem> getItems() {
        return items;
    }

    public void setItems(List<ConServiceItem> items) {
        this.items = items;
    }
}
