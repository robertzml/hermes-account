package com.shengdangjia.hermesaccount.controller;

import com.shengdangjia.hermesaccount.business.AccountBusiness;
import com.shengdangjia.hermesaccount.business.AuthBusiness;
import com.shengdangjia.hermesaccount.model.LoginConfirmModel;
import com.shengdangjia.hermesaccount.model.LoginModel;
import com.shengdangjia.hermescommon.model.ErrorCode;
import com.shengdangjia.hermescommon.model.ResponseData;
import com.shengdangjia.hermescommon.utility.RestHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
     * 验证IMEI，一致则直接登录，不一致则发送验证码
     * @param model 登录提交
     * @return 成功返回Id token，失败返回验证码 token
     */
    @ApiOperation(value = "用户登录", notes = "验证IMEI，一致则直接登录，不一致则发送验证码")
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
            } else {
                // IMEI 不一致
                var token = this.authBusiness.sendVerifyCode(model.telephone);

                return RestHelper.makeResponse(token, 24, "更换登录设备");
            }

        } catch (Exception e) {
            return RestHelper.makeResponse(null, ErrorCode.DATABASE_FAILED);
        }
    }

    /**
     * 用户登录确认
     * @param model 登录确认模型
     * @return id token
     */
    @ApiOperation(value = "用户登录确认", notes = "比较验证码")
    @ApiImplicitParam(name = "model", value = "用户登录确认信息", required = true)
    @RequestMapping(value = "/loginConfirm", method = RequestMethod.POST)
    public ResponseData<String> loginConfirm(@RequestBody LoginConfirmModel model) {
        try {
            var token = this.authBusiness.loginConfirm(model.telephone, model.imei, model.token, model.verifyCode);

            return RestHelper.makeResponse(token, ErrorCode.SUCCESS);
        } catch (Exception e) {
            return RestHelper.makeResponse(null, ErrorCode.DATABASE_FAILED);
        }
    }
}
