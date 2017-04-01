package net.okdi.mob.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 
 * @author haifeng.he
 * @version V1.0
 */
public interface RobInfoService {
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询抢单广播信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:04:39</dd>
	 * @param systemType										--系统类型 1是站点 2是手机端
	 * @param memberId											--人员id 
	 * @return
	 * @since v1.0
	 */
	public String doQueryRobInfo(Short systemType, Long memberId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单之前校验该条广播记录的状态以及报价是否符合条件</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:08:40</dd>
	 * @param broadcastId  --广播id
	 * @param quotationAmt --报价金额
	 * @param quotationId --报价表的id
	 * @return
	 * @since v1.0
	 */
	public String isRightConditions(Long robId, BigDecimal quotationAmt,Long quotationId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>报价抢单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:14:50</dd>
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
	public String robExpress(Long userId,Long quotationId,Short broadcastType,Long loginMemberId,Long compId, Long broadcastId, BigDecimal quotationAmt, Long takeMemberId, Short quotationType);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据广播id计算最低报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:12:53</dd>
	 * @param broadcastId  --广播id
	 * @return
	 * @since v1.0
	 */
	public String getLowestPrice(Long broadcastId);
	
	
	
	
	
	
	
	
	
	public String createRobAmssy(String senderName,String senderMobile,Long senderAddressId,String senderAddressName,
			String senderDetailAddressName,Integer parcelTotalCount,
			BigDecimal parcelTotalWeight,String broadcastRemark,BigDecimal quotationAmount,
			String parcelsJsonStr,String appointTime);
	public String cancelRobAmssy(Long broadcastId);
	public String queryRob(Long memberId,double longitude,double latitude);
	public String queryRobByBroadcastId(Long broadcastId);
	public String rob(Long broadcastId,Long memberId);
}
