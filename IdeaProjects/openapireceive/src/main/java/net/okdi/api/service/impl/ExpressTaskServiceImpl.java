package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.EnterpriseAddressInfoMapper;
import net.okdi.api.dao.EnterpriseCooperationSiteMapper;
import net.okdi.api.dao.EnterpriseInfoMapper;
import net.okdi.api.dao.EnterpriseShopInfoMapper;
import net.okdi.api.dao.EnterpriseUserInfoMapper;
import net.okdi.api.entity.EnterpriseAddressInfo;
import net.okdi.api.entity.EnterpriseCooperationSite;
import net.okdi.api.entity.EnterpriseInfo;
import net.okdi.api.entity.EnterpriseShopInfo;
import net.okdi.api.entity.EnterpriseUserInfo;
import net.okdi.api.entity.ExpCustomerInfo;
import net.okdi.api.entity.ExpCustomerMemberRelation;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.ParTaskInfo;
import net.okdi.api.service.CompInfoService;
import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.api.service.ExpressTaskService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class ExpressTaskServiceImpl implements ExpressTaskService {

	public final Byte task_source_ec = 3; //来源是电商管家：与创建任务的接口一致
	public final Byte task_type_take = 0; //类型是取件：与任务接口一致

	@Autowired
	private TakeTaskService takeTaskService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private SendNoticeService sendNoticeService;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private ExpCustomerInfoService expCustomerInfoService;
	@Autowired
	private CompInfoService compInfoService;
	@Autowired
	private ParcelInfoService parcelInfoService;

	/**以下需要添加企业表的数据信息**/
	@Autowired
	private EnterpriseAddressInfoMapper enterpriseAddressInfoMapper;
	@Autowired
	private EnterpriseCooperationSiteMapper enterpriseCooperationSiteMapper;
	@Autowired
	private EnterpriseInfoMapper enterpriseInfoMapper;
	@Autowired
	private EnterpriseShopInfoMapper enterpriseShopInfoMapper;
	@Autowired
	private EnterpriseUserInfoMapper enterpriseUserInfoMapper;
	/**以上需要添加企业表的数据信息**/
	@Autowired
	private ConstPool constPool;
	@Autowired 
	private NoticeHttpClient noticeHttpClient;

	boolean isAddedAsked = true;

	/**
	 * @Method: createTaskSimple 创建任务
	 * @param accessType	"0,1,2"分别代表0 手机,1compId ,2 memberId
	 * @param data			收派员电话/compId/memberId
	 * @param memberId		电商开发者注册的memberid
	 * @param address		地址详细信息
	 * @param packageNum	包裹数量
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发送人手机号
	 * @param senderName	发送人姓名
	 * @param erpId			电商id
	 * @param allsms		是否全短信模式0 全部发短信 1 未注册收派员发短信，注册收派员发推送:但是有一点， 传给后台的时候需要转义
	 * @return
	 * @throws Exception 
	 * @see net.okdi.api.service.ExpressTaskService#createTaskSimple(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> createTaskSimple(String accessType, String data, String memberId, String address,
			String packageNum, String packageWeight, String senderMobile, String senderName, String erpId,String allsms,String takeTime)
			throws Exception {
		if("0".equals(allsms)){//
			allsms = "sms";
		}else{
			allsms = "push";
		}
		if ("0".equals(accessType)) {//手机
			String phone = data;
			return createTaskByPhone(phone, memberId, address, packageNum, packageWeight, senderMobile, senderName,
					erpId,allsms,takeTime);
		} else if ("1".equals(accessType)) {//公司Id
			Long compId = Long.valueOf(data);
			return createTaskByCompId(compId, memberId, address, packageNum, packageWeight, senderMobile, senderName,
					erpId,allsms);
		} else if ("2".equals(accessType)) {//memberId
			Long expMemberId = Long.valueOf(data);
			Long compId = null;
			Long expNetId = null;
			try {
				//根据memberId取出这个人注册的信息
				MemberInfo memberInfo = memberInfoService.getMemberInfoById(expMemberId);
				compId = memberInfo.getCompId();
				Map map = compInfoService.queryCompBasicInfo(memberInfo.getCompId());
				expNetId = Long.valueOf(map.get("netId").toString());
			} catch (Exception e) {
				throw new ServiceException("查询收派员基本信息的时候出错！");
			}
			
			Map<String, Object> createTaskByMemberId = createTaskByMemberId(expMemberId, expNetId, compId, memberId, address, packageNum, packageWeight,
					senderMobile, senderName, erpId,allsms, takeTime);
		    
			try {
				noticeHttpClient.saveMsg(senderMobile, "你好", Long.parseLong(memberId), accessType=="0"?data:"", Long.parseLong(createTaskByMemberId.get("taskId").toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return createTaskByMemberId;
		} else {
			throw new ServiceException("该状态不为系统内定状态0,1,2，请检查参数accessType");
		}

	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据手机号创建任务(可能只会发短信)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午2:54:34</dd>
	 * @param phone			收派员电话
	 * @param memberId		电商开发者注册的memberid
	 * @param address		地址详细信息
	 * @param packageNum	包裹数量
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发送人手机号
	 * @param senderName	发送人姓名
	 * @param erpId			电商id
	 * @return
	 * @throws Exception
	 * @since v1.0
	 */
	private Map<String, Object> createTaskByPhone(String phone, String memberId, String address, String packageNum,
			String packageWeight, String senderMobile, String senderName, String erpId,String allsms,String takeTime) throws Exception {
		Long expMemberId = null;
		Long expNetId = null;
		Long compId = null;
		//根据手机号查找是否已经注册
		List<Map<String, Object>> list = memberInfoService.queryMemberInfoForFhw(phone);
		try {
			expMemberId = Long.valueOf(String.valueOf(list.get(0).get("deliverId")));
			expNetId = Long.valueOf(String.valueOf(list.get(0).get("netId")));
			compId = Long.valueOf(String.valueOf(list.get(0).get("compId")));
		} catch (Exception e) {
			list = null;
		}
		if (list == null || list.size() == 0) {
			//线程池启动发送短信
			String noticeTakeTime = takeTime;
			if(PubMethod.isEmpty(noticeTakeTime)){
				 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				 noticeTakeTime = sdf.format(new Date());
			}
			taskExecutor.execute(new ExpSmsThread(phone, senderName, senderMobile, address,noticeTakeTime));
			Map m = new HashMap();
			m.put("taskId", "");
			return m;
		}

		return createTaskByMemberId(expMemberId, expNetId, compId, memberId, address, packageNum, packageWeight,
				senderMobile, senderName, erpId, allsms,takeTime);
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据memberId来创建任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午3:16:27</dd>
	 * @param expMemberId		收派员id
	 * @param expNetId			网点id
	 * @param compId			站点id
	 * @param memberId			电商开发者注册的memberid
	 * @param address			地址
	 * @param packageNum		包裹数量
	 * @param packageWeight		包裹重量
	 * @param senderMobile		发送人手机号
	 * @param senderName		发送人姓名
	 * @param erpId				电商id
	 * @return
	 * @throws Exception
	 * @since v1.0
	 */
	private Map<String, Object> createTaskByMemberId(Long expMemberId, Long expNetId, Long compId, String memberId,
			String address, String packageNum, String packageWeight, String senderMobile, String senderName,
			String erpId,String allsms,String takeTime) throws Exception {
		List<EnterpriseInfo> lsErp = null;
		Long customerId = null;
		//以下是增加企业信息的
		if (isAddedAsked) {
			if (!PubMethod.isEmpty(erpId)) {
				lsErp = enterpriseInfoMapper.selectByErpId(Long.valueOf(erpId));
				if (lsErp == null || lsErp.size() == 0) {
					
					//插入企业信息表
					Long enterpriseId = IdWorker.getIdWorker().nextId();
					EnterpriseInfo record = new EnterpriseInfo();
					record.setEnterpriseId(enterpriseId);
					record.setEnterpriseSource(Long.valueOf(memberId));
					record.setPlatformEnterpriseId(erpId);
					enterpriseInfoMapper.insert(record);

					//插入企业地址信息表
					EnterpriseAddressInfo enterpriseAddressInfo = new EnterpriseAddressInfo();
					Long enterpriseAddressId = IdWorker.getIdWorker().nextId();
					enterpriseAddressInfo.setEnterpriseAddressId(enterpriseAddressId);
					enterpriseAddressInfo.setEnterpriseId(enterpriseId);
					enterpriseAddressInfo.setDetailedAddresss(address);
					enterpriseAddressInfo.setContactPhone1(senderMobile);
					enterpriseAddressInfoMapper.insert(enterpriseAddressInfo);

					//插入企业用户信息表
					EnterpriseUserInfo enterpriseUserInfo = new EnterpriseUserInfo();
					Long enterpriseUserId = IdWorker.getIdWorker().nextId();
					enterpriseUserInfo.setEnterpriseUserId(enterpriseUserId);
					enterpriseUserInfo.setEnterpriseId(enterpriseId);
					enterpriseUserInfo.setUserName(senderName);
					enterpriseUserInfoMapper.insert(enterpriseUserInfo);

					//插入企业店铺信息表
					EnterpriseShopInfo enterpriseShopInfo = new EnterpriseShopInfo();
					Long enterpriseShopId = IdWorker.getIdWorker().nextId();
					enterpriseShopInfo.setEnterpriseShopId(enterpriseShopId);
					enterpriseShopInfo.setEnterpriseId(enterpriseId);
					enterpriseShopInfoMapper.insert(enterpriseShopInfo);

					//插入企业合作快递信息表
					EnterpriseCooperationSite enterpriseCooperationSite = new EnterpriseCooperationSite();
					Long enterpriseCooperationSiteId = IdWorker.getIdWorker().nextId();
					enterpriseCooperationSite.setId(enterpriseCooperationSiteId);
					enterpriseCooperationSite.setEnterpriseShopId(enterpriseShopId);
					enterpriseCooperationSite.setEnterpriseId(enterpriseId);
					enterpriseCooperationSite.setExpMemberId1(expMemberId);
					enterpriseCooperationSiteMapper.insert(enterpriseCooperationSite);

					//重置erpId为本系统内的企业Id
					erpId = String.valueOf(enterpriseId);
					//增加客户信息
					customerId = addExpCustomerInfo(compId, expMemberId, senderMobile, senderName, erpId);
				} else {
					customerId = Long.valueOf(lsErp.get(0).getPlatformEnterpriseId());
				}
			}
		} else {
			customerId = addExpCustomerInfo(compId, expMemberId, senderMobile, senderName, erpId);
		}
		//以上是增加企业信息的

		return createTaskByMemberIdOrCompId(expMemberId, expNetId, compId, memberId, address, customerId, packageNum,
				packageWeight, senderMobile, senderName, false, allsms,takeTime);
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据站点Id创建任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午3:06:31</dd>
	 * @param compId		站点Id
	 * @param memberId		发起的memberId	
	 * @param address		地址详细信息
	 * @param packageNum	包裹数量
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发送人手机号
	 * @param senderName	发送人姓名
	 * @param erpId			电商id
	 * @return
	 * @throws Exception
	 * @since v1.0
	 */
	private Map<String, Object> createTaskByCompId(Long compId, String memberId, String address, String packageNum,
			String packageWeight, String senderMobile, String senderName, String erpId,String allsms) throws Exception {

		Map map = compInfoService.queryCompBasicInfo(compId);
		Long expNetId = Long.valueOf(map.get("netId").toString());
		Long customerId = null;
		return createTaskByMemberIdOrCompId(null, expNetId, compId, memberId, address, customerId, packageNum,
				packageWeight, senderMobile, senderName, true, allsms,null);
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加客户信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午3:15:24</dd>
	 * @param compId		站点id
	 * @param expMemberId	收派员id
	 * @param senderMobile	发送者手机号
	 * @param senderName	发送姓名
	 * @param erpId			电商id
	 * @return
	 * @since v1.0
	 */
	private Long addExpCustomerInfo(Long compId, Long expMemberId, String senderMobile, String senderName, String erpId) {
		if (PubMethod.isEmpty(erpId)) {
			return null;
		}
		//查询在这个站点下是否已经有了注册的客户Id，erpId
		List<ExpCustomerInfo> lsExp = expCustomerInfoService.queryByErpIdandCompId(compId, Long.valueOf(erpId));
		Long customerId = null;
		if (lsExp == null || lsExp.size() == 0) {//不存在erpId相关的客户，增加客户信息
			Short customerType = 0;
			String customerPhone = senderMobile;
			String customerName = senderName;
			Long townId = null;
			String townName = null;
			Long userId = null;
			String contactMsgs = null;
			//以下是expMemberIds要求的格式：[{"expmemberId":"111","sort":1}，{"expmemberId":"222","sort":2}]  
			String expMemberIds = "[{\"expmemberId\":\"" + expMemberId + "\",\"sort\":1}]";
			BigDecimal agencyFee = null;
			String detailedAddress = null;
			Long discountGroupId = null;
			Short settleType = null;
			customerId = Long.valueOf(expCustomerInfoService.insertCustomer(compId, customerName, customerType,
					customerPhone, Long.valueOf(erpId), townId, townName, detailedAddress, discountGroupId, agencyFee,
					settleType, expMemberIds, contactMsgs, userId,null));
		} else {//存在erpId相关的客户，客户收派员不存在的情况下，增加客户的收派员信息
			//查找收派员的信息
			List<ExpCustomerMemberRelation> lsExpMember = expCustomerInfoService.queryExpMembers(compId, lsExp.get(0)
					.getId());
			JSONArray ja = null;
			//只有在收派员信息不超过3个时才处理，超过3个不处理
			if (lsExpMember == null || lsExpMember.size() < 3) {
				boolean isExists = false;
				for (int i = 0; i < lsExpMember.size(); i++) {
					if (expMemberId.equals(lsExpMember.get(i).getExpMemberId())) {
						isExists = true;
						break;
					}
				}
				if (!isExists) {
					ja = new JSONArray();
					JSONObject jo = new JSONObject();
					jo.put("expmemberId", expMemberId);
					jo.put("sort", lsExpMember.size() + 1);
					expCustomerInfoService.insertExpList(compId, lsExpMember.get(0).getCustomerId(), ja.toJSONString());
				}
			}
			customerId = lsExpMember.get(0).getCustomerId();
		}
		return customerId;
	}
	
	
	/**
	 * 字段很多，简单描述下，如果有id，则包裹根据id更新包裹，如果有taskTaskId，则插入新表中
	 * @Method: saveParcelInfo 
	 * @param id				包裹id
	 * @param takeTaskId		取件任务id
	 * @param sendTaskId		派件任务id
	 * @param senderUserId		发货方memberID
	 * @param sendCustomerId	发件客户ID
	 * @param addresseeCustomerId	地址客户id
	 * @param sendCasUserId			发件人CASID
	 * @param addresseeCasUserId	收件人CASID 
	 * @param expWaybillNum			包裹运单号
	 * @param compId				公司id
	 * @param netId					网络id
	 * @param addresseeName			收件人姓名
	 * @param addresseeMobile		收件人手机号码
	 * @param addresseeAddressId	收件人乡镇id
	 * @param addresseeAddress		收件人详细地址
	 * @param sendName				发件人姓名
	 * @param sendMobile			发件人手机
	 * @param sendAddressId			发件人乡镇id
	 * @param sendAddress			发件人详细地址	
	 * @param chareWeightForsender	包裹重量
	 * @param freight				包裹应收运费
	 * @param goodsPaymentMethod	支付方式	
	 * @param codAmount				代收货款金额
	 * @param insureAmount			保价金额
	 * @param pricePremium			保价费
	 * @param packingCharges		包装费	
	 * @param freightPaymentMethod	运费支付方式
	 * @param sendLongitude			发件人地址经度
	 * @param sendLatitude			发件人地址纬度
	 * @param addresseeLongitude	收件人地址经度
	 * @param addresseeLatitude		收件人地址纬度
	 * @param goodsDesc				产品描述
	 * @param parcelRemark			包裹备注
	 * @param serviceId				服务产品ID
	 * @param signMember			签收人
	 * @param createUserId			创建人id
	 * @param parcelEndMark			包裹结束标志
	 * @param actualTakeMember		收件人id
	 * @param actualSendMember		派件人id
	 * @param receiptId				收据id
	 * @param parcelStatus			包裹状态
	 * @param actualCodAmount		代收货款实际收到的货款金额
	 * @return 
	 * @see net.okdi.api.service.ExpressTaskService#saveParcelInfo(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.lang.Short, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.lang.Short, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Short, java.math.BigDecimal)
	 */
	@Override
	public Map<String, Object> saveParcelInfo(Long id, Long takeTaskId, Long sendTaskId, Long senderUserId,
			Long sendCustomerId, Long addresseeCustomerId, Long sendCasUserId, Long addresseeCasUserId,
			String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile,
			Long addresseeAddressId, String addresseeAddress, String sendName, String sendMobile, Long sendAddressId,
			String sendAddress, BigDecimal chareWeightForsender, BigDecimal freight, Short goodsPaymentMethod,
			BigDecimal codAmount, BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges,
			Short freightPaymentMethod, BigDecimal sendLongitude, BigDecimal sendLatitude,
			BigDecimal addresseeLongitude, BigDecimal addresseeLatitude, String goodsDesc, String parcelRemark,
			Long serviceId, String signMember, Long createUserId, String parcelEndMark, Long actualTakeMember,
			Long actualSendMember, Long receiptId, Short parcelStatus, BigDecimal actualCodAmount) {

		//首先查找该条任务是否已经存在
		ParTaskInfo pti = takeTaskService.cacheTaskInfo(String.valueOf(takeTaskId));
		if (pti == null) {
			throw new ServiceException("008", "任务id不存在");
		}

		compId = pti.getCoopCompId();
		netId = pti.getCoopNetId();
		sendCustomerId = pti.getCustomerId();
		
		//查找是否存在包裹
		Long parId = parcelInfoService.queryParcelInfoByExpWayBillNumAndNetId(netId, expWaybillNum);
		if (parId == null || Long.valueOf(0L).equals(parId)) {
			id = null;
		} else {
			id = parId;
		}

		return parcelInfoService.saveParcelInfo(id, takeTaskId, sendTaskId, senderUserId, sendCustomerId,
				addresseeCustomerId, sendCasUserId, addresseeCasUserId, expWaybillNum, compId, netId, addresseeName,
				addresseeMobile, addresseeAddressId, addresseeAddress, sendName, sendMobile, sendAddressId,
				sendAddress, chareWeightForsender, freight, goodsPaymentMethod, codAmount, insureAmount, pricePremium,
				packingCharges, freightPaymentMethod, sendLongitude, sendLatitude, addresseeLongitude,
				addresseeLatitude, goodsDesc, parcelRemark, serviceId, signMember, createUserId, parcelEndMark,
				actualTakeMember, actualSendMember, receiptId, parcelStatus, actualCodAmount,"");
	}

	/**
	 * 当给网点的时候compId为空
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午5:52:57</dd>
	 * @param expMemberId	收派员memberId
	 * @param expNetId		收派员memberId
	 * @param toCompId		收件人站点id
	 * @param memberId		发起人memberId
	 * @param address		地址
	 * @param customerId	客户id
	 * @param packageNum	包裹数量
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发送者手机号
	 * @param senderName	发送人姓名
	 * @param isComp		是否是站点发送
	 * @return
	 * @throws Exception
	 * @since v1.0
	 */
	private Map<String, Object> createTaskByMemberIdOrCompId(Long expMemberId, Long expNetId, Long toCompId,
			String memberId, String address, Long customerId, String packageNum, String packageWeight,
			String senderMobile, String senderName, boolean isComp,String allsms,String takeTime) throws Exception {

		Long fromCompId = null;
		if (isComp) {
			fromCompId = toCompId;
			toCompId = null;
		}
		Long fromMemberId = null;
		Long toMemberId = null;
		if (!PubMethod.isEmpty(expMemberId)) {
			toMemberId = Long.valueOf(expMemberId);
		}
		Long coopNetId = Long.valueOf(expNetId);
		
		String appointTime = null;
        if(!PubMethod.isEmpty(takeTime)){
        	String [] date = takeTime.split(":");
          Calendar ca = Calendar.getInstance();
          ca.setTime(new Date());
          ca.set(ca.HOUR_OF_DAY, Integer.parseInt(date[0]));
          ca.set(ca.MINUTE, Integer.parseInt(date[1]));
          ca.set(ca.SECOND, 0);
          Date dateNew = ca.getTime();
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  appointTime = sdf.format(dateNew);
        }
		String appointDesc = "";
		Long actorMemberId = null;
		if (!PubMethod.isEmpty(expMemberId)) {
			actorMemberId = Long.valueOf(expMemberId);
		}
		String contactName = senderName;
		String contactMobile = senderMobile;//电商手机号
		String contactTel = senderMobile;
		Long contactAddressId = 10L;//默认赋值为中国
		String contactAddress = address;
		Long createUserId = null;
		if (!PubMethod.isEmpty(memberId)) {
			createUserId = Long.valueOf(memberId);//电商memberId
		}
		BigDecimal contactAddrLongitude = null;
		BigDecimal contactAddrLatitude = null;
		BigDecimal parEstimateWeight = BigDecimal.valueOf(Double.valueOf(packageWeight));
		Byte parEstimateCount = Byte.valueOf(packageNum);

		return takeTaskService.create(fromCompId, fromMemberId, toCompId, toMemberId, coopNetId, appointTime,
				appointDesc, actorMemberId, contactName, contactMobile, contactTel, contactAddressId, contactAddress,
				customerId, createUserId, contactAddrLongitude, contactAddrLatitude, task_source_ec, task_type_take,
				null, null, parEstimateWeight, parEstimateCount, null, Byte.valueOf("0"),null,allsms);
	}

	/**
	 * 该内部类提供了线程发短信的支持
	 * @author chuanshi.chai
	 * @version V1.0
	 */
	class ExpSmsThread extends Thread {
		String phone = null;
		String senderName = null;
		String senderMobile = null;
		String address = null;
		String takeTime = null;

		public ExpSmsThread(String phone, String senderName, String senderMobile, String address,String takeTime) {
			this.phone = phone;
			this.senderName = senderName;
			this.senderMobile = senderMobile;
			this.address = address;
			this.takeTime = takeTime;
		}

		@Override
		public void run() {
			//发送短信
			sendNoticeService.asigntoExpMemberAmssy(null, null, null, null, senderName, senderMobile, address, null,
					phone, address,"sms",takeTime);
		}
	}
}