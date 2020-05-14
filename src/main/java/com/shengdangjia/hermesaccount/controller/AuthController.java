package com.shengdangjia.hermesaccount.controller;

import com.shengdangjia.hermescommon.model.ErrorCode;
import com.shengdangjia.hermescommon.model.ResponseData;
import com.shengdangjia.hermescommon.utility.RestHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 * 处理用户登录
 */
@RequestMapping(value = "auth")
@RestController
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseData login() {

        return RestHelper.makeResponse(null, ErrorCode.SUCCESS);
    }
}
