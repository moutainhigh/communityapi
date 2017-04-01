package net.okdi.apiV3.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.okdi.apiV3.entity.ReceiveMsg;
import net.okdi.apiV3.service.WalletNewService;
import net.okdi.core.base.BaseController;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.PayHttpClient;
import net.okdi.httpClient.WechatHttpClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSON;

/**
 * 快递员钱包相关接口
 * @Project Name:publicapi 
 * @Package net.okdi.apiV3.controller  
 * @Title: WalletController.java 
 * @ClassName: WalletController <br/> 
 * @date: 2016-1-20 下午3:18:33 <br/> 
 * @version v2.0 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/walletNew")
public class WalletNewController extends BaseController{

	private static Logger logger = Logger.getLogger(WalletNewController.class);
	@Autowired
	private WalletNewService walletNewService;//注入service
	@Autowired
	private PayHttpClient payHttpClient;
	@Autowired
	private WechatHttpClient WechatHttpClient;
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/checkAccountPwdIsExist 验证是否设置密码
	 * @apiVersion 0.2.0
	 * @apiDescription 验证是否设置密码
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiSampleRequest /walletNew/checkAccountPwdIsExist
	 * @apiSuccess {String} flag  是否设置密码：0否1是
	 * @apiSuccessExample Success-Response:
	    {
		    "DATA": {
		        "flag": "0"
		    },
		    "RESULT": "success"
		}
	
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	    "DATA": {
	 *	        "MSG": "账户号不存在"
	 *	    },
	 *	    "RESULT": "false"
	 *	}
	 */
	@ResponseBody
	@RequestMapping(value = "/checkAccountPwdIsExist", method = { RequestMethod.POST, RequestMethod.GET })
	public String checkAccountPwdIsExist(String accountId){
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.checkAccountPwdIsExist.001","accountId不能为空 ");
		}
		try {
			return this.walletNewService.checkAccountPwdIsExist(accountId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/validatePassword 验证钱包旧密码
	 * @apiVersion 0.2.0
	 * @apiDescription 验证钱包旧密码
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} password 加密密码
	 * @apiSampleRequest /walletNew/validatePassword
	 * @apiSuccess {String} isRight  旧密码是否正确：0否1是
	 * @apiSuccessExample Success-Response:
	    {
		    "success": true,
		    "error_code": "",
		    "error_msg": "",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": {
		        "isRight": "0"
		    }
		}
	
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *		    "success": false,
	 *		    "error_code": "err.account.101",
	 *		    "error_msg": "账户号不存在",
	 *		    "page": "0",
	 *		    "hasnext": false,
	 *		    "rows": "0",
	 *		    "total": "0",
	 *		    "data": ""
	 *		}
	 */
	@ResponseBody
	@RequestMapping(value = "/validatePassword", method = { RequestMethod.POST, RequestMethod.GET })
	public String validatePassword(String accountId,Long memberId,String password){
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.validatePassword.001","账户不能为空 ");
		}
		if(PubMethod.isEmpty(password)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.validatePassword.002","密码不能为空 ");
		}
		try {
			return this.walletNewService.validatePassword(accountId, memberId, password);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/setWalletPassword 设置钱包密码
	 * @apiVersion 0.2.0
	 * @apiDescription 设置钱包密码
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {String} password 加密密码
	 * @apiSampleRequest /walletNew/setWalletPassword
	 * @apiSuccess {String} RESULT  
	 * @apiSuccessExample Success-Response:
	    {
		    "RESULT": "success"
		}
	
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 *     
	 */
	@ResponseBody
	@RequestMapping(value = "/setWalletPassword", method = { RequestMethod.POST, RequestMethod.GET })
	public String setWalletPassword(String accountId,String password){
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.setWalletPassword.001","账户号不能为空 ");
		}
		if(PubMethod.isEmpty(password)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.setWalletPassword.002","密码不能为空 ");
		}
		String result="";
		try {
			result = walletNewService.setWalletPassword(accountId, password);
	} catch (Exception e) {
		    result = jsonFailure(e);
	}
		return result;
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/getBalance 查询余额
	 * @apiVersion 0.2.0
	 * @apiDescription 查询余额
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiSampleRequest /walletNew/getBalance
	 * @apiSuccess {String} cashAvailableBalance	现金账户可用余额
	 * @apiSuccess {String} teleBalance 通信账户余额
	 * @apiSuccess {String} teleAvailableBalance 通信账户可用余额
	 * @apiSuccess {String} freezeBalance 通信账户冻结/预扣金额
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "balance": "150.00",
		        "freezeBalance": "0.00",
		        "telecomBalance": "0.00"
		    },
		    "success": true
		}
	
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	    {
		    "success": false,
		    "error_code": "err.sys.905",
		    "error_msg": "系统异常,请联系技术支持!",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": ""
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/getBalance", method = { RequestMethod.POST, RequestMethod.GET })
	public String getBalance(String accountId){
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.getBalance.001","accountId不能为空 ");
		}
		try {
			HashMap<String, Object> map = walletNewService.getBalance(accountId);
			return jsonSuccess(map);	
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/sendVerifyCodeCreateAccount 发送-开通钱包短信验证码
	 * @apiVersion 0.2.0
	 * @apiDescription 发送-开通钱包短信验证码
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} validTimes 是否验证每天发送次数 true验证 false不验证
	 * @apiSampleRequest /walletNew/sendVerifyCodeCreateAccount
	 * @apiSuccess {String} result	SendTrue发送成功；SendFalse发送失败
	 * @apiSuccessExample Success-Response:
	    {"result":"SendTrue"}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	    {"result":"SendFalse"}
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerifyCodeCreateAccount", method = { RequestMethod.POST, RequestMethod.GET })
	public String sendVerifyCodeCreateAccount(String mobile, String validTimes,String version){
		if(PubMethod.isEmpty(mobile)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.sendVerifyCodeCreateAccount.001","手机号不能为空 ");
		}
		String result = "";
		try {
			Long busineType = 9l;
			result = walletNewService.sendVerifyCodeCreateAccount(mobile, busineType, validTimes,version);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/validVerifyCodeCreateAccount 校验-开通钱包短信验证码
	 * @apiVersion 0.2.0
	 * @apiDescription 校验-开通钱包短信验证码
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} verifyCode 短信验证码
	 * @apiSampleRequest /walletNew/validVerifyCodeCreateAccount
	 * @apiSuccess {String} result	true校验成功；false校验失败
	 * @apiSuccessExample Success-Response:
	    {"result":"true"}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	    {"result":"false"}
	 */
	@ResponseBody
	@RequestMapping(value = "/validVerifyCodeCreateAccount", method = { RequestMethod.POST, RequestMethod.GET })
	public String validVerifyCodeCreateAccount(String mobile, String verifyCode){
		if(PubMethod.isEmpty(mobile)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.validVerifyCodeCreateAccount.001","手机号不能为空 ");
		}
		if(PubMethod.isEmpty(verifyCode)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.validVerifyCodeCreateAccount.002","验证码不能为空 ");
		}
		String result = "";
		try {
			Long busineType = 9l;
			result = walletNewService.validVerifyCodeCreateAccount(mobile, busineType, verifyCode);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/sendVerifyCodeFindPassword 发送-找回支付密码短信验证码
	 * @apiVersion 0.2.0
	 * @apiDescription 发送-找回支付密码短信验证码
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} validTimes 是否验证每天发送次数 true验证 false不验证
	 * @apiSampleRequest /walletNew/sendVerifyCodeFindPassword
	 * @apiSuccess {String} result	SendTrue发送成功；SendFalse发送失败
	 * @apiSuccessExample Success-Response:
	    {"result":"SendTrue"}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	    {"result":"SendFalse"}
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerifyCodeFindPassword", method = { RequestMethod.POST, RequestMethod.GET })
	public String sendVerifyCodeFindPassword(String mobile, String validTimes,String version){
		if(PubMethod.isEmpty(mobile)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.sendVerifyCodeFindPassword.001","手机号不能为空 ");
		}
		String result = "";
		try {
			Long busineType = 10l;
			result = walletNewService.sendVerifyCodeCreateAccount(mobile, busineType, validTimes,version);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/validVerifyCodeFindPassword 校验-找回支付密码短信验证码
	 * @apiVersion 0.2.0
	 * @apiDescription 校验-找回支付密码短信验证码
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} verifyCode 短信验证码
	 * @apiSampleRequest /walletNew/validVerifyCodeFindPassword
	 * @apiSuccess {String} result	true校验成功；false校验失败
	 * @apiSuccessExample Success-Response:
	    {"result":"true"}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	    {"result":"false"}
	 */
	@ResponseBody
	@RequestMapping(value = "/validVerifyCodeFindPassword", method = { RequestMethod.POST, RequestMethod.GET })
	public String validVerifyCodeFindPassword(String mobile, String verifyCode){
		if(PubMethod.isEmpty(mobile)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.validVerifyCodeFindPassword.001","手机号不能为空 ");
		}
		if(PubMethod.isEmpty(verifyCode)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.validVerifyCodeFindPassword.002","验证码不能为空 ");
		}
		String result = "";
		try {
			Long busineType = 10l;
			result = walletNewService.validVerifyCodeCreateAccount(mobile, busineType, verifyCode);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/findBankCard 查询我的银行卡列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询我的银行卡列表
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiSampleRequest /walletNew/findBankCard
	 * @apiSuccess {String} bankId 渠道ID
	 * @apiSuccess {String} bankName 银行名称
	 * @apiSuccess {String} bindPhone 银行卡绑定的手机号
	 * @apiSuccess {String} cardNum 银行卡号
	 * @apiSuccess {String} cardOwnerName 银行卡开户人姓名
	 * @apiSuccess {String} id 银行卡绑定ID
	 * @apiSuccess {String} isDefault 是否是默认的 0 否，1 是
	 * @apiSuccess {String} picUrl 银行卡logo
	 * @apiSuccess {String} status 银行卡状态 0：正常 1：禁用
	 * @apiSuccessExample Success-Response:
	    {
		    "DATA": [
		        {
		            "bankId": "102",
		            "bankName": "1",
		            "bindPhone": "15011232453",
		            "cardNum": "6525552335566555655",
		            "cardOwnerName": "ab",
		            "id": "96405995896452096",
		            "isDefault": "0",
		            "picUrl": "http://www.okdit.net/nfs_data/okdiLife/bank/102.png",
		            "status": "0"
		        }
		    ],
		    "RESULT": "success"
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	    {
		    "DATA": {
		        "MSG": "系统异常,请联系技术支持!"
		    },
		    "RESULT": "error"
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/findBankCard", method = { RequestMethod.POST, RequestMethod.GET })
	public String findBankCard(String accountId){
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.findBankCard.001","accountId不能为空 ");
		}
		String result = "";
		try {
			result = walletNewService.findBankCard(accountId);
		} catch (Exception e) {
			result = jsonFailure(e);
		}
		return result;
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/getBankInfoList 查询所有支持的银行卡列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询所有支持的银行卡列表
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiSampleRequest /walletNew/getBankInfoList
	 * @apiSuccess {String} id 渠道ID
	 * @apiSuccess {String} channels_name 渠道名称
	 * @apiSuccess {String} channels_type 渠道类型
	 * @apiSuccess {String} channels_code 简码
	 * @apiSuccess {String} pic_url LOGO地址
	 * @apiSuccess {String} agency_id
	 * @apiSuccess {String} support 是否支持
	 * @apiSuccessExample Success-Response:
	    {
		    "success": true,
		    "error_code": "",
		    "error_msg": "",
		    "page": "1",
		    "hasnext": false,
		    "rows": "20",
		    "total": "19",
		    "data": [
			        {
			            "id": "101",
			            "channels_name": "招商银行",
			            "channels_type": "deposit",
			            "channels_code": "CMB_D",
			            "pic_url": "http://static.okdi.net/bank/icon/101.png",
			            "agency_id": "",
			            "support": "1"
			        },
			        {
			            "id": "102",
			            "channels_name": "中国工商银行",
			            "channels_type": "deposit",
			            "channels_code": "ICBC_D",
			            "pic_url": "http://static.okdi.net/bank/icon/102.png",
			            "agency_id": "",
			            "support": "1"
			        }
           		]
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	   	{
		    "success": false,
		    "error_code": "",
		    "error_msg": "",
		    "page": "1",
		    "hasnext": false,
		    "rows": "",
		    "total": "",
		    "data": []
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/getBankInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	public String getBankInfoList(){
		try {
			return this.walletNewService.getBankInfoList();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/insertBankCard 添加银行卡
	 * @apiVersion 0.2.0
	 * @apiDescription 添加银行卡
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {String} bankName 银行名称
	 * @apiParam {String} memberName 银行卡开户人姓名
	 * @apiParam {String} bankCard 银行号卡
	 * @apiParam {String} idNum 开户人身份证号码
	 * @apiParam {Long} memberPhone 银行卡绑定手机号
	 * @apiParam {Long} bankId 渠道号ID
	 * @apiSampleRequest /walletNew/insertBankCard
	 * @apiSuccess {String} bankCardId 银行卡id
	 * @apiSuccessExample Success-Response:
	    {
		    "success": true,
		    "error_code": "",
		    "error_msg": "",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": {
		        "bankCardId": "96414573197800448"
		    }
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	   	{
		    "success": false,
		    "error_code": "",
		    "error_msg": "",
		    "page": "1",
		    "hasnext": false,
		    "rows": "",
		    "total": "",
		    "data": ""
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/insertBankCard", method = { RequestMethod.POST, RequestMethod.GET })
	public String insertBankCard(String accountId, String bankName,String memberName,
			String bankCard, String idNum,Long memberPhone,Long bankId){
		System.out.println("添加银行卡传过来的参数===accountId"+accountId+" bankName="+bankName+" memberName="+memberName+
				"bankCard="+bankCard+" idNum="+idNum+" memberPhone="+memberPhone+" bankId="+bankId);
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.insertBankCard.001","accountId不能为空");
		}
		if(PubMethod.isEmpty(bankName)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.insertBankCard.002","所属银行不能为空");
		}
		if(PubMethod.isEmpty(bankCard)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.insertBankCard.003","银行卡号不能为空");
		}
		if(PubMethod.isEmpty(idNum)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.insertBankCard.004","身份证号不能为空");
		}
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.insertBankCard.005","手机号不能为空");
		}
		if(PubMethod.isEmpty(memberName)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.insertBankCard.006","持卡人姓名不能为空");
		}
		if(PubMethod.isEmpty(bankId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.insertBankCard.007","bankId不能为空");
		}
		try{
			return  this.walletNewService.insertBankCard(accountId,bankName,memberName,bankCard,idNum,memberPhone,bankId);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/deleteBankCard 删除银行卡
	 * @apiVersion 0.2.0
	 * @apiDescription 删除银行卡
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {Long} bankCardId 银行卡ID
	 * @apiSampleRequest /walletNew/deleteBankCard
	 * @apiSuccessExample Success-Response:
	    {
		    "success": true,
		    "error_code": "",
		    "error_msg": "",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": ""
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	   	{
		    "success": false,
		    "error_code": "",
		    "error_msg": "",
		    "page": "1",
		    "hasnext": false,
		    "rows": "",
		    "total": "",
		    "data": ""
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteBankCard", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteBankCard(Long bankCardId,String accountId){
		if(PubMethod.isEmpty(bankCardId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.deleteBankCard.001","bankCardId不能为空");
		}
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.deleteBankCard.002","accountId不能为空");
		}
		try{
			return  this.walletNewService.deleteBankCard(bankCardId,accountId);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/withdraw 提现申请
	 * @apiVersion 0.2.0
	 * @apiDescription 提现申请
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {String} money 提现金额,单位:元 小数点2位
	 * @apiParam {String} bankCardNum 提现到银行卡,当用户提现到银行卡时,需要传此参数。bankCardNum用户绑定的银行卡号
	 * @apiParam {String} bankName 银行名称
	 * @apiParam {String} remark 备注
	 * @apiSampleRequest /walletNew/withdraw
	 * @apiSuccessExample Success-Response:
	    {"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	   	{"success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/withdraw", method = { RequestMethod.POST, RequestMethod.GET })
	public String withdraw(String accountId,String money,String bankCardNum,String bankName,String remark){
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.withdraw.001","accountId不能为空");
		}
		if(PubMethod.isEmpty(money)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.withdraw.002","money不能为空");
		}
		if(PubMethod.isEmpty(bankCardNum)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.withdraw.003","bankCardNum不能为空");
		}
		if(PubMethod.isEmpty(bankName)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.withdraw.004","bankName不能为空");
		}
		try{
			return  this.walletNewService.withdraw(accountId,money,bankCardNum,bankName,remark);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/isHaveBankOrWechat 查询该账户是否绑定银行卡或微信账户
	 * @apiVersion 0.2.0
	 * @apiDescription 查询该账户是否绑定银行卡或微信账户
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiSampleRequest /walletNew/isHaveBankOrWechat
	 * @apiSuccess {String} isHaveBankFlag 是否绑定了银行卡：0否1是
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "isHaveBankFlag": "1"
		    },
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	   	{
	 	     "success":	false,
	 	     "errCode":	"err.001",
	      	 "message":"XXX"
	    }
	 */
	@ResponseBody
	@RequestMapping(value = "/isHaveBankOrWechat", method = { RequestMethod.POST, RequestMethod.GET })
	public String isHaveBankOrWechat(String accountId){
		try {
			HashMap<String, Object> resultMap = this.walletNewService.isHaveBankOrWechat(accountId);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/checkWithdrawLimit 查询提现额度
	 * @apiVersion 0.2.0
	 * @apiDescription 查询提现额度
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiSampleRequest /walletNew/checkWithdrawLimit
	 * @apiSuccess {String} accountId 钱包账户id
	 * @apiSuccess {String} dayAmount 用户日提现申请金额，不区分是否成功
	 * @apiSuccess {String} dayTimes 用户日提现申请次数，不区分是否成功
	 * @apiSuccess {String} monthAmount 用户月提现申请金额，不区分是否成功
	 * @apiSuccess {String} monthTimes 用户月提现申请次数，不区分是否成功
	 * @apiSuccess {String} perDayMax 单日提现最大额度
	 * @apiSuccess {String} perDayTimes 单日提现最大笔数
	 * @apiSuccess {String} perWeekMax 单周提现最大额度
	 * @apiSuccess {String} perWeekTimes 单周提现最大笔数
	 * @apiSuccess {String} perWithDrawMax 单笔提现最大额度
	 * @apiSuccess {String} perWithDrawMin 单笔提现最小额度
	 * @apiSuccess {String} weekAmount 用户周提现申请金额，不区分是否成功
	 * @apiSuccess {String} weekTimes 用户周提现申请次数，不区分是否成功
	 * @apiSuccess {String} error_code 1:余额不足;2:每日最多提现 %d 次;3:每周最多提现 %d 次;4:每日提现不能超过 %.2f 元;5:每周提现不能超过 %.2f 元;
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "accountId": "96243083446858752",
		        "dayAmount": "0.0",
		        "dayTimes": "0",
		        "monthAmount": "100.0",
		        "monthTimes": "1",
		        "perDayMax": "1000.00",
		        "perDayTimes": "1",
		        "perWeekMax": "2000.00",
		        "perWeekTimes": "2",
		        "perWithDrawMax": "500.00",
		        "perWithDrawMin": "50.00",
		        "weekAmount": "100.0",
		        "weekTimes": "1"
		    },
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	   	{"errcode":"000","message":"每日最多提现 2 次","success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/checkWithdrawLimit", method = { RequestMethod.POST, RequestMethod.GET })
	public String checkWithdrawLimit(String accountId){
		String resultMap = "";
		try {
			resultMap = this.walletNewService.checkWithdrawLimit(accountId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
		return resultMap;
	}

	/**
	 * @MethodName: net.okdi.apiV3.controller.WalletNewController.java.cashTradeList 
	 * @Description: TODO(废弃) 
	 * @param @param accountId
	 * @param @param startDate
	 * @param @param endDate
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-3-3
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/cashTradeList", method = { RequestMethod.POST, RequestMethod.GET })
	public String cashTradeList(String accountId, String startDate,String endDate){
		try {
			HashMap<String, Object> resultMap = this.walletNewService.cashTradeList(accountId,startDate,endDate);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/orderDetail 查询账单详情
	 * @apiVersion 0.2.0
	 * @apiDescription 查询账单详情
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {String} tid 交易号
	 * @apiSampleRequest /walletNew/orderDetail
	 * @apiSuccess {String} tid 交易号
	 * @apiSuccess {String} outer_tid 订单号
	 * @apiSuccess {String} created 交易时间
	 * @apiSuccess {String} outer_trade_type 交易类别  10010 群发通知-仅短信 10011 群发通知-电话优先 10012 群发通知-仅电话10013 拨打电话 10014 充值-余额充值到通讯费 
	 * 										10015 充值-微信充值到通讯费 10016 平台赠送-注册  10017 平台赠送-充值10018 平台赠送-邀请 10019 平台赠送-新手体验金 10020 平台赠送-好友充值返利 10021 平台赠送－关注好递用户（通信费账户） 10023 平台赠送－关注好递用户（现金余额账户）10022 群发通知-短信+群呼1006  平台赠送-邀请爱购猫用户 
	 *  1002  提现  200001收款-寄件
 	 * @apiSuccess {String} tradeStatus 交易状态
 	 * @apiSuccess {String} tradeType 交易说明
 	 * @apiSuccess {String} tradeMoney 交易金额
 	 * @apiSuccess {String} smsOrPhoneCount 短信/电话数量（仅电话/仅短信类型）
 	 * @apiSuccess {String} phoneCount 电话数量（电话优先类型）
 	 * @apiSuccess {String} noticeCount 短信数量（电话优先类型）
 	 * @apiSuccess {String} weChatCount 微信推送数量
 	 * @apiSuccess {String} payType 充值支付方式：微信支付/余额支付
	 * @apiSuccessExample Success-Response:
		 {
		    "data": {
		        "created": "2016-03-03 14:30:19",
		        "outer_tid": "",
		        "outer_trade_type": "1002",
		        "tid": "99858909520475136",
		        "tradeMoney": "-50",
		        "tradeStatus": "进行中",
		        "tradeType": "提现-abc(2222)"
		    },
		    "success": true
		}
		{
		    "data": {
		        "count": "1",
		        "created": "2016-03-02 17:48:26",
		        "outer_tid": "199562050756608",
		        "outer_trade_type": "10010",
		        "tid": "99780777115399168",
		        "tradeMoney": "-0.041",
		        "tradeStatus": "交易成功",
		        "tradeType": "群发通知-仅短信"
		    },
		    "success": true
		}
		{
		    "data": {
		        "created": "2016-03-02 14:18:18",
		        "outer_tid": "",
		        "outer_trade_type": "10017",
		        "tid": "99767556801311744",
		        "tradeMoney": "+4.0",
		        "tradeStatus": "交易成功",
		        "tradeType": "平台赠送-充值"
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
	@RequestMapping(value = "/orderDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String orderDetail(String accountId, String tid){
		try {
			HashMap<String, Object> resultMap = this.walletNewService.orderDetail(accountId,tid);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/rechargeByWeChat 微信充值到通信账户
	 * @apiVersion 0.2.0
	 * @apiDescription 微信充值到通信账户
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {Double} money 充值金额
	 * @apiSampleRequest /walletNew/rechargeByWeChat
	 * @apiSuccess {String} sign 签名
	 * @apiSuccess {String} timestamp 时间戳
	 * @apiSuccess {String} noncestr 随机字符串，不长于32位
	 * @apiSuccess {String} partnerid 商户号
	 * @apiSuccess {String} mch_id 商户号id
	 * @apiSuccess {String} prepay_id 预约号id 
	 * @apiSuccess {String} random_trade_num 交易号 
	 * @apiSuccess {String} package 扩展字段 暂填写固定值Sign=WXPay
	 * @apiSuccess {String} appid 微信分配的公众账号ID 
	 * @apiSuccess {String} tid 
	 * @apiSuccess {String} prepayId 预约号id 
	 * @apiSuccess {String} channelsCode 微信渠道
	 * @apiSuccessExample Success-Response:
		 {
		    "success": true,
		    "error_code": "",
		    "error_msg": "",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": {
		        "sign": "FD0B163340E945C9D8491C4074182F5F",
		        "timestamp": "1456733490",
		        "noncestr": "da09ba3g1t2geu8zbybcma8gn2cuuawf",
		        "partnerid": "1242142002",
		        "mch_id": "1242142002",
		        "prepay_id": "wx2016022916113031bdfff4090429464400",
		        "random_trade_num": "99593484086817792D2237235853",
		        "package": "Sign=WXPay",
		        "appid": "wx3fa4ab0149e5f9f6",
		        "tid": "99593484086817792",
		        "prepayId": "wx2016022916113031bdfff4090429464400",
		        "channelsCode": "weixinpay"
		    }
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
	@RequestMapping(value = "/rechargeByWeChat", method = { RequestMethod.POST, RequestMethod.GET })
	public String rechargeByWeChat(String accountId, Double money){
		try {
			return this.walletNewService.rechargeByWeChat(accountId,money);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/queryRewardMoney 根据输入金额查询充值奖励金额
	 * @apiVersion 0.2.0
	 * @apiDescription 根据输入金额查询充值奖励金额
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {Double} money 充值金额
	 * @apiSampleRequest /walletNew/queryRewardMoney
	 * @apiSuccess {Double} rewardMoney 充值活动奖励金额
	 * @apiSuccessExample Success-Response:
		 {
		    "data": {
		        "rewardMoney": 2
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
	@RequestMapping(value = "/queryRewardMoney", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRewardMoney(Double money){
		try {
			HashMap<String, Object> resultMap = this.walletNewService.queryRewardMoney(money);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/rechargeByBalance 将余额充值到通信账户
	 * @apiVersion 0.2.0
	 * @apiDescription 将余额充值到通信账户
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {Double} money 充值金额
	 * @apiSampleRequest /walletNew/rechargeByBalance
	 * @apiSuccess {String} result false余额不足  true充值成功
	 * @apiSuccessExample Success-Response:
	 * {
		    "data": {
		        "result": "true"
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
	@RequestMapping(value = "/rechargeByBalance", method = { RequestMethod.POST, RequestMethod.GET })
	public String rechargeByBalance(String accountId, Double money){
		if(PubMethod.isEmpty(money)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.rechargeByBalance.001","请输入转账金额");
		}
		try {
			HashMap<String, Object> resultMap = this.walletNewService.rechargeByBalance(accountId,money);
			return jsonSuccess(resultMap);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
	
	
	public static void main(String[] args) {
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
        String now = null;
        String end = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
       System.out.println(now);
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            end = shortSdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
       System.out.println(end);
       
       System.out.println(Double.valueOf("-1")+Double.valueOf("-2"));
       int pageSize = 20;
       int currentPage = 2;
       int begin = pageSize * (currentPage - 1);
		int pageEnd =  begin + pageSize;
		int ends = Math.min(pageEnd, 30);
		if(begin>=ends||ends==0){
			System.out.println("111111");
		}
       System.out.println("begin:"+begin+";ends:"+ends);
	  
       String aString = "15.456";
       double parseDouble = Double.parseDouble(aString);
       DecimalFormat  df   = new DecimalFormat("0.00"); 
       String format = df.format(parseDouble);
       System.out.println(format);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/getAccountId", method = { RequestMethod.POST, RequestMethod.GET })
	public String getAccountId(Long memberId){
		try {
			String resultMap = this.walletNewService.getAccountId(memberId);
			return resultMap;
		} catch (Exception e) {
			return "";
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/cancelOrder 微信支付取消支付订单
	 * @apiVersion 0.2.0
	 * @apiDescription 微信支付取消支付订单
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包账户id
	 * @apiParam {String} tid 支付系统交易号
	 * @apiSampleRequest /walletNew/cancelOrder
	 * @apiSuccess {String} success true/false
	 * @apiSuccessExample Success-Response:
	 * {
		    "success": true,
		    "error_code": "",
		    "error_msg": "",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": ""
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 	{
		    "success": false,
		    "error_code": "err.trade.517",
		    "error_msg": "支付订单状态不正确",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": ""
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String cancelOrder(String tid, String accountId){
		if(PubMethod.isEmpty(tid)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.cancelOrder.001","支付系统交易号不能为空");
		}
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.cancelOrder.002","支付账户Id不能为空");
		}
		try {
			return this.walletNewService.cancelOrder(tid,accountId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/repayment 继续支付订单
	 * @apiVersion 0.2.0
	 * @apiDescription 继续支付订单
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} tid 支付系统交易号
	 * @apiParam {String} isJSAPI 是否为微信公众号支付（false不是 true 是）
	 * @apiParam {String} openid 用户标识（微信isJSAPI为true时必须传）
	 * @apiSampleRequest /walletNew/repayment
	 * @apiSuccess {String} sign 签名
	 * @apiSuccess {String} timestamp 时间戳
	 * @apiSuccess {String} noncestr 随机字符串，不长于32位
	 * @apiSuccess {String} partnerid 商户号
	 * @apiSuccess {String} package 扩展字段 暂填写固定值Sign=WXPay
	 * @apiSuccess {String} appid 微信分配的公众账号ID 
	 * @apiSuccess {String} tid 交易号
	 * @apiSuccess {String} prepayId 预约号id 
	 * @apiSuccess {String} channelsCode 微信渠道
	 * @apiSuccessExample Success-Response:
	 * {
		    "success": true,
		    "error_code": "",
		    "error_msg": "",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": {
		        "sign": "D6EA7AC93A342C99F385670FB9612003",
		        "timestamp": "1450426469",
		        "noncestr": "2mwxm3lge3hr7iq3f7c57p8jgnbchng7",
		        "partnerid": "1242142002",
		        "package": "Sign=WXPay",
		        "appid": "wx3fa4ab0149e5f9f6",
		        "tid": "92980093021851648",
		        "prepayId": "wx20151218161423c51aff746e0285120727",
		        "channelsCode": "weixinpay"
		    }
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 	{
		    "success": false,
		    "error_code": "err.trade.510",
		    "error_msg": "支付订单未创建",
		    "page": "0",
		    "hasnext": false,
		    "rows": "0",
		    "total": "0",
		    "data": ""
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/repayment", method = { RequestMethod.POST, RequestMethod.GET })
	public String repayment(String tid, String isJSAPI,String openid){
		if(PubMethod.isEmpty(tid)){
			return paramsFailure("net.okdi.apiV3.controller.WalletNewController.repayment.001","支付系统交易号不能为空");
		}
		try {
			return this.walletNewService.repayment(tid,isJSAPI,openid);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryInviteRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryInviteRecord(String memberId){
		try {
			return jsonSuccess(this.walletNewService.queryInviteRecord(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryRechargeRebateAct", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRechargeRebateAct(){
		try {
			return jsonSuccess(this.walletNewService.queryRechargeRebateAct());
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/rechargeMoneyToInvite", method = { RequestMethod.POST, RequestMethod.GET })
	public String rechargeMoneyToInvite(String accountId,String memberId,String rechargeMoney){
		try {
			return jsonSuccess(this.walletNewService.rechargeMoneyToInvite(accountId,memberId,rechargeMoney,"1"));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/queryRebateRecord 查询外快记录（好友充值返利记录）
	 * @apiVersion 0.2.0
	 * @apiDescription 查询外快记录（好友充值返利记录）
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 快递员钱包id
	 * @apiParam {String} page 当前页码
	 * @apiParam {String} rows 每页显示条数
	 * @apiSampleRequest /walletNew/queryRebateRecord
	 * @apiSuccess {String} createTime 时间
	 * @apiSuccess {String} memberName 姓名
	 * @apiSuccess {String} memberPhone 手机号
	 * @apiSuccess {String} rechargeMoney 充值金额
	 * @apiSuccess {String} rebateMoney 返利金额
	 * @apiSuccess {String} outerTradeType 10018 平台赠送-邀请；10020 平台赠送-好友充值返利；10021 平台赠送－关注好递用户（通信费账户）;10023 平台赠送－关注好递用户（现金余额账户）;1006 平台赠送-邀请爱购猫用户
	 * @apiSuccess {String} tid 交易号id
	 * @apiSuccessExample Success-Response:
	 * {
		    "data": [
		        {
		            "createTime": "16/07/09 15:15",
		            "memberName": "你大爷",
		            "rebateMoney": "1.25",
		            "rechargeMoney": "25.00",
		            "tid": "111458191130243072"
		        },
		        {
		            "createTime": "16/07/09 15:08",
		            "memberName": "你大爷",
		            "rebateMoney": "1.25",
		            "rechargeMoney": "25.00",
		            "tid": "111457750770266112"
		        }
		    ],
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 	{
		    "success": false
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRebateRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRebateRecord(String accountId,String page,String rows){
		try {
			List<HashMap<String, Object>> list = this.walletNewService.queryRebateRecord(accountId,page,rows);
			return jsonSuccess(list);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/queryActivityList 查询充值奖励规则
	 * @apiVersion 0.2.0
	 * @apiDescription 查询充值奖励规则
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiSampleRequest /walletNew/queryActivityList
	 * @apiSuccess {Long} activityId 活动id
	 * @apiSuccess {Double} startMoney 充值金额范围开始
	 * @apiSuccess {Double} endMoney 充值金额范围结束
	 * @apiSuccess {Double} rewardMoney 奖励金额
	 * @apiSuccess {Short} isDelete 删除标识  0否1是
	 * @apiSuccessExample Success-Response:
	 * {
		    "data": {
		        "activityId": 233801265020928,
		        "ruleList": [
		            {
		                "activityId": 233801265020928,
		                "createTime": "2016-09-07 17:01:11",
		                "endMoney": 10,
		                "id": 233801265020929,
		                "isDelete": 0,
		                "rewardMoney": 1,
		                "startMoney": 1,
		                "style": "",
		                "type": 2
		            },
		            {
		                "activityId": 233801265020928,
		                "createTime": "2016-09-07 17:01:11",
		                "endMoney": 20,
		                "id": 233801265020930,
		                "isDelete": 0,
		                "rewardMoney": 2,
		                "startMoney": 11,
		                "style": "",
		                "type": 2
		            },
		            {
		                "activityId": 233801265020928,
		                "createTime": "2016-09-07 17:01:11",
		                "endMoney": 500,
		                "id": 233801265020931,
		                "isDelete": 0,
		                "rewardMoney": 3,
		                "startMoney": 21,
		                "style": "",
		                "type": 2
		            }
		        ]
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
	@RequestMapping(value = "/queryActivityList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryActivityList(){
		try {
			return this.walletNewService.queryActivityList();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/createScanCodeOrder 扫码收款生成微信二维码订单
	 * @apiVersion 0.2.0
	 * @apiDescription 扫码收款生成微信二维码订单
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {Long} accountId 钱包id
	 * @apiParam {Double} tradeTotalAmount 付款金额
	 * @apiParam {Long} memberId 快递员id
	 * @apiSampleRequest /walletNew/createScanCodeOrder
	 * @apiSuccess {String} appid 微信分配的公众账号ID
	 * @apiSuccess {String} channelsCode 微信渠道
	 * @apiSuccess {String} code_url 二维码url
	 * @apiSuccess {String} noncestr 随机字符串，不长于32位
	 * @apiSuccess {String} package 
	 * @apiSuccess {String} partnerid 商户号
	 * @apiSuccess {String} prepayId 预约号id
	 * @apiSuccess {String} sign 
	 * @apiSuccess {String} tid 交易id
	 * @apiSuccess {String} timestamp 时间戳
	 * @apiSuccessExample Success-Response:
	 * {
		    "data": {
		        "appid": "wxd11b57b3d88a4c21",
		        "channelsCode": "weixinpay",
		        "code_url": "weixin://wxpay/bizpayurl?pr=4gSEAwO",
		        "noncestr": "p0v59tgpi17g1s3ksh5fvkol6z0td4dv",
		        "package": "Sign=WXPay",
		        "partnerid": "1248131301",
		        "prepayId": "wx20161121162541bec20fd51f0059172669",
		        "sign": "CEDC185257B1D9010BC50B49769A2E75",
		        "tid": "123693169419514880",
		        "timestamp": "1479716741"
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
    @RequestMapping(value = "/createScanCodeOrder", method = { RequestMethod.POST, RequestMethod.GET })
    public String createScanCodeOrder(Double tradeTotalAmount,Long accountId,Long memberId){
    	if(PubMethod.isEmpty(tradeTotalAmount)){
    		return paramsFailure("net.okdi.apiV3.controller.WalletNewController.createScanCodeOrder.001", "tradeTotalAmount不能为空");
    	}
    	if(PubMethod.isEmpty(accountId)){ 
    		return paramsFailure("net.okdi.apiV3.controller.SendPackageController.createScanCodeOrder.002", "accountId不能为空");
    	}
    	try {
    		return this.walletNewService.createScanCodeOrder(tradeTotalAmount,accountId,memberId);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonFailure(e);
    	}
    }
	
	
	/**
	 * 微信扫码付款回调接口
	 * @param request
	 * @param response
	 * @param is
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/scanCodeCallback", method = { RequestMethod.POST, RequestMethod.GET })
	public void scanCodeCallback(HttpServletRequest request, HttpServletResponse response, InputStream is)
			throws IOException {
		PrintWriter pw = response.getWriter();
		logger.info("微信扫码支付异步回调 /walletNew/scanCodeCallback publicapi--net.okdi.apiV3.controller.WalletNewController.scanCodeCallback");
		try {
			String return_code = "FAIL";
			String return_msg = "ERROR";
			/*byte[] tempbytes = new byte[1024];  
            int byteread = 0;
            while ((byteread = is.read(tempbytes)) != -1) {
            	System.out.println("aaaaaaaaaaaaaaaaaa");
            	System.out.write(tempbytes, 0, byteread);
            	System.out.println("bbbbbbbbbbbbbbbbbbbbb");
            	
			}*/
			/** 微信异步回调返回数据是xml **/
			ReceiveMsg receiveMsg = new ReceiveMsg();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();// 获得根节点
			NodeList msgs = root.getChildNodes();
			// 解析XML里面的数据并且封装成对象
			for (int i = 0; i < msgs.getLength(); i++) {// 遍历元素
				Node msg = msgs.item(i);
				if (msg.getNodeType() == Node.ELEMENT_NODE) {// 如果是元素节点
					String nodeName = msg.getNodeName().trim();// 获得元素的名
					String nodeValue = msg.getTextContent();
					setReceiveMsg(receiveMsg, nodeName, nodeValue);
				}
			}
			logger.info("微信扫码支付异步回调scanCodeCallback--微信支付异步回调返回的参数/walletNew/scanCodeCallback:" + JSON.toJSONString(receiveMsg));
			// 只要return_code为SUCCESS时候 微信才会返回业务数据
			if ("SUCCESS".equals(receiveMsg.getReturn_code())) {
				String payStatus = "";
				String attach = "";
				// 根据业务结果判断支付状态 SUCCESS为支付成功 其他为支付失败
				if ("SUCCESS".equals(receiveMsg.getResult_code())) {
					payStatus = "1";
					attach = receiveMsg.getAttach();
				} else {
					payStatus = "2";
				}
				// 1.调用财务系统修改订单支付状态
		        String result = this.payHttpClient.invokePaymentCallback(receiveMsg.getOut_trade_no(), payStatus, 
		        		receiveMsg.getAttach(), receiveMsg.getSign(), receiveMsg.getMch_id(), receiveMsg.getTransaction_id(), "NATIVE",receiveMsg.getOpenid());
		        logger.info("微信扫码支付异步回调scanCodeCallback调用财务系统返回结果:" + JSON.toJSONString(result));
		        Map map = (Map) JSON.parseObject(result).get("data");
				//String accountId = map.get("accountId").toString();
		        String accountId = map.get("sellerAccountId").toString();
		        if (JSON.parseObject(result).get("success").equals(Boolean.valueOf(true))) {
		        	if("1".equals(map.get("state").toString())){//交易成功
		        		String memberId = WechatHttpClient.getMemberIdByAccountId(accountId);
		        		//2.成功发送一条推送
		        		if(!PubMethod.isEmpty(memberId)){
		        			String total_fee = receiveMsg.getTotal_fee();//微信返回金额为分
							double parseDouble = Double.parseDouble(total_fee);
							double money = parseDouble/100;
							Map<String, String> smsMap = new HashMap<String, String>();
							smsMap.put("money",money+"");
							smsMap.put("memberId",memberId);
							openApiHttpClient.doPassSendStr("expressRegister/sendMsgExpressFee/", smsMap);
							logger.info("微信扫码支付异步回调scanCodeCallback，快递员收款推送成功！");
						}
				        return_code = "SUCCESS";
				        return_msg = "OK";
				        logger.info("微信扫码支付异步回调scanCodeCallback，请求财务系统成功！");
		        	}
		        } 
			}
			String xml = "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA["
					+ return_msg + "]]></return_msg></xml>";
			logger.info("微信扫码支付异步回调scanCodeCallback--同步告诉微信结果:" + xml);
			pw.write(xml);
		} catch (Exception e) {
			e.printStackTrace();
			String xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg>ERROR</return_msg></xml>";
			logger.info("微信扫码支付异步回调scanCodeCallback--同步告诉微信结果:" + xml);
			pw.write(xml);
		} finally {
			pw.close();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV3.controller.WechatPayBackController.java.setReceiveMsg 
	 * @Description: TODO(封装数据) 
	 * @param @param msg
	 * @param @param name
	 * @param @param value   
	 * @return void  返回值类型
	 * @throws 
	 * @auth zhaohu
	 */
	private void setReceiveMsg(ReceiveMsg msg, String name, String value) {
		if (name.equals("return_code"))
			msg.setReturn_code(value);
		if (name.equals("return_msg"))
			msg.setReturn_msg(value);
		if (name.equals("appid"))
			msg.setAppid(value);
		if (name.equals("mch_id"))
			msg.setMch_id(value);
		if (name.equals("nonce_str"))
			msg.setNonce_str(value);
		if (name.equals("sign"))
			msg.setSign(value);
		if (name.equals("result_code"))
			msg.setResult_code(value);
		if (name.equals("openid"))
			msg.setOpenid(value);
		if (name.equals("is_subscribe"))
			msg.setIs_subscribe(value);
		if (name.equals("trade_type"))
			msg.setTrade_type(value);
		if (name.equals("bank_type"))
			msg.setBank_type(value);
		if (name.equals("total_fee"))
			msg.setTotal_fee(value);
		if (name.equals("cash_fee"))
			msg.setCash_fee(value);
		if (name.equals("transaction_id"))
			msg.setTransaction_id(value);
		if (name.equals("out_trade_no"))
			msg.setOut_trade_no(value);
		if (name.equals("time_end"))
			msg.setTime_end(value);
		if (name.equals("attach"))
			msg.setAttach(value);
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /walletNew/queryExpressFeeRecord 查询收款记录
	 * @apiVersion 0.2.0
	 * @apiDescription 查询收款记录
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 钱包id
	 * @apiParam {String} currentPage 当前页码
	 * @apiParam {String} pageSize 每页显示多少条数据
	 * @apiSampleRequest /walletNew/queryExpressFeeRecord
	 * @apiSuccess {String} items 每天账单条目（先解析itemsName，然后遍历取得日期，根据日期取得对应items中的列表，与钱包账单一样）
	 * @apiSuccess {String} itemsName 日期
	 * @apiSuccess {String} tradeCount 今天共多少笔交易
	 * @apiSuccess {String} tradeMoney 今天总共交易金额
	 * @apiSuccess {String} createTime 创建时间
	 * @apiSuccess {String} money 交易金额
	 * @apiSuccess {String} payways 交易方式：0 钱包   2 微信 
	 * @apiSuccessExample Success-Response:
	 * 		***  （先解析itemsName，然后遍历取得日期，根据日期取得对应items中的列表，与钱包账单一样） ****
	 *  {
   			"items": {
		        "2016-05-18": [
		            {
		                "createTime": "15:24",
		                "money": "0.01",
		                "payways": "2"
		            }
		        ],
		        "2016-11-22": [
		            {
		                "createTime": "17:14",
		                "money": "0.10",
		                "payways": "2"
		            },
		            {
		                "createTime": "17:09",
		                "money": "0.10",
		                "payways": "2"
		            }
		        ],
		        "今天": [
		            {
		                "createTime": "14:45",
		                "money": "0.01",
		                "payways": "2"
		            }
		        ]
	    	},
		    "itemsName": [
		        "今天",
		        "2016-11-22",
		        "2016-05-18"
		    ],
		    "todayCount": {
		        "tradeCount": 1,
		        "tradeMoney": "0.01"
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
    @RequestMapping(value = "/queryExpressFeeRecord", method = { RequestMethod.POST, RequestMethod.GET })
    public String queryExpressFeeRecord(String accountId,String currentPage,String pageSize){
		if(PubMethod.isEmpty(accountId)){
    		return paramsFailure("net.okdi.apiV3.controller.WalletNewController.queryExpressFeeRecord.001", "accountId不能为空");
    	}
    	if(PubMethod.isEmpty(currentPage)){ 
    		return paramsFailure("net.okdi.apiV3.controller.WalletNewController.queryExpressFeeRecord.002", "currentPage不能为空");
    	}
    	if(PubMethod.isEmpty(pageSize)){ 
    		return paramsFailure("net.okdi.apiV3.controller.WalletNewController.queryExpressFeeRecord.002", "pageSize不能为空");
    	}
    	try {
    		return this.walletNewService.queryExpressFeeRecord(accountId,currentPage,pageSize);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonFailure(e);
    	}
    }
}
