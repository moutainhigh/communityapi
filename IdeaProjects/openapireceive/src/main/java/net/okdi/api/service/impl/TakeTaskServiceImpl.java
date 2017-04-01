package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasCompbusinessMapper;
import net.okdi.api.dao.BasEmployeeRelationMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.BasOnLineMemberMapper;
import net.okdi.api.dao.DicAddressaidMapper;
import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.dao.ParParceladdressMapper;
import net.okdi.api.dao.ParParcelinfoMapper;
import net.okdi.api.dao.ParTaskDisposalRecordMapper;
import net.okdi.api.dao.ParTaskInfoMapper;
import net.okdi.api.dao.ParTaskProcessMapper;
import net.okdi.api.dao.RobParcelRelationMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasEmployeeRelation;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.entity.ExpCompRelation;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.api.entity.ParTaskDisposalRecord;
import net.okdi.api.entity.ParTaskInfo;
import net.okdi.api.entity.ParTaskProcess;
import net.okdi.api.entity.RobParcelRelation;
import net.okdi.api.job.TakeTaskJob;
import net.okdi.api.service.BusinessBranchService;
import net.okdi.api.service.CourierService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.MobMemberLoginService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.api.vo.TaskVo;
import net.okdi.api.vo.VO_MemberInfo;
import net.okdi.apiV1.dao.BasNetInfoMapperV1;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.apiV4.service.TaskRemindService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.common.quartz.JobExecutor;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.DateUtil;
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

@Service
public class TakeTaskServiceImpl extends BaseServiceImpl<ParTaskInfo> implements TakeTaskService {
	public static final Log logger = LogFactory.getLog(TakeTaskServiceImpl.class);
	//缓存开关
	private boolean CACHE_OPEN = true;

	//任务来源
	final Byte task_source_okdi = 1; //好递网
	final Byte task_source_exp = 2; //站点自建
	final Byte task_source_ec = 3; //电商管家
	final Byte task_source_app_personal = 4; //好递个人端
	final Byte task_source_app_exp = 5; //好递接单王
	//处理方类型
	final Byte disposal_object_member = 0; //派送员
	final Byte disposal_object_sendexp = 1; //派送站点
	final Byte disposal_object_custservice = 2; //客服
	final Byte disposal_object_department = 3; //营业分部
	final Byte disposal_object_exp = 4; //站点
	//任务状态
	final Byte task_status_untake = 0; //待处理
	final Byte task_status_distribute = 1; //已分配
	final Byte task_status_finish = 2; //已完成
	final Byte task_status_cancel = 3; //已取消
	final Byte task_status_delete = 10; //任务删除
	//日志状态
	final Byte log_task_status_distribute = 0; //指派
	final Byte log_task_status_cancel = 1; //取消
	final Byte log_task_status_refuse = 2; //拒绝
	final Byte log_task_status_finish = 4; //完结
	
	//显示状态
	final Byte show_flag_hide = 0; //隐藏
	final Byte show_flag_show = 1; //显示
	//异常状态
	final Byte task_error_flag_ok = 0; //正常
	final Byte task_error_flag_error = 1; //异常
	//任务创建类型
	final Byte task_original = 0; //正常
	final Byte task_grab = 1; //抢单

	@Autowired
	private ParTaskInfoMapper parTaskInfoMapper;
	@Autowired
	private ParTaskProcessMapper parTaskProcessMapper;
	@Autowired
	private ParTaskDisposalRecordMapper parTaskDisposalRecordMapper;
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private BasEmployeeRelationMapper basEmployeeRelationMapper;
	@Autowired
	private DicAddressaidMapper dicAddressaidMapper;
	@Autowired 
	private ReceivePackageService receivePackageService;
	@Autowired
	private EhcacheService ehcacheService; //缓存
	@Autowired
	private BusinessBranchService businessBranchService;
	/*@Autowired
	private ExpCustomerInfoService expCustomerInfoService;*/
	@Autowired
	private SendNoticeService sendNoticeService;  //消息推送服务
	
	@Autowired
    private CourierService courierService;
	
    @SuppressWarnings("unused")
	@Autowired
    private BasNetInfoMapper basNetInfoMapper;
    
    @Autowired
    private BasOnLineMemberMapper basOnLineMemberMapper;
    
    @Autowired
	private ParParcelinfoMapper parParcelinfoMapper;
    
    @SuppressWarnings("unused")
	@Autowired
	private BasCompbusinessMapper basCompbusinessMapper;
    
    @Value("${save.courier.head}")
	private String imageUrl;
    
    @Value("${net.info.pic.url}")
    private String netImageUrl;
    
    
    @Autowired
	private RobParcelRelationMapper robParcelRelationMapper;
    @Autowired
	private ParcelInfoService parcelInfoService;
    @Autowired
	private ParParceladdressMapper parParceladdressMapper;
    @Autowired
	private ConstPool constPool;
    @Autowired
    private MobMemberLoginService mobMemberLoginService;
    
    @Autowired
    private TaskRemindService taskRemindService;
    
	@Autowired
	private NoticeHttpClient noticeHttpClient;
    
    @Autowired
	private MongoTemplate mongoTemplate;
    
    @Autowired
    private BasNetInfoMapperV1 basNetInfoMapperV1;
    
    @SuppressWarnings("unused")
	@Autowired
    private MemberInfoMapper memberInfoMapper;
    
    private JobExecutor jobExecutor = new JobExecutor();
    
    private static final Object lock = new Object();
    
	/*private void addContacts(Long compId, String contactName, String contactMobile,
			String contactsPhone, Short customerType, Long townId,
			String detailedAddress, Long memberId, String townName) throws ServiceException {
		ExpCustomerInfo customer = new ExpCustomerInfo();
		customer.setCompId(compId);
		customer.setCustomerName(contactName);
		customer.setTownName(townName);
		customer.setContactsMobile(contactMobile);
		customer.setContactsPhone(contactsPhone);
		customer.setCustomerType(customerType);
		customer.setTownId(townId);
		customer.setDetailedAddress(detailedAddress);
		customer.setMemberId(memberId);
		expCustomerInfoService.saveExpCustomerInfo(customer);
	}*/

	@Override
	//通过手机号查询联系人信息
	public Map<String, Object> loadContacts(String mobile, Long compId) throws ServiceException {
//		if(PubMethod.isEmpty(mobile)) {
//			throw new ServiceException("openapi.TakeTaskServiceImpl.loadContacts.001", "请输入发件人手机号");
//		}
//		if(PubMethod.isEmpty(compId)) {
//			throw new ServiceException("openapi.TakeTaskServiceImpl.loadContacts.003", "当前登陆人无站点信息");
//		}
//		ExpCustomerInfo customer = expCustomerInfoService.getExpCustomerInfoByContactsMobileAndCopmId(mobile, compId);
//		Map<String, Object> map = null;
//		if(customer != null) {
//			DicAddressaid dicAddress = ehcacheService.get("addressCache",String.valueOf(customer.getTownId()), DicAddressaid.class);
//			Long addrIdLev4 = null;
//			if(PubMethod.isEmpty(dicAddress)) {
//				addrIdLev4 = dicAddressaidMapper.getParentId(customer.getTownId());
//			} else {
//				addrIdLev4 = dicAddress.getParentId();
//			}
//			map = new HashMap<String, Object>();
//			map.put("customerId", customer.getId());
//			map.put("customerType", customer.getCustomerType());
//			map.put("contactsPhone", customer.getContactsPhone());
//			map.put("customerName", customer.getCustomerName());
//			map.put("addrIdLev5", customer.getTownId());
//			map.put("addrIdLev4", addrIdLev4);
//			map.put("detailedAddress", customer.getDetailedAddress());
//			map.put("memberId", customer.getMemberId());
//			//map.put("okdiCustomerId", customer.getOkdiCustomerId());
//			if(customer.getMemberId() != null) {
//				MemberInfo member = getMember(customer.getMemberId());
//				if(member != null) {
//					map.put("memberName", member.getMemberName());
//				} else {
//					map.put("memberName", "");
//				}
//			} else {
//				map.put("memberName", "");
//			}
//		} else {
//			throw new ServiceException("openapi.TakeTaskServiceImpl.loadContacts.002", "无发件人信息");
//		}
//		return map;
		throw new ServiceException("openapi.TakeTaskServiceImpl.loadContacts.002", "无发件人信息");
		//return null;
	}
	
	@Override
	public Map<String, Object> getCompOrEmployee(Long compId, BigDecimal addrLongitude, BigDecimal addrLatitude) throws ServiceException {
		if(PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.getCompOrEmployee.001", "当前操作人所属站点不存在");
		}
		Map<String, Long> map = new HashMap<String, Long>();
		map = autoDistribute(compId, addrLongitude, addrLatitude);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(map != null) {
			if(map.get("memberId") != null && !"".equals(map.get("memberId").toString())) {
				resultMap.put("memberId", map.get("memberId").toString());
				MemberInfo memberinfo = getMember(Long.parseLong(map.get("memberId").toString()));
				if(memberinfo != null && memberinfo.getMemberName() != null) {
					resultMap.put("memberName", memberinfo.getMemberName());
					resultMap.put("compType", 0);
				} else {
					resultMap.put("memberName", "");
					resultMap.put("compType", "");
				}
			} else {
				resultMap.put("memberId", "");
				resultMap.put("memberName", "");
				resultMap.put("compType", "");
			}
			if(map.get("compId") != null && !"".equals(map.get("compId").toString())) {
				resultMap.put("compId", map.get("compId").toString());
				BasCompInfo basCompInfo = getComp(Long.parseLong(map.get("compId").toString()));
				if(basCompInfo != null && basCompInfo.getCompName() != null) {
					resultMap.put("compName", basCompInfo.getCompName());
					resultMap.put("compType", basCompInfo.getCompTypeNum());
				} else {
					resultMap.put("compName", "");
					resultMap.put("compType", "");
				}
			} else {
				resultMap.put("compId", "");
				resultMap.put("compName", "");
			}
		}
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> queryTask(String senderName, String startTime, String endTime, String senderPhone,
			String spacetime, Long memberId, Long compId, Long operatorCompId, Page page, Byte querystatus) throws ServiceException {
		if(PubMethod.isEmpty(operatorCompId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.queryTask.001", "当前操作人无所属站点");
		}
		//cache参数拼接
		String cacheParam = "";
		Map<String,Object> map = new HashMap<String, Object>();
		if(senderName != null && !"".equals(senderName)) {
			map.put("contactName", "%"+senderName+"%");
			cacheParam += "contactName=%"+senderName+"%";
		} else {
			map.put("contactName", "");
			cacheParam += "contactName=";
		}
		if(startTime != null && !"".equals(startTime)) {
			map.put("startTime", DateUtil.getZeroTimeOfDay(startTime));
			cacheParam += "startTime="+startTime;
		} else {
			map.put("startTime", null);
			cacheParam += "startTime=";
		}
		if(endTime != null && !"".equals(endTime)) {
			map.put("endTime", DateUtil.getEndTimeOfDay(endTime));
			cacheParam += "endTime="+endTime;
		} else {
			map.put("endTime", null);
			cacheParam += "endTime=";
		}
		map.put("contactMobile", senderPhone);
		cacheParam += "contactMobile="+senderPhone;
		map.put("spacetime", getTime(spacetime)); //持续时间
		map.put("spacetimeparam", spacetime); //持续时间
		cacheParam += "spacetimeparam="+spacetime;
		map.put("memberId", memberId);
		cacheParam += "memberId="+memberId;
		map.put("compId", compId);
		cacheParam += "compId="+compId;
		map.put("disposalType", querystatus);
		cacheParam += "disposalType="+querystatus;
		map.put("page", page);
		cacheParam += "currentPage="+page.getCurrentPage()+"pageSize="+page.getPageSize();
		map.put("operatorCompId", operatorCompId);
		cacheParam += "operatorCompId="+operatorCompId;
		//计算页数
		int pageNum = 0;
		//查询结果
		List<Map<String,Object>> resultlist = null;
		//保存此站点的条件查询语句
		List<String> compQueryList = null;
		//判断compid类型
		if(disposal_object_exp.equals(compTypeNum(operatorCompId))) {//站点
			map.put("disposalObject", disposal_object_exp);
			cacheParam += "disposalObject="+disposal_object_exp;
		} else if(disposal_object_department.equals(compTypeNum(operatorCompId))) { //营业分部
			map.put("disposalObject", disposal_object_department);
			cacheParam += "disposalObject="+disposal_object_department;
		}
		String cacheCount = cacheParam + "Count";
		boolean queryByDB = false;
		int totalCount = 0;
		
		if(ehcacheService.getByKey("TakeTaskCacheQueryResult", cacheParam)) { //缓存不为空
			//map中key为查询条件拼串，value为查询结果集
			resultlist = getCache("TakeTaskCacheQueryResult", cacheParam, ArrayList.class);
			if(!PubMethod.isEmpty(resultlist)) {
				String strCount = getCache("TakeTaskCacheQueryResult", cacheCount, String.class);
				//String strCount = getCache("TakeTaskCacheQueryResult", operatorCompId+"Count"+querystatus, String.class);
				Integer count = new Integer(0);
				if(!PubMethod.isEmpty(strCount)) {
					count = Integer.parseInt(strCount);
				}
				if(!PubMethod.isEmpty(count)) {
					totalCount = count.intValue();
				}
				Map<String,Object> addmap = null;
				List<Map> maptemp = JSON.parseArray(resultlist.toString(), java.util.Map.class);
				for(int m = 0; m < maptemp.size(); m++) {
					addmap = new HashMap<String,Object>();
					if(!PubMethod.isEmpty(maptemp.get(m).get("id"))) {
						addmap.put("id", maptemp.get(m).get("id"));
					}
					if(!PubMethod.isEmpty(maptemp.get(m).get("taskId"))) {
						addmap.put("taskId", maptemp.get(m).get("taskId"));
					}
					if(!PubMethod.isEmpty(maptemp.get(m).get("cancelId"))) {
						addmap.put("cancelId", maptemp.get(m).get("cancelId"));
					}
					resultlist.set(m, addmap);
				}
			}
			queryByDB = false;
		} else {
			queryByDB = true;
		}
		//查询数据库
		if(queryByDB) {
			//统计页数，返回结果值
			totalCount = parTaskDisposalRecordMapper.queryTaskByExpCount(map);
			if(task_status_cancel.equals(querystatus)) { //查询取消任务记录，需要关联log表
				resultlist = parTaskDisposalRecordMapper.queryCancelTaskByExp(map);
			} else {
				resultlist = parTaskDisposalRecordMapper.queryTaskByExp(map);
			}
			//存入缓存
			putCache("TakeTaskCacheQueryResult", cacheParam, resultlist);
			putCache("TakeTaskCacheQueryResult", cacheCount, totalCount);
			//putCache("TakeTaskCacheQueryResult", operatorCompId+"Count"+querystatus, totalCount);
			
			//获取缓存中当前站点查询条件集合
			if(ehcacheService.getByKey("TakeTaskCacheQueryResult", String.valueOf(operatorCompId))) {
				compQueryList = getCache("TakeTaskCacheQueryResult", String.valueOf(operatorCompId), ArrayList.class);
			}
			if(compQueryList==null){
				compQueryList = new ArrayList();
			}
			if(!compQueryList.contains(cacheParam)){
				compQueryList.add(cacheParam);
			}
			if(!compQueryList.contains(cacheCount)) {
				compQueryList.add(cacheCount);
			}
			//存入缓存
			putCache("TakeTaskCacheQueryResult", String.valueOf(operatorCompId), compQueryList);
		}
		//compQueryList = getCache("TakeTaskCacheQueryResult", String.valueOf(operatorCompId), ArrayList.class);
		if(totalCount % page.getPageSize() > 0) {
			pageNum = (totalCount/page.getPageSize()) + 1;
		}
		if(resultlist != null && resultlist.size() > 0) {
			ParTaskDisposalRecord cacheRecord = null;
			ParTaskInfo cacheTaskInfo = null;
			ParTaskProcess cacheProcess = null;
			for(int i = 0; i < resultlist.size(); i++) {
				//任务记录缓存
				cacheRecord = cacheRecord(String.valueOf(resultlist.get(i).get("id")));
				//任务信息缓存
				cacheTaskInfo = cacheTaskInfo(String.valueOf(resultlist.get(i).get("taskId")));
				//取消任务查询任务日志缓存
				if(task_status_cancel.equals(querystatus)) {
					cacheProcess = cacheProcess(String.valueOf(resultlist.get(i).get("cancelId")));
				}
				//组装数据
				resultlist.set(i, setResultList(resultlist.get(i), cacheRecord, cacheTaskInfo, cacheProcess));
			}
		}
		page.setItems(resultlist);
		if(resultlist != null && resultlist.size() > 0) {
			for(int i=0;i<page.getItems().size();i++){
				//处理持续时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createTime = sdf.format(((HashMap)page.getItems().get(i)).get("createTime"));
				String date = DateUtil.getSysOptDate(); //系统当前时间
				HashMap<String, Long> timeMap = DateUtil.getTimeDescriptionMap(date, createTime);
				String timeDesc=null;
				if(timeMap.get("minute") != null && !"null".equals(timeMap.get("minute")) && timeMap.get("minute")>=0){
					timeDesc = timeMap.get("minute")+"分";
				}
				if(timeMap.get("hour") != null && !"null".equals(timeMap.get("hour")) && timeMap.get("hour")>0){
					timeDesc = timeMap.get("hour")+"时"+timeDesc;
				}
				if(timeMap.get("day") != null && !"null".equals(timeMap.get("day")) && timeMap.get("day")>0){
					timeDesc = timeMap.get("day")+"天"+timeDesc;
				}
				if(null==timeDesc){
					timeDesc="0分";
				}
				((HashMap<String, Object>) page.getItems().get(i)).put("spacetime",timeDesc);
			}
		}
		page.setPageCount(pageNum);
		page.setTotal(totalCount);
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("page", page);
		return resultmap;
	}
	
	private ParTaskDisposalRecord cacheRecord(String key) throws ServiceException {
		String record = getCache("TakeTaskCacheTaskRecord", key, String.class);
		ParTaskDisposalRecord cacheRecord = null;
		if(!PubMethod.isEmpty(record)) {
			cacheRecord = getCache("TakeTaskCacheTaskRecord", key, ParTaskDisposalRecord.class);
		}
		if(PubMethod.isEmpty(cacheRecord)) {
			cacheRecord = parTaskDisposalRecordMapper.selectByPrimaryKey(Long.parseLong(key));
			putCache("TakeTaskCacheTaskRecord", key, cacheRecord);
		}
		logger.debug(cacheRecord);
		return cacheRecord;
	}
	
	@Override
	public ParTaskInfo cacheTaskInfo(String key) throws ServiceException {
		ParTaskInfo cacheTaskInfo = null;
		String taskInfo = getCache("TakeTaskCacheTaskInfo", key, String.class);
		if(!PubMethod.isEmpty(taskInfo)) {
			cacheTaskInfo = getCache("TakeTaskCacheTaskInfo", key, ParTaskInfo.class);
		}
		if(PubMethod.isEmpty(cacheTaskInfo)) {
			cacheTaskInfo = parTaskInfoMapper.selectByPrimaryKey(Long.parseLong(key));
			putCache("TakeTaskCacheTaskInfo", key, cacheTaskInfo);
		}
		return cacheTaskInfo;
	}
	
	private ParTaskProcess cacheProcess(String key) throws ServiceException {
		ParTaskProcess cacheProcess = null; 
		String process = getCache("TakeTaskCacheTaskProcess", key, String.class);
		if(!PubMethod.isEmpty(process)) {
			cacheProcess = getCache("TakeTaskCacheTaskProcess", key, ParTaskProcess.class);
		}
		if(PubMethod.isEmpty(cacheProcess)) {
			cacheProcess = parTaskProcessMapper.selectByPrimaryKey(Long.parseLong(key));
			putCache("TakeTaskCacheTaskProcess", key, cacheProcess);
		}
		return cacheProcess;
	}
	
	
	//组装数据
	private Map<String,Object> setResultList(Map<String,Object> data, ParTaskDisposalRecord cacheRecord, ParTaskInfo cacheTaskInfo, ParTaskProcess cacheProcess) throws ServiceException {
		if(!PubMethod.isEmpty(cacheRecord)) {
			data.put("disposalDesc", cacheRecord.getDisposalDesc());
			data.put("createTime", cacheRecord.getCreateTime());
			data.put("taskErrorFlag", cacheRecord.getTaskErrorFlag());
			data.put("disposalType", cacheRecord.getDisposalType());
		} else {
			data.put("disposalDesc", "");
			data.put("createTime", "");
			data.put("taskErrorFlag", "");
			data.put("disposalType", "");
		}
		if(!PubMethod.isEmpty(cacheTaskInfo)) {
			data.put("appointTime", cacheTaskInfo.getAppointTime());
			data.put("appointDesc", cacheTaskInfo.getAppointDesc());
			data.put("contactName", cacheTaskInfo.getContactName());
			data.put("contactMobile", cacheTaskInfo.getContactMobile());
			data.put("contactAddress", cacheTaskInfo.getContactAddress());
			data.put("compId", cacheTaskInfo.getCoopCompId());
			BasCompInfo basCompInfo = getComp(cacheTaskInfo.getCoopCompId());
			if(!PubMethod.isEmpty(basCompInfo)) {
				data.put("compName", basCompInfo.getCompName());
				MemberInfo memberInfo = null;
				memberInfo = getMember(cacheTaskInfo.getActorMemberId());
				if(!PubMethod.isEmpty(memberInfo)) {
					data.put("memberName", memberInfo.getMemberName());
					data.put("memberPhone", memberInfo.getMemberPhone());
				} else {
					data.put("memberName", "");
					data.put("memberPhone", "");
				}
			} else {
				data.put("compName", "");
			}
			String addrDetail = "";
			if(!task_source_ec.equals(cacheTaskInfo.getTaskSource())) {
				DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(cacheTaskInfo.getContactAddressId()), DicAddressaid.class);
				if(PubMethod.isEmpty(address)){
					address = this.dicAddressaidMapper.findById(cacheTaskInfo.getContactAddressId());
				}
				if(!PubMethod.isEmpty(address)){
					addrDetail=address.getCountyName() + address.getTownName() + cacheTaskInfo.getContactAddress().substring(cacheTaskInfo.getContactAddress().indexOf(" ")+1, cacheTaskInfo.getContactAddress().length());
					addrDetail = addrDetail.replaceAll("-城区", "").replaceAll("县城", "");
				}
			} else {
				addrDetail = cacheTaskInfo.getContactAddress();
			}
//			String addrDetail = cacheTaskInfo.getContactAddress();
//			if(addrDetail != null && !"".equals(addrDetail)) {
//				addrDetail = addrDetail.substring(addrDetail.indexOf("-")+1, addrDetail.length());
//			}
			data.put("contactAddress", addrDetail);
		} else {
			data.put("appointTime", "");
			data.put("appointDesc", "");
			data.put("contactName", "");
			data.put("contactMobile", "");
			data.put("contactAddress", "");
			data.put("compId", "");
			data.put("compName", "");
			data.put("memberName", "");
			data.put("memberPhone", "");
			data.put("contactAddress", "");
		}
		if(!PubMethod.isEmpty(cacheProcess)) {
			data.put("memberName", cacheProcess.getOperatorDesc());
			data.put("taskProcessDesc", data.get("disposalDesc"));
			data.put("taskTransmitCause", cacheProcess.getTaskTransmitCause());
			data.put("compId", cacheProcess.getOperatorCompId());
			BasCompInfo basCompInfo = getComp(cacheProcess.getOperatorCompId());
			if(!PubMethod.isEmpty(basCompInfo)) {
				data.put("compName", basCompInfo.getCompName());
			} else {
				data.put("compName", "");
			}
			data.put("cancelTime", cacheProcess.getCreateTime());
		}
		return data;
	}
	
	private String getTime(String time) throws ServiceException {
		if("h1".equals(time)){
			return DateUtil.getLastHours(-1);
		}else if("h2".equals(time)){
			return DateUtil.getLastHours(-2);
		}else if("h2m".equals(time)){
			return DateUtil.getLastHours(-2);
		}else{
			return null;
		}
	}
	

	
	
	
	
	@Override
	public Map<String, Object> queryTaskNoPage(Long memberId, Integer status, Byte taskType) throws ServiceException {
		Map<String, Object> param = new HashMap<String, Object>();
		String cacheparam = "";
		param.put("memberId", memberId);
		cacheparam += "memberId=" + memberId;
		param.put("disposalType", status);
		cacheparam += "disposalType=" + status;
		param.put("taskType", taskType);
		cacheparam += "taskType=" + taskType;
		//任务记录表和任务信息表关联查询
		Map<String, Object> resultmap = new HashMap<String, Object>();
		String noPage = getCache("TakeTaskCacheRecordResultByMemberId", cacheparam, String.class);
		List<Long> querylist = null; 
				//getCache("TakeTaskCacheRecordResultByMemberId", cacheparam, ArrayList.class); 
		if(!PubMethod.isEmpty(noPage) && !noPage.equals("[]")) {
			querylist = JSON.parseArray(noPage, java.lang.Long.class);
		} else {
			querylist = parTaskDisposalRecordMapper.queryTaskByMember(param);
			putCache("TakeTaskCacheRecordResultByMemberId", cacheparam, querylist);
		}
		List<String> cachelist = getCache("TakeTaskCacheRecordResultByMemberId", String.valueOf(memberId), ArrayList.class);
		if(cachelist==null){
			cachelist = new ArrayList<String>();
		}
		if(!cachelist.contains(cacheparam)){
			cachelist.add(cacheparam);
		}
		putCache("TakeTaskCacheRecordResultByMemberId", String.valueOf(memberId), cachelist);
		List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
		if(querylist != null && querylist.size() > 0) {
			ParTaskDisposalRecord cacheRecord = null;
			ParTaskInfo cacheTaskInfo = null;
			for(int i = 0; i < querylist.size(); i++) {
				cacheRecord = cacheRecord(String.valueOf(querylist.get(i)));
				if(!PubMethod.isEmpty(cacheRecord)) {
					cacheTaskInfo = cacheTaskInfo(String.valueOf(cacheRecord.getTaskId()));
				}
				//组装数据
				resultlist.add(setResultNoPage(cacheRecord,cacheTaskInfo));
			}
		}
		resultmap.put("resultlist", resultlist);
		return resultmap;
	}
	
	
	
	
	
	
	
	private Map<String, Object> setResultNoPage(ParTaskDisposalRecord cacheRecord, ParTaskInfo cacheTaskInfo) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!PubMethod.isEmpty(cacheRecord)) {
			map.put("id", cacheRecord.getId());
			map.put("taskId", cacheRecord.getTaskId());
			map.put("disposalDesc", cacheRecord.getDisposalDesc());
			map.put("createTime", cacheRecord.getCreateTime());
		} else {
			map.put("id", "");
			map.put("taskId", "");
			map.put("disposalDesc", "");
			map.put("createTime", "");
		}
		if(!PubMethod.isEmpty(cacheTaskInfo)) {
			map.put("parEstimateCount", cacheTaskInfo.getParEstimateCount());
			map.put("taskFlag", cacheTaskInfo.getTaskFlag());
			map.put("contactTel", cacheTaskInfo.getContactTel());
			map.put("contactAddressId", cacheTaskInfo.getContactAddressId());
			map.put("taskType", cacheTaskInfo.getTaskType());
			map.put("appointTime", cacheTaskInfo.getAppointTime());
			map.put("appointDesc", cacheTaskInfo.getAppointDesc());
			map.put("contactName", cacheTaskInfo.getContactName());
			map.put("contactMobile", cacheTaskInfo.getContactMobile());
			map.put("contactAddress", cacheTaskInfo.getContactAddress());
			map.put("memberId", cacheTaskInfo.getActorMemberId());
			map.put("taskSource", cacheTaskInfo.getTaskSource());
			map.put("expWayBillNum", "");
			map.put("codAmount", "");
			map.put("freight", "");
			map.put("parcelId", "");
		} else {
			map.put("parEstimateCount", "");
			map.put("taskFlag", "");
			map.put("contactTel", "");
			map.put("contactAddressId", "");
			map.put("taskType", "");
			map.put("appointTime", "");
			map.put("appointDesc", "");
			map.put("contactName", "");
			map.put("contactMobile", "");
			map.put("contactAddress", "");
			map.put("memberId", "");
			map.put("expWayBillNum", "");
			map.put("codAmount", "");
			map.put("freight", "");
			map.put("parcelId", "");
		}
		return map;
	}

	//分配任务
	@Override
	public String distribute(String id, Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long currentMemberId) throws ServiceException {
		ehcacheService.removeAll("TakeTaskCacheUnTakeCount");
		if(PubMethod.isEmpty(id)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.distribute.004", "请选择要分配的任务");
		}
		String[] splitId = id.split(",");
		if(splitId == null || splitId.length == 0) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.distribute.004", "请选择要分配的任务");
		}
		Map<String, Object> ToBusinesSegmtMap = new HashMap<String, Object>();
		for(int i = 0; i < splitId.length; i++) {
			distributeTask(Long.parseLong(splitId[i]), fromCompId, fromMemberId, toCompId, toMemberId, ToBusinesSegmtMap, currentMemberId);
		}
		if(ToBusinesSegmtMap != null && ToBusinesSegmtMap.get("task") != null && ToBusinesSegmtMap.get("compId") != null && ToBusinesSegmtMap.get("countNum") != null) {
			Map<String, Object> map = queryTaskUnTakeCountByCompId((Long) ToBusinesSegmtMap.get("compId"), task_status_untake);
			Integer count = new Integer(0);
			if(map != null && map.get("num") != null) {
				count = (Integer) map.get("num");
			}
			createTaskSendNoticeToBusinesSegmt((TaskVo) ToBusinesSegmtMap.get("task"), String.valueOf(ToBusinesSegmtMap.get("sendaddr")), (Long) ToBusinesSegmtMap.get("compId"), count);
		}
		
		
		return null;
	}
	
	private String distributeTask(Long id, Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Map<String, Object> ToBusinesSegmtMap, Long currentMemberId) throws ServiceException {
		ParTaskDisposalRecord taskrecord = cacheRecord(String.valueOf(id));
		TaskVo task = new TaskVo();
		if(!PubMethod.isEmpty(taskrecord)) {
			task.setTaskId(taskrecord.getTaskId());
			taskrecord.setModifiedTime(new Date());
			task.setCreateTime(taskrecord.getCreateTime());
			if(task_status_finish.equals(taskrecord.getDisposalType())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.distributeTask.001", taskrecord.getTaskId() + "任务已完结无法分配！");
			}
			//add by 201507008 已取消不能转单
			if(task_status_delete.equals(taskrecord.getDisposalType())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.003", taskrecord.getTaskId() + "任务已删除无法再次完结");
			}
			if(task_status_cancel.equals(taskrecord.getDisposalType())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.004","对方已取消发件");
//				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.004", findtask.getTaskId() + "任务已取消无法再次完结");
			}
			// add end
		} else {
			throw new ServiceException("openapi.TakeTaskServiceImpl.distributeTask.002", "任务分配失败");
		}
		task.setFromCompId(fromCompId);
		task.setFromMemberId(fromMemberId);
		task.setToCompId(toCompId);
		task.setToMemberId(toMemberId);
		task.setLogTaskStatus(log_task_status_distribute);
		task.setOperatorId(fromMemberId);
		MemberInfo memberinfo = getMember(fromMemberId);
		if(memberinfo != null) {
			task.setOperatorDesc(memberinfo.getMemberName());
		} else {
			task.setOperatorDesc("客户");
			task.setOperatorId(null);
		}
		task.setOperatorCompId(fromCompId);
		task.setCompId(toCompId);
		task.setMemberId(toMemberId);
		//需要判断分配的营业分部或收派员是否有此任务id
		String createUserId = "";
		if(toCompId != null) { //营业分部
			createUserId = distributeToDepartment(task, taskrecord.getDisposalType(), ToBusinesSegmtMap, currentMemberId);
		} else if(toMemberId != null) {//收派员
			createUserId = distributeToMember(task, taskrecord.getDisposalType(), taskrecord, currentMemberId);
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(toMemberId));
			cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(toMemberId));
		} else {
			throw new ServiceException("openapi.TakeTaskServiceImpl.distributeTask.003", "请选择分配取件员或营业分部");
		}
		
		if(taskrecord != null) {
			if(!disposal_object_member.equals(taskrecord.getDisposalObject())) {
				taskrecord.setDisposalType(task_status_distribute);
			} else {//收派员分配，将自己的状态改为取消
				taskrecord.setDisposalType(task_status_cancel);
				cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(taskrecord.getMemberId()));
				cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(taskrecord.getMemberId()));
			}
		}
		parTaskDisposalRecordMapper.updateByPrimaryKeySelective(taskrecord);
		putCache("TakeTaskCacheTaskRecord", String.valueOf(id), taskrecord);
		List<ParTaskDisposalRecord> tasklist = getDistributeTaskRecord(taskrecord.getTaskId());
		Map<Byte, ParTaskDisposalRecord> taskMap = getTaskRecordMap(tasklist);
		//清空缓存
		String recordcompId = "";
		String recordmemberId = "";
		ParTaskDisposalRecord memberRecord = taskMap.get(disposal_object_member);
		if(!PubMethod.isEmpty(memberRecord)) {
			recordmemberId = String.valueOf(memberRecord.getMemberId());
			cleanQueryInfo("TakeTaskCacheQueryResult", recordcompId);
			cleanQueryInfo("TakeTaskCacheQueryResult", recordmemberId);
			cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", recordmemberId);
		}
