package net.okdi.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.BasVerifyRecoredMapper;
import net.okdi.api.dao.SendNoticeMapper;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.BasVerifyRecored;
import net.okdi.api.entity.ParSmsAudit;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.MobMemberLoginService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.core.common.Constant;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.DateUtil;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * @ClassName SendNoticeServiceImpl
 * @Description 消息接口实现类
 * @author feng.wang
 * @date 2014-10-23
 */
@Service
public class SendNoticeServiceImpl implements SendNoticeService {
	
	
	private static Logger logger = Logger.getLogger(SendNoticeServiceImpl.class);

	@Autowired
	private NoticeHttpClient noticeHttpClient;
	@Autowired
	private MobMemberLoginService mobMemberLoginService;
	@Autowired
	private BasVerifyRecoredMapper basVerifyRecoredMapper;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private BasNetInfoMapper netInfoMapper;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private SendNoticeMapper sendNoticeMapper; 
	
	/**
	 * 分配任务给收派员发送短信模版
	 */
	@Value("${assign.task.sms.toExpMember}")
	private String assignToExpMember;
    @Value("${assign.task.sms.toExpMemberAmssy}")
    private String assignToExpMemberAmssy;
	/**
	 * 分配任务给收派员发送消息模版
	 */
	@Value("${assign.task.notice.toExpMember}")
	private String noticeToExpMember;

	/**
	 * 分配任务给收派员后给客户发消息
	 */
	@Value("${assign.task.notice.toCustomer}")
	private String noticeToCustomer;

	/**
	 * 分配任务给收派员后给客户发短信模版
	 */
	@Value("${assign.task.sms.toCustomer}")
	private String assignToCustomer;

	/**
	 * 取消任务给客户发短信模版
	 */
	@Value("${cancel.task.sms.toCustomer}")
	private String cancelToCustomer;
	
	/**
	 * 取消任务给客户发推送模版
	 */
	@Value("${cancel.task.notice.toCustomer}")
	private String cancelNoticeToCustomer;

	/**
	 * 取消任务给收派员发短信模版
	 */
	@Value("${cancel.task.sms.toExpMember}")
	private String cancelToExpMember;

	/**
	 * 人员离职推送消息
	 */
	@Value("${quit.notice.toExpMember}")
	private String quitNoticeToExp;
	@Value("${quit.notice.toExpMember.ds}")
	private String quitNoticeToExpDS;
	
	/**	
	 * 人员离职推送内容
	 */
	@Value("${put.quit.notice}")
	private String putQuitNotice;
	@Value("${put.quit.notice.ds}")
	private String putQuitNoticeDS;
	
	/**
	 * 	修改角色信息
	 */
	@Value("${role.info}")
	private String roleInfo;
	/**
	 * 	修改角色信息 发送短信内容
	 */
	@Value("${role.sms.info}")
	private String roleSmsInfo;
	
	/**
	 * 新的取件任务
	 */
	@Value("${task.notice.toSite}")
	private String taskNotice;

	/**
	 * 客服拒绝身份验证
	 */
	@Value("${refuse.verify.toExpMember}")
	private String refuseVerify;

	/**
	 * 客服拒绝归属验证
	 */
	@Value("${refuse.relation.toExpMember}")
	private String refuseRelation;
	/**
	 * 客服拒绝归属验证
	 */
	@Value("${refuse.relation.toExpMemberTZ}")
	private String refuseRelationTZ;
	/**
	 * 客服通过归属验证 duanxin
	 */
	@Value("${pass.relation.toExpMember}")
	private String passRelation;
	/**
	 * 客服通过归属验证 推送
	 */
	@Value("${passTS.relation.toExpMember}")
	private String passRelationTS;
	
	/**
	 * 客服身份认证通过
	 */
	@Value("${pass.realName.toExpMember}")
	private String passRealNameMsg;
	/**
	 * 客服身份认证拒绝
	 */
	@Value("${refuse.realName.toExpMember}")
	private String refuseRealNameMsg;
	/**
	 * 客服快递认证通过（短信）
	 */
	@Value("${pass.express.toExpMember}")
	private String passExpressMsg;
	/**
	 * 客服快递认证通过(通知)
	 */
	@Value("${pass.express.toExpMemberTZ}")
	private String passExpressMsgTZ;
	/**
	 * 客服快递认证拒绝
	 */
	@Value("${refuse.express.toExpMember}")
	private String refuseExpressMsg;
	@Value("${refuse.express.toExpMemberTZ}")
	private String refuseExpressMsgTZ;
	/**
	 * 为邀请者推送消息
	 */
	@Value("${push.message.toInviter}")
	private String toInviterMsg;
	/**
	 * 为被邀请者推送消息
	 */
	@Value("${push.message.toInvitee}")
	private String toInviteeMsg;
	
	/**
	 * 派件转代收点推送消息
	 */
	@Value("${push.message.changeAcceptMsg}")
	private String changeAcceptMsg;
	
	/**
	 *  客服拒绝网点认证
	 */
	@Value("${refuse.netDot.authenticat}")
	private String refuseNetDotAuthenticat;
	
	
	/**
	 *  客服拒绝网点认证 非登陆状态下发送信息
	 */
	@Value("${refuse.netDot.sendMsg}")
	private String refuseNetDotSendMsg;

	
	/**
	 * 	客户回复
	 */
	@Value("${customer.reply}")
	private String customerReply;
	/**
	 * 	取件通知
	 */
	@Value("${task.push}")
	private String taskPush;
	
	@Value("${rob.push}")
	private String robPush;
	
	/**
	 *  网点认证成功
	 */
	@Value("${success.netDot.authenticat}")
	private String successNetDotAuthenticat;
	
	@Value("${success.netDot.SendMsg}")
	private String successNetDotSendMsg;
	/**
	 *  快递员、后勤申请加入该站点
	 */
	@Value("${apply.join.net}")
	private String applyJoinNetDot;
	
	@Value("${apply.join.net.SendMsg}")
	private String applyJoinNetDotSendMsg;
	/**
	 * 分配包裹给收派员
	 */
	@Value("${assign.par.notice.toExp}")
	private String parNoticeToExp;

	/**
	 * 派件标题
	 */
	@Value("${par.title}")
	private String parTitle;
	
	
	/**
	 * 收件员取消任务主标题
	 */
	@Value("${cancel.task.byExp}")
	private String cancelTaskByExp;

	/**
	 * 营业分部取消任务主标题
	 */
	@Value("${cancel.task.bySite}")
	private String cancelTaskBySite;

	/**
	 * 收派员接收短信的总条数
	 */
	@Value("${exp.sms.total}")
	private String expSmsTotal;
	
	/**
	 * 收派员取件通知标题
	 */
	@Value("${assign.task.notice.title}")
	private String assignTaskNoticeTitle;
	
	/**
	 * 收派员取消取件通知
	 */
	@Value("${cancel.task.notice.title}")
	private String cancelTaskNoticeTitle;
	
	/**
	 * 重新分配任务短信内容
	 */
	@Value("${turn.task.sms.toCustomer}")
	private String turnSmsToCustomer;
	
	/**
	 * 重新分配任务推送内容
	 */
	@Value("${turn.task.notice.toCustomer}")
	private String turnNoticeToCustomer;
	
	@Value("${push_environment}")
	private String pushEnvironment;
	
	@Value("${push_time_open}")
	private String pushTimeOpen;
	/**
	 * (代收站 _店长审核店员, 归属的)
	 */
	@Value("${refuse.site.sendMsgSuccess}")
	private String siteSendMsgSuccess;
	@Value("${refuse.site.sendMsgfail}")
	private String siteSendMsgfail;
	@Value("${refuse.msg.sendSuccess}")
	private String refuseMsgSendSuccess;
	@Value("${refuse.msg.sendfail}")
	private String refuseMsgSendfail;
	/**
	 * (店长)身份认证
	 */
	@Value("${refuse.identity.sendMsgSuccess}")
	private String identitySendMsgSuccess;
	@Value("${refuse.identity.sendMsgfail}")
	private String identitySendMsgfail;
	/**
	 * 店员入驻代收点推送给店长消息
	 */
	@Value("${refuse.manager.sendMsgSuccess}")
	private String managerSendMsgSuccess;
	//申诉成功前台
	@Value("${send.new.WebmasterSuccess}")
	private String sendNewWebmasterSuccess;
	//申诉失败前台
	@Value("${send.new.WebmasterFailure}")
	private String sendNewWebmasterFailure;
	
	//原站长前台
	@Value("${send.old.WebmasterSuccess}")
	private String sendOldWebmasterSuccess;
	
	//充值通讯费奖励推送
	@Value("${send.recharge.rechargeRewardMsg}")
	private String rechargeRewardMsg;
	
	//代收点叫快递
	@Value("${send.called.calledCourierMsg}")
	private String calledCourierMsg;
	//代收点代寄-已收包裹选择交寄
	@Value("${send.consig.consigCourierMsg}")
	private String sendConsigConsigCourierMsg;
	//快递员在提醒-取件通知-同意取件
	@Value("${send.notice.noticeCourierMsg}")
	private String sendNoticeNoticeCourierMsg;
	//快递员同意取件后，代收点取消叫快递
	@Value("${send.cancel.cancelCourierMsg}")
	private String sendCancelCancelCourierMsg;
	
	//付款成功后给当前快递员推送消息
	@Value("${send.Notice.CirmTakeMsg}")
	private String sendNoticeCirmTakeMsg;
	//付款成功微信账户有钱但是同步财务失败给快递员推送消息
	@Value("${send.Synch.FinancialFailMsg}")
	private String sendSynchFinancialFailMsg;
	
	
	/** 发件人电话姓名地址拼接 格式：1514568923 | 张三 | 海淀区田村路43号 或者 1514568923 | 海淀区田村路43号 **/
	private String getSendInfo(String sendMob, String sendName, String sendAddress) {
		if (!PubMethod.isEmpty(sendMob)) {
			sendMob = sendMob + " | ";
		}
		if (!PubMethod.isEmpty(sendName)) {
			sendName = sendName + " | ";
			/** 姓名不为空 地址截取14个字 **/
			sendAddress = PubMethod.limitString(sendAddress, 28);
		}
		if (PubMethod.isEmpty(sendName)) {
			/** 姓名不为空 地址截取18个字 **/
			sendAddress = PubMethod.limitString(sendAddress, 36);
		}
		return sendMob + sendName + sendAddress;

	}
	
