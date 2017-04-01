package net.okdi.mob.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.RobInfoHttpClient;
import net.okdi.mob.service.BaiDuClient;
import net.okdi.mob.service.RobInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author haifeng.he
 * @version V1.0
 */
@Service
public class RobInfoServiceImpl implements RobInfoService{

	@Autowired
	private RobInfoHttpClient robInfoHttpClient;
	@Autowired
	private BaiDuClient baiduClient;
	/**
	 * 查询抢单广播信息
	 * @Method: doQueryRobInfo 
	 * @param systemType										--系统类型 1是站点 2是手机端
	 * @param memberId											--人员id 
	 * @return 
	 * @see net.okdi.mob.service.RobInfoService#doQueryRobInfo(java.lang.Short, java.lang.Long)
	 */
	@Override
	public String doQueryRobInfo(Short systemType, Long memberId) {
		return robInfoHttpClient.queryRobInfo(systemType, memberId);
	}
	/**
	 * 抢单之前校验该条广播记录的状态以及报价是否符合条件
	 * @Method: isRightConditions 
	 * @param broadcastId  --广播id
	 * @param quotationAmt --报价金额
	 * @param quotationId --报价表的id
	 * @return 
	 * @see net.okdi.mob.service.RobInfoService#isRightConditions(java.lang.Long, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public String isRightConditions(Long robId, BigDecimal quotationAmt,Long quotationId) {
		return robInfoHttpClient.isRightConditions(robId,quotationAmt,quotationId);
	}
	/**
	 * 报价抢单
	 * @Method: robExpress 
	 * @param userId			--广播推送给的人员id
	 * @param broadcastType		--广播来源 1:个人端 2:电商管家
	 * @param loginMemberId		--发布抢单广播的客户id(													
	 * @param compId			--站点id
	 * @param broadcastId		--广播id
	 * @param quotationAmt		--报价金额
	 * @param takeMemberId		--指定的取件员
	 * @param quotationType		--报价类型 1站点报价、2收派员报价
	 * @return 
	 * @see net.okdi.mob.service.RobInfoService#robExpress(java.lang.Long, java.lang.Long, java.lang.Short, java.lang.Long, java.lang.Long, java.lang.Long, java.math.BigDecimal, java.lang.Long, java.lang.Short)
	 */
	@Override
	public String robExpress(Long userId,Long quotationId,Short broadcastType,Long loginMemberId,Long compId, Long broadcastId, BigDecimal quotationAmt, Long takeMemberId, Short quotationType) {
		return robInfoHttpClient.robExpress(userId,quotationId,broadcastType,loginMemberId,compId,broadcastId,quotationAmt,takeMemberId,quotationType);
	}
	/**
	 * 根据广播id计算最低报价
	 * @Method: getLowestPrice 
	 * @param broadcastId  --广播id
	 * @return 
	 * @see net.okdi.mob.service.RobInfoService#getLowestPrice(java.lang.Long)
	 */
	@Override
	public String getLowestPrice(Long broadcastId) {
		return robInfoHttpClient.getLowestPrice(broadcastId);
	}
	
	
	@Override
	public String createRobAmssy(String senderName,String senderMobile,Long senderAddressId,String senderAddressName,
			String senderDetailAddressName,Integer parcelTotalCount,
			BigDecimal parcelTotalWeight,String broadcastRemark,BigDecimal quotationAmount,
			String parcelsJsonStr,String appointTime){
		double senderLongitude = 0;
		double senderLatitude = 0;
		try{
			Double[]location = baiduClient.getResult((PubMethod.isEmpty(senderAddressName) ?  "" : senderAddressName)+ (PubMethod.isEmpty(senderDetailAddressName) ?  "" : senderDetailAddressName));
			senderLongitude = location[0];
			senderLatitude = location[1];
		}catch(Exception e){
			
		}
		return robInfoHttpClient.createRob(senderName, senderMobile, senderAddressId, senderAddressName,
				senderDetailAddressName, BigDecimal.valueOf(senderLongitude),BigDecimal.valueOf(senderLatitude), parcelTotalCount, parcelTotalWeight, 
				broadcastRemark, quotationAmount, (short)1, parcelsJsonStr,null,appointTime);//广播来源 0:个人端 1电商管家
	}
	@Override
	public String cancelRobAmssy(Long broadcastId){
		return robInfoHttpClient.cancelRob(broadcastId, null);
	}
	@Override
	public String queryRob(Long memberId,double longitude,double latitude){
		return robInfoHttpClient.queryRob(memberId, longitude , latitude);
	}
	@Override
	public String queryRobByBroadcastId(Long broadcastId){
		return robInfoHttpClient.queryRobByBroadcastId(broadcastId);
	}
	@Override
	public String rob(Long broadcastId,Long memberId){
		return robInfoHttpClient.rob(broadcastId,memberId);
	}
}
