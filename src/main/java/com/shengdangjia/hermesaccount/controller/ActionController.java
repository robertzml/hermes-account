package com.shengdangjia.hermesaccount.controller;

import com.shengdangjia.hermesaccount.business.ActionBusiness;
import com.shengdangjia.hermesaccount.entity.Action;
import com.shengdangjia.hermescommon.model.ErrorCode;
import com.shengdangjia.hermescommon.model.ResponseData;
import com.shengdangjia.hermescommon.utility.RestHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "操作记录接口")
@RequestMapping(value = "action")
@RestController
public class ActionController {
    @Autowired
    ActionBusiness actionBusiness;

    /**
     * 获取所有操作记录
     * @return
     */
    @ApiOperation("获取所有操作记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    ResponseData<List<Action>> findAll() {
        var r = actionBusiness.findAll();
        return RestHelper.makeResponse(r, ErrorCode.SUCCESS);
    }
}
