package net.okdi.apiV1.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasEmployeeAuditMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.DicAddressaidMapper;
import net.okdi.api.dao.HandleDateMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.service.DicAddressService;
import net.okdi.api.service.RobInfoService;
import net.okdi.apiV1.dao.BasCompInfoMapperV1;
import net.okdi.apiV1.dao.BasCompimageMapperV1;
import net.okdi.apiV1.dao.BasEmployeeAuditMapperV1;
import net.okdi.apiV1.dao.MemberInfoMapperV1;
import net.okdi.apiV1.dao.MemberInvitationRegisterMapper;
import net.okdi.apiV1.entity.PushMessageToSomeOne;
import net.okdi.apiV1.service.QueryNearInfoService;
import net.okdi.apiV2.service.impl.TelephoneRewardServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.AbstractHttpClient;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.DistanceUtil;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class QueryNearInfoServiceImpl extends AbstractHttpClient implements QueryNearInfoService{
	
	@Autowired
	private BasCompInfoMapperV1 basCompInfoMapperV1;
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;//站点
	@Autowired
	private MemberInvitationRegisterMapper memberInvitationRegisterMapper;
	
	@Autowired
	private BasEmployeeAuditMapperV1 basEmployeeAuditMapper;
	@Autowired
	private BasEmployeeAuditMapper auditMapper;
	@Autowired
	private MemberInfoMapperV1 memberInfoMapper;
	private static long DISTANCE_VALUE = 5000;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private DicAddressService parseService;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private DicAddressaidMapper dicAddressaidMapper;
	@Autowired
	private BasNetInfoMapper netInfoMapper;
	@Autowired
	private BasCompimageMapperV1 compimageMapper;
	@Autowired
	private RobInfoService robInfoService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private HandleDateMapper handleDateMapper;
	
	@Value("${compPic.readPath}")
	public String readPath;
	
	@Value("${share.readPath}")
	public String shardReadPath;
	
	@Value("${promo.okdi.inviteManager.url}")
	public String inviteManagerUrl;//邀请代收站
	
	@Value("${promo.okdi.inviteWebmaster.url}")
	public String inviteWebmasterUrl;//邀请站点站长
	
	@Value("${share_comp_title}")
	public String shareCompTitle;//邀请站长标题
	@Value("${share_comp_content}")
	public String shareCompContent;//邀请站长内容
	@Value("${share_col_title}")
	public String shareColTitle;//邀请代收站店长标题
	@Value("${share_col_content}")
	public String shareColContent;//邀请代收站店长内容
	
	@Value("${shortlink_http_url}")
	private String shortLinkUrl;//获取短链接KEY
	@Value("${longlink_http_url}")
	private String longLinkUrl;//短网址调转到长网址
	
	@Value("${pay_url}")
	private String payUrl; //新版财务url
	
	@Autowired
	private NoticeHttpClient noticeHttpClient;//短信httpclient
	public static final Log logger = LogFactory.getLog(QueryNearInfoServiceImpl.class);
	

	@Override
	public List<Map<String, Object>> queryCompInfoByRoleId(Double longitude, Double latitude, String roleId) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapParam = this.queryFromPosition(longitude,latitude);
		mapParam.put("roleId", roleId);
		if("1".equals(roleId)){//如果1的话是站长 查询的是周围5公里的未被领用的1003的站点
			list = this.basCompInfoMapperV1.queryCompInfoByLongitudeAndLatitude(mapParam);
			
		}else if("0".equals(roleId) || "-1".equals(roleId)){
			list = this.basCompInfoMapperV1.queryCompInfoByRoleId(mapParam);
		}else if("2".equals(roleId) || "3".equals(roleId)){//如果是2的话 代表的是代收站的店长 查询的是周围5公里的未被领用的代收点
			list = this.basCompInfoMapperV1.queryCollectionByRoleId(mapParam);//如果是3的话 代表的是代收站的店员 查询的是周围5公里的已被领取的代收点信息和入驻的代收站信息
		}
		for(Map<String,Object> map : list){
			double distance=DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(String.valueOf(map.get("latitude"))), Double.parseDouble(String.valueOf(map.get("longitude"))));
			if(distance - DISTANCE_VALUE <= 0){
				Map<String,Object> mapResult = new HashMap<String,Object>();
				mapResult.put("compId", String.valueOf(map.get("compId")));
				mapResult.put("compName", String.valueOf(map.get("compName")));
				mapResult.put("compAddress", String.valueOf(map.get("compAddress")));
				mapResult.put("compTypeNum", String.valueOf(map.get("compTypeNum")));
				mapResult.put("distance", distance);
				BasNetInfo basNetInfo = this.robInfoService.queryBasNetInfo(Long.parseLong(String.valueOf(map.get("netId"))));
				mapResult.put("netId", basNetInfo.getNetId());
				mapResult.put("netName", basNetInfo.getNetName());
				Map<String,Object> mapImg = this.compimageMapper.queryCompPic(Long.parseLong(String.valueOf(map.get("compId"))));
				mapResult.put("compImgUrl", PubMethod.isEmpty(mapImg)?"": readPath+String.valueOf(mapImg.get("imageUrl")));
				mapResult.put("longitude", String.valueOf(map.get("longitude")));
				mapResult.put("latitude", String.valueOf(map.get("latitude")));
				mapResult.put("resPhone", String.valueOf(map.get("resPhone")));
				listResult.add(mapResult);
			}
			
		}
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
	@Override
	public List<Map<String, Object>> queryWechatCompInfoByRoleId(Double longitude, Double latitude) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapParam = this.queryFromPosition(longitude,latitude);
		long start = System.currentTimeMillis();
			list = this.basCompInfoMapperV1.queryWechatCompInfoByLongitudeAndLatitude(mapParam);//查询附近站点  comp_type_num  (1006,1050)
		long end = System.currentTimeMillis();
		for(Map<String,Object> map : list){
			double distance=DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(String.valueOf(map.get("latitude"))), Double.parseDouble(String.valueOf(map.get("longitude"))));
			if(distance - DISTANCE_VALUE <= 0){
				Map<String,Object> mapResult = new HashMap<String,Object>();
				mapResult.put("compId", String.valueOf(map.get("compId")));
				mapResult.put("compName", String.valueOf(map.get("compName")));
				mapResult.put("compAddress", String.valueOf(map.get("compAddress")));
				mapResult.put("compTypeNum", String.valueOf(map.get("compTypeNum")));
				mapResult.put("distance", distance);
				//BasNetInfo basNetInfo = this.robInfoService.queryBasNetInfo(Long.parseLong(String.valueOf(map.get("netId"))));
				mapResult.put("netId", String.valueOf(map.get("netId")));
				mapResult.put("netName", String.valueOf(map.get("netName")));
				//Map<String,Object> mapImg = this.compimageMapper.queryCompPic(Long.parseLong(String.valueOf(map.get("compId"))));
				mapResult.put("compImgUrl",readPath+String.valueOf(map.get("netId"))+".jpg");
				mapResult.put("longitude", String.valueOf(map.get("longitude")));
				mapResult.put("latitude", String.valueOf(map.get("latitude")));
				if(map.get("resPhone")==null||map.get("resPhone")=="null"){
					mapResult.put("resPhone","");
				}else{
					mapResult.put("resPhone", String.valueOf(map.get("resPhone")));
				}
				listResult.add(mapResult);
			}
		}
		long end2 = System.currentTimeMillis();
		Collections.sort(listResult, new Comparator<Object>() {  
			@SuppressWarnings("unchecked")
			public int compare(Object a, Object b) {
				double one = Double.parseDouble(String.valueOf(((Map<String,Object>)a).get("distance")));  
				double two = Double.parseDouble(String.valueOf(((Map<String,Object>)b).get("distance")));  
				int k = (int)(one - two);
				return k  ;   
			}  
		});
		long end3 = System.currentTimeMillis();
		System.out.println("第一次: " + (end - start) + ".循环：" + (end2 - end) + ". " + (end3 - end2));
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
	public List<Map<String, Object>> queryCompInfoByCompName(Double longitude, Double latitude, String roleId,
			String compName) {
		Map<String, Object> Resultmap = parseService.parseAddrByList(String.valueOf(latitude),String.valueOf(longitude));
		Long addressId = Long.parseLong(String.valueOf(Resultmap.get("addressId")));
		DicAddressaid address = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
		if(PubMethod.isEmpty(address)){
			address = this.dicAddressaidMapper.findById(addressId);
		}
		if(!PubMethod.isEmpty(address) && address.getAddressLevel()>=5){
			addressId=address.getCountyId();
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//通过addressId 和名字去 模糊查询库里的列表
		list = this.redisService.get("nearCompInfoByCompNameCache", addressId+"-"+compName+"-"+roleId, List.class);
		if(PubMethod.isEmpty(list)){
			list = this.basCompInfoMapperV1.queryCompInfoByCompName(addressId,compName,roleId,readPath);
			this.redisService.put("nearCompInfoByCompNameCache", addressId+"-"+compName+"-"+roleId, list);
		}

		return list;
	}

	@Override
	public int queryVerifyCode(String mobile, String randomCode) {
		this.redisService.put("randomCode-mobile-cache", mobile, randomCode);
		return 1;
	}

	@Override
	public Map<String, Object> queryShareInfo(String compTypeNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		if("1006".equals(compTypeNum)){//邀请站点站长
			map.put("shareTitle", shareCompTitle);
			map.put("shareContent", shareCompContent);
			map.put("shareUrl", inviteWebmasterUrl);
			map.put("logoUrl", shardReadPath+"dsz.png");
		}else if("1040".equals(compTypeNum)){//邀请代收站站长
			map.put("shareTitle", shareColTitle);
			map.put("shareContent", shareColContent);
			map.put("shareUrl", inviteManagerUrl);
			map.put("logoUrl", shardReadPath+"dsz.png");
		}
		return map;
	}

	@Override
	public Map<String, Object> getValidationStatus(String mobile) {
		//通过手机号查询memberId 
		MemberInfo memberInfo = this.redisService.get("memberInfo-mobile-cache", mobile, MemberInfo.class);
		if(PubMethod.isEmpty(memberInfo)){
			memberInfo = this.memberInfoMapper.queryMemberInfoByMemberPhone(mobile);
			this.redisService.put("memberInfo-mobile-cache", mobile, memberInfo);
		}
		Long memberId = memberInfo.getMemberId();
		//BasEmployeeAudit basEmployeeAudit = this.basEmployeeAuditMapper.queryRoleInfoAndRaltion(memberId,1L);
//		Map<String, Object> map = this.basEmployeeAuditMapper.queryRoleInfoAndRaltion(memberId);
		Map<String, Object> map = this.queryRoleInfoAndRaltion(memberId);
		
//		//是否邀请别人 但是别人没有来注册
//		MemberInvitationRegister memberInvitationRegister = this.memberInvitationRegisterMapper.queryIsRegistered(memberId);
//		
//		if(!PubMethod.isEmpty(memberInvitationRegister)){
//			map.put("roleId", memberInvitationRegister.getToMemberRoleId());//邀请的角色  1是站长 2是店长
//			map.put("isHaveNet", "-1");//是否选择快递公司
//			if(memberInvitationRegister.getToMemberRoleId()==1){//如果邀请的是站长
//				map.put("isHaveNet", PubMethod.isEmpty(memberInvitationRegister.getToMemberNetId())?"0":"1");//是否选择快递公司
//			}
//			map.put("isInvitaRegister", memberInvitationRegister.getIsRegistered());//是否注册
//			
//		}else{
//			map.put("invitaRegisterRoleId", "-1");//谁也没邀请
//		}
//		map.put("invitaRegisterPhone",PubMethod.isEmpty(memberInvitationRegister)?-1:memberInvitationRegister.getToMemberPhone());
//		BasCompInfo basCompInfo = null;
//		if(!PubMethod.isEmpty((map.get("compId")))){
//			basCompInfo = this.basCompInfoMapper.findById(Long.parseLong(String.valueOf(map.get("compId"))));
//		}
//		map.put("compStatus",!PubMethod.isEmpty(basCompInfo)?basCompInfo.getCompStatus():-1);
//		if("1".equals(String.valueOf(map.get("roleId")))){
//			map.put("relationFlag", !PubMethod.isEmpty(basCompInfo)?basCompInfo.getCompStatus():-1);
//		}
		map.put("headImgUrl",constPool.getHeadImgPath()+memberId+".jpg");
		return map;
	}

	public Map<String, Object> queryRoleInfoAndRaltion(Long memberId) {
		Map<String, Object> map = new HashMap<String,Object>();
		BasEmployeeAudit verifyInfo = this.redisService.get("verifyInfo-memberId-cache", String.valueOf(memberId), BasEmployeeAudit.class);
		if(PubMethod.isEmpty(verifyInfo)){
			verifyInfo = this.basEmployeeAuditMapper.queryBasEmployeeAudit(memberId,1L);
			this.redisService.put("verifyInfo-memberId-cache", String.valueOf(memberId), verifyInfo);
		}
		BasEmployeeAudit relationInfo = this.redisService.get("relationInfo-memberId-cache", String.valueOf(memberId), BasEmployeeAudit.class);
		if(PubMethod.isEmpty(relationInfo)){
			relationInfo = this.basEmployeeAuditMapper.queryBasEmployeeAudit(memberId,2L);
			this.redisService.put("relationInfo-memberId-cache", String.valueOf(memberId), relationInfo);
		}
		BasEmployeeAudit expressInfo = this.redisService.get("expressInfo-memberId-cache", String.valueOf(memberId), BasEmployeeAudit.class);
		if(PubMethod.isEmpty(expressInfo)){
			expressInfo = this.basEmployeeAuditMapper.queryBasEmployeeAudit(memberId,3L);
			this.redisService.put("expressInfo-memberId-cache", String.valueOf(memberId), expressInfo);
		}
		BasCompInfo basCompInfo = this.robInfoService.queryBasCompInfo(PubMethod.isEmpty(relationInfo)?null:relationInfo.getAuditComp());
//		if(!PubMethod.isEmpty(PubMethod.isEmpty(relationInfo)?null:relationInfo.getAuditComp())){
//			basCompInfo = this.basCompInfoMapper.findById(PubMethod.isEmpty(relationInfo)?null:relationInfo.getAuditComp());
//		}
		map.put("compStatus",!PubMethod.isEmpty(basCompInfo)?basCompInfo.getCompStatus():-1);
		map.put("relationFlag", PubMethod.isEmpty(relationInfo)?"-1":relationInfo.getAuditOpinion());
		if(!PubMethod.isEmpty(relationInfo) && !PubMethod.isEmpty(relationInfo.getApplicationTime())){
			map.put("applicationTime", relationInfo.getApplicationTime().getTime());
		}else{
			map.put("applicationTime", -1);
		}
		map.put("expressFlag", PubMethod.isEmpty(expressInfo)?"-1":expressInfo.getAuditOpinion());
		map.put("verifyFlag", PubMethod.isEmpty(verifyInfo)?"-1":verifyInfo.getAuditOpinion());
		map.put("roleId", PubMethod.isEmpty(relationInfo)?null:relationInfo.getApplicationRoleType());
		map.put("compId", PubMethod.isEmpty(relationInfo)?null:relationInfo.getAuditComp());
		if("1".equals(String.valueOf(map.get("roleId")))){
			map.put("relationFlag", !PubMethod.isEmpty(basCompInfo)?basCompInfo.getCompStatus():-1);
		}
		map.put("memberId", memberId);
		//2016年1月6日10:37:07 增加返回字段，快递员姓名by zhaohu
		MemberInfo memberInfo = this.memberInfoMapper.queryMemberInfoByMemberId(memberId);
		if (!PubMethod.isEmpty(memberInfo)) {
			map.put("memberName", memberInfo.getMemberName());
		}else {
			map.put("memberName", "");
		}
		//2016年1月12日18:18:23 增加返回字段,是否是虚拟站点下的快递员 by zhaohu
		BasCompInfo compInfo = this.basCompInfoMapper.isVirtualuser(memberId);
		if (!PubMethod.isEmpty(compInfo)) {
			Short registWay = compInfo.getCompRegistWay();
			if ("-1".equals(String.valueOf(registWay))) {
				map.put("isVirtualuser", "1");
			}else {
				map.put("isVirtualuser", "0");
			}
		}else {
			map.put("isVirtualuser", "");
			logger.info("此人没有站点归属信息！++++");
		}
		//2016年3月1日17:21:25 增加返回字段，钱包账户id---accountId
		String accountId = "";
		String url = payUrl;
		String methodName = "ws/account/get/memberId";
		Map<String, String> param = new HashMap<String, String>();
		param.put("memberId", String.valueOf(memberId));
		logger.info("openAPI 使用外部系统memberId查询账户接口: url =" + url + " methodName="	+ methodName + " map=" + param);
		String result = this.Post(url + methodName, param);
		logger.info("publicapi 使用外部系统memberId查询账户接口: result =" + result);
		JSONObject res = JSON.parseObject(result);
		if(res.get("success").toString().equals("false")){
			map.put("accountId", accountId);
		}else{
			Map resMap = (Map) res.get("data");
			accountId = (String) resMap.get("account_id");
			logger.info("外部系统通过memberId查询账户accountId："+accountId);
			map.put("accountId", accountId);
		}
		
		return map;
	}

	@Override
	public void invitationIntoCompInfo(String fromMemberPhone, String toMemberPhone, String invitationType) {
		String noticeContent = "";
		if("1006".equals(invitationType)){//邀请站长
			Long startTime1 = System.currentTimeMillis();	
			noticeContent = "您的下属快递员"+fromMemberPhone+"，邀请您前往好递快递员平台，验证并开通站长管理权限。点击验证:"+getShortUrlV1();
			Long startTime2 = System.currentTimeMillis();
			logger.info("这是发送短信的时间差         :  "+(startTime2-startTime1));	
		}else if("1040".equals(invitationType)){//邀请店长
			noticeContent = "您的店员 "+fromMemberPhone+"，邀请您前往好递平台开通代收发包裹服务，狂卷周边客源，抢占无限商机。点击开通："+getShortUrlV2();
		}
		
		noticeThread sThread=new noticeThread(toMemberPhone,noticeContent);
		sThread.setDaemon(false);
		taskExecutor.execute(sThread);
	}
	
	
	class noticeThread extends Thread{
		private String toMemberPhone=null;
		private String noticeContent=null;
		public noticeThread(String toMemberPhone, String noticeContent) {
			this.toMemberPhone=toMemberPhone;
			this.noticeContent=noticeContent;
		}
		public void run(){
			noticeHttpClient.doSmsSend("02", 0L, toMemberPhone ,
					noticeContent, "");
		}
	}
	
	private String getShortUrlV1() {
		Long startTime = System.currentTimeMillis();
		Map<String, String> map=new HashMap<>();
		String param="1=1";
		map.put("sys", "1");
		map.put("text", inviteWebmasterUrl+"?"+param);
		String shortKey=noticeHttpClient.Post(shortLinkUrl, map);
		Long startTime1 = System.currentTimeMillis();
		logger.info("这是获取短链接的时间差         :  "+(startTime1-startTime));
		return longLinkUrl+shortKey;
	}
	private String getShortUrlV2() {
		Map<String, String> map=new HashMap<>();
		String param="1=1";
		map.put("sys", "1");
		map.put("text", inviteManagerUrl+"?"+param);
		String shortKey=noticeHttpClient.Post(shortLinkUrl, map);
		return longLinkUrl+shortKey;
	}

	@Override
	public void initVirtualCompInfo() {
		//每个网络都新建站点和代收点
		List<BasNetInfo> listNetInfo = this.netInfoMapper.queryNetInfo();
		for(BasNetInfo basNetInfo : listNetInfo){
			//每个网络添加1个站点和1个代收站
			BasCompInfo basCompInfo = new BasCompInfo();
			basCompInfo.setCompId(IdWorker.getIdWorker().nextId());
			basCompInfo.setCompName("未标记站点");
			basCompInfo.setCompRegistWay((short)-1);//做个标记
			basCompInfo.setBelongToNetId(basNetInfo.getNetId());//快递公司
			basCompInfo.setCompTypeNum("1006");
			basCompInfo.setCompStatus((short)2);
			basCompInfo.setCreateTime(new Date());
			basCompInfo.setBelongToCompId(-1L);
			basCompInfo.setWriteSendStatus((short)-1);
			basCompInfo.setGoodsPaymentStatus((short)-1);
			basCompInfo.setTaoGoodsPaymentStatus((short)-1);
			this.basCompInfoMapperV1.insert(basCompInfo);
			BasCompInfo basCompInfoV2 = new BasCompInfo();
			basCompInfoV2.setCompId(IdWorker.getIdWorker().nextId());
			basCompInfoV2.setCompName("未标记代收站");
			basCompInfoV2.setCompRegistWay((short)-1);//做个标记
			basCompInfoV2.setBelongToNetId(basNetInfo.getNetId());//快递公司
			basCompInfoV2.setCompTypeNum("1040");
			basCompInfoV2.setCompStatus((short)2);
			basCompInfoV2.setCreateTime(new Date());
			basCompInfoV2.setBelongToCompId(-1L);
			basCompInfoV2.setWriteSendStatus((short)-1);
			basCompInfoV2.setGoodsPaymentStatus((short)-1);
			basCompInfoV2.setTaoGoodsPaymentStatus((short)-1);
			this.basCompInfoMapperV1.insert(basCompInfoV2);
		}
		
	}

	@Override
	public Map<String,Object> invitationHaveNetInfo(Long memberId, Long netId,Short roleId) {
		Map<String,Object> map = new HashMap<String,Object>();
		///将这个人和虚拟的站点建立关系
		//先查出这个netId下的虚拟站点
		BasCompInfo basCompInfo = this.basCompInfoMapperV1.queryVirtualCompInfo(netId,"1006");
		Long compId = basCompInfo.getCompId();
		BasEmployeeAudit basEmployeeAudit = new BasEmployeeAudit();
		basEmployeeAudit.setId(IdWorker.getIdWorker().nextId());
		basEmployeeAudit.setMemberId(memberId);
		basEmployeeAudit.setAuditComp(compId);
		basEmployeeAudit.setAuditItem((short)2);
		basEmployeeAudit.setAuditOpinion((short)-1);
		basEmployeeAudit.setApplicationRoleType(roleId);
		basEmployeeAudit.setAuditTime(new Date());
		//审核表中加入1条 归属-1的
		int count = basEmployeeAuditMapper.getRelation(basEmployeeAudit);
		System.out.println("未完善的归属的数量"+count);
		if(count<=0){
			int a = this.basEmployeeAuditMapper.inserRelation(basEmployeeAudit);
		}
		
		map.put("compId", compId);
		return map;
	}

	@Override
	public Map<String, Object> queryAuthenticationInfo(String mobile) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		MemberInfo memberInfo = this.memberInfoMapper.queryMemberInfoByMemberPhone(mobile);
		if(PubMethod.isEmpty(memberInfo)){
			throw new ServiceException("000000001", "无该手机号信息");
		}
		Long memberId = memberInfo.getMemberId();
		
		BasEmployeeAudit verifyInfo = this.basEmployeeAuditMapper.queryBasEmployeeAudit(memberId,1L);
		BasEmployeeAudit expressInfo = this.basEmployeeAuditMapper.queryBasEmployeeAudit(memberId,3L);
		BasEmployeeAudit relationInfo = this.basEmployeeAuditMapper.queryBasEmployeeAudit(memberId,2L);
		logger.info("verifyInfo 是否为空    "+JSON.toJSONString(verifyInfo));
		logger.info("expressInfo 是否为空    "+JSON.toJSONString(verifyInfo));
		logger.info("relationInfo 是否为空    "+JSON.toJSONString(verifyInfo));
		map.put("relationFlag", PubMethod.isEmpty(relationInfo)?"-1":relationInfo.getAuditOpinion());
		map.put("auditId", PubMethod.isEmpty(relationInfo)?"-1":relationInfo.getId());
		map.put("expressFlag", PubMethod.isEmpty(expressInfo)?"-1":expressInfo.getAuditOpinion());
		map.put("verifyFlag", PubMethod.isEmpty(verifyInfo)?"-1":verifyInfo.getAuditOpinion());
		map.put("roleId", PubMethod.isEmpty(relationInfo)?null:relationInfo.getApplicationRoleType());
		map.put("compId", PubMethod.isEmpty(relationInfo)?null:relationInfo.getAuditComp());
		map.put("memberId", memberId);
		map.put("message", "verifyInfo 是否为空    "+JSON.toJSONString(verifyInfo)+"   expressInfo 是否为空    "+JSON.toJSONString(verifyInfo)+"  relationInfo 是否为空    "+JSON.toJSONString(verifyInfo));
		BasCompInfo basCompInfo = null;
		if(!PubMethod.isEmpty(PubMethod.isEmpty(relationInfo)?null:relationInfo.getAuditComp())){
			basCompInfo = this.basCompInfoMapper.findById(PubMethod.isEmpty(relationInfo)?null:relationInfo.getAuditComp());
		}
		map.put("compStatus",!PubMethod.isEmpty(basCompInfo)?basCompInfo.getCompStatus():-1);
		return map;
	}

	@Override
	public void deleteWrongData(Long auditId) {
		//this.auditMapper.delete(auditId);
		this.basEmployeeAuditMapper.deleteWrongData(auditId);
		this.redisService.removeAll("verifyInfo-memberId-cache");
		this.redisService.removeAll("relationInfo-memberId-cache");
		this.redisService.removeAll("expressInfo-memberId-cache");
		this.redisService.removeAll("findShopowner1xxx");
		this.redisService.removeAll("findShopowner2xxx");
		this.redisService.removeAll("findShopowner3xxx");
	}

	@Override
	public void deleteRedisInfo(String redisName, String key) {
		if(PubMethod.isEmpty(key)){
			this.redisService.removeAll(redisName);
			this.ehcacheService.removeAll(redisName);
		}else{
			this.redisService.remove(redisName, key);
			this.ehcacheService.remove(redisName, key);
		}
		
	}

	@Override
	public Map<String, Object> queryPasswordByMobile(String mobile) {
		DesEncrypt des = new DesEncrypt();
		Map<String, Object> map = this.handleDateMapper.queryPasswordByMobile(mobile);
		String password = des.convertPwd(String.valueOf(map.get("loginPassword")), "ABC");
		map.put("loginPassword", password);
		return map;
	}

	@Override
	public List<BasEmployeeAudit> queryWrongDataByMobile(String mobile) {
		List<BasEmployeeAudit> list = this.basEmployeeAuditMapper.queryWrongDataByMobile(mobile);
		return list;
	}

	@Override
	public Object parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryUnReadMessage(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		//登录的时候 未读的公告数量
		Query query = new Query();
		query.addCriteria(Criteria.where("memberPhone").is(mobile).and("isRead").is((short)0));
		long unReadCount = this.mongoTemplate.count(query, PushMessageToSomeOne.class);
		map.put("unReadCount", unReadCount);
		return map;
	}
}
