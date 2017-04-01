package net.okdi.api.controller;

import net.okdi.api.service.PromoService;
import net.okdi.api.service.StatisDSService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statisDS")
public class StatisDSController extends BaseController{
	@Autowired
	StatisDSService statisDSService;
	@Autowired
	PromoService promoService;

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>统计页面的访问次数</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>ccs</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-23 下午1:35:44</dd>
	 * @param shortkey	短链接的key
	 * @param time		时间戳
	 * @param shopId	店铺id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("/addVisitCount")
	public String addVisitCount(String shortkey,String time,String shopId){
		statisDSService.addDsVisitCount(shortkey, time, shopId);
		return "success";
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>统计下载次数</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>ccs</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-23 下午1:36:47</dd>
	 * @param shortkey		短链接的key
	 * @param productType	产品类型	 1.接单王 2.个人端 3.网站 4.站点 5其它
	 * @param userAgent		用户浏览器类型
	 * @param appType		下载的app类型 1:android 2:iphone 3:PC',
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("/addDownCount")
	public String addDownCount(String shortkey,String productType,String userAgent,String appType){
		statisDSService.addDsDownCount(shortkey, productType, userAgent, appType);
		return "success";
	}
}
