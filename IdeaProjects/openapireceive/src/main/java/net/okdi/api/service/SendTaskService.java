/**  
 * @Project: openapi
 * @Title: SendTaskService.java
 * @Package net.okdi.api.service
 * @author amssy
 * @date 2014-12-9 下午07:16:04
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
 */
package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author amssy
 * @version V1.0
 */
public interface SendTaskService {
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>添加派件任务</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 上午11:30:06</dd>
     * @param parcelId          '包裹ID',
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
     * @since v1.0
     */
	public String addSendTask(Long parcelId,Integer taskType, Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc, Integer taskSource,
			Integer taskStatus, Integer taskIsEnd, Date taskEndTime,
			Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Date createTime, Long createUserId,
			Integer taskFlag, Date modifyTime, Double contactAddrLongitude,
			Double contactAddrLatitude);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>批量转单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午03:33:48</dd>
	 * @param parcelIds 待提包裹ID串
	 * @param memberId 收派员I
	 * @return
	 * @since v1.0
	 */
    public String changSendPerson(String parcelIds,Long memberId,Long oldMemberId);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>判断包裹是否已经提货</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午03:46:51</dd>
     * @param parcelId 包裹ID
     * @return
     * @since v1.0
     */
    public String ifHasPickUp(String expWayBillNum,Long netId);
    
    public void updateParcel(Long taskId,Long parcelId,Long createUserId);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>添加派件任务</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 上午11:30:06</dd>
     * @param parcelId          '包裹ID串',
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
     * @since v1.0
     */
	public String addSendTaskBatch(String parcelId,Long memberId,String memberPhone);
	
	public String createSendTaskOrUpdate(String parcelId,Long memberId,String memberPhone);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据快递员ID查询派件任务列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午06:15:34</dd>
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public String querySendTaskList(Long memberId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>派件签收</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-11 上午11:47:38</dd>
	 * @param taskId
	 * @return
	 * @since v1.0
	 */
	public String finishTask(Long taskId,String parcelIds,Double totalCodAmount,Double totalFreight,Short type,Long memberId,String expWaybillNum) ;
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>派件任务取消</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-11 下午01:19:23</dd>
	 * @param taskId 任务ID
	 * @param parcelId 包裹Id
	 * @param memberId 收派员Id
	 * @return
	 * @since v1.0
	 */
	public String cancelSendTask(Long taskId, Long memberId, Long parcelId , Long Id ,String cancelType,Long compId,String compName,String textValue) throws RuntimeException;

	public boolean ifHasPickUpByParcelId(String ParcelId);
	public String addOrUpdateSendTask(Long parcelId,Integer taskType, Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc, Integer taskSource,
			Integer taskStatus, Integer taskIsEnd, Date taskEndTime,
			Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Date createTime, Long createUserId,
			Integer taskFlag, Date modifyTime, Double contactAddrLongitude,
			Double contactAddrLatitude);

	public boolean ifHasPickUpByParcelIdAndMemberId(String ParcelId,Long memberId);
}
