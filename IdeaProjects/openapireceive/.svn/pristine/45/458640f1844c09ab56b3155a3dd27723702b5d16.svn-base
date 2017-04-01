package net.okdi.apiV4.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV4.dao.AssignPackageMapper;
import net.okdi.apiV4.entity.MemberInfo;
import net.okdi.apiV4.entity.ParParceladdress;
import net.okdi.apiV4.entity.ParParcelconnection;
import net.okdi.apiV4.entity.ParParcelinfo;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.apiV4.vo.VO_SiteMemberInfo;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;
@Service
public class AssignPackageServiceImpl implements AssignPackageService {

	@Autowired
	private AssignPackageMapper assignPackageMapper;
	
	@Autowired
	private EhcacheService ehcacheService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Value("${headImgPath}")
	private String headImgPath;
	
	public static final Log logger = LogFactory.getLog(AssignPackageServiceImpl.class);
	/**
	 * 通过站点Id查询站点下的其他收派员列表
	 */
	@Override
	public Map<String, Object> queryEmployeeByCompId(Long compId,String memberid) {
	
		Map<String, Object> map = new HashMap<String, Object>();

//		List<MemberInfo> list = ehcacheService.get("siteMemberListCache", String.valueOf(compId),ArrayList.class);
		List<VO_SiteMemberInfo> resultList = new ArrayList<VO_SiteMemberInfo>();
		
//		if (!PubMethod.isEmpty(list)) {
//			String str=JSONObject.toJSONString(list);
//			JSONArray ja=JSONArray.parseArray(str);
//			for(int i=0;i<ja.size();i++){
//				JSONObject json = (JSONObject) ja.get(i);				
//				SiteMemberInfo sm=new SiteMemberInfo();
//				sm.setMemberId(json.getLong("memberId"));
//				sm.setMemberName(json.getString("memberName"));
//				sm.setRoleId(json.getShort("roleId"));//'角色 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员',
//				resultList.add(sm);
//				map.put("countMember", ja.size());
//			}
//		}else{
		Map<String , Object>paramap=new HashMap<String, Object>();
		paramap.put("compId", compId);
		paramap.put("memberId", Long.parseLong(memberid));
		logger.info("x查询站点其他快递员的列表的参数："+paramap);
		List<MemberInfo> list = assignPackageMapper.queryOtherEmployeeByCompId(paramap);
			logger.info("缓存中的本站点员工数"+list.size());
//			ehcacheService.put("siteMemberListCache", String.valueOf(compId), list);
			for(MemberInfo vom:list){
				VO_SiteMemberInfo sm=new VO_SiteMemberInfo();
				Long memberId = vom.getMemberId();
				sm.setMemberId(memberId);
				//处理姓名
				String name = "";
				if(PubMethod.isEmpty(vom.getMemberName())){
					if("0".equals(vom.getRoleId().toString())){
						name = "快递员"+"-"+vom.getMemberPhone();
					}else if("1".equals(vom.getRoleId().toString())){
						name = "站长"+"-"+vom.getMemberPhone();
					}else if("-1".equals(vom.getRoleId().toString())){
						name = "后勤"+"-"+vom.getMemberPhone();
					}else if("2".equals(vom.getRoleId().toString())){
						name = "店长"+"-"+vom.getMemberPhone();
					}else if("3".equals(vom.getRoleId().toString())){
						name = "店员"+"-"+vom.getMemberPhone();
					}
				}else {
					name = vom.getMemberName();
				}
				sm.setMemberName(name);
				sm.setRoleId(vom.getRoleId());//'角色 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员',
				sm.setHeadImgPath(headImgPath+String.valueOf(memberId)+".jpg");
				sm.setMemberPhone(vom.getMemberPhone());
				resultList.add(sm);
			}
			map.put("memberList",resultList);//memberList
			map.put("countMember", resultList.size());
//		}
		return map;
	}
	/**
	 * 查询站点下所有收派员
	 */
	@Override
	public List<MemberInfo> queryAllParParcel(Long compId) {
		List<MemberInfo> list = assignPackageMapper.queryEmployeeByCompId(compId);
		logger.info("缓存中的本站点员工数"+list.size());
		return list;
	}
	
