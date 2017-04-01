package net.okdi.apiV1.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasCompbusiness;
import net.okdi.api.entity.BasCompimage;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.service.DicAddressService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.apiV1.dao.BasCompBusinessMapperV1;
import net.okdi.apiV1.dao.BasCompInfoMapperV1;
import net.okdi.apiV1.dao.BasCompimageMapperV1;
import net.okdi.apiV1.dao.BasEmployeeRelationMapperV1;
import net.okdi.apiV1.dao.BasNetInfoMapperV1;
import net.okdi.apiV1.entity.AnnounceMessageInfo;
import net.okdi.apiV1.entity.PushMessageToSomeOne;
import net.okdi.apiV1.service.ExpressSiteService;
import net.okdi.apiV1.service.PushMessageService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;

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

import com.amssy.common.util.primarykey.IdWorker;

@Service
public class ExpressSiteServiceImpl implements ExpressSiteService {
	
	public static final Log logger = LogFactory.getLog(ExpressSiteServiceImpl.class);
    
	@Autowired
	private EhcacheService ehcacheService;//缓存
	
	@Autowired
	private RedisService redisService;  //缓存
	@Autowired
	private DicAddressService parseService;
	@Autowired
	private SendNoticeService sendNoticeService;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private BasCompBusinessMapperV1 compbusinessInfoMapper;
	@Autowired
	private BasEmployeeRelationMapperV1 basEmployeeRelationMapper;
	
	@Autowired
	private BasCompInfoMapperV1 compInfoMapper;
	
	@Autowired
	private BasNetInfoMapperV1 netInfoMapper;
	
	@Autowired
	private BasCompimageMapperV1 compimageMapper;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Value("${compPic.readPath}")
	public String readPath;
	@Value("${life_http_url}")
	private String lifeHttpUrl;
	@Value("${shortlink_http_url}")
	private String shortLinkUrl;//获取短链接KEY
	@Value("${longlink_http_url}")
	private String longLinkUrl;//短网址调转到长网址

	@Autowired
	private NoticeHttpClient noticeHttpClient;//短信httpclient
	
	
	
	
	
	/**
	 * 
	 * @第1个接口： 查询网点详情信息
	 * 
	 */
	@Override
	public Map<String, Object> queryCompBasicInfo(Long compId) throws ServiceException {
		if (PubMethod.isEmpty(compId)) {
			return null;
		}
		BasCompInfo compInfo = redisService.get("compCache", compId.toString(), BasCompInfo.class);
		if (PubMethod.isEmpty(compInfo)) {
			
			compInfo = this.compInfoMapper.findById(compId);
			//System.out.println("------------------------查询的BasCompInfo:"+compInfo.toString());
			redisService.put("compCache", compId.toString(), compInfo);
		}
		if (PubMethod.isEmpty(compInfo)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.queryCompBasicInfo.001", "获取网点基础信息参数异常");
		}
		BasNetInfo netInfo = null;
		if(!PubMethod.isEmpty(compInfo.getBelongToNetId())){
			netInfo = redisService.get("netCache", compInfo.getBelongToNetId().toString(), BasNetInfo.class);
			if (PubMethod.isEmpty(netInfo)) {
				netInfo=this.netInfoMapper.findById(compInfo.getBelongToNetId());
			}
		}
		//------------start-----------
		BasCompbusiness compbusiness = redisService.get("compBusinessCache", compId.toString(),BasCompbusiness.class);
		if (PubMethod.isEmpty(compbusiness) && !redisService.getByKey("compBusinessCache", compId.toString())) {
			compbusiness = this.compbusinessInfoMapper.findById(compId);
			redisService.put("compBusinessCache", compId.toString(),compbusiness);
		} 
		//---------------end------
		Map<String, Object> compInfoMap = new HashMap<String, Object>();
		//----------------start-------------
		if (compbusiness != null) {
			compInfoMap.put("responsible", compbusiness.getResponsible());
			compInfoMap.put("responsibleIdNum", compbusiness.getResponsibleIdNum());
			compInfoMap.put("responsibleTelephone", compbusiness.getResponsibleTelephone());
		}else{
			compInfoMap.put("responsible", "");
			compInfoMap.put("responsibleIdNum","");
			compInfoMap.put("responsibleTelephone","");
		}
		//-----------------end------------
		compInfoMap.put("compId", compInfo.getCompId());
		compInfoMap.put("netId", compInfo.getBelongToNetId());
		if(!PubMethod.isEmpty(netInfo)){
			compInfoMap.put("netName", netInfo.getNetName());
		}else {
			compInfoMap.put("netName", "");
		}
		compInfoMap.put("compTypeNum", compInfo.getCompTypeNum());
		try {
			compInfoMap.put("useCompId", "-1".equals(compInfo.getRelationCompId()+"") ? null : compInfo.getRelationCompId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		compInfoMap.put("compName", compInfo.getCompName());
		compInfoMap.put("compTelephone", (PubMethod.isEmpty(compInfo.getCompTelephone()) || "-".equals(compInfo.getCompTelephone())) ? compInfo.getCompMobile() : compInfo
				.getCompTelephone());
		compInfoMap.put("addressId", compInfo.getCompAddressId());
		String addr =compInfo.getCompAddress();
		if(!PubMethod.isEmpty(addr)){
			if(addr.indexOf("|")>=0){
				String[] strArr = addr.split("\\|");
				if(strArr.length>=2){
					compInfoMap.put("province", addr.split("\\|")[0]);
					compInfoMap.put("address", addr.split("\\|")[1]);
				}else{
					compInfoMap.put("province", addr.split("\\|")[0]);
					compInfoMap.put("address",null);
				}
				
			}else{
				compInfoMap.put("province", addr);
				compInfoMap.put("address",null);
			}
		}
		
		
		compInfoMap.put("longitude", compInfo.getLongitude());
		compInfoMap.put("latitude", compInfo.getLatitude());
		compInfoMap.put("compStatus", compInfo.getCompStatus());
		return compInfoMap;
	}
	
	
/*-收派员------------------------------------------start*/
	/**
	 * 
	 * 第1个接口：快递员通过手机号查询站长入住状态
	 */
	public Map<String, Object> queryJoinState(String member_phone) {
		String comp_id = compInfoMapper.queryJoinState1(member_phone);
		//System.out.println(">>>>>>>>>>>>>>>>>查询到的comp_id："+comp_id);
		if(PubMethod.isEmpty(comp_id)){
			throw new ServiceException("openapi.ExpressSiteServiceImpl.queryJoinState.001", "没有通过此手机号找到站长");
		}
		Map map = redisService.get("queryJoinState2", comp_id, Map.class);
		if (PubMethod.isEmpty(map)) {
			 map =compInfoMapper.queryJoinState2(comp_id);
			 redisService.put("queryJoinState2", comp_id, map);
	    }
		System.out.println("============map:"+map.toString());
	//	redisService.removeAll("queryJoinState3");
		/*String imgurl=redisService.get("queryJoinState33",comp_id,String.class);
		System.out.println("图片："+imgurl);
		if (PubMethod.isEmpty(imgurl)) {*/
		String	 imgurl=compInfoMapper.queryJoinState3(comp_id);
			// if(!PubMethod.isEmpty(imgurl)){
				// redisService.put("queryJoinState33",comp_id,imgurl);
			 //}
			 
	   // }
		
		String showPath = readPath;
		map.put("reverseImgUrl", showPath+imgurl);
		
		System.out.println(">>>>>>>>>>>>>>>>>查询到信息："+map.toString());
		
		
		return map;
	}
	
	/**
	 * 第2个接口：快递员---申请入站
	 */
	public void applyJoin(String member_id,String audit_comp,String application_role_type,  String audit_item){
		
		if(PubMethod.isEmpty(member_id)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.applyJoin","member_id参数异常");
		}
		if(PubMethod.isEmpty(audit_comp)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.applyJoin","audit_comp参数异常");
		}
		if(PubMethod.isEmpty(application_role_type)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.applyJoin","application_role_type参数异常");
		}
		if(PubMethod.isEmpty(audit_item)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.applyJoin","audit_item参数异常");
		}
		
	//	String application_time=new Date().toLocaleString();
		Long id =IdWorker.getIdWorker().nextId();
		System.out.println(">>>>>>>>>>>>>> 第1个接口：快递员----申请入站：");
		
		//插入员工审核表bas_employeeaudit
		compInfoMapper.deleteRelation(member_id);
		compInfoMapper.applyJoin( id,member_id, audit_comp, application_role_type, new Date(), audit_item,"0");
		
		//申请入站，如果身份和快递认证有审核信息，同时更新 身份和快递认证审核信息的compId
		//---------------2015年11月28日13:19:26---------------begin//
		int sf = this.compInfoMapper.queryCountSF(member_id);
		if(sf > 0){
			this.compInfoMapper.updateSFAuditInfo(member_id,audit_comp);//修改 身份认证信息的站点id--compId
		}
		int kd = this.compInfoMapper.queryCountKD(member_id);
		if (kd > 0) {
			this.compInfoMapper.updateKDAuditInfo(member_id,audit_comp);//修改 快递认证信息的站点id--compId
		}
		//---------------2015年11月28日13:19:26---------------end//
		
		//推送
		Map<String, Object> retMap= compInfoMapper.queryPushMessage(Long.parseLong(audit_comp));
		if(!PubMethod.isEmpty(retMap)){
			Long memberId = (Long) retMap.get("member_id");//登陆app的人的id
			String mob = (String) retMap.get("member_phone");//登陆app的人的电话
			sendNoticeService.applyJoinNet(memberId, mob, Short.parseShort("1"));//推送申请入站
		}
		
		ehcacheService.remove("memberInfochCache", String.valueOf(member_id));
		ehcacheService.remove("employeeCache", String.valueOf(audit_comp));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(audit_comp));
		ehcacheService.remove("memberInfoMobilechCache", String.valueOf(audit_comp));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(audit_comp));
		redisService.removeAll("verifyInfo-memberId-cache");
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		redisService.removeAll("findShopowner1xxx");
		redisService.removeAll("findShopowner2xxx");
		redisService.removeAll("findShopowner3xxx");

		
	}
	
