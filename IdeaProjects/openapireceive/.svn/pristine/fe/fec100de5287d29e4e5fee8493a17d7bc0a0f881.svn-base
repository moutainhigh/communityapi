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
	 * @MethodName: net.okdi.apiV3.controller.phoneRecordController.java.savePhoneRecord 
	 * @Description: TODO(保存 电话记录) 
	    @param receivePhone   接收者电话
	   @param startTime 时间
	   @param flag    标识 1：本机电话  2：两端呼
	   @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-6
	 * @auth guoqiang.jing
	 */
	@ResponseBody
	@RequestMapping(value = "/savePhoneRecord", method = {RequestMethod.GET , RequestMethod.POST })
	public String savePhoneRecord(String sendPhone,String receivePhone,String flag) {		
		try{
			phoneRecordService.savePhoneRecord(sendPhone,receivePhone,flag);
			return jsonSuccess();
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	
	
	/**
	 * @MethodName: net.okdi.apiV3.controller.PhoneRecordController.java.queryPhoneRecord 
	 * @Description: TODO(查询电话记录) 
	 * @param  ddd
	         @param receivePhone  接收者电话
	         @param currentPage  
	         @param pageSize
	         @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-7
	 * @auth guoqiang.jing
	 *  {"data":
	 *      {"totalCount":1,
	 *      "list":[{
	 *       "startTime":"2016-03-01 14:01:16",  
	 *       "receivePhone":"13521283934"     
	 *       "flag":"1"
	 *       }]},"success":true}
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
			Map map = phoneRecordService.queryPhoneRecord(sendPhone,receivePhone,currentPage,pageSize);
			return jsonSuccess(map);
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	
	/**
	 * @MethodName: net.okdi.apiV3.controller.phoneRecordController.java.savePhoneRecord 
	 * @Description: TODO(删除 电话记录) 
	    @param id   接收者电话
	   @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-4-6
	 * @auth guoqiang.jing
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePhoneRecord", method = {RequestMethod.GET , RequestMethod.POST })
	public String deletePhoneRecord(String id) {		
		try{
			phoneRecordService.deletePhoneRecord(id);
			return jsonSuccess();
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	
}