	/**
	 * 通过  compId  查询站点下所有收派员2017-1-15  新的
	 */
	@Override
	public List<MemberInfo> queryMember(Long compId) {
		List<MemberInfo> list = assignPackageMapper.queryMemberByCompId(compId);
		
		logger.info("缓存中的本站点员工数"+list.size());
		return list;
	}
	/**
	 * 添加一个包裹
	 */
	public Map<String, Object> saveParcelInfo(String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile, Long addresseeAddressId,String cityName,
			String addresseeAddress,BigDecimal freight,BigDecimal codAmount,Long createUserId, Long actualSendMember){

		if(!PubMethod.isEmpty(expWaybillNum) && expWaybillNum.length() > 32){
			expWaybillNum = expWaybillNum.substring(0,32);
		}

		Map<String, Object> map = new HashMap<String, Object>(); 
		Long id =  IdWorker.getIdWorker().nextId();//生成包裹Id
		if(!PubMethod.isEmpty(expWaybillNum) && !PubMethod.isEmpty(compId)){
			//查找是否存在包裹
			Long parId = this.getParcelId(expWaybillNum,compId);
			if (!PubMethod.isEmpty(parId)) {
				id=-1L;//-1代表已经存在这个运单号的包裹
			}else{
				this.addParcelInfo(expWaybillNum, compId, netId, addresseeName, addresseeMobile, addresseeAddressId, cityName, addresseeAddress, freight, codAmount, createUserId, actualSendMember,id);
			}
		}else{
			this.addParcelInfo(expWaybillNum, compId, netId, addresseeName, addresseeMobile, addresseeAddressId, cityName, addresseeAddress, freight, codAmount, createUserId, actualSendMember,id);
		}
		

/*				if(PubMethod.isEmpty(sendMemberId)){
					//进行包裹信息和包裹地址信息的插入操作
					map.put("isSameSender", "0");//0代表第一次添加
				}else{//对比实际派件人id和actualSendMember,是否是同一个收派员
					if(actualSendMember.equals(sendMemberId)){
						//进行包裹信息和包裹地址信息的插入操作--------当同一个包裹被多次分派给同一个人会产生重复收据
						map.put("isSameSender", "1");//1代表当天同一个收件人在本站点有多个包裹,或者把同一个包裹两次分派给同一个收派员
					}else{
						//进行包裹信息和包裹地址信息的插入操作--------当同一个包裹被多次分派给不同的收派员会产生重复收据
						map.put("isSameSender", "2");//2代表当天把同一个收件人的包裹分派给了不同的收派员
						}
				}
			}else{
			进行包裹信息和包裹地址信息的更新操作  
				Criteria criteria = where("uid").is(id);
				Query query = Query.query(criteria);
				Update update=new Update();
				update.set("expWaybillNum", expWaybillNum);
				update.set("compId", compId);
				update.set("netId", netId);
				update.set("freight", freight);
				update.set("codAmount", codAmount);
				update.set("actualSendMember", actualSendMember);
				update.set("tackingStatus", (short)0);
				update.set("parcelStatus", (short) 10);
				update.set("parcelEndMark", 0);
				mongoTemplate.upsert(query, update, ParParcelinfo.class);
				Criteria criteria1 = where("uid").is(id);
				Query query1 = Query.query(criteria1);
				Update update1=new Update();
				update1.set("addresseeName", addresseeName);
				update1.set("addresseeMobile", addresseeMobile);
				mongoTemplate.upsert(query1, update1, ParParceladdress.class);
				map.put("isSameSender", "3");//3代把同一个快递包裹分派了两次
			ehcacheService.remove("parcelIdsCacheByMemberId",PubMethod.isEmpty(sendMemberId)?"":String.valueOf(sendMemberId));//删除快递员查询待派包裹缓存
			}*/
/*		//放置包裹信息和包通过运单号和网络Id 查询包裹信息裹地址信息到缓存
		this.ehcacheService.remove("parcelInfoCache", String.valueOf(parcelInfo.getId()));
		this.ehcacheService.remove("parcelAddressCache", String.valueOf(parcelInfo.getId()));
		this.ehcacheService.remove("parcelIdCacheByExpWayBillNumAndNetIdNew",String.valueOf(parcelInfo.getExpWaybillNum()+parcelInfo.getNetId()));
		this.ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(createUserId));
		this.ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(actualSendMember));
		this.ehcacheService.remove("takeIdsCacheByMemberId", String.valueOf(createUserId));
		this.ehcacheService.remove("queryAlreadySignList", String.valueOf(actualSendMember));
		this.ehcacheService.remove("queryAlreadySignList", String.valueOf(createUserId));
		this.ehcacheService.remove("sendTaskCache", String.valueOf(createUserId));
		cleanQueryInfo("takeParcelIdsCacheByMemberId", String.valueOf(compId));
		ehcacheService.removeAll("expressserviceCache");//takeParcelIdsCacheByMemberId
*/		
		map.put("parcelId", id);
		return map;
	}
	
