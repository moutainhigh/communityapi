package net.okdi.api.service;

/**
 * @ClassName SendNoticeService
 * @Description 消息接口
 * @author feng.wang
 * @date 2014-10-23
 */
public interface SendNoticeService {

	/**
	 * 分配任务给站点或者营业分部 --->给营业分部或者站点推送消息
	 * @param taskId 任务ID
	 * @param memberId 营业分部或者站点ID
	 * @param businesSegmtName 营业分部或者站点名称
	 * @param sendMob 发件人手机号
	 * @param sendName 发件人姓名
	 * @param sendAddress 发件人地址
	 * @param taskNum 任务数量
	 * @return
	 */
	public boolean asignToBusinesSegmt(Long taskId, String memberId, String businesSegmtName,
			String sendMob, String sendName, String sendAddress, int taskNum);

	/**
	 * 任务分配给收派员--->给收派员推送消息
	 * @param taskId 任务ID
	 * @param memberId 收派员Id
	 * @param sendLon 发件人经度
	 * @param sendLat 发件人纬度
	 * @param sendName 发件人姓名
	 * @param sendMobile 发件人手机
	 * @param sendAddress 发件人地址（海淀区田村路43号）
	 * @param netId 网络ID
	 * @param expMemberMob 收派员手机号
	 * @param sendDetailAddress 发件人详细地址（北京市海淀区田村路43号）
	 * @param noticeType 消息类型： sms只发短信 其他走正常业务
	 * @return
	 */
	public boolean asigntoExpMember(Long taskId, Long memberId, String sendLon, String sendLat,
			String sendName, String sendMobile, String sendAddress, Long netId,
			String expMemberMob, String sendDetailAddress,String noticeType);

	/**
	 * 任务分配给收派员--->给客户推送消息
	 * @param taskId 任务ID
	 * @param taskSource 任务来源 1：好递网 2：站点自建 3：电商管家 4：好递个人端 5：好递接单王
	 * @param memberId 客户ID
	 * @param customerMob 客户手机号（发短信用）
	 * @param expMemberName 取件人（收派员）姓名
	 * @param expMemberMob 取件人（收派员）电话
	 * @param expMemberLon 取件人（收派员）所在位置的经度
	 * @param expMemberLat 取件人（收派员）所在位置的纬度
	 * @return
	 */
	public boolean assignToCustomer(Long taskId, Integer taskSource, String memberId,
			String customerMob, String expMemberName, String expMemberMob, String expMemberLon,
			String expMemberLat);
	
	/**
	 * 任务重新分配--->给客户推送消息
	 * @param taskId 任务ID
	 * @param taskSource 任务来源 1：好递网 2：站点自建 3：电商管家 4：好递个人端 5：好递接单王
	 * @param memberId 客户ID
	 * @param customerMob 客户手机号（发短信用）
	 * @param expMemberName 取件人（收派员）姓名
	 * @param expMemberMob 取件人（收派员）电话
	 * @param expMemberLon 取件人（收派员）所在位置的经度
	 * @param expMemberLat 取件人（收派员）所在位置的纬度
	 * @param oldExpName 重新分配前收派员的姓名
	 * @return
	 */
	public boolean turnToCustomer(Long taskId, Integer taskSource, String memberId,
			String customerMob, String expMemberName, String expMemberMob, String expMemberLon,
			String expMemberLat,String oldExpName);
	

	/**
	 * 取消任务 --->给客户推送消息
	 * @param taskId 任务ID
	 * @param taskSource 任务来源 1：好递网 2：站点自建 3：电商管家 4：好递个人端 5：好递接单王
	 * @param memberId 客户ID
	 * @param customerMob 客户手机号
	 * @param canselReason 取消原因
	 * @param siteName 站点或者营业分部的名称
	 * @return
	 */
	public boolean cancelToCustomer(Long taskId, Integer taskSource, String memberId,
			String customerMob, String canselReason,String siteName);
	

