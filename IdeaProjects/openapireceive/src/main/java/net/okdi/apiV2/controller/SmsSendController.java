package net.okdi.apiV2.controller;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV1.service.ExpressUserService;
import net.okdi.apiV2.service.SmsSendService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping("/smsSend")
public class SmsSendController extends BaseController{

	@Autowired
	SmsSendService smsSendService;
	@Autowired
	ExpressUserService expressUserService;
	/*
	 * yangkai
	 * 2016-03-21
	 * 查询短信一天的发送次数
	 */
	@ResponseBody
	@RequestMapping(value = "/statisticalNumberOneDay", method = { RequestMethod.POST,RequestMethod.GET })
	public String memberSendNumQueryOneDay(Long memberId,Integer num){
		try {
			return JSON.toJSONString(smsSendService.memberSendNumQueryOneDay(memberId,num));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/*
	 * yangkai
	 * 2016-03-21
	 * 查询短信一小时的发送次数
	 */
	@ResponseBody
	@RequestMapping(value = "/statisticalNumberOneHour", method = { RequestMethod.POST,RequestMethod.GET })
	public String memberSendNumQueryOneHour(Long memberId,Integer num){
		try {
			return JSON.toJSONString(smsSendService.memberSendNumQueryOneHour(memberId,num));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/*
	 * yangkai
	 * 2016-03-21
	 * 查询用户是否已经认证过，没有认证的用户每天只能发10条短信
	 */
	@ResponseBody
	@RequestMapping(value = "/memberAuthentication", method = { RequestMethod.POST,RequestMethod.GET })
	public String memberAuthentication(Long memberId,Integer num){
		try {
			//先判断用户是否进行了身份认证
			Map map=this.expressUserService.queryRealNameAuditInfo(memberId+"");
			if(PubMethod.isEmpty(map)){
				Map maps=new HashMap();
				maps.put("success",false);
				maps.put("cause", "用户信息违法!");
				return JSON.toJSONString(maps);
			}else{
				if(PubMethod.isEmpty(map.get("auditStatus"))){
					Map maps=new HashMap();
					maps.put("success",false);
					maps.put("cause", "用户信息违法!");
					return JSON.toJSONString(maps);
				}else if("1".equals(map.get("auditStatus"))){
					return jsonSuccess();
				}else{
					Map maps=new HashMap();
					maps.put("success",false);
					maps.put("cause", "用户信息违法!");
					return JSON.toJSONString(maps);
				}
			}
//			return JSON.toJSONString(smsSendService.memberAuthentication(memberId,num));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/*
	 * yangkai
	 * 2016-03-21
	 * 查询短信一天的发送次数
	 */
	@ResponseBody
	@RequestMapping(value = "/identityNotPass", method = { RequestMethod.POST,RequestMethod.GET })
	public String identityNotPass(Long memberId,Integer num){
		try {
			return JSON.toJSONString(smsSendService.identityNotPass(memberId,num));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
}


