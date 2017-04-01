/**  
 * @Project: openapi
 * @Title: ParcelSupplementService.java
 * @Package net.okdi.api.service
 * @author amssy
 * @date 2015-1-28 下午7:54:01
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.core.common.page.Page;

/**
 * @author amssy
 * @version V1.0
 */
public interface ParcelSupplementService {
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-28 下午8:16:23</dd>
	 * @param createTime 创建时间
	 * @param expWaybillNum 运单号
	 * @param memberId 收派员id
	 * @param customerName 客户名称
	 * @param senderPhone 发件人手机
	 * @param parcelStatus 包裹状态
	 * @param compId 站点id
	 * @param departId 营业分部id
	 * @param page 分页
	 * @return
	 * @since v1.0
	 */
	public Page queryParcelList(String createTime, String expWaybillNum, Long memberId, String customerName, String senderPhone, Long parcelStatus, Long compId, Long departId, Page page);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查看包裹详情</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-29 下午4:46:02</dd>
	 * @param parcelId 包裹id
	 * @return
	 * @since v1.0
	 */
	public Map<String, Object> queryParcelDetail(Long compId, Long parcelId, String type);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加或更新包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-30 下午3:44:46</dd>
	 * @param id
	 * @param sendCustomerId
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
	 * @param insureAmount
	 * @param pricePremium
	 * @param packingCharges
	 * @param freightPaymentMethod
	 * @param parcelRemark
	 * @param createUserId
	 * @param actualTakeMember
	 * @param actualCodAmount
	 * @param noFly
	 * @return
	 * @since v1.0
	 */
	public Map<String, Object> addOrUpdateParcelInfo(Long id,Long sendCustomerId, String expWaybillNum, Long compId, 
			Long netId, String addresseeName, String addresseeMobile, Long addresseeAddressId, String addresseeAddress, 
			String sendName, String sendMobile, Long sendAddressId, String sendAddress, BigDecimal chareWeightForsender, 
			BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges, Short freightPaymentMethod, 
			String parcelRemark, Long createUserId, Long actualTakeMember, BigDecimal actualCodAmount, Short noFly, BigDecimal signGoodsTotal);
}
