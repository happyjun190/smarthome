package com.smarthome.web.model.out.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录DTO
 * Created by wushenjun on 2017/03/02.
 */
@Data
@ApiModel
public class LoginDTO {
    @ApiModelProperty(value = "用户id", required = true)
    private int userId;
    @ApiModelProperty(value = "用户token", required = true)
    private String userToken;

}