package net.okdi.mob.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.okdi.core.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import net.okdi.core.base.BaseController;
import net.okdi.core.exception.ServiceException;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.RegisterService;
import net.okdi.mob.vo.WechatBoundResult;

/**
 * 基本的注册登录接口，包括手机号码验证，发送短信，短信验证码校验，修改密码等
 * @Description: 该类是手机端的注册登录相关的基本类
 * @author
 * @date 2014-10-18下午1:40:13
 */
@Controller
@RequestMapping("/newRegister")
public class RegisterController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private RegisterService registerService;
	
	@Autowired
	ExpGatewayService expGatewayService;
	
	/**
	 * 验证手机号码是否使用
	 * @Method: validate
	 * @Description: TODO
	 * @param phone
	 * @Param isNeedKey 是否需要返回key "1"表示需要
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-5 下午5:31:23
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/validate", method = { RequestMethod.POST, RequestMethod.GET })
	public String validate(HttpServletRequest request, String phone, String isNeedKey) {
        logger.info("验证手机号是否登录: phone:{}, isNeedKey:{}", phone, isNeedKey);
		Map<String, Object> map = registerService.validate(phone);
		if ("1".equals(map.get("registered")) || !"1".equals(isNeedKey)) { //已经注册或不需要key
			return JSON.toJSONString(map);
		}
		Enumeration myEnumeration = request.getHeaderNames();
		String headMobile = "";
		String headVersion = "";
		while (myEnumeration.hasMoreElements()) {
			Object element = myEnumeration.nextElement();
			if ("version".equals(element)) {
				headVersion = request.getHeader(element.toString());
			} else if ("mobile".equals(element)) {
				headMobile = request.getHeader(element.toString());
			}
		}
		String key = getKey(headMobile, headVersion);
		map.put("key", key);
		return JSON.toJSONString(map);
	}
	private String getKey(String headMobile, String version) {
		String mobileMD5 = GenMD5.generateMd5(headMobile);
		DesEncrypt des = new DesEncrypt();
		des.getKey("okdi"); //生成密匙
		return des.getEncString(mobileMD5 + version);
	}
	/**
	 * 发送手机验证码功能
	 * @param phone 发送手机号
	 * @param type： findpwd找回密码 reg注册 默认reg
	 * @return {"data":{"result":"OverMaxLimited"},"success":true}
	 * 返回json数据{"result":"OverMaxLimited"} result:OverMaxLimited 超过当日限制发送次数 SendFalse发送短信失败 SendTrue发送成功
	 */
	@ResponseBody
	@RequestMapping(value = "/sendSmsCode", method = { RequestMethod.POST })
	public String sendSmsCode(String phone,String type) {
		Map<String, Object> map = registerService.sendSmsCode(phone, type);
		return JSON.toJSONString(map);
	}
	/**
	 * 
	 * @param smsCode 短信验证码
	 * @param type 验证短信码类型  findpwd找回密码 reg注册 默认reg
	 * @return {"pass":"no","success":true}pass false 不通过 true 通过 invalid 验证码已失效
	 */
	@ResponseBody
	@RequestMapping(value = "/validateVerify", method = { RequestMethod.POST, RequestMethod.GET })
	public String validateVerify(String phone, String smsCode,String type) {
		//Map<String,Object> testMap = new HashMap<String,Object>();
		//testMap.put("success","true");
		//testMap.put("pass","true");
		//return JSON.toJSONString(testMap);
		Map<String, Object> map = registerService.validateVerify(phone, smsCode, type);
		return JSON.toJSONString(map);
	}
	/**
     * @api {post} /newRegister/register 注册功能
     * @apiVersion 0.3.0
     * @apiDescription 注册功能
     * @apiGroup 我的页面(新需求)
     * @apiParam {String} weNumber 微信号
     * @apiParam {String} weName 微信名称
     * @apiParam {String} userName 手机号
     * @apiParam {String} password 密码
     * @apiParam {String} source 使用2：个人端， 3：接单王（具体平台来源类型，默认0系统注册，站点系统：1，个人端：2，接单王：3）
     * @apiParam {String} deviceType 设备类型（deviceType）（pc：电脑  android：安卓手机 iphone：苹果手机）
     * @apiSampleRequest /newRegister/register
     * @apiSuccess {String} memberId memberId
     * @apiSuccessExample Success-Response:
	 * 注册功能
	 * 注册，存储登录信息，功能
	 * 在通行证注册并保存信息：得到memberId	取消掉下面一步：存储本地登录数据（这一步由通行证来保存数据）
	 * @param userName 手机号
	 * @param password 密码
	 * @param source   使用2：个人端， 3：接单王（具体平台来源类型，默认0系统注册，站点系统：1，个人端：2，接单王：3）
	 * @param deviceType 设备类型（deviceType）（pc：电脑  android：安卓手机 iphone：苹果手机）
	 * @return {memberId:123321};失败返回{memberId:null}
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
	public String register(String weNumber, String weName,String userName, String password, String source,String deviceType,String nickName) {
		System.out.println("@@@@@@@@@"+userName+","+weNumber+","+weName+","+password+","+source+","+deviceType);
		Map<String, Object> map = registerService.register(weNumber, weName, userName, password, source, deviceType,nickName);
		return JSON.toJSONString(map);	
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateaccountIdBymemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateaccountIdBymemberId(Integer x) {
		String result = registerService.updateaccountIdBymemberId(x);
		return JSON.toJSONString(result);
	}
	
	//117972045979648
//	117972045979648
	/**
	 * 修改密码
	 * @param memberId 用户id
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return {"data":{"success":true},"success":true} data内success true修改成功false修改失败
	 */
	@ResponseBody
	@RequestMapping(value = "/changepwd", method = { RequestMethod.POST, RequestMethod.GET })
	public String changepwd(String memberId,String oldPwd ,String newPwd){
		Map<String, Object> map = registerService.changepwd(memberId, oldPwd, newPwd);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 找回密码（直接调用此接口可以修改该手机号的密码）
	 * 这里仅提供设置新密码的功能，前台的流程应该这样：输入手机号->*验证手机号是否存在*->发送短信验证码->正确->设置新密码
	 * @param memberId	成员memberId
	 * @param password 新密码 (这个后台写的时候注意下,算法加密)
	 * @return {"success":true/false}
	 */
	@ResponseBody
	@RequestMapping(value = "/getBackPwd", method = { RequestMethod.POST, RequestMethod.GET })
	public String getBackPwd(String memberId,String password){
		Map<String, Object> map = registerService.getBackPwd(memberId, password);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 登出
	 * @Method: loginOut 
	 * @Description:  
	 * @param memberId 个人的memberId
	 * @param channelNo  01接单王，02个人端
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-5 下午6:58:26
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/loginOut", method = { RequestMethod.POST, RequestMethod.GET })
	public String loginOut(Long memberId,String channelNo){
		Map<String, Object> map = registerService.loginOut(memberId,channelNo);
		return JSON.toJSONString(map);
	}
	
	@ResponseBody
	@RequestMapping(value = "/pushLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public String pushLogIn(String channelNo, String version, String deviceType, String deviceToken){
		Map<String, Object> map = registerService.pushLogin(channelNo, version, deviceType, deviceToken);
		return JSON.toJSONString(map);
	}
	
	
	/**
	 * cas.okdi.net/user/userUpdate
	 * @Method:userUpdate
	 * @Description: TODO
	 * @param   memberId用户ID（不能为空）
	 * @param telephone座机 
	 * @param idNum身份证 
	 * @param username姓名 
	 * @param realname真实姓名
	 * @param addressName地址 
	 * @param address详细地址 
	 * @param countryId四级地址ID 
	 * @param addressId 五级地址ID 
	 * @param zip邮编
	 * @param myfiles 图片
	 * @return {"success":true}
	 * @author xiangwei.liu
	 * @date 2014-11-10 上午9:35:54
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/userUpdate", method = { RequestMethod.POST, RequestMethod.GET })
	public String  userUpdate(Long memberId,String telephone,String idNum,String username, String realname,
							  String addressName,String address,String countryId,String addressId,String zip,@RequestParam(value = "myfiles", required = false)  MultipartFile[] myfiles) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		if(PubMethod.isEmpty(telephone)){
			return paramsFailure();
		}
		if(!VerifyUtil.isMobile(telephone) && !VerifyUtil.isTel(telephone)){
			return paramsFailure();
		}
		return registerService.userUpdate(memberId, telephone, idNum, username, realname, addressName, address, countryId, addressId, zip,myfiles);
	}
	
	/**
	 * 
	 * @Method:getMemberMsg
	 * @Description: TODO
	 * @param memberId用户ID（不能为空）
	 * @return [
    	{
        "address": "",
        "addressId": "",
        "addressName": "",
        "countryId": "15",
        "createTime": "2014-10-25 14:20:28",
        "dataPercent": "20",
        "email": "",
        "idNum": "152127198907280314",
        "imgUrl": "",
        "loginPwd": "Uhp+assOQic=",
        "memberId": "13753198095106048",
        "mobile": "13166663214",
        "modifyTime": "2014-11-10 12:47:43",
        "pwdLevel": "",
        "realname": "",
        "resource": "1",
        "telephone": "5693009",
        "userStatus": "0",
        "username": "",
        "zip": "100000"
    	}
			]
	 * @date 2014-11-10 上午9:35:54
	 * @author xiangwei.liu
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberMsg", method = { RequestMethod.POST, RequestMethod.GET })
	public String getMemberMsg(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		 return  registerService.getMemberMsg(memberId);
	}
	
	/**
	 * 登录功能，验证密码正确，则返回memerberId
	 * @param channelNo 02个人端，01接单王
	 * @param userName 用户名
	 * @param password 密码
	 * @param deviceType 设备类型
	 * @param deviceToken 设备标识
	 * @param version 版本号
	 * @param source 注册来源 (0：系统注册 1：站点系统 2：个人端 3：接单王4：好递超市 5:好递生活)
	 * @return {"data":{"memberId":1161393912325559,"relationFlag":0,"veriFlag":0},"success":true}
	 * 角色 1:收派员,2:后勤,3:站长
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public String login(String channelNo,String userName,String password,String deviceType,
			String deviceToken,String version,String address,Integer source){
		    Map<String, Object> map = registerService.login(channelNo,userName,password,deviceType,deviceToken,version,address,source);
		    return JSON.toJSONString(map);
	}
	/**
	 * 登录功能，验证密码正确，则返回memerberId
	 * @param channelNo 02个人端，01接单王
	 * @param userName 用户名
	 * @param password 密码
	 * @param deviceType 设备类型
	 * @param deviceToken 设备标识
	 * @param version 版本号
	 * @param source 注册来源 (0：系统注册 1：站点系统 2：个人端 3：接单王4：好递超市 5:好递生活)
	 * @return {"data":{"memberId":1161393912325559,"relationFlag":0,"veriFlag":0},"success":true}
	 */
	@ResponseBody
	@RequestMapping(value = "/login_jdw", method = { RequestMethod.POST })
	public String login_jdw(String channelNo,String userName,String password,String deviceType,String deviceToken,
			String version,HttpServletResponse response,Integer source){
		if( PubMethod.isEmpty(userName) || PubMethod.isEmpty(password)){
			return paramsFailure();
		}
		String  ResultStr = "";	
		//Map<String, Object> map = registerService.login_jdw(userName, password);
		Map<String, Object> map = registerService.login(channelNo,userName,password,deviceType,deviceToken,version,null,source);
		try {
			  ResultStr= longResult(map);
		} catch(Exception e){
			exp(response,new ServiceException("获取登录信息异常"));
			throw new ServiceException("获取登录信息异常");	
		}
		return ResultStr;
	}
	
	/**
	 * 插入地址坐标信息以及token
	 * @param memberId 人员Id
	 * @param channelNo 02个人端，01接单王
	 * @param deviceType 设备类型
	 * @param deviceToken 设备标识
	 * @param version 版本号
	 * @param lng 经度
	 * @param lat 纬度
	 * @return {"data":{"memberId":1161393912325559,"relationFlag":0,"veriFlag":0},"success":true}
	 * 角色 1:收派员,2:后勤,3:站长
	 */
	@ResponseBody
	@RequestMapping(value = "/InsertCoordinateAndToken", method = { RequestMethod.POST })
	public String InsertCoordinateAndToken(Long memberId,String channelNo,String lng,String lat,HttpServletResponse response
			,String deviceType,String deviceToken,String version,String address){ 
//		String version,String channelNo,String deviceToken,String channelId,String deviceType
		System.out.println("登录进入InsertCoordinateAndToken方法，参数：memberId="+memberId+",deviceType="+deviceType+",deviceToken="+deviceToken+"！！！！！！ ");
		if(PubMethod.isEmpty(memberId)){
			paramsFailure("openapi.ParcelInfoController.queryParcelList.001","人员Id为必填项");
		}
		if(PubMethod.isEmpty(channelNo)){
			paramsFailure("openapi.ParcelInfoController.queryParcelList.002","channelNo必填项");
		}
		if(PubMethod.isEmpty(deviceToken)){
			paramsFailure("openapi.ParcelInfoController.queryParcelList.003","deviceToken必填项");
		}
		if(PubMethod.isEmpty(lng) || PubMethod.isEmpty(lat)){
			paramsFailure("openapi.ParcelInfoController.queryParcelList.004","经纬度为必填项");
		}
		try {
			registerService.InsertCoordinateAndToken(memberId, channelNo, lng, lat, response, deviceType, deviceToken, version, address);
		} catch(Exception e){
			throw new ServiceException("插入设备号,地址信息,发生异常");	
		}
		return jsonSuccess(null);
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>给电商管家提供一个接口，查询手机号和账户号(目前手机号和账户号是一致的)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>ccs</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-6-17 下午2:44:16</dd>
	 * @param phone		根据手机号查询
	 * @return			
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getMIdAndAC", method = { RequestMethod.POST ,RequestMethod.GET})
	public String getMIdAndAC(String mobile) {
		Map<String, Object> map = registerService.validate(mobile);
		map.put("accountCard", map.get("memberId"));
		map.put("nickName", "");
		try{
			String str = registerService.getMemberMsg(Long.valueOf(map.get("memberId").toString()));
			//{"address":"脱光光","addressId":"","addressName":"安徽省亳州市利辛县","countryId":"11001045","createTime":"2014-09-19 15:33:38","dataPercent":"","email":"","idNum":"","identityStatus":0,"imgUrl":"http://publicapi.okdit.net/nfs_data/mob/head/1671411110875008.jpg","isDisabled":0,"loginPwd":"xm+rgEc75sg=","memberId":"1671411110875008","mobile":"13261658330","modifyTime":"2015-01-16 11:20:51","pwdLevel":"0","realname":"真实姓名","resource":"0","telephone":"13261658330","userStatus":"0","username":"","zip":""}
			String nickName = JSON.parseObject(str).get("realname").toString();
			if(PubMethod.isEmpty(nickName)){
				nickName="";
			}
			map.put("nickName", nickName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(map);
	}
	@ResponseBody
	@RequestMapping(value = "/getRegisterShortUrlAmssy", method = { RequestMethod.POST})
	public String getRegisterShortUrlAmssy(String shopId) {
		try{
			if(PubMethod.isEmpty(shopId)){
				paramsFailure("publicapi.RegisterController.getRegisterShortUrl.001","shopId参数非空异常");
			}
			return jsonSuccess(this.registerService.getRegisterShortUrlAmssy(shopId));
		}catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * 	登录功能，则返回memerberId,realname
	 * @param userName 用户名
	 * @param password 密码
	 * @param source 注册来源 (0：系统注册 1：站点系统 2：个人端 3：接单王4：好递超市 5:好递生活 6:抢送货)
	 * @return {"data":{"memberId":1161393912325559,"realname":美国对战,},"success":true}
	 */
	@ResponseBody
	@RequestMapping("/login_singleMember")
	public String loginSingleMember(String userName,String password){
		if( PubMethod.isEmpty(userName) || PubMethod.isEmpty(password)){
			return paramsFailure();
		}
		try {
			return jsonSuccess(registerService.loginSingleMember(userName,password,6));
		} catch(Exception e){
			return jsonFailure(e);	
		}
	}
	
	
	/**
	 * @api {post} /newRegister/saveToken 保存Token
	 * @apiPermission user
	 * @apiDescription 接口描述 保存Token	chunyang.tan
	 * @apiparam {Long} memberId 送派员id. 不登录时可传0
	 * @apiparam {String} channelNo 和登录时传的channelNo一样
	 * @apiparam {String} deviceType 设备类型   android IP/IE
	 * @apiparam {String} deviceToken 设备标识   
	 * @apiparam {String} version 版本号
	 * @apiGroup 保存Token
	 * @apiSampleRequest /newRegister/saveToken
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
     *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   result:{"errCode":1,"errSubCode":"","message":"java.lang.NumberFormatException: For input string: \"null\"","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("/saveToken")
	public String saveToken(Long memberId ,String channelNo,String deviceType,String deviceToken,
			String version){
		System.out.println("（phoneLoginIn）保存token--net.okdi.mob.controller.RegisterController.saveToken");
		if(PubMethod.isEmpty(channelNo) || PubMethod.isEmpty(deviceType) || PubMethod.isEmpty(version)){
			return paramsFailure();
		}
		try {
			registerService.saveToken(memberId,channelNo,deviceType,deviceToken,version);
			return jsonSuccess(null);
		} catch(Exception e){
			return jsonFailure(e);	
		}
	}
	
	/**
	 * @api {post} /newRegister/updateName 修改送派员真实姓名
	 * @apiPermission user
	 * @apiDescription 接口描述 修改派送员的显示名称	chunyang.tan
	 * @apiparam {Long} memberId 送派员id
	 * @apiparam {String} realname 真实姓名
	 * @apiGroup 社区配送
	 * @apiSampleRequest /newRegister/updateName
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
     *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":1,"errSubCode":"","message":"你的送派员id或真实姓名为空","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("/updateName")
	public String updateName(Long memberId, String realname){
		if( PubMethod.isEmpty(memberId) || PubMethod.isEmpty(realname)){
			return paramsFailure();
		}
		try {
			registerService.updateName(memberId,realname);
			return jsonSuccess(null);
		} catch(Exception e){
			return jsonFailure(e);	
		}
	}
		
	
}