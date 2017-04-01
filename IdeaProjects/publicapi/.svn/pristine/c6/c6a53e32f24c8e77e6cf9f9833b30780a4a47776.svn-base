package net.okdi.apiV4.service;

public interface ReceivePackageReportService {

	String queryTakeforms(String memberId, String date, String compId);

	public String queryPackage(String memberId, String expWaybillNum, String netId, String netName);

	public String finishTakeReceivePackage(String memberId, String jsonData, String codes, String compId, String deliveryAddress,
			String versionId, String terminalId);

	public String takeReceivePackageByMember(String memberId, String sendName, String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress, String addresseePhone, String netName, String roleId, 
			String expWaybillNum, String code, String comment, Long parcelId, String compId, String pacelWeight, String parcelType, 
			String serviceName, String sendProv, String sendCity, String addresseeProv, String addresseeCity, String sendDetailed,
			String addresseeDetailed);

	public String againUpload(String memberId, String netId, String netName, String expWaybillNum, String code, Long parcelId);

	public String takeReceivePackage(String memberId, String sendName, String sendPhone, String sendAddress, String deliveryAddress, 
			String netId, String addresseeName, String addresseeAddress, String addresseePhone, String netName, String roleId, 
			String expWaybillNum, String code, String comment, String compId, String pacelWeight, String parcelType, 
			String serviceName, String sendProv, String sendCity, String addresseeProv, String addresseeCity, String sendDetailed,
			String addresseeDetailed,String versionId ,String terminalId);

	public String queryPackageCode(String memberId, String code, String netId);

}
