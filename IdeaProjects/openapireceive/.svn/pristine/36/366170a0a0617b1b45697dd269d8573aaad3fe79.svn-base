package net.okdi.api.controller;

import java.util.Date;

import net.okdi.api.service.SmsInterceptionLogService;
import net.okdi.core.base.BaseController;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/smsInterceptionLog")
public class SmsInterceptionLogController extends BaseController {
	
	@Autowired
	SmsInterceptionLogService smsInterceptionLogService;
	
	/**
     * 	查询短信拦截列表 
     * @author chunyang.tan
     * @param sendPhone	发送人号码
     * @param createTime	创建时间
     * @param sendContent	发送内容
     * @param pageNo	当前页
     * @param pageSize	一页多少条
     * @return
     * 		result:{"data":{"page":{"currentPage":0,"hasFirst":true,"hasLast":false,"hasNext":false,"hasPre":true,"items":[],"offset":-10,"pageCount":0,"pageSize":10,"rows":[],"total":0}},"success":true}
     */
	@ResponseBody
	@RequestMapping("/querySmsInterceptionLog")
	public String querySmsInterceptionLog(String sendPhone,String startTime, String endTime,String sendContent, Integer pageNo, Integer pageSize)
	{
		
		return jsonSuccess2(smsInterceptionLogService.querySmsInterceptionLog(sendPhone, startTime, endTime, sendContent,pageNo, pageSize));
	}

	/**
	 * 	账户查询
	 * @author chunyang.tan
	 * @param memberId	发送人id
	 * @param sendPhone	发送人电话
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/accountInquiry")
	public String accountInquiry(String memberId)
	{
		if(PubMethod.isEmpty(memberId))
		{
			throw new ServiceException("openapi.SmsInterceptionLogController.querySmsInterceptionLog.001","queryStatus参数异常");
		}
		return jsonSuccess(smsInterceptionLogService.queryAccountInquiry(memberId));
	}
	/**
	 * 
	 * @Method 查询电话号码的短信拦截次数与状态
	 * @author AiJun.Han
	 * @Description
	 * @data 2016-1-23 上午11:42:38
	 * @param sendPhone 电话号码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryInformation")
	public String queryIfHas(String sendPhone){
		if(PubMethod.isEmpty(sendPhone))
		{
			throw new ServiceException("openapi.SmsInterceptionLogController.queryIfHas.001","sendPhone参数异常");
		}
		return jsonSuccess(smsInterceptionLogService.queryIfHas(sendPhone));
	}
	/**
	 * 
	 * @Method 添加禁用号码
	 * @author AiJun.Han
	 * @Description
	 * @data 2016-1-23 上午11:45:48
	 * @param sendPhone
	 * @param disableType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertInterception")
	public String insertInterception(String sendPhone,String disableType){
		if(PubMethod.isEmpty(sendPhone)){
			throw new ServiceException("openapi.SmsInterceptionLogController.insertInterception.001","sendPhone参数异常");
		}
		if(PubMethod.isEmpty(disableType)){
			throw new ServiceException("openapi.SmsInterceptionLogController.insertInterception.001","disableType参数异常");
		}
		return jsonSuccess(smsInterceptionLogService.insertTelPhone(sendPhone, disableType));
	}
	@ResponseBody
	@RequestMapping("getMemberId")
	public String getMemberId(String sendPhone){
		if(PubMethod.isEmpty(sendPhone)){
			throw new ServiceException("openapi.SmsInterceptionLogController.getMemberId.001","sendPhone参数异常");
		}
		return jsonSuccess(smsInterceptionLogService.getMemberId(sendPhone));
	}
}
