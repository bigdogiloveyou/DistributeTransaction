package com.xushu.dt.client.log.enums;

/**
 * @author xushu
 */
public enum ActivityStatus {

    /**
     * 主事务初始化
     */
    INIT(0),

    /**
     * 主事务提交
     */
    COMMIT(1),

    /**
     * 主事务提交，分支事务全部执行完成（是先提交分支事务，再提交主事务，能到这个状态说明都成功了）
     */
    COMMIT_FINISH(2),

    /**
     * 主事务回滚
     */
    ROLLBACK(3);

    private int status;

    ActivityStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }


}
