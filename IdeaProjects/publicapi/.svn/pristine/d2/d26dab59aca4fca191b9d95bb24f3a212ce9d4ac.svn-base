package net.okdi.apiV4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.apiV4.service.SmsTemplateService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

@Controller("smsTemplateController")
@RequestMapping("/smsTemplate")
public class SmsTemplateController extends BaseController{

	@Autowired
	private SmsTemplateService smsTemplateService;


	/**	 *
	 * @api {post} /smsTemplate/querySmsTemplateContent 短信模板查询
	 * @apiPermission user
	 * @apiDescription  短信模板 jiong.zheng
	 * @apiparam {Long} memberId 人员memberId(不能为空)
	 * @apiGroup 短信模板
	 * @apiSampleRequest /smsTemplate/querySmsTemplateContent
	 * @apiSuccess {Long} id: 主键id
	 * @apiSuccess {Long} memberId: 人员memberId
	 * @apiSuccess {String} templateContent: 模板内容
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 		{
		    "data": [
		        {
		            "createTime": 1472456820887,
		            "id": 232161179738112,
		            "memberId": 212192318504960,
		            "name": "zj",
		            "templateContent": "beijing222",
		            "updateTime": 1472461547617
		        },
		        {
		            "createTime": 1472461615136,
		            "id": 232171239776256,
		            "memberId": 212192318504960,
		            "name": "gaga",
		            "templateContent": "11111",
		            "updateTime": 1472461615136
		        }
		    ],
		    "success": true
		}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/querySmsTemplateContent", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySmsTemplateContent(Long memberId){

		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!");
		}
		String result = smsTemplateService.querySmsTemplateContent(memberId);
		return result;
	}

	/**	 *
	 * @api {post} /smsTemplate/saveSmsTemplateContent 短信模板保存
	 * @apiPermission user
	 * @apiDescription  短信模板 jiong.zheng
	 * @apiparam {Long} memberId 人员memberId(不能为空)
	 * @apiparam {String} content 模板内容(不能为空)
	 * @apiparam {String} name 实名认证名称
	 * @apiparam {Long} phone 快递员手机号
	 * @apiGroup 短信模板
	 * @apiSampleRequest /smsTemplate/saveSmsTemplateContent
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 		{"success":true}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSmsTemplateContent", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveSmsTemplateContent(Long memberId, String content, String name, Long phone){

		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!");
		}
		if(PubMethod.isEmpty(content)){
			return paramsFailure("002", "content 不能为空!");
		}
		String result = smsTemplateService.saveSmsTemplateContent(memberId, content, name, phone);
		return result;
	}
	/**	 *
	 * @api {post} /smsTemplate/updateSmsTemplateContent 短信模板修改
	 * @apiPermission user
	 * @apiDescription  短信模板 jiong.zheng
	 * @apiparam {Long} id 主键id(不能为空)
	 * @apiparam {Long} memberId 人员memberId(不能为空)
	 * @apiparam {String} content 模板内容(不能为空)
	 * @apiGroup 短信模板
	 * @apiSampleRequest /smsTemplate/updateSmsTemplateContent
	 * @apiSuccess {String} data: 1 更新成功,0 更新失败
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 		{"data":"1","success":true}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSmsTemplateContent", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateSmsTemplateContent(Long id, Long memberId, String content){

		if(PubMethod.isEmpty(id)){
			return paramsFailure("001", "id 不能为空!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!");
		}
		String result = smsTemplateService.updateSmsTemplateContent(id, memberId, content);
		System.out.println("result: "+result);
		return result;
	}

	/**	 *
	 * @api {post} /smsTemplate/deleteSmsTemplateContent 短信模板删除
	 * @apiPermission user
	 * @apiDescription  短信模板 jiong.zheng
	 * @apiparam {Long} id 主键id(不能为空)
	 * @apiGroup 短信模板
	 * @apiSampleRequest /smsTemplate/deleteSmsTemplateContent
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 		{"success":true}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteSmsTemplateContent", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteSmsTemplateContent(Long id){

		if(PubMethod.isEmpty(id)){
			return paramsFailure("001", "id 不能为空!");
		}
		String result = smsTemplateService.deleteSmsTemplateContent(id);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/killToken", method = { RequestMethod.POST, RequestMethod.GET })
	public String killToken(String token){

		System.out.println("token:"+token);
		try {
			String result =  smsTemplateService.killToken(token);
			return result;

		} catch (Exception e) {
		    return paramsFailure("001", "禁用token失败");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/dealTemplate", method = { RequestMethod.POST, RequestMethod.GET })
	public String dealTemplate(Long memberId){

		System.out.println("memberId:"+memberId);
		try {
			String result =  smsTemplateService.dealTemplate(memberId);
			return result;

		} catch (Exception e) {
		    return paramsFailure("001", "处理模板数据失败");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/dealTemplatePhone", method = { RequestMethod.POST, RequestMethod.GET })
	public String dealTemplatePhone(){

		try {
			String result =  smsTemplateService.dealTemplatePhone();
			return result;

		} catch (Exception e) {
			return paramsFailure("001", "处理模板数据失败");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/isBelongBlackAndWrite", method = {RequestMethod.POST, RequestMethod.GET})
	public String isBelongBlackAndWrite(String content){
		try {
			return smsTemplateService.isBelongBlackAndWrite(content);
		} catch (Exception e) {
			return paramsFailure("001", "验证黑白名单失败");
		}
	}


}