//		cleanQueryInfo("TakeTaskCacheQueryResult", createUserId);
//		cleanQueryInfo("TakeTaskCacheQueryResult", createUserId+"Count");
		memberRecord = taskMap.get(disposal_object_department);
		if(!PubMethod.isEmpty(memberRecord)) {
			recordcompId = String.valueOf(memberRecord.getCompId());
			recordmemberId = String.valueOf(memberRecord.getMemberId());
			cleanQueryInfo("TakeTaskCacheQueryResult", recordcompId);
			cleanQueryInfo("TakeTaskCacheQueryResult", recordmemberId);
			cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", recordmemberId);
			String fcompCount = recordcompId==null?"":recordcompId+"Count";
			String fmemberCount = recordmemberId==null?"":recordmemberId+"Count";
			removeCache("TakeTaskCacheQueryResult", fcompCount);
			removeCache("TakeTaskCacheQueryResult", fmemberCount);
			if(String.valueOf(memberRecord.getCompId()) != null && !"".equals(String.valueOf(memberRecord.getCompId()))) {
				String result = getCache("employeeCache", String.valueOf(memberRecord.getCompId()), String.class);
				List<VO_MemberInfo> list = null;
				if(!PubMethod.isEmpty(result)) {
					list = JSON.parseArray(result, VO_MemberInfo.class); 
				}
				if(!PubMethod.isEmpty(list)) {
					for(VO_MemberInfo mem : list) {
						cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(mem.getMemberId()));
						cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(mem.getMemberId()));
					}
				}
			}
			
			//清除站点下营业分部和收派员的缓存
			if(memberRecord.getCompId() != null && !"".equals(memberRecord.getCompId().toString())) {
				List<Map<String, Object>> cleanresult = this.getMemberInfoByCompId(memberRecord.getCompId());
				if(cleanresult != null && cleanresult.size() > 0) {
					for(Map<String, Object> map : cleanresult) {
						cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(map.get("memberId")));
						cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(map.get("memberId")));
					}
				}
			}
		}
		memberRecord = taskMap.get(disposal_object_exp);
		if(!PubMethod.isEmpty(memberRecord)) {
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(memberRecord.getCompId()));
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(memberRecord.getMemberId()));
			cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(memberRecord.getMemberId()));
			String fcompCount = memberRecord.getCompId()==null?"":memberRecord.getCompId()+"Count";
			String fmemberCount = memberRecord.getMemberId()==null?"":memberRecord.getMemberId()+"Count";
			removeCache("TakeTaskCacheQueryResult", fcompCount);
			removeCache("TakeTaskCacheQueryResult", fmemberCount);
			if(String.valueOf(memberRecord.getCompId()) != null && !"".equals(String.valueOf(memberRecord.getCompId()))) {
				String result = getCache("employeeCache", String.valueOf(memberRecord.getCompId()), String.class);
				List<VO_MemberInfo> list = null;
				if(!PubMethod.isEmpty(result)) {
					list = JSON.parseArray(result, VO_MemberInfo.class); 
				}
				if(!PubMethod.isEmpty(list)) {
					for(VO_MemberInfo mem : list) {
						cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(mem.getMemberId()));
						cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(mem.getMemberId()));
					}
				}
			}
			//清除站点下营业分部和收派员的缓存
			if(memberRecord.getCompId() != null && !"".equals(memberRecord.getCompId().toString())) {
				List<Map<String, Object>> cleanresult = this.getMemberInfoByCompId(memberRecord.getCompId());
				if(cleanresult != null && cleanresult.size() > 0) {
					for(Map<String, Object> map : cleanresult) {
						cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(map.get("memberId")));
						cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(map.get("memberId")));
					}
				}
			}
		}
		cleanQueryInfo("TakeTaskCacheQueryResult", createUserId);
		String createUserCount = createUserId==null?"":createUserId+"Count";
		removeCache("TakeTaskCacheQueryResult", createUserCount);
		cleanQueryInfo("TakeTaskCacheRecordResult", "getTaskFromOthers_" + String.valueOf(taskrecord.getTaskId()));
		removeCache("TakeTaskCacheRecordResult", String.valueOf(taskrecord.getTaskId()));
		cleanQueryInfo("TakeTaskCacheTaskRecordResult", String.valueOf(taskrecord.getTaskId()));
		removeCache("TakeTaskCacheTaskProcessResult", String.valueOf(taskrecord.getTaskId()));
		String removeOkdi = taskrecord.getTaskId()==null?"":("okdi_"+String.valueOf(taskrecord.getTaskId()));
		removeCache("TakeTaskCacheTaskProcessResult", removeOkdi);
		removeCache("TakeTaskCacheTaskProcess", removeOkdi);
		ehcacheService.removeAll("TakeTaskCacheTaskRecordResult");
		
		//调用电商管家
		if(task.getTaskSource() == 3  && task.getTaskFlag() == 1){
			try {
				MemberInfo member = getMember(toMemberId);
				BasCompInfo compInfo = ehcacheService.get("compCache", member.getCompId().toString(), BasCompInfo.class);
				if (PubMethod.isEmpty(compInfo)) {
					compInfo = this.basCompInfoMapper.findById(member.getCompId());
					ehcacheService.put("compCache", member.getCompId().toString(), compInfo);
				}
				Map<String,String> map = new HashMap<String,String>();
				map.put("taskId", task.getTaskId().toString());
				map.put("siteName", compInfo.getCompName());
				map.put("takeMemberMobile", member.getMemberPhone());
				map.put("takeMember", member.getMemberName());
				String url = constPool.getErpUrl() + "changeTakeMemberServiceChooseExpressAction.aspx";
				String response = Post(url, map);
				Map<String,Object> result = JSON.parseObject(response);
				if(PubMethod.isEmpty(result) || !"success".equals(result.get("RESULT").toString())){
					throw new ServiceException("openapi.TakeTaskServiceImpl.distributeTask.010", "转单，回调ERP异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("openapi.TakeTaskServiceImpl.distributeTask.011", "转单，回调ERP异常");
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Method: getTaskInfoByTaskId 
	 * @Description: 查询任务信息
	 * @param taskId
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午5:01:38
	 * @since jdk1.6
	 */
	private ParTaskInfo getTaskInfoByTaskId(Long taskId) throws ServiceException {
		ParTaskInfo taskinfo = cacheTaskInfo(String.valueOf(taskId)); 
		return taskinfo;
	}
	
	/**
	 * 
	 * @Method: getTaskDisposalRecord 
	 * @Description: 查询待分配任务是否创建过
	 * @param taskId
	 * @param disposalObject
	 * @param memberId
	 * @param compId
	 * @return
	 * @author xpf
	 * @date 2014-10-29 下午5:18:16
	 * @since jdk1.6
	 */
	private ParTaskDisposalRecord getTaskDisposalRecord(Long taskId, Byte disposalObject, Long memberId, Long compId) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		String cacheparam = "";
		map.put("taskId", taskId);
		cacheparam += "taskId=" + taskId;
		map.put("disposalObject", disposalObject);
		cacheparam += "disposalObject=" + disposalObject;
		map.put("memberId", memberId);
		cacheparam += "memberId=" + memberId;
		map.put("compId", compId);
		cacheparam += "compId=" + compId;
		
		ParTaskDisposalRecord record = null;
		String strrecord = getCache("TakeTaskCacheTaskRecordResult", cacheparam, String.class);
		if(!PubMethod.isEmpty(strrecord)) {
			record = JSON.parseObject(strrecord, ParTaskDisposalRecord.class);
		}
		if(PubMethod.isEmpty(record)) {
			record = parTaskDisposalRecordMapper.queryByTaskIdAndDisposalObject(map);
			List<String> tasklist = null;
			putCache("TakeTaskCacheTaskRecordResult", cacheparam, record);
			if(ehcacheService.getByKey("TakeTaskCacheTaskRecordResult", String.valueOf(taskId))) {
				tasklist = getCache("TakeTaskCacheTaskRecordResult", String.valueOf(taskId), ArrayList.class);
			}
			if(tasklist==null){
				tasklist = new ArrayList<String>();
			}
			if(!tasklist.contains(cacheparam)){
				tasklist.add(cacheparam);
			}
			//存入缓存
			putCache("TakeTaskCacheTaskRecordResult", String.valueOf(taskId), tasklist);
			//putCache("TakeTaskCacheQueryResult", String.valueOf(taskId), tasklist);
		}
		return record;
	}
	
	/**
	 * 
	 * @Method: distributeToDepartment 
	 * @Description: 分配给营业分部
	 * @param task
	 * @author xpf
	 * @date 2014-10-28 下午4:24:10
	 * @since jdk1.6
	 */
	private String distributeToDepartment(TaskVo task, Byte status, Map<String, Object> ToBusinesSegmtMap, Long currentMemberId) throws ServiceException {
		//查询任务信息获取经纬度
		ParTaskInfo taskinfo = getTaskInfoByTaskId(task.getTaskId());
		task.setTaskType(taskinfo.getTaskType());
		task.setCoopCompId(taskinfo.getCoopCompId());
		task.setContactAddrLatitude(taskinfo.getContactAddrLatitude());
		task.setContactAddrLongitude(taskinfo.getContactAddrLongitude());
		task.setContactName(taskinfo.getContactName());
		task.setContactMobile(taskinfo.getContactMobile());
		task.setTaskSource(taskinfo.getTaskSource());
		task.setTaskStatus(taskinfo.getTaskStatus());
		task.setCustomerId(taskinfo.getCustomerId());
		task.setContactAddressId(taskinfo.getContactAddressId());
		task.setContactAddress(taskinfo.getContactAddress());
		task.setTaskFlag(taskinfo.getTaskFlag());
		String sendaddr = "";
		if(!task_source_ec.equals(taskinfo.getTaskSource())) {
			DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(taskinfo.getContactAddressId()), DicAddressaid.class);
			if(PubMethod.isEmpty(address)){
				address = this.dicAddressaidMapper.findById(taskinfo.getContactAddressId());
			}
			if(!PubMethod.isEmpty(address)){
				sendaddr=address.getCountyName() + address.getTownName() + taskinfo.getContactAddress().substring(taskinfo.getContactAddress().indexOf(" ")+1, taskinfo.getContactAddress().length());
				sendaddr = sendaddr.replaceAll("-城区", "").replaceAll("县城", "");
			}
		} else {
			sendaddr = taskinfo.getContactAddress();
		}
		
//		String sendaddr = task.getContactAddress().split(" ")[0];
//		String addrdetail = task.getContactAddress().split(" ")[1];
//		sendaddr = sendaddr.substring(sendaddr.indexOf("-")+1, sendaddr.length()) + addrdetail;
		if(taskinfo != null) {
			task.setContactAddrLatitude(taskinfo.getContactAddrLatitude());
			task.setContactAddrLongitude(taskinfo.getContactAddrLongitude());
			taskinfo.setModifyTime(new Date());
		}
		task.setCoopCompId(task.getToCompId());
		task.setActorMemberId(null);
		//查询任务是否在其他营业分部或是收派员
		//boolean returnflag = getTaskFromOthers(task, status, currentMemberId);
		Map<String, Object> returnflag = getTaskFromOthers(task, status, currentMemberId);
		if(returnflag != null && returnflag.get("flag") != null && (Boolean) returnflag.get("flag")) {
			return String.valueOf(taskinfo.getCreateUserId());
		}
		/*if(returnflag) {
			return String.valueOf(taskinfo.getCreateUserId());
		}*/
		//查询任务记录表是否存在营业分部的任务
		ParTaskDisposalRecord taskrecord = getTaskDisposalRecord(task.getTaskId(), disposal_object_department, null, task.getToCompId());
		if(taskrecord != null) {//更新状态
			taskrecord.setDisposalType(task_status_untake);
			taskrecord.setShowFlag(show_flag_show);
			taskrecord.setTaskErrorFlag(task_error_flag_ok);
			taskrecord.setModifiedTime(new Date());
			taskrecord.setDisposalDesc("");
			//营业分部自动分配
			Map<String, Long> mapid = autoDistribute(task.getToCompId(), task.getContactAddrLongitude(), task.getContactAddrLatitude());
			if(mapid != null && mapid.get("memberId") != null) { //系统分配
				MemberInfo member = getMember(Long.parseLong(mapid.get("memberId").toString()));
				task.setActorMemberId(mapid.get("memberId"));
				task.setTaskStatus(task_status_distribute);
				task.setToMemberId(mapid.get("memberId"));
				//MemberInfo memberinfo = getMember(task.getToMemberId());
				task.setTaskProcessDesc("系统自动指定任务给：" + (member == null?"--":member.getMemberName()));
				task.setOperatorDesc("系统");
				//操作任务处理记录表和任务流转日志表
				taskLog(task);
				taskrecord.setDisposalType(task_status_distribute);
				//查询任务记录表是否存在收派员的任务
				ParTaskDisposalRecord partask = getTaskDisposalRecord(task.getTaskId(), disposal_object_member, task.getToMemberId(), null);
				if(partask != null) { //更新状态
					partask.setDisposalType(task_status_untake);
					partask.setShowFlag(show_flag_show);
					partask.setTaskErrorFlag(task_error_flag_ok);
					partask.setModifiedTime(new Date());
					parTaskDisposalRecordMapper.updateByPrimaryKeySelective(partask);
					putCache("TakeTaskCacheTaskRecord", String.valueOf(partask.getId()), partask);
				} else {//创建新纪录
					createMemberTask(task);
				}
				//发消息给收派员和客户
				if(returnflag != null && returnflag.get("memberId") != null && !"".equals(returnflag.get("memberId").toString())) {
					MemberInfo memberinfo = getMember(Long.parseLong(returnflag.get("memberId").toString()));
					if(memberinfo != null) {
						createTaskSendNoticeToPerson(task, sendaddr, 2, memberinfo.getMemberName(), "");
					} else {
						createTaskSendNoticeToPerson(task, sendaddr, 2, "", "");
					}
				} else {
					createTaskSendNoticeToPerson(task, sendaddr, 2, "", "");
				}
				parTaskDisposalRecordMapper.updateByPrimaryKeySelective(taskrecord);
				putCache("TakeTaskCacheTaskRecord", String.valueOf(taskrecord.getId()), taskrecord);
			} else {
				parTaskDisposalRecordMapper.updateByPrimaryKeySelective(taskrecord);
				putCache("TakeTaskCacheTaskRecord", String.valueOf(taskrecord.getId()), taskrecord);
				//营业分部发信息
				Integer countNum = (Integer) ToBusinesSegmtMap.get("countNum");
				if(PubMethod.isEmpty(countNum)) {
					countNum = 1;
				} else {
					countNum++;
				}
				ToBusinesSegmtMap.put("task", task);
				ToBusinesSegmtMap.put("sendaddr", sendaddr);
				ToBusinesSegmtMap.put("compId", task.getToCompId());
				ToBusinesSegmtMap.put("countNum", countNum);
//				if(current == (idNum - 1)) {
//					createTaskSendNoticeToBusinesSegmt(task, sendaddr, task.getToCompId(), count);
//				}
			}
		} else {//创建新记录
			task.setCompId(task.getToCompId());
			creatByDepartment(task);
			if(task.getToMemberId() != null) {
				task.setActorMemberId(task.getToMemberId());
				createMemberTask(task);
				//发消息给收派员和客户
				if(returnflag != null && returnflag.get("memberId") != null && !"".equals(returnflag.get("memberId").toString())) {
					MemberInfo memberinfo = getMember(Long.parseLong(returnflag.get("memberId").toString()));
					if(memberinfo != null) {
						createTaskSendNoticeToPerson(task, sendaddr, 2, memberinfo.getMemberName(), "");
					} else {
						createTaskSendNoticeToPerson(task, sendaddr, 2, "", "");
					}
				} else {
					createTaskSendNoticeToPerson(task, sendaddr, 2, "", "");
				}
			} else {
				//营业分部发信息
				Integer countNum = (Integer) ToBusinesSegmtMap.get("countNum");
				if(PubMethod.isEmpty(countNum)) {
					countNum = 1;
				} else {
					countNum++;
				}
				ToBusinesSegmtMap.put("task", task);
				ToBusinesSegmtMap.put("sendaddr", sendaddr);
				ToBusinesSegmtMap.put("compId", task.getCompId());
				ToBusinesSegmtMap.put("countNum", countNum);
//				if(current == (idNum - 1)) {
//					Map<String, Object> map = queryTaskUnTakeCountByCompId(task.getCompId(), task_status_untake);
//					Integer count = new Integer(0);
//					if(map != null && map.get("num") != null) {
//						count = (Integer) map.get("num");
//					}
//					createTaskSendNoticeToBusinesSegmt(task, sendaddr, task.getCompId(), count);
//				}
			}
		}
		if(taskinfo != null) {
			taskinfo.setTaskStatus(task_status_distribute);
			taskinfo.setCoopCompId(task.getCoopCompId());
			taskinfo.setActorMemberId(task.getActorMemberId());
			//更新任务信息表
			parTaskInfoMapper.updateByPrimaryKey(taskinfo);
			putCache("TakeTaskCacheTaskInfo", String.valueOf(taskinfo.getTaskId()), taskinfo);
			//根据taskId更新包裹表数据，将包裹所属站点compId设置为空
			updateParcelCompIdByTaskId(task.getTaskId(), 1, taskinfo.getCoopCompId());
		}
		return String.valueOf(taskinfo.getCreateUserId());
	}
	/**
	 * 
	 * @Method: getTaskFromOthers 
	 * @Description: 任务重新分配
	 * @param task
	 * @throws ServiceException
	 * @author xpf
	 * @date 2014-11-3 上午11:01:56
	 * @since jdk1.6
	 */
	private Map<String, Object> getTaskFromOthers(TaskVo task, Byte taskStatus, Long currentMemberId) throws ServiceException {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String cacheparam = "";
		map.put("taskId", task.getTaskId());
		cacheparam += "taskId=" + task.getTaskId();
		map.put("compId", task.getToCompId());
		cacheparam += "compId=" + task.getToCompId();
		map.put("memberId", task.getToMemberId());
		cacheparam += "memberId=" + task.getToMemberId();
		
		BasCompInfo fromcompinfo = getComp(task.getFromCompId());
		MemberInfo frommemberinfo = getMember(task.getFromMemberId());
		//获取公司信息
		BasCompInfo compinfo = getComp(task.getToCompId());
		//获取收派员信息
		MemberInfo memberinfo = getMember(task.getToMemberId());
		//读取缓存
		String taskResult = getCache("TakeTaskCacheRecordResult", cacheparam, String.class);
		List<ParTaskDisposalRecord> tasklist = null;
		if(!PubMethod.isEmpty(taskResult)) {
			tasklist = JSON.parseArray(taskResult,ParTaskDisposalRecord.class);
		} else {
			tasklist = parTaskDisposalRecordMapper.queryTaskByMemberIdOrCompId(map);
			//数据放入缓存
			putCache("TakeTaskCacheRecordResult", cacheparam, tasklist);
			List<String> taskQueryList = null;
			//获取缓存中当前站点查询条件集合
			if(ehcacheService.getByKey("TakeTaskCacheRecordResult", String.valueOf(task.getTaskId()))) {
				taskQueryList = getCache("TakeTaskCacheRecordResult", String.valueOf(task.getTaskId()), ArrayList.class);
			}
			if(taskQueryList==null){
				taskQueryList = new ArrayList<String>();
			}
			if(!taskQueryList.contains(cacheparam)){
				taskQueryList.add(cacheparam);
			}
			//存入缓存
			putCache("TakeTaskCacheRecordResult", "getTaskFromOthers_"+String.valueOf(task.getTaskId()), taskQueryList);
		}
		String sendaddr = "";
		if(!task_source_ec.equals(task.getTaskSource())) {
			DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(task.getContactAddressId()), DicAddressaid.class);
			if(PubMethod.isEmpty(address)){
				address = this.dicAddressaidMapper.findById(task.getContactAddressId());
			}
			if(!PubMethod.isEmpty(address)){
				sendaddr=address.getCountyName() + address.getTownName() + task.getContactAddress().substring(task.getContactAddress().indexOf(" ")+1, task.getContactAddress().length());
				sendaddr = sendaddr.replaceAll("-城区", "").replaceAll("县城", "");
			}
		} else {
			sendaddr = task.getContactAddress();
		}
		if(tasklist != null && tasklist.size() > 0) { //重新分配或重新发起
			for(ParTaskDisposalRecord taskrecrod : tasklist) {
				if(task.getToCompId() != null && task.getToCompId().equals(taskrecrod.getCompId()) && !task_status_cancel.equals(taskrecrod.getDisposalType())) {
					resultmap.put("flag", true);
					return resultmap;
					//throw new ServiceException("error", task.getTaskId() + "任务不可重复分配给" + (compinfo == null?"--":compinfo.getCompName()) + "网点");
				}
				if(task.getToMemberId() != null && task.getToMemberId().equals(taskrecrod.getMemberId()) && !task_status_cancel.equals(taskrecrod.getDisposalType())) {
					resultmap.put("flag", true);
					return resultmap;
					//throw new ServiceException("error", task.getTaskId() + "任务不可重复分配给" + (memberinfo == null?"--":memberinfo.getMemberName()) + "收派员");
				}
				//待处理任务查询
				if(task_status_untake.equals(taskrecrod.getDisposalType())) {
					if(disposal_object_department.equals(taskrecrod.getDisposalObject())) {
						fromcompinfo = getComp(taskrecrod.getCompId());
						//修改分配来源
						task.setFromCompId(taskrecrod.getCompId());
					} else {
						task.setFromCompId(null);
					}
					if(disposal_object_member.equals(taskrecrod.getDisposalObject())) {
						frommemberinfo = getMember(taskrecrod.getMemberId());
						//修改分配来源
						task.setFromMemberId(taskrecrod.getMemberId());
					} else {
						task.setFromMemberId(null);
					}
				}
				//更新取消状态
				//获取未取消任务的收派员的id
				if(!task_status_cancel.equals(taskrecrod.getDisposalType()) && disposal_object_member.equals(taskrecrod.getDisposalObject())) {
					resultmap.put("memberId", taskrecrod.getMemberId());
				}
				taskrecrod.setDisposalType(task_status_cancel);
				taskrecrod.setShowFlag(show_flag_hide);
				taskrecrod.setModifiedTime(new Date());
				parTaskDisposalRecordMapper.updateByPrimaryKeySelective(taskrecrod);
				//更新缓存
				putCache("TakeTaskCacheTaskRecord", String.valueOf(taskrecrod.getId()), taskrecrod);
				cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(taskrecrod.getCompId()));
				cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(taskrecrod.getMemberId()));
				cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(taskrecrod.getMemberId()));
			}
			
