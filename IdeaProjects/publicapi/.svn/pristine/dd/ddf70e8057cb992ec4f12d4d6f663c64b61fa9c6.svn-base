package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import net.okdi.apiV1.service.ExpressUserService;
import net.okdi.core.base.BaseService_jdw;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.PassportHttpClient;
import net.okdi.core.passport.ShortLinkHttpClient;
import net.okdi.core.util.Base64;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.CommonService;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.RegisterService;

@Service("registerService")
public class RegisterServiceImpl extends BaseService_jdw implements RegisterService {
	@Value("${passPortUrl}")
	private String ucenterUrl;
	@Autowired
	private ConstPool constPool; 
	
	@Autowired
	private PassportHttpClient passportHttpClient;
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ExpressUserService expressUserService;
	
	//private static IsThisPhoneLoginHttpClient isThisPhoneLoginHttpClient = new IsThisPhoneLoginHttpClient();
	
//	@Autowired
//	private MobMemberLoginService mobMemberLoginService;
//
	@Autowired
	private ExpGatewayService expGatewayService;
	private DesEncrypt des = new DesEncrypt();//加解密的算法类
	
	@Override
	public Map<String, Object> validate(String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(phone)) {
			map.put("mobile", phone);
		} else {
			throw new ServiceException("你的电话号码为空");
		}
		try {
			Map m = passportHttpClient.doPassSendObj("service/validate", map);
			System.out.println(m);
			return m;
		} catch (Exception e) {
			throw new ServiceException("连接通行证系统错误");
		}
	}
	
	@Override
	public Map<String, Object> sendSmsCode(String phone,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(phone)) {
			map.put("moblie", phone);
			if("findpwd".equals(type)){
				map.put("type", "findpwd");
			}else if("reg".equals(type)){
				map.put("type", "reg");
			}else{
				throw new ServiceException("你的验证短信码类型不符合规范为空");
			}
		} else {
			throw new ServiceException("你的电话号码为空");
		}
		try {
			Map result = passportHttpClient.doPassSendObj("service/sendSmsCode", map);
			if(result==null){
				result = new HashMap<String,Object>();
				result.put("message", "调用通信 返回值通信失败");
				result.put("code","");
			}
			return result;
		} catch (Exception e) {
			throw new ServiceException("与通行证通信失败");
		}
	}

	@Override
	public Map<String, Object> validateVerify(String phone, String smsCode,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(phone) && !PubMethod.isEmpty(smsCode)) {
			map.put("verifyAccount", phone);
			map.put("verifyCode", smsCode);
			if("findpwd".equals(type)){
				map.put("type", "findpwd");
			}else if("reg".equals(type)){
				map.put("type", "reg");
			}else if("modifyPh".equals(type)){
				map.put("type", "modifyPh");
			}else{
				throw new ServiceException("你的验证短信码类型不符合规范为空");
			}
			System.out.println(map);
		} else {
			throw new ServiceException("你的电话号码或者短信验证码为空");
		}
		try {
			Map result = passportHttpClient.doPassSendObj("service/validateVerify", map );
			return result;
		} catch (Exception e) {
			throw new ServiceException("与通行证通信失败");
		}
	}

	@Override
	public Map<String, Object> register(String weNumber,String weName,String userName, String password, String source,String deviceType,String nickName) {
		System.out.println("$$$$$$$$$$"+userName+","+weNumber+","+weName+","+password+","+source+","+deviceType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weNumber", weNumber);
		map.put("weName", weName);
		if (!PubMethod.isEmpty(userName) && !PubMethod.isEmpty(password)
				&& !PubMethod.isEmpty(source)) {
			map.put("mobile", userName);
			map.put("password",  deBase64Pwd(password));
			map.put("source", source);
			if("2".equals(source)){
				if(deviceType == null){
					deviceType = "iphone";
				}
			}
			map.put("deviceType", deviceType);
		} else {
			throw new ServiceException("你的电话号码或者密码或者来源为空");
		}
		Map result= null;
		try {
			System.out.println("*********************");
			result = passportHttpClient.doPassSendObj("service/register", map);
			if ("null".equals(result.get("memberId")) || result.get("memberId")== null || "".equals(result.get("memberId"))) {
				throw new ServiceException("通行证系统注册失败");
			}else{
				//添加昵称
				if(!PubMethod.isEmpty(nickName)){
				  this.expressUserService.completeMemberInfo(result.get("memberId").toString(), nickName, null, null);
				}
			}
		} catch (Exception e) {
			throw new ServiceException("连接通行证系统错误");
		}
			//TODO:这里需要进行本地库表的插入操作，重新调用站点系统
			//newRegisterService.register(memberId, userName, password, type,null);
        //调用ucenter 查询当前手机号是否被邀请过，如果邀请过推送通知
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("mobile", userName);
            String ucenterResult = Post(constPool.getPassPortUrl() + "service/notifyCourier", params);
            if (!PubMethod.isEmpty(ucenterResult)) {
                JSONObject jsonObject = JSON.parseObject(ucenterResult);
                Long memberId = jsonObject.getLong("memberId");
                String content = jsonObject.getString("content");
                Map<String, Object> map1 = new HashMap<>();
                map1.put("memberId", memberId);
                map1.put("content", content);
                Post(constPool.getOpenApiUrl() + "notice/notifyCourier", map1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
	}
	
	
	@Override
	public Map changepwd(String memberId,String oldPwd ,String newPwd){
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(memberId)&&!PubMethod.isEmpty(newPwd)) {
			map.put("memberId", memberId);
			map.put("oldPassWords", deBase64Pwd(oldPwd));
			map.put("loginPwd",  deBase64Pwd(newPwd));
		} else {
			throw new ServiceException("你的密码或用户Id为空");
		}
		try {
			Map result = passportHttpClient.doPassSendObj("user/changepwd", map);
			return result;
		} catch (Exception e) {
			throw new ServiceException("与通行证通信失败");
		}
	}
	
	@Override
	public Map<String,Object> getBackPwd(String memberId,String password){
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(memberId) && !PubMethod.isEmpty(password)) {
			
			map.put("memberId", des.convertPwd(memberId,"ENC"));
			map.put("loginPwd", des.convertPwd(deBase64Pwd(password),"ENC"));
		} else {
			throw new ServiceException("你的电话号码或者新密码为空");
		}
		try {
			Map result = passportHttpClient.doPassSendObj("service/findpwd/modifypwd", map);
			return result;
		} catch (Exception e) {
			throw new ServiceException("连接通行证系统错误");
		}
	}
	
	/**
	 * 做一个base64的简单解密的方法,这个主要是解析前台传过来的密码文本
	 * @throws Base64DecodingException 
	 */
	private String deBase64Pwd(String password){
		try{
			return new String(Base64.decode(password.getBytes()));
		}catch(Exception e){
			throw new ServiceException("密码: "+password+" 反向解析错误");
		}
	}

	/**
	 * 登录功能
	 * @Method: login 
	 * @Description: 登录功能
	 * @param channelNo		01:接单王，02：个人端
	 * @param userName		手机号
	 * @param password		密码
	 * @param deviceType	设备类型 YN_ANDRIOD
	 * @param deviceToken	设备token(百度的，暂时别为空)
	 * @param version		版本
	 * @param address		地址可为空
	 * @return 
	 * @see net.okdi.mob.service.RegisterService#login(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * @since jdk1.6
	*/
	@Override
	public Map<String, Object> login(String channelNo, String userName, String password, String deviceType, 
			String deviceToken,String version,String address,Integer source) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(userName) && !PubMethod.isEmpty(password)) {
			map.put("mobile", userName);
			map.put("password", deBase64Pwd(password));
		} else {
			throw new ServiceException("你的电话号码或密码为空");
		}
		map.put("source", source);
		Map result = null;
		try {
			//{"memberId":"3111399625520007","success":true}
			result = passportHttpClient.doPassSendObj("user/mobLogin", map);
		} catch (Exception e) {
			throw new ServiceException("连接通行证系统错误");
		}
		if(result.get("success")==null||!"true".equals(result.get("success").toString())){
			return result;
		}
		String str = null;
		Map resultMap = null;
		for(int i = 0 ; i < 5; i++){
			String memberId = result.get("memberId").toString();
			Map<String,String> phoneMap = new HashMap<String,String>();
			phoneMap.put("channelNo", channelNo);
			phoneMap.put("memberId", memberId);
			phoneMap.put("deviceType", deviceType);
			phoneMap.put("deviceToken", deviceToken);
			phoneMap.put("version", version);
			//m.put("loginIn", "success");
			resultMap = openApiHttpClient.doPassSendObj("mobPush/phoneLoginIn", phoneMap);
			System.out.println("（phoneLoginIn）login方法--net.okdi.mob.service.impl.RegisterServiceImpl.login------"+i);
			if("success".equals(resultMap.get("loginIn")))
				break;
		}
		//mobMemberLoginService.saveOrUpdateMobMemberLogin(channelNo, Long.parseLong(memberId), deviceType, deviceToken, version, address);
		return result;
	}
	
	@Override
	public Map<String, Object> login_jdw(String userName, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(userName) && !PubMethod.isEmpty(password)) {
			map.put("mobile", userName);
			map.put("password", deBase64Pwd(password));
		} else {
			throw new ServiceException("你的电话号码或密码为空");
		}
		Map result = null;
		try {
			//{"memberId":"3111399625520007","success":true}
			result = passportHttpClient.doPassSendObj("user/mobLogin", map);
		} catch (Exception e) {
			throw new ServiceException("连接通行证系统错误");
		}
		return result;
	}
	@Override
	public Map<String, Object> loginOut(Long memberId,String channelNo) {
		Map result = new HashMap();
		//result = isThisPhoneLoginHttpClient.doPassSendObj("phonelogin/phoneLoginOut", map);
//		MobMemberLogin mobMemberLogin = new MobMemberLogin();
//		mobMemberLogin.setChannelNo(channelNo);
//		mobMemberLogin.setChannelId(memberId);
		Map phoneMap = new HashMap();
		phoneMap.put("memberId", memberId);
		phoneMap.put("channelNo", channelNo);
		Map<String,Object> map=openApiHttpClient.doPassSendObj("mobPush/phoneLoginOut", phoneMap);
		if(PubMethod.isEmpty(map) || map.get("loginOut") == null || !"success".equals(map.get("loginOut").toString())){
			result.put("success", "false");
			return result;
		}
		//mobMemberLoginService.deleteMobMemberLogin(mobMemberLogin);
		result.put("success", "true");
		result.put("memberId", memberId);
		return result;
	}
	
	@Override
	public Map<String, Object> pushLogin(String channelNo, String version, String deviceType, String deviceToken) {
		Map<String, Object> map = new HashMap<>();
		map.put("channelNo", channelNo);
		map.put("memberId", 0);
		map.put("deviceType", deviceType);
		map.put("deviceToken", deviceToken);
		map.put("version", version);
		map.put("address", "");
		return openApiHttpClient.doPassSendObj("mobPush/phoneLoginIn", map);
	}
	
	@Override
	public String userUpdate(Long memberId, String telephone, String idNum,
			String username,String realname ,String addressName, String address,
			String countryId, String addressId, String zip,MultipartFile[] myfiles) {
			Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
			paraMeterMap.put("memberId",memberId);
			paraMeterMap.put("telephone",telephone);
			paraMeterMap.put("idNum",idNum);
			//暂时不加,如果加上默认的username将被致空,调用接口mapper中没有为空判断
//			paraMeterMap.put("username",username);
			paraMeterMap.put("realname",realname);
			paraMeterMap.put("addressName",addressName);
			paraMeterMap.put("address",address);
			paraMeterMap.put("countryId",countryId);
			paraMeterMap.put("addressId",addressId);
			paraMeterMap.put("zip",zip);
			if(myfiles!=null){
				commonService.uploadPic("head",memberId,myfiles);
			}
		return passportHttpClient.doPassSendStr("user/userUpdate",paraMeterMap);
	}
	
	@Override
	public String getMemberMsg(Long memberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		DesEncrypt des = new DesEncrypt();//加解密的算法类
		String memberInfo = passportHttpClient.doPassSendStr("service/getMemberMsg",paraMeterMap);
		String memberStr = des.convertPwd(memberInfo, "ABC");
		String str = memberStr.substring(1,memberStr.length()-1);
		
		JSONObject jso = JSON.parseObject(str);
		jso.put("imgUrl", constPool.getReadPath()+constPool.getHead()+memberId+".jpg");
		String result = JSON.toJSONString(jso);
		return result;
	}
	@Override
	public String bindClient(String version, String channelNo, String deviceToken, String channelId, String deviceType) {
		String memberId = channelId;
		Map<String,String> phoneMap = new HashMap<String,String>();
		phoneMap.put("channelNo", channelNo);
		phoneMap.put("memberId", memberId);
		phoneMap.put("deviceType", deviceType);
		phoneMap.put("deviceToken", deviceToken);
		phoneMap.put("version", version);
		
		openApiHttpClient.doPassSendObj("mobPush/phoneLoginIn", phoneMap);
		System.out.println("（phoneLoginIn）bindClient方法--net.okdi.mob.service.impl.RegisterServiceImpl.bindClient");
		//mobMemberLoginService.saveOrUpdateMobMemberLogin(channelNo, Long.parseLong(memberId), deviceType, deviceToken, version, "");
		Map m = new HashMap();
		m.put("success", "true");
		return JSON.toJSONString(m);
	}

