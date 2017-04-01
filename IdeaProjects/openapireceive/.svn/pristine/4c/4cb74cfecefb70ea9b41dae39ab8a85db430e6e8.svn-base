package net.okdi.apiV4.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.core.common.page.Page;
/**
 * @Project Name:springmvc 
 * @Package net.okdi.apiV4.service  
 * @Title: SendPackageService.java 
 * @ClassName: SendPackageService <br/> 
 * @date: 2016-4-16 下午1:18:39 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
public interface SendPackageService {

	Page queryParcelToBeTakenList(Long memberId, Integer currentPage,Integer pageSize);

	HashMap<String, Object> queryParcelDetail(String parId);

	void changeSendPerson(String newMemberId,String oldMemberId,String parIds);

	void pickUpParcel(String parIds,Long memberId, String memberPhone);

	Page querySendTaskList(Long memberId,Integer currentPage,Integer pageSize,String mobilePhone);

	public List<Map<String, Object>> queryNearCompInfo(Double longitude, Double latitude,Short agentType);

	Map<String,Object> saveNearComp(String compName, String compMobile, Long actorMemberId);

	void updateParcelInfo(String parId, String netId, String expNum,String mobile, String address, String codAmount, String freight,String name);

	void normalSignParcel(Long taskId, String parcelId, Double totalCodAmount,Double totalFreight, Long memberId,String mobile,String name,String address,String sex,String signType,String signFlag);

	void exceptionSignParcel(String taskId, String parcelId, String memberId,String exceptionType, String textValue, String compId);

	public void changeAccept(Long newMemberId,Long newCompId,Long newNetId, Long oldMemberId,String parIds,Short flag);

	String addSendTask( Integer taskType, Long coopCompId,Long coopNetId, Integer parEstimateCount, Integer taskSource,
			Integer taskStatus, Integer taskIsEnd, Long memberId, String actorPhone, String contactName,String contactMobile, Long contactAddressId,
			String contactAddress, Date createTime,Integer taskFlag, Date modifyTime,String expWaybillNum, BigDecimal freight,BigDecimal codAmount,String cityName);

	void normalSignParcelBatch(String taskIds,String signType);

	HashMap<String, Object> querySendRecordList(Long memberId, String signDate) throws Exception;

	HashMap<String, Object> isRegisterByPhone(String memberPhone);

	HashMap<String, Object> querySendCountAll(String memberId);

	HashMap<String, Object> leaveOffice(Long memberId, Long compId,
			String memberName, String memberPhone);

	Map<String,Object> ifCompExist(String compName, String compMobile);

	String updateParcelStatusBySendTaskId(Long sendTaskId, Short sendSmsStatus,Short callBackStatus,Short sendSmsType);

	void updateIsSendMsgFlag(String sendTaskId);

	void updateNumByParId(String jsonData);

	void saveParcels(String memberId,String phone, String phoneData);

}
