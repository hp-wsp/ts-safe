package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.ManagerDao;
import com.ts.server.safe.base.domain.Manager;
import com.ts.server.safe.common.id.IdGenerators;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 管理员业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerService.class);

    private final ManagerDao dao;

    @Autowired
    public ManagerService(ManagerDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Manager save(Manager t){

        if(dao.hasUsername(t.getUsername())){
            throw new BaseException("用户名已经使用");
        }
        t.setId(IdGenerators.uuid());

        dao.insert(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Manager update(Manager t){

        if(!dao.update(t)){
            throw new BaseException("修改管理失败");
        }

        return dao.findOne(t.getId());
    }

    public Manager get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            LOGGER.debug("Get manager fail id ={},throw={}", id, e.getMessage());
            throw new BaseException("管理员不存在");
        }
    }

    public Optional<Manager> getValidate(String username, String password){
        try {
            Manager m = dao.findOneByUsername(username);
            if (m.isForbid()) {
                throw new BaseException(106, "用户被禁用，请联系管理员");
            }
            return StringUtils.equals(m.getPassword(), password) ? Optional.of(m) : Optional.empty();
        }catch (BaseException e){
            throw e;
        }catch (DataAccessException e){
            LOGGER.error("Get manager username={},throw={}", username, e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Manager> getUsername(String username){
        try{
            return Optional.of(dao.findOneByUsername(username));
        }catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        Manager t = get(id);
        if(t.isRoot()){
            throw new BaseException("超级管理员不能删除");
        }
        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePassword(String id, String password, String newPassword){
        Manager manager = get(id);

        if(!StringUtils.equals(manager.getPassword(), password)){
            throw new BaseException("密码错误");
        }

        return dao.updatePassword(id, newPassword);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean resetPassword(String id, String newPassword){
        return dao.updatePassword(id, newPassword);
    }

    public Long count(String username){
        return dao.count(username);
    }

    public List<Manager> query(String username, int offset, int limit){
        return dao.find(username, offset, limit);
    }
}
