package net.okdi.mob.service;


import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.core.common.page.Page;

public interface ExpGatewayService {
	public String saveCompPersInfo(Long memberId ,Long compId,String memberName,short roleId,String memberPhone,String applicationDesc);

	public String getCompInfo(Long loginCompId);
	
	//任务分配给售派员
	public String toMember(String id, Long formCompId, Long fromMemberId, Long toMemberId);
	
	//收派员指派给收派员
	public String memberToMember(String id, Long fromMemberId, Long toMemberId);
	
	//收派员取消任务
	public String cancelMember(String taskId, Long memberId, Byte taskTransmitCause, String disposalDesc ,String expFlag);
	
	//查询取消任务
	public String queryRefuseTask(String senderName, String startTime, String endTime, String senderPhone,
			 Long operatorCompId, Page page);
	
	//任务完结
	public String finishTask(Long id, Long memberId, Long compId,Long netId,Long takeTaskId);
	//任务完结
	public String finishTask(Long taskId);
	//查询任务明细
	public String queryTaskDetail(Long id);
	
	public String updateOnlineMember(Long memberId,Short roleId,Short employeeWorkStatusFlag,String areaColor);
	
	//翟士贺后给接口
	public String getPromptCompForMobile(Long netId, String compTypeNum,Long addressId,Short roleId);
	
	public String getSameCompNameForMobile(Long netId, String compName);
	
