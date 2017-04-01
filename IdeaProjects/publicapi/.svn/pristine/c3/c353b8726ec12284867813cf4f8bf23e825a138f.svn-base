package net.okdi.apiV3.controller;

import java.util.Map;

import net.okdi.apiV3.service.PhoneRecordService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/phoneRecord")
public class PhoneRecordController extends BaseController {

	@Autowired
	PhoneRecordService phoneRecordService;
	
	
	/**
	 * @api {post} /phoneRecord/savePhoneRecord 保存电话记录
	 * @apiVersion 0.3.0
	 * @apiDescription 保存电话记录
	 * @apiGroup ACCOUNT 拨打电话
	 * @apiParam {String} sendPhone    发送者电话
	 * @apiParam {String} receivePhone 接收者电话
	 * @apiParam {String}  flag        标识 1：本机电话  2：两端呼
	 * @apiSampleRequest  /phoneRecord/savePhoneRecord
	 * @apiSuccessExample Success-Response:
	        HTTP/1.1 200 OK
	 * 		{"success":"true"}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/savePhoneRecord", method = {RequestMethod.GET , RequestMethod.POST })
	public String savePhoneRecord(String sendPhone,String receivePhone,String flag) {	
		System.out.println("进入public");
		if(PubMethod.isEmpty(sendPhone)){
			return PubMethod.paramError("net.okdi.apiV3.controller.PhoneRecordController.queryPhoneRecord","sendPhone不能为空!");
		}
		if(PubMethod.isEmpty(receivePhone)){
			return PubMethod.paramError("net.okdi.apiV3.controller.PhoneRecordController.queryPhoneRecord","receivePhone不能为空!");
		}
		
		if(PubMethod.isEmpty(receivePhone)){
			return PubMethod.paramError("net.okdi.apiV3.controller.PhoneRecordController.queryPhoneRecord","flag不能为空!");
		}
		try{
			return phoneRecordService.savePhoneRecord(sendPhone,receivePhone,flag);
			
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	
	
	/**
	 * @api {post} /phoneRecord/queryPhoneRecord 查询电话记录
	 * @apiVersion 0.3.0
	 * @apiDescription 查询电话记录
	 * @apiGroup ACCOUNT 拨打电话
	 * @apiParam {String} receivePhone 接收者电话
	 *  @apiParam {String} sendPhone 发送者手机号
	 * @apiParam {Integer} currentPage    
	 * @apiParam {Integer}  pageSize       
	 * @apiSampleRequest  /phoneRecord/queryPhoneRecord
	 * @apiSuccess {Long} totalCount 
	 * @apiSuccess {String} id  
	 * @apiSuccess {String}  sendPhone    发送者手机号
	 * @apiSuccess {String} receivePhone  接收者电话
	 * @apiSuccess {String} startTime
	 * @apiSuccess {String} flag          标识 1：本机电话  2：两端呼
	 *  @apiSuccess {String} duration     时长   
	 *  @apiSuccess {String} fee          费用
	 * @apiSuccessExample Success-Response:
	       {"data":
	 *      {"totalCount":1,
	 *      "list":[{
	 *       "id":"5705d31b47050604c721c21d",
	 *       "startTime":"1460027688122",  
	 *       "receivePhone":"13521283934"     
	 *       "flag":"1",
	 *       "duration":"",
	 *       "fee":""
	 *       }]},"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPhoneRecord", method = {RequestMethod.GET , RequestMethod.POST })
	public String queryPhoneRecord(String sendPhone,String receivePhone, Integer currentPage, Integer pageSize) {	
		
		if(PubMethod.isEmpty(currentPage)){
			currentPage = 1;
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize = 10;
		}
		try{
			return  phoneRecordService.queryPhoneRecord(sendPhone,receivePhone,currentPage,pageSize);
		}catch(RuntimeException re){
			return this.jsonFailure(re);
		}
	}	
	
	/**
	 * @api {post} /phoneRecord/deletePhoneRecord 删除电话记录
	 * @apiVersion 0.3.0
	 * @apiDescription 删除电话记录
	 * @apiGroup ACCOUNT 拨打电话
	 * @apiParam {String} id 
	 * @apiSampleRequest  /phoneRecord/deletePhoneRecord
	 * @apiSuccessExample Success-Response:
	        HTTP/1.1 200 OK
	 * 		{"success":"true"}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePhoneRecord", method = {RequestMethod.GET , RequestMethod.POST })
	public String deletePhoneRecord(String id) {		
		if(PubMethod.isEmpty(id)){
			return PubMethod.paramError("net.okdi.apiV3.controller.PhoneRecordController.deletePhoneRecord","id不能为空!");
		}
		try{
			return  phoneRecordService.deletePhoneRecord(id);
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	
}