	/**
	 * 营业分部/收派员取消任务--->给站点/营业分部推送消息
	 * @param cancelType 任务取消类型：EXP：收派员 BUSINES:营业分部
	 * @param taskId 任务ID
	 * @param memberId 站点/营业分部ID
	 * @param siteName 站点/营业分部名称
	 * @param siteMob 站点电话
	 * @param sendMob 发件人手机号
	 * @param sendName 发件人姓名
	 * @param sendAddress 发件人地址
	 * @param cancelName 取消营业分部或者收派员的名称
	 * @param cancelReason 取消原因
	 * @param taskNum 任务数量
	 * @return
	 */
	public boolean cancelToSite(String cancelType, Long taskId, String memberId, String siteName,
			String sendMob, String sendName, String sendAddress, String cancelName,
			String cancelReason, int taskNum);

	/**
	 * 任务取消 --->给收派员推送消息
	 * @param taskId 任务ID
	 * @param memberId 收派员ID
	 * @param sendName 发件人姓名
	 * @param sendMob 发件人电话
	 * @param sendAddress 发件人地址
	 * @param expMemberMob 收派员手机号
	 * @return
	 */
	public boolean cancelToExpMember(Long taskId, Long memberId, String sendName, String sendMob,
			String sendAddress, String expMemberMob);

	/**
	 * 人员离职推送消息（接单方）
	 * @param memberId 收派员ID
	 * @param mob 离职人员手机号
	 * @return
	 */
	public boolean quitToExpMember(Long memberId, String mob,int roleId);

	/**
	 * 客服拒绝身份验证
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	public boolean refuseToExp(Long memberId, String mob);

	/**
	 * 客服拒绝归属验证
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	public boolean refuseRelationToExp(Long memberId, String mob);
		
	/**
	 * 修改角色信息
	 * @param memberId 人员id
	 * @param mob 
	 * @return
	 */
	public boolean updateRoleInfo(Long memberId,Long userId,String phone);
	
	
	/**
	 * 	站点认证失败 chunyang.tan add
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	public boolean refuseNetDotAuthenticat(Long compId, String mob,Short compStatus, String refuseDesc);
	
	/**
	 * 	站点认证成功 chunyang.tan add
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	public boolean successNetDotAuthenticat(Long memberId, String mob,Short compStatus);
	
	/**
	 * 	快递员、后勤申请加入该站点 chunyang.tan add
	 * @param memberId 站长ID
	 * @param mob 站长手机号
	 * @return
	 */
	public boolean applyJoinNet(Long memberId, String mob,Short compStatus) ;
	
	/**
	 * 	客户回复 chunyang.tan add
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 * TODO
	 */
	public boolean customerReply(Long memberId, String mob);

	/**
	 * 	客户回复 chunyang.tan add
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 * TODO
	 */
	public boolean taskPush(Long memberId, String mob);
	
	/**
	 * 分配包裹给收派员推送消息
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @param parNum 包裹数量
	 * @return
	 */
	public boolean parNoticeToExp(Long memberId, String mob, int parNum);
	
	
	/** 
	 * 叫快递给站点系统发送广播消息
	 * @param broadId 广播ID
	 * @param siteMemberId 站点ID
	 * @param distance 站点距离距发件人的公里数
	 * @param sendAddress 发件人的地址
	 * @param num 待抢单的任务数
	 * @return
	*/
	public boolean broadNoticeToSite(Long broadId,Long siteMemberId,String distance,String sendAddress,int num);
	
	/** 
	 * 叫快递给收派员发送广播消息
	 * @param broadId 广播ID
	 * @param expMemberId 收派员ID
	 * @param distance 收派员距发件人的公里数
	 * @param sendAddress 发件人的地址
	 * @param parNum 包裹数量
	 * @return
	*/
	public boolean broadNoticeToExp(Long broadId,Long expMemberId,String distance,String sendAddress,int parNum);
	//配送员
	public boolean broadNoticeToApp(Long broadId,Long memberId);
	//配送员
	public boolean cancelNoticeToApp(Long broadId,Long memberId,String shopName,String orderNum);
	
