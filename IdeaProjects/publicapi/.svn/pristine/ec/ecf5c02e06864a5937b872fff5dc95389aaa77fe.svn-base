package net.okdi.apiV4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.apiV4.service.MsgTemplateService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

/**
 * 新版 模板管理
 * @author zhaohu
 * @date 2016年11月11日15:00:42
 */
@Controller
@RequestMapping("msgTemplate")
public class MsgTemplateController extends BaseController{
	
	@Autowired
	private MsgTemplateService msgTemplateService;
	
	
	/**
	 * @author zhaohu
	 * @api {post} /msgTemplate/queryTemplateList 查询模板列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询模板列表
	 * @apiGroup 新版模板管理
	 * @apiParam {String} memberId 快递员id
	 * @apiParam {Short} auditStatus 审核状态	0:待审核, 1:已通过, 2:不通过
	 * @apiSampleRequest /msgTemplate/queryTemplateList
	 * @apiSuccess {String} templateName  模板名字
	 * @apiSuccess {String} templateId  模板id
	 * @apiSuccess {String} templateContent  模板内容
	 * @apiSuccess {String} submitTime  提交时间
	 * @apiSuccess {String} remarks  审核失败原因
	 * @apiSuccess {Integer} count  列表数量
	 * @apiSuccessExample Success-Response:
	     {
		    "data": {
		        "count": 1,
		        "listData": [
		            {
		                "remarks": "",
		                "submitTime": "11-07 15:29",
		                "templateContent": "客户您好,您的快件在公司门口,请速到前台领取。",
		                "templateId": 232353582858240,
		                "templateName": ""
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
	@RequestMapping("queryTemplateList")
	public String queryTemplateList(String memberId,Short auditStatus) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.queryTemplateList.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(auditStatus)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.queryTemplateList.002", "auditStatus不能为空");
		}
		try {
			return msgTemplateService.queryTemplateList(memberId,auditStatus);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /msgTemplate/deleTemplate 删除模板
	 * @apiVersion 0.2.0
	 * @apiDescription 删除模板
	 * @apiGroup 新版模板管理
	 * @apiParam {String} templateId 模板id
	 * @apiSampleRequest /msgTemplate/deleTemplate
	 * @apiSuccess {Boolean} success  
	 * @apiSuccessExample Success-Response:
	     {
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
	@RequestMapping("deleTemplate")
	public String deleTemplate(String templateId) {
		if (PubMethod.isEmpty(templateId)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.deleTemplate.001", "templateId不能为空");
		}
		try {
			return msgTemplateService.deleTemplate(templateId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /msgTemplate/saveTemplate 保存模板
	 * @apiVersion 0.2.0
	 * @apiDescription 保存模板
	 * @apiGroup 新版模板管理
	 * @apiParam {String} version  版本号;版本号必须添加，目的为了新老版本的兼容，老版本为空，新版本不为空。
	 * @apiParam {String} templateName  模板名字
	 * @apiParam {String} templateContent  模板内容，如果插入编号就在其位置上拼上"#编号#"
	 * @apiParam {String} phone  快递员手机号
	 * @apiParam {String} memberId  人员id
	 * @apiSampleRequest /msgTemplate/saveTemplate
	 * @apiSuccess {List} data  此字段如果为空，则说明没敏感词；有值则是敏感词集合，前端进行标红并提示
	 * @apiSuccess {String} errorMsg  如果有这个字段则toast提示里面的内容
	 * @apiSuccess {String} auditStatus  短信模板的审核状态，根据这个字段进行页面跳转0:待审核, 1:已通过, 2:不通过, 3:系统通过
	 * @apiSuccessExample Success-Response:
	     {
		    "data":{}, 
		    "errorMsg":"审核通过",//如果系统审核通过，则返回这个字段
		    "success": true,
		    "auditStatus":3
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
	@RequestMapping(value = "/saveTemplate", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveTemplate(String templateName,String templateContent,String phone,String memberId) {
		if (PubMethod.isEmpty(templateName)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.saveTemplate.001", "模板名称不能为空");
		}
		if (PubMethod.isEmpty(templateContent)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.saveTemplate.002", "模板内容不能为空");
		}
		if (PubMethod.isEmpty(phone)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.saveTemplate.003", "用户手机号不能为空");
		}
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.saveTemplate.004", "memberId不能为空");
		}
		try {
			return msgTemplateService.saveTemplate(templateName,templateContent, phone, memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /msgTemplate/updateTemplate 修改模板
	 * @apiVersion 0.2.0
	 * @apiDescription 修改模板
	 * @apiGroup 新版模板管理
	 * @apiParam {String} templateName  模板名字
	 * @apiParam {String} templateContent  模板内容，如果插入编号就在其位置上拼上"#编号#"
	 * @apiParam {String} templateId  模板id
	 * @apiSampleRequest /msgTemplate/updateTemplate
	 * @apiSuccess {List} data  此字段如果为空，则说明没敏感词；有值则是敏感词集合，前端进行标红并提示
	 * @apiSuccess {String} errorMsg  如果有这个字段则toast提示里面的内容
	 * @apiSuccess{String} auditStatus  短信模板的审核状态，根据这个字段进行页面跳转0:待审核, 1:已通过, 2:不通过, 3:系统通过
	 * @apiSuccessExample Success-Response:
	     {
		    "data": [
		        "高利贷", 
		        "老虎机", 
		        "针刺案",
		        
		    ], 
		    "errorMsg":"通知内容不符合发送要求!",
		    "success": true,
		    "auditStatus":0
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
	@RequestMapping("updateTemplate")
	public String updateTemplate(String templateId,String templateName,String templateContent) {
		if (PubMethod.isEmpty(templateId)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.updateTemplate.001", "templateId不能为空");
		}
		if (PubMethod.isEmpty(templateName)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.updateTemplate.002", "templateName不能为空");
		}
		if (PubMethod.isEmpty(templateContent)) {
			return paramsFailure("net.okdi.apiV4.controller.MsgTemplateController.updateTemplate.003", "templateContent不能为空");
		}
		try {
			return msgTemplateService.updateTemplate(templateId,templateName,templateContent);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

}
