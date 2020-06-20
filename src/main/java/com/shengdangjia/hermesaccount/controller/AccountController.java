package com.shengdangjia.hermesaccount.controller;

import com.shengdangjia.hermesaccount.business.AccountBusiness;
import com.shengdangjia.hermesaccount.entity.Account;
import com.shengdangjia.hermescommon.model.ErrorCode;
import com.shengdangjia.hermescommon.model.ResponseData;
import com.shengdangjia.hermescommon.utility.RestHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户管理控制器
 */
@Api(tags = "用户管理接口")
@RequestMapping(value = "/account")
@RestController
public class AccountController {

    @Autowired
    AccountBusiness accountBusiness;

    @Autowired
    HttpServletRequest request;

    /**
     * 获取用户列表
     *
     * @return
     */
    @ApiOperation(value = "获取用户列表", notes = "返回所有用户信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    ResponseData<List<Account>> findAll() {
        var r = accountBusiness.findAll();
        return RestHelper.makeResponse(r, ErrorCode.SUCCESS);
    }

    /**
     * 查询用户信息
     * 用户ID、手机号选一
     * @param id 用户ID
     * @param telephone 手机号
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息", notes = "根据用户ID或手机号获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID"),
            @ApiImplicitParam(name = "telephone", value = "用户手机号")
    })
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    ResponseData<Account> find(@RequestParam(value = "id", required = false) String id,
                               @RequestParam(value = "telephone", required = false) String telephone) {
        if (!StringUtils.isEmpty(id)) {
            var r = accountBusiness.findById(id);
            return RestHelper.makeResponse(r, ErrorCode.SUCCESS);
        }

        if (!StringUtils.isEmpty(telephone)) {
            var r = accountBusiness.findByTelephone(telephone);

            // logUtility.verbose("find user", "find user by " + telephone);
            return RestHelper.makeResponse(r, ErrorCode.SUCCESS);
        }

        return RestHelper.makeResponse(null, ErrorCode.ERROR);
    }
}
