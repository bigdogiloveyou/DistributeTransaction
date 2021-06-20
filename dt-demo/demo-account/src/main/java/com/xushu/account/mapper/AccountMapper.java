package com.xushu.account.mapper;

import com.xushu.account.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author xushu
 */
@Repository
public interface AccountMapper {

    Account query(@Param("uid") String uid);

    int freeze(@Param("uid") String uid, @Param("amount") long amount);

    int commit(@Param("uid") String uid, @Param("amount") long amount);

    int unfreeze(@Param("uid") String uid, @Param("amount") long amount);

}
