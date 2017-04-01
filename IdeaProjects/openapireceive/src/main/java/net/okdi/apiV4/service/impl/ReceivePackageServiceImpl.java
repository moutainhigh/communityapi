package net.okdi.apiV4.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.serializer.LongArraySerializer;

import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.api.service.impl.TakeTaskServiceImpl;
import net.okdi.apiV4.dao.BasEmployeeAuditMapperV4;
import net.okdi.apiV4.dao.MemberInfoMapperV4;
import net.okdi.apiV4.entity.AccSrecpayvouchers;
import net.okdi.apiV4.entity.BasCompInfo;
import net.okdi.apiV4.entity.BasCustomerInfo;
import net.okdi.apiV4.entity.ContentHis;
import net.okdi.apiV4.entity.ExpCustomerInfo;
import net.okdi.apiV4.entity.MemberInfo;
import net.okdi.apiV4.entity.MemberInfoVO;
import net.okdi.apiV4.entity.NewMemberInfo;
import net.okdi.apiV4.entity.PackageReport;
import net.okdi.apiV4.entity.ParDayCount;
import net.okdi.apiV4.entity.ParMonthCount;
import net.okdi.apiV4.entity.ParParcelMark;
import net.okdi.apiV4.entity.ParParceladdress;
import net.okdi.apiV4.entity.ParParcelconnection;
import net.okdi.apiV4.entity.ParParcelinfo;
import net.okdi.apiV4.entity.ParTaskDisposalRecord;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.entity.ParTaskProcess;
import net.okdi.apiV4.entity.ParWeekCount;
import net.okdi.apiV4.entity.ParcelSignInfo;
import net.okdi.apiV4.entity.ServiceTypeInfo;
import net.okdi.apiV4.entity.TaskRemind;
import net.okdi.apiV4.entity.WechatBound;
import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.apiV4.service.TaskRemindService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.common.page.Page;
import net.okdi.core.common.page.PageUtil;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;
import com.mongodb.WriteResult;