	//用户确认签收，扫码推送
	public boolean configSignToApp(Long broadcastId,Long memberId,String tradeNum,String totalAmount);
	
	/** 
	 * 收派员报价后给客户推送消息
	 * @param broadId 广播ID
	 * @param cstmMemberId 客户ID
	 * @param expName 收派员姓名
	 * @param expNetName 收派员所属网络
	 * @return
	*/
	public boolean expQuoteToCustomer(Long broadId,Long cstmMemberId,String expName,String expNetName);
	
	/** 
	 * 站点报价后给客户推送消息
	 * @param broadId 广播ID
	 * @param cstmMemberId 客户ID
	 * @param siteName 公司名称
	 * @return
	*/
	public boolean siteQuoteToCustomer(Long broadId,Long cstmMemberId,String siteName);
	
	
	/** 
	 * 收派员竞价失败后推送消息
	 * @param broadId 广播ID
	 * @param expMemberId 收派员ID
	 * @param sendAddress 发件人地址
	 * @return
	*/
	public boolean bidFailToExp(Long broadId,Long expMemberId,String sendAddress);
	/** 
	 * 收派员竞价失败后推送消息
	 * @param broadId 广播ID
	 * @param expMemberId 收派员ID
	 * @param sendAddress 发件人地址
	 * @return
	*/
	public boolean cancelBroadcastToMember(Long broadId,Long expMemberId,String sendAddress);
	
	/** 
	 * 站点竞价成功后推送消息
	 * @param broadId 广播ID
	 * @param siteMemberId 站点ID
	 * @param taskId 任务ID
	 * @param sendName 发件人姓名
	 * @param sendMob 发件人电话
	 * @param sendAddress 发件人地址
	 * @return
	*/
	public boolean bidSuccessToSite(Long broadId,Long siteMemberId,Long taskId,String sendName,
			String sendMob,String sendAddress);
	
	/** 
	 * 收派员竞价成功后推送消息
	 * @param broadId 广播ID
	 * @param expMemberId 收派员ID
	 * @param expMob 收派员手机号
	 * @param taskId 任务ID
	 * @param sendAddress 发件人地址
	 * @return
	*/
	public boolean bidSuccessToExp(Long broadId,Long expMemberId,String expMob,Long taskId,String sendAddress);
	
	/** 
	 * 呼叫快递哥发送短信
	 * @param mobile  快递哥手机号
	 * @return
	*/
	public boolean callExp(String mobile);
	
	/** 
	 * 确认交寄后，向已填写的收件人电话发送推送
	 * @param parceId 包裹ID
	 * @param expWaybillNum 运单号
	 * @param netId 网络ID
	 * @param sendName 发件人姓名
	 * @param recptMob	收件人电话
	 * @param expMemberLon 收派员经度
	 * @param expMemberLat 收派员纬度
	 * @param expMemberMob 收派员电话
	 * @return
	*/
	public boolean sendToRecpt(Long parceId,String expWaybillNum,Long netId,String sendName,String recptMob,
			String expMemberLon,String expMemberLat,String expMemberMob);
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王免费短信日志记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午16:05:14</dd>
	 * @param memberPhone  收派员电话
	 * @param receiver_mobile 收件人手机号,多个用,好分隔
	 * @param sms_source  短信来源 0:接单王 1:个人端
	 * @param phone_type  手机类型 0:Android 1:Ios
 	 * @return	{"success","true"}
	 * @since v1.0
	*/
	public void sendSmsAudit(String memberPhone, String receiver_mobile , Short sms_source , Short phone_typeShort, Short sendSuccess , String sendVersion);
	public void sendSmsAuditOnKnownMemberId(String memberPhone, String receiver_mobile , Short sms_source , Short phone_typeShort, Short sendSuccess , String sendVersion,Long memberId);

