package net.okdi.apiV4.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.entity.ParcelSignInfo;
import net.okdi.core.common.page.Page;

public interface ReceivePackageService {

	public Page queryTakePackList(Long memberId, Integer currentPage, Integer pageSize);
	public Page queryNewTakePackList(Long memberId, Integer currentPage, Integer pageSize);

	public Map<String, Object> queryTakeTaskDetailByTaskId(Long memebrId, Long taskId);

	public String takeSendPackage(String taskId, String memberId, Integer packageNum, Double freightMoney, String sign);

	public Page queryHasTakeList(String memberId, String roleId, String netName, Integer currentPage, Integer pageSize, String date);

	public Map<String, Object> queryHasTakePackDetailByParcelId(String parcelId);

	public Map<String, Object> querySiteMemberByMemberId(String memberId);

	public Map<String,Object> queryCompMemberByMemberId(String memberId);
	
	public String turnTakePackageToMember(String taskId, String memberId, String toMemberId, String toMemberPhone);

	public String consignationToSiteByMemberId(String parcelIds);

	@SuppressWarnings("rawtypes")
	public List<Map> queryNearMemberByTude(String memberId, String netId);

	public String consignationToMemberByParcelIds(String parcelIds, String toMemberId);

	public String saveMemberInfoToNewMemberInfo(String parcelIds, String newMemberName, String compName, String newMemberPhone);

	public String calledCourierToMember(String taskId, String memberId, String parcelIds, String flag);

	public String memberConfirmOrder(String taskId, String memberId,
			String phone);
	public String memberConfirmOrderWeiXin(String taskId,String memberId,String phone,Short flag);
	
	public String calledCourierToMemberWeiXin(String taskId,String flag);

	public String collectPointsConfirmSend(String taskId, String memberId, String flag);

	public List<Map<String,Object>> queryTakeRecordList(String memberId, String date, String phone);

	public Map<String,Object> takeSendPackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double valueOf, String packageJson, String flag);

	public Page queryTakeConsigOrderList(String memberId,
			String netId, Integer currentPage, Integer pageSize);

	public List<Map<String,Object>> queryTakePackageList(String taskId, String memberId);

	public String deleteParcelByParcelId(String taskId, String parcelId);

	public String deleteTaskByTaskId(String taskId);

	public Long queryTakePackCount(Long memberId);
	public Long queryTakePackCountDSD(Long memberId,Long compId);

	public String sendPush(String memberId, String phone);

	public String updateWeChatPayStatus(String payNum, Short payStatus);

	public ParTaskInfo getTaskIdByOrderNum(String orderNum);
	
	public Map<String,Object> takeSendPackageByMemberAdd(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double freightMoney, String packageJson, String sign);

	public Long queryConsigOrderNum(Long memberId, Long auditComp);

	public Long queryHasParcelNum(Long memberId);

	public String findPayStatusByPayNum(String payNum);

	public Map<String, Object> queryRecordDetailByTaskId(String taskId);
	
	public String recivesaomaCreate(String tradeNum, Double tradeTotalAmount);
	
	void insertBasCustomerInfo(String userName,String mobilePhone,String address);
	
	public String addParcelToTaskId(String taskId, String memberId, int packageNum, String sign, String packageJson);
	public String takeReceivePackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, String packageJson);

	public String takeReceivePackageByMembers(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,Long parcelId,String compId,String pacelWeight,String parcelType,String serviceName);
	
	public Map<String,Object> takeReceivePackageDetailInfo(String taskId, Long memberId);

	public String takeReceivePackageByOnlyCode(String parcelIds, Long memberId);

	public Map<String, Object> queryTakeRecordLists(String memberId,
			String date, String phone, String expWaybillNum,
			String parcelEndMark,String all,Integer currentPage,Integer pageSize);

	public Map<String,Object> consignationInfo(String memberId,String parcelIds);

	public Map<String, Object> queryTakeforms(String memberId, String date,String all);
	///首页查询取件数量
	public Long queryTakePackageCount(String date, Long memberId);

	public Map<String, Object> queryTakeRecordListk(String memberId,
			String date, String phone, String expWaybillNum, String all,
			Integer currentPage, Integer pageSize, Integer index);

	public List<String> findMarkComment(Long netId,Integer signType);


	String addNewReceiveOrder(String memberId, String senderName, String senderPhone, String addressDetail, Integer packNum, String tagContent);

	String tagOrder(String memberId, Long taskId, String tagContent);

    void confirmDelivery(String uids);

	String operationOrderStatus(String memberId, Long taskId, String ancelReason);
	
	public String finishOrder(String memberId, Long taskId, String parcelIds, String sendName,String deliveryAddress,
			String sendPhone, String sendAddress, String netId,String roleId,
			String addresseeName, String addresseePhone,String addresseeAddress, 
			String expWaybillNum,String marker,String bourn,String code,String comment,String netName);
	
	public  Map<String, Object> queryPackage(String memberId, String sendMobile, String netId,String netName,String expWaybillNum, String code);
	public Map<String, Object> queryTakeRecordDetailed(String memberId, Long uid);
	
	public Map<String, Object> queryRecRecordList(String memberId, String date,String phone, String expWaybillNum, String compId,
			Integer currentPage, Integer pageSize);
	
	public Map<String, Object> checkParcelDetail(Long parcelId,String contactAddress,Long netId,String netName,String contactMobile,String contactName);
	public void saveParcelInfo(String mark,Long parcelId,String contactAddress,Long netId,String netName,String contactMobile,String contactName,String expWaybillNum,String code,String addresseeName,String addresseeAddress,String addresseePhone);
	
	public String confirmTakeParcel(String uids, Long memberId, String taskId, String flag);
	
	void inserExpCustomerInfo(String userName, String mobilePhone,String address,String memberId); 
	
	void insertPackageReport(String memberId, String netId,String compId, String date) ;
	
	Map<String, Object> queryPackageMobile(String memberId, String sendMobile,String netId, String netName);
	
	public String checkPidIsUnique(String expWaybillNum);
	
	public List<String> serviceType(Long netId);
}
