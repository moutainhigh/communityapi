/**  
 * @Project: mob
 * @Title: ExpParGatewayService.java
 * @Package net.okdi.mob.service
 * @Description: TODO
 * @author chuanshi.chai
 * @date 2014-11-4 上午9:17:48
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.OpenDataException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.GetPicConPath;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.entity.BasOnlineMember;
import net.okdi.mob.service.ExpParGatewayService;

/**
 * @ClassName ExpParGatewayService
 * @Description TODO
 * @author chuanshi.chai
 * @date 2014-11-4
 * @since jdk1.6
*/
@Service("expParGatewayService")
public class ExpParGatewayServiceImpl implements ExpParGatewayService{

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Autowired
	private ConstPool constPool;
	
//	@Autowired
//	private BasOnlineMemberMapper basOnlineMemberMapper;
	
	/**
	 * @Method: createAppoint 
	 * @Description: 
	 * @param fromCompId
	 * @param fromMemberId
	 * @param toCompId
	 * @param toMemberId
	 * @param coopNetId
	 * @param appointTime
	 * @param appointDesc
	 * @param actorMemberId
	 * @param contactName
	 * @param contactMobile
	 * @param contactTel
	 * @param contactAddressId
	 * @param contactAddress
	 * @param customerId
	 * @param createUserId
	 * @param contactAddrLongitude
	 * @param contactAddrLatitude
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#createAppoint(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.math.BigDecimal, java.math.BigDecimal) 
	 * @since jdk1.6
	*/
	@Override
	public String createAppoint(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId,
			String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId,
			BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude,BigDecimal parEstimateWeight,Byte parEstimateCount,Long broadcastId) {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("fromCompId", fromCompId);
		map.put("fromMemberId", fromMemberId);
		//map.put("toCompId", toCompId);
		map.put("toMemberId", toMemberId);
		map.put("coopNetId", coopNetId);
		map.put("appointTime", appointTime);
		map.put("appointDesc", appointDesc);
		map.put("actorMemberId", actorMemberId);
		map.put("contactName", contactName);
		map.put("contactMobile", contactMobile);
		map.put("contactTel", contactTel);
		map.put("contactAddressId", contactAddressId);
		map.put("contactAddress", contactAddress);
		map.put("customerId", customerId);
		map.put("createUserId", createUserId);
		map.put("contactAddrLongitude", contactAddrLongitude);
		map.put("contactAddrLatitude", contactAddrLatitude);
		map.put("parEstimateWeight", parEstimateWeight);
		map.put("parEstimateCount", parEstimateCount);
		map.put("broadcastId", broadcastId);
		String result = openApiHttpClient.doPassSendStr("task/create/personal", map);
		return result;
	}

