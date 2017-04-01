package net.okdi.apiV2.controller;

import java.util.Map;

import net.okdi.apiV2.service.VlinkCallService;
import net.okdi.core.base.BaseController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @author jingguoqiang
 * @desc  vlink语音短信
 */
@Controller
@RequestMapping("/vlinkCall")
public class VlinkCallController extends BaseController{
	
	Logger logger = Logger.getLogger(VlinkCallController.class);
	
    @Autowired
	VlinkCallService vlinkCallService;
	
    /**
	 * @api {post} /vlinkCall/sendSmsCode 发送手机语音验证码功能
	 * @apiPermission user
	 * @apiDescription 查询站点或营业分部下的人员列表 kai.yang
 	 * @apiparam {String} phone 发送手机号
 	 * @apiparam {String} type 发送语音验证码找回密码 findVoice 修改密码发送语音验证码 updateVoice 注册发送语音验证码
 	 * payVoice 设置支付密码发送语音验证码  replaceVoice替换手机号发送语音验证码 findPayVoice找回支付密码发送语音验证码
 	 * @apiparam {String} verifyCode 验证码
 	 * @apiGroup 免费短信
 	 * @apiSampleRequest /vlinkCall/sendSmsCode
 	 * @apiSuccess {String} result 0,1,2,3,4
 	 * @apiError {String} result false
 	 * @apiSuccessExample {json} Success-Response:
 	 *     HTTP/1.1 200 OK
 	 * {"data":{"result":"1"},"success":true}
 	 * @apiErrorExample {json} Error-Response:
 	 *     HTTP/1.1 200 OK
 	 *  {"errCode":1,"errSubCode":"","message":"获取验证码次数过于频繁，请稍后再试！","success":false}
 	 *  {"errCode":1,"errSubCode":"","message":"获取验证码超过今日最大次数限制，请明天再试！","success":false}
 	 * @apiVersion 0.2.0
 	 */
    @ResponseBody
	@RequestMapping(value = "/sendSmsCode", method = { RequestMethod.POST })
	public String sendSmsCode(String phone,String type,String verifyCode) {
		Map<String, Object> map = vlinkCallService.sendSmsCode(phone, type,verifyCode);
		return JSON.toJSONString(map);
	}
	
}
