package com.smarthome.web.model.in.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * app用户登录参数
 */
@ApiModel
public class LoginInModel {

    @ApiModelProperty(value = "手机账号", example="18688888888", required = true)
    String phone;

    @ApiModelProperty(value = "MD5之后的密码", example = "e10adc3949ba59abbe56e057f20f883e", required = true)
    String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
