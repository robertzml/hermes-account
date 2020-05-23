package com.shengdangjia.hermesaccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 注册确认模型
 */
@ApiModel(value = "注册确认模型")
public class RegisterConfirmModel {
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    public String telephone;

    /**
     * IMEI
     */
    public String imei;

    /**
     * 验证码token
     */
    public String token;

    /**
     * 验证码
     */
    public String verifyCode;

    public RegisterConfirmModel() {}
}
