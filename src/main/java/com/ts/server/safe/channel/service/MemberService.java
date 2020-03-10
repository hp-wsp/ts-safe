package com.ts.server.safe.channel.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.dao.MemberDao;
import com.ts.server.safe.channel.domain.Member;
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
 * 服务商用户业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class MemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    private final MemberDao dao;

    @Autowired
    public MemberService(MemberDao dao) {
        this.dao = dao;
    }

    /**
     * 新增服务商用户
     *
     * @param t {@link Member}
     * @return {@link Member}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Member save(Member t){

        if(dao.hasUsername(t.getUsername())){
            throw new BaseException("用户名已经存在");
        }

        t.setId(IdGenerators.uuid());
        t.setRoot(dao.notHasMember(t.getChannelId()));
        if(StringUtils.isBlank(t.getName())){
            t.setName(t.getUsername());
        }
        dao.insert(t);

        return dao.findOne(t.getId());
    }

    /**
     * 修改服务渠道商用户
     *
     * @param t {@link Member}
     * @return {@link Member}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Member update(Member t){
        if(!dao.update(t)){
            throw new BaseException("修改用户失败");
        }

        return dao.findOne(t.getId());
    }

    /**
     * 得到服务商用户
     *
     * @param id 申报员编号
     * @return {@link Member}
     */
    public Member get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("用户不存在");
        }
    }

    /**
     * 验证用户密码是否正确
     *
     * @param username 用户名
     * @param password 密码
     * @return 验证成功返回申报员信息
     */
    public Optional<Member> getValidate(String username, String password){
        try{
            Member m = dao.findOneByUsername(username);
            if(m.getStatus() != Member.Status.ACTIVE){
                throw new BaseException(106, "用户被禁用，请联系管理员");
            }
            return StringUtils.equals(m.getPassword(), password)? Optional.of(m): Optional.empty();
        }catch (BaseException e){
            throw e;
        }catch (Exception e){
            LOGGER.error("Get member username={},throw={}", username, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * 通过用户名查询服务商用户
     *
     * @param username 用户名
     * @return {@link Member}
     */
    public Optional<Member> getUsername(String username){
        try{
            return Optional.of(dao.findOneByUsername(username));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * 用户名是否存在
     *
     * @param username 用户名
     * @return true 存在
     */
    public boolean hasUsername(String username){
        return dao.hasUsername(username);
    }

    /**
     * 删除服务商用户
     *
     * @param id 编号
     * @return true:删除成功
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        Member t = get(id);
        if(t.isRoot()){
            throw new BaseException("管理员不能删除");
        }

        return dao.delete(id);
    }

    /**
     * 删除服务商用户
     *
     * @param channelId 服务商编号
     */
    public void deleteMembers(String channelId){
        dao.deleteMembers(channelId);
    }

    /**
     * 激活服务商用户
     *
     * @param channelId 服务商编号
     * @param isActive true:激活，false:不激活
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void activeMember(String channelId, boolean isActive){
        dao.activeMembers(channelId, isActive);
    }

    /**
     * 禁用用户
     *
     * @param id 用户编号
     * @return {@link Member}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Member forbid(String id){
        if(!dao.forbid(id, true)){
            throw new BaseException("禁用用户失败");
        }
        return get(id);
    }

    /**
     * 激活用户
     *
     * @param id 用户编号
     * @return {@link Member}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Member active(String id){
        if(!dao.forbid(id, false)){
            throw new BaseException("激活用户失败");
        }
        return get(id);
    }

    /**
     * 修改密码
     *
     * @param id 服务渠道商用户编号
     * @param password 老密码
     * @param newPassword 新密码
     * @return true:修改成功
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePassword(String id, String password, String newPassword){
        Member o = get(id);

        if(!StringUtils.equals(o.getPassword(), password)){
            throw new BaseException("密码错误");
        }

        return dao.updatePassword(id, newPassword);
    }

    /**
     * 重置密码
     *
     * @param id 服务渠道商用户密码
     * @param newPassword 新密码
     * @return true:重置成功
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean resetPassword(String id, String newPassword){
        return dao.updatePassword(id, newPassword);
    }

    /**
     * 查询申报员记录数
     *
     * @param channelId 服务渠道商用户编号
     * @param username 用户名
     * @param phone 联系电话
     * @return 记录数
     */
    public Long count(String channelId, String username, String phone){
        return dao.count(channelId, username, phone);
    }

    /**
     * 查询申报员
     *
     * @param channelId 服务渠道商用户编号
     * @param username 用户名
     * @param phone 联系电话
     * @param offset 查询开始位置
     * @param limit 查询条数
     * @return 申报员集合
     */
    public List<Member> query(String channelId, String username, String phone, int offset, int limit){
        return dao.find(channelId, username, phone, offset, limit);
    }

    /**
     * 查询服务商所有激活用户
     *
     * @param channelId 服务商编号
     * @return 用户集合
     */
    public List<Member> queryActiveMembers(String channelId){
        return dao.findActiveMembers(channelId);
    }

    /**
     * 查询服务商超级用户
     *
     * @param channelId 渠道编号
     * @return {@link Member}
     */
    public Member getRoot(String channelId){
        return dao.findRoot(channelId);
    }
}
