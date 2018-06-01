package com.smarthome.web.model.out.log;



import com.smarthome.commons.result.JsonResult;
import lombok.Data;

import java.util.Set;

@Data
public class UserOpLog {
	private String id;//日志表ID
	private String userId;//用户ID
	//private String loginName;//登录账号
	private String method;//方法名
	private String resultCode;//返回码
	private String resultMessage;//返回消息
	private Set<Object>  paramsSet;//参数Set
	private String params;//请求参数，用于保存到MySQL中
	private String result;//结果
	private String ip;//设备IP
	private String url;//请求路径
	//private Date ctime;//操作时间
	private long ctime;//创建时间
	private long costTime;//接口调用花费时间
	private String plateform;

	private JsonResult jsonResult; //返回结果，用于保存到MySQL中

}
