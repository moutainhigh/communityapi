package net.okdi.track.controller;

import net.okdi.apiV1.controller.ExpressUserController;
import net.okdi.apiV1.service.ExpressUserService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.track.service.QueryCourierInfoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 获取物流信息
 * @Project Name:publicapi 
 * @Package net.okdi.track.controller  
 * @Title: QueryCourieInfoController.java 
 * @ClassName: QueryCourieInfoController <br/> 
 * @date: 2015-11-17 下午5:51:35 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/queryCourie")
public class QueryCourierInfoController extends BaseController {

	@Autowired
	private QueryCourierInfoService queryCourierInfoService;
	Logger log = Logger.getLogger(ExpressUserController.class);
	/**
	 * @api {post} /queryCourie/queryCourierInformation 查询物流动态
	 * @apiPermission user
	 * @apiDescription 接口描述 查询物流动态	AiJun.Han
	 * @apiparam {String}  billCode  快递单号	
	 * @apiparam {String}   code 快递简码(例如：圆通快递：  YTO)
	 * @apiGroup ACCOUNT 物流
	 * @apiSampleRequest /queryCourie/queryCourierInformation
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {
	    "data": [
	        {
	            "context": "派件已 签收 ,签收人是拍照签收签收网点是丽江",
	            "time": "2014-11-24 14:24:18"
	        }
	    ],
	    "message": "ok",
	    "state": "",
	    "status": ""
	}
	 *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   result:{"errCode":1,"errSubCode":"","message":"java.lang.NumberFormatException: For input string: \"null\"","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCourierInformation", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCourierInformation(String billCode,String code){
		try {
			if (PubMethod.isEmpty(billCode)) {
				return PubMethod.paramsFailure("net.okdi.track.controller.QueryCourierInfoController.queryCourierInformation.001", "快递单号不能为空");
			}
			if (PubMethod.isEmpty(code)) {
				return PubMethod.paramsFailure("net.okdi.track.controller.QueryCourierInfoController.queryCourierInformation.002", "快递网络标识不能为空");
			}
			return this.queryCourierInfoService.queryCourierInformation(billCode,code);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
