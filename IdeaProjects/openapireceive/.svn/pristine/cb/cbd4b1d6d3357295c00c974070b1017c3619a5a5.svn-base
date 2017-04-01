package net.okdi.apiV4.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompEmployeeResumeMapper;
import net.okdi.api.dao.BasEmployeeAuditMapper;
import net.okdi.api.dao.BasEmployeeRelationMapper;
import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.service.AreaElectronicFenceService;
import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.apiV1.dao.BasCompimageMapperV1;
import net.okdi.apiV2.dao.MemberInfoMapperV2;
import net.okdi.apiV4.dao.ParParceladdressMapperV4;
import net.okdi.apiV4.dao.ParParcelinfoMapperV4;
import net.okdi.apiV4.dao.ParTaskInfoMapperV4;
import net.okdi.apiV4.entity.AccSrecpayvouchers;
import net.okdi.apiV4.entity.ParParcelRelation;
import net.okdi.apiV4.entity.ParParceladdress;
import net.okdi.apiV4.entity.ParParcelconnection;
import net.okdi.apiV4.entity.ParParcelinfo;
import net.okdi.apiV4.entity.ParTaskDisposalRecord;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.entity.SubJoinComp;
import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.apiV4.service.ProblemPackageService;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.common.page.Page;
import net.okdi.core.common.page.PageUtil;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DistanceUtil;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;
/**
 * @Project Name:springmvc 
 * @Package net.okdi.apiV4.service.impl  
 * @Title: SendPackageServiceImpl.java 
 * @ClassName: SendPackageServiceImpl <br/> 
 * @date: 2016-4-16 下午1:19:00 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
@Service
public class SendPackageServiceImpl extends BaseServiceImpl implements SendPackageService {
	
	public static final Log logger = LogFactory.getLog(SendPackageServiceImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;//mongo
	@Autowired
	private RedisService redisService;//缓存
	@Autowired
	private ParParcelinfoMapperV4 parParcelinfoMapperV4;//包裹表mapper
	@Autowired
	private ParParceladdressMapperV4 parParceladdressMapperV4;//收件人、发件人信息mapper
	@Autowired
	private ParTaskInfoMapperV4 parTaskInfoMapperV4;//包裹任务表mapper--(包括附近代售点查询)
	@Autowired
	private MemberInfoMapperV2 MemberInfoMapperV2;//人员信息mapper
	
	private static long DISTANCE_VALUE = 5000;//查询附近多少米的站点
	@Autowired
	private ProblemPackageService problemPackageService;
	@Autowired
	private SendNoticeService sendNoticeService;//推送service
	@Autowired
	private AssignPackageService assignPackageService;
	@Autowired
	private ExpCustomerInfoService expCustomerInfoService;//客户信息表
	//----以下  离职用//
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private BasEmployeeAuditMapper basEmployeeAuditMapper;
	@Autowired
	private BasEmployeeRelationMapper basEmployeeRelationMapper;
	@Autowired
	private BasCompEmployeeResumeMapper basCompEmployeeResumeMapper;
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private AreaElectronicFenceService areaElectronicFenceService;
	@Autowired
	private ParcelInfoService parcelInfoService;
//	@Autowired
//	private BasCompimageMapperV1 compimageMapper;
//	@Value("${compPic.readPath}")
//	public String readPath;//站点门店照url
	//----以上  离职用//
	@Autowired
	private ConstPool constPool;
	/**
	 * 查询待提包裹列表
	 */
	@Override
	public Page queryParcelToBeTakenList(Long memberId, Integer currentPage,Integer pageSize) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		Query query = new Query();
        query.addCriteria(Criteria.where("actualSendMember").is(memberId));
        query.addCriteria(Criteria.where("sendTaskId").is(null));
        query.addCriteria(Criteria.where("parcelEndMark").ne("1"));
		query.with(new Sort(Direction.DESC, "createTime"));
		query.skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		Long count = mongoTemplate.count(query, ParParcelinfo.class);
		logger.info("进入查询待提包裹列表，query="+query.toString());
		List<ParParcelinfo> list = mongoTemplate.find(query, ParParcelinfo.class);
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		if(!PubMethod.isEmpty(list)){
			for (ParParcelinfo par : list) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("createTime", sdf.format(par.getCreateTime()));
				map.put("expWaybillNum", par.getExpWaybillNum());
				map.put("parId", par.getUid());
				ParParceladdress parParceladdress = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(par.getUid())), ParParceladdress.class);
				if(!PubMethod.isEmpty(parParceladdress)){
					map.put("addresseeMobile", parParceladdress.getAddresseeMobile());
					//截取详细地址
					String addresseeAddress = parParceladdress.getAddresseeAddress();
					if(PubMethod.isEmpty(addresseeAddress)){
						map.put("addresseeAddress", "");
					}else {
						String add = "";
						String[] address = addresseeAddress.split(" ");
						if(address!=null && address.length>0 && address.length==1){
							//只展示详细地址，不展示省市区，2016年5月27日17:22:50--黄总要求 by-zhaohu
						}else {
							add = address[1];
						}
						map.put("addresseeAddress", add);
					}
				}else {
					map.put("addresseeAddress", "");
					map.put("addresseeMobile", "");
				}
				resultList.add(map);
			}
		}
		Page page = PageUtil.buildPage(currentPage, pageSize);
		page.setItems(resultList);
		page.setTotal(count.intValue());
		logger.info("查询待提包裹列表结束+++count="+count);
		return page;
	}
	/**
	 * 待提包裹详情
	 */
	@Override
	public HashMap<String, Object> queryParcelDetail(String parId) {
		logger.info("进入查询待提包裹详情！parId="+parId);
		HashMap<String, Object> resultMap = new HashMap<>();
		ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parId))), ParParcelinfo.class);
		
		resultMap.put("taskId", PubMethod.isEmpty(parInfo.getSendTaskId()) ? "" :parInfo.getSendTaskId());//任务id
		resultMap.put("parId", parInfo.getUid());//包裹id
		resultMap.put("exp_waybill_num", parInfo.getExpWaybillNum());//快递单号
		resultMap.put("cod_amount", parInfo.getCodAmount());//应收货款
		resultMap.put("freight", parInfo.getFreight());//应收运费
		ParParceladdress address = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parId))), ParParceladdress.class);
		resultMap.put("addressee_mobile", address.getAddresseeMobile());//收件人手机号
		resultMap.put("addressee_name", address.getAddresseeName());//收件人姓名
		resultMap.put("city_id", address.getAddresseeAddressId());//城市id
		//收件人详细地址
		String addresseeAddress = address.getAddresseeAddress();
		if(PubMethod.isEmpty(addresseeAddress)){
			resultMap.put("addressee_address", "");
			resultMap.put("addressee_cityName", "");
		}else {
//			StringBuffer sbf = new StringBuffer();
//			String[] addresArray = addresseeAddress.split(" ");
//			if(addresArray!=null && addresArray.length>0){
//				for(String str :addresArray){
//					sbf.append(str);
//				}
//			}
//			resultMap.put("addressee_address", sbf);
			String add = "";
			String city = "";
			String[] addressStr = addresseeAddress.split(" ");
			if(addressStr!=null && addressStr.length>0 && addressStr.length==1){
				city = addressStr[0];
			}else {
				city = addressStr[0];
				add = addressStr[1];
			}
			resultMap.put("addressee_address", add);
			resultMap.put("addressee_cityName", city);
		}
		if(!PubMethod.isEmpty(parInfo.getNetId())){
			HashMap<String, Object> netMap = parParceladdressMapperV4.queryNetInfo(parInfo.getNetId());
			resultMap.put("netId", PubMethod.isEmpty(parInfo.getNetId()) ? "" :parInfo.getNetId());
			resultMap.put("net_name", PubMethod.isEmpty(netMap.get("net_name")) ? "" : netMap.get("net_name").toString());
		}else{
			resultMap.put("netId", ""); 
			resultMap.put("net_name", ""); 
		}
		return resultMap;
	}
	/**
	 * 代派包裹转单（批量）
	 */
	@Override
	public void changeSendPerson(String newMemberId,String oldMemberId,String parIds) {
		if(this.ifHasPickUpByParcelIdAndMemberId(parIds,Long.parseLong(oldMemberId))){    //通过SendTaskId是否为空判断是否提货,如果已经提货 不能转单
			logger.info("包裹已经产生taskId，不能转单,请检查数据库！");
			//包裹被提走
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.changeSendPerson.001", "包裹已被转单（或提货）");
		}
		String[] parId = parIds.split(",");
		if(parId!=null && parId.length>0){
			//更新包裹异常时间 因为包裹异常后更新包裹状态后不再是异常包裹
			for(String id : parId){
				ParParcelinfo parcelinfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(id))), ParParcelinfo.class);
				//更新包裹异常时间为null
				Query query = new Query();
				query.addCriteria(Criteria.where("uid").is(Long.parseLong(id)));
				query.addCriteria(Criteria.where("mobilePhone").is(PubMethod.isEmpty(parcelinfo.getMobilePhone()) ? "" :parcelinfo.getMobilePhone()));//片键条件
				this.mongoTemplate.updateFirst(query, Update.update("exceptionTime", null), ParParcelinfo.class);
			}
		}
		List <Long>list = new ArrayList<Long>();
		list= getParcelIdList(list,parIds);
		
		//循环单条更新
		if(list!=null && list.size()>0){
			for(Long uid : list){
				ParParcelinfo parcelinfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(uid)), ParParcelinfo.class);
				Query query = new Query();
				query.addCriteria(Criteria.where("uid").is(uid));
				query.addCriteria(Criteria.where("mobilePhone").is(PubMethod.isEmpty(parcelinfo.getMobilePhone()) ? "" :parcelinfo.getMobilePhone()));//片键条件
				Update update = new Update();
				update.set("actualSendMember", Long.parseLong(newMemberId));
				update.set("errorMessage", null);
				//单条数据更新
				this.mongoTemplate.updateFirst(query, update, ParParcelinfo.class);//通过ParcelId集合更新包裹信息表成新的memberId
			}
		}
		
		
		//保存包裹流转表
		for(String id : parId){
			ParParcelRelation relation = new ParParcelRelation();
			relation.setUid(IdWorker.getIdWorker().nextId());
			relation.setCreateTime(new Date());
			relation.setNewMemberId(Long.parseLong(newMemberId));
			relation.setOldMemberId(Long.parseLong(oldMemberId));
			relation.setNewParId(Long.parseLong(id));
			relation.setOldParId(Long.parseLong(id));
			relation.setOperationType(Short.parseShort("3"));
			this.mongoTemplate.insert(relation);
		}
		logger.info("包裹转单结束！！！parIds="+parIds+","+oldMemberId+"转给"+newMemberId);
	}
