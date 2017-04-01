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
package net.okdi.mob.service;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ExpParGatewayService
 * @Description TODO
 * @author chuanshi.chai
 * @date 2014-11-4
 * @since jdk1.6
*/
public interface ExpParGatewayService {
	
	String decideGoods(Long channelId,String expWaybillNum,Long netId);
	
	String addParLogisticSearch(Long channelId,Long netId,String expWaybillNum,
			String traceStatus,String traceDetail,
			Long appointId,String recMobile,String systemMark,String expType);
	String findParLogList(Long channelId, String expType);
	String queryRelevantAddressList(Long townId,String keyword,Integer count);
	String parseAddrNew(String lat,String lng);
	
	String createAppoint(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId,
			String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId,
			BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude,BigDecimal parEstimateWeight,Byte parEstimateCount,Long broadcastId);
	String cancelMember(String taskId, Long memberId);
	String queryRefusetask(String senderName, String startTime, String endTime, String senderPhone,
			 Long operatorCompId, Integer currentPage, Integer pageSize) ;
	String getMemberInfoById(Long memberId);
	String queryTaskDetail(Long id);
	String finishTask(Long taskId, Long memberId, Long compId);
	
	String queryCompBasicInfo(Long loginCompId);
	String queryNearMember(double lng, double lat, Long townId,
			Long streetId, Long netId, int sortFlag,int howFast);
	String queryNearComp(Double lng,Double lat,Long recProvince,Long sendProvince,Double weight,Long netId,Long sendTownId,Long streetId);
	
	String getStationMember(Long compId);
	
	String queryPersonalTask(Long memberId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询发件包裹列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-23 下午8:31:12</dd>
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public String queryPersonalParcelList(Long memberId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询包裹详情</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-2 上午10:52:47</dd>
	 * @param parcelId
	 * @return
	 * @since v1.0
	 */
	public String queryParcelDetail(Long parcelId);

	String updateParLogList(Long id, String traceStatus, String traceDetail);
}