//			for(ParTaskDisposalRecord taskDrecrod : tasklist) {
//			}
			//记录分配日志
			if(task.getFromCompId() != null) {//站点分配
				if(task.getToCompId() != null) {
					if(task_status_cancel.equals(taskStatus) || task_status_untake.equals(taskStatus)) {
						task.setTaskProcessDesc("网点指定任务给：" + (compinfo == null?"--":compinfo.getCompName()));
					} else {
						task.setTaskProcessDesc("任务由网点" + (fromcompinfo == null?"--":fromcompinfo.getCompName()) + "转为网点" + (compinfo == null?"--":compinfo.getCompName()) + "执行");
					}
					//保存日志信息
					taskLog(task);
				} else if(task.getMemberId() != null) {
					if(task_status_cancel.equals(taskStatus) || task_status_untake.equals(taskStatus)) {
						task.setTaskProcessDesc("网点指定任务给：" + (memberinfo == null?"--":memberinfo.getMemberName()));
					} else {
						task.setTaskProcessDesc("任务由网点" + (fromcompinfo == null?"--":fromcompinfo.getCompName()) + "转为取件员" + (memberinfo == null?"--":memberinfo.getMemberName()) + "执行");
					}
					//保存日志信息
					taskLog(task);
				}
			} else if(task.getFromMemberId() != null) { //收派员分配
				task.setOperatorCompId(task.getCoopCompId());
				if(task.getToCompId() != null) {
					task.setTaskProcessDesc("任务由取件员" + (frommemberinfo == null?"--":frommemberinfo.getMemberName()) + "转为网点" + (compinfo == null?"--":compinfo.getCompName()) + "执行");
				} else if(task.getMemberId() != null) {
					task.setTaskProcessDesc("任务由取件员" + (frommemberinfo == null?"--":frommemberinfo.getMemberName()) + "转为取件员" + (memberinfo == null?"--":memberinfo.getMemberName()) + "执行");
				}
				if(!PubMethod.isEmpty(frommemberinfo)) {
					/*******************这里是贺海峰进行修改的 站点重新分配任务给本站点的收派员后 需要给营业分部下面的收派员发一条取消任务的短信 ***************/

					if(currentMemberId == null || !currentMemberId.equals(frommemberinfo.getMemberId())) {
						boolean isSend = this.sendNoticeService.cancelToExpMember(task.getTaskId(), frommemberinfo.getMemberId(), 
								task.getContactName(), task.getContactMobile(), sendaddr, frommemberinfo.getMemberPhone());
					}
//					"===========================这里看的是进入短信发送的方法结束"+isSend);
				}
				//保存日志信息
				taskLog(task);
			}
		} else { //未处理分配，日志记录
			if(task.getFromCompId() != null) {//站点分配
				if(task.getToCompId() != null) {
					task.setTaskProcessDesc("网点指定任务给：" + (compinfo == null?"--":compinfo.getCompName()));
					//保存日志信息
					taskLog(task);
				} else if(task.getMemberId() != null) {
					task.setTaskProcessDesc("网点指定任务给：" + (memberinfo == null?"--":memberinfo.getMemberName()));
					//保存日志信息
					taskLog(task);
				}
			} else if(task.getFromMemberId() != null) { //收派员分配
				task.setOperatorCompId(task.getCoopCompId());
				if(task.getToCompId() != null) {
					task.setTaskProcessDesc("任务由取件员" + (frommemberinfo == null?"--":frommemberinfo.getMemberName()) + "转为网点" + (compinfo == null?"--":compinfo.getCompName()) + "执行");
					//保存日志信息
					taskLog(task);
				} else if(task.getMemberId() != null) {
					task.setTaskProcessDesc("任务由取件员" + (frommemberinfo == null?"--":frommemberinfo.getMemberName()) + "转为取件员" + (memberinfo == null?"--":memberinfo.getMemberName()) + "执行");
					//保存日志信息
					taskLog(task);
				}
				if(!PubMethod.isEmpty(frommemberinfo)) {
					/*******************这里是贺海峰进行修改的 站点重新分配任务给本站点的收派员后 需要给营业分部下面的收派员发一条取消任务的短信 ***************/
					if(currentMemberId == null || !currentMemberId.equals(frommemberinfo.getMemberId())) {
						boolean isSend = this.sendNoticeService.cancelToExpMember(task.getTaskId(), frommemberinfo.getMemberId(), 
								task.getContactName(), task.getContactMobile(), sendaddr, frommemberinfo.getMemberPhone());
					}
				}
			}
		}
		if(!PubMethod.isEmpty(fromcompinfo)) {
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(fromcompinfo.getCompId()));
		}
		if(!PubMethod.isEmpty(compinfo)) {
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(compinfo.getCompId()));
		}
		if(!PubMethod.isEmpty(frommemberinfo)) {
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(frommemberinfo.getMemberId()));
		}
		if(!PubMethod.isEmpty(memberinfo)) {
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(memberinfo.getMemberId()));
		}
		return resultmap;
	}
	
	/**
	 * 
	 * @Method: distributeToMember 
	 * @Description: 分配给收派员
	 * @param task
	 * @author xpf
	 * @date 2014-10-29 上午10:21:41
	 * @since jdk1.6
	 */
	private String distributeToMember(TaskVo task, Byte status, ParTaskDisposalRecord taskRecord, Long currentMemberId) throws ServiceException {
		//查询任务信息获取经纬度
		ParTaskInfo taskinfo = getTaskInfoByTaskId(task.getTaskId());
		task.setTaskType(taskinfo.getTaskType());
		task.setCoopCompId(taskinfo.getCoopCompId());
		task.setContactAddrLatitude(taskinfo.getContactAddrLatitude());
		task.setContactAddrLongitude(taskinfo.getContactAddrLongitude());
		task.setContactName(taskinfo.getContactName());
		task.setContactMobile(taskinfo.getContactMobile());
		task.setTaskSource(taskinfo.getTaskSource());
		task.setTaskStatus(taskinfo.getTaskStatus());
		task.setCustomerId(taskinfo.getCustomerId());
		task.setContactAddressId(taskinfo.getContactAddressId());
		task.setContactAddress(taskinfo.getContactAddress());
		task.setTaskFlag(taskinfo.getTaskFlag());
		String sendaddr = "";
		if(!task_source_ec.equals(task.getTaskSource())) {
			DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(task.getContactAddressId()), DicAddressaid.class);
			if(PubMethod.isEmpty(address)){
				address = this.dicAddressaidMapper.findById(task.getContactAddressId());
			}
			if(!PubMethod.isEmpty(address)){
				sendaddr=address.getCountyName() + address.getTownName() + task.getContactAddress().substring(task.getContactAddress().indexOf(" ")+1, task.getContactAddress().length());
				sendaddr = sendaddr.replaceAll("-城区", "").replaceAll("县城", "");
			}
		} else {
			sendaddr = task.getContactAddress();
		}
//		String sendaddr = taskinfo.getContactAddress().split(" ")[0];
//		String addrdetail = taskinfo.getContactAddress().split(" ")[1];
//		sendaddr = sendaddr.substring(sendaddr.indexOf("-")+1, sendaddr.length()) + addrdetail;
		taskinfo.setActorMemberId(task.getToMemberId());
		//查询收派员所属的站点或营业分部
		Long coopCompid = basEmployeeRelationMapper.queryCompIdByMemberId(task.getToMemberId()).getCompId();
		taskinfo.setCoopCompId(coopCompid);
		//查询任务是否在其他营业分部或是收派员
		Map<String, Object> returnflag = getTaskFromOthers(task, status, currentMemberId);
		if(returnflag != null && returnflag.get("flag") != null && (Boolean) returnflag.get("flag")) {
			return String.valueOf(taskinfo.getCreateUserId());
		}
		/*boolean returnflag = getTaskFromOthers(task, status, currentMemberId);
		if(returnflag) {
			return String.valueOf(taskinfo.getTaskId());
		}*/
		//查询任务记录表是否存在收派员的任务
		ParTaskDisposalRecord partask = getTaskDisposalRecord(task.getTaskId(), disposal_object_member, task.getToMemberId(), null);
		//营业分部
		ParTaskDisposalRecord comptask = getTaskDisposalRecord(task.getTaskId(), disposal_object_department, null, coopCompid);
		if(!PubMethod.isEmpty(comptask)) {
			comptask.setDisposalType(task_status_distribute);
			comptask.setShowFlag(show_flag_show);
			comptask.setTaskErrorFlag(task_error_flag_ok);
			comptask.setModifiedTime(new Date());
			parTaskDisposalRecordMapper.updateByPrimaryKeySelective(comptask);
			//putCache("TakeTaskCacheTaskRecord", String.valueOf(comptask.getId()), comptask);
			removeCache("TakeTaskCacheTaskRecord", String.valueOf(comptask.getId()));
		}
//		if(!PubMethod.isEmpty(taskRecord.getCompId()) && disposal_object_department.equals(taskRecord.getDisposalObject())) {
//			comptask = getTaskDisposalRecord(task.getTaskId(), disposal_object_department, null, taskRecord.getCompId());
//		}
		//查询上级任务
		ParTaskDisposalRecord exptask = getTaskDisposalRecord(task.getTaskId(), disposal_object_exp, null, null);
		if(exptask != null) { //上级取消状态,变为分配状态
			if(task_status_cancel.equals(exptask.getDisposalType())) {
				exptask.setDisposalType(task_status_distribute);
				exptask.setShowFlag(show_flag_show);
				exptask.setTaskErrorFlag(task_error_flag_ok);
				exptask.setModifiedTime(new Date());
				parTaskDisposalRecordMapper.updateByPrimaryKeySelective(exptask);
				//putCache("TakeTaskCacheTaskRecord", String.valueOf(exptask.getId()), exptask);
				removeCache("TakeTaskCacheTaskRecord", String.valueOf(exptask.getId()));
			}
		} else {
			//营业分部id查询站点id
			ExpCompRelation relationCompId = businessBranchService.queryCompRelationByBusinessId(coopCompid);
			if(!PubMethod.isEmpty(relationCompId)) {
				exptask = getTaskDisposalRecord(task.getTaskId(), disposal_object_exp, null, relationCompId.getParentCompId());
				if(exptask != null && task_status_cancel.equals(exptask.getDisposalType())) {
					exptask.setDisposalType(task_status_distribute);
					exptask.setShowFlag(show_flag_show);
					exptask.setTaskErrorFlag(task_error_flag_ok);
					exptask.setModifiedTime(new Date());
					parTaskDisposalRecordMapper.updateByPrimaryKeySelective(exptask);
					//putCache("TakeTaskCacheTaskRecord", String.valueOf(exptask.getId()), exptask);
					removeCache("TakeTaskCacheTaskRecord", String.valueOf(exptask.getId()));
				}
			}
		}
		
		if(partask != null) {
			partask.setDisposalType(task_status_untake);
			partask.setShowFlag(show_flag_show);
			partask.setTaskErrorFlag(task_error_flag_ok);
			partask.setModifiedTime(new Date());
			parTaskDisposalRecordMapper.updateByPrimaryKeySelective(partask);
			//putCache("TakeTaskCacheTaskRecord", String.valueOf(partask.getId()), partask);
			removeCache("TakeTaskCacheTaskRecord", String.valueOf(partask.getId()));
			//发消息给收派员和客户
			if(returnflag != null && returnflag.get("memberId") != null && !"".equals(returnflag.get("memberId").toString())) {
				MemberInfo memberinfo = getMember(Long.parseLong(returnflag.get("memberId").toString()));
				if(memberinfo != null) {
					createTaskSendNoticeToPerson(task, sendaddr, 2, memberinfo.getMemberName(), "");
				} else {
					createTaskSendNoticeToPerson(task, sendaddr, 2, "", "");
				}
			} else {
				createTaskSendNoticeToPerson(task, sendaddr, 2, "", "");
			}
		} else { //创建任务
			createMemberTask(task);
			//发消息给收派员和客户
			if(returnflag != null && returnflag.get("memberId") != null && !"".equals(returnflag.get("memberId").toString())) {
				MemberInfo memberinfo = getMember(Long.parseLong(returnflag.get("memberId").toString()));
				if(memberinfo != null) {
					createTaskSendNoticeToPerson(task, sendaddr, 2, memberinfo.getMemberName(), "");
				} else {
					createTaskSendNoticeToPerson(task, sendaddr, 2, "", "");
				}
			} else {
				createTaskSendNoticeToPerson(task, sendaddr, 2, "", "");
			}
		}
		if(taskinfo != null) {
			taskinfo.setTaskStatus(task_status_distribute);
			taskinfo.setModifyTime(new Date());
			//更新任务信息表
			parTaskInfoMapper.updateByPrimaryKeySelective(taskinfo);
			//putCache("TakeTaskCacheTaskInfo", String.valueOf(taskinfo.getTaskId()), taskinfo);
			removeCache("TakeTaskCacheTaskInfo", String.valueOf(taskinfo.getTaskId()));
			//根据taskId更新包裹表数据，将包裹所属站点compId设置为空
			updateParcelCompIdByTaskId(task.getTaskId(), 1, taskinfo.getCoopCompId());
		}
		return String.valueOf(taskinfo.getCreateUserId());
	}

	@Override
	public String cancel(String taskId, Long memberId, Byte taskTransmitCause, String disposalDesc, int status, int source) throws ServiceException {
		ehcacheService.removeAll("TakeTaskCacheUnTakeCount");
		if(taskId == null || "".equals(taskId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.cancel.001", "请选择取消的任务");
		}
		String[] splitId = taskId.split(",");
		if(splitId == null || splitId.length == 0) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.cancel.001", "请选择取消的任务");
		}
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.cancel.004", "无取消操作人");
		}
		if(PubMethod.isEmpty(taskTransmitCause)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.cancel.005", "请选择取消原因");
		}
		if((taskTransmitCause == 3 || "3".equals(taskTransmitCause.toString())) && PubMethod.isEmpty(disposalDesc)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.cancel.006", "请填写其他原因的备注");
		}
		Map<String, Object> ToBusinesSegmtMap = new HashMap<String, Object>();
		for(int i = 0; i < splitId.length; i++) {
			cancelTask(Long.parseLong(splitId[i]), memberId, taskTransmitCause, disposalDesc, status, source, ToBusinesSegmtMap);
		}
		if(ToBusinesSegmtMap != null && ToBusinesSegmtMap.get("taskrecord") != null && ToBusinesSegmtMap.get("compId") != null && ToBusinesSegmtMap.get("countNum") != null) {
			Map<String, Object> map = queryTaskUnTakeCountByCompId((Long) ToBusinesSegmtMap.get("compId"), task_status_untake);
			Integer count = new Integer(0);
			if(map != null && map.get("num") != null) {
				count = (Integer) map.get("num");
			}
			cancelTaskSendNoticeToBusinesSegmt((String) ToBusinesSegmtMap.get("sendType"), (ParTaskDisposalRecord) ToBusinesSegmtMap.get("taskrecord"), (Long) ToBusinesSegmtMap.get("compId"), 1, String.valueOf(ToBusinesSegmtMap.get("reason")), count);
		}
		return null;
	}
	
	private void cancelTask(Long taskId, Long memberId, Byte taskTransmitCause, String disposalDesc, int status, int source, Map<String, Object> ToBusinesSegmtMap) throws ServiceException {
		boolean smsToCust = true;
		List<ParTaskDisposalRecord> tasklist = getTaskRecord(taskId);
		//整理任务记录Map, key=disposalObject value=ParTaskDisposalRecord对象
		Map<Byte, ParTaskDisposalRecord> taskMap = getTaskRecordMap(tasklist);
		ParTaskInfo canceltask = cacheTaskInfo(String.valueOf(taskId));
		if(task_status_cancel.equals(canceltask.getTaskStatus())) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.cancelTask.002", "任务已经取消不可重复取消");
		}
		if(task_status_finish.equals(canceltask.getTaskStatus())) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.cancelTask.007", "任务已经完成不可取消");
		}
		String sendaddr = "";
		if(!task_source_ec.equals(canceltask.getTaskSource())) {
			/*DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(canceltask.getContactAddressId()), DicAddressaid.class);
			if(PubMethod.isEmpty(address)){
				address = this.dicAddressaidMapper.findById(canceltask.getContactAddressId());
			}
			if(!PubMethod.isEmpty(address)){
				sendaddr=address.getCountyName() + address.getTownName() + canceltask.getContactAddress().substring(canceltask.getContactAddress().indexOf(" ")+1, canceltask.getContactAddress().length());
				sendaddr = sendaddr.replaceAll("-城区", "").replaceAll("县城", "");
			}*/
			sendaddr = canceltask.getContactAddress().substring(canceltask.getContactAddress().indexOf(" ")+1, canceltask.getContactAddress().length());
		} else {
			sendaddr = canceltask.getContactAddress().substring(canceltask.getContactAddress().indexOf(" ")+1, canceltask.getContactAddress().length());
			//sendaddr = canceltask.getContactAddress();
		}
