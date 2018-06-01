package com.smarthome.web.model.in.version;

import com.smarthome.web.model.in.BaseInModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wushenjun on 2017/7/14.
 */
@Data
@ApiModel
public class VersionCheckInModel extends BaseInModel {
    @ApiModelProperty(value = "app版本号", example="1.0.0", required = true)
    private String appVersion;
    @ApiModelProperty(value = "app类型(scm-app类型 6-SCMAndroid 7-SCMiOS 8-SCMTTAndroid 9-SCMTTiOS 10-工业版Android 11-工业版iOS)", example="6", required = true)
    private int appType;
}