	/** 推送内容前面时间戳开关 **/
	private String getContent(String content,String extraParam,String date){
		if(!"true".equals(pushTimeOpen)){
			return content;
		}
		StringBuffer buffer=new StringBuffer();
		buffer.append(pushEnvironment);
		buffer.append(date);
		buffer.append(content);
		buffer.append("【好递】");
		buffer.append(extraParam);
		return buffer.toString();
	}
	
	

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
	@Override
	public boolean asignToBusinesSegmt(Long taskId, String memberId, String businesSegmtName,
			String sendMob, String sendName, String sendAddress, int taskNum) {
		String date=DateUtil.getNowTime();
		logger.debug("分配任务给站点或者营业分部 --->给营业分部或者站点推送消息：任务ID "+taskId+",营业分部或者站点ID "+memberId+",调用日期  "+
				date);
		/** 用户ID为空 返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 消息标题 **/
		String messege = taskNotice;
		/** 消息内容 **/
		String content = this.getSendInfo(sendMob, sendName, sendAddress);
		/** 调用消息推送接口 **/
		this.noticeHttpClient.pushSocketPC(Constant.PUSH_TYPE_PC,memberId, Constant.PUSH_ASSIGN_TYPE, messege, "",
				content, taskId == null ? "" : taskId.toString(), taskNum);
		return true;
	}

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
	@Override
	public boolean asigntoExpMember(Long taskId, Long memberId, String sendLon, String sendLat,
			String sendName, String sendMobile, String sendAddress, Long netId,
			String expMemberMob, String sendDetailAddress,String noticeType) {
		String date=DateUtil.getNowTime();
		logger.debug("任务分配给收派员--->给收派员推送消息：任务ID "+taskId+",收派员Id "+memberId+",调用日期  "+
				date);
		/** 防止空指针异常 **/
		if (PubMethod.isEmpty(sendName)) {
			sendName = "";
		}
		if (PubMethod.isEmpty(sendAddress)) {
			sendAddress = "";
		}
		if (PubMethod.isEmpty(sendMobile)) {
			sendMobile = "";
		}
		/** 短连接 **/
		String shortLink = "";
		if (!PubMethod.isEmpty(sendDetailAddress)) {
			shortLink = this.noticeHttpClient.getOkdiShortUrl(sendDetailAddress, sendMobile);
		}
		String messege = this.assignToExpMember.replace("${sendAddress}", sendAddress).replace(
				"${sendName}", sendName).replace("${sendmobile}", sendMobile).replace("${link}",
				shortLink == null ? "" : shortLink);
		if(Constant.NOTICE_TYPE.equals(noticeType)){
			/** 发送短信 **/
			this.doSmsSend(expMemberMob, messege, null);
			return true;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS, memberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {// 如果登录接单王APP 直接推送消息
			messege = this.noticeToExpMember.replace("${sendAddress}", sendAddress).replace(
					"${sendName}", sendName).replace("${sendMobile}", sendMobile);
			/** 调用消息推送接口 **/
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),
					Constant.PUSH_ASSIGN_TYPE, assignTaskNoticeTitle, messege, taskId == null ? "" : taskId
							.toString(), Constant.MSGTYPE_NOTICE);
			return true;
		}
		// 如果未登录接单王 直接发短信 收派员收短信是有次数校验的目前只能接收3条
		/** 收派员手机号为空 返回失败 **/
		if (PubMethod.isEmpty(expMemberMob)) {
			return false;
		}
		// 超过短信限制条数，不再发送短信
		/*int smsTotal = this.basVerifyRecoredMapper.getSmsTotalOfExp(expMemberMob);
		if (smsTotal >= Integer.valueOf(expSmsTotal)) { 
			logger.debug("超过短信限制条数，不再给收派员发送短信。收派员电话："+expMemberMob+",收派员ID:"+memberId);
			return false;
		}*/
		/** 发送短信 **/
		this.doSmsSend(expMemberMob, messege, null);
		/** 任务分派给收派员发送短信 保存短信记录 **/
		/*this.basVerifyRecoredMapper.insert(getVerifyRecord(memberId, expMemberMob, messege));*/
		return true;
	}

	/** 获取短信记录实体 **/
	private BasVerifyRecored getVerifyRecord(Long memberId, String verifyAccount, String messege) {
		BasVerifyRecored verifyRecord = new BasVerifyRecored();
		verifyRecord.setId(IdWorker.getIdWorker().nextId());
		verifyRecord.setMemberId(memberId);
		verifyRecord.setVerifyType(Constant.Sms.VERIFY_TYPE_SMS);
		verifyRecord.setVerifyAccount(verifyAccount);
		verifyRecord.setStatus(Constant.Sms.SMS_VERIFY_CODE_STATUS_NO);
		verifyRecord.setContent(messege);
		verifyRecord.setCreateTime(PubMethod.getSysTimestamp());
		verifyRecord.setBusineType(Constant.Sms.SMS_BUSINE_TYPE_EXP);
		return verifyRecord;
	}

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
	@Override
	public boolean assignToCustomer(Long taskId, Integer taskSource, String memberId,
			String customerMob, String expMemberName, String expMemberMob, String expMemberLon,
			String expMemberLat) {
		String date=DateUtil.getNowTime();
		logger.debug("任务分配给收派员--->给客户推送消息：任务ID "+taskId+",客户Id "+memberId+",调用日期  "+
				date);
		/** 防止空指针异常 **/
		if (PubMethod.isEmpty(expMemberName)) {
			expMemberName = "";
		}
		if (PubMethod.isEmpty(expMemberMob)) {
			expMemberMob = "";
		}
		
		/** 推送内容 **/
		String noticeStr = noticeToCustomer.replace("${expMemberName}", expMemberName).replace(
				"${expMemberMob}", expMemberMob);
		/** 短信内容 初始化和推送内容一致 有短链接的时候内容发生变化**/
		String smsStr = noticeStr;
		if (!PubMethod.isEmpty(expMemberLon) && !PubMethod.isEmpty(expMemberLat)) {
			/** 获取短连接 **/
			String shortLink = this.noticeHttpClient.getPersonalShortUrl(expMemberLon, expMemberLat,
					expMemberMob);
			if(!PubMethod.isEmpty(shortLink)){
				smsStr = assignToCustomer.replace("${expMemberName}", expMemberName).replace(
								"${expMemberMob}", expMemberMob).replace("${link}",shortLink);
			}
		}
		
		/** 客户是站点直接发短信 **/
		if (taskSource == Constant.TASK_SOURCE_EXP || taskSource == Constant.TASK_SOURCE_OKDI) {
			/** 发送短信 **/
			this.doSmsSend(customerMob, smsStr, null);
			return true;
		} else if (taskSource == Constant.TASK_SOURCE_PERSONAL
				|| taskSource == Constant.TASK_SOURCE_ORDERS) {
			if (!PubMethod.isEmpty(memberId)) {
				String channelNo = "";
				if (taskSource == Constant.TASK_SOURCE_PERSONAL) {
					channelNo = Constant.PUSH_TYPE_PERSONAL;// 02:个人端
				} else if (taskSource == Constant.TASK_SOURCE_ORDERS) {
					channelNo = Constant.PUSH_TYPE_ORDERS;// 01：接单王
				}
				/** 调用是否登录接口**/
				String result=ifOnline(channelNo, Long.valueOf(memberId));
				if(Constant.NOPUSH_NOSMS.equals(result)){
					return true;
				}
				/** 登录状态下发推送 **/
				if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
					this.pushExt(result,channelNo, memberId.toString(), Constant.PUSH_ASSIGN_TYPE, 
							Constant.PUSH_TITLE, noticeStr,taskId == null ? "" : taskId.toString(), Constant.MSGTYPE_NOTICE);
					return true;
				}
				/** 发送短信 **/
				this.doSmsSend(customerMob, smsStr, null);
				return true;
			} else {
				/** 发送短信 **/
				this.doSmsSend(customerMob, smsStr, null);
			}
		}
		return true;
	}

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
	@Override
	public boolean turnToCustomer(Long taskId, Integer taskSource, String memberId,
			String customerMob, String expMemberName, String expMemberMob, String expMemberLon,
			String expMemberLat,String oldExpName){
		String date=DateUtil.getNowTime();
		logger.debug("任务重新分配--->给客户推送消息：任务ID "+taskId+",客户Id "+memberId+",调用日期  "+
				date);
		/** 防止空指针异常 **/
		if (PubMethod.isEmpty(expMemberName)) {
			expMemberName = "";
		}
		if (PubMethod.isEmpty(expMemberMob)) {
			expMemberMob = "";
		}
		if (PubMethod.isEmpty(oldExpName)) {
			oldExpName = "";
		}
		
		/** 推送内容 **/
		String noticeStr=this.turnNoticeToCustomer.replace("${oldExpName}", oldExpName).replace(
				"${newExpName}", expMemberName).replace("${newExpMob}", expMemberMob);
		/** 短信内容 初始化和推送内容一致 有短链接的时候内容发生变化**/
		String smsStr=noticeStr;
		if (!PubMethod.isEmpty(expMemberLon) && !PubMethod.isEmpty(expMemberLat)) {
			/** 获取短连接 **/
			String shortLink = this.noticeHttpClient.getPersonalShortUrl(expMemberLon, expMemberLat,
					expMemberMob);
			if(!PubMethod.isEmpty(shortLink)){
				smsStr=this.turnSmsToCustomer.replace("${oldExpName}", oldExpName).replace(
						"${newExpName}", expMemberName).replace("${newExpMob}", expMemberMob).replace("${link}", shortLink);
			}
		}
		
		/** 客户是站点直接发短信 **/
		if (taskSource == Constant.TASK_SOURCE_EXP || taskSource == Constant.TASK_SOURCE_OKDI) {
			/** 发送短信 **/
			this.doSmsSend(customerMob, smsStr, null);
			return true;
		} else if (taskSource == Constant.TASK_SOURCE_PERSONAL
				|| taskSource == Constant.TASK_SOURCE_ORDERS) {
			if (!PubMethod.isEmpty(memberId)) {
				String channelNo = "";
				if (taskSource == Constant.TASK_SOURCE_PERSONAL) {
					channelNo = Constant.PUSH_TYPE_PERSONAL;// 02:个人端
				} else if (taskSource == Constant.TASK_SOURCE_ORDERS) {
					channelNo = Constant.PUSH_TYPE_ORDERS;// 01：接单王
				}
				/** 调用是否登录接口**/
				String result=ifOnline(channelNo, Long.valueOf(memberId));
				if(Constant.NOPUSH_NOSMS.equals(result)){
					return true;
				}
				/** 登录状态下发推送 **/
				if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
					this.pushExt(result,channelNo, memberId.toString(), Constant.PUSH_TURN_TYPE,
							Constant.PUSH_TITLE, noticeStr,taskId == null ? "" : taskId.toString(), Constant.MSGTYPE_NOTICE);
					return true;
				}
				/** 发送短信 **/
				this.doSmsSend(customerMob, smsStr, null);
				return true;
			} else {
				/** 发送短信 **/
				this.doSmsSend(customerMob, smsStr, null);
				return true;
			}
		}
		return true;
	}
	
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
	@Override
	public boolean cancelToCustomer(Long taskId, Integer taskSource, String memberId,
			String customerMob, String canselReason,String siteName) {
		String date=DateUtil.getNowTime();
		logger.debug("取消任务 --->给客户推送消息：任务ID "+taskId+",客户Id "+memberId+",取消原因 "+canselReason+",调用日期  "+
				date);
		/** 获取短连接 **/
		String shortLink =this.noticeHttpClient.getPersonalLoadtUrl();
		/** 短信内容 **/
		String smsStr=this.cancelToCustomer.replace("${reason}", this.haddleNull(canselReason))
							.replace("${loadUrl}", this.haddleNull(shortLink));;
		/** 推送内容 **/
		String noticeStr=this.cancelNoticeToCustomer.replace("${reason}", this.haddleNull(canselReason));;
		/** 客户是站点直接发消息 **/
		if (taskSource == Constant.TASK_SOURCE_EXP || taskSource == Constant.TASK_SOURCE_OKDI) {
			this.doSmsSend(customerMob, smsStr, null);
		} else if (taskSource == Constant.TASK_SOURCE_PERSONAL
				|| taskSource == Constant.TASK_SOURCE_ORDERS) {
			if (!PubMethod.isEmpty(memberId)) {
				String channelNo = "";
				if (taskSource == Constant.TASK_SOURCE_PERSONAL) {
					channelNo = Constant.PUSH_TYPE_PERSONAL;// 02:个人端
				} else if (taskSource == Constant.TASK_SOURCE_ORDERS) {
					channelNo = Constant.PUSH_TYPE_ORDERS;// 01：接单王
				}
				
				/** 调用是否登录接口**/
				String result=ifOnline(channelNo, Long.valueOf(memberId));
				if(Constant.NOPUSH_NOSMS.equals(result)){
					return true;
				}
				/** 登录状态下发推送 **/
				if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
					this.pushExt(result,channelNo, memberId.toString(), Constant.PUSH_CANCEL_TYPE, 
							Constant.PUSH_TITLE, noticeStr,taskId == null ? "" : taskId.toString(), Constant.MSGTYPE_NOTICE);
					return true;
				}
				this.doSmsSend(customerMob, smsStr, null);
				return true;
			} else {
				/** 客户未登录直接发短信 **/
				this.doSmsSend(customerMob, smsStr, null);
				return true;
			}
		}
		return true;
	}

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
	@Override
	public boolean cancelToSite(String cancelType, Long taskId, String memberId, String siteName,
			String sendMob, String sendName, String sendAddress, String cancelName,
			String cancelReason, int taskNum) {
		String date=DateUtil.getNowTime();
		logger.debug("营业分部/收派员取消任务--->给站点/营业分部推送消息：任务ID "+taskId+",站点/营业分部ID "+memberId+",取消原因 "+cancelReason+",调用日期  "+
				date);
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		String title = "";
		/** 收派员取消任务 **/
		if (Constant.TASK_CANCEL_EXP.equals(cancelType) && cancelType != null) {
			title = this.cancelTaskByExp;
		}
		/** 营业分部取消任务 **/
		if (Constant.TASK_CANCEL_BUSINES.equals(cancelType) && cancelType != null) {
			title = this.cancelTaskBySite;
		}
		String content = this.getSendInfo(sendMob, sendName, sendAddress);
		/** 调用消息推送接口 **/
		this.noticeHttpClient.pushSocketPC(Constant.PUSH_TYPE_PC,memberId, Constant.PUSH_CANCEL_TYPE, title,
				cancelReason, content, taskId == null ? "" : taskId.toString(), taskNum);
		return true;
	}

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
	@Override
	public boolean cancelToExpMember(Long taskId, Long memberId, String sendName, String sendMob,
			String sendAddress, String expMemberMob) {
		String date=DateUtil.getNowTime();
		logger.debug("任务取消 --->给收派员推送消息：任务ID "+taskId+",收派员ID "+memberId+",调用日期  "+
				date);
		/** 防止空指针异常 **/
		if (PubMethod.isEmpty(sendAddress)) {
			sendAddress = "";
		}
		if (PubMethod.isEmpty(sendName)) {
			sendName = "";
		}
		if (PubMethod.isEmpty(sendMob)) {
			sendMob = "";
		}
		String messege = this.cancelToExpMember.replace("${sendAddress}", sendAddress).replace(
				"${sendName}", sendName).replace("${sendmobile}", sendMob);
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_CANCEL_TYPE, 
					cancelTaskNoticeTitle,messege, taskId == null ? "" : taskId.toString(), Constant.MSGTYPE_NOTICE);
			//return true;  modify 20150707   取消任务发送推送短信
		}
		/** 收派员未登录APP直接发短信 **/
		this.doSmsSend(expMemberMob, messege, null);
		return true;
	}

	/**
	 * 人员离职推送消息（接单王）
	 * @param memberId 收派员ID
	 * @param mob 离职人员手机号
	 * @return
	 */
	public boolean quitToExpMember(Long memberId, String mob ,int roleId) {
		String date=DateUtil.getNowTime();
		logger.debug("人员离职推送消息（接单王）：收派员ID "+memberId+",离职人员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg=null;
		String putMsg=null;
		if(roleId==-1||roleId==0||roleId==1){
			 msg=quitNoticeToExp;
			 putMsg=putQuitNotice;
		}else{
			 msg=quitNoticeToExpDS;
			 putMsg=putQuitNoticeDS;
		}
		logger.debug(">>>>>人员离职推送消息内容： "+msg+",短信内容： "+putMsg);
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_NOTICE_TYPE, 
					Constant.PUSH_TITLE,putMsg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}else
		{
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}

	/**
	 * 客服拒绝身份验证(发短信)
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	@Override
	public boolean refuseToExp(Long memberId, String mob) {
		/** 收派员手机号为空返回发送失败 **/
		if (PubMethod.isEmpty(mob)) {
			return false;
		}
		/** 调用发送短信接口 **/
		this.doSmsSend(mob,refuseVerify, null);
		return true;
	}

	/**
	 * 客服拒绝归属验证
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @return
	 */
	@Override
	public boolean refuseRelationToExp(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("客服拒绝归属验证：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg=refuseRelation;
		String msgTZ=refuseRelationTZ;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_NOTICE_REFUSE, 
					Constant.PUSH_TITLE,msgTZ, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		/** 发送短信情况下如果手机号为空返回推送失败 **/
		if (PubMethod.isEmpty(mob)) {
			return false;
		}
		/** 非登录状态下发送短信 **/
		this.doSmsSend(mob,msg, null);
		return true;
	}
	
	
	/**
	 * 	站点认证失败 chunyang.tan add
	 * @param memberId memberId
	 * @param mob 登陆人手机号
	 * @return
	 */
	@Override
	public boolean refuseNetDotAuthenticat(Long memberId, String mob,Short compStatus, String refuseDesc) {
		String date=DateUtil.getNowTime();
		logger.debug("站点认证失败：网点ID "+memberId+",公司手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg=refuseNetDotAuthenticat;
		String sendMsg=refuseNetDotSendMsg;
		if(refuseDesc!=null){
			msg = msg.replace("您可重新提交资料", "原因："+refuseDesc+"，您可重新提交资料");
			sendMsg = sendMsg.replace("您可重新提交资料", "原因："+refuseDesc+"，您可重新提交资料");
		}
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.NET_DOT_NOTICE_REFUSE, 
					Constant.PUSH_TITLE,msg, compStatus+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}else
		{
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,sendMsg, null);
		}
		return true;
	}
	
	/**
	 * 	站点认证成功 chunyang.tan add
	 * @param compId 网点ID
	 * @param mob 公司手机号
	 * @return
	 */
	@Override
	public boolean successNetDotAuthenticat(Long memberId, String mob,Short compStatus) {
		String date=DateUtil.getNowTime();
		logger.debug("站点认证成功：网点ID "+memberId+",公司手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/*if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}*/
		/** 推送内容**/
		String msg=successNetDotAuthenticat;
		String sendMsg=successNetDotSendMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.NET_DOT_NOTICE_SUCCESS, 
					Constant.PUSH_TITLE,msg, compStatus+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}else
		{
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,sendMsg, null);
		}
		return true;
	}
	
	/**
	 * 	快递员、后勤申请加入该站点 chunyang.tan add
	 * @param memberId 站长memberId
	 * @param mob 登陆人手机号
	 * @return
	 */
	@Override
	public boolean applyJoinNet(Long memberId, String mob,Short compStatus) {
		String date=DateUtil.getNowTime();
		logger.debug("站点认证成功：站长memberId： "+memberId+",登陆人手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/*if(Constant.NOPUSH_NOSMS.equals(result)){  //如果没登录就不走下面的发推送
			return true;
		}*/
		/** 推送内容**/
		String msg=applyJoinNetDot;
		String sendMsg=applyJoinNetDotSendMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.APPLY_JOIN_NET_DOT, 
					Constant.PUSH_TITLE,msg, compStatus+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}else
		{   System.out.println("快递员、后勤申请加入该站点--发短信");
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,sendMsg, null);
		}
		return true;
	}
	
	
	/**
	 * 	客户回复 chunyang.tan add
	 * @param memberId 网点ID
	 * @param mob 公司手机号
	 * @return
	 * TODO
	 */
	@Override
	public boolean customerReply(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("站点认证失败：网点ID "+memberId+",公司手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容**/
		String msg=customerReply;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_CUSTOMER_REPLY, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		return true;
	}
	
	/**
	 * 	客户回复 chunyang.tan add
	 * @param memberId 网点ID
	 * @param mob 公司手机号
	 * @return
	 * TODO
	 */
	@Override
	public boolean robPush(Long memberId, String mob, String msg) {
		String date=DateUtil.getNowTime();
		logger.debug("站点认证失败：用户ID "+memberId+",快递员手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		noticeHttpClient.pushSocketPC("05", memberId.toString(), Constant.PUSH_TASK_ROB, Constant.PUSH_TITLE, "", robPush, msg, 0);
		return true;
	}
	
	@Override
	public boolean taskPush(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("站点认证失败：用户ID "+memberId+",快递员手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容**/
		String msg = taskPush;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_TASK_REPLY, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		return true;
	}
	
	/**
	 * 分配包裹给收派员推送消息
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @param parNum 包裹数量
	 * @return
	 */
	@Override
	public boolean parNoticeToExp(Long memberId, String mob, int parNum) {
		String date=DateUtil.getNowTime();
		logger.debug("分配包裹给收派员推送消息：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			/** 推送消息内容 **/
			String msg = this.parNoticeToExp.replace("${parNum}", String.valueOf(parNum));
			/** 调用推送接口 **/
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),
					Constant.PUSH_PARMSG_TOEXP, parTitle,msg, "", Constant.MSGTYPE_NOTICE);
			return true;
		}
		return true;
	}
	
	
	
	/** 
	 * 处理null字符串为""(防止空指针异常)
	 * @param str 需要处理的字符串
	*/
	private String haddleNull(String str){
		if (PubMethod.isEmpty(str)) {
			str = "";
		}
		return str;
	}
	
	/**
	 * 叫快递给收派员广播内容
	 */
	@Value("${broad.notice.toExp}")
	private String broadToExp;
	
	/**
	 * 收派员报价给客户推送内容
	 */
	@Value("${quote.notice.toCustomer}")
	private String quoteToCustomer;
	
	/**
	 * 竞价失败给收派员推送内容
	 */
	@Value("${bid.fail.notice.toExp}")
	private String bidFailNoticeToExp;
	/**
	 * 广播取消推送给已报价的收派员
	 */
	@Value("${bid.cancel.notice.toExp}")
	private String bidCancelNoticeToExp;
	
	/**
	 * 竞价陈功给收派员推送内容
	 */
	@Value("${bid.success.notice.toExp}")
	private String bidSuccessNoticeToExp;
	
	@Value("${call.notice.toExp}")
	private String callExp;
	
	@Value("${par.notice.toRecpt}")
	private String parNoticeToRecpt;
	
	@Value("${site.quote.toCustomer}")
	private String siteQuoteToCustomer;
	
	@Value("${quote.success.title}")
	private String quoteSuccessTitle;
	
	@Value("${quote.task.title}")
	private String quoteTaskTitle;
	
	@Value("${bid.success.title}")
	private String bidSuccessTitle;
	
	@Value("${member.title}")
	private String membertitle;
	
	@Value("${member.broad.notice}")
	private String memberBroadNotice;
	
	@Value("${member.broad.cancel}")
	private String memberBroadCancel;
	
	@Value("${config.sign.msg}")
	private String configSignMsg;
	
	/** 
	 * 叫快递给站点系统发送广播消息
	 * @param broadId 广播ID
	 * @param siteMemberId 站点ID
	 * @param distance 站点距离距发件人的公里数
	 * @param sendAddress 发件人的地址
	 * @param num 待抢单的任务数 
	 * @return
	*/
	public boolean broadNoticeToSite(Long broadId,Long siteMemberId,String distance,
			String sendAddress,int num){
		String date=DateUtil.getNowTime();
		logger.debug("叫快递给站点系统发送广播消息：广播ID "+broadId+",站点ID "+siteMemberId+",调用日期  "+
				date);
		/** 参数处理 **/
		if(PubMethod.isEmpty(broadId)){
			return false;
		}
		if(PubMethod.isEmpty(siteMemberId)){
			return false;
		}
		if (PubMethod.isEmpty(distance)) {
			distance = "";
		}
		if (PubMethod.isEmpty(sendAddress)) {
			sendAddress = "";
		}
		if(num==0){
			num=1;
		}
		/** 调用推送接口 **/
		this.noticeHttpClient.pushSocketPC(Constant.PUSH_TYPE_PC,String.valueOf(siteMemberId), Constant.PUSH_BROAD_TOEXP,quoteTaskTitle,
				"", "", String.valueOf(broadId), num);
		return true;
		
	}

	/** 
	 * 叫快递给收派员发送广播消息
	 * @param broadId 广播ID
	 * @param expMemberId 收派员ID
	 * @param distance 收派员距发件人的公里数
	 * @param sendAddress 发件人的地址
	 * @param parNum 包裹数量
	 * @return
	*/
	@Override
	public boolean broadNoticeToExp(Long broadId, Long expMemberId, String distance,
			String sendAddress, int parNum) {
		String date=DateUtil.getNowTime();
		logger.debug("叫快递给收派员发送广播消息：广播ID "+broadId+",收派员ID "+expMemberId+",调用日期  "+
				date);
		/** 参数处理 **/
		if (PubMethod.isEmpty(distance)) {
			distance = "";
		}
		if (PubMethod.isEmpty(sendAddress)) {
			sendAddress = "";
		}
		if(PubMethod.isEmpty(broadId)){
			return false;
		}
		if(PubMethod.isEmpty(expMemberId)){
			return false;
		}
		if(parNum==0){
			parNum=1;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,expMemberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容 **/
		String msg=this.broadToExp.replace("${distance}", distance).replace("${sendAddress}", sendAddress)
				.replace("${parNum}", String.valueOf(parNum));
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS,String.valueOf(expMemberId), Constant.PUSH_BROAD_TOEXP,
					Constant.PUSH_TITLE,msg, String.valueOf(broadId), Constant.MSGTYPE_NOTICE);
			return true;
		}
		
		return true;
	}
	//配送员
	public boolean broadNoticeToApp(Long broadId,Long memberId){
		String result=mobMemberLoginService.isDeliveryOnlineMember(Constant.PUSH_TYPE_MEMBER, memberId);
		System.out.println("#############################配送员发广播在线状态："+result);
		/** 登录状态下发推送 **/
		if (Constant.DELIVERY_ANDRIOD_LOG_ONLINE.equals(result)||Constant.DELIVERY_IOS_LOG.equals(result)) {
			System.out.println("#############################配送员发广播在线推送："+memberId);
			if(Constant.DELIVERY_ANDRIOD_LOG_ONLINE.equals(result)){
				result=Constant.YESPUSH_NOSMS_ANDRIOD;
			}else{
				result=Constant.YESPUSH_NOSMS_APPLE;
			}
			this.pushExt(result,Constant.PUSH_TYPE_MEMBER,String.valueOf(memberId), Constant.PUSH_BROAD_TOMEMBER,
					this.membertitle,this.memberBroadNotice, String.valueOf(broadId), Constant.MSGTYPE_NOTICE);
			System.out.println("#############################配送员发广播在线推送完成："+memberId);
			return true;
		}
		return true;
	}
	//配送员
	public boolean cancelNoticeToApp(Long broadId,Long memberId,String shopName,String orderNum){
		String result=mobMemberLoginService.isDeliveryOnlineMember(Constant.PUSH_TYPE_MEMBER, memberId);
		System.out.println("#############################配送员取消广播在线状态："+result);
		/** 登录状态下发推送 **/
		if (Constant.DELIVERY_ANDRIOD_LOG_ONLINE.equals(result)||Constant.DELIVERY_IOS_LOG.equals(result)) {
			/** 推送内容 **/
			System.out.println("#############################配送员取消广播在线推送："+memberId);
			String msg=this.memberBroadCancel.replace("${shopName}", shopName).replace("${orderNum}", orderNum);
			if(Constant.DELIVERY_ANDRIOD_LOG_ONLINE.equals(result)){
				result=Constant.YESPUSH_NOSMS_ANDRIOD;
			}else{
				result=Constant.YESPUSH_NOSMS_APPLE;
			}
			this.pushExt(result,Constant.PUSH_TYPE_MEMBER,String.valueOf(memberId), Constant.PUSH_CANCELBROAD_TOMEMBER,
					this.membertitle,msg, String.valueOf(broadId), Constant.MSGTYPE_NOTICE);
			System.out.println("#############################配送员取消广播在线推送完成："+memberId);
			return true;
		}
		return true;
	}
	
	//用户确认签收，扫码推送
	public boolean configSignToApp(Long broadcastId,Long memberId,String tradeNum,String totalAmount){
		String result=mobMemberLoginService.isDeliveryOnlineMember(Constant.PUSH_CONFIG_SIGN, memberId);
		System.out.println("#############################用户确认签收状态状态："+result);
		/** 登录状态下发推送 **/
		if (Constant.DELIVERY_ANDRIOD_LOG_ONLINE.equals(result)||Constant.DELIVERY_IOS_LOG.equals(result)) {
			/** 推送内容 **/
			System.out.println("#############################用户确认签收状态在线推送："+memberId);
			String msg=this.memberBroadCancel.replace("${tradeNum}", tradeNum).replace("${totalAmount}", totalAmount);
			if(Constant.DELIVERY_ANDRIOD_LOG_ONLINE.equals(result)){
				result=Constant.YESPUSH_NOSMS_ANDRIOD;
			}else{
				result=Constant.YESPUSH_NOSMS_APPLE;
			}
			this.pushExt(result,Constant.PUSH_TYPE_MEMBER,String.valueOf(memberId), Constant.PUSH_CONFIG_SIGN,
					this.membertitle,msg, String.valueOf(broadcastId), Constant.MSGTYPE_NOTICE);
			System.out.println("#############################用户确认签收状态在线推送完成："+memberId);
			return true;
		}
		return true;
	}
	/** 
	 * 收派员报价后给客户推送消息
	 * @param broadId 广播ID
	 * @param cstmMemberId 客户ID
	 * @param expName 收派员姓名
	 * @param expNetName 收派员所属网络
	 * @return
	*/
	@Override
	public boolean expQuoteToCustomer(Long broadId, Long cstmMemberId, String expName,
			String expNetName) {
		String date=DateUtil.getNowTime();
		logger.debug("收派员报价后给客户推送消息：广播ID "+broadId+",客户ID "+cstmMemberId+",调用日期  "+
				date);
		/** 参数处理 **/
		if (PubMethod.isEmpty(expName)) {
			expName = "";
		}
		if (PubMethod.isEmpty(expNetName)) {
			expNetName = "";
		}
		if(PubMethod.isEmpty(broadId)){
			return false;
		}
		if(PubMethod.isEmpty(cstmMemberId)){
			return false;
		}
		
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_PERSONAL,cstmMemberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容 **/
		String msg=this.quoteToCustomer.replace("${expNetName}", expNetName).replace("${expName}", expName);
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			/** 调用推送 **/
			this.pushExt(result,Constant.PUSH_TYPE_PERSONAL, String.valueOf(cstmMemberId), Constant.PUSH_BROAD_QOUTE,
					Constant.PUSH_TITLE,msg,String.valueOf(broadId), Constant.MSGTYPE_NOTICE);
			logger.debug("收派员报价后给客户推送消息：,内容 "+msg+",调用日期  "+date);
			return true;
		}
		return true;
	}
	
	/** 
	 * 站点报价后给客户推送消息
	 * @param broadId 广播ID
	 * @param cstmMemberId 客户ID
	 * @param siteName 公司名称
	 * @return
	*/
	public boolean siteQuoteToCustomer(Long broadId,Long cstmMemberId,String siteName){
		String date=DateUtil.getNowTime();
		logger.debug("站点报价后给客户推送消息：广播ID "+broadId+",客户ID "+cstmMemberId+",调用日期  "+
				date);
		if(PubMethod.isEmpty(broadId)){
			return false;
		}
		if(PubMethod.isEmpty(cstmMemberId)){
			return false;
		}
		if (PubMethod.isEmpty(siteName)) {
			siteName = "";
		}
		
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_PERSONAL,cstmMemberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容 **/
		String msg=this.siteQuoteToCustomer.replace("${siteName}", siteName);
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_PERSONAL, String.valueOf(cstmMemberId), Constant.PUSH_BROAD_QOUTE,
					Constant.PUSH_TITLE,msg,String.valueOf(broadId), Constant.MSGTYPE_NOTICE);
			logger.debug("站点报价后给客户推送消息：,内容 "+msg+",调用日期  "+date);
			return true;
		}
		return true;
	}
	
	/** 
	 * 收派员竞价失败后推送消息
	 * @param broadId 广播ID
	 * @param expMemberId 收派员ID
	 * @param sendAddress 发件人地址
	 * @return
	*/
	@Override
	public boolean bidFailToExp(Long broadId,Long expMemberId,String sendAddress){
		/** 参数处理 **/
		if(PubMethod.isEmpty(expMemberId)){        
			return false;                          
		}                                          
		if(PubMethod.isEmpty(broadId)){            
			return false;                          
		}                                          
		if (PubMethod.isEmpty(sendAddress)) {      
			sendAddress = "";                      
		}                                          
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,expMemberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容 **/
		String msg=this.bidFailNoticeToExp;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS,String.valueOf(expMemberId), Constant.PUSH_ROB_failed,
					Constant.PUSH_TITLE,msg, String.valueOf(broadId), Constant.MSGTYPE_NOTICE);
			return true;
		}
		return true;
	}
	
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
	@Override
	public boolean bidSuccessToSite(Long broadId,Long siteMemberId,Long taskId,String sendName,
			String sendMob,String sendAddress){
		String date=DateUtil.getNowTime();
		logger.debug("站点竞价成功后推送消息：广播ID "+broadId+",站点ID "+siteMemberId+",任务ID "+taskId+",调用日期  "+
				date);
		if(PubMethod.isEmpty(siteMemberId)){
			return false;
		}
		if(PubMethod.isEmpty(broadId)){
			return false;
		}
		if(PubMethod.isEmpty(taskId)){
			return false;
		}
		if (PubMethod.isEmpty(sendAddress)) {
			sendAddress = "";
		}
		/** 推送内容 **/
		String content=this.getSendInfo(sendMob, sendName, sendAddress);
		/** 调用推送接口 **/
		this.noticeHttpClient.pushSocketPC(Constant.PUSH_TYPE_PC,String.valueOf(siteMemberId), Constant.BID_SUCCESS_NOTICE,
				quoteSuccessTitle,"", content, String.valueOf(broadId), 1);
		return true;
	}
	
	/** 
	 * 收派员竞价成功后推送消息
	 * @param broadId 广播ID
	 * @param expMemberId 收派员ID
	 * @param expMob 收派员手机号
	 * @param taskId 任务ID
	 * @param sendAddress 发件人地址
	 * @return
	*/
	@Override
	public boolean bidSuccessToExp(Long broadId,Long expMemberId,String expMob,Long taskId,String sendAddress){
		String date=DateUtil.getNowTime();
		logger.debug("收派员竞价成功后推送消息：广播ID "+broadId+",收派员ID "+expMemberId+",任务ID "+taskId+",调用日期  "+
				date);
		if(PubMethod.isEmpty(expMemberId)){
			return false;
		}
		if(PubMethod.isEmpty(broadId)){
			return false;
		}
		if(PubMethod.isEmpty(taskId)){
			return false;
		}
		if (PubMethod.isEmpty(sendAddress)) {
			sendAddress = "";
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,expMemberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容 **/
		String msg=this.bidSuccessNoticeToExp.replace("${sendAddress}", sendAddress);
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS,String.valueOf(expMemberId), Constant.BID_SUCCESS_NOTICE,
					bidSuccessTitle,msg, String.valueOf(broadId), Constant.MSGTYPE_NOTICE);
			return true;
		}
		if (!PubMethod.isEmpty(expMob)) {
			this.doSmsSend(expMob,msg, null);
			return true;
		}
		return true;
	}

	/** 
	 * 呼叫快递哥发送短信
	 * @param mobile  快递哥手机号
	 * @return
	*/
	@Override
	public boolean callExp(String mobile) {
		if(PubMethod.isEmpty(mobile)){
			return false;
		}
		/** 短连接 **/
		String shortLink =this.noticeHttpClient.getExpLoadtUrl();
		/** 短信内容 **/
		String msg=this.callExp.replace("${loadLink}", shortLink==null?"":shortLink);
		this.doSmsSend(mobile, msg, "");
		return true;
	}
	
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
	@Override
	public boolean sendToRecpt(Long parceId,String expWaybillNum,Long netId,String sendName,String recptMob,
			String expMemberLon,String expMemberLat,String expMemberMob) {
		String date=DateUtil.getNowTime();
		logger.debug("确认交寄后，向已填写的收件人电话发送推送：包裹ID "+parceId+",调用日期  "+
				date);
		if(PubMethod.isEmpty(recptMob)){
			return false;
		}
		if(PubMethod.isEmpty(expMemberMob)){
			return false;
		}
		if(PubMethod.isEmpty(parceId)){
			return false;
		}
		if(PubMethod.isEmpty(expWaybillNum)){
			return false;
		}
		if(PubMethod.isEmpty(netId)){
			return false;
		}
		if(PubMethod.isEmpty(sendName)){
			sendName="";
		}
		//额外参数拼接
		String extraParam=getExtraParam(parceId,expWaybillNum,netId);
		if(PubMethod.isEmpty(extraParam)){
			return false;
		}
		/** 推送消息内容 **/
		String noticeMsg=this.parNoticeToRecpt.replace("${link}","").replace("${recptName}", sendName);
		String ifReg=this.noticeHttpClient.ifReg(recptMob);
		if (!PubMethod.isEmpty(ifReg) && JSON.parseObject(ifReg).get("registered").equals(true)){
			Long memberId=(Long) JSON.parseObject(ifReg).get("memberId");
			/** 调用是否登录接口**/
			String result=ifOnline(Constant.PUSH_TYPE_PERSONAL,memberId);
			if(Constant.NOPUSH_NOSMS.equals(result)){
				return true;
			}
			/** 登录状态下发推送 **/
			if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
				this.pushExt(result,Constant.PUSH_TYPE_PERSONAL,String.valueOf(memberId), Constant.PUSH_CONFIRM_SENT,
						Constant.PUSH_TITLE,noticeMsg,extraParam, Constant.MSGTYPE_NOTICE);
				return true;
			}
		}
		/** 未注册和注册了不在线都是发送短信 **/
		this.sendSmsToRept(sendName,expMemberLon, expMemberLat, expMemberMob, recptMob);
		return true;
	}
	
	/** 拼接额外参数 **/
	private String getExtraParam(Long parceId,String expWaybillNum,Long netId){
		if(PubMethod.isEmpty(parceId)){
			return "";
		}
		if(PubMethod.isEmpty(expWaybillNum)){
			return "";
		}
		if(PubMethod.isEmpty(netId)){
			return "";
		}
		String netCode="";
		BasNetInfo netInfo= this.ehcacheService.get("netCache", netId.toString(), BasNetInfo.class);
		if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
			netInfo = this.netInfoMapper.findById(netId);
		}
		if(!PubMethod.isEmpty(netInfo)&&!PubMethod.isEmpty(netInfo.getCode())){
			netCode=netInfo.getCode();
		}
		if(PubMethod.isEmpty(netCode)){
			return "";
		}
		StringBuilder builer = new StringBuilder();
		builer.append(parceId);
		builer.append("|");
		builer.append(expWaybillNum);
		builer.append("|");
		builer.append(netCode);
		return builer.toString();
	}
	
	private void sendSmsToRept(String sendName,String expMemberLon,String expMemberLat,String expMemberMob,String recptMob){
		/** 获取短连接 **/
		String shortLink ="";
		if(!PubMethod.isEmpty(expMemberLon)&&!PubMethod.isEmpty(expMemberLat)){
			shortLink = this.noticeHttpClient.getPersonalShortUrl(expMemberLon, expMemberLat,
					expMemberMob);
		}
		/** 短信内容 **/
		String msg=this.parNoticeToRecpt.replace("${link}", shortLink==null?"":shortLink).replace("${recptName}", sendName);
		this.doSmsSend(recptMob, msg, "");
	}
	

	/** 
	 * 根据用户类型和用户ID判断此用户是否在线
	 * @param channelNo 用户类型
	 * @param memberId 用户ID
	 * @return true登录 false未登录
	*/
	private String ifOnline(String channelNo, Long memberId){
		/** channelNo(01:好递哥 02:个人端)为空返回未登录 **/
		if (PubMethod.isEmpty(channelNo)) {
			return Constant.NOPUSH_NOSMS;
		}
		/** memberId为空返回未登录 **/
		if (PubMethod.isEmpty(memberId)) {
			return Constant.NOPUSH_NOSMS;
		}
		/** 调用是否登录接口 **/
		String result= mobMemberLoginService.isOnlineMember(channelNo, memberId);
		logger.debug("调用是否登录接口的返回值："+result);
		if (PubMethod.isEmpty(result)) {
			return Constant.NOPUSH_NOSMS;
		}   
		 return result;
	}
	
	/** 
	 * 手机端消息推送
	 * @param mobType 手机类型(APPLE:苹果 ANDRIOD:安卓 ) 
	 * @param channelNo 用户类型(01:好递哥 02:个人端)
	 * @param memberId 用户ID
	 * @param type 业务类型
	 * @param title 消息标题
	 * @param content 消息内容
	 * @param extraParam 额外参数
	 * @param msgType 消息推送类型(分为通知：notice和消息：message)
	*/
	private void pushExt(String mobType,String channelNo,String memberId, String type, String title, String content,
			String extraParam, String msgType){
		String date=DateUtil.getNowTime();
		String contentStr=getContent(content,extraParam,date);
		this.mobMemberLoginService.pushExt(mobType,channelNo,memberId,type, title, contentStr, extraParam, msgType);
		logger.debug("手机端消息推送:调用日期#"+date+",用户类型#"+channelNo+",用户ID#"+
				memberId+",业务类型#"+type+",消息标题#"+title+",消息内容#"+contentStr+",额外参数#"+extraParam);
	}
	
	/**
	 * @Description: TODO(推送消息，专用推广用) 
	 * @param mobType
	 * @auth amssy
	 */
	private void pushExtForSendTS(String mobType,String channelNo,String memberId, String type, String title, String content,
			String extraParam, String msgType,String deviceType,String deviceToken){
		String date=DateUtil.getNowTime();
		String contentStr=null;
		if(Constant.YESPUSH_NOSMS_ANDRIOD.equals(mobType)){ //安卓数据
			//contentStr=content+","+date;
			contentStr=content;
		}else{
			contentStr =  content;
			extraParam = title+","+date;
		}
		
		this.mobMemberLoginService.pushExtForSendTS(mobType,channelNo,memberId,type, title, contentStr, extraParam, msgType,deviceType,deviceToken);
		logger.debug("手机端消息推送:调用日期#"+date+",用户类型#"+channelNo+",用户ID#"+
				memberId+",业务类型#"+type+",消息标题#"+title+",消息内容#"+contentStr+",额外参数#"+extraParam);
	}
	
	/** 
	 * 发送短信
	 * @param usePhone 目的手机号
	 * @param content 短信内容
	 * @param extraCommonParam 额外参数
	 * @since v1.0
	*/
	private void doSmsSend(String usePhone, String content, String extraCommonParam){
		//String timeStr=getPushTimeStr("");
		this.noticeHttpClient.doSmsSend(Constant.SMS_CHANNEL_NO_EXP, Constant.SMS_CHANNEL_ID, usePhone,
				content, extraCommonParam);
	}
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
	@Override
	public void sendSmsAudit(String memberPhone, String receiver_mobile,
			Short sms_source, Short phone_type ,Short sendSuccess , String sendVersion) {
		List<ParSmsAudit> parList = new ArrayList<ParSmsAudit>();
		Long memberId = memberInfoService.getMemberId(memberPhone);
		String[] split = receiver_mobile.split(",");
		for(int i = 0; i < split.length; i++ ){
			ParSmsAudit parSmsAudit = new ParSmsAudit();
			parSmsAudit.setId(IdWorker.getIdWorker().nextId());
			parSmsAudit.setMemberId(memberId);
			parSmsAudit.setReceiverMobile(split[i]);
			parSmsAudit.setMemberMobile(memberPhone);
			parSmsAudit.setSmsSource(sms_source);
			parSmsAudit.setPhoneType(phone_type);
			parSmsAudit.setCreateTime(new Date());
			parSmsAudit.setSendSuccess(sendSuccess);
			parSmsAudit.setSendVersion(sendVersion);
			parList.add(parSmsAudit);
		}
		sendNoticeMapper.sendSmsAudit(parList);
	}
	
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
	@Override
	public void sendSmsAuditOnKnownMemberId(String memberPhone, String receiver_mobile,
			Short sms_source, Short phone_type ,Short sendSuccess , String sendVersion,Long memberId) {
		List<ParSmsAudit> parList = new ArrayList<ParSmsAudit>();
		//Long memberId = memberInfoService.getMemberId(memberPhone);
		String[] split = receiver_mobile.split(",");
		for(int i = 0; i < split.length; i++ ){
			ParSmsAudit parSmsAudit = new ParSmsAudit();
			parSmsAudit.setId(IdWorker.getIdWorker().nextId());
			parSmsAudit.setMemberId(memberId);
			parSmsAudit.setReceiverMobile(split[i]);
			parSmsAudit.setMemberMobile(memberPhone);
			parSmsAudit.setSmsSource(sms_source);
			parSmsAudit.setPhoneType(phone_type);
			parSmsAudit.setCreateTime(new Date());
			parSmsAudit.setSendSuccess(sendSuccess);
			parSmsAudit.setSendVersion(sendVersion);
			parList.add(parSmsAudit);
		}
		sendNoticeMapper.sendSmsAudit(parList);
	}

	@Override
	public boolean cancelBroadcastToMember(Long broadId, Long expMemberId,
			String sendAddress) {
		/** 参数处理 **/
		if(PubMethod.isEmpty(expMemberId)){        
			return false;                          
		}                                          
		if(PubMethod.isEmpty(broadId)){            
			return false;                          
		}                                          
		if (PubMethod.isEmpty(sendAddress)) {      
			sendAddress = "";                      
		}                                          
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,expMemberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容 **/
		String msg=this.bidCancelNoticeToExp;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS,String.valueOf(expMemberId), Constant.PUSH_ROB_failed,
					Constant.PUSH_TITLE,msg, String.valueOf(broadId), Constant.MSGTYPE_NOTICE);
			return true;
		}
		return true;
	}
	/**
	 * 更改角色信息
	 * @param  人员id
	 * @param userId	操作人id(当前站长id) 
	 * @return
	 */
	@Override
	public boolean updateRoleInfo(Long memberId,Long userId,String phone) {
		String date=DateUtil.getNowTime();
		logger.debug("更改角色信息：人员id "+memberId+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			/** 推送消息内容 **/
			String msg = this.roleInfo;
			/** 调用推送接口 **/
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),
					Constant.NET_DOT_ADJUST,Constant.PUSH_TITLE, msg, "", Constant.MSGTYPE_MESSAGE);
			return true;
		}else{
			String msg = this.roleSmsInfo;
			this.doSmsSend(phone, msg, null);
			return true;
		}
	}
	@Override
	public boolean asigntoExpMemberAmssy(Long taskId, Long memberId,
			String sendLon, String sendLat, String sendName, String sendMobile,
			String sendAddress, Long netId, String expMemberMob,
			String sendDetailAddress, String noticeType, String takeTime) {
		String date=DateUtil.getNowTime();
		logger.debug("任务分配给收派员--->给收派员推送消息：任务ID "+taskId+",收派员Id "+memberId+",调用日期  "+
				date);
		if(PubMethod.isEmpty(takeTime)){
			 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			 takeTime = sdf.format(date);
		}

		/** 防止空指针异常 **/
		if (PubMethod.isEmpty(sendName)) {
			sendName = "";
		}
		if (PubMethod.isEmpty(sendAddress)) {
			sendAddress = "";
		}
		if (PubMethod.isEmpty(sendMobile)) {
			sendMobile = "";
		}
		/** 短连接 **/
		String shortLink = "";
		/*if (!PubMethod.isEmpty(sendDetailAddress)) {
			shortLink = this.noticeHttpClient.getOkdiShortUrl(sendDetailAddress, sendMobile);
		}*/
		shortLink = this.noticeHttpClient.getPromoOkdiExpressUrl();
		String messege = this.assignToExpMemberAmssy.replace("${sendAddress}", sendAddress).replace(
				"${sendName}", sendName).replace("${sendmobile}", sendMobile).replace("${link}",
				shortLink == null ? "" : shortLink).replace("${takeTime}", takeTime);
		if(Constant.NOTICE_TYPE.equals(noticeType)){
			/** 发送短信 **/
			System.out.println("张梦楠发送短息=======message  :  "+messege);
			this.doSmsSend(expMemberMob, messege, null);
			return true;
		}
		System.out.println("**********************执行了此推送，收派员手机号："+expMemberMob);
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS, memberId);
		System.out.println("**********************收派员在线状态："+result);
		/*if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}*///20150717  wangbowei  需求变更
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {// 如果登录接单王APP 直接推送消息
			/*messege = this.noticeToExpMember.replace("${sendAddress}", sendAddress).replace(
					"${sendName}", sendName).replace("${sendMobile}", sendMobile);*/
			messege = this.assignToExpMemberAmssy.replace("${sendAddress}", sendAddress).replace(
					"${sendName}", sendName).replace("${sendmobile}", sendMobile).replace("${link}",
							"").replace("${takeTime}", takeTime);
			/** 调用消息推送接口 **/
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),
					Constant.PUSH_ASSIGN_TYPE, assignTaskNoticeTitle, messege, taskId == null ? "" : taskId
							.toString(), Constant.MSGTYPE_NOTICE);
			// add by zhaishihe 20150717  wangbowei  需求变更begin
			System.out.println("******************************************test out begin************************************************");
			System.out.println("*************登录状态下推送完成，执行短信发送");
			System.out.println("*************收派员手机号："+expMemberMob);
			messege = this.assignToExpMemberAmssy.replace("${sendAddress}", sendAddress).replace(
					"${sendName}", sendName).replace("${sendmobile}", sendMobile).replace("${link}",
							shortLink == null ? "" : shortLink).replace("${takeTime}", takeTime);
			if (!PubMethod.isEmpty(expMemberMob)) {
				this.doSmsSend(expMemberMob, messege, null);
				System.out.println("*************发送短信内容："+messege);
			}
			System.out.println("******************************************test out end***************************************************");
			// add end
			return true;
		}
		// 如果未登录接单王 直接发短信 收派员收短信是有次数校验的目前只能接收3条
		/** 收派员手机号为空 返回失败 **/
		if (PubMethod.isEmpty(expMemberMob)) {
			return false;
		}
		// 超过短信限制条数，不再发送短信
		/*int smsTotal = this.basVerifyRecoredMapper.getSmsTotalOfExp(expMemberMob);
		if (smsTotal >= Integer.valueOf(expSmsTotal)) { 
			logger.debug("超过短信限制条数，不再给收派员发送短信。收派员电话："+expMemberMob+",收派员ID:"+memberId);
			return false;
		}*/
		/** 发送短信 **/
		this.doSmsSend(expMemberMob, messege, null);
		/** 任务分派给收派员发送短信 保存短信记录 **/
		/*this.basVerifyRecoredMapper.insert(getVerifyRecord(memberId, expMemberMob, messege));*/
		return true;
	}

	/**
	 * 客服通过身份认证
	 * @param memberId 人员id
	 * @param mob	人员手机号
	 */
	@Override
	public boolean passRealNameCheck(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("客服通过身份认证：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg = passRealNameMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_PASS_REALNAME_TYPE, 
					Constant.PUSH_TITLE,msg, memberId+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			/** 发送短信情况下如果手机号为空返回推送失败 **/
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}
	/**
	 * 客服拒绝身份认证
	 * @param memberId 人员id
	 * @param mob	人员手机号
	 */
	@Override
	public boolean refuseRealNameCheck(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("客服拒绝身份认证：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg = refuseRealNameMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_REFUSE_REALNAME_TYPE, 
					Constant.PUSH_TITLE,msg, memberId+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			/** 发送短信情况下如果手机号为空返回推送失败 **/
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}
	/**
	 * 客服通过快递认证
	 * @param memberId 人员id
	 * @param mob	人员手机号
	 */
	@Override
	public boolean passExpressCheck(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("客服通过快递认证：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg = passExpressMsg;//短信
		String msgTZ = passExpressMsgTZ;//通知
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_PASS_EXPRESS_TYPE, 
					Constant.PUSH_TITLE,msgTZ, memberId+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			/** 发送短信情况下如果手机号为空返回推送失败 **/
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}
	/**
	 * 客服拒绝快递认证
	 * @param memberId 人员id
	 * @param mob	人员手机号
	 */
	@Override
	public boolean refuseExpressCheck(Long memberId, String mob,String remark) {
		String date=DateUtil.getNowTime();
		logger.debug("客服拒绝快递认证：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg = refuseExpressMsg;
		String msgTZ = refuseExpressMsgTZ;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_REFUSE_EXPRESS_TYPE, 
					Constant.PUSH_TITLE,msgTZ.replace("请按要求重新上传", "原因："+remark+"，请按要求重新上传"), memberId+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			/** 发送短信情况下如果手机号为空返回推送失败 **/
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,msg.replace("请登录好递快递员", "原因："+remark+"，请登录好递快递员"), null);
		}
		return true;
	}
	/**
	 * (代收站, 店长审核店员)店员归属失败
	 * @param memberId 店员id
	 * @param mob 店员手机号
	 * @return
	 */
	@Override
	public boolean sendPushSMSRemindRefuse(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("代收站店长审核店员拒绝：店员ID "+memberId+",店员手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg=siteSendMsgfail;
		String msg1=refuseMsgSendfail;
		
		/** 非登录状态下发送短信 **/
		//this.doSmsSend(mob,msg, null);
		
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_REFUSE_ASSISTANT_TYPE, 
					Constant.PUSH_TITLE,msg1, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			//发送短信情况下如果手机号为空返回推送失败 
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			// 非登录状态下发送短信 
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}
	
	/**
	 * 	(代收站, 店长审核店员)店员归属成功 jiong.zheng
	 * @param memberId 店员ID
	 * @param mob 店员手机号
	 * @return
	 */
	@Override
	public boolean sendPushSMSRemindSuccess(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("代收站店长审核店员成功：店员ID "+memberId+",店员手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/*if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}*/
		/** 推送内容**/
		String msg=siteSendMsgSuccess;
		String msg1=refuseMsgSendSuccess;
		
		/** 非登录状态下发送短信 **/
		//this.doSmsSend(mob,msg, null);
		
		// 登录状态下发推送
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_PASS_ASSISTANT_TYPE, 
					Constant.PUSH_TITLE,msg1, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			// 发送短信情况下如果手机号为空返回推送失败 **
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			// 非登录状态下发送短信 **
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}

	/**
	 * 身份认证失败 jiong.zheng
	 * @param memberId 店长店员ID
	 * @param mob 店长店员手机号
	 * @return
	 */
	public boolean sendPushSMSRemindIdentityRefuse(Long memberId, String mob,String refuseDesc) {
		String date=DateUtil.getNowTime();
		logger.debug("店长店员身份审核失败：店长店员ID "+memberId+",店长店员手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/*if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}*/
		/** 推送内容**/
		String msg=identitySendMsgfail;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_REFUSE_IDENTITY_TYPE, 
					Constant.PUSH_TITLE,msg.replace("请按要求重新上传", "原因："+refuseDesc+"，请按要求重新上传"), "",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			/** 发送短信情况下如果手机号为空返回推送失败 **/
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}

	/**
	 * 身份认证成功 jiong.zheng
	 * @param memberId 店长店员ID
	 * @param mob 店长店员手机号
	 * @return
	 */
	public boolean sendPushSMSRemindIdentitySuccess(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("店长店员身份审核成功：店长店员ID "+memberId+",店长店员手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/*if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}*/
		/** 推送内容**/
		String msg=identitySendMsgSuccess;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_PASS_IDENTITY_TYPE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			/** 发送短信情况下如果手机号为空返回推送失败 **/
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}

	//店员申请入站,店长收推送,或者没登陆状态下收短信
	public boolean sendPushSMSManagerSuccess(Long memberId, String mob) {
		// TODO Auto-generated method stub
		String date=DateUtil.getNowTime();
		logger.debug("店员入驻店铺发送给店长推送成功：店长ID "+memberId+",店长手机号 "+mob+",调用日期  "+
				date);
		/** 网点ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/*if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}*/
		/** 推送内容**/
		String msg=managerSendMsgSuccess;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_PASS_MANAGER_TYPE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}else {
			/** 发送短信情况下如果手机号为空返回推送失败 **/
			if (PubMethod.isEmpty(mob)) {
				return false;
			}
			/** 非登录状态下发送短信 **/
			this.doSmsSend(mob,msg, null);
		}
		return true;
	}

	@Override
	public boolean passRelationToExp(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("客服通过归属验证：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg=passRelation;
		String msgTS=passRelationTS;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PASS_NOTICE_REFUSE, 
					Constant.PUSH_TITLE,msgTS, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		/** 发送短信情况下如果手机号为空返回推送失败 **/
		if (PubMethod.isEmpty(mob)) {
			return false;
		}
		/** 非登录状态下发送短信 **/
		this.doSmsSend(mob,msg, null);
		return true;
		
	}

	/**
	 * 为邀请者推送消息
	 */
	@Override
	public boolean sendMsgToInviter(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("为邀请者推送消息：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg = toInviterMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_TO_INVITER_TYPE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
//		/** 发送短信情况下如果手机号为空返回推送失败 **/
//		if (PubMethod.isEmpty(mob)) {
//			return false;
//		}
//		/** 非登录状态下发送短信 **/
//		this.doSmsSend(mob,msg, null);
		return true;
	}
	/**
	 * 为被邀请者推送消息
	 */
	@Override
	public boolean sendMsgToInvitee(Long memberId, String mob) {
		String date=DateUtil.getNowTime();
		logger.debug("为被邀请者推送消息：收派员ID "+memberId+",收派员手机号 "+mob+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg = toInviteeMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_TO_INVITEE_TYPE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
//		/** 发送短信情况下如果手机号为空返回推送失败 **/
//		if (PubMethod.isEmpty(mob)) {
//			return false;
//		}
//		/** 非登录状态下发送短信 **/
//		this.doSmsSend(mob,msg, null);
		return true;
	}
   
	/** 临时推广,非业务**/
	@Override
	public boolean sendTS(String memberId, String deviceType, String deviceToken,String title,String message,String extraParam) {
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		String result=null;
		if("android".equalsIgnoreCase(deviceType)){
			result="YN_ANDRIOD";
		}else{
			result="YN_APPLE";   
		}
		/** 登录状态下发推送 **/
		//if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			String aaa=null;
			if("1".equals(extraParam)){
				aaa="322";
			}else if("2".equals(extraParam)){
				aaa="321";
			}else{
				aaa="320";
			}
			
			if (deviceType.equals("android")) {
				if (PubMethod.isEmpty(memberId)) {
					memberId = deviceToken;
				}
			}
			this.pushExtForSendTS(result,Constant.PUSH_TYPE_ORDERS, memberId,aaa,title,message, extraParam,Constant.MSGTYPE_MESSAGE,deviceType,deviceToken);
			return true;
		//}
		//return true;
	}
	/**
	 * 申诉成功给新站长发送推送
	 * @param compName 公司名称
	 * @param memberId 人员id
	 * @param plaintPhone 申诉手机号
	 * @param compType 站点或者营业分部
	 */
	@Override
	public boolean sendAppealNewWebmasterSuccess(String compName,
			String memberId, String mob, String compType) {
			// TODO Auto-generated method stub
			String date=DateUtil.getNowTime();
			logger.debug("申诉人申诉成功推送成功：申诉ID "+memberId+",申诉手机号 "+mob+",调用日期  "+
					date);
			/** 网点ID为空返回推送失败 **/
			if (PubMethod.isEmpty(memberId)) {
				return false;
			}
			/** 调用是否登录接口**/
			String result=ifOnline(Constant.PUSH_TYPE_ORDERS,Long.valueOf(memberId));
			/*if(Constant.NOPUSH_NOSMS.equals(result)){
				return true;
			}*/
			/** 推送内容**/
			String msg=sendNewWebmasterSuccess;
			String[] strArr = sendNewWebmasterSuccess.split("-");
			msg = strArr[0]+compName+compType+strArr[1];
			/** 发推送 **/ //登录状态下
			if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
				this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.APPEAL_TO_SUCCESS_TYPE, 
						Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
				return true;
			}/*else {
				/** 发送短信情况下如果手机号为空返回推送失败 **//*
				if (PubMethod.isEmpty(mob)) {
					return false;
				}
				 //非登录状态下发送短信 
				this.doSmsSend(mob,msg, null);
			}*/
			return true;
	}

	/**
	 * 申诉成功给原站长发送推送
	 * @param compName 公司名称
	 * @param memberId 人员id
	 * @param plaintPhone 申诉手机号
	 * @param compType 站点或者营业分部
	 */
	public boolean sendAppealOldWebmasterSuccess(String compName,
			String memberId, String oldMemberPhone, String compType) {
			// TODO Auto-generated method stub
			String date=DateUtil.getNowTime();
			logger.debug("申诉人申诉失败推送成功：申诉ID "+memberId+",申诉手机号 "+oldMemberPhone+",调用日期  "+
					date);
			/** 网点ID为空返回推送失败 **/
			if (PubMethod.isEmpty(memberId)) {
				return false;
			}
			/** 调用是否登录接口**/
			String result=ifOnline(Constant.PUSH_TYPE_ORDERS,Long.valueOf(memberId));
			/*if(Constant.NOPUSH_NOSMS.equals(result)){
				return true;
			}*/
			/** 推送内容**/
			String msg=sendOldWebmasterSuccess;
			/** 发推送 **/ //登录状态下
			if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
				this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.OLD_TO_SUCCESS_TYPE, 
						Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
				return true;
			}/*else {
				/** 发送短信情况下如果手机号为空返回推送失败 **//*
				if (PubMethod.isEmpty(mob)) {
					return false;
				}
				 //非登录状态下发送短信 
				this.doSmsSend(mob,msg, null);
			}*/
			return true;
	}

	/**
	 * 申诉失败给申诉站长发送推送
	 * @param compName 公司名称
	 * @param memberId 人员id
	 * @param plaintPhone 申诉手机号
	 * @param compType 站点或者营业分部
	 */
	public boolean sendAppealNewWebmasterFailure(String compName,
			String memberId, String plaintPhone, String compType, String remark) {
		// TODO Auto-generated method stub
			String date=DateUtil.getNowTime();
			logger.debug("申诉人申诉失败推送成功：申诉ID "+memberId+",申诉手机号 "+plaintPhone+",调用日期  "+
					date);
			/** 网点ID为空返回推送失败 **/
			if (PubMethod.isEmpty(memberId)) {
				return false;
			}
			/** 调用是否登录接口**/
			String result=ifOnline(Constant.PUSH_TYPE_ORDERS,Long.valueOf(memberId));
			/*if(Constant.NOPUSH_NOSMS.equals(result)){
				return true;
			}*/
			/** 推送内容**/
			String msg=sendNewWebmasterFailure;
			
			String[] strArr = sendNewWebmasterFailure.split("-");
			msg = strArr[0]+compName+compType+strArr[1];
			msg = msg.split("=")[0]+remark;
			/** 发推送 **/ //登录状态下
			if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
				this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.APPEAL_TO_FAILURE_TYPE, 
						Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
				return true;
			}/*else {
				/** 发送短信情况下如果手机号为空返回推送失败 **//*
				if (PubMethod.isEmpty(mob)) {
					return false;
				}
				 //非登录状态下发送短信 
				this.doSmsSend(mob,msg, null);
			}*/
			return true;
	}

	/**
	 * 充值通讯费赠送奖励推送
	 */
	@Override
	public boolean sendSmsByRecharge(Double money, Long memberId) {
		String date=DateUtil.getNowTime();
		logger.debug("充值通讯费赠送奖励推送：收派员ID "+memberId+",奖励金额： "+money+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
//		if(Constant.NOPUSH_NOSMS.equals(result)){
//			return true;
//		}
		/** 推送内容**/
		String msg = String.valueOf(money)+rechargeRewardMsg;//xx元充值奖励已存入您的通信费账户，请查看钱包
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_RECHARGE_REWARD_TYPE, 
					Constant.PUSH_TITLE,msg, memberId+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}
//		else {
//			/** 发送短信情况下如果手机号为空返回推送失败 **/
//			if (PubMethod.isEmpty(mob)) {
//				return false;
//			}
//			/** 非登录状态下发送短信 **/
//			this.doSmsSend(mob,msg, null);
//		}
		return true;
	}

	@Override
	public boolean sendPushCalledCourier(Long memberId, String mobile) {
		String date=DateUtil.getNowTime();
		logger.debug("代收点叫快递推送：收派员ID "+memberId+",收派员电话： "+mobile+",调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		if(Constant.NOPUSH_NOSMS.equals(result)){
			return true;
		}
		/** 推送内容**/
		String msg = calledCourierMsg;//叫快递信息
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_CALLED_TYPE_MESSAGE, 
					Constant.PUSH_TITLE,msg, memberId+"",Constant.MSGTYPE_MESSAGE);
			return true;
		}
//		else {
//			/** 发送短信情况下如果手机号为空返回推送失败 **/
//			if (PubMethod.isEmpty(mob)) {
//				return false;
//			}
//			/** 非登录状态下发送短信 **/
//			this.doSmsSend(mob,msg, null);
//		}
		return true;
	}
	/**
	 * 派件转代收点推送
	 */
	@Override
	public boolean sendMsgByChangeAccept(Long memberId) {
		String date=DateUtil.getNowTime();
		logger.debug("为派件转代收点的人员推送消息：收派员ID "+memberId+","+"调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/** 推送内容**/
		String msg = changeAcceptMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_TO_CHANGE_ACCEPT_TYPE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		return true;
	}

	/**
	 * 代收点取消叫快递
	 */
	@Override
	public boolean sendCancelCancelCourier(Long memberId, String memberMobile) {
		String date=DateUtil.getNowTime();
		logger.debug("为派件转代收点的人员推送消息：收派员ID "+memberId+","+"调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/** 推送内容**/
		String msg = sendCancelCancelCourierMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_CANCEL_TYPE_MESSAGE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		return true;
	}
	/**
	 * 确认交寄给收派员发推送
	 */
	@Override
	public boolean sendPushConsigCourier(Long memberId,
			String toMemberPhone) {
		String date=DateUtil.getNowTime();
		logger.debug("为派件转代收点的人员推送消息：收派员ID "+memberId+","+"调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/** 推送内容**/
		String msg = sendConsigConsigCourierMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_CONSIG_TYPE_MESSAGE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		return true;
	}
	
	/**
	 * 收派员同意取件给代收点发送推送
	 */
	@Override
	public boolean sendPushNoticeCourier(Long memberId, String mobile) {
		String date=DateUtil.getNowTime();
		logger.debug("为派件转代收点的人员推送消息：收派员ID "+memberId+","+"调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/** 推送内容**/
		String msg = sendNoticeNoticeCourierMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_NOTICE_TYPE_MESSAGE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		return true;
	}

	/**
	 * 付款成功,同步财务也成功,给快递员发推送
	 */
	@Override
	public boolean sendPushCirmTakeSuccess(Long memberId, String memberPhone) {
		String date=DateUtil.getNowTime();
		logger.debug("付款成功给当前收派员推送消息：收派员ID "+memberId+", 收派员手机号: "+memberPhone+"调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/** 推送内容**/
		String msg = sendNoticeCirmTakeMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_NOTICE_CIRMTAKE_MESSAGE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		return true;
	}
	/**
	 * 付款成功,微信账户有钱,财务同步失败给快递员发推送
	 */
	@Override
	public boolean sendSynchFinancialMessage(Long memberId, String memberPhone) {
		String date=DateUtil.getNowTime();
		logger.debug("付款成功给当前收派员推送消息：收派员ID "+memberId+", 收派员手机号: "+memberPhone+"调用日期  "+
				date);
		/** 收派员ID为空返回推送失败 **/
		if (PubMethod.isEmpty(memberId)) {
			return false;
		}
		/** 调用是否登录接口**/
		String result=ifOnline(Constant.PUSH_TYPE_ORDERS,memberId);
		/** 推送内容**/
		String msg = sendSynchFinancialFailMsg;
		/** 登录状态下发推送 **/
		if (Constant.YESPUSH_NOSMS_APPLE.equals(result)||Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)) {
			this.pushExt(result,Constant.PUSH_TYPE_ORDERS, memberId.toString(),Constant.PUSH_SYNCH_FAIL_MESSAGE, 
					Constant.PUSH_TITLE,msg, "",Constant.MSGTYPE_MESSAGE);
			return true;
		}
		return true;
	}
	
	
	
}
















