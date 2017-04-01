package net.okdi.apiV4.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.apiV4.entity.MemberInfo;

public interface AssignPackageService {

	public Map<String, Object> queryEmployeeByCompId(Long compId,String memberId);
	
	public Map<String,Object> saveParcelInfo(String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile,Long addresseeAddressId,
			String cityName,String addresseeAddress, BigDecimal freight,BigDecimal codAmount,Long createUserId, Long actualSendMember);
	
	public void createParcelInfo(Long sendTaskId, Long createUserId,Long actualSendMember,Short sendSmsType,Short sendSmsStatus,Short callBackStatus,
			String addresseeMobile,String sendMobile,String parcelNum,Short replyStatus,Short isNum);
	
	public void updateParcelInfo(Long sendTaskId,Short callBackStatus);

	public void updateParcelReply(Long sendTaskId,Short replyStatus);
	
	public Map<String,Object> parcelIsExist(String addresseeMobile,Long compId);

	public void deleteSendTaskByTaskId(Long sendTaskId);

	public List<MemberInfo> queryAllParParcel(Long compId);
	/**
	 * 
	 * @param compId
	 * @return 所有收派员
	 */

	public List<MemberInfo> queryMember(Long compId);

}
