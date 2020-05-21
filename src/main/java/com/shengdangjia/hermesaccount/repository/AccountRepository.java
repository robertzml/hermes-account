package com.shengdangjia.hermesaccount.repository;

import com.shengdangjia.hermesaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

    public Account findByTelephone(String telephone);
}
