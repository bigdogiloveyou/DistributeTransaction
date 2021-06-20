package com.xushu.dt.client.tc;

import com.xushu.dt.client.context.ActionContext;
import com.xushu.dt.client.context.DTContext;
import com.xushu.dt.client.context.DTContextEnum;
import com.xushu.dt.client.log.entity.Action;
import com.xushu.dt.client.log.entity.Activity;
import com.xushu.dt.client.log.enums.ActionStatus;
import com.xushu.dt.client.log.enums.ActivityStatus;
import com.xushu.dt.client.log.repository.ActionRepository;
import com.xushu.dt.client.log.repository.ActivityRepository;
import com.xushu.dt.client.tm.TransactionManager;
import com.xushu.dt.client.util.Utils;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author xushu
 */
@Slf4j
public class TransactionCoordinatorImpl implements TransactionCoordinator {

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private TransactionManager dtTransactionManager;

    @Override
    public void batchReTry(CoordinatorParam param) {
        List<Activity> activities = activityRepository.queryUnfinished(param.getShardingKey(), new Date(), param.getLimit());
        for (Activity activity : activities) {
            if (!execute(activity)) {
                activityRepository.updateRetry(activity.getXid(), activity.getStatus(), activity.getRetryCount() + 1, getRetryTime(activity.getExecutionTime()));
            }
        }
    }

    /**
     * 执行单个Activity补偿操作
     *
     * @param activity
     * @return
     */
    private boolean execute(Activity activity) {
        boolean result = false;
        ActivityStatus activityStatus = Utils.getFinalStatus(activity);
        if (activityStatus == null) {
            return false;
        }
        try {
            Utils.initDTContext(activity);
            ActionContext actionContext = DTContext.get(DTContextEnum.ACTION_CONTEXT);
            List<Action> actions = actionRepository.query(activity.getXid());
            for (Action action : actions) {
                if (ActionStatus.INIT.getStatus() == action.getStatus()) {
                    actionContext.put(action.getName(), action);
                }
            }
            if (ActivityStatus.COMMIT_FINISH == activityStatus) {
                result = dtTransactionManager.commit();
            } else if (ActivityStatus.ROLLBACK == activityStatus) {
                result = dtTransactionManager.rollback();
            }
        } finally {
            DTContext.clear();
        }
        return result;
    }

    private Date getRetryTime(Date executionTime) {
        Instant instant = executionTime.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime tmp = instant.atZone(zoneId).toLocalDateTime();
        LocalDateTime retryTime = tmp.plusMinutes(5);
        return Date.from(retryTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
