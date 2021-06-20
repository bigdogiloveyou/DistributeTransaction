package com.xushu.dt.log.repository.mybatis;

import com.xushu.dt.client.exception.DTException;
import com.xushu.dt.client.log.entity.Activity;
import com.xushu.dt.client.log.enums.ActivityStatus;
import com.xushu.dt.client.log.repository.ActivityRepository;
import com.xushu.dt.log.repository.mybatis.mapper.ActivityMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xushu
 */
public class ActivityMybatisRepository implements ActivityRepository {

    @Resource
    private ActivityMapper activityMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insert(Activity activity) {
        int insert = activityMapper.insert(activity);
        if (insert != NumberUtils.INTEGER_ONE) {
            throw new DTException("插入主事务记录Activity异常，事务回滚");
        }
    }

    @Override
    public Activity query(String xid) {
        return activityMapper.query(xid);
    }

    @Override
    public Activity queryForUpdate(String xid) {
        return activityMapper.queryForUpdate(xid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateStatus(String xid, ActivityStatus fromStatus, ActivityStatus toStatus) {
        int update = activityMapper.updateStatus(xid, fromStatus.getStatus(), toStatus.getStatus());
        if (update != NumberUtils.INTEGER_ONE) {
            throw new DTException("更新主事务记录Activity异常");
        }
    }

    @Override
    public List<Activity> queryUnfinished(String shardingKey, Date executionTime, int limit) {
        return activityMapper.queryUnfinished(shardingKey, executionTime, limit);
    }

    @Override
    public void updateRetry(String xid, int fromStatus, int retryCount, Date executeTime) {
        activityMapper.updateRetry(xid, fromStatus, retryCount, executeTime);
    }

}