@Service
public class ReceivePackageServiceImpl extends BaseServiceImpl implements
		ReceivePackageService {

	private static Logger logger = Logger
			.getLogger(ReceivePackageServiceImpl.class);
	@Autowired
	private MemberInfoMapperV4 memberInfoMapperV4;

	@Autowired
	TaskRemindService taskRemindService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private SendNoticeService sendNoticeService;
	@Autowired
	private TakeTaskService takeTaskService;

	@Value("${courier.down.http}")
	private String courierDownHttp;

	// 叫快递调用取派件新项目
	@Value("${opencall}")
	private String opencall;
	@Autowired
	private NoticeHttpClient noticeHttpClient;

	@Autowired
	ExpCustomerInfoService customerInfoService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private BasEmployeeAuditMapperV4 basEmployeeAuditMapper;

	// 用户头像
	@Value("${headImgPath}")
	private String headImgPath;

	@Autowired
	private AssignPackageService assignPackage;

	@Autowired
	private ConstPool constPool;
	
	// 查询取件订单(收派员)
	@Override
	public Page queryTakePackList(Long memberId, Integer currentPage,
			Integer pageSize) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		/**
		 * query.addCriteria(Criteria.where("taskStatus").is((byte)1));//已分配
		 * query.addCriteria(Criteria.where("taskType").is((byte)0));//取件
		 * //根据memberId查询属于哪个compId Long compId =
		 * memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
		 * query.addCriteria(Criteria.where("contactCompId").is(compId));//公司id
		 * long型
		 * query.addCriteria(Criteria.where("taskIsEnd").is((byte)0));//未结束
		 */
		Query query = new Query();
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));// memberId
		query.addCriteria(Criteria.where("taskIsEnd").is((byte) 0));// 包裹未结束
		// query.addCriteria(Criteria.where("taskId").is(taskId));
		query.addCriteria(Criteria.where("taskType").is((byte) 0));// 取件
		query.addCriteria(Criteria.where("taskStatus").is((byte) 1));// 已分配
		// 根据创建时间进行排序
		query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
		Long count = mongoTemplate.count(query, ParTaskInfo.class);

		query.skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		logger.info("进入站点取件订单列表，query=" + query);
		List<ParTaskInfo> listParTask = mongoTemplate.find(query,
				ParTaskInfo.class);
		for (ParTaskInfo parTaskInfo : listParTask) {
			map = new HashMap<String, Object>();
			String contactMobile = parTaskInfo.getContactMobile();// 联系人手机
			String contactAddress = parTaskInfo.getContactAddress();// 联系人地址
			Date createTime = parTaskInfo.getCreateTime();// 时间
			Long uid = parTaskInfo.getUid();
			map.put("taskId", uid);// 任务id
			map.put("contactMobile", contactMobile);// 联系人手机号
			map.put("contactAddress", contactAddress);// 联系人地址
			map.put("createTime", createTime);// 创建时间
			list.add(map);
			map = null;
		}
		Page page = PageUtil.buildPage(currentPage, pageSize);
		page.setItems(list);
		page.setTotal(count.intValue());
		query = null;
		return page;
	}

	// 查询取件订单(收派员)----new
	@Override
	public Page queryNewTakePackList(Long memberId, Integer currentPage,
			Integer pageSize) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		/**
		 * query.addCriteria(Criteria.where("taskStatus").is((byte)1));//已分配
		 * query.addCriteria(Criteria.where("taskType").is((byte)0));//取件
		 * //根据memberId查询属于哪个compId Long compId =
		 * memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
		 * query.addCriteria(Criteria.where("contactCompId").is(compId));//公司id
		 * long型
		 * query.addCriteria(Criteria.where("taskIsEnd").is((byte)0));//未结束
		 */
		Query query = new Query();
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));// memberId
		query.addCriteria(Criteria.where("taskIsEnd").is((byte) 0));// 包裹未结束
		// query.addCriteria(Criteria.where("taskId").is(taskId));
		query.addCriteria(Criteria.where("taskType").is((byte) 0));// 取件
		query.addCriteria(Criteria.where("taskStatus").is((byte) 1));// 已分配
		// 根据创建时间进行排序
		query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
		Long count = mongoTemplate.count(query, ParTaskInfo.class);

		query.skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		logger.info("进入站点取件订单列表，query=" + query);
		List<ParTaskInfo> listParTask = mongoTemplate.find(query,
				ParTaskInfo.class);
		for (ParTaskInfo parTaskInfo : listParTask) {
			map = new HashMap<String, Object>();
			String contactMobile = parTaskInfo.getContactMobile();// 联系人手机
			String contactAddress = parTaskInfo.getContactAddress();// 联系人地址
			Short tagFalg = parTaskInfo.getTagFalg();// 是否标记 0:未标记, 1:已标记
			String comment = parTaskInfo.getComment();// 标记内容
			Date createTime = parTaskInfo.getCreateTime();// 时间
			Long uid = parTaskInfo.getUid();
			map.put("taskId", uid);// 任务id
			map.put("contactMobile", contactMobile);// 联系人手机号
			map.put("contactAddress", contactAddress);// 联系人地址
			map.put("createTime", createTime);// 创建时间
			map.put("tagFalg", tagFalg);// 是否标记 0 或者 空都是未标记, 1:才是已标记
			map.put("packNum", parTaskInfo.getParEstimateCount());// 是否标记 0 或者
																	// 空都是未标记,
																	// 1:才是已标记
			// map.put("comment", comment);// 标记内容, 如果多个内容,中间用 | 隔开
			list.add(map);
			map = null;
		}
		Page page = PageUtil.buildPage(currentPage, pageSize);
		page.setItems(list);
		page.setTotal(count.intValue());
		query = null;
		return page;
	}

	// 代收点交寄订单查询
	@Override
	public Page queryTakeConsigOrderList(String memberId, String netId,
			Integer currentPage, Integer pageSize) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// Long compId = memberInfoMapperV4.findCompIdByMemberId(Longi
		// .valueOf(memberId));
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		Long compId = Long.valueOf(memberInfoVO.getCompId());
		Query query = new Query();
		query.addCriteria(Criteria.where("contactCompId").is(compId));// 公司id
																		// long型
		query.addCriteria(Criteria.where("taskStatus").is((byte) 1));// 已分配
		// query.addCriteria(Criteria.where("customerId").is(memberId));
		query.addCriteria(Criteria.where("taskType").is((byte) 0));// 取件
		// 根据memberId查询属于哪个compId
		query.addCriteria(Criteria.where("taskIsEnd").is((byte) 0));// 未结束

		// 根据创建时间进行排序
		query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
		Long count = mongoTemplate.count(query, ParTaskInfo.class);

		query.skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		logger.info("进入代收点交寄订单查询，query=" + query);
		// 查询任务表
		List<ParTaskInfo> listParTaskInfo = mongoTemplate.find(query,
				ParTaskInfo.class);
		List<Map<String, Object>> listMap2 = null;
		Map<String, Object> map2 = null;
		for (ParTaskInfo parTaskInfo : listParTaskInfo) {
			Long uid2 = parTaskInfo.getUid();
			map = new HashMap<String, Object>();
			map.put("taskId", uid2);// 任务id
			Long actorMemberId = parTaskInfo.getActorMemberId();
			listMap2 = new ArrayList<Map<String, Object>>();
			String headImg = headImgPath + "" + actorMemberId + ".jpg";// 发件人头像
			map.put("headImg", headImg);
			// String findMemberName =
			// memberInfoMapperV4.findMemberNameByMemberId(actorMemberId);
			memberInfoVO = this.getValByMemberId(actorMemberId + "");
			String findMemberName = memberInfoVO.getMemberName();
			// String contactName = parTaskInfo.getContactName();//执行人姓名
			map.put("contactName", findMemberName);
			String contactMobile = parTaskInfo.getActorPhone();// 执行人手机号
			map.put("contactMobile", contactMobile);
			// Long coopNetid = parTaskInfo.getCoopNetId();//执行人公司compId
			// Long findNetId =
			// memberInfoMapperV4.findNetIdByComp(contactCompId);//执行人netId
			// String netName =
			// memberInfoMapperV4.findNetNameByNetId(coopNetid);//执行人netName
			String netName = memberInfoVO.getNetName();
			// String netName =
			// memberInfoMapperV4.findNetNameByNetId(Long.valueOf(netId));//发件人网络
			map.put("netName", netName);
			query = null;
			query = new Query();
			Long taskId = parTaskInfo.getUid();
			query.addCriteria(Criteria.where("takeTaskId").is(taskId));// 取件任务的taskid
			query.addCriteria(Criteria.where("parcelEndMark").is("0"));// 包裹未结束
			Byte estimateCount = parTaskInfo.getParEstimateCount();// 包裹数量
			map.put("estimateCount", estimateCount);
			// 查询包裹表
			List<ParParcelinfo> listParcelInfo = mongoTemplate.find(query,
					ParParcelinfo.class);
			for (ParParcelinfo parcelinfo : listParcelInfo) {
				map2 = new HashMap<String, Object>();
				query = null;
				query = new Query();
				// 一个包裹表对应一个地址表
				Long uid = parcelinfo.getUid();
				Date createTime = parcelinfo.getCreateTime();
				map2.put("createTime", createTime);// 包裹的创建时间
				map2.put("parcelId", uid);// 包裹id
				query.addCriteria(Criteria.where("uid").is(uid));
				ParParceladdress listParcelAddress = mongoTemplate.findOne(
						query, ParParceladdress.class);
				String sendAddress = listParcelAddress.getSendAddress();// 发件人地址
				map2.put("sendAddress", sendAddress);
				String sendMobile = listParcelAddress.getSendMobile();// 发件人电话
				map2.put("sendMobile", sendMobile);
				String addresseeAddress = listParcelAddress
						.getAddresseeAddress();// 收件人地址
				map2.put("addresseeAddress", addresseeAddress);
				String addresseeMobile = listParcelAddress.getAddresseeMobile();// 收件人电话
				map2.put("addresseeMobile", addresseeMobile);
				listMap2.add(map2);
				map2 = null;
			}
			map.put("recordInfo", listMap2);// 放入包裹地址的信息
			listMap.add(map);
			map = null;
			listMap2 = null;
		}
		Page page = PageUtil.buildPage(currentPage, pageSize);
		page.setItems(listMap);
		page.setTotal(count.intValue());
		return page;
	}

	// 待取任务详情
	@Override
	public Map<String, Object> queryTakeTaskDetailByTaskId(Long memberId,
			Long taskId) {

		Map<String, Object> map = new HashMap<String, Object>();
		// Map<String,Object> map =
		// parTaskInfoMapperV4.queryTakeTaskDetailByTaskId(taskId);
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(taskId));// 任务id
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));// 执行人的memberId
		query.addCriteria(Criteria.where("taskType").is((byte) 0));// 任务类型 0:取件
		query.addCriteria(Criteria.where("taskStatus").is((byte) 1));// 已分配
		query.addCriteria(Criteria.where("taskIsEnd").is((byte) 0));// 0未结束
		ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
				ParTaskInfo.class);
		if (!PubMethod.isEmpty(parTaskInfo)) {
			String contactName = parTaskInfo.getContactName();// 发件姓名
			String contactMobile = parTaskInfo.getContactMobile();// 发件手机
			String contactAddress = parTaskInfo.getContactAddress();// 发件地址
			String appointDesc = parTaskInfo.getAppointDesc();// 描述
			map.put("contactName", contactName);// 发件姓名
			map.put("contactMobile", contactMobile);// 发件手机
			map.put("contactAddress", contactAddress);// 发件地址
			map.put("appointDesc", appointDesc);// 发件备注
			map.put("taskId", parTaskInfo.getUid());// 任务id
			map.put("taskSource", parTaskInfo.getTaskSource());// 来源
			map.put("taskStatus", parTaskInfo.getTaskStatus());// 任务状态
			map.put("taskFlag", parTaskInfo.getTaskFlag());//
			map.put("thirdId", parTaskInfo.getThirdId());// 第三方id 微信id
			map.put("parEstimateCount", parTaskInfo.getParEstimateCount());// 包裹数量
			// 新加
			map.put("tagContent", parTaskInfo.getComment());// 标记内容,
															// 如果内容有多个,则中间用 | 隔开
															// 新加
															// 2017年02月05日17:36:02
															// by zj ,
															// 对原始需求无影响,这是新加的字段

			return map;
		} else {
			map.put("contactName", "");
			map.put("contactMobile", "");
			map.put("contactAddress", "");
			map.put("appointDesc", "");
			map.put("taskId", taskId);
			map.put("taskSource", "");
			map.put("taskStatus", "");
			map.put("taskFlag", "");
			map.put("thirdId", "");
			map.put("parEstimateCount", "");
			map.put("tagContent", "");
			return map;
		}

	}

	// 确认取件(收派员取件,包括收现金和 二维码扫描) sign:21 微信支付确认取件(代收点), 25:不是代收代呢微信支付,是其他
	@Override
	public String takeSendPackage(String taskId, String memberId,
			Integer packageNum, Double freightMoney, String sign) {
		logger.info("=============问题takeSendPackage====taskId:" + taskId
				+ ",memberId:" + memberId + ",packageNum:" + packageNum
				+ ",freightMoney:" + freightMoney + ",sign:" + sign);
		try {
			/**
			 * 从任务过来的 1.任务完成 2.任务记录完成 3.包裹下面添加取件时间, 包裹状态改为已取件 4.保存收据
			 */
			// 1.查询任务中parStatus字段是否为空,如果为空代表是收现金,流程正常走
			// payStatus付款状态：初始化为0-未确定，10 现金支付 20 微信支付完成并且财务系统同步完成 21
			// 微信支付完成并且财务系统同步失败 22 微信支付失败
			// 2.如果parStatus字段不是空,已经点击了微信支付 20 和 21 状态流程继续往下走, 如果是 22
			// 状态给return回去,如果是21返回给手机端一个状态,同时流程继续向下走
			Query query = new Query();
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
			query.addCriteria(Criteria.where("actorMemberId").is(
					Long.valueOf(memberId)));
			ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
					ParTaskInfo.class);
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("cpTakeTaskId").is(
					Long.valueOf(Long.valueOf(taskId))));
			query.addCriteria(Criteria.where("cpReceiptStatus").is((short) 0));
			ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
					ParParcelinfo.class);

			Short payStatus = parTaskInfo.getPayStatus();// 付款状态

			logger.info("takeSendPackage============== memberPhone  开始");
			MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
			String memberPhone = memberInfoVO.getMemberPhone();
			logger.info("takeSendPackage============== memberPhone 结束");
			if (0 != payStatus) {// 如果付款状态不是0,说明已经点过微信支付
				logger.info("==================问题========payStatus："
						+ payStatus);
				// 1.判断状态是否成功
				if (22 == payStatus) {// 微信支付失败
					logger.info("======================================22============================================takeSendPackage");
					return "22";// 需要弹出来付款二维码
				} else if (21 == payStatus) {// 流程继续向下走,但是需给手机端返回一个文案
					logger.info("======================================21============================================takeSendPackage");
					// 不弹付款二维码,但是给提示,财务系统同步失败,请联系客服
					Short flag = 21;// 21 微信支付完成并且财务系统同步失败
					if (!PubMethod.isEmpty(parParcelinfo)) {
						// 说明是代收点微信支付的
						sign = "21";
					} else {
						// 收派员微信支付的
						sign = "25";
					}
					String takeTwo = this.confirmTakeTwo(taskId, memberId,
							freightMoney, packageNum, flag, sign);
					// 发推送(给当前快递员)
					// String memberPhone =
					// memberInfoMapperV4.findMemberPhoneByMemberId(Long.valueOf(memberId));

					sendNoticeService.sendSynchFinancialMessage(
							Long.valueOf(memberId), memberPhone);
					if ("001".equals(takeTwo)) {
						return "21";// 不弹付款二维码,但是给提示,财务系统同步失败,请联系客服
					} else if ("002".equals(takeTwo)) {
						return "002";// 异常
					}
				} else if (20 == payStatus) {
					Short flag = 20;// 20 微信支付完成并且财务系统同步完成
					// sign = "25";
					if (!PubMethod.isEmpty(parParcelinfo)) {
						// 说明是代收点微信支付的
						sign = "21";
					} else {
						// 收派员微信支付的
						sign = "25";
					}
					logger.info("============问题=====sign：" + sign);
					String takeTwo = this.confirmTakeTwo(taskId, memberId,
							freightMoney, packageNum, flag, sign);
					if ("001".equals(takeTwo)) {
						// 发推送(给当前快递员)
						logger.info("============问题2=====takeTwo: " + takeTwo);
						// String memberPhone =
						// memberInfoMapperV4.findMemberPhoneByMemberId(Long.valueOf(memberId));
						sendNoticeService.sendPushCirmTakeSuccess(
								Long.valueOf(memberId), memberPhone);
					}
				} else {// 23 微信支付失败同步财务失败
					logger.info("微信同步过来的数据,本来不应该出现这个情况========.java=======付款状态: "
							+ payStatus + "===memberInfoVO: " + memberInfoVO);
				}
			} else {
				// 收现金
				Short flag = 10;
				sign = "10";
				logger.info("============问题3=====flag" + flag);
				this.confirmTakeTwo(taskId, memberId, freightMoney, packageNum,
						flag, sign);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "002"; // 002 代表异常
		}
		return "001";// 001 代表成功
	}

	// 确认取件2
	public String confirmTakeTwo(String taskId, String memberId,
			Double freightMoney, int packageNum, Short flag, String sign) {
		try {
			logger.info("========================= 微信回调 confirmTakeTwo 确认取件接口 开始 ......");
			logger.info("===taskId: " + taskId + " ==memberId: " + memberId
					+ "===sign: " + sign + "== ......");
			Query query = new Query();
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
			query.addCriteria(Criteria.where("actorMemberId").is(
					Long.valueOf(memberId)));
			ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
					ParTaskInfo.class);
			String mobilePhone = parTaskInfo.getMobilePhone();//
			query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
			Update update = new Update();
			update.set("taskStatus", (byte) 2);// 任务已完成
			update.set("taskIsEnd", (byte) 1);// 任务结束1:结束
			update.set("taskEndTime", new Date());// 任务结束时间
			if (10 == flag) {// 10:现金支付
				update.set("payStatus", flag);// 收现金, 10 现金支付
			}
			logger.info("=======================开始更新任务完成  ......");
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));// 任务taskId
			query.addCriteria(Criteria.where("disposalType").is((byte) 1));// 处理类型1:已分配
			update = null;
			update = new Update();
			update.set("disposalType", (byte) 2);// 已完成
			update.set("modifiedTime", new Date());// 最后修改时间
			logger.info("========================= 开始更新任务记录完成 ......");
			mongoTemplate.updateFirst(query, update,
					ParTaskDisposalRecord.class);
			query = null;
			query = new Query();

			if ("21".equals(sign)) {// 微信支付确认取件 21, 用cpTakeTaskId查询
				query.addCriteria(Criteria.where("cpTakeTaskId").is(
						Long.valueOf(taskId)));
				logger.info("======代收点微信支付 根据 cpTakeTaskId 字段查询出包裹  ......");
			} else {// 不是微信支付的
				query.addCriteria(Criteria.where("takeTaskId").is(
						Long.valueOf(taskId)));
				logger.info("======不是微信支付 正常流程走的查询包裹   ......");
			}
			List<ParParcelinfo> listParcel = mongoTemplate.find(query,
					ParParcelinfo.class);
			Long receiptId = IdWorker.getIdWorker().nextId();// 收据id
			for (ParParcelinfo parParcelinfo : listParcel) {
				Long uid = parParcelinfo.getUid();
				String mobilePhone1 = parParcelinfo.getMobilePhone();
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("uid").is(uid));
				query.addCriteria(Criteria.where("mobilePhone")
						.is(mobilePhone1));
				update = new Update();
				/**
				 * query.addCriteria(Criteria.where("actualTakeMember").is(Long.
				 * valueOf(memberId)));//取件人id
				 * query.addCriteria(Criteria.where("parcelStatus"
				 * ).is((short)1));//已取件
				 * query.addCriteria(Criteria.where("parcelEndMark"
				 * ).is("0"));//未结束
				 */
				update.set("pickupTime", new Date());// 取件时间
				update.set("parcelStatus", (short) 1);// 1:已取件
				update.set("receiptId", receiptId);// 1:收据id
				update.set("cpReceiptStatus", (short) 1);// 1:付款(代收点已付和收派员已付)
				logger.info("====== 开始更新包裹中的数据 ========  ......");
				mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
				// 确认取件插入微信绑定号信息

				try {
					String result = this.saveWechatBoundInfo(parParcelinfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.info("确认取件往微信WechatBound表中插入信息失败..............");
				}

			}
			AccSrecpayvouchers accSrecpayvouchers = new AccSrecpayvouchers();
			accSrecpayvouchers.setUid(receiptId);
			// String memberName =
			// memberInfoMapperV4.findMemberNameByMemberId(Long.valueOf(memberId));//收款人姓名
			// Long compId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));//收款人姓名
			MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
			String memberName = memberInfoVO.getMemberName();
			Long compId = Long.valueOf(memberInfoVO.getCompId());
			this.setAccSrecpayvouchers(accSrecpayvouchers, null, freightMoney,
					packageNum, Long.valueOf(memberId), memberName, compId);
			mongoTemplate.insert(accSrecpayvouchers);// 插入收据表
			return "001";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "002";
		}
	}

	private String saveWechatBoundInfo(ParParcelinfo parParcelinfo) {
		// 同时插入数据微信绑定手机号信息 WechatBound表 2016年7月16日15:31:21--by zhaohu---黄总需求
		// parParcelinfo.get
		/*
		 * WechatBound wechatBound =
		 * this.mongoTemplate.findOne(Query.query(Criteria
		 * .where("mobile").is(addresseeMobile)), WechatBound.class);
		 * if(PubMethod.isEmpty(wechatBound)){ WechatBound bond = new
		 * WechatBound(); bond.setMobile(addresseeMobile);
		 * bond.setAddressName(address); this.mongoTemplate.insert(bond); }
		 */
		return null;
	}

	// 收派员或者代收点自取件
	// String json =
	// "{"data":[{"receiveProvince":"beijing","receivePhone":"15810885211","receiveName":"zhangsan","expWaybillNum":"123"},{"receiveProvince":"shanghai","receivePhone":"15810885212","receiveName":"lisi","expWaybillNum":"456"},{"receiveProvince":"guangzhou","receivePhone":"15810885213","receiveName":"wanger","expWaybillNum":"789"},{"receiveProvince":"shenzhen","receivePhone":"15810885214","receiveName":"mazi","expWaybillNum":"110"}]}";
	@Override
	public Map<String, Object> takeSendPackageByMember(String memberId,
			String sendName, String sendPhone, String sendAddress,
			String netId, String packageNum, Double freightMoney,
			String packageJson, String flag) {
		logger.info("紧紧传过来的memberId：" + memberId + ",sendName:" + sendName
				+ ",sendPhone:" + sendPhone + ",sendAddress:" + sendAddress
				+ ",netId:" + netId + ",packageNum:" + packageNum
				+ ",freightMoney:" + freightMoney + ",packageJson:"
				+ packageJson + ",flag:" + flag);
		logger.info("=======走的取件新方法    takeSendPackageByMember   server＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		String roleId = memberInfoVO.getRoleId();
		if ("2".equals(roleId) || "3".equals(roleId)) {// 代收点的现金支付,微信支付 1 站长 0
														// 收派员 -1 后勤 2 代收站店长 3
														// 代收站店员'
			logger.info("==1============代收点自添加取件 takeSendPackageByMember ====================== 代收点的只能现金支付");
			if (flag.equals("0")) {// 收现金
				flag = "0";
			} else if (flag.equals("1")) {// 微信支付
				String sign = "21";
				Map<String, Object> map = this.takeSendPackageByMemberAdd(
						memberId, sendName, sendPhone, sendAddress, netId,
						packageNum, freightMoney, packageJson, sign);
				logger.info("==2============收派员自添加取件 takeSendPackageByMember 返回的map数据: ====== 微信支付"
						+ map);
				return map;// 返回taskId 和 money 收运费
			}
		}
		// 微信支付
		if ("1".equals(flag)) {// 收派员微信支付
			logger.info("===3===========收派员自添加取件 takeSendPackageByMember ====================== 微信支付");
			String sign = "11";
			netId = memberInfoVO.getNetId();// 收派员就自己的网络
			Map<String, Object> map = this.takeSendPackageByMemberAdd(memberId,
					sendName, sendPhone, sendAddress, netId, packageNum,
					freightMoney, packageJson, sign);
			logger.info("===4===========收派员自添加取件 takeSendPackageByMember 返回的map数据: ====== 微信支付"
					+ map);
			return map;// 返回taskId 和 money 收运费
		}
		logger.info("=====5==========收派员自添加取件 takeSendPackageByMember 现金支付的 =========== ");
		// 现金支付
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			// Long netId = memberInfoMapperV4.findNetIdByCompId(compId);
			// 1.生成一个已完成的任务
			// 2.保存包裹
			// 3.包裹地址
			// 4.保存信息监控表
			// 5.保存收据
			JSONObject jsonObject = JSONObject.parseObject(packageJson);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int size = jsonArray.size();
			AccSrecpayvouchers accSrecpayvouchers = new AccSrecpayvouchers();// 收付凭证
			// 一个任务的包裹对应一个收据id
			Long receiptId = IdWorker.getIdWorker().nextId();
			accSrecpayvouchers.setUid(receiptId);
			// String memberName =
			// memberInfoMapperV4.findMemberNameByMemberId(Long.valueOf(memberId));
			// Long compId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
			String memberName = memberInfoVO.getMemberName();
			String compId = memberInfoVO.getCompId();
			// 把寄件人的信息存入到客户管理里面
			logger.info("收现金 ...添加包裹时,把寄件人的信息添加到包裹里面.......customerCompId: "
					+ compId + "..寄件人姓名: " + sendName + "..寄件人姓名: " + sendPhone
					+ ".寄件人memberId: " + memberId + "======================== ");
			// 插入客户信息
			/*
			 * Map map1=new HashMap(); map1.put("compId",compId);
			 * map1.put("customerName",sendName);
			 * map1.put("customerPhone",sendPhone);
			 * map1.put("memberId",memberId); String url =
			 * constPool.getOpencustomerUrl() +
			 * "customerNewInfoTwo/insertCustomerV4"; String result = Post(url,
			 * map1);
			 */

			// ParParcelinfo parParcelinfo = null;//包裹表
			// ParParceladdress parParceladdress = null;
			// ParParcelconnection parParcelconnection = null;
			// Long compId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
			// Long senderCompId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
			// Long compId = Long.valueOf(memberInfoVO.getCompId());
			String netId2 = memberInfoVO.getNetId();
			String netId1 = netId2;
			if (PubMethod.isEmpty(netId2)) {
				logger.info("takeSendPackageByMember==== 空 ==============netId1: "
						+ netId2);
				netId1 = "";
			}
			// Long netId1 = memberInfoMapperV4.findNetIdByCompId(compId);
			for (int i = 0; i < size; i++) {
				// map = new HashMap<String,Object>();
				// Long id = IdWorker.getIdWorker().nextId();//主键id
				JSONObject object = jsonArray.getJSONObject(i);
				String receiveProvince = object.getString("receiveProvince");// 收件人城市
				String receivePhone = object.getString("receivePhone");// 收件人手机
				String receiveName = object.getString("receiveName");// 收件人姓名
				String expWaybillNum = object.getString("expWaybillNum");// 包裹运单号
				String weightTransit = "0";
				if (!PubMethod.isEmpty(object.getString("weightFortransit"))) {
					weightTransit = object.getString("weightFortransit");
				}
				// 包裹重量
				// parParcelinfo = null;
				ParParcelinfo parParcelinfo = new ParParcelinfo();// 包裹表
				long parcelId = IdWorker.getIdWorker().nextId();// 包裹主键id
				logger.info("收现金包裹中的主键parcelId====: " + parcelId
						+ " Long 转换之后的id====:" + Long.valueOf(parcelId));
				parParcelinfo.setUid(parcelId);// 1.包裹id 生成的19位id id
												// 与地址表中的主键id相同
				parParcelinfo.setMobilePhone(sendPhone);// 片键 ------------------
														// 用的发件人的手机号
				parParcelinfo.setExpWaybillNum(expWaybillNum);// 5.运单号
																// ExpWaybillNum
				// parParcelinfo.setTakeTaskId(Long.valueOf(taskId));//2.取件任务id
				// TakeTaskId
				// parParcelinfo.setSenderUserId(Long.valueOf(customerId));//4.发货方客户ID
				// SenderUserId
				// 包裹属于哪个公司只有有快递员接单时才有
				if ("0".equals(roleId) || "1".equals(roleId)
						|| "-1".equals(roleId)) {// 收派员
					parParcelinfo.setCompId(Long.valueOf(compId));// 6.公司id
																	// CompId
					if (!PubMethod.isEmpty(netId1)) {
						parParcelinfo.setNetId(Long.valueOf(netId1));// 7.网络id
																		// NetId
					} else {
						parParcelinfo.setNetId(null);// 7.网络id NetId
					}
					parParcelinfo.setTakeTaskId(11111111l);// 主 代收点微信支付字段,此处没用到
															// 给空
					parParcelinfo.setCpTakeTaskId(22222222l);// 副
				} else { // 代收点
					if (!PubMethod.isEmpty(netId)) {
						// netId = "0";
						parParcelinfo.setNetId(Long.valueOf(netId));// 7.网络id
																	// NetId

					} else {
						parParcelinfo.setNetId(null);// 7.网络id NetId
					}
					parParcelinfo.setCpTakeTaskId(11111111l);// //主代收点微信支付字段,此处没用到
																// 给空
					parParcelinfo.setTakeTaskId(22222222l);// 副
				}

				parParcelinfo.setCpReceiptStatus((short) 1);// 1:已付, 代收点收现金

				// 8.包裹重量 ChareWeightForsender
				parParcelinfo.setFreight(null);// 9.包裹应收运费 Freight
				parParcelinfo.setGoodsPaymentMethod((short) 0);// 10.支付方式
																// GoodsPaymentMethod
																// 0:不代收, 1:代收
				parParcelinfo.setCodAmount(BigDecimal.valueOf((double) 0));// 11.代收货款金额
																			// CodAmount
				// 12.保价金额 InsureAmount
				// 13.保价费 PricePremium
				// 14.包装费 PackingCharges
				parParcelinfo.setFreightPaymentMethod((short) 0);// 15.运费支付方式
																	// FreightPaymentMethod
																	// 0
				parParcelinfo
						.setActualCodAmount(BigDecimal.valueOf((double) 0));// )16.代收货款实际收到的货款金额
																			// ActualCodAmount
																			// 0

				// 包裹计费重量(采购)
				if (PubMethod.isEmpty(weightTransit)) {
					weightTransit = "0";
				}
				logger.info("新添加一个包裹计费重量================weightTransit: "
						+ weightTransit);
				parParcelinfo.setChareWeightFortransit(BigDecimal
						.valueOf(Double.valueOf(weightTransit)));
				// 17.产品描述 GoodsDesc null
				// 18.包裹备注 ParcelRemark null
				// 19.服务产品ID ServiceId null
				parParcelinfo.setSignMember(null);// 20.签收人 SignMember 收件人
				parParcelinfo.setCreateUserId(Long.valueOf(memberId));// 21.创建人id
																		// CreateUserId
																		// memberId
				parParcelinfo.setParcelEndMark("0");// 22.包裹结束标志 ParcelEndMark
													// 0:未结束
				parParcelinfo.setActualTakeMember(Long.valueOf(memberId));// 23.取件人id
																			// ActualTakeMember
																			// memberId
				parParcelinfo.setPickupTime(new Date());// 24.取件时间 PickupTime
														// new Date();
				// 25.派件人id ActualSendMember null
				parParcelinfo.setReceiptId(receiptId);// 26.收据id ReceiptId
				// 27.签收结果 0：未签收/拒签 1：签收 SignResult 0 null
				parParcelinfo.setTackingStatus((short) 0);// 28.包裹当前状态 0:在途,未签收
															// 1:已签收
															// TackingStatus 0 0
				if ("0".equals(roleId) || "1".equals(roleId)
						|| "-1".equals(roleId)) {// 收派员
					parParcelinfo.setParcelStatus((short) 1);// 29.包裹状态
																// 0：待取件;1：已取件;10：待派件;11：已派件;12:派件异常
																// ParcelStatus
																// ; 1：已取件
				} else {// 代收点存发货方compId
					parParcelinfo.setSenderCompId(Long.valueOf(compId));// 发货方存代收点的compId
					parParcelinfo.setSenderUserId(Long.valueOf(memberId));// 发货方存代收点的memberId
				}
				// 30.disposal_desc null
				// 31.如果异常原因不为空则添加异常时间 ExceptionTime null
				Date date = new Date();
				parParcelinfo.setCreateTime(date);// 32.设置包裹创建时间 CreateTime new
													// Date();
				mongoTemplate.insert(parParcelinfo);// 插入包裹

				// =============================================================================================
				// parParceladdress = null;
				ParParceladdress parParceladdress = new ParParceladdress();// 包裹地址表
				parParceladdress.setUid(parcelId); // 1.包裹地址id 生成的19位id id
													// 与包裹表中的主键id相同
				// parParceladdress.setSendCustomerId(Long.valueOf(customerId));//2.发件客户ID
				// SendCustomerId
				parParceladdress.setAddresseeAddressId(null);// 3.收件客户ID
																// AddresseeCustomerId
				parParceladdress.setSendCasUserId(null);// 4.发件人CASID
														// SendCasUserId
				parParceladdress.setAddresseeCasUserId(null);// 5.收件人CASID
																// AddresseeCasUserId
				parParceladdress.setAddresseeName(receiveName);// 6.收件人姓名
																// AddresseeName
				parParceladdress.setAddresseeMobile(receivePhone);// 7.收件人手机号码
																	// AddresseeMobile
																	// ---- 片键
																	// -- 收件人手机号
				parParceladdress.setAddresseeAddressId(null);// 8.收件人乡镇id
																// AddresseeAddressId
				parParceladdress.setAddresseeAddress(receiveProvince);// 9.收件人详细地址
																		// AddresseeAddress
				parParceladdress.setSendName(sendName);// 10.发件人姓名 SendName
				parParceladdress.setSendMobile(sendPhone);// 11.发件人手机 SendMobile
				parParceladdress.setSendAddressId(null);// 12.发件人乡镇id
														// SendAddressId
				parParceladdress.setSendAddress(sendAddress);// 13.发件人详细地址
																// SendAddress
				parParceladdress.setSendLongitude(null);// 14.发件人地址经度
														// SendLongitude
				parParceladdress.setSendLatitude(null);// 15.发件人地址纬度
														// SendLatitude
				parParceladdress.setAddresseeLongitude(null);// 16.收件人地址经度
																// AddresseeLongitude
				parParceladdress.setAddresseeLatitude(null);// 17.收件人地址纬度
															// AddresseeLatitude
				parParceladdress.setCreateTime(new Date());// 地址创建时间

				mongoTemplate.insert(parParceladdress);// 插入地址表
				// parParcelconnection = null;
				ParParcelconnection parParcelconnection = new ParParcelconnection();// 包裹收派过程信息监控表
				parParcelconnection.setUid(IdWorker.getIdWorker().nextId());
				parParcelconnection.setMobilePhone(sendPhone);// 片键---包裹监控包---也用发件人手机号
																// -----------------
				if ("0".equals(roleId) || "1".equals(roleId)
						|| "-1".equals(roleId)) {// 收派员
					parParcelconnection.setCompId(Long.valueOf(compId));// 公司compId
					parParcelconnection.setNetId(Long.valueOf(netId1));// 网络netId

				} else { // 代收点
					if (!PubMethod.isEmpty(netId)) {
						// netId = "0";
						parParcelconnection.setNetId(Long.valueOf(netId));// 网络netId
					} else {
						parParcelinfo.setNetId(null);// 7.网络id NetId
					}
				}
				parParcelconnection.setCosignFlag((short) 1);// 取件
				parParcelconnection.setCreateTime(date);// 创建时间
				parParcelconnection.setExpMemberId(Long.valueOf(memberId));// memberId
				parParcelconnection.setExpMemberSuccessFlag(null);// 取派件成功标志
				parParcelconnection.setParId(parcelId);// 包裹id
				// parParcelconnection.setTaskId(taskId);//任务id

				mongoTemplate.insert(parParcelconnection);// 插入信息监控表
			}
			if ("0".equals(roleId) || "1".equals(roleId) || "-1".equals(roleId)) {// 收派员
				this.setAccSrecpayvouchers(accSrecpayvouchers, null,
						freightMoney, size, Long.valueOf(memberId), memberName,
						Long.valueOf(compId));
			} else {
				this.setAccSrecpayvouchers(accSrecpayvouchers, null,
						freightMoney, size, Long.valueOf(memberId), memberName,
						null);
			}
			mongoTemplate.insert(accSrecpayvouchers);// 插入收据表
			// ##############################################start#############################
			try {
				this.insertBasCustomerInfo(sendName, sendPhone, sendAddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.info("自取件--收现金,takeSendPackageByMember保存客户信息失败--大数据用, 失败原因: "
						+ e.getStackTrace());
			}

			// ##############################################end#############################
		} catch (Exception e) {
			e.printStackTrace();
			map.put("data", "002");
			return map; // 002 代表自取件异常
		}
		map.put("data", "001");
		return map;// 001 代表成功
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////
	// 收派员自添加任务与包裹
	// String json =
	// "{"data":[{"receiveProvince":"beijing","receivePhone":"15810885211","receiveName":"zhangsan","expWaybillNum":"123"},{"receiveProvince":"shanghai","receivePhone":"15810885212","receiveName":"lisi","expWaybillNum":"456"},{"receiveProvince":"guangzhou","receivePhone":"15810885213","receiveName":"wanger","expWaybillNum":"789"},{"receiveProvince":"shenzhen","receivePhone":"15810885214","receiveName":"mazi","expWaybillNum":"110"}]}";
	public Map<String, Object> takeSendPackageByMemberAdd(String memberId,
			String sendName, String sendPhone, String sendAddress,
			String netId, String packageNum, Double freightMoney,
			String packageJson, String sign) {
		// String roleId =
		// memberInfoMapperV4.findRoleByMemberId(Long.valueOf(memberId));
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		Long compId = Long.valueOf(memberInfoVO.getCompId());
		String roleId = memberInfoVO.getRoleId();
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("==============收派员自添加取件 takeSendPackageByMemberAdd ===============开始创建任务");
		try {
			// Long compId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));

			// 保存客户信息
			logger.info("微信收款 ...添加包裹时,把寄件人的信息添加到包裹里面.......customerCompId: "
					+ compId + "..寄件人姓名: " + sendName + "..寄件人姓名: " + sendPhone
					+ ".寄件人memberId: " + memberId + "======================== ");
			// 插入客户信息
			/*
			 * Map map1=new HashMap(); map1.put("compId",compId);
			 * map1.put("customerName",sendName);
			 * map1.put("customerPhone",sendPhone);
			 * map1.put("memberId",memberId); String url =
			 * constPool.getOpencustomerUrl() +
			 * "customerNewInfoTwo/insertCustomerV4"; String result = Post(url,
			 * map1);
			 */
			// 1.生成一个任务
			// 2.生成一个任务监控
			// 3.生成一个任务信息表
			// 给这些包裹把任务taskId添加进去
			ParTaskInfo parTaskInfo = new ParTaskInfo();// 任务表
			long taskId = IdWorker.getIdWorker().nextId();// 任务id
			logger.info("======收派员自添加取件 takeSendPackageByMemberAdd ===============开始创建任务taskId: "
					+ taskId);
			parTaskInfo.setUid(taskId);// 任务主键(任务id)
			parTaskInfo.setTaskType((byte) 0);// 0:取件
			parTaskInfo.setMobilePhone(sendPhone);
			parTaskInfo.setAmount(BigDecimal.valueOf(0));// 初始金额
			parTaskInfo.setCoopCompId(compId);// 公司compId
			if (!PubMethod.isEmpty(netId)) {
				parTaskInfo.setCoopNetId(Long.valueOf(netId));
			}
			parTaskInfo.setTaskStatus((byte) 1);// 任务状态1: 已分配
			// parTaskInfo.setCustomerId(customerId);//
			parTaskInfo.setTaskIsEnd((byte) 0);// 任务结束 0:未结束
			parTaskInfo.setContactName(sendName);// 联系人name
			parTaskInfo.setContactMobile(sendPhone);// 联系人手机----片键-----发件人手机号
			parTaskInfo.setContactTel(sendPhone);// 联系人电话
			parTaskInfo.setContactAddress(sendAddress);// 联系人详细地址
			parTaskInfo.setPayStatus((short) 0);// 默认值0
			Date date = new Date();
			parTaskInfo.setCreateTime(date);// 创建时间 任务创建时间
			parTaskInfo.setActorMemberId(Long.valueOf(memberId));// 执行人的memberId(添加取件的memberId)
			// String actorPhone =
			// memberInfoMapperV4.findMemberPhoneByMemberId(Long.valueOf(memberId));
			String actorPhone = memberInfoVO.getMemberPhone();
			parTaskInfo.setActorPhone(actorPhone);// 执行人的phone(添加取件的人的phone)
			parTaskInfo.setCreateUserId(Long.valueOf(memberId));// 创建人memberId,添加人的memberId
			parTaskInfo.setTaskFlag((byte) 0);// 任务标志 0:正常单
			parTaskInfo.setParEstimateCount(Byte.valueOf(packageNum));// 包裹数量
			parTaskInfo.setTaskSource((byte) 2);// 2收派员站点自建
			mongoTemplate.insert(parTaskInfo);// 叫快递插入已完成任务记录
			logger.info("=====1.取件插入问题parTaskInfo==sendPhone="
					+ parTaskInfo.getContactMobile() + ",Uid="
					+ parTaskInfo.getUid());
			ParTaskDisposalRecord parTaskDisposalRecord = new ParTaskDisposalRecord();// 任务记录表
			parTaskDisposalRecord.setUid(IdWorker.getIdWorker().nextId());// 主键id
			parTaskDisposalRecord.setDisposalType((byte) 1);// 1:已分配
			parTaskDisposalRecord.setTaskId(taskId);// 任务id
			parTaskDisposalRecord.setMemberId(Long.valueOf(memberId));
			parTaskDisposalRecord.setCompId(compId);// 公司compId
			parTaskDisposalRecord.setDisposalObject((byte) 0);// 派送员
			parTaskDisposalRecord.setShowFlag((byte) 1);// 1显示
			parTaskDisposalRecord.setTaskErrorFlag((byte) 0);// 异常标识 0:正常
			parTaskDisposalRecord.setCreateTime(date);// 创建时间
			parTaskDisposalRecord.setDisposalDesc("自建任务---收派员添加包裹");
			mongoTemplate.insert(parTaskDisposalRecord);// 叫快递插入任务信息表中一条已经完成的任务信息
			logger.info("=====2.取件插入问题parTaskDisposalRecord==taskId="
					+ parTaskDisposalRecord.getTaskId() + ",Uid="
					+ parTaskDisposalRecord.getUid());
			// Long netId = memberInfoMapperV4.findNetIdByCompId(compId);
			// 1.生成一个已完成的任务
			// 2.保存包裹
			// 3.包裹地址
			// 4.保存信息监控表
			JSONObject jsonObject = JSONObject.parseObject(packageJson);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int size = jsonArray.size();
			logger.info("打印手机端传过来的jsonObject:" + jsonObject
					+ " 拿到的data 数据 jsonArray: " + jsonArray + " 几个包裹: " + size);
			// AccSrecpayvouchers accSrecpayvouchers = new
			// AccSrecpayvouchers();//收付凭证
			// 一个任务的包裹对应一个收据id
			// Long receiptId = IdWorker.getIdWorker().nextId();
			// accSrecpayvouchers.setUid(receiptId);
			// String memberName =
			// memberInfoMapperV4.findMemberNameByMemberId(Long.valueOf(memberId));
			// ParParcelinfo parParcelinfo = null;//包裹表
			// ParParceladdress parParceladdress = null;
			// ParParcelconnection parParcelconnection = null;
			// Long senderCompId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
			for (int i = 0; i < size; i++) {
				logger.info("=====添加包裹的个数是多少个::::: size: " + size
						+ " 第几个包裹了i: " + i);
				// map = new HashMap<String,Object>();
				// Long id = IdWorker.getIdWorker().nextId();//主键id
				JSONObject object = jsonArray.getJSONObject(i);
				String receiveProvince = object.getString("receiveProvince");// 收件人城市
				String receivePhone = object.getString("receivePhone");// 收件人手机
				String receiveName = object.getString("receiveName");// 收件人姓名
				String expWaybillNum = object.getString("expWaybillNum");// 包裹运单号
				// String weightFortransit =
				// "10";//object.getString("weightFortransit");//包裹重量
				String weightFortransit = "0";
				if (!PubMethod.isEmpty(object.getString("weightFortransit"))) {
					weightFortransit = object.getString("weightFortransit");
				}
				;// 包裹重量
					// parParcelinfo = null;
				ParParcelinfo parParcelinfo = new ParParcelinfo();
				long parcelId = IdWorker.getIdWorker().nextId();// 包裹主键id
				logger.info("微信支付生成的包裹主键parcelId=====:" + parcelId
						+ " Long转换之后的: " + Long.valueOf(parcelId));
				parParcelinfo.setUid(parcelId);// 1.包裹id 生成的19位id id
												// 与地址表中的主键id相同
				// 为了测试用时间戳生成主键id
				// long parcelId = Long.valueOf("11111") +new Date().getTime();
				// parParcelinfo.setUid(parcelId);//1.包裹id 生成的19位id id
				// 与地址表中的主键id相同
				parParcelinfo.setMobilePhone(sendPhone);// 包裹 ----- 片键
														// ----发件人手机号
														// ---------------------
				parParcelinfo.setExpWaybillNum(expWaybillNum);// 5.运单号
																// ExpWaybillNum
				parParcelinfo.setCompId(compId);// 6.公司id CompId
				if (!PubMethod.isEmpty(netId)) {
					parParcelinfo.setNetId(Long.valueOf(netId));// 7.网络id NetId
				} else {
					parParcelinfo.setNetId(null);// 7.网络id NetId
				}
				// 8.包裹重量 ChareWeightForsender
				parParcelinfo.setChareWeightFortransit(BigDecimal
						.valueOf(Double.valueOf(weightFortransit)));
				parParcelinfo.setFreight(null);// 9.包裹应收运费 Freight
				parParcelinfo.setGoodsPaymentMethod((short) 0);// 10.支付方式
																// GoodsPaymentMethod
																// 0:不代收, 1:代收
				parParcelinfo.setCodAmount(BigDecimal.valueOf((double) 0));// 11.代收货款金额
																			// CodAmount
				// 12.保价金额 InsureAmount
				// 13.保价费 PricePremium
				// 14.包装费 PackingCharges

				parParcelinfo.setFreightPaymentMethod((short) 0);// 15.运费支付方式
																	// FreightPaymentMethod
																	// 0
				parParcelinfo
						.setActualCodAmount(BigDecimal.valueOf((double) 0));// )16.代收货款实际收到的货款金额
																			// ActualCodAmount
																			// 0
				// Long senderCompId =
				// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
				// 17.产品描述 GoodsDesc null
				// 18.包裹备注 ParcelRemark null
				// 19.服务产品ID ServiceId null
				parParcelinfo.setSignMember(null);// 20.签收人 SignMember 收件人
				parParcelinfo.setCreateUserId(Long.valueOf(memberId));// 21.创建人id
																		// CreateUserId
																		// memberId
				// parParcelinfo.setTakeTaskId(taskId);//2.取件任务id TakeTaskId
				if ("21".equals(sign)) {// 21:代收点微信支付
					parParcelinfo.setCpTakeTaskId(taskId);// 主 该字段是代收点微信支付存的任务id
					parParcelinfo.setTakeTaskId(11111111l);// 福(为了收派员交寄查询数据用的)
				} else {
					parParcelinfo.setTakeTaskId(taskId);// 主 2.取件任务id
														// TakeTaskId,该字段是收派员微信支付
					parParcelinfo.setCpTakeTaskId(22222222l);// 福(为了代收点交寄查询数据用的)
				}
				parParcelinfo.setCpReceiptStatus((short) 0);// 0:未付, 1:已付,代收点

				parParcelinfo.setParcelEndMark("0");// 22.包裹结束标志 ParcelEndMark
													// 0:未结束
				parParcelinfo.setActualTakeMember(Long.valueOf(memberId));// 23.取件人id
																			// ActualTakeMember
																			// memberId
				parParcelinfo.setParcelStatus((short) 0);// 29.包裹状态
															// 0：待取件;1：已取件;10：待派件;11：已派件;12:派件异常
															// ParcelStatus ;
															// 1：已取件
				parParcelinfo.setPickupTime(date);// 24.取件时间 PickupTime new
													// Date();
				// 25.派件人id ActualSendMember null
				// parParcelinfo.setReceiptId(receiptId);//26.收据id ReceiptId
				// 27.签收结果 0：未签收/拒签 1：签收 SignResult 0 null
				parParcelinfo.setTackingStatus((short) 0);// 28.包裹当前状态 0:在途,未签收
															// 1:已签收
															// TackingStatus 0 0
				if ("2".equals(roleId) || "3".equals(roleId)) {// 代收点

					parParcelinfo.setSenderCompId(compId);// 发货方存代收点的compId
					parParcelinfo.setSenderUserId(Long.valueOf(memberId));// 发货方存代收点的memberId
				}
				// 30.disposal_desc null
				// 31.如果异常原因不为空则添加异常时间 ExceptionTime null
				parParcelinfo.setCreateTime(date);// 32.设置包裹创建时间 CreateTime new
													// Date();
				mongoTemplate.insert(parParcelinfo);// 插入包裹
				logger.info("=====3.取件插入问题parParcelinfo==MobilePhone="
						+ parParcelinfo.getMobilePhone() + ",Uid="
						+ parParcelinfo.getUid());

				// =============================================================================================
				// parParceladdress = null;
				ParParceladdress parParceladdress = new ParParceladdress();// 包裹地址表
				logger.info("紧紧传过来的地址表的uid：" + parcelId + ",receiverName:"
						+ receiveName);
				parParceladdress.setUid(parcelId); // 1.包裹地址id 生成的19位id id
													// 与包裹表中的主键id相同----------片键
				// parParceladdress.setSendCustomerId(Long.valueOf(customerId));//2.发件客户ID
				// SendCustomerId
				parParceladdress.setAddresseeAddressId(null);// 3.收件客户ID
																// AddresseeCustomerId
				parParceladdress.setSendCasUserId(null);// 4.发件人CASID
														// SendCasUserId
				parParceladdress.setAddresseeCasUserId(null);// 5.收件人CASID
																// AddresseeCasUserId
				parParceladdress.setAddresseeName(receiveName);// 6.收件人姓名
																// AddresseeName
				parParceladdress.setAddresseeMobile(receivePhone);// 7.收件人手机号码
																	// AddresseeMobile----------片键
				parParceladdress.setAddresseeAddressId(null);// 8.收件人乡镇id
																// AddresseeAddressId
				parParceladdress.setAddresseeAddress(receiveProvince);// 9.收件人详细地址
																		// AddresseeAddress
				parParceladdress.setSendName(sendName);// 10.发件人姓名 SendName
				parParceladdress.setSendMobile(sendPhone);// 11.发件人手机 SendMobile
				parParceladdress.setSendAddressId(null);// 12.发件人乡镇id
														// SendAddressId
				parParceladdress.setSendAddress(sendAddress);// 13.发件人详细地址
																// SendAddress
				parParceladdress.setSendLongitude(null);// 14.发件人地址经度
														// SendLongitude
				parParceladdress.setSendLatitude(null);// 15.发件人地址纬度
														// SendLatitude
				parParceladdress.setAddresseeLongitude(null);// 16.收件人地址经度
																// AddresseeLongitude
				parParceladdress.setAddresseeLatitude(null);// 17.收件人地址纬度
															// AddresseeLatitude
				parParceladdress.setCreateTime(new Date());// 地址创建时间
				mongoTemplate.insert(parParceladdress);// 插入地址表
				logger.info("=====4.取件插入问题parParceladdress==AddresseeMobile="
						+ parParceladdress.getAddresseeMobile() + "SendMobile="
						+ parParceladdress.getSendMobile() + ",Uid="
						+ parParceladdress.getUid());

				// parParcelconnection = null;
				ParParcelconnection parParcelconnection = new ParParcelconnection();// 包裹收派过程信息监控表
				parParcelconnection.setUid(IdWorker.getIdWorker().nextId());// -----------片键
				parParcelconnection.setMobilePhone(sendPhone);// -----------片键----发件人手机号
				parParcelconnection.setCompId(compId);// 公司compId
				if (!PubMethod.isEmpty(netId)) {
					parParcelconnection.setNetId(Long.valueOf(netId));// 网络netId
				} else {
					parParcelconnection.setNetId(null);// 网络netId//7.网络id NetId
				}
				// parParcelconnection.setNetId(Long.valueOf(netId));//网络netId
				parParcelconnection.setCosignFlag((short) 1);// 取件
				parParcelconnection.setCreateTime(new Date());// 创建时间
				parParcelconnection.setExpMemberId(Long.valueOf(memberId));// memberId
				parParcelconnection.setExpMemberSuccessFlag(null);// 取派件成功标志
				parParcelconnection.setParId(parcelId);// 包裹id
				parParcelconnection.setTaskId(taskId);// 任务id

				mongoTemplate.insert(parParcelconnection);// 插入信息监控表

			}
			// ##############################################start#############################
			try {
				this.insertBasCustomerInfo(sendName, sendPhone, sendAddress);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.info("自取件--微信支付,takeSendPackageByMemberAdd保存客户信息失败--大数据用, 失败原因: "
						+ e.getStackTrace());
			}
			// ##############################################end#############################
			// 调取微信支付的
			// Map<String, Object> saomaCreate =
			// customerInfoService.saomaCreate(taskId+"", freightMoney);
			// logger.info("======收派员自添加取件 takeSendPackageByMemberAdd ===============创建任务结束saomaCreate: "+saomaCreate);
			map.put("taskId", taskId);
			map.put("freightMoney", freightMoney);
			logger.info("任务创建完成=========== 返回taskId 开始生成二维码==============taskId: "
					+ taskId);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("data", "002");
			return map; // 003 代表自取件异常
		}
		// return "001";//001 代表成功
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 组合数据
	public void setAccSrecpayvouchers(AccSrecpayvouchers accSrecpayvouchers,
			Double totalCodAmount, Double totalFreight, int count,
			Long memberId, String memberName, Long compId) {
		accSrecpayvouchers.setPaymentWay((short) 2);
		accSrecpayvouchers.setVoucherFlag((short) 0);
		accSrecpayvouchers.setBillQuantity(count);
		accSrecpayvouchers.setRecePeopleId(memberId);
		accSrecpayvouchers.setRecePeopleName(memberName);
		accSrecpayvouchers.setCompId(compId);
		accSrecpayvouchers.setCreateUserId(memberId);
		accSrecpayvouchers.setCreateTime(new Date());
		accSrecpayvouchers.setTotalCodAmount(totalCodAmount == null ? null
				: new BigDecimal(totalCodAmount));
		accSrecpayvouchers.setTotalFreight(totalFreight == null ? null
				: new BigDecimal(totalFreight));
		accSrecpayvouchers.setTotalAmount(new BigDecimal(
				totalCodAmount == null ? 0 : totalCodAmount)
				.add(new BigDecimal(totalFreight == null ? 0 : totalFreight)));
		accSrecpayvouchers.setActualAmount(new BigDecimal(
				totalCodAmount == null ? 0 : totalCodAmount)
				.add(new BigDecimal(totalFreight == null ? 0 : totalFreight)));
		accSrecpayvouchers.setVoucherStatus((short) 2);
	}

	// 查询已取包裹列表(代收点和快递员已取包裹查询)
	@Override
	public Page queryHasTakeList(String memberId, String roleId,
			String netName, Integer currentPage, Integer pageSize,
			String queryDate) {
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 1.判断roleId角色是收派员还是代收点 1 站长 0 收派员 -1 后勤 :属于收派员; 2 代收站店长 3 代收站店员 :属于代收点
		Date startDate = null;
		Date endDate = null;
		// mogon 格式话日期
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			/*
			 * String d1 = queryDate + " 00:00:00"; String d2 = queryDate +
			 * " 23:59:59"; startDate = sim.parse(d1); endDate = sim1.parse(d2);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("1".equals(roleId) || "0".equals(roleId) || "-1".equals(roleId)) {// 属于收派员
			/**
			 * 代收点点击确认交寄给收派员 update.set("senderCompId", compId);//代收点的compId
			 * update.set("senderUserId", Long.valueOf(memberId));//代收点的memberId
			 * update.set("actualTakeMember", actorMemberId);//实际取件人的memberId
			 * update.set("parcelStatus", (short)1);//1: 已取件
			 * update.set("parcelEndMark", "0");//包裹结束标志 0:未结束
			 */

			Query query = new Query();
			query.addCriteria(Criteria.where("actualTakeMember").is(
					Long.valueOf(memberId)));// 取件人id
			query.addCriteria(Criteria.where("parcelStatus").is((short) 1));// 已取件
			query.addCriteria(Criteria.where("parcelEndMark")
					.in("0", (short) 0));// 未结束
			/*
			 * query.addCriteria(new Criteria("createTime").gte(startDate).lte(
			 * endDate));
			 */// 根据手机端传过来的时间进行查询数据
				// 根据创建时间进行排序
			query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
			Long count = mongoTemplate.count(query, ParParcelinfo.class);

			query.skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
			query.limit(pageSize);// 从skip开始,取多少条记录
			logger.info("进入站点已取包裹列表，query=" + query);
			List<ParParcelinfo> listParTask = mongoTemplate.find(query,
					ParParcelinfo.class);
			logger.info("查询的包裹不为空 ========  ||||||||||| =======listParTask: "
					+ listParTask);
			for (ParParcelinfo parParcelinfo : listParTask) {
				Date createTime = parParcelinfo.getCreateTime();// 已取包裹时间
				map = new HashMap<String, Object>();
				query = null;
				query = new Query();
				Long id = parParcelinfo.getUid();// 包裹id
				logger.info("收派员查询已取包裹列表========  ||||||||||| =======uid: "
						+ id);
				// query.addCriteria(Criteria.where("uid").is(id));//包裹id对应地址表的主键id
				query.addCriteria(Criteria.where("uid").is(id)
						.and("sendMobile").ne(null));
				ParParceladdress parParceladdress = mongoTemplate.findOne(
						query, ParParceladdress.class);
				String sendMobile = parParceladdress.getSendMobile();// 发件人手机
				String sendAddress = parParceladdress.getSendAddress();// 发件人地址
				String addresseeAddress = parParceladdress
						.getAddresseeAddress();// 收件人地址
				String addresseeMobile = parParceladdress.getAddresseeMobile();// 收件人手机
				Short cpReceiptStatus = parParcelinfo.getCpReceiptStatus();// 0:未付款,
																			// 1:已付款
				BigDecimal weightFortransit = parParcelinfo
						.getChareWeightFortransit();// 包裹重量
				String expWaybillNum = parParcelinfo.getExpWaybillNum();
				// map.put("netName", netName);
				map.put("sendMobile", sendMobile);// 发件人电话
				map.put("expWaybillNum", expWaybillNum);// 发件人电话
				map.put("cpReceiptStatus", cpReceiptStatus);// 是否付款
				map.put("sendAddress", sendAddress);// 发件人地址
				map.put("addresseeAddress", addresseeAddress);// 收件人地址
				map.put("addresseeMobile", addresseeMobile);// 收件人电话
				map.put("parcelId", id);// 包裹主键id
				map.put("createTime", createTime);// 已取包裹时间
				map.put("weightFortransit", weightFortransit);// 包裹重量
				map.put("netId", "");// 网络id
				map.put("netName", "");// 网络名称
				list.add(map);
				map = null;
			}
			Page page = PageUtil.buildPage(currentPage, pageSize);
			page.setItems(list);
			page.setTotal(count.intValue());
			return page;
		} else if ("2".equals(roleId) || "3".equals(roleId)) {// 属于代收点

			Query query = new Query();
			// 根据memberId 查询出compId
			// 查询不加公司id,因为代收点添加取件的时候没有添加公司id
			MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
			// Long compId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
			Long compId = Long.valueOf(memberInfoVO.getCompId());
			// query.addCriteria(Criteria.where("compId").is(compId));
			query.addCriteria(Criteria.where("actualTakeMember").is(
					Long.valueOf(memberId)));// 代收点的memberId
			query.addCriteria(Criteria.where("senderCompId").is(compId));// 发货方(代收点的compId)
			query.addCriteria(Criteria.where("parcelEndMark").is("0"));// 未结束
			/*
			 * query.addCriteria(new Criteria("createTime").gte(startDate).lte(
			 * endDate));
			 */// 根据手机端传过来的时间进行查询数据
				// query.addCriteria(Criteria.where("cpTakeTaskId").is("0"));//包裹是微信支付过来的
				// query.addCriteria(Criteria.where("cpReceiptStatus").is((short)1));//1:已付款(新需求不管是否付款都可以查到)
				// query.addCriteria(Criteria.where("takeTaskId").is(null));//任务为空
				// netName = "EMS速递";
			if (!PubMethod.isEmpty(netName)) {
				List<Long> netIdMap = memberInfoMapperV4
						.findNetIdByNetName(netName);
				query.addCriteria(Criteria.where("netId").in(netIdMap));// 网络id
			}
			// query.addCriteria(Criteria.where("takeTaskId").is(null));
			// 根据创建时间进行排序
			query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
			Long count = mongoTemplate.count(query, ParParcelinfo.class);

			query.skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
			query.limit(pageSize);// 从skip开始,取多少条记录
			logger.info("进入代收点已取包裹列表，query=" + query);
			List<ParParcelinfo> listPar = mongoTemplate.find(query,
					ParParcelinfo.class);
			for (ParParcelinfo parParcelinfo : listPar) {
				Date createTime = parParcelinfo.getCreateTime();// 已取包裹时间
				map = new HashMap<String, Object>();
				Long id = parParcelinfo.getUid();
				query = null;
				query = new Query();
				// 添加查询条件sendMobile来确定是取件的地址信息
				query.addCriteria(Criteria.where("uid").is(id)
						.and("sendMobile").ne(null));
				ParParceladdress parParceladdress = mongoTemplate.findOne(
						query, ParParceladdress.class);
				String sendMobile = parParceladdress.getSendMobile();// 发件人手机
				String sendAddress = parParceladdress.getSendAddress();// 发件人地址
				String addresseeAddress = parParceladdress
						.getAddresseeAddress();// 收件人地址
				String addresseeMobile = parParceladdress.getAddresseeMobile();// 收件人手机
				BigDecimal weightFortransit = parParcelinfo
						.getChareWeightFortransit();
				Long netId = parParcelinfo.getNetId();
				logger.info("list中netId为" + netId);
				// String netName1 =
				// memberInfoMapperV4.findNetNameByNetId(netId);//网络名称\
				String netName1 = this.getValByNetId(netId + "");
				Short cpReceiptStatus = parParcelinfo.getCpReceiptStatus();
				String expWaybillNum = parParcelinfo.getExpWaybillNum();
				map.put("netId", netId);// 网络id 用于比较选择的网络是否是同一个,不是同一个不让叫快递
				map.put("cpReceiptStatus", cpReceiptStatus);// 网络名称
				map.put("netName", netName1);// 网络名称
				map.put("sendMobile", sendMobile);// 发件人手机号
				map.put("sendAddress", sendAddress);// 发件人地址
				map.put("addresseeAddress", addresseeAddress);// 收件人详细地址
				map.put("addresseeMobile", addresseeMobile);// 收件人手机号
				map.put("parcelId", id);// 包裹主键id
				map.put("createTime", createTime);// 已取包裹时间
				map.put("weightFortransit", weightFortransit);// 包裹重量
				map.put("expWaybillNum", expWaybillNum);// 运单号
				list.add(map);
				map = null;
			}
			Page page = PageUtil.buildPage(currentPage, pageSize);
			page.setItems(list);
			page.setTotal(count.intValue());
			return page;
		}
		return null;
	}

	// 已取包裹详情
	@Override
	public Map<String, Object> queryHasTakePackDetailByParcelId(String parcelId) {

		Map<String, Object> map = new HashMap<String, Object>();
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(Long.valueOf(parcelId))
				.and("sendMobile").ne(null));
		ParParceladdress parParceladdress = mongoTemplate.findOne(query,
				ParParceladdress.class);
		// 1.查询发件人收件人信息
		String sendName = parParceladdress.getSendName();
		map.put("sendName", sendName);// 发件人姓名
		String sendMobile = parParceladdress.getSendMobile();// 发件人电话
		map.put("sendMobile", sendMobile);
		String sendAddress = parParceladdress.getSendAddress();
		map.put("sendAddress", sendAddress);// 发件人详细地址
		// 任务表中的备注

		query = new Query();
		query.addCriteria(Criteria.where("uid").is(Long.valueOf(parcelId)));
		query.addCriteria(new Criteria().orOperator(Criteria
				.where("takeTaskId").ne(null), Criteria.where("cpTakeTaskId")
				.ne(null)));

		ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
				ParParcelinfo.class);
		BigDecimal weightFortransit = parParcelinfo.getChareWeightFortransit();
		String expWaybillNum = parParcelinfo.getExpWaybillNum();
		map.put("expWaybillNum", expWaybillNum);// 运单号
		map.put("weightFortransit", weightFortransit);// 包裹重量
		String parcelRemark = parParcelinfo.getParcelRemark();// 包裹备注
		map.put("parcelRemark", parcelRemark);// 包裹备注
		String addresseeName = parParceladdress.getAddresseeName();
		map.put("addresseeName", addresseeName);// 收件人姓名
		String addresseeMobile = parParceladdress.getAddresseeMobile();
		map.put("addresseeMobile", addresseeMobile);// 收件人手机
		String addresseeAddress = parParceladdress.getAddresseeAddress();
		map.put("addresseeAddress", addresseeAddress);// 收件人详细地址
		return map;
	}

	// 查询该站点下的收派员
	@Override
	public Map<String, Object> querySiteMemberByMemberId(String memberId) {
		Long compId = memberInfoMapperV4.findCompIdByMemberId(Long
				.valueOf(memberId));
		logger.info("查询该站点下的收派员:"+",memberId:"+memberId+",compId:"+compId);
		Map<String, Object> map2 = assignPackage.queryEmployeeByCompId(compId,memberId);
		logger.info("查询该站点下的收派员列表:"+map2);
		/*
		 * System.out.println("=========: "+map2); List<Map<String, Object>>
		 * listMap =
		 * memberInfoMapperV4.querySiteMemberByMemberId(Long.valueOf(memberId));
		 * for (Map<String, Object> map : listMap) { Object object =
		 * map.get("member_id"); map.put("headImgPath",
		 * headImgPath+""+object+".jpg");//头像 }
		 */
		return map2;
	}

	/**
	 * 2017-1-15 查询该站点下的所有收派员
	 */
	@Override
	public Map<String, Object> queryCompMemberByMemberId(String memberId) {
		Long compId = memberInfoMapperV4.findCompIdByMemberId(Long
				.valueOf(memberId));
		List<MemberInfo> member = assignPackage.queryMember(compId);
		Map<String, Object> map = null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		for (MemberInfo m : member) {
			map = new HashMap<String, Object>();
			map.put("compId", m.getCompId());
			map.put("memberName", m.getMemberName());
			map.put("memberId", m.getMemberId());
			map.put("memberPhone", m.getMemberPhone());
			map.put("roleId", m.getRoleId());
			listMap.add(map);
		}
		map2.put("listMap", listMap);
		map2.put("count", listMap.size());
		return map2;
	}

	// 转单=======new
	@Override
	public String turnTakePackageToMember(String taskId, String memberId,
			String toMemberId, String toMemberPhone) {
		try {
			logger.info("转单============================================收派员转单--------------");
			Query query = new Query();
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
			query.addCriteria(Criteria.where("actorMemberId").is(
					Long.valueOf(memberId)));
			ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
					ParTaskInfo.class);
			String mobilePhone = parTaskInfo.getMobilePhone();
			query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
			Update update = new Update();
			/**
			 * 收派员查询已取包裹
			 * query.addCriteria(Criteria.where("actualTakeMember").is(
			 * Long.valueOf(memberId)));//取件人id
			 * query.addCriteria(Criteria.where(
			 * "parcelStatus").is((short)1));//已取件
			 * query.addCriteria(Criteria.where("parcelEndMark").is("0"));//未结束
			 * 
			 * //收派员查询取件订单
			 * query.addCriteria(Criteria.where("taskIsEnd").is((byte
			 * )0));//包裹未结束
			 * //query.addCriteria(Criteria.where("taskId").is(taskId));
			 * query.addCriteria(Criteria.where("taskType").is((byte)0));//取件
			 * query
			 * .addCriteria(Criteria.where("actorMemberId").is(memberId));//
			 * memberId
			 * query.addCriteria(Criteria.where("taskStatus").is((byte)1));//已分配
			 * //根据创建时间进行排序 query.with(new
			 * Sort(Direction.DESC,"createTime"));//根据活动开始时间排序
			 */
			update.set("actorMemberId", Long.valueOf(toMemberId));// 被转单的收派员memberId
			update.set("actorPhone", toMemberPhone);// 被转单的收派员电话
			update.set("modifyTime", new Date());// 更新日期
			// Long coopCompId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(toMemberId));
			MemberInfoVO memberInfoVO = this.getValByMemberId(toMemberId);
			Long coopCompId = Long.valueOf(memberInfoVO.getCompId());
			update.set("coopCompId", coopCompId);// 被转单收派员的compId(任务受理方)
			// Long compNetId =
			// memberInfoMapperV4.findNetIdByCompId(coopCompId);
			Long compNetId = Long.valueOf(memberInfoVO.getNetId());
			update.set("coopNetId", compNetId);// 被转单收派员的netId(任务受理方)
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));
			query.addCriteria(Criteria.where("memberId").is(
					Long.valueOf(memberId)));
			update = new Update();
			update.set("memberId", Long.valueOf(toMemberId));// 被转单的收派员memberId
			update.set("modifyTime", new Date());// 更新时间
			update.set("compId", coopCompId);// 被转单公司compId
			mongoTemplate.updateFirst(query, update,
					ParTaskDisposalRecord.class);//
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("takeTaskId").is(
					Long.valueOf(taskId)));// 取件任务id
			query.addCriteria(Criteria.where("parcelStatus").is((short)0));// 待派
			List<ParParcelinfo> listParParcelinfo = mongoTemplate.find(query,
					ParParcelinfo.class);
			// 遍历包裹,修改成接单人员的信息
			for (ParParcelinfo parParcelinfo : listParParcelinfo) {
				Long uid = parParcelinfo.getUid();
				String mobilePhone1 = parParcelinfo.getMobilePhone();
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("uid").is(uid));
				query.addCriteria(Criteria.where("mobilePhone")
						.is(mobilePhone1));
				update = null;
				update = new Update();
				update.set("actualTakeMember", Long.valueOf(toMemberId));
				update.set("compId", Long.valueOf(coopCompId));
				update.set("netId", Long.valueOf(compNetId));
				mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
			}
			// 转单插入一条 parparcelconnection 收派过程信息表监控表
			ParParcelconnection parcelconnection = new ParParcelconnection();
			// 设置接收人和交付人的信息
			parcelconnection.setUid(IdWorker.getIdWorker().nextId());// id
			parcelconnection.setRecCompId(coopCompId);// 接单人的compid
			parcelconnection.setRecNetId(compNetId);// 接单人的netid
			parcelconnection.setCreateTime(new Date());// 时间
			parcelconnection.setRecMemberId(Long.parseLong(toMemberId));// 接单人的memberid
			parcelconnection.setRecCosignFlag((short) 4);// 接单
			parcelconnection.setExpMemberId(Long.parseLong(memberId));// 交付人的memberid
			parcelconnection.setTaskId(Long.parseLong(taskId));// 任务id
			parcelconnection.setCosignFlag((short) 4);// 转单
			// 查询交付人的信息
			MemberInfoVO memberInfoVO1 = this.getValByMemberId(memberId);
			parcelconnection
					.setCompId(Long.parseLong(memberInfoVO1.getCompId()));// 交付人的compid
			parcelconnection.setNetId(Long.parseLong(memberInfoVO1.getNetId()));// 交付人的netid
			parcelconnection.setMobilePhone(mobilePhone);// 收件人手机号
			this.mongoTemplate.insert(parcelconnection);// 插入一条包裹收派信息记录表
		} catch (Exception e) {
			e.printStackTrace();
			return "002";
		}
		return "001";
		// try {
		// logger.info("转单============================================收派员转单--------------");
		// Query query = new Query();
		// query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
		// query.addCriteria(Criteria.where("actorMemberId").is(
		// Long.valueOf(memberId)));
		// ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
		// ParTaskInfo.class);
		// String mobilePhone = parTaskInfo.getMobilePhone();
		// query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
		// Update update = new Update();
		// /**
		// * 收派员查询已取包裹
		// * query.addCriteria(Criteria.where("actualTakeMember").is(
		// * Long.valueOf(memberId)));//取件人id
		// * query.addCriteria(Criteria.where(
		// * "parcelStatus").is((short)1));//已取件
		// * query.addCriteria(Criteria.where("parcelEndMark").is("0"));//未结束
		// *
		// * //收派员查询取件订单
		// * query.addCriteria(Criteria.where("taskIsEnd").is((byte
		// * )0));//包裹未结束
		// * //query.addCriteria(Criteria.where("taskId").is(taskId));
		// * query.addCriteria(Criteria.where("taskType").is((byte)0));//取件
		// * query
		// * .addCriteria(Criteria.where("actorMemberId").is(memberId));//
		// * memberId
		// * query.addCriteria(Criteria.where("taskStatus").is((byte)1));//已分配
		// * //根据创建时间进行排序 query.with(new
		// * Sort(Direction.DESC,"createTime"));//根据活动开始时间排序
		// */
		// update.set("actorMemberId", Long.valueOf(toMemberId));//
		// 被转单的收派员memberId
		// update.set("actorPhone", toMemberPhone);// 被转单的收派员电话
		// update.set("modifyTime", new Date());// 更新日期
		// // Long coopCompId =
		// // memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(toMemberId));
		// MemberInfoVO memberInfoVO = this.getValByMemberId(toMemberId);
		// Long coopCompId = Long.valueOf(memberInfoVO.getCompId());
		// update.set("coopCompId", coopCompId);// 被转单收派员的compId(任务受理方)
		// // Long compNetId =
		// // memberInfoMapperV4.findNetIdByCompId(coopCompId);
		// Long compNetId = Long.valueOf(memberInfoVO.getNetId());
		// update.set("coopNetId", compNetId);// 被转单收派员的netId(任务受理方)
		// mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
		// query = null;
		// query = new Query();
		// query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));
		// query.addCriteria(Criteria.where("memberId").is(
		// Long.valueOf(memberId)));
		// update = new Update();
		// update.set("memberId", Long.valueOf(toMemberId));// 被转单的收派员memberId
		// update.set("modifyTime", new Date());// 更新时间
		// update.set("compId", coopCompId);// 被转单公司compId
		// mongoTemplate.updateFirst(query, update,
		// ParTaskDisposalRecord.class);//
		// query = null;
		// query = new Query();
		// query.addCriteria(Criteria.where("takeTaskId").is(
		// Long.valueOf(taskId)));// 取件任务id
		// List<ParParcelinfo> listParParcelinfo = mongoTemplate.find(query,
		// ParParcelinfo.class);
		// // Long compIdByMemberId =
		// //
		// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(toMemberId));//被转单人的compId
		// // Long findNetId =
		// // memberInfoMapperV4.findNetIdByCompId(coopCompId);//被转单人的netId
		// for (ParParcelinfo parParcelinfo : listParParcelinfo) {
		// Long uid = parParcelinfo.getUid();
		// String mobilePhone1 = parParcelinfo.getMobilePhone();
		// query = null;
		// query = new Query();
		// query.addCriteria(Criteria.where("uid").is(uid));
		// query.addCriteria(Criteria.where("mobilePhone")
		// .is(mobilePhone1));
		// update = null;
		// update = new Update();
		// update.set("actualTakeMember", Long.valueOf(toMemberId));
		// update.set("compId", Long.valueOf(coopCompId));
		// update.set("netId", Long.valueOf(compNetId));
		// mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
		// }
		// // 转单插入一条 par_task_process 转单任务记录表
		// ParTaskProcess parTaskProcess = new ParTaskProcess();
		// parTaskProcess.setUid(IdWorker.getIdWorker().nextId());// 主键id
		// parTaskProcess.setCreateTime(new Date());
		// // 任务发起的公司compId
		// // Long fromCompId =
		// // memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
		// memberInfoVO = this.getValByMemberId(memberId);
		// Long fromCompId = Long.valueOf(memberInfoVO.getCompId());
		// parTaskProcess.setFromCompId(fromCompId);
		// parTaskProcess.setFromMemberId(Long.valueOf(memberId));
		// // Long toCompId =
		// // memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(toMemberId));
		// parTaskProcess.setFromCompId(coopCompId);
		// parTaskProcess.setFromMemberId(Long.valueOf(toMemberId));
		// parTaskProcess.setTaskStatus((byte) 0);// 任务状态 0:指派
		// parTaskProcess.setTaskTransmitCause((byte) 2);// 默认给的原因2:网点任务太多忙不过来
		// parTaskProcess.setOperatorId(Long.valueOf(memberId));// 创建人id
		// parTaskProcess.setOperatorCompId(fromCompId);
		// parTaskProcess.setTaskId(Long.valueOf(taskId));// 任务id
		// mongoTemplate.insert(parTaskProcess);// 插入到 转单任务记录表中
		// } catch (Exception e) {
		// e.printStackTrace();
		// return "002";
		// }
		// return "001";
	}

	// 收派员包裹交寄给站点
	@Override
	public String consignationToSiteByMemberId(String parcelIds) {

		try {

			logger.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝0.收派员将包裹交给站点参数＝＝＝＝＝＝＝＝＝: " + parcelIds);
			Query query = null;
			Update update = null;
			String[] parcelIdArr = parcelIds.split(",");

			for (String parcelId : parcelIdArr) {
				// query = null;
				query = new Query();

				// logger.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝1.1收派员将包裹交给站点单个参数＝＝＝＝＝＝＝＝＝: "+parcelId);
				if (PubMethod.isEmpty(parcelId))
					return "002";// 002 代表交寄失败

				query.addCriteria(Criteria.where("uid")
						.is(Long.valueOf(parcelId)).and("takeTaskId").ne(null));

				ParParcelinfo parParcelinfo2 = mongoTemplate.findOne(query,
						ParParcelinfo.class);
				logger.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝1.2收派员将包裹交给站点单个参数＝＝＝＝＝＝＝＝＝: "
						+ parcelId + " parParcelinfo2");
				if (PubMethod.isEmpty(parParcelinfo2))
					return "002";// 002 代表交寄失败

				Long actualTakeMember = parParcelinfo2.getActualTakeMember();// 实际取件人memberId

				String mobilePhone = parParcelinfo2.getMobilePhone();
				// query = null;
				query = new Query();
				// update = null;
				update = new Update();
				/**
				 * query.addCriteria(Criteria.where("actualTakeMember").is(Long.
				 * valueOf(memberId)));
				 * query.addCriteria(Criteria.where("parcelEndMark"
				 * ).is("1"));//结束
				 * query.addCriteria(Criteria.where("pickupTime")
				 * .gte(start).lte(end));
				 */
				query.addCriteria(Criteria.where("uid")
						.is(Long.valueOf(parcelId)).and("takeTaskId").ne(null));
				query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));

				// ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
				// ParParcelinfo.class);

				update.set("parcelEndMark", "1");// 交给站点包裹结束
				update.set("pickupTime", new Date());// 给站点的时间(为了收派员取件记录查到)
				logger.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝1.收派员将包裹交给站点查询条件: " + query);
				WriteResult wr = mongoTemplate.updateFirst(query, update,
						ParParcelinfo.class);// 修改改包裹的为已结束
				logger.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝2.收派员将包裹交给站点更新记录条数: " + wr.getN());
				// 用于快递圈中取件数量的查询======================================
				try {
					logger.info("开始执行天, 周 , 月  数量的方法===================== >>>>>>>>>>>>>>>开始");
					long start = System.currentTimeMillis();
					// 开始执行插入天的数量的方法
					/*
					 * this.updateDay(actualTakeMember); //开始执行插入月的数量的方法
					 * this.updateMonth(actualTakeMember); //开始执行插入周的数量的方法
					 * this.updateWeek(actualTakeMember);
					 */
					this.updateDayWeekMonth(actualTakeMember);
					long end = System.currentTimeMillis();
					logger.info("执行 天, 周 , 月三个数量的 方法用了 "
							+ ((end - start) / 1000)
							+ " 秒============================");
					logger.info("结束执行天, 周 , 月  数量的方法===================== >>>>>>>>>>>>>>>结束");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// query = null;
				query = new Query();

				query.addCriteria(Criteria.where("parId")
						.is(Long.valueOf(parcelId)).and("cosignFlag").is(1));
				logger.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝3.收派员将包裹交给站点查询ParParcelconnection条件: "
						+ query);
				ParParcelconnection parParcelconnection = mongoTemplate
						.findOne(query, ParParcelconnection.class);
				if (!PubMethod.isEmpty(parParcelconnection)) {
					Long uid = parParcelconnection.getUid();
					// query = null;
					query = new Query();
					query.addCriteria(new Criteria("uid").is(uid)
							.and("cosignFlag").is(1));
					String mobilePhone2 = parParcelconnection.getMobilePhone();
					query.addCriteria(new Criteria("mobilePhone")
							.is(mobilePhone2));
					// update = null;
					update = new Update();
					update.set("expMemberSuccessFlag", (short) 1);// 修改包裹状态成功
					WriteResult wr1 = mongoTemplate.updateFirst(query, update,
							ParParcelconnection.class);// 修改包裹记录过程表为成功

					logger.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝4.收派员将包裹交给站点查询ParParcelconnection条数: "
							+ wr1.getN());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "002";// 002 代表交寄失败
		}
		return "001";// 001 代表交寄成功
	}

	private void updateWeek(Long actualTakeMember) {
		logger.info("进入更新周数量的方法.........updateWeek.............memberId: "
				+ actualTakeMember);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int iWeek = cal.get(Calendar.WEEK_OF_YEAR);
		String iiw = iWeek + "";
		// if(iWeek < 10){
		// iiw = "0"+iWeek;
		// }
		System.out.println("今天是今年的第: " + iiw + " 周");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String format = dateFormat.format(date) + "-" + iiw;// 格式 yyyy-ww 年-周
		logger.info("拼装好的 时间 年-周: " + format);
		Query query3 = Query.query(Criteria.where("memberId")
				.is(actualTakeMember).and("strWeek").is(format));
		List<ParWeekCount> list = mongoTemplate
				.find(query3, ParWeekCount.class);
		logger.info("是否查询到第+" + format + "+周有数据list: " + list);
		if (list.size() == 0 || list == null) {
			Long provinceId = this.queryProvinceId(actualTakeMember);// 站点所属省份id
			logger.info("1 进入开始插入周数量的方法=============================省份id===========provinceId: "
					+ provinceId);

			// 就创建
			ParWeekCount weekCount = new ParWeekCount();
			weekCount.setMemberId(actualTakeMember);
			weekCount.setCreateTime(date);// 创建时间
			weekCount.setModifyTime(date);
			weekCount.setTakeCount(1);// 取件数量
			weekCount.setStrWeek(format);// 时间当天的
			weekCount.setProvinceId(provinceId);
			mongoTemplate.insert(weekCount);
			logger.info("2 插入完成周数量的方法============================================");
		} else {
			// 更新
			logger.info("1 开始更新周数量的方法===============================");
			Update updateD = new Update();
			Query query4 = new Query();
			query4.addCriteria(Criteria.where("memberId").is(actualTakeMember));
			query4.addCriteria(Criteria.where("strWeek").is(format));
			// updateD.set("memberId", actualTakeMember);
			// updateD.set("strDay", format);
			updateD.set("modifyTime", new Date());
			updateD.inc("takeCount", +1);
			mongoTemplate.updateFirst(query4, updateD, ParWeekCount.class);
			logger.info("3 周数量的方法 更新完成   加 1===============================");
		}
		logger.info("进入更新周数量的方法 执行结束...........updateWeek.................................");

	}

	private void updateMonth(Long actualTakeMember) {

		//
		logger.info("进入更新月数量的方法.........updateMonth.............memberId: "
				+ actualTakeMember);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

		// 先去查询是否有这条记录
		Date date = new Date();
		String format = dateFormat.format(date);
		logger.info("月==============更新数量后的格式化日期: " + format);
		Query query3 = Query.query(Criteria.where("memberId")
				.is(actualTakeMember).and("strMouth").is(format));
		List<ParMonthCount> list = mongoTemplate.find(query3,
				ParMonthCount.class);
		logger.info("月的是否查询到数据 时间 : " + format + " === list: " + list);
		if (list.size() == 0 || list == null) {
			Long provinceId = this.queryProvinceId(actualTakeMember);// 站点所属省份id
			logger.info("1 进入开始插入当月数量的方法================================省份id===========provinceId: "
					+ provinceId);
			// 就创建
			ParMonthCount monthCount = new ParMonthCount();
			monthCount.setMemberId(actualTakeMember);
			monthCount.setCreateTime(date);// 创建时间
			monthCount.setModifyTime(date);
			monthCount.setTakeCount(1);// 取件数量
			monthCount.setStrMouth(format);// 时间当天的
			monthCount.setProvinceId(provinceId);
			mongoTemplate.insert(monthCount);
			logger.info("2 插入完成当月数量的方法============================================");
		} else {
			Update updateD = new Update();
			// 更新
			logger.info("1 开始更新当月数量的方法===============================");
			Query query4 = new Query();
			query4.addCriteria(Criteria.where("memberId").is(actualTakeMember));
			query4.addCriteria(Criteria.where("strMouth").is(format));
			// updateD.set("memberId", actualTakeMember);
			// updateD.set("strDay", format);
			updateD.set("modifyTime", new Date());
			updateD.inc("takeCount", +1);
			mongoTemplate.updateFirst(query4, updateD, ParMonthCount.class);
			logger.info("3 当月数量的方法 更新完成   加 1===============================");
		}
		logger.info("进入更新月数量的方法 执行结束...........updateMonth.................................");

	}

	private void updateDayWeekMonth(Long actualTakeMember) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", actualTakeMember);// Long memberId, String flag
		map.put("flag", 1);// Long memberId, String flag
		String communityapiUrl = constPool.getCommunityapiUrl();
		String url = communityapiUrl + "attentionContacts/updateDayWeekMonth";
		String result = this.Post(url, map);
	}

	private void updateDay(Long actualTakeMember) {
		//
		logger.info("进入更新天数量的方法.........updateDay.............memberId: "
				+ actualTakeMember);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 先去查询是否有这条记录
		Date date = new Date();
		String format = dateFormat.format(date);
		logger.info("更新天数量的方法的日期:format:  " + format);
		Query query3 = Query.query(Criteria.where("memberId")
				.is(actualTakeMember).and("strDay").is(format));
		List<ParDayCount> list = mongoTemplate.find(query3, ParDayCount.class);
		logger.info("更新天数量的方法是否查询到数据:{{{{{{{{{{}}}}}}}}}}}:::::::list:  "
				+ list);
		String communityapiUrl = constPool.getCommunityapiUrl();
		String url = communityapiUrl + "attentionContacts/updateDayWeekMonth";
		if (list.size() == 0 || list == null) {
			// 就创建
			Map<String, Object> map = new HashMap<String, Object>();
			Long provinceId = this.queryProvinceId(actualTakeMember);// 站点所属省份id
			logger.info("1 进入开始插入当天数量的方法================================省份id===========provinceId: "
					+ provinceId);
			map.put("memberId", actualTakeMember);// Long memberId, String flag
			map.put("flag", 1);// Long memberId, String flag
			ParDayCount dayCount = new ParDayCount();
			// dayCount.setMemberId(actualTakeMember);
			map.put("actualTakeMember", actualTakeMember);
			// dayCount.setCreateTime(date);//创建时间
			map.put("date", date);
			// dayCount.setModifyTime(date);
			map.put("date", date);
			// dayCount.setTakeCount(1);//取件数量
			map.put("takeCount", 1);
			// dayCount.setStrDay(format);//时间当天的
			map.put("strDay", format);
			// dayCount.setProvinceId(provinceId);
			map.put("provinceId", provinceId);
			String result = this.Post(url, map);
			// mongoTemplate.insert(dayCount);
			logger.info("2 插入完成当天数量的方法============================================");
		} else {
			Update updateD = new Update();
			// 更新
			logger.info("1 开始更新当天数量的方法===============================");
			Query query4 = new Query();
			query4.addCriteria(Criteria.where("memberId").is(actualTakeMember));
			query4.addCriteria(Criteria.where("strDay").is(format));
			// updateD.set("memberId", actualTakeMember);
			// updateD.set("strDay", format);
			updateD.set("modifyTime", new Date());
			updateD.inc("takeCount", +1);
			// mongoTemplate.updateFirst(query4, updateD, ParDayCount.class);

			logger.info("3 当天数量的方法 更新完成   加 1===============================");
		}
		logger.info("进入更新天数量的方法 执行结束...........updateDay.................................");

	}

	/**
	 * 根据memberId查询站点所属的provinceId
	 */
	public Long queryProvinceId(Long memberId) {
		Long provinceId = null;
		if (!PubMethod.isEmpty(memberId)) {
			HashMap<String, Object> map = basEmployeeAuditMapper
					.queryProvinceId(memberId);
			if (!PubMethod.isEmpty(map)) {
				provinceId = PubMethod.isEmpty(map.get("provinceId")) ? null
						: Long.valueOf(map.get("provinceId").toString());
			}
		}
		return provinceId;
	}

	// 代收点查询附近5公里范围内的收派员
	@SuppressWarnings("rawtypes")
	public List<Map> queryNearMemberByTude(String memberId, String netId) {
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = null;
		// 查询出代收点的经纬度
		Map<String, Object> map = memberInfoMapperV4
				.findLongOrLatiTudeByMemberId(Long.valueOf(memberId));
		// Long compId = Long.valueOf(map.get("comp_id")+"");//公司compId
		Double longitude = Double.valueOf(map.get("longitude") + "");
		Double latitude = Double.valueOf(map.get("latitude") + "");
		// 1.查询附近5公里的收派员(选择的同一个网络下的netId)
		// 查询出代收点的经纬度
		Long netId1 = null;
		if (PubMethod.isEmpty(netId)) {
			netId1 = null;
		} else {
			netId1 = Long.valueOf(netId);
		}
		String roleType = "2";
		// List<Map<String, Object>> queryNearMemberForWechat =
		// courierService.queryNearMemberForWechat(longitude, latitude, 1, 5,
		// netId1, roleType);
		List<Map> queryNearMemberForWechat = new ArrayList<>();

		Map<String, String> callMap = new HashMap<>();
		callMap.put("lng", String.valueOf(longitude));
		callMap.put("lat", String.valueOf(latitude));
		callMap.put("sortFlag", "1");
		callMap.put("howFast", "5");
		callMap.put("assignNetId",
				String.valueOf(PubMethod.isEmpty(netId1) ? "0" : netId1));
		callMap.put("roleType", roleType);
		String nearCourierJson = noticeHttpClient.Post(opencall
				+ "Courier/queryNearMemberForWechat", callMap);
		if (PubMethod.isEmpty(nearCourierJson)) {
			return queryNearMemberForWechat;
		}
		String dataJson = JSON.parseObject(nearCourierJson).getString("data");
		if (PubMethod.isEmpty(dataJson)) {
			return queryNearMemberForWechat;
		}
		List<Map> array = JSON.parseArray(dataJson, Map.class);
		return array;
		// List<Map<String,Object>> byTudeList =
		// this.queryNearMemberByTude(memberId);
		/*
		 * int len = queryNearMemberForWechat.size(); if(len <= 0){
		 * List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 * //list.add(); return list; } for (Map<String, Object> map2 :
		 * queryNearMemberForWechat) { map1 = new HashMap<String,Object>();
		 * //收派员与代收点的距离 //double distance=DistanceUtil.getDistance(latitude,
		 * longitude, Double.parseDouble(String.valueOf(map2.get("latitude"))),
		 * Double.parseDouble(String.valueOf(map2.get("longitude"))));
		 * map1.put("memberName", map2.get("memberName"));
		 * map1.put("memberMobile", map2.get("memberMobile"));
		 * map1.put("netName", map2.get("netName")); map1.put("memberId",
		 * map2.get("memberId")); map1.put("longitude", map2.get("longitude"));
		 * map1.put("latitude", map2.get("latitude")); map1.put("createTime",
		 * map2.get("createTime")); map1.put("distance", map2.get("distance"));
		 * map1.put("headImgPath", map2.get("headImg"));//头像 list1.add(map1);
		 * map2 = null; } return list1;
		 */
	}

	public Map<String, Object> queryFromPosition(Double longitude,
			Double latitude) {
		int dis = 5;// 设置5公里范围内
		double EARTH_RADIUS = 6378.137 * 1000;
		double dlng = Math.abs(2 * Math.asin(Math.sin(dis * 1000
				/ (2 * EARTH_RADIUS))
				/ Math.cos(latitude)));
		dlng = dlng * 180.0 / Math.PI; // 弧度转换成角度
		double dlat = Math.abs(dis * 1000 / EARTH_RADIUS);
		dlat = dlat * 180.0 / Math.PI; // 弧度转换成角度
		double bottomLat = latitude - dlat;
		double topLat = latitude + dlat;
		double leftLng = longitude - dlng;
		double rightLng = longitude + dlng;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bottomLat", bottomLat);
		params.put("topLat", topLat);
		params.put("leftLng", leftLng);
		params.put("rightLng", rightLng);
		return params;

	}

	// 得到代收点：名称，电话 还有包裹信息
	@Override
	public Map<String, Object> consignationInfo(String memberId,
			String parcelIds) {
		HashMap<String, Object> resultMap = new HashMap<>();
		// MemberInfoVO memberInfoVO = getDaiByMemberId(memberId);
		MemberInfoVO memberInfoVO = getValByMemberId(memberId) == null ? new MemberInfoVO()
				: getValByMemberId(memberId);
		String roleId = memberInfoVO.getRoleId();
		String memberName = memberInfoVO.getMemberName();
		String compId = memberInfoVO.getCompId();// 代理点id
		String compName = memberInfoVO.getCompName();// 名称
		String compTelephone = memberInfoVO.getMemberPhone();// 电话
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberName", memberName);
		map.put("compId", compId); // 代理点id
		map.put("compName", compName);// 名称
		map.put("compTelephone", compTelephone);// 电话
		map.put("roleId", roleId); // 角色

		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		// 包裹id
		String[] parcelIdArr = parcelIds.split(",");
		logger.info("代收点交寄给收派员包裹:===================: " + parcelIdArr);
		ParParcelinfo parParcelinfo = null;
		HashMap<String, Object> parMap = null;
		Query query = null;
		for (String parcelId : parcelIdArr) {
			query = null;
			query = new Query();
			query.addCriteria(new Criteria("uid").is(Long.valueOf(parcelId)));

			parParcelinfo = mongoTemplate.findOne(query, ParParcelinfo.class);
			if (!PubMethod.isEmpty(parParcelinfo)) {
				parMap = new HashMap<>();
				Date createTime = parParcelinfo.getCreateTime() == null ? new Date()
						: parParcelinfo.getCreateTime();// 时间
				// Long uid = parParcelinfo.getUid();
				Query query1 = new Query();
				// logger.info("从地址中取到的 ================== uid:"+uid);
				query1.addCriteria(Criteria.where("uid").is(
						Long.valueOf(parcelId)));
				ParParceladdress parParceladdress = mongoTemplate.findOne(
						query1, ParParceladdress.class) == null ? new ParParceladdress()
						: mongoTemplate.findOne(query1, ParParceladdress.class);
				logger.info("取件记录中查询地址parParceladdress============: "
						+ parParceladdress
						+ "===== parParceladdress.getUid() :"
						+ parParceladdress.getUid());

				Long netId = parParcelinfo.getNetId();
				String netName = memberInfoMapperV4.findNetNameByNetId(netId) == null ? ""
						: memberInfoMapperV4.findNetNameByNetId(netId);// 快递网络名称
				String sendAddress = parParceladdress.getSendAddress() == null ? ""
						: parParceladdress.getSendAddress();// 发件人地址
				String sendMobile = parParceladdress.getSendMobile() == null ? ""
						: parParceladdress.getSendMobile();// 发件人电话
				String sendName = parParceladdress.getSendName() == null ? ""
						: parParceladdress.getSendName();// 发件人name

				parMap.put("sendMobile", sendMobile);
				parMap.put("sendName", sendName);
				parMap.put("createTime", createTime);
				parMap.put("sendAddress", sendAddress);
				parMap.put("netName", netName);

				mapList.add(parMap);
			}
		}

		resultMap.put("map", map);// 交寄信息
		resultMap.put("mapList", mapList);// 取件信息
		resultMap.put("mapListNum", mapList.size());// 交寄多少件
		return resultMap;
	}

	private BasCompInfo getDaiByCompId(String compId) {
		// 代收点基本信息放入缓存中--2016年12月22日
		BasCompInfo basCompInfo = this.redisService.get("memberInfo-dai-cache",
				compId, BasCompInfo.class);
		if (PubMethod.isEmpty(basCompInfo)) {
			basCompInfo = this.basEmployeeAuditMapper.getDaiByCompId(compId);
			this.redisService.put("memberInfo-dai-cache", compId, basCompInfo);
		}
		return basCompInfo;
	}

	// 代收点交寄给收派员 12-26
	@Override
	public String consignationToMemberByParcelIds(String parcelIds,
			String toMemberId) {

		try {
			Query query = null;
			Update update = null;
			// Long compId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(toMemberId));//公司compId
			// Long netId =
			// memberInfoMapperV4.findNetIdByCompId(compId);//网络netId
			MemberInfoVO memberInfoVO = this.getValByMemberId(toMemberId);
			Long compId = Long.valueOf(memberInfoVO.getCompId());
			String netId2 = memberInfoVO.getNetId();
			String netId3 = netId2;
			if (PubMethod.isEmpty(netId2)) {
				netId3 = "";
			}
			String[] parcelIdArr = parcelIds.split(",");
			/**
			 * 收派员已取包裹查询
			 * query.addCriteria(Criteria.where("actualTakeMember").is(
			 * Long.valueOf(memberId)));//取件人id
			 * query.addCriteria(Criteria.where("parcelStatus"
			 * ).is((short)1));//已取件
			 * query.addCriteria(Criteria.where("parcelEndMark").is("0"));//未结束
			 */
			logger.info("代收点交寄给收派员包裹:===================: " + parcelIdArr);
			for (String parcelId : parcelIdArr) {
				// query = null;
				query = new Query();
				// update = null;
				update = new Update();
				query.addCriteria(new Criteria("uid")
						.is(Long.valueOf(parcelId)).and("cpTakeTaskId")
						.ne(null));
				ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
						ParParcelinfo.class);

				Long senderUserId = parParcelinfo.getSenderUserId();// 发货方客户ID---代理点的id
				try {
					logger.info("11 代收点开始更新日, 周, 月 的方法=======================>>>>>>>>>>>>>>>");
					/*
					 * this.updateDay(senderUserId);
					 * this.updateWeek(senderUserId);
					 * this.updateMonth(senderUserId);
					 */
					this.updateDayWeekMonth(senderUserId);
					logger.info("22 代收点结束更新日, 周, 月 的方法完成====================================>>>>>>>>>>>>>");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String mobilePhone = parParcelinfo.getMobilePhone();
				query.addCriteria(new Criteria("mobilePhone").is(mobilePhone));
				update.set("actualTakeMember", Long.valueOf(toMemberId));// 被交寄的收派员
				update.set("parcelStatus", (short) 1);// 包裹状态 1: 已取件
				// update.set("parcelEndMark", (short) 0);//未结束
				update.set("compId", compId);// 公司compId 被交寄的收派员的compId
				if (!PubMethod.isEmpty(netId3)) {
					update.set("netId", Long.valueOf(netId3));// 网络id被交寄的收派员的
																// netId
				} else {
					update.set("netId", null);
				}

				update.set("createTime", new Date());// 给收派员时间(为了代收点取件记录查询)
				logger.info("===1包裹问题查询====" + query);

				WriteResult wr = mongoTemplate.updateFirst(query, update,
						ParParcelinfo.class);// 修改包裹表
				logger.info("===2.包裹问题查询,更新数量====" + wr.getN());

				// query = null;
				query = new Query();
				query.addCriteria(Criteria.where("parId")
						.is(Long.valueOf(parcelId)).and("cosignFlag").is(1));
				ParParcelconnection parParcelconnection = mongoTemplate
						.findOne(query, ParParcelconnection.class);
				if (!PubMethod.isEmpty(parParcelconnection)) {
					logger.info("根据包裹id 查询包裹 :===================parcelId: "
							+ parcelId + "===");
					// query = null;
					query = new Query();
					Long uid = parParcelconnection.getUid();
					String mobilePhone2 = parParcelconnection.getMobilePhone();
					query.addCriteria(new Criteria("uid").is(uid));
					query.addCriteria(Criteria.where("mobilePhone")
							.is(mobilePhone2).and("cosignFlag").is(1));
					update.set("parId", Long.valueOf(parcelId));// 包裹id
					update = null;
					update = new Update();
					if (!PubMethod.isEmpty(netId3)) {
						update.set("netId", Long.valueOf(netId3));// 网络id被交寄的收派员的
																	// netId
					} else {
						update.set("netId", null);
					}
					update.set("compId", compId);// 公司compId
					update.set("expMemberId", Long.valueOf(toMemberId));// 执行人员的memberId
					update.set("expMemberSuccessFlag", (short) 1);// 成功
					logger.info("===3.包裹问题查询====" + query);
					WriteResult wre = mongoTemplate.updateFirst(query, update,
							ParParcelconnection.class);// 更改包裹信息监控表
					logger.info("===4.包裹问题查询,更新数量====" + wre.getN());
				} else {
					logger.info("根据包裹id 查询parParcelconnection为null");
				}
			}
			// 确认交寄发送推送
			// String toMemberPhone =
			// memberInfoMapperV4.findMemberPhoneByMemberId(Long.valueOf(toMemberId));
			String toMemberPhone = memberInfoVO.getMemberPhone();
			logger.info("包裹交寄完成======================= 没抱过, 开始发推送=======================收派员 toMemberId: "
					+ toMemberId + " 收派员手机号: toMemberPhone" + toMemberPhone);
			sendNoticeService.sendPushConsigCourier(Long.valueOf(toMemberId),
					toMemberPhone);
		} catch (Exception e) {
			e.printStackTrace();
			return "002";
		}
		return "001";
	}

	// 没有快递员,添加新的快递员
	@Override
	public String saveMemberInfoToNewMemberInfo(String parcelIds,
			String newMemberName, String compName, String newMemberPhone) {

		// 根据新添加的用户手机号,name 去member_info表中查询是否已经注册过
		try {
			Long toMemberId = memberInfoMapperV4
					.findMemberIdByNameAndPhone(newMemberPhone);
			if (!PubMethod.isEmpty(toMemberId)) {// 不为空,该用户已经注册过,并且交寄给他
				// String roleId =
				// memberInfoMapperV4.findRoleByMemberId(toMemberId);
				MemberInfoVO memberInfoVO = this.getValByMemberId(toMemberId
						+ "");
				String roleId = memberInfoVO.getRoleId();
				// 1.先去查询一下角色
				// 2.如果是店长或者是店员则收不到推送,并且包裹也不在被添加的这个店长或者店员已取包裹里面
				// 如果是收派员就按正常流程走
				// Long compId =
				// memberInfoMapperV4.findCompIdByMemberId(toMemberId);
				// Long netId = memberInfoMapperV4.findNetIdByCompId(compId);
				Long compId = Long.valueOf(memberInfoVO.getCompId());
				String netId = memberInfoVO.getNetId();
				String netId1 = netId;
				if (PubMethod.isEmpty(netId)) {
					netId1 = "";
				}
				if ("2".equals(roleId) || "3".equals(roleId)) {// 代收点
					Query query = null;
					Update update = null;
					String[] parcelIdArr = parcelIds.split(",");
					for (String parcelId : parcelIdArr) {
						query = new Query();
						update = new Update();
						query.addCriteria(Criteria.where("uid").is(
								Long.valueOf(parcelId)));
						ParParcelinfo parParcelinfo = mongoTemplate.findOne(
								query, ParParcelinfo.class);
						String mobilePhone = parParcelinfo.getMobilePhone();
						query.addCriteria(Criteria.where("mobilePhone").is(
								mobilePhone));
						update.set("actualTakeMember", Long.valueOf(toMemberId));// 被交寄的收派员
						update.set("parcelStatus", (short) 0);// 包裹状态 0: 待取件
						update.set("cpReceiptStatus", (short) 0);// 未付款,这个条件就给代收点限制在已取包裹中查询不出来
						if (!PubMethod.isEmpty(netId1)) {
							update.set("netId", Long.valueOf(netId1));// 网络id被交寄的收派员的
																		// netId
						} else {
							update.set("netId", null);
						}
						update.set("compId", compId);// 网络compid被交寄的收派员的 compId
						mongoTemplate.updateFirst(query, update,
								ParParcelinfo.class);// 修改包裹表
						query = new Query();
						query.addCriteria(Criteria.where("parId").is(
								Long.valueOf(parcelId)));
						ParParcelconnection parParcelconnection = mongoTemplate
								.findOne(query, ParParcelconnection.class);
						String mobilePhone2 = parParcelconnection
								.getMobilePhone();
						query.addCriteria(Criteria.where("mobilePhone").is(
								mobilePhone2));
						update.set("parId", Long.valueOf(parcelId));// 包裹id
						update = new Update();
						if (!PubMethod.isEmpty(netId1)) {
							update.set("netId", Long.valueOf(netId1));// 网络id被交寄的收派员的
																		// netId
						} else {
							update.set("netId", null);
						}
						update.set("compId", compId);// 公司compId
						update.set("expMemberId", Long.valueOf(toMemberId));// 执行人员的memberId
						update.set("expMemberSuccessFlag", (short) 1);// 成功
						mongoTemplate.updateFirst(query, update,
								ParParcelconnection.class);// 更改包裹信息监控表
					}
					return "003";
				} else {
					// 不是代收点,是收派员
					String result = this.consignationToMemberByParcelIds(
							parcelIds, String.valueOf(toMemberId));
					if ("001".equals(result)) {
						return "003";// 该人员已经注册过

					}
					return "003";// 已经交寄给该人员 , (说明该人员已经注册过)
				}
			} else {// 该用户未注册过
					// 先去查询一下该手机号是否注册过
				Query query1 = new Query();
				Update update1 = null;
				query1.addCriteria(Criteria.where("newMemberPhone").is(
						newMemberPhone));
				NewMemberInfo newMemberInfo2 = mongoTemplate.findOne(query1,
						NewMemberInfo.class);
				if (!PubMethod.isEmpty(newMemberInfo2)) {// 该快递员已经添加过,但还没有注册过好递快递员
					Long uid = newMemberInfo2.getUid();
					List<String> listPracelIds = newMemberInfo2
							.getListPracelIds();
					update1 = null;
					update1 = new Update();
					query1 = null;
					query1 = new Query();
					query1.addCriteria(Criteria.where("uid").is(uid));
					String[] parcelIdArr = parcelIds.split(",");
					List<String> asList = Arrays.asList(parcelIdArr);
					update1.set("listPracelIds", listPracelIds + "---" + asList);
					mongoTemplate.updateFirst(query1, update1,
							NewMemberInfo.class);
					for (String parcelId : parcelIdArr) {
						query1 = null;
						query1 = new Query();
						update1 = null;
						update1 = new Update();
						query1.addCriteria(Criteria.where("uid").is(
								Long.valueOf(parcelId)));
						ParParcelinfo parParcelinfo = mongoTemplate.findOne(
								query1, ParParcelinfo.class);
						String mobilePhone = parParcelinfo.getMobilePhone();
						query1.addCriteria(Criteria.where("mobilePhone").is(
								mobilePhone));
						// update.set("actualTakeMember", null);//被交寄的收派员
						update1.set("parcelStatus", (short) 1);// 包裹状态 1: 已取件
						// update.set("compId", compId);//公司compId
						// update.set("netId", netId);//网络id
						// update.set("netId", netId);//网络id
						mongoTemplate.updateFirst(query1, update1,
								ParParcelinfo.class);// 修改包裹表
						// /query1 = null;
						query1 = new Query();
						query1.addCriteria(Criteria.where("parId").is(
								Long.valueOf(parcelId)));
						ParParcelconnection parParcelconnection = mongoTemplate
								.findOne(query1, ParParcelconnection.class);
						String mobilePhone2 = parParcelconnection
								.getMobilePhone();
						query1.addCriteria(Criteria.where("mobilePhone").is(
								mobilePhone2));
						// update1 = null;
						update1 = new Update();
						// update.set("netId", netId);//网络id
						// update.set("compId", compId);//公司compId
						// update.set("expMemberId",
						// Long.valueOf(toMemberId));//执行人员的memberId
						update1.set("expMemberSuccessFlag", (short) 1);// 成功
						update1.set("createTime", new Date());// 创建时间
						mongoTemplate.updateFirst(query1, update1,
								ParParcelconnection.class);// 更改包裹信息监控表
					}
					return courierDownHttp;// 已经添加过还没有注册过
				}
				NewMemberInfo newMemberInfo = new NewMemberInfo();
				newMemberInfo.setUid(IdWorker.getIdWorker().nextId());
				newMemberInfo.setCompName(compName);
				newMemberInfo.setNewMemberName(newMemberName);
				newMemberInfo.setNewMemberPhone(newMemberPhone);
				Query query = null;
				Update update = null;
				String[] parcelIdArr = parcelIds.split(",");
				List<String> listPricelId = new ArrayList<String>();
				if (PubMethod.isEmpty(parcelIds)) {
					newMemberInfo.setListPracelIds(null);
					mongoTemplate.insert(newMemberInfo);// 把新的快递员添加到库中
					return "001";
				}
				for (String parcelId : parcelIdArr) {
					query = null;
					query = new Query();
					update = null;
					update = new Update();
					listPricelId.add(parcelId);
					query.addCriteria(Criteria.where("uid").is(
							Long.valueOf(parcelId)));
					ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
							ParParcelinfo.class);
					String mobilePhone = parParcelinfo.getMobilePhone();
					query.addCriteria(Criteria.where("mobilePhone").is(
							mobilePhone));
					// update.set("actualTakeMember", null);//被交寄的收派员
					update.set("parcelStatus", (short) 1);// 包裹状态 1: 已取件
					// update.set("compId", compId);//公司compId
					// update.set("netId", netId);//网络id
					// update.set("netId", netId);//网络id
					mongoTemplate.updateFirst(query, update,
							ParParcelinfo.class);// 修改包裹表
					query = null;
					query = new Query();
					query.addCriteria(Criteria.where("parId").is(
							Long.valueOf(parcelId)));
					ParParcelconnection parParcelconnection = mongoTemplate
							.findOne(query, ParParcelconnection.class);
					String mobilePhone2 = parParcelconnection.getMobilePhone();
					query.addCriteria(Criteria.where("mobilePhone").is(
							mobilePhone2));
					update = null;
					update = new Update();
					// update.set("netId", netId);//网络id
					// update.set("compId", compId);//公司compId
					// update.set("expMemberId",
					// Long.valueOf(toMemberId));//执行人员的memberId
					update.set("expMemberSuccessFlag", (short) 1);// 成功
					update.set("createTime", new Date());// 创建时间
					mongoTemplate.updateFirst(query, update,
							ParParcelconnection.class);// 更改包裹信息监控表
				}
				newMemberInfo.setListPracelIds(listPricelId);
				mongoTemplate.insert(newMemberInfo);// 把新的快递员添加到库中

				return courierDownHttp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "002";// 失败
		}
	}

	// 代收点叫快递,取消叫快递
	@Override
	public String calledCourierToMember(String task_Id, String memberId,
			String parcelIds, String flag) {
		logger.info("task_Id: " + task_Id + " ,memberId: " + memberId
				+ " ,parcelIds: " + parcelIds + " ,flag: " + flag);
		try {
			TakeTaskServiceImpl taskServiceImpl = new TakeTaskServiceImpl();
			// 判断是否是叫快递是否是取消 1:叫快递, 0:取消
			if ("0".equals(flag)) {// 取消叫快递,任务取消
				// 1.任务表中任务状态改为已取消
				// 2.任务记录表中任务状态改为已取消
				// 3.监控表不做修改
				Query query = new Query();
				query.addCriteria(Criteria.where("uid").is(
						Long.valueOf(task_Id)));
				// 取消叫快递加推送
				// 1.先去查询是否有快递员接单了,有就发推送,没有就不发推送
				ParTaskInfo taskInfo = mongoTemplate.findOne(query,
						ParTaskInfo.class);
				Long actorMemberId = taskInfo.getActorMemberId();// 执行人memberId(收派员)
				String mobilePhone = taskInfo.getMobilePhone();
				query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
				// String actorPhone = taskInfo.getActorPhone();//执行人的电话(收派员)
				if (!PubMethod.isEmpty(actorMemberId)) {
					Update update = new Update();
					update.set("actorMemberId", null);
					mongoTemplate.updateFirst(query, update, ParTaskInfo.class);

					return "004";// 代表不叫了,但是已经由快递员接单了,推送还没有给代收点推过来
				}
				Update update = new Update();
				update.set("taskStatus", (byte) 3);// 任务已取消
				update.set("modifyTime", new Date());// 最后修改时间
				mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("taskId").is(
						Long.valueOf(task_Id)));
				update = null;
				update = new Update();
				update.set("disposalType", (byte) 3);// 已取消
				update.set("modifiedTime", new Date());// 最后修改时间
				mongoTemplate.updateFirst(query, update,
						ParTaskDisposalRecord.class);// 修改任务信息表
				// 点击不叫了,把抢单中的记录给删掉
				taskRemindService.removeById(Long.valueOf(task_Id), null);
				// 把包裹表中的任务taskId改为null
				String[] parcelIdArr = parcelIds.split(",");
				for (String parcelId : parcelIdArr) {
					// 包裹中把任务taskId清空(取消任务清空包裹中的taskId)
					/**
					 * 代收点查询已取包裹的参数, --- 取消交寄更改的状态
					 * query.addCriteria(Criteria.where
					 * ("senderCompId").is(compId));//发货方(代收点的compId)
					 * query.addCriteria
					 * (Criteria.where("actualTakeMember").is(Long
					 * .valueOf(memberId)));//代收点的memberId
					 * query.addCriteria(Criteria
					 * .where("parcelEndMark").is("0"));//未结束
					 * 
					 * 
					 * //代收点查询已取包裹列表
					 * query.addCriteria(Criteria.where("senderCompId"
					 * ).is(compId));//发货方(代收点的compId)
					 * query.addCriteria(Criteria
					 * .where("actualTakeMember").is(Long
					 * .valueOf(memberId)));//代收点的memberId
					 * query.addCriteria(Criteria
					 * .where("parcelEndMark").is("0"));//未结束
					 * query.addCriteria(Criteria
					 * .where("cpReceiptStatus").is((short)1));//1:已付款
					 */
					query = null;
					query = new Query();
					query.addCriteria(Criteria.where("uid").is(
							Long.valueOf(parcelId)));
					ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
							ParParcelinfo.class);
					String mobilePhone1 = parParcelinfo.getMobilePhone();
					query.addCriteria(Criteria.where("mobilePhone").is(
							mobilePhone1));
					update = null;
					update = new Update();
					update.set("actualTakeMember", Long.valueOf(memberId));// 取消叫快递还需要把代收点的memberId弄过来
					update.set("takeTaskId", null);
					update.set("parcelStatus", (short) 1);// 包裹状态 1:已取件
					mongoTemplate.updateFirst(query, update,
							ParParcelinfo.class);//
				}
				return "003";// 003代表任务取消
			} else if ("1".equals(flag)) {// 叫快递

				// 1.生成一个任务
				// 2.生成一个任务监控
				// 3.生成一个任务信息表
				// 给这些包裹把任务taskId添加进去
				ParTaskInfo parTaskInfo = new ParTaskInfo();// 任务表
				long taskId = IdWorker.getIdWorker().nextId();// 任务id
				parTaskInfo.setUid(taskId);// 任务主键(任务id)-------------片键
				// String memberPhone =
				// memberInfoMapperV4.findMemberPhoneByMemberId(Long.valueOf(memberId));
				MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
				String memberPhone = memberInfoVO.getMemberPhone();
				parTaskInfo.setMobilePhone(memberPhone);// ---------做片键用
				parTaskInfo.setTaskType((byte) 0);// 0:取件
				// Long compId =
				// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
				// parTaskInfo.setCoopCompId(compId);//公司compId
				// Long netId = memberInfoMapperV4.findNetIdByCompId(compId);
				// parTaskInfo.setCoopNetId(netId);//网络id
				parTaskInfo.setTaskStatus((byte) 0);// 任务状态0: 待处理
				parTaskInfo.setTaskIsEnd((byte) 0);// 任务结束 0:未结束
				// parTaskInfo.setTaskEndTime(new Date());//任务结束时间
				// parTaskInfo.setActorMemberId(Long.valueOf(memberId));//执行任务的收派员memberId
				// String memberName =
				// memberInfoMapperV4.findMemberNameByMemberId(Long.valueOf(memberId));
				// String memberAddress =
				// memberInfoMapperV4.findMemberAddressByMemberId(Long.valueOf(memberId));
				// Long contactCompId =
				// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));//联系人公司compId
				String memberName = memberInfoVO.getMemberName();
				String memberAddress = memberInfoVO.getCompAddress();
				Long contactCompId = Long.valueOf(memberInfoVO.getCompId());
				// parTaskInfo.setActorPhone(memberPhone);//执行人的电话
				parTaskInfo.setContactName(memberName);// 联系人name代收点的
				parTaskInfo.setContactMobile(memberPhone);// 联系人手机代收点的---------片键
				parTaskInfo.setContactTel(memberPhone);// 联系人电话代收点的
				parTaskInfo.setContactAddress(memberAddress);// 联系人详细地址代收点的
				parTaskInfo.setContactCompId(contactCompId);// 联系人公司compId
				parTaskInfo.setCreateUserId(Long.valueOf(memberId));// 代收点memberId
				parTaskInfo.setTaskFlag((byte) 1);// 抢单
				parTaskInfo.setPayStatus((short) 0);// 默认值0
				Date date = new Date();
				parTaskInfo.setCreateTime(date);// 创建时间
				taskServiceImpl.createJob(date, taskId);

				parTaskInfo.setCreateUserId(Long.valueOf(memberId));// 创建人memberId代收点的
				parTaskInfo.setTaskFlag((byte) 1);// 任务标志 1:抢单
				int length = parcelIds.split(",").length;
				parTaskInfo.setParEstimateCount((byte) length);// 包裹数量
				// parTaskInfo.setModifyTime(new Date());//最后修改时间
				parTaskInfo.setCustomerId(Long.valueOf(memberId));// 发件人memberId代收点的
				parTaskInfo.setTaskSource((byte) 4);// 叫快递takeSource 4:好递个人端
				mongoTemplate.insert(parTaskInfo);// 叫快递插入已完成任务记录
				ParTaskDisposalRecord parTaskDisposalRecord = new ParTaskDisposalRecord();// 任务记录表
				parTaskDisposalRecord.setUid(IdWorker.getIdWorker().nextId());// 主键id---------片键
				parTaskDisposalRecord.setDisposalType((byte) 0);// 0:待处理
				parTaskDisposalRecord.setTaskId(taskId);// 任务id
				// parTaskDisposalRecord.setMemberId(Long.valueOf(memberId));
				// parTaskDisposalRecord.setCompId(compId);//公司compId
				parTaskDisposalRecord.setDisposalObject((byte) 0);// 派送员
				parTaskDisposalRecord.setShowFlag((byte) 1);// 1显示
				parTaskDisposalRecord.setTaskErrorFlag((byte) 0);// 异常标识 0:正常
				parTaskDisposalRecord.setCreateTime(new Date());// 创建时间
				parTaskDisposalRecord.setDisposalDesc("叫快递--抢单");
				// parTaskDisposalRecord.setModifiedTime(new Date());//最后修改时间
				mongoTemplate.insert(parTaskDisposalRecord);// 叫快递插入任务信息表中一条已经完成的任务信息
				// ParParcelconnection parParcelconnection = null;
				String[] parcelIdArr = parcelIds.split(",");
				Query query = null;
				Update update = null;
				for (String parcelId : parcelIdArr) {
					/*
					 * parParcelconnection = new ParParcelconnection();
					 * parParcelconnection
					 * .setUid(IdWorker.getIdWorker().nextId());//主键id
					 * parParcelconnection
					 * .setParId(Long.valueOf(parcelId));//包裹id
					 * parParcelconnection.setCreateTime(new Date());//创建时间
					 * mongoTemplate.insert(parParcelconnection);//包裹收派过程信息监控表
					 */
					// 包裹中把任务taskId添加进去(关联起来)

					/**
					 * 查询已取包裹的条件 代收点的
					 * query.addCriteria(Criteria.where("senderCompId"
					 * ).is(compId));//发货方(代收点的compId)
					 * query.addCriteria(Criteria.
					 * where("actualTakeMember").is(Long
					 * .valueOf(memberId)));//代收点的memberId
					 * query.addCriteria(Criteria
					 * .where("parcelEndMark").is("0"));//未结束
					 * //query.addCriteria
					 * (Criteria.where("cpReceiptStatus").is((short)1));//1:已付款
					 */
					//
					// query = null;
					query = new Query();
					query.addCriteria(Criteria.where("uid")
							.is(Long.valueOf(parcelId)).and("cpTakeTaskId")
							.ne(null));
					ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
							ParParcelinfo.class);
					logger.info("根据包裹idparcelId: " + parcelId
							+ " ,parParcelinfo: " + parParcelinfo);
					String mobilePhone = parParcelinfo.getMobilePhone();
					query.addCriteria(Criteria.where("mobilePhone").is(
							mobilePhone));
					// update = null;
					update = new Update();
					update.set("takeTaskId", Long.valueOf(taskId));
					update.set("parcelStatus", (short) 0);// 包裹状态
															// 0:待取件(叫快递是为了让收派员抢到任务,但是不能在他的已取包裹中查到)
					update.set("parcelEndMark", "0");// 包裹结束状态 0:未结束
					mongoTemplate.updateFirst(query, update,
							ParParcelinfo.class);//

				}
				// 查询包裹下的netId,现在传过来的包裹id都是同一个公司下面的
				// query = null;
				query = new Query();
				query.addCriteria(Criteria.where("uid")
						.is(Long.valueOf(parcelIdArr[0])).and("cpTakeTaskId")
						.ne(null));
				ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
						ParParcelinfo.class);
				Long netId = parParcelinfo.getNetId();
				String netId1 = netId + "";
				if (PubMethod.isEmpty(netId)) {
					netId1 = "";
				}
				// 开始发送推送(叫快递才发推送)
				// 1.查询附近20公里的收派员(不分站点) 2016-06-04日将 5公司改为50公里
				// 查询出代收点的经纬度
				Map<String, Object> map = memberInfoMapperV4
						.findLongOrLatiTudeByMemberId(Long.valueOf(memberId));
				String longitude = map.get("longitude") + "";
				String latitude = map.get("latitude") + "";

				logger.info("开始叫快递==================longitude: " + longitude
						+ "========latitude: " + latitude
						+ "===================");
				Map<String, String> callMap = new HashMap<String, String>();
				callMap.put("lng", longitude);
				callMap.put("lat", latitude);
				callMap.put("taskId", taskId + "");
				callMap.put("assignNetId", netId1);
				callMap.put("currMemberId", memberId);
				callMap.put("contactMobile", memberPhone);
				callMap.put("contactAddress", memberAddress);
				callMap.put("sortFlag", "1");
				callMap.put("howFast", "20");
				callMap.put("roleType", "2");
				callMap.put("createTime", new Date().getTime() + "");
				logger.info("开始叫快递==================callMap: " + callMap
						+ "===========================");
				String url = constPool.getOpencall() + "callcourier/cc";
				logger.info("叫快递url====: " + url + "==================");
				noticeHttpClient.Post(opencall + "callcourier/cc", callMap);
				logger.info("叫快递结束............");

				/*
				 * List<Map<String, Object>> queryNearMemberForWechat =
				 * courierService.queryNearMemberForWechat(longitude, latitude,
				 * 1, 5, netId, "2"); //List<Map<String,Object>> byTudeList =
				 * this.queryNearMemberByTude(memberId);
				 * 
				 * for (Map<String, Object> mapMember :
				 * queryNearMemberForWechat) { String member_Id =
				 * mapMember.get("memberId")+""; String memberMobile =
				 * mapMember.get("memberMobile")+"";
				 * if(!member_Id.equals(memberId)){// //保存到提醒中
				 * noticeHttpClient.saveMsg(memberPhone, memberAddress,
				 * Long.valueOf(member_Id), memberMobile, taskId);
				 * //代收点叫快递给附近收派员发推送
				 * sendNoticeService.sendPushCalledCourier(Long
				 * .valueOf(member_Id), memberMobile); }
				 * 
				 * }
				 */
				// 需要把任务id更新到包裹表中
				return taskId + "";
			}
			return "001";// 叫快递成功
		} catch (Exception e) {
			e.printStackTrace();
			return "002";// 叫快递,取消叫快递失败
		}
	}

	// 收派员确认取件-抢单
	public String memberConfirmOrder(String taskId, String memberId,
			String phone) {

		// 1.先去查询任务是否已经取消
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
		query.addCriteria(Criteria.where("taskStatus").is((byte) 3));// 任务是否取消
		ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
				ParTaskInfo.class);

		// 修改taskRemind中的taskStatus 为已取件
		query = null;
		query = new Query();
		query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));
		Update update1 = new Update();
		update1.set("taskStatus", (short) 1);// 已分配
        update1.set("isRemove", (short)1);
        mongoTemplate.updateFirst(query, update1, TaskRemind.class);

		if (!PubMethod.isEmpty(parTaskInfo)) {
			// throw new ServiceException("003", "任务已经被取消!!!");
			return "003";// 代表任务已经取消
		}
		// 2.查询包裹是否领取
		query = null;
		query = new Query();
		query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
		ParTaskInfo parTaskInfo1 = mongoTemplate.findOne(query,
				ParTaskInfo.class);
		String mobilePhone = parTaskInfo1.getMobilePhone();
		query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
		Long actorMemberId = parTaskInfo1.getActorMemberId();
		if (PubMethod.isEmpty(actorMemberId)) {// 代表没有领取
			// 把该收派员更新到任务表中
			// 3.任务挂到该收派员下
			/**
			 * 代收点查询交寄任务列表
			 * query.addCriteria(Criteria.where("taskStatus").is((byte)1));//已分配
			 * query.addCriteria(Criteria.where("taskType").is((byte)0));//取件
			 * //根据memberId查询属于哪个compId Long compId =
			 * memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
			 * query
			 * .addCriteria(Criteria.where("contactCompId").is(compId));//公司id
			 * long型
			 * query.addCriteria(Criteria.where("taskIsEnd").is((byte)0));//未结束
			 */
			// Long compId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
			Update update = new Update();
			update.set("actorMemberId", Long.valueOf(memberId));// 执行人的memberId
			update.set("actorPhone", phone);// 执行人的手机号
			update.set("modifyTime", new Date());// 最后修改时间
			update.set("taskStatus", (byte) 1);// 1:已分配
			// update.set("customerId", Long.valueOf(memberId));//执行人memberId
			update.set("taskType", (byte) 0);// 0:取件
			// update.set("contactCompId",
			// compId);//联系人公司compId(备注,叫快递时就给数据插入进去了)
			update.set("taskIsEnd", (byte) 0);// 未结束
			// Long coopCompId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
			MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
			Long coopCompId = Long.valueOf(memberInfoVO.getCompId());
			update.set("coopCompId", coopCompId);// 确认收派员的compId(任务受理方)
			// Long compNetId =
			// memberInfoMapperV4.findNetIdByCompId(coopCompId);
			Long compNetId = Long.valueOf(memberInfoVO.getNetId());
			update.set("coopNetId", compNetId);// 确认收派员的netId(任务受理方)
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));
			update = null;
			update = new Update();
			update.set("disposalType", (byte) 1);// 已分配
			update.set("memberId", Long.valueOf(memberId));// 执行人的memberId
			// Long compId =
			// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));//根据memberId
			// 查询公司id
			update.set("compId", coopCompId);// 公司compId
			update.set("disposalObject", (byte) 0);// 派送员
			update.set("modifiedTime", new Date());// 最后更新时间
			update.set("showFlag", (byte) 1);// 显示
			update.set("taskErrorFlag", (byte) 1);// 异常 1:正常
			mongoTemplate.updateFirst(query, update,
					ParTaskDisposalRecord.class);
			// 同时还要把 抢单人的memberId更新到包裹中
			query = new Query();
			query.addCriteria(Criteria.where("takeTaskId").is(
					Long.valueOf(taskId)));
			List<ParParcelinfo> listParcelS = mongoTemplate.find(query,
					ParParcelinfo.class);
			for (ParParcelinfo parParcelinfo : listParcelS) {
				Long uid = parParcelinfo.getUid();
				String mobilePhone1 = parParcelinfo.getMobilePhone();
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("uid").is(uid));
				query.addCriteria(Criteria.where("mobilePhone")
						.is(mobilePhone1));
				update = null;
				update = new Update();
				update.set("actualTakeMember", Long.valueOf(memberId));
				mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
			}
			// 收派员确认同意取件给代收点发送推送
			String mobile = parTaskInfo1.getContactMobile();// 代收点的手机号
			Long customerId = parTaskInfo1.getCustomerId();// 代收点的memberId
			sendNoticeService.sendPushNoticeCourier(customerId, mobile);
			// throw new ServiceException("001", "抢单成功!!!");
			// 把其他人的抢单中的该记录全部干掉
			taskRemindService.removeById(Long.valueOf(taskId),
					Long.valueOf(memberId));
			return "001";// 代表抢单成功
		} else {// 代表收派员任务已经领取
				// throw new ServiceException("004", "该订单已经被抢!!!");
			return "004";
		}
		// return null;
	}

	/**
	 * 微信端发的取件任务，app端在提配中进行 “同意”操作调用
	 */
	public String memberConfirmOrderWeiXin(String taskId, String memberId,
			String phone, Short flag) {// flag 用来标注是抢单还是正常单

		/*
		 * //1.先去查询任务是否已经取消 Query query = new Query();
		 * query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
		 * query.addCriteria(Criteria.where("taskStatus").is((byte)3));//任务是否取消
		 * ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
		 * ParTaskInfo.class); if(!PubMethod.isEmpty(parTaskInfo)){ return
		 * "003";//代表任务已经取消 }
		 */
		// 2.修改取件任务的执行人
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
		ParTaskInfo parTaskInfo1 = mongoTemplate.findOne(query,
				ParTaskInfo.class);
		Long actorMemberId = parTaskInfo1.getActorMemberId();
		String actorPhone = parTaskInfo1.getActorPhone();
		Byte taskFlag = parTaskInfo1.getTaskFlag();

		if ("0".equals(taskFlag.toString())) {
			phone = actorPhone;
			memberId = actorMemberId == null ? memberId : actorMemberId
					.toString();
		}

		if (!PubMethod.isEmpty(memberId)) {//
			// 把该收派员更新到任务表中
			// 3.任务挂到该收派员下
			Long compId = memberInfoMapperV4.findCompIdByMemberId(Long
					.valueOf(memberId));
			Update update = new Update();
			update.set("actorMemberId", Long.valueOf(memberId));// 执行人的memberId
			update.set("actorPhone", phone);// 执行人的手机号
			update.set("modifyTime", new Date());// 最后修改时间
			update.set("taskStatus", (byte) 1);// 1:已分配
			Long netId = memberInfoMapperV4.findNetIdByComp(compId);
			update.set("coopNetId", netId);

			update.set("taskType", (byte) 0);// 0:取件
			update.set("contactCompId", compId);// 执行人公司compId
			update.set("taskIsEnd", (byte) 0);// 未结束
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			// 抢单更新包裹信息
			if (1 == flag) {
				Query queryParcel = Query.query(Criteria.where("takeTaskId")
						.is(Long.valueOf(taskId)));
				Update updateParcel = new Update();
				updateParcel.set("compId", compId);
				updateParcel.set("actualTakeMember", Long.valueOf(memberId));
				updateParcel.set("netId", netId);
				mongoTemplate.updateFirst(queryParcel, updateParcel,
						ParParcelinfo.class);
				takeTaskService.removeTaskIgomoCache(compId,
						Long.valueOf(memberId));// 清除微信端查询发件记录的缓存
				logger.info("抢单更新包裹信息compId:" + compId + "actualTakeMember:"
						+ memberId + "netId:" + netId);
			}

			query = new Query();
			query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));
			update = new Update();
			update.set("disposalType", (byte) 1);// 已分配
			update.set("memberId", Long.valueOf(memberId));// 执行人的memberId
			update.set("compId", compId);// 公司compId
			update.set("disposalObject", (byte) 0);// 派送员
			update.set("modifiedTime", new Date());// 最后更新时间
			update.set("showFlag", (byte) 1);// 显示
			update.set("taskErrorFlag", (byte) 1);// 异常 1:正常
			mongoTemplate.updateFirst(query, update,
					ParTaskDisposalRecord.class);

			return "001";// 收派员在提醒中确认取件成功
		} else {
			return "002";// 收派员为空

		}
	}

	/**
	 * 微信端发的取件任务，app端在提配中进行 “取消”操作调用
	 */
	public String calledCourierToMemberWeiXin(String taskId, String flag) {// flag
																			// 1拒绝
																			// 2
																			// 取消
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));

		// ParTaskInfo taskInfo = mongoTemplate.findOne(query,
		// ParTaskInfo.class);

		Update update = new Update();
		if ("1".equals(flag)) {
			update.set("taskStatus", (byte) 4);// 快递员拒绝接单
		} else {
			update.set("taskStatus", (byte) 3);// 任务已取消
		}
        update.set("isRemove", 1);
        update.set("modifyTime", new Date());// 最后修改时间
		mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
		query = new Query();
		query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));
		update = new Update();
		update.set("disposalType", (byte) 3);// 已取消
		update.set("modifiedTime", new Date());// 最后修改时间
		mongoTemplate.updateFirst(query, update, ParTaskDisposalRecord.class);// 修改任务信息表
		// 把包裹表中的任务taskId改为null

		query.addCriteria(Criteria.where("takeTaskId").is(Long.valueOf(taskId)));
		update = new Update();
		update.set("parcelEndMark", (byte) 1);//

		mongoTemplate.updateMulti(query, update, ParParcelinfo.class);// 修改包裹表信息

		return "003";// 001代表任务取消
	}

	// 代收点确认交寄或者取消交寄
	@Override
	public String collectPointsConfirmSend(String taskId, String memberId,
			String flag) {

		try {
			if ("0".equals(flag)) {// 取消交寄
				// 1.任务已取消
				Query query = new Query();
				query.addCriteria(Criteria.where("uid")
						.is(Long.valueOf(taskId)));
				ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
						ParTaskInfo.class);
				String payNum = parTaskInfo.getPayNum();
				Short payStatus = parTaskInfo.getPayStatus();
				if (!PubMethod.isEmpty(payStatus)) {
					if (10 == payStatus || 20 == payStatus || 21 == payStatus
							|| !PubMethod.isEmpty(payNum)) {
						return "005";
					}
				}
				String mobilePhone = parTaskInfo.getMobilePhone();
				query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
				Long actorMemberId = parTaskInfo.getActorMemberId();// 收派员的memberId
				String actorPhone = parTaskInfo.getActorPhone();// 收派员的phone
				Update update = new Update();
				update.set("taskStatus", (byte) 3);// 任务已取消
				update.set("taskIsEnd", (byte) 1);// 包裹结束标志
				update.set("taskEndTime", new Date());// 最后结束时间
				mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("taskId").is(
						Long.valueOf(taskId)));
				update = null;
				update = new Update();
				update.set("disposalType", (byte) 3);// 已取消
				update.set("modifiedTime", new Date());// 最后修改时间
				mongoTemplate.updateFirst(query, update,
						ParTaskDisposalRecord.class);// 修改任务信息表
				// 把包裹表中的任务taskId改为null,包裹表中的任务taskId清空
				// 先去查询一下这个单子是否是微信支付的
				/*
				 * query = null; query = new Query();
				 * query.addCriteria(Criteria.
				 * where("cpTakeTaskId").is(Long.valueOf
				 * (Long.valueOf(taskId))));
				 * query.addCriteria(Criteria.where("cpReceiptStatus"
				 * ).is((short)1)); ParParcelinfo parParcelinfo1 =
				 * mongoTemplate.findOne(query, ParParcelinfo.class);
				 * List<ParParcelinfo> listParcel = null;
				 * if(!PubMethod.isEmpty(parParcelinfo1)){ //代收点微信支付的 listParcel
				 * = mongoTemplate.find(query, ParParcelinfo.class); }else{
				 */
				// 不是代收点微信支付的
				// 根据任务taskId查询出下面的包裹
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("takeTaskId").is(
						Long.valueOf(taskId)));
				query.addCriteria(Criteria.where("cpReceiptStatus").is(
						(short) 1));
				List<ParParcelinfo> listParcel = mongoTemplate.find(query,
						ParParcelinfo.class);
				/* } */
				// 取消交寄给快递员发推送
				sendNoticeService.sendCancelCancelCourier(actorMemberId,
						actorPhone);
				for (ParParcelinfo parParcelinfo : listParcel) {
					// 包裹中把任务taskId清空(取消任务清空包裹中的taskId)
					/**
					 * 代收点查询已取包裹列表
					 * query.addCriteria(Criteria.where("senderCompId"
					 * ).is(compId));//发货方(代收点的compId)
					 * query.addCriteria(Criteria.
					 * where("actualTakeMember").is(Long
					 * .valueOf(memberId)));//代收点的memberId
					 * query.addCriteria(Criteria
					 * .where("parcelEndMark").is("0"));//未结束
					 * //query.addCriteria
					 * (Criteria.where("cpReceiptStatus").is((short)1));//1:已付款
					 */
					/**
					 * 自取件 parParcelinfo.setParcelStatus((short)1);//29.包裹状态
					 * 0：待取件;1：已取件;10：待派件;11：已派件;12:派件异常 ParcelStatus ; 1：已取件
					 * parParcelinfo.setCompId(compId);//6.公司id CompId
					 * parParcelinfo.setParcelEndMark("0");//22.包裹结束标志
					 * ParcelEndMark 0:未结束
					 * parParcelinfo.setActualTakeMember(Long
					 * .valueOf(memberId));//23.取件人id ActualTakeMember memberId
					 */
					Long parcelId = parParcelinfo.getUid();
					String mobilePhone1 = parParcelinfo.getMobilePhone();
					query = null;
					query = new Query();
					query.addCriteria(Criteria.where("uid").is(parcelId));
					query.addCriteria(Criteria.where("mobilePhone").is(
							mobilePhone1));
					update = null;
					update = new Update();
					/**
					 * update.set("actualTakeMember", Long.valueOf(memberId));
					 * update.set("takeTaskId", null);
					 * update.set("parcelStatus", (short)0);//包裹状态 0:待取件
					 */
					update.set("actualTakeMember", Long.valueOf(memberId));// 取消交寄还需要把代收点的memberId弄过来
					update.set("takeTaskId", null);
					update.set("parcelStatus", (short) 1);// 1:已取件
					update.set("cpTakeTaskId", (short) 0);// 0
					mongoTemplate.updateFirst(query, update,
							ParParcelinfo.class);//
				}
				return "001";// 003代表取消交寄
			} else if ("1".equals(flag)) {// 确认交寄
				// 点击确认交寄之前先判断快递员是否已经接单
				Query query = new Query();
				// 1.任务已完成
				query.addCriteria(Criteria.where("uid")
						.is(Long.valueOf(taskId)));
				ParTaskInfo taskInfo = mongoTemplate.findOne(query,
						ParTaskInfo.class);
				Long actorMemberId2 = taskInfo.getActorMemberId();
				if (PubMethod.isEmpty(actorMemberId2)) {
					throw new Exception("亲,点错了,该单还没有快递员接!!!!");
					// return "111";
				}
				query = null;
				query = new Query();
				/**
				 * 代收代呢查询待交寄订单列表
				 * query.addCriteria(Criteria.where("taskStatus").is
				 * ((byte)1));//已分配
				 * query.addCriteria(Criteria.where("taskType").is
				 * ((byte)0));//取件 //根据memberId查询属于哪个compId Long compId =
				 * memberInfoMapperV4
				 * .findCompIdByMemberId(Long.valueOf(memberId));
				 * query.addCriteria
				 * (Criteria.where("contactCompId").is(compId));//公司id long型
				 * query
				 * .addCriteria(Criteria.where("taskIsEnd").is((byte)0));//未结束
				 */
				// 1.任务已完成
				query.addCriteria(Criteria.where("uid")
						.is(Long.valueOf(taskId)));
				String mobilePhone = taskInfo.getMobilePhone();
				query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
				Update update = new Update();
				update.set("taskStatus", (byte) 2);// 任务已完成
				update.set("taskIsEnd", (byte) 1);// 任务结束
				update.set("taskEndTime", new Date());// 任务结束时间
				update.set("modifyTime", new Date());// 最后修改时间
				mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("taskId").is(
						Long.valueOf(taskId)));
				update = null;
				update = new Update();
				update.set("disposalType", (byte) 2);// 任务已完成
				update.set("modifiedTime", new Date());// 最后修改时间
				mongoTemplate.updateFirst(query, update,
						ParTaskDisposalRecord.class);// 修改任务信息表
				// 2.包裹表
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("uid")
						.is(Long.valueOf(taskId)));// 查询任务表中的执行人(收派员的memberId)
				ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
						ParTaskInfo.class);
				Long actorMemberId = parTaskInfo.getActorMemberId();// 收派员的memberId
				query = null;
				query = new Query();
				// 先去查询一下这个单子是否是微信支付的
				/*
				 * query = null; query = new Query();
				 * query.addCriteria(Criteria.
				 * where("cpTakeTaskId").is(Long.valueOf
				 * (Long.valueOf(taskId))));
				 * query.addCriteria(Criteria.where("cpReceiptStatus"
				 * ).is((short)1)); ParParcelinfo parParcelinfo1 =
				 * mongoTemplate.findOne(query, ParParcelinfo.class);
				 * //查询出该任务下的所有包裹 List<ParParcelinfo> listParcel = null;
				 * if(!PubMethod.isEmpty(parParcelinfo1)){ //说明是代收点微信支付过来的包裹
				 * listParcel = mongoTemplate.find(query, ParParcelinfo.class);
				 * }else {
				 */
				query = null;
				query = new Query();
				// 不是代收点微信支付过来的包裹,那就是正常流程走的包裹
				query.addCriteria(Criteria.where("takeTaskId").is(
						Long.valueOf(taskId)));
				query.addCriteria(Criteria.where("cpReceiptStatus").is(
						(short) 1));
				List<ParParcelinfo> listParcel = mongoTemplate.find(query,
						ParParcelinfo.class);
				// }

				// 代收点的取件compId
				// Long compId =
				// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
				// 收派员的取件compId
				// Long memberCompId =
				// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(actorMemberId));
				// Long netId =
				// memberInfoMapperV4.findNetIdByCompId(memberCompId);

				MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
				Long compId = Long.valueOf(memberInfoVO.getCompId());
				memberInfoVO = this.getValByMemberId(actorMemberId + "");
				Long memberCompId = Long.valueOf(memberInfoVO.getCompId());
				String netId2 = memberInfoVO.getNetId();
				String netId3 = netId2;
				if (PubMethod.isEmpty(netId2)) {
					netId3 = "";
				}
				// 更新该任务下的所有包裹发货方是代收点,发货方客户是代收点, 实际取件人是收派员
				for (ParParcelinfo parParcelinfo : listParcel) {
					/**
					 * 收派员查询已取包裹列表 Query query = new Query();
					 * query.addCriteria(Criteria
					 * .where("actualTakeMember").is(Long
					 * .valueOf(memberId)));//取件人id
					 * query.addCriteria(Criteria.where
					 * ("parcelStatus").is((short)1));//已取件
					 * query.addCriteria(Criteria
					 * .where("parcelEndMark").is("0"));//未结束
					 */
					Long parcelId = parParcelinfo.getUid();// 包裹id
					String mobilePhone1 = parParcelinfo.getMobilePhone();
					query = null;
					query = new Query();
					query.addCriteria(Criteria.where("uid").is(parcelId));
					query.addCriteria(Criteria.where("mobilePhone").is(
							mobilePhone1));
					update = null;
					update = new Update();
					update.set("senderCompId", compId);// 代收点的compId
					update.set("senderUserId", Long.valueOf(memberId));// 代收点的memberId
					update.set("actualTakeMember", actorMemberId);// 实际取件人的memberId
					update.set("parcelStatus", (short) 1);// 1: 已取件
					update.set("parcelEndMark", "0");// 包裹结束标志 0:未结束
					update.set("createTime", new Date());// 给收派员时间(为了代收点取件记录查询)
					update.set("compId", memberCompId);// 给收派员compId
					if (!PubMethod.isEmpty(netId3)) {
						update.set("netId", Long.valueOf(netId3));// 给收派员的netId
					} else {
						update.set("netId", null);// 给收派员的netId
					}
					mongoTemplate.updateFirst(query, update,
							ParParcelinfo.class);

					// 3.包裹监控表
					query = null;
					query = new Query();
					query.addCriteria(Criteria.where("parId").is(parcelId));
					ParParcelconnection parParcelconnection = mongoTemplate
							.findOne(query, ParParcelconnection.class);
					if (!PubMethod.isEmpty(parParcelconnection)) {
						String mobilePhone2 = parParcelconnection
								.getMobilePhone();
						query.addCriteria(Criteria.where("mobilePhone")
								.is(mobilePhone2).and("uid")
								.is(parParcelconnection.getUid()));

						update = null;
						update = new Update();
						if (!PubMethod.isEmpty(netId3)) {
							update.set("netId", Long.valueOf(netId3));// 给收派员的netId
						} else {
							update.set("netId", null);// 给收派员的netId
						}
						update.set("compId", memberCompId);// 收派员的compId
						update.set("expMemberId", actorMemberId);// 收派员的memberId
						update.set("taskId", Long.valueOf(taskId));// 任务taskId
						update.set("cosignFlag", (short) 1);// 1:取件
						update.set("expMemberSuccessFlag", (short) 1);// 1:成功
						mongoTemplate.updateFirst(query, update,
								ParParcelconnection.class);
					} else {
						logger.info("更新ParParcelconnection出错=================================== ");

					}
				}

				return "001";// 001表示确认交寄
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "002";// 异常
		}
		return "001";// 正常返回
	}

	// 取件记录查询
	public List<Map<String, Object>> queryTakeRecordList(String memberId,
			String date, String phone) {
		logger.info("进入取件记录查询 queryTakeRecordList ======= 取件记录查询=====memberId: "
				+ memberId + " ====== date: " + date + "===phone: " + phone);
		Query query = new Query();
		// mogon 格式话日期
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		if (PubMethod.isEmpty(phone)) {
			logger.info("手机号传入的空=======================phone: " + phone);
			try {
				String d1 = date + " 00:00:00";
				String d2 = date + " 23:59:59";
				start = sim.parse(d1);
				end = sim1.parse(d2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// String roleId =
		// memberInfoMapperV4.findRoleByMemberId(Long.valueOf(memberId));
		// Long compId =
		// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		logger.info("根据memberId:" + memberId + "查出来的memberinfo:"
				+ JSON.toJSONString(memberInfoVO == null ? "" : memberInfoVO));
		String roleId = memberInfoVO.getRoleId();
		Long compId = Long.valueOf(memberInfoVO.getCompId());
		if ("0".equals(roleId) || "1".equals(roleId) || "-1".equals(roleId)) {// 收派员
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("actualTakeMember").is(
					Long.valueOf(memberId)));
			query.addCriteria(Criteria.where("parcelEndMark").is("1"));// 结束
			query.addCriteria(new Criteria("takeTaskId").ne(null));
			// 增加手机号查询条件2016年6月1日 14:35:05
			if (!PubMethod.isEmpty(phone)) {
				query.addCriteria(Criteria.where("mobilePhone").regex(phone));
				Calendar ca = Calendar.getInstance();
				Date now = new Date();
				ca.add(Calendar.MONTH, -3);
				Date before = ca.getTime();
				System.out.println("取件记录查询3个月之前的今天=====收派员的=====memberId: "
						+ memberId + "========:三月之前的今天:" + before + "==now: "
						+ now);
				query.addCriteria(Criteria.where("pickupTime").lte(now)
						.gte(before));// <=now >=before
			} else {
				logger.info("手机号传入的是空===收派员==========phone: " + phone);
				query.addCriteria(Criteria.where("pickupTime").gte(start)
						.lte(end));
			}
			// 根据创建时间进行排序
			query.with(new Sort(Direction.DESC, "pickupTime"));// 根据活动开始时间排序

			logger.info("收派员取件记录查询====================================: "
					+ query);
		} else if ("2".equals(roleId) || "3".equals(roleId)) {// 代收点
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("senderCompId").is(compId));// 代收点compId
			query.addCriteria(Criteria.where("parcelStatus").is((short) 1));// 已取件
			query.addCriteria(new Criteria("cpTakeTaskId").ne(null));
			// 增加手机号查询条件2016年6月1日 14:35:05
			if (!PubMethod.isEmpty(phone)) {
				query.addCriteria(Criteria.where("mobilePhone").regex(phone));
				Calendar ca = Calendar.getInstance();
				Date now = new Date();
				ca.add(Calendar.MONTH, -3);
				Date before = ca.getTime();
				System.out.println(before);
				System.out.println("取件记录查询3个月之前的今天=====代收点的=====memberId: "
						+ memberId + "========: 时间:" + before);
				query.addCriteria(Criteria.where("createTime").lte(now)
						.gte(before));// <=now >=before
			} else {
				logger.info("手机号传入的是空===代收点==========phone: " + phone);
				query.addCriteria(Criteria.where("createTime").gte(start)
						.lte(end));
			}
			// 根据创建时间进行排序
			query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
			logger.info("代收点取件记录查询====================================: "
					+ query);
		}
		// .skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
		// .limit(pageSize);// 从skip开始,取多少条记录
		// count = mongoTemplate.count(query, ParParcelinfo.class);
		List<ParParcelinfo> listParcel = mongoTemplate.find(query,
				ParParcelinfo.class);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		logger.info("取件记录查询出来的包裹==========listParcel: " + listParcel);
		for (ParParcelinfo parParcelinfo : listParcel) {
			Date createTime = parParcelinfo.getCreateTime();// 时间
			map = new HashMap<String, Object>();
			Long uid = parParcelinfo.getUid();
			query = null;
			query = new Query();
			// query.addCriteria(Criteria.where("uid").is(uid));
			logger.info("从地址中取到的 ================== uid:" + uid);
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(uid))
					.and("sendMobile").ne(null));
			ParParceladdress parParceladdress = mongoTemplate.findOne(query,
					ParParceladdress.class);
			logger.info("取件记录中查询地址parParceladdress============: "
					+ parParceladdress + "===== parParceladdress.getUid() :"
					+ parParceladdress.getUid());
			BigDecimal chareWeightFortransit = parParcelinfo
					.getChareWeightFortransit();// 计费重量(销售)
			Short freightPaymentMethod = parParcelinfo
					.getFreightPaymentMethod();// 应收运费支付方式
			BigDecimal freight = parParcelinfo.getFreight();// 应收运费
			String expWaybillNum = parParcelinfo.getExpWaybillNum();// 运单号
			BigDecimal pricePremium = parParcelinfo.getPricePremium();// 保价费
			BigDecimal codAmount = parParcelinfo.getCodAmount();// 代收货款金额
			BigDecimal packingCharges = parParcelinfo.getPackingCharges();// 包装费
			BigDecimal insureAmount = parParcelinfo.getInsureAmount();// 保价金额
			Long actualTakeMember = parParcelinfo.getActualTakeMember();// 任务id
			Date pickupTime = parParcelinfo.getPickupTime();
			String parcelRemark = parParcelinfo.getParcelRemark();// 包裹备注
			// 查询快递网络
			Long netId = parParcelinfo.getNetId();
			String netName = memberInfoMapperV4.findNetNameByNetId(netId);
			String sendAddress = parParceladdress.getSendAddress();// 发件人地址
			String sendMobile = parParceladdress.getSendMobile();// 发件人电话
			String sendName = parParceladdress.getSendName();// 发件人name
			String addresseeAddress = parParceladdress.getAddresseeAddress();// 收件人地址
			String addresseeMobile = parParceladdress.getAddresseeMobile();// 收件人手机号
			String addresseeName = parParceladdress.getAddresseeName();// 收件人姓名

			map.put("parcelRemark", parcelRemark);
			map.put("addresseeAddress", addresseeAddress);
			map.put("addresseeMobile", addresseeMobile);
			map.put("addresseeName", addresseeName);
			map.put("chareWeightFortransit", chareWeightFortransit);
			map.put("freightPaymentMethod", freightPaymentMethod);
			map.put("freight", freight);
			map.put("expWaybillNum", expWaybillNum);
			map.put("pricePremium", pricePremium);
			map.put("codAmount", codAmount);
			map.put("packingCharges", packingCharges);
			map.put("insureAmount", insureAmount);
			map.put("netName", netName);
			map.put("sendAddress", sendAddress);
			map.put("sendMobile", sendMobile);
			map.put("sendName", sendName);
			if ("2".equals(roleId) || "3".equals(roleId)) {// 代收点角色显示createTime时间
				map.put("createTime", createTime);
				logger.info("显示代收点的时间: =====\\\\\\\\\\\\\\\\\\=====: "
						+ createTime);
			} else {
				map.put("createTime", pickupTime);
				logger.info("显示收派员的时间: ==== ||||||||| ======: " + pickupTime);
			}
			map.put("actualTakeMember", actualTakeMember);
			listMap.add(map);
			map = null;
		}
		// Page page = PageUtil.buildPage(currentPage, pageSize);
		// page.setItems(listMap);
		// page.setTotal(count.intValue());
		return listMap;
	}

	// 取件记录详情(已不用,不用理会)
	public Map<String, Object> queryRecordDetailByTaskId(String taskId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
		ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
				ParTaskInfo.class);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(parTaskInfo)) {
			String contactName = parTaskInfo.getContactName();// 发件姓名
			String contactMobile = parTaskInfo.getContactMobile();// 发件手机
			String contactAddress = parTaskInfo.getContactAddress();// 发件地址
			String appointDesc = parTaskInfo.getAppointDesc();// 描述
			map.put("contactName", contactName);// 发件姓名
			map.put("contactMobile", contactMobile);// 发件手机
			map.put("contactAddress", contactAddress);// 发件地址
			map.put("appointDesc", appointDesc);// 发件备注
			map.put("taskId", parTaskInfo.getUid());// 任务id
			map.put("taskSource", parTaskInfo.getTaskSource());// 来源
			map.put("taskStatus", parTaskInfo.getTaskStatus());// 任务状态
			map.put("parEstimateCount", parTaskInfo.getParEstimateCount());// 包裹数量
			return map;
		} else {
			map.put("contactName", "");// 发件姓名
			map.put("contactMobile", "");// 发件手机
			map.put("contactAddress", "");// 发件地址
			map.put("appointDesc", "");// 发件备注
			map.put("taskId", "");// 任务id
			map.put("taskSource", "");// 来源
			map.put("taskStatus", "");// 任务状态
			map.put("parEstimateCount", "");// 包裹数量
			return map;
		}
	}

	// 查询取件包裹列表
	public List<Map<String, Object>> queryTakePackageList(String taskId,
			String memberId) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("actualTakeMember").is(
				Long.valueOf(memberId)));// 收派员的memberId
		query.addCriteria(Criteria.where("takeTaskId").is(Long.valueOf(taskId)));// 任务taskId
		query.addCriteria(Criteria.where("parcelStatus").is((short) 0));// 待取件
		List<ParParcelinfo> listParcel = mongoTemplate.find(query,
				ParParcelinfo.class);
		for (ParParcelinfo parParcel : listParcel) {
			query = null;
			query = new Query();
			Long uid = parParcel.getUid();
			query.addCriteria(Criteria.where("uid").is(uid).and("sendMobile")
					.ne(null));
			ParParceladdress parParceladdress = mongoTemplate.findOne(query,
					ParParceladdress.class);
			map = new HashMap<String, Object>();
			map.put("parcelId", uid);// 包裹的parcelId
			String addresseeAddress = parParceladdress.getAddresseeAddress();// 收件人地址
			map.put("addresseeAddress", addresseeAddress);
			String addresseeMobile = parParceladdress.getAddresseeMobile();// 收件人电话
			map.put("addresseeMobile", addresseeMobile);
			String addresseeName = parParceladdress.getAddresseeName();// 收件人姓名
			map.put("addresseeName", addresseeName);
			String expWaybillNum = parParcel.getExpWaybillNum();// 收件人单号
			map.put("expWaybillNum", expWaybillNum);
			BigDecimal weightFortransit = parParcel.getChareWeightFortransit();
			map.put("weightFortransit", weightFortransit);// 包裹重量
			listMap.add(map);
			map = null;
		}
		return listMap;
	}

	// 删除包裹
	public String deleteParcelByParcelId(String taskId, String parcelId) {
		try {
			ParParcelinfo findOne = this.mongoTemplate.findOne(
					Query.query(Criteria.where("uid").is(
							Long.parseLong(parcelId))), ParParcelinfo.class);
			Query query = new Query();
			// 删除包裹
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(parcelId)));
			query.addCriteria(Criteria.where("mobilePhone").is(
					findOne.getMobilePhone()));
			mongoTemplate.remove(query, ParParcelinfo.class);
			// 删除地址
			Query query2 = new Query();
			query2.addCriteria(Criteria.where("uid").is(Long.valueOf(parcelId)));
			query2.addCriteria(Criteria.where("addresseeMobile").is(
					findOne.getMobilePhone()));
			mongoTemplate.remove(query2, ParParceladdress.class);
			// 修改任务中的包裹数量
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
			Update update = new Update();
			ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
					ParTaskInfo.class);
			Byte parEstimateCount = parTaskInfo.getParEstimateCount();
			String mobilePhone = parTaskInfo.getMobilePhone();
			query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
			update.set("parEstimateCount", (byte) (parEstimateCount - 1));
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			// 删除包裹信息监控表中的数据
			// query = null;
			query = new Query();
			query.addCriteria(Criteria.where("parId")
					.is(Long.valueOf(parcelId)));
			ParParcelconnection parParcelconnection = mongoTemplate.findOne(
					query, ParParcelconnection.class);
			query = new Query();
			Long uid = parParcelconnection.getUid();
			query.addCriteria(new Criteria("uid").is(uid).and("cosignFlag")
					.is((short) 1));
			String mobilePhone2 = parParcelconnection.getMobilePhone();
			query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone2));
			mongoTemplate.remove(query, ParParcelconnection.class);
			return "001";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "002";
		}
	}

	// 包裹删除完之后 删除任务
	@Override
	public String deleteTaskByTaskId(String taskId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
			ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
					ParTaskInfo.class);
			String mobilePhone = parTaskInfo.getMobilePhone();
			query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
			// 删除任务
			mongoTemplate.remove(query, ParTaskInfo.class);
			// 删除任务记录
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));
			mongoTemplate.remove(query, ParTaskDisposalRecord.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "002";
		}
		return "001";
	}

	// 收派员查询取件订单数量
	@Override
	public Long queryTakePackCount(Long memberId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));// memberId
		query.addCriteria(Criteria.where("taskIsEnd").is((byte) 0));// 包裹未结束
		query.addCriteria(Criteria.where("taskType").is((byte) 0));// 取件
		query.addCriteria(Criteria.where("taskStatus").is((byte) 1));// 已分配
		// 根据创建时间进行排序
		query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
		Long count = mongoTemplate.count(query, ParTaskInfo.class);
		return count;
	}

	// 代收点查询已取包裹数量
	@Override
	public Long queryTakePackCountDSD(Long memberId, Long compId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("actualTakeMember").is(
				Long.valueOf(memberId)));// 代收点的memberId
		query.addCriteria(Criteria.where("senderCompId").is(compId));// 发货方(代收点的compId)
		query.addCriteria(Criteria.where("parcelEndMark").is("0"));// 未结束
		// query.addCriteria(Criteria.where("cpReceiptStatus").is((short)1));//代收点自添加包裹，代收点的收款状态;
		// 0 未付 1 已付
		Long count = mongoTemplate.count(query, ParParcelinfo.class);
		return count;
	}

	public String sendPush(String memberId, String phone) {
		sendNoticeService.sendPushCalledCourier(Long.valueOf(memberId), phone);
		System.out.println("推过去了.......");
		return null;
	}

	// 微信调取接口修改微信支付状态
	@Override
	public String updateWeChatPayStatus(String payNum, Short parStatus) {
		logger.info("进入取件里面的 updateWeChatPayStatus 更新付款状态 payStatus: "
				+ parStatus + " payNum: " + payNum);
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("payNum").is(payNum));
			ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
					ParTaskInfo.class);
			Long uid = parTaskInfo.getUid();
			String mobilePhone = parTaskInfo.getMobilePhone();
			query = new Query();
			query.addCriteria(Criteria.where("uid").is(uid));
			query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
			Update update = new Update();
			update.set("payStatus", parStatus);
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			logger.info("进入取件里面的 updateWeChatPayStatus  更新完成...................uid: "
					+ uid + " mobilePhone: " + mobilePhone);
			return "001";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "002";
		}
	}

	@Override
	public ParTaskInfo getTaskIdByOrderNum(String payNum) {

		Query query = new Query();
		query.addCriteria(Criteria.where("payNum").is(payNum));
		ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
				ParTaskInfo.class);
		return parTaskInfo;
	}

	// 代收点查询交寄订单的数量
	@Override
	public Long queryConsigOrderNum(Long memberId, Long auditComp) {
		Query query = new Query();
		query.addCriteria(Criteria.where("taskType").is((byte) 0));// 取件
		query.addCriteria(Criteria.where("taskStatus").is((byte) 1));// 已分配
		// query.addCriteria(Criteria.where("customerId").is(memberId));
		// 根据memberId查询属于哪个compId
		Long compId = memberInfoMapperV4.findCompIdByMemberId(Long
				.valueOf(memberId));
		query.addCriteria(Criteria.where("contactCompId").is(compId));// 公司id
																		// long型
		query.addCriteria(Criteria.where("taskIsEnd").is((byte) 0));// 未结束

		// 根据创建时间进行排序
		query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
		Long count = mongoTemplate.count(query, ParTaskInfo.class);
		return count;
	}

	// 收派员查询已取包裹的数量
	@Override
	public Long queryHasParcelNum(Long memberId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("actualTakeMember").is(
				Long.valueOf(memberId)));// 取件人id
		query.addCriteria(Criteria.where("parcelStatus").is((short) 1));// 已取件
		query.addCriteria(Criteria.where("parcelEndMark").is("0"));// 未结束

		// 根据创建时间进行排序
		// query.with(new Sort(Direction.DESC,"createTime"));//根据活动开始时间排序
		Long count = mongoTemplate.count(query, ParParcelinfo.class);
		return count;
	}

	@Override
	public String findPayStatusByPayNum(String payNum) {
		Query query = new Query();
		query.addCriteria(Criteria.where("payNum").is(payNum));
		ParTaskInfo info = mongoTemplate.findOne(query, ParTaskInfo.class);
		return String.valueOf(info.getPayStatus());
	}

	@Override
	public BaseDao getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}

	public MemberInfoVO getValByMemberId(String memberId) {

		// 快递员基本信息放入缓存中--2016年5月30日19:55:09 by zhaohu
		MemberInfoVO memberInfoVO = this.redisService.get(
				"memberInfo-byzhaohu-cache", memberId, MemberInfoVO.class);
		if (PubMethod.isEmpty(memberInfoVO)) {
			memberInfoVO = this.basEmployeeAuditMapper
					.queryMemberInfoByZhaohu(memberId);
			this.redisService.put("memberInfo-byzhaohu-cache", memberId,
					memberInfoVO);
		}
		return memberInfoVO;
	}

	public String getValByNetId(String netId1) {
		logger.info("netID=" + netId1 + "!!!!!!!!!!!!!!!!!!!!!");
		if (PubMethod.isEmpty(netId1) || "0".endsWith(netId1)
				|| "null".equals(netId1)) {
			return "";
		} else {
			// 快递员基本信息放入缓存中--2016年5月30日19:55:09 by zhaohu
			List<Map<String, Object>> listMap = this.redisService.get(
					"netNameByNetId-redis", "getNetName", List.class);
			logger.info("listMap====================: " + listMap);
			Map<String, Object> map = new HashMap<String, Object>();
			if (PubMethod.isEmpty(listMap)) {
				logger.info("===================== redis 为空=============");
				List<Map<String, Object>> list = this.basEmployeeAuditMapper
						.queryAllNetNameAndNetId();
				for (Map<String, Object> map2 : list) {
					String netId = map2.get("netId") + "";
					String netName = map2.get("netName") + "";
					map.put(netId, netName);
				}
				this.redisService.put("netNameByNetId-redis", "getNetName",
						list);
			} else {
				logger.info("===================== redis bu 为空=============");
				for (Map<String, Object> map2 : listMap) {
					String netId = map2.get("netId") + "";
					String netName = map2.get("netName") + "";
					map.put(netId, netName);
				}
			}

			String netName = map.get(netId1) + "";
			if (PubMethod.isEmpty(netName)) {
				netName = "";
			}
			// logger.info("getValByNetId==================: "+map);
			return netName;
		}
	}

	@Override
	public String recivesaomaCreate(String tradeNum, Double tradeTotalAmount) {
		logger.info("添加自取件调 用    RecivesaomaCreate==================: tradeNum ="
				+ tradeNum + "tradeTotalAmount =" + tradeTotalAmount);
		// 插入客户信息
		Map map1 = new HashMap();
		map1.put("tradeNum", tradeNum);
		map1.put("tradeTotalAmount", tradeTotalAmount);

		String url = constPool.getOpenApiUrl() + "customerNewInfo/saomaCreate";
		String result = Post(url, map1);
		logger.info("添加自取件调 用    RecivesaomaCreate   结果==================: "
				+ result);
		return result;
	}

	/**
	 * @MethodName: net.okdi.apiV4.service.impl.ReceivePackageServiceImpl.
	 *              insertBasCustomerInfo
	 * @Description: TODO(插入客户信息基础数据---大数据用)
	 * @param @param userName
	 * @param @param mobilePhone
	 * @param @param address
	 * @return void 返回值类型
	 * @throws
	 * @date 2016-7-23
	 * @auth zhaohu
	 */
	@Override
	public void insertBasCustomerInfo(String userName, String mobilePhone,String address) {
		logger.info("insertBasCustomerInfo--进入保存客户基础数据接口，参数为：userName="
				+ userName + ",mobilePhone=" + mobilePhone + ",address="
				+ address + "");
		// 1.先查询有木有,存在的话就更新地址,姓名;反之 插入数据
		boolean exists = this.mongoTemplate.exists(
				Query.query(Criteria.where("mobilePhone").is(mobilePhone)),
				BasCustomerInfo.class);
		if (exists) {
			BasCustomerInfo customerInfoOld = this.mongoTemplate.findOne(
					Query.query(Criteria.where("mobilePhone").is(mobilePhone)),
					BasCustomerInfo.class);
			List<String> addressList = customerInfoOld.getAddressList();
			addressList.add(address);
			// a.更新数据
			Update update = new Update();
			update.set("userName", userName);
			update.set("mobilePhone", mobilePhone);
			update.set("recentAddress", address);
			update.set("addressList", addressList);
			update.set("updateTime", new Date());
			this.mongoTemplate.updateFirst(
					Query.query(Criteria.where("mobilePhone").is(mobilePhone)),
					update, BasCustomerInfo.class);
			logger.info("insertBasCustomerInfo--手机号" + mobilePhone
					+ "已存在，更新地址等相关数据！！！");
		} else {
			// b.插入数据
			// 2.用手机号去查询okdi_user，存在的话 保存memberId，反之不保存memberId
			Long memberId = isRegistReturnMemberId(mobilePhone);
			BasCustomerInfo customerInfo = new BasCustomerInfo();
			customerInfo.setUid(IdWorker.getIdWorker().nextId());
			customerInfo.setUserName(userName);
			customerInfo.setMobilePhone(mobilePhone);
			customerInfo.setMemberId(memberId);
			customerInfo.setRecentAddress(address);
			List<String> addressList = new ArrayList<String>();
			addressList.add(address);
			customerInfo.setAddressList(addressList);
			customerInfo.setCreateTime(new Date());
			customerInfo.setUpdateTime(new Date());
			this.mongoTemplate.insert(customerInfo);
			logger.info("insertBasCustomerInfo--手机号" + mobilePhone+ "不存在，插入客户信息相关数据！！！");
		}
	}

	/**
	 * 判断手机号是否是好递用户，返回memberId
	 */
	public Long isRegistReturnMemberId(String mobile) {
		Long memberId = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		String methodName = "/service/validate";// registered
		String url = constPool.getUcenterUrl() + methodName;
		String result = Post(url, map);
		if (!PubMethod.isEmpty(result)) {
			JSONObject jsonObj = JSONObject.parseObject(result);
			if (!PubMethod.isEmpty(jsonObj)) {
				memberId = jsonObj.getLong("memberId");
			}
		}
		return memberId;
	}

	// 添加取件详情中,还可以继续添加包裹(从取件任务过来的) 收派员
	public String addParcelToTaskId(String taskId, String memberId,
			int packageNum, String sign, String packageJson) {
		try {
			logger.info("======新添加取件,,收派员=================== 收派员从取件任务过来的确认取件taskId: "
					+ taskId
					+ " ,memberId: "
					+ memberId
					+ " ,packageJson:  "
					+ packageJson + ".....packageNum: " + packageNum);
			logger.info("===taskId: " + taskId + " ==memberId: " + memberId
					+ "===sign: " + sign + "== ......");
			Query query = new Query();
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
			query.addCriteria(Criteria.where("actorMemberId").is(
					Long.valueOf(memberId)));
			ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
					ParTaskInfo.class);
			String mobilePhone = parTaskInfo.getMobilePhone();//
			query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
			Update update = new Update();
			update.set("taskStatus", (byte) 2);// 任务已完成
			update.set("taskIsEnd", (byte) 1);// 任务结束1:结束
			update.set("taskEndTime", new Date());// 任务结束时间
			/*
			 * if(10 == flag){//10:现金支付 update.set("payStatus", flag);//收现金, 10
			 * 现金支付 }
			 */
			update.set("parEstimateCount", packageNum);// 包裹预估数
			logger.info("=======================开始更新任务完成  ......");
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			query = new Query();
			query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));// 任务taskId
			query.addCriteria(Criteria.where("disposalType").is((byte) 1));// 处理类型1:已分配
			update = new Update();
			update.set("disposalType", (byte) 2);// 已完成
			update.set("modifiedTime", new Date());// 最后修改时间
			logger.info("========================= 开始更新任务记录完成 ......");
			mongoTemplate.updateFirst(query, update,
					ParTaskDisposalRecord.class);
			query = new Query();

			/*
			 * if("21".equals(sign)){//微信支付确认取件 21, 用cpTakeTaskId查询
			 * query.addCriteria
			 * (Criteria.where("cpTakeTaskId").is(Long.valueOf(taskId)));
			 * logger.info("======代收点微信支付 根据 cpTakeTaskId 字段查询出包裹  ......");
			 * }else{//不是微信支付的
			 * query.addCriteria(Criteria.where("takeTaskId").is(
			 * Long.valueOf(taskId)));
			 * logger.info("======不是微信支付 正常流程走的查询包裹   ......"); }
			 */
			query.addCriteria(Criteria.where("takeTaskId").is(
					Long.valueOf(taskId)));

			List<ParParcelinfo> listParcel = mongoTemplate.find(query,
					ParParcelinfo.class);
			long receiptId = IdWorker.getIdWorker().nextId();// 收据id
			for (ParParcelinfo parParcelinfo : listParcel) {
				Long uid = parParcelinfo.getUid();
				String mobilePhone1 = parParcelinfo.getMobilePhone();
				query = new Query();
				query.addCriteria(Criteria.where("uid").is(uid));
				query.addCriteria(Criteria.where("mobilePhone")
						.is(mobilePhone1));
				update = new Update();
				update.set("pickupTime", new Date());// 取件时间
				update.set("parcelStatus", (short) 1);// 1:已取件
				update.set("receiptId", receiptId);// 1:收据id
				update.set("cpReceiptStatus", (short) 1);// 1:付款(代收点已付和收派员已付)
				logger.info("====== 开始更新包裹中的数据 ========  ......");
				mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
			}
			// ////////////////////////////////////////////////////////////////////////////////////////
			if (!PubMethod.isEmpty(packageJson)) {
				logger.info("取件订单中该快递员新添加包裹了.memberId: " + memberId
						+ ".的包裹..packageJson: " + packageJson);
				JSONObject jsonObject = JSONObject.parseObject(packageJson);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				int size = jsonArray.size();
				AccSrecpayvouchers accSrecpayvouchers = new AccSrecpayvouchers();// 收付凭证
				// 一个任务的包裹对应一个收据id
				// Long receiptId = IdWorker.getIdWorker().nextId();
				accSrecpayvouchers.setUid(receiptId);
				String memberName = memberInfoMapperV4
						.findMemberNameByMemberId(Long.valueOf(memberId));
				Long customerCompId = memberInfoMapperV4
						.findCompIdByMemberId(Long.valueOf(memberId));
				Long netId1 = memberInfoMapperV4
						.findNetIdByCompId(customerCompId);
				logger.info("根据memberId: " + memberId + " 查询出的memberName: "
						+ memberName + " 公司id: " + customerCompId + " 网络id: "
						+ netId1);
				// 把寄件人的信息存入到客户管理里面
				// logger.info(" ...添加新包裹时,把寄件人的信息添加到包裹里面.......customerCompId: "+customerCompId+"..寄件人姓名: "+sendName+"..寄件人姓名: "+sendPhone+".寄件人memberId: "+memberId+"======================== ");
				// customerInfoNewService.insertCustomerV4(customerCompId,
				// sendName, null, null, sendPhone, null, null, null, null,
				// null, null, null, Long.valueOf(memberId));

				for (int i = 0; i < size; i++) {
					// map = new HashMap<String,Object>();
					// Long id = IdWorker.getIdWorker().nextId();//主键id
					JSONObject object = jsonArray.getJSONObject(i);
					String receiveProvince = object
							.getString("receiveProvince");// 收件人城市
					String receivePhone = object.getString("receivePhone");// 收件人手机
					String receiveName = object.getString("receiveName");// 收件人姓名
					String expWaybillNum = object.getString("expWaybillNum");// 包裹运单号
					String weightTransit = "0";
					if (!PubMethod
							.isEmpty(object.getString("weightFortransit"))) {
						weightTransit = object.getString("weightFortransit");
					}
					;// 包裹重量
					ParParcelinfo parParcelinfo = new ParParcelinfo();// 包裹表
					long parcelId = IdWorker.getIdWorker().nextId();// 包裹主键id
					parParcelinfo.setUid(parcelId);// 1.包裹id 生成的19位id id
													// 与地址表中的主键id相同
					parParcelinfo.setMobilePhone(mobilePhone);// 片键
																// ------------------
																// 用的发件人的手机号
					parParcelinfo.setExpWaybillNum(expWaybillNum);// 5.运单号
																	// ExpWaybillNum
					parParcelinfo.setChareWeightFortransit(BigDecimal
							.valueOf(Double.valueOf(weightTransit)));// 包裹重量(采购)
					// parParcelinfo.setTakeTaskId(Long.valueOf(taskId));//2.取件任务id
					// TakeTaskId
					// parParcelinfo.setSenderUserId(Long.valueOf(customerId));//4.发货方客户ID
					// SenderUserId
					// 包裹属于哪个公司只有有快递员接单时才有
					// Long compId =
					// memberInfoMapperV4.findCompIdByMemberId(Long.valueOf(memberId));
					parParcelinfo.setCompId(customerCompId);// 6.公司id CompId
					parParcelinfo.setNetId(Long.valueOf(netId1));// 7.网络id NetId
					parParcelinfo.setCpTakeTaskId(receiptId);// 代收点微信支付字段,此处没用到
																// 给空
					parParcelinfo.setCpReceiptStatus((short) 1);// 1:已付, 代收点收现金
					// 任务id 更新到包裹表中
					parParcelinfo.setTakeTaskId(Long.valueOf(taskId));
					// 8.包裹重量 ChareWeightForsender
					parParcelinfo.setFreight(null);// 9.包裹应收运费 Freight
					parParcelinfo.setGoodsPaymentMethod((short) 0);// 10.支付方式
																	// GoodsPaymentMethod
																	// 0:不代收,
																	// 1:代收
					parParcelinfo.setCodAmount(BigDecimal.valueOf((double) 0));// 11.代收货款金额
																				// CodAmount
					// 12.保价金额 InsureAmount
					// 13.保价费 PricePremium
					// 14.包装费 PackingCharges
					parParcelinfo.setFreightPaymentMethod((short) 0);// 15.运费支付方式
																		// FreightPaymentMethod
																		// 0
					parParcelinfo.setActualCodAmount(BigDecimal
							.valueOf((double) 0));// )16.代收货款实际收到的货款金额
													// ActualCodAmount 0
					Long senderCompId = memberInfoMapperV4
							.findCompIdByMemberId(Long.valueOf(memberId));

					// 17.产品描述 GoodsDesc null
					// 18.包裹备注 ParcelRemark null
					// 19.服务产品ID ServiceId null
					parParcelinfo.setSignMember(null);// 20.签收人 SignMember 收件人
					parParcelinfo.setCreateUserId(Long.valueOf(memberId));// 21.创建人id
																			// CreateUserId
																			// memberId
					parParcelinfo.setParcelEndMark("0");// 22.包裹结束标志
														// ParcelEndMark 0:未结束
					parParcelinfo.setActualTakeMember(Long.valueOf(memberId));// 23.取件人id
																				// ActualTakeMember
																				// memberId
					parParcelinfo.setPickupTime(new Date());// 24.取件时间
															// PickupTime new
															// Date();
					// 25.派件人id ActualSendMember null
					parParcelinfo.setReceiptId(receiptId);// 26.收据id ReceiptId
					// 27.签收结果 0：未签收/拒签 1：签收 SignResult 0 null
					parParcelinfo.setTackingStatus((short) 0);// 28.包裹当前状态
																// 0:在途,未签收
																// 1:已签收
																// TackingStatus
																// 0 0
					parParcelinfo.setParcelStatus((short) 1);// 29.包裹状态
																// 0：待取件;1：已取件;10：待派件;11：已派件;12:派件异常
																// ParcelStatus
																// ; 1：已取件
					// 30.disposal_desc null
					// 31.如果异常原因不为空则添加异常时间 ExceptionTime null
					Date date = new Date();
					parParcelinfo.setCreateTime(date);// 32.设置包裹创建时间 CreateTime
														// new Date();
					mongoTemplate.insert(parParcelinfo);// 插入包裹
					logger.info("插入包裹表中信息完成....................");
					// =============================================================================================
					ParParceladdress parParceladdress = new ParParceladdress();// 包裹地址表
					parParceladdress.setUid(parcelId); // 1.包裹地址id 生成的19位id id
														// 与包裹表中的主键id相同
					// parParceladdress.setSendCustomerId(Long.valueOf(customerId));//2.发件客户ID
					// SendCustomerId
					parParceladdress.setAddresseeAddressId(null);// 3.收件客户ID
																	// AddresseeCustomerId
					parParceladdress.setSendCasUserId(null);// 4.发件人CASID
															// SendCasUserId
					parParceladdress.setAddresseeCasUserId(null);// 5.收件人CASID
																	// AddresseeCasUserId
					parParceladdress.setAddresseeName(receiveName);// 6.收件人姓名
																	// AddresseeName
					parParceladdress.setAddresseeMobile(receivePhone);// 7.收件人手机号码
																		// AddresseeMobile
																		// ----
																		// 片键 --
																		// 收件人手机号
					parParceladdress.setAddresseeAddressId(null);// 8.收件人乡镇id
																	// AddresseeAddressId
					parParceladdress.setAddresseeAddress(receiveProvince);// 9.收件人详细地址
																			// AddresseeAddress
					parParceladdress.setSendName(parTaskInfo.getContactName());// 10.发件人姓名
																				// SendName
					parParceladdress.setSendMobile(parTaskInfo
							.getContactMobile());// 11.发件人手机 SendMobile
					parParceladdress.setSendAddressId(null);// 12.发件人乡镇id
															// SendAddressId
					parParceladdress.setSendAddress(parTaskInfo
							.getContactAddress());// 13.发件人详细地址 SendAddress
					parParceladdress.setSendLongitude(null);// 14.发件人地址经度
															// SendLongitude
					parParceladdress.setSendLatitude(null);// 15.发件人地址纬度
															// SendLatitude
					parParceladdress.setAddresseeLongitude(null);// 16.收件人地址经度
																	// AddresseeLongitude
					parParceladdress.setAddresseeLatitude(null);// 17.收件人地址纬度
																// AddresseeLatitude

					mongoTemplate.insert(parParceladdress);// 插入地址表
					logger.info("插入地址表中信息完成.............................");
					ParParcelconnection parParcelconnection = new ParParcelconnection();// 包裹收派过程信息监控表
					parParcelconnection.setUid(IdWorker.getIdWorker().nextId());
					parParcelconnection.setMobilePhone(parTaskInfo
							.getContactMobile());// 片键---包裹监控包---也用发件人手机号
													// -----------------
					parParcelconnection.setCompId(customerCompId);// 公司compId
					parParcelconnection.setNetId(Long.valueOf(netId1));// 网络netId
					parParcelconnection.setCosignFlag((short) 1);// 取件
					parParcelconnection.setCreateTime(date);// 创建时间
					parParcelconnection.setExpMemberId(Long.valueOf(memberId));// memberId
					parParcelconnection.setExpMemberSuccessFlag(null);// 取派件成功标志
					parParcelconnection.setParId(parcelId);// 包裹id
					// parParcelconnection.setTaskId(taskId);//任务id

					mongoTemplate.insert(parParcelconnection);// 插入信息监控表
					logger.info("插入信息监控表中信息完成............................................");
				}
				/*
				 * //Long compId =
				 * memberInfoMapperV4.findCompIdByMemberId(Long.valueOf
				 * (memberId)); try {
				 * this.setAccSrecpayvouchers(accSrecpayvouchers, null,
				 * freightMoney, Integer.valueOf(packageNum),
				 * Long.valueOf(memberId), memberName, customerCompId);
				 * mongoTemplate.insert(accSrecpayvouchers);//插入收据表 } catch
				 * (Exception e) { // TODO Auto-generated catch block
				 * //e.printStackTrace();
				 * logger.info("插入收据表失败,失败原因: "+e.getStackTrace()); }
				 */
			}
			// ////////////////////////////////////////////////////////////////////////////////////////
			logger.info("收派员从取件订单中.....信息插入完成...........................................");
			return "001";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "002";
		}
	}

	// 收派员和代收点自取件 此方法与takeSendPackageByMember方法一致
	public String takeReceivePackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, String packageJson) {
		logger.info("=======走的取件新方法    takeReceivePackageByMember   server＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		String roleId = memberInfoVO.getRoleId();
		logger.info("根据memberId: " + memberId + " 查询出这个人的信息" + memberInfoVO);
		// 现金支付
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// Long netId = memberInfoMapperV4.findNetIdByCompId(compId);

			JSONObject jsonObject = JSONObject.parseObject(packageJson);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int size = jsonArray.size();
			AccSrecpayvouchers accSrecpayvouchers = new AccSrecpayvouchers();// 收付凭证
			// 一个任务的包裹对应一个收据id
			Long receiptId = IdWorker.getIdWorker().nextId();
			accSrecpayvouchers.setUid(receiptId);
			String memberName = memberInfoVO.getMemberName();
			String compId = memberInfoVO.getCompId();
			// 把寄件人的信息存入到客户管理里面
			String netId2 = memberInfoVO.getNetId();
			String netId1 = netId2;
			if (PubMethod.isEmpty(netId2)) {
				logger.info("takeReceivePackageByMember==== 空 ==============netId1: "
						+ netId2);
				netId1 = "";
			}
			// Long netId1 = memberInfoMapperV4.findNetIdByCompId(compId);
			for (int i = 0; i < size; i++) {
				// map = new HashMap<String,Object>();
				// Long id = IdWorker.getIdWorker().nextId();//主键id
				JSONObject object = jsonArray.getJSONObject(i);
				String receiveProvince = object.getString("receiveProvince");// 收件人城市
				String receivePhone = object.getString("receivePhone");// 收件人手机
				String receiveName = object.getString("receiveName");// 收件人姓名
				String expWaybillNum = object.getString("expWaybillNum");// 包裹运单号
				String weightTransit = "0";
				if (!PubMethod.isEmpty(object.getString("weightFortransit"))) {
					weightTransit = object.getString("weightFortransit");
				}
				;// 包裹重量
					// parParcelinfo = null;
				ParParcelinfo parParcelinfo = new ParParcelinfo();// 包裹表
				long parcelId = IdWorker.getIdWorker().nextId();// 包裹主键id
				logger.info("新添加包裹中的主键parcelId====: " + parcelId
						+ " Long 转换之后的id====:" + Long.valueOf(parcelId));
				parParcelinfo.setUid(parcelId);// 1.包裹id 生成的19位id id
				parParcelinfo.setMobilePhone(sendPhone);// 片键 ------------------
				parParcelinfo.setExpWaybillNum(expWaybillNum);// 5.运单号
				// parParcelinfo.setTakeTaskId(Long.valueOf(taskId));//2.取件任务id
				// parParcelinfo.setSenderUserId(Long.valueOf(customerId));//4.发货方客户ID
				// SenderUserId
				// 包裹属于哪个公司只有有快递员接单时才有
				if ("0".equals(roleId) || "1".equals(roleId)
						|| "-1".equals(roleId)) {// 收派员
					parParcelinfo.setCompId(Long.valueOf(compId));// 6.公司id
					if (!PubMethod.isEmpty(netId1)) {
						parParcelinfo.setNetId(Long.valueOf(netId1));// 7.网络id
					} else {
						parParcelinfo.setNetId(null);// 7.网络id NetId
					}
					parParcelinfo.setTakeTaskId(11111111l);// 主 代收点微信支付字段,此处没用到
					parParcelinfo.setCpTakeTaskId(22222222l);// 副
				} else { // 代收点
					if (!PubMethod.isEmpty(netId)) {
						// netId = "0";
						parParcelinfo.setNetId(Long.valueOf(netId));// 7.网络id
					} else {
						parParcelinfo.setNetId(null);// 7.网络id NetId
					}
					parParcelinfo.setCpTakeTaskId(11111111l);// //主代收点微信支付字段,此处没用到
					parParcelinfo.setTakeTaskId(22222222l);// 副
				}

				parParcelinfo.setCpReceiptStatus((short) 1);// 1:已付, 代收点收现金
				parParcelinfo.setFreight(null);// 9.包裹应收运费 Freight
				parParcelinfo.setGoodsPaymentMethod((short) 0);// 10.支付方式
				parParcelinfo.setCodAmount(BigDecimal.valueOf((double) 0));// 11.代收货款金额
				parParcelinfo.setFreightPaymentMethod((short) 0);// 15.运费支付方式
				parParcelinfo
						.setActualCodAmount(BigDecimal.valueOf((double) 0));// )16.代收货款实际收到的货款金额

				if (PubMethod.isEmpty(weightTransit)) {
					weightTransit = "0";
				}
				logger.info("新添加一个包裹计费重量================weightTransit: "
						+ weightTransit);
				parParcelinfo.setChareWeightFortransit(BigDecimal
						.valueOf(Double.valueOf(weightTransit)));
				parParcelinfo.setSignMember(null);// 20.签收人 SignMember 收件人
				parParcelinfo.setCreateUserId(Long.valueOf(memberId));// 21.创建人id
				parParcelinfo.setParcelEndMark("0");// 22.包裹结束标志 ParcelEndMark
				parParcelinfo.setActualTakeMember(Long.valueOf(memberId));// 23.取件人id
				parParcelinfo.setPickupTime(new Date());// 24.取件时间 PickupTime
														// new Date();
				// 25.派件人id ActualSendMember null
				parParcelinfo.setReceiptId(receiptId);// 26.收据id ReceiptId
				// 27.签收结果 0：未签收/拒签 1：签收 SignResult 0 null
				parParcelinfo.setTackingStatus((short) 0);// 28.包裹当前状态 0:在途,未签收
				if ("0".equals(roleId) || "1".equals(roleId)
						|| "-1".equals(roleId)) {// 收派员
					parParcelinfo.setParcelStatus((short) 1);// 29.包裹状态
				} else {// 代收点存发货方compId
					parParcelinfo.setSenderCompId(Long.valueOf(compId));// 发货方存代收点的compId
					parParcelinfo.setSenderUserId(Long.valueOf(memberId));// 发货方存代收点的memberId
				}
				// 30.disposal_desc null
				// 31.如果异常原因不为空则添加异常时间 ExceptionTime null
				Date date = new Date();
				parParcelinfo.setCreateTime(date);// 32.设置包裹创建时间 CreateTime new
				mongoTemplate.insert(parParcelinfo);// 插入包裹
				// =============================================================================================
				ParParceladdress parParceladdress = new ParParceladdress();// 包裹地址表
				parParceladdress.setUid(parcelId); // 1.包裹地址id 生成的19位id id
				// parParceladdress.setSendCustomerId(Long.valueOf(customerId));//2.发件客户ID
				parParceladdress.setAddresseeAddressId(null);// 3.收件客户ID
				parParceladdress.setSendCasUserId(null);// 4.发件人CASID
				parParceladdress.setAddresseeCasUserId(null);// 5.收件人CASID
				parParceladdress.setAddresseeName(receiveName);// 6.收件人姓名
				parParceladdress.setAddresseeMobile(receivePhone);// 7.收件人手机号码
				parParceladdress.setAddresseeAddressId(null);// 8.收件人乡镇id
				parParceladdress.setAddresseeAddress(receiveProvince);// 9.收件人详细地址
				parParceladdress.setSendName(sendName);// 10.发件人姓名 SendName
				parParceladdress.setSendMobile(sendPhone);// 11.发件人手机 SendMobile
				parParceladdress.setSendAddressId(null);// 12.发件人乡镇id
				parParceladdress.setSendAddress(sendAddress);// 13.发件人详细地址
				parParceladdress.setSendLongitude(null);// 14.发件人地址经度
				parParceladdress.setSendLatitude(null);// 15.发件人地址纬度
				parParceladdress.setAddresseeLongitude(null);// 16.收件人地址经度
				parParceladdress.setAddresseeLatitude(null);// 17.收件人地址纬度
				parParceladdress.setCreateTime(new Date());// 地址创建时间

				mongoTemplate.insert(parParceladdress);// 插入地址表
				logger.info("收派员或者代收点自添加取件插入地址表.........");
				// parParcelconnection = null;
				ParParcelconnection parParcelconnection = new ParParcelconnection();// 包裹收派过程信息监控表
				parParcelconnection.setUid(IdWorker.getIdWorker().nextId());
				parParcelconnection.setMobilePhone(sendPhone);// 片键---包裹监控包---也用发件人手机号
																// -----------------
				if ("0".equals(roleId) || "1".equals(roleId)
						|| "-1".equals(roleId)) {// 收派员
					parParcelconnection.setCompId(Long.valueOf(compId));// 公司compId
					parParcelconnection.setNetId(Long.valueOf(netId1));// 网络netId

				} else { // 代收点
					if (!PubMethod.isEmpty(netId)) {
						// netId = "0";
						parParcelconnection.setNetId(Long.valueOf(netId));// 网络netId
					} else {
						parParcelinfo.setNetId(null);// 7.网络id NetId
					}
				}
				parParcelconnection.setCosignFlag((short) 1);// 取件
				parParcelconnection.setCreateTime(date);// 创建时间
				parParcelconnection.setExpMemberId(Long.valueOf(memberId));// memberId
				parParcelconnection.setExpMemberSuccessFlag(null);// 取派件成功标志
				parParcelconnection.setParId(parcelId);// 包裹id
				// parParcelconnection.setTaskId(taskId);//任务id

				mongoTemplate.insert(parParcelconnection);// 插入信息监控表
				logger.info("收派员或者代收点自添加取件插入信息监控表完成..................");
			}
			/*
			 * try { if("0".equals(roleId) || "1".equals(roleId) ||
			 * "-1".equals(roleId)){//收派员
			 * this.setAccSrecpayvouchers(accSrecpayvouchers, null,
			 * freightMoney, Integer.valueOf(packageNum),
			 * Long.valueOf(memberId), memberName, Long.valueOf(compId)); }else{
			 * this.setAccSrecpayvouchers(accSrecpayvouchers, null,
			 * freightMoney, Integer.valueOf(packageNum),
			 * Long.valueOf(memberId), memberName, null); }
			 * mongoTemplate.insert(accSrecpayvouchers);//插入收据表 } catch
			 * (Exception e1) { // TODO Auto-generated catch block
			 * //e1.printStackTrace();
			 * logger.info("插入收据表失败, 失败原因: "+e1.getStackTrace()); }
			 */
			try {
				// this.insertBasCustomerInfo(sendName, sendPhone, sendAddress);
			} catch (Exception e) {
				// e.printStackTrace();
				logger.info("自取件--,takeSendPackageByMember保存客户信息失败--大数据用, 失败原因: "
						+ e.getStackTrace());
			}
			logger.info("收派员或者代收点自添加取件插入数据======================全部完成");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("data", "002");
			return "002"; // 002 代表自取件异常
		}
		map.put("data", "001");
		return "001";// 001 代表成功
	}

	
	// 查询取件订单(揽收用的
	
	public List<ParTaskInfo> queryNewPackLists(Long memberId) {	
		List <Long> list = new ArrayList<>();
		Query query = new Query();
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));// memberId
		query.addCriteria(Criteria.where("taskIsEnd").is((byte) 0));// 包裹未结束
		query.addCriteria(Criteria.where("taskType").is((byte) 0));// 取件
		query.addCriteria(Criteria.where("taskStatus").is((byte) 1));// 已分配			
		List<ParTaskInfo> listParTask = mongoTemplate.find(query,ParTaskInfo.class);
		return listParTask;
	}
	// 揽收用的查询订单详情（包裹）
	@SuppressWarnings("rawtypes")
	public List<ParParcelinfo> getPid(Long taskId) {
		List<ParParcelinfo> list = this.mongoTemplate.find(
				Query.query(Criteria.where("takeTaskId")
						.is(taskId).and("parcelStatus")
						.is((short) 0)), ParParcelinfo.class);
		logger.info("根据taskId: " + taskId + " 查询出包裹信息list: " + list);		
		return list;	
}
	
	// 2017-2-15新版添加取件 查询订单下包裹返给手机  揽收运单号查询
	@Override
	public Map<String, Object> queryPackage(String memberId, String sendMobile,
			String netId, String netName, String expWaybillNum, String code) {
		logger.info("新的包裹----memberId："+ memberId+ ",expWaybillNum:"+ expWaybillNum);
		Calendar ca = Calendar.getInstance();
		Date now = new Date();
		ca.add(Calendar.DAY_OF_MONTH, -10);
		Date before = ca.getTime();
		Query query = null;
		
		Map<String, Object> allMap = new HashMap<>(); 
		List<Map<String, Object>> listMap = new ArrayList<>();
		// 先去重
		if (!PubMethod.isEmpty(expWaybillNum)) {
			query = new Query();
			query.addCriteria(Criteria.where("controllerMemberId").is(Long.valueOf(memberId)));// 物权人
			query.addCriteria(Criteria.where("expWaybillNum").is(expWaybillNum));
			query.addCriteria(Criteria.where("controllerTaskFlag").in(1, 2,10,11,12));
				//  1已取,2已发货,10，待派，11已派																
			if (!PubMethod.isEmpty(mongoTemplate.find(query,ParParceladdress.class))) {
				logger.info("添加包裹--此运单号已经揽收：" + "expWaybillNum:"+ expWaybillNum);
				allMap.put("flag", 1);// 1：已揽收，0：未揽收
				allMap.put("listMap", listMap);
				return allMap;// 给出提示 此运单号已存在
			}
		}
		Query query1 = new Query();	
		query1.addCriteria(Criteria.where("takeMemberId").is(Long.valueOf(memberId)));
		if (!PubMethod.isEmpty(expWaybillNum)) {
			query1.addCriteria(Criteria.where("expWaybillNum").is(expWaybillNum));
		} else if (!PubMethod.isEmpty(code)) {
			query1.addCriteria(Criteria.where("code").is(code));
		}
		query1.addCriteria(Criteria.where("createTime").lte(now).gte(before));// <=now>=before		
		query1.addCriteria(Criteria.where("controllerTaskFlag").is((short) 0));// 待取
		ParParceladdress par = mongoTemplate.findOne(query1,
				ParParceladdress.class) == null ? null
				: mongoTemplate.findOne(query, ParParceladdress.class);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(par)) {
			map.put("sendAddress", par.getSendAddress());// 发件人详细地址
			map.put("sendPhone", par.getSendPhone());
			map.put("sendMobile", par.getSendMobile());// 发件人电话
			map.put("sendName", par.getSendName());// 发件人姓名
			map.put("addresseeAddress", par.getAddresseeAddress());// 收件详细地址
			map.put("addresseeMobile", par.getAddresseeMobile());// 收件人电话
			map.put("addresseePhone", par.getAddresseePhone());
			map.put("addresseeName", par.getAddresseeName());// 收件人姓名
			map.put("expWaybillNum", par.getExpWaybillNum());// 包裹运单号
			map.put("netName", par.getNetName());// 网点名
			map.put("netId", par.getNetId());// 网点id
			map.put("code", par.getCode());// 揽收码
			map.put("uid", par.getUid());// 包裹id
			map.put("content", par.getContent());// 包裹备注
			map.put("pacelWeight", par.getPacelWeight());// 包裹重量
			map.put("parcelType", par.getParcelType()); //  物品类型
			map.put("serviceName", par.getServiceName());// 服务产品
			listMap.add(map);
		}
		allMap.put("flag", 0);// 1：已揽收，0：未揽收
		allMap.put("listMap", listMap);
		return allMap;
	}
	//揽收手机号查询订单
	  @Override
	public Map<String, Object> queryPackageMobile(String memberId, String sendMobile,String netId, String netName) {		  
		  Map<String, Object> allMap = new HashMap<>(); // 手机号查询
		  List<Map<String, Object>> listMap = new ArrayList<>();
		  Map<String, Object> map = null;
		  List<ParTaskInfo> queryNewPackLists = this.queryNewPackLists( Long.valueOf(memberId));		  
		  if(!PubMethod.isEmpty(queryNewPackLists)){
			  for (ParTaskInfo parTaskInfo : queryNewPackLists) {
				  Long taskId = parTaskInfo.getUid();
				  List<ParParcelinfo> pid2s = this.getPid(taskId);
				  if(!PubMethod.isEmpty(pid2s)){
					  for (ParParcelinfo parParcelinfo : pid2s) {
						  Long pid = parParcelinfo.getUid();
						  ParParceladdress parParceladdress = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(pid).and("sendMobile").is(sendMobile)),ParParceladdress.class);
					  		if(!PubMethod.isEmpty(parParceladdress)){
					  			  map = new HashMap<String, Object>();
					  			  map.put("sendAddress",parParceladdress.getSendAddress());// 发件人地址
								  map.put("sendMobile", parParceladdress.getSendMobile());//手机
								  map.put("sendPhone",parParceladdress.getSendPhone());// 发件人电话
								  map.put("sendName", parParceladdress.getSendName()); // 发件人名字
								  map.put("addresseeAddress",parParceladdress.getAddresseeAddress());// 收件详细地址
								  map.put("addresseeMobile",parParceladdress.getAddresseeMobile());
								  map.put("addresseePhone",parParceladdress.getAddresseePhone());// 收件人电话
								  map.put("addresseeName",parParceladdress.getAddresseeName());// 收件人姓名
								  map.put("expWaybillNum",parParceladdress.getExpWaybillNum());// 包裹运单号
								  map.put("netName", parParceladdress.getNetName());// 网点名
								  map.put("netId", parParceladdress.getNetId());// 网点id
								  map.put("code", parParceladdress.getCode());// 揽收码
								  map.put("uid", parParceladdress.getUid());// 包裹id
								  map.put("content", parParceladdress.getContent());// 包裹备注
								  map.put("pacelWeight", parParceladdress.getPacelWeight());// 包裹重量
								  map.put("parcelType", parParceladdress.getParcelType()); //  物品类型
								  map.put("serviceName", parParceladdress.getServiceName());// 服务产品
								  listMap.add(map);
					  		}												
					}					  					  
				  }				 
			}
						  
		  }
		allMap.put("flag", 0);// 1：已揽收，0：未揽收
		allMap.put("listMap", listMap);
		return allMap;	  
   } 	  
					
	// 2017-3-16 新版添加取件，（拍照揽收） 好多表都要修改
	@Override
	public String takeReceivePackageByMembers(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment, Long parcelId,
			String compId,String pacelWeight,String parcelType,String serviceName) {
		logger.info("新的添加包裹--memberId：" + memberId + ",sendPhone:" + sendPhone + ",sendAddress:"+ sendAddress + ",addresseePhone:" + addresseePhone
				+ ",addresseeAddress:" + addresseeAddress + ",expWaybillNum:"+ expWaybillNum + "标记内容：" + comment + "揽收码code：" + code);
		Query query = null;
		// Criteria cr = new Criteria();
		// cr.orOperator(Criteria.where("controllerMemberId").is(Long.valueOf(memberId)),Criteria.where("takeMemberId").is(Long.valueOf(memberId)));
		// -------2017年2月9日 去重----code-------//
		if (!PubMethod.isEmpty(code)) {
			query = new Query();
			SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = null;
			Date date1 = new Date();
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			String datec = sim.format(date1);
			try {
				String d1 = datec + " 00:00:00";
				start = sim1.parse(d1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// query.addCriteria(cr);//查询第一次揽收和物权人
			query.addCriteria(Criteria.where("controllerMemberId").is(Long.valueOf(memberId)));
			query.addCriteria(Criteria.where("code").is(code));
			query.addCriteria(Criteria.where("pickupTime").gte(start).lte(date1));
			query.addCriteria(Criteria.where("controllerTaskFlag").in(1, 2));// 已取和已发货
			if (!PubMethod.isEmpty(mongoTemplate.find(query,ParParceladdress.class))) {
				logger.info("新的添加包裹--------------此运取件标号已存在：" + "code:" + code);
				return "004";// 给出提示 此运取件码已存在
			}
		}		
		Date date = new Date(); // 时间很总要
		SimpleDateFormat simf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = simf.format(date);
		try {
			MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
			logger.info("根据memberId: " + memberId + " 查询出这个人的信息" + memberInfoVO);
			if (PubMethod.isEmpty(roleId)) {
				roleId = memberInfoVO.getRoleId();// 手机段可以传 如果为空自己查询
			}	
			String memberName = memberInfoVO.getMemberName();// 快递员
			if (PubMethod.isEmpty(compId)) {
				compId = memberInfoVO.getCompId();//查询站点id
			}
			if (!PubMethod.isEmpty(parcelId)) {
				// 更新包裹表
				this.updateParParcelinfo(memberId,  sendPhone, netId,  netName, roleId,
						expWaybillNum, code, parcelId, compId, date);
				// 更新包裹地址表
				this.updateParParceladdress(memberId, sendName, sendPhone,
						sendAddress, deliveryAddress, netId, addresseeName,
						addresseeAddress, addresseePhone, netName, roleId,
						expWaybillNum, code, comment, parcelId, compId,
						memberName, date,pacelWeight,parcelType,serviceName);
			} else {
				// 创建包裹id
				parcelId = IdWorker.getIdWorker().nextId();// 包裹主键id
				// 插入包裹表
				this.insertParParcelinfo(memberId, sendName, sendPhone,
						sendAddress, deliveryAddress, netId, addresseeName,
						addresseeAddress, addresseePhone, netName, roleId,
						expWaybillNum, code, comment, parcelId, compId,
						memberName, date);
				// 插入包裹地址表
				this.insertParParceladdress(memberId, sendName, sendPhone,
						sendAddress, deliveryAddress, netId, addresseeName,
						addresseeAddress, addresseePhone, netName, roleId,
						expWaybillNum, code, comment, parcelId, compId,
						memberName, date,pacelWeight,parcelType,serviceName);
				logger.info("收派员或者代收点自添加取件插入地址表.........包裹id=地址表id" + parcelId);
				// 插入包裹标记表
				if(!PubMethod.isEmpty(comment)){
					this.insertParParcelMark(memberId, roleId,expWaybillNum, code, comment, parcelId, memberName, date);
				}
				logger.info("添加客户==寄件人的信息存入到客户管理=======是否是好递用户=======OK");
				Map<String,String> map = new HashMap<>();
				map.put("memberId", memberId);
				map.put("compId",compId);
				map.put("receName",sendName);
				map.put("mobile",sendPhone);
				map.put("address", sendAddress);
				String url = constPool.getOpenApiParcelUrl() + "sendPackage/addCustomer";
				String result = Post(url, map);				
			}
			// 插入操作记录表
			this.insertParParcelconnection(memberId, sendName, sendPhone,deliveryAddress, netId, netName, roleId,
					expWaybillNum, code,  parcelId, compId, memberName,date);
			logger.info("收派员或者代收点自添加取件插入信息监控表完成......==============包裹id"+ parcelId);
			// 插入包裹报表
			this.insertPackageReport(memberId, netId, compId, dateString);
			logger.info("收派员或者代收点自添加取件插入报表数据完成....dateString："+ dateString);	
			//this.inserExpCustomerInfo(sendName, sendPhone, sendAddress,memberId);					
			logger.info("收派员或者代收点自添加取件是完成=====================OK");
		} catch (Exception e) {
			e.printStackTrace();
			return "002"; // 002 代表自取件异常
		}
		return "001";// 001 代表成功
	}
	@Override
	public void inserExpCustomerInfo(String userName, String mobilePhone,String address,String memberId) {		
		logger.info("inserExpCustomerInfo--进入保存客户数据接口，参数为：userName="
				+ userName + ",mobilePhone=" + mobilePhone + ",address="+ address);
		// 1.先查询有木有,存在的话就更新地址,姓名;反之 插入数据
		Query query =new Query();
		 Criteria cr = new Criteria();
		 cr.orOperator(Criteria.where("customerName").is(userName),Criteria.where("customerPhone").is(mobilePhone));
		query.addCriteria(cr);//揽收人
		boolean exists = this.mongoTemplate.exists(query,ExpCustomerInfo.class);
		if (exists) {		
			// a.更新数据
			Update update = new Update();
			update.set("customerName", userName);
			update.set("customerPhone", mobilePhone);
			update.set("detailedAddress", address);
			update.set("updateTime", new Date());
//			this.mongoTemplate.updateFirst(Query.query(Criteria.where("mobilePhone").is(mobilePhone)),
//					update, ExpCustomerInfo.class);
		logger.info("insertBasCustomerInfo--手机号" + mobilePhone+ "已存在，更新地址等相关数据！！！");
		} else {
			// b.插入数据
			// 2.用手机号去查询okdi_user，存在的话 保存memberId，反之不保存memberId
			Long mId = isRegistReturnMemberId(mobilePhone);
			ExpCustomerInfo customerInfo = new ExpCustomerInfo();	
			if (!PubMethod.isEmpty(mId)) {
				customerInfo.setIsOkdit( (byte)1);
			}
			customerInfo.setId(IdWorker.getIdWorker().nextId());
			customerInfo.setCustomerName(userName);
			customerInfo.setCustomerPhone(mobilePhone);
			customerInfo.setDetailedAddress(address);
			customerInfo.setMemberId(Long.valueOf(memberId));
			customerInfo.setCreateTime(new Date());
			customerInfo.setUpdateTime(new Date());
			this.mongoTemplate.insert(customerInfo);
			logger.info("insertBasCustomerInfo--手机号" + mobilePhone+ "不存在，插入客户信息相关数据！！！");
		}
	}
	// 插入包裹信息表
	private void insertParParcelinfo(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment, Long parcelId,
			String compId, String memberName, Date date) {
		//MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		ParParcelinfo parParcelinfo = new ParParcelinfo();// 包裹表
		logger.info("新添加包裹中的包裹信息表parcelId=: " + parcelId );
		parParcelinfo.setUid(parcelId);// 1.包裹id 生成的19位id id与地址表中的主键id相同----
		parParcelinfo.setMobilePhone(sendPhone);// 片键 --// 用的发件人的手机号----
		parParcelinfo.setExpWaybillNum(expWaybillNum);// 5.运单号
		// 包裹属于哪个公司只有有快递员接单时才有
		parParcelinfo.setCompId(Long.valueOf(compId));// 6.公司id// CompId----
		parParcelinfo.setNetId(Long.valueOf(netId));// 7.网络id// NetId ----
		parParcelinfo.setCreateUserId(Long.valueOf(memberId));// 21.创建人id----
		parParcelinfo.setParcelEndMark("0");// 22.包裹结束标志 0:未结束----
		parParcelinfo.setParcelStatus((short) 1); // 已取件 0未取件
		parParcelinfo.setActualTakeMember(Long.valueOf(memberId));// 23.取件人id
		parParcelinfo.setPickupTime(date);// 24.取件时间 PickupTime ----
		// 27.签收结果 0：未签收/拒签 1：签收 SignResult 0 null
		parParcelinfo.setTackingStatus((short) 0);// 28.包裹状态 0未签收1:已签收-
		parParcelinfo.setCode(code); // 29.取件码
		// parParcelinfo.setComment(comment); // 30.标记备注 去包裹标记表查询
		if ("0".equals(roleId) || "1".equals(roleId) || "-1".equals(roleId)) {// 收派员
			parParcelinfo.setParcelStatus((short) 1);//1：已取件 0：待取件;10：待派件;11：已派件;12:派件异常
		} else {// 代收点存发货方compId
			parParcelinfo.setParcelStatus((short) 1);
			parParcelinfo.setSenderCompId(Long.valueOf(compId));// 发货方存代收点的compId
			parParcelinfo.setSenderUserId(Long.valueOf(memberId));// 发货方存代收点的memberId
		}
		// Date date = new Date(); //时间很总要
		parParcelinfo.setCreateTime(date);// 32.设置包裹创建时间 CreateTime new---
		logger.info("3333包裹信息表parcelId=: " + parcelId + " expWaybillNum:"+ expWaybillNum + "deliveryAddress:" + deliveryAddress);
		mongoTemplate.insert(parParcelinfo);// 插入包裹
	}

	// 插入包裹地址表
	private void insertParParceladdress(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment, Long parcelId,
			String compId, String memberName, Date date,String pacelWeight,String parcelType,String serviceName) {
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		logger.info("收派员或者代收点自添加取件插入包裹地址表======================包裹id" + parcelId);
		ParParceladdress parParceladdress = new ParParceladdress();// 包裹地址表======
		parParceladdress.setUid(parcelId); // 1.包裹地址id 生成的19位id id与包裹表中的主键id相同
		parParceladdress.setAddresseeName(addresseeName);// 5.收件人姓名
		parParceladdress.setAddresseePhone(addresseePhone);// 6.收件人手机号码//
		parParceladdress.setAddresseeMobile(addresseePhone);
		parParceladdress.setAddresseeAddress(addresseeAddress);// 7.收件人详细地址
		parParceladdress.setAddresseeAddressId(null);// 8.收件人乡镇id
		parParceladdress.setSendName(sendName);// 9.发件人姓名 SendName
		parParceladdress.setSendPhone(sendPhone);// 10.发件人手机 SendMobile
		parParceladdress.setSendMobile(sendPhone);
		parParceladdress.setSendAddress(sendAddress);// 11.发件人详细地址
		parParceladdress.setPacelWeight(pacelWeight);//包裹重量
		parParceladdress.setParcelType(parcelType);
		parParceladdress.setServiceName(serviceName);
		// parParceladdress.addContent(comment, memberName);//标记备注 去标记备注表查询获取
		if (!PubMethod.isEmpty(comment)) {
			parParceladdress.setPackageflag((short) 1);
		} else {
			parParceladdress.setPackageflag((short) 0);
		}
		parParceladdress.setExpWaybillNum(expWaybillNum);// 运单号
		parParceladdress.setPickupTime(date);// 包裹揽收时间
		parParceladdress.setNetId(Long.valueOf(netId));// 网络id
		parParceladdress.setNetName(netName);// 网络名
		parParceladdress.setTakeMemberId(Long.valueOf(memberId));//第一次包裹揽收人id
		parParceladdress.setControllerTaskFlag((short) 1);// 已取
		parParceladdress.setCode(code);//
		parParceladdress.setCompId(Long.valueOf(compId));// 站点id
		parParceladdress.setControllerMemberId(Long.valueOf(memberId));// 物权所有人id
		parParceladdress.setCreateTime(date);// 第一次包裹创建时间
		if ("2".equals(roleId) || "3".equals(roleId)) {
			parParceladdress.setControllerNetId(null);// 物权网络id
			parParceladdress.setControllerNetName("");// 物权网络名称
		} else {
			parParceladdress.setControllerNetId(Long.valueOf(memberInfoVO.getNetId()));// 物权网络id
			parParceladdress.setControllerNetName(memberInfoVO.getNetName());// 物权网络名称
		}
		parParceladdress.setControllerCompId(Long.valueOf(compId));// 物权站点id		
		parParceladdress.setCreateTime(date);// 地址创建时间
		mongoTemplate.insert(parParceladdress);// 插入地址表
	}
	// 插入包裹记录表
	private void insertParParcelconnection(String memberId, String sendName,
			String sendPhone, String deliveryAddress,String netId,  String netName, String roleId,
			String expWaybillNum, String code,  Long parcelId,String compId, String memberName, Date date) {
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		ParParcelconnection parParcelconnection = new ParParcelconnection();// 包裹收派过程信息监控表==========
		parParcelconnection.setUid(IdWorker.getIdWorker().nextId());
		parParcelconnection.setParId(parcelId);// 包裹id
		parParcelconnection.setExpWaybillNum(expWaybillNum);// 运单号
		parParcelconnection.setMobilePhone(sendPhone);// 片键---包裹监控包---
		parParcelconnection.setCompId(Long.valueOf(compId));// 站点compId
		parParcelconnection.setNetId(Long.valueOf(netId));// 网络netId
		parParcelconnection.setNetName(netName);// 网络名称
		parParcelconnection.setCreateTime(date);// 创建交互时间
		parParcelconnection.setExpMemberSuccessFlag((short) 0);// 取派件成功标志
		parParcelconnection.setCosignFlag((short) 1);// 揽收
		// parParcelconnection.setTaskId(Long.valueOf(111111));//任务id
		parParcelconnection.setDeliveryAddress(deliveryAddress);// 交付地址
		parParcelconnection.setExpMemberId(Long.valueOf(memberId));// memberId
		parParcelconnection.setDeliveryName(sendName);// 交付人姓名
		parParcelconnection.setDeliveryMobile(sendPhone);// 交付人电话
		parParcelconnection.setDeliveryUnits("");// 交付人单位
		// 揽收人------站点Id------------------
		parParcelconnection.setRecCompId(Long.valueOf(compId));
		parParcelconnection.setRecCosignFlag((short) 1);// 接受人动作标示 1揽收； 2派件 4,接单
		parParcelconnection.setRecMemberId(Long.valueOf(memberId));// 接受人id
		parParcelconnection.setRecName(memberName);
		parParcelconnection.setRecMobile(memberInfoVO.getMemberPhone());//电话
		parParcelconnection.setRecUnits(memberInfoVO.getCompName());//接受单位
		if ("2".equals(roleId) || "3".equals(roleId)) {
			parParcelconnection.setRecNetId(null);
			parParcelconnection.setRecnetName(null);			
		} else {
			parParcelconnection.setRecNetId(Long.valueOf(memberInfoVO.getNetId()));
			parParcelconnection.setRecnetName(memberInfoVO.getNetName());
		}	
		mongoTemplate.insert(parParcelconnection);// 插入信息监控表
		logger.info("====== 开始更新包裹中的数据 ======== parParcelconnection ......");
		// private Short recMemberSuccessFlag;// 接受人动作标志 0成功；1失败
	}

	// 插入包裹标记表
	private void insertParParcelMark(String memberId,String roleId,String expWaybillNum, String code, 
			String comment, Long parcelId, String memberName, Date date) {
		logger.info("收派员或者代收点自添加取件插入标记内容表......包裹id=标记内容表id" + parcelId);
		ParParcelMark parParcelMark = new ParParcelMark();// 包裹标记备注表==================
		parParcelMark.setCode(code);// 揽收码
		parParcelMark.setExpWaybillNum(expWaybillNum);// 运单号
		parParcelMark.setMarkMemberId(Long.valueOf(memberId));// 标记人id
		parParcelMark.setRoleId(Short.valueOf(roleId));// 标记人身份
		parParcelMark.setParId(parcelId);// 包裹id
		String[] commentdArr = comment.split(" ");
		for (String content : commentdArr) {
			parParcelMark.addContent(content, memberName);// 标记人 ，内容 时间
		}
		mongoTemplate.insert(parParcelMark);// 插入包裹标记备注表
	}
	// 插入包裹报表
	@Override
	public  void insertPackageReport(String memberId, String netId, String compId, String date) {		
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(Long.valueOf(memberId)));
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		//String dateNowStr = sim.format(date);// 当前 年月 日 
		Date now = null;
		try {
			now = sim.parse(date);// 格式化date
		} catch (ParseException e) {
			e.printStackTrace();
		}// 查询报表中揽收数
		query.addCriteria(Criteria.where("createTime").is(now));
		PackageReport packageReport = mongoTemplate.findOne(query,PackageReport.class);		
		if (PubMethod.isEmpty(packageReport)){
			packageReport = new PackageReport();
			packageReport.setCreateTime(now);
			packageReport.setCompId(Long.valueOf(compId));
			packageReport.setMemberId(Long.valueOf(memberId));
			packageReport.setReceiveCount((long) 1);
			mongoTemplate.insert(packageReport);// 包裹报表
			logger.info("插入包裹报表..用户memberId:"+ memberId+",now:"+now+",compId:"+compId+",netId:"+netId+",dateNowStr:"+date);
		} else {
			Update update = new Update();
			update.inc("receiveCount", 1);
			mongoTemplate.updateFirst(query, update, PackageReport.class);
			logger.info("更新报表..memberId:"+ memberId + ",packageReport:" + packageReport);
		}
		
	}
	// 更新包裹表
	private void updateParParcelinfo(String memberId,String sendPhone,String netId, String netName, 
			String roleId,String expWaybillNum, String code, Long parcelId, String compId,
		 Date date) {
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("uid").is(parcelId));
		query1.addCriteria(Criteria.where("mobilePhone").is(sendPhone));
		Update update = new Update();
		update.set("parcelStatus", (short) 1);
		update.set("parcelEndMark", (short) 0);
		update.set("tackingStatus", (short) 0);
		//update.set("createTime", date);
		update.set("expWaybillNum", expWaybillNum);
		update.set("code", code);
		update.set("actualTakeMember", Long.valueOf(memberId));
		update.set("createUserId", Long.valueOf(memberId));
		update.set("netId", Long.valueOf(netId));
		update.set("compId", Long.valueOf(compId));
		update.set("pickupTime", date);
		logger.info("====== 开始更新包裹中的数据 ======== ParParcelinfo ......");
		//mongoTemplate.updateFirst(query1, update, ParParcelinfo.class);
		mongoTemplate.upsert(query1, update, ParParcelinfo.class);
	}
	// 更新包裹地址表
	private void updateParParceladdress(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment, Long parcelId,
			String compId, String memberName, Date date,String pacelWeight,String parcelType,String serviceName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(parcelId));
		query.addCriteria(Criteria.where("addresseeMobile").is(addresseePhone));
		Update update = new Update();
		update.set("controllerTaskFlag", (short) 1);
		if (!PubMethod.isEmpty(comment)) {
			update.set("packageflag", (short) 1);
		} else {
			update.set("packageflag", (short) 0);
		}
		update.set("controllerMemberId", Long.valueOf(memberId));
		update.set("addresseeName", addresseeName);
		update.set("addresseePhone", addresseePhone);
		update.set("addresseeAddress", addresseeAddress);
		update.set("expWaybillNum", expWaybillNum);
		update.set("code", code);
		update.set("sendName", sendName);
		update.set("sendPhone", sendPhone);
		update.set("sendMobile", sendPhone);
		update.set("sendAddress", sendAddress);
		update.set("deliveryAddress", deliveryAddress);
		update.set("parcelType", parcelType);
		update.set("serviceName", serviceName);
		update.set("pacelWeight", pacelWeight);
		update.set("pickupTime", date);
		update.set("createTime", date);
		update.set("takeMemberId", Long.valueOf(memberId));
		update.set("compId", Long.valueOf(compId));
		update.set("controllerCompId", Long.valueOf(compId));
		if ("2".equals(roleId) || "3".equals(roleId)) {
			update.set("netName", "");
			update.set("controllerNetName", "");
		} else {
			update.set("netId", Long.valueOf(netId));
			update.set("netName", netName);
			update.set("controllerNetName", netName);
			update.set("controllerNetId", Long.valueOf(netId));
		}
		logger.info("====== 开始更新包裹中的数据 ======== ParParceladdress ......");
		mongoTemplate.updateFirst(query, update, ParParceladdress.class);
	}

	// 订单详情----new
	@Override
	public Map<String, Object> takeReceivePackageDetailInfo(String taskId,
			Long memberId) {
		logger.info("收派员从取件订单中或者自己扫描个人端二维码查询寄件人和包裹信息, 任务taskId: " + taskId
				+ " ,mmemberId: " + memberId);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 拿到包裹任务表
		Query queryA = new Query(new Criteria("uid").is(Long.valueOf(taskId)));
		queryA.addCriteria(Criteria.where("actorMemberId").is(memberId));
		ParTaskInfo parTaskInfo = mongoTemplate.findOne(queryA,
				ParTaskInfo.class);
		logger.info("根据memberId: " + memberId + " 或者taskId:" + taskId
				+ " 查询出来的任务为: " + parTaskInfo);
		Map<String, Object> allMap = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(parTaskInfo)) {
			allMap.put("contactAddress", parTaskInfo.getContactAddress());// 寄件人地址
			allMap.put("contactMobile", parTaskInfo.getContactMobile());// 寄件人手机
			allMap.put("contactName", parTaskInfo.getContactName());// 寄件人姓名
			allMap.put("parEstimateCount", parTaskInfo.getParEstimateCount());// 包裹预估数
			allMap.put("taskId", parTaskInfo.getUid());// 任务id
		}
		// 根据快递员的memberid查到网络id,网络名称,
		Long compId = memberInfoMapperV4.findCompIdByMemberId(memberId);// 公司compId
		logger.info("根据memebrId:" + memberId + " 查询出的compId: " + compId);
		Long netId = memberInfoMapperV4.findNetIdByCompId(compId);// 网络id
		logger.info("根据compId:" + compId + " 查询出的netId: " + netId);
		String netName = memberInfoMapperV4.findNetNameByNetId(netId);// 网络名称
		logger.info("根据netId:" + netId + " 查询出的netName: " + netName);
		allMap.put("netName", netName);
		allMap.put("netId", netId);

		logger.info("根据memebrId:" + memberId + " 查询出的compId: " + compId);
		// 根据任务id拿到包裹和包裹地址表
		List<ParParcelinfo> list = this.mongoTemplate.find(
				Query.query(Criteria.where("takeTaskId").is(Long.parseLong(taskId)).
						and("parcelStatus").is((short) 0)), ParParcelinfo.class);
		logger.info("根据taskId: " + taskId + " 查询出包裹信息list: " + list);
		Map<String, Object> map = null;
		if (!PubMethod.isEmpty(list)) {
			for (ParParcelinfo parParcelinfo : list) {
				map = new HashMap<String, Object>();
				Long uid = parParcelinfo.getUid();
				map.put("parcelId", uid);// 包裹主键id
				map.put("createTime", parParcelinfo.getCreateTime());// 包裹创建时间
				// 拿到包裹id,找到包裹地址表
				ParParceladdress parParceladdress = this.mongoTemplate.findOne(
						Query.query(Criteria.where("uid").is(uid)),
						ParParceladdress.class);
				logger.info("根据包裹id: " + uid + " 查询出地址的详细信息: "
						+ parParceladdress);
				
					map.put("addresseeAddress",
							parParceladdress.getAddresseeAddress());// 收件详细地址
					map.put("addresseeMobile",
							parParceladdress.getAddresseeMobile());// 收件人电话
					map.put("addresseeName",
							parParceladdress.getAddresseeName());// 收件人姓名
					map.put("expWaybillNum",
							parParceladdress.getExpWaybillNum());// 包裹运单号
					map.put("netId",netId);// 包裹netid
					//判断包裹信息是否完善
					boolean isfinished = this.isfinished(parParceladdress);
					map.put("isFinished", isfinished);//包裹是否完善

					//首先判断包裹是否携带快递公司名称,没有则更改为快递员的netname
					if (PubMethod.isEmpty(parParceladdress.getNetName())) {
						//更新包裹地址表中netname网络名称
						Query query1=new Query();
						query1.addCriteria(Criteria.where("uid").is(uid));
						query1.addCriteria(Criteria.where("addresseeMobile").is(parParceladdress.getAddresseeMobile()));
						Update update=new Update();
						update.set("netName", netName);
						this.mongoTemplate.updateFirst(query1,update, ParParceladdress.class);
						////end///////////////////////////
						map.put("parcelNetName", netName);// 包裹网络名称
					}else {
						map.put("parcelNetName", parParceladdress.getNetName());// 包裹网络名称
					}

					// 查询包裹是否标记
					ParParcelMark parParcelMark = this.mongoTemplate.findOne(
							Query.query(Criteria.where("parId").is(
									parParceladdress.getUid())),
							ParParcelMark.class);
					map.put("isMark", "false");// 是否标记
					if (!PubMethod.isEmpty(parParcelMark)) {
						map.put("isMark", "true");// 是否标记
					}
					listMap.add(map);
					map = null;
				
			}
		}
		allMap.put("listMap", listMap);
		logger.info("最后返回的数据listMap: " + listMap);
		return allMap;
	}

	//查询包裹是否完善=====new
	public boolean isfinished(ParParceladdress parParceladdress){
		boolean isFinished=true;
//		update.set("sendName", contactName);
//		update.set("sendAddress", contactAddress);
//		update.set("sendMobile", contactMobile);
//		update.set("expWaybillNum", expWaybillNum);
//		update.set("code", code);
//		update.set("addresseeName", addresseeName);
//		update.set("addresseeAddress", addresseeAddress);
		//update.set("addresseeMobile", addresseePhone);//片键===不能修改
		if (!PubMethod.isEmpty(parParceladdress)) {
			//如果四个选项有一个为空,即为未完善
			if (PubMethod.isEmpty(parParceladdress.getAddresseeName())|| PubMethod.isEmpty(parParceladdress.getAddresseeAddress())) {
				isFinished=false;
			}
			if (PubMethod.isEmpty(parParceladdress.getAddresseeMobile())|| PubMethod.isEmpty(parParceladdress.getSendAddress())) {
				isFinished=false;
			}			
			//如果运单号和code有一个完善就是完善
			if (PubMethod.isEmpty(parParceladdress.getCode())&&PubMethod.isEmpty(parParceladdress.getExpWaybillNum())) {
				isFinished=false;
			}
			if (PubMethod.isEmpty(parParceladdress.getSendName())|| PubMethod.isEmpty(parParceladdress.getSendMobile())) {
				isFinished=false;
			}	
					
		}else{
			isFinished=false;
		}
		return isFinished;
		
	}
		
	// 快递员扫描个人端二维码取包裹
	@Override
	public String takeReceivePackageByOnlyCode(String parcelIds, Long memberId) {
		logger.info("快递员扫描个人端二维码取包裹...parcelIds: " + parcelIds + " ,memberId: "
				+ memberId);
		try {
			if (!PubMethod.isEmpty(parcelIds)) {
				String[] parcelS = parcelIds.split(",");
				Query query = null;
				Update update = null;
				for (String parcelId : parcelS) {
					logger.info("快递员扫描个人端二维码取包裹parcelId: " + parcelId);
					query = new Query(new Criteria("uid").is(parcelId));
					ParParcelinfo parParcelinfo = mongoTemplate.findOne(query,
							ParParcelinfo.class);
					String mobilePhone = parParcelinfo.getMobilePhone();
					query.addCriteria(Criteria.where("mobilePhone").is(
							mobilePhone));
					update = new Update();
					update.set("pickupTime", new Date());// 取件时间
					update.set("parcelStatus", (short) 1);// 1:已取件
					update.set("actualTakeMember", memberId);// 实际取件人
					update.set("receiptId", null);// 1:收据id
					update.set("cpReceiptStatus", (short) 1);// 1:付款(代收点已付和收派员已付)
					logger.info("====== 开始更新包裹中的数据 ========  ......");
					mongoTemplate.updateFirst(query, update,
							ParParcelinfo.class);
				}
			}
			logger.info("快递员扫描个人端二维码取包裹收取包裹完成......");
			return "001";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("快递员扫描个人端二维码取包裹失败, 失败原因: " + e.getStackTrace());
			return "002";
		}
	}

	// 该站点下所有包裹List<ParParcelinfo>
	public List<ParParcelinfo> queryAllParParcel(Long compId, Date start,
			Date end) {
		// List<MemberInfo> list = assignPackage.queryAllParParcel(compId);//
		// 站点下所有收派员
		List<MemberInfo> list = assignPackage.queryMember(compId);// 站点下所有收派员
		List<ParParcelinfo> ParParcelinfo = new ArrayList<>();
		for (MemberInfo memberInfo : list) {
			Long memberId = memberInfo.getMemberId();
			logger.info("站点下所有收派员 queryMember=====memberId: " + memberId
					+ " ==角色 roleid： " + memberInfo.getRoleId());
			Query query = new Query();
			query.addCriteria(Criteria.where("actualTakeMember").is(
					Long.valueOf(memberId)));
			query.addCriteria(Criteria.where("pickupTime").gte(start).lte(end));
			query.addCriteria(Criteria.where("parcelStatus").is((short) 1));// 已取
			query.with(new Sort(Direction.DESC, "pickupTime"));// 根据活动开始时间排序

			List<ParParcelinfo> find = mongoTemplate.find(query,
					ParParcelinfo.class) == null ? new ArrayList()
					: mongoTemplate.find(query, ParParcelinfo.class);
			ParParcelinfo.addAll(find);
			find = null;
		}
		return ParParcelinfo;
	}

	// 取件记录查询 2016-12-21 增加角色，手机号，运单号查询
	public Map<String, Object> queryTakeRecordLists(String memberId,
			String date, String phone, String expWaybillNum,
			String parcelEndMark, String all, Integer currentPage,
			Integer pageSize) {
		Query query = new Query();
		// mogon 格式话日期
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		if (PubMethod.isEmpty(phone) && PubMethod.isEmpty(expWaybillNum)) {
			logger.info("手机号传入的空=======================phone: " + phone
					+ "运单号传入的空=======expWaybillNum: " + expWaybillNum);
			try {
				String d1 = date + " 00:00:00";
				String d2 = date + " 23:59:59";
				start = sim.parse(d1);
				end = sim1.parse(d2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		logger.info("根据memberId:" + memberId + "查出来的memberinfo:"
				+ JSON.toJSONString(memberInfoVO == null ? "" : memberInfoVO));
		String roleId = memberInfoVO.getRoleId();// 角色
		Long compId = Long.valueOf(memberInfoVO.getCompId());
		List<ParParcelinfo> listParcel = null;
		if (!"all".equals(all)) {
			if ("1".equals(roleId) || "-1".equals(roleId)) {// 站长，后勤
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("actualTakeMember").is(
						Long.valueOf(memberId)));
				// query.addCriteria(Criteria.where("parcelEndMark").is("1"));//结束
				// 收派员看到的是交寄的
				// query.addCriteria(new Criteria("takeTaskId").ne(null));
				// 增加手机号查询条件2016年6月1日 14:35:05
				if (!PubMethod.isEmpty(phone)) {
					query.addCriteria(Criteria.where("mobilePhone")
							.regex(phone));
					Calendar ca = Calendar.getInstance();
					Date now = new Date();
					ca.add(Calendar.MONTH, -3);
					Date before = ca.getTime();
					query.addCriteria(Criteria.where("pickupTime").lte(now)
							.gte(before));// <=now >=before
				} else if (!PubMethod.isEmpty(expWaybillNum)) {// 增加运单号查询条件2016年12月21日
					query.addCriteria(Criteria.where("expWaybillNum").is(
							expWaybillNum));
					Calendar ca = Calendar.getInstance();
					Date now = new Date();
					ca.add(Calendar.MONTH, -3);
					Date before = ca.getTime();
					query.addCriteria(Criteria.where("pickupTime").lte(now)
							.gte(before));// <=now >=before
				} else {
					logger.info("手机号传入的是空===收派员==========phone: " + phone
							+ "运单号传入的空=======expWaybillNum: " + expWaybillNum);
					query.addCriteria(Criteria.where("pickupTime").gte(start)
							.lte(end));
				}
				// 根据创建时间进行排序
				query.with(new Sort(Direction.DESC, "pickupTime"));// 根据活动开始时间排序

				logger.info("收派员取件记录查询====================================: "
						+ query);
			} else if ("2".equals(roleId) || "3".equals(roleId)) {// 代收点
				query = null;
				query = new Query();
				// query.addCriteria(Criteria.where("senderCompId").is(compId));//代收点compId
				query.addCriteria(Criteria.where("actualTakeMember").is(
						Long.valueOf(memberId)));
				// query.addCriteria(Criteria.where("parcelStatus").is((short)1));//已取件
				if (PubMethod.isEmpty(parcelEndMark)) {
					parcelEndMark = "0";// 默认看到为 0未交寄 1 表示已交寄
				}
				query.addCriteria(Criteria.where("parcelEndMark").is(
						parcelEndMark));// 结束 代收点看到的是 0未交寄 1 已交寄的
				// query.addCriteria(new Criteria("cpTakeTaskId").ne(null));
				// 增加手机号
				if (!PubMethod.isEmpty(phone)) {
					query.addCriteria(Criteria.where("mobilePhone")
							.regex(phone));
					Calendar ca = Calendar.getInstance();
					Date now = new Date();
					ca.add(Calendar.MONTH, -3);
					Date before = ca.getTime();
					query.addCriteria(Criteria.where("createTime").lte(now)
							.gte(before));// <=now >=before
				} else if (!PubMethod.isEmpty(expWaybillNum)) {// 增加运单号查询条件2016年12月21日
					query.addCriteria(Criteria.where("expWaybillNum").is(
							expWaybillNum));
					Calendar ca = Calendar.getInstance();
					Date now = new Date();
					ca.add(Calendar.MONTH, -3);
					Date before = ca.getTime();
					query.addCriteria(Criteria.where("createTime").lte(now)
							.gte(before));// <=now >=before
				} else {
					logger.info("手机号传入的是空===代收点==========phone: " + phone);
					query.addCriteria(Criteria.where("createTime").gte(start)
							.lte(end));
				}
				// 根据创建时间进行排序
				query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
				logger.info("代收点取件记录查询====================================: "
						+ query);
			} else if ("0".equals(roleId)) {// 快递员
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("actualTakeMember").is(
						Long.valueOf(memberId)));
				// query.addCriteria(Criteria.where("parcelEndMark").is("1"));//结束
				// 快递员看到的是本人交寄的
				// query.addCriteria(new Criteria("takeTaskId").ne(null));
				// 增加手机号查询条件2016年6月1日 14:35:05
				if (!PubMethod.isEmpty(phone)) {
					query.addCriteria(Criteria.where("mobilePhone")
							.regex(phone));
					Calendar ca = Calendar.getInstance();
					Date now = new Date();
					ca.add(Calendar.MONTH, -3);
					Date before = ca.getTime();
					query.addCriteria(Criteria.where("pickupTime").lte(now)
							.gte(before));// <=now >=before
				} else if (!PubMethod.isEmpty(expWaybillNum)) {// 增加运单号查询条件2016年12月21日
					query.addCriteria(Criteria.where("expWaybillNum").is(
							expWaybillNum));
					Calendar ca = Calendar.getInstance();
					Date now = new Date();
					ca.add(Calendar.MONTH, -3);
					Date before = ca.getTime();
					query.addCriteria(Criteria.where("pickupTime").lte(now)
							.gte(before));// <=now >=before
				} else {
					logger.info("手机号传入的是空===收派员==========phone: " + phone
							+ "运单号传入的空=======expWaybillNum: " + expWaybillNum);
					query.addCriteria(Criteria.where("pickupTime").gte(start)
							.lte(end));
				}
				// 根据创建时间进行排序
				query.with(new Sort(Direction.DESC, "pickupTime"));// 根据活动开始时间排序
				logger.info("收派员取件记录查询====================================: "
						+ query);
			}
			listParcel = mongoTemplate.find(query, ParParcelinfo.class);
		} else {

			listParcel = this.queryAllParParcel(compId, start, end);
		}
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		logger.info("取件记录查询出来的包裹==========listParcel: " + listParcel);
		Map<String, Object> ppMap = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(currentPage)) {// 分页
			int fromIndex = (currentPage - 1) * pageSize > listParcel.size() ? listParcel
					.size() : (currentPage - 1) * pageSize;
			int toIndex = currentPage * pageSize > listParcel.size() ? listParcel
					.size() : currentPage * pageSize;
			listParcel = listParcel.subList(fromIndex, toIndex);
		}
		for (ParParcelinfo parParcelinfo : listParcel) {
			Date createTime = parParcelinfo.getCreateTime();// 时间
			map = new HashMap<String, Object>();
			Long uid = parParcelinfo.getUid();
			query = null;
			query = new Query();
			// query.addCriteria(Criteria.where("uid").is(uid));
			logger.info("从地址中取到的 ================== uid:" + uid);
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(uid))
					.and("sendMobile").ne(null));
			ParParceladdress parParceladdress = mongoTemplate.findOne(query,
					ParParceladdress.class);
			logger.info("取件记录中查询地址parParceladdress============: "
					+ parParceladdress + "===== parParceladdress.getUid() :"
					+ parParceladdress.getUid());
			BigDecimal chareWeightFortransit = parParcelinfo
					.getChareWeightFortransit();// 计费重量(销售)
			Short freightPaymentMethod = parParcelinfo
					.getFreightPaymentMethod();// 应收运费支付方式
			BigDecimal freight = parParcelinfo.getFreight();// 应收运费
			String expWaybillNum1 = parParcelinfo.getExpWaybillNum();// 运单号
			BigDecimal pricePremium = parParcelinfo.getPricePremium();// 保价费
			BigDecimal codAmount = parParcelinfo.getCodAmount();// 代收货款金额
			BigDecimal packingCharges = parParcelinfo.getPackingCharges();// 包装费
			BigDecimal insureAmount = parParcelinfo.getInsureAmount();// 保价金额
			Long actualTakeMember = parParcelinfo.getActualTakeMember();// 任务id
			Date pickupTime = parParcelinfo.getPickupTime();
			String parcelRemark = parParcelinfo.getParcelRemark();// 包裹备注
			// 查询快递网络
			Long netId = parParcelinfo.getNetId();
			String netName = memberInfoMapperV4.findNetNameByNetId(netId);// 快递网络名称
			String sendAddress = parParceladdress.getSendAddress();// 发件人地址
			String sendMobile = parParceladdress.getSendMobile();// 发件人电话
			String sendName = parParceladdress.getSendName();// 发件人name
			String addresseeAddress = parParceladdress.getAddresseeAddress();// 收件人地址
			String addresseeMobile = parParceladdress.getAddresseeMobile();// 收件人手机号
			String addresseeName = parParceladdress.getAddresseeName();// 收件人姓名

			map.put("parcelRemark", parcelRemark);
			map.put("addresseeAddress", addresseeAddress);
			map.put("addresseeMobile", addresseeMobile);
			map.put("addresseeName", addresseeName);
			map.put("chareWeightFortransit", chareWeightFortransit);
			map.put("freightPaymentMethod", freightPaymentMethod);
			map.put("freight", freight);
			map.put("expWaybillNum", expWaybillNum1);
			map.put("pricePremium", pricePremium);
			map.put("codAmount", codAmount);
			map.put("packingCharges", packingCharges);
			map.put("insureAmount", insureAmount);
			map.put("netName", netName);
			map.put("sendAddress", sendAddress);
			map.put("sendMobile", sendMobile);
			map.put("sendName", sendName);
			map.put("parcelId", uid);
			if ("2".equals(roleId) || "3".equals(roleId)) {// 代收点角色显示createTime时间
				map.put("createTime", createTime);
				logger.info("显示代收点的时间: =====\\\\\\\\\\\\\\\\\\=====: "
						+ createTime);
			} else {
				map.put("createTime", pickupTime);
				logger.info("显示收派员的时间: ==== ||||||||| ======: " + pickupTime);
			}
			map.put("actualTakeMember", actualTakeMember);
			listMap.add(map);
		}
		ppMap.put("listMap", listMap);
		ppMap.put("listMapNum", listMap.size());
		return ppMap;
	}

	// 揽收记录查询 2017-2-18增加角色，手机号，运单号查询
	public Map<String, Object> queryTakeRecordListk(String memberId,
			String date, String phone, String expWaybillNum, String all,
			Integer currentPage, Integer pageSize, Integer index) {
		logger.info("进入取件记录查询 queryTakeRecordListk ==memberId:" + memberId
				+ "= date:" + date + "=phone:" + phone + "=expWaybillNum: "
				+ expWaybillNum + "=index: " + index);
		Query query = null;
		// mogon 格式话日期
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		if (PubMethod.isEmpty(phone) && PubMethod.isEmpty(expWaybillNum)) {
			logger.info("手机号传入的空=======================phone: " + phone
					+ "运单号传入的空=======expWaybillNum: " + expWaybillNum);
			try {
				String d1 = date + " 00:00:00";
				String d2 = date + " 23:59:59";
				start = sim.parse(d1);
				end = sim1.parse(d2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		logger.info("根据memberId:" + memberId + "查出来的memberinfo:"
				+ JSON.toJSONString(memberInfoVO == null ? "" : memberInfoVO));
		String roleId = memberInfoVO.getRoleId();// 角色
		Long compId = Long.valueOf(memberInfoVO.getCompId());
		List<ParParcelinfo> listParcel = null;
		if ("all".equals(all)) {
			listParcel = this.queryAllParParcel(compId, start, end);
		} else {
			query = new Query();
			query.addCriteria(Criteria.where("actualTakeMember").is(
					Long.valueOf(memberId)));
			if (!PubMethod.isEmpty(phone)) {
				query.addCriteria(Criteria.where("mobilePhone").regex(phone));
				Calendar ca = Calendar.getInstance();
				Date now = new Date();
				ca.add(Calendar.MONTH, -3);
				Date before = ca.getTime();
				System.out.println("取件记录查询3个月之前的今天=====收派员的=====memberId: "
						+ memberId + "========:三月之前的今天:" + before + "==now: "
						+ now);
				query.addCriteria(Criteria.where("pickupTime").lte(now)
						.gte(before));// <=now >=before
				query.addCriteria(Criteria.where("parcelStatus").is((short) 1));// 已取
			} else if (!PubMethod.isEmpty(expWaybillNum)) {// 增加运单号查询条件2016年12月21日
				query.addCriteria(Criteria.where("expWaybillNum").regex(
						expWaybillNum));
				Calendar ca = Calendar.getInstance();
				Date now = new Date();
				ca.add(Calendar.MONTH, -3);
				Date before = ca.getTime();
				System.out.println("取件记录查询3个月之前的今天=====收派员的=====memberId: "
						+ memberId + "========:三月之前的今天:" + before + "==now: "
						+ now);
				query.addCriteria(Criteria.where("pickupTime").lte(now)
						.gte(before));// <=now >=before
				query.addCriteria(Criteria.where("parcelStatus").is((short) 1));// 已取
			} else {
				logger.info("手机号传入的是空===收派员==========phone: " + phone
						+ "运单号传入的空=======expWaybillNum: " + expWaybillNum);
				query.addCriteria(Criteria.where("pickupTime").gte(start)
						.lte(end));
				query.addCriteria(Criteria.where("parcelStatus").is((short) 1));// 已取
			}
			query.with(new Sort(Direction.DESC, "pickupTime"));// 根据活动开始时间排序
			if (index == 1) { // index:1 >> 发货相关
				query.addCriteria(Criteria.where("shipmentRemark").is(null)
						.and("sendToPeerRemark").is(null));
			}
			listParcel = mongoTemplate.find(query, ParParcelinfo.class);
		}
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		String num = listParcel.size() + "";
		Map<String, Object> map = null;
		Map<String, Object> ppMap = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(currentPage)) {// 分页
			int fromIndex = (currentPage - 1) * pageSize > listParcel.size() ? listParcel
					.size() : (currentPage - 1) * pageSize;
			int toIndex = currentPage * pageSize > listParcel.size() ? listParcel
					.size() : currentPage * pageSize;
			listParcel = listParcel.subList(fromIndex, toIndex);
		}
		for (ParParcelinfo parParcelinfo : listParcel) {
			map = new HashMap<String, Object>();
			query = new Query();
			Date createTime = parParcelinfo.getCreateTime();// 时间
			Long uid = parParcelinfo.getUid();
			logger.info("从地址中取到的 ================== uid:" + uid);
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(uid))
					.and("sendMobile").ne(null));
			ParParceladdress parParceladdress = mongoTemplate.findOne(query,
					ParParceladdress.class);
			logger.info("取件记录中查询地址parParceladdress============: "
					+ parParceladdress + "===== parParceladdress.getUid() :"
					+ parParceladdress.getUid());
			Short freightPaymentMethod = parParcelinfo
					.getFreightPaymentMethod();// 应收运费支付方式
			BigDecimal freight = parParcelinfo.getFreight();// 应收运费
			String expWaybillNum1 = parParcelinfo.getExpWaybillNum();// 运单号
			Long actualTakeMember = parParcelinfo.getActualTakeMember();// 取件人
			Date pickupTime = parParcelinfo.getPickupTime(); // 取件时间
			String parcelRemark = parParcelinfo.getParcelRemark();// 包裹备注
			Long netId = parParcelinfo.getNetId();// 查询快递网络
			// ------------------2017年1月11日----------------------------//
			// String code =;//取件码
			String comment = parParcelinfo.getComment();// 标记备注
			String shipmentRemark = parParcelinfo.getShipmentRemark(); // "1":已发运
																		// "0"或空表示未发运
			Date shipmentTime = parParcelinfo.getShipmentTime(); // 发运时间
			String parParcel = 1 + ""; // 包裹数量
			String marker = parParceladdress.getMarker();// 大头笔
			// -----------------------------------------------------------//
			String netName = memberInfoMapperV4.findNetNameByNetId(netId);// 快递网络名称
			String sendAddress = parParceladdress.getSendAddress();// 发件人地址
			String sendMobile = parParceladdress.getSendMobile();// 发件人电话
			String sendName = parParceladdress.getSendName();// 发件人name
			map.put("code", parParcelinfo.getCode());
			map.put("comment", comment);
			map.put("shipmentRemark", shipmentRemark);
			map.put("shipmentTime", shipmentTime);
			map.put("parParcel", parParcel);// 包裹数量
			map.put("marker", marker);
			map.put("parcelRemark", parcelRemark);
			map.put("addresseeAddress", parParceladdress.getAddresseeAddress());
			map.put("addresseeMobile", parParceladdress.getAddresseeMobile());
			map.put("addresseeName", parParceladdress.getAddresseeName());
			map.put("sendAddress", sendAddress);
			map.put("sendMobile", sendMobile);
			map.put("sendName", sendName);
			// map.put("chareWeightFortransit", chareWeightFortransit);
			map.put("freightPaymentMethod", freightPaymentMethod);
			map.put("freight", freight);
			map.put("expWaybillNum", expWaybillNum1);
			map.put("netName", netName);
			map.put("uid", uid);
			map.put("createTime", pickupTime);
			// map.put("createTime", createTime);
			map.put("actualTakeMember", actualTakeMember);
			listMap.add(map);
		}
		ppMap.put("listMap", listMap);
		ppMap.put("listMapNum", num);
		return ppMap;
	}

	// 揽收记录列表 2017-2-18 最新版
	@Override
	public Map<String, Object> queryRecRecordList(String memberId, String date,
			String phone, String expWaybillNum, String compId,
			Integer currentPage, Integer pageSize) {
		logger.info("揽收记录查询 queryRecRecordList==memberId:" + memberId
				+ "= date:" + date + "=phone:" + phone + "=expWaybillNum: "
				+ expWaybillNum);
		Query query = null;
		// mogon 格式话日期
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;		
		try {
			String d1 = date + " 00:00:00";
			String d2 = date + " 23:59:59";
			start = sim.parse(d1);
			end = sim1.parse(d2);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		List<ParParcelconnection> listParcel = null;
		query = new Query();
		if (!PubMethod.isEmpty(compId)) {//判断是个人还是本站全部
			query.addCriteria(Criteria.where("recCompId").is(Long.valueOf(compId)));
		} else {
			query.addCriteria(Criteria.where("recMemberId").is(Long.valueOf(memberId)));			
		}
		if (!PubMethod.isEmpty(phone)) {//发件人手机号查询
			query.addCriteria(Criteria.where("mobilePhone").regex(phone));
			/*Calendar ca = Calendar.getInstance();
			Date now = new Date();三个月内数据查询
			ca.add(Calendar.MONTH, -3);
			Date before = ca.getTime();
			query.addCriteria(Criteria.where("createTime").lte(now).gte(before));//<=now>=before*/
		} else if (!PubMethod.isEmpty(expWaybillNum)) {// 增加运单号查询条件2016年12月21日
			query.addCriteria(Criteria.where("expWaybillNum").regex(expWaybillNum));		
		}
		query.addCriteria(Criteria.where("createTime").gte(start).lte(end)); // 非精确条件查询时间为当天		
		query.with(new Sort(Direction.DESC, "createTime"));// 根据活动开始时间排序
		query.addCriteria(Criteria.where("recCosignFlag").is((short) 1));// 已取
        logger.info("揽收记录查询 queryRecRecordList query: " + query);
		listParcel = mongoTemplate.find(query, ParParcelconnection.class);
		List<Map<String, Object>> listMap = new ArrayList<>();
		String num = listParcel.size() + "";
		Map<String, Object> map = null;
		Map<String, Object> ppMap = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(currentPage)) {// 分页
			int fromIndex = (currentPage - 1) * pageSize > listParcel.size() ? listParcel
					.size() : (currentPage - 1) * pageSize;
			int toIndex = currentPage * pageSize > listParcel.size() ? listParcel
					.size() : currentPage * pageSize;
			listParcel = listParcel.subList(fromIndex, toIndex);
		}
		for (ParParcelconnection parParcelconnection : listParcel) {
			map = new HashMap<String, Object>();
			map.put("code", parParcelconnection.getCode()); // 取件标号			
			map.put("expWaybillNum", parParcelconnection.getExpWaybillNum());// 包裹运单号
			map.put("deliveryName", parParcelconnection.getDeliveryName());// 交付人姓名
			map.put("deliveryMobile", parParcelconnection.getDeliveryMobile());// 交付人电话
			map.put("deliveryUnits", parParcelconnection.getDeliveryUnits());// 交付单位
			map.put("createTime", parParcelconnection.getCreateTime());// 交付时间
			map.put("parId", parParcelconnection.getParId()); // 包裹id
			query = new Query();
			query.addCriteria(Criteria.where("uid").is(parParcelconnection.getParId()));
			ParParceladdress parParceladdress = mongoTemplate.findOne(query,ParParceladdress.class);
			if(PubMethod.isEmpty(parParceladdress)){
				map.put("netName", parParcelconnection.getNetName()); // 交付人网点名称
			}else{
				map.put("netName", parParceladdress.getNetName()); // 包裹的网点名称
			}			
			listMap.add(map);
		}
		ppMap.put("listMap", listMap);
		ppMap.put("listMapNum", num);
		return ppMap;
	}

	// 揽收详情 2017-2-18
	@Override
	public Map<String, Object> queryTakeRecordDetailed(String memberId, Long uid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(uid));
		ParParceladdress parParceladdress = mongoTemplate.findOne(query,
				ParParceladdress.class) == null ? new ParParceladdress()
				: mongoTemplate.findOne(query, ParParceladdress.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("netName", parParceladdress.getNetName());// 公司名称
		map.put("code", parParceladdress.getCode());// 揽收码--取件标号
		map.put("expWaybillNum", parParceladdress.getExpWaybillNum());// 运单号
		map.put("sendAddress", parParceladdress.getSendAddress());// 发件人地址
		map.put("sendMobile", parParceladdress.getSendMobile());// 发件人电话
		map.put("sendName", parParceladdress.getSendName());// 发件人名称
		map.put("addresseeAddress", parParceladdress.getAddresseeAddress());// 收件人地址
		map.put("addresseeMobile", PubMethod.isEmpty(parParceladdress.getAddresseePhone()) ? parParceladdress.getAddresseeMobile() : parParceladdress.getAddresseePhone());// 收件电话
		map.put("addresseeName", parParceladdress.getAddresseeName());// 收件人姓名
		map.put("shipmentRemark", parParceladdress.getShipmentRemark());// 是否发运
		map.put("shipmentTime", parParceladdress.getShipmentTime());// 发运时间
		map.put("uid", uid); // 包裹id
		map.put("num", 1);// 包裹数量
		map.put("pickupTime", parParceladdress.getPickupTime());// 包裹揽收时间
		map.put("pacelWeight", parParceladdress.getPacelWeight());// 包裹重量
		map.put("parcelType", parParceladdress.getParcelType());// 物品类型
		map.put("serviceName", parParceladdress.getServiceName());// 服务产品
		
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("parId").is(uid));
		ParParcelMark parParcelMark = mongoTemplate.findOne(query1,
				ParParcelMark.class)==null?null:mongoTemplate.findOne(query1,ParParcelMark.class);				
		List<ContentHis> content = new ArrayList<>();
		if(!PubMethod.isEmpty(parParcelMark)){
			content = parParcelMark.getContent();
		}					
		map.put("comment", content);// 标记备注
		query1.addCriteria(Criteria.where("recMemberId").is(Long.valueOf(memberId)));//接受人mid；
		query1.addCriteria(Criteria.where("recCosignFlag").is((short) 1));//揽收标示  1；
		ParParcelconnection parParcelconnection = mongoTemplate.findOne(query1,ParParcelconnection.class);

		map.put("deliveryAddress", parParcelconnection.getDeliveryAddress());// 交付地址
		map.put("deliveryMobile", parParcelconnection.getDeliveryMobile()); // 交付电话
		map.put("deliveryName", parParcelconnection.getDeliveryName());// 交付人姓名
		map.put("deliveryUnits", parParcelconnection.getDeliveryUnits());// 交付单位
		map.put("createTime", parParcelconnection.getCreateTime());// 交付时间
		return map;
	}

	// 2016-12-28 揽收报表
	//@SuppressWarnings("all")
	@Override
	public Map<String, Object> queryTakeforms(String memberId, String date,
			String all) {
		// mogon 格式话日期 date=2016-12 date=2016-11
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		int daysOfMonth = 0;// 得到天数
		try {
			daysOfMonth = getDaysOfMonth(sdf1.parse(date));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		int nMonth = 0;// 得到月份
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM");// 自定义时间格式
		try {
			Date a = dateformat.parse(date);// String转Date
			nMonth = a.getMonth() + 1;// 当前月份
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		try {
			String d1 = date + "-01" + " 00:00:00";
			String d2 = date + "-" + daysOfMonth + " 23:59:59";
			start = sim.parse(d1); // 每月第1日
			end = sim1.parse(d2); // 每月最后一日
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String dateNowStr = sdf.format(d);// 当前年月份

		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		logger.info("根据memberId:" + memberId + "查出来的memberinfo:"
				+ JSON.toJSONString(memberInfoVO == null ? "" : memberInfoVO));
		// String roleId =
		// memberInfoVO.getRoleId()==null?"0":memberInfoVO.getRoleId();// 角色
		Long compId = Long.valueOf(memberInfoVO.getCompId()) == null ? null
				: Long.valueOf(memberInfoVO.getCompId());// 站点id
		List<ParParcelinfo> listParcel = null;
		Query query = new Query();
		if (PubMethod.isEmpty(all)) {
			query.addCriteria(Criteria.where("actualTakeMember").is(
					Long.valueOf(memberId)));
			if (dateNowStr.equals(date)) {
				//Calendar ca = Calendar.getInstance();
				Date now = new Date();
				query.addCriteria(Criteria.where("pickupTime").gte(start).lte(now));// <=now >=start
			} else {
				query.addCriteria(Criteria.where("pickupTime").gte(start).lte(end));
			}
			// query.with(new Sort(Direction.DESC,"pickupTime"));//根据活动开始时间排序
			listParcel = mongoTemplate.find(query, ParParcelinfo.class);
		} else {
			listParcel = this.queryAllPar(compId);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<Integer, Integer> mapList = new HashMap<>();

		SimpleDateFormat sns = new SimpleDateFormat("d");
		long count = 0;// 每一天的包裹量
		for (ParParcelinfo parParcelinfo : listParcel) {
			Date pickup = parParcelinfo.getPickupTime() == null ? new Date()
					: parParcelinfo.getPickupTime();
			String pkp = sns.format(pickup); //
			Integer intPkp = Integer.valueOf(pkp);
			Integer nCount = mapList.get(intPkp) == null ? 0 : mapList
					.get(intPkp);
			mapList.put(intPkp, ++nCount);
		}
		Set<Integer> keySet = mapList.keySet();
		List<Integer> keyList = new ArrayList<>();
		for (Integer key : keySet) {
			keyList.add(key);
		}
		Collections.sort(keyList, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			};
		});

		Map<String, Object> resultMap = null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Integer key : keyList) {
			resultMap = new LinkedHashMap<>();
			resultMap.put("date", nMonth + "月" + key + "日");
			resultMap.put("count", mapList.get(key));
			listMap.add(resultMap);// 要数组
		}
		map.put("mapList", listMap);
		map.put("sum", listParcel.size());// 总数
		return map;
	}

	// 获取月份的天数 2016-12-28
	public int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	// 该站点下所有包裹List<ParParcelinfo>
	public List<ParParcelinfo> queryAllPar(Long compId) {
		// List<MemberInfo> list = assignPackage.queryAllParParcel(compId);//
		// 站点下所有收派员
		List<MemberInfo> list = assignPackage.queryMember(compId);// 站点下所有收派员
		List<ParParcelinfo> ParParcelinfo = new ArrayList<>();
		Query query = null;
		for (MemberInfo memberInfo : list) {
			Long memberId = memberInfo.getMemberId();
			query = new Query();
			query.addCriteria(Criteria.where("actualTakeMember").is(
					Long.valueOf(memberId)));
			query.with(new Sort(Direction.DESC, "pickupTime"));// 根据活动开始时间排序
			List<ParParcelinfo> find = mongoTemplate.find(query,
					ParParcelinfo.class) == null ? new ArrayList()
					: mongoTemplate.find(query, ParParcelinfo.class);
			ParParcelinfo.addAll(find);
			find = null;
		}
		return ParParcelinfo;
	}

	// 进入首页，查询当天的的取件记录
	@Override
	public Long queryTakePackageCount(String date, Long memberId) {
		logger.info("进入取件记录查询 queryTakeRecordList ======= 取件记录查询=====memberId: "
				+ memberId + " ====== date: " + date);
		Query query = new Query();
		// mogon 格式话日期
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sim1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		Date end = null;
		try {
			String d1 = date + " 00:00:00";
			String d2 = date + " 23:59:59";
			start = sim.parse(d1);
			end = sim1.parse(d2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// parcelStatus
		query.addCriteria(Criteria.where("actualTakeMember").is(
				Long.valueOf(memberId)));
		query.addCriteria(Criteria.where("parcelStatus").is((short) 1));

		query.addCriteria(Criteria.where("pickupTime").lte(end).gte(start));
		long count = mongoTemplate.count(query, ParParcelinfo.class);
		logger.info("取件包裹总量是：" + count);
		return count;
	}

	// 2017-1-13 标记备注
	@Override
	public List<String> findMarkComment(Long netId,Integer signType) {	
		//标记类型暂时取消，不区分取派件
       // Criteria criteria = Criteria.where("netId").is(netId).and("signType").is(signType).and("flag").is(0);
        
        Criteria criteria = Criteria.where("netId").is(netId).and("flag").is(0);
        Query query = Query.query(criteria);     
       List<ParcelSignInfo> list = mongoTemplate.find(query, ParcelSignInfo.class);//公司标记备注
       if (PubMethod.isEmpty(list)) {
    	   Criteria criterias = Criteria.where("netId").is(-1).and("flag").is(0);
           Query query2 = Query.query(criterias);      
           list = mongoTemplate.find(query2, ParcelSignInfo.class);//其他标记备注                  
       }    
       List<String> sign=new ArrayList<>();
       for(ParcelSignInfo signInfo:list){
    	   String signContent = signInfo.getSignContent();
    	   sign.add(signContent);
       }
       logger.info("取件标记备注："+sign);
       return sign;  
	}
	
	//服务类型
	@Override
	public List<String> serviceType(Long netId) {
		  Criteria criteria = Criteria.where("netId").is(netId).and("flag").is(0);
	       Query query = Query.query(criteria);     
	       List<ServiceTypeInfo> list = mongoTemplate.find(query, ServiceTypeInfo.class);
	       if (PubMethod.isEmpty(list)) {
	    	   Criteria criterias = Criteria.where("netId").is(-1).and("flag").is(0);
	           Query query2 = Query.query(criterias);      
	           list = mongoTemplate.find(query2, ServiceTypeInfo.class);               
	       }    
	       List<String> serName=new ArrayList<>();
	       for(ServiceTypeInfo ser:list){
	    	   String serviceName = ser.getServiceName();
	    	   serName.add(serviceName);
	       }
	       logger.info("服务产品："+serName);
	       return serName;  
	}
	// 自添加取件订单, 不带包裹的, 只是一个任务
	public String addNewReceiveOrder(String memberId, String senderName,
			String senderPhone, String addressDetail, Integer packNum,
			String tagContent) {
		logger.info("快递员自己添加订单memberId :" + memberId + " ,senderName: "
				+ senderName + " ,senderPhone: " + senderPhone
				+ " ,addressDetail: " + addressDetail + " ,packNum: " + packNum
				+ " ,tagContent: " + tagContent);

		// 1.生成一个任务
		// 2.生成一个任务监控
		// 3.生成一个任务信息表
		// 给这些包裹把任务taskId添加进去
		ParTaskInfo parTaskInfo = new ParTaskInfo();// 任务表
		long taskId = IdWorker.getIdWorker().nextId();// 任务id
		parTaskInfo.setUid(taskId);// 任务主键(任务id)-------------片键
		logger.info("快递员自己添加订单的taskId: " + taskId);
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		String memberPhone = memberInfoVO.getMemberPhone();
		parTaskInfo.setMobilePhone(memberPhone);// ---------做片键用
		parTaskInfo.setTaskType((byte) 0);// 0:取件
		parTaskInfo.setTaskStatus((byte) 1);// 任务状态0: 待处理, 1:已分配
		parTaskInfo.setTaskIsEnd((byte) 0);// 任务结束 0:未结束
		Long contactCompId = Long.valueOf(memberInfoVO.getCompId());
		parTaskInfo.setContactName(senderName);// 联系人name
		parTaskInfo.setContactMobile(senderPhone);// 联系人手机---片键
		parTaskInfo.setContactTel(senderPhone);// 联系人电话
		parTaskInfo.setContactAddress(addressDetail);// 联系人详细地址
		parTaskInfo.setContactCompId(contactCompId);// 联系人公司compId
		parTaskInfo.setActorMemberId(Long.valueOf(memberId));// 联系人memberId
		parTaskInfo.setPayStatus((short) 0);// 默认值0

		parTaskInfo.setCreateUserId(Long.valueOf(memberId));// 创建人memberId代收点的
		parTaskInfo.setTaskFlag((byte) 0);// 任务标志 0:正常单, 1:抢单

		parTaskInfo.setParEstimateCount(Byte.valueOf(packNum + ""));// 包裹数量
		// parTaskInfo.setModifyTime(new Date());//最后修改时间
		parTaskInfo.setCustomerId(Long.valueOf(memberId));// 发件人memberId代收点的
		parTaskInfo.setTaskSource((byte) 4);// 叫快递takeSource 4:好递个人端
		parTaskInfo.setComment(tagContent);// 传过来直接存, 传过来的理论上应该是用 | 隔开的
		try {
			mongoTemplate.insert(parTaskInfo);// 叫快递插入已完成任务记录
			ParTaskDisposalRecord parTaskDisposalRecord = new ParTaskDisposalRecord();// 任务记录表
			parTaskDisposalRecord.setUid(IdWorker.getIdWorker().nextId());// 主键id---------片键
			parTaskDisposalRecord.setDisposalType((byte) 0);// 0:待处理
			parTaskDisposalRecord.setTaskId(taskId);// 任务id
			parTaskDisposalRecord.setDisposalObject((byte) 0);// 派送员
			parTaskDisposalRecord.setShowFlag((byte) 1);// 1显示
			parTaskDisposalRecord.setTaskErrorFlag((byte) 0);// 异常标识 0:正常
			parTaskDisposalRecord.setCreateTime(new Date());// 创建时间
			parTaskDisposalRecord.setDisposalDesc("自添加取件订单----");
			parTaskDisposalRecord.setMemberId(Long.valueOf(memberId));
			mongoTemplate.insert(parTaskDisposalRecord);//
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("收派员自添加取件订单失败, 失败原因: " + e.getMessage());
			return "002";
		}
		return "001";
	}

	@Override
	public String tagOrder(String memberId, Long taskId, String tagContent) {
		logger.info("标记订单memberId: " + memberId + " , taskId: " + taskId
				+ " ,tagContent: " + tagContent);
		Query query = new Query();
		query.addCriteria(new Criteria("uid").is(taskId));
		ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,
				ParTaskInfo.class);
		logger.info("订单详情是否查询到parTaskInfo: " + parTaskInfo);
		String mobilePhone = parTaskInfo.getMobilePhone();
		query.addCriteria(new Criteria("mobilePhone").is(mobilePhone));
		if (!PubMethod.isEmpty(parTaskInfo)) {
			String comment = parTaskInfo.getComment();
			Update update = new Update();
			update.set("comment", comment + "|" + tagContent);
			update.set("tagFalg", (short) 1);// 1: 标记, 0:未标记
			WriteResult writeResult = mongoTemplate.updateFirst(query, update,
					"parTaskInfo");
			int n = writeResult.getN();
			logger.info("订单是否标记成功n: " + n + " ,标记内容为tagContent: " + tagContent);
			return "001";//
		}
		return "002";
	}

	@Override
	public void confirmDelivery(String uids) {
		String[] ids;
		try {
			ids = uids.split("-");
			for (String id : ids) {
				mongoTemplate.updateFirst(
						Query.query(Criteria.where("uid").is(
								Long.parseLong(id.trim()))),
						Update.update("shipmentRemark", "1").set(
								"shipmentTime", new Date()),
						ParParcelinfo.class);
			}
		} catch (Exception e) {
			throw new ServiceException("002", "UIDS解析异常");
		}
	}
	//取消订单===new
	@Override
	public String operationOrderStatus(String memberId, Long taskId,
			String ancelReason) {

		try {
			logger.info("订单取消memberId: " + memberId + " ,taskId: " + taskId+ " ,取消原因ancelReason: " + ancelReason);

			// 1.找到该订单
			Query query = new Query();
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(taskId)));
			ParTaskInfo parTaskInfo = mongoTemplate.findOne(query,ParTaskInfo.class);
			String mobilePhone = parTaskInfo.getMobilePhone();// 联系人手机====片键
			query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
			Long actorMemberId = parTaskInfo.getActorMemberId();// 收派员的memberId
			String actorPhone = parTaskInfo.getActorPhone();// 收派员的phone
			Update update = new Update();
			update.set("taskStatus", (byte) 3);// 任务已取消
			update.set("taskIsEnd", (byte) 1);// 包裹结束标志
			update.set("taskEndTime", new Date());// 最后结束时间
			update.set("cancelReason", ancelReason);// 取消原因
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			logger.info("更新包裹任务表成功");
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("taskId").is(Long.valueOf(taskId)));
			update = null;
			update = new Update();
			update.set("disposalType", (byte) 3);// 已取消
			update.set("modifiedTime", new Date());// 最后修改时间
			mongoTemplate.updateFirst(query, update,ParTaskDisposalRecord.class);// 修改任务信息表

			// 根据任务taskId查询出下面的包裹
			query = null;
			query = new Query();
			query.addCriteria(Criteria.where("takeTaskId").is(
					Long.valueOf(taskId)));// 任务id
			query.addCriteria(Criteria.where("parcelStatus").is((short)0));// 0：待取件
			// query.addCriteria(Criteria.where("cpReceiptStatus").is((short)
			// 1));//未付款
			List<ParParcelinfo> listParcel = mongoTemplate.find(query,
					ParParcelinfo.class);
			// 包裹为空直接return回去
			logger.info("根据taskId: " + taskId + " 查询包裹是否存在listParcel: "
					+ listParcel);
			if (PubMethod.isEmpty(listParcel)) {
				return "001";// 取消订单成功
			}
			// 遍历包裹,更改相关的信息
			for (ParParcelinfo parParcelinfo : listParcel) {
				logger.info("包裹id: " + parParcelinfo.getUid());
				// 包裹中把任务taskId清空(取消任务清空包裹中的taskId)
				Long parcelId = parParcelinfo.getUid();
				String mobilePhone1 = parParcelinfo.getMobilePhone();
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("uid").is(parcelId));
				query.addCriteria(Criteria.where("mobilePhone")
						.is(mobilePhone1));
				update = null;
				update = new Update();
				/**
				 * update.set("actualTakeMember", Long.valueOf(memberId));
				 * update.set("takeTaskId", null); update.set("parcelStatus",
				 * (short)0);//包裹状态 0:待取件
				 */
				update.set("actualTakeMember", parTaskInfo.getCustomerId());// 取消交寄还需要把代收点的memberId弄过来
				update.set("takeTaskId", "");// 解除包裹好任务的关联关系
				update.set("parcelStatus", (short) 0);// 0:待取件
				update.set("cpTakeTaskId", (short) 0);// 0:未付
				mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
				logger.info("更新包裹信息表成功");
				// 更改包裹的物权信息
				ParParceladdress parParceladdress = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(parcelId)), ParParceladdress.class);
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("uid").is(parcelId));
				query.addCriteria(Criteria.where("addresseeMobile").is(parParceladdress.getAddresseeMobile()));//包裹地址表的片键=====
				update = null;
				update = new Update();
				update.set("controllerMemberId", parTaskInfo.getCustomerId());// 将物权人信息设置为发件人
				this.mongoTemplate.updateFirst(query, update,
						ParParceladdress.class);
				logger.info("更新包裹地址表成功");
				// 转单插入一条 parparcelconnection 收派过程信息表监控表
				ParParcelconnection parcelconnection = new ParParcelconnection();
				// 设置相关的信息
				parcelconnection.setUid(IdWorker.getIdWorker().nextId());// id
				parcelconnection.setCreateTime(new Date());// 时间
				parcelconnection.setRecCosignFlag((short) 5);// 取消订单
				parcelconnection.setExpMemberId(Long.parseLong(memberId));// 交付人的memberid
				parcelconnection.setTaskId(taskId);// 任务id
				parcelconnection.setCosignFlag((short) 5);// 取消订单
				// 查询交付人的信息
				MemberInfoVO memberInfoVO1 = this.getValByMemberId(memberId);
				parcelconnection.setCompId(Long.parseLong(memberInfoVO1.getCompId()));// 交付人的compid
				parcelconnection.setNetId(Long.parseLong(memberInfoVO1.getNetId()));// 交付人的netid
				parcelconnection.setMobilePhone(mobilePhone);// 收件人手机号
				this.mongoTemplate.insert(parcelconnection);// 插入一条包裹收派信息记录表
				logger.info("插入一条包裹收派信息记录表");
			}
			//如果该订单的来源是微信,给微信端发送推送取消原因
			if ((byte)6==parTaskInfo.getTaskSource()) {
				Map<String,String> param=new HashMap<String, String>();
				param.put("cancelReason",ancelReason );//取消原因
				param.put("orderNumber", String.valueOf(taskId));//任务id
				param.put("openId", parTaskInfo.getThirdId());//任务id
				//String openId, String orderNumber, String cancelReason
				logger.info("取消订单时想向微信端推送消息的参数:"+taskId+",取消订单的原因是:"+ancelReason+",openId:"+parTaskInfo.getThirdId());
				String result=noticeHttpClient.pushCancelReason(param);
				logger.info("向微信端推送消息的结果:"+result);
			}
		} catch (Exception e) {
			return "002";// 002代表取消订单失败
		}
		return "001";// 001代表取消订单成功
	}

	@Override
	public String finishOrder(String memberId, Long taskId, String parcelIds,
			String sendName, String sendPhone, String sendAddress,
			String netId, String netName, String deliveryAddress,
			String addresseeName, String addresseePhone,
			String addresseeAddress, String expWaybillNum, String marker,
			String bourn, String code, String comment, String roleId) {

		try {
			// if(!PubMethod.isEmpty(parcelIds)){//调用揽收方法
			// this.takeReceivePackageByMembers( memberId, sendName,sendPhone,
			// sendAddress, netId,roleId,deliveryAddress,
			// addresseeName, addresseePhone, addresseeAddress, expWaybillNum,
			// bourn, code, comment,netName);
			//
			// }
			Query query = new Query();
			query.addCriteria(Criteria.where("actorMemberId").is(memberId));// memberId
			// query.addCriteria(Criteria.where("taskIsEnd").is((byte) 0));//
			// 包裹未结束
			query.addCriteria(Criteria.where("uid").is(taskId));// 任务id
			// query.addCriteria(Criteria.where("taskStatus").is((byte)
			// 1));//0待处理，1已分配，2已完成，3已取消，4微信叫快递-快递员拒绝接单 10已删除
			logger.info("查询订单，query=" + query);
			// ParTaskInfo parTask =
			// mongoTemplate.findOne(query,ParTaskInfo.class);
			Update update = new Update();
			update.set("taskStatus", (byte) 2);
			update.set("taskIsEnd", (byte) 1);
			mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.info("收派员结束订单失败, 失败原因: " + e.getMessage());
			return "002";// 结束订单失败
		}
		return "001";// 订单结束成功
	}
	//查看包裹详情
	@Override
	public Map<String, Object> checkParcelDetail(Long parcelId,
			String contactAddress, Long netId, String netName,
			String contactMobile, String contactName) {
		// 根据包裹id查询出包裹详情
		Map<String, Object> map = new HashMap<>();
		map.put("netId", netId);// 网络id'
		map.put("netName", netName);// 网络名称
		map.put("parcelId", parcelId);// 包裹id
		map.put("expWaybillNum", "");// 运单号
		map.put("code", "");// 取件标号
		map.put("contactName", "");// 寄件人
		map.put("contactMobile", "");// 寄件人手机号
		map.put("contactAddress", "");// 寄件人详细地址
		map.put("addresseeName", "");// 收件人的姓名
		map.put("addresseeAddress", "");// 收件人的详细地址
		map.put("addresseeMobile", "");// 收件人的手机号
		// ParParcelinfo parParcelinfo =
		// this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(parcelId)),
		// ParParcelinfo.class);
		// 查询包裹地址表
		ParParceladdress parParceladdress = this.mongoTemplate.findOne(
				Query.query(Criteria.where("uid").is(parcelId)),
				ParParceladdress.class);
		if (!PubMethod.isEmpty(parParceladdress)) {
			map.put("expWaybillNum", parParceladdress.getExpWaybillNum());
			map.put("code", parParceladdress.getCode());// 取件标号
			
			map.put("contactName", parParceladdress.getSendName());// 发件人的姓名
			map.put("contactAddress", parParceladdress.getSendAddress());// 发件人的详细地址
			map.put("contactMobile", parParceladdress.getSendMobile());// 发件人的手机号
			
			map.put("addresseeName", parParceladdress.getAddresseeName());// 收件人的姓名
			map.put("addresseeAddress", parParceladdress.getAddresseeAddress());// 收件人的详细地址
			map.put("addresseeMobile", parParceladdress.getAddresseeMobile());// 收件人的手机号
		}
		return map;
	}
	//订单中保存包裹信息
	@Override
	public void saveParcelInfo(String mark, Long parcelId,
			String contactAddress, Long netId, String netName,
			String contactMobile, String contactName, String expWaybillNum,
			String code, String addresseeName, String addresseeAddress,
			String addresseePhone) {
		logger.info("订单中保存包裹信息:参数是:mark:"+mark+",parcelId:"+parcelId+",contactAddress:"+contactAddress+",netId:"+netId+",netName:"+netName
				+",contactMobile:"+contactMobile+",contactName:"+contactName+",expWaybillNum:"+expWaybillNum
				+"code:"+code+",addresseeName:"+addresseeName+",addresseeAddress:"+addresseeAddress+",addresseePhone:"+addresseePhone);
		// 更新包裹apraddress表
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("uid").is(parcelId));
		ParParceladdress parParceladdress = this.mongoTemplate.findOne(query1, ParParceladdress.class);
		query1.addCriteria(Criteria.where("addresseeMobile").is(parParceladdress.getAddresseeMobile()));
		Update update = new Update();
		update.set("sendName", contactName);
		update.set("sendAddress", contactAddress);
		update.set("sendMobile", contactMobile);
		update.set("expWaybillNum", expWaybillNum);
		update.set("code", code);
		update.set("addresseeName", addresseeName);
		update.set("addresseeAddress", addresseeAddress);
		//update.set("addresseeMobile", addresseePhone);//片键===不能修改
		update.set("netName", netName);
		update.set("netId", netId);
		this.mongoTemplate.updateFirst(query1, update, ParParceladdress.class);
		// 查询到包裹信息表
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(parcelId));
		ParParcelinfo parParcelinfo = this.mongoTemplate.findOne(query,ParParcelinfo.class);
		Long takeTaskId = parParcelinfo.getTakeTaskId();
		query=null;
		query=new Query();
		query.addCriteria(Criteria.where("uid").is(takeTaskId));
		ParTaskInfo parTaskInfo = this.mongoTemplate.findOne(query, ParTaskInfo.class);
		Long memberId = parTaskInfo.getActorMemberId();
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId.toString());
		String memberName = memberInfoVO.getMemberName();
		// 更新包裹标记内容表,如果标记内容是空,不添加
		if (!PubMethod.isEmpty(mark)) {
			query = null;
			query = new Query();
			query.query(Criteria.where("parId").is(parcelId));
			ParParcelMark parParcelMark = this.mongoTemplate.findOne(query,
					ParParcelMark.class);
			if (!PubMethod.isEmpty(parParcelMark)) {
				update = null;
				update = new Update();
				// 添加备注
				parParcelMark.addContent(mark, memberName);
				update.set("markTime", new Date());
				update.set("content", parParcelMark.getContent());
				update.set("markMemberId", memberId);
				this.mongoTemplate.updateFirst(query, update, ParParcelMark.class);
			}else {
				//如果找不到这个对象数据,就重新插入一条数据
				ParParcelMark mark2=new ParParcelMark();
				mark2.addContent(mark, memberName);
				mark2.setExpWaybillNum(expWaybillNum);
				mark2.setMarkMemberId(memberId);
				mark2.setMarkTime(new Date());
				mark2.setParId(parcelId);
			}
		}
	}

	// 确认取件
	@Override
	public String confirmTakeParcel(String uids, Long memberId, String taskId,String flag) {
		
		String result="001";
		ParTaskInfo parTaskInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(taskId))), ParTaskInfo.class);
		///如果flag是1 说明是直接从订单列表页直接确认取件，则根据taskid，查询挂在该订单下面的所有包裹
		if ("1".equals(flag)) {
			//拿到所有的包裹id'//根据taskid查询所属的包裹
			Query query = new Query();
			query.addCriteria(Criteria.where("takeTaskId").is(
					Long.valueOf(taskId)));// 任务id
			query.addCriteria(Criteria.where("parcelStatus").is((short)0));// 0：待取件
			List<ParParcelinfo> listParcel = mongoTemplate.find(query,
					ParParcelinfo.class);
			if (PubMethod.isEmpty(listParcel)) {
				result="002";
				return result;
			}
			StringBuffer bf =new StringBuffer();
			for (int i = 0; i < listParcel.size(); i++) {
				if (i==listParcel.size()-1) {
					bf.append(listParcel.get(i).getUid());
				}else {
					
					bf.append(listParcel.get(i).getUid()).append("-");
				}
			}
			uids=bf.toString();
			// 包裹为空直接return回去
			logger.info("根据taskId: " + taskId + " 查询包裹是否存在listParcel: "
					+ listParcel+",uids:"+bf.toString());//根据taskid查询所属的包裹
		}
		String[] ids = uids.split("-");
		logger.info("确认取件的包裹ids:" + uids + ",memberid:" + memberId + ",taskid:"
				+ taskId);
		for (String parcelId : ids) { 
			try {
				/**查询包裹中交付人的信息**************************************/
				ParParcelinfo parParcelinfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parcelId))), ParParcelinfo.class);
				ParParceladdress one = this.mongoTemplate.findOne(
						Query.query(Criteria.where("uid").is(Long.parseLong(parcelId))),ParParceladdress.class);
				Long controllerMemberId = one.getControllerMemberId();// 原来的物权所有人
				String sendName = one.getSendName();//发件人姓名
				String sendAddress = one.getSendAddress();//发件人的地址
				String sendMobile = one.getSendMobile();//发件人的手机号
				String expWaybillNum = one.getExpWaybillNum();//包裹的运单号
				String netName = one.getNetName();//网络名称
				String code = one.getCode();//取件编号
				String controllerCompName = one.getControllerCompName();//交付单位
				String addresseeMobile = one.getAddresseeMobile();//片键======
				/***修改包裹信息表中的信息*******************************************/
				// 根据包裹id,查询包裹,修改包裹信息表
				Query query = new Query();
				Update update = new Update();
				query.addCriteria(Criteria.where("uid").is(
						Long.parseLong(parcelId)));
				
					query.addCriteria(Criteria.where("takeTaskId").is(
							Long.parseLong(taskId)));
				
				query.addCriteria(Criteria.where("mobilePhone").is(
						parParcelinfo.getMobilePhone()));//包裹信息表中的片键====
				update.set("actualTakeMember", memberId);//实际取件人
				update.set("parcelStatus", (short) 1);//已取件
				this.mongoTemplate.updateFirst(query, update,
						ParParcelinfo.class);
				logger.info("修改包裹信息表成功");
				/***修改包裹地址表**************************************/
				// 修改包裹地址表
				query = null;
				query = new Query();
				query.addCriteria(Criteria.where("uid").is(
						Long.parseLong(parcelId)));
				query.addCriteria(Criteria.where("addresseeMobile").is(
						addresseeMobile));//更新操作中必须查询片键,而且不能被修改
				update = null;
				update = new Update();
				update.set("precontrollermemberid",controllerMemberId );//原来的物权所有人
				update.set("pickupTime", new Date());// 取件时间
				update.set("controllerMemberId", memberId);
				update.set("controllerTaskFlag", (short) 1);// 已取件--未发货
				
				this.mongoTemplate.updateFirst(query, update,
						ParParceladdress.class);
				logger.info("修改包裹地址表成功");
				/***插入一条包裹信息记录表**************************************/
				// 转单插入一条 parparcelconnection 收派过程信息表监控表
				ParParcelconnection parcelconnection = new ParParcelconnection();
				parcelconnection.setUid(IdWorker.getIdWorker().nextId());// id
				parcelconnection.setParId(Long.parseLong(parcelId));//包裹id
				//更新交付人的信息
				logger.info("查询交付人的信息是:sendName:"+sendName+",controllerCompName:"+controllerCompName+",expWaybillNum:"+expWaybillNum+",sendAddress:"+sendAddress+",sendMobile:"+sendMobile+",netName:"+netName+",controllerCompName:"+controllerCompName+",code:"+code);
				parcelconnection.setDeliveryName(sendName);//交付人姓名
				parcelconnection.setDeliveryAddress(sendAddress);//交付人地址
				parcelconnection.setDeliveryMobile(sendMobile);//交付人手机号
				parcelconnection.setMobilePhone(sendMobile);//交付人手机号
				parcelconnection.setExpWaybillNum(expWaybillNum);//包裹运单号
				parcelconnection.setNetName(netName);//网络名称
				parcelconnection.setTaskId(Long.parseLong(taskId));//任务号
				
				parcelconnection.setCosignFlag((short)2);//派件
				parcelconnection.setCreateTime(new Date());//创建时间
				parcelconnection.setDeliveryUnits(controllerCompName);//交付单位
				parcelconnection.setCode(code);//取件编号
				//设置接收人的信息
				MemberInfoVO memberInfoVO = this.getValByMemberId(memberId+"");
				//接收人的compid和netid
				Long recCompId=null ;
				Long recNetId=null;
				String recName =null;
				String recMobile = null;
				String recnetName=null;
				String compName =null;
				if (!PubMethod.isEmpty(memberInfoVO)) {
					recCompId = Long.parseLong(memberInfoVO.getCompId());
					recNetId =Long.parseLong(memberInfoVO.getNetId());
					recName = memberInfoVO.getMemberName();
					recMobile = memberInfoVO.getMemberPhone();
					recnetName = memberInfoVO.getNetName();
					compName = memberInfoVO.getCompName();
				}
				parcelconnection.setRecMemberId(memberId);//接收人id
				parcelconnection.setRecName(recName);//接收人姓名
				parcelconnection.setRecMobile(recMobile);//接收人手机
				parcelconnection.setRecnetName(recnetName);//接收人网络名称
				parcelconnection.setRecUnits(compName);//接收人单位
				parcelconnection.setRecCompId(recCompId);//接收人公司id
				parcelconnection.setRecNetId(recNetId);//接收人网络id
				parcelconnection.setRecCosignFlag((short)1);//揽收

				logger.info("查询接收人的信息是:recCompId:"+recCompId+",recNetId:"+recNetId+",recName:"+recName+",recMobile:"+recMobile+",recnetName:"+recnetName+",compName:"+compName+",recCompId:"+recCompId+",recNetId:"+recNetId);

				this.mongoTemplate.insert(parcelconnection);// 插入一条包裹收派信息记录表
				logger.info("包裹收派信息记录表成功插入一条记录");
				//插入一条包裹报表数据
				logger.info("插入一条包裹报表数据,参数是:memberId:"+memberId+",recNetId:"+recNetId+"recCompId:"+recCompId);
				Date date = new Date();
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = sim.format(date);
				this.insertPackageReport(memberId+"", recNetId+"", recCompId+"",dateString);

			} catch (Exception e) {
				logger.info("取件失败抛出的异常:" + e);
				result="002";//取件失败
				continue;// 跳过这个包裹
			

		}

		// 判断订单中是否还有包裹,如果没有就更改订单状态
		
		List<ParParcelinfo> list = this.mongoTemplate.find(
				Query.query(Criteria.where("takeTaskId")
						.is(Long.parseLong(taskId)).and("parcelStatus")
						.is((short) 0)), ParParcelinfo.class);
			if (null == list || list.size() == 0) {
				Query query = new Query();
				query.addCriteria(Criteria.where("uid").is(Long.parseLong(taskId)));
				query.addCriteria(Criteria.where("mobilePhone").is(parTaskInfo.getMobilePhone()));//包裹任务表里的片键-====
				Update update = new Update();
				update.set("taskStatus", (byte) 2);// 任务已完成
				update.set("taskIsEnd", (byte) 1);// 任务结束标志
				update.set("taskEndTime", new Date());// 最后结束时间
				this.mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
			}
		}
	
		
		return result;// 取件成功
}
	//查询运单号是否唯一
	@Override
	public String checkPidIsUnique(String expWaybillNum) {
		Query query=new Query();
		query.addCriteria(Criteria.where("expWaybillNum").is(expWaybillNum));
		Long count = this.mongoTemplate.count(query, ParParceladdress.class);
		if (count>0) {
			return "002";//库中存在该运单号
		}
		return "001";
	}

	
}