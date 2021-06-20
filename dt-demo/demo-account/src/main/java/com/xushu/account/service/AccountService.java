package com.xushu.account.service;

import com.xushu.common.account.dto.AccountDTO;

/**
 * @author xushu
 */
public interface AccountService {

    boolean freeze(AccountDTO accountDTO);

    boolean commit(AccountDTO accountDTO);

    boolean unfreeze(AccountDTO accountDTO);
}