//		String sendaddr = canceltask.getContactAddress().split(" ")[0];
//		String addrdetail = canceltask.getContactAddress().split(" ")[1];
//		sendaddr = sendaddr.substring(sendaddr.indexOf("-")+1, sendaddr.length()) + addrdetail;
		HashMap<Byte, String> reasonMap = new HashMap<Byte, String>();
		reasonMap.put((byte) 1, "客户取消发件");
		reasonMap.put((byte) 2, "多次联系不上客户，上门无人");
		reasonMap.put((byte) 3, "其他原因");
		reasonMap.put((byte) 10, "超出本网点范围");
		reasonMap.put((byte) 11, "网点任务太多，忙不过来");
		reasonMap.put((byte) 12, "超出本人收派范围");
		reasonMap.put((byte) 13, "本人任务太多，忙不过来");
		if(taskTransmitCause < (byte) 10) { //客户原因，所有数据全取消
			//更新取消状态，任务记录表
			cancelTaskDisposalRecord(null, taskId, disposalDesc, null, task_error_flag_ok);
			//记录取消日志信息
			if(memberId != null && canceltask.getTaskSource() != null && ("1".equals(canceltask.getTaskSource().toString()) || "4".equals(canceltask.getTaskSource().toString())) && memberId.equals(canceltask.getCreateUserId()) && source == 1) {
				logger.debug("-------------客户取消-----------------");
				smsToCust = false;
				cancelTaskLog(taskId, null, taskTransmitCause, reasonMap.get(taskTransmitCause), disposalDesc, 1);
			} else {
				logger.debug("-------------站点取消-----------------");
				cancelTaskLog(taskId, memberId, taskTransmitCause, reasonMap.get(taskTransmitCause), disposalDesc, 1);
			}
			//更改任务信息表状态
			cancelTaskInfo(taskId, task_status_cancel, disposal_object_member, canceltask.getCoopCompId());
			//是否为收派员取消
			if(status != 1) {
				if(taskMap != null && taskMap.get(disposal_object_member) != null) {
					ParTaskDisposalRecord member = taskMap.get(disposal_object_member);
					//给收派员发消息
					cancelNoticeToExpMember(canceltask.getTaskId(), member.getMemberId(), canceltask.getContactName(), canceltask.getContactMobile(), sendaddr, getMember(member.getMemberId()).getMemberPhone());
				}
			}
			//给客户发消息
			if(smsToCust) {
				String siteName = "";
				if(status == 1) {
					MemberInfo smsMemberId = getMember(memberId);
					if(!PubMethod.isEmpty(smsMemberId)) {
						siteName = smsMemberId.getMemberName();
					}
				} else {
					BasEmployeeRelation relation = basEmployeeRelationMapper.queryCompIdByMemberId(memberId);
					if(!PubMethod.isEmpty(relation)) {
						BasCompInfo compName = getComp(relation.getCompId());
						if(compName != null) {
							siteName = compName.getCompName();
						}
					}
				}
				cancelNoticeToCustomer(taskId, Integer.parseInt(canceltask.getTaskSource().toString()), String.valueOf(canceltask.getCustomerId()), canceltask.getContactMobile(), (taskTransmitCause==3?disposalDesc:reasonMap.get(taskTransmitCause)), siteName);
			}
			//根据taskId更新包裹表数据，将包裹所属站点compId设置为空
			updateParcelCompIdByTaskId(taskId, 0, null);
		} else {
			if(taskMap != null) {
				refuseTask(memberId, taskTransmitCause, status, taskMap, disposalDesc, reasonMap, ToBusinesSegmtMap);
			}
		}
		//清空缓存
		String recordcompId = "";
		String recordmemberId = "";
		ParTaskDisposalRecord memberRecord = taskMap.get(disposal_object_member);
		if(!PubMethod.isEmpty(memberRecord)) {
			recordmemberId = String.valueOf(memberRecord.getMemberId());
			cleanQueryInfo("TakeTaskCacheQueryResult", recordcompId);
			cleanQueryInfo("TakeTaskCacheQueryResult", recordmemberId);
			cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", recordmemberId);
		}
		memberRecord = taskMap.get(disposal_object_department);
		if(!PubMethod.isEmpty(memberRecord)) {
			recordcompId = String.valueOf(memberRecord.getCompId());
			recordmemberId = String.valueOf(memberRecord.getMemberId());
			cleanQueryInfo("TakeTaskCacheQueryResult", recordcompId);
			cleanQueryInfo("TakeTaskCacheQueryResult", recordmemberId);
			cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", recordmemberId);
			String fcompCount = recordcompId==null?"":recordcompId+"Count";
			String fmemberCount = recordmemberId==null?"":recordmemberId+"Count";
			removeCache("TakeTaskCacheQueryResult", fcompCount);
			removeCache("TakeTaskCacheQueryResult", fmemberCount);

			if(String.valueOf(memberRecord.getCompId()) != null && !"".equals(String.valueOf(memberRecord.getCompId()))) {
				String result = getCache("employeeCache", String.valueOf(memberRecord.getCompId()), String.class);
				List<VO_MemberInfo> list = null;
				if(!PubMethod.isEmpty(result)) {
					list = JSON.parseArray(result, VO_MemberInfo.class); 
				}
				if(!PubMethod.isEmpty(list)) {
					for(VO_MemberInfo mem : list) {
						cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(mem.getMemberId()));
						cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(mem.getMemberId()));
					}
				}
			}
			
			//清除站点下营业分部和收派员的缓存
			if(memberRecord.getCompId() != null && !"".equals(memberRecord.getCompId().toString())) {
				List<Map<String, Object>> cleanresult = this.getMemberInfoByCompId(memberRecord.getCompId());
				if(cleanresult != null && cleanresult.size() > 0) {
					for(Map<String, Object> map : cleanresult) {
						cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(map.get("memberId")));
						cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(map.get("memberId")));
					}
				}
			}
		}
		memberRecord = taskMap.get(disposal_object_exp);
		if(!PubMethod.isEmpty(memberRecord)) {
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(memberRecord.getCompId()));
			cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(memberRecord.getMemberId()));
			cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(memberRecord.getMemberId()));
			String fcompCount = memberRecord.getCompId()==null?"":memberRecord.getCompId()+"Count";
			String fmemberCount = memberRecord.getMemberId()==null?"":memberRecord.getMemberId()+"Count";
			removeCache("TakeTaskCacheQueryResult", fcompCount);
			removeCache("TakeTaskCacheQueryResult", fmemberCount);
			if(String.valueOf(memberRecord.getCompId()) != null && !"".equals(String.valueOf(memberRecord.getCompId()))) {
				String result = getCache("employeeCache", String.valueOf(memberRecord.getCompId()), String.class);
				List<VO_MemberInfo> list = null;
				if(!PubMethod.isEmpty(result)) {
					list = JSON.parseArray(result, VO_MemberInfo.class); 
				}
				if(!PubMethod.isEmpty(list)) {
					for(VO_MemberInfo mem : list) {
						cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(mem.getMemberId()));
						cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(mem.getMemberId()));
					}
				}
			}
			//清除站点下营业分部和收派员的缓存
			if(memberRecord.getCompId() != null && !"".equals(memberRecord.getCompId().toString())) {
				List<Map<String, Object>> cleanresult = this.getMemberInfoByCompId(memberRecord.getCompId());
				if(cleanresult != null && cleanresult.size() > 0) {
					for(Map<String, Object> map : cleanresult) {
						cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(map.get("memberId")));
						cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(map.get("memberId")));
					}
				}
			}
		}
		cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(canceltask.getCreateUserId()));
		String createUserCount = canceltask.getCreateUserId()==null?"":canceltask.getCreateUserId()+"Count";
		removeCache("TakeTaskCacheQueryResult", createUserCount);
		cleanQueryInfo("TakeTaskCacheRecordResult", "getTaskFromOthers_" + String.valueOf(taskId));
		removeCache("TakeTaskCacheRecordResult", String.valueOf(taskId));
		cleanQueryInfo("TakeTaskCacheTaskRecordResult", String.valueOf(taskId));
		removeCache("TakeTaskCacheTaskProcessResult", String.valueOf(taskId));
		String removeOkdi = taskId==null?"":("okdi_"+String.valueOf(taskId));
		removeCache("TakeTaskCacheTaskProcessResult", removeOkdi);
		removeCache("TakeTaskCacheTaskProcess", removeOkdi);
		ehcacheService.removeAll("TakeTaskCacheTaskRecordResult");
	}
	
	private void refuseTask(Long memberId, Byte taskTransmitCause, int status, Map<Byte, ParTaskDisposalRecord> taskMap, String disposalDesc, HashMap<Byte, String> reasonMap, Map<String, Object> ToBusinesSegmtMap) throws ServiceException {
		ParTaskDisposalRecord taskrecord = null;
		boolean hasComp = false;
		String refusemsg = "";
		if(status == 1) { //收派员拒绝
			//查询上级
			refusemsg = "取件员：" + reasonMap.get(taskTransmitCause);
			taskrecord = taskMap.get(disposal_object_department);
			if(taskrecord != null) {
				if(task_status_cancel.equals(taskrecord.getDisposalType())) {
					throw new ServiceException("openapi.TakeTaskServiceImpl.cancelTask.002", "任务已经取消不可重复取消");
				}
				unTakeTaskDisposalRecord(taskrecord.getId(), taskrecord.getTaskId(), refusemsg, show_flag_show, task_error_flag_error);
				if(taskMap.get(disposal_object_exp) == null) {//营业分部没有上级了，修改信息表状态
					cancelTaskInfo(taskMap.get(disposal_object_member).getTaskId(), task_status_untake, disposal_object_member, taskrecord.getCompId());
				}
				//给上级营业部发消息
				Integer countNum = (Integer) ToBusinesSegmtMap.get("countNum");
				if(PubMethod.isEmpty(countNum)) {
					countNum = 1;
				} else {
					countNum++;
				}
				ToBusinesSegmtMap.put("taskrecord", taskrecord);
				ToBusinesSegmtMap.put("compId", taskMap.get(disposal_object_department).getCompId());
				ToBusinesSegmtMap.put("reason", reasonMap.get(taskTransmitCause));
				ToBusinesSegmtMap.put("countNum", countNum);
				ToBusinesSegmtMap.put("sendType", "EXP");
				hasComp = true;
			} else {
				taskrecord = taskMap.get(disposal_object_exp);
				if(taskrecord != null) {
					if(task_status_cancel.equals(taskrecord.getDisposalType())) {
						throw new ServiceException("openapi.TakeTaskServiceImpl.cancelTask.002", "任务已经取消不可重复取消");
					}
					unTakeTaskDisposalRecord(taskrecord.getId(), taskrecord.getTaskId(), refusemsg, show_flag_show, task_error_flag_error);
					cancelTaskInfo(taskMap.get(disposal_object_member).getTaskId(), task_status_untake, disposal_object_member, taskrecord.getCompId());
					//给站点发消息
					Integer countNum = (Integer) ToBusinesSegmtMap.get("countNum");
					if(PubMethod.isEmpty(countNum)) {
						countNum = 1;
					} else {
						countNum++;
					}
					ToBusinesSegmtMap.put("taskrecord", taskrecord);
					ToBusinesSegmtMap.put("compId", taskMap.get(disposal_object_exp).getCompId());
					ToBusinesSegmtMap.put("reason", reasonMap.get(taskTransmitCause));
					ToBusinesSegmtMap.put("countNum", countNum);
					ToBusinesSegmtMap.put("sendType", "EXP");
					hasComp = true;
				} else {
					if(task_status_cancel.equals(taskMap.get(disposal_object_member).getDisposalType())) {
						throw new ServiceException("openapi.TakeTaskServiceImpl.cancelTask.002", "任务已经取消不可重复取消");
					}
					//修改任务信息表状态
					cancelTaskInfo(taskMap.get(disposal_object_member).getTaskId(), task_status_cancel, (byte) -1, null);
					//给客户发消息
					ParTaskInfo canceltask = cacheTaskInfo(String.valueOf(taskMap.get(disposal_object_member).getTaskId()));
					MemberInfo smsMemberId = getMember(taskMap.get(disposal_object_member).getMemberId());
					String siteName = "";
					if(!PubMethod.isEmpty(smsMemberId)) {
						siteName = smsMemberId.getMemberName();
					}
					cancelNoticeToCustomer(taskMap.get(disposal_object_member).getTaskId(), Integer.parseInt(canceltask.getTaskSource().toString()), String.valueOf(canceltask.getCustomerId()), canceltask.getContactMobile(), (taskTransmitCause==3?disposalDesc:reasonMap.get(taskTransmitCause)), siteName);
					hasComp = false;
					//根据taskId更新包裹表数据，将包裹所属站点compId设置为空
					updateParcelCompIdByTaskId(taskMap.get(disposal_object_member).getTaskId(), 0, null);
				}
			}
			taskrecord = taskMap.get(disposal_object_member);
			//修改收派员任务记录
			cancelTaskDisposalRecord(taskrecord.getId(),taskrecord.getTaskId(),disposalDesc,show_flag_hide, task_error_flag_ok);
			//添加log日志
			if(hasComp) {
				cancelTaskLog(taskrecord.getTaskId(), memberId, taskTransmitCause, reasonMap.get(taskTransmitCause), disposalDesc, 0);
			} else {
				cancelTaskLog(taskrecord.getTaskId(), memberId, taskTransmitCause, reasonMap.get(taskTransmitCause), disposalDesc, 1);
			}
		} else if(status == 2) { //营业分部拒绝
			taskrecord = taskMap.get(disposal_object_department);
			if(PubMethod.isEmpty(taskrecord)) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.refuseTask.001", "营业分部不存在此任务");
			}
			BasCompInfo bascomp = getComp(taskrecord.getCompId());
			refusemsg = bascomp==null?reasonMap.get(taskTransmitCause):(bascomp.getCompName() + "：" + reasonMap.get(taskTransmitCause));
			//查询上级
			taskrecord = taskMap.get(disposal_object_exp);
			if(taskrecord != null) {
				if(task_status_cancel.equals(taskrecord.getDisposalType())) {
					throw new ServiceException("openapi.TakeTaskServiceImpl.cancelTask.002", "任务已经取消不可重复取消");
				}
				unTakeTaskDisposalRecord(taskrecord.getId(),taskrecord.getTaskId(),refusemsg,show_flag_show,task_error_flag_error);
				cancelTaskInfo(taskMap.get(disposal_object_department).getTaskId(), task_status_untake, (byte) -1, taskrecord.getCompId());
				//给站点发消息
				Integer countNum = (Integer) ToBusinesSegmtMap.get("countNum");
				if(PubMethod.isEmpty(countNum)) {
					countNum = 1;
				} else {
					countNum++;
				}
				ToBusinesSegmtMap.put("taskrecord", taskrecord);
				ToBusinesSegmtMap.put("compId", taskMap.get(disposal_object_exp).getCompId());
				ToBusinesSegmtMap.put("reason", reasonMap.get(taskTransmitCause));
				ToBusinesSegmtMap.put("countNum", countNum);
				ToBusinesSegmtMap.put("sendType", "BUSINES");
				hasComp = true;
			} else {
				if(task_status_cancel.equals(taskMap.get(disposal_object_department).getDisposalType())) {
					throw new ServiceException("openapi.TakeTaskServiceImpl.cancelTask.002", "任务已经取消不可重复取消");
				}
				//修改任务信息表状态
				cancelTaskInfo(taskMap.get(disposal_object_department).getTaskId(), task_status_cancel, (byte) -1, null);
				//给客户发消息
				ParTaskInfo canceltask = cacheTaskInfo(String.valueOf(taskMap.get(disposal_object_department).getTaskId()));
				BasEmployeeRelation relation = basEmployeeRelationMapper.queryCompIdByMemberId(memberId);
				String siteName = "";
				if(!PubMethod.isEmpty(relation)) {
					BasCompInfo compName = getComp(relation.getCompId());
					if(compName != null) {
						siteName = compName.getCompName();
					}
				}
				cancelNoticeToCustomer(taskMap.get(disposal_object_department).getTaskId(), Integer.parseInt(canceltask.getTaskSource().toString()), String.valueOf(canceltask.getCustomerId()), canceltask.getContactMobile(), (taskTransmitCause==3?disposalDesc:reasonMap.get(taskTransmitCause)), siteName);
				//营业分部消息推送
				//cancelTaskSendNoticeToBusinesSegmt(taskMap.get(disposal_object_department), taskMap.get(disposal_object_department).getCompId(), 1, reasonMap.get(taskTransmitCause));
				hasComp = false;
				//根据taskId更新包裹表数据，将包裹所属站点compId设置为空
				updateParcelCompIdByTaskId(taskMap.get(disposal_object_department).getTaskId(), 0, null);
			}
			taskrecord = taskMap.get(disposal_object_department);
			//修改收派员任务记录
			//cancelTaskDisposalRecord(taskrecord.getId(),taskrecord.getTaskId(),disposalDesc,show_flag_hide, task_error_flag_ok);
			//添加log日志
			if(hasComp) {
				cancelTaskDisposalRecord(taskrecord.getId(),taskrecord.getTaskId(),disposalDesc,show_flag_hide, task_error_flag_ok);
				cancelTaskLog(taskrecord.getTaskId(), memberId, taskTransmitCause, reasonMap.get(taskTransmitCause), disposalDesc, 0);
			} else {
				cancelTaskDisposalRecord(taskrecord.getId(),taskrecord.getTaskId(),disposalDesc,show_flag_show, task_error_flag_ok);
				cancelTaskLog(taskrecord.getTaskId(), memberId, taskTransmitCause, reasonMap.get(taskTransmitCause), disposalDesc, 1);
			}
			//下属收派员
			if(taskMap.get(disposal_object_member) != null) {
				//收派员取消
				cancelTaskDisposalRecord(taskMap.get(disposal_object_member).getId(),taskMap.get(disposal_object_member).getTaskId(),"",show_flag_hide, task_error_flag_ok);
				if(taskMap.get(disposal_object_exp) != null) {
					cancelTaskInfo(taskMap.get(disposal_object_member).getTaskId(), task_status_untake, disposal_object_member, null);
				} else {
					cancelTaskInfo(taskMap.get(disposal_object_member).getTaskId(), task_status_cancel, disposal_object_member, null);
				}
				//发消息给收派员
				ParTaskInfo canceltask = parTaskInfoMapper.selectByPrimaryKey(taskMap.get(disposal_object_department).getTaskId());
				String sendaddr = "";
				if(!task_source_ec.equals(canceltask.getTaskSource())) {
					DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(canceltask.getContactAddressId()), DicAddressaid.class);
					if(PubMethod.isEmpty(address)){
						address = this.dicAddressaidMapper.findById(canceltask.getContactAddressId());
					}
					if(!PubMethod.isEmpty(address)){
						sendaddr=address.getCountyName() + address.getTownName() + canceltask.getContactAddress().substring(canceltask.getContactAddress().indexOf(" ")+1, canceltask.getContactAddress().length());
						sendaddr = sendaddr.replaceAll("-城区", "").replaceAll("县城", "");
					}
				} else {
					sendaddr = canceltask.getContactAddress();
				}
				cancelNoticeToExpMember(taskMap.get(disposal_object_member).getTaskId(), taskMap.get(disposal_object_member).getMemberId(), canceltask.getContactName(), canceltask.getContactMobile(), sendaddr, getMember(taskMap.get(disposal_object_member).getMemberId()).getMemberPhone());
			}
		} else { //站点拒绝
			taskrecord = taskMap.get(disposal_object_exp);
			if(task_status_cancel.equals(taskrecord.getDisposalType())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.cancelTask.002", "任务已经取消不可重复取消");
			}
			//更新取消状态，任务记录表
			cancelTaskDisposalRecord(null, taskrecord.getTaskId(), disposalDesc, null, task_error_flag_ok);
			//记录取消日志信息
			cancelTaskLog(taskrecord.getTaskId(), memberId, taskTransmitCause, reasonMap.get(taskTransmitCause), disposalDesc, 1);
			//更改任务信息表状态
			cancelTaskInfo(taskrecord.getTaskId(), task_status_cancel, disposal_object_member, taskrecord.getCompId());
			//给客户发消息
			ParTaskInfo canceltask = parTaskInfoMapper.selectByPrimaryKey(taskMap.get(disposal_object_exp).getTaskId());
			String sendaddr = "";
			if(!task_source_ec.equals(canceltask.getTaskSource())) {
				DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(canceltask.getContactAddressId()), DicAddressaid.class);
				if(PubMethod.isEmpty(address)){
					address = this.dicAddressaidMapper.findById(canceltask.getContactAddressId());
				}
				if(!PubMethod.isEmpty(address)){
					sendaddr=address.getCountyName() + address.getTownName() + canceltask.getContactAddress().substring(canceltask.getContactAddress().indexOf(" ")+1, canceltask.getContactAddress().length());
					sendaddr = sendaddr.replaceAll("-城区", "").replaceAll("县城", "");
				}
			} else {
				sendaddr = canceltask.getContactAddress();
			}
			BasEmployeeRelation relation = basEmployeeRelationMapper.queryCompIdByMemberId(memberId);
			String siteName = "";
			if(!PubMethod.isEmpty(relation)) {
				BasCompInfo compName = getComp(relation.getCompId());
				if(compName != null) {
					siteName = compName.getCompName();
				}
			}
			cancelNoticeToCustomer(taskMap.get(disposal_object_exp).getTaskId(), Integer.parseInt(canceltask.getTaskSource().toString()), String.valueOf(canceltask.getCustomerId()), canceltask.getContactMobile(), (taskTransmitCause==3?disposalDesc:reasonMap.get(taskTransmitCause)), siteName);
			if(taskMap.get(disposal_object_member) != null) {
				//发消息给收派员
				cancelNoticeToExpMember(taskMap.get(disposal_object_member).getTaskId(), taskMap.get(disposal_object_member).getMemberId(), canceltask.getContactName(), canceltask.getContactMobile(), sendaddr, getMember(taskMap.get(disposal_object_member).getMemberId()).getMemberPhone());
			}
			//站点推送消息
			//cancelTaskSendNoticeToBusinesSegmt(taskrecord, taskMap.get(disposal_object_exp).getCompId(), 1, reasonMap.get(taskTransmitCause));
			//根据taskId更新包裹表数据，将包裹所属站点compId设置为空
			updateParcelCompIdByTaskId(taskMap.get(disposal_object_exp).getTaskId(), 0, null);
		}
	}
	
	/**
	 * 
	 * @Method: unTakeTaskDisposalRecord 
	 * @Description: 更新未处理状态，任务记录表
	 * @param id
	 * @param taskId
	 * @param disposalDesc
	 * @author xpf
	 * @date 2014-10-30 上午11:56:29
	 * @since jdk1.6
	 */
	private void unTakeTaskDisposalRecord(Long id, Long taskId, String disposalDesc, Byte showFlag, Byte taskErrorFlag) throws ServiceException {
		ParTaskDisposalRecord taskrecord = null;
		String recordcache = getCache("TakeTaskCacheTaskRecord", String.valueOf(id), String.class);
		if(!PubMethod.isEmpty(recordcache)) {
			taskrecord = JSON.parseObject(recordcache, ParTaskDisposalRecord.class);
		}
		if(PubMethod.isEmpty(taskrecord)) {
			taskrecord = parTaskDisposalRecordMapper.selectByPrimaryKey(id);
		}
		if(!PubMethod.isEmpty(taskrecord)) {
			taskrecord.setId(id);
			taskrecord.setTaskId(taskId);
			taskrecord.setShowFlag(showFlag);
			taskrecord.setDisposalType(task_status_untake);
			taskrecord.setModifiedTime(new Date());
			taskrecord.setDisposalDesc(disposalDesc);
			taskrecord.setTaskErrorFlag(taskErrorFlag);
			parTaskDisposalRecordMapper.updateCancelTaskByTaskId(taskrecord);
			putCache("TakeTaskCacheTaskRecord", String.valueOf(id), taskrecord);
		}
	}
	
	/**
	 * 
	 * @Method: cancelTaskDisposalRecord 
	 * @Description: 更新取消状态，任务记录表
	 * @param id
	 * @param taskId
	 * @param disposalDesc
	 * @author xpf
	 * @date 2014-10-30 上午11:56:29
	 * @since jdk1.6
	 */
	private void cancelTaskDisposalRecord(Long id, Long taskId, String disposalDesc, Byte showFlag, Byte taskErrorFlag) throws ServiceException {
		if(!PubMethod.isEmpty(id)) {
			String recordcache = getCache("TakeTaskCacheTaskRecord", String.valueOf(id), String.class);
			ParTaskDisposalRecord taskrecord = null;
			if(!PubMethod.isEmpty(recordcache)) {
				taskrecord = JSON.parseObject(recordcache, ParTaskDisposalRecord.class); 
			}
			if(PubMethod.isEmpty(taskrecord)) {
				taskrecord = parTaskDisposalRecordMapper.selectByPrimaryKey(id);
			}
			if(!PubMethod.isEmpty(taskrecord)) {
				taskrecord.setTaskId(taskId);
				taskrecord.setShowFlag(showFlag);
				taskrecord.setDisposalType(task_status_cancel);
				taskrecord.setModifiedTime(new Date());
				taskrecord.setDisposalDesc(disposalDesc);
				taskrecord.setTaskErrorFlag(taskErrorFlag);
				parTaskDisposalRecordMapper.updateCancelTaskByTaskId(taskrecord);
				putCache("TakeTaskCacheTaskRecord", String.valueOf(id), taskrecord);
			}
		} else {
			List<ParTaskDisposalRecord> taskrecordlist = getTaskRecord(taskId);
			if(!PubMethod.isEmpty(taskrecordlist)) {
				for(ParTaskDisposalRecord taskrecord : taskrecordlist) {
					taskrecord.setId(taskrecord.getId());
					taskrecord.setTaskId(taskId);
					taskrecord.setShowFlag(showFlag);
					taskrecord.setDisposalType(task_status_cancel);
					taskrecord.setModifiedTime(new Date());
					taskrecord.setDisposalDesc(disposalDesc);
					taskrecord.setTaskErrorFlag(taskErrorFlag);
					parTaskDisposalRecordMapper.updateCancelTaskByTaskId(taskrecord);
					putCache("TakeTaskCacheTaskRecord", String.valueOf(taskrecord.getId()), taskrecord);
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @Method: cancelTaskLog 
	 * @Description: 取消任务记录日志
	 * @param taskId
	 * @param memberId
	 * @param taskTransmitCause
	 * @param taskProcessDesc
	 * @author xpf
	 * @date 2014-10-30 下午12:04:27
	 * @since jdk1.6
	 */
	private void cancelTaskLog(Long taskId, Long memberId, Byte taskTransmitCause, String taskProcessDesc, String disposalDesc, int prevComp) throws ServiceException {
		TaskVo task = new TaskVo();
		task.setTaskId(taskId);
		task.setOperatorId(memberId);
		MemberInfo memberinfo = getMember(memberId);
		if(memberinfo != null) {
			task.setOperatorDesc(memberinfo.getMemberName());
		} else {
			task.setOperatorDesc("客户");
			task.setOperatorId(null);
		}
		//查询memberid所属的站点或营业分部
		//未加缓存
		BasEmployeeRelation relation = basEmployeeRelationMapper.queryCompIdByMemberId(memberId);
		if(relation != null) {
			task.setOperatorCompId(relation.getCompId());
		} else {
			task.setOperatorCompId(null);
		}
		task.setLogTaskStatus(log_task_status_cancel);
		task.setTaskTransmitCause(taskTransmitCause);
		String msg = "";
		if(disposalDesc != null && taskTransmitCause == (byte) 3) {
			msg = disposalDesc;
		} else {
			msg = taskProcessDesc;
		}
		if(taskTransmitCause < (byte) 10) {
			task.setTaskProcessDesc("任务已被取消：" + msg);	
		} else {
			if(prevComp == 0) {//有上级
				task.setTaskProcessDesc("任务已被驳回：" + msg);
			} else {
				task.setTaskProcessDesc("任务已被取消：" + msg);
			}
		}
		taskLog(task);
	}
	/**
	 * 
	 * @Method: cancelTaskInfo 
	 * @Description: 任务信息表更新取消状态
	 * @param taskId
	 * @author xpf
	 * @date 2014-10-30 下午1:13:14
	 * @since jdk1.6
	 */
	private void cancelTaskInfo(Long taskId, Byte status, Byte cancelType, Long coopCompId) throws ServiceException {
		String taskcache = getCache("TakeTaskCacheTaskInfo", String.valueOf(taskId), String.class);
		ParTaskInfo task = null;
		if(!PubMethod.isEmpty(taskcache)) {
			task = JSON.parseObject(taskcache, ParTaskInfo.class);
		}
		if(PubMethod.isEmpty(task) && !ehcacheService.getByKey("TakeTaskCacheTaskInfo", String.valueOf(taskId))) {
			task = parTaskInfoMapper.selectByPrimaryKey(Long.parseLong(String.valueOf(taskId)));
		}
		if(!PubMethod.isEmpty(task)) {
			task.setTaskId(taskId);
			task.setTaskStatus(status);
			task.setModifyTime(new Date());
			if(!PubMethod.isEmpty(coopCompId)) {
				task.setCoopCompId(coopCompId);
			}
			if(disposal_object_member.equals(cancelType)) { //收派员或客户取消
				task.setActorMemberId(null);
			}
			parTaskInfoMapper.updateParTaskInfoBySelective(task);
			putCache("TakeTaskCacheTaskInfo", String.valueOf(taskId), task);
		}
	}
	
	/**
	 * 
	 * @Method: getTaskRecord 
	 * @Description: 根据taskid查询未取消未完成的任务记录
	 * @param taskId
	 * @return
	 * @author xpf
	 * @date 2014-10-29 下午7:39:25
	 * @since jdk1.6
	 */
	private List<ParTaskDisposalRecord> getTaskRecord(Long taskId) throws ServiceException {
		//读取缓存
		String result = ehcacheService.get("TakeTaskCacheRecordResult", taskId+"", String.class);
		List<ParTaskDisposalRecord> tasklist = JSON.parseArray(result,ParTaskDisposalRecord.class);
		if(tasklist == null || tasklist.size() <= 0) {
			tasklist = parTaskDisposalRecordMapper.queryTaskAvailableByTaskId(taskId);
			//数据放入缓存
			putCache("TakeTaskCacheRecordResult", String.valueOf(taskId), tasklist);
		}
		if(tasklist != null && tasklist.size() > 0) {
			for(ParTaskDisposalRecord task : tasklist) {
				if(task_status_finish.equals(task.getDisposalType())) {
					throw new ServiceException("openapi.TakeTaskServiceImpl.getTaskRecord.003", task.getTaskId() + "任务已完成");
				}
			}
		}
		return tasklist;
	}
	
	/**
	 * 
	 * @Method: getTaskRecord 
	 * @Description: 根据taskid查询未取消未完成的任务记录
	 * @param taskId
	 * @return
	 * @author xpf
	 * @date 2014-10-29 下午7:39:25
	 * @since jdk1.6
	 */
	private List<ParTaskDisposalRecord> getDistributeTaskRecord(Long taskId){
		List<ParTaskDisposalRecord> tasklist =parTaskDisposalRecordMapper.queryDistributeTaskByTaskId(taskId);
		return tasklist;
	}
	
	private Map<Byte, ParTaskDisposalRecord> getTaskRecordMap(List<ParTaskDisposalRecord> tasklist) throws ServiceException {
		Map<Byte, ParTaskDisposalRecord> resultMap = null;
		if(tasklist != null && tasklist.size() > 0) {
			resultMap = new HashMap<Byte, ParTaskDisposalRecord>();
			for(ParTaskDisposalRecord task : tasklist) {
				if(disposal_object_member.equals(task.getDisposalObject())) { //收派员
					resultMap.put(disposal_object_member, task);
					cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(task.getMemberId()));
				}
				if(disposal_object_department.equals(task.getDisposalObject())) { //营业分部
					resultMap.put(disposal_object_department, task);
					cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(task.getCompId()));
					if(task.getCompId() != null && !"".equals(task.getCompId().toString())) {
						String result = getCache("employeeCache", String.valueOf(task.getCompId()), String.class);
						List<VO_MemberInfo> list = null;
						if(!PubMethod.isEmpty(result)) {
							list = JSON.parseArray(result, VO_MemberInfo.class); 
						}
						if(!PubMethod.isEmpty(list)) {
							for(VO_MemberInfo mem : list) {
								cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(mem.getMemberId()));
							}
						}
					}
				}
				if(disposal_object_exp.equals(task.getDisposalObject())) { //站点
					resultMap.put(disposal_object_exp, task);
					cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(task.getCompId()));
					if(task.getCompId() != null && !"".equals(task.getCompId().toString())) {
						String result = getCache("employeeCache", String.valueOf(task.getCompId()), String.class);
						List<VO_MemberInfo> list = null;
						if(!PubMethod.isEmpty(result)) {
							list = JSON.parseArray(result, VO_MemberInfo.class); 
						}
						if(!PubMethod.isEmpty(list)) {
							for(VO_MemberInfo mem : list) {
								cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(mem.getMemberId()));
							}
						}
					}
				}
			}
		}
		return resultMap;
	}

	@Override
	public Map<String,Object> queryTaskDetail(Long id) throws ServiceException {
		if(PubMethod.isEmpty(id)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.queryTaskDetail.002", "请选择需要查询的任务记录");
		}
		//从缓存中读取任务记录
		ParTaskDisposalRecord taskrecord = cacheRecord(String.valueOf(id));
		Long taskId = null;
		if(taskrecord != null) {
			taskId = taskrecord.getTaskId();
		} else {
			throw new ServiceException("openapi.TakeTaskServiceImpl.queryTaskDetail.001", "查询任务信息失败");
		}
		Map<String,Object> resultmap = new HashMap<String,Object>();
		//任务信息缓存
		ParTaskInfo taskinfo = cacheTaskInfo(String.valueOf(taskId));
		Map<String, Object> taskjsonmap = taskInfoToMap(taskinfo);
		//查询日志记录
		List<Map<String,Object>> resultlist = getCache("TakeTaskCacheTaskProcessResult", String.valueOf(taskId), ArrayList.class);
		if(resultlist != null && resultlist.size() > 0) {
			Map<String,Object> addmap = null;
			List<Map> maptemp = JSON.parseArray(resultlist.toString(), java.util.Map.class);
			for(int m = 0; m < maptemp.size(); m++) {
				addmap = new HashMap<String,Object>();
				if(!PubMethod.isEmpty(maptemp.get(m).get("id"))) {
					addmap.put("id", maptemp.get(m).get("id"));
				}
				resultlist.set(m, addmap);
			}
		} else {
			resultlist = parTaskProcessMapper.selectByTaskId(taskId);
			putCache("TakeTaskCacheTaskProcessResult", String.valueOf(taskId), resultlist);
		}
		if(resultlist != null && resultlist.size() > 0) {
			ParTaskProcess cacheProcess = null;
			for(int i = 0; i < resultlist.size(); i++) {
				cacheProcess = cacheProcess(String.valueOf(resultlist.get(i).get("id")));
				//组装日志信息
				resultlist.set(i, setLogResult(resultlist.get(i), cacheProcess));
			}
		}
		resultmap.put("resultlist", resultlist);
		resultmap.put("taskinfo", taskjsonmap);
		return resultmap;
	}
	
	private Map<String, Object> setLogResult(Map<String, Object> data, ParTaskProcess cacheProcess) throws ServiceException {
		if(!PubMethod.isEmpty(cacheProcess)) {
			data.put("taskProcessDesc", cacheProcess.getTaskProcessDesc());
			data.put("createTime", cacheProcess.getCreateTime());
			data.put("memberName", cacheProcess.getOperatorDesc());
			BasCompInfo compinfo = getComp(cacheProcess.getOperatorCompId());
			if(!PubMethod.isEmpty(compinfo)) {
				data.put("compName", compinfo.getCompName());
			} else {
				data.put("compName", "");
			}
		} else {
			data.put("taskProcessDesc", "");
			data.put("createTime", "");
			data.put("memberName", "");
			data.put("compName", "");
		}
		return data;
	}
	
	private Map<String, Object> taskInfoToMap(ParTaskInfo taskinfo) throws ServiceException {
		Map<String, Object> taskmap = new HashMap<String, Object>();
		//获取公司信息
		BasCompInfo compinfo = getComp(taskinfo.getCoopCompId());
		//获取收派员信息
		MemberInfo memberinfo = getMember(taskinfo.getActorMemberId());
		taskmap.put("actorMemberName", (memberinfo==null?"":memberinfo.getMemberName()));
		taskmap.put("actorMemberPhone", (memberinfo==null?"":memberinfo.getMemberPhone()));
		taskmap.put("appointDesc", taskinfo.getAppointDesc());
		if(taskinfo.getAppointTime() != null) {
			taskmap.put("appointTime", taskinfo.getAppointTime());
		} else {
			taskmap.put("appointTime", "");
		}
		if(taskinfo.getParEstimateWeight() != null) {
			taskmap.put("goodsWeight", taskinfo.getParEstimateWeight());
		} else {
			taskmap.put("goodsWeight", "");
		}
		taskmap.put("goodsDesc", taskinfo.getAppointDesc());
		taskmap.put("contactAddress", taskinfo.getContactAddress());
		taskmap.put("contactAddressId", taskinfo.getContactAddressId());
		taskmap.put("contactMobile", taskinfo.getContactMobile());
		taskmap.put("contactName", taskinfo.getContactName());
		taskmap.put("contactTel", taskinfo.getContactTel());
		taskmap.put("coopCompName", (compinfo==null?"":compinfo.getCompName()));
		taskmap.put("createTime", taskinfo.getCreateTime());
		taskmap.put("taskFlag", taskinfo.getTaskFlag());
		taskmap.put("taskId", taskinfo.getTaskId());
		taskmap.put("taskIsEnd", taskinfo.getTaskIsEnd());
		taskmap.put("taskSource", taskinfo.getTaskSource());
		taskmap.put("taskStatus", taskinfo.getTaskStatus());
		taskmap.put("taskType", taskinfo.getTaskType());
		taskmap.put("compId", taskinfo.getCoopCompId());
		return taskmap;
	}

	@Override
	public String finishTask(Long id, Long memberId, Long compId, Long taskId) throws ServiceException {
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.002", "请选择要完成的任务记录");
		}
