package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.NumberQueryService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("numberQueryController")
@RequestMapping("numberQuery")
public class NumberQueryController extends BaseController{
	
	@Autowired
	private NumberQueryService numberQueryService;
	/**
	 * @author yangkai
	 * @api {post} /numberQuery/homepage 查询首页数目
	 * @apiVersion 0.2.0
	 * @apiDescription 查询首页数目
	 * @apiGroup 数目查询
	 * @apiParam {Long} memberId 用户ID
	 * @apiSampleRequest /numberQuery/homepage
	 * @apiSuccess {String} name   网点名称
	 * @apiSuccess {String} url   网点 url 
	 * @apiSuccess {Long} smallBell   铃铛数量
	 * @apiSuccess {Long} available_balance: 通讯费余额
	 * @apiSuccess {Long} extra: 外快余额
	 * @apiSuccess {Long} wrong  通知异常--发送失败
	 * @apiSuccess {Long} sendWrong   派件异常数量
	 * @apiSuccess {Long} noticeRecordNum   通知记录数量
	 * @apiSuccess {Long} notice   最新的公告时间   没有公告返回0
	 * @apiSuccess {Long} lanShou   揽收包裹数量
	 * @apiSuccess {Long} delivery   投递包裹数量
	 * @apiSuccessExample Success-Response:
	   {"data":{
	   	"smallBell":0,
	   	"wrong":1,
	   	"available_balance":1,
	   	"extra":1,
	   	"notice":1,
	   	"noticeRecordNum":1,
	   	"name": "百世汇通",
	   	"url": "http://www.800bestex.com/",
	   	"sendWrong":1,
	   	"lanShou":1,
	   	"delivery":1
	   	},
	   	"success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"net.okdi.apiV4.controller.NumberQueryController.homepage.memberId",
	 *    "message":"用户id不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("homepage")
	public String homepage(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.NumberQueryController.homepage.memberId","用户id不能为空");
		}
		try {
			return jsonSuccess(this.numberQueryService.homepage(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zj
	 * @api {post} /numberQuery/newHomepage 新版查询首页数目
	 * @apiVersion 5.0.0
	 * @apiDescription 查询首页数目
	 * @apiGroup 新版-短信(目前没用到,等着用)
	 * @apiParam {Long} memberId 用户ID
	 * @apiSampleRequest /numberQuery/homepage
	 * @apiSuccess {String} name   网点名称
	 * @apiSuccess {String} url   网点 url 
	 * @apiSuccess {Long} smallBell   铃铛数量
	 * @apiSuccess {Long} available_balance: 通讯费余额
	 * @apiSuccess {Long} extra: 外快余额
	 * @apiSuccess {Long} wrong  通知异常--发送失败
	 * @apiSuccess {Long} sendWrong   派件异常数量
	 * @apiSuccess {Long} noticeRecordNum   通知记录数量
	 * @apiSuccess {Long} notice   最新的公告时间   没有公告返回0
	 * @apiSuccess {Long} lanShou   揽收包裹数量
	 * @apiSuccess {Long} delivery   投递包裹数量
	 * @apiSuccessExample Success-Response:
	   {"data":{
	   	"smallBell":0,
	   	"wrong":1,
	   	"available_balance":1,
	   	"extra":1,
	   	"notice":1,
	   	"noticeRecordNum":1,
	   	"name": "百世汇通",
	   	"url": "http://www.800bestex.com/",
	   	"sendWrong":1,
	   	"lanShou":1,
	   	"delivery":1
	   	},
	   	"success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"net.okdi.apiV4.controller.NumberQueryController.homepage.memberId",
	 *    "message":"用户id不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/newHomepage", method={RequestMethod.POST, RequestMethod.GET})
	public String newHomepage(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.NumberQueryController.homepage.memberId","用户id不能为空");
		}
		try {
			return jsonSuccess(this.numberQueryService.newHomepage(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author yangkai
	 * @api {post} /numberQuery/noticeRecordSuccessNum 查询通知记录成功数目
	 * @apiVersion 0.2.0
	 * @apiDescription 查询通知记录成功数目
	 * @apiGroup 数目查询
	 * @apiParam {Long} memberId 用户ID
	 * @apiSampleRequest /numberQuery/noticeRecordSuccessNum
	 * @apiSuccess {Long} smsNum   短信成功数
	 * @apiSuccess {Long} phoneNum  电话成功数
	 * @apiSuccess {Long} chatNum  微信模板推送成功数
	 * @apiSuccessExample Success-Response:
	   {"data":{
	   	"smsNum":0,
	   	"phoneNum":0,},
	   	"success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"net.okdi.apiV4.controller.NumberQueryController.homepage.memberId",
	 *    "message":"用户id不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping("noticeRecordSuccessNum")
	public String noticeRecordSuccessNum(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.NumberQueryController.homepage.memberId","用户id不能为空");
		}
		try {
			return jsonSuccess(this.numberQueryService.noticeRecordSuccessNum(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
