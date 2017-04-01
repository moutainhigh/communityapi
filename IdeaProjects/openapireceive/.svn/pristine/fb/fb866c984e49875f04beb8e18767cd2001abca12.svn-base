/**  
 * @Project: openapi
 * @Title: SendTaskServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @author amssy
 * @date 2014-12-9 下午07:16:23
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
 */
package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.ParParcelinfoMapper;
import net.okdi.api.dao.ParTaskDisposalRecordMapper;
import net.okdi.api.dao.SendTaskMapper;
import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.api.entity.ParTaskDisposalRecord;
import net.okdi.api.service.DispatchParService;
import net.okdi.api.service.ExpressPriceService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.SendTaskService;
import net.okdi.api.vo.TaskVo;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * @author mengnan.zhang
 * @version V1.0
 */
@Service
public class SendTaskServiceImpl implements SendTaskService {
	@Autowired
	SendTaskMapper sendTaskMapper;
	@Autowired
	ParParcelinfoMapper parParcelinfoMapper; 
	@Autowired
	ParTaskDisposalRecordMapper parTaskDisposalRecordMapper;
	@Autowired
	ExpressPriceService expressPriceService;
	@Autowired
	EhcacheService ehcacheService;
	@Autowired
	ParcelInfoService parcelInfoService;
	@Autowired
	DispatchParService dispatchParService;
	
	
    SerializerFeature [] s = {SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullNumberAsZero,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteDateUseDateFormat};
    /**
     * @Method: addSendTask 添加派件任务
     * @param taskType          '任务类型 0:取件 1:派件 2:自取件 3：销售等非快递',
     * @param coopCompId        '任务受理方站点',
     * @param coopNetId         '任务受理方网络',
     * @param parEstimateCount  '包裹预估数',
     * @param parEstimateWeight '包裹预估重量',
     * @param appointTime       '预约取件时间',
     * @param appointDesc       '预约描述',
     * @param taskSource        '任务来源 1:好递网,2:站点自建,3:电商管家,4:好递个人端,5:好递接单王',
     * @param taskStatus        '任务状态',
     * @param taskIsEnd         '任务是否结束',
     * @param taskEndTime       '任务结束时间',
     * @param actorMemberId     '执行人员ID 取件员id',
     * @param actorPhone        '执行人电话',
     * @param contactName       '联系人姓名',
     * @param contactMobile     '联系人手机',
     * @param contactTel        '联系人电话',
     * @param contactAddressId  '联系人地址id',
     * @param contactAddress    '详细地址',
     * @param customerId        '客户id/发件人id',
     * @param contactCasUserId  '联系人CAS_ID',
     * @param contactCompId     '联系人公司ID',
     * @param createTime        '创建时间',
     * @param createUserId      '创建人ID',
     * @param taskFlag          '任务标志 0：正常,1：抢单',
     * @param modifyTime        '最后修改时间',
     * @param contactAddrLongitude '联系人地址的经度信息',
     * @param contactAddrLatitude  '联系人地址的纬度信息',
     * @return 
     * @see net.okdi.api.service.SendTaskService#addSendTask(java.lang.Integer, java.lang.Long, java.lang.Long, java.lang.Integer, java.lang.Double, java.util.Date, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.util.Date, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.lang.Long, java.lang.Integer, java.util.Date, java.lang.Double, java.lang.Double)
     */
	@Override         
	public String addSendTask(Long parcelId,Integer taskType, Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc, Integer taskSource,
			Integer taskStatus, Integer taskIsEnd, Date taskEndTime,
			Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Date createTime, Long createUserId,
			Integer taskFlag, Date modifyTime, Double contactAddrLongitude,
			Double contactAddrLatitude) {
		ehcacheService.remove("await_Parce_List", actorMemberId+"");
		ehcacheService.remove("sendTaskCache", actorMemberId+"");
//		ehcacheService.remove("parcelIdsCacheByMemberId",actorMemberId+"");
		ehcacheService.removeAll("parcelIdsCacheByMemberId");
		dispatchParService.removeTaskCache();
		dispatchParService.clearDispatchCache();
		ehcacheService.remove("queryAlreadySignList",  String.valueOf(actorMemberId));
		ehcacheService.remove("", "");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] keys = { "taskType", "coopCompId", "coopNetId",
				"parEstimateCount", "parEstimateWeight", "appointTime",
				"appointDesc", "taskSource", "taskStatus", "taskIsEnd",
				"taskEndTime", "actorMemberId", "actorPhone", "contactName",
				"contactMobile", "contactTel", "contactAddressId",
				"contactAddress", "customerId", "contactCasUserId",
				"contactCompId", "createTime", "createUserId", "taskFlag",
				"modifyTime", "contactAddrLongitude", "contactAddrLatitude" };
		Object[] values = { taskType, coopCompId, coopNetId, parEstimateCount,
				parEstimateWeight, appointTime, appointDesc, taskSource,
				taskStatus, taskIsEnd, taskEndTime, actorMemberId, actorPhone,
				contactName, contactMobile, contactTel, contactAddressId,
				contactAddress, customerId, contactCasUserId, contactCompId,
				createTime, createUserId, taskFlag, modifyTime,
				contactAddrLongitude, contactAddrLatitude };
		map = this.getParam(keys, values);
		sendTaskMapper.addSendTaskInfo(map);
		this.updateParcel(Long.parseLong(map.get("taskId").toString()), parcelId,createUserId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("taskId", map.get("id")+"");
		data.put("success", true);
		return JSON.toJSONString(data);
	}
	
	
	
