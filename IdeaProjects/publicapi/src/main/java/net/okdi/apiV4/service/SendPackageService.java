package net.okdi.apiV4.service;

import java.math.BigDecimal;


public interface SendPackageService {

	String queryParcelToBeTakenList(Long memberId, Integer currentPage,
			Integer pageSize);

	String queryParcelDetail(String parId);

	String queryNearCompInfo(Double longitude, Double latitude, Short agentType);

	String saveNearComp(String agentType, String compName, String compMobile, String compAddress, Long actorMemberId);

	String changeAccept(Long newMemberId, Long newCompId, Long newNetId,
			Long oldMemberId, String parIds,Short flag);

	String createSendTask(String actorPhone, Long memberId,String expWaybillNum, Long compId, Long netId, String addresseeName, 
			String addresseeMobile,Long addresseeAddressId,String cityName,String addresseeAddress, BigDecimal freight,BigDecimal codAmount);

	String changeSendPerson(String newMemberId, String oldMemberId,String parIds);

	String pickUpParcel(String parIds, Long memberId, String memberPhone);

	String querySendTaskList(Long memberId, Integer currentPage,Integer pageSize,String mobilePhone);

	String updateParcelInfo(String parId, String netId, String expNum,
			String mobile, String address, String codAmount, String freight,
			String name);

	String normalSignParcel(Long taskId, String parcelId,
			Double totalCodAmount, Double totalFreight, Long memberId,
			String mobile, String name, 
			String address, String sex,String signType,String signFlag,String custlabel,String custParentLabel,String compName);

	String normalSignParcelBatch(String taskIds,String signType);

	String exceptionSignParcel(String taskId, String parcelId, String memberId,
			String exceptionType, String textValue, String compId);

	String querySendRecordList(Long memberId, String signDate,String mobilePhone);

	String isRegisterByPhone(String memberPhone);

	String querySendCountAll(String memberId);

	String leaveOffice(Long memberId, Long compId, String memberName,String memberPhone);

	String ifCompExist(String compName, String compMobile);

	String updateNumByParId(String jsonData);

	String saveParcels(String memberId, String phone, String phoneData);

	String saveParcelsNew(String memberId, String phone, String jsonData);

	String updatePhoneInWrongList(String msgId, String taskId, String mobile);

	String queryOpenId(String mobile);

	String ifExistedCustomer(String mobilePhone, String memberId);

	String queryOpenIdByMobiles(String mobiles);

	String queryOpenIdState(String phone);

	  
	    /**     
	     * @discription 在此输入一句话描述作用
	     * @author zhaohu       
	     * @created 2016-10-20 下午3:21:29     
	     * @param oldPhone
	     * @param newPhone
	     * @param memberId
	     * @return     
	     */
	String replacePhone(String oldPhone, String newPhone, String memberId);

    String signPkg(Long memberId, String taskInfo);

    String signPkgConfirmNobound(Long opsId, Long memberId, String ops, String phone);

	String signPkgConfirmBound(Long opsId, String type);

    String updateWaybillNum(String json);

    String createSendTask(String info, Long memberId, Long netId, Long compId);

    String haveSignPkgList(Long memberId, Integer currentPage);

    String submitPackage(Long memberId, String taskIds);

	String staySign(String memberId, String expNum, String netId,String compId, String netName,String receName, String mobile, String actualSendMember,
			String contactAddress, String contactAddrLongitude, String contactAddrLatitude,String numType);
	//2016-12-24 song
	String querySendRecords(Long memberId, String signDate, String mobilePhone, String expWaybillNum, Short signResult,String compId,Integer currentPage,Integer pageSize);

	String querySendForms(Long memberId, String signDate, Short signResult, String compId);
	
	/**
	 * 2016/12/31  首页查询今日派的数量
	 * @param memberId
	 * @return
	 */
	Long querySendRecords(Long memberId);

    String findParcelByWaybillNo(Long memberId, String wayBill);

	String updatePhoWay(String memberId, String uid, String expNum, String mobile, String netId,
			String netName,String compId, String receName);

	String signRecord(String memberId, String signTime, String signResult);

	String createData(String memberId, String expNum, String netId,String compId,String netName,String receName,
			String mobile, String actualSendMember, String contactAddress, String contactAddrLongitude, String contactAddrLatitude);

	String querySendRecordDetail(Long memberId, Long parId);

    String createParcel(Long memberId, String parNum, String mobile, String receName, String wayBill, Long companyId, Integer ali);

	String updateParcelForSmsOrAli(String upInfo);

    String smsCreatePracleByWayBill(Long memberId, Long compId, String wayBill, Long companyId);

	String deleteParcelByParId(Long memberId, Long parId);

    String smsCreateParcelWhenSelectNoPhone(Long memberId, Long compId, String mobile, String parNum,
											String wayBill, Long companyId, Integer ali);

	String smsCreatePracleByWayBillForAli(Long memberId, Long compId, String wayBill, Long companyId, String parNum);

	String newQuerySendCountAll(String memberId);

	String delePack(String uid);

    String signPackageBatch(Long memberId, Long netId, Long compId, String parcelIds);

    String smsCreateParcelBatchByPhone(Long memberId, Long compId, String jsonParam);

	String deleteParcelBatchByParId(Long memberId, String parIds);

	String updateAddress(String memberId, String uid, String contactAddress, String contactAddrLongitude, String contactAddrLatitude);
}
