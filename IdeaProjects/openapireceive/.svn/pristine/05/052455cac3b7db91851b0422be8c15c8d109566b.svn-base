package net.okdi.apiV4.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.mongodb.WriteResult;

import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.apiV4.dao.BasEmployeeAuditMapperV4;
import net.okdi.apiV4.entity.ContentHis;
import net.okdi.apiV4.entity.CooperationExpCompanyAuth;
import net.okdi.apiV4.entity.MemberInfoVO;
import net.okdi.apiV4.entity.PackageReport;
import net.okdi.apiV4.entity.ParParcelMark;
import net.okdi.apiV4.entity.ParParceladdress;
import net.okdi.apiV4.entity.ParParcelconnection;
import net.okdi.apiV4.entity.ParParcelinfo;
import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.apiV4.service.ReceiveOrderFromWebsiteService;
import net.okdi.apiV4.service.ReceivePackageReportService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.sms.OpenPlatformHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
@Service
public class ReceivePackageReportServiceImpl extends BaseServiceImpl implements ReceivePackageReportService{

	private static Logger logger = Logger.getLogger(ReceivePackageReportServiceImpl.class);
	@Autowired
	private RedisService redisService;	
	@Autowired
	private BasEmployeeAuditMapperV4 basEmployeeAuditMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private AssignPackageService assignPackage;	
	@Autowired
	private ReceivePackageService receivePackageService;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private BasNetInfoMapper basNetInfoMapper;
	@Autowired
	private OpenPlatformHttpClient openPlatformHttpClient;
	
	@Autowired
	private ReceiveOrderFromWebsiteService receiveOrderFromWebsiteService;

	/**
	 * 揽收前查询
	 */
	@SuppressWarnings("all")
	@Override
	public Map<String, Object> queryPackage(String memberId,String expWaybillNum, String netId, String netName) {
		logger.info("揽收去重和查询----memberId："+ memberId+ ",expWaybillNum:"+ expWaybillNum);
	
		Map<String, Object> allMap = new HashMap<>(); 
		List<Map<String, Object>> listMap = new ArrayList<>();
		// 1；先去重， 对  1已取,2已发货,10，待派，11已派	
		if (!PubMethod.isEmpty(expWaybillNum)) {
			Query query  = new Query();
			query.addCriteria(Criteria.where("controllerMemberId").is(Long.valueOf(memberId)));// 物权人
			query.addCriteria(Criteria.where("expWaybillNum").is(expWaybillNum));
			//query.addCriteria(Criteria.where("netId").is(Long.parseLong(netId)));
			query.addCriteria(Criteria.where("controllerTaskFlag").in(1, 2,10,11,12));			
			if (!PubMethod.isEmpty(mongoTemplate.find(query,ParParceladdress.class))) {
				logger.info("揽收包裹--此运单号已经揽收 expWaybillNum:"+ expWaybillNum);
				allMap.put("flag", 1);// 1：已揽收，0：未揽收
				allMap.put("listMap", listMap);
				return allMap;// 给出提示 此运单号已存在
			}
		}
		/*BasNetInfo basNetInfo = basNetInfoMapper.queryNetAuthByNetId(Long.valueOf(netId));	//有无订单
		if(1==basNetInfo.getApiParcelIsFromOrderFlag()){*/
			//查询订单
			Query query1 = new Query();	
			query1.addCriteria(Criteria.where("expWaybillNum").is(expWaybillNum));
			query1.addCriteria(Criteria.where("netId").is(Long.parseLong(netId)));
			query1.addCriteria(Criteria.where("controllerTaskFlag").is((short) 0));// 待取
			ParParceladdress par = mongoTemplate.findOne(query1,ParParceladdress.class);								
			if (!PubMethod.isEmpty(par)) {	
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("parcelId", par.getUid());// 包裹id
				map.put("sendName", par.getSendName());// 发件人姓名
				map.put("sendPhone", par.getSendPhone());
				map.put("sendMobile", par.getSendMobile());// 发件人电话		
				map.put("sendAddress", par.getSendAddress());// 发件人地址
				map.put("sendProv", par.getSendProv());// 发件人省份
				map.put("sendCity", par.getSendCity());// 发件人市区
				map.put("sendDetailed", par.getSendDetailed());//详细地址	
										
				map.put("addresseeName", par.getAddresseeName());// 收件人姓名
				map.put("addresseeMobile", par.getAddresseeMobile());// 收件人电话     *
				map.put("addresseePhone", par.getAddresseePhone());
				map.put("addresseeAddress", par.getAddresseeAddress());// 收件人地址
				map.put("addresseeProv", par.getAddresseeProv());// 收件人省份
				map.put("addresseeCity", par.getAddresseeCity());// 收件人市区
				map.put("addresseeDetailed", par.getAddresseeDetailed());// 收件人详细地址后部				
				map.put("expWaybillNum", par.getExpWaybillNum());// 包裹运单号   *
				map.put("netName", par.getNetName());// 网点名    *
				map.put("netId", par.getNetId());// 网点id			*
				map.put("packageflag", par.getPackageflag());// 是否备注
				if (1 == par.getPackageflag()) {	
					Query query2 = new Query();	
					query2.addCriteria(Criteria.where("expWaybillNum").is(expWaybillNum));
					ParParcelMark pMark = mongoTemplate.findOne(query2,ParParcelMark.class);
					List<ContentHis> content = pMark.getContent();
					map.put("content", content);// 包裹备注
				}else{
					map.put("content", par.getContent());// 包裹备注
				}				
				map.put("pacelWeight", par.getPacelWeight());// 包裹重量
				map.put("parcelType", par.getParcelType()); //  物品类型
				map.put("serviceName", par.getServiceName());// 服务产品
				listMap.add(map);
			}													
		//}
			CooperationExpCompanyAuth querysqInfo = this.querysqInfo(memberId,netId);	//授权信息	
			BasNetInfo basNetInfo = basNetInfoMapper.queryNetAuthByNetId(Long.valueOf(netId));					
			if (!PubMethod.isEmpty(querysqInfo)&&basNetInfo.getAuthStatus() ==1) {	
				allMap.put("flag", 2);// 1：已揽收，0：未揽收    2国通授权包裹				
			}else{
				allMap.put("flag", 0);// 1：已揽收，0：未揽收
			}		
		allMap.put("listMap", listMap);
		return allMap;
	}

