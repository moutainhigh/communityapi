package net.okdi.apiV4.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.passport.OpenApiHttpClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class ReceivePackageServiceImpl implements ReceivePackageService {
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	private static Logger logger = Logger.getLogger(NumberQueryServiceImpl.class);
	
	//查询取件订单(收派员)或者待交寄订单(代收点)
	@Override
	public String queryTakePackList(String memberId, String currentPage, String pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakePackList", map);
		return result;
	}

	//待取任务详情
	@Override
	public String queryTakeTaskDetailByTaskId(String memberId, String taskId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("taskId", taskId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakeTaskDetailByTaskId", map);
		return result;
	}

	
	//确认取件(收派员取件和代收点取件)
	@Override
	public String takeSendPackage(String taskId, String memberId, String packageNum, String freightMoney, String sign) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		map.put("packageNum", packageNum);
		map.put("freightMoney", freightMoney);
		map.put("sign", sign);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/takeSendPackage", map);
		return result;
	}
	
	//收派员或者代收点自己添加的取件(自取件)
	/**
	 * String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double freightMoney, String packageJson
	 */
	@Override
	public String takeSendPackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double freightMoney, String packageJson, String flag) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("sendName", sendName);
		map.put("sendPhone", sendPhone);
		map.put("sendAddress", sendAddress);
		map.put("netId", netId);
		map.put("packageNum", packageNum);
		map.put("freightMoney", freightMoney);
		map.put("packageJson", packageJson);
		map.put("flag", flag);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/takeSendPackageByMember", map);
		return result;
	}
	
	
	
	//查询已取包裹列表
	@Override
	public String queryHasTakeList(String memberId, String roleId, String date, String netName, String currentPage, String pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		map.put("netName", netName);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		//map.put("date", date);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryHasTakeList", map);
		return result;
	}

	//已取包裹详情
	@Override
	public String queryHasTakePackDetailByParcelId(String parcelId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parcelId", parcelId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryHasTakePackDetailByParcelId", map);
		return result;
	}

	//查询该站点下的收派员
	@Override
	public String querySiteMemberByMemberId(String memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/querySiteMemberByMemberId", map);
		return result;
	}
	//获取代理点信息
	@Override
	public String consignationInfo(String memberId,String parcelIds) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("parcelIds", parcelIds);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/consignationInfo", map);
		return result;
	}
	
	//转单
	@Override
	public String turnTakePackageToMember(String taskId, String memberId, String toMemberId, String toMemberPhone) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		map.put("toMemberId", toMemberId);
		map.put("toMemberPhone", toMemberPhone);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/turnTakePackageToMember", map);
		return result;
	}

	//收派员包裹交寄给站点
	@Override
	public String consignationToSiteByMemberId(String parcelIds) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parcelIds", parcelIds);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/consignationToSiteByMemberId", map);
		return result;
	}

	//查询附近5公里范围内的收派员
	public String queryNearMemberByTude(String memberId, String netId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("netId", netId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryNearMemberByTude", map);
		return result;
	}
	
	
	//代收点交寄给收派员
	@Override
	public String consignationToMemberByParcelIds(String parcelIds, String toMemberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parcelIds", parcelIds);
		map.put("toMemberId", toMemberId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/consignationToMemberByParcelIds", map);
		return result;
	}

	//没有快递员,添加新的快递员
	@Override
	public String saveMemberInfoToNewMemberInfo(String parcelIds, String newMemberName, 
			String compName, String newMemberPhone) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parcelIds", parcelIds);
		map.put("newMemberName", newMemberName);
		map.put("compName", compName);
		map.put("newMemberPhone", newMemberPhone);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/saveMemberInfoToNewMemberInfo", map);
		return result;
	}

	//代收点叫快递
	@Override
	public String calledCourierToMember(String task_Id, String memberId, String parcelIds, String flag) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", task_Id);
		map.put("memberId", memberId);
		map.put("parcelIds", parcelIds);
		map.put("flag", flag);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/calledCourierToMember", map);
		return result;
	}
	
	//收派员确认取件
	public String memberConfirmOrder(String taskId, String memberId,
			String phone) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		map.put("phone", phone);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/memberConfirmOrder", map);
		return result;
	}

	//代收点确认交寄或者取消交寄
	@Override
	public String collectPointsConfirmSend(String taskId, String memberId, String flag) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		map.put("flag", flag);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/collectPointsConfirmSend", map);
		return result;
	}

	//查询取件记录
	@Override
	public String queryTakeRecordList(String memberId, String date, String phone) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		
		map.put("date", date);
		
		map.put("phone", phone);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakeRecordList", map);
		return result;
	}
	//2016-12-23         新增订单号expWaybillNum，交寄状态    parcelEndMark
	@Override
	public String queryTakeRecordListk(String memberId, String date, String phone, String expWaybillNum,String all,Integer currentPage,Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);	
		map.put("date", date);		
		map.put("phone", phone);
		map.put("expWaybillNum", expWaybillNum);
		//map.put("parcelEndMark", parcelEndMark);
		map.put("all", all);	
		map.put("currentPage", currentPage);	
		map.put("pageSize", pageSize);
        map.put("index", 0);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakeRecordListk", map);
		return result;
	}
	//揽收报表  12-28
	@Override
	public String queryTakeforms(String memberId, String date, String all) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);	
		map.put("date", date);	
		map.put("all", all);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakeforms", map);
		return result;
	}
	//查询交寄列表
	@Override
	public String queryTakeConsigOrderList(String memberId, String netId,
			String currentPage, String pageSize) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("netId", netId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakeConsigOrderList", map);
		return result;
	}

	//查询包裹列表(从任务过来的)
	@Override
	public String queryTakePackageList(String taskId, String memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakePackageList", map);
		return result;
	}

	//删除包裹
	@Override
	public String deleteParcelByParcelId(String taskId, String parcelId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("parcelId", parcelId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/deleteParcelByParcelId", map);
		return result;
	}

	@Override
	public String deleteTaskByTaskId(String taskId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/deleteTaskByTaskId", map);
		return result;
	}

	@Override
	public String getTaskIdByOrderNum(String orderNum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderNum", orderNum);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/getTaskIdByOrderNum", map);
		return result;
	}

	//收派员自取件为了微信支付---------自取件的
	@Override
	public String takeSendPackageByMemberAdd(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double freightMoney, String packageJson) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("sendName", sendName);
		map.put("sendPhone", sendPhone);
		map.put("sendAddress", sendAddress);
		map.put("netId", netId);
		map.put("packageNum", packageNum);
		map.put("freightMoney", freightMoney);
		map.put("packageJson", packageJson);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/takeSendPackageByMemberAdd", map);
		return result;
	}

	@Override
	public String queryRecordDetailByTaskId(String taskId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryRecordDetailByTaskId", map);
		return result;
	}

	@Override
	public String addParcelToTaskId(String taskId, String memberId, int packageNum, String sign, String packageJson) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		map.put("packageNum", packageNum);
		map.put("sign", sign);
		map.put("packageJson", packageJson);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/addParcelToTaskId", map);
		return result;
	}

	@Override
	public String takeReceivePackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, String packageJson) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("sendName", sendName);
		map.put("sendPhone", sendPhone);
		map.put("sendAddress", sendAddress);
		map.put("netId", netId);
		map.put("packageNum", packageNum);
		map.put("packageJson", packageJson);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/takeReceivePackageByMember", map);
		return result;
	}

	@Override
	public String takeReceivePackageByMembers(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,Long parcelId,String compId,String pacelWeight,String parcelType,String serviceName) {		
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
		logger.info("开始进来了后参数：memberId：" + memberId + ",sendName:"+ sendName + ",sendPhone:" + sendPhone + ",sendAddress:"+ sendAddress +"交付地址deliveryAddress"+deliveryAddress
				+",netId:" + netId + ",addresseeName:" + addresseeName+",addresseeAddress:" + addresseeAddress+ ",addresseePhone:" + addresseePhone+",netName"+netName+"roleId"+roleId+",expWaybillNum:" +
				expWaybillNum +"揽收码code："+code+"标记内容："+comment+"parcelId"+parcelId+"compId"+compId+"服务"+serviceName);	
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/takeReceivePackageByMembers", map);
		return result;
	}

	
	@Override
	public String takeReceivePackageDetailInfo(String taskId, Long memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/takeReceivePackageDetailInfo", map);
		return result;
	}

	@Override
	public Long queryTakePackageCount(Long memberId) {
		Date date=new Date();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String today = sim.format(date);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("date", today);
		map.put("memberId", memberId);
		logger.info("查询取件数量1111111：参数是：memberid："+memberId+"，date:"+today);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakePackageCount", map);
		//对数据进行解析
		Map<String, Object>returnmap = (Map<String, Object>) JSON.parse(result);
		logger.info("查询取件返回的map："+returnmap);
		long count = Long.parseLong(String.valueOf(returnmap.get("data")));
		return count;
		
	}

	@Override
	public String markContent(Long netId,Integer signType) {
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("netId", netId);
		map.put("signType", signType);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/content", map);
		return result;
	}
	@Override
	public String serviceType(Long netId) {
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("netId", netId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/serviceType", map);
		return result;
	}
	@Override
	public String queryCompMemberByMemberId(String memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryCompMemberByMemberId", map);
		return result;
	}

	/**
	 *
	 * @param memberId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public String queryNewTakePackList(String memberId, String currentPage, String pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryNewTakePackList", map);
		return result;
	}

	@Override
	public String addNewReceiveOrder(String memberId, String senderName, String senderPhone, String addressDetail, Integer packNum, String tagContent) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("senderName", senderName);
		map.put("senderPhone", senderPhone);
		map.put("addressDetail", addressDetail);
		map.put("packNum", packNum);
		map.put("tagContent", tagContent);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/addNewReceiveOrder", map);
		return result;
	}

	@Override
	public String tagOrder(String memberId, Long taskId, String tagContent) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("taskId", taskId);
		map.put("tagContent", tagContent);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/tagOrder", map);
		return result;
	}
	@Override
	public String operationOrderStatus(String memberId, Long taskId, String ancelReason) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("taskId", taskId);
		map.put("ancelReason", ancelReason);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/operationOrderStatus", map);
		return result;
	}

	@Override
	public String finishOrder(String memberId, Long taskId, String parcelIds, String sendName, String sendPhone, String sendAddress, 
			String netId, String addresseeName, String addresseePhone, String addresseeAddress, String expWaybillNum, String marker,
			String bourn, String code, String comment) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("taskId", taskId);
		map.put("parcelIds", parcelIds);
		map.put("sendName", sendName);
		map.put("sendPhone", sendPhone);
		map.put("sendAddress", sendAddress);
		map.put("netId", netId);
		map.put("addresseeName", addresseeName);
		map.put("addresseePhone", addresseePhone);
		map.put("addresseeAddress", addresseeAddress);
		map.put("expWaybillNum", expWaybillNum);
		map.put("marker", marker);
		map.put("bourn", bourn);
		map.put("code", code);
		map.put("comment", comment);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/finishOrder", map);
		return result;
	}

	
	@Override
	public String checkParcelDetail(Long parcelId, String contactAddress, Long netId, String netName, String contactMobile, String contactName) {
		//根据包裹id,查询包裹详情
		Map<String, Object>map=new HashMap<>();
		map.put("parcelId", parcelId);
		map.put("contactAddress", contactAddress);
		map.put("netId", netId);
		map.put("netName", netName);
		map.put("contactMobile", contactMobile);
		map.put("contactName", contactName);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/checkParcelDetail", map);
		return result;
	}

	@Override
	public String saveParcelInfo(String mark, Long parcelId, String contactAddress, Long netId, String netName, String contactMobile, String contactName, String expWaybillNum, String code, String addresseeName, String addresseeAddress, String addresseePhone) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mark", mark);
		map.put("parcelId", parcelId);
		map.put("contactAddress", contactAddress);
		map.put("netId", netId);
		map.put("netName", netName);
		map.put("contactMobile", contactMobile);
		map.put("contactName", contactName);
		map.put("expWaybillNum", expWaybillNum);
		map.put("code", code);
		map.put("addresseeName", addresseeName);
		map.put("addresseeAddress", addresseeAddress);
		map.put("addresseePhone", addresseePhone);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/saveParcelInfo", map);
		return result;
	}

	//揽收查询
	@Override
	public String queryPackage(String memberId, String sendMobile, String netId, String netName, String expWaybillNum, String code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("sendMobile", sendMobile);
		map.put("netId", netId);
		map.put("netName", netName);
		map.put("expWaybillNum", expWaybillNum);
		map.put("code", code);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryPackage", map);
		return result;
	}
	
	//揽收手机号查询
	@Override
	public String queryPackageMobile(String memberId, String sendMobile, String netId, String netName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("sendMobile", sendMobile);
		map.put("netId", netId);
		map.put("netName", netName);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryPackageMobile", map);
		return result;
	}
	//揽收查询详情
	@Override
	public String queryTakeRecordDetailed(String memberId, Long uid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("uid", uid);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryTakeRecordDetailed", map);
		return result;
	}
	//揽收查询记录
	@Override
	public String queryRecRecordList(String memberId, String date, String phone, String expWaybillNum, String compId, Integer currentPage, Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("date", date);
		map.put("phone", phone);
		map.put("compId", compId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		map.put("expWaybillNum", expWaybillNum);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/queryRecRecordList", map);
		return result;
	}

	@Override
	public String confirmTakeParcel(String uids, Long memberId, String taskId,String flag) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("uids", uids);
		map.put("memberId", memberId);
		map.put("taskId", taskId);
		map.put("flag", flag);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/confirmTakeParcel", map);
		return result;
	}
	//查询运单号唯一性
	@Override
	public String checkPidIsUnique(String expWaybillNum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("expWaybillNum", expWaybillNum);
		String result = openApiHttpClient.doPassTakeStrParcel("receivePackage/checkPidIsUnique", map);
		return result;
	}


	
}
