package com.ts.server.safe.company.service;

import com.ts.server.safe.company.dao.CompProductDao;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.company.domain.CompProduct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 生产企业信息业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class CompProductService {
    private final CompProductDao dao;

    @Autowired
    public CompProductService(CompProductDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CompProduct> save(CompInfo info, List<CompProduct> products){
        dao.deleteByCompId(info.getId());

        Arrays.stream(CompProductKey.values())
                .map(e -> buildProduct(info, e, products))
                .forEach(dao::insert);

        return dao.find(info.getId());
    }

    private CompProduct buildProduct(CompInfo info, CompProductKey key, List<CompProduct> products){
        return products.stream()
                .filter(e -> StringUtils.equals(key.getKey(), e.getProKey()))
                .peek(e -> {
                    e.setCompId(info.getId());
                    e.setChannelId(info.getChannelId());
                    e.setProName(key.getRemark());
                })
                .findFirst()
                .orElseGet(() -> {
                    CompProduct t = new CompProduct();
                    t.setCompId(info.getId());
                    t.setChannelId(info.getChannelId());
                    t.setProKey(key.getKey());
                    t.setProName(key.getRemark());
                    t.setProValue(key.getDefValue());
                    return t;
                });
    }

    public List<CompProduct> query(String compId){
        return dao.find(compId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOfCompany(String compId){
        dao.deleteByCompId(compId);
    }
}