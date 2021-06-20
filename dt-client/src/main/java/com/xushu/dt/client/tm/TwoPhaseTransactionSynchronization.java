package com.xushu.dt.client.tm;

import com.xushu.dt.client.context.DTContext;
import com.xushu.dt.client.context.DTContextEnum;
import com.xushu.dt.client.exception.DTException;
import com.xushu.dt.client.log.entity.Activity;
import org.springframework.transaction.support.TransactionSynchronization;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 本地事务同步器。
 * 用于在本地事务提交或回滚后执行分布式事务整体的提交或回滚
 *
 * @author xushu
 */
public class TwoPhaseTransactionSynchronization implements TransactionSynchronization {

    @Resource
    private TransactionManager dtTransactionManager;

    @Override
    public void beforeCommit(boolean readOnly) {
        if (!DTContext.inTransaction()) {
            return;
        }
        Activity activity = DTContext.get(DTContextEnum.ACTIVITY);
        if (activity.getTimeoutTime().before(new Date())) {
            throw new DTException("分布式事务提交超时");
        }
    }

    @Override
    public void afterCompletion(int status) {
        if (!DTContext.inTransaction()) {
            return;
        }
        try {
            if (status == TransactionSynchronization.STATUS_COMMITTED) {
                dtTransactionManager.commit();
            } else if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                dtTransactionManager.rollback();
            }
        } finally {
            DTContext.clear();
        }
    }

}
