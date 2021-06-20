package com.xushu.pay.mapper;

import com.xushu.pay.entity.PayChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xushu
 */
@Repository
public interface PayChannelMapper {

    List<PayChannel> query(@Param("uid") String uid, @Param("orderId") String orderId);

    int insert(@Param("channels") List<PayChannel> channels);

}
