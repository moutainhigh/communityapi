package net.okdi.circle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.circle.service.ExpressCircleService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

@Controller
@RequestMapping("/expressCircle")
public class ExpressCircleController extends BaseController {

	private ExpressCircleService expressCircleService;
	/**
	 * 查询快递圈列表
	 * @param circleMember 用户的memberId
	 * @param circleLongitude 经度
	 * @param circleLatitude 维度
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCircleList", method = {RequestMethod.POST , RequestMethod.GET })
	public String queryCircleList(String memberId, String circleLongitude, String circleLatitude){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.circle.controller.ExpressCircleController.queryCircleList", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(circleLongitude)){
			return paramsFailure("net.okdi.circle.controller.ExpressCircleController.queryCircleList", "circleLongitude 不能为空!!!");
		}
		if(PubMethod.isEmpty(circleLatitude)){
			return paramsFailure("net.okdi.circle.controller.ExpressCircleController.circleLatitude", "circleMember 不能为空!!!");
		}
		String result = expressCircleService.queryCircleList(memberId, Double.valueOf(circleLongitude), Double.valueOf(circleLatitude));
		return result;
	}
}
