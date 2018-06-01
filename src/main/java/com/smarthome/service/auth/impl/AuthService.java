package com.smarthome.service.auth.impl;

import com.smarthome.commons.constant.RedisConstants;
import com.smarthome.commons.result.JsonResult;
import com.smarthome.commons.result.ReturnCode;
import com.smarthome.dao.user.UserDAO;
import com.smarthome.model.user.TabUserInfo;
import com.smarthome.service.auth.IAuthService;
import com.smarthome.util.EncryptUtils;
import com.smarthome.util.XDateUtils;
import com.smarthome.web.model.in.auth.LoginInModel;
import com.smarthome.web.model.out.auth.LoginDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by wushenjun on 2017/4/15.
 * 权限验证service，登录注册等
 */
@Service
public class AuthService implements IAuthService {
    /** 数字 */
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserDAO userDAO;

    @Override
    public JsonResult appLogin(LoginInModel inModel) {
        String loginName = inModel.getPhone();//登录账号
        String password = inModel.getPassword();//登录密码
        if (StringUtils.isAnyEmpty(loginName,password)) {
            return JsonResult.build(ReturnCode.PARAM_ERROR, "请输入账号或密码!");
        }
        logger.info("password:{}", EncryptUtils.MD5Str(password));
        //根据注册账号判断用户是否已经注册
        TabUserInfo tabUserInfo = userDAO.getUserInfoByPhone(loginName);
        if(tabUserInfo!=null && tabUserInfo.getPassword().equals(EncryptUtils.MD5Str(password + tabUserInfo.getLoginSalt()))) {
            // 生成一个随机的token
            String token = UUID.randomUUID().toString().replace("-", "");
            String tokenKey = RedisConstants.Prefix.APP_TOKEN + token;
            //TODO
            //保存login相关的信息到redis中
            String userLoginInfoKey = RedisConstants.Prefix.USER_LOGIN_INFO + String.valueOf(tabUserInfo.getId());

            Map<String, String> userLoginInfo = new HashMap<>();
            userLoginInfo.put(RedisConstants.UserLoginInfo.APP_TOKEN.id(), token);
            userLoginInfo.put(RedisConstants.UserLoginInfo.APP_LOGIN_TIME.id(), XDateUtils.getNowTime());

            redisTemplate.opsForHash().putAll(userLoginInfoKey, userLoginInfo);

            int maxRedisAge = RedisConstants.Prefix.APP_TOKEN.ttl();// token存进redis，保存一天（单位：分钟）
            //设置token to userId
            redisTemplate.opsForValue().set(tokenKey, String.valueOf(tabUserInfo.getId()), maxRedisAge);

            /*Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("userToken", token);*/
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserToken(token);
            loginDTO.setUserId(tabUserInfo.getId());

            return JsonResult.build(ReturnCode.SUCCESS, "登录成功！", loginDTO);
        }

        return JsonResult.build(ReturnCode.PARAM_ERROR, "账号或密码错误！");
    }





}
