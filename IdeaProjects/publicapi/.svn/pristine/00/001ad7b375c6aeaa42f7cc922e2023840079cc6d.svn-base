/**  
 * @Project: publicapi
 * @Title: BroadcastServiceImpl.java
 * @Package net.okdi.mob.service.impl
 * @author amssy
 * @date 2015-1-20 下午03:56:18
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.BaiDuClient;
import net.okdi.mob.service.BroadcastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author amssy
 * @version V1.0
 */
@Service
public class BroadcastServiceImpl implements BroadcastService {
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private BaiDuClient baiduClient;
	/**
	 * @Method: addBroadcastOwn 
	 * @param jsonString
	 * @return 
	 * @see net.okdi.mob.service.BroadcastService#addBroadcastOwn(java.lang.String) 
	 */
	@Override
	public String addBroadcastOwn(String jsonString) {
		Map <String,String>map = new HashMap<String,String>();
		map.put("jsonString", jsonString);
	    String result = openApiHttpClient.doPassSendStr("broadcast/addBroadcastOwn", map);
		return result;
	}
	/**
	 * @Method: addBroadcast 
	 * @param jsonString
	 * @param memberId
	 * @return 
	 * @see net.okdi.mob.service.BroadcastService#addBroadcast(java.lang.String, java.lang.Long) 
	*/
	@Override
	public String addBroadcast(String jsonString, Long memberId) {
		JSONObject obj = JSON.parseObject(jsonString);
		String addressName = obj.getString("senderAddressName");
		Double[]location = baiduClient.getResult(addressName);
		obj.put("senderLongitude",location[0]);
		obj.put("senderLatitude", location[1]);
		Map <String,String>map = new HashMap<String,String>();
		map.put("jsonString", obj.toJSONString());
		map.put("memberId", memberId+"");
		String result = openApiHttpClient.doPassSendStr("broadcast/addBroadcast", map);
		return result;
	}
	
