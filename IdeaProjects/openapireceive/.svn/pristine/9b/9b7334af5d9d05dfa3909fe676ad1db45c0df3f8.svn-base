package net.okdi.api.service;

import java.math.BigDecimal;

import net.okdi.api.vo.VO_DispatchSingleParInfo;
import net.okdi.core.common.page.Page;

public interface DispatchParService {

	Page findPars(Long compId,Long actualSendMember, String receiveName, String receivePhone, String startDate,
			String endDate, String billNo, Integer currentPage, Integer pageSize, Short tackingStatus);

	VO_DispatchSingleParInfo findParById(Long id);

	void addPar(Long createUserId,String actualSendMember,Long compId,Long netId, String expWaybillNum,
			BigDecimal codAmount, Long addresseeAddressId, String addresseeAddress,
			String addresseeMobile, String addresseeName, Long sendAddressId,
			String sendAddress, String sendMobile, String sendName, Short freightPaymentMethod, BigDecimal freight,
			BigDecimal packingCharges, BigDecimal chareWeightForsender, BigDecimal insureAmount,
			BigDecimal pricePremium, String parcelRemark);

	void updatePar(Long id,  Long netId, String actualSendMember,
			String expWaybillNum, BigDecimal codAmount, Long addresseeAddressId, String addresseeAddress,
			String addresseeMobile, String addresseeName, Long sendAddressId, String sendAddress, String sendMobile,
			String sendName, Short freightPaymentMethod, BigDecimal freight, BigDecimal packingCharges,
			BigDecimal chareWeightForsender, BigDecimal insureAmount, BigDecimal pricePremium, String parcelRemark);

	void removeTaskCache();

	void clearDispatchCache();
}
