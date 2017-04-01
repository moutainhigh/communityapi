package net.okdi.apiV1.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import net.okdi.apiV1.service.LoginService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.handleMessage.MessageSenderRabbit;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.GenMD5;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.classfile.ConstantPool;

@Controller
@RequestMapping("/okdiLogin")
public class LoginController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MessageSenderRabbit  messageSenderRabbit;
	
	@Autowired
	private  ConstPool constPool; 
	
	/**
	 * @author 贺海峰
	 * @api {post} /okdiLogin/okdiLoginExpress 快递员登录
	 * @apiVersion 0.2.0
	 * @apiDescription 快递员登录 -贺海峰
	 * @apiGroup 快递注册
	 * @apiParam {String} userName 手机号
	 * @apiParam {String} channelNo 01
	 * @apiParam {String} version 版本号
	 * @apiParam {String} deviceToken 设备号
	 * @apiParam {String} password 密码
	 * @apiParam {String} deviceType android|ios
	 * @apiSampleRequest /okdiLogin/okdiLoginExpress
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compStatus  站点状态 -1未提交 0待审核 1审核通过 2审核拒绝
	 * @apiSuccess {String} expressFlag  快递认证 -1未提交 0待审核 1审核通过 2审核拒绝
	 * @apiSuccess {String} relationFlag  归属认证 -1未提交 0待审核 1审核通过 2审核拒绝
	 * @apiSuccess {String} verifyFlag  身份认证 -1未提交 0待审核 1审核通过 2审核拒绝
	 * @apiSuccess {String} roleId  邀请过的别人的角色  1站长 2店长  -1 谁也没邀请
	 * @apiSuccess {String} isHaveNet  邀请站长的时候是否选快递公司了 0没选 1选了
	 * @apiSuccess {String} memberId  登录人id
	 * @apiSuccess {String} headImgUrl  登录人头像url
	 * @apiSuccess {String} isInvitaRegister  是否邀请别人注册(-1代表没有邀请过别人 0代表邀请过别人但是没有来注册 1代表邀请过别人,别人也来注册了)
	 * @apiSuccess {String} invitaRegisterPhone  邀请的那个人的手机号
	 * @apiSuccess {String} memberName  快递员姓名
	 * @apiSuccess {String} isVirtualuser  是否是虚拟站点下的快递员 0否1是
	 * @apiSuccess {String} accountId  钱包账号id
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "compId": 3435125,
		        "compStatus": 2,
		        "expressFlag": -1,
		        "headImgUrl": "http://cas.okdit.net/nfs_data/mob/head/176879518924800.jpg",
		        "isInvitaRegister": -1,
		        "memberId": 889,
		        "relationFlag": 2,
		        "roleId": 0,
		        "verifyFlag": 1
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
	@RequestMapping(value = "/okdiLoginExpress", method = { RequestMethod.POST, RequestMethod.GET })
	public String okdiLoginExpress(String channelNo,String userName,String password,String deviceType,String deviceToken,
			String version,HttpServletResponse response,Integer source,HttpServletRequest request){
		if( PubMethod.isEmpty(userName) || PubMethod.isEmpty(password)){
			return paramsFailure();
		}
		
		String regip= getRemortIP(request);
		String longResult = loginService.longResult(userName,password,source);
		try {
			if(PubMethod.isEmpty(longResult)||PubMethod.isEmpty(JSON.parseObject(longResult).getJSONObject("data"))) return jsonFailure(new ServiceException("请输入正确密码"));
			String memberId =  JSON.parseObject(longResult).getJSONObject("data").get("memberId").toString();
			Map<String,Object> parMap = new HashMap<String,Object>();
			parMap.put("channelNo", channelNo);
			parMap.put("userName", userName);
			parMap.put("password", password);
			parMap.put("deviceType", deviceType);
			parMap.put("deviceToken", deviceToken);
			parMap.put("version", version);
			parMap.put("source", source);
			parMap.put("memberId", memberId);
			parMap.put("regIp", regip);
			messageSenderRabbit.setRoutingKey(constPool.getLoginAuxiliaryKey());
			messageSenderRabbit.sendDataToQueue(JSON.toJSONString(parMap));
		} catch (Exception e) {
			return jsonFailure(e);
		}

		Enumeration myEnumeration = request.getHeaderNames();
		String headMobile = "";
		String headVersion = "";
		while (myEnumeration.hasMoreElements()) {
			Object element = myEnumeration.nextElement();
			if ("mobile".equals(element)) {
				headMobile = request.getHeader(element.toString());
			} else if ("version".equals(element)) {
				headVersion = request.getHeader(element.toString());
			}
		}
		String key = getKey(headMobile, headVersion);
		JSONObject jsonObject = JSON.parseObject(longResult);
		Object success = jsonObject.get("success");
		if ("true".equals(success) || (boolean) success) {
			jsonObject.put("key", key);
		}
		return jsonObject.toString();
	}

	private String getKey(String headMobile, String version) {
		logger.info("登陆接口, 接收到的请求头参数: headMobile:{} version:{}", headMobile, version);
		String mobileMD5 = GenMD5.generateMd5(headMobile);
		DesEncrypt des = new DesEncrypt();
		des.getKey("okdi"); //生成密匙
		return des.getEncString(mobileMD5 + version);
	}

	public String getRemortIP(HttpServletRequest request) {
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		//System.out.println(ip);
		return ip;
	}

}
