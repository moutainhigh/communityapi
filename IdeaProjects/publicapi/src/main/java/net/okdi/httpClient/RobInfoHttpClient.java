package net.okdi.httpClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;

public class RobInfoHttpClient extends AbstractHttpClient{
	
	@Autowired
	private ConstPool constPool; 
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询抢单广播信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:06:00</dd>
	 * @param systemType										--系统类型 1是站点 2是手机端
	 * @param memberId											--人员id 
	 * @return
	 * @since v1.0
	 */
	public String queryRobInfo(Short systemType,Long memberId){
		if(PubMethod.isEmpty(systemType)){
			return PubMethod.paramsFailure("publicapi.RobInfoHttpClient.queryRobInfo.001", "systemType不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramsFailure("publicapi.RobInfoHttpClient.queryRobInfo.002", "memberId不能为空");
		}
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("systemType", String.valueOf(systemType));
			map.put("memberId", String.valueOf(memberId));
			String url = constPool.getOpenApiUrl() + "robInfo/queryRobInfo";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单之前校验该条广播记录的状态以及报价是否符合条件</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:10:12</dd>
	 * @param broadcastId  --广播id
	 * @param quotationAmt --报价金额
	 * @param quotationId --报价表的id
	 * @return
	 * @since v1.0
	 */
	public String isRightConditions(Long broadcastId, BigDecimal quotationAmt,Long quotationId) {
		if(PubMethod.isEmpty(broadcastId)){
			return PubMethod.paramsFailure("publicapi.RobInfoHttpClient.isRightConditions.001", "broadcastId不能为空");
		}
		if(PubMethod.isEmpty(quotationAmt)){
			return PubMethod.paramsFailure("publicapi.RobInfoHttpClient.isRightConditions.002", "quotationAmt不能为空");
		}
		if(PubMethod.isEmpty(quotationId)){
			return PubMethod.paramsFailure("publicapi.RobInfoHttpClient.isRightConditions.003", "quotationId不能为空");
		}
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("broadcastId", String.valueOf(broadcastId));
			map.put("quotationAmt", String.valueOf(quotationAmt));
			map.put("quotationId", String.valueOf(quotationId));
			String url = constPool.getOpenApiUrl() + "robInfo/isRightConditions";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>报价抢单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:16:28</dd>
	 * @param userId			--广播推送给的人员id
	 * @param broadcastType		--广播来源 1:个人端 2:电商管家
	 * @param loginMemberId		--发布抢单广播的客户id(													
	 * @param compId			--站点id
	 * @param broadcastId		--广播id
	 * @param quotationAmt		--报价金额
	 * @param takeMemberId		--指定的取件员
	 * @param quotationType		--报价类型 1站点报价、2收派员报价
	 * @return
	 * @since v1.0
	 */
	public String robExpress(Long userId,Long quotationId,Short broadcastType,Long loginMemberId,Long compId, Long broadcastId, BigDecimal quotationAmt, Long takeMemberId, Short quotationType) {
		if(PubMethod.isEmpty(broadcastId)){
			return PubMethod.paramsFailure("publiapi.RobInfoHttpClient.robExpress.001", "broadcastId不能为空");
		}
		if(PubMethod.isEmpty(broadcastType)){
			return PubMethod.paramsFailure("publiapi.RobInfoHttpClient.robExpress.002", "broadcastType不能为空");
		}
		if(PubMethod.isEmpty(loginMemberId)){
			return PubMethod.paramsFailure("publiapi.RobInfoHttpClient.robExpress.003", "loginMemberId不能为空");
		}
		if(PubMethod.isEmpty(quotationAmt)){
			return PubMethod.paramsFailure("publiapi.RobInfoHttpClient.robExpress.004", "quotationAmt不能为空");
		}
		if(PubMethod.isEmpty(quotationType)){
			return PubMethod.paramsFailure("publiapi.RobInfoHttpClient.robExpress.005", "quotationType不能为空");
		}
		if(PubMethod.isEmpty(quotationId)){
			return PubMethod.paramsFailure("publiapi.RobInfoHttpClient.robExpress.006", "quotationId不能为空");
		}
		if(PubMethod.isEmpty(userId)){
			return PubMethod.paramsFailure("publiapi.RobInfoHttpClient.robExpress.007", "userId不能为空");
		}
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("compId", String.valueOf(compId));
			map.put("broadcastId", String.valueOf(broadcastId));
			map.put("loginMemberId", String.valueOf(loginMemberId));
			map.put("quotationAmt", String.valueOf(quotationAmt));
			map.put("takeMemberId", String.valueOf(takeMemberId));
			map.put("quotationType", String.valueOf(quotationType));
			map.put("broadcastType", String.valueOf(broadcastType));
			map.put("quotationId", String.valueOf(quotationId));
			map.put("userId", String.valueOf(userId));
			String url = constPool.getOpenApiUrl() + "robInfo/robExpress";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据广播id计算最低报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:14:01</dd>
	 * @param broadcastId  --广播id
	 * @return
	 * @since v1.0
	 */
	public String getLowestPrice(Long broadcastId) {
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("broadcastId", String.valueOf(broadcastId));
			String url = constPool.getOpenApiUrl() + "robInfo/getLowestPrice";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	
	
	
	
	
	public String createRob(String senderName,String senderMobile,Long senderAddressId,String senderAddressName,
			String senderDetailAddressName,BigDecimal senderLongitude,BigDecimal senderLatitude,Integer parcelTotalCount,
			BigDecimal parcelTotalWeight,String broadcastRemark,BigDecimal quotationAmount,short broadcastSource,
			String parcelsJsonStr,Long createUser,String appointTime){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("senderName", senderName);
			map.put("senderMobile", senderMobile);
			map.put("senderAddressId",PubMethod.isEmpty(senderAddressId) ? null : String.valueOf(senderAddressId));
			map.put("senderAddressName",senderAddressName);
			map.put("senderDetailAddressName", senderDetailAddressName);
			map.put("senderLongitude", PubMethod.isEmpty(senderLongitude) ? null :String.valueOf(senderLongitude));
			map.put("senderLatitude", PubMethod.isEmpty(senderLatitude) ? null :String.valueOf(senderLatitude));
			map.put("parcelTotalCount", PubMethod.isEmpty(parcelTotalCount) ? null :String.valueOf(parcelTotalCount));
			map.put("parcelTotalWeight", PubMethod.isEmpty(parcelTotalWeight) ? null :String.valueOf(parcelTotalWeight));
			map.put("broadcastRemark", broadcastRemark);
			map.put("quotationAmount", PubMethod.isEmpty(quotationAmount) ? null : String.valueOf(quotationAmount));
			map.put("broadcastSource", PubMethod.isEmpty(broadcastSource) ? "-1" : String.valueOf(broadcastSource));
			map.put("parcelsJsonStr", parcelsJsonStr);
			map.put("createUser", PubMethod.isEmpty(createUser) ? null : String.valueOf(createUser));
			map.put("appointTime", appointTime);
			String url = constPool.getOpenApiUrl() + "robInfo/createRob";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	public String cancelRob(Long broadcastId,Long memberId){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("broadcastId", PubMethod.isEmpty(broadcastId) ? null :String.valueOf(broadcastId));
			map.put("memberId",PubMethod.isEmpty(memberId) ? null : String.valueOf(memberId));
			String url = constPool.getOpenApiUrl() + "robInfo/cancelRob";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	public String queryRob(Long memberId,double longitude,double latitude){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("memberId",PubMethod.isEmpty(memberId) ? null : String.valueOf(memberId));
			map.put("longitude",String.valueOf(longitude));
			map.put("latitude",String.valueOf(latitude));
			String url = constPool.getOpenApiUrl() + "robInfo/queryRob";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	public String queryRobByBroadcastId(Long broadcastId){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("broadcastId",PubMethod.isEmpty(broadcastId) ? null : String.valueOf(broadcastId));
			String url = constPool.getOpenApiUrl() + "robInfo/queryRobByBroadcastId";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	public String rob(Long broadcastId,Long memberId){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("broadcastId",PubMethod.isEmpty(broadcastId) ? null : String.valueOf(broadcastId));
			map.put("memberId",PubMethod.isEmpty(memberId) ? null : String.valueOf(memberId));
			String url = constPool.getOpenApiUrl() + "robInfo/rob";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
}
