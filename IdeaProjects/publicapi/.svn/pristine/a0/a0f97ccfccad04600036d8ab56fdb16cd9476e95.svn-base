package net.okdi.mob.controller;

import java.util.Map;

import net.okdi.core.base.BaseController;
import net.okdi.mob.service.RegisterCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
/**
 * 基本的注册登录接口，包括手机号码验证，发送短信，短信验证码校验，修改密码等
 * @Description: 该类是手机端的注册登录相关的基本类
 * @author
 * @date 2014-10-18下午1:40:13
 */
@Controller
@RequestMapping("/newRegisterCode")
public class RegisterCodeController extends BaseController {
	@Autowired
	private RegisterCodeService registerCodeService;
	

	/**
	 * @author 贺海峰
	 * @api {post} /newRegisterCode/newSendSmsCode 获取验证码-贺海峰
	 * @apiVersion 0.2.0
	 * @apiDescription 获取验证码-贺海峰
	 * @apiGroup 群发通知
	 * @apiParam {String} phone 手机号
	 * @apiParam {String} type 类型 findpwd 找回密码 reg  注册  modifyPh 修改电话
	 * @apiParam {String} verifyCode 图形验证码
	 * @apiParam {String} codeFilter 传0 或者 空, 0 是更换手机号用到的(好递快递员新加的需求),其他传空(不需传值)
	 * @apiSampleRequest /newRegisterCode/newSendSmsCode
	 * @apiSuccess {String} SendTrue  发送成功
	 * @apiSuccessExample Success-Response:
		{
		    "data": [
		        {
					"跟旧接口一样"
		        }
		    ],
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
	@RequestMapping(value = "/newSendSmsCode", method = { RequestMethod.POST, RequestMethod.GET })
	public String newSendSmsCode(String phone,String type,String verifyCode,String version, String codeFilter) {
		Map<String, Object> map = registerCodeService.newSendSmsCode(phone, type,verifyCode,version,codeFilter);
		return JSON.toJSONString(map);
	}
	
}