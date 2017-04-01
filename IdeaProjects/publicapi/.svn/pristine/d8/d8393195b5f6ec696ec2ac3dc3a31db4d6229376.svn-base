package net.okdi.apiV2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.okdi.apiV2.service.NetInfoApplyService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

@Controller
@RequestMapping("/NetInfoApply")
public class NetInfoApplyController extends BaseController{
	@Autowired
	private NetInfoApplyService netInfoApplyService;
	/**
	 * @author 韩爱军
	 * @api {post} /NetInfoApply/insertNetInfoApply 添加快递网络申请
	 * @apiVersion 0.2.0
	 * @apiDescription 添加快递网络申请 -韩爱军
	 * @apiGroup 新版-注册
	 * @apiParam {String} memberId 登录人memberId 不能为null
	 * @apiParam {String} netName  快递网络名称
	 * @apiparam {String} telphone 快递电话
	 * @apiparam {File}    file    上传file文件
	 * @apiSampleRequest /NetInfoApply/insertNetInfoApply
	 * @apiSuccessExample Success-Response:
	{
    "data": "0",
    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     
	 */
	@ResponseBody
	@RequestMapping(value = "insertNetInfoApply", method = { RequestMethod.POST, RequestMethod.GET })
	public String insertNetInfoApply(String memberId,String netName,String telphone,@RequestParam(value = "file", required = false)MultipartFile file){
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.NetInfoApplyController","memberId不能为空");
		}
		if(PubMethod.isEmpty(netName)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.NetInfoApplyController","netName不能为空");
		}
		if(PubMethod.isEmpty(telphone)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.NetInfoApplyController","telphone不能为空");
		}
		if(PubMethod.isEmpty(file)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.NetInfoApplyController","file不能为空");
		}
		try {
			String rs= this.netInfoApplyService.insertNetInfoApply(memberId, netName, telphone, file);
			return jsonSuccess(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
