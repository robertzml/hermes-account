package com.shengdangjia.hermesaccount.controller;

import com.shengdangjia.hermesaccount.business.AccountBusiness;
import com.shengdangjia.hermesaccount.model.RegisterConfirmModel;
import com.shengdangjia.hermescommon.model.ErrorCode;
import com.shengdangjia.hermescommon.model.HermesException;
import com.shengdangjia.hermescommon.model.ResponseData;
import com.shengdangjia.hermescommon.utility.RestHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户注册控制器
 */
@Api(tags = "用户注册接口")
@RequestMapping(value = "/register")
@RestController
public class RegisterController {
    @Autowired
    AccountBusiness accountBusiness;

    /**
     * 用户注册
     *
     * @param telephone 电话号码
     * @return token
     */
    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "telephone", value = "手机号", required = true)
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    ResponseData<String> signUp(@RequestParam(value = "telephone") String telephone) {
        try {
            var token = accountBusiness.register(telephone);

            return RestHelper.makeResponse(token, ErrorCode.SUCCESS);
        } catch (HermesException e) {
            return RestHelper.makeResponse(null, e.getCode(), e.getMessage());
        } catch (Exception e) {
            return RestHelper.makeResponse(null, ErrorCode.DATABASE_FAILED);
        }
    }

    /**
     * 注册确认
     *
     * @param model 确认参数
     * @return
     */
    @ApiOperation("用户注册确认")
    @ApiImplicitParam(name = "model", value = "确认参数", required = true)
    @RequestMapping(value = "/signUpConfirm", method = RequestMethod.POST)
    ResponseData signUpConfirm(@RequestBody RegisterConfirmModel model) {
        try {
            accountBusiness.registerFinish(model.telephone, model.imei, model.token, model.verifyCode);
            return RestHelper.makeResponse(null, ErrorCode.SUCCESS);
        } catch (HermesException e) {
            return RestHelper.makeResponse(null, e.getCode(), e.getMessage());
        } catch (Exception e) {
            return RestHelper.makeResponse(null, ErrorCode.DATABASE_FAILED);
        }
    }
}
