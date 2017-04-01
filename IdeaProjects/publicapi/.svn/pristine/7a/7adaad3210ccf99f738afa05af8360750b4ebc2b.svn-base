package net.okdi.apiV4.service;

import java.util.List;
import java.util.Map;

public interface ReceivePackageService {

	public String queryTakePackList(String memberId, String currentPage, String pageSize);

	public String queryTakeTaskDetailByTaskId(String memebrId, String taskId);

	public String takeSendPackage(String taskId, String memberId, String packageNum, String freightMoney, String sign);

	public String queryHasTakeList(String memberId, String roleId, String date, String netName, String currentPage, String pageSize);

	public String queryHasTakePackDetailByParcelId(String parcelId);

	public String querySiteMemberByMemberId(String memberId);

	public String turnTakePackageToMember(String taskId, String memberId, String toMemberId, String toMemberPhone);

	public String consignationToSiteByMemberId(String parcelIds);

	public String queryNearMemberByTude(String memberId, String netId);

	public String consignationToMemberByParcelIds(String parcelIds, String toMemberId);

	public String saveMemberInfoToNewMemberInfo(String parcelIds, String newMemberName, String compName, String newMemberPhone);

	public String calledCourierToMember(String taskId, String memberId, String parcelIds, String flag);

	public String memberConfirmOrder(String taskId, String memberId,
			String phone);

	public String collectPointsConfirmSend(String taskId, String memberId, String flag);

	public String queryTakeRecordList(String memberId, String date, String phone);
	//2016-12-23 取件记录新改
	public String queryTakeRecordListk(String memberId, String date, String phone, String expWaybillNum,String all,Integer currentPage,Integer pageSize);
	
	public String takeSendPackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double freightMoney, String packageJson, String flag);

	public String queryTakeConsigOrderList(String memberId, String netId,
			String currentPage, String pageSize);

	public String queryTakePackageList(String taskId, String memberId);

	public String deleteParcelByParcelId(String taskId, String parcelId);

	public String deleteTaskByTaskId(String taskId);

	//根据订单id查询任务id  memberId 和 手机号
	public String getTaskIdByOrderNum(String orderNum);

	public String takeSendPackageByMemberAdd(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double freightMoney, String packageJson);

	public String queryRecordDetailByTaskId(String taskId);

	public String addParcelToTaskId(String taskId, String memberId, int packageNum, String sign, String packageJson);

	public String takeReceivePackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, String packageJson);


	public String takeReceivePackageDetailInfo(String taskId, Long memberId);

	public String consignationInfo(String memberId,String parcelIds);

	public String queryTakeforms(String memberId, String date, String all);

	/**
	 * 查询当日揽收数量
	 * @param memberId
	 * @return
	 */
	public Long queryTakePackageCount(Long memberId);
	
     //2017-1-4拍照揽收
	public String takeReceivePackageByMembers(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,Long parcelId,String compId,String pacelWeight,String parcelType,String serviceName);

	public String markContent(Long netId,Integer signType);

	public String queryCompMemberByMemberId(String memberId);


    String queryNewTakePackList(String memberId, String currentPage, String pageSize);

	String addNewReceiveOrder(String memberId, String senderName, String senderPhone, String addressDetail, Integer packNum, String tagContent);

	String tagOrder(String memberId, Long taskId, String tagContent);

	String operationOrderStatus(String memberId, Long taskId, String ancelReason);

	public String finishOrder(String memberId, Long taskId, String parcelIds, String sendName, String sendPhone, String sendAddress, String netId, String addresseeName, String addresseePhone, String addresseeAddress, String expWaybillNum, String marker, String bourn, String code, String comment);


	public String queryPackage(String memberId, String sendMobile, String netId, String netName, String expWaybillNum, String code);

	public String queryTakeRecordDetailed(String memberId, Long uid);

	public String queryRecRecordList(String memberId, String date, String phone, String expWaybillNum, String compId, Integer currentPage, Integer pageSize);

	
	public String checkParcelDetail(Long parcelId,String contactAddress,Long netId,String netName,String contactMobile,String contactName);
	public String saveParcelInfo(String mark,Long parcelId,String contactAddress,Long netId,String netName,String contactMobile,String contactName,String expWaybillNum,String code,String addresseeName,String addresseeAddress,String addresseePhone);

	public String confirmTakeParcel(String uids, Long memberId, String taskId, String flag);

	public String queryPackageMobile(String memberId, String sendMobile, String netId, String netName);

	public String checkPidIsUnique(String expWaybillNum);

	public String serviceType(Long netId);



}
