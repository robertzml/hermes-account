package com.shengdangjia.hermesaccount.business;

import com.shengdangjia.hermesaccount.entity.Account;
import com.shengdangjia.hermesaccount.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountBusiness {
    @Autowired
    AccountRepository accountRepository;

    /**
     * 获取所有用户
     *
     * @return
     */
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    /**
     * 按ID获取用户
     * @param id 用户ID
     * @return
     */
    public Account findById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    /**
     * 按手机号查询用户
     *
     * @param telephone 手机号
     * @return
     */
    public Account findByTelephone(String telephone) {
        return accountRepository.findByTelephone(telephone);
    }
}