	@Override         
	public String addOrUpdateSendTask(Long parcelId,Integer taskType, Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc, Integer taskSource,
			Integer taskStatus, Integer taskIsEnd, Date taskEndTime,
			Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Date createTime, Long createUserId,
			Integer taskFlag, Date modifyTime, Double contactAddrLongitude,
			Double contactAddrLatitude) {
		
		ehcacheService.remove("await_Parce_List", actorMemberId+"");
		ehcacheService.remove("sendTaskCache", actorMemberId+"");
//		ehcacheService.remove("parcelIdsCacheByMemberId",actorMemberId+"");
		ehcacheService.removeAll("parcelIdsCacheByMemberId");
		dispatchParService.removeTaskCache();
		dispatchParService.clearDispatchCache();
		ehcacheService.remove("queryAlreadySignList",  String.valueOf(actorMemberId));
		Map<String, Object> map = new HashMap<String, Object>();
		String[] keys = { "taskType", "coopCompId", "coopNetId",
				"parEstimateCount", "parEstimateWeight", "appointTime",
				"appointDesc", "taskSource", "taskStatus", "taskIsEnd",
				"taskEndTime", "actorMemberId", "actorPhone", "contactName",
				"contactMobile", "contactTel", "contactAddressId",
				"contactAddress", "customerId", "contactCasUserId",
				"contactCompId", "createTime", "createUserId", "taskFlag",
				"modifyTime", "contactAddrLongitude", "contactAddrLatitude" };
		Object[] values = { taskType, coopCompId, coopNetId, parEstimateCount,
				parEstimateWeight, appointTime, appointDesc, taskSource,
				taskStatus, taskIsEnd, taskEndTime, actorMemberId, actorPhone,
				contactName, contactMobile, contactTel, contactAddressId,
				contactAddress, customerId, contactCasUserId, contactCompId,
				createTime, createUserId, taskFlag, modifyTime,
				contactAddrLongitude, contactAddrLatitude };
		map = this.getParam(keys, values);
		
		ParParcelinfo parcelInfo = parcelInfoService.getParcelInfoByIdNoEnd(parcelId);   //1.查询没有结束且在途的包裹  --jinggq
		if(!PubMethod.isEmpty(parcelInfo) && !PubMethod.isEmpty(parcelInfo.getSendTaskId())) { //2。如果有任务号  --jinggq
			Map<Object, Object> parcel = this.parcelInfoService.findParcelDetailByWaybillNumAndNetId(parcelInfo.getExpWaybillNum(),parcelInfo.getNetId());
			if(!PubMethod.isEmpty(parcel.get("actualSendMember"))){
			 String sendMember = 	parcel.get("actualSendMember").toString();
			 ehcacheService.remove("sendTaskCache", sendMember);
			}
			map.put("taskId", parcelInfo.getSendTaskId());
			sendTaskMapper.updateByPrimaryKeySelective(map);             //2.通过任务号-par_task_info表信息   --jinggq
			this.updateParcel(parcelInfo.getSendTaskId(), parcelId,createUserId);  //3.通过pracelid和createUserId更新SendTaskId和parcel_status,tacking_status  --jinggq
		} else {  //4.如果没有任务号   --jinggq
			sendTaskMapper.addSendTaskInfo(map);        //5.向任务表添加任务  --jinggq
			this.updateParcel(Long.parseLong(map.get("taskId").toString()), parcelId,createUserId); //6.将taskId更入ParcelInfo  --jinggq
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("taskId", map.get("id")+"");
		data.put("success", true);
		return JSON.toJSONString(data);
	}

	private Map<String, Object> getParam(String[] keys, Object[] values) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		Long id = IdWorker.getIdWorker().nextId();
		map.put("taskId", id);
		return map;
	}

