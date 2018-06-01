package com.smarthome.web.controller.version;

import com.smarthome.commons.annotation.Permission;
import com.smarthome.commons.result.JsonResult;
import com.smarthome.service.version.VersionService;
import com.smarthome.web.model.in.version.VersionCheckInModel;
import com.smarthome.web.model.out.version.VersionUpdateDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("version")
@AllArgsConstructor
public class VersionController {
    private static final Logger logger = LoggerFactory.getLogger(VersionController.class);

    private final VersionService versionService;


    /**
     * app端检查版本更新
     * @param request
     * @param version
     * @param inModel
     * @return
     */
    @ApiOperation(value = "app端检查版本更新", tags="wushenjun", notes = "app端检查版本更新")
    @Permission(loginReqired=false)
    @RequestMapping(value = "checkVersionUpdate/{version}", method = RequestMethod.POST)
    public JsonResult<VersionUpdateDTO> checkVersionUpdate(HttpServletRequest request,
                                                           @ApiParam(value = "版本号：v100", required = true) @PathVariable String version,
                                                           @ApiParam(value = "检查版本更新信息", required = true) @RequestBody VersionCheckInModel inModel) {
        JsonResult<VersionUpdateDTO> jsonResult;
        try {
            switch (version) {
                case "v100":
                    jsonResult = versionService.checkVersionUpdate(inModel);
                    break;
                default:
                    jsonResult = JsonResult.paramError("无效的URL版本号!");
                    break;
            }
        } catch (Exception e) {
            jsonResult = JsonResult.exception("检查版本更新失败!");
            logger.error(e.getMessage(),e);
        }
        return jsonResult;
    }

}