	public void addParcelInfo(String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile, Long addresseeAddressId,String cityName,
			String addresseeAddress,BigDecimal freight,BigDecimal codAmount,Long createUserId, Long actualSendMember,Long id){
		ParParcelinfo parcelInfo = new ParParcelinfo();
		ParParceladdress parceladdress = new ParParceladdress();
		ParParcelconnection connect = new ParParcelconnection();

		
		parcelInfo.setUid(id);  //包裹id
		parceladdress.setUid(id);  //包裹地址表id

		parcelInfo.setExpWaybillNum(expWaybillNum); //运单号
		parcelInfo.setCompId(compId);  //公司id
		parcelInfo.setNetId(netId);    //网络id
		
		parceladdress.setAddresseeName(addresseeName); //收件人姓名  
		parceladdress.setAddresseeMobile(addresseeMobile); //收件人手机号码   --片键
		parceladdress.setAddresseeAddressId(addresseeAddressId);//收件人乡镇id
		String address=cityName+" "+addresseeAddress;
		parceladdress.setAddresseeAddress(address);//收件人详细地址
		parcelInfo.setFreight(freight); //包裹应收运费
		parcelInfo.setCodAmount(codAmount);//代收货款金额
		
		parcelInfo.setCreateUserId(createUserId); //创建人id
		
		parcelInfo.setActualSendMember(actualSendMember); //派件人id
		parcelInfo.setTackingStatus((short)0);//包裹当前状态 0:在途,未签收 1:已签收
		parcelInfo.setGoodsPaymentMethod((short) 1);
		parcelInfo.setFreightPaymentMethod((short)2);
		parcelInfo.setParcelStatus((short) 10);//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'
		parcelInfo.setParcelEndMark("0");//包裹结束标志  0：未结束 1：结束'
		parcelInfo.setSignResult((short) 0);
		parcelInfo.setIsSendMsg((short) 0);//是否发过短信，0否1是
		parcelInfo.setReplyStatus((short)0);//0未回复
		parcelInfo.setMobilePhone(addresseeMobile);//片键
		connect.setParId(parcelInfo.getUid());
		connect.setCompId(compId);
		connect.setNetId(netId);
		connect.setCreateTime(new Date());
		connect.setCosignFlag((short) 2);
		connect.setExpMemberSuccessFlag((short) 1);//'收派员取/派成功标志 0：失败1：成功',
		connect.setMobilePhone(addresseeMobile);//片键

		addParcelConnection(connect);  //插入 ParcelConnection表(收派过程监控表) 
		parcelInfo.setCreateTime(new Date()); //设置包裹创建时间
		mongoTemplate.insert(parcelInfo);
		mongoTemplate.insert(parceladdress);
	}
	
	public void addParcelConnection(ParParcelconnection parParcelconnection) throws ServiceException {
		if(PubMethod.isEmpty(parParcelconnection)) {
			throw new ServiceException("openapi.ParcelInfoServiceImpl.addParcelConnection.001", "收派记录添加失败");
		}
		parParcelconnection.setUid(IdWorker.getIdWorker().nextId());
		mongoTemplate.insert(parParcelconnection);
	}
	