	/**
	 * @Method: decideGoods 
	 * @Description: TODO
	 * @param channelId
	 * @param expWaybillNum
	 * @param netId
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#decideGoods(java.lang.Long, java.lang.String, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String decideGoods(Long memberId, String expWaybillNum, Long netId) {
		Map map = new HashMap();
		map.put("channelId", memberId);
		map.put("expWaybillNum", expWaybillNum);
		map.put("netId", netId);
		String result = openApiHttpClient.doPassSendStr("logisticSearch/decideGoods", map);
		return result;
	}

	/**
	 * 加到我收的快递
	 * @Method: saveReceive 
	 * @Description: TODO
	 * @param channelId
	 * @param netId
	 * @param expWaybillNum
	 * @param traceStatus
	 * @param traceDetail
	 * @param appointId
	 * @param recMobile
	 * @param systemMark
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#saveReceive(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public String addParLogisticSearch(Long channelId, Long netId, String expWaybillNum, String traceStatus, String traceDetail,
			Long appointId, String recMobile, String systemMark,String expType) {
		Map map = new HashMap();
		map.put("channelId", channelId);
		map.put("netId", netId);
		map.put("expWaybillNum", expWaybillNum);
		map.put("traceStatus",traceStatus );
		map.put("traceDetail",traceDetail );
		map.put("systemMark", "01");
		String result = null;
		if("0".equals(expType)){
			result = openApiHttpClient.doPassSendStr("logisticSearch/save/send", map);
		}else if("1".equals(expType)){
			result = openApiHttpClient.doPassSendStr("logisticSearch/save/receive", map);
		}
		return result;
	}

	/**
	 * 收发快递列表
	 * @Method: findParLogList 
	 * @Description: TODO
	 * @param channelId	用户ID
	 * @param expType	0发快递  1收快递
	 * @return 	list
	 * @see net.okdi.mob.service.ExpParGatewayService#findParLogList(java.lang.Long, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public String findParLogList(Long channelId, String expType) {
		Map map = new HashMap();
		map.put("channelId", channelId);
		map.put("expType", expType);
		String result  = openApiHttpClient.doPassSendStr("logisticSearch/list", map);
//		if("0".equals(expType)){
//			Map taskMap = new HashMap();
//			taskMap.put("memberId", channelId);
//			taskMap.put("currentPage", "1");
//			taskMap.put("pageSize", "100");
//			///query/okditask
//			//Long memberId, Integer currentPage, Integer pageSize
//			String taskResult = openApiHttpClient.doPassSendStr("task/query/personaltask", taskMap);
//			JSONObject jstask = JSONObject.parseObject(taskResult);
//			if("true".equals(jstask.get("success").toString())){
//				JSONObject jtotal = JSONObject.parseObject(result);
//				jtotal.put("taskData", jstask.get("data"));
//				return JSON.toJSONString(jtotal);
//			}
//			//System.out.println(taskResult);
//		}
		return result;
	}
	/**
	 * 地址联想
	 * @Method: findParLogList 
	 * @Description: TODO
	 * @param channelId
	 * @param expType
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#findParLogList(java.lang.Long, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public String queryRelevantAddressList(Long townId,String keyword,Integer count) {
		Map map = new HashMap();
		map.put("townId", townId);
		map.put("keyword", keyword);
		map.put("count", count);
		String result  = openApiHttpClient.doPassSendStr("address/queryRelevantAddressList", map);
		return result;
	}

	@Override
	public String parseAddrNew(String lat, String lng) {
		//2015-07-18 杨凯 之前如果经度和纬度都是0，那么返回的地址是台湾，但是我们想让他都是0的时候返回北京，所以就强行把00改成北京的经纬度
		if (lat != null && lng != null) {
			if (lat.equals("0") && lng.equals("0")) {
				lat = "39.90960456049752";
				lng = "116.3972282409668";
			}
		}
		Map map = new HashMap();
		map.put("lat", lat);
		map.put("lng", lng);
		String result  = openApiHttpClient.doPassSendStr("expGateway/parseAddrNew", map);
		return result;
	}

	@Override
	public String cancelMember(String taskId, Long memberId) {
		Map map = new HashMap();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		String result  = openApiHttpClient.doPassSendStr("task/cancelTaskOkdi", map);
		return result;
	}
	@Override
	public String queryRefusetask(String senderName, String startTime, String endTime, String senderPhone,
			 Long operatorCompId, Integer currentPage, Integer pageSize) {
		Map map = new HashMap();
		map.put("senderName", senderName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("senderPhone", senderPhone);
		map.put("operatorCompId", operatorCompId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String result  = openApiHttpClient.doPassSendStr("task/query/refusetask", map);
		return result;
	}

	/**
	 * @Method: getMemberInfoById 
	 * @Description: TODO
	 * @param memberId
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#getMemberInfoById(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String getMemberInfoById(Long memberId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		String result  = openApiHttpClient.doPassSendStr("memberInfo/getMemberInfoById", map);
		return result;
	}

	/**
	 * @Method: queryTaskDetail 
	 * @Description: TODO
	 * @param taskId
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#queryTaskDetail(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String queryTaskDetail(Long taskId) {
		Map map = new HashMap();
		map.put("taskId", taskId);
		String result  = openApiHttpClient.doPassSendStr("task/okdiTaskDetail", map);
		return result;
	}

	/**
	 * 确认 -- 交寄确认
	 * @Method: finishTask 
	 * @Description: 新版是完成任务
	 * @param taskId
	 * @param memberId
	 * @param compId
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#finishTask(java.lang.Long, java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public String finishTask(Long taskId, Long memberId, Long compId) {
//		Map map = new HashMap();
//		map.put("taskId", taskId);
//		map.put("memberId", memberId);
//		map.put("compId", compId);
//		String result  = openApiHttpClient.doPassSendStr("task/finish/task", map);
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("taskId",taskId);
		  return openApiHttpClient.doPassSendStr("task/finish/taskPersonal",paraMeterMap);
	}
	/**
	 * @Method: queryCompBasicInfo 
	 * @Description: TODO
	 * @param loginCompId
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#queryCompBasicInfo(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String queryCompBasicInfo(Long compId) {
		Map map = new HashMap();
		map.put("loginCompId", compId);
		//String result  = openApiHttpClient.doPassSendStr("compInfo/queryCompBasicInfo", map);
		Map myRsMap  = openApiHttpClient.doPassSendObj("compInfo/queryCompBasicInfo", map);
		//System.out.println(":::::: "+myRsMap);
		//{"data":{"address":"北京-海淀区|田村路43号","addressId":11000206,"compId":14384014995751936,"compName":"我爱申通1","compStatus":1,"compTelephone":"010-66162328","compTypeNum":"1006","latitude":39.935660,"longitude":116.263574,"netId":1500,"netName":"申通快递"},"success":true}
		
		//List<BasOnlineMember> lsOnline = null;//basOnlineMemberMapper.queryByCompId(compId);
		//JSONObject jo = JSONObject.parseObject(result);
		//myRsMap.put("onlineNum", lsOnline.size());
		return JSON.toJSONString(myRsMap);
		//return result;
	}

	/**
	 * @Method: queryNearMember 
	 * @Description: TODO
	 * @param lng
	 * @param lat
	 * @param townId
	 * @param streetId
	 * @param netId
	 * @param sortFlag
	 * @param howFast
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#queryNearMember(double, double, java.lang.Long, java.lang.Long, java.lang.Long, int, int) 
	 * @since jdk1.6
	*/
	@Override
	public String queryNearMember(double lng, double lat, Long townId, Long streetId, Long netId, int sortFlag,
			int howFast) {
		Map map = new HashMap();
		map.put("lng", lng);
		map.put("lat", lat);
		map.put("townId", townId);
		map.put("streetId", streetId);
		map.put("netId",netId );
		map.put("sortFlag",sortFlag );
		map.put("howFast",howFast );
		String result  = openApiHttpClient.doPassSendStr("Courier/queryNearMember", map);
		return result;
	}

