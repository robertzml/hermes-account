package com.shengdangjia.hermesaccount.controller;

import com.shengdangjia.hermesaccount.business.AccountBusiness;
import com.shengdangjia.hermesaccount.business.AuthBusiness;
import com.shengdangjia.hermesaccount.entity.Account;
import com.shengdangjia.hermesaccount.model.LoginModel;
import com.shengdangjia.hermescommon.model.ErrorCode;
import com.shengdangjia.hermescommon.model.ResponseData;
import com.shengdangjia.hermescommon.utility.RestHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 * 处理用户登录
 */
@Api(tags = "用户登录认证接口")
@RequestMapping(value = "auth")
@RestController
public class AuthController {

    @Autowired
    AccountBusiness accountBusiness;

    @Autowired
    AuthBusiness authBusiness;

    /**
     * 用户登录
     * @param model 登录提交
     * @return
     */
    @ApiImplicitParam(name = "model", value = "用户登录信息", required = true)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseData<String> login(@RequestBody LoginModel model) {
        try {
            var account = accountBusiness.findByTelephone(model.telephone);
            if (account == null) {
                return RestHelper.makeResponse(null, ErrorCode.OBJECT_NOT_FOUND);
            }

            if (account.getImei().equals(model.imei)) {
                // IMEI 一致
                var token = this.authBusiness.login(account);

                return RestHelper.makeResponse(token, ErrorCode.SUCCESS);
            }

            return RestHelper.makeResponse(null, ErrorCode.SUCCESS);
        } catch (Exception e) {
            return RestHelper.makeResponse(null, ErrorCode.DATABASE_FAILED);
        }
    }
}
