package net.okdi.apiV4.service;

import java.util.Map;

import net.okdi.apiV4.entity.CooperationExpCompanyAuth;

public interface ReceivePackageReportService {

	Map<String, Object> queryTakeforms(String memberId, String date, String compId);

	public	Map<String, Object> queryPackage(String memberId, String expWaybillNum,
			String netId, String netName);

	public Map<String, Object> takeReceivePackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment, Long parcelId,
			String compId, String pacelWeight, String parcelType,
			String serviceName,String sendProv,String sendCity,String addresseeProv,String addresseeCity,
			String sendDetailed,String addresseeDetailed);
	/**
	 * 授权信息查询
	 * @param memberId
	 * @return object
	 */
	Object querysqInfo(String memberId,String netId);

	public String finishTakeReceivePackage(String memberId, String jsonData,String codes,String compId,String deliveryAddress,
			String versionId ,String terminalId);

	public Map<String, Object> againUpload(String memberId, String netId,
			String netName, String expWaybillNum, String code, Long parcelId);
	/**
	 * 揽收散件
	 */
	public Map<String, Object> takeReceivePackage(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment,
			String compId, String pacelWeight, String parcelType,
			String serviceName, String sendProv, String sendCity,
			String addresseeProv, String addresseeCity, String sendDetailed,
			String addresseeDetailed, String versionId ,String terminalId);
	/**
	 * 揽收散件订单数据封装
	 */
	public Map<String, Object> uploadParcelRecinfo(String memberId, String txlogisticid,
			String createordertime, String code,
			CooperationExpCompanyAuth querysqInfo, String sendName,
			String sendPhone, String sendProv, String sendCity,
			String sendDetailed, String addresseeName, String addresseePhone,
			String addresseeProv, String addresseeCity,
			String addresseeDetailed, String netId);
	/**
	 * 揽收散件去重查询
	 */
	public String  queryPackageCode(String memberId, String code,
			String netId);

	

	

}
