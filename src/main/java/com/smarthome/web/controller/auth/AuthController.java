package com.smarthome.web.controller.auth;

import com.smarthome.commons.annotation.Permission;
import com.smarthome.commons.result.JsonResult;
import com.smarthome.service.auth.IAuthService;
import com.smarthome.web.controller.version.VersionController;
import com.smarthome.web.model.in.auth.LoginInModel;
import com.smarthome.web.model.out.auth.LoginDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wushenjun on 2018/6/1.
 */

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(VersionController.class);

    private final IAuthService authService;


    /**
     * app登录
     * @param request
     * @param version
     * @param inModel
     * @return
     */
    @ApiOperation(value = "app登录", tags="wushenjun", notes = "app登录")
    @Permission(loginReqired=false)
    @RequestMapping(value = "appLogin/{version}", method = RequestMethod.POST)
    public JsonResult<LoginDTO> appLogin(HttpServletRequest request,
                                         @ApiParam(value = "版本号：v100", required = true) @PathVariable String version,
                                         @ApiParam(value = "app登录", required = true) @RequestBody LoginInModel inModel) {
        JsonResult<LoginDTO> jsonResult;
        try {
            switch (version) {
                case "v100":
                    jsonResult = authService.appLogin(inModel);
                    break;
                default:
                    jsonResult = JsonResult.paramError("无效的URL版本号!");
                    break;
            }
        } catch (Exception e) {
            jsonResult = JsonResult.exception("登录失败!");
            logger.error(e.getMessage(),e);
        }
        return jsonResult;
    }
}
