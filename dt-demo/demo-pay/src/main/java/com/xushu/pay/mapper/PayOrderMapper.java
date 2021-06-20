package com.xushu.pay.mapper;

import com.xushu.pay.entity.PayOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author xushu
 */
@Repository
public interface PayOrderMapper {

    PayOrder query(@Param("uid") String uid, @Param("orderId") String orderId);

    int insert(PayOrder order);

}
