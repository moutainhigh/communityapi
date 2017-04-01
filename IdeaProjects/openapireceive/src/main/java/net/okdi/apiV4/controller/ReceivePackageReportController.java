package net.okdi.apiV4.controller;

import java.util.Map;

import net.okdi.apiV4.service.ReceivePackageReportService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/receiveReport")
public class ReceivePackageReportController extends BaseController{

	private static Logger logger = Logger.getLogger(ReceivePackageReportController.class);
	
	@Autowired
	private ReceivePackageReportService reportService;
	/**
	 * 揽收前查询
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPackage", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryPackage(String memberId, String expWaybillNum, String netId,String netName){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}	
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("002", "netId 不能为空!!!");
		}	
		if(PubMethod.isEmpty(expWaybillNum)){
			return paramsFailure("003", "expWaybillNum 不能为空!!!");
		}	
		Map<String, Object> result = reportService.queryPackage(memberId,expWaybillNum,netId,netName);
		
		return jsonSuccess(result);
	}
	/**
	 * 揽散件去重查询
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPackageCode", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryPackageCode(String memberId, String code, String netId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}	
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("002", "netId 不能为空!!!");
		}	
		if(PubMethod.isEmpty(code)){
			return paramsFailure("003", "code 不能为空!!!");
		}	
		String result = reportService.queryPackageCode(memberId,code,netId);		
		return jsonSuccess(result);
	}
	@ResponseBody
	@RequestMapping(value = "/queryTakeforms", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeforms(String memberId, String date, String compId){
		logger.info("j进入揽收报表查询数据====memberId :"+memberId+"date :" + date +"compId"+compId);
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(date)){
			return paramsFailure("001", "date 不能为空!!!");
		}
		Map<String,Object> result = reportService.queryTakeforms(memberId, date,compId);		
		return jsonSuccess(result);
	}
	/**
	 * 揽收包裹
	 */
	@ResponseBody
	@RequestMapping(value = "/takeReceivePackageByMember", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageByMember(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,Long parcelId,String compId,String pacelWeight,String parcelType,String serviceName,
			 String sendProv,String sendCity,String addresseeProv,String addresseeCity,String sendDetailed,String addresseeDetailed){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(sendPhone)){
			return paramsFailure("003", "sendPhone 不能为空!!!");
		}
		if(PubMethod.isEmpty(sendAddress)){
			return paramsFailure("004", "sendAddress 不能为空!!!");
		}
		if(PubMethod.isEmpty(addresseePhone)){
			return paramsFailure("006", "addresseePhone 不能为空!!!");
		}
		if(PubMethod.isEmpty(addresseeAddress)){
			return paramsFailure("007", "addresseeAddress 不能为空!!!");
		}
		Map<String, Object> result = reportService.takeReceivePackageByMember(memberId, sendName, sendPhone,  sendAddress, deliveryAddress,
				 netId,  addresseeName,  addresseeAddress, addresseePhone, netName, roleId,
				 expWaybillNum , code, comment, parcelId, compId,pacelWeight,parcelType,serviceName, sendProv, sendCity, addresseeProv,
				 addresseeCity, sendDetailed, addresseeDetailed);
		return jsonSuccess(result);		

	}
	/**
	 * 揽散件包裹（上传订单，创建作业记录）揽收码专用
	 */
	@ResponseBody
	@RequestMapping(value = "/takeReceivePackage", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackage(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,String compId,String pacelWeight,String parcelType,String serviceName,
			 String sendProv,String sendCity,String addresseeProv,String addresseeCity,String sendDetailed,String addresseeDetailed,
			 String versionId ,String terminalId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(sendPhone)){
			return paramsFailure("003", "sendPhone 不能为空!!!");
		}
		if(PubMethod.isEmpty(sendAddress)){
			return paramsFailure("004", "sendAddress 不能为空!!!");
		}
		if(PubMethod.isEmpty(addresseePhone)){
			return paramsFailure("006", "addresseePhone 不能为空!!!");
		}
		if(PubMethod.isEmpty(addresseeAddress)){
			return paramsFailure("007", "addresseeAddress 不能为空!!!");
		}
		Map<String, Object> result = reportService.takeReceivePackage(memberId, sendName, sendPhone,  sendAddress, deliveryAddress,
				 netId,  addresseeName,  addresseeAddress, addresseePhone, netName, roleId,
				 expWaybillNum , code, comment, compId,pacelWeight,parcelType,serviceName, sendProv, sendCity, addresseeProv,
				 addresseeCity, sendDetailed, addresseeDetailed,versionId,terminalId);
		return jsonSuccess(result);		

	}
	/**
	 * 揽收--确认取件
	 */
	@ResponseBody
	@RequestMapping(value = "/finishTakeReceivePackage", method = {RequestMethod.POST, RequestMethod.GET })
	public String finishTakeReceivePackage(String memberId ,String jsonData,String codes,String compId,String deliveryAddress,
			String versionId ,String terminalId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}	
		String result = reportService.finishTakeReceivePackage(memberId,jsonData, codes,compId,deliveryAddress,versionId ,terminalId);		
		
		return jsonSuccess(result);
	}
	/**
	 * 揽收--重新上传
	 */
	@ResponseBody
	@RequestMapping(value = "/againUpload", method = {RequestMethod.POST, RequestMethod.GET })
	public String againUpload(String memberId, String netId, String netName,String expWaybillNum ,String code,Long parcelId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}	
		Map<String, Object> result = reportService.againUpload(memberId, netId, netName, expWaybillNum,code,parcelId);
		return jsonSuccess(result);		
	}
}
