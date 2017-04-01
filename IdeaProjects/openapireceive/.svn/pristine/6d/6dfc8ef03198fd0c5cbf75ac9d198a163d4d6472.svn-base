package net.okdi.api.controller;


import java.util.Map;

import net.okdi.api.service.PromoService;
import net.okdi.api.service.VisitShortUrlLogService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 做压力测试用
 * 
 * @author  ccs
 * @version  [版本号, 2014-10-10 下午04:22:50 ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/statistics")
public class ShortUrlController extends BaseController {
	
	@Autowired
	VisitShortUrlLogService visitShortUrlLogService;
	@Autowired
	PromoService promoService;
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-19 下午6:13:27</dd>
	 * @param shortUrl		短链接的key
	 * @param sendLon		经度
	 * @param sendLat		维度
	 * @param sendMob		发送者手机号
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("/addVisitCount")
	public String addVisitCount(String shortUrl,String sendLon,String sendLat,String sendMob){
		visitShortUrlLogService.addVisitCount(shortUrl,sendLon,sendLat,sendMob);
		return "success";
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-19 下午6:13:54</dd>
	 * @param shortUrl	短链接的key
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("addDownCount")
	public String addDownCount(String shortUrl){
		visitShortUrlLogService.addDownCount(shortUrl);
		return "success";
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查找收派员的数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-4-15 下午1:25:31</dd>
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("findExpCount")
	public String findExpCount(){
		return jsonSuccess(Long.valueOf("156700")+visitShortUrlLogService.findExpCount());
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查找发送短信的数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-4-15 下午1:26:02</dd>
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("findSmsCount")
	public String findSmsCount(){
		return jsonSuccess(Long.valueOf("5986700")+visitShortUrlLogService.findSmsCount());
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询个人今天使用微信发送的短信数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-3 下午2:57:21</dd>
	 * @param memberId	查询人的memberId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("findTodayWxSmsCount")
	public String findTodayWxSmsCount(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.ShortUrlController.findTodayWxSmsCount.001", "用户memberId不能为空！");
		}
		return jsonSuccess(visitShortUrlLogService.findTodayWxSmsCount(memberId));
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>发送email</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-23 下午3:57:00</dd>
	 * @param memberId	签到的memberId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("insertWxSignLog")
	public String insertWxSignLog(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.ShortUrlController.insertWxSignLog.001", "用户memberId不能为空！");
		}
		Map<String, Object> signMap = promoService.insertWxSignLog(memberId);
		return jsonSuccess(signMap);
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>发送email</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-23 下午3:57:00</dd>
	 * @param memberId	签到的memberId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("queryTodaySign")
	public String queryTodaySign(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.ShortUrlController.queryTodaySign.001", "用户memberId不能为空！");
		}
		Boolean b = promoService.queryTodaySign(memberId);
		return jsonSuccess(b);
	}
	
	@ResponseBody
	@RequestMapping("insertWxShareLog")
	public String insertWxShareLog(Long memberId,Long sharedMemberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.ShortUrlController.queryTodaySign.001", "用户memberId不能为空！");
		}
		promoService.insertWxShareLog(memberId,sharedMemberId);
		return jsonSuccess();
	}
}