//		if(PubMethod.isEmpty(compId)) {
//			throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.003", "当前操作人无所属站点");
//		}
//		if(PubMethod.isEmpty(memberId)) {
//			throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.004", "当前操作人不存在");
//		}
		ParTaskDisposalRecord findtask = null;
		List<ParTaskDisposalRecord> recordList = null;
		Long tempTaskId = null;
		if(!PubMethod.isEmpty(id)) {
			findtask = getCache("TakeTaskCacheTaskRecord", String.valueOf(id), ParTaskDisposalRecord.class);
			if(PubMethod.isEmpty(findtask)) {
				findtask = parTaskDisposalRecordMapper.selectByPrimaryKey(id);
			}
			if(task_status_finish.equals(findtask.getDisposalType())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.001", findtask.getTaskId() + "任务已完结无法再次完结");
			}
			if(task_status_delete.equals(findtask.getDisposalType())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.003", findtask.getTaskId() + "任务已删除无法再次完结");
			}
			if(task_status_cancel.equals(findtask.getDisposalType())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.004","对方已取消发件");
//				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.004", findtask.getTaskId() + "任务已取消无法再次完结");
			}
			recordList = getTaskRecord(findtask.getTaskId());
			tempTaskId = findtask.getTaskId();
			//取件任务完成，包裹算是已取件状态 然后更新取件时间
			if(tempTaskId!=null){
				parParcelinfoMapper.updatePickupTime(tempTaskId);
			}
		} else {
			ParTaskInfo taskinfo = getCache("TakeTaskCacheTaskInfo", String.valueOf(taskId), ParTaskInfo.class);
			if(PubMethod.isEmpty(taskinfo)) {
				taskinfo = parTaskInfoMapper.selectByPrimaryKey(taskId);
			}
			if(PubMethod.isEmpty(taskinfo)) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.005", "取件任务不存在");
			}
			if(task_status_finish.equals(taskinfo.getTaskStatus())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.001", taskinfo.getTaskId() + "任务已完结无法再次完结");
			}
			if(task_status_delete.equals(taskinfo.getTaskStatus())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.003", taskinfo.getTaskId() + "任务已删除无法再次完结");
			}
			if(task_status_cancel.equals(taskinfo.getTaskStatus())) {
				throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.004","对方已取消发件");
				//throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.004", taskinfo.getTaskId() + "任务已取消无法再次完结");
			}
			recordList = getTaskRecord(taskinfo.getTaskId());
			tempTaskId = taskinfo.getTaskId();
			this.ehcacheService.remove("TakeTaskCacheTaskInfo", String.valueOf(taskId));
		}
		final Byte task_is_end = 1; //任务结束
		if(recordList != null && recordList.size() > 0) {
			for(ParTaskDisposalRecord record : recordList) {
				//更新任务处理记录表状态
				record.setDisposalType(task_status_finish); //完成状态
				parTaskDisposalRecordMapper.updateByPrimaryKey(record);
				//putCache("TakeTaskCacheTaskRecord", String.valueOf(record.getId()), record);
				this.ehcacheService.remove("TakeTaskCacheTaskRecord", String.valueOf(record.getId()));
				cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(record.getCompId()));
				cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(record.getMemberId()));
				String fcompCount = record.getCompId()==null?"":record.getCompId()+"Count";
				String fmemberCount = record.getMemberId()==null?"":record.getMemberId()+"Count";
				removeCache("TakeTaskCacheQueryResult", fcompCount);
				removeCache("TakeTaskCacheQueryResult", fmemberCount);
				if(disposal_object_member.equals(record.getDisposalObject())) {
					cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(record.getMemberId()));
					cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(record.getMemberId()));
				}
				if(record.getCompId() != null && !"".equals(record.getCompId().toString())) {
					String result = getCache("employeeCache", String.valueOf(record.getCompId()), String.class);
					List<VO_MemberInfo> list = null;
					if(!PubMethod.isEmpty(result)) {
						list = JSON.parseArray(result, VO_MemberInfo.class); 
					}
					if(!PubMethod.isEmpty(list)) {
						for(VO_MemberInfo mem : list) {
							cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(mem.getMemberId()));
							cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(mem.getMemberId()));
						}
					}
				}
				
				//清除站点下营业分部和收派员的缓存
				if(record.getCompId() != null && !"".equals(record.getCompId().toString())) {
					List<Map<String, Object>> cleanresult = this.getMemberInfoByCompId(record.getCompId());
					if(cleanresult != null && cleanresult.size() > 0) {
						for(Map<String, Object> map : cleanresult) {
							cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(map.get("memberId")));
							cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(map.get("memberId")));
						}
					}
				}
			}
		}
		ParTaskInfo taskinfo = getCache("TakeTaskCacheTaskInfo", String.valueOf(tempTaskId), ParTaskInfo.class);
		if(PubMethod.isEmpty(taskinfo)) {
			taskinfo = parTaskInfoMapper.selectByPrimaryKey(tempTaskId);
		}
		//更新任务信息表状态
		taskinfo.setTaskStatus(task_status_finish); //完成状态
		taskinfo.setTaskIsEnd(task_is_end);
		taskinfo.setTaskEndTime(new Date());
		parTaskInfoMapper.updateByPrimaryKeySelective(taskinfo);
		//putCache("TakeTaskCacheTaskInfo", String.valueOf(tempTaskId), taskinfo);
		this.ehcacheService.remove("TakeTaskCacheTaskInfo", String.valueOf(tempTaskId));
		cleanQueryInfo("TakeTaskCacheRecordResult", "getTaskFromOthers_"+String.valueOf(tempTaskId));
		cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(taskinfo.getCreateUserId()));
		String createUserCount = taskinfo.getCreateUserId()==null?"":taskinfo.getCreateUserId()+"Count";
		removeCache("TakeTaskCacheQueryResult", createUserCount);
		ehcacheService.removeAll("TakeTaskCacheUnTakeCount");
		
		//记录日志信息
		TaskVo task = new TaskVo();
		task.setTaskId(tempTaskId);
		task.setFromCompId(null);
		task.setFromMemberId(null);
		task.setOperatorCompId(compId);
		task.setOperatorId(memberId);
		task.setLogTaskStatus(log_task_status_finish);
		MemberInfo memberinfo = getMember(memberId);
		if(memberinfo != null) {
			task.setOperatorDesc(memberinfo.getMemberName());
		} else {
			task.setOperatorDesc("客户");
		}
		task.setTaskProcessDesc("任务已完成");
		taskLog(task);
		removeCache("TakeTaskCacheTaskProcessResult", String.valueOf(tempTaskId));
		
		
		//调用电商管家
		if(!PubMethod.isEmpty(taskinfo) && (!PubMethod.isEmpty(memberId) || !PubMethod.isEmpty(compId)|| !PubMethod.isEmpty(id))) {
			if(taskinfo.getTaskSource() == 3 && taskinfo.getTaskFlag() == 1){
				try {
					Map<String,String> map = new HashMap<String,String>();
					map.put("taskId", taskinfo.getTaskId().toString());
					String url = constPool.getErpUrl() + "takeFinishServiceChooseExpressAction.aspx";
					String response = Post(url, map);
					Map<String,Object> result = JSON.parseObject(response);
					if(PubMethod.isEmpty(result) || !"success".equals(result.get("RESULT").toString())){
						throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.010", "完成，回调ERP异常");
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("openapi.TakeTaskServiceImpl.finishTask.011", "完成，回调ERP异常");
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> create(Long fromCompId, Long fromMemberId, Long toCompId,
			Long toMemberId, Long coopNetId, String appointTime,
			String appointDesc, Long actorMemberId, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long createUserId,
			BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude,
			Byte taskSource, Byte taskType, Short customerType, String saveFlag, 
			BigDecimal parEstimateWeight, Byte parEstimateCount, BigDecimal parEstimatePrice, Byte taskFlag, Long broadcastId, String noticeType) throws ServiceException, Exception {
		ehcacheService.removeAll("TakeTaskCacheUnTakeCount");
		//参数非空验证
//		if(PubMethod.isEmpty(contactName)) {
//			throw new ServiceException("openapi.TakeTaskServiceImpl.create.001", "请输入发件人姓名");
//		}
		if(PubMethod.isEmpty(contactMobile)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.create.002", "请输入发件人手机号");
		}
		if(PubMethod.isEmpty(contactAddressId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.create.003", "请选择发件地址");
		}
		if(PubMethod.isEmpty(contactAddress)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.create.003", "请选择发件地址");
		}
//		if(saveFlag != null && "1".equals(saveFlag)) {
//			String townName = contactAddress.split(" ")[0];
//			String detail = contactAddress.split(" ")[1];
//			addContacts(fromCompId, contactName, contactMobile, contactTel, customerType, contactAddressId, detail, toMemberId, townName);
//		}
		final Byte task_is_end = 0; //任务结束
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TaskVo task = new TaskVo();
		//清理缓存
		String cacheFromCompId = String.valueOf(fromCompId);
		String cacheFromMemberId = String.valueOf(fromMemberId);
		String cacheToCompId = String.valueOf(toCompId);
		String cacheToMemberId = String.valueOf(toMemberId);
		String cacheCreateUserId = String.valueOf(createUserId);
		
		task.setTaskType(taskType);
		task.setCoopCompId(fromCompId);
		task.setCoopNetId(coopNetId);
		task.setCreateTime(new Date());
		if(appointTime != null && !"".equals(appointTime)) {
			task.setAppointTime(sdf.parse(appointTime));
		}
		task.setAppointDesc(appointDesc);
		task.setTaskSource(taskSource);
		task.setTaskStatus(task_status_untake);
		task.setActorMemberId(actorMemberId);
		task.setParEstimateWeight(parEstimateWeight);
		task.setParEstimateCount(parEstimateCount);
		task.setContactName(contactName);
		task.setContactMobile(contactMobile);
		task.setContactTel(contactTel);
		task.setContactAddressId(contactAddressId);
		String[] address = contactAddress.split("\\|");
		String sendAddress = "";
		if(address != null && address.length > 0) {
			if(address.length > 1) {
				sendAddress = address[0] +" "+ address[1];
			} else {
				sendAddress = address[0];
			}
		}
		task.setContactAddress(sendAddress);
		task.setCustomerId(customerId);
		task.setCreateUserId(createUserId);
		task.setContactAddrLongitude(contactAddrLongitude);
		task.setContactAddrLatitude(contactAddrLatitude);
		task.setTaskFlag(taskFlag); //0正常 1抢单
		task.setTaskIsEnd(task_is_end);
		task.setFromCompId(fromCompId);
		task.setFromMemberId(fromMemberId);
		task.setToCompId(toCompId);
		task.setToMemberId(toMemberId);
		task.setOperatorCompId(fromCompId);
		task.setOperatorId(fromMemberId);
		MemberInfo memberinfo = getMember(fromMemberId);
		if(memberinfo != null) {
			task.setOperatorDesc(memberinfo.getMemberName());
		} else {
			task.setOperatorDesc("客户");
			task.setOperatorId(null);
		}
		task.setCompId(fromCompId);
		task.setMemberId(fromMemberId);
		task.setDisposalObject(compTypeNum(fromCompId));
		task.setShowFlag(show_flag_show);
		task.setTaskErrorFlag(task_error_flag_ok);
		task.setParEstimatePrice(parEstimatePrice); //报价
		
		String result = createTask(task, broadcastId, noticeType);
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("taskId", result);
		//清空广播缓存
		ehcacheService.remove("robBroadcastCache", String.valueOf(task.getCreateUserId()));
		String fcompCount = cacheFromCompId==null?"":cacheFromCompId+"Count";
		String fmemberCount = cacheFromMemberId==null?"":cacheFromMemberId+"Count";
		String tcompCount = cacheToCompId==null?"":cacheToCompId+"Count";
		String tmemberCount = cacheToMemberId==null?"":cacheToMemberId+"Count";
		String createUserCount = cacheCreateUserId==null?"":cacheCreateUserId+"Count";
		//清空缓存
		cleanQueryInfo("TakeTaskCacheQueryResult", cacheFromCompId);
		cleanQueryInfo("TakeTaskCacheQueryResult", cacheFromMemberId);
		cleanQueryInfo("TakeTaskCacheQueryResult", cacheToCompId);
		cleanQueryInfo("TakeTaskCacheQueryResult", cacheToMemberId);
		cleanQueryInfo("TakeTaskCacheQueryResult", cacheCreateUserId);
		cleanQueryInfo("TakeTaskCacheRecordResult", "getTaskFromOthers_"+String.valueOf(result));
		cleanQueryInfo("TakeTaskCacheTaskRecordResult", String.valueOf(result));
		//ehcacheService.removeAll("TakeTaskCacheUnTakeCount");
		removeCache("TakeTaskCacheQueryResult", fcompCount);
		removeCache("TakeTaskCacheQueryResult", fmemberCount);
		removeCache("TakeTaskCacheQueryResult", tcompCount);
		removeCache("TakeTaskCacheQueryResult", tmemberCount);
		removeCache("TakeTaskCacheQueryResult", createUserCount);
		removeCache("TakeTaskCacheTaskProcessResult", String.valueOf(result));
		cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", cacheToMemberId);
		String removeOkdi = result==null?"":("okdi_"+String.valueOf(result));
		removeCache("TakeTaskCacheTaskProcessResult", removeOkdi);
		removeCache("TakeTaskCacheTaskProcess", removeOkdi);
		//清除营业分部下面收派员的缓存
		if(cacheToCompId != null && !"".equals(cacheToCompId)) {
			String resultcache = getCache("employeeCache", cacheToCompId, String.class);
			List<VO_MemberInfo> list = null;
			if(!PubMethod.isEmpty(resultcache)) {
				list = JSON.parseArray(resultcache, VO_MemberInfo.class); 
			}
			if(!PubMethod.isEmpty(list)) {
				for(VO_MemberInfo mem : list) {
					cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(mem.getMemberId()));
					cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(mem.getMemberId()));
				}
			}
		}
		ehcacheService.removeAll("TakeTaskCacheTaskRecordResult");
		//清除站点下营业分部和收派员的缓存
		if(fromCompId != null && !"".equals(fromCompId.toString())) {
			List<Map<String, Object>> cleanresult = this.getMemberInfoByCompId(fromCompId);
			if(cleanresult != null && cleanresult.size() > 0) {
				for(Map<String, Object> map : cleanresult) {
					cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(map.get("memberId")));
					cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(map.get("memberId")));
				}
			}
		}
		return resultmap;
	}
	
	/**
	 * 
	 * @Method: compTypeNum 
	 * @Description: 查询站点类型
	 * @param compId
	 * @return
	 * @since jdk1.6
	 */
	public Byte compTypeNum(Long compId) throws ServiceException {
		BasCompInfo basCompInfo = null;
		basCompInfo = getCache("compCache", PubMethod.isEmpty(compId)?null:compId.toString(), BasCompInfo.class);
		if(basCompInfo == null) {
			logger.debug("查询站点类型------未走缓存");
			basCompInfo = this.basCompInfoMapper.findById(compId);
		}
		if(basCompInfo != null) {
			if(basCompInfo.getCompTypeNum() != null) {
				if("1006".equals(basCompInfo.getCompTypeNum())) {
					return disposal_object_exp;
				} else if("1050".equals(basCompInfo.getCompTypeNum())) {
					return disposal_object_department;
				}
			}
		} else {
			return null;
		}
		return null;
	}
	
	/**
	 * 
	 * @Method: createTask 
	 * @Description: 创建任务
	 * @param task
	 * @return
	 * @since jdk1.6
	 */
	private String createTask(TaskVo task, Long broadcastId, String noticeType) throws ServiceException, Exception {
		byte taskstatus = 0;
		long pkid = IdWorker.getIdWorker().nextId();
		task.setTaskId(pkid);//任务id
		//插入任务信息表
		String resultId = taskInfo(task);
		String sendaddr = "";
		if(!task_source_ec.equals(task.getTaskSource())) {
			DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(task.getContactAddressId()), DicAddressaid.class);
			if(PubMethod.isEmpty(address)){
				address = this.dicAddressaidMapper.findById(task.getContactAddressId());
			}
			if(!PubMethod.isEmpty(address)){
				sendaddr=address.getCountyName() + address.getTownName() + task.getContactAddress().substring(task.getContactAddress().indexOf(" ")+1, task.getContactAddress().length());
				sendaddr = sendaddr.replaceAll("-城区", "").replaceAll("县城", "");
			}
		} else {
			sendaddr = task.getContactAddress();
		}
		if(task.getDisposalObject() != null && disposal_object_exp.equals(task.getDisposalObject())) { //站点创建
			if(task.getToCompId() != null) {//营业分部
				creatByExp(task);
				task.setCoopCompId(task.getToCompId());
				//营业分部创建(任务处理记录表,任务流转日志表)
				task.setCompId(task.getToCompId());
				task.setActorMemberId(null);
				creatByDepartment(task);
				taskstatus = 1;
				//判断是否自动分配到收派员
				if(task.getToMemberId() != null) {
					//添加收派员任务记录
					createMemberTask(task);
					task.setCompId(null);
					task.setActorMemberId(task.getToMemberId());
					taskstatus = 1;
					//发消息给收派员和客户
					if(task_grab.equals(task.getTaskFlag())) {
						createTaskSendNoticeToPerson(task, sendaddr, 1, "", noticeType);
					} else {
						createTaskSendNoticeToPerson(task, sendaddr, 2, "", noticeType);
					}
					cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(task.getToMemberId()));
				} else {
					//发消息给营业分部
					Map<String, Object> map = queryTaskUnTakeCountByCompId(task.getCompId(), task_status_untake);
					Integer count = new Integer(0);
					if(map != null && map.get("num") != null) {
						count = (Integer) map.get("num");
					}
					createTaskSendNoticeToBusinesSegmt(task, sendaddr, task.getCompId(), count);
				}
			} else if(task.getToMemberId() != null) { //收派员
				creatByExp(task);
				//直接分配给收派员
				createMemberTask(task);
				task.setCompId(null);
				task.setActorMemberId(task.getToMemberId());
				taskstatus = 1;
				//发消息给收派员和客户
				if(task_grab.equals(task.getTaskFlag())) {
					createTaskSendNoticeToPerson(task, sendaddr, 1, "", noticeType);
				} else {
					createTaskSendNoticeToPerson(task, sendaddr, 2, "", noticeType);
				}
			} else { //自动分配
				creatByExp(task);
				//判断是否分配到营业分部或是收派员
				if(task.getToCompId() != null) { //分配到营业分部
					task.setCompId(task.getToCompId());
					creatByDepartment(task);
					//task.setCompId(task.getToCompId());
					task.setActorMemberId(null);
					taskstatus = 1;
					//判断是否自动分配到收派员
					if(task.getToMemberId() != null) {
						//添加收派员任务记录
						createMemberTask(task);
						task.setCompId(null);
						task.setActorMemberId(task.getToMemberId());
						taskstatus = 1;
						//发消息给收派员和客户
						if(task_grab.equals(task.getTaskFlag())) {
							createTaskSendNoticeToPerson(task, sendaddr, 1, "", noticeType);
						} else {
							createTaskSendNoticeToPerson(task, sendaddr, 2, "", noticeType);
						}
					} else {
						//发消息给营业分部
						Map<String, Object> map = queryTaskUnTakeCountByCompId(task.getCompId(), task_status_untake);
						Integer count = new Integer(0);
						if(map != null && map.get("num") != null) {
							count = (Integer) map.get("num");
						}
						createTaskSendNoticeToBusinesSegmt(task, sendaddr, task.getCompId(), count);
					}
				} else if(task.getToMemberId() != null) { //分配到收派员
					createMemberTask(task);
					task.setCompId(null);
					task.setActorMemberId(task.getToMemberId());
					taskstatus = 1;
					//发消息给收派员和客户
					if(task_grab.equals(task.getTaskFlag())) {
						createTaskSendNoticeToPerson(task, sendaddr, 1, "", noticeType);
					} else {
						createTaskSendNoticeToPerson(task, sendaddr, 2, "", noticeType);
					}
					cleanQueryInfo("TakeTaskCacheRecordResultByMemberId", String.valueOf(task.getToMemberId()));
				} else if(task_source_okdi.equals(task.getTaskSource()) || task_source_app_personal.equals(task.getTaskSource())) { //判断是否站点创建任务(非站点创建任务给站点弹气泡)
					Map<String, Object> map = queryTaskUnTakeCountByCompId(task.getFromCompId(), task_status_untake);
					Integer count = new Integer(0);
					if(map != null && map.get("num") != null) {
						count = (Integer) map.get("num");
					}
					createTaskSendNoticeToBusinesSegmt(task, sendaddr, task.getFromCompId(), count);
				}
			}
		} else if(task.getDisposalObject() != null && disposal_object_department.equals(task.getDisposalObject())) {//营业分部创建
			if(task.getToMemberId() != null) { //收派员
				creatByDepartment(task);
				createMemberTask(task);
				task.setCompId(null);
				task.setActorMemberId(task.getToMemberId());
				taskstatus = 1;
				//发消息给收派员和客户
				if(task_grab.equals(task.getTaskFlag())) {
					createTaskSendNoticeToPerson(task, sendaddr, 1, "", noticeType);
				} else {
					createTaskSendNoticeToPerson(task, sendaddr, 2, "", noticeType);
				}
			} else { //自动分配
				creatByDepartment(task);
				if(task.getToMemberId() != null) {
					createMemberTask(task);
					task.setCompId(null);
					task.setActorMemberId(task.getToMemberId());
					taskstatus = 1;
					//发消息给收派员和客户
					if(task_grab.equals(task.getTaskFlag())) {
						createTaskSendNoticeToPerson(task, sendaddr, 1, "", noticeType);
					} else {
						createTaskSendNoticeToPerson(task, sendaddr, 2, "", noticeType);
					}
				} else {
					taskstatus = 0;
					//发消息给营业分部
					Map<String, Object> map = queryTaskUnTakeCountByCompId(task.getFromCompId(), task_status_untake);
					Integer count = new Integer(0);
					if(map != null && map.get("num") != null) {
						count = (Integer) map.get("num");
					}
					createTaskSendNoticeToBusinesSegmt(task, sendaddr, task.getFromCompId(), count);
				}
			}
		} else {//收派员创建任务(上级营业分部或站点)
			//收派员所属站点或营业分部任务处理记录
			if(task.getCompId() != null) { //站点审核通过
				createExpTaskWithMember(task);
				Map<String, Object> map = queryTaskUnTakeCountByCompId(task.getCompId(), task_status_untake);
				Integer count = new Integer(0);
				if(map != null && map.get("num") != null) {
					count = (Integer) map.get("num");
				}
				createTaskSendNoticeToBusinesSegmt(task, sendaddr, task.getCompId(), count);
			}
			createMemberTask(task);
			task.setCompId(null);
			task.setActorMemberId(task.getToMemberId());
			taskstatus = 1;
			//发消息给客户
			if(task_grab.equals(task.getTaskFlag())) {
				createTaskSendNoticeToPerson(task, sendaddr, 1, "", noticeType);
			} else {
				createTaskSendNoticeToPerson(task, sendaddr, 2, "", noticeType);
			}
		}
		//更新包裹表
		//if(task_grab.equals(task.getTaskFlag())) {
		List<RobParcelRelation> relation = robParcelRelationMapper.queryRobParcelRelationByRobId(broadcastId);
		if(!PubMethod.isEmpty(relation)) {
			ParParcelinfo parcelupdate = null;
			ParParceladdress paraddr = null;
			for(RobParcelRelation parcel : relation) {
				parcelupdate = parcelInfoService.getParcelInfoById(parcel.getParcelId());
				if(!PubMethod.isEmpty(parcelupdate)) {
					parcelupdate.setCompId(task.getCoopCompId());
					parcelupdate.setNetId(task.getCoopNetId());
					parcelupdate.setId(parcel.getParcelId());
					parcelupdate.setTakeTaskId(task.getTaskId());
					parcelupdate.setActualTakeMember(task.getActorMemberId());
					parcelupdate.setCreateTime(new Date());
					parcelupdate.setParcelStatus((short) 0);
					parParcelinfoMapper.updateParcelSelective(parcelupdate);
					//更新对象缓存
					ehcacheService.put("parcelInfoCache", String.valueOf(parcel.getParcelId()), parcelupdate);
				}
				paraddr = parcelInfoService.getParParceladdressById(parcel.getParcelId());
				if(!PubMethod.isEmpty(paraddr)) {
					paraddr.setId(parcel.getParcelId());
					paraddr.setSendName(task.getContactName());
					paraddr.setSendMobile(task.getContactMobile());
					paraddr.setSendAddressId(task.getContactAddressId());
					paraddr.setSendAddress(task.getContactAddress().replaceAll("\\|", " "));
					paraddr.setSendCasUserId(task.getCreateUserId());
					parParceladdressMapper.updateParceladdressSelective(paraddr);
					//更新对象缓存
					ehcacheService.put("parcelAddressCache", String.valueOf(parcel.getParcelId()), paraddr);
				}
				//清理缓存
				ehcacheService.remove("takeParcelIdsCacheByTaskId", String.valueOf(task.getTaskId()));
				ehcacheService.remove("takeParcelIdsCacheByMemberId", String.valueOf(task.getTaskId()));
				cleanQueryInfo("takeParcelIdsCacheByMemberId", String.valueOf(task.getCoopCompId()));
			}
		}
		//}
		task.setTaskStatus(taskstatus);
		ParTaskInfo pti = parTaskInfoMapper.selectByPrimaryKey(task.getTaskId());
		pti.setCoopCompId(task.getCoopCompId());
		pti.setTaskId(task.getTaskId());
		pti.setActorMemberId(task.getActorMemberId());
		pti.setTaskStatus(taskstatus);
		parTaskInfoMapper.updateByPrimaryKey(pti);
		//任务信息放入缓存
		putCache("TakeTaskCacheTaskInfo", String.valueOf(pti.getTaskId()), pti);
		return resultId;
	}
	
	private MemberInfo getMember(Long memberId) throws ServiceException {
		MemberInfo memberinfo = memberInfoService.getMemberInfoById(memberId);
		return memberinfo;
	}
	
	private BasCompInfo getComp(Long compId) throws ServiceException {
		BasCompInfo basCompInfo = null;
		basCompInfo = ehcacheService.get("compCache", PubMethod.isEmpty(compId)?null:compId.toString(), BasCompInfo.class);
		if(basCompInfo == null) {
			logger.debug("查询公司信息------未走缓存");
			basCompInfo = basCompInfoMapper.findById(compId);
		} else {
			logger.debug("查询公司信息------走缓存");
		}
		return basCompInfo;
	}
	
	/**
	 * 
	 * @Method: creatByExp 
	 * @Description: 站点创建任务
	 * @param task
	 * @since jdk1.6
	 */
	private void creatByExp(TaskVo task) throws ServiceException {
		task.setTaskStatus(task_status_distribute);
		task.setLogTaskStatus(log_task_status_distribute);
		//获取公司信息
		BasCompInfo compinfo = getComp(task.getToCompId());
		//获取收派员信息
		MemberInfo memberinfo = getMember(task.getToMemberId());
		//需要自动分配
		if(task.getToMemberId() == null && task.getToCompId() == null) {
			Map<String, Long> mapid = autoDistribute(task.getCompId(), task.getContactAddrLongitude(), task.getContactAddrLatitude());
			//自动分配
			if(mapid!= null) {
				//自动分配给收派员
				if(mapid.get("memberId") != null) { 
					MemberInfo member = getMember(Long.parseLong(mapid.get("memberId").toString()));
					task.setToMemberId(mapid.get("memberId"));
					task.setToCompId(null);
					task.setTaskProcessDesc("系统自动指定任务给：" + (member==null?"--":member.getMemberName()));
					task.setOperatorDesc("系统");
				} else if(mapid.get("compId") != null) {//分配给营业分部
					BasCompInfo compName = getComp(task.getCompId());
					task.setToCompId(mapid.get("compId"));
					task.setToMemberId(null);
					task.setTaskProcessDesc("系统自动指定任务给：" + (compName==null?"--":compName.getCompName()));
					task.setOperatorDesc("系统");
				}
				//操作任务处理记录表和任务流转日志表
				taskLog(task);
				TaskRecord(task);
			} else { //未分配到，生成待处理任务
				task.setToMemberId(null);
				task.setToCompId(null);
				task.setTaskStatus(task_status_untake);
				//操作任务处理记录表,不记录log
				TaskRecord(task); 
			}
		} else {
			if(task.getToMemberId() != null) {
				if(task_source_ec.equals(task.getTaskSource()) || task_source_app_personal.equals(task.getTaskSource())) {
					task.setTaskProcessDesc("客户指定任务给：" + (memberinfo==null?"--":memberinfo.getMemberName()));
					task.setOperatorDesc("客户");
					task.setFromCompId(null);
					task.setFromMemberId(null);
					task.setToCompId(null);
					task.setOperatorCompId(null);
					task.setOperatorId(null);
				} else {
					task.setTaskProcessDesc("网点指定任务给：" + (memberinfo==null?"--":memberinfo.getMemberName()));
				}
			} else if(task.getToCompId() != null) {
				task.setTaskProcessDesc("网点指定任务给：" + (compinfo==null?"--":compinfo.getCompName()));
			}
			//操作任务处理记录表和任务流转日志表
			taskLog(task);
			TaskRecord(task);
		}
	}

	/**
	 * 
	 * @Method: creatByDepartment 
	 * @Description: 营业分部创建任务
	 * @param task
	 * @since jdk1.6
	 */
	private void creatByDepartment(TaskVo task) throws ServiceException {
		task.setFromCompId(task.getCompId());
		task.setFromMemberId(null);
		task.setToCompId(null);
		task.setLogTaskStatus(log_task_status_distribute);
		task.setMemberId(null);
		task.setDisposalObject(disposal_object_department);
		task.setShowFlag(show_flag_show);
		task.setTaskErrorFlag(task_error_flag_ok);
		//自动分配收派员
		Map<String, Long> mapid = null;
		//获取收派员信息
		MemberInfo memberinfo = getMember(task.getToMemberId());
		if(task.getToMemberId() != null) { //分配收派员
			task.setTaskStatus(task_status_distribute);
			task.setTaskProcessDesc("网点指定任务给：" + (memberinfo==null?"--":memberinfo.getMemberName()));
			//操作任务处理记录表和任务流转日志表
			taskLog(task);
			TaskRecord(task);
		} else { //自动分配
			mapid = autoDistribute(task.getCompId(), task.getContactAddrLongitude(), task.getContactAddrLatitude());
			if(mapid != null && mapid.get("memberId") != null) { //系统分配
				MemberInfo member = getMember(Long.parseLong(mapid.get("memberId").toString()));
				task.setTaskStatus(task_status_distribute);
				task.setToMemberId(mapid.get("memberId"));
				task.setTaskProcessDesc("系统自动指定任务给：" + (member==null?"--":member.getMemberName()));
				task.setOperatorDesc("系统");
				//操作任务处理记录表和任务流转日志表
				taskLog(task);
				TaskRecord(task);
			} else {
				task.setTaskStatus(task_status_untake);
				task.setToMemberId(null);
				//操作任务处理记录表,不记录log
				TaskRecord(task); 
			}
		}
	}
	
	/**
	 * 
	 * @Method: createMemberTask 
	 * @Description: 收派员任务处理记录
	 * @param task
	 * @since jdk1.6
	 */
	private void createMemberTask(TaskVo task) throws ServiceException {
		task.setCompId(null);
		task.setTaskStatus(task_status_untake);
		task.setMemberId(task.getToMemberId());
		task.setDisposalObject(disposal_object_member);
		task.setShowFlag(show_flag_show);
		task.setTaskErrorFlag(task_error_flag_ok);
		//操作任务处理记录表,不记录log
		TaskRecord(task);
	}
	
	/**
	 * 
	 * @Method: createExpTaskWithMember 
	 * @Description: 添加收派员所属站点或营业分部任务处理记录
	 * @param task
	 * @since jdk1.6
	 */
	private void createExpTaskWithMember(TaskVo task) throws ServiceException {
		//BasEmployeeRelation employee = basEmployeeRelationMapper.queryCompIdByMemberId(task.getToMemberId());
		task.setFromCompId(null);
		task.setFromMemberId(null);
		task.setToCompId(null);
		task.setLogTaskStatus(log_task_status_distribute);
		task.setOperatorCompId(null);
		task.setOperatorId(null);
		//获取收派员信息
		MemberInfo memberinfo = getMember(task.getToMemberId());
		task.setTaskProcessDesc("客户指定任务给：" + (memberinfo==null?"--":memberinfo.getMemberName()));
		task.setOperatorDesc("客户");
		//task.setCompId(employee.getCompId());
		task.setMemberId(null);
		task.setDisposalObject(compTypeNum(task.getCompId()));
		task.setCoopCompId(task.getCompId());
		//操作任务处理记录表和任务流转日志表
		taskLog(task);
		TaskRecord(task);
	}
	
	/**
	 * 
	 * @Method: addTaskRecord 
	 * @Description: 任务处理记录
	 * @param task
	 * @since jdk1.6
	 */
	private void TaskRecord(TaskVo task) throws ServiceException {
		//任务处理记录表
		task.setId(IdWorker.getIdWorker().nextId());
		if(task.getCreateTime() == null) {
			task.setCreateTime(new Date());
		}
		parTaskDisposalRecordMapper.insert(task);
		ParTaskDisposalRecord cacheRecord = new ParTaskDisposalRecord();
		cacheRecord.setId(task.getId());
		cacheRecord.setTaskId(task.getTaskId());
		cacheRecord.setDisposalType(task.getTaskStatus());
		cacheRecord.setMemberId(task.getMemberId());
		cacheRecord.setCompId(task.getCompId());
		cacheRecord.setDisposalObject(task.getDisposalObject());
		cacheRecord.setShowFlag(task.getShowFlag());
		cacheRecord.setTaskErrorFlag(task.getTaskErrorFlag());
		cacheRecord.setDisposalDesc(task.getDisposalDesc());
		cacheRecord.setCreateTime(task.getCreateTime());
		cacheRecord.setModifiedTime(task.getModifiedTime());
		//放入缓存
		putCache("TakeTaskCacheTaskProcess", String.valueOf(cacheRecord.getId()), cacheRecord);
	}
	
	/**
	 * 
	 * @Method: taskLog 
	 * @Description: 任务流转日志
	 * @param task
	 * @since jdk1.6
	 */
	private void taskLog(TaskVo task) throws ServiceException {
		//任务流转日志表
		task.setId(IdWorker.getIdWorker().nextId());
		task.setLogCreateTime(new Date());
		parTaskProcessMapper.insert(task);
		ParTaskProcess cacheProcess = new ParTaskProcess();
		cacheProcess.setId(task.getId());
		cacheProcess.setTaskId(task.getTaskId());
		cacheProcess.setFromCompId(task.getFromCompId());
		cacheProcess.setFromMemberId(task.getFromMemberId());
		cacheProcess.setToCompId(task.getToCompId());
		cacheProcess.setToMemberId(task.getToMemberId());
		cacheProcess.setCreateTime(task.getLogCreateTime());
		cacheProcess.setTaskStatus(task.getLogTaskStatus());
		cacheProcess.setTaskTransmitCause(task.getTaskTransmitCause());
		cacheProcess.setOperatorId(task.getOperatorId());
		cacheProcess.setOperatorCompId(task.getOperatorCompId());
		cacheProcess.setOperatorDesc(task.getOperatorDesc());
		cacheProcess.setTaskProcessDesc(task.getTaskProcessDesc());
		//放入缓存
		putCache("TakeTaskCacheTaskProcess", String.valueOf(cacheProcess.getId()), cacheProcess);
	}
	
	/**
	 * 
	 * @Method: taskInfo 
	 * @Description: 保存任务信息
	 * @param task
	 * @since jdk1.6
	 */
	
	private String taskInfo(TaskVo task) throws ServiceException {
		//插入任务信息表
		Long id = IdWorker.getIdWorker().nextId();
		task.setId(id);
		if(task.getCreateTime() == null) {
			task.setCreateTime(new Date());
		}
		parTaskInfoMapper.insert(task);
		String re =  String.valueOf(task.getTaskId());
		return re;
	}
	
	
	/**
	 * 
	 * @Method: autoDistribute 
	 * @Description: 自动分配营业分部或是收派员
	 * @param compId
	 * @param addrLongitude
	 * @param addrLatitude
	 * @param status 1站点分配 2营业分部分配
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午3:52:21
	 * @since jdk1.6
	 */
	private Map<String, Long> autoDistribute(Long compId, BigDecimal addrLongitude, BigDecimal addrLatitude) throws ServiceException {
		if(addrLongitude == null || addrLatitude == null) {
			return null;
		}
		Double lat = addrLatitude!=null?Double.valueOf(addrLatitude.toString()):null;
		Double lgt = addrLongitude!=null?Double.valueOf(addrLongitude.toString()):null;
		List<Map> distributelist = courierService.autoSuggest(compId, lat, lgt);
		if(distributelist != null && distributelist.size() > 0) {
			Map map = new HashMap();
			if(distributelist.get(0).get("memberId") != null && !"".equals(distributelist.get(0).get("memberId"))) {
				map.put("memberId", Long.parseLong(distributelist.get(0).get("memberId").toString()));
			}
			if(distributelist.get(0).get("compId") != null && !"".equals(distributelist.get(0).get("compId"))) {
				map.put("compId", Long.parseLong(distributelist.get(0).get("compId").toString()));
			}
			return map;
		} else {
			return null;
		}
	}
	
	
	
	@Override
	public int queryTaskUnFinished(Long compId, Long memberId) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", compId);
		map.put("memberId", memberId);
		List<ParTaskDisposalRecord> tasklist = parTaskDisposalRecordMapper.queryTaskUnFinished(map);
		if(tasklist != null && tasklist.size() > 0) {
			if(compId != null) { //站点或营业分部
				for(ParTaskDisposalRecord record : tasklist) {
					if(compId.equals(record.getCompId()) && disposal_object_department.equals(record.getDisposalObject())) { //compId为营业分部
						List<ParTaskDisposalRecord> comptasklist = parTaskDisposalRecordMapper.queryCompTaskById(record.getTaskId());
						if(comptasklist != null && comptasklist.size() > 0) {
							for(ParTaskDisposalRecord task : comptasklist) {
								if(record.getTaskId().equals(task.getTaskId()) && disposal_object_exp.equals(task.getDisposalObject())) {//站点分配的任务
									return 1;
								}
							}
						}
					}
				}
			} else {
				for(ParTaskDisposalRecord record : tasklist) {
					if(memberId.equals(record.getMemberId()) && disposal_object_member.equals(record.getDisposalObject())) { //收派员离职
						return 1;
					}
				}
			}
			return 0;
		} else {//可以解除关系
			return 0;
		}
	}
	
	/**
	 * 
	 * @Method: createTaskSendNoticeToPerson
	 * @Description: 发消息给收派员或客户
	 * @param task
	 * @param sendaddr
	 * @param sendStatus 0收派员 1客户 2都发
	 * @author xpf
	 * @date 2014-11-10 下午3:24:50
	 * @since jdk1.6
	 */
	private void createTaskSendNoticeToPerson(TaskVo task, String sendaddr, int sendStatus, String oldExpName, String noticeType) {
		String lot = task.getContactAddrLongitude()==null?"":task.getContactAddrLongitude().toString();
		String lat = task.getContactAddrLatitude()==null?"":task.getContactAddrLatitude().toString();
		MemberInfo member = getMember(task.getToMemberId());
		String memMap = ehcacheService.get("onLineMember", String.valueOf(task.getToMemberId()), String.class);
		Map<String,Object> map = null;
		if(!PubMethod.isEmpty(memMap)) {
			map = JSON.parseObject(memMap,HashMap.class);
		}
		String expMemberLon = "";
		String expMemberLat = "";
		if(map != null) {
			expMemberLon = map.get("lng")!=null?map.get("lng").toString():"";
			expMemberLat = map.get("lat")!=null?map.get("lat").toString():"";
		} else {
			//查询数据库
			Map<String,Object> memberMap = basOnLineMemberMapper.selectLngLat(task.getToMemberId());
			if(memberMap != null) {
				expMemberLon = memberMap.get("LONGITUDE")!=null?memberMap.get("LONGITUDE").toString():"";
				expMemberLat = memberMap.get("LATITUDE")!=null?memberMap.get("LATITUDE").toString():"";
			}
		}
		if(sendStatus == 0) {
			//发消息给收派员
			noticeToMember(task.getTaskId(),task.getToMemberId(),lot,lat,task.getContactName(),task.getContactMobile(),sendaddr,
					task.getCoopNetId(),member.getMemberPhone(), task.getContactAddress().replaceAll("-", ""), noticeType,task.getAppointTime());
		} else if(sendStatus == 1) {
			//给客户发送消息
			if(oldExpName != null && !"".equals(oldExpName)) {
				turnToCustomer(task.getTaskId(), Integer.parseInt(task.getTaskSource().toString()), String.valueOf(task.getCustomerId()), task.getContactMobile(),member.getMemberName(),
						member.getMemberPhone(),expMemberLon,expMemberLat,oldExpName);
			} else {
				noticeToCustomer(task.getTaskId(), Integer.parseInt(task.getTaskSource().toString()), String.valueOf(task.getCustomerId()), task.getContactMobile(),member.getMemberName(),
						member.getMemberPhone(),expMemberLon,expMemberLat);
			}
		} else {
			//发消息给收派员
			noticeToMember(task.getTaskId(),task.getToMemberId(),lot,lat,task.getContactName(),task.getContactMobile(),sendaddr,
					task.getCoopNetId(),member.getMemberPhone(), task.getContactAddress().replaceAll("-", ""), noticeType,task.getAppointTime());
			//给客户发送消息
			if(oldExpName != null && !"".equals(oldExpName)) {
				turnToCustomer(task.getTaskId(), Integer.parseInt(task.getTaskSource().toString()), String.valueOf(task.getCustomerId()), task.getContactMobile(),member.getMemberName(),
						member.getMemberPhone(),expMemberLon,expMemberLat,oldExpName);
			} else {
				noticeToCustomer(task.getTaskId(), Integer.parseInt(task.getTaskSource().toString()), String.valueOf(task.getCustomerId()), task.getContactMobile(),member.getMemberName(),
						member.getMemberPhone(),expMemberLon,expMemberLat);
			}
			
		}
	}
	
	/**
	 * 
	 * @Method: createTaskSendNoticeToBusinesSegmt 
	 * @Description: 发消息给营业分部或站点
	 * @param task
	 * @param sendaddr
	 * @author xpf
	 * @date 2014-11-10 下午3:40:03
	 * @since jdk1.6
	 */
	private void createTaskSendNoticeToBusinesSegmt(TaskVo task, String sendaddr, Long compId, Integer num) {
		BasCompInfo compName = getComp(compId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", compId);
		String result = getCache("employeeCache", String.valueOf(compId), String.class);
		List<VO_MemberInfo> list = null;
		if(!PubMethod.isEmpty(result) && result.length() > 0 && !"[]".equals(result)) {
			list = JSON.parseArray(result, VO_MemberInfo.class);
		} else {
			list = this.memberInfoService.queryEmployeeCache(compId);
			ehcacheService.put("employeeCache", String.valueOf(compId), list);
		}
		if(!PubMethod.isEmpty(list)) {
			String strmember = "";
			for(VO_MemberInfo mem : list) {
				if(mem.getRoleId() != null && !"0".equals(mem.getRoleId().toString())) {
					if(PubMethod.isEmpty(strmember)) {
						strmember = mem.getMemberId()==null?"":String.valueOf(mem.getMemberId());
					} else {
						if(!PubMethod.isEmpty(mem.getMemberId())) {
							strmember += "," + String.valueOf(mem.getMemberId());
						}
					}
				}
			}
			//发消息给营业分部
			noticeToBusinesSegmt(task.getTaskId(),strmember,compName.getCompName(),task.getContactMobile(),task.getContactName(),sendaddr, num);
		}
	}
	
	/**
	 * 
	 * @Method: cancelTaskSendNoticeToBusinesSegmt 
	 * @Description: 取消任务给站点或营业分部发消息
	 * @param taskrecord
	 * @param id
	 * @param status
	 * @param reason
	 * @author xpf
	 * @date 2014-11-10 下午6:03:02
	 * @since jdk1.6
	 */
	private void cancelTaskSendNoticeToBusinesSegmt(String sendType, ParTaskDisposalRecord taskrecord, Long id, int status, String reason, int taskNum) {
		ParTaskInfo task = getTaskInfoByTaskId(taskrecord.getTaskId());
		String sendaddr = "";
		if(!task_source_ec.equals(task.getTaskSource())) {
			DicAddressaid address = ehcacheService.get("addressCache", String.valueOf(task.getContactAddressId()), DicAddressaid.class);
			if(PubMethod.isEmpty(address)){
				address = this.dicAddressaidMapper.findById(task.getContactAddressId());
			}
			if(!PubMethod.isEmpty(address)){
				sendaddr=address.getCountyName() + address.getTownName() + task.getContactAddress().substring(task.getContactAddress().indexOf(" ")+1, task.getContactAddress().length());
				sendaddr = sendaddr.replaceAll("-城区", "").replaceAll("县城", "");
			}
		} else {
			sendaddr = task.getContactAddress();
		}
//		String sendaddr = task.getContactAddress().split(" ")[0];
//		String addrdetail = task.getContactAddress().split(" ")[1];
//		sendaddr = sendaddr.substring(sendaddr.indexOf("-")+1, sendaddr.length()) + addrdetail;
		String cancelName = "";
		if(status == 0) {
			cancelName = getMember(id).getMemberName();
		} else if(status == 1) {
			cancelName = getComp(id).getCompName();
		}
		BasCompInfo compName = getComp(taskrecord.getCompId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", taskrecord.getCompId());
		List<BasEmployeeRelation> employeelist = basEmployeeRelationMapper.queryCountById(map);
		if(employeelist != null && employeelist.size() > 0) {
			String strmember = "";
			for(BasEmployeeRelation relation : employeelist) {
				if(relation.getRoleId() != null && !"0".equals(relation.getRoleId())) {
					if(PubMethod.isEmpty(strmember)) {
						strmember = relation.getMemberId()==null?"":String.valueOf(relation.getMemberId());
					} else {
						if(!PubMethod.isEmpty(relation.getMemberId())) {
							strmember += "," + String.valueOf(relation.getMemberId());
						}
					}
				}
			}
			cancelNoticeToSite(sendType, taskrecord.getTaskId(), strmember, compName.getCompName(), task.getContactMobile(), task.getContactName(), sendaddr, cancelName, reason, taskNum);
		}
	}
	
	/**
	 * 
	 * @Method: noticeToMember 
	 * @Description: 发消息给收派员
	 * @param taskId
	 * @param memberId
	 * @param sendLon
	 * @param sendLat
	 * @param sendName
	 * @param sendMobile
	 * @param sendAddress
	 * @param netId
	 * @param expMemberMob
	 * @author xpf
	 * @date 2014-11-10 下午1:18:07
	 * @since jdk1.6
	 */
	private void noticeToMember(Long taskId, Long memberId, String sendLon, String sendLat, String sendName, String sendMobile, String sendAddress, Long netId,
			String expMemberMob, String sendDetailAddress, String noticeType,Date takeTime) {
		String takeTimeShort=null;
		if(PubMethod.isEmpty(takeTime)){
			takeTimeShort = "立即";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			takeTimeShort= sdf.format(takeTime);
		}
		//发消息给收派员
		sendNoticeService.asigntoExpMemberAmssy(taskId, memberId, sendLon, sendLat, sendName, sendMobile, sendAddress, netId, expMemberMob, sendDetailAddress, "zmn",takeTimeShort);
	}
	/**
	 * 
	 * @Method: noticeToCustomer 
	 * @Description: 发消息给客户
	 * @param taskId
	 * @param taskSource
	 * @param memberId
	 * @param customerMob
	 * @param expMemberName
	 * @param expMemberMob
	 * @param expMemberLon
	 * @param expMemberLat
	 * @author xpf
	 * @date 2014-11-10 上午11:45:36
	 * @since jdk1.6
	 */
	private void noticeToCustomer(Long taskId, Integer taskSource, String memberId, String customerMob, String expMemberName, String expMemberMob, String expMemberLon,
			String expMemberLat) {
		//任务分配给收派员--->给客户推送消息
		if(PubMethod.isEmpty(memberId) || "null".equals(memberId)) {
			memberId = "";
		}
		System.out.println("任务重新分配--->给客户推送消息");
		sendNoticeService.assignToCustomer(taskId, taskSource, memberId, customerMob, expMemberName, expMemberMob, expMemberLon, expMemberLat);
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>任务重新分配--->给客户推送消息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-4 下午1:38:34</dd>
	 * @param taskId
	 * @param taskSource
	 * @param memberId
	 * @param customerMob
	 * @param expMemberName
	 * @param expMemberMob
	 * @param expMemberLon
	 * @param expMemberLat
	 * @param oldExpName
	 * @since v1.0
	 */
	private void turnToCustomer(Long taskId, Integer taskSource, String memberId, String customerMob, String expMemberName, String expMemberMob, String expMemberLon, String expMemberLat,String oldExpName) {
		System.out.println("任务重新分配--->给客户推送消息  包含旧收派员姓名");
		sendNoticeService.turnToCustomer(taskId, taskSource, memberId, customerMob, expMemberName, expMemberMob, expMemberLon, expMemberLat, oldExpName);
	}
	
	
	/**
	 * 
	 * @Method: noticeToBusinesSegmt 
	 * @Description: 发消息给营业分部
	 * @param taskId
	 * @param memberId
	 * @param businesSegmtName
	 * @param sendName
	 * @param sendAddress
	 * @author xpf
	 * @date 2014-11-10 下午2:07:13
	 * @since jdk1.6
	 */
	private void noticeToBusinesSegmt(Long taskId, String memberId, String businesSegmtName, String sendMob, String sendName, String sendAddress, int taskNum) {
		//站点分配任务给营业分部 --->给营业分部推送消息
		if(!PubMethod.isEmpty(memberId) && !"null".equals(memberId)) {
			sendNoticeService.asignToBusinesSegmt(taskId, memberId, businesSegmtName, sendMob, sendName, sendAddress, taskNum);
		}
	}
	
	/**
	 * 
	 * @Method: cancelNoticeToCustomer
	 * @Description: 取消任务发消息给客户
	 * @param taskId
	 * @param taskSource
	 * @param memberId
	 * @param customerMob
	 * @param canselReason
	 * @author xpf
	 * @date 2014-11-10 下午3:56:13
	 * @since jdk1.6
	 */
	private void cancelNoticeToCustomer(Long taskId, Integer taskSource, String memberId, String customerMob, String canselReason, String siteName) {
		//站点取消任务 --->给客户推送消息
		if(PubMethod.isEmpty(memberId) || "null".equals(memberId)) {
			memberId = "";
		}
		sendNoticeService.cancelToCustomer(taskId, taskSource, memberId, customerMob, canselReason, siteName);
		
	}
	
	/**
	 * 
	 * @Method: cancelNoticeToExpMember 
	 * @Description: 取消任务发消息给收派员
	 * @param taskId
	 * @param memberId
	 * @param sendName
	 * @param sendMob
	 * @param sendAddress
	 * @param expMemberMob
	 * @author xpf
	 * @date 2014-11-10 下午4:14:44
	 * @since jdk1.6
	 */
	private void cancelNoticeToExpMember(Long taskId, Long memberId, String sendName, String sendMob, String sendAddress, String expMemberMob) {
		//任务取消 --->给收派员推送消息
		sendNoticeService.cancelToExpMember(taskId, memberId, sendName, sendMob, sendAddress, expMemberMob);
	}
	
	/**
	 * 
	 * @Method: cancelNoticeToSite 
	 * @Description: 取消任务发消息给站点或营业分部
	 * @param taskId
	 * @param memberId
	 * @param siteName
	 * @param sendName
	 * @param sendAddress
	 * @param cancelName
	 * @param cancelReason
	 * @author xpf
	 * @date 2014-11-10 下午6:06:57
	 * @since jdk1.6
	 */
	private void cancelNoticeToSite(String sendType ,Long taskId, String memberId, String siteName, String sendMob, String sendName, String sendAddress, String cancelName, String cancelReason, int taskNum) {
		//营业分部/收派员取消任务--->给站点推送消息
		if(!PubMethod.isEmpty(memberId) && !"null".equals(memberId)) {
			sendNoticeService.cancelToSite(sendType, taskId, memberId, siteName, sendMob, sendName, sendAddress, cancelName, cancelReason, taskNum);
		}
	}
	
	
	@Override
	public Page queryOkdiTask(Long memberId, Page page, Byte takeSource) throws ServiceException {
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.queryOkdiTask.001", "当前操作人不存在");
		}
		//cache参数拼接
		String cacheParam = "";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("createUserId", memberId);
		cacheParam += "createUserId="+memberId;
		params.put("page", page);
		cacheParam += "currentPage="+page.getCurrentPage()+"pageSize="+page.getPageSize();
		params.put("takeSource", takeSource);
		cacheParam += "takeSource="+takeSource;
		
		boolean queryByDB = false;
		int totalCount = 0;
		//计算页数
		int pageNum = 0;
		Map<String,Object> map = new HashMap<String, Object>();
 		map.put("createUserId", memberId);
		//保存此站点的条件查询语句
		List<String> memberQueryList = null;
		
		List<Map<String,Object>> okdilist = null;
		if(ehcacheService.getByKey("TakeTaskCacheQueryResult", cacheParam)) { //缓存不为空
			//map中key为查询条件拼串，value为查询结果集
			okdilist = getCache("TakeTaskCacheQueryResult", cacheParam, ArrayList.class);
			if(okdilist != null && okdilist.size() > 0) {
				Integer count = getCache("TakeTaskCacheQueryResult", memberId+"Count", Integer.class);
				if(!PubMethod.isEmpty(count)) {
					totalCount = count.intValue();
				}
				Map<String,Object> addmap = null;
				List<Map> maptemp = JSON.parseArray(okdilist.toString(), java.util.Map.class);
				for(int m = 0; m < maptemp.size(); m++) {
					addmap = new HashMap<String,Object>();
					if(!PubMethod.isEmpty(maptemp.get(m).get("taskId"))) {
						addmap.put("taskId", maptemp.get(m).get("taskId"));
					}
					okdilist.set(m, addmap);
				}
			}
			queryByDB = false;
		} else {
			queryByDB = true;
		}
		//查询数据库
		if(queryByDB) {
			okdilist = parTaskInfoMapper.queryTaskByCreateUserId(params);
			//统计页数，返回结果值
			totalCount = parTaskInfoMapper.queryTaskCountByCreateUserId(map);
			//存入缓存
			putCache("TakeTaskCacheQueryResult", cacheParam, okdilist);

			putCache("TakeTaskCacheQueryResult", memberId+"Count", totalCount);
			
			//获取缓存中当前站点查询条件集合
			if(ehcacheService.getByKey("TakeTaskCacheQueryResult", String.valueOf(memberId))) {
				memberQueryList = getCache("TakeTaskCacheQueryResult", String.valueOf(memberId), ArrayList.class);
			}
			if(memberQueryList==null){
				memberQueryList = new ArrayList<String>();
			}
			if(!memberQueryList.contains(cacheParam)){
				memberQueryList.add(cacheParam);
			}
			//存入缓存
			putCache("TakeTaskCacheQueryResult", String.valueOf(memberId), memberQueryList);
		}
		if(totalCount % page.getPageSize() > 0) {
			pageNum = (totalCount/page.getPageSize()) + 1;
		}
		List<Map<String,Object>> allList = new ArrayList<Map<String, Object>>();
		if(okdilist != null && okdilist.size() > 0) {
			ParTaskInfo cacheTaskInfo = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Map<String,Object>> untakeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> distributeList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> finishList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> cancelList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> refuseList = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> info : okdilist) {
				//任务信息缓存
				cacheTaskInfo = cacheTaskInfo(String.valueOf(info.get("taskId")));
				//组装数据
				setWechatResultList(info, cacheTaskInfo, sdf);
				
				if(info.get("taskStatus") != null && !"".equals(info.get("taskStatus").toString())) {
					if(task_status_untake.equals(info.get("taskStatus"))) {
						untakeList.add(info);
					}
					if(task_status_distribute.equals(info.get("taskStatus"))) {
						if((Boolean)info.get("sendStatus")) {
							distributeList.add(info);
						} else {
							untakeList.add(info);
						}
					}
					if(task_status_cancel.equals(info.get("taskStatus"))) {
						//客户原因取消
						if(info.get("taskTransmitCause") != null && !"".equals(info.get("taskTransmitCause").toString()) && Integer.parseInt(info.get("taskTransmitCause").toString()) < 10) {
							cancelList.add(info);
						} else {
							refuseList.add(info);
						}
					}
					if(task_status_finish.equals(info.get("taskStatus"))) {
						finishList.add(info);
					}
				}
			}
			allList.addAll(untakeList);
			allList.addAll(distributeList);
			allList.addAll(refuseList);
			allList.addAll(cancelList);
			allList.addAll(finishList);
		}
		page.setPageCount(pageNum);
		page.setTotal(totalCount);
		page.setItems(allList);
		return page;
	}
	
	/**
	 * 微信端查询发件记录
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map queryWechatTask(String memberId, Page page, Byte takeSource, String netName, String actorMemberName,
			String actorPhone) throws ServiceException {
		Map<String, List<Map>> dataMap = new TreeMap<String, List<Map>>(new Comparator<String>() {  
            public int compare(String d1, String d2) {  
                return d2.compareTo(d1);  
            }  
        }); 
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("memberId.TakeTaskServiceImpl.queryOkdiTask.001", "当前操作人不存在");
		}
		// cache参数拼接
		String cacheParam = "";
		cacheParam += "memberId=" + memberId;
		cacheParam += "currentPage=" + page.getCurrentPage() + "pageSize=" + page.getPageSize();
		cacheParam += "takeSource=" + takeSource;
		// 增加查询条件
		cacheParam += "netName=" + netName;
		cacheParam += "actorMemberName=" + actorMemberName;
		cacheParam += "actorPhone=" + actorPhone;
		boolean queryByDB = false;
		Long totalCount = 0L;
		// 计算页数
		int pageNum = 0;
		
		List<Map> okdilist = null;
		if (ehcacheService.getByKey("TakeTaskCacheQueryList", cacheParam)) { // 缓存不为空
			// map中key为查询条件拼串，value为查询结果集
			okdilist = getCache("TakeTaskCacheQueryList", cacheParam, ArrayList.class);
			if (okdilist != null && okdilist.size() > 0) {
				Integer count = getCache("TakeTaskCacheQueryList", memberId + "Count", Integer.class);
				if (!PubMethod.isEmpty(count)) {
					totalCount = count.longValue();
				}
			}
			queryByDB = false;
		} else {
			queryByDB = true;
		}
		// 查询数据库
		if (queryByDB) {
			Criteria criteria = Criteria.where("createUserId").is(Long.valueOf(memberId)).and("taskSource").is(takeSource).and("taskStatus").ne(10).and("actorMemberId").ne(null).and("isRemove").ne((byte)1);
			Query query = Query.query(criteria)
					.skip(page.getOffset())
					.limit(page.getPageSize())
					.with(new Sort(Direction.DESC, "createTime"));
			
			okdilist = mongoTemplate.find(query, Map.class, "parTaskInfo");
			int size = okdilist.size();
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = okdilist.get(i);
				Long netId = (Long)map.get("coopNetId");
				Long actorMemberId = (Long)map.get("actorMemberId");
				String memberName = parTaskInfoMapper.getMemberName(actorMemberId);
				String netName1 = parTaskInfoMapper.getNetName(netId);
				map.put("netImageUrl", netImageUrl+netId+".jpg");
				map.put("netName", netName1);
				map.put("courierName", memberName);
				Date createTime = ((Date)map.get("createTime"));
				Date now = new Date();
				Long dayBegin = dayBegin(now).getTime();
				Long dayEnd = dayEnd(now).getTime();
				Byte isToday = 0;
				long time = createTime.getTime();
				if(time>=dayBegin&&time<=dayEnd){
					isToday=1;	
				}
				map.put("isToday", isToday);//发件记录的创建时间是否是今天 0否 1是
			}
			okdilist = this.setWechatResultList(okdilist);
			
			// 统计页数，返回结果值   taskSource:6 微信叫快递
			Query countQuery = Query.query(Criteria.where("createUserId").is(Long.valueOf(memberId)).and("taskSource").is(6).and("taskStatus").ne(10).and("actorMemberId").ne(null).and("isRemove").ne((byte)1));
			totalCount = mongoTemplate.count(countQuery, "parTaskInfo");
			//totalCount = parTaskInfoMapper.queryWeTaskCountByMemberId(map);
			// 存入缓存
			putCache("TakeTaskCacheQueryList", cacheParam, okdilist);
			putCache("TakeTaskCacheQueryList", memberId + "Count", totalCount);
		}
		//拼装数据,按日期返回
		for (Map map : okdilist) {
			Object createTime = map.get("createTime");
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(createTime);
			List<Map> dataList = dataMap.get(date);
			if (dataList == null) {
				dataList = new ArrayList<>();
			}
			dataList.add(map);
			dataMap.put(date, dataList);
		}
//		if (totalCount % page.getPageSize() > 0) {
//			pageNum = (int) ((totalCount / page.getPageSize()) + 1);
//		}
//		page.setItems(okdilist);
//		page.setPageCount(pageNum);
//		page.setTotal(totalCount.intValue());
		return dataMap;
	}
	
	//组装数据
	@SuppressWarnings("unchecked")
	private List setWechatResultList(List infoList) throws ServiceException {
		List resultList = new ArrayList();
		if (!PubMethod.isEmpty(infoList)) {
			for (int i = 0; i < infoList.size(); i++) {//任务状态 0待处理，1已分配，2已完成，3已取消，4微信叫快递-快递员拒绝接单 10已删除
				Map map = (HashMap) infoList.get(i);
				String status = map.get("taskStatus") != null ? String.valueOf(map.get("taskStatus")) : "";
				if ("".equals(status) || "0".equals(status)) {
					map.put("taskStatusTxt", "已通知快递员");
				}else if("1".equals(status)) {
					map.put("taskStatusTxt", "快递员已接单");
				}else if("2".equals(status)) {
					map.put("taskStatusTxt", "订单已完成");
				}else if("3".equals(status)) {
					map.put("taskStatusTxt", "订单已取消");
				}else if ("4".equals(status)) {
					map.put("taskStatusTxt", "快递员拒绝接单");
				}
				resultList.add(map);
			}
		}
		return resultList;
	}
	//组装数据
	private void setWechatResultList(Map<String,Object> info, ParTaskInfo cacheTaskInfo, SimpleDateFormat sdf) throws ServiceException {
		if(!PubMethod.isEmpty(cacheTaskInfo)) {
			info.put("createTime", sdf.format(cacheTaskInfo.getCreateTime()));
			info.put("createTimePersonal", cacheTaskInfo.getCreateTime());
			info.put("parEstimateWeight", cacheTaskInfo.getParEstimateWeight());
			info.put("parEstimateCount", cacheTaskInfo.getParEstimateCount());
			info.put("taskStatus", cacheTaskInfo.getTaskStatus());
			info.put("netName", "");
			if(!PubMethod.isEmpty(cacheTaskInfo.getActorMemberId())) {
				info.put("sendStatus", true);
			} else {
				info.put("sendStatus", false);
			}
			info.put("contactName", cacheTaskInfo.getContactName());
			info.put("contactMobile", cacheTaskInfo.getContactMobile());
			info.put("contactAddress", cacheTaskInfo.getContactAddress());
			info.put("compId", cacheTaskInfo.getCoopCompId());
			MemberInfo memberinfo = getMember(cacheTaskInfo.getActorMemberId());
			if(!PubMethod.isEmpty(memberinfo)) {
				info.put("memberName", memberinfo.getMemberName());
				info.put("imageUrl", imageUrl+memberinfo.getMemberId()+".jpg");
				info.put("memberPhone", memberinfo.getMemberPhone());
			} else {
				info.put("memberName", "");
				info.put("imageUrl", "");
				info.put("memberPhone", "");
			}
		} else {
				info.put("compName", "");
				info.put("netName", "");
				info.put("compPhone", "");
				info.put("compType", "");
				info.put("responsible", "");
		}
		}

	@Override
	public List<Map<String, Object>> getMemberInfoByCompId(Long parentId) throws ServiceException {
		if(PubMethod.isEmpty(parentId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.getMemberInfoByCompId.001", "当前操作人所属站点不存在");
		}
		List<Map<String, Object>> maplist = memberInfoService.getMemberInfoByCompId(parentId);
		List<Map<String, Object>> memlist = new ArrayList<Map<String, Object>>(); //收派员
		List<Map<String, Object>> Logistics = new ArrayList<Map<String, Object>>(); //后勤
		List<Map<String, Object>> leaderlist = new ArrayList<Map<String, Object>>(); //站长
		List<Map<String, Object>> complist = new ArrayList<Map<String, Object>>(); //站点集合
		if(maplist != null && maplist.size() > 0) {
			for(Map<String, Object> map : maplist) {
				if(map.get("roleId") != null && !"无".equals(map.get("roleId").toString())) {
					if("0".equals(map.get("roleId").toString())) {
						memlist.add(map);
					} else if("1".equals(map.get("roleId").toString())) {
						leaderlist.add(map);
					} else if("-1".equals(map.get("roleId").toString())) {
						Logistics.add(map);
					} else if("-2".equals(map.get("roleId").toString())) {
						leaderlist.add(map);
					}
				} else {
					complist.add(map);
				}
			}
		}
		memlist.addAll(leaderlist);
		memlist.addAll(Logistics);
		memlist.addAll(complist);
		return memlist;
	}

	@Override
	public Map<String,Object> queryOkdiTaskDetail(Long taskId) throws ServiceException {
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.queryOkdiTaskDetail.002", "请选择要查询任务信息");
		}
		Map<String,Object> resultmap = new HashMap<String,Object>();
		//查询任务信息
		
		ParTaskInfo taskinfo = cacheTaskInfo(String.valueOf(taskId));
		if(taskinfo == null) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.queryOkdiTaskDetail.001", "查询任务信息失败");
		}
		Map<String, Object> taskjsonmap = taskInfoToMap(taskinfo);
		
		BasCompInfo compinfo = getComp((Long) taskjsonmap.get("compId"));
		BasNetInfo netInfo = null;
		if(compinfo != null && compinfo.getBelongToNetId() != null && !"".equals(compinfo.getBelongToNetId().toString())) {
			netInfo = getCache("netCache", compinfo.getBelongToNetId().toString(), BasNetInfo.class);
			if(netInfo == null) {
				taskjsonmap.put("netName", "");
				logger.debug("查询网点信息------未走缓存");
			} else {
				taskjsonmap.put("netName", netInfo.getNetName());
				logger.debug("查询网点信息------走缓存");
			}
		}
		taskjsonmap.put("compPhone", (compinfo==null?"":compinfo.getCompTelephone()));
		if(compinfo!=null && compinfo.getCompStatus() != null && "1".equals(compinfo.getCompStatus().toString())) {
			taskjsonmap.put("compType", "已认证");
		} else {
			taskjsonmap.put("compType", "");
		}
		if(!PubMethod.isEmpty(taskinfo)) {
			if(taskinfo.getTaskStatus() != null && !"".equals(taskinfo.getTaskStatus().toString()) && "3".equals(taskinfo.getTaskStatus().toString())) {
				String cachelogparam = "okdi_" + taskinfo.getTaskId();
				//判断是否为站点方取消
				//读取缓存信息
				String result = getCache("TakeTaskCacheTaskProcess", cachelogparam, String.class);
				List<ParTaskProcess> taskProcessList = null;
				if(PubMethod.isEmpty(result)) {
					taskProcessList = JSON.parseArray(result,ParTaskProcess.class);
				}
				if(PubMethod.isEmpty(taskProcessList)) {
					taskProcessList = parTaskProcessMapper.selectMaxCancelTaskByTaskId(taskinfo.getTaskId());
					if(taskProcessList != null && taskProcessList.size() > 0) {
						taskjsonmap.put("taskTransmitCause", taskProcessList.get(0).getTaskTransmitCause());
					} else {
						taskjsonmap.put("taskTransmitCause", "");
					}
					putCache("TakeTaskCacheTaskProcess", cachelogparam, taskProcessList);
				} else {
					taskjsonmap.put("taskTransmitCause", taskProcessList.get(0).getTaskTransmitCause());
				}
			} else {
				taskjsonmap.put("taskTransmitCause", "");
			}
		}
		resultmap.put("taskinfo", taskjsonmap);
		return resultmap;
	}

	@Override
	public Map<String, Object> queryTaskUnTakeCount(Long compId, Byte status) throws ServiceException {
		if(PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.queryTaskUnTakeCount.001", "当前操作人所属站点不存在");
		}
		Map<String,Object> resultmap = new HashMap<String,Object>();
		String key = "";
		//判断compid类型
		if(disposal_object_exp.equals(compTypeNum(compId))) {//站点
			resultmap.put("disposalObject", disposal_object_exp);
			key += "disposalObject=" + disposal_object_exp;
		} else if(disposal_object_department.equals(compTypeNum(compId))) { //营业分部
			resultmap.put("disposalObject", disposal_object_department);
			key += "disposalObject=" + disposal_object_department;
		}
		resultmap.put("operatorCompId", compId);
		key += "operatorCompId=" + compId;
		resultmap.put("disposalType", status);
		key += "disposalType=" + status;
		int resultnum = 0;
		Integer num = ehcacheService.get("TakeTaskCacheUnTakeCount", key, Integer.class);
		if(!PubMethod.isEmpty(num)) {
			resultnum = num.intValue();
		} else {
			resultnum = parTaskDisposalRecordMapper.queryTaskByExpCount(resultmap);
			putCache("TakeTaskCacheUnTakeCount", key, new Integer(resultnum));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("num", resultnum);
		BasCompInfo basCompInfo = getComp(compId);
		if(PubMethod.isEmpty(basCompInfo)) {
			map.put("compName", "");
		} else {
			map.put("compName", basCompInfo.getCompName());
		}
		return map;
	}
	
	public Map<String, Object> queryTaskUnTakeCountByCompId(Long compId, Byte status) throws ServiceException {
		if(PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.queryTaskUnTakeCount.001", "当前操作人所属站点不存在");
		}
		Map<String,Object> resultmap = new HashMap<String,Object>();
		//判断compid类型
		if(disposal_object_exp.equals(compTypeNum(compId))) {//站点
			resultmap.put("disposalObject", disposal_object_exp);
		} else if(disposal_object_department.equals(compTypeNum(compId))) { //营业分部
			resultmap.put("disposalObject", disposal_object_department);
		}
		resultmap.put("operatorCompId", compId);
		resultmap.put("disposalType", status);
		int resultnum = 0;
		resultnum = parTaskDisposalRecordMapper.queryTaskByExpCount(resultmap);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("num", resultnum);
		BasCompInfo basCompInfo = getComp(compId);
		if(PubMethod.isEmpty(basCompInfo)) {
			map.put("compName", "");
		} else {
			map.put("compName", basCompInfo.getCompName());
		}
		return map;
	}

	@Override
	public String updateContacts(Long taskId, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId) throws ServiceException {
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.updateContacts.001", "请选择要更新的任务信息");
		}
		if(PubMethod.isEmpty(contactName)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.updateContacts.002", "请输入发件人姓名");
		}
		if(PubMethod.isEmpty(contactMobile)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.updateContacts.003", "请输入发件人手机号");
		}
		if(PubMethod.isEmpty(contactAddressId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.updateContacts.004", "请选择发件地址");
		}
		if(PubMethod.isEmpty(contactAddress)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.updateContacts.004", "请选择发件地址");
		}
		ParTaskInfo taskinfo = getCache("TakeTaskCacheTaskInfo", String.valueOf(taskId), ParTaskInfo.class);
		if(PubMethod.isEmpty(taskinfo) && !ehcacheService.getByKey("TakeTaskCacheTaskInfo", String.valueOf(taskId))) {
			taskinfo = parTaskInfoMapper.selectByPrimaryKey(taskId);
		}
		taskinfo.setTaskId(taskId);
		taskinfo.setContactName(contactName);
		taskinfo.setContactMobile(contactMobile);
		taskinfo.setContactTel(contactTel);
		taskinfo.setContactAddressId(contactAddressId);
		taskinfo.setContactAddress(contactAddress);
		taskinfo.setCustomerId(customerId);
		parTaskInfoMapper.updateByPrimaryKeySelective(taskinfo);
		putCache("TakeTaskCacheTaskInfo", String.valueOf(taskId), taskinfo);
		return null;
	}

	@Override
	public String updateTaskInfoForOkdi(Long taskId, Long memberId) {
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.updateTaskInfoForOkdi.001", "请选择要更新的任务信息");
		}
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.TakeTaskServiceImpl.updateTaskInfoForOkdi.002", "当前操作人不存在");
		}
		ParTaskInfo taskInfo = getCache("TakeTaskCacheTaskInfo", String.valueOf(taskId), ParTaskInfo.class);
		if(PubMethod.isEmpty(taskInfo) && !ehcacheService.getByKey("TakeTaskCacheTaskInfo", String.valueOf(taskId))) {
			taskInfo = parTaskInfoMapper.selectByPrimaryKey(taskId);
		}
		taskInfo.setCreateUserId(memberId);
		parTaskInfoMapper.updateByPrimaryKeySelective(taskInfo);
		
		putCache("TakeTaskCacheTaskInfo", String.valueOf(taskId), taskInfo);
		return null;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新包裹包compId</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-6 上午10:55:33</dd>
	 * @param taskId 任务id
	 * @param status 0删除compId，1更新compId
	 * @param compId 站点id
	 * @since v1.0
	 */
	private void updateParcelCompIdByTaskId(Long taskId, int status, Long compId) {
		List<Long> parcelList = parParcelinfoMapper.queryParcelInfoListByTaskId(taskId);
		Long clearCacheId = null;
		if(!PubMethod.isEmpty(parcelList)) {
			ParParcelinfo parParcelinfo = null;
			for(Long id : parcelList) {
				parParcelinfo = parcelInfoService.getParcelInfoById(id);
				if(!PubMethod.isEmpty(parParcelinfo.getCompId())) {
					clearCacheId = parParcelinfo.getCompId();
				} else {
					clearCacheId = compId;
				}
				if(status == 0) {
					parParcelinfo.setCompId(null);
				} else {
					parParcelinfo.setCompId(compId);
				}
				ehcacheService.put("parcelInfoCache", String.valueOf(id), parParcelinfo);
			}
			parParcelinfo = new ParParcelinfo();
			if(status == 0) {
				parParcelinfo.setCompId(null);
			} else {
				parParcelinfo.setCompId(compId);
			}
			parParcelinfo.setTakeTaskId(taskId);
			parParcelinfoMapper.updateCompIdByTakeTaskId(parParcelinfo);
		}
		cleanQueryInfo("takeParcelIdsCacheByMemberId", String.valueOf(clearCacheId));
		//ehcacheService.remove("takeParcelIdsCacheByMemberId", String.valueOf(clearCacheId));
	}
	
	//加缓存
	private void putCache(String cacheName, String key, Object value) throws ServiceException {
		if(CACHE_OPEN) {
			
			ehcacheService.put(cacheName, key, value);
		}
	}
	
	//读缓存
	private <T> T getCache(String cacheName, String key, Class<T> className) throws ServiceException {
		if(CACHE_OPEN) {
			return ehcacheService.get(cacheName, key, className);
		}
		return null;
	}
	
	//清缓存
	private void removeCache(String cacheName, String key) throws ServiceException {
		if(CACHE_OPEN) {
			ehcacheService.remove(cacheName, key);
		}
	}
	
	private void cleanQueryInfo(String cacheName, String key) throws ServiceException {
		String queryresult = getCache(cacheName, key, String.class);
		List<String> querylist = null;
		if(!PubMethod.isEmpty(queryresult)) {
			querylist = JSON.parseArray(queryresult, String.class);
		}
		if(!PubMethod.isEmpty(querylist)) {
			ehcacheService.remove(cacheName, querylist);
		}
	}

	@Override
	public BaseDao getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String,Object>> takeTaskRecordList(Long memberId, String date) {
        List <Map<String,Object>> list = parTaskInfoMapper.takeTaskRecordList(memberId, date);
		return list;
	}

	@Override
	public Map<String, Object> takeTaskDetail(Long taskId) {
		List<Map<String,Object>>list = parTaskInfoMapper.queryParcelsByTaskId(taskId);
		Map<String,Object>map = parTaskInfoMapper.queryTakeTaskById(taskId);
		map.put("parcelData", list);
		if("3".equals(map.get("taskStatus").toString())){
			map.put("reason", "客户取消");
		}else{
			map.put("reason", "");
		}
		return map;
	}

	/**
	 * @Method: createTaskIgomo 
	 * @param fromCompId
	 * @param coopNetId
	 * @param appointDesc
	 * @param actorMemberId
	 * @param contactName
	 * @param contactMobile
	 * @param contactAddress
	 * @param contactAddrLongitude 
	 * @see net.okdi.api.service.TakeTaskService#createTaskIgomo(java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.math.BigDecimal) 
	*/
//	toMemberId  createUserId
	@Override
	public Map<String,Object> createTaskIgomo(Long fromCompId, Long coopNetId,
			String appointDesc, Long actorMemberId, String contactName,String contactMobile, String contactAddress,
			BigDecimal contactAddrLongitude,BigDecimal contactAddrLatitude,String actorPhone,String openId,Byte taskSource,
			Long memberId,String parcelStr,Byte taskFlag, Integer howFast, Byte parEstimateCount,Long assignNetId) {
		
		Map<String,Object> resultMap = new HashMap<>();
		net.okdi.apiV4.entity.ParTaskInfo taskInfo = new net.okdi.apiV4.entity.ParTaskInfo();
		taskInfo.setCustomerId(memberId);
		taskInfo.setCoopCompId(fromCompId);
		taskInfo.setCoopNetId(coopNetId);
		taskInfo.setAppointDesc(appointDesc);
		taskInfo.setActorMemberId(actorMemberId);
		taskInfo.setContactName(contactName);
		taskInfo.setContactMobile(contactMobile);
		taskInfo.setContactAddress(contactAddress);
		taskInfo.setContactAddrLatitude(contactAddrLatitude);
		taskInfo.setContactAddrLongitude(contactAddrLongitude);
		taskInfo.setParEstimateCount(parEstimateCount);
		long nextId = IdWorker.getIdWorker().nextId();
		resultMap.put("taskId", nextId);
		taskInfo.setUid(nextId);
		taskInfo.setCreateTime(new Date());
		taskInfo.setTaskType(new Byte("0")); //任务类型 0:取件 1:派件 2:自取件 3：销售等非快递
		taskInfo.setTaskStatus(new Byte("0"));
		taskInfo.setTaskIsEnd((byte) 0);
		taskInfo.setActorPhone(actorPhone);
		taskInfo.setThirdId(openId);
		taskInfo.setCreateUserId(memberId);
		taskInfo.setTaskSource(taskSource);
		taskInfo.setTaskFlag(taskFlag);//任务标志 0：正常(指定快递员),1：抢单 
		taskInfo.setPayStatus((short)0);//付款状态：初始化为0-未确定，10 现金支付       20 微信支付   21 微信支付中      22 微信支付完成财务并且财务系统同步完成    23 微信支付完成并且财务系统同步失败 
		//放入缓存,抢单和取消抢单时从缓存中获取订单状态
		ehcacheService.put("WechatCallExpress", nextId+"", 0);
		net.okdi.apiV4.entity.ParTaskDisposalRecord parTaskDisposalRecord = new net.okdi.apiV4.entity.ParTaskDisposalRecord();
		parTaskDisposalRecord.setCompId(fromCompId);
		Date date = new Date();
		parTaskDisposalRecord.setCreateTime(date);
		String disposalDesc="微信叫快递创建任务";
		parTaskDisposalRecord.setDisposalDesc(disposalDesc);
		parTaskDisposalRecord.setDisposalObject((byte) 0);
		parTaskDisposalRecord.setDisposalType((byte)0);
		parTaskDisposalRecord.setUid(IdWorker.getIdWorker().nextId());
		parTaskDisposalRecord.setMemberId(actorMemberId);
		parTaskDisposalRecord.setShowFlag((byte) 1);
		parTaskDisposalRecord.setTaskErrorFlag((byte) 1);
		parTaskDisposalRecord.setTaskId(nextId);
//		添加包裹任务记录表
		mongoTemplate.insert(parTaskDisposalRecord);
		mongoTemplate.insert(taskInfo, "parTaskInfo");
		logger.info("解析parcelStr串并添加包裹");
		this.addParcel(parcelStr,fromCompId,coopNetId,memberId,actorMemberId,nextId,appointDesc,contactName,contactMobile,contactAddress);
		try {
		if(1==taskFlag){//如果为抢单模式,查询附近快递员并 发送推送 
			
			if(PubMethod.isEmpty(contactAddrLongitude)||PubMethod.isEmpty(contactAddrLatitude)||PubMethod.isEmpty(howFast)){
				throw new ServiceException("微信抢单模式下,发件人的经纬度信息或  howFast 不能为空");
			}
			/*//1.查询附近快递员
			List<Map<String,Object>> nearMemberForWechat = this.courierService.queryNearMemberForWechat(contactAddrLongitude.doubleValue(), contactAddrLatitude.doubleValue(), 1, howFast,assignNetId, "1");
			//2.发推送
			int countMember = nearMemberForWechat.size();
			resultMap.put("countMember", countMember);
			if(countMember>0){
				for (int i = 0; i < nearMemberForWechat.size(); i++) {
					Map<String, Object> map = nearMemberForWechat.get(i);
					Long actorMemberId1 = (Long)map.get("memberId");
					String actorPhone1 = (String)map.get("memberMobile");
					noticeHttpClient.saveMsg(contactMobile, contactAddress, actorMemberId1, actorPhone1, nextId);
					mobMemberLoginService.taskPush(actorMemberId1, actorPhone1);
				}
				TakeTaskServiceImpl taskServiceImpl = new TakeTaskServiceImpl();
				taskServiceImpl.createJob(date, nextId);//启动定时任务
*/				
			
				Map<String,String> dataMap = new HashMap<>();
				dataMap.put("currMemberId", memberId+"");
				dataMap.put("contactMobile", contactMobile);
				dataMap.put("contactAddress", contactAddress);
				dataMap.put("taskId", nextId+"");
				dataMap.put("lng", contactAddrLongitude+"");
				dataMap.put("lat", contactAddrLatitude+"");
				dataMap.put("howFast", "5");
				dataMap.put("sortFlag", "1");
				dataMap.put("assignNetId", assignNetId == null ? "0" : assignNetId+"");
				dataMap.put("roleType", "1");
				dataMap.put("createTime", new Date().getTime()+"");
				dataMap.put("source", "2");
				String url = constPool.getOpencall()+"callcourier/cc";
				noticeHttpClient.Post(url, dataMap);
				int size=0;
//				if(!PubMethod.isEmpty(result)){
//					String courierListJson = JSON.parseObject(result).getString("data");
//					JSONArray jsonArr = JSON.parseArray(courierListJson);
//					size = jsonArr.size();
//				}
				resultMap.put("countMember", size);
		}else{//选择快递员下单
				//产品说这里的文案直接就是发件人的地址和电话拼起来，以后再变也不要怪我的文案不规范
				noticeHttpClient.saveMsg(contactMobile, contactAddress, actorMemberId, actorPhone, nextId);
				removeTaskIgomoCache(fromCompId, actorMemberId);
				mobMemberLoginService.taskPush(actorMemberId, actorPhone);
//				createJob(createTime, nextId, fromCompId, actorMemberId);
				resultMap.put("countMember", -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("添加消息失败");
		}
		return resultMap;
	}
	
	/**
	 * 微信取消抢单发快递
	 */
	@Override
	public void cancelWechatOrder(Long taskId) {
		//1.修改微信叫快递缓存中的订单状态为 2-->取消订单
		ehcacheService.put("WechatCallExpress", taskId+"", 3);
		//2.修改mongodb中的任务状态
		//mongoTemplate.upsert(Query.query(Criteria.where("uid").is(taskId)), new Update().set("taskStatus", 3), net.okdi.apiV4.entity.ParTaskInfo.class);
		this.receivePackageService.calledCourierToMemberWeiXin(taskId.toString(),"2");
		taskRemindService.removeById(taskId, null);
		logger.info("微信取消叫快递(不叫了),删除发送的附近快递员抢单中的记录taskId:"+taskId);
	}
	//解析包裹参数字符串数据,并添加包裹
	private void addParcel(String parcelStr,Long compId,Long netId,Long createUserId,Long actualTakeMember,Long taskId,String appointDesc,String contactName,String contactMobile, String contactAddress){
		try {
			String[] parcels = parcelStr.split("\\,");
			for (String parcel : parcels) {
				String[] parms = parcel.split("\\|");
				String addresseeName = parms[0];
				String addresseeMobile = parms[1];
				String addresseeAddress = parms[2];
				
				this.saveParcelInfo(compId, netId, addresseeName, addresseeMobile, addresseeAddress, createUserId, actualTakeMember,taskId,appointDesc,contactName,contactMobile,contactAddress);
			}
			logger.info("微信端发快递--保存包裹和包裹地址成功,包裹信息json参数parcelStr==>"+parcelStr);
		} catch (Exception e) {
			logger.info("json参数解析异常parcelStr==>"+parcelStr);
			e.printStackTrace();
		}
	}

	/**
	 * 添加一个包裹
	 */
	private void saveParcelInfo(Long compId, Long netId, String addresseeName, String addresseeMobile,
			String addresseeAddress,Long createUserId, Long actualTakeMember,Long taskId,String appointDesc,String contactName,String contactMobile, String contactAddress){

		net.okdi.apiV4.entity.ParParcelinfo parcelInfo = new net.okdi.apiV4.entity.ParParcelinfo();
		net.okdi.apiV4.entity.ParParceladdress parceladdress = new net.okdi.apiV4.entity.ParParceladdress();
		net.okdi.apiV4.entity.ParParcelconnection connect = new net.okdi.apiV4.entity.ParParcelconnection();
		Long id =  IdWorker.getIdWorker().nextId();
		parcelInfo.setUid(id);  //包裹id
		parceladdress.setUid(id);  //包裹地址表id

		parcelInfo.setCompId(compId);  //公司id
		parcelInfo.setNetId(netId);    //网络id
		parcelInfo.setTakeTaskId(taskId);
		parceladdress.setAddresseeName(addresseeName); //收件人姓名  
		parceladdress.setAddresseeMobile(addresseeMobile); //收件人手机号码
//		parceladdress.setAddresseeAddressId(addresseeAddressId);//收件人乡镇id
		parceladdress.setAddresseeAddress(addresseeAddress);//收件人详细地址
		parceladdress.setSendAddress(contactAddress);
		parceladdress.setSendName(contactName);
		parceladdress.setSendMobile(contactMobile);
		parcelInfo.setCreateUserId(createUserId); //创建人id
		parcelInfo.setParcelRemark(appointDesc);
		parcelInfo.setActualTakeMember(actualTakeMember);
		parcelInfo.setTackingStatus((short)0);//包裹当前状态 0:在途,未签收 1:已签收
		parcelInfo.setGoodsPaymentMethod((short) 1);
		parcelInfo.setFreightPaymentMethod((short)2);
		parcelInfo.setParcelStatus((short) 0);//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'
		parcelInfo.setParcelEndMark("0");//包裹结束标志  0：未结束 1：结束'
		parcelInfo.setSignResult((short) 0);
		parcelInfo.setMobilePhone(contactMobile);
		connect.setParId(parcelInfo.getUid());
		connect.setCompId(compId);
		connect.setNetId(netId);
		connect.setCreateTime(new Date());
		connect.setCosignFlag((short) 2);
		connect.setExpMemberSuccessFlag((short) 1);//'收派员取/派成功标志 0：失败1：成功',
		
		connect.setUid(IdWorker.getIdWorker().nextId());
		mongoTemplate.insert(connect);
		parcelInfo.setCreateTime(new Date()); //设置包裹创建时间
		mongoTemplate.insert(parcelInfo);
		mongoTemplate.insert(parceladdress);

	}
	
	@Override
	public void removeTaskIgomoCache(Long fromCompId, Long actorMemberId) {
		ehcacheService.removeAll("TakeTaskCacheRecordResultByMemberId");
		ehcacheService.removeAll("TakeTaskCacheQueryResult");
		ehcacheService.removeAll("TakeTaskCacheQueryList");
		ehcacheService.removeAll("TakeTaskCacheTaskRecordResult");
		cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(fromCompId));
		cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(actorMemberId));
		cleanQueryInfo("TakeTaskCacheQueryResult", String.valueOf(actorMemberId));
		ehcacheService.removeAll("TakeTaskCacheUnTakeCount");
		removeCache("TakeTaskCacheQueryResult", String.valueOf(fromCompId));
	}
	
	/**
	 * 微信叫快递抢单
	 */
	@Override
	public void wechatRob(Long memberId, String netName, String netId, String phone, String mname, Long taskId) {
		Query query = Query.query(Criteria.where("uid").is(taskId));
		net.okdi.apiV4.entity.ParTaskInfo task = null;
		synchronized (lock) {
			String taskStatus = ehcacheService.get("WechatCallExpress", taskId + "", String.class);
			task = mongoTemplate.findOne(query, net.okdi.apiV4.entity.ParTaskInfo.class);
			String taskFlag ="";
			if (PubMethod.isEmpty(taskStatus)) {
				if (task == null) {
					throw new ServiceException("任务不存在");
				}
				taskFlag = String.valueOf(task.getTaskFlag());
			}
			if ("0".equals(taskFlag)) {
				throw new ServiceException("不是抢单业务");
			}
			if ("1".equals(taskStatus)) {
				throw new ServiceException("订单已被抢");
			}
			if ("2".equals(taskStatus)) {
				throw new ServiceException("订单已结束");
			}
			if("3".equals(taskStatus)){
				throw new ServiceException("订单已取消");
			}
			if("10".equals(taskStatus)){
				throw new ServiceException("订单已取消");
			}
			ehcacheService.put("WechatCallExpress", taskId + "", 1);
		}
		
	/*	Update update = new Update();
		update.set("taskFlag", 3);
		update.set("coopNetId", netId);
		update.set("netName", netName);
		update.set("actorPhone", phone);
		update.set("actorMemberId", memberId);
		mongoTemplate.updateFirst(query, update, ParTaskInfo.class);
	*/	
		this.receivePackageService.memberConfirmOrderWeiXin(taskId.toString(), memberId.toString(), phone,(short)1);
		
		taskRemindService.removeById(taskId, memberId);
		logger.info("有人抢单,删除其他快递员提醒中的记录taskId:"+taskId+"memberId:"+memberId);
		JSONObject json = new JSONObject();
		json.put("mname", mname);
		json.put("phone", phone);
		json.put("netId", netId);
		json.put("netName", netName);
		Long createUserId = task.getCreateUserId();
		String contactMobile = task.getContactMobile();
		logger.info("抢单， 给叫快递的人发送推送::" + createUserId + ":" + contactMobile);
		mobMemberLoginService.robPush(createUserId, contactMobile, json.toJSONString());
	}
	
	@Override
	public void updateTaskStatus(Long taskId, int status) {
		Query query = Query.query(Criteria.where("uid").is(taskId));
		net.okdi.apiV4.entity.ParTaskInfo par=mongoTemplate.findOne(query, net.okdi.apiV4.entity.ParTaskInfo.class);
		if(!PubMethod.isEmpty(par)){
			logger.info("updateTaskStatus更新taskId:"+taskId+",status:"+status);
			query.addCriteria(new Criteria("mobilePhone").is(par.getMobilePhone()));
			Update update = new Update();
			update.set("taskStatus", (byte)status);
			mongoTemplate.updateFirst(query, update, "parTaskInfo");
		}else{
			logger.info("updateTaskStatus更新失败taskId:"+taskId+",status:"+status);
		}
		
	}
	
	public void createJob(Date createTime, Long taskId) {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("taskId", taskId);
		
		Calendar cale = Calendar.getInstance();
		cale.setTime(createTime);
		cale.set(Calendar.MINUTE, cale.get(Calendar.MINUTE) + 3);
		Date fireTime = cale.getTime();
		logger.info("定时任务触发时间:" + fireTime);
		jobExecutor.executeOnlyOnce(TakeTaskJob.class, fireTime, dataMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> queryTakeById(Long taskId) {
		Query query = Query.query(Criteria.where("uid").is(taskId));
		Map map = mongoTemplate.findOne(query, Map.class, "parTaskInfo");
		map.put("actorMemberId", map.get("actorMemberId"));
		map.put("id", map.get("uid"));
		map.put("taskId", map.get("uid"));
		map.put("thirdid", map.get("thirdId"));
		map.remove("_id");
		map.remove("thirdId");
		return map;
	}

	//运营平台查询取件列表
	public Page queryTask(String taskSource, String netName,String taskStatus, String contactAddress, String contactMobile,
			String actorPhone, String startTime, String endTime, Integer currentPage, Integer pageSize) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		if(!PubMethod.isEmpty(taskSource)) {
			map.put("taskSource", taskSource);
		} else {
			map.put("taskSource", null);
		}
		if(!PubMethod.isEmpty(netName)) {
			map.put("netName", netName);
		} else {
			map.put("netName", null);
		}
		if(!PubMethod.isEmpty(taskStatus)) {
			map.put("taskStatus", taskStatus);
		} else {
			map.put("taskStatus", null);
		}
		if(!PubMethod.isEmpty(contactAddress)) {
			map.put("contactAddress", contactAddress);
		} else {
			map.put("contactAddress", null);
		}
		if(!PubMethod.isEmpty(contactMobile)) {
			map.put("contactMobile", contactMobile);
		} else {
			map.put("contactMobile", null);
		}
		if(!PubMethod.isEmpty(actorPhone)) {
			map.put("actorPhone", actorPhone);
		} else {
			map.put("actorPhone", null);
		}
		if(startTime != null && !"".equals(startTime)) {
			map.put("startTime", DateUtil.getZeroTimeOfDay(startTime));
		} else {
			map.put("startTime", null);
		}
		if(endTime != null && !"".equals(endTime)) {
			map.put("endTime", DateUtil.getEndTimeOfDay(endTime));
		} else {
			map.put("endTime", null);
		}
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		map.put("page", page);
		//计算页数
		int pageNum = 0;
		//查询结果
		List<Map<String,Object>> resultlist = null;
		
		int totalCount = 0;
		
			//统计页数，返回结果值
			totalCount = parTaskDisposalRecordMapper.queryTaskCount(map);
			
			resultlist = parTaskDisposalRecordMapper.queryTaskByCriteria(map);
			
		if(totalCount % page.getPageSize() > 0) {
			pageNum = (totalCount/page.getPageSize()) + 1;
		}
		page.setItems(resultlist);
		page.setPageCount(pageNum);
		page.setTotal(totalCount);
		return page;
	}
	
	@Override
	public BasNetInfo getNetInfoByNetId(Long netId) {
		BasNetInfo net = basNetInfoMapperV1.findById(netId);
		return net;
	}



	/**
	 * 获取指定时间的那天 00:00:00.000 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayBegin(final Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 0);
	        c.set(Calendar.MINUTE, 0);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MILLISECOND, 0);
	        return c.getTime();
	}
	/**
	 * 获取指定时间的那天 23:59:59.999 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayEnd(final Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 23);
	        c.set(Calendar.MINUTE, 59);
	        c.set(Calendar.SECOND, 59);
	        c.set(Calendar.MILLISECOND, 999);
	        return c.getTime();
	}
}


