package com.shengdangjia.hermesaccount.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@ApiModel(description = "操作记录类")
@Entity
@Table(name = "au_action")
public class Action {
    @Id
    public String id;

    @ApiModelProperty(name = "用户ID")
    public String userId;

    @ApiModelProperty(name = "操作类型")
    public short type;

    @ApiModelProperty(name = "记录时间")
    public Timestamp logTime;

    @ApiModelProperty(name = "参数")
    public String parameter1;
}
