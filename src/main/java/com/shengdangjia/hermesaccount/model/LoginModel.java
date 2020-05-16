package com.shengdangjia.hermesaccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录模型
 */
@ApiModel
public class LoginModel {
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    public String telephone;

    /**
     * IMEI
     */
    @ApiModelProperty(value = "IMEI")
    public String imei;

    public LoginModel() {}
}
