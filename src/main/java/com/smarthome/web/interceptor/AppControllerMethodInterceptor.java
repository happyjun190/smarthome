package com.smarthome.web.interceptor;

import com.smarthome.commons.result.JsonResult;
import com.smarthome.commons.result.ReturnCode;
import com.smarthome.web.model.in.BaseInModel;
import com.smarthome.web.model.in.auth.LoginInModel;
import com.smarthome.web.model.out.log.UserOpLog;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by wushenjun on 2016/11/30.
 */
@Aspect
@Component
public class AppControllerMethodInterceptor extends BaseInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AppControllerMethodInterceptor.class);


    /**
     * 定义拦截规则
     */
    @Pointcut("execution(* com.smarthome.web.controller..*(..)) && (" +
            " @annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            " ||  @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " ||  @annotation(org.springframework.web.bind.annotation.GetMapping) )")
    public void appControllerMethodPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return
     */
    @Around("appControllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) {
        long beginTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        logger.info("请求开始，方法：{}", methodName);

        String userId = null;
        String userName = null;
        String plateform = null;
        Set<Object> allParams = new LinkedHashSet<>();
        Object result = null;
        UserOpLog userOpLog = new UserOpLog();
        userOpLog.setMethod(methodName);
        userOpLog.setCtime(beginTime / 1000);

        //处理参数列表：
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletResponse) {
                //do nothing...
            } else if(arg instanceof BaseInModel) {
                // in model
                BaseInModel baseInModel = (BaseInModel)arg;

                //登录等特殊接口，用其他方法获取userId，如果获取不到则为“-1”
                try {
                    switch (methodName) {
                        case "appLogin":
                            userId = userDAO.getUserIdByPhone(((LoginInModel)arg).getPhone());
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    logger.error("根据登录账号获取userIdInt时发生异常", e);
                }

                plateform = baseInModel.getPlateform();

                //如果userId已经有了（已经通过web cookie获取到，或者通过登录方法获取到），则不用从map中获取
                if(StringUtils.isBlank(userId)){
                    //提前尝试获取userId，以便保存到用户日志中
                    String usertoken = baseInModel.getUserToken();
                    if(StringUtils.isNotBlank(usertoken)){
                        userId = redisTemplate.opsForValue().get(usertoken); // 根据UserToken获取userId
                    }
                }

                baseInModel.setUserId(userId);

                //判断是否需要登陆
                if(isLoginRequired(method) && userId.equals("-1")) { // 该接口需要登录却没有登录……
                    result = JsonResult.build(ReturnCode.NOT_LOGIN, "用户未登录或已经过期，请重新登录。", null);
                }

                allParams.add(baseInModel);

            } else{
                //allParams.add(arg);
            }
        }


        // 判断是否登录
        if(isLoginRequired(method) && StringUtils.isBlank(userId)) {
            result = JsonResult.build(ReturnCode.NOT_LOGIN, "该操作需要登录！");
        }

        try {
            if (result == null) {
                // 一切正常的情况下，继续执行被拦截的方法
                result = pjp.proceed();
            }
        } catch (Throwable e) {
            logger.info("exception: ", e);
            result = JsonResult.build(ReturnCode.EXCEPTION, "发生异常：" + e.getMessage());
        }

        if (result instanceof JsonResult) {
            userOpLog.setJsonResult((JsonResult) result);
            userOpLog.setResultMessage(((JsonResult) result).getMessage());
            userOpLog.setResultCode(((JsonResult)result).getCode());
        }
        userOpLog.setUserId(userId);
        userOpLog.setParamsSet(allParams);
        userOpLog.setCostTime(System.currentTimeMillis() - beginTime);
        userOpLog.setPlateform(plateform);
        //logService.saveUserOpLog(userOpLog);

        return result;
    }

}