	@Override
	public String queryBroadcastList(Long loginMemberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("loginMemberId", PubMethod.isEmpty(loginMemberId)?"":loginMemberId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/queryBroadcastList", map);
		return result;
	}
	
	@Override
	public String queryBroadcastDetail(Long broadcastId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("broadcastId", PubMethod.isEmpty(broadcastId)?"":broadcastId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/queryBroadcastDetail", map);
		return result;
	}
	@Override
	public String cancelBroadcast(Long broadcastId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("broadcastId", PubMethod.isEmpty(broadcastId)?"":broadcastId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/cancelBroadcast", map);
		return result;
	}
	
	@Override
	public String timeoutBroadcast(Long broadcastId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("broadcastId", PubMethod.isEmpty(broadcastId)?"":broadcastId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/timeoutBroadcast", map);
		return result;
	}
	
	@Override
	public String broadcastRestart(Long broadcastId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("broadcastId", PubMethod.isEmpty(broadcastId)?"":broadcastId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/broadcastRestart", map);
		return result;
	}
	
	@Override
	public String broadcastRestartForTask(Long taskId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("taskId", PubMethod.isEmpty(taskId)?"":taskId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/broadcastRestartForTask", map);
		return result;
	}
	
	@Override
	public String createTaskEC(String broadcastParam) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("broadcastParam", broadcastParam);
		String result = openApiHttpClient.doPassSendStr("broadcast/createTask/ec", map);
		return result;
	}
	
	@Override
	public String createTaskPersonal(Long broadcastId, Long quotateId, String appointTime, String contactTel, String senderPhone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("broadcastId", PubMethod.isEmpty(broadcastId)?"":broadcastId.toString());
		map.put("quotateId", PubMethod.isEmpty(quotateId)?"":quotateId.toString());
		map.put("appointTime", appointTime);
		map.put("contactTel", contactTel);
		map.put("senderPhone", senderPhone);
		String result = openApiHttpClient.doPassSendStr("broadcast/createTask/personal", map);
		return result;
	}
	
	@Override
	public String deleteBroadcast(Long broadcastId, Long loginMemberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("broadcastId", PubMethod.isEmpty(broadcastId)?"":broadcastId.toString());
		map.put("loginMemberId", PubMethod.isEmpty(loginMemberId)?"":loginMemberId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/deleteBroadcast", map);
		return result;
	}
	
	@Override
	public String deleteTakeTask(Long taskId, Long loginMemberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("taskId", PubMethod.isEmpty(taskId)?"":taskId.toString());
		map.put("loginMemberId", PubMethod.isEmpty(loginMemberId)?"":loginMemberId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/deleteTakeTask", map);
		return result;
	}
	
	@Override
	public String queryTaskDetail(Long taskId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("taskId", PubMethod.isEmpty(taskId)?"":taskId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/queryTaskDetail", map);
		return result;
	}
	
	@Override
	public String scanParcel(Long id, Long taskId, String expWaybillNum, String phone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", PubMethod.isEmpty(id)?"":id.toString());
		map.put("taskId", PubMethod.isEmpty(taskId)?"":taskId.toString());
		map.put("expWaybillNum", expWaybillNum);
		map.put("phone", phone);
		String result = openApiHttpClient.doPassSendStr("broadcast/scanParcel", map);
		return result;
	}
	
	@Override
	public String broadcastCreateParcelPersonal(Long taskId, String expWaybillNum, String phone, Long addresseeAddressId, String addresseeAddress) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("taskId", PubMethod.isEmpty(taskId)?"":taskId.toString());
		map.put("expWaybillNum", expWaybillNum);
		map.put("phone", phone);
		map.put("addresseeAddressId", PubMethod.isEmpty(addresseeAddressId)?"":addresseeAddressId.toString());
		map.put("addresseeAddress", addresseeAddress);
		String result = openApiHttpClient.doPassSendStr("broadcast/broadcastCreateParcelPersonal", map);
		return result;
	}
	
	@Override
	public String broadcastCreateParcelEC(Long taskId, String expWaybillNum, String phone, Long addresseeAddressId, String addresseeAddress) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("taskId", PubMethod.isEmpty(taskId)?"":taskId.toString());
		map.put("expWaybillNum", expWaybillNum);
		map.put("phone", phone);
		map.put("addresseeAddressId", PubMethod.isEmpty(addresseeAddressId)?"":addresseeAddressId.toString());
		map.put("addresseeAddress", addresseeAddress);
		String result = openApiHttpClient.doPassSendStr("broadcast/broadcastCreateParcelEC", map);
		return result;
	}
	
	@Override
	public String deleteParcel(Long id, Long taskId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", PubMethod.isEmpty(id)?"":id.toString());
		map.put("taskId", PubMethod.isEmpty(taskId)?"":taskId.toString());
		String result = openApiHttpClient.doPassSendStr("broadcast/deleteParcel", map);
		return result;
	}
	@Override
	public String addBroadcastOwnOperation(String jsonString) {
		Map <String,String>map = new HashMap<String,String>();
		map.put("jsonString", jsonString);
	    String result = openApiHttpClient.doPassSendStr("broadcast/addBroadcastOwnOperation", map);
		return result;
	}
	@Override
	public String addBroadcastOperation(String jsonString, Long memberId) {
		JSONObject obj = JSON.parseObject(jsonString);
		String addressName = obj.getString("senderAddressName");
		Double[]location = baiduClient.getResult(addressName);
		obj.put("senderLongitude",location[0]);
		obj.put("senderLatitude", location[1]);
		Map <String,String>map = new HashMap<String,String>();
		map.put("jsonString", obj.toJSONString());
		map.put("memberId", memberId+"");
		String result = openApiHttpClient.doPassSendStr("broadcast/addBroadcastOperation", map);
		return result;
	}
	@Override
	public String getAllOnLineMember() {
		Map<String,String> map = new HashMap<String,String>();
		String result = openApiHttpClient.doPassSendStr("Courier/getAllOnLineMember", map);
		return result;
	}
}