	/**
	 * @Method: queryNearComp 
	 * @Description: TODO
	 * @param lng
	 * @param lat
	 * @param recProvince
	 * @param sendProvince
	 * @param weight
	 * @param netId
	 * @param sendTownId
	 * @param streetId
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#queryNearComp(java.lang.Double, java.lang.Double, java.lang.Long, java.lang.Long, java.lang.Double, java.lang.Long, java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String queryNearComp(Double lng, Double lat, Long recProvince, Long sendProvince, Double weight, Long netId,
			Long sendTownId, Long streetId) {
		Map map = new HashMap();
		map.put("lng", lng);
		map.put("lat", lat);
		map.put("recProvince", recProvince);
		map.put("sendProvince", sendProvince);
		map.put("weight",weight );
		map.put("netId",netId );
		map.put("sendTownId",sendTownId);
		map.put("streetId",streetId );
		String result  = openApiHttpClient.doPassSendStr("Courier/queryNearComp", map);
		JSONObject jo =JSON.parseObject(result);
		jo.put("basicUrl", constPool.getReadPath()+constPool.getNet());
		return JSON.toJSONString(jo);
	}

	@Override
	public String getStationMember(Long compId) {
		Map map = new HashMap<String,Object>();
		List<BasOnlineMember> lsOnline =null;// basOnlineMemberMapper.queryByCompId(compId);
		if(lsOnline ==null ){
			lsOnline  = new ArrayList<BasOnlineMember>();
		}else{
			for(BasOnlineMember b:lsOnline){
				b.setMemberUrl(constPool.getReadPath()+constPool.getHead()+b.getMemberId()+".jpg");
			}
		}
		map.put("data", lsOnline);
		map.put("success", true);
		return JSON.toJSONString(map);
	}

	@Override
	public String queryPersonalTask(Long memberId){
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("currentPage", 1);
		map.put("pageSize", 10000);
		//{"data":{"currentPage":1,"hasFirst":false,"hasLast":true,"hasNext":true,"hasPre":false,"items":[{"compId":118557476438016,"compName":"网点名称网点名称","compPhone":"18810104858","compType":"已认证","contactAddress":"北京市北京市区城区 海淀区玉泉路","contactMobile":"18601124718","contactName":"未填写","createTime":"2014-12-22 13:59:01","memberName":"","netName":"顺丰速运","taskId":120532133789696,"taskStatus":0,"taskTransmitCause":""},{"compId":118561159036928,"compName":"站点AB11","compPhone":"15399991111","compType":"已认证","contactAddress":"北京市海淀区 田村路23号","contactMobile":"18601124718","contactName":"未填写","createTime":"2014-12-22 14:15:03","memberName":"小六","netName":"凡宇速递","taskId":120534151249920,"taskStatus":1,"taskTransmitCause":""},{"compId":118561159036928,"compName":"站点AB11","compPhone":"15399991111","compType":"已认证","contactAddress":"北京市北京市区城区 海淀区玉泉路","contactMobile":"18601124718","contactName":"未填写","createTime":"2014-12-22 15:55:32","memberName":"","netName":"凡宇速递","taskId":120546795315200,"taskStatus":3,"taskTransmitCause":3},{"compId":118561159036928,"compName":"站点AB11","compPhone":"15399991111","compType":"已认证","contactAddress":"北京市北京市区城区 海淀区玉泉路","contactMobile":"18601124717","contactName":"未填写","createTime":"2014-12-22 16:47:48","memberName":"","netName":"凡宇速递","taskId":120553371648000,"taskStatus":3,"taskTransmitCause":1},{"compId":118561159036928,"compName":"站点AB11","compPhone":"15399991111","compType":"已认证","contactAddress":"北京市北京市区城区 海淀区玉泉路","contactMobile":"18601124717","contactName":"未填写","createTime":"2014-12-22 17:01:28","memberName":"","netName":"凡宇速递","taskId":120555091312640,"taskStatus":3,"taskTransmitCause":2},{"compId":118561159036928,"compName":"站点AB11","compPhone":"15399991111","compType":"已认证","contactAddress":"北京市北京市区城区 海淀区玉泉路","contactMobile":"18000000000","contactName":"未填写","createTime":"2014-12-22 17:02:56","memberName":"","netName":"凡宇速递","taskId":120555275862016,"taskStatus":3,"taskTransmitCause":11},{"compId":118561159036928,"compName":"站点AB11","compPhone":"15399991111","compType":"已认证","contactAddress":"北京市北京市区城区 海淀区玉泉路","contactMobile":"18000000000","contactName":"未填写","createTime":"2014-12-22 17:11:24","memberName":"小七","netName":"凡宇速递","taskId":120556341215232,"taskStatus":2,"taskTransmitCause":""}],"offset":0,"pageCount":0,"pageSize":100,"rows":[],"total":0},"success":true}
		//String result  = openApiHttpClient.doPassSendStr("task/query/personaltask", map);
		String result  = openApiHttpClient.doPassSendStr("task/query/personaltask", map);
		JSONObject finObj = JSONObject.parseObject(result);
		JSONArray ja = finObj.getJSONObject("data").getJSONArray("items");
		for(int i = ja.size()-1 ;i>=0;i--){
			if("2".equals(ja.getJSONObject(i).getString("taskStatus"))){
				ja.remove(i);
			}
		}
		finObj.getJSONObject("data").put("items", ja);
		
		return JSON.toJSONString(finObj);
	}
	
	@Override
	public String queryPersonalParcelList(Long memberId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		String result  = openApiHttpClient.doPassSendStr("parcelInfo/querySendParcelList", map);
		return result;
	}

	@Override
	public String queryParcelDetail(Long parcelId) {
		Map map = new HashMap();
		map.put("parcelId", parcelId);
		map.put("compId", "");
		map.put("status", "");
		String result  = openApiHttpClient.doPassSendStr("parcelInfo/queryParcelDetail", map);
		return result;
	}

	@Override
	public String updateParLogList(Long id, String traceStatus, String traceDetail) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("traceStatus", traceStatus);
		map.put("traceDetail", traceDetail);
		String result  = openApiHttpClient.doPassSendStr("logisticSearch/update", map);
		return result;
	}
	
	
}