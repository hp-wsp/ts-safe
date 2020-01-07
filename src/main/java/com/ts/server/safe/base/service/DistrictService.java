package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.DistrictDao;
import com.ts.server.safe.base.domain.District;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 行政区划业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class DistrictService {
    private final DistrictDao dao;

    @Autowired
    public DistrictService(DistrictDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(District t){
        if(dao.has(t.getId())){
            dao.update(t);
        }else{
            dao.insert(t);
        }
    }

    public List<District> query(String parentId){
        return dao.find(parentId);
    }

    public Optional<District> getOpt(String id){
        try{
            return Optional.of(dao.findOne(id));
        }catch (DataAccessException e){
            return Optional.empty();
        }
    }

    public void setDistrictName(String id, Consumer<District> consumer, String errMsg){
        if(StringUtils.isBlank(id)){
            District empty = new District();
            empty.setId("");
            empty.setName("");
            empty.setFullName("");
            consumer.accept(empty);
            return ;
        }

        Optional<District> opt = getOpt(id);
        if(opt.isEmpty()){
            throw new BaseException(errMsg);
        }
        opt.ifPresent(consumer);
    }
}
