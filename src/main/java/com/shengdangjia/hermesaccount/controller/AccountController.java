package com.shengdangjia.hermesaccount.controller;

import com.shengdangjia.hermesaccount.business.AccountBusiness;
import com.shengdangjia.hermescommon.model.ErrorCode;
import com.shengdangjia.hermescommon.model.ResponseData;
import com.shengdangjia.hermescommon.utility.RestHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理控制器
 */
@Api(tags = "用户管理接口")
@RequestMapping(value = "/account")
@RestController
public class AccountController {

    @Autowired
    AccountBusiness accountBusiness;

    /**
     * 获取用户列表
     *
     * @return
     */
    @ApiOperation("获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    ResponseData getAll() {
        var r = accountBusiness.findAll();
        return RestHelper.makeResponse(r, ErrorCode.SUCCESS);
    }

    /**
     * 获取用户信息
     *
     * @param telephone 手机号
     * @return 用户信息
     */
    @ApiOperation("获取用户信息")
    @ApiImplicitParam(name = "telephone", value = "用户手机号", required = true)
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    ResponseData find(@RequestParam(value = "telephone") String telephone) {
        var r = accountBusiness.findByTelephone(telephone);

        // logUtility.verbose("find user", "find user by " + telephone);
        return RestHelper.makeResponse(r, ErrorCode.SUCCESS);
    }
}
