package com.xushu.account.dubbo;

import com.xushu.account.service.AccountService;
import com.xushu.common.account.dto.AccountDTO;
import com.xushu.common.account.service.AccountDubboService;
import com.xushu.dt.api.context.DTParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author xushu
 */
@Service
@Slf4j
public class AccountDubboServiceImpl implements AccountDubboService {

    @Resource
    private AccountService accountService;


    @Override
    public boolean freeze(AccountDTO accountDTO) {
        log.info("access AccountDubboService freeze accountDTO:{}", accountDTO);
        try {
            return accountService.freeze(accountDTO);
        } catch (Exception e) {
            log.error("freeze error", e);
            return false;
        }
    }

    @Override
    public void commit(DTParam dtParam, AccountDTO accountDTO) {
        log.info("access AccountDubboService commit dtParam:{}, accountDTO:{}", dtParam, accountDTO);
        accountService.commit(accountDTO);

    }

    @Override
    public void unfreeze(DTParam dtParam, AccountDTO accountDTO) {
        log.info("access AccountDubboService unfreeze dtParam:{}, accountDTO:{}", dtParam, accountDTO);
        accountService.unfreeze(accountDTO);
    }
}
