package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.apiV4.service.ReceivePackageReportService;
import net.okdi.core.passport.OpenApiHttpClient;
@Service
public class ReceivePackageReportServiceImpl  implements ReceivePackageReportService{

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	private static Logger logger = Logger.getLogger(NumberQueryServiceImpl.class);
	@Override
	public String queryTakeforms(String memberId, String date, String compId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);	
		map.put("date", date);	
		map.put("compId", compId);
		String result = openApiHttpClient.doPassTakeStrParcel("receiveReport/queryTakeforms", map);
		return result;
	}
	@Override
	public String queryPackage(String memberId, String expWaybillNum, String netId, String netName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("expWaybillNum", expWaybillNum);
		map.put("netId", netId);
		map.put("netName", netName);		
		String result = openApiHttpClient.doPassTakeStrParcel("receiveReport/queryPackage", map);
		return result;
	}
	@Override
	public String queryPackageCode(String memberId, String code, String netId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("code", code);
		map.put("netId", netId);		
		String result = openApiHttpClient.doPassTakeStrParcel("receiveReport/queryPackageCode", map);
		return result;
	}
	@Override
	public String finishTakeReceivePackage(String memberId, String jsonData, String codes, String compId, String deliveryAddress,
			String versionId, String terminalId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("jsonData", jsonData);	
		map.put("codes", codes);
		map.put("compId", compId);
		map.put("deliveryAddress", deliveryAddress);
		map.put("versionId", versionId);
		map.put("terminalId", terminalId);
		//map.put("nId", nId);
		String result = openApiHttpClient.doPassTakeStrParcel("receiveReport/finishTakeReceivePackage", map);
		return result;
	}
	@Override
	public String takeReceivePackageByMember(String memberId, String sendName, String sendPhone, String sendAddress, 
			String deliveryAddress, String netId, String addresseeName, String addresseeAddress, String addresseePhone,
			String netName, String roleId, String expWaybillNum, String code, String comment, Long parcelId, String compId, 
			String pacelWeight, String parcelType, String serviceName, String sendProv, String sendCity, String addresseeProv,
			String addresseeCity, String sendDetailed, String addresseeDetailed) {
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("memberId", memberId);
		map.put("sendName", sendName);
		map.put("sendPhone", sendPhone);
		map.put("sendAddress", sendAddress);
		map.put("deliveryAddress", deliveryAddress);	
		map.put("netId", netId);
		map.put("addresseeName", addresseeName);
		map.put("addresseeAddress", addresseeAddress);
		map.put("addresseePhone", addresseePhone);
		map.put("netName", netName);
		map.put("roleId", roleId);
		map.put("expWaybillNum", expWaybillNum);
		map.put("code", code);
		map.put("comment", comment);
		map.put("parcelId", parcelId);	
		map.put("compId", compId);
		map.put("pacelWeight", pacelWeight);
		map.put("parcelType", parcelType);
		map.put("serviceName", serviceName);
		map.put("sendProv", sendProv);		
		map.put("sendCity", sendCity);
		map.put("addresseeProv", addresseeProv);
		map.put("addresseeCity", addresseeCity);
		map.put("sendDetailed", sendDetailed);
		map.put("addresseeDetailed", addresseeDetailed);
			
		String result = openApiHttpClient.doPassTakeStrParcel("receiveReport/takeReceivePackageByMember", map);
		return result;
	}
	@Override
	public String againUpload(String memberId, String netId, String netName, String expWaybillNum, String code, Long parcelId) {
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("memberId", memberId);	
		map.put("netId", netId);
		map.put("netName", netName);
		map.put("expWaybillNum", expWaybillNum);
		map.put("code", code);
		map.put("parcelId", parcelId);				
		String result = openApiHttpClient.doPassTakeStrParcel("receiveReport/againUpload", map);
		return result;
	}
	@Override
	public String takeReceivePackage(String memberId, String sendName, String sendPhone, String sendAddress,
			String deliveryAddress, String netId, String addresseeName, String addresseeAddress, String addresseePhone,
			String netName, String roleId, String expWaybillNum, String code, String comment, String compId,
			String pacelWeight, String parcelType, String serviceName, String sendProv, String sendCity, String addresseeProv,
			String addresseeCity, String sendDetailed, String addresseeDetailed,String versionId ,String terminalId) {
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("memberId", memberId);
		map.put("sendName", sendName);
		map.put("sendPhone", sendPhone);
		map.put("sendAddress", sendAddress);
		map.put("deliveryAddress", deliveryAddress);	
		map.put("netId", netId);
		map.put("addresseeName", addresseeName);
		map.put("addresseeAddress", addresseeAddress);
		map.put("addresseePhone", addresseePhone);
		map.put("netName", netName);
		map.put("roleId", roleId);
		map.put("expWaybillNum", expWaybillNum);
		map.put("code", code);
		map.put("comment", comment);
		map.put("compId", compId);
		map.put("pacelWeight", pacelWeight);
		map.put("parcelType", parcelType);
		map.put("serviceName", serviceName);
		map.put("sendProv", sendProv);		
		map.put("sendCity", sendCity);
		map.put("addresseeProv", addresseeProv);
		map.put("addresseeCity", addresseeCity);
		map.put("sendDetailed", sendDetailed);
		map.put("addresseeDetailed", addresseeDetailed);
		map.put("versionId", versionId);
		map.put("terminalId", terminalId);
			
		String result = openApiHttpClient.doPassTakeStrParcel("receiveReport/takeReceivePackage", map);
		return result;
	}
	

}
