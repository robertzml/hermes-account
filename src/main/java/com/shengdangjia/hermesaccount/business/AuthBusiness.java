package com.shengdangjia.hermesaccount.business;

import com.shengdangjia.hermesaccount.entity.Account;
import com.shengdangjia.hermescommon.model.HermesException;
import com.shengdangjia.hermescommon.utility.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;


@Configuration
public class AuthBusiness {

    @Autowired
    ActionBusiness actionBusiness;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录
     * IMEI 一致时直接登录，保存id token
     *
     * @param account 用户信息
     * @return Id Token
     * @throws HermesException
     */
    public String login(Account account) throws HermesException {
        // 保存操作记录
        this.actionBusiness.insert(account.getId(), (short) 2, account.getImei());

        // 生成id token
        var token = JwtHelper.createIdJWT(account.getId());

        // id token 1小时过期
        stringRedisTemplate.opsForValue().set("id_" + account.getId(), token, Duration.ofHours(1));

        return token;
    }
}
