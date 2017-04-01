package net.okdi.apiV4.service;

import java.math.BigDecimal;

public interface AssignPackageService {

	String queryEmployeeByCompId(Long compId);

	String saveParcelInfo(String expWaybillNum, Long compId, Long netId,String addresseeName, String addresseeMobile,
			Long addresseeAddressId,String cityName, String addresseeAddress,BigDecimal freight, BigDecimal codAmount, Long createUserId,Long actualSendMember);

	String parcelIsExist(String addresseeMobile,Long compId);

    String saveParcelInfo(String info, Long memberId);

	/**查询派件异常
	 * @param memberId
	 * @return
	 */
	Long queryAssignException(Long memberId);
}