	/**
	 * @Method: changSendPerson 
	 * @param parcelIds
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.SendTaskService#changSendPerson(java.lang.String, java.lang.Long) 
	*/
	@Override
	public String changSendPerson(String parcelIds, Long memberId,Long oldMemberId) {
		ehcacheService.remove("takeIdsCacheByMemberId", memberId+"");
		ehcacheService.remove("takeIdsCacheByMemberId", oldMemberId+"");
		ehcacheService.remove("parcelIdsCacheByMemberId", memberId+"");
		ehcacheService.remove("parcelIdsCacheByMemberId", oldMemberId+"");
		ehcacheService.remove("queryAlreadySignList",  String.valueOf(memberId));
		ehcacheService.remove("queryAlreadySignList",  String.valueOf(oldMemberId));
		String[] split = parcelIds.split(",");
		for (String parId : split) {    //1.循环包裹id    --jinggq
			ehcacheService.remove("parcelAddressCache", parId);
			ehcacheService.remove("parcelInfoCache", parId);
			 Map map = this.parParcelinfoMapper.queryWaybillNumById(Long.parseLong(parId));  //2.查询运单号he网络id   --jinggq
			 String expWayBillNum = PubMethod.isEmpty(map.get("expWayBillNum"))?"":map.get("expWayBillNum").toString();
			 String netId = PubMethod.isEmpty(map.get("netId"))?"":map.get("netId").toString();
			 ehcacheService.remove("parcelIdCacheByExpWayBillNumAndNetId", expWayBillNum+netId);
			//更新包裹异常时间 因为包裹异常后更新包裹状态后不再是异常包裹
			parParcelinfoMapper.updateExceptionTime(Long.parseLong(parId));  //3.更新包裹异常时间为null   --jinggq
		}
		if(ifHasPickUpByParcelId(parcelIds)){    //4.通过SendTaskId是否为空判断是否提货,如果提货了 不能转单  --jinggq
			return jsonFail("3");
		}
		List <String>list = new ArrayList<String>();
		list= getParcelIdList(list,parcelIds);
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("ids", list);
		params.put("memberId", memberId);
		sendTaskMapper.changSendPerson(params);//5.通过ParcelId集合更新包裹信息表成新的memberId   --jinggq
		return jsonSuccess(null);
	}
	private List<String> getParcelIdList(List<String>list,String parcelIds){
		
		if(!PubMethod.isEmpty(parcelIds)){
			String []ids = parcelIds.split(",");
			list = Arrays.asList(ids);
		}
		return list;
	}
	 private String jsonSuccess(Object map) {
			Map<String, Object> allMap = new HashMap<String, Object>();
			allMap.put("success", true);
			if (null != map) {
				allMap.put("data", map);
			}
			return JSON.toJSONString(allMap,s).replaceAll(":null", ":\"\"");
		}
	 
	 private String jsonFail(Object map) {
			Map<String, Object> allMap = new HashMap<String, Object>();
			allMap.put("success", false);
			if (null != map) {
				allMap.put("data", map);
			}
			return JSON.toJSONString(allMap,s).replaceAll(":null", ":\"\"");
		}

