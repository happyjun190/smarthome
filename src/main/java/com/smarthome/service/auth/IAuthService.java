package com.smarthome.service.auth;


import com.smarthome.commons.result.JsonResult;
import com.smarthome.web.model.in.auth.LoginInModel;

/**
 * Created by wushenjun on 2017/4/15.
 * 权限验证interface，登录注册等
 */
public interface IAuthService {



    /**
     * app用户登录
     * @param inModel
     * @return
     */
    JsonResult appLogin(LoginInModel inModel);





}
