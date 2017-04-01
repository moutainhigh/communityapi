package net.okdi.mob.controller;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.base.BaseController;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("wallet")
public class WalletController extends BaseController{
	@Autowired
	private WalletService walletService;
	String validTimes = "false";  //是否验证每天发送次数(必填) true验证 false不验证
	
	@ResponseBody
	@RequestMapping("setWalletPassword")
	public String setWalletPassword(String memberId,String password){
		String result="";
		try {
			result = walletService.setWalletPassword(memberId, password);
			Map a = JSON.parseObject(result);
			return jsonSuccess(a);
	} catch (Exception e) {
		    result = jsonFailure(e);
	}
		return result;
	}
	

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>检查账户号是否存在</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-21 下午4:52:13</dd>
	 * @param memberId  登录人id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/accountCardExist", method = { RequestMethod.POST , RequestMethod.GET})
	public String accountCardExist(Long memberId){
		String result = "";
		try {
			result = walletService.accountCardExist(String.valueOf(memberId));
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(map != null && map.get("RESULT") != null && !"".equals(map.get("RESULT").toString())) {
				if("success".equals(map.get("RESULT"))){
					resultMap.put("isExist", 1); //账号存在
				} else if("error".equals(map.get("RESULT"))) {
					resultMap.put("isExist", 0); //不存在
				}
			} else {
				throw new ServiceException("publicapi.WalletController.accountCardExist.001", "电商接口调用异常");
			}
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>创建账户和账本</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-21 下午5:47:18</dd>
	 * @param nick 账户名
	 * @param userPhone 开户电话
	 * @param catId 账户类型 100001:个人、100002:收派员、100003:店铺、100004:电商、100005:好递运营部、100006:好递系统
	 * @param memberId 外部系统ID
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/createAccount", method = { RequestMethod.POST , RequestMethod.GET})
	public String createAccount(String nick, String userPhone, String catId, Long memberId){
		String result = "";
		try {
			if(PubMethod.isEmpty(nick)) {
				nick = "okdi_" + userPhone;
			}
			result = walletService.createAccount(nick, userPhone, catId, memberId, memberId);
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(map != null && map.get("DATA") != null && !"".equals(map.get("DATA").toString())) {
				Map<String, Object> dataMap = JSON.parseObject(map.get("DATA").toString(), Map.class);
				if(dataMap != null && dataMap.get("result") != null && !"".equals(dataMap.get("result").toString())) {
					if("true".equals(dataMap.get("result").toString())) {
						resultMap.put("success", true);
						return JSON.toJSONString(resultMap);
					} else if("false".equals(dataMap.get("result").toString())) {
						throw new ServiceException("publicapi.WalletController.createAccount.001", "开通钱包失败");
					} else {
						throw new ServiceException("publicapi.WalletController.createAccount.002", "电商接口调用异常");
					}
				} else {
					throw new ServiceException("publicapi.WalletController.createAccount.002", "电商接口调用异常");
				}
			} else {
				throw new ServiceException("publicapi.WalletController.createAccount.002", "电商接口调用异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>按账户名和日期返回交易明细</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-22 上午11:08:41</dd>
	 * @param accountCard 账户名
	 * @param created 交易日期 (yyyy-MM-dd)
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cashTradeDetail", method = { RequestMethod.POST , RequestMethod.GET})
	public String cashTradeDetail(Long memberId, String created){
		String result = "";
		try {
			result = walletService.cashTradeDetail(String.valueOf(memberId), created);
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(map != null && map.get("RESULT") != null && !"".equals(map.get("RESULT").toString())) {
				if("success".equals(map.get("RESULT").toString())) {
					if(map.get("DATA") != null && !"".equals(map.get("DATA").toString())) {
						Map<String, Object> dataMap = JSON.parseObject(map.get("DATA").toString(), Map.class);
						return jsonSuccess(dataMap);
					} else {
						return jsonSuccess(resultMap);
					}
				} else if("error".equals(map.get("RESULT").toString())) {
					throw new ServiceException("publicapi.WalletController.cashTradeDetail.001", "账户号不能为空");
				} else {
					throw new ServiceException("publicapi.WalletController.cashTradeDetail.002", "电商接口调用异常");
				}
			} else {
				throw new ServiceException("publicapi.WalletController.cashTradeDetail.002", "电商接口调用异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询余额及提现成功次数及总次数</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-22 上午11:19:15</dd>
	 * @param memberId
	 * @param catId 账户类型 100001:个人、100002:收派员、100003:店铺、100004:电商、100005:好递运营部、100006:好递系统
	 * @param startTime yyyy-MM-dd hh:mm:ss 提现成功次数,开始时间,为空默认为当前凌晨
	 * @param endTime yyyy-MM-dd hh:mm:ss 提现成功次数,结束时间,为空默认为当前时间
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getBalance", method = { RequestMethod.POST , RequestMethod.GET})
	public String getBalance(Long memberId, String catId, String startTime, String endTime){
		String result = "";
		try {
			result = walletService.getBalance(memberId, memberId, catId, startTime, endTime);
			Map<String, Object> map = JSON.parseObject(result, Map.class);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(map != null && map.get("RESULT") != null && !"".equals(map.get("RESULT").toString())) {
				if("success".equals(map.get("RESULT").toString())) {
					if(map.get("DATA") != null && !"".equals(map.get("DATA").toString())) {
						Map<String, Object> dataMap = JSON.parseObject(map.get("DATA").toString(), Map.class);
						return jsonSuccess(dataMap);
					} else {
						return jsonSuccess(resultMap);
					}
				} else if("error".equals(map.get("RESULT").toString())) {
					throw new ServiceException("publicapi.WalletController.getBalance.001", "用户账户不存在");
				} else {
					throw new ServiceException("publicapi.WalletController.getBalance.002", "电商接口调用异常");
				}
			} else {
				throw new ServiceException("publicapi.WalletController.getBalance.002", "电商接口调用异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>验证旧密码是否正确</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-22 下午2:01:35</dd>
	 * @param accountCard 账户名
	 * @param memberId    
	 * @param password    密码
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("validatePassword")
	public String validatePassword(Long memberId,String password){
		String result = "";
		try {
			result = walletService.validatePassword(memberId+"", memberId, password);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>开通钱包发送短信验证码</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 上午11:37:21</dd>
	 * @param mobile 手机号(必填)
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("sendVerifyCodeCreateAccount")
	public String sendVerifyCodeCreateAccount(String mobile){
		String result = "";
		try {
			Long busineType = 8l;
			result = walletService.sendVerifyCode(mobile, busineType, validTimes);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>找回支付密码发送短信验证码</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 上午11:37:21</dd>
	 * @param mobile 手机号(必填)
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("sendVerifyCodeFindPassword")
	public String sendVerifyCodeFindPassword(String mobile){
		String result = "";
		try {
			Long busineType = 7l;
			result = walletService.sendVerifyCode(mobile, busineType, validTimes);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>开通钱包验证验证码</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 上午11:37:21</dd>
	 * @param mobile 手机号(必填)
	 * @param verifyCode 验证码
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("validVerifyCodeCreateAccount")
	public String validVerifyCodeCreateAccount(String mobile, String verifyCode){
		String result = "";
		try {
			Long busineType = 8l;
			result = walletService.validVerifyCode(mobile, busineType, verifyCode);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>找回支付密码验证验证码</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 上午11:37:21</dd>
	 * @param mobile 手机号(必填)
	 * @param verifyCode 验证码
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("validVerifyCodeFindPassword")
	public String validVerifyCodeFindPassword(String mobile, String verifyCode){
		String result = "";
		try {
			Long busineType = 7l;
			result = walletService.validVerifyCode(mobile, busineType, verifyCode);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断钱包账号是否设置的钱包密码</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 上午11:37:21</dd>
	 * @param mobile 手机号(必填)
	 * @param verifyCode 验证码
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping("checkAccountPwdIsExist")
	public String checkAccountPwdIsExist(Long memberId){
		String result = "";
		try {
			result = walletService.checkAccountPwdIsExist(memberId);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	/**
	 * 
	 * @api {post} http://192.168.53.32:8080/okdilifeapi/wallet/getBankInfoList
	 * @apiVersion 0.2.0
	 * @apiDescription 查询银行卡列表 
	 * @apiGroup wallet
	 *
	 * @apiParam {String} cardType 银行卡类型 credit:信用卡 deposit:储蓄卡
	 * @apiSampleRequest http://192.168.53.32:8080/okdilifeapi/wallet/getBankInfoList
	 * 
	 * @apiSuccess {String} result  json字符串
	 *
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     LIST1:储蓄卡list    LIST2:信用卡list
	 *    {
	 *   "RESULT": "success",
     *		"DATA": {
     *   		"LIST1": [],
     *  	"LIST2": [
     *       {
     *           "bankName": "广招银行",
     *           "cardType": "credit",
     *           "creditCode": "CMB_C",
     *           "depositCode": null,
     *           "id": 120,
     *           "picUrl": "http://static.okdi.net/bank/icon/120.png"
     *       }
     *   ]
     * 		}
	 *	}
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
     * 	"RESULT": "error",
     *	 "DATA": {
     *   "MSG":"登陆会话过期，请重新登陆重新登陆<\/a>"
     * 	}
	 *	}
	 */
	@ResponseBody
	@RequestMapping(value = "/getBankInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	public String getBankInfoList(String cardType){
		if(PubMethod.isEmpty(cardType)){
			return paramsFailure("okdilifeapi.WalletController.getBankInfoList.001","账户类型不能为空 ");
		}
		try {
			return this.walletService.getBankInfoList(cardType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	//提现业务 - 查询提现额度
	@ResponseBody
	@RequestMapping(value = "/getWithdrawLimit", method = { RequestMethod.POST, RequestMethod.GET })
	public String getWithdrawLimit(String accountCard,String memberId,String catId,String startTime,String endTime){
		if(PubMethod.isEmpty(accountCard)){
			return paramsFailure("okdilifeapi.WalletController.getWithdrawLimit.001","账户类型不能为空 ");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("okdilifeapi.WalletController.getWithdrawLimit.002","memberId不能为空 ");
		}
		if(PubMethod.isEmpty(catId)){
			return paramsFailure("okdilifeapi.WalletController.getWithdrawLimit.003","账户类型不能为空 ");
		}
		if(PubMethod.isEmpty(startTime)){
			return paramsFailure("okdilifeapi.WalletController.getWithdrawLimit.004","开始时间不能为空 ");
		}
		if(PubMethod.isEmpty(endTime)){
			return paramsFailure("okdilifeapi.WalletController.getWithdrawLimit.005","结束时间不能为空 ");
		}
		try {
			return this.walletService.getWithdrawLimit(accountCard,memberId,catId,startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /wallet/getOKdiLifeCashTradeByType 按账户号和交易类型查询好递生活账单
	 * @apiPermission user
	 * @apiDescription 按账户号和交易类型查询好递生活账单 kai.yang
	 * @apiparam {String} accountCard 账户号
	 * @apiparam {int} currentPage 当前页
	 * @apiparam {int} type 交易类型
	 * @apiGroup ACCOUNT 交易业务
	 * @apiSampleRequest /wallet/getOKdiLifeCashTradeByType
	 * @apiSuccess {String} tid 交易流水号
	 * @apiSuccess {String} title 标题
	 * @apiSuccess {String} type 交易类型
	 * @apiSuccess {String} amount 发生金额
	 * @apiSuccess {String} date 列表所有日期
	 * @apiSuccess {String} paytime 账单详情所用日期
	 * @apiSuccess {String} status 交易状态 1：交易成功 2：处理中 3：交易失败
	 * @apiSuccess {String} statusName 交易状态名称
	 * @apiSuccess {String} img 图标
	 * @apiSuccess {String} tradeNum 订单号
	 * @apiSuccess {String} bankName 银行名称
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * {"data":[{
	 * 			"tid":"161154732793866","title":"赚外快-邀请","type":"6","amount":"+20.00","date":"08-03 18:38",
	 * 			"paytime":"2015-08-03 18:38:25.0","status":"1","statusName":"交易成功","img":"http://static.okdi.net/bill/qianxiang2x.png",
	 * 			"tradeNum":"","bankName":"好递"},
	 * 			{"tid":"161154242060298","title":"赚外快-邀请","type":"6","amount":"+20.00","date":"08-03 18:34",
	 * 			"paytime":"2015-08-03 18:34:31.0","status":"1","statusName":"交易成功","img":"http://static.okdi.net/bill/qianxiang2x.png",
	 * 			"tradeNum":"","bankName":"好递"},
	 * 			{"tid":"158260526718986","title":"赚外快-邀请","type":"6","amount":"+20.00","date":"07-18 19:17",
	 * 			"paytime":"2015-07-18 19:17:20.0","status":"1","statusName":"交易成功","img":"http://static.okdi.net/bill/qianxiang2x.png",
	 * 			"tradeNum":"","bankName":"好递"}],
	 * "success":true}
	 * 
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"message":"钱包账户不存在","success":false,"errcode":"004"}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getOKdiLifeCashTradeByType", method = { RequestMethod.POST, RequestMethod.GET })
	public String getOKdiLifeCashTradeByType(String  accountCard, int currentPage,int type){
		if(PubMethod.isEmpty(accountCard)){
			return paramsFailure("okdilifeapi.WalletController.getOKdiLifeCashTradeByType.001","账户号不能为空 ");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("okdilifeapi.WalletController.getOKdiLifeCashTradeByType.002","当前页不能为空 ");
		}
		if(PubMethod.isEmpty(type)){
			return paramsFailure("okdilifeapi.WalletController.getOKdiLifeCashTradeByType.003","交易类型不能为空 ");
		}
		try {
			return walletService.getOKdiLifeCashTradeByType(accountCard, currentPage,type);		
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	//TODO 2
	/**
	 * @api {post} /wallet/getOKdiLifeCashTrade 按账户号查询好递生活账单
	 * @apiPermission user
	 * @apiDescription 按账户号查询好递生活账单 kai.yang
	 * @apiparam {String} accountCard 账户号
	 * @apiparam {int} currentPage 当前页
	 * @apiGroup ACCOUNT 交易业务
	 * @apiSampleRequest /wallet/getOKdiLifeCashTrade
	 * @apiSuccess {String} month 月份
	 * @apiSuccess {String} outcome 月支出
	 * @apiSuccess {String} intcome 月收入
	 * @apiSuccess {String} tradeList 交易详情列表
	 * @apiSuccess {String} tid 交易流水号
	 * @apiSuccess {String} title 标题
	 * @apiSuccess {String} type 交易类型
	 * @apiSuccess {String} amount 发生金额
	 * @apiSuccess {String} date 列表所有日期
	 * @apiSuccess {String} paytime 账单详情所用日期
	 * @apiSuccess {String} status 交易状态 1：交易成功 2：处理中 3：交易失败
	 * @apiSuccess {String} statusName 交易状态名称
	 * @apiSuccess {String} img 图标
	 * @apiSuccess {String} tradeNum 订单号
	 * @apiSuccess {String} bankName 银行名称
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * {"data":[{
	 * 			"month":"本月","outcome":"","intcome":"40.00",
	 * 			"tradeList":[{
	 * 						"tid":"161154732793866","title":"赚外快-邀请","type":"6","amount":"+20.00",
	 * 						"date":"08-03 18:38","paytime":"2015-08-03 18:38:25.0","status":"1","statusName":"交易成功",
	 * 						"img":"http://static.okdi.net/bill/qianxiang2x.png","tradeNum":"","bankName":"好递"},
	 * 						{"tid":"161154242060298","title":"赚外快-邀请","type":"6","amount":"+20.00","date":"08-03 18:34",
	 * 						"paytime":"2015-08-03 18:34:31.0","status":"1","statusName":"交易成功","img":"http://static.okdi.net/bill/qianxiang2x.png",
	 * 						"tradeNum":"","bankName":"好递"}
	 * 						]},
	 * 			{"month":"七月","outcome":"","intcome":"20.00",
	 * 			"tradeList":[{
	 * 						"tid":"158260526718986","title":"赚外快-邀请","type":"6","amount":"+20.00","date":"07-18 19:17",
	 * 						"paytime":"2015-07-18 19:17:20.0","status":"1","statusName":"交易成功","img":"http://static.okdi.net/bill/qianxiang2x.png",
	 * 						"tradeNum":"","bankName":"好递"}]
	 * 			}],
	 * "success":true}
	 * 
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *    {"message":"钱包账户不存在","success":false,"errcode":"004"}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getOKdiLifeCashTrade", method = { RequestMethod.POST, RequestMethod.GET })
	public String getOKdiLifeCashTrade(String  accountCard, int currentPage){
		if(PubMethod.isEmpty(accountCard)){
			return paramsFailure("okdilifeapi.WalletController.getOKdiLifeCashTrade.001","账户号不能为空 ");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("okdilifeapi.WalletController.getOKdiLifeCashTrade.002","当前页不能为空 ");
		}
		try {
			return walletService.getOKdiLifeCashTrade(accountCard, currentPage);		
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	//TODO 3
	/**
	 * @api {post} /wallet/getTradeType 查询交易类型
	 * @apiPermission user
	 * @apiDescription 查询交易类型 kai.yang
	 * @apiparam {String} catId 账户类型 100001:个人、100002:收派员、100003:店铺、100007:配送员 
	 * @apiGroup ACCOUNT 交易业务
	 * @apiSampleRequest /wallet/getTradeType
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *{"data":{"2":"提现","1":"购物","0":"全部","6":"赚外快"},"success":true}
	 * 
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 * {"errCode":0,"errSubcode":"okdilifeapi.WalletController.getTradeType.001","message":"账户类型不能为空 ","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getTradeType", method = { RequestMethod.POST, RequestMethod.GET })
	public String getTradeType(String  catId){ //catId  100001:个人、100002:收派员、100003:店铺、100007:配送员 
		if(PubMethod.isEmpty(catId)){
			return paramsFailure("okdilifeapi.WalletController.getTradeType.001","账户类型不能为空 ");
		}
		try {
			return walletService.getTradeType(catId);		
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}