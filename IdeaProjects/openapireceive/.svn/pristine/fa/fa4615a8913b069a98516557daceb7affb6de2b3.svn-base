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
import net.okdi.api.entity.BasNetInfo;
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
import net.okdi.apiV4.entity.CooperationExpCompanyAuth;
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
import net.okdi.apiV4.service.ReceiveOrderFromWebsiteService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.apiV4.service.TaskRemindService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.common.page.Page;
import net.okdi.core.common.page.PageUtil;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.sms.OpenPlatformHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.outersrv.service.CooperationCompanyService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ReceiveOrderFromWebsiteServiceImpl extends BaseServiceImpl implements
		ReceiveOrderFromWebsiteService {

	private static Logger logger = Logger
			.getLogger(ReceiveOrderFromWebsiteServiceImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private OpenPlatformHttpClient openPlatformHttpClient;
	
	@Autowired
	private ReceivePackageService receivePackageService;
	@Autowired
	private RedisService redisService;

	@Autowired
	private BasEmployeeAuditMapperV4 basEmployeeAuditMapper;
	
//	@Autowired @Qualifier("cooperationCompanyServiceImpl")
//	private CooperationCompanyService cooperationCompanyService;
	
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

	//订单中调用国通查询接口查询电商件的包裹
	@Override
	public Map<String, Object> queryTakePackListFromWebsite(String userCode, String orgCode,String memberId,String netId) {
		//调用运营平台的接口,查询电商包裹的信息
		Map<String,String> param=new HashMap<>();
		param.put("userCode", userCode);
		param.put("orgCode", orgCode);
		String result = openPlatformHttpClient.queryFromWebsite(userCode,orgCode);
		logger.info("国通返回的包裹信息的包裹信息111111111111:"+result);
		//将返回的json串进行解析
		JSONObject jsonObject=JSONObject.parseObject(result);
		//返回给前台的展示的数据的集合
		List<Map<String, Object>>listmap=new ArrayList<Map<String,Object>>();
		//拿到包裹的集合
		if (!PubMethod.isEmpty(jsonObject)) {
			List<Map<String,Object>> list = (List<Map<String, Object>>)jsonObject.get("result");
			for (Map<String, Object> map : list) {
				Map<String, Object>parcelmap=new HashMap<String, Object>();
				logger.info("国通返回的包裹信息的包裹信息222222222222:"+map);
				//循环遍历创建包裹地址表和信息表
				Map<String, Object> map2 = this.createparcel(map,parcelmap,"2",memberId, netId);
				logger.info("返回给前段页面的包裹信息33333333333:"+map2);
				//将每个包裹返回的信息放到集合中
				listmap.add(map2);
			}
		}
		Map<String, Object>hh=new HashMap<>();
		hh.put("data", listmap);
		hh.put("packNum", listmap.size());
		hh.put("success", true);
		return hh;
	}
	//创建包裹地址表和信息表
	public Map<String, Object> createparcel(Map<String, Object> map,Map<String, Object> parcelmap,String parFlag,String memberId,String netId){
		/***start**创建包裹地址表*****************************************/
		//根据以下信息创建一个包裹
		ParParceladdress parceladdress=new ParParceladdress();
		//对接收人的信息进行判断
		Map<String, Object> receiver =null;
		if (!PubMethod.isEmpty(map.get("receiver"))) {
			receiver = (Map<String, Object>) map.get("receiver");//接收人信息
			//设置包裹的收件人的信息
			String address = receiver.get("address")+"";
			String city = receiver.get("city")+"";
			String prov = receiver.get("prov")+"";
			parceladdress.setAddresseeCity(city);
			parceladdress.setAddresseeAddress(address);
			parceladdress.setAddresseeProv(prov);
			parceladdress.setAddresseeName(receiver.get("userName")+"");//接收人的姓名
			parceladdress.setAddresseeMobile(receiver.get("phone")+"");//收件人的手机号---片键
			parceladdress.setAddresseePhone(receiver.get("phone")+"");//收件人的手机
			parceladdress.setAddressOrgCode(receiver.get("orgCode")+"");//收件人所在地的站点编码（必填）
			parceladdress.setAddressUserCode(receiver.get("userCode")+"");//收件人所在地的快递员编码（必填）
			
		}
		//对发送人的信息进行判断
		Map<String, Object> sender = null;//寄件人的信息
		String fromwhere="";//寄件人地址
		if (!PubMethod.isEmpty(map.get("sender"))) {
			sender = (Map<String, Object>) map.get("sender");//寄件人信息
			String address = sender.get("address")+"";
			String city = sender.get("city")+"";
			String prov = sender.get("prov")+"";
			fromwhere=prov+city+address;//返回给前端的地址
			parceladdress.setSendAddress(address);//寄件人的地址
			parceladdress.setSendCity(city);
			parceladdress.setSendProv(prov);
			parceladdress.setSendName(sender.get("userName")+"");//寄件人的姓名
			parceladdress.setSendOrgCode(sender.get("orgCode")+"");//寄件人所在地的站点编码（必填）
			parceladdress.setSendUserCode(sender.get("userCode")+"");//寄件人所在地的快递员编码（必填）
			parceladdress.setSendMobile(sender.get("contactPhone")+"");//寄件联系人姓名
			parceladdress.setSendName(sender.get("contactUserName")+"");//寄件联系人电话
		}
		//其他信息
		String postFee = String.valueOf(map.get("postFee"));
		String weight = String.valueOf(map.get("weight"));
		String postType = String.valueOf(map.get("postType"));
		String receiveCode = String.valueOf(map.get("receiveCode"));//揽收码
		String packageTargetAction = String.valueOf(map.get("packageTargetAction"));
		String receiveTaskUserCode = String.valueOf(map.get("receiveTaskUserCode"));
		String markRemark = String.valueOf(map.get("markRemark"));
		String taskStatus = String.valueOf(map.get("taskStatus"));
		String paintMarker = String.valueOf(map.get("paintMarker"));
		String billCode = String.valueOf(map.get("billCode"));
		String controller = String.valueOf(map.get("controller"));
		String tid = String.valueOf(map.get("tid"));
		String arriveCity = String.valueOf(map.get("arriveCity"));
		String originPlatform = String.valueOf(map.get("originPlatform"));
		//往包裹里插入国通返回的信息
		Long uid = IdWorker.getIdWorker().nextId();
		parceladdress. setUid(uid);//设置uid
		parceladdress.setPacelWeight(weight);//包裹重量
		parceladdress.setCode(receiveCode);//揽收码
		parceladdress.setControllerTaskFlag((short)0);//待取
		parceladdress.setReceiveTaskUserCode(receiveTaskUserCode);//揽收任务执行人编码
		//判断包裹是否被标记
		parceladdress.setPackageflag((short)0);//包裹未被标记
		if (!PubMethod.isEmpty(markRemark)) {
			parceladdress.addContent(markRemark, controller);//包裹标记
			parceladdress.setPackageflag((short)1);//包裹被标记
		}
		//包裹运单号
		parceladdress.setExpWaybillNum(billCode);//运单号
		parceladdress.setMarker(paintMarker);//大头笔
		parceladdress.setControllerName(controller);//当前控制人的名称
		parceladdress.setTid(tid);//快递公司订单ID(国通)
		parceladdress.setOriginPlatform(originPlatform);//电商平台
		parceladdress.setBourn(arriveCity);//目的地
		parceladdress.setCreateTime(new Date());
		parceladdress.setParFlag(parFlag);//包裹标识
		parceladdress.setReceiveTaskUserId(Long.parseLong(memberId));//任务执行人为当前快递员
		parceladdress.setNetId(Long.parseLong(netId));
		parceladdress.setControllerTaskFlag((short)0);//未取
		this.mongoTemplate.insert(parceladdress);
		/***end**创建包裹地址表*****************************************/
		/***start**创建包裹信息表*****************************************/
		ParParcelinfo parcelinfo=new ParParcelinfo();
		parcelinfo.setUid(uid);//包裹id
		parcelinfo.setExpWaybillNum(billCode);//运单号
		parcelinfo.setTackingStatus((short)0);//未签收
		parcelinfo.setCreateTime(new Date());
		parcelinfo.setMobilePhone(sender.get("contactPhone")+"");//=====片键
		parcelinfo.setParcelStatus((short)0);//待取
		parcelinfo.setCreateUserId(Long.parseLong(memberId));//包裹创建人
		this.mongoTemplate.insert(parcelinfo);
		/***end**创建包裹信息表*****************************************/
		/***start**进入订单首页返回给前段的数据*****************************************/
		parcelmap.put("sendMobile", sender.get("contactPhone")+"");//寄件人的手机号
		parcelmap.put("sendAddress", fromwhere);//寄件人的地址
		parcelmap.put("createTime", new Date().getTime());//包裹生成时间
		parcelmap.put("uid", uid);//包裹id
		return parcelmap;
	}

	//保存来自国通的包裹信息
	@Override
	public void saveParcelFromGT(String parcels,String memberId,String netId) {
		logger.info("保存来自国通的包裹信息111111111111:"+parcels);
		//将返回的json串进行解析
		JSONObject jsonObject=JSONObject.parseObject(parcels);
		//返回给前台的展示的数据的集合
		List<Map<String, Object>>listmap=new ArrayList<Map<String,Object>>();
		//拿到包裹的集合
		if (!PubMethod.isEmpty(jsonObject)) {
			List<Map<String,Object>> list = (List<Map<String, Object>>)jsonObject.get("result");
			if (!PubMethod.isEmpty(list)) {
				for (Map<String, Object> map : list) {
					Map<String, Object>parcelmap=new HashMap<String, Object>();
					logger.info("国通返回的包裹信息的包裹信息222222222222:"+map);
					//循环遍历创建包裹地址表和信息表
					String parflag="1";//"1"表示国通推送过来的信息
					Map<String, Object> map2 = this.createparcel(map,parcelmap,parflag,memberId,netId);//parflag="1"表示国通推送过来的信息
					logger.info("每一个包裹信息33333333333:"+map2);
					
				}
			}
		}
		//也许有人会说，我就这样，改不了了，爱咋地就咋地吧！这是破罐子破摔吗？说到底，这样的人其实就是一个对自己不负责的人。
		
	}

	//查询本地来自国通的订单
	@Override
	public Page QueryParcelFromGT(long memberId,long netId, int currentpage, int pagesize) {
		//从包裹地址表中查询对应的包裹
		Query query=new Query();
		query.addCriteria(Criteria.where("receiveTaskUserId").is(memberId));
		query.addCriteria(Criteria.where("controllerTaskFlag").is((short)0));//未取
		//说明是查询来自国通的包裹
		if (netId==6536l) {
			query.addCriteria(Criteria.where("parFlag").in("1"));//查询来自国通推送的包裹
		}else{
			//未完待续
			//query.addCriteria(Criteria.where("parFlag").in("3","4"));//查询来自国通的包裹
		}
		List<ParParceladdress> list = this.mongoTemplate.find(query, ParParceladdress.class);
		Long count = this.mongoTemplate.count(query, ParParceladdress.class);
		Page page = PageUtil.buildPage(currentpage, pagesize);
		page.setItems(list);
		page.setTotal(count.intValue());
		query = null;
		return page;
		
	}

	//订单(电商件和散件)中搜索当前包裹
	@Override
	public Page huntParcelGT(String mobile, long memberId,long netId, int currentPage,int pageSize,String flag) {
		logger.info("根据手机号订单(电商件和散件)中搜索当前包裹的参数:mobile:"+mobile+",memberId"+memberId+",netId:"+netId+",flag:"+flag);
		Query query=new Query();
		query.addCriteria(Criteria.where("taskIsEnd").is((short)0));//任务未结束
		query.addCriteria(Criteria.where("taskStatus").is((short)1));//任务已经分配
		query.addCriteria(Criteria.where("taskType").is((byte)0));//取件
		query.addCriteria(Criteria.where("contactMobile").regex(mobile));//模糊匹配手机号
		query.addCriteria(Criteria.where("coopNetId").is(netId));//netid
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));//memberId
		/*List<ParTaskInfo> list = this.mongoTemplate.find(query, ParTaskInfo.class);
		logger.info("查询订单表的返回的数据:"+list);
		Long count = this.mongoTemplate.count(query, ParTaskInfo.class);
		Page page = PageUtil.buildPage(currentPage, pageSize);
		page.setItems(list);
		page.setTotal(count.intValue());
		query = null;*/
		/****************************start**********************************/
		Long count = mongoTemplate.count(query, ParTaskInfo.class);
		
		query.skip((currentPage - 1) * pageSize);// skip相当于从那条记录开始
		query.limit(pageSize);// 从skip开始,取多少条记录
		logger.info("进入站点取件订单列表，query=" + query);
		List<ParTaskInfo> listParTask = mongoTemplate.find(query,ParTaskInfo.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map=null;
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
		/****************************end**********************************/
		return page; 
	}

	//确认取件
	@Override
	public String confirmTakeParcelGT(String uids, Long memberId,Long netId,String terminalId, String versionId ) {
		List<Map<String, Object>>listmap=new ArrayList<Map<String, Object>>();
		// 确认取件
			String[] ids = uids.split("-");
			logger.info("确认取件的包裹ids:" + uids + ",memberid:" + memberId );
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
//					parcelconnection.setTaskId(Long.parseLong(taskId));//任务号
					
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
					parcelconnection.setRecNetId(netId);//接收人网络id
					parcelconnection.setRecCosignFlag((short)1);//揽收

					logger.info("查询接收人的信息是:recCompId:"+recCompId+",recNetId:"+recNetId+",recName:"+recName+",recMobile:"+recMobile+",recnetName:"+recnetName+",compName:"+compName+",recCompId:"+recCompId+",recNetId:"+recNetId);

					this.mongoTemplate.insert(parcelconnection);// 插入一条包裹收派信息记录表
					logger.info("包裹收派信息记录表成功插入一条记录");
					//插入一条包裹报表数据
					logger.info("插入一条包裹报表数据,参数是:memberId:"+memberId+",recNetId:"+recNetId+"recCompId:"+recCompId);
					Date date = new Date();
					SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
					String dateString = sim.format(date);
					this.receivePackageService.insertPackageReport(memberId+"", recNetId+"", recCompId+"",dateString);
					//将需要上传的包裹信息进行封装
					Map<String, Object> map = this.uploadParcelReicevedinfo(memberId, netId, one.getCreateTime(), expWaybillNum,terminalId,versionId);
					listmap.add(map);
				} catch (Exception e) {
					logger.info("取件失败抛出的异常:" + e);
					continue;// 跳过这个包裹
				}
			}
			//来自国通的电商包裹进行签收上传
			Map<String, Object>upload=new HashMap<String, Object>();
			upload.put("list", listmap);
			JSONObject param = new JSONObject();
			param.put("expId", netId);
			logger.info("调用国通签收上传接口的参数:"+upload);
			String result = openPlatformHttpClient.uploadParcelTakenInfo( param.toJSONString());
			logger.info("调用国通签收上传接口返回的结果:"+result);
			return result;// 取件成功true,false;
			
	}
	public MemberInfoVO getValByMemberId(String memberId) {

		// 快递员基本信息放入缓存中--2016年5月30日19:55:09 by zhaohu
		MemberInfoVO memberInfoVO = this.redisService.get("memberInfo-byzhaohu-cache", memberId, MemberInfoVO.class);
		if (PubMethod.isEmpty(memberInfoVO)) {
			memberInfoVO = this.basEmployeeAuditMapper
					.queryMemberInfoByZhaohu(memberId);
			this.redisService.put("memberInfo-byzhaohu-cache", memberId,
					memberInfoVO);
		}
		return memberInfoVO;
	}
	//拿到国通员工的信息
	public CooperationExpCompanyAuth getByMemberId(Long memberId,Long netId) {
		CooperationExpCompanyAuth companyAuth = this.mongoTemplate.findOne(Query.query(Criteria.where("memberId").is(memberId).and("netId").is(netId)), CooperationExpCompanyAuth.class);
		return companyAuth;
	}
	// 封装国通包裹签收上传的信息
	@Override
	public Map<String, Object> uploadParcelReicevedinfo(Long memberId,Long recNetId,Date createtime,String expWaybillNum,String terminalId, String versionId){
		//将需要上传的包裹信息进行封装
		Map<String, Object>map=new HashMap<String, Object>();
		//拿到国通员工的信息
		logger.info("拿到的国通快递员的信息的参数:memberId:"+memberId+",netId:"+recNetId);
		CooperationExpCompanyAuth cooperationExpCompanyAuth = this.getByMemberId(memberId, recNetId);
		logger.info("拿到的国通快递员的信息:"+JSON.toJSONString(cooperationExpCompanyAuth));
		map.put("code", cooperationExpCompanyAuth.getUserCode());//员工编号
		//时间格式
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
		String createDate = sdf.format(createtime);//数据创建时间（格式：yyyyMMddHHmmss年月日时分秒）

		Date date2 = new Date();
		String date1 = sdf.format(date2);//扫描时间（格式：yyyyMMddHHmmss年月日时分秒）
		String oprationDate = sdf1.format(date2);//操作日期（格式：yyyyMMdd年月日）
		map.put("createDate", createDate);//数据创建时间（格式：yyyyMMddHHmmss年月日时分秒）
		map.put("date", date1);//扫描时间（格式：yyyyMMddHHmmss年月日时分秒）
		map.put("deliveryCode", cooperationExpCompanyAuth.getUserCode());//送货或者收获人员编号(可选)
		map.put("deliveryName", cooperationExpCompanyAuth.getUserName());//送货或者收获人员姓名(可选)
		map.put("hkSign", "N");
		map.put("oprationDate", oprationDate);//操作日期（格式：yyyyMMdd年月日）
		map.put("scanBillnumber", expWaybillNum);//运单号
		map.put("noteOrAddresseeOrMobile", "");//签收人
		map.put("scanEmpcode", cooperationExpCompanyAuth.getUserCode());//扫描人员编号 快递登录接口返回的数据
		map.put("scanType", "01");//扫描类型
		map.put("siteId", cooperationExpCompanyAuth.getNetId());//网点编号
		//判断设备id和版本号是否存在
		map.put("terminalId",terminalId );
		map.put("versionId", versionId);//app、傳的解析不了固定
		if (PubMethod.isEmpty(terminalId)) {
			map.put("terminalId", IdWorker.getIdWorker().nextId());
		}
		if (PubMethod.isEmpty(versionId)) {
			map.put("versionId", "5.1.0");
		}
		return map;
		
	}
	
	//封装查询是否授权的方法
	public boolean checkIsAuthorized(Long memberId,Long netId){
		boolean flag=false;
		CooperationExpCompanyAuth companyAuth = this.mongoTemplate.findOne(Query.query(Criteria.where("memberId").is(memberId).and("netId").is(netId)), CooperationExpCompanyAuth.class);
		if (!PubMethod.isEmpty(companyAuth)) {
			flag=true;
		}
		return flag;
	}

	//查询订单中电商件的包裹列表时,判断是否有查询的权限
	@Override
	public Boolean IsRightToApi(String netId) {
		//BasNetInfo findOne = this.mongoTemplate.findOne(Query.query(Criteria.where("netId").is(Long.parseLong(netId))), BasNetInfo.class);
		BasNetInfo findOne = basEmployeeAuditMapper.querystatusfrombasnet(Long.parseLong(netId));
		//如果不为空,查询授权状态
		boolean flag=false;
		if (!PubMethod.isEmpty(findOne)) {
			Short authStatus = findOne.getAuthStatus();
			Short apiParcelDeliveryFlag = findOne.getApiOrderDeliveryFlag();//是否有订单推送授权状态
			//这两个状态都唯一,才表明该快递员已经授权
			if ("1".equals(authStatus+"")&&"1".equals(apiParcelDeliveryFlag+"")) {
				flag=true;
			}
		}
		return flag;
	}
	
	
	
	
	
	
}