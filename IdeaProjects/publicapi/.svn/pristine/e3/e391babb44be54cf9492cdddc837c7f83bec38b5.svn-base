package net.okdi.apiV2.controller;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/marketManage")
public class TelephoneRewardController extends BaseController {

	
	//注册弹窗
	@Value("${reg_bounced_url}")
	private String regBouncedUrl;
	//登录弹窗
	@Value("${login_bounced_url}")
	private String loginBouncedUrl;
	//实名认证弹窗
	@Value("${relNameCert_url}")
	private String relNameCertUrl;
	
	@Autowired
	ConstPool constPool;
	
	@Autowired
	private RedisService redisService;
	@Autowired
	private TelephoneRewardService telephoneRewardService;
	
	/**
	 * @author 郑炯
	 * @api {post} /marketManage/loginReceiveRewardNum 登录领奖励
	 * @apiVersion 0.2.0
	 * @apiDescription 登录领奖励 -郑炯
	 * @apiGroup 营销管理
	 * @apiparam {String} phone 登录人电话
	 * @apiSampleRequest /marketManage/loginReceiveRewardNum
	 * @apiSuccess data:001 代表成功, 002:代表失败,333:代表该用户没有进行实名认证(进行弹窗提示{文案找产品要})
	 * @apiSuccessExample Success-Response:
		{
		    "data": "001"
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/loginReceiveRewardNum", method = { RequestMethod.POST, RequestMethod.GET })
	public String loginReceiveRewardNum(String phone){
		
		String result = telephoneRewardService.loginReceiveRewardNum(phone);
		return result;
	}
	
	/**
	 * @author 郑炯
	 * @api {post} /marketManage/getSMSOrPhoneNum 查询群发通知条数
	 * @apiVersion 0.2.0
	 * @apiDescription 通知奖励 -郑炯
	 * @apiGroup 营销管理
	 * @apiParam {String} accountId 钱包accountId
	 * @apiparam {String} phone 登录人电话
	 * @apiSampl
	 * eRequest /marketManage/getSMSOrPhoneNum
	 * @apiSuccess {String} lenPhone 电话通知条数(如果返回值不是no则弹窗显示提示并且把返回值数量直接显示;否则弹窗不显示提示)
	 * @apiSuccess {String} lenSms 短信通知条数(如果返回值不是no则弹窗显示提示并且把返回值数量直接显示;否则弹窗不显示提示)
	 * @apiSuccess {String} preferentialNum 优惠数量(如果返回值不是no则弹窗显示提示并且把返回值数量直接显示;否则弹窗不显示提示)
	 * @apiSuccess {Double} telecomBalance 可用余额
	 * @apiSuccess {Integer} one 第一条短信可输入字数
	 * @apiSuccess {Integer} two 第二条短信可输入字数
	 * @apiSuccess {Integer} three 第三条短信可输入字数
	 * @apiSuccess {Integer} contentNum 总的可发送条数
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "lenPhone": 0,
		        "lenSms": 0,
		        "preferentialNum": 100,
		        "one":30,
		        "two":80,
		        "three":150,
		        "contentNum":3
		    },
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/getSMSOrPhoneNum", method = { RequestMethod.POST, RequestMethod.GET })
	public String getSMSOrPhoneNum(String accountId, String phone){
		
		String result = telephoneRewardService.getSMSOrPhoneNum(accountId, phone);
		return result;
	}
	
	/**
	 * @author 郑炯
	 * @api {post} /marketManage/getLoginOrRegisterReward 注册登录弹框提示
	 * @apiVersion 0.2.0
	 * @apiDescription 注册登录弹框提示 -郑炯
	 * @apiGroup 营销管理
	 * @apiparam {String} phone 登录人电话phone
	 * @apiParam {String} memberId 用户的memberId
	 * @apiSampl
	 * eRequest /marketManage/getLoginOrRegisterReward
	 * @apiSuccess {String} rewBool 通知领奖励(登录) 字符串 true 和 false
	 * @apiSuccess {String} rewRewardUrl 通知领奖励(登录) 弹框的url
	 * @apiSuccess {String} regBool 注册领奖励(注册) 字符串 true 和 false
	 * @apiSuccess {String} regRewardUrl 注册领奖励(注册) 弹框的url
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "rewBool": "true",
		        "rewRewardUrl": "http://life.okdit.net/okdilife/zwk/invlife-ad.jsp"
		        "rewBool": "true",
		        "rewRewardUrl": "http://life.okdit.net/okdilife/zwk/invlife-ad.jsp"
		    },
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/getLoginOrRegisterReward", method = { RequestMethod.POST, RequestMethod.GET })
	public String getLoginOrRegisterReward(String memberId, String phone){

		//根据用户手机号查询当天的这个登陆领奖励的是否已领取过
		//根据memberId 查询用户的手机号
		//String mobile = telephoneRewardService.queryPhoneByMemberId(memberId);
		Map<String,String> rewRewardMap = telephoneRewardService.queryIsGetReward(phone);
		
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "0";//telephoneRewardService.queryAuditItemByPhone(phone);//先注掉,为了进去一次弹一次这个微信优先的弹框
		//boolean byKey = redisService.getByKey("queryAuditItemByPhoneByZj", phone);
		System.out.println("查询到的openapi实名认证缓存....为result: "+result+" ==实名弹窗链接::: "+ constPool.getRelNameCertUrl());
		if("0".equals(result)){//未实名认证
			/**********注掉日期2017年3月6日 19:31:24,不让弹了**********/
			/*map.put("rewRewardUrl", constPool.getRelNameCertUrl());
			map.put("rewBool", "true");*/
			map.put("rewRewardUrl", "");
			map.put("rewBool", "false");
			System.out.println("publicapi 中getLoginOrRegisterReward方法返回实名认证没通过的链接{map}: "+map);
		}else{//已实名认证
			map.put("rewRewardUrl", rewRewardMap.get("rewRewardUrl"));
			map.put("rewBool", rewRewardMap.get("rewBool"));
		}
		//注册领奖励
		/*******主调日期2017年3月6日 19:32:49,不让弹了*******/
		/*Map<String,String> regRewardMap = telephoneRewardService.queryIsGetRegister(memberId);
		map.put("regBool", regRewardMap.get("regBool"));
		map.put("regRewardUrl", regRewardMap.get("regRewardUrl"));*/
		map.put("regBool", "false");
		map.put("regRewardUrl", "");
		telephoneRewardService.updateRegisterStatus(memberId);
		//更新已经领用过的状态 登录 和注册
		telephoneRewardService.updateLoginStatus(phone);
		
		return jsonSuccess(map);
	}
	
	/**
	 * @author 郑炯
	 * @api {post} /marketManage/getRegisterReward 注册弹框提示(接口目前不用)
	 * @apiVersion 0.2.0
	 * @apiDescription 新用户注册奖励(接口目前不用) -郑炯
	 * @apiGroup 营销管理
	 * @apiParam {String} memberId 用户的memberId
	 * @apiSampl
	 * eRequest /marketManage/getRegisterReward
	 * @apiSuccess {String} regBool 注册领奖励(注册) 字符串 true 和 false
	 * @apiSuccess {String} regRewardUrl 注册领奖励(注册) 弹框的url
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "rewBool": "true",
		        "rewRewardUrl": "http://life.okdit.net/okdilife/zwk/invlife-ad.jsp"
		    },
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	/*@ResponseBody
	@RequestMapping(value = "/getRegisterReward", method = { RequestMethod.POST, RequestMethod.GET })
	public String getRegisterReward(String memberId){
		
		Map<String,Object> map = new HashMap<String,Object>();
		//注册领奖励
		Map<String,String> regRewardMap = telephoneRewardService.queryIsGetRegister(memberId);
		map.put("regBool", regRewardMap.get("regBool"));
		map.put("regRewardUrl", regRewardMap.get("regRewardUrl"));
		telephoneRewardService.updateRegisterStatus(memberId);
		return jsonSuccess(map);
	}
	*/
	
	/**
	 * @author 郑炯
	 * @api {post} /marketManage/isReceiveLoginReward 查询当天是否领取过免费奖励数量
	 * @apiVersion 0.2.0
	 * @apiDescription 查询当天是否领取过免费奖励数量 -郑炯
	 * @apiGroup 营销管理
	 * @apiParam {String} phone 用户的 phone(当前登录手机号)
	 * @apiSampleRequest /marketManage/isReceiveLoginReward
	 * @apiSuccess date 001:代表已经领取过, 002:代表还没有领取过, 333:代表该用户没有进行实名认证(getLoginOrRegisterReward 注册登录奖励的弹窗也不用掉用)
	 * @apiSuccessExample Success-Response:
		{
		    "data": 001,
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/isReceiveLoginReward", method = { RequestMethod.POST, RequestMethod.GET })
	public String isReceiveLoginReward(String phone){
		
		String result = telephoneRewardService.isReceiveLoginReward(phone);
		return result;
		
	}
	
}