//	/**
//	 *	判断包裹是否已被提货
//	 */
//	private boolean ifHasPickUp(String parIds) {
//		List<String> li = new ArrayList<String>();
//		String[] split = parIds.split(",");
//		Collections.addAll(li, split);//数组转list
//		Query query = new Query();
//		query.addCriteria(Criteria.where("uid").in(li));
//		List<ParParcelinfo> sendTaskIdList = this.mongoTemplate.find(query, ParParcelinfo.class);
//		//如果有任何一个taskId不为空，则说明已经生成过任务id 不能转单
//		if(sendTaskIdList!=null && sendTaskIdList.size()>0){
//			for(ParParcelinfo par : sendTaskIdList){
//				if(!PubMethod.isEmpty(par.getSendTaskId())){
//					logger.info("ifHasPickUp--包裹已经产生taskId，不能转单,请检查数据库！parId="+par.getUid());
//					return true;
//				}
//				break;
//			}
//		}
//		return false;
//	}
	/**
	 *	数组转list
	 */
	private List<Long> getParcelIdList(List<Long>list,String parcelIds){
		if(!PubMethod.isEmpty(parcelIds)){
			String []ids = parcelIds.split(",");
			if(ids!=null && ids.length>0){
				for(String id :ids){
					list.add(Long.parseLong(id));
				}
			}
		}
		return list;
	}
	/**
	 * 提货-（批量）
	 */
	@Override
	public void pickUpParcel(String parIds,Long memberId, String memberPhone) {
		//判断是否被人提走（或创建任务），如果true，则返回；else继续
		if(ifHasPickUpByParcelIdAndMemberId(parIds, memberId)){
			logger.info("包裹已经产生taskId，不能提货，请检查数据库！");
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.pickUpParcel.001", "包裹已被提走");
		}
		String [] ids = parIds.split(",");
    	for(int i = 0; i < ids.length; i++){ 
    		ParParcelinfo parParcelinfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(ids[i]))), ParParcelinfo.class);  //得到包裹信息 
    		ParParceladdress parParceladdress = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(ids[i]))), ParParceladdress.class);//得到包裹地址
    		//如果有任务id就更新，如果没有就创建任务
    		this.addOrUpdateSendTask(Long.parseLong(ids[i]),1,parParcelinfo.getCompId(), parParcelinfo.getNetId(),parParcelinfo.getGoodsNum(),parParcelinfo.getChareWeightForsender()==null?0.00:parParcelinfo.getChareWeightForsender().doubleValue(), null, null, -1, 11, 0, null, memberId, memberPhone, parParceladdress.getAddresseeName(),parParceladdress.getAddresseeMobile(), parParceladdress.getAddresseePhone(), parParceladdress.getAddresseeAddressId(), parParceladdress.getAddresseeAddress(), null, null, null, new Date(), memberId, 0, new Date(), null, null,parParcelinfo.getParcelNum());
    		//更新包裹异常时间 因为包裹异常后更新包裹状态后不再是异常包裹   null
    		Query query = new Query();
    		query.addCriteria(Criteria.where("uid").is(Long.parseLong(ids[i])));
    		query.addCriteria(Criteria.where("mobilePhone").is(PubMethod.isEmpty(parParcelinfo.getMobilePhone()) ? "" :parParcelinfo.getMobilePhone()));//片键条件
			this.mongoTemplate.updateFirst(query, Update.update("exceptionTime", null), ParParcelinfo.class);
    	}
    	logger.info("提货结束！！！！parIds="+parIds+",memberId="+memberPhone);
	}
	/**
	 * 如果有任务id就更新，如果没有就创建任务
	 */
	public void addOrUpdateSendTask(Long parcelId,Integer taskType, Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc, Integer taskSource,
			Integer taskStatus, Integer taskIsEnd, Date taskEndTime,
			Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Date createTime, Long createUserId,
			Integer taskFlag, Date modifyTime, Double contactAddrLongitude,
			Double contactAddrLatitude,String parcelNum) {
		
		Update update = new Update();
		String[] keys = { "taskType", "coopCompId", "coopNetId",
				"parEstimateCount", "parEstimateWeight", "appointTime",
				"appointDesc", "taskSource", "taskStatus", "taskIsEnd",
				"taskEndTime", "actorMemberId", "actorPhone", "contactName",
				"contactMobile", "contactTel", "contactAddressId",
				"contactAddress", "customerId", "contactCasUserId",
				"contactCompId", "createTime", "createUserId", "taskFlag",
				"modifyTime", "contactAddrLongitude", "contactAddrLatitude", "parcelNum" };
		Object[] values = { taskType, coopCompId, coopNetId, parEstimateCount,
				parEstimateWeight, appointTime, appointDesc, taskSource,
				taskStatus, taskIsEnd, taskEndTime, actorMemberId, actorPhone,
				contactName, contactMobile, contactTel, contactAddressId,
				contactAddress, customerId, contactCasUserId, contactCompId,
				createTime, createUserId, taskFlag, modifyTime,
				contactAddrLongitude, contactAddrLatitude, parcelNum };
		
		//1.查询没有结束且在途的包裹 
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(parcelId));
		query.addCriteria(Criteria.where("parcelEndMark").ne("1"));
		query.addCriteria(Criteria.where("tackingStatus").ne((short)1));
		logger.info("查询没有结束且在途的包裹 query="+query);
		ParParcelinfo parcelInfo = mongoTemplate.findOne(query, ParParcelinfo.class);
		if(!PubMethod.isEmpty(parcelInfo) && !PubMethod.isEmpty(parcelInfo.getSendTaskId())) { //2.如果有任务号
			//2.通过任务号更新-par_task_info表信息 
			update = this.getUpdateParam(keys, values);//组装数据
			ParTaskInfo parTask = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(parcelInfo.getSendTaskId())), ParTaskInfo.class);
			Query queryTask = new Query();
			queryTask.addCriteria(Criteria.where("uid").is(parcelInfo.getSendTaskId()));
			queryTask.addCriteria(Criteria.where("mobilePhone").is(parTask.getMobilePhone()));//片键条件
			this.mongoTemplate.updateFirst(queryTask, update, ParTaskInfo.class);
			//3.通过pracelid和createUserId更新SendTaskId和parcel_status=11,tacking_status=0   包裹表
			Query queryPar = new Query();
			queryPar.addCriteria(Criteria.where("uid").is(parcelInfo.getUid()));
			queryPar.addCriteria(Criteria.where("mobilePhone").is(parcelInfo.getMobilePhone()));//片键条件
			Update updatePar = new Update();
			updatePar.set("parcelStatus", Short.valueOf("11"));
			updatePar.set("tackingStatus", Short.valueOf("0"));
			updatePar.set("errorMessage", null);
			updatePar.set("actualSendMember", createUserId);
			this.mongoTemplate.updateFirst(queryPar, updatePar, ParParcelinfo.class);
			logger.info("此包裹有任务id！进行更新包裹和任务操作！结束");
		} else {  //4.如果没有任务号
			//获取封装数据
			ParTaskInfo taskInfo = this.getAddParams(taskType, coopCompId, coopNetId, parEstimateCount,
					parEstimateWeight, appointTime, appointDesc, taskSource,
					taskStatus, taskIsEnd, taskEndTime, actorMemberId, actorPhone,
					contactName, contactMobile, contactTel, contactAddressId,
					contactAddress, customerId, contactCasUserId, contactCompId,
					createTime, createUserId, taskFlag, modifyTime,
					contactAddrLongitude, contactAddrLatitude, parcelNum);
			//5.向任务表添加任务
			this.mongoTemplate.insert(taskInfo);
			// update par_parcelinfo set send_task_id = #{taskId},parcel_status=11,
			//tacking_status=0,error_message=null,actual_send_member = #{createUserId} where id = #{parcelId}
			Update updatePar = new Update();
			updatePar.set("sendTaskId", taskInfo.getUid());
			updatePar.set("parcelStatus", Short.valueOf("11"));//待派
			updatePar.set("tackingStatus", Short.valueOf("0"));//包裹当前状态 0:在途,未签收 1:已签收',
			updatePar.set("errorMessage", null);
			updatePar.set("actualSendMember", createUserId);
			//6.将taskId更入ParcelInfo
			Query queryPar = new Query();
			queryPar.addCriteria(Criteria.where("uid").is(parcelInfo.getUid()));
			queryPar.addCriteria(Criteria.where("mobilePhone").is(parcelInfo.getMobilePhone()));//片键条件
			this.mongoTemplate.updateFirst(queryPar, updatePar, ParParcelinfo.class);
			logger.info("此包裹没有有任务id！进行添加任务和更新包裹任务id操作！结束");
		}
	}
	/**
	 * 组装参数--添加操作
	 */
	private ParTaskInfo getAddParams(Integer taskType, Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc, Integer taskSource,
			Integer taskStatus, Integer taskIsEnd, Date taskEndTime,
			Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Date createTime, Long createUserId,
			Integer taskFlag, Date modifyTime, Double contactAddrLongitude,
			Double contactAddrLatitude, String parcelNum) {
		
		ParTaskInfo taskInfo = new ParTaskInfo();
		taskInfo.setUid(IdWorker.getIdWorker().nextId());//生成任务id
		taskInfo.setTaskType(Byte.parseByte("1"));//任务类型 0:取件 1:派件 2:自取件 3：销售等非快递',
		taskInfo.setCoopCompId(coopCompId);
		taskInfo.setCoopNetId(coopNetId);
		taskInfo.setParEstimateCount(PubMethod.isEmpty(parEstimateCount) ? null :Byte.parseByte(parEstimateCount.toString()));
		taskInfo.setParEstimateWeight(PubMethod.isEmpty(parEstimateWeight) ? null :new BigDecimal(parEstimateWeight));
		//appointTime
		//appointDesc
		taskInfo.setTaskSource(Byte.parseByte("-1"));//任务来源 1:好递网,2:站点自建,3:电商管家,4:好递个人端,5:好递接单王,6:微信端', -1自己建立
		taskInfo.setTaskStatus(Byte.parseByte("11"));//任务状态 0待处理，1已分配，2已完成，3已取消，10已删除',11 待派件
		taskInfo.setTaskIsEnd(Byte.parseByte("0"));//任务是否结束 0否1是
		//taskEndTime
		taskInfo.setActorMemberId(actorMemberId);
		taskInfo.setActorPhone(actorPhone);
		taskInfo.setContactName(contactName);
		taskInfo.setContactMobile(contactMobile);
		taskInfo.setContactTel(contactTel);
		taskInfo.setContactAddressId(contactAddressId);
		taskInfo.setContactAddress(contactAddress);
		//customerId, contactCasUserId, contactCompId
		taskInfo.setCreateTime(new Date());
		taskInfo.setCreateUserId(createUserId);
		taskInfo.setTaskFlag(Byte.parseByte("0"));//任务标志 0：正常,1：抢单',
		taskInfo.setModifyTime(new Date());
		taskInfo.setMobilePhone(contactMobile);//片键
		//contactAddrLongitude,contactAddrLatitude
		logger.info("组装taskInfo！");
		return taskInfo;
	}
	/**
	 * 组装参数--修改操作
	 */
	private Update getUpdateParam(String[] keys, Object[] values) {
		Update update = new Update();
		for (int i = 0; i < keys.length; i++) {
			update.set(keys[i], values[i]);
		}
		logger.info("组装更新条件update！");
		return update;
	}
	/**
	 * 判断是否被其他人提货（或创建任务）
	 */
	public boolean ifHasPickUpByParcelIdAndMemberId(String ParcelId,Long memberId) {
		List<Long> li = new ArrayList<Long>();
		String[] split = ParcelId.split(",");
		if(split!=null && split.length>0){
			for(String id :split){
				li.add(Long.parseLong(id));
			}
		}
		List<ParParcelinfo> list = this.mongoTemplate.find(Query.query(Criteria.where("uid").in(li).andOperator(Criteria.where("actualSendMember").is(memberId))), ParParcelinfo.class);
		if(list!=null && list.size()>0){
			for(ParParcelinfo par : list){
				if(!PubMethod.isEmpty(par.getSendTaskId())){
					logger.info("ifHasPickUpByParcelIdAndMemberId--包裹已经产生taskId，不能转单或提货,请检查数据库！parId="+par.getUid());
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 查询待派任务列表
	 */
	@Override
	public Page querySendTaskList(Long memberId,Integer currentPage,Integer pageSize,String mobilePhone) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		Query query = new Query();
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));
		query.addCriteria(Criteria.where("taskType").is(Byte.parseByte("1")));//1派件
		query.addCriteria(Criteria.where("taskFlag").is(Byte.parseByte("0")));
		query.addCriteria(Criteria.where("taskIsEnd").is(Byte.parseByte("0")));
		query.with(new Sort(Direction.DESC, "createTime"));
		
		query.skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		Long count = mongoTemplate.count(query, ParTaskInfo.class);
		logger.info("进入查询待派任务列表，query="+query.toString());
		
		List<ParTaskInfo> list = this.mongoTemplate.find(query, ParTaskInfo.class);
		if(list!=null && list.size()>0){
			for(ParTaskInfo task : list){
				Query queryPar = new Query();
				queryPar.addCriteria(Criteria.where("sendTaskId").is(task.getUid()));
				if(!PubMethod.isEmpty(mobilePhone)){
					queryPar.addCriteria(new Criteria().orOperator(Criteria.where("mobilePhone").regex(mobilePhone),
							Criteria.where("parcelNum").regex(mobilePhone)));
				}
				ParParcelinfo parcelinfo = this.mongoTemplate.findOne(queryPar, ParParcelinfo.class);
				if(!PubMethod.isEmpty(parcelinfo) && !PubMethod.isEmpty(parcelinfo.getSendTaskId()) && !PubMethod.isEmpty(parcelinfo.getUid())){
					HashMap<String, Object> map = new HashMap<>();
					ParParceladdress parParceladdress = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(parcelinfo.getUid())), ParParceladdress.class);
					if(!PubMethod.isEmpty(parParceladdress)){
						map.put("mobile", parParceladdress.getAddresseeMobile());
						//截取详细地址
						String addresseeAddress = parParceladdress.getAddresseeAddress();
						if(PubMethod.isEmpty(addresseeAddress)){
							map.put("address", "");
						}else {
							String add = "";
							String[] address = addresseeAddress.split(" ");
							if(address!=null && address.length>0 && address.length==1){
//								add = address[0];    //只展示详细地址，不展示省市区，2016年5月27日17:22:50--黄总要求 by-zhaohu
							}else {
								add = address[1];
							}
							map.put("address", add);
						}
					}else {
						map.put("address", "");
						map.put("mobile", "");
					}
					map.put("isSendMsg", PubMethod.isEmpty(parcelinfo.getIsSendMsg())?"1":parcelinfo.getIsSendMsg());//是否发送过短信 0否1是
					map.put("parcelNum", parcelinfo.getParcelNum());//编号
					map.put("expNum", parcelinfo.getExpWaybillNum());//快递单号
					map.put("parcelId", parcelinfo.getUid());
					map.put("taskId", parcelinfo.getSendTaskId());
					map.put("sendSmsType", parcelinfo.getSendSmsType());//群发通知类型：1.电话优先 2.仅电话 3.仅短信
					map.put("sendSmsStatus", parcelinfo.getSendSmsStatus());//群发通知状态  0.发送成功  1.呼叫成功  2.重复  3.呼叫失败  4.发送失败   5.用户退订 
					map.put("callBackStatus", parcelinfo.getCallBackStatus());//群发通知回执状态 0.未知  1.成功  2.失败  3.呼叫未知  4.呼叫成功  5.呼叫失败 6.短信转通道
					map.put("replyStatus", parcelinfo.getReplyStatus());//回复状态      0.未回复  1.客户回复  2.短信已读
					map.put("isAgainSend", PubMethod.isEmpty(parcelinfo.getIsAgainSend()) ? "" : parcelinfo.getIsAgainSend());//是否是重派的标识，空值为正常待派，1为重派待派
					map.put("isRepeat", (short)1);
					if(!PubMethod.isEmpty(parcelinfo.getNetId())){
						HashMap<String, Object> netMap = parParceladdressMapperV4.queryNetInfo(parcelinfo.getNetId());
						map.put("netId", PubMethod.isEmpty(parcelinfo.getNetId()) ? "" :parcelinfo.getNetId());
						map.put("net_name", PubMethod.isEmpty(netMap.get("net_name")) ? "" : netMap.get("net_name").toString());
					}else{
						map.put("netId", ""); 
						map.put("net_name", ""); 
					}
					
					resultList.add(map);
				}
			}
		}
		logger.info("待派任务个数，count="+count);
		Page page = PageUtil.buildPage(currentPage, pageSize);
		page.setItems(resultList);
		page.setTotal(count.intValue());
		return page;
	}
	@Override
	public List<Map<String, Object>> queryNearCompInfo(Double longitude,Double latitude,Short agentType) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapParam = this.queryFromPosition(longitude,latitude);
		mapParam.put("agentType", agentType);
			list = this.parTaskInfoMapperV4.queryNearCompInfo(mapParam);//查询附近站点  comp_type_num  (1006,1050)
		for(Map<String,Object> map : list){
			double distance=DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(String.valueOf(map.get("latitude"))), Double.parseDouble(String.valueOf(map.get("longitude"))));
			if(distance - DISTANCE_VALUE <= 0){
				Map<String,Object> mapResult = new HashMap<String,Object>();
				mapResult.put("compId", PubMethod.isEmpty(map.get("compId")) ? "" :map.get("compId").toString());
				mapResult.put("compName", PubMethod.isEmpty(map.get("compName")) ? "" :map.get("compName").toString());
				mapResult.put("compAddress", String.valueOf(map.get("compAddress")));
				mapResult.put("compTypeNum", String.valueOf(map.get("compTypeNum")));
				mapResult.put("distance", distance);
				mapResult.put("netId", PubMethod.isEmpty(map.get("netId")) ? "" :map.get("netId").toString());
				mapResult.put("netName", PubMethod.isEmpty(map.get("netName")) ? "" :map.get("netName").toString());
				//产品说代收点头像是显示站长的头像
				String resPhone = (String)map.get("resPhone");
				Long resMemberId = parTaskInfoMapperV4.findMemberIdByPhone(resPhone);
				mapResult.put("compImgUrl",constPool.getHeadImgPath()+resMemberId+".jpg");
				
				mapResult.put("longitude", String.valueOf(map.get("longitude")));
				mapResult.put("latitude", String.valueOf(map.get("latitude")));
				mapResult.put("agentType", PubMethod.isEmpty(map.get("agentType")) ? "" :map.get("agentType").toString());
				if(PubMethod.isEmpty(resPhone)){
					mapResult.put("resPhone","");
				}else{
					mapResult.put("resPhone", resPhone);
				}
				listResult.add(mapResult);
			}
		}
				logger.info("组装数据..查询附近的代收点....");
		Collections.sort(listResult, new Comparator<Object>() {  
			@SuppressWarnings("unchecked")
			public int compare(Object a, Object b) {
				double one = Double.parseDouble(String.valueOf(((Map<String,Object>)a).get("distance")));  
				double two = Double.parseDouble(String.valueOf(((Map<String,Object>)b).get("distance")));  
				int k = (int)(one - two);
				return k  ;   
			}  
		});
		return listResult;
	}
	
	
	public Map<String,Object> queryFromPosition(Double longitude, Double latitude) {
		int dis=5;//设置5公里范围内
		double EARTH_RADIUS = 6378.137 * 1000;
		double dlng = Math.abs(2 * Math.asin(Math.sin(dis*1000 / (2 * EARTH_RADIUS)) / Math.cos(latitude)));
		dlng = dlng*180.0/Math.PI;        //弧度转换成角度
		double dlat = Math.abs(dis*1000 / EARTH_RADIUS);
		dlat = dlat*180.0/Math.PI;     //弧度转换成角度
		double bottomLat=latitude - dlat;
		double topLat=latitude + dlat;
		double leftLng=longitude - dlng;
		double rightLng=longitude + dlng;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("bottomLat", bottomLat);
		params.put("topLat", topLat);
		params.put("leftLng", leftLng);
		params.put("rightLng", rightLng);
		return params;
		
	}
	@Override
	public Map<String, Object> saveNearComp(String compName, String compMobile,
			Long actorMemberId) {
		Map<String, Object> map=new HashMap<>();
		SubJoinComp subJoinComp = new SubJoinComp();
		subJoinComp.setCompName(compName);
		subJoinComp.setCompMobile(compMobile);
		subJoinComp.setActorMemberId(actorMemberId);
		mongoTemplate.insert(subJoinComp);
		logger.info("快递员转代收----保存附近代收点成功");
		return map;
	}
	/**
	 * 编辑派件包裹
	 */
	@Override
	public void updateParcelInfo(String parId, String netId, String expNum,String mobile, String address, String codAmount, String freight,String name) {
		ParParcelinfo parParcelinfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parId))), ParParcelinfo.class);
		Query query = new Query();
		Update update = new Update();
		update.set("netId", Long.parseLong(netId));
		update.set("expWaybillNum", expNum);
		update.set("codAmount", PubMethod.isEmpty(codAmount) ? 0 :new BigDecimal(codAmount));
		update.set("freight", PubMethod.isEmpty(freight) ? 0 :new BigDecimal(freight));
		query.addCriteria(Criteria.where("uid").is(Long.parseLong(parId)));
		query.addCriteria(Criteria.where("mobilePhone").is(parParcelinfo.getMobilePhone()));//片键条件
		//1.更新包裹信息
		this.mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
		//2.更新收件人地址相关信息
		Update updateA = new Update();
		updateA.set("addresseeMobile", mobile);
		ParParceladdress addObj = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parId))), ParParceladdress.class);
		String addressStr = "";
		if(!PubMethod.isEmpty(addObj)){
			String add = addObj.getAddresseeAddress();
			if(!PubMethod.isEmpty(add)){
//				String[] addStr = add.split(" ");
//				if(addStr!=null && addStr.length>0 && addStr.length==1){
//					addressStr = addStr[0];
//				}else {
//					addressStr = addStr[0];
//				}
				// 环星大厦//
				if(add.indexOf(" ") == -1){ //如果不包含空格，加上一个
					add = " "+add;
				}
				String citySub = add.substring(0, add.indexOf(" "));
				String addressSub = add.substring(add.indexOf(" "),add.length());
				if(!PubMethod.isEmpty(citySub)&&!PubMethod.isEmpty(addressSub)){
					addressStr = citySub;
				}else if(!PubMethod.isEmpty(citySub)&&PubMethod.isEmpty(addressSub)){
					addressStr = citySub;
				}
			}
		}
		updateA.set("addresseeAddress", addressStr+" "+address);
		updateA.set("addresseeName", name);
		Query queryAdd = new Query();
		queryAdd.addCriteria(Criteria.where("uid").is(Long.parseLong(parId)));
		queryAdd.addCriteria(Criteria.where("addresseeMobile").is(addObj.getAddresseeMobile()));//片键条件
		this.mongoTemplate.updateFirst(queryAdd, updateA, ParParceladdress.class);
		//3.更新connection表
		ParParcelconnection parcelconnection = this.mongoTemplate.findOne(Query.query(where("parId").is(Long.parseLong(parId))), ParParcelconnection.class);
		Query queryCon = new Query();
		queryCon.addCriteria(Criteria.where("parId").is(Long.parseLong(parId)));
		queryCon.addCriteria(Criteria.where("mobilePhone").is(parcelconnection.getMobilePhone()));//片键条件
		this.mongoTemplate.updateFirst(queryCon, Update.update("netId", Long.parseLong(netId)), ParParcelconnection.class);
		//4.更新taskInfo的收件人信息
		ParParcelinfo parcelinfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parId))), ParParcelinfo.class);
		ParTaskInfo taskInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(parcelinfo.getSendTaskId())), ParTaskInfo.class);
		Update updateB = new Update();
		updateB.set("contactMobile", mobile);
		updateB.set("contactAddress", addressStr+" "+address);
		updateB.set("contactName", name);
		Query queryTask = new Query();
		queryTask.addCriteria(Criteria.where("uid").is(parcelinfo.getSendTaskId()));
		queryTask.addCriteria(Criteria.where("mobilePhone").is(taskInfo.getMobilePhone()));//片键条件
		this.mongoTemplate.updateFirst(queryTask, updateB, ParTaskInfo.class);
		logger.info("编辑派件包裹信息结束！parId="+parId);
	}
	/**
	 * 派件正常签收
	 */
	@Override
	public void normalSignParcel(Long taskId, String parcelId,Double totalCodAmount, Double totalFreight, Long memberId
			,String mobile,String name,String address,String sex,String signType,String signFlag) {
		//更新task信息（task_is_end=1），生成票据信息，更新包裹信息
		//1.修改任务信息
		if(!PubMethod.isEmpty(taskId)){
			ParTaskInfo taskInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(taskId)), ParTaskInfo.class);
			Update update = new Update();
			update.set("taskIsEnd", Byte.parseByte("1"));//是否结束
			update.set("taskEndTime", new Date());//任务结束时间
			Query queryTask = new Query();
			queryTask.addCriteria(Criteria.where("uid").is(taskId));
			queryTask.addCriteria(Criteria.where("mobilePhone").is(taskInfo.getMobilePhone()));//片键条件
			this.mongoTemplate.updateFirst(queryTask, update, ParTaskInfo.class);
		}
		//2.插入票据信息
		AccSrecpayvouchers acc = new AccSrecpayvouchers();
		HashMap<String, Object> map = MemberInfoMapperV2.queryCompInfo(memberId);
		if(!PubMethod.isEmpty(totalCodAmount) && !PubMethod.isEmpty(totalFreight)){
			MemberInfo member = MemberInfoMapperV2.queryMemberName(memberId);//姓名
			acc.setUid(IdWorker.getIdWorker().nextId());
			this.setAccParams(acc, totalCodAmount, totalFreight, 1, memberId,member.getMemberName(),PubMethod.isEmpty(map.get("compId")) ? null : Long.valueOf(map.get("compId").toString()));
			this.mongoTemplate.insert(acc);//保存数据
		}
		//3.更新包裹信息
		Update update = new Update();
		if(totalFreight != null){
			update.set("freightPaymentStatus", (short)1);//运费已收
			update.set("freightPaymentTime", new Date());//费用支付时间
		}
		if(totalCodAmount != null){
			update.set("codIsRecovery", (short)1);//代收货款已收回
		}
		if(!PubMethod.isEmpty(acc.getUid())){
			update.set("receiptId", acc.getUid());
		}
		update.set("tackingStatus", (short)1);//已签收
		//signFlag : null 正常签收1派而不签
		if(PubMethod.isEmpty(signFlag)){
			update.set("signResult", (short)1);//0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收）
		}else if ("1".equals(signFlag)) {
			update.set("signResult", (short)2);//0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收）
		}
		update.set("parcelEndMark", "1");//结束
		update.set("signTime", new Date());//签收时间
		update.set("parcelStatus",(short)11);//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'
		ParParcelinfo parcelinfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parcelId))), ParParcelinfo.class);
		Query queryPar = new Query();
		queryPar.addCriteria(Criteria.where("uid").is(Long.parseLong(parcelId)));
		queryPar.addCriteria(Criteria.where("mobilePhone").is(parcelinfo.getMobilePhone()));//片键条件
		this.mongoTemplate.updateFirst(queryPar, update, ParParcelinfo.class);
		//保存客户信息---signType:1.签收并保存客户信息 2.签收
		if("1".equals(signType)){
			//插入客户信息
			Map map1=new HashMap();
			map1.put("compId",Long.parseLong(map.get("compId").toString()));
			map1.put("customerName",name);
			map1.put("gender",PubMethod.isEmpty(sex) ? null :Byte.parseByte(sex));
			map1.put("customerPhone",mobile);
			map1.put("townName",address);
			map1.put("memberId",memberId);
			String url = constPool.getOpencustomerUrl() + "customerNewInfoTwo/insertCustomerV4";
			String result = Post(url, map1);
		}
		//删除发送失败的短信   2016年5月25日15:32:51
		Map map1=new HashMap();
		map1.put("memberId",memberId);
		map1.put("firstMsg",taskId+"");
		String url = constPool.getSmsZtUrl() + "wrongPrice/update";
		String result = Post(url, map1);
		logger.info("派件正常签收！同时调用sms删除发送失败的短信，parId="+parcelId);
		logger.info("派件正常签收！parId="+parcelId);
	}
	/**
	 * 组装参数
	 */
	public void setAccParams(AccSrecpayvouchers acc, Double totalCodAmount, Double totalFreight, Integer count, Long memberId,String memberName,Long compId){
		acc.setPaymentWay((short)2);//付款方式 1:银收;2:现收;3:银付;4:现付',
		acc.setVoucherFlag((short)0);//凭证标识 0：收款凭证;1：付款凭证',
		acc.setBillQuantity(count);
		acc.setRecePeopleId(memberId);
		acc.setRecePeopleName(memberName);
		acc.setCompId(compId);
		acc.setCreateUserId(memberId);
		acc.setCreateTime(new Date());
		acc.setTotalCodAmount(totalCodAmount==null ? null : new BigDecimal(totalCodAmount));//合计代收货款金额
		acc.setTotalFreight(totalFreight == null? null :new BigDecimal(totalFreight));//合计运费
		acc.setTotalAmount(new BigDecimal(totalCodAmount == null? 0 :totalCodAmount).add(new BigDecimal(totalFreight == null? 0 :totalFreight)));
		acc.setActualAmount(new BigDecimal(totalCodAmount == null? 0 : totalCodAmount).add(new BigDecimal(totalFreight == null? 0 :totalFreight)));
		acc.setVoucherStatus((short)2);
	}
	/**
	 * 派件异常签收
	 */
	@Override
	public void exceptionSignParcel(String taskId, String parcelId,String memberId, 
			String exceptionType, String textValue,String compId) {
		//exceptionType:1.超出收派范围2.忙不过来3.客户取消4.联系不到客户5.其他原因
		HashMap<String, Object> typeMap = new HashMap<>();
		typeMap.put("1", "超出收派范围");
		typeMap.put("2", "忙不过来");
		typeMap.put("3", "客户取消");
		typeMap.put("4", "联系不到客户");
		typeMap.put("5", "其他原因");
		//解决重复提交的问题，先去查是否已存在,存在直接抛异常
		ParTaskDisposalRecord taskrecord = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(taskId))
				.andOperator(Criteria.where("disposalType").is(Byte.parseByte("3")))), ParTaskDisposalRecord.class);
		if(!PubMethod.isEmpty(taskrecord)){
			logger.info("你妹啊，此包裹已经被异常签收，不允许重复签收。taskId="+taskrecord.getTaskId());
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.exceptionSignParcel.001", "不能重复异常签收"); 
		}
		ParTaskInfo taskInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(taskId))), ParTaskInfo.class);
		ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parcelId))), ParParcelinfo.class);
		//1.任务结束
		Update update = new Update();
		update.set("taskIsEnd", Byte.parseByte("1"));//结束
		update.set("taskEndTime", new Date());//结束时间
		Query queryTask = new Query();
		queryTask.addCriteria(Criteria.where("uid").is(Long.parseLong(taskId)));
		queryTask.addCriteria(Criteria.where("mobilePhone").is(taskInfo.getMobilePhone()));//片键条件
		this.mongoTemplate.updateFirst(queryTask, update, ParTaskInfo.class);
		//2.保存任务记录表
		ParTaskDisposalRecord record = new ParTaskDisposalRecord();
		record.setUid(IdWorker.getIdWorker().nextId());
		this.setRecordParams(record,taskId,parcelId,memberId,exceptionType,textValue,compId,typeMap);
		this.mongoTemplate.insert(record);
		//3.更新包裹信息
		Update updatePar = new Update();
		updatePar.set("parcelStatus", Short.parseShort("12"));//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件;12:派件异常;',
		updatePar.set("exceptionTime", new Date());//异常时间
		updatePar.set("parcelEndMark", "0");//包裹是否结束;包裹不结束，任务结束
		if(!PubMethod.isEmpty(textValue) && "5".equals(exceptionType)){//其他原因
			updatePar.set("errorMessage", textValue);//异常信息
			updatePar.set("disposalDesc", textValue);
		}else {
			updatePar.set("errorMessage", typeMap.get(exceptionType).toString());//异常信息
			updatePar.set("disposalDesc", typeMap.get(exceptionType).toString());
		}
		Query queryPar = new Query();
		queryPar.addCriteria(Criteria.where("uid").is(Long.parseLong(parcelId)));
		queryPar.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));//片键条件
		this.mongoTemplate.updateFirst(queryPar, updatePar, ParParcelinfo.class);
		
		//删除发送失败的短信   2016年5月25日15:32:51
		Map map1=new HashMap();
		map1.put("memberId",Long.parseLong(memberId));
		map1.put("firstMsg",taskId+"");
		String url = constPool.getSmsZtUrl() + "wrongPrice/update";
		String result = Post(url, map1);
		logger.info("派件异常签收！同时调用sms删除发送失败的短信，taskId="+taskId);
		logger.info("派件异常签收！parId="+parcelId+",exceptionType="+exceptionType+",其他原因textValue="+textValue+",memberId="+memberId);
	}
	/**
	 * 组装参数
	 */
	private void setRecordParams(ParTaskDisposalRecord record,String taskId, String parcelId,String memberId, String exceptionType, String textValue,String compId,HashMap<String, Object> typeMap) {
		//exceptionType:1.超出收派范围2.忙不过来3.客户取消4.联系不到客户5.其他原因
		record.setCompId(PubMethod.isEmpty(compId) ? null :Long.parseLong(compId));
		record.setCreateTime(new Date());
		record.setModifiedTime(new Date());
		record.setDisposalObject(Byte.parseByte("0"));//'处理方类型 0：派送员,1：派送站点,2：客服,3：营业分部',
		record.setDisposalType(Byte.parseByte("3"));//处理类型 0：待处理,1：已分配,2：已完成,3：已取消',
		record.setMemberId(Long.parseLong(memberId));
		record.setShowFlag(Byte.parseByte("0"));//显示标识 0：不显示,1：显示',
		record.setTaskId(Long.parseLong(taskId));
		record.setTaskErrorFlag(Byte.parseByte("1"));//异常标识 0：正常,1：异常',
		if(!PubMethod.isEmpty(textValue) && "5".equals(exceptionType)){   //其他原因
			record.setDisposalDesc(textValue);
		}else {
			record.setDisposalDesc(typeMap.get(exceptionType).toString());
		}
	}
	/**
	 * 转代收
	 */
	@Override
	public void changeAccept(Long newMemberId,Long newCompId,Long newNetId, Long oldMemberId,String parIds,Short flag) {
		String[] split = parIds.split(",");
		for (String parId : split) {
			Long newId = IdWorker.getIdWorker().nextId();//新包裹Id
			Query query = Query.query(where("uid").is(Long.valueOf(parId)));
			ParParcelinfo parcel = mongoTemplate.findOne(query, ParParcelinfo.class);
			Long sendTaskId = parcel.getSendTaskId();
			//1.将原收派员的任务设置为已结束--task_is_end=1 结束   task_end_time=当前时间  结束时间update.set("tackingStatus", (short)1);//已签收
			ParTaskInfo task = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(sendTaskId)), ParTaskInfo.class);
			Query queryTask = new Query();
			queryTask.addCriteria(Criteria.where("uid").is(sendTaskId));
			queryTask.addCriteria(Criteria.where("mobilePhone").is(task.getMobilePhone()));//片键条件
			mongoTemplate.updateFirst(queryTask, new Update().set("taskIsEnd", (byte)1).set("taskEndTime", new Date()), ParTaskInfo.class);
			logger.info("转代收--将原收派员的任务设置为已结束");
			//2.原包裹表做修改操作---parcel_status=11 已派件     parcel_end_mark=1 结束       sign_time= 签收时间   //signResult 0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收）
			Query queryPar = new Query();
			queryPar.addCriteria(Criteria.where("uid").is(Long.valueOf(parId)));
			queryPar.addCriteria(Criteria.where("mobilePhone").is(parcel.getMobilePhone()));//片键条件
			mongoTemplate.updateFirst(queryPar, new Update().set("parcelStatus", (short)11).set("parcelEndMark", "1").set("signResult", (short)3).set("signTime", new Date()).set("tackingStatus", (short)1), ParParcelinfo.class);
			logger.info("转代收--原包裹表做修改操作---parcel_status=11 已派件     parcel_end_mark=1 结束    sign_result=1 签收");
			if(1==flag){
				//3.添加新的 包裹(复制包裹和地址)----comp_id=参数 代收点ID     actor_member_id=参数   执行人ID   parcel_end_mark=0 未结束  send_task_id 为空 派件任务ID  （其它信息同上一条包裹信息）
				problemPackageService.copyParcel(parId, newId, newCompId, newNetId, newMemberId,"");
				logger.info("转代收--添加新的 包裹(复制包裹和地址)");
				//4.添加任务流程处理表--par_task_process 任务处理流程表
				Long compId = parcel.getCompId();
				Long actualSendMember = parcel.getActualSendMember();
				ParTaskInfo taskInfo = mongoTemplate.findOne( Query.query(where("uid").is(sendTaskId)), ParTaskInfo.class);
				Long operatorId = taskInfo.getCreateUserId();//创建人id
				String taskProcessDesc="转代收";
				problemPackageService.addParTaskProcess(sendTaskId,compId,actualSendMember,newCompId,newMemberId,0,5,operatorId,compId,taskProcessDesc,taskProcessDesc);
				logger.info("转代收--添加任务流程处理表");
				//5.添加包裹关系表 operationType  操作类型（1：转代收 2：重派）
				problemPackageService.addParcelRelation(Long.valueOf(parId), newId, (short) 1, oldMemberId, newMemberId);
				logger.info("转代收--添加包裹关系表 ");
				//转完后，为接单的代收点人员发送推送
				sendNoticeService.sendMsgByChangeAccept(newMemberId);
			}else{
				logger.info("添加新的附近代收点,终结包裹状态和任务状态:sendTaskId"+sendTaskId);
			}
			
			//删除发送失败的短信   2016年5月25日15:32:51
			ParParcelinfo findOne = this.mongoTemplate.findOne(Query.query(where("uid").is(Long.parseLong(parId))), ParParcelinfo.class);
			Map map1=new HashMap();
			map1.put("memberId",oldMemberId);
			map1.put("firstMsg",PubMethod.isEmpty(findOne.getSendTaskId()) ? "" :findOne.getSendTaskId()+"");
			String url = constPool.getSmsZtUrl() + "wrongPrice/update";
			String result = Post(url, map1);
			logger.info("转代收！同时调用sms删除发送失败的短信，taskId="+findOne.getSendTaskId());
		  }
	}
	
    /**
     * 添加派件同时添加包裹
     */
	@Override         
	public String addSendTask( Integer taskType, Long compId,Long netId, Integer parEstimateCount, Integer taskSource,
			Integer taskStatus, Integer taskIsEnd, Long memberId, String actorPhone, String addresseeName,String addresseeMobile, Long addresseeAddressId,
			String addresseeAddress, Date createTime, Integer taskFlag, Date modifyTime,String expWaybillNum, BigDecimal freight,BigDecimal codAmount,String cityName) {
//		ehcacheService.remove("await_Parce_List", actorMemberId+"");
//		ehcacheService.remove("sendTaskCache", actorMemberId+"");
//		ehcacheService.removeAll("parcelIdsCacheByMemberId");
//		dispatchParService.removeTaskCache();
//		dispatchParService.clearDispatchCache();
//		ehcacheService.remove("queryAlreadySignList",  String.valueOf(actorMemberId));
//		ehcacheService.remove("", "");
		//添加包裹
		Map<String, Object> parcelInfo = this.assignPackageService.saveParcelInfo(expWaybillNum, compId, netId, addresseeName, addresseeMobile,addresseeAddressId,cityName,addresseeAddress,PubMethod.isEmpty(freight)?new BigDecimal(0):freight, PubMethod.isEmpty(codAmount)?new BigDecimal(0):codAmount, memberId, memberId);
		Long parcelId = (Long)parcelInfo.get("parcelId");
		logger.info("添加包裹,parcelId为-1表示该运单号的包裹已存在,parcelId为正常表示添加包裹成功==>parcelId:"+parcelId);
		Map<String, Object> data = new HashMap<String, Object>();
		if(-1==parcelId){//包裹和任务都不保存
			data.put("success", true);
			data.put("parcelId", parcelId);
			data.put("taskId", "");
			return JSON.toJSONString(data);
		}
		ParTaskInfo parTaskInfo = new ParTaskInfo();
		Long taskId = IdWorker.getIdWorker().nextId();
		parTaskInfo.setUid(taskId);
		parTaskInfo.setTaskType(taskType.byteValue());
		parTaskInfo.setCoopCompId(compId);
		parTaskInfo.setCoopNetId(netId);
		parTaskInfo.setParEstimateCount(parEstimateCount.byteValue());
		parTaskInfo.setTaskSource(taskSource.byteValue());
		parTaskInfo.setTaskStatus(taskStatus.byteValue());
		parTaskInfo.setTaskIsEnd(taskIsEnd.byteValue());
		parTaskInfo.setActorMemberId(memberId);
		parTaskInfo.setActorPhone(actorPhone);
		parTaskInfo.setContactName(addresseeName);
		parTaskInfo.setContactMobile(addresseeMobile);
		parTaskInfo.setContactAddressId(addresseeAddressId);
		String address=cityName+" "+addresseeAddress;
		parTaskInfo.setContactAddress(address);
		parTaskInfo.setCreateTime(createTime);
		parTaskInfo.setCreateUserId(memberId);
		parTaskInfo.setTaskFlag(taskFlag.byteValue());
		parTaskInfo.setModifyTime(modifyTime);
		parTaskInfo.setMobilePhone(addresseeMobile);//片键
		mongoTemplate.insert(parTaskInfo);
		this.updateParcel(taskId, parcelId,memberId);//更新包裹表,添加任务id
		data.put("parcelId", parcelId);
		data.put("taskId", taskId);
		data.put("success", true);
/*		//创建派件任务，生成一条短信假数据，为了短信记录在提醒里显示，不在群发通知里显示 2016年5月20日18:52:11 by zhaohu
		Map map=new HashMap();
		map.put("msgId",taskId+"");
		map.put("receiverPhone",addresseeMobile+"");
		map.put("memberId",memberId+"");
		map.put("memberPhone",actorPhone+"");
		String url = constPool.getSmsZtUrl() + "forgeHeadSms/sms";
		String result = Post(url, map);
	*/	
		return JSON.toJSONString(data);
	}
	/**
	 * 更新包裹表,添加任务id
	 */
	public void updateParcel(Long taskId, Long parcelId,Long createUserId) {
		Criteria criteria = where("uid").is(parcelId);
		Query query = Query.query(criteria);
		Update update=new Update();
		update.set("sendTaskId", taskId);
		update.set("actualSendMember", createUserId);
		update.set("parcelStatus", (short)11);//已派件
		update.set("tackingStatus", (short)0);
		mongoTemplate.upsert(query, update, ParParcelinfo.class);
//		this.ehcacheService.remove("takeIdsCacheByMemberId", String.valueOf(createUserId));
//		this.ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(createUserId));
//		this.ehcacheService.remove("queryTakeByWaybillNum", String.valueOf(createUserId));
	}
	/**
	 * 正常签收-批量
	 */
	@Override
	public void normalSignParcelBatch(String taskIds,String signType) {
		//signType:1客户正常签收2有派无签
		if(!PubMethod.isEmpty(taskIds)){
			String[] taskId = taskIds.split(",");
			if(taskId!=null && taskId.length>0){
				for(String task:taskId){
					ParTaskInfo taskInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(task))), ParTaskInfo.class);
					ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("sendTaskId").is(Long.parseLong(task))), ParParcelinfo.class);
					//1.修改任务信息
					Update update = new Update();
					update.set("taskIsEnd", Byte.parseByte("1"));//是否结束
					update.set("taskEndTime", new Date());//任务结束时间
					Query queryTask = new Query();
					queryTask.addCriteria(Criteria.where("uid").is(Long.parseLong(task)));
					queryTask.addCriteria(Criteria.where("mobilePhone").is(taskInfo.getMobilePhone()));//片键条件
					this.mongoTemplate.updateFirst(queryTask, update, ParTaskInfo.class);
					//2.更新包裹信息
					Update updateStr = new Update();
					updateStr.set("freightPaymentStatus", (short)1);//运费已收
					updateStr.set("freightPaymentTime", new Date());//费用支付时间
					updateStr.set("codIsRecovery", (short)1);//代收货款已收回
					
					updateStr.set("tackingStatus", (short)1);//已签收
					updateStr.set("signResult", Short.parseShort(signType));//0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收）
					updateStr.set("parcelEndMark", "1");//结束
					updateStr.set("signTime", new Date());//签收时间
					updateStr.set("parcelStatus",(short)11);//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'
					Query queryPar = new Query();
					queryPar.addCriteria(Criteria.where("uid").is(parInfo.getUid()));
					queryPar.addCriteria(Criteria.where("sendTaskId").is(Long.parseLong(task)));
					queryPar.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));//片键条件
					this.mongoTemplate.updateFirst(queryPar, updateStr, ParParcelinfo.class);
					//删除发送失败的短信   2016年5月25日15:33:46
					Map map1=new HashMap();
					map1.put("memberId",PubMethod.isEmpty(taskInfo.getActorMemberId()) ? null :taskInfo.getActorMemberId());
					map1.put("firstMsg",task+"");
					String url = constPool.getSmsZtUrl() + "wrongPrice/update";
					String result = Post(url, map1);
				}
			}
		}
	}
	/**
	 * 派件记录列表
	 */
	@Override
	public HashMap<String, Object> querySendRecordList(Long memberId, String signDate) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		List<HashMap<String, Object>> normalList = new ArrayList<HashMap<String,Object>>();//正常签收
		List<HashMap<String, Object>> sendButNoSignList = new ArrayList<HashMap<String,Object>>();//有派无签
		List<HashMap<String, Object>> changeSignList = new ArrayList<HashMap<String,Object>>();//转代收签收
		List<HashMap<String, Object>> exceptionList = new ArrayList<HashMap<String,Object>>();//异常签收
		//1.查询正常签收派件列表
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = signDate + " 00:00:00";
		String endTime = signDate + " 23:59:59";
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("actualSendMember").is(memberId));
		//signResult：签收结果 0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收）
		query1.addCriteria(Criteria.where("signResult").is((short)1));
		query1.addCriteria(Criteria.where("signTime").gte(sdf.parse(startTime)).lte(sdf.parse(endTime)));
		query1.with(new Sort(Direction.DESC, "signTime"));
		List<ParParcelinfo> normal = this.mongoTemplate.find(query1, ParParcelinfo.class);
		if(normal!=null && normal.size()>0){
			for(ParParcelinfo par:normal){
				HashMap<String, Object> normalMap = new HashMap<>();
				normalMap.put("parId", par.getUid());
				normalMap.put("freight", par.getFreight());
				normalMap.put("codAmount", par.getCodAmount());
				normalMap.put("expWaybillNum", par.getExpWaybillNum());
				
				ParParceladdress parParceladdress = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(par.getUid())), ParParceladdress.class);
				if(!PubMethod.isEmpty(parParceladdress)){
					normalMap.put("addresseeMobile", parParceladdress.getAddresseeMobile());
					normalMap.put("addresseeName", parParceladdress.getAddresseeName());
					//详细地址
					String addresseeAddress = parParceladdress.getAddresseeAddress();
					normalMap.put("addresseeAddress", addresseeAddress);
				}else {
					normalMap.put("addresseeAddress", "");
					normalMap.put("addresseeMobile", "");
					normalMap.put("addresseeName", "");
				}
				if(!PubMethod.isEmpty(par.getNetId())){
					HashMap<String, Object> netMap = parParceladdressMapperV4.queryNetInfo(par.getNetId());
					normalMap.put("net_name", PubMethod.isEmpty(netMap.get("net_name")) ? "" : netMap.get("net_name").toString());
				}else{
					normalMap.put("net_name", ""); 
				}
				normalList.add(normalMap);
			}
		}
		//有派无签 列表
		Query query3 = new Query();
		query3.addCriteria(Criteria.where("actualSendMember").is(memberId));
		//signResult：签收结果 0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收）
		query3.addCriteria(Criteria.where("signResult").is((short)2));
		query3.addCriteria(Criteria.where("signTime").gte(sdf.parse(startTime)).lte(sdf.parse(endTime)));
		query3.with(new Sort(Direction.DESC, "signTime"));
		List<ParParcelinfo> sendButNoSign = this.mongoTemplate.find(query3, ParParcelinfo.class);
		if(sendButNoSign!=null && sendButNoSign.size()>0){
			for(ParParcelinfo par:sendButNoSign){
				HashMap<String, Object> normalMap = new HashMap<>();
				normalMap.put("parId", par.getUid());
				normalMap.put("freight", par.getFreight());
				normalMap.put("codAmount", par.getCodAmount());
				normalMap.put("expWaybillNum", par.getExpWaybillNum());
				
				ParParceladdress parParceladdress = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(par.getUid())), ParParceladdress.class);
				if(!PubMethod.isEmpty(parParceladdress)){
					normalMap.put("addresseeMobile", parParceladdress.getAddresseeMobile());
					normalMap.put("addresseeName", parParceladdress.getAddresseeName());
					//详细地址
					String addresseeAddress = parParceladdress.getAddresseeAddress();
					normalMap.put("addresseeAddress", addresseeAddress);
				}else {
					normalMap.put("addresseeAddress", "");
					normalMap.put("addresseeMobile", "");
					normalMap.put("addresseeName", "");
				}
				if(!PubMethod.isEmpty(par.getNetId())){
					HashMap<String, Object> netMap = parParceladdressMapperV4.queryNetInfo(par.getNetId());
					normalMap.put("net_name", PubMethod.isEmpty(netMap.get("net_name")) ? "" : netMap.get("net_name").toString());
				}else{
					normalMap.put("net_name", ""); 
				}
				sendButNoSignList.add(normalMap);
			}
		}
		//转代收签收 列表
		Query query4 = new Query();
		query4.addCriteria(Criteria.where("actualSendMember").is(memberId));
		//signResult：签收结果 0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收）
		query4.addCriteria(Criteria.where("signResult").is((short)3));
		query4.addCriteria(Criteria.where("signTime").gte(sdf.parse(startTime)).lte(sdf.parse(endTime)));
		query4.with(new Sort(Direction.DESC, "signTime"));
		List<ParParcelinfo> changeSign = this.mongoTemplate.find(query4, ParParcelinfo.class);
		if(changeSign!=null && changeSign.size()>0){
			for(ParParcelinfo par:changeSign){
				HashMap<String, Object> normalMap = new HashMap<>();
				normalMap.put("parId", par.getUid());
				normalMap.put("freight", par.getFreight());
				normalMap.put("codAmount", par.getCodAmount());
				normalMap.put("expWaybillNum", par.getExpWaybillNum());
				
				ParParceladdress parParceladdress = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(par.getUid())), ParParceladdress.class);
				if(!PubMethod.isEmpty(parParceladdress)){
					normalMap.put("addresseeMobile", parParceladdress.getAddresseeMobile());
					normalMap.put("addresseeName", parParceladdress.getAddresseeName());
					//详细地址
					String addresseeAddress = parParceladdress.getAddresseeAddress();
					normalMap.put("addresseeAddress", addresseeAddress);
				}else {
					normalMap.put("addresseeAddress", "");
					normalMap.put("addresseeMobile", "");
					normalMap.put("addresseeName", "");
				}
				if(!PubMethod.isEmpty(par.getNetId())){
					HashMap<String, Object> netMap = parParceladdressMapperV4.queryNetInfo(par.getNetId());
					normalMap.put("net_name", PubMethod.isEmpty(netMap.get("net_name")) ? "" : netMap.get("net_name").toString());
				}else{
					normalMap.put("net_name", ""); 
				}
				changeSignList.add(normalMap);
			}
		}
		//2.查询异常签收派件列表
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("actualSendMember").is(memberId));
		query2.addCriteria(Criteria.where("exceptionTime").gte(sdf.parse(startTime)).lte(sdf.parse(endTime)));
		query2.with(new Sort(Direction.DESC, "exceptionTime"));
		List<ParParcelinfo> exception = this.mongoTemplate.find(query2, ParParcelinfo.class);
		if(exception!=null && exception.size()>0){
			for(ParParcelinfo par:exception){
				HashMap<String, Object> exceptionMap = new HashMap<>();
				exceptionMap.put("parId", par.getUid());
				exceptionMap.put("freight", par.getFreight());
				exceptionMap.put("codAmount", par.getCodAmount());
				exceptionMap.put("expWaybillNum", par.getExpWaybillNum());
				exceptionMap.put("disposalDesc", par.getDisposalDesc());
				
				ParParceladdress parParceladdress = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(par.getUid())), ParParceladdress.class);
				if(!PubMethod.isEmpty(parParceladdress)){
					exceptionMap.put("addresseeMobile", parParceladdress.getAddresseeMobile());
					exceptionMap.put("addresseeName", parParceladdress.getAddresseeName());
					//详细地址
					String addresseeAddress = parParceladdress.getAddresseeAddress();
					exceptionMap.put("addresseeAddress", addresseeAddress);
				}else {
					exceptionMap.put("addresseeAddress", "");
					exceptionMap.put("addresseeMobile", "");
					exceptionMap.put("addresseeName", "");
				}
				if(!PubMethod.isEmpty(par.getNetId())){
					HashMap<String, Object> netMap = parParceladdressMapperV4.queryNetInfo(par.getNetId());
					exceptionMap.put("net_name", PubMethod.isEmpty(netMap.get("net_name")) ? "" : netMap.get("net_name").toString());
				}else{
					exceptionMap.put("net_name", ""); 
				}
				exceptionList.add(exceptionMap);
			}
		}
		resultMap.put("finishList", normalList);//正常签收-签
		resultMap.put("finishCount", normalList.size());
		resultMap.put("exceptionList",exceptionList);//异常签收-异
		resultMap.put("exceptionCount", exceptionList.size());
		resultMap.put("sendButNoSignList",sendButNoSignList);//有派无签-派
		resultMap.put("sendButNoSignCount", sendButNoSignList.size());
		resultMap.put("changeSignList",changeSignList);//转代收签收-转
		resultMap.put("changeSignCount", changeSignList.size());
		return resultMap;
	}
	/**
	 * 判断该手机号是否注册-0否1是
	 */
	@Override
	public HashMap<String, Object> isRegisterByPhone(String memberPhone) {
		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> map = MemberInfoMapperV2.isRegisterByPhone(memberPhone);
		if(PubMethod.isEmpty(map)){
			resultMap.put("isRegister", "0");
		}else {
			resultMap.put("isRegister", "1");
		}
		return resultMap;
	}
	/**
	 * 查询待派数量和待提数量
	 */
	@Override
	public HashMap<String, Object> querySendCountAll(String memberId) {
		HashMap<String, Object> resultMap = new HashMap<>();
		//1.待提包裹数量
		Query query = new Query();
        query.addCriteria(Criteria.where("actualSendMember").is(Long.parseLong(memberId)));
        query.addCriteria(Criteria.where("sendTaskId").is(null));
        query.addCriteria(Criteria.where("parcelEndMark").ne("1"));
		Long toBeTakencount = mongoTemplate.count(query, ParParcelinfo.class);
		logger.info("待提包裹数量："+toBeTakencount+"，query="+query.toString());
		resultMap.put("toBeTakencount", toBeTakencount);
		//2.待派任务数量
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("actorMemberId").is(Long.parseLong(memberId)));
		query2.addCriteria(Criteria.where("taskType").is(Byte.parseByte("1")));//1派件
		query2.addCriteria(Criteria.where("taskFlag").is(Byte.parseByte("0")));
		query2.addCriteria(Criteria.where("taskIsEnd").is(Byte.parseByte("0")));
		Long sendTaskcount = mongoTemplate.count(query2, ParTaskInfo.class);
		logger.info("待派任务数量："+sendTaskcount+"，query="+query2.toString());
		resultMap.put("sendTaskcount", sendTaskcount);
		return resultMap;
	}
	/**
	 * 快递员离职
	 */
	@Override
	public HashMap<String, Object> leaveOffice(Long memberId, Long compId,String memberName, String memberPhone) {
		HashMap<String, Object> resultMap = new HashMap<>();
		//1.查询三天内抢单取件任务是否完成？完成则可以离职，未完成则不能离职
		//SELECT * FROM par_task_info WHERE task_type = 0 AND actor_member_id = 1 AND task_flag = 1 and task_is_end = 0 AND create_time BETWEEN '' and '';
		Query query = new Query();
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));//收派员id
		query.addCriteria(Criteria.where("taskType").is(Byte.parseByte("0")));//0取件
		query.addCriteria(Criteria.where("taskFlag").is(Byte.parseByte("1")));//1抢单
		query.addCriteria(Criteria.where("taskIsEnd").is(Byte.parseByte("0")));//是否结束？0否1是
		//三天之内
		Date now = new Date();
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, -3);
		Date before = ca.getTime();
		query.addCriteria(Criteria.where("createTime").lte(now).gte(before));//<=now  >=before
		List<ParTaskInfo> list = this.mongoTemplate.find(query, ParTaskInfo.class);
		if(list!=null && list.size()>0){
			resultMap.put("isFinish", "1");
			logger.info("快递员有三天内未完成的抢单取件任务！不能离职，赶紧去完成！memberId="+memberId+";compId="+compId+"!");
		}else {
			//2.离职删除相关信息
			this.leave(memberId, compId, memberName, memberPhone);
			resultMap.put("isFinish", "0");
			logger.info("恭喜你，终于可以正式离职了！拜拜！！~");
		}
		this.redisService.remove("memberInfo-byzhaohu-cache", String.valueOf(memberId));//清理快递员基本信息缓存
		return resultMap;
	}
	/**
	 * 离职删除相关信息
	 */
	public void leave(Long memberId, Long compId,String memberName, String memberPhone){
		Map<String, Object> map = new HashMap<String, Object>();
		this.basEmployeeAuditMapper.deleteRelationLogByMemberId(memberId);       //1.删除bas_employeeaudit归属关系
		int a = this.basEmployeeRelationMapper.deleteMemberInfo(memberId);       //2.删除bas_employee_relation人员表
		map = createMap(memberId, compId, memberName);
		int b = this.basCompEmployeeResumeMapper.doAddResum(map);                //3.增加bas_comp_employee_resume履历表
		this.areaElectronicFenceService.removeAreaMember(compId, memberId);      //4.更新片区收派员---？？
		expCustomerInfoService.deleteByMemberId(compId, memberId);               //5.删除exp_customer_member_relation客户收派员关系表----？？
																				 //6.删除par_parcelconnection收派过程记录表 ----？？
		List<ParParcelconnection> list = this.mongoTemplate.find(Query.query(Criteria.where("expMemberId").is(memberId)), ParParcelconnection.class);
		if(list!=null && list.size()>0){
			for(ParParcelconnection  con : list){
				Query query = new Query();
				query.addCriteria(Criteria.where("uid").is(con.getUid()));
				query.addCriteria(Criteria.where("mobilePhone").is(con.getMobilePhone()));//片键条件
				this.mongoTemplate.remove(query, ParParcelconnection.class);
			}
		}
		//清理缓存
		ehcacheService.remove("employeeCache", compId.toString());
		ehcacheService.remove("resumCache", String.valueOf(memberId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
		ehcacheService.remove("memberInfoMobilechCache", compId.toString());
		ehcacheService.remove("memberInfochCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		ehcacheService.remove("employeeRelationCache", String.valueOf(memberId));//
		this.redisService.remove("relationInfo-memberId-cache", String.valueOf(memberId));
		this.redisService.removeAll("findShopowner1xxx");
		this.redisService.removeAll("findShopowner2xxx");
		this.redisService.removeAll("findShopowner3xxx");
	}
	
	private Map<String, Object> createMap(Long memberId, Long compId, String memberName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resumId", IdWorker.getIdWorker().nextId());
		map.put("compId", compId);
		map.put("compName", queryCompNameById(compId).get(0).get("compName"));
		map.put("netId", queryCompNameById(compId).get(0).get("netId"));
		map.put("memberId", memberId);
		map.put("memberName", memberName);
		map.put("createTime", new Date());
		map.put("employeeOnTheJobFlag", 2);
		return map;
	}
	/**
	 * 
	 * @MethodName: net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.java.queryCompNameById 
	 * @Description: TODO(通过compId获取网点资料) 
	 * @param @param compId
	 * @param @return   
	 * @return List<Map<String,Object>>  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	public List<Map<String, Object>> queryCompNameById(Long compId) {
		List<Map<String, Object>> list = this.memberInfoMapper.queryCompNameById(compId);
		return list;
	}
	/**
	 *  转代收--根据代收点名称和手机号判断代收点是否存在
	 */
	@Override
	public Map<String,Object> ifCompExist(String compName, String compMobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> ifCompExist = parParcelinfoMapperV4.ifCompExist(compName,compMobile);
		if(ifCompExist.size()>0){
			map.put("ifCompExist", "1");
			map.put("compInfo", ifCompExist);
		}else{
			map.put("ifCompExist", "0");
			map.put("compInfo", ifCompExist);
		}
		return map;
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
	@Override
	public String updateParcelStatusBySendTaskId(Long sendTaskId, Short sendSmsStatus,Short callBackStatus,Short sendSmsType) {
		try {
			ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("sendTaskId").is(sendTaskId)), ParParcelinfo.class);
			Query query = new Query();
			Update update = new Update();
			update.set("sendSmsStatus", sendSmsStatus);
			update.set("callBackStatus", callBackStatus);
			update.set("sendSmsType", sendSmsType);
			query.addCriteria(Criteria.where("uid").is(parInfo.getUid()));
			query.addCriteria(Criteria.where("sendTaskId").is(sendTaskId));
			query.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));//片键条件
			mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
			return "001";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "002";
	}
	/**
	 * 根据taskid修改是否发短信状态为1,0否1是
	 */
	@Override
	public void updateIsSendMsgFlag(String sendTaskId) {
		ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("sendTaskId").is(Long.parseLong(sendTaskId))), ParParcelinfo.class);
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(parInfo.getUid()));
		query.addCriteria(Criteria.where("sendTaskId").is(Long.parseLong(sendTaskId)));
		query.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));//片键条件
		this.mongoTemplate.updateFirst(query, Update.update("isSendMsg", (short)1), ParParcelinfo.class);
	}
	
	/**
	 * 根据包裹parid修改编号 
	 */
	@Override
	public void updateNumByParId(String jsonData) {
		//{"items":[{"parId":"123","parNum":"123","phone": "123"},{"parId":"456","parNum":"456","phone": "456"}]}
		JSONObject jsonOBJ = JSONObject.parseObject(jsonData);
		if(!PubMethod.isEmpty(jsonOBJ)){
			List<Map<String, String>> maplist = (List<Map<String, String>>) jsonOBJ.get("items");
			if(maplist!=null && maplist.size()>0){
				for(Map<String, String> map : maplist){
					String parId = map.get("parId");
					String parNum = map.get("parNum");
					String phone = map.get("phone");
					if(PubMethod.isEmpty(parId)){
						throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.updateNumByParId.001","parId为空，数据异常，请核查数据库！");
					}
					ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parId))), ParParcelinfo.class);
					Query query = new Query();
					query.addCriteria(Criteria.where("uid").is(Long.parseLong(parId)));
					query.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));//片键条件
					this.mongoTemplate.updateFirst(query, Update.update("parcelNum", parNum), ParParcelinfo.class);
				}
			}
		}
	}
	
	/**
	 * 群发通知--批量保存电话号码为包裹信息
	 */
	@Override
	public void saveParcels(String memberId,String phone, String phoneData) {
		String [] phoneArry = phoneData.split(",");
		if(phoneArry!=null && phoneArry.length>0){
			for(String mobilePhone : phoneArry){
				ParTaskInfo parTaskInfo = new ParTaskInfo();
				Date now=new Date();
				parTaskInfo.setUid(IdWorker.getIdWorker().nextId());
				parTaskInfo.setTaskType((byte)1);
				parTaskInfo.setCoopCompId(null);
				parTaskInfo.setCoopNetId(null);
				parTaskInfo.setParEstimateCount((byte)1);
				parTaskInfo.setTaskSource((byte)-1);
				parTaskInfo.setTaskStatus((byte)11); //TODO
				parTaskInfo.setTaskIsEnd((byte)0); //TODO
				parTaskInfo.setActorMemberId(Long.parseLong(memberId));
				parTaskInfo.setActorPhone(phone);
				parTaskInfo.setContactName("");
				parTaskInfo.setContactMobile(mobilePhone);
				parTaskInfo.setContactAddressId(null);
				parTaskInfo.setContactAddress(null);
				parTaskInfo.setCreateTime(now);
				parTaskInfo.setCreateUserId(Long.parseLong(memberId));
				parTaskInfo.setTaskFlag((byte)0);
				parTaskInfo.setModifyTime(now);
				parTaskInfo.setMobilePhone(mobilePhone);//片键
				ParParcelinfo par=new ParParcelinfo();
				par.setUid(IdWorker.getIdWorker().nextId());
				par.setSendTaskId(parTaskInfo.getUid());
				par.setCreateUserId(Long.parseLong(memberId));
				par.setActualSendMember(Long.parseLong(memberId));
				par.setSendSmsType(null);
				par.setSendSmsStatus(null);
				par.setCallBackStatus(null);
				par.setCreateTime(now);
				par.setParcelNum("");
				par.setReplyStatus(null);
				par.setParcelEndMark("0");
				par.setIsSendMsg((short)0);//是否发送过短信，0否1是
				par.setMobilePhone(mobilePhone);//片键
				ParParceladdress parParceladdress=new ParParceladdress();
				parParceladdress.setUid(par.getUid());
				parParceladdress.setAddresseeMobile(mobilePhone);//片键
				ParParcelconnection arParcelconnection=new ParParcelconnection();
				arParcelconnection.setTaskId(parTaskInfo.getUid());
				arParcelconnection.setParId(par.getUid());
				arParcelconnection.setUid(IdWorker.getIdWorker().nextId());
				arParcelconnection.setMobilePhone(mobilePhone);//片键
				mongoTemplate.insert(par);
				mongoTemplate.insert(parTaskInfo);
				mongoTemplate.insert(parParceladdress);
				mongoTemplate.insert(arParcelconnection);
			}
		}
		
	}
	
	
	
	
	
	
	
}
