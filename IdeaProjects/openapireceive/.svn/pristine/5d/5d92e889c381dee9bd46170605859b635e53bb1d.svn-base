package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.entity.ParParcelconnection;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.api.vo.VO_ParcelInfoAndAddressInfo;
import net.okdi.api.vo.VO_ParcelList;
import net.okdi.core.base.BaseService;


public interface ParcelInfoService extends BaseService<ParParcelinfo>{
	
	/**
	 * 查询包裹详情
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据运单编号与网络id查询包裹详情</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-6 下午1:02:10</dd>
	 * @param wayBillNum 运单编号
	 * @param netId 网络id
	 * @return
	 * @since v1.0
	 */
	Map<Object, Object> findParcelDetailByWaybillNumAndNetId(String wayBillNum, Long netId);
	
	/**
	 * 保存包裹信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午2:35:19</dd>
	 * @param id
	 * @param takeTaskId
	 * @param sendTaskId
	 * @param senderUserId
	 * @param sendCustomerId
	 * @param addresseeCustomerId
	 * @param sendCasUserId
	 * @param addresseeCasUserId
	 * @param expWaybillNum
	 * @param compId
	 * @param netId
	 * @param addresseeName
	 * @param addresseeMobile
	 * @param addresseeAddressId
	 * @param addresseeAddress
	 * @param sendName
	 * @param sendMobile
	 * @param sendAddressId
	 * @param sendAddress
	 * @param chareWeightForsender
	 * @param freight
	 * @param goodsPaymentMethod
	 * @param codAmount
	 * @param insureAmount
	 * @param pricePremium
	 * @param packingCharges
	 * @param freightPaymentMethod
	 * @param sendLongitude
	 * @param sendLatitude
	 * @param addresseeLongitude
	 * @param addresseeLatitude
	 * @param goodsDesc
	 * @param parcelRemark
	 * @param serviceId
	 * @param signMember
	 * @param createUserId
	 * @param parcelEndMark
	 * @param actualTakeMember
	 * @param actualSendMember
	 * @param receiptId
	 * @return
	 * @since v1.0
	 */
	public Map<String, Object> saveParcelInfo(Long id, Long takeTaskId, Long sendTaskId, Long senderUserId,
			Long sendCustomerId, Long addresseeCustomerId, Long sendCasUserId, Long addresseeCasUserId,
			String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile, 
			Long addresseeAddressId, String addresseeAddress, String sendName, String sendMobile, 
			Long sendAddressId, String sendAddress, BigDecimal chareWeightForsender, BigDecimal freight, Short goodsPaymentMethod, 
			BigDecimal codAmount, BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges,
			Short freightPaymentMethod, BigDecimal sendLongitude, BigDecimal sendLatitude, BigDecimal addresseeLongitude,
			BigDecimal addresseeLatitude, String goodsDesc, String parcelRemark, Long serviceId, String signMember,
			Long createUserId, String parcelEndMark, Long actualTakeMember, Long actualSendMember, Long receiptId,
			Short parcelStatus,BigDecimal actualCodAmount,String disposalDesc
			);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过包裹id删除对应包裹内容信息与包裹地址信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-8 下午7:30:54</dd>
	 * @param id  包裹id
	 * @since v1.0
	 */
	public void deleteParcelInfoByParcelId(Long id,String expWayBillNum,Long netId);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>扫描单号后判断是否为系统中已存在的包裹，如果已存在则返回包裹ID</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 下午6:25:32</dd>
	 * @param expWayBillNum   包裹运单号 
	 * @return
	 * @since v1.0
	 */
	public Long queryParcelInfoByExpWayBillNumAndNetId(Long netId,String expWayBillNum);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过运单号和网络id查询包裹id</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午1:47:25</dd>
	 * @param expWayBillNum
	 * @param netId
	 * @return
	 * @since v1.0
	 */
	public Long getParcelId(String expWayBillNum,Long netId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过包裹id查询包裹内容信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午2:40:17</dd>
	 * @param id
	 * @return
	 * @since v1.0
	 */
	public ParParcelinfo getParcelInfoById(Long id);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过包裹id查询包裹地址信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午2:48:50</dd>
	 * @param id
	 * @return
	 * @since v1.0
	 */
	public ParParceladdress getParParceladdressById(Long id);	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据登录人ID查询 待派包裹列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:07:16</dd>
	 * @param memberId  收派员ID
	 * @return
	 * @since v1.0
	 */
	public List<Map<String,Object>> queryParcelListBySendMemberId(Long memberId,Integer currentPage,Integer pageSize);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新包裹状态及结算状态</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午4:32:42</dd>
	 * @param parcelId 包裹ID
	 * @param accSrecpayvouchersId 收款凭证ID
	 * @param memberId 收派员ID
	 * @param type 0取件 1派件
	 * @since v1.0
	 */
	public void updateParcelSettleAccounts(Long parcelId,Double totalCodAmount,Double totalFreight,Long accSrecpayvouchersId,Long memberId,short type);

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询取件任务(只查询该收派员一天的取件任务)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param actualTakeMember 实际取件人Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	public List  queryTakeTaskList(Long actualTakeMember);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询一个单号的包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param actualTakeMember 实际取件人Id
	 * @param receiptId 运单Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	public List  queryTakeByWaybillNum(Long actualTakeMember,Long receiptId);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>更改收件人</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param memberId 收派员Id
	 * @param parceId 包裹Id
	 * @param TaskId 任务Id
	 * @param AddressId 收件人地址Id
	 * @param takePersonName 收件人姓名
	 * @param takePersonMoble 收件人电话
	 * @param takePersonAddress 收件人地址
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	 public void modyfyTaskInfo(Long memberId, Long parceId,Long TaskId,Long AddressId, String takePersonName
			  , String takePersonMoble,String takePersonAddress);
	 
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>将发件任务Id制空</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
		 * @param memberId 收派员Id
		 * @param parceId 包裹Id
		 * @return
		 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
		 * @since v1.0
		 */
	 public void updateParcelStatus(Long parcelId,Long memberId,String errorMessage);
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>查询一个已完成取件任务下的包裹</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
		 * @param memberId 收派员Id
		 * @param 取件任务Id 
		 * @return
		 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
		 * @since v1.0
		 */
	 public Map<String,Object> queryTakeTaskParcel( Long takeTaskId);
	 
	 
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>解除取件任务和包裹的关系</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
		 * @param parcelIds 包裹Id多个用,分隔
		 * @return
		 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
		 * @since v1.0
		 */
	 public void delTakeTaskRelationParcel(String parcelIds, Long takeId);
	 
	 public void addbatchSaveParcelInfo(List<VO_ParcelInfoAndAddressInfo> list);

	 
	 public Map<String,Object> queryByTakeTaskStatus(Long takeTaskId);
	 
	 /**
	  * 
	  * <dt><span class="strong">方法描述:</span></dt><dd>查询发件包裹记录</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2015-1-23 下午8:39:13</dd>
	  * @param memberId
	  * @return
	  * @since v1.0
	  */
	 public List<Map<String, Object>> querySendParcelList(Long memberId);
	 
	 public String deleteParcelByTakeTaskId(Long taskTaskId,Long netId);
	 
	 /**
	  * 
	  * <dt><span class="strong">方法描述:</span></dt><dd>包裹收派过程记录</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2015-3-2 下午3:39:14</dd>
	  * @param parParcelconnection
	  * @since v1.0
	  */
	 public void addParcelConnection(ParParcelconnection parParcelconnection);
	 
	 
	 /**
	  * 
	  * <dt><span class="strong">方法描述:</span></dt><dd>包裹收派过程删除记录</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2015-3-12 上午11:52:50</dd>
	  * @param parcelId
	  * @since v1.0
	  */
	 public void removeParcelConnection(Long parcelId);
	 
	 /**
	  * 
	  * <dt><span class="strong">方法描述:</span></dt><dd>包裹id查询收派过程记录</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2015-3-12 下午1:38:17</dd>
	  * @return
	  * @since v1.0
	  */
	 public List<ParParcelconnection> queryConnectionListByParId(Long parcelId);
	 
	 
	 /**
	  * 
	  * <dt><span class="strong">方法描述:</span></dt><dd>收派员离职删除收派过程记录</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2015-3-12 下午2:55:43</dd>
	  * @since v1.0
	  */
	 public void deleteParcelConnectionByMemberId(Long memberId);
	 
     public void removeParcelCache(Long parcelId,Long memberId);
	
     public void editAddresseeInfo(Long customerId,String customerName,String customerMobile,String customerAddress,Long memberId,String parcelId);
		
     public List<Map<String,Object>> queryAlreadySignList(Long sendMemberId,Integer currentPage, Integer pageSize);
     
     public void  cancelParcelBatche(Long memberId,String parcelId,Long compId,String compName);
     
     public Long queryAlreadySignCount(Long sendMemberId);
     
     public Long getParcelListBySendMemberIdCount(Long memberId);
     
     public ParParcelinfo getParcelInfoByIdNoEnd(Long id);
     
    /** 
     * 取件记录查询（包裹）
 	 * @param memberId 用户ID
 	 * @param date 日期
 	 * @return
 	 * @since v1.0
 	*/
     public List<VO_ParcelList> takeRecordList(Long memberId,String date);

     public Map<String,Object> sendRecordList(Long memberId, Date date);

}