	/**
	 * 第1个接口：快递员---申请入null站
	 */
	/*public void applyJoinNoSign(String member_id,String belongToNetId,String application_role_type, 
			 String toMemberPhone){
		
		if(PubMethod.isEmpty(member_id)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.applyJoin","member_id参数异常");
		}
		if(PubMethod.isEmpty(belongToNetId)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.applyJoin","belongToNetId参数异常");
		}
		if(PubMethod.isEmpty(application_role_type)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.applyJoin","application_role_type参数异常");
		}
		
		if(PubMethod.isEmpty(toMemberPhone)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.applyJoin","toMemberPhone参数异常");
		}
	
		
		
		String application_time=new Date().toLocaleString();
		Long id =IdWorker.getIdWorker().nextId();
		System.out.println(">>>>>>>>>>>>>> 第1个接口：快递员----申请入未标记站点：");
		
		
		BasCompInfo compInfo = new BasCompInfo();
		
		Long longID=IdWorker.getIdWorker().nextId();
		compInfo.setCompId(longID);
		compInfo.setCompName("未标记站点");
		compInfo.setBelongToNetId(Long.parseLong(belongToNetId)); //关联的网络
		compInfo.setCompAddress("");  //地址
		compInfo.setLongitude(null);
		compInfo.setLatitude(null);
		compInfo.setRelationCompId(-1L);  //无领用关系
		compInfo.setBelongToCompId((long) -1);
		compInfo.setCompTypeNum("1006");  //加盟公司
		compInfo.setCompStatus(Short.parseShort("-2"));//公司状态 -2:新添的未标记站点，-1:创建,0:提交待审核,1:审核通过,2: 审核不通过4:再次提交待审核',
		compInfo.setCompNum(null);    //公司标识代码
		compInfo.setCompShort(null);  //公司简称
		compInfo.setFirstLetter(PiyinUtil.cn2py("未标记站点".substring(0, 1)).toUpperCase());
		compInfo.setCompAddressId(null);  
		compInfo.setRegistFlag((short)1);// 已注册
		compInfo.setCompRegistWay((short)6);  //网点注册方式 6：手机端 
		compInfo.setWriteSendStatus((short) 0);
		compInfo.setGoodsPaymentStatus((short) 0);
		compInfo.setCreateTime(new Date());
		compInfo.setModifiyTime(new Date());
		compInfo.setTaoGoodsPaymentStatus((short) -1);
		compInfoMapper.insert(compInfo);
		                  //id,人员id,网点id,申请角色类型 1：站长 ,时间,审核项 1:身份审核,2:归属审核3快递员认证审核'
		compInfoMapper.applyJoin( id,member_id, null, application_role_type, application_time, "2","1");
		
		       // 主键id
					long intid = IdWorker.getIdWorker().nextId();
					  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					  String createTime = dateFormat.format(new Date());
			int isRegister = 0;
			 compInfoMapper.saveMemberInvitationRegister(intid, createTime,
							member_id, fromMemberPhone, Integer.parseInt(application_role_type), toMemberPhone,
							toRoleId,Long.parseLong(belongToNetId), isRegister);
			 
			 compInfoMapper.updateMemberInvitationRegister(Long.parseLong(member_id), toMemberPhone, Long.parseLong(belongToNetId))	;
		
	}*/
  