	/**
	 * @Method: ifHasPickUp 
	 * @param parcelId
	 * @return 5522336699
	 * @see net.okdi.api.service.SendTaskService#ifHasPickUp(java.lang.Long) 
	 * {"data":{"hasSendPickUp":true,"hasSign":false,"hasTakePickUp":false,
	 *          "parcelId":"","sendmemberId":"127068784607232"},"success":true}
	*/
	@Override
	public String ifHasPickUp(String expWayBillNum,Long netId) {   //判断包裹是否已经被提走  --jinggq
		  
		Map<String,Object> map = new HashMap<String,Object>();
		//send_task_id 是否生成了派件任务
		//take_task_id 是否生成了取件任务
		//send_member 查询实际派件人Id       --jinggq
//		List<Object> takeTaskIdList = sendTaskMapper.ifHasTakePickUp( expWayBillNum, netId);
		List<Map> resultList = sendTaskMapper.ifHasPickUp( expWayBillNum, netId);
//		2. actual_take_member 包裹是否被取走    --jinggq
		List<Object> idList = sendTaskMapper.ifParcelExist( expWayBillNum, netId);
		//3. tackingStatus  parcelEndMark 包裹是否已签收   --jinggq
		List<Map> signList = sendTaskMapper.ifParcelSign(expWayBillNum, netId);
		if(!PubMethod.isEmpty(signList) && !PubMethod.isEmpty(signList.get(0))){
			 Map signMap = 	signList.get(0);
			 String tackingStatus = PubMethod.isEmpty(signMap.get("tackingStatus"))?"":signMap.get("tackingStatus").toString();
			 String parcelEndMark = PubMethod.isEmpty(signMap.get("parcelEndMark"))?"":signMap.get("parcelEndMark").toString();
				 if(tackingStatus.equals("1") || parcelEndMark.equals("1")){
					 map.put("hasSign", true);
				 } else {
					 map.put("hasSign", false);
				 }
		} else {
			map.put("hasSign", false);
		}
		
		if(!PubMethod.isEmpty(resultList) && !PubMethod.isEmpty(resultList.get(0))){
			Map taskMap =  resultList.get(0);
			map.put("hasSendPickUp", PubMethod.isEmpty(taskMap.get("sendTaskId"))?false:true);
			map.put("hasTakePickUp", PubMethod.isEmpty(taskMap.get("takeTaskId"))?false:true);
			map.put("sendmemberId", PubMethod.isEmpty(taskMap.get("actualSendMember"))?"":taskMap.get("actualSendMember").toString());
		} else {
			map.put("hasSendPickUp", false);
			map.put("hasTakePickUp", false);
			map.put("sendmemberId","");
		}
		map.put("parcelId", PubMethod.isEmpty(idList)?"":idList.get(0));
		return jsonSuccess(map);
	}
	
