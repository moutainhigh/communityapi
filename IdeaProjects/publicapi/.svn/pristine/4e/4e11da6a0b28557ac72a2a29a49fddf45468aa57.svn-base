package net.okdi.circle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.circle.service.ExpressCircleService;
import net.okdi.core.base.BaseController;

/**
 * 快递圈
 * @author zj
 * @date 2016.4.9
 */
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
	public String queryCircleList(String circleMember, String circleLongitude, String circleLatitude){
		
		String result = expressCircleService.queryCircleList(circleMember, circleLongitude, circleLatitude);
		return result;
	}
}