	/**
	 * 任务分配给收派员--->给收派员推送消息(电商)
	 * @author mengnan.zhang
	 * @param taskId 任务ID
	 * @param memberId 收派员Id
	 * @param sendLon 发件人经度
	 * @param sendLat 发件人纬度
	 * @param sendName 发件人姓名
	 * @param sendMobile 发件人手机
	 * @param sendAddress 发件人地址（海淀区田村路43号）
	 * @param netId 网络ID
	 * @param expMemberMob 收派员手机号
	 * @param sendDetailAddress 发件人详细地址（北京市海淀区田村路43号）
	 * @param noticeType 消息类型： sms只发短信 其他走正常业务
	 * @return
	 */
	public boolean asigntoExpMemberAmssy(Long taskId, Long memberId, String sendLon, String sendLat,
			String sendName, String sendMobile, String sendAddress, Long netId,
			String expMemberMob, String sendDetailAddress,String noticeType,String date);
	/**
	 * 客服通过身份认证
	 * @author zhaohu
	 * @date 2015年11月2日13:29:04
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	public boolean passRealNameCheck(Long memberId, String mob);
	/**
	 * 客服拒绝身份认证
	 * @author zhaohu
	 * @date 2015年11月2日13:29:04
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	public boolean refuseRealNameCheck(Long memberId, String mob);
	/**
	 * 客服通过快递认证
	 * @author zhaohu
	 * @date 2015年11月2日13:29:04
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	public boolean passExpressCheck(Long memberId, String mob);
	/**
	 * 客服拒绝快递认证
	 * @author zhaohu
	 * @date 2015年11月2日13:29:04
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	public boolean refuseExpressCheck(Long memberId, String mob,String remark);
	/**
	 * (代收站店长审核店员)归属审核失败推送
	 * @author zhengjiong
	 * @date 2015年11月3日13:29:04
	 * @param memberId 收派员ID
	 * @param mob 店员手机号
	 * @return
	 */
	public boolean sendPushSMSRemindRefuse(Long memberId, String mob);
	/**
	 * (代收站店长审核店员)归属审核成功推送
	 * @author zhengjiong
	 * @date 2015年11月3日13:29:04
	 * @param memberId 收派员ID
	 * @param mob 店员手机号
	 * @return
	 */
	public boolean sendPushSMSRemindSuccess(Long memberId, String mob);
	/**
	 * (店长)身份认证失败推送
	 * @author zhengjiong
	 * @date 2015年11月3日13:29:04
	 * @param memberId 收派员ID
	 * @param mob 店员店长手机号
	 * @return
	 */
	public boolean sendPushSMSRemindIdentityRefuse(Long memberId, String mob,String refuseDesc);
	/**
	 * (店长)身份认证成功推送
	 * @author zhengjiong
	 * @date 2015年11月3日13:29:04
	 * @param memberId 收派员ID
	 * @param mob 店员店长手机号
	 * @return
	 */
	public boolean sendPushSMSRemindIdentitySuccess(Long memberId, String mob);

	/**
	 * 店员入驻代收点,给店长发送推送
	 * @author zhengjiong
	 * @date 2015年11月4日13:29:04
	 * @param memberId 店长ID
	 * @param mob 店长手机号
	 * @return
	 */
	public boolean sendPushSMSManagerSuccess(Long memberId, String mobile);
	/**
	 * 
	 * @MethodName: net.okdi.api.service.SendNoticeService.java.passRelationToExp 
	 * @Description: TODO(客服通过归属认证推送) 
	 * @param @param memberId
	 * @param @param memberPhone   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-11-7
	 * @auth zhaohu
	 */
	public boolean passRelationToExp(Long memberId, String memberPhone);
	/**
	 * @MethodName: net.okdi.api.service.SendNoticeService.java.sendMsgToInviter 
	 * @Description: TODO( 为邀请者推送消息 ) 
	 * @param @param valueOf
	 * @param @param memberPhone
	 * @param @return   
	 * @return boolean  返回值类型
	 * @throws 
	 * @date 2015-11-7
	 * @auth zhaohu
	 */
	public boolean sendMsgToInviter(Long memberId, String memberPhone);
	/**
	 * @MethodName: net.okdi.api.service.SendNoticeService.java.sendMsgToInvitee 
	 * @Description: TODO(为被邀请者推送消息) 
	 * @param @param valueOf
	 * @param @param memberPhone
	 * @param @return   
	 * @return boolean  返回值类型
	 * @throws 
	 * @date 2015-11-7
	 * @auth zhaohu
	 */
	public boolean sendMsgToInvitee(Long memberId, String memberPhone);
	