 	public Long getParcelId(String expWayBillNum, Long compId) {
		Long parcelId = null;//this.ehcacheService.get("parcelIdCacheByExpWayBillNumAndNetIdNew", String.valueOf(expWayBillNum+netId), Long.class);
		if(PubMethod.isEmpty(parcelId)){
			Criteria criteria=where("expWaybillNum").is(expWayBillNum).and("compId").is(compId);
			Query query=Query.query(criteria);
			ParParcelinfo parParcelinfo = mongoTemplate.findOne(query, ParParcelinfo.class);
			if(PubMethod.isEmpty(parParcelinfo)){
				return null;
			}
				parcelId = parParcelinfo.getUid();
//			this.ehcacheService.put("parcelIdCacheByExpWayBillNumAndNetIdNew", String.valueOf(expWayBillNum+netId), parcelId);
		}
		return parcelId;
	}
	@Override
	public void createParcelInfo(Long sendTaskId, Long createUserId,
			Long actualSendMember, Short sendSmsType, Short sendSmsStatus,
			Short callBackStatus,String addresseeMobile,String sendMobile,String parcelNum,Short replyStatus,Short isNum) {
		ParTaskInfo parTaskInfo = new ParTaskInfo();
		Date now=new Date();
		parTaskInfo.setUid(sendTaskId);
		parTaskInfo.setTaskType((byte)1);
		parTaskInfo.setCoopCompId(null);
		parTaskInfo.setCoopNetId(null);
		parTaskInfo.setParEstimateCount((byte)1);
		parTaskInfo.setTaskSource((byte)-1);
		parTaskInfo.setTaskStatus((byte)11); //TODO
		parTaskInfo.setTaskIsEnd((byte)0); //TODO
		parTaskInfo.setActorMemberId(actualSendMember);
		parTaskInfo.setActorPhone(sendMobile);
		parTaskInfo.setContactName("");
		parTaskInfo.setContactMobile(addresseeMobile);
		parTaskInfo.setContactAddressId(null);
		parTaskInfo.setContactAddress(null);
		parTaskInfo.setCreateTime(now);
		parTaskInfo.setCreateUserId(createUserId);
		parTaskInfo.setTaskFlag((byte)0);
		parTaskInfo.setModifyTime(now);
		parTaskInfo.setMobilePhone(addresseeMobile);//片键
		ParParcelinfo par=new ParParcelinfo();
		par.setUid(IdWorker.getIdWorker().nextId());
		par.setSendTaskId(sendTaskId);
		par.setCreateUserId(createUserId);
		par.setActualSendMember(actualSendMember);
		par.setSendSmsType(sendSmsType);
		par.setSendSmsStatus(sendSmsStatus);
		par.setCallBackStatus(callBackStatus);
		par.setCreateTime(now);
		if (sendSmsType == 1 && isNum == 1) {
			par.setParcelNum(parcelNum);
		} else {
			par.setParcelNum("");
		}
		par.setReplyStatus(replyStatus);
		par.setParcelEndMark("0");
		par.setIsSendMsg((short)1);
		par.setMobilePhone(addresseeMobile);//片键
		ParParceladdress parParceladdress=new ParParceladdress();
		parParceladdress.setUid(par.getUid());
		parParceladdress.setAddresseeMobile(addresseeMobile);//片键
		ParParcelconnection arParcelconnection=new ParParcelconnection();
		arParcelconnection.setTaskId(sendTaskId);
		arParcelconnection.setParId(par.getUid());
		arParcelconnection.setUid(IdWorker.getIdWorker().nextId());
		arParcelconnection.setMobilePhone(addresseeMobile);//片键
		mongoTemplate.insert(par);
		mongoTemplate.insert(parTaskInfo);
		mongoTemplate.insert(parParceladdress);
		mongoTemplate.insert(arParcelconnection);
	}
	@Override
	public void updateParcelInfo(Long sendTaskId, Short callBackStatus) {
		ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("sendTaskId").is(sendTaskId)), ParParcelinfo.class);
		if(!PubMethod.isEmpty(parInfo)){
			Query query=new Query();
			query.addCriteria(new Criteria("sendTaskId").is(sendTaskId).and("uid").is(parInfo.getUid()).and("mobilePhone").is(parInfo.getMobilePhone()));
			Update update=new Update();
			update.set("callBackStatus", callBackStatus);
			mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
		}
