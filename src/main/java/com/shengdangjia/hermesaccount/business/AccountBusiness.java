package com.shengdangjia.hermesaccount.business;

import com.shengdangjia.hermesaccount.entity.Account;
import com.shengdangjia.hermesaccount.entity.Action;
import com.shengdangjia.hermesaccount.repository.AccountRepository;
import com.shengdangjia.hermesaccount.repository.ActionRepository;
import com.shengdangjia.hermesaccount.utility.SMSHelper;
import com.shengdangjia.hermescommon.model.HermesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.UUID;
import java.util.List;
import java.util.Random;

/**
 * 用户业务类
 */
@Configuration
public class AccountBusiness {
    //region 字段
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    //endregion


    //region 注册过程

    /**
     * 用户注册申请
     * @param telephone 电话号码
     * @return token 验证码token
     * @throws HermesException
     */
    public String register(String telephone) throws HermesException {
        if (telephone.length() != 11) {
            throw new HermesException(21, "手机号格式错误");
        }

        // 检查手机号是否存在
        var account = this.accountRepository.findByTelephone(telephone);
        if (account != null) {
            throw new HermesException(20, "手机号已存在");
        }

        // 发送验证码
        String verifyCode = generateCode();
        var result = SMSHelper.sendVerifyCode(telephone, verifyCode);
        if (!result) {
            throw new HermesException(22, "发送验证码失败");
        }

        // 生成验证码token
        var token = UUID.randomUUID().toString();

        // 验证码有效期10分钟
        stringRedisTemplate.opsForValue().set("vc_" + token, telephone + verifyCode, Duration.ofSeconds(10 * 60));
        return token;
    }

    /**
     * 注册完成
     * 成功后添加用户
     * @param telephone     手机
     * @param imei          IMEI
     * @param token         验证令牌
     * @param verifyCode    验证码
     * @throws HermesException
     */
    @Transactional(rollbackFor = {HermesException.class, RuntimeException.class, Error.class})
    public void registerFinish(String telephone, String imei, String token, String verifyCode) throws HermesException {
        // 比较验证码
        var vc = stringRedisTemplate.opsForValue().get("vc_" + token);
        if (vc == null || !vc.equals(telephone + verifyCode))
            throw new HermesException(23, "验证码错误");

        Account account = new Account();
        var uid = UUID.randomUUID().toString();
        account.setId(uid);
        account.setTelephone(telephone);
        account.setImei(imei);
        account.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        account.setStatus(0);
        var t = accountRepository.save(account);

        Action action = new Action();
        action.id = UUID.randomUUID().toString();
        action.userId = uid;
        action.type = (short)1;
        action.logTime = new Timestamp(System.currentTimeMillis());
        action.parameter1 = imei;
        actionRepository.save(action);
    }

    //endregion

    /**
     * 获取所有用户
     *
     * @return
     */
    public List<Account> findAll() {
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

    /**
     * 生成手机验证码
     *
     * @return 六位验证码
     */
    private String generateCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
