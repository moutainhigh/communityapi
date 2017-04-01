/**  
 * @Project: mob
 * @Title: MsgGateway.java
 * @Package net.okdi.mob.controller
 * @Description: TODO
 * @author chuanshi.chai
 * @date 2014-12-4 下午2:51:15
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.core.base.BaseController;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.RegisterService;

/**
 * 接单王绑定推送接口(暂时不用)
 * @author chuanshi.chai
 * @date 2014-12-4
 * @since jdk1.6
*/
@Controller
@RequestMapping("/msgGateway")
public class MsgGatewayController extends BaseController{

	@Autowired
	RegisterService registerService;
	
	@Autowired
	ExpGatewayService expGatewayService;
	
	
	/**
	 * 绑定可以推送接口
	 * @param version	版本
	 * @param channelNo	01,02
	 * @param deviceToken	
	 * @param channelId	memberId
	 * @param deviceType android
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bindClient", method = { RequestMethod.POST })
	public String bindClient(String version,String channelNo,String deviceToken,String channelId,String deviceType){
		if(PubMethod.isEmpty(channelId)||PubMethod.isEmpty(channelNo)){
			return paramsFailure();
		}
		try {
			registerService.bindClient(version,channelNo,deviceToken, channelId, deviceType);
		} catch(Exception e){
			throw new ServiceException("插入设备号,地址信息,发生异常");	
		}
		return jsonSuccess(null);
	}
}