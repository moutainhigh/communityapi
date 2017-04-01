package net.okdi.api.service;

import java.math.BigDecimal;
import java.util.Map;

public interface ExpressTaskService {

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:59:45</dd>
	 * @param accessType 0 手机,1 compId,2 memberId
	 * @param phone
	 * @param memberId
	 * @param address
	 * @param packageNum
	 * @param packageWeight
	 * @param senderMobile
	 * @param senderName
	 * @param erpId
	 * @return
	 * @throws Exception
	 * @since v1.0
	 */
	Map<String, Object> createTaskSimple(String accessType,String phone, String memberId, String address, String packageNum,
			String packageWeight, String senderMobile, String senderName,String erpId,String allsms,String takeTime) throws Exception;

	
	Map<String, Object> saveParcelInfo(Long id, Long takeTaskId, Long sendTaskId, Long senderUserId,
			Long sendCustomerId, Long addresseeCustomerId, Long sendCasUserId, Long addresseeCasUserId,
			String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile, 
			Long addresseeAddressId, String addresseeAddress, String sendName, String sendMobile, 
			Long sendAddressId, String sendAddress, BigDecimal chareWeightForsender, BigDecimal freight, Short goodsPaymentMethod, 
			BigDecimal codAmount, BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges,
			Short freightPaymentMethod, BigDecimal sendLongitude, BigDecimal sendLatitude, BigDecimal addresseeLongitude,
			BigDecimal addresseeLatitude, String goodsDesc, String parcelRemark, Long serviceId, String signMember,
			Long createUserId, String parcelEndMark, Long actualTakeMember, Long actualSendMember, Long receiptId,
			Short parcelStatus,BigDecimal actualCodAmount
			);
}