	public boolean sendTS(String memberId,String deviceType,String deviceToken,String title,String message,String extraParam) ;
	/**
	 * 申诉成功
	 * @param compName 公司名称
	 * @param memberId 人员id
	 * @param plaintPhone 负责人电话
	 */
	public boolean sendAppealNewWebmasterSuccess(String compName, String memberId,
			String plaintPhone, String compType);
	/**
	 * 申诉失败
	 * @param compName 公司名称
	 * @param memberId 人员id
	 * @param plaintPhone 负责人电话
	 * @param compType 站点或者营业分部
	 */
	public boolean sendAppealOldWebmasterSuccess(String compName, String memberId,
			String plaintPhone, String compType);

	/**
	 * 申诉失败
	 * @param compName 公司名称
	 * @param memberId 人员id
	 * @param plaintPhone 负责人电话
	 * @param compType 站点或者营业分部
	 */
	public boolean sendAppealNewWebmasterFailure(String compName, String memberId,
			String plaintPhone, String compType, String remark);

	/**
	 * @MethodName: net.okdi.api.service.SendNoticeService.java.sendSmsByRecharge 
	 * @Description: TODO(充值通讯费赠送奖励推送) 
	 * @param @param money
	 * @param @param mobile   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-3-28
	 * @auth zhaohu
	 */
	public boolean sendSmsByRecharge(Double money, Long mobile);

	/**
	 * 取件代收点叫快递
	 * @param memberId memberId
	 * @param mobile 电话
	 * @return
	 */
	public boolean sendPushCalledCourier(Long memberId, String mobile);
	/**
	 * 
	 * @MethodName: net.okdi.api.service.SendNoticeService.java.sendMsgByChangeAccept 
	 * @Description: TODO(派件转代收点推送) 
	 * @param @param newMemberId   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-4-22
	 * @auth zhaohu
	 */
	public boolean sendMsgByChangeAccept(Long newMemberId);

	boolean robPush(Long memberId, String mob, String msg);

	/**
	 * 
	 * @MethodName: net.okdi.api.service.SendNoticeService.sendCancelCancelCourier
	 * @Description: TODO(代收点取消叫快递) 
	 * @param @param newMemberId   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-4-22
	 * @auth zhengjiong
	 */
	public boolean sendCancelCancelCourier(Long valueOf, String memberMobile);

	/**
	 * 
	 * @MethodName: net.okdi.api.service.SendNoticeService.sendPushConsigCourier(Long, String)
	 * @Description: TODO(确认交寄给快递员发送推送) 
	 * @param @param actorMemberId 收派员memberid  
	 * @param @param toMemberPhone 收派员手机号
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-4-22
	 * @auth zhengjiong
	 */
	public boolean sendPushConsigCourier(Long actorMemberId, String toMemberPhone);

	/**
	 * 
	 * @MethodName: net.okdi.api.service.SendNoticeService.sendPushNoticeCourier(Long, String)
	 * @Description: TODO(收派员同意取件给代收点发送推送) 
	 * @param @param customerId 代收点memberid  
	 * @param @param mobile 代收点手机号
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-4-22
	 * @auth zhengjiong
	 */
	public boolean sendPushNoticeCourier(Long customerId, String mobile);

	public boolean sendPushCirmTakeSuccess(Long memberId, String memberPhone);

	public boolean sendSynchFinancialMessage(Long valueOf, String memberPhone);


}