	/**
	 * 第6个接口：通过以下方式邀请店长入住, 微信, 短信
	 */
	 /* public String invite(String fromMemberId, String fromMemberPhone,
			  Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId) {
		
	    // 主键id
		long intid = IdWorker.getIdWorker().nextId();
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		  String createTime = dateFormat.format(new Date());
      int isRegister = 0;
     compInfoMapper.saveMemberInvitationRegister(intid, createTime,
    		 fromMemberId, fromMemberPhone, fromMemberRoleid, toMemberPhone,
				toRoleId,null, isRegister);
			
		
			
		String ret= noticeHttpClient.doSmsSend("02", 0L, toMemberPhone ,
					 "【好递】您的下属快递员"+fromMemberPhone+"，邀请您前往好递快递员平台，验证并开通站长管理权限。点击验证:"+getShortUrl(), "");
		System.out.println(">>>>>>发送手机信息返回值："+ret);	
		// TODO Auto-generated method stub
			//return	noticeHttpClient.doSmsSend("02", 0L, telephone , "【好递】手机号为 "+telephone+" 的小伙伴邀请您加入【店铺名称+代收点】，店铺成为代收点后后开一个店赚两份钱!下载地址XXXXXXXXXX", "");
		   return ret;
		}
		
		private String getShortUrl() {
			Map<String, String> map=new HashMap<>();
			map.put("sys", "1");
			map.put("text", lifeHttpUrl);
			String shortKey=noticeHttpClient.Post(shortLinkUrl, map);
			return longLinkUrl+shortKey;
		}*/
		
/*站长-----------------start------------------------------*/
		/**
		 * 
		 * 第1个接口
		 * @Description: 保存网点基本信息
		 */
		public String submitBasCompInfo(Long loginCompId, String compName, String belongToNetId, String county, 
				Double longitude, Double latitude,String address,String responsibleTelephone,String member_id) {
			
			if(PubMethod.isEmpty(loginCompId)){
				throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.submitBasCompInfo","loginCompId参数异常");
			}
			if(PubMethod.isEmpty(belongToNetId)){
				throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.submitBasCompInfo","belongToNetId参数异常");
			}
			if(PubMethod.isEmpty(member_id)){
				throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.submitBasCompInfo","member_id参数异常");
			}
			
			//Long relationCompId = redisService.get("queryRelationCompId", String.valueOf(loginCompId), Long.class);
			//if (PubMethod.isEmpty(relationCompId)) {
			Long	 relationCompId= compInfoMapper.queryRelationCompId(loginCompId);
			//	redisService.put("queryRelationCompId", String.valueOf(loginCompId), relationCompId);
			//}
			System.out.println("==========是否被领用:"+relationCompId);
		    if(!String.valueOf(relationCompId).equals("-1")){
		    	System.out.println("==========是否被领用FANHUI:"+relationCompId);
//		    	return "001"; //001:该网点已被领用
		    	throw new ServiceException("00099999", "此站点已被领用");
		    }
		/*	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String application_time = dateFormat.format(new Date());
			Long id =IdWorker.getIdWorker().nextId();
			//插入员工审核表bas_employeeaudit
			compInfoMapper.applyJoin( id,member_id, String.valueOf(loginCompId), "1", application_time, "2","1");*/
			
			
			BasCompInfo compInfo =redisService.get("compInfofindById", String.valueOf(loginCompId), BasCompInfo.class);
			if (PubMethod.isEmpty(compInfo)) {
				 compInfo = this.compInfoMapper.findById(loginCompId);
				redisService.put("compInfofindById", String.valueOf(loginCompId), compInfo);
				
		    }
			
			Map<String, Object> Resultmap = parseService.parseAddrByList(String.valueOf(latitude),String.valueOf(longitude));
			Long addressId = Long.parseLong(String.valueOf(Resultmap.get("addressId")));
			
			Long longID=IdWorker.getIdWorker().nextId();
			System.out.println("..........新的信息compid:"+longID);
			//1.插入一条站点数据
			compInfo.setCompId(longID);
			compInfo.setCompName(compName);
			compInfo.setBelongToNetId(Long.parseLong(belongToNetId)); //关联的网络
			compInfo.setCompAddress(county+"|"+address);  //地址
			compInfo.setLongitude(PubMethod.isEmpty(longitude) ? null : BigDecimal.valueOf(longitude));
			compInfo.setLatitude(PubMethod.isEmpty(latitude) ? null : BigDecimal.valueOf(latitude));
			compInfo.setLatitude(new BigDecimal(latitude));
			compInfo.setRelationCompId(longID);  //从网络信息生成的,标记被自己领用
			compInfo.setBelongToCompId((long) -1);
			compInfo.setCompTypeNum("1006");  //加盟公司
			compInfo.setCompStatus(Short.parseShort("-1"));//公司状态 -1:创建,0:提交待审核,1:审核通过,2: 审核不通过4:再次提交待审核',
			compInfo.setCompNum(null);    //公司标识代码
			compInfo.setCompShort(null);  //公司简称
			compInfo.setFirstLetter(PiyinUtil.cn2py(compName.substring(0, 1)).toUpperCase());
			compInfo.setCompAddressId(addressId);  
			compInfo.setRegistFlag((short)1);// 已注册
			compInfo.setCompRegistWay((short)6);  //网点注册方式 6：手机端 
			compInfo.setWriteSendStatus((short) 0);
			compInfo.setGoodsPaymentStatus((short) 0);
			compInfo.setCreateTime(new Date());
			compInfo.setModifiyTime(new Date());
			compInfo.setTaoGoodsPaymentStatus((short) -1);
			compInfo.setCreateUser(Long.parseLong(member_id));
			
			compInfoMapper.insert(compInfo);
			
			compInfoMapper.updateCompTypeNum(longID, loginCompId);  //2.更新原网络抓取站点的relation_comp_id
			System.out.println("..........更新成功");
			//redisService.removeAll("queryRelationCompId");
			//redisService.put("queryRelationCompId", String.valueOf(loginCompId), longID); //更新的id插入缓存
			
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String createTime = dateFormat.format(new Date());
			// 3.插入 到bas_employeeaudit 1条数据
			compInfoMapper.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), Long.parseLong(member_id), 1L, longID,2,1, new Date());//插入归属审核是通过
           
			Map<String, Object> map = new HashMap<String, Object>();  //4.插入关系表：一个站点有一个站长
			map.put("id", IdWorker.getIdWorker().nextId());
			map.put("member_id", member_id);
			map.put("comp_id", longID);
			map.put("role_id", 1);//1：站长
			map.put("area_color", "#c2c2c2");
			map.put("employee_work_status_flag", 1);
			map.put("review_task_receiving_flag", 1);
		    basEmployeeRelationMapper.insertBasEmployeeRelation(map);
			
		    redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
		    
		    redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
			redisService.removeAll("relationInfo-memberId-cache");
			redisService.removeAll("expressInfo-memberId-cache");
			ehcacheService.removeAll("compCache");
			ehcacheService.removeAll("compImageCache");
			redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
			
		    		
			return String.valueOf(longID);
		}
		/**
		 * 
		 * 第2个接口
		 * @Description: 保存网点认证信息
		 */
		@Override
		public Map submitCompVerifyInfo(Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, 
				String frontImg,String holdImg, String reverseImg,Long memberId) throws ServiceException {
			
			if(PubMethod.isEmpty(loginCompId)){
				throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.submitCompVerifyInfo","loginCompId参数异常");
			}
			if(PubMethod.isEmpty(memberId)){
				throw new ServiceException("net.okdi.apiV1.service.impl.ExpressSiteServiceImpl.submitBasCompInfo","memberId参数异常");
			}
			redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
			
		Map returnMap =new HashMap();	
		/*String compid=	compbusinessInfoMapper.queryCompId(loginCompId);
		if(!PubMethod.isEmpty(compid)){
			returnMap.put("state", "-1");
			returnMap.put("message", "该网点已被认证");
			return returnMap;
		}*/
		/*String compstatus = compInfoMapper.queryCompStatus(loginCompId); //查询站点现在处于什么状态
		System.out.println("公司状态："+compstatus);
		if(compstatus.equals("1")){
			returnMap.put("state", "-1");
			returnMap.put("message", "该网点已被认证");
			return returnMap;
		}
		if(compstatus.equals("0")){
			returnMap.put("state", "-1");
			returnMap.put("message", "该网点处于提交待审核状态");
			return returnMap;
		}*/
		    compbusinessInfoMapper.deleteBusinessMessage(loginCompId);//1.删除原来加的负责人信息
			BasCompbusiness	compbusinessInfo = new BasCompbusiness(); //1.新增负责人信息
				compbusinessInfo.setCompId(loginCompId);
				compbusinessInfo.setResponsible(responsible);
				compbusinessInfo.setResponsibleIdNum(responsibleNum);
				compbusinessInfo.setResponsibleTelephone(responsibleTelephone);

				compbusinessInfoMapper.insertMessage(compbusinessInfo);
				
				compimageMapper.deleteImageByCompId(loginCompId);//2.删除原来保存的照片
				System.out.println("..........保存认证照片开始");
				List<BasCompimage> compimageList = new ArrayList<BasCompimage>();  //2.保存认证照片
				
					BasCompimage compimage = new BasCompimage();
					BasCompimage compimage1 = new BasCompimage();
					BasCompimage compimage2 = new BasCompimage();
					compimage.setId(IdWorker.getIdWorker().nextId());
					compimage.setCompId(loginCompId);
					compimage.setImageType(Short.parseShort("5"));//图片类型 1:LOGO, 2:营业执照   5:负责人身份证正面照 3:站点门店照片
					compimage.setImageDetail("身份证正面照");
					compimage.setImageUrl(frontImg);
					compimageList.add(compimage);
					
					compimage1.setId(IdWorker.getIdWorker().nextId());
					compimage1.setCompId(loginCompId);
					compimage1.setImageType(Short.parseShort("2"));//图片类型 1:LOGO, 2:营业执照   5:负责人身份证正面照 3:站点门店照片
					compimage1.setImageDetail("营业执照");
					System.out.println("营业执照holdImg:"+holdImg);
					compimage1.setImageUrl(holdImg);
					compimageList.add(compimage1);
					
					compimage2.setId(IdWorker.getIdWorker().nextId());
					compimage2.setCompId(loginCompId);
					compimage2.setImageType(Short.parseShort("8"));//图片类型 1:LOGO, 2:营业执照   5:负责人身份证正面照 6:站点门店照片
					compimage2.setImageDetail("站点门店照片");
					compimage2.setImageUrl(reverseImg);
					compimageList.add(compimage2);
					
					compimageMapper.saveCompImageBatch(compimageList);
					
					redisService.removeAll("findShopowner1xxx");//清除图片缓存
					redisService.removeAll("findShopowner2xxx");
					redisService.removeAll("findShopowner3xxx");
					
				
				
				    
				    int compStatus=0;
				    compInfoMapper.updateCompStatus(compStatus, loginCompId);//3.更新网点信息为0：提交待审核。
				
					redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
					redisService.removeAll("relationInfo-memberId-cache");
					redisService.removeAll("expressInfo-memberId-cache");
					ehcacheService.remove("compCache", String.valueOf(loginCompId));
					ehcacheService.remove("compBusinessCache", String.valueOf(loginCompId));

					ehcacheService.removeAll("compImageCache");
		           redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
		 
				returnMap.put("state", "0");
			    returnMap.put("message", "站点认证审核提交成功！");
				return returnMap;
		}
		
		/**
		 *  1.第3个接口：添加网点/营业部信息
		 * @Method: saveCompInfo 
		 * @Description: 
		 */
		public BasCompInfo saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
				Double longitude, Double latitude, String county,String address) throws ServiceException{
			
			redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
			
			Map<String, Object> sameComp= new HashMap<String, Object>();
			    sameComp =redisService.get("SameCompNameForMobile", netId+compName,Map.class);
			    //System.out.println(">>>>>>>>缓存有数据>>>>"+sameComp.toString());
			if(PubMethod.isEmpty(sameComp)){
				//System.out.println(">>>>>>>>是否有相同网点>>有>>>>>");
				sameComp=this.getSameCompNameForMobile(netId, compName);//检查是否有相同网点
				redisService.put("SameCompNameForMobile", netId+compName, sameComp);
			}
			  
			
			
			
			if(!PubMethod.isEmpty(sameComp)){
				throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.009", "站点已存在");
			}
			Map<String, Object> Resultmap = parseService.parseAddrByList(String.valueOf(latitude),String.valueOf(longitude));
			Long addressId = Long.parseLong(String.valueOf(Resultmap.get("addressId")));
			
			//1.添加一条站点或营业部
			BasCompInfo compInfo=new BasCompInfo();
			Long compid=IdWorker.getIdWorker().nextId();
			compInfo.setCompId(compid);
			compInfo.setRelationCompId(-1L);
			this.setCompInfo(compInfo, memberId, netId, compTypeNum, null, compName, compTelephone, addressId, 
					county+"|"+address, longitude, latitude, (short)6, (short)1);
			int count = this.compInfoMapper.insert(compInfo);
			if(count<1){
				throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.009", "保存公司信息参数异常,存在重名网点禁止新增");
			}
			
			/*String application_time=new Date().toLocaleString();
			Long id =IdWorker.getIdWorker().nextId();
			System.out.println(">>>>>>>>>>>>>> 第1个接口：快递员----申请入站：");*/
			//插入员工审核表bas_employeeaudit
			//compInfoMapper.applyJoin( id,String.valueOf(memberId), String.valueOf(compid), "1", application_time, "2","1");
			
			//2.插入 到bas_employeeaudit 1条数据，插入归属审核是通过
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String createTime = dateFormat.format(new Date());
			compInfoMapper.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), memberId, 1L, compid,2,1,new Date());
           
			 //3、插入关系表：一个站点有一个站长
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("id", IdWorker.getIdWorker().nextId());
			map.put("member_id", memberId);
			map.put("comp_id", compid);
			map.put("role_id", 1);//1：站长
			map.put("area_color", "#c2c2c2");
			map.put("employee_work_status_flag", 1);
			map.put("review_task_receiving_flag", 1);
		    basEmployeeRelationMapper.insertBasEmployeeRelation(map);
		    
		    redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
		    
		    redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
			redisService.removeAll("relationInfo-memberId-cache");
			redisService.removeAll("expressInfo-memberId-cache");
			ehcacheService.removeAll("compCache");
			ehcacheService.removeAll("compImageCache");
			
			return compInfo;
		}
		
		/**
		 * 2.第3个接口：添加网点/营业部信息,查是否有相同电话
		 * @Method: getSameCompNameForMobile 
		 * @Description: 查询同网络下网点重名信息（手机端 包含未注册信息）
		 * @param netId 网络ID
		 * @param compName 网点名称
		 * @return Map<String, Object>
		 * @throws ServiceException 
		 * @see net.okdi.api.service.CompInfoService#getSameCompNameForMobile(java.lang.Long, java.lang.String) 
		 * @since jdk1.6
		 */
		public Map<String, Object> getSameCompNameForMobile(Long netId,String compName) throws ServiceException{
			Map<String, Object> paras = new HashMap<String, Object>();
			if (PubMethod.isEmpty(netId)) {
				throw new ServiceException("openapi.CompInfoServiceImpl.getSameCompNameForMobile.001", "获取同网络下重名网点信息,netId参数异常");
			}
			if(PubMethod.isEmpty(compName)){
				return null;
			}
			paras.put("netId", netId);
			paras.put("compName", PubMethod.isEmpty(compName)?"":compName.trim());
			
			List<Map<String, Object>> compList = compInfoMapper.getSameCompNameForMobile(paras);
			if (PubMethod.isEmpty(compList)) {
				return null;
			} else {
				return compList.get(0);
			}
		}
		
		/**
		 * 3.第3个接口：组装信息
		 * @Method: setCompInfo 
		 * @Description: 设置网点信息对象
		 * @param compInfo 网点信息对象
		 * @param loginMemberId 登录用户ID
		 * @param netId 网络ID
		 * @param compTypeNum 网点类型 1006站点 1050营业分部
		 * @param useCompId 领用网点ID
		 * @param compName 网点名称
		 * @param compTelephone 网点电话
		 * @param addressId 乡镇地址ID
		 * @param address 网点详细地址
		 * @param longitude 经度
		 * @param latitude 纬度
		 * @param compRegistWay 网点注册方式 5站点 6手机端
		 * @param registFlag 网点注册标识 0未注册 1已注册
		 * @throws ServiceException
		 * @author guoqiang.jing
		 * @date 2015-10-14 
		 * @since jdk1.6
		 */
		private void setCompInfo(BasCompInfo compInfo, Long loginMemberId, Long netId, String compTypeNum, Long useCompId, String compName, String compTelephone, Long addressId,
				String address, Double longitude, Double latitude, Short compRegistWay,Short registFlag) {
				compInfo.setCompNum(null);
				compInfo.setCompShort(null);
				compInfo.setCompName(compName);
				compInfo.setFirstLetter(PiyinUtil.cn2py(compName.substring(0, 1)).toUpperCase());
				compInfo.setRegistFlag(registFlag);// 已注册
				compInfo.setCompTypeNum(compTypeNum);
				compInfo.setCompAddressId(addressId);
				compInfo.setCompAddress(address);
				compInfo.setLongitude(PubMethod.isEmpty(longitude) ? null : BigDecimal.valueOf(longitude));
				compInfo.setLatitude(PubMethod.isEmpty(latitude) ? null : BigDecimal.valueOf(latitude));
				compInfo.setCompStatus(PubMethod.isEmpty(compInfo.getCompStatus()) ? (short) -1 : compInfo.getCompStatus());
				compInfo.setCompRegistWay(PubMethod.isEmpty(compInfo.getCompRegistWay()) ? compRegistWay : compInfo.getCompRegistWay());
				compInfo.setBelongToNetId(netId);
				compInfo.setBelongToCompId((long) -1);
				compInfo.setWriteSendStatus((short) 0);
				compInfo.setGoodsPaymentStatus((short) 0);
				compInfo.setCreateTime(PubMethod.isEmpty(compInfo.getCreateTime()) ? new Date() : compInfo.getCreateTime());
				compInfo.setModifiyTime(new Date());
				compInfo.setCreateUser(loginMemberId);
				compInfo.setCompTelephone(compTelephone);
				compInfo.setTaoGoodsPaymentStatus((short) -1);
		}