	/**
	 * @Method: ifHasPickUpByParcelId 
	 * @param parcelId
	 * @return 
	 * @see net.okdi.api.service.SendTaskService#ifHasPickUp(java.lang.Long) 
	*/
	@Override
	public boolean ifHasPickUpByParcelId(String ParcelId) {
		List<String> li = new ArrayList<String>();
		String[] split = ParcelId.split(",");
		Collections.addAll(li, split);
		List sendTaskIdList = sendTaskMapper.ifHasPickUpByParcelId(li);
		if(!sendTaskIdList.contains(null)){
			return true;
		}
		return false;
	}

	
	@Override
	public boolean ifHasPickUpByParcelIdAndMemberId(String ParcelId,Long memberId) {
		Map map = new HashMap();
		List<String> li = new ArrayList<String>();
		String[] split = ParcelId.split(",");
		Collections.addAll(li, split);
		map.put("parList", li);
		map.put("memberId", memberId);
		List sendTaskIdList = sendTaskMapper.ifHasPickUpByParcelIdAndMemberId(map);
		if(!sendTaskIdList.contains(null)){
			if(!PubMethod.isEmpty(sendTaskIdList)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * @Method: updateParcel 
	 * @param taskId
	 * @param parcelId 
	 * @see net.okdi.api.service.SendTaskService#updateParcel(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public void updateParcel(Long taskId, Long parcelId,Long createUserId) {
		sendTaskMapper.updateParcel(taskId, parcelId,createUserId);
		this.ehcacheService.remove("takeIdsCacheByMemberId", String.valueOf(createUserId));
		this.ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(createUserId));
//		this.ehcacheService.remove("queryTakeByWaybillNum", String.valueOf(createUserId));
	}

	/**
	 * @Method: addSendTaskBatch 
	 * @param parcelIds 包裹ID串
	 * @param memberId 收派员Id
	 * @param memberPhone 收派员电话
	 * @return   15214251425
	 * @see net.okdi.api.service.SendTaskService#addSendTaskBatch(java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	public String addSendTaskBatch(String parcelIds,Long memberId,String memberPhone) {
		if(ifHasPickUpByParcelId(parcelIds)) {
			return jsonFail("3");
		}
		String [] parcelId = parcelIds.split(",");
		
		for(int i =0;i<parcelId.length;i++){
			ParParcelinfo parParcelinfo = parcelInfoService.getParcelInfoById(Long.parseLong(parcelId[i]));
			ParParceladdress parParceladdress = parcelInfoService.getParParceladdressById(Long.parseLong(parcelId[i])); 
//			parParceladdress.getAddresseeMobile()
			//更新包裹异常时间 因为包裹异常后更新包裹状态后不再是异常包裹
			parParcelinfoMapper.updateExceptionTime(Long.parseLong(parcelId[i]));
			this.addSendTask(Long.parseLong(parcelId[i]),1,parParcelinfo.getCompId(), parParcelinfo.getNetId(),parParcelinfo.getGoodsNum(),parParcelinfo.getChareWeightForsender()==null?0.00:parParcelinfo.getChareWeightForsender().doubleValue(), null, null, -1, 11, 0, null, memberId, memberPhone, parParceladdress.getAddresseeName(),parParceladdress.getAddresseeMobile(), parParceladdress.getAddresseePhone(), parParceladdress.getAddresseeAddressId(), parParceladdress.getAddresseeAddress(), null, null, null, new Date(), memberId, 0, new Date(), null, null);
		}
		return jsonSuccess(null);
	}
	@Override
	public String createSendTaskOrUpdate(String parcelId, Long memberId,
			String memberPhone) {
	    	String [] ids = parcelId.split(",");
	    	for(int i = 0; i < ids.length; i++){ 
	    		 Map map = this.parParcelinfoMapper.queryWaybillNumById(Long.parseLong(ids[i]));   //1.查询运单号和netid,用来移除缓存  --jinggq
				 String expWayBillNum = PubMethod.isEmpty(map.get("expWayBillNum"))?"":map.get("expWayBillNum").toString();
				 String netId = PubMethod.isEmpty(map.get("netId"))?"":map.get("netId").toString();
				 ehcacheService.remove("parcelIdCacheByExpWayBillNumAndNetId", expWayBillNum+netId);
	    		ParParcelinfo parParcelinfo = parcelInfoService.getParcelInfoById(Long.parseLong(ids[i]));  //得到包裹信息   ---jinggq
	    		ParParceladdress parParceladdress = parcelInfoService.getParParceladdressById(Long.parseLong(ids[i]));   //得到包裹地址  --jinggq
	    		this.addOrUpdateSendTask(Long.parseLong(ids[i]),1,parParcelinfo.getCompId(), parParcelinfo.getNetId(),parParcelinfo.getGoodsNum(),parParcelinfo.getChareWeightForsender()==null?0.00:parParcelinfo.getChareWeightForsender().doubleValue(), null, null, -1, 11, 0, null, memberId, memberPhone, parParceladdress.getAddresseeName(),parParceladdress.getAddresseeMobile(), parParceladdress.getAddresseePhone(), parParceladdress.getAddresseeAddressId(), parParceladdress.getAddresseeAddress(), null, null, null, new Date(), memberId, 0, new Date(), null, null);
	    		//5.更新包裹异常时间 因为包裹异常后更新包裹状态后不再是异常包裹   --jinggq
				parParcelinfoMapper.updateExceptionTime(Long.parseLong(ids[i]));
	    	}
		    return jsonSuccess(null);
	}
	
	/**
	 * @Method: querySendTaskList 
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.SendTaskService#querySendTaskList(java.lang.Long) 
	*/
	@Override
	public String querySendTaskList(Long memberId) {
		String str = ehcacheService.get("sendTaskCache", memberId+"", String.class);
		if(!PubMethod.isEmpty(str) && !"[]".equals(str)){
			List<Map> listData = JSON.parseArray(str, Map.class);
			Map<String,Object> dateMap = new HashMap<String,Object> ();
			dateMap.put("resultlist", listData);
			return jsonSuccess(dateMap);
		}else{
		List <Map<String,Object>>list = sendTaskMapper.querySendTaskList(memberId);
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("resultlist", list);
		String result = JSON.toJSONString(map,s);
		ehcacheService.put("sendTaskCache", memberId+"",list);
		return jsonSuccess(map);
		}
		}

	/**
	 * @Method: finishTask 
	 * @param taskId
	 * @return 
	 * @see net.okdi.api.service.SendTaskService#finishTask(java.lang.Long) 
	*/
	@Override
	public String finishTask(Long taskId,String parcelIds,Double totalCodAmount,Double totalFreight,Short type,Long memberId,String expWaybillNum) {
		ehcacheService.remove("sendTaskCache", memberId+"");
		ehcacheService.remove("queryAlreadySignList", String.valueOf(memberId));
		dispatchParService.removeTaskCache();
		expressPriceService.settleAccounts(parcelIds, totalCodAmount, totalFreight, memberId, type);
		if(!PubMethod.isEmpty(taskId)){
			sendTaskMapper.finishSendTask(taskId);
		}
		return jsonSuccess(null);
	}
	
	@Override
	public String cancelSendTask(Long taskId, Long memberId, Long parcelId, Long Id ,String cancelType,Long compId,String compName,String textValue) throws RuntimeException{
		ehcacheService.remove("sendTaskCache", String.valueOf(memberId));
		dispatchParService.removeTaskCache();
		ehcacheService.remove("queryAlreadySignList", String.valueOf(memberId));
		HashMap<String, String> reasonMap = new HashMap<String, String>();
		reasonMap.put( "1", "客户取消");
		reasonMap.put("2", "联系不上客户");
		reasonMap.put("3", "其他原因");
		reasonMap.put("10", "超出本网点范围");
		reasonMap.put("11", "网点任务太多，忙不过来");
		reasonMap.put("12", "超出收派范围");
		reasonMap.put( "13", "忙不过来");
		reasonMap.put( "15", textValue);
		TaskVo task = new TaskVo();
		task.setTaskId(taskId);
		task.setMemberId(memberId);
		task.setCompId(compId);
		task.setDisposalObject((byte)0);
		task.setShowFlag((byte)0);
		task.setTaskErrorFlag(((byte)1));
		String reason = "";
		if("3".equals(cancelType)){
			reason = reasonMap.get("15");
			task.setDisposalDesc(compName+":"+reasonMap.get("15"));
		} else {
			reason = reasonMap.get(cancelType);
			task.setDisposalDesc(compName+":"+reasonMap.get(cancelType));
		}
		if(PubMethod.isEmpty(Id)) {
			Map map  = parTaskDisposalRecordMapper.queryIdByTaskId(taskId);
			if(!PubMethod.isEmpty(map)){
				 Id = PubMethod.isEmpty(map.get("id"))?null:Long.parseLong(map.get("id").toString());
			}
		}
		if(!PubMethod.isEmpty(Id)){
			ParTaskDisposalRecord selectByPrimaryKey = parTaskDisposalRecordMapper.selectByPrimaryKey(Id);
			if(!PubMethod.isEmpty(selectByPrimaryKey) && selectByPrimaryKey.getDisposalType() == 3){
				throw new ServiceException("openapi.SendTaskService.cancelSendTask.001", "不能重复取消"); 
			}
			task.setId(Id);
			ParTaskDisposalRecord parTaskDisposalRecord = new ParTaskDisposalRecord();
			BeanUtils.copyProperties( parTaskDisposalRecord, task);
			parTaskDisposalRecord.setDisposalType((byte)3);
			task.setModifiedTime(new Date());
			parTaskDisposalRecordMapper.updateByPrimaryKey(parTaskDisposalRecord);
		} else {
			task.setCreateTime(new Date());
			task.setTaskStatus((byte)3);
			task.setId(IdWorker.getIdWorker().nextId());
			parTaskDisposalRecordMapper.insert(task);
		}
		parcelInfoService.updateParcelStatus(parcelId, memberId,reason); //Edit by ccs 20150320 原因，包裹表增加字段error_message
		sendTaskMapper.updateTaskStatus(taskId);
		return jsonSuccess(null);
	}

}
