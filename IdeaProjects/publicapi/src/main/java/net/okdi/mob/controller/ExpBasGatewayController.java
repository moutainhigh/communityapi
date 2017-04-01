package net.okdi.mob.controller;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.VerifyUtil;
import net.okdi.mob.service.ExpBasGatewayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 接单王基础业务：获得所有公司，网络地址信息
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/expBasGateway")
public class ExpBasGatewayController extends BaseController{
	@Autowired
	ExpBasGatewayService expBasGatewayService;
	
	
	/**
	 * 获得所有公司
	 * @Method: getNets 
	 * @Description: TODO
	 * @return{"data":{"A":[{"code":"aae","firstLetter":"A","netId":1526,"netName":"AAE全球专递"}]},"success":true}
	 * @author xiangwei.liu
	 * @date 2014-11-4 下午1:46:44
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getNets", method = { RequestMethod.POST, RequestMethod.GET })
	public String getNets() {
 		return expBasGatewayService.getNets();
	}
	
	/**
	 * @Method: getNetArea 获得网络地址信息
	 * @Description: TODO
	 * @param Long compId 公司ID
	 * @return {"data":["怀柔区","朝阳区","平谷区","丰台区","密云县"],"success":true}
	 * @author xiangwei.liu
	 * @date 2014-11-5 下午6:07:15
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getNetArea", method = { RequestMethod.POST })
	public String getNetArea(Long compId) {
		if(PubMethod.isEmpty(compId))  
		   return paramsFailure();
		return expBasGatewayService.getNetArea(compId);
	}
}