package com.shengdangjia.hermesaccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录确认模型
 */
@ApiModel(value = "登录确认模型")
public class LoginConfirmModel {
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

    /**
     * 验证码token
     */
    @ApiModelProperty(value = "验证码token")
    public String token;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    public String verifyCode;

    public LoginConfirmModel() {}
}