//	@Override
//	public String InsertCoordinateAndToken(Long memberId, String channelNo,
//			String lng, String lat, HttpServletResponse response,
//			String deviceType, String deviceToken, String version,
//			String address) {
//		bindClient(version, channelNo, deviceToken, memberId+"", deviceType);
//		
//		expGatewayService.saveOnLineMember(ParMap);
//		return null;
//	}

	@Override
	public String InsertCoordinateAndToken(Long memberId,String channelNo, String lng,
			String lat, HttpServletResponse response, String deviceType,
			String deviceToken, String version, String address) {
			Map<String,Object> pareMap = new  HashMap<String,Object>();
			pareMap.put("memberId", memberId);
			pareMap.put("channelNo", channelNo);
			pareMap.put("lng", lng);
			pareMap.put("lat",lat);
			pareMap.put("deviceType",deviceType);
			pareMap.put("deviceToken", deviceToken);
			pareMap.put("version", version);
			pareMap.put("address", address);
//			expGatewayService.saveOnLineMember(pareMap);
			openApiHttpClient.doPassSendStr("mobPush/phoneLoginIn",pareMap);
			System.out.println("请求openapi方法--mobPush/phoneLoginIn结束！！！！");
			return jsonSuccess(null);
	}
	@Override
	public String getRegisterShortUrlAmssy(String shopId){
		ShortLinkHttpClient linkClient = new ShortLinkHttpClient();	
		String registerHttpUrl = constPool.getRegisterHttpUrl();//访问网站页面
		String shortLinkUrl = constPool.getShortlinkHttpUrl();//获取短链接KEY
		String longLinkUrl = constPool.getLonglinkHttpUrl();//短网址调转到长网址
		String params = "shopId=" + shopId;
		Map<String, String> mapLink = new HashMap<String, String>();
		mapLink.put("sys", "1");
		mapLink.put("text", registerHttpUrl + "?" + params);
		String shortKey = linkClient.doPost(shortLinkUrl, mapLink);
		return longLinkUrl + shortKey;
	}

	@Override
	public Map<String, Object> loginSingleMember(String userName,
			String password, Integer source) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(userName) && !PubMethod.isEmpty(password)) {
			map.put("mobile", userName);
			map.put("password", deBase64Pwd(password));
		} else {
			throw new ServiceException("你的电话号码或密码为空");
		}
		map.put("source", source);
		Map result = null;
		
		try {
			//{"memberId":"3111399625520007","success":true}
			result = passportHttpClient.doPassSendObj("user/mobLogin", map);
		} catch (Exception e) {
			throw new ServiceException("连接通行证系统错误");
		}
		if(result.get("success")==null||!"true".equals(result.get("success").toString())){
			throw new ServiceException("登录信息错误");
		}else{
			Map memberInfoMap = JSONObject.parseObject(this.getMemberMsg(Long.valueOf(result.get("memberId")+"")));
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("memberId", memberInfoMap.get("memberId"));
			resultMap.put("realname", memberInfoMap.get("realname"));
			return resultMap;
		}
	}

	/**
	 * 	保存Token
	 */
	@Override
	public void saveToken(Long memberId,String channelNo, String deviceType,
			String deviceToken, String version) {
		Map<String,String> phoneMap = new HashMap<String,String>();
		
		Map result = null;
		phoneMap.put("channelNo", channelNo);
		phoneMap.put("memberId", memberId+"");
		phoneMap.put("deviceType", deviceType);
		phoneMap.put("deviceToken", deviceToken);
		phoneMap.put("version", version);
		
		result = openApiHttpClient.doPassSendObj("mobPush/phoneLoginIn", phoneMap);
		System.out.println("（phoneLoginIn）saveToken方法--net.okdi.mob.service.impl.RegisterServiceImpl.saveToken");
		if(PubMethod.isEmpty(result) || result.get("loginIn")==null||!"success".equals(result.get("loginIn").toString())){
			System.out.println("*****************************请求结果："+result);
			throw new ServiceException("保存信息错误");
		}
	}
	
	/**
	 * 	修改姓名
	 */
	@Override
	public void updateName(Long memberId, String realname) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(memberId) && !PubMethod.isEmpty(realname)) {
			map.put("memberId", memberId);
			map.put("realname", realname);
		} else {
			throw new ServiceException("你的送派员id或真实姓名为空");
		}
		Map result = null;
		try {
			//{"memberId":"3111399625520007","success":true}
			result = passportHttpClient.doPassSendObj("/user/userUpdate", map);
		} catch (Exception e) {
			throw new ServiceException("连接通行证系统错误");
		}
		
		if(result.get("success")==null||!"true".equals(result.get("success").toString())){
			throw new ServiceException("修改信息错误");
		}
	}

	@Override
	public String updateaccountIdBymemberId(Integer x) {
      Map<String,Integer> map = new HashMap<String,Integer>();
      map.put("x", x);
      Map<String,Object> resultMap = passportHttpClient.doPassSendObj("service/updateaccountIdBymemberId", map);
//      passportHttpClient.
		return resultMap.toString();
	}

//	@Override
//	public String insertWeChat(String userName, String weNumber, String weName) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("userName", userName);
//		map.put("weNumber", weNumber);
//		map.put("weName", weName);
//		String result = null;
//		try {
//			result = openApiHttpClient.doPassSendStr("expressUser/insertWeChat", map);
//		} catch (Exception e) {
//			return PubMethod.sysErrorUS();
//		}
//		return result;
//	}
		
	
}