package com.xushu.common.account.service;

import com.xushu.common.account.dto.AccountDTO;
import com.xushu.dt.api.annotation.TwoPhaseCommit;
import com.xushu.dt.api.context.DTParam;

/**
 * @author xushu
 */
public interface AccountDubboService {

    @TwoPhaseCommit(name = "transferOutAccount", confirmMethod = "commit", cancelMethod = "unfreeze")
    boolean freeze(AccountDTO accountDTO);

    void commit(DTParam dtParam, AccountDTO accountDTO);

    void unfreeze(DTParam dtParam, AccountDTO accountDTO);

}