	/**
	 *揽散件去重查询
	 */
	@Override
	public String queryPackageCode(String memberId, String code,String netId) {				
		// 1；先去重， 对  1已取,2已发货takeMemberId（第一次揽收人）			
		    Query query = new Query();
		    query.addCriteria(Criteria.where("controllerMemberId").is(Long.valueOf(memberId)));// 物权人
			//query.addCriteria(Criteria.where("takeMemberId").is(Long.valueOf(memberId)));// 物权人
			query.addCriteria(Criteria.where("code").is(code));
			query.addCriteria(Criteria.where("netId").is(Long.parseLong(netId)));
			query.addCriteria(Criteria.where("controllerTaskFlag").in(1, 2));		
		//	query.addCriteria(Criteria.where("uploadTF").is((short) 1));	//上传成功；暂时不用
			ParParceladdress par = mongoTemplate.findOne(query,ParParceladdress.class);			
			if (!PubMethod.isEmpty(par)) {	
				logger.info("揽收包裹--此揽收码已经揽收 code:"+ code);	
				return "002";											
			}
			return "001";
	}	
	/**
	 *报表查询
	 */
	@Override
	public Map<String, Object> queryTakeforms(String memberId, String date,String compId) {		
		// mogon 格式话日期 date=2016-12  date=2016-11
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
		//MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		//Long compId = Long.valueOf(memberInfoVO.getCompId())==null?null:Long.valueOf(memberInfoVO.getCompId());// 站点id新表手机端传值
		List<PackageReport> listParcel = null;		
		Map<String, Object> map = new HashMap<>();
		Map<Integer, Long> mapList = new HashMap<>();
		SimpleDateFormat sns = new SimpleDateFormat("d");
		long count = 0;// 每一天的包裹量		
		Query query = new Query();
		if (!PubMethod.isEmpty(compId)) {
			query.addCriteria(Criteria.where("compId").is(Long.valueOf(compId)));	//本站全部					
		}else{
			query.addCriteria(Criteria.where("memberId").is(Long.valueOf(memberId)));	//个人的											
		}
		if (dateNowStr.equals(date)) {//判断数据是否到当天
			//Calendar ca = Calendar.getInstance();
			Date now = new Date();
			query.addCriteria(Criteria.where("createTime").gte(start).lte(now));// <=now >=start到当前日期的
		} else {
			query.addCriteria(Criteria.where("createTime").gte(start).lte(end));//整个月的
		}		 	
		 query.with(new Sort(Direction.DESC,"createTime"));//根据活动开始时间排序
		listParcel = mongoTemplate.find(query, PackageReport.class);
		for  ( PackageReport packageReport : listParcel ) {						
			Date pickup = packageReport.getCreateTime() == null?new Date():packageReport.getCreateTime();
			Long nCount = packageReport.getReceiveCount() == null?0L:packageReport.getReceiveCount();
			String pkp = sns.format(pickup); //
			Integer intPkp = Integer.valueOf(pkp);
            mapList.put(intPkp, (mapList.get(intPkp) == null ? 0 : mapList.get(intPkp)) + nCount);
            count += nCount;
		}					
		//排序			倒序									
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
		Map<String, Object> resultMap=null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Integer key : keyList) {
			resultMap = new LinkedHashMap<>();
			resultMap.put("date", nMonth + "月" + key + "日");
			resultMap.put("count", mapList.get(key));
			listMap.add(resultMap);//要数组
		}
		map.put("mapList", listMap);
		map.put("sum",count);// 总数
		logger.info("mapList" + listMap + "sum"+ count);
		return map;	
	}
	// 获取月份的天数 2016-12-28
	public int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 揽散件包裹（上传订单，创建作业记录）揽收码专用
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> takeReceivePackage(String memberId,
			String sendName, String sendPhone, String sendAddress,
			String deliveryAddress, String netId, String addresseeName,
			String addresseeAddress, String addresseePhone, String netName,
			String roleId, String expWaybillNum, String code, String comment,
			String compId, String pacelWeight,String parcelType, String serviceName,
			String sendProv,String sendCity, String addresseeProv, String addresseeCity,
			String sendDetailed, String addresseeDetailed, String versionId ,String terminalId) {
		logger.info("手机端传的roleId："+roleId);
		Map<String, Object> maps = new HashMap<>();	
		String txlogisticid =null;
		Date date = new Date(); // 时间很总要		
		try {
			MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
			logger.info("快递员信息："+memberInfoVO.getCompId()+"=="+memberInfoVO.getRoleId()+"---"+memberInfoVO.getNetName()+memberInfoVO.getNetId());
			if (PubMethod.isEmpty(roleId)) {
				roleId = memberInfoVO.getRoleId();// 手机段可以传 如果为空自己查询
			}	
			String memberName = memberInfoVO.getMemberName();// 快递员
			if (PubMethod.isEmpty(compId)) {
				compId = memberInfoVO.getCompId();//查询站点id
			}	
			/**1*******授权信息查询***************/
			CooperationExpCompanyAuth querysqInfo = this.querysqInfo(memberId,netId);	//授权信息	
				// 插入包裹表
			/*	this.insertParParcelinfo(memberId, sendName, sendPhone,
						sendAddress, deliveryAddress, netId, addresseeName,
						addresseeAddress, addresseePhone, netName, roleId,
						expWaybillNum, code, comment, parcelId, compId,
						memberName, date);*/
		 	/**2*******插入包裹地址表***************/					
			Long parcelId = IdWorker.getIdWorker().nextId();// 包裹主键id
			txlogisticid = parcelId.toString();//物流订单号（必填）好递任务订单号
			
			/*Query queryGt =new Query();
			queryGt.addCriteria(Criteria.where("controllerMemberId").is(Long.valueOf(memberId)));// 物权人
			queryGt.addCriteria(Criteria.where("code").is(code));
			queryGt.addCriteria(Criteria.where("netId").is(Long.parseLong(netId)));
			//	queryGt.addCriteria(Criteria.where("uploadTF").is((short) 0));	//上传成功；暂时不用
	    	ParParceladdress parP = mongoTemplate.findOne(queryGt, ParParceladdress.class);
			 if (!PubMethod.isEmpty(parP)) {
				 parcelId = parP.getUid();
				 txlogisticid=parP.getTxlogisticid();
				this.updateParParceladdress(memberId, sendName, sendPhone, sendAddress, deliveryAddress,
						netId, addresseeName, addresseeAddress, addresseePhone, netName, 
						roleId, expWaybillNum, code, comment, parcelId, compId, memberName, 
						date, pacelWeight, parcelType, serviceName, sendProv, sendCity, addresseeProv, 
						addresseeCity, querysqInfo, txlogisticid, sendDetailed, addresseeDetailed);
				logger.info("收派员或者代收点自添加取件更新地址表.........包裹id=地址表code" + code);	
			}else{				*/
				this.insertParParceladdress(memberId, sendName, sendPhone,
						sendAddress, deliveryAddress, netId, addresseeName,
						addresseeAddress, addresseePhone, netName, roleId,
						expWaybillNum, code, comment, parcelId, compId,
						memberName, date,pacelWeight,parcelType,serviceName,
						 sendProv, sendCity, addresseeProv, addresseeCity,txlogisticid,
						  sendDetailed, addresseeDetailed);		
				logger.info("收派员或者代收点自添加取件插入地址表.........包裹id=地址表id" + parcelId);		
			//}			 									
				/***3*****添加寄件人***************/
				this.addCustomer(memberId, compId, sendName, sendPhone, sendAddress);						
				/***4******插入包裹标记表************/
				if(!PubMethod.isEmpty(comment)){
					this.insertParParcelMark(memberId, roleId,expWaybillNum, code, comment, parcelId, memberName);
				}
				/***5***生成作业记录表***********揽散件特不上创国通（需求）*************/						
				this.insertParParcelconnection(memberId, sendName, sendPhone,sendAddress,deliveryAddress, netId, netName,
						expWaybillNum,code,compId,date,parcelId, versionId , terminalId,roleId);
				/***6*******插入报表数据*****************/	
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = sim.format(date);			
				this.receivePackageService.insertPackageReport(memberId, netId, compId,dateString);
				logger.info("插入一条包裹报表数据,参数是:memberId:"+memberId+",recNetId:"+netId+"compId:"+compId);																
				/***7***==判断是否授权=======查询国通接==（揽散件下单）包裹上传=*****************/	
				String mailno = null;
				String suc = null;
				String reason =null;
				BasNetInfo basNetInfo = basNetInfoMapper.queryNetAuthByNetId(Long.valueOf(netId));					
				if (!PubMethod.isEmpty(querysqInfo)&&basNetInfo.getAuthStatus() ==1&&basNetInfo.getApiParcelUploadFlag() ==1) {					
					SimpleDateFormat simf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String createordertime = simf.format(date);
					/*****8*******上传国通数据封装*******订单*************************/
					Map<String, Object> map = this.uploadParcelRecinfo( memberId, txlogisticid, createordertime , code, querysqInfo
							,  sendName,  sendPhone,  sendProv,  sendCity,  sendDetailed,  addresseeName,  addresseePhone,
							 addresseeProv,  addresseeCity,  addresseeDetailed,  netId);					
					JSONObject param = new JSONObject();
					param.put("expId", netId);
					param.put("data",map);								
					String result = openPlatformHttpClient.uploadPackagenInfo(param.toJSONString());
					JSONObject jsons = JSONObject.parseObject(result);
					logger.info(jsons+"======开始==第一次====");		
					List<Map<String ,String>> object = (List<Map<String ,String>>) jsons.get("responseitems");	
					for (Map<String, String> map2 : object) {
						 mailno = map2.get("mailno");
						 if (!PubMethod.isEmpty(mailno)) {
							//========更新包裹地址表===============
							this.updataParParceladdressgGt(parcelId, addresseePhone, memberId, mailno);
							/******9******上传国通作业记录**********暂时不用了**********************//*
							if (!PubMethod.isEmpty(querysqInfo)&&basNetInfo.getAuthStatus() ==1&&basNetInfo.getApiParcelConnFlag() ==1) {	
								List<Map<String, Object>>listmap=new ArrayList<>();
								Map<String, Object> mapGt = receiveOrderFromWebsiteService.uploadParcelReicevedinfo(Long.parseLong(memberId), Long.parseLong(netId), new Date(), expWaybillNum,terminalId,versionId);
								listmap.add(mapGt);	
								//来自国通的电商包裹进行揽收上传
								Map<String, Object>upload=new HashMap<String, Object>();
								upload.put("list", listmap);
								logger.info("调用国通通揽收上传记录接口的参数:"+upload);
								String results = openPlatformHttpClient.uploadParcelTakenInfo( JSON.toJSONString(upload));
								logger.info("调用国通揽收上传记录接口返回的结果:"+results);
							}				*/		
						 }
					    suc = map2.get("success");
					    reason = map2.get("reason");
						/*maps.put("success", suc);
						maps.put("reason", reason);
						maps.put("parcelId", parcelId);
						maps.put("code", code);																														
						maps.put("expWaybillNum", mailno);//返回给手机端						
						maps.put("netId", netId);
						maps.put("netName", netName);*/					
					}																					 										
				}
				//更新包裹状态
				Query query =new Query();
		    	query.addCriteria(Criteria.where("uid").is(parcelId));
		    	query.addCriteria(Criteria.where("addresseeMobile").is(addresseePhone));	
		    	Update update =new Update();
				if("true".equals(suc)){				    	
			    	update.set("uploadTF", (short) 1); //1成功  ，0失败；
			    }else{
			    	update.set("uploadTF", (short) 0); //1成功  ，0失败；
			    }
		    	this.mongoTemplate.updateFirst(query, update, ParParceladdress.class);
				
				maps.put("suc", suc);
				maps.put("reason", reason);
				maps.put("parcelId", parcelId);
				maps.put("code", code);																														
				maps.put("expWaybillNum", mailno);//返回给手机端						
				maps.put("netId", netId);
				maps.put("netName", netName);
				logger.info(code+"：code=====揽收码转换成运单号======mailno："+mailno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;// 001 代表成功		
	}		
	/**
	 * 揽散件信息封装
	 */
	@Override
	public Map<String, Object> uploadParcelRecinfo(String memberId,String txlogisticid,String createordertime ,String code,CooperationExpCompanyAuth querysqInfo
			, String sendName, String sendPhone, String sendProv, String sendCity, String sendDetailed, String addresseeName, String addresseePhone,
			String addresseeProv, String addresseeCity, String addresseeDetailed, String netId){		
		Map<String, Object> map = new HashMap<>();					
		//List<String> ls = new ArrayList<>();
		map.put("txlogisticid", txlogisticid);//1物流订单号
		map.put("orderstatus", "1");//2订单状态
		map.put("createordertime", createordertime);//3下单时间			
		/**********下面揽收码太长国通不要***************/
		//map.put("fetchcode", code + netId +memberId);// 揽收码+快递公司id+ mid   手机端生成	
		map.put("fetchcode", code);// 揽收码 手机端生成	
		map.put("pickupoperatorcode", querysqInfo.getUserCode());//7取件员编号
		map.put("pickupoperatorname", querysqInfo.getUserName());//8取件员名称（必填） 快递登录接口返回的数据
		map.put("pickuppointcode", querysqInfo.getOrgCode());//9取件员网点编码
		map.put("pickuppointname", querysqInfo.getOrgName());//10取件网点名称					
		Map<String, Object> of = new HashMap<>();
		of.put("name", sendName);//11发件人姓名
		of.put("mobile", sendPhone);//12发件人手机
		of.put("prov", sendProv);//13发件人省份
		of.put("city", sendCity);//14发件人市区 以，分割
		of.put("address", sendDetailed);//16发件人详细地址
		map.put("sender", of);//17发件人信息
		Map<String, Object> os = new HashMap<>();
		os.put("name", addresseeName);//18收件人姓名
		os.put("mobile", addresseePhone);//19
		os.put("prov", addresseeProv);//20
		os.put("city", addresseeCity);//21
		os.put("address", addresseeDetailed);	//23		
		map.put("receiver", os);//收件人信息24						
		map.put("orderHasPrint", false);//面单是否打印 true:已打印 false:未打印				
		return map;		
	}
	/**
	 * 揽收包裹
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> takeReceivePackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment, Long parcelId,
			String compId, String pacelWeight, String parcelType,
			String serviceName,String sendProv,String sendCity,String addresseeProv,String addresseeCity,
			String sendDetailed,String addresseeDetailed) {
		ParParceladdress up=null;
		if (!PubMethod.isEmpty(expWaybillNum)) {
			Query query = new Query();
			query.addCriteria(Criteria.where("takeMemberId").is(Long.valueOf(memberId)));// 物权人
			query.addCriteria(Criteria.where("expWaybillNum").is(expWaybillNum));
			query.addCriteria(Criteria.where("netId").is(netId));
			query.addCriteria(Criteria.where("controllerTaskFlag").in(0,1));
			 up = mongoTemplate.findOne(query,ParParceladdress.class);
			
		}
		Map<String, Object> maps = new HashMap<>();	
		String txlogisticid =null;
		Date date = new Date(); // 时间很总要
		SimpleDateFormat simf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createordertime = simf.format(date);
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
			CooperationExpCompanyAuth querysqInfo = this.querysqInfo(memberId,netId);	//授权信息							
				// 创建包裹id    物流订单号
			if (PubMethod.isEmpty(up)) {
				//证明是要创建
				parcelId = IdWorker.getIdWorker().nextId();// 包裹主键id
				txlogisticid = parcelId.toString();//物流订单号（必填）好递任务订单号
				// 插入包裹表
			/*	this.insertParParcelinfo(memberId, sendName, sendPhone,
						sendAddress, deliveryAddress, netId, addresseeName,
						addresseeAddress, addresseePhone, netName, roleId,
						expWaybillNum, code, comment, parcelId, compId,
						memberName, date);*/
				// 插入包裹地址表
				this.insertParParceladdress(memberId, sendName, sendPhone,
						sendAddress, deliveryAddress, netId, addresseeName,
						addresseeAddress, addresseePhone, netName, roleId,
						expWaybillNum, code, comment, parcelId, compId,
						memberName, date,pacelWeight,parcelType,serviceName,
						 sendProv, sendCity, addresseeProv, addresseeCity,txlogisticid,
						  sendDetailed, addresseeDetailed);				
				logger.info("收派员或者代收点自添加取件插入地址表.........包裹id=地址表id" + parcelId);				
			}else{
				parcelId=up.getUid();
				this.updateParParceladdress(memberId, sendName, sendPhone,
						sendAddress, deliveryAddress, netId, addresseeName,
						addresseeAddress, addresseePhone, netName, roleId,
						expWaybillNum, code, comment, parcelId, compId,
						memberName, date,pacelWeight,parcelType,serviceName,
						 sendProv, sendCity, addresseeProv, addresseeCity,querysqInfo,txlogisticid,
						  sendDetailed, addresseeDetailed);
			}
				logger.info("添加客户==寄件人的信息存入到客户管理=======是否是好递用户=======OK");
				this.addCustomer(memberId, compId, sendName, sendPhone, sendAddress);						
				/****************插入包裹标记表**********************/
				if(!PubMethod.isEmpty(comment)){
					this.insertParParcelMark(memberId, roleId,expWaybillNum, code, comment, parcelId, memberName);
				}						
				//===========判断是否授权=======查询国通接==（揽散件下单）包裹上传==				
				BasNetInfo basNetInfo = basNetInfoMapper.queryNetAuthByNetId(Long.valueOf(netId));				
				if (!PubMethod.isEmpty(querysqInfo)&&basNetInfo.getAuthStatus() ==1&&basNetInfo.getApiParcelUploadFlag() ==1) {					
					Map<String, Object> map = new HashMap<>();					
					map.put("txlogisticid", txlogisticid);//1物流订单号
					map.put("orderstatus", "1");//2订单状态
					map.put("createordertime", createordertime);//3下单时间					
					map.put("fetchcode", expWaybillNum);// 4揽收码运单号
					map.put("mailno", expWaybillNum);//5运单号
					map.put("remark", "揽收码等于运单号");//6备注				
					map.put("pickupoperatorcode", querysqInfo.getUserCode());//7取件员编号
					map.put("pickupoperatorname", querysqInfo.getUserName());//8取件员名称（必填） 快递登录接口返回的数据
					map.put("pickuppointcode", querysqInfo.getOrgCode());//9取件员网点编码
					map.put("pickuppointname", querysqInfo.getOrgName());//10取件网点名称					
					Map<String, Object> of = new HashMap<>();
					of.put("name", sendName);//11发件人姓名
					of.put("mobile", sendPhone);//12发件人手机
					of.put("prov", sendProv);//13发件人省份
					of.put("city", sendCity);//14发件人市区 以，分割
					of.put("address", sendDetailed);//16发件人详细地址
					map.put("sender", of);//17发件人信息
					Map<String, Object> os = new HashMap<>();
					os.put("name", addresseeName);//18收件人姓名
					os.put("mobile", addresseePhone);//19
					os.put("prov", addresseeProv);//20
					os.put("city", addresseeCity);//21
					os.put("address", addresseeDetailed);	//23		
					map.put("receiver", os);//收件人信息24							
					map.put("orderHasPrint", false);//面单是否打印 true:已打印 false:未打印									
					JSONObject param = new JSONObject();
					param.put("expId", netId);
					param.put("data",map);								
					String result = openPlatformHttpClient.uploadPackagenInfo(param.toJSONString());
					JSONObject jsons = JSONObject.parseObject(result);
					logger.info(jsons+"======开始======");						
					List<Map<String ,String>> object = (List<Map<String ,String>>) jsons.get("responseitems");	
					String mailno = null;
					for (Map<String, String> map2 : object) {
						 mailno = map2.get("mailno");
						 if (!PubMethod.isEmpty(mailno)) {
							//========更新包裹地址表===============
							this.updataParParceladdressgGt(parcelId, addresseePhone, memberId, mailno);
						 }
					    String success = map2.get("success");
						String reason = map2.get("reason");
						maps.put("success", success);
						maps.put("reason", reason);
						maps.put("parcelId", parcelId);
						maps.put("code", code);																														
						maps.put("expWaybillNum", mailno);//返回给手机端						
						maps.put("netId", netId);
						maps.put("netName", netName);
						logger.info(code+"：code=====揽收码转换成运单号======mailno："+mailno);
					}																					 										
				}													
			/*// 插入操作记录表
			this.insertParParcelconnection(memberId, sendName, sendPhone,deliveryAddress, netId, netName, roleId,
					expWaybillNum, code,  parcelId, compId, memberName,date);
			logger.info("收派员或者代收点自添加取件插入信息监控表完成......==============包裹id"+ parcelId);
			// 插入包裹报表
			this.insertPackageReport(memberId, netId, compId, dateString);*/			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("订单上传国通捕获异常");; // 002 代表自取件异常
		}
		return maps;// 001 代表成功
	}
	//获取收派员信息
	public MemberInfoVO getValByMemberId(String memberId) {
		// 快递员基本信息放入缓存中--2016年5月30日19:55:09 by zhaohu
		MemberInfoVO memberInfoVO = this.redisService.get("memberInfo-byzhaohu-cache", memberId, MemberInfoVO.class);
		if (PubMethod.isEmpty(memberInfoVO)) {
			memberInfoVO = this.basEmployeeAuditMapper.queryMemberInfoByZhaohu(memberId);
			this.redisService.put("memberInfo-byzhaohu-cache", memberId,memberInfoVO);
		}
	logger.info("快递员信息查询："+memberInfoVO);
		return memberInfoVO;
	}
	/**
	 *揽收添加寄件人
	 */
	  public void addCustomer (String memberId,String compId,String sendName,String sendPhone,String sendAddress){
		  Map<String,String> mapAdd = new HashMap<>();
		  try {			  
		    mapAdd.put("memberId", memberId);
		   	mapAdd.put("compId",compId);
			mapAdd.put("receName",sendName);
			mapAdd.put("mobile",sendPhone);
			mapAdd.put("address", sendAddress);
			String url = constPool.getOpenApiParcelUrl() + "sendPackage/addCustomer";
			String resul = Post(url, mapAdd);
			logger.info("添加客户==寄件人的信息存入到客户管理=======是否是好递用户=======OK");
		} catch (Exception e) {
			logger.info("捕获异常");
		}
				
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
	/**
	 * 更新包裹地址表
	 */	
	private void updataParParceladdressgGt(Long parcelId,String addresseePhone,String memberId,String mailno){
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("uid").is(parcelId));
			query.addCriteria(Criteria.where("addresseeMobile").is(addresseePhone));
			Update update = new Update();
			update.set("expWaybillNum", mailno);
			//mongoTemplate.upsert(query, update, ParParceladdress.class);
			mongoTemplate.updateFirst(query, update, ParParceladdress.class);
		} catch (Exception e) {
			logger.info("更新包裹地址表捕获异常");
		}				
	}
	// 插入包裹地址表
	private void insertParParceladdress(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment, Long parcelId,
			String compId, String memberName, Date date,String pacelWeight,String parcelType,
			String serviceName,String sendProv,String sendCity,String addresseeProv,String addresseeCity,
			String txlogisticid,String sendDetailed,String addresseeDetailed) {
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);//快递员信息
		logger.info("收派员或者代收点自添加取件插入包裹地址表======================包裹id" + parcelId);
		ParParceladdress parParceladdress = new ParParceladdress();// 包裹地址表======
		parParceladdress.setUid(parcelId); // 1.包裹地址id 生成的19位id id与包裹表中的主键id相同
		parParceladdress.setExpWaybillNum(expWaybillNum);// 2.运单号			
		/*if(code!=null&&code.length()>5){
			parParceladdress.setFetchcode(code);//国通要的fetchcode(可能扫码)
		}else{
			
		}	*/
		parParceladdress.setFetchcode(code);//国通要的fetchcode  
		parParceladdress.setNetId(Long.valueOf(netId));//4. 网络id
		parParceladdress.setNetName(netName);// 5.网络名		
		parParceladdress.setSendName(sendName);// 6.发件人姓名 SendName
		parParceladdress.setSendPhone(sendPhone);// 7.发件人手机 SendMobile
		parParceladdress.setSendMobile(sendPhone);//8
		parParceladdress.setSendCity(sendCity);//9发件人市区
		parParceladdress.setSendProv(sendProv);//10发件人省份
		parParceladdress.setSendAddress(sendAddress);// 11.发件人完整地址	
		parParceladdress.setSendDetailed(sendDetailed);//12发件人详细地址	
		parParceladdress.setAddresseeName(addresseeName);// 13.收件人姓名
		parParceladdress.setAddresseePhone(addresseePhone);// 14.收件人手机号码//
		parParceladdress.setAddresseeMobile(addresseePhone);//14
		parParceladdress.setAddresseeCity(addresseeCity);// 15.收件人省份
		parParceladdress.setAddresseeProv(addresseeProv);// 16.收件人市区
		parParceladdress.setAddresseeDetailed(addresseeDetailed);//17收件人详细地址
		parParceladdress.setAddresseeAddress(addresseeAddress);// 17.收件人完整地址		
		parParceladdress.setPacelWeight(pacelWeight);//18包裹重量
		parParceladdress.setParcelType(parcelType);//19物品类型
		parParceladdress.setServiceName(serviceName);//20服务产品
		parParceladdress.setPickupTime(date);// 21.包裹揽收时间第一次
		parParceladdress.setCreateTime(date);// 22地址创建时间		
		parParceladdress.setHkSign("N");//23航空件
		parParceladdress.setTxlogisticid(txlogisticid);	//物流订单号（给快递公司）好递任务订单号	
		parParceladdress.setTakeMemberId(Long.valueOf(memberId));//24第一次包裹揽收人id
		parParceladdress.setCompId(Long.parseLong(compId));//25 包裹揽收时站点id
		// parParceladdress.addContent(comment, memberName);//标记备注 去标记备注表查询获取
		if (!PubMethod.isEmpty(comment)) {
			parParceladdress.setPackageflag((short) 1);
		} else {
			parParceladdress.setPackageflag((short) 0);
		}	
		if (!PubMethod.isEmpty(code)) {
			parParceladdress.setControllerTaskFlag((short) 1);// 25已取，揽散件用的
			parParceladdress.setCode(code);//3揽收码（页面）
		}  else{
			parParceladdress.setControllerTaskFlag((short) 0);// 25待取  揽收运单号用的			
		}		
		parParceladdress.setReceiveTaskUserId(Long.valueOf(memberId));//26任务执行人id			
		//parParceladdress.setReceiveTaskUserCode(querysqInfo.getUserCode());//27揽收任务执行人编码	
		parParceladdress.setCompId(Long.valueOf(compId));// 站点id
		parParceladdress.setControllerMemberId(Long.valueOf(memberId));// 物权所有人id
		parParceladdress.setCreateTime(date);// 第一次包裹创建时间
		if ("2".equals(roleId) || "3".equals(roleId)) {
			parParceladdress.setControllerNetId(null);// 物权网络id
			parParceladdress.setControllerNetName(null);// 物权网络名称
		} else {
			logger.info("获取roleId："+roleId+",NetId:"+memberInfoVO.getNetId()+";NetName:"+memberInfoVO.getNetName());
			parParceladdress.setControllerNetId(Long.valueOf(memberInfoVO.getNetId()));// 物权网络id
			parParceladdress.setControllerNetName(memberInfoVO.getNetName());// 物权网络名称
		}
		parParceladdress.setControllerCompId(Long.valueOf(compId));// 物权站点id
		mongoTemplate.insert(parParceladdress);// 插入地址表
		logger.info("收派员或者代收点自添加取件插入包裹地址表=========成功=======包裹id" + parcelId);
	}
		
		// 更新包裹地址表
	private void updateParParceladdress(String memberId, String sendName,
			String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,
			String addresseePhone, String netName, String roleId,
			String expWaybillNum, String code, String comment, Long parcelId,
			String compId, String memberName, Date date,String pacelWeight,String parcelType,
			String serviceName,String sendProv,String sendCity,String addresseeProv,String addresseeCity,
			CooperationExpCompanyAuth querysqInfo,String txlogisticid,String sendDetailed,String addresseeDetailed) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(Long.valueOf(parcelId)));// 揽收人
		query.addCriteria(Criteria.where("addresseeMobile").is(addresseePhone));
		
		Update update = new Update();
		update.set("takeMemberId", Long.valueOf(memberId));			
		update.set("expWaybillNum", expWaybillNum);
		update.set("code", code);
		update.set("fetchcode", code);
		
		update.set("addresseeName", addresseeName);
		update.set("addresseePhone", addresseePhone);
		update.set("addresseeCity", addresseeCity);
		update.set("addresseeProv", addresseeProv);
		update.set("addresseeDetailed", addresseeDetailed);
		update.set("addresseeAddress", addresseeAddress);
		update.set("sendName", sendName);
		update.set("sendPhone", sendPhone);
		update.set("sendMobile", sendPhone);
		update.set("sendProv", sendProv);
		update.set("sendCity", sendCity);
		update.set("sendDetailed", sendDetailed);
		update.set("sendAddress", sendAddress);
		update.set("deliveryAddress", deliveryAddress);
		
		update.set("parcelType", parcelType);
		update.set("serviceName", serviceName);
		update.set("pacelWeight", pacelWeight);
		update.set("createTime", date);
		update.set("netId", Long.valueOf(netId));
		update.set("netName", netName);			
		logger.info("====== 开始更新包裹中的数据 ======== ParParceladdress ......");
		mongoTemplate.updateFirst(query, update, ParParceladdress.class);
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
	// 插入包裹标记表
	private void insertParParcelMark(String memberId,String roleId,String expWaybillNum, String code, 
			String comment, Long parcelId, String memberName) {
		logger.info("收派员或者代收点自添加取件插入标记内容表......包裹id=标记内容表id" + parcelId+"标记内容："+comment);
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
	/**
	 * 快递公司授权
	 */
	@Override
	public CooperationExpCompanyAuth querysqInfo(String memberId,String netId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(Long.valueOf(memberId)).and("netId").is(Long.valueOf(netId)));
		CooperationExpCompanyAuth mInfo = mongoTemplate.findOne(query, CooperationExpCompanyAuth.class);										
		return mInfo;
	}
	/**
	 * 揽收--确认取件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String finishTakeReceivePackage(String memberId,String jsonData, String codes,String compId,
			String deliveryAddress,String versionId ,String terminalId) {
		/*{"items":[{"netId":"1526","netName":"国通","expWay":"15011232453","sendName":"张三","sendAddress":"北京市海淀区环星大厦","sendMobile":"13535353000"},
		{"netId":"1536","netName":"国通","expWay":"15688888888","sendName":"张三","sendAddress":"北京市海淀区环星大厦","sendMobile":"13535353000"}]}*/
			/*****查询快递员信息*************/
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		logger.info("根据memberId: " + memberId + " 查询出这个人的信息" + memberInfoVO);	
		String roleId = memberInfoVO.getRoleId();// 手机段可以传 如果为空自己查询
		
		List<Map<String, Object>>listmap=new ArrayList<>();
		Date date =new Date();		
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		if(!PubMethod.isEmpty(jsonObject.get("items"))){		
			List<Map<String, Object>> maplist = (List<Map<String, Object>>) jsonObject.get("items");
			String netId=null;
			for(Map<String, Object> map : maplist){
				netId = map.get("netId").toString();
				String netName = map.get("netName").toString();
				String expWaybillNum = map.get("expWay").toString();
				/*String sendName=map.get("sendName").toString();
				String sendAddress =map.get("sendAddress").toString();
				String sendMobile = map.get("sendMobile").toString();*/
				String sendName=null;
				String sendAddress=null;
				String sendMobile=null;
				String addresseeMobile =null;
				Long pid =null;
				ParParceladdress par = this.mongoTemplate.findOne(
						Query.query(Criteria.where("expWaybillNum").is(expWaybillNum)),ParParceladdress.class);					
				if(!PubMethod.isEmpty(par)){
					sendName = par.getSendName();//发件人姓名
					sendAddress = par.getSendAddress();//发件人的地址
					sendMobile = par.getSendMobile();//发件人的手机号
					pid = par.getUid();//包裹的id
					addresseeMobile = par.getAddresseeMobile();//片键===========
					/***修改包裹地址表**************************************/
				    this.updataPackageGt(memberId, compId, pid, addresseeMobile);					
				}else{
					/*****国通的包裹新建************************************/
					pid = IdWorker.getIdWorker().nextId();// 包裹主键id
					ParParceladdress parParceladdress =new ParParceladdress();
					parParceladdress.setUid(pid);
					parParceladdress.setExpWaybillNum(expWaybillNum);
					parParceladdress.setNetId(Long.parseLong(netId));
					parParceladdress.setNetName(netName);
					if("2".equals(roleId)||"3".equals(roleId)){
						parParceladdress.setControllerNetId(null);
						parParceladdress.setControllerNetName(null);
					}else{
						parParceladdress.setControllerNetId(Long.valueOf(memberInfoVO.getNetId()));
						parParceladdress.setControllerNetName(memberInfoVO.getNetName());
					}	
					parParceladdress.setCompId(Long.parseLong(compId));
					parParceladdress.setControllerMemberId(Long.parseLong(memberId));	
					Date data =new Date();
					parParceladdress.setPickupTime(data);
					parParceladdress.setCreateTime(data);
					parParceladdress.setControllerTaskFlag((short) 1);//已取

                    //片键忘了加，出bug了，没有手机号，取订单号了
                    parParceladdress.setAddresseeMobile(expWaybillNum);
					/*parParceladdress.setSendName(sendName);//寄件人信息
					parParceladdress.setSendAddress(sendAddress);
					parParceladdress.setSendMobile(sendMobile);*/
					
					mongoTemplate.insert(parParceladdress);
				}																	
				/***插入操作记录表**************************************/							
				this.insertParParcelconnection(memberId, sendName, sendMobile,sendAddress,deliveryAddress, netId, netName,
						expWaybillNum,codes,compId,date,pid, versionId , terminalId, roleId);									
				logger.info("包裹揽收信息记录表成功插入一条记录");						
				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = sim.format(date);
				/***插入报表**************************************/	
				this.receivePackageService.insertPackageReport(memberId, netId, compId,dateString);
				logger.info("插入一条包裹报表数据,参数是:memberId:"+memberId+",recNetId:"+netId+"compId:"+compId);	
				if(!PubMethod.isEmpty(expWaybillNum)){
					//=============判断是否授权============查询国通接口===（揽散件下单）包裹上传==
					CooperationExpCompanyAuth querysqInfo = this.querysqInfo(memberId,netId);
					BasNetInfo basNetInfo = basNetInfoMapper.queryNetAuthByNetId(Long.valueOf(netId));		
					logger.info(basNetInfo.getAuthStatus()+"====授权信息查询====="+basNetInfo.getApiParcelConnFlag());
					if (!PubMethod.isEmpty(querysqInfo)&&basNetInfo.getAuthStatus() ==1&&basNetInfo.getApiParcelConnFlag() ==1) {	
						logger.info("授权才能进=======memberId："+memberId+"netId:"+netId+"expWaybillNum:"+expWaybillNum+"设备号terminalId"+terminalId+"版本号versionId："+versionId);
						terminalId="OKDID3FNB";//手机端传的：D337DDDF-193A-4BC3-BA66-6D2D2AF5F96D
						Map<String, Object> maps = receiveOrderFromWebsiteService.uploadParcelReicevedinfo(Long.parseLong(memberId), Long.parseLong(netId), new Date(), expWaybillNum,terminalId,versionId);						
						listmap.add(maps);	
					}
				}					
			}
			 String result="002";
			if(!PubMethod.isEmpty(listmap)){
				//来自国通的电商包裹进行揽收上传
				Map<String, Object>upload=new HashMap<String, Object>();
				upload.put("list", listmap);
				JSONObject json = new JSONObject();
				json.put("expId", netId);
				json.put("data", upload);
				String paramJson = json.toJSONString();
				logger.info("调用国通揽收记录上传接口的参数*****paramJson="+paramJson);
				 result = openPlatformHttpClient.uploadParcelTakenInfo(paramJson);
				logger.info("调用国通揽收记录上传接口返回的结果:"+result);	
				JSONObject jsons = JSONObject.parseObject(result);
				logger.info("jsons="+jsons);				
				logger.info("取件成功=========哈哈哈========"+jsons);
			}
			return result;//002 :不是国通包裹
		}		
		return "001";// 取件成功 没有包裹		
	}		
	/**
	 * 修改包裹表
	 */
	public void updataPackageGt(String memberId,String compId,Long pid,String addresseeMobile ){
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(pid));
		query.addCriteria(Criteria.where("addresseeMobile").is(addresseeMobile));
		Update update = new Update();
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		logger.info("根据memberId: " + memberId + " 查询出这个人的信息" + memberInfoVO);
		//String memberName = memberInfoVO.getMemberName();// 快递员
		if (PubMethod.isEmpty(compId)) {
			compId = memberInfoVO.getCompId();//查询站点id
		}							
		update.set("controllerMemberId",Long.valueOf(memberId) );//物权所有人id
		update.set("controllerCompId", Long.valueOf(compId));// 站点id					
		update.set("controllerNetId", memberInfoVO.getNetId());// 网点id
		update.set("controllerNetName", memberInfoVO.getNetName());
		update.set("pickupTime", new Date());// 取件时间					
		update.set("controllerTaskFlag", (short) 1);// 已取件--未发货					
		this.mongoTemplate.updateFirst(query, update,ParParceladdress.class);
		logger.info("修改包裹地址表成功");
	}
	/**
	 * 插入包裹记录表
	 */
	public  void insertParParcelconnection(String memberId, String sendName,String sendPhone,String sendAddress,
			 String deliveryAddress,String netId,String netName,String expWaybillNum,
             String code,String compId,Date date,Long pid ,String versionId,String terminalId,String roleId) {
		MemberInfoVO memberInfoVO = this.getValByMemberId(memberId);
		ParParcelconnection parParcelconnection = new ParParcelconnection();// 包裹收派过程信息监控表
		try {
			parParcelconnection.setUid(IdWorker.getIdWorker().nextId());
			if (PubMethod.isEmpty(pid)) {
				pid=IdWorker.getIdWorker().nextId();//揽收记录用的
			}
			parParcelconnection.setParId(pid);// 包裹id
			parParcelconnection.setExpWaybillNum(expWaybillNum);// 运单号
			parParcelconnection.setCode(code);//揽散件问题
			parParcelconnection.setNetId(Long.parseLong(netId));//快递公司
			parParcelconnection.setNetName(netName);
			parParcelconnection.setMobilePhone(PubMethod.isEmpty(sendPhone) ? expWaybillNum : sendPhone);// 片键---包裹监控包---
			parParcelconnection.setDeliveryName(sendName);//交付人姓名
			parParcelconnection.setDeliveryAddress(sendAddress);//交付人地址
			parParcelconnection.setDeliveryMobile(sendPhone);//交付人手机号
			parParcelconnection.setDeliveryUnits("");		//交互单位
			
			parParcelconnection.setCreateTime(date);// 创建交互时间
			parParcelconnection.setExpMemberSuccessFlag((short) 0);// 取派件成功标志	
			parParcelconnection.setVersionId(versionId);
			parParcelconnection.setTerminalId(terminalId);		
			// 揽收人------站点Id------------------

			parParcelconnection.setRecCompId(Long.valueOf(PubMethod.isEmpty(compId) ? "0" : compId));
			parParcelconnection.setRecCosignFlag((short) 1);// 接受人动作标示 1揽收； 2派件 4,接单
			parParcelconnection.setRecMemberId(Long.valueOf(memberId));// 接受人id
			parParcelconnection.setRecName(memberInfoVO.getMemberName());
			parParcelconnection.setRecMobile(memberInfoVO.getMemberPhone());//电话
			parParcelconnection.setRecUnits(memberInfoVO.getCompName());//接受单位	
			if("2".equals(roleId)||"3".equals(roleId)){
				parParcelconnection.setRecNetId(null);
				parParcelconnection.setRecnetName(null);
			}else{
				parParcelconnection.setRecNetId(Long.valueOf(PubMethod.isEmpty(memberInfoVO.getNetId()) ? "0" : memberInfoVO.getNetId()));
				parParcelconnection.setRecnetName(memberInfoVO.getNetName());
			}	
			if(!PubMethod.isEmpty(code)){//揽散件特殊
				parParcelconnection.setNetFlag(true);//作业记录上传成功
			}	
			mongoTemplate.insert(parParcelconnection);// 插入信息监控表
			logger.info("====== 插入包裹记录的数据 ====成功==== parParcelconnection ......");
		} catch (Exception e) {
            e.printStackTrace();
            logger.info("揽散件不能有异常=======");
		}										
	}
	/**
	 * 揽收中的重新上传
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> againUpload(String memberId,String netId, String netName, String expWaybillNum,
			String code, Long parcelId) {	
		logger.info("==判断是否授权=======查询国通接==（揽散件重新下单）包裹上传===");
		CooperationExpCompanyAuth querysqInfo = this.querysqInfo(memberId,netId);	//授权信息
		BasNetInfo basNetInfo = basNetInfoMapper.queryNetAuthByNetId(Long.valueOf(netId));
		logger.info("==判断是否授权======netId:"+netId+"========"+basNetInfo.getApiParcelUploadFlag()+"========="+basNetInfo.getAuthStatus());
		Map<String, Object> maps = new HashMap<>();	
		if (!PubMethod.isEmpty(querysqInfo)&&basNetInfo.getAuthStatus() ==1&&basNetInfo.getApiParcelUploadFlag()==1) {	
			Query query = new Query();
			query.addCriteria(Criteria.where("takeMemberId").is(Long.parseLong(memberId)));
			if(!PubMethod.isEmpty(expWaybillNum)){
				query.addCriteria(Criteria.where("expWaybillNum").is(expWaybillNum));
			}else if(!PubMethod.isEmpty(code)){
				query.addCriteria(Criteria.where("code").is(code));
			}
			ParParceladdress par = this.mongoTemplate.findOne(query,ParParceladdress.class)==null?
					null: this.mongoTemplate.findOne(query,ParParceladdress.class);		
			logger.info("code:"+code+"=========查询包裹=========par"+par);
			if(!PubMethod.isEmpty(par)){
				Map<String, Object> map = new HashMap<>();		
				map.put("txlogisticid", par.getTxlogisticid());//1物流订单号
				map.put("orderstatus", "1");//2订单状态				
				SimpleDateFormat simf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createordertime = simf.format(par.getCreateTime());
				map.put("createordertime", createordertime);//3下单时间
				if(!PubMethod.isEmpty(expWaybillNum)){
					map.put("fetchcode", expWaybillNum);// 4揽收码
					map.put("mailno", expWaybillNum);//5运单号
				}else{					
					map.put("fetchcode", code );// 揽收码										
					map.put("mailno", "");//运单号
				}				
				map.put("pickupoperatorcode", querysqInfo.getUserCode());//7取件员编号
				map.put("pickupoperatorname", querysqInfo.getUserName());//8取件员名称（必填） 快递登录接口返回的数据
				map.put("pickuppointcode", querysqInfo.getOrgCode());//9取件员网点编码
				map.put("pickuppointname", querysqInfo.getOrgName());//10取件网点名称					
				Map<String, Object> of = new HashMap<>();
				of.put("name", par.getSendName());//11发件人姓名
				of.put("mobile", par.getSendPhone());//12发件人手机
				of.put("prov", par.getSendProv());//13发件人省份
				of.put("city", par.getSendCity());//14发件人市区 以，分割
				//of.put("area", "");//15发件人区县
				of.put("address", par.getSendDetailed());//16发件人详细地址
				map.put("sender", of);//17发件人信息
				Map<String, Object> os = new HashMap<>();
				os.put("name", par.getAddresseeName());//18收件人姓名
				os.put("mobile",par.getAddresseePhone());//19
				os.put("prov",par.getAddresseeProv());//20
				os.put("city",par.getAddresseeCity());//21
				//os.put("area", "");//22
				os.put("address",par.getAddresseeDetailed());	//23		
				map.put("receiver", os);//收件人信息24	
				map.put("orderHasPrint", false);//面单是否打印 true:已打印 false:未打印	
				JSONObject param = new JSONObject();
				param.put("expId", netId);
				param.put("data",map);	
				String result = openPlatformHttpClient.uploadPackagenInfo(param.toJSONString());
				JSONObject jsons = JSONObject.parseObject(result);
				logger.info(result+"======第二次上传======jsons"+jsons);						
				List<Map<String ,String>> object = (List<Map<String ,String>>) jsons.get("responseitems");	
				String mailno = null;
				for (Map<String, String> map2 : object) {
					 mailno = map2.get("mailno");					
				    String success = map2.get("success");
					String reason = map2.get("reason");
					maps.put("success", success);
					maps.put("reason", reason);
					maps.put("parcelId", parcelId);
					maps.put("code", code);																														
					maps.put("expWaybillNum", mailno);//返回给手机端						
					maps.put("netId", netId);
					maps.put("netName", netName);
					logger.info(code+"：code=====揽收码转换成运单号==第二次上传====mailno："+mailno);
				}		
			}																																							 										
		}								
		return maps;		
	}	
}