/*站长-------------------------------------end-----------------------------*/
		/**
		 * 
		 * 第8个接口
		 * @Description: 运营平台--审核站点
		 */
		public void auditSite(Long compId, String compStatus){
			Long id = IdWorker.getIdWorker().nextId();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String auditTime = dateFormat.format(new Date());
			
			compInfoMapper.performAudit(id, compId, compStatus, null, new Date());
			
			compInfoMapper.auditSite(compId, compStatus);
		}

		/**
		 * 第9个接口
		 * @Description: 运营平台--审核站点通过(新)
		 */
		public Map updateCompStatus(Long compId, String compMess,String compType) {
			Map returnMap = new HashMap();
			Long id = IdWorker.getIdWorker().nextId();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String auditTime = dateFormat.format(new Date());
			
			if("1".equals(compType)){  //营业执照
				String businessLicense =redisService.get("queryBusinesslicense", compMess,String.class);
				 if(PubMethod.isEmpty(businessLicense)){
					  businessLicense = compbusinessInfoMapper.queryBusinesslicense(compId);  //查询营业执照
					}
				if(businessLicense==null||"".equals(businessLicense)){  //如果没查到,将营业执照更新进去
					compbusinessInfoMapper.updateBusinesslicense(compMess, compId);
					compInfoMapper.performAudit(id, compId, "1", null, new Date());
					compInfoMapper.auditSite(compId, "1"); //更新bas_compinfo的审核状态为通过
					
					redisService.put("queryBusinesslicense", compMess, compMess); //营业执照放缓存
					returnMap.put("success", "true");
					returnMap.put("message", "操作成功");
				}else{
					String phone = redisService.get("queryPhoneByBusinesslicense", compMess,String.class);
					if(PubMethod.isEmpty(phone)){
					   phone =compbusinessInfoMapper.queryPhoneByBusinesslicense(compMess);//
					   redisService.put("queryPhoneByBusinesslicense",  compMess, phone);
					}
					returnMap.put("success", "false");
					returnMap.put("message", "该营业执照已被手机号为"+phone+"的站点认证过，不能审核通过");
				}
			}else
			if("2".equals(compType)){ //站点
				String compStatus="1";  
				compInfoMapper.updateCompType(compId,compMess, compStatus);
				compInfoMapper.performAudit(id, compId, "1", null, new Date());
				
				compInfoMapper.auditSite(compId, "1"); //更新bas_compinfo的审核状态为通过
				returnMap.put("success", "true");
				returnMap.put("message", "操作成功");
			}else
			if("3".equals(compType)){  //营业许可证
				String compLicense =redisService.get("queryComplicense", compMess,String.class);
				if(PubMethod.isEmpty(compLicense)){
					compLicense = compbusinessInfoMapper.queryComplicense(compId);
				}
				if(compLicense==null||"".equals(compLicense)){  //如果没查到,将许可证更新进去
					compbusinessInfoMapper.updateComplicense(compMess, compId);
					
					compInfoMapper.performAudit(id, compId, "1", null, new Date());
					compInfoMapper.auditSite(compId, "1"); //更新bas_compinfo的审核状态为通过
					
					 redisService.put("queryComplicense", compMess, compMess);//营业xukezhing放缓存
					 
					returnMap.put("success", "true");
					returnMap.put("message", "操作成功");
				}else{
					String phone = redisService.get("queryPhoneByCompLicense", compMess,String.class);
					if(PubMethod.isEmpty(phone)){
					   phone =compbusinessInfoMapper.queryPhoneByCompLicense(compMess);//
					   redisService.put("queryPhoneByCompLicense",  compMess, phone);
					}
					returnMap.put("success", "false");
					returnMap.put("message", "该营业许可证已被手机号为"+phone+"的站点认证过，不能审核通过");
					
					
				}
			}
			
			redisService.removeAll("findShopowner1xxx");//清除图片缓存
			redisService.removeAll("findShopowner2xxx");
			redisService.removeAll("findShopowner3xxx");
			
			ehcacheService.remove("compCache", compId.toString());//清除运营平台compId的详细信息缓存
			//删除缓存
			ehcacheService.removeAll("compCache");
		/*	//1.得到邀请人的收派员的信息
			Map<String, Object> retMap=compInfoMapper.queryFormMemberId(compId);
			if(!PubMethod.isEmpty(retMap)){
				String fromMemberId = (String) retMap.get("from_member_id");
				String roleId = (String) retMap.get("from_member_role_id");
				if(!PubMethod.isEmpty(fromMemberId)){//如果有，将其加入成员工
					
					Map<String, Object> map = new HashMap<String, Object>();  //插入关系表
					map.put("id", IdWorker.getIdWorker().nextId());
					map.put("member_id", fromMemberId);
					map.put("comp_id", compId);
					map.put("role_id", roleId);//1：站长
					map.put("area_color", "#c2c2c2");
					map.put("employee_work_status_flag", 1);
					map.put("review_task_receiving_flag", 1);
				    basEmployeeRelationMapper.insertBasEmployeeRelation(map);
				    
				  //2.将归属审核(2)的收派员的compid补全。
				    compInfoMapper.updateBasEmployeeaudit(compId, fromMemberId, "2");
				    
				}
			}*/
			
			//推送
			String flag=(String)returnMap.get("success");
			if(flag.equals("true")){
				Map<String, Object> retMap= compInfoMapper.queryPushMessage(compId);
				if(!PubMethod.isEmpty(retMap)){
					Long memberId = (Long) retMap.get("member_id");//登陆app的人的id
					String mob = (String) retMap.get("member_phone");//登陆app的人的电话
					sendNoticeService.successNetDotAuthenticat(memberId, mob, Short.parseShort("1"));//推送站点认证成功
				}
			}
		
			
			
			return returnMap;
		}
		/**
		 * 第11个接口
		 * @Description: 运营平台--审核站点不通过
		 */
		public void  updateCompStatusToRefuse(Long compId, String refuseDesc){
			System.out.println("+++++++++++审核网点标注日志++++++++++");
			Long id = IdWorker.getIdWorker().nextId();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String auditTime = dateFormat.format(new Date());
			
			compInfoMapper.performAudit(id, compId, "2", refuseDesc, new Date());
			compInfoMapper.auditSite(compId, "2");
			
			redisService.removeAll("findShopowner1xxx");//清除图片缓存
			redisService.removeAll("findShopowner2xxx");
			redisService.removeAll("findShopowner3xxx");
			
			//推送
			Map<String, Object> retMap= compInfoMapper.queryPushMessage(compId);
			if(!PubMethod.isEmpty(retMap)){
				Long memberId = (Long) retMap.get("member_id"); //登陆app的人的id
				String mob = (String) retMap.get("member_phone");//登陆app的人的电话
				sendNoticeService.refuseNetDotAuthenticat(memberId, mob, Short.parseShort("1"), refuseDesc);//推送站点认证shibai
			}
			
			
		}
		/**
		 * 第10个接口
		 * @Description: 运营平台--查询站点名称
		 */
		public String queryCompName(Long compId) {
			String compName = redisService.get("queryCompName", String.valueOf(compId),String.class);
			if(PubMethod.isEmpty(compName)){
			   compName =compInfoMapper.queryCompName(compId);
			    redisService.put("queryCompName",  String.valueOf(compId), compName);
			}
			return compName;
		}

	@Override
	public void sendTS(String title, String message, String extraParam,
			String idkey, String pushWay, String platform, String useType,
			String userType, String startTime1, String endTime1) {
		try {
				if ("1".equals(useType)) {
				/*	List<Map<String, Object>> list = compInfoMapper
							.queryDeviceInfos();*/
			    String[] userTypeParam={};
				if ("3".equals(userType)) {
				  userTypeParam=new String[] {"2","3"};
				} else if ("2".equals(userType)) {
				  userTypeParam=new String[]{"1","0","-1"};
				} else {
				 userTypeParam=new String[]{"1","2","3","0","-1"};
				}
				//给所有苹果安装用户推送  否则  只推送安卓用户
				
				if(PubMethod.isEmpty(idkey)){
					List arrPhones = new ArrayList<>();
					List<Map<String, Object>> list = compInfoMapper.queryDeviceInfosByCon(userTypeParam, platform, startTime1,endTime1, useType,null);
					try {
						this.doAdd(title, message, arrPhones, extraParam, pushWay);
					} catch (Exception e) {
					}
					pushMessageService.savePushMessageInfo(title, message,extraParam, arrPhones, pushWay,platform,Short.valueOf(useType));
					//根据手机号
					for (Map<String, Object> cont : list) {
						String memberPhone = cont.get("member_phone") + "";
						arrPhones.add(memberPhone);
						String memberId = cont.get("memberId") + "";
						String deviceType = cont.get("DEVICE_TYPE") + "";
						String deviceToken = cont.get("DEVICE_TOKEN") + "";
						//通过memberId（CHANNEL_ID）判断是否登录
						if(!PubMethod.isEmpty(memberId)){
								System.out.println("memberPhone:" + memberPhone+ ",memberId:" + memberId+ ",deviceType:" + deviceType+ ",deviceToken" + deviceToken);
								boolean flag = sendNoticeService.sendTS(memberId,deviceType, deviceToken, title, message,extraParam);
						}
				}
					
		        
				}else{
					  /**************将手机号分解*****************/
					   List arrPhones = redisService.get("storePhone", idkey,List.class);
					   int  size=arrPhones.size()%200==0?arrPhones.size()/200:arrPhones.size()/200+1;  
					   List<List<String>>result=ExpressSiteServiceImpl.createList(arrPhones, size);
					   pushMessageService.savePushMessageInfo(title, message,extraParam, arrPhones, pushWay,platform,Short.valueOf(useType));
					   for(List<String> subArr:result) {  
							try {
								this.doAdd(title, message, subArr, extraParam, pushWay);
							} catch (Exception e) {
							}
							//根据手机号
							List<Map<String, Object>> list = compInfoMapper.queryDeviceInfosByCon(userTypeParam, platform, startTime1,endTime1, useType,subArr);
							for (Map<String, Object> cont : list) {
								String memberPhone = cont.get("member_phone") + "";
								String memberId = cont.get("memberId") + "";
								String deviceType = cont.get("DEVICE_TYPE") + "";
								String deviceToken = cont.get("DEVICE_TOKEN") + "";
								for (int j = 0; j < arrPhones.size(); j++) {
									if (arrPhones.get(j).equals(memberPhone)) {
										//通过memberId（CHANNEL_ID）判断是否登录
										if(!PubMethod.isEmpty(memberId)){
											System.out.println("memberPhone:" + memberPhone+ ",memberId:" + memberId+ ",deviceType:" + deviceType+ ",deviceToken" + deviceToken);
											boolean flag = sendNoticeService.sendTS(memberId,deviceType, deviceToken, title, message,extraParam);
										}
									}
								}
							}
				        }
						
				}
				
			
				 
			    	// 把推送的记录插入到mongo中
				
			
				redisService.remove("storePhone", idkey); // 移除这个idkey的手机号集合
			} else {
				
			List arrPhones = new ArrayList<>();
			// 把推送的记录插入到mongo中
			pushMessageService.savePushMessageInfo(title, message,extraParam, arrPhones, pushWay,platform,Short.valueOf(useType));
			int startSize=0;
			int size=200;
			do{
				List<Map<String, Object>> list = compInfoMapper.queryDeviceInfos(startSize,size);
				for (Map<String, Object> cont : list) {
					String memberPhone = cont.get("member_phone") + "";
					arrPhones.add(memberPhone);
					String memberId = cont.get("memberId") + "";
					String deviceType = cont.get("DEVICE_TYPE") + "";
					String deviceToken = cont.get("DEVICE_TOKEN") + "";
					System.out.println("memberPhone:" + memberPhone+ ",memberId:" + memberId + ",deviceType:"+ deviceType + ",deviceToken" + deviceToken);
					boolean flag = sendNoticeService.sendTS(memberId, deviceType, deviceToken, title,message, extraParam);
				}
				size=list.size();
				startSize=startSize+size;
			}while(size==200);
			
		
			try {
				this.doAdd(title, message, arrPhones, extraParam, pushWay);
			} catch (Exception e) {
			}
		}

	} catch (Exception e) {
		// TODO: handle exception
	}

	}
	  //拆分list
	  public static List<List<String>>  createList(List<String> targe,int size) {  
	        List<List<String>> listArr = new ArrayList<List<String>>();  
	        //获取被拆分的数组个数  
	        for(int i=0;i<size;i++) {  
	            List<String>  sub = new ArrayList<String>();  
	            //把指定索引数据放入到list中  
	            for(int j=i*200;j<=200*(i+1)-1;j++) {  
	                if(j<=targe.size()-1) {  
	                    sub.add(targe.get(j));  
	                }  
	            }  
	            listArr.add(sub);  
	        }  
	        return listArr;  
	    }  

		private void doAdd(String title, String message, List arrPhones,
				String extraParam, String pushWay) {
			for(Object memberPhone:arrPhones){
				PushMessageToSomeOne pushMessageToSomeOne = new PushMessageToSomeOne();
				pushMessageToSomeOne.setPushId(IdWorker.getIdWorker().nextId());
				pushMessageToSomeOne.setContent(message);
				pushMessageToSomeOne.setCreateTime(new Date().getTime());
				pushMessageToSomeOne.setExtraParam(Short.parseShort(extraParam));
				pushMessageToSomeOne.setMemberPhone(memberPhone.toString());
				pushMessageToSomeOne.setPushWay(Short.parseShort(pushWay));
				pushMessageToSomeOne.setTitle(title);
				pushMessageToSomeOne.setToMemberId(null);
				pushMessageToSomeOne.setIsRead((short)0);
				this.mongoTemplate.save(pushMessageToSomeOne, "pushMessageToSomeOne");
			}
		}

		/**
		 * @Description: 系统公告临时推广
		 */
		@Override
		public void sendAnnounceTS(String announceType, String title,String content, String creator, String idkey, String pushWay) {
			try {
				 System.out.println(">>>><<<<<<<<<<<<<<<"+title+","+content+","+creator);
				//String[] arrPhones = phones.split(" ");   //分组成电话数据
//				List<Map<String, Object>> list=compInfoMapper.queryDeviceInfos(20,1);
				//把推送的记录插入到mongo中
				pushMessageService.saveAnnounceMessageInfo(announceType,title, creator,content,pushWay);
				redisService.remove("messageInfo", "newestTime");

				int startSize=0;
				int size=200;
				do{
					List<Map<String, Object>> list = compInfoMapper.queryDeviceInfos(startSize,size);
					for (Map<String, Object> cont : list) {
						String memberPhone = cont.get("member_phone") + "";
						String memberId = cont.get("memberId") + "";
						String deviceType = cont.get("DEVICE_TYPE") + "";
						String deviceToken = cont.get("DEVICE_TOKEN") + "";
						System.out.println("memberPhone:" + memberPhone+ ",memberId:" + memberId + ",deviceType:"+ deviceType + ",deviceToken" + deviceToken);
						boolean flag = sendNoticeService.sendTS(memberId, deviceType, deviceToken, title,content, null);
					}
					size=list.size();
					startSize=startSize+size;
				}while(size==200);
//				System.out.println("=========="+list.size());
//				for(Map<String, Object> cont:list){
//				
//				  String memberPhone =cont.get("member_phone")+"";    
//				  String memberId   =  cont.get("memberId")+"";
//				  String deviceType =cont.get("DEVICE_TYPE")+"";
//				  String deviceToken = cont.get("DEVICE_TOKEN")+"";
//				  //发送推送内容
//				  System.out.println("发送系统公告===手机号:"+memberPhone+",标题:"+title+"内容:"+content+",memberId:"+memberId);
//				  boolean flag =sendNoticeService.sendTS(memberId, deviceType,deviceToken,title,content,null);
//				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		
		}

		@Override
		public void saveEmployeeaudit(String memberId, String compId,String roleType) {
			if (PubMethod.isEmpty(memberId)) {
				throw new ServiceException("openapi.CompInfoServiceImpl.saveEmployeeaudit.001", "memberId参数异常");
			}
			
			                     /*id,member_id,application_role_type,audit_comp, audit_item,audit_opinion,application_time*/
			compInfoMapper.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), Long.parseLong(memberId), Long.parseLong(roleType), Long.parseLong(compId),4,1, new Date());//插入归属审核是通过
	           
		}


		@Override
		public Map queryPhones(String phone, Integer currentPage,Integer pageSize) {
			currentPage = (currentPage-1)*pageSize;
			List phoneList =  compInfoMapper.queryPhones(phone, currentPage, pageSize);
			int total = compInfoMapper.queryPhonesTotal(phone);
			
			Map<String, Object> map=new HashMap<String, Object>();
			    map.put("total", total);
			    map.put("currentPage", currentPage);
				map.put("pageSize", pageSize);
				map.put("record", phoneList);
			return map;
		}


		@Override
		public List exportPhones(String phone) {
			
			return compInfoMapper.exportPhones(phone);
		}


		@Override
		public List<AnnounceMessageInfo> queryMyMessage(String phone) {
			Query query = new Query();
			query.addCriteria(Criteria.where("status").is(0));
			query.with(new Sort(Direction.DESC,"createTime"));//根据发送时间排序
			List<AnnounceMessageInfo> list = this.mongoTemplate.find(query, AnnounceMessageInfo.class);
			return list;
		}


		@Override
		public void deleleMyAllMessage(String memberPhone) {
			//FIXME:批量删除禁止使用,最好添加逻辑删除字段(SJZ 2016-05-27)
			this.mongoTemplate.remove(Query.query(Criteria.where("memberPhone").is(memberPhone)), PushMessageToSomeOne.class, "pushMessageToSomeOne");
		}


		@Override
		public void deleleMyOneMessage(Long pushId) {
			//FIXME:批量删除禁止使用,最好添加逻辑删除字段(SJZ 2016-05-27)
			this.mongoTemplate.remove(Query.query(Criteria.where("pushId").is(pushId)), PushMessageToSomeOne.class, "pushMessageToSomeOne");
		}


		@Override
		public void readOneMessage(Long pushId) {
			Query query = new Query();
			query.addCriteria(Criteria.where("pushId").is(pushId));
			Update update = new Update();
			update.set("isRead", (short)1);
			this.mongoTemplate.updateFirst(query, update, PushMessageToSomeOne.class);
		}

}
