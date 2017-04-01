package net.okdi.taobaoApi.controller;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.taobaoApi.service.TaobaoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/taobaoApi")
public class TaobaoApiController extends BaseController {

	@Autowired
	private TaobaoApiService taobaoApiService;//注入service
	
	/**
	 * @author zhaohu
	 * @api {post} /taobaoApi/queryPhoneStatus 根据运单号查询手机号状态
	 * @apiVersion 0.2.0
	 * @apiDescription 根据运单号查询手机号状态
	 * @apiGroup 阿里大于短信对接
	 * @apiParam {Long} companyId 快递公司ID
	 * @apiParam {String} mailNo 运单号
	 * @apiSampleRequest /taobaoApi/queryPhoneStatus
	 * @apiSuccess {String} isFlag 是否是手机号 0否1是（如果是1说明是手机号可以用阿里大于发短信，如果是0说明非手机号 让快递员输入手机号走其它通道）
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "isFlag": "1"
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
	@RequestMapping(value = "/queryPhoneStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryPhoneStatus(Long companyId , String mailNo){
		if(PubMethod.isEmpty(companyId)){
			return paramsFailure("public-net.okdi.taobaoApi.controller.TaobaoApiController.queryPhoneStatus.001", "companyId不能为空");
		}
		if(PubMethod.isEmpty(mailNo)){
			return paramsFailure("public-net.okdi.taobaoApi.controller.TaobaoApiController.queryPhoneStatus.002", "mailNo不能为空");
		}
		try {
			return taobaoApiService.queryPhoneStatus(companyId , mailNo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /taobaoApi/queryCompanyList 查询与阿里合作的快递公司列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询与阿里合作的快递公司列表
	 * @apiGroup 阿里大于短信对接
	 * @apiSampleRequest /taobaoApi/queryCompanyList
	 * @apiSuccess {String} netName 快递公司名称
	 * @apiSuccess {Long} id 快递公司ID，传给后台
	 * @apiSuccess {String} picUrl 快递公司Logo
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "netName": "1"
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
	@RequestMapping(value = "/queryCompanyList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompanyList(){
		try {
			return taobaoApiService.queryCompanyList();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
