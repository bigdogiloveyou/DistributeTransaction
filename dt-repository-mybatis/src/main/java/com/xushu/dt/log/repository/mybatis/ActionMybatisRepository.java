package com.xushu.dt.log.repository.mybatis;

import com.xushu.dt.client.exception.DTException;
import com.xushu.dt.client.log.entity.Action;
import com.xushu.dt.client.log.enums.ActionStatus;
import com.xushu.dt.client.log.repository.ActionRepository;
import com.xushu.dt.log.repository.mybatis.mapper.ActionMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xushu
 */
public class ActionMybatisRepository implements ActionRepository {

    @Resource
    private ActionMapper actionMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insert(Action action) {
        int insert = actionMapper.insert(action);
        if (insert != NumberUtils.INTEGER_ONE) {
            throw new DTException("插入分支事务记录Action异常，事务回滚");
        }
    }

    @Override
    public List<Action> query(String xid) {
        return actionMapper.query(xid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateStatus(String xid, String name, ActionStatus fromStatus, ActionStatus toStatus) {
        int update = actionMapper.updateStatus(xid, name, fromStatus.getStatus(), toStatus.getStatus());
        if (update != NumberUtils.INTEGER_ONE) {
            throw new DTException("更新分支事务记录Action异常");
        }
    }

}