	public String saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Long addressId, String address);
	
	public String saveOrUpdateCompBasicInfo(Map<String,Object> paraMeterMap);
	
	public String querySameComp(Long loginCompId);
	
	
	//创建接单王的任务
	public String doParTaskrecord(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId, String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId, BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude);

	 //登陆之后保存位置
	public String saveOnLineMember(Long id,Long netId,String netName,Long compId,
			 String compName,Long memberId,String memberName,String memberMobile,
			 Double lng,Double lat,String memo);
	 
	//登陆之后更新收派员位置信息
	public String updateAddrByMember(Long memberId, Double lng, Double lat);
	 
	 //退出时删除收派员的地址信息
	 public String deleteOnLineMember(Long memberId);
	 //添加_站点删除收派员的操作
	 public String  deleteMemberInfo(Long memberId,Long compId,String memberName,String memberPhone,Long userId);
	 
	 //添加收派员时验证手机是否存在
	 public String checkTel(String associatedNumber,Long compId);
	//查询收派员详细信息(状态,收派员,站长,后勤)
	 public String  getMemberInfoById(Long memberId);
	 //查询一个站点下的收派员
	 public Map<String,Object>  getStationMember(Long logMemberId,Long compId);
	 
	 //根据不同状态查询不同任务
	 public String getStatusBytasks(Long memberId,Integer status,String taskType);
	 //查询5个单号
	 public String getAuditInfo(Long memberId);
	 //更新发件人
	 public String updateContacts(Long taskId, String contactName, String contactMobile, String contactTel, Long contactAddressId, String contactAddress, Long customerId);
	 //发送短信
	 public String doSmsSend(String channelNO,Long extraCommonParam, String  usePhones,
				String  BasicContent,String netNames,String waybillNum,String memberPhone,String lat,String lng,String  compId ,String version);
		/**
		 * @param String smsTemplate 发送内容
		 * @param String phoneAndWaybillNum 发送号码
		 * @param Long memberId 发送人id
		 */
	 public String sendSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,
			 String voiceUrl,String msgId,Short isNum, String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, String md5Hex);//
	 
	 public String findMassnotificationrecord(Long memberId,String queryTime,Integer pageSize,Integer pageNo,Short status,Short type,String receiverPhone,
			 String sendContent, String number, String billNum,String name);

	 
	 
	 /**
		 * 	短信聊天 
		 * @author chunyang.tan add
		 * @param String firstMsgId 关联主表id
		 * @return
		 */
		public String querySmsChat(String firstMsgId,int pageNo,int pageSize);
	 //登陆之后返回状态
	 	/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>查询人员审核状态</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:22:17</dd>
		 * @param memberId 人员id
		 * @return {"data":{
		 * 					"memberId":781398355116084, --人员id
		 * 					"relationFlag":0, --归属验证标识 0 待审核 1通过 2拒绝
		 * 					"veriFlag":0}, --身份验证标识 0 待审核 1通过 2拒绝
		 * 				"success":true}
		 * @since v1.0
		 */
	 public Map<String,Object> getValidationStatus(Long memberId);
	 //添加组名时,验证是否以重复
	 public String ifExitJDW(Long memberId,String contactPhone);
	 
	 public String addMemberInfo(Long compId,String associatedNumber,String memberName,Short roleId,String areaColor,Long userId,Short memberSource);
		
	 public String doEditComp(Long compIdOld,Long compIdNew);
	 
	 public String adviceInfo(Long compId,Long memberId,String channelNo,String content,String adviceUser
				,String tel,String memo,String loginId,String compName);

	 public Integer QueryExistByCount(Long memberId);
	 
	 public String mobileRegistration(Long memberId, String memberName,
				String memberPhone, String idNum, Long compId, Short roleId,
				String applicationDesc, Short flag,String addressName,String memberSourceFlag
				,Long netId,String compName , String compTelephone , Long addressId , String address
				 ,Double longitude , Double latitude,String compTypeNum ,short existAdd,String stationPhone);
	 
	 
	 public String findParcelDetailByWaybillNumAndNetId(String wayBillNum, Long netId);

	 public String saveParcelInfo(HttpServletResponse response ,short ParceTypeFlag,String parmJSON,Long actualAcount,
				Long memberId);
	 
	 public String deleteParcelInfoByParcelId(Long id,String expWayBillNum,Long netId);
	 
	 public String finishTask(Long taskId,String parcelIds,Double totalCodAmount,Double totalFreight,Short type,Long memberId);

	 public Map<String,Object> querySendTaskList(Long memberId);
	 
	 public String ifHasPickUp(String expWayBillNum,Long netId);
	 
	 public String changSendPerson(String parcelIds, Long memberId,Long oldMemberId,String memberPhone);
	 
	 public String createSendTaskBat(String parcelId,Long memberId,String memberPhone,Long sendTaskId);
	 
	 
	 public String createSendTask( String parcelId,Long coopCompId,
				Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
				Date appointTime, String appointDesc,Long actorMemberId, String actorPhone, String contactName,
				String contactMobile, String contactTel, Long contactAddressId,
				String contactAddress, Long customerId, Long contactCasUserId,
				Long contactCompId, Long createUserId,
				Integer taskFlag, Double contactAddrLongitude,
				Double contactAddrLatitude);
	 
	 public String queryParcelListBySendMemberId(Long memberId,Integer currentPage,Integer pageSize);
	 
	 public String  settleAccounts(String parcelIds,Double totalCodAmount,Double totalFreight,Long memberId,short type);
	 
	 public String  queryTakeTaskList(Long actualTakeMember);
	 
	 public Map<String,Object>  queryTakeByWaybillNum(Long actualTakeMember,Long receiptId);
	 public Map<String,Object> modyfyTaskInfo(Long memberId, Long parceId,Long TaskId,Long AddressId, String takePersonName
			  , String takePersonMoble,String takePersonAddress);
	 
	 public String  getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight);

	 public String cancelSendTask(Long taskId, Long memberId, Long parcelId , Long Id ,String cancelType,Long compId,String textValue);
	 
	 public String  getStationHostPhone(Long logMemberId, Long compId);
	 
	 public String doExpSmsSend(String userPhone , String content , String extraCommonParam);
	 
	 public String queryCompInfo(Long loginCompId);
	 
	 public String finishTaskPersonal(Long taskId);
	 
	 public String queryAlreadySignList(Long sendMemberId, Integer currentPage, Integer pageSize);

	 public String  cancelParcelBatche(Long memberId,String parcelId,Long compId,String compName);
	 
	 
	 public String  scannerSendTaskSign(Long parceId,String expWaybillNum,Long compId ,Long netId,String addressAddressName
				,String addressAddressMobile,Long addresseeAddressId,
				 String addresseeAddress,Double freight,Double codAmount,Long memberId,Long sendTaskId);
	 
	 public String  scannerSendTaskException(Long parceId,String expWaybillNum,Long compId ,Long netId,String addressAddressName
				,String addressAddressMobile,Long addresseeAddressId,
				 String addresseeAddress,Double freight,Double codAmount,Long memberId, String disposalDesc,Long sendTaskId);
	 
	 public String  scanSemdTaskCreate(Long parcelId,String expWaybillNum,Long compId ,Long netId,String addressAddressName
				,String addressAddressMobile,Long addresseeAddressId,
				 String addresseeAddress,Double freight,Double codAmount,Long memberId,String memberPhone,String sendTaskId);
	 public String  verifyRelation(String phone);
	 public String sendSmsTwo(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,
			 String voiceUrl,String msgId,Short isNum);

	public String queryInviteInfo(String phone, String firstMsgId);

	public String queryParcelNumber(String msgId);

	public String queryCountAndCost(String queryDate, String memberId);

	public String newQuerySmsChat(Long memberId, String msgId, Integer pageNo, Integer pageSize);
}