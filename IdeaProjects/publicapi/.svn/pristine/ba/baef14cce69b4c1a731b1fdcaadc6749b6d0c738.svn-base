package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.CustomerAddLabelService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/CustomerAddLabel")
public class CustomerAddLabelController  extends BaseController {
	@Autowired
	private CustomerAddLabelService customerAddLabelService;

	/**
	 * @author chaoxu.wei
	 * @api {post} /customerAddLabel/insertLabel 添加标签new
	 * @apiVersion 0.3.0
	 * @apiDescription 添加标签new
	 * @apiGroup 客户管理
	 * @apiParam {String} labelName 标签名labelName
	 * @apiParam {Long} memberId 收派员memberId
	 * @apiParam {String} customerIds 多个客户customerIds(多个用英文逗号分隔)
	 * @apiSampleRequest /CustomerAddLabel/insertLabel
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample Success-Response:
	 *     {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/insertLabel", method={RequestMethod.POST, RequestMethod.GET})
	public String insertLabel(String labelName,Long memberId,String customerIds){
		
		try {
			return  customerAddLabelService.insertLabel(labelName, memberId, customerIds);
	
		} catch (Exception e) {
			return jsonFailure(e);
		}
		
	}

	/**
	 * @author chaoxu.wei
	 * @api {post} /CustomerAddLabel/queryLabelList 查询标签列表new
	 * @apiVersion 0.3.0
	 * @apiDescription 查询标签列表new
	 * @apiGroup 客户管理
	 * @apiParam {Long} memberId 收派员memberId 
	 * @apiSampleRequest /CustomerAddLabel/queryLabelList
	 * @apiSuccess {Integer} count 数量
	 * @apiSuccess {Long} labelId 标签id
	 * @apiSuccess {String} labelName 标签名
	 * @apiSuccess {Long} customerId 客户id
	 * @apiSuccess {String} customerName 客户名
	 * @apiSuccess {String} labelName 标签名
	 * @apiSuccessExample Success-Response:
	   {
		    "data": [
		        {
		            "count": 3,
		            "labelId": 242337295245312,
		            "labelName": "123",
		            "list": [
		                {
		                    "customerId": 223304034353152,
		                    "customerName": "",
		                    "userHead": ""
		                },
		                {
		                    "customerId": 222373337169920,
		                    "customerName": "",
		                    "userHead": ""
		                },
		                {
		                    "customerId": 222372506697728,
		                    "customerName": "",
		                    "userHead": ""
		                }
		            ]
		        },
		        {
		            "count": 2,
		            "labelId": 242343207116800,
		            "labelName": "456",
		            "list": [
		                {
		                    "customerId": 214928137175041,
		                    "customerName": "",
		                    "userHead": ""
		                },
		                {
		                    "customerId": 214927360843776,
		                    "customerName": "",
		                    "userHead": ""
		                }
		            ]
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
	@RequestMapping(value="/queryLabelList", method={RequestMethod.POST,RequestMethod.GET})
	public String queryLabelList(Long memberId){
		
	   try {
		return customerAddLabelService.queryLabelList(memberId);
		
	} catch (Exception e) {
		
		return jsonFailure(e);
	}
	}	
	
	/**
	 * @author chaoxu.wei
	 * @api {post} /CustomerAddLabel/getLabel 在编辑联系人中查询标签列表new
	 * @apiVersion 0.3.0
	 * @apiDescription 在编辑联系人中查询标签列表new
	 * @apiGroup 客户管理
	 * @apiParam {Long} memberId 收派员memberId 
	 * @apiSampleRequest /CustomerAddLabel/getLabel
	 * @apiSuccess {Long} labelId 标签id
	 * @apiSuccess {String} labelName 标签名
	 * @apiSuccessExample Success-Response:
	   {
		    "data": [
		        {
		            "labelId": 242337295245312,
		            "labelName": "123"
		        },
		        {
		            "labelId": 242343207116800,
		            "labelName": "456"
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
	@RequestMapping(value="/getLabel", method={RequestMethod.POST,RequestMethod.GET})
	public String getLabel(Long memberId){
		
		try {
			return customerAddLabelService.getLabel(memberId);
			
		} catch (Exception e) {
			
            return jsonFailure(e);
		}
		
	}
	
	/**
	 * @author chaoxu.wei
	 * @api {post} /CustomerAddLabel/updateLabel 编辑联系人标签new
	 * @apiVersion 0.3.0
	 * @apiDescription 编辑联系人标签new
	 * @apiGroup 客户管理
	 * @apiParam {Long} customerId 联系人customerId 
	 * @apiParam {Long} memberId 收派员memberId
	 * @apiParam {Long} labelId 标签labelId
	 * @apiSampleRequest /CustomerAddLabel/updateLabel
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample Success-Response:
	 *     {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/updateLabel", method={RequestMethod.POST,RequestMethod.GET})
	public String updateLabel(Long customerId,Long memberId,Long labelId){
		
		try {
			return customerAddLabelService.updateLabel(customerId,memberId,labelId);
			
		} catch (Exception e) {
			
			return jsonFailure(e);
			}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /CustomerAddLabel/editLabelCustomer 编辑标签成员
	 * @apiVersion 0.3.0
	 * @apiDescription 编辑标签成员
	 * @apiGroup 客户管理
	 * @apiParam {String} customerIds 最终联系人customerIds(用逗号分隔)
	 * @apiParam {Long} memberId 收派员memberId
	 * @apiParam {Long} labelId 标签labelId
	 * @apiParam {String} labelName 标签名字
	 * @apiSampleRequest /CustomerAddLabel/editLabelCustomer
	 * @apiError {String} result false
	 * @apiSuccessExample Success-Response:
	 *     {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/editLabelCustomer", method={RequestMethod.POST,RequestMethod.GET})
	public String editLabelCustomer(Long labelId, Long memberId, String customerIds,String labelName){
		if(PubMethod.isEmpty(labelId)){
			return paramsFailure("net.okdi.apiV4.controller.CustomerAddLabelController.editLabelCustomer", "labelId不能为空");
		}
		try {
			return customerAddLabelService.editLabelCustomer(memberId,labelId, customerIds,labelName);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /CustomerAddLabel/deleteLabel 删除标签
	 * @apiVersion 0.3.0
	 * @apiDescription 删除标签
	 * @apiGroup 客户管理
	 * @apiParam {Long} memberId 收派员memberId
	 * @apiParam {Long} labelId 标签labelId
	 * @apiSampleRequest /CustomerAddLabel/deleteLabel
	 * @apiError {String} result false
	 * @apiSuccessExample Success-Response:
	 *     {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/deleteLabel", method={RequestMethod.POST,RequestMethod.GET})
	public String deleteLabel(Long memberId, Long labelId){
		try {
			if (PubMethod.isEmpty(labelId)) {
				return paramsFailure("openapi.CustomerAddLabelController.deleteLabelId.001", "labelId不能为空");
			}
			if (PubMethod.isEmpty(memberId)) {
				return paramsFailure("openapi.CustomerAddLabelController.deleteLabelId.002", "memberId不能为空");
			}
			return customerAddLabelService.deleteLabel(memberId,labelId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
