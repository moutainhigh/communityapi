package net.okdi.mob.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.okdi.mob.service.MobDeviceRecordService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Controller
@RequestMapping("/mobDeviceRecord")
public class DeviceRecordController {
	@Autowired
	private MobDeviceRecordService mobDeviceRecordService;
	Logger logger = Logger.getLogger(DeviceRecordController.class);
	public static SerializerFeature[] s = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullNumberAsZero,SerializerFeature.WriteNullStringAsEmpty};
	/**
	 * <dd>插入设备信息</dd>
	 * <dt>作者:</dt><dd>ccs</dd>
	 * <dt>时间:</dt><dd>2015-8-21 下午12:33:24</dd>
	 * @param memberId		所有者
	 * @param memberPhone	手机号
	 * @param channelNo		App类型（渠道号）01:接单王 02：个人端 03:好递生活 04:好递商铺06：好递配送员',
	 * @param deviceType	设备类型（“android”或者“ios”)
	 * @param deviceToken	每台设备唯一的token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/record", method = { RequestMethod.POST, RequestMethod.GET })
	public String record(Long memberId, String memberPhone, String channelNo, 
			String deviceType, String deviceToken ,HttpServletRequest request){
		
		
		
		/******原调用方法
		String regip= getRemortIP(request);
		return this.mobDeviceRecordService.record(memberId, memberPhone, channelNo,regip, deviceType, deviceToken);
	   */
		
		//****************不再调用后台，直接返回********************
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		
		allMap.put("data", 1);
		logger.info("=================调用publicapi/mobDeviceRecord/record  直接返回！！");
		return JSON.toJSONString(allMap,s).replaceAll(":null", ":\"\"");
	}

	
	public String getRemortIP(HttpServletRequest request) {
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		//System.out.println(ip);
		return ip;
	}
}
