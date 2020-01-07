package com.ts.server.safe.base.service;

import com.ts.server.safe.base.dao.ResourceDao;
import com.ts.server.safe.base.domain.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 资源业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceService.class);
    private final ResourceDao dao;

    @Autowired
    public ResourceService(ResourceDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Resource t){
        dao.insert(t);
    }

    public Optional<Resource> get(String id){
        try{
            return Optional.of(dao.findOne(id));
        }catch (DataAccessException e){
            LOGGER.error("Get resource fail id={},throws={}", id, e.getMessage());
            return Optional.empty();
        }
    }
}
