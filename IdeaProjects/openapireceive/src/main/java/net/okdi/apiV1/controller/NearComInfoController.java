package net.okdi.apiV1.controller;

import net.okdi.apiV1.service.NearComInfoService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/queryNearInfo")
public class NearComInfoController extends BaseController{
	@Autowired
	private NearComInfoService nearComInfoService;
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description  查询附近代收点(好递生活)
	 * @data 2015-12-2 下午5:35:24
	 * @param longitude 
	 * @param latitude
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfo(Double longitude,Double latitude){
		
		try {
			return jsonSuccess(this.nearComInfoService.queryCompInfo( longitude, latitude));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
}
