package com.smarthome.web.model.out.version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 版本更新DTO
 * @author wushenjun
 * 2015年4月28日
 */
@Data
@ApiModel
public class VersionUpdateDTO {
	@ApiModelProperty(value = "服务器最新版本号", required = true)
	private String appVersion;
	@ApiModelProperty(value = "SCM设备类型 6-SCMAndroid 7-SCMiOS 8-SCMTTAndroid 9-SCMTTiOS 10-工业版Android 11-工业版iOS", required = true)
	private int appType;
	@ApiModelProperty(value = "更新时间", required = true)
	private String updateTime;
	@ApiModelProperty(value = "是否强制更新(false：非强制更新  true：强制更新)", required = true)
	private boolean forceUpdate;
	@ApiModelProperty(value = "下载链接", required = true)
	private String url;
	@ApiModelProperty(value = "软件包下载链接", required = true)
	private String packageUrl;
	@ApiModelProperty(value = "更新说明", required = true)
	private String updateDec;
	@ApiModelProperty(value = "图片说明，有更新显示最新的图片介绍，没有则显示当前版本图片，返回三张即可", required = true)
	private List<Map<String, String>> updatePic;
	@ApiModelProperty(value = "提示语", required = true)
	private String message;
	@ApiModelProperty(value = "是否是最新版本(0不是 1是)", required = true)
	private int isLatestVersion;
}
