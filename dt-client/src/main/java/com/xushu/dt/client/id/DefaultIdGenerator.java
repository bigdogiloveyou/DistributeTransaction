package com.xushu.dt.client.id;

import java.util.UUID;

/**
 * UUID算法ID生成器
 *
 * @author xushu
 */
public class DefaultIdGenerator implements IdGenerator {

    @Override
    public String getId() {
        return UUID.randomUUID().toString();
    }

}