//		query.addCriteria(Criteria.where("uid").is(parInfo.getUid()));
//		query.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));//片键条件
	}
	@Override
	public void updateParcelReply(Long sendTaskId, Short replyStatus) {
		ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("sendTaskId").is(sendTaskId)), ParParcelinfo.class);
		if (!PubMethod.isEmpty(parInfo)) {
			Query query = new Query();
			query.addCriteria(new Criteria("sendTaskId").is(sendTaskId).and("uid").is(parInfo.getUid()).and("mobilePhone").is(parInfo.getMobilePhone()));
//			query.addCriteria(Criteria.where("uid").is(parInfo.getUid()));
//			query.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));// 片键条件
			Update update = new Update();
			update.set("replyStatus", replyStatus);
			mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
		}
	}
	@Override
	public Map<String,Object> parcelIsExist(String addresseeMobile,Long compId){
		//通过收件人手机号查询实际派件人id
		Map<String, Object> map = new HashMap<String, Object>(); 
		Criteria criteria2=where("addresseeMobile").is(addresseeMobile);
		Query query2=Query.query(criteria2);
		List<ParParceladdress> list = mongoTemplate.find(query2, ParParceladdress.class);
		boolean mobileFlag=false;
		if(list.size()>0){
			a:for (ParParceladdress parParceladdress : list) {
				Long addressId = parParceladdress.getUid();
				
				Criteria criteria3=where("uid").is(addressId).and("compId").is(compId);
				Query query3=Query.query(criteria3);
				List<ParParcelinfo> list2 = mongoTemplate.find(query3, ParParcelinfo.class);
				for (ParParcelinfo parParcelinfo : list2) {
					Date createTime = parParcelinfo.getCreateTime();
					if(createTime.getTime()>=dayBegin(new Date()).getTime()&&createTime.getTime()<=dayEnd(new Date()).getTime()){
						mobileFlag=true;
						break a;
					}
				  }
				}
			}
		map.put("mobileFlag", mobileFlag);
		return map;
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
	/**
	 * 删除派件任务
	 */
	@Override
	public void deleteSendTaskByTaskId(Long sendTaskId) {
		ParTaskInfo taskInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(sendTaskId)), ParTaskInfo.class);
		ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("sendTaskId").is(sendTaskId)), ParParcelinfo.class);
		Update update  = new Update();
		update.set("taskType", null);
		update.set("coopCompId", null);
		update.set("coopNetId", null);
		update.set("parEstimateCount", null);
		update.set("parEstimateWeight", null);
		update.set("taskSource", null);
		update.set("taskStatus", Byte.parseByte("88"));//88为删除的数据
		update.set("taskIsEnd", null);
		update.set("taskEndTime", null);
		update.set("actorMemberId", null);
		update.set("actorPhone", null);
		update.set("contactName", null);
		update.set("contactMobile", null);
		update.set("contactTel", null);
		update.set("contactAddressId", null);
		update.set("contactAddress", null);
		update.set("customerId", null);
		update.set("contactCasUserId", null);
		update.set("contactCompId", null);
		update.set("createUserId", null);
		update.set("taskFlag", null);
		update.set("modifyTime", new Date());
		update.set("orderNum", null);
		update.set("payStatus", null);
		update.set("thirdId", null);
		update.set("payNum", null);
		if (!PubMethod.isEmpty(taskInfo)) {
			Query queryTask = new Query();
			queryTask.addCriteria(new Criteria("uid").is(sendTaskId).and("mobilePhone").is(taskInfo.getMobilePhone()));
//			queryTask.addCriteria(Criteria.where("mobilePhone").is(
//					taskInfo.getMobilePhone()));// 片键条件
			this.mongoTemplate.updateFirst(queryTask, update, ParTaskInfo.class);
		}
		//删除包裹
		Update update2  = new Update();
		update2.set("takeTaskId", null);
		update2.set("sendTaskId", null);
		update2.set("actualTakeMember", null);
		update2.set("actualSendMember", null);
		update2.set("exceptionTime", null);
		update2.set("pickupTime", null);
		update2.set("parcelStatus", (short)88);//88为删除数据
		if(!PubMethod.isEmpty(parInfo)){
		Query queryPar = new Query();
		queryPar.addCriteria(new Criteria("uid").is(parInfo.getUid()).and("sendTaskId").is(sendTaskId).and("mobilePhone").is(parInfo.getMobilePhone()));
//		queryPar.addCriteria(Criteria.where("sendTaskId").is(sendTaskId));
//		queryPar.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));//片键条件
		this.mongoTemplate.updateFirst(queryPar, update2, ParParcelinfo.class);
		}
	}
	
	

}

