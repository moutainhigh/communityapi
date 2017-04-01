package net.okdi.apiV2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompEmployeeResumeMapper;
import net.okdi.api.dao.BasEmployeeAuditMapper;
import net.okdi.api.dao.BasEmployeeRelationMapper;
import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasCompbusiness;
import net.okdi.api.entity.BasCompimage;
import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.api.service.AreaElectronicFenceService;
import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.apiV1.dao.BasCompInfoMapperV1;
import net.okdi.apiV2.dao.BasCompBusinessMapperV2;
import net.okdi.apiV2.dao.BasCompInfoMapperV2;
import net.okdi.apiV2.dao.BasCompimageMapperV2;
import net.okdi.apiV2.dao.BasEmployeeAuditMapperV2;
import net.okdi.apiV2.dao.BasEmployeeRelationMapperV2;
import net.okdi.apiV2.service.ExpressRegisterService;
import net.okdi.apiV2.vo.VO_BasCompInfo;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.AbstractHttpClient;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class ExpressRegisterServiceImpl extends AbstractHttpClient implements ExpressRegisterService {

	public static final Log logger = LogFactory.getLog(ExpressRegisterServiceImpl.class);
	
	@Value("${net.info.pic.url}") //config_production.property中配置
	public String headImgPath;
	 @Value("${pay_url}")
	private String payUrl; //新版财务url
	@Autowired
	private RedisService redisService;  //缓存
	@Autowired
	BasEmployeeAuditMapperV2 basEmployeeAuditMapperV2;	//快递员审核表
	@Autowired
	private BasCompInfoMapperV2 basCompInfoMapperV2;//站点信息表
	@Autowired
	private BasCompInfoMapperV1 compInfoMapper;
	
	@Autowired
	private BasEmployeeRelationMapperV2 basEmployeeRelationMapperV2;//人员关系表
	@Autowired
	private BasEmployeeRelationMapper basEmployeeRelationMapper;
	@Autowired
	private BasCompEmployeeResumeMapper basCompEmployeeResumeMapper;
	@Autowired
	private BasEmployeeAuditMapper basEmployeeAuditMapper;
	@Autowired
	private AreaElectronicFenceService areaElectronicFenceService;
	@Autowired
	private ParcelInfoService parcelInfoService;
	@Autowired
	private SendNoticeService sendNoticeService;
	@Autowired
	private ExpCustomerInfoService expCustomerInfoService;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	
	@Autowired
	private BasCompBusinessMapperV2 compbusinessInfoMapperV2;
	@Autowired
	private BasCompimageMapperV2 compimageMapperV2;
	@Value("${compPic.readPath}")
	public String readPath;
	/**
	 * 获取快递员的身份和快递认证状态
	 */
	@Override
	public HashMap<String, Object> queryAuditStatus(String memberId) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		BasEmployeeAudit memberMapSF = basEmployeeAuditMapperV2.queryRealNameStatus(memberId);//收派员身份认证状态
		if (memberMapSF!=null) {
			resultMap.put("memberStatusSF", memberMapSF.getAuditOpinion());
		}else {
			resultMap.put("memberStatusSF", "-1");//-1 未完善/未提交 0 待审核  1通过 2拒绝
			logger.info("没有查到此人员的身份认证审核信息！默认传给手机端状态为-1");
		}
		BasEmployeeAudit memberMapKD = basEmployeeAuditMapperV2.queryExpressStatus(memberId);//快递员认证状态
		if (memberMapKD!=null) {
			resultMap.put("memberStatusKD", memberMapKD.getAuditOpinion());
		}else {
			resultMap.put("memberStatusKD", "-1");//-1 未完善/未提交 0 待审核  1通过 2拒绝
			logger.info("没有查到此人员的快递认证审核信息！默认传给手机端状态为-1");
		}
		return resultMap;
	}
	/**
	 * 获取站点信息列表-按首字母排序
	 */
	@Override
	public Map<String, List<VO_BasCompInfo>> queryCompInfoByFirstLetter(String netId,String addressName) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("netId", netId);
		paramMap.put("addressName", addressName);
		List<BasCompInfo> compInfoList =  basCompInfoMapperV2.queryCompInfoByNetIdAndAddressId(paramMap);
		return sortCompInfoFirstLetter(compInfoList);
	}
	/**
	 * 按首字母排序
	 */
	private Map<String, List<VO_BasCompInfo>> sortCompInfoFirstLetter(List<BasCompInfo> compInfoList) {
		String[] typeArray = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "OTHER" };
		Map<String, List<VO_BasCompInfo>> allNet = new HashMap<String, List<VO_BasCompInfo>>();
		for (String type : typeArray) {
			allNet.put(type, new ArrayList<VO_BasCompInfo>());
		}
		for (BasCompInfo per : compInfoList) {
			String letter = PubMethod.isEmpty(per.getFirstLetter()) ? null : per.getFirstLetter().trim();
			VO_BasCompInfo data = new VO_BasCompInfo();
			data.setCompId(per.getCompId());
			data.setCompName(per.getCompName());
			data.setCompTypeNum(per.getCompTypeNum());
			data.setFirstLetter(per.getFirstLetter());
			data.setLatitude(per.getLatitude());
			data.setLongitude(per.getLongitude());
			List<VO_BasCompInfo> list = null;
			if (!PubMethod.isEmpty(letter)) {
				list = allNet.get(letter);
			}
			if (list == null) {
				allNet.get("OTHER").add(data);
			} else {
				allNet.get(letter.trim()).add(data);
			}
		}
//		for (Map.Entry<String, List<VO_BasCompInfo>> entry : allNet.entrySet()) {
//			if (entry.getValue()!=null && entry.getValue().size()>0) {
//				Collections.sort(entry.getValue());
//			}
//		}
		return allNet;
	}
	/**
	 * 判断选择的站点/营业分部是否被领用V2--站长角色
	 */
	@Override
	public HashMap<String, Object> isRelationByLeader(String compId,String compNumType) {
		//1003:网络直营站点	1006:加盟站点	1050:营业分部
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("siteRelationFlag","-1");//siteRelationFlag为空：新建站点/营业分部
		//compId不为空，说明是选择的站点，并非新建
		if (!PubMethod.isEmpty(compId)) {
			//根据compId查询是否被领取
			Long relationCompId= basCompInfoMapperV2.queryRelationCompId(Long.valueOf(compId));
			//选择的是网络抓取未被领取的站点
			if (compNumType.equals("1003")) {
				if ((String.valueOf(relationCompId)).equals("-1")) {
					resultMap.put("siteRelationFlag","0");//站点未被领用
				}else {
					resultMap.put("siteRelationFlag","1");//站点已被领用
				}
				logger.info("站长选择的是网络抓取未被领取的站点");
				//选择的是已经被领取的加盟站点	
			}else if (compNumType.equals("1006")) {
				resultMap.put("siteRelationFlag","1");//站点已被领用
				logger.info("站长选择的是已经被领取的加盟站点");
				//选择的是已经被领取的营业分部
			}else if (compNumType.equals("1050")) {
				resultMap.put("siteRelationFlag","1");//营业分部已被领用
				logger.info("站长选择的是已经被领取的营业分部");
			}
		//compId为空说明是新创建
		}else {
			
		}
		resultMap.put("compNumType", compNumType);//1003:网络直营站点	1006:加盟站点	1050:营业分部
		resultMap.put("compId", compId);//compId为空：新建站点/营业分部
		return resultMap;
	}
	/**
	 * 判断选择的站点/营业分部是否被领用V3--站长角色
	 */
	@Override
	public HashMap<String, Object> isRelationByLeaderV3(String compId,String compNumType) {
		//1003:网络直营站点	1006:加盟站点	1050:营业分部
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("siteRelationFlag","-1");//siteRelationFlag为空：新建站点/营业分部
		//compId不为空，说明是选择的站点，并非新建
		if (!PubMethod.isEmpty(compId)) {
			//根据compId查询是否被领取
			Long relationCompId= basCompInfoMapperV2.queryRelationCompId(Long.valueOf(compId));
			//选择的是网络抓取未被领取的站点
			if ("1003".equals(compNumType)) {
				if ((String.valueOf(relationCompId)).equals("-1")) {
					resultMap.put("siteRelationFlag","0");//站点未被领用
				}else {
					resultMap.put("siteRelationFlag","1");//站点已被领用
				}
				logger.info("站长选择的是1003类型，网络抓取未被领用的站点");
				//选择的是加盟站点/营业分部
			}else if ("1006".equals(compNumType) || "1050".equals(compNumType)) {
				//查询此站点下站长数量，>0说明有站长，站点已领用；=0说明没站长，站点未领用
				int count = basEmployeeAuditMapperV2.queryLeaderCountInComp(compId);
				if (count > 0) {
					resultMap.put("siteRelationFlag","1");//站点已被领用
					logger.info("站长选择的是已被领用的站点/营业分部");
				}else {
					resultMap.put("siteRelationFlag","0");//站点未被领用
					logger.info("站长选择的是未被领用的站点/营业分部");
				}
			}
			//compId为空说明是新创建
		}else {
			
		}
		resultMap.put("compNumType", compNumType);//1003:网络直营站点	1006:加盟站点	1050:营业分部
		resultMap.put("compId", compId);//compId为空：新建站点/营业分部
		return resultMap;
	}
	/**
	 * 站长领用网络抓取的站点
	 */
	@Override
	public HashMap<String, Object> submitUseCompInfo(Long webCompId,String compName, String belongToNetId, String county,String member_id,String addressId,String roleType,String memberName) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Long relationCompId= basCompInfoMapperV2.queryRelationCompId(webCompId);
		System.out.println("==========是否被领用:"+relationCompId);
	    if(!String.valueOf(relationCompId).equals("-1")){
	    	System.out.println("==========是否被领用FANHUI:"+relationCompId);
	    	throw new ServiceException("00099999", "此站点已被领用");
	    }
		
		BasCompInfo compInfo =redisService.get("compInfofindById", String.valueOf(webCompId), BasCompInfo.class);
		if (PubMethod.isEmpty(compInfo)) {
			compInfo = this.basCompInfoMapperV2.findById(webCompId);
			redisService.put("compInfofindById", String.valueOf(webCompId), compInfo);
			
	    }
		
		Long longID=IdWorker.getIdWorker().nextId();
		System.out.println("..........新的信息compid:"+longID);
		//1.插入一条站点数据
		compInfo.setCompId(longID);
		compInfo.setCompName(compName);
		compInfo.setBelongToNetId(Long.parseLong(belongToNetId)); //关联的网络
		compInfo.setCompAddress(county);  //地址
		compInfo.setRelationCompId(longID);  //从网络信息生成的,标记被自己领用
		compInfo.setBelongToCompId((long) -1);
		compInfo.setCompTypeNum("1006");  //加盟公司
		compInfo.setCompStatus(Short.parseShort("-1"));//公司状态 -1:创建,0:提交待审核,1:审核通过,2: 审核不通过4:再次提交待审核',
		compInfo.setCompNum(null);    //公司标识代码
		compInfo.setCompShort(null);  //公司简称
		compInfo.setFirstLetter(PiyinUtil.cn2py(compName.substring(0, 1)).toUpperCase());
		compInfo.setCompAddressId(Long.valueOf(addressId));  
		compInfo.setRegistFlag((short)1);// 已注册
		compInfo.setCompRegistWay((short)6);  //网点注册方式 6：手机端 
		compInfo.setWriteSendStatus((short) 0);
		compInfo.setGoodsPaymentStatus((short) 0);
		compInfo.setCreateTime(new Date());
		compInfo.setModifiyTime(new Date());
		compInfo.setTaoGoodsPaymentStatus((short) -1);
		compInfo.setCreateUser(Long.parseLong(member_id));
		
		basCompInfoMapperV2.insert(compInfo);
		
		basCompInfoMapperV2.updateCompTypeNum(longID, webCompId);  //2.更新原网络抓取站点的relation_comp_id
		System.out.println("..........更新成功");
//		// 3.插入 到bas_employeeaudit 1条数据(归属通过信息)
//		basCompInfoMapperV2.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), Long.parseLong(member_id), 1L, longID,2,1, new Date());//插入归属审核是通过
		
		doGsInfo(member_id, roleType, longID.toString());//---2016年4月27日11:01:52--by hu.zhao//
		
		//----------如果存在修改此人的身份和快递认证的 compid和角色id--------start---//
		int sf = this.compInfoMapper.queryCountSF(String.valueOf(member_id));
		if(sf > 0){
			//修改 身份认证信息的站点id和角色
			basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(member_id),longID,Integer.valueOf(roleType));
		}
		int kd = this.compInfoMapper.queryCountKD(String.valueOf(member_id));
		if (kd > 0) {
			//修改 快递认证信息的站点id和角色
			basCompInfoMapperV2.updateKDAuditInfo(Long.valueOf(member_id),longID,Integer.valueOf(roleType));
		}
		//----------如果存在修改此人的身份和快递认证的 compid和角色id--------end---//	
			
		//初始化身份和快递认证信息
		this.initExpressAudit(member_id, String.valueOf(longID), roleType);
		
		Map<String, Object> map = new HashMap<String, Object>();  //4.插入关系表：一个站点有一个站长
		map.put("id", IdWorker.getIdWorker().nextId());
		map.put("member_id", member_id);
		map.put("comp_id", longID);
		map.put("role_id", 1);//1：站长
		map.put("area_color", "#c2c2c2");
		map.put("employee_work_status_flag", 1);
		map.put("review_task_receiving_flag", 1);
		basEmployeeRelationMapperV2.insertBasEmployeeRelation(map);
		
		//将实名更新
		basCompInfoMapperV2.updateMemberNameOfmemberInfo(memberName, Long.valueOf(member_id));
		
	    redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
	    redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		redisService.removeAll("compCache");
		redisService.removeAll("compImageCache");
		redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
		
		redisService.removeAll("queryCompInfoByFirstLetterCache");//清缓存
		redisService.removeAll("queryRelationCompIdOfBasCompinfo"); //站长领用站点后清除这个缓存

		resultMap.put("compId", longID);
		return resultMap;
		
	}
	/**
	 * 初始化身份和快递认证审核信息（两条-1）
	 */
	@Override
	public void initExpressAudit(String memberId, String compId, String roleType) {
		//同时初始化插入身份和快递认证信息---状态为-1
		BasEmployeeAudit memberMapSF = basEmployeeAuditMapperV2.queryRealNameStatus(memberId);//身份认证
		if (PubMethod.isEmpty(memberMapSF)) {
			//保存审核信息==身份认证
			BasEmployeeAudit auditShenfen = new BasEmployeeAudit();
			auditShenfen.setId(IdWorker.getIdWorker().nextId());
			auditShenfen.setMemberId(Long.valueOf(memberId));
			auditShenfen.setAuditItem((short) 1);		//1:身份审核,2:归属审核
			auditShenfen.setAuditOpinion((short) -1);	//1:审核通过,2:审核未通过 0待审核 -1 未完善
			auditShenfen.setAuditComp(Long.valueOf(compId));
			auditShenfen.setApplicationRoleType(Short.valueOf(roleType));//1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
			basEmployeeAuditMapperV2.insertAuditInfo(auditShenfen);//身份
		}
		
		BasEmployeeAudit memberMapKD = basEmployeeAuditMapperV2.queryExpressStatus(memberId);//快递员认证状态
		if (PubMethod.isEmpty(memberMapKD)) {
			//保存审核信息==身份认证
			BasEmployeeAudit auditKuaidi = new BasEmployeeAudit();
			auditKuaidi.setId(IdWorker.getIdWorker().nextId());
			auditKuaidi.setMemberId(Long.valueOf(memberId));
			auditKuaidi.setAuditItem((short) 3);		//1:身份审核,2:归属审核
			auditKuaidi.setAuditOpinion((short) -1);	//1:审核通过,2:审核未通过 0待审核 -1 未完善
			auditKuaidi.setAuditComp(Long.valueOf(compId));
			auditKuaidi.setApplicationRoleType(Short.valueOf(roleType));//1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
			basEmployeeAuditMapperV2.insertAuditInfo(auditKuaidi);//身份
		}
		redisService.removeAll("verifyInfo-memberId-cache");  //清楚缓存
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
	}
	/**
	 * 查询在同一个快递网络下是否有重名的站点
	 */
	@Override
	public HashMap<String, Object> isRepeatCompInfoName(String netId,String compName) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		paramMap.put("netId", netId);
		paramMap.put("compName", compName.trim());
		List<HashMap<String, Object>> sameCompInfo = basCompInfoMapperV2.isRepeatCompInfoName(paramMap);
		if (sameCompInfo!=null && sameCompInfo.size()>0) {
			resultMap.put("isRepeat", "1");
		}else {
			resultMap.put("isRepeat", "0");
		}
		return resultMap;
	}
	/**
	 * 站长创建新站点/营业分部
	 */
	@Override
	public HashMap<String, Object> saveNewCompInfo(Long memberId, Long netId,String compTypeNum, String compName, String compTelephone,String county,String addressId,String descriptionMsg,String roleType,String memberName) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
		//1.添加一条站点或营业部
		BasCompInfo compInfo=new BasCompInfo();
		Long compid=IdWorker.getIdWorker().nextId();
		compInfo.setCompId(compid);
		compInfo.setRelationCompId((long) -1);
		this.setCompInfo(compInfo, memberId, netId, compTypeNum, null, compName, compTelephone, Long.valueOf(addressId), 
				county, (short)6, (short)1,descriptionMsg);
		int count = this.basCompInfoMapperV2.insert(compInfo);
		if(count<1){
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.009", "保存公司信息参数异常,存在重名网点禁止新增");
		}
//		//插入一条归属信息
//		basCompInfoMapperV2.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), memberId, 1L, compid,2,1,new Date());
		doGsInfo(memberId.toString(), roleType, compid.toString());//---2016年4月27日11:12:46--by hu.zhao//
		//----------如果存在修改此人的身份和快递认证的 compid和角色id--------start---//
		int sf = this.compInfoMapper.queryCountSF(String.valueOf(memberId));
		if(sf > 0){
			//修改 身份认证信息的站点id和角色
			basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(memberId),compid,Integer.valueOf(roleType));
		}
		int kd = this.compInfoMapper.queryCountKD(String.valueOf(memberId));
		if (kd > 0) {
			//修改 快递认证信息的站点id和角色
			basCompInfoMapperV2.updateKDAuditInfo(Long.valueOf(memberId),compid,Integer.valueOf(roleType));
		}
		//----------如果存在修改此人的身份和快递认证的 compid和角色id--------end---//	
		//初始化身份和快递认证信息
		this.initExpressAudit(String.valueOf(memberId), String.valueOf(compid), roleType);
		 //3、插入关系表：一个站点有一个站长
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("id", IdWorker.getIdWorker().nextId());
		map.put("member_id", memberId);
		map.put("comp_id", compid);
		map.put("role_id", 1);//1：站长
		map.put("area_color", "#c2c2c2");
		map.put("employee_work_status_flag", 1);
		map.put("review_task_receiving_flag", 1);
		basEmployeeRelationMapperV2.insertBasEmployeeRelation(map);
		
		//将实名更新
		basCompInfoMapperV2.updateMemberNameOfmemberInfo(memberName, Long.valueOf(memberId));
	    
	    redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
	    redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		redisService.removeAll("compCache");
		redisService.removeAll("compImageCache");
		
		redisService.removeAll("queryCompInfoByFirstLetterCache");//清缓存
		redisService.removeAll("queryRelationCompIdOfBasCompinfo"); //站长领用站点后清除这个缓存
		
		resultMap.put("compid", compid);
		return resultMap;
	}
	/**
	 * @MethodName: net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.java.setCompInfo 
	 * @Description: TODO(封装map) 
	 * @param @param compInfo
	 * @param @param loginMemberId
	 * @param @param netId
	 * @param @param compTypeNum
	 * @param @param useCompId
	 * @param @param compName
	 * @param @param compTelephone
	 * @param @param addressId
	 * @param @param address
	 * @param @param compRegistWay
	 * @param @param registFlag   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-12-4
	 * @auth zhaohu
	 */
	private void setCompInfo(BasCompInfo compInfo, Long loginMemberId, Long netId, String compTypeNum, Long useCompId, String compName, String compTelephone, Long addressId,
			String address, Short compRegistWay,Short registFlag,String descriptionMsg) {
		compInfo.setCompNum(null);
		compInfo.setCompShort(null);
		compInfo.setCompName(compName);
		compInfo.setFirstLetter(PiyinUtil.cn2py(compName.substring(0, 1)).toUpperCase());
		compInfo.setRegistFlag(registFlag);// 已注册
		compInfo.setCompTypeNum(compTypeNum);
		compInfo.setCompAddressId(addressId);
		compInfo.setCompAddress(address);
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
		if ("1050".equals(compTypeNum)) {
			compInfo.setDescriptionMsg(descriptionMsg);
			logger.info("站长创建的是营业分部！");
		}else{
			compInfo.setDescriptionMsg(null);
			logger.info("站长创建的是加盟站点！");
		}
	}
	
	
	
	
	

	/**
	 * @MethodName: net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.java.registerCourier 
	 * @Description: 注册收派员或者后勤角色
	 * @param memberId  快递员membrId,ucenter自动生成,不能null 
	 *        memberName
	 * @param netId      不能null
	 * @param compId     可null 
	 * @param compTypeNum 类型
	 * @param compName   不能null
	 * @param applicationRoleType  申请角色类型  0 收派员 -1 后勤  不能null
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-12-3
	 * @auth guoqiang.jing
	 */
	public HashMap<String, Object> registerCourier(Long memberId,String memberName,Long netId,String compId,String compTypeNum,String compName,String applicationRoleType){
		
		/*Long num =redisService.get("queryRelationCompIdOfBasCompinfo", String.valueOf(compId), Long.class);  //站点被领用后要清除这里的缓存
		if (PubMethod.isEmpty(num)) {
		   num = basCompInfoMapperV2.queryRelationCompIdOfBasCompinfo(compId); //查询此站点是否被领用,-1没有被领用
		   redisService.put("queryRelationCompIdOfBasCompinfo", String.valueOf(compId), num);
	    }*/
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		if("1003".equals(compTypeNum)||PubMethod.isEmpty(compId)){//compTypeNum=1003||compId=null进入虚拟站点
			Map<String,Object> map = new HashMap<String,Object>();
			///将这个人和虚拟的站点建立关系
			//先查出这个netId下的虚拟站点
			BasCompInfo basCompInfo =redisService.get("queryVirtualCompInfo", String.valueOf(netId), BasCompInfo.class);  //
			if (PubMethod.isEmpty(basCompInfo)) {
			   basCompInfo = this.basCompInfoMapperV2.queryVirtualCompInfo(netId,"1006");
			    redisService.put("queryVirtualCompInfo", String.valueOf(netId), basCompInfo);
			}
			Long virtualCompId = basCompInfo.getCompId();
			
			//Long id =IdWorker.getIdWorker().nextId();
			int auditItem=2;        //审核项 1:身份审核(实名审核), 2:归属审核 ,3:快递员认证审核,4.客服确认快递员'
			int auditOpinion=-1;    //审核结果 :-1为完善   0待审核    1通过    2拒绝
			
			//插入快递员归属审核:待审核-bas_employeeaudit
			int gs = this.compInfoMapper.queryCountGS(String.valueOf(memberId));
			if (gs > 0) {
				logger.info("此快递员已经有了归属，数据异常，请查看数据库。");
				//修改 归属认证信息的站点id和角色
				basCompInfoMapperV2.updateGSAuditInfo(memberId,virtualCompId,Integer.parseInt(applicationRoleType));
			}else {
				basCompInfoMapperV2.addGSAudit( IdWorker.getIdWorker().nextId(),memberId, virtualCompId, Integer.parseInt(applicationRoleType), new Date(), auditItem,auditOpinion,compName);
			}
			
			int sf = this.compInfoMapper.queryCountSF(String.valueOf(memberId));
			if(sf > 0){
				//修改 身份认证信息的站点id和角色
				basCompInfoMapperV2.updateSFAuditInfo(memberId,virtualCompId,Integer.parseInt(applicationRoleType));
			}else{
				//插入快递员身份审核:未完善-bas_employeeaudit
				basCompInfoMapperV2.addSFAudit( IdWorker.getIdWorker().nextId(),memberId, virtualCompId, Integer.parseInt(applicationRoleType), new Date(), 1,-1,compName);
			}
			int kd = this.compInfoMapper.queryCountKD(String.valueOf(memberId));
			if (kd > 0) {
				//修改 快递认证信息的站点id和角色
				basCompInfoMapperV2.updateKDAuditInfo(memberId,virtualCompId,Integer.parseInt(applicationRoleType));
			}else{
				//插入快递员快递员审核:未完善-bas_employeeaudit
				basCompInfoMapperV2.addKDAudit( IdWorker.getIdWorker().nextId(),memberId, virtualCompId, Integer.parseInt(applicationRoleType), new Date(), 3,-1,compName);
			}
			resultMap.put("compId", virtualCompId);
		}else{
			//Long id =IdWorker.getIdWorker().nextId();
			int auditItem=2;      //审核项 1:身份审核(实名审核), 2:归属审核 ,3:快递员认证审核,4.客服确认快递员'
			int auditOpinion=0;    //审核结果 :-1为完善   0待审核    1通过    2拒绝
			//插入快递员归属审核:待审核-bas_employeeaudit
			int gs = this.compInfoMapper.queryCountGS(String.valueOf(memberId));
			if (gs > 0) {
				logger.info("此快递员已经有了归属，数据异常，请查看数据库。");
				//修改 归属认证信息的站点id和角色
				basCompInfoMapperV2.updateGSAuditInfo(memberId,Long.parseLong(compId),Integer.parseInt(applicationRoleType));
			}else {
				basCompInfoMapperV2.addGSAudit( IdWorker.getIdWorker().nextId(),memberId, Long.parseLong(compId), Integer.parseInt(applicationRoleType), new Date(), auditItem,auditOpinion,compName);
			}
			
			
			int sf = this.compInfoMapper.queryCountSF(String.valueOf(memberId));
			if(sf > 0){
				//修改 身份认证信息的站点id--compId
				basCompInfoMapperV2.updateSFAuditInfo(memberId,Long.parseLong(compId),Integer.parseInt(applicationRoleType));
			}else{
				//插入快递员身份审核:未完善-bas_employeeaudit
				basCompInfoMapperV2.addSFAudit( IdWorker.getIdWorker().nextId(),memberId, Long.parseLong(compId), Integer.parseInt(applicationRoleType), new Date(), 1,-1,compName);
			}
			int kd = this.compInfoMapper.queryCountKD(String.valueOf(memberId));
			if (kd > 0) {
				//修改 快递认证信息的站点id--compId
				basCompInfoMapperV2.updateKDAuditInfo(memberId,Long.parseLong(compId),Integer.parseInt(applicationRoleType));
			}else{
				//插入快递员快递员审核:未完善-bas_employeeaudit
				basCompInfoMapperV2.addKDAudit( IdWorker.getIdWorker().nextId(),memberId, Long.parseLong(compId), Integer.parseInt(applicationRoleType), new Date(), 3,-1,compName);
			}
			
			logger.info("=======准备推送" );						
			//给有站长的站点推送
			if(!PubMethod.isEmpty(compId)){
				logger.info("=======进入推送compId="+compId );
				Map<String, Object> retMap= basCompInfoMapperV2.queryPushMessage(Long.parseLong(compId));
				//logger.info("=======size大小["+retMap.size()+"]============================" );
				//logger.info("=======member_id:["+(Long) retMap.get("member_id")+"],member_phone:"+(String) retMap.get("member_phone")+",compId:"+(Long)retMap.get("comp_id") );
			   logger.info("=======查询推送retMap="+retMap );	
				if(!PubMethod.isEmpty(retMap)){
					Object object_member_id = retMap.get("member_id");
					Object object_member_phone = retMap.get("member_phone");
					Object object_comp_id = retMap.get("comp_id");
					 logger.info("=======object_member_id:"+object_member_id+",object_member_phone:"+object_member_phone+",object_comp_id:"+object_comp_id );	
					
					if(!PubMethod.isEmpty(object_member_id)&&!PubMethod.isEmpty(object_member_phone)){
						Long memberIdZZ = (Long) retMap.get("member_id");//站长的member_id
						String mob = (String) retMap.get("member_phone");//站长的电话
						sendNoticeService.applyJoinNet(memberIdZZ, mob, Short.parseShort("1"));//推送申请入站
					}
					
					
				}
				logger.info("=======if条件完成======" );
			}
			
			logger.info("=======外层if条件完成======" );
			resultMap.put("compId", compId);
		}
		
		logger.info("=======准备更新" );
		//将实名更新进
		basCompInfoMapperV2.updateMemberNameOfmemberInfo(memberName, memberId);
		return resultMap;
		
	}
	
	
	/** 
	* @Description: 查询单号是否被重复认证   
	* @author: jingguoqiang
	* @date 2015-12-4 下午3:54:39 
	*/ 
	@Override
	public List queryRepeatOrder(String orderArr, String netId,String phone) {
		  List retList = new ArrayList();
		   String[] strArr = orderArr.split(",");
		   
		   for(String order:strArr){
			     List<String> orders = new ArrayList<String>();
				   orders = basCompInfoMapperV2.queryRepeatOrderOfRel(order,netId,phone);
				 //System.out.println("集合:"+orderAndMemberIdAndPhone.toString());
				  if(!orders.isEmpty()){
					  retList.add(order);
				  }
			   
		   }  
		   logger.info("最后返回的单号重复结果:"+retList.toString());
		return retList;
	}
	/**
	 * 查询此站点下的收派员和后勤人员数量，不包括站长角色
	 */
	@Override
	public int queryCountInCompInfo(String compId) {
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.queryCountInCompInfo.001","compId参数异常");
		}
		return this.basEmployeeAuditMapperV2.queryCountInCompInfo(compId);
	}
	/**
	 * 查询此代收点下的店员数量，不包括店长角色
	 */
	@Override
	public int queryCountInShop(String compId) {
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.queryCountInShop.001","compId参数异常");
		}
		return this.basEmployeeAuditMapperV2.queryCountInShop(compId);
	}
	/**
	 * 站长离职
	 */
	@Override
	public void leaveOfficeByLeader(Long memberId, Long compId,String memberName, String memberPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		this.basEmployeeAuditMapper.deleteRelationLogByMemberId(memberId);
		
		int a = this.basEmployeeRelationMapper.deleteMemberInfo(memberId);//删除归属关系表中的记录
		map = createMap(memberId, compId, memberName);
		int b = this.basCompEmployeeResumeMapper.doAddResum(map);
		this.areaElectronicFenceService.removeAreaMember(compId, memberId);
		this.parcelInfoService.deleteParcelConnectionByMemberId(memberId);
/*		String content = "亲爱的接单王用户，很遗憾您已从所在站点离职，请选择新任职站点！";
		noticeHttpClient.doSmsSend("02", 0L, memberPhone, content, String.valueOf(compId));*/
		//********2015年12月19日18:43:47  站长离职不需要推送**********//
//		this.sendNoticeService.quitToExpMember(memberId, memberPhone);
		//********2015年12月19日18:43:47  站长离职不需要推送**********//
//		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		expCustomerInfoService.deleteByMemberId(compId, memberId);
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
	* @Description: 查询归属详情   
	* @author: jingguoqiang
	* @date 2015-12-5 下午7:37:45 
	*/ 
	@Override
	public Map queryGSInfo(Long memberId, int roleId) {
		Map map = basCompInfoMapperV2.queryResponsibles(memberId,roleId);
		map.put("headImgPath", headImgPath + memberId + ".png");
		return map;
	}
	
	/**
	 * 判断该站点是否已认证V3（站长/收派员/后勤角色使用）
	 */
	@Override
	public HashMap<String, Object> isIdentifyCompInfo(String compTypeNum,String compId) {
		HashMap<String, Object> resultMap = new HashMap<>();
		if ("1003".equals(compTypeNum) || PubMethod.isEmpty(compId)) {
			resultMap.put("isIdentify", "0");
			logger.info("判断该站点的站点信息是否已认证：选择的是1003类型或者手填的站点名称--compId为空，默认状态为未认证！");
		}else if ("1006".equals(compTypeNum) || "1050".equals(compTypeNum) && !PubMethod.isEmpty(compId) ) {
			String compStatus = basCompInfoMapperV2.queryCompStatusByCompId(compId);
			if (!PubMethod.isEmpty(compStatus)) {
				if ("-1".equals(compStatus) || "2".equals(compStatus)) {//未认证或者认证失败
					resultMap.put("isIdentify", "0");
					logger.info("判断该站点的站点信息是否已认证：该站点认证状态为未认证或认证失败！状态为："+compStatus);
				}else {
					logger.info("判断该站点的站点信息是否已认证：该站点认证状态为待认证或认证成功！状态为："+compStatus);
					resultMap.put("isIdentify", "1");
				}
			}
		}
		resultMap.put("compId", compId);
		return resultMap;
	}
	/**
	 * 收派员/后勤创建或领取未认证站点并提交站点认证信息----收派员/后勤角色使用V3
	 */
	@Override
	public HashMap<String, Object> saveCompInfoByCourier(Long compId,Long memberId, Long netId, String compTypeNum, 
			String compName,String compTelephone, String county, String addressId,String roleType, String memberName,
			String responsible, String responsibleTelephone, String responsibleNum, String licenseNum, String holdImg, String reverseImg) {
		HashMap<String, Object> resultMap = new HashMap<>();
		//compId为空，创建站点/营业分部、并提交站点认证信息
		if (PubMethod.isEmpty(compId)) {
			HashMap<String, Object> compInfo = this.saveNewCompInfoByCourier(memberId, netId, compTypeNum, compName, compTelephone, county, addressId, roleType, memberName);
			logger.info("compId为空，创建站点/营业分部成功！");
			if (!PubMethod.isEmpty(compInfo)) {
				Long loginCompId = Long.valueOf(compInfo.get("compid")+"");//创建站点生成的新compId
				this.saveCompVerifyInfoV3(loginCompId, responsible, responsibleTelephone, responsibleNum, holdImg, reverseImg, memberId, licenseNum);
				logger.info("收派员创建站点id为："+loginCompId+"，提交站点认证信息成功！");
				resultMap.put("compId", loginCompId);
			}
			//1003类型，领用站点，并提交站点认证信息
		}else if ("1003".equals(compTypeNum) && !PubMethod.isEmpty(compId)) {
			HashMap<String, Object> compInfo = this.submitUseCompInfoByCourier(compId, compName, String.valueOf(netId), county, String.valueOf(memberId), addressId, roleType, memberName);
			logger.info("收派员选择1003，领用站点成功！");
			if (!PubMethod.isEmpty(compInfo)) {
				Long loginCompId = Long.valueOf(compInfo.get("compId")+"");//领用站点生成的新compId
				this.saveCompVerifyInfoV3(loginCompId, responsible, responsibleTelephone, responsibleNum, holdImg, reverseImg, memberId, licenseNum);
				logger.info("收派员领用站点id为："+loginCompId+"，提交站点认证信息成功！");
				resultMap.put("compId", loginCompId);
			}
			//1006或1050类型，直接提交站点认证信息
		}else if ("1006".equals(compTypeNum) || "1050".equals(compTypeNum) && !PubMethod.isEmpty(compId)) {
			this.saveCompVerifyInfoV3(compId, responsible, responsibleTelephone, responsibleNum, holdImg, reverseImg, memberId, licenseNum);
			logger.info("收派员选择的1006或者1050未认证的站点ID："+compId+"，直接提交站点认证信息，提交站点认证信息成功！");
			resultMap.put("compId", compId);
		}
		return resultMap;
	}
	
	/**
	 * 收派员、后勤角色创建新站点/营业分部
	 */
	public HashMap<String, Object> saveNewCompInfoByCourier(Long memberId, Long netId,String compTypeNum, String compName, String compTelephone,String county,String addressId,String roleType,String memberName) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
		//1.添加一条站点或营业部
		BasCompInfo compInfo=new BasCompInfo();
		Long compid=IdWorker.getIdWorker().nextId();
		compInfo.setCompId(compid);
		compInfo.setRelationCompId((long) -1);
		this.setCompInfoByCourier(compInfo, memberId, netId, compTypeNum, null, compName, compTelephone, Long.valueOf(addressId), 
				county, (short)6, (short)1);
		int count = this.basCompInfoMapperV2.insert(compInfo);
		if(count<1){
			throw new ServiceException("openapi.CompInfoServiceImpl.saveNewCompInfoByCourier.009", "保存站点异常");
		}
//		//插入一条归属信息//待审核
//		basCompInfoMapperV2.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), memberId, Long.valueOf(roleType), compid,2,0,new Date());
		doGsInfo(memberId.toString(), roleType, compid.toString());//---2016年4月27日11:12:46--by hu.zhao//
		//----------如果存在修改此人的身份和快递认证的 compid和角色id--------start---//
		int sf = this.compInfoMapper.queryCountSF(String.valueOf(memberId));
		if(sf > 0){
			//修改 身份认证信息的站点id和角色
			basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(memberId),compid,Integer.valueOf(roleType));
		}
		int kd = this.compInfoMapper.queryCountKD(String.valueOf(memberId));
		if (kd > 0) {
			//修改 快递认证信息的站点id和角色
			basCompInfoMapperV2.updateKDAuditInfo(Long.valueOf(memberId),compid,Integer.valueOf(roleType));
		}
		//----------如果存在修改此人的身份和快递认证的 compid和角色id--------end---//	
		//初始化身份和快递认证信息（-1）
		this.initExpressAudit(String.valueOf(memberId), String.valueOf(compid), roleType);
		//将实名更新
		basCompInfoMapperV2.updateMemberNameOfmemberInfo(memberName, Long.valueOf(memberId));
	    
	    redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
	    redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		redisService.removeAll("compCache");
		redisService.removeAll("compImageCache");
		
		redisService.removeAll("queryCompInfoByFirstLetterCache");//清缓存
		redisService.removeAll("queryRelationCompIdOfBasCompinfo"); //站长领用站点后清除这个缓存
		
		resultMap.put("compid", compid);
		return resultMap;
	}
	/**
	 * 封装数据
	 */
	private void setCompInfoByCourier(BasCompInfo compInfo, Long loginMemberId, Long netId, String compTypeNum, Long useCompId, String compName, String compTelephone, Long addressId,
			String address, Short compRegistWay,Short registFlag) {
		compInfo.setCompNum(null);
		compInfo.setCompShort(null);
		compInfo.setCompName(compName);
		compInfo.setFirstLetter(PiyinUtil.cn2py(compName.substring(0, 1)).toUpperCase());
		compInfo.setRegistFlag(registFlag);// 已注册
		compInfo.setCompTypeNum(compTypeNum);
		compInfo.setCompAddressId(addressId);
		compInfo.setCompAddress(address);
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
	
	/**
	 * 收派员/后勤角色领用网络抓取的站点
	 */
	public HashMap<String, Object> submitUseCompInfoByCourier(Long webCompId,String compName, String belongToNetId, String county,String member_id,String addressId,String roleType,String memberName) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Long relationCompId= basCompInfoMapperV2.queryRelationCompId(webCompId);
		System.out.println("==========是否被领用:"+relationCompId);
	    if(!String.valueOf(relationCompId).equals("-1")){
	    	System.out.println("==========是否被领用FANHUI:"+relationCompId);
	    	throw new ServiceException("00099999", "此站点已被领用");
	    }
		BasCompInfo compInfo =redisService.get("compInfofindById", String.valueOf(webCompId), BasCompInfo.class);
		if (PubMethod.isEmpty(compInfo)) {
			compInfo = this.basCompInfoMapperV2.findById(webCompId);
			redisService.put("compInfofindById", String.valueOf(webCompId), compInfo);
			
	    }
		Long longID=IdWorker.getIdWorker().nextId();
		System.out.println("..........新的信息compid:"+longID);
		//1.插入一条站点数据
		compInfo.setCompId(longID);
		compInfo.setCompName(compName);
		compInfo.setBelongToNetId(Long.parseLong(belongToNetId)); //关联的网络
		compInfo.setCompAddress(county);  //地址
		compInfo.setRelationCompId(longID);  //从网络信息生成的,标记被自己领用
		compInfo.setBelongToCompId((long) -1);
		compInfo.setCompTypeNum("1006");  //加盟公司
		compInfo.setCompStatus(Short.parseShort("-1"));//公司状态 -1:创建,0:提交待审核,1:审核通过,2: 审核不通过4:再次提交待审核',
		compInfo.setCompNum(null);    //公司标识代码
		compInfo.setCompShort(null);  //公司简称
		compInfo.setFirstLetter(PiyinUtil.cn2py(compName.substring(0, 1)).toUpperCase());
		compInfo.setCompAddressId(Long.valueOf(addressId));  
		compInfo.setRegistFlag((short)1);// 已注册
		compInfo.setCompRegistWay((short)6);  //网点注册方式 6：手机端 
		compInfo.setWriteSendStatus((short) 0);
		compInfo.setGoodsPaymentStatus((short) 0);
		compInfo.setCreateTime(new Date());
		compInfo.setModifiyTime(new Date());
		compInfo.setTaoGoodsPaymentStatus((short) -1);
		compInfo.setCreateUser(Long.parseLong(member_id));
		basCompInfoMapperV2.insert(compInfo);
		basCompInfoMapperV2.updateCompTypeNum(longID, webCompId);  //2.更新原网络抓取站点的relation_comp_id
		System.out.println("..........更新成功");
//		// 3.插入 到bas_employeeaudit 1条数据(归属待审核信息)
//		basCompInfoMapperV2.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), Long.parseLong(member_id), Long.valueOf(roleType), longID,2,0, new Date());//插入归属审核是待审核
		doGsInfo(member_id.toString(), roleType, longID.toString());//---2016年4月27日11:20:38--by hu.zhao//
		//----------如果存在修改此人的身份和快递认证的 compid和角色id--------start---//
		int sf = this.compInfoMapper.queryCountSF(String.valueOf(member_id));
		if(sf > 0){
			//修改 身份认证信息的站点id和角色
			basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(member_id),longID,Integer.valueOf(roleType));
		}
		int kd = this.compInfoMapper.queryCountKD(String.valueOf(member_id));
		if (kd > 0) {
			//修改 快递认证信息的站点id和角色
			basCompInfoMapperV2.updateKDAuditInfo(Long.valueOf(member_id),longID,Integer.valueOf(roleType));
		}
		//----------如果存在修改此人的身份和快递认证的 compid和角色id--------end---//	
		//初始化身份和快递认证信息
		this.initExpressAudit(member_id, String.valueOf(longID), roleType);
		//将实名更新
		basCompInfoMapperV2.updateMemberNameOfmemberInfo(memberName, Long.valueOf(member_id));
	    redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
	    redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		redisService.removeAll("compCache");
		redisService.removeAll("compImageCache");
		redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
		redisService.removeAll("queryCompInfoByFirstLetterCache");//清缓存
		redisService.removeAll("queryRelationCompIdOfBasCompinfo"); //站长领用站点后清除这个缓存
		resultMap.put("compId", longID);
		return resultMap;
		
	}
	
	/**
	 * 提交站点认证信息V3
	 */
	@Override
	public HashMap<String, Object> saveCompVerifyInfoV3(Long loginCompId,String responsible, String responsibleTelephone,
			String responsibleNum, String holdImg, String reverseImg,Long memberId, String licenseNum) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
	    compbusinessInfoMapperV2.deleteBusinessMessage(loginCompId);//1.删除原来加的负责人信息
		BasCompbusiness	compbusinessInfo = new BasCompbusiness(); //1.新增负责人信息
		compbusinessInfo.setCompId(loginCompId);
		compbusinessInfo.setResponsible(responsible);
		compbusinessInfo.setResponsibleIdNum(responsibleNum);//身份证号
		compbusinessInfo.setResponsibleTelephone(responsibleTelephone);
		compbusinessInfo.setBusinessLicenseNum(licenseNum);//营业执照号
		compbusinessInfoMapperV2.insertMessage(compbusinessInfo);
		compimageMapperV2.deleteImageByCompId(loginCompId);//2.删除原来保存的照片
		System.out.println("..........保存认证照片开始");
		List<BasCompimage> compimageList = new ArrayList<BasCompimage>();  //2.保存认证照片
		BasCompimage compimage1 = new BasCompimage();
		BasCompimage compimage2 = new BasCompimage();
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
		compimageMapperV2.saveCompImageBatch(compimageList);
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
        logger.info("提交站点认证信息成功！V3版本！！！！！！！！！！！！！");
        resultMap.put("success", "true");
		return resultMap;
	}
	
	/**
	 * 站长领用网络抓取的站点V3--站长使用
	 */
	@Override
	public HashMap<String, Object> submitUseCompInfoV3(Long webCompId,
			String compName, String belongToNetId, String county,
			String member_id, String addressId, String roleType,
			String memberName, String compTypeNum) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		//1003-->直接领用；1006/1050没有站长-->直接加入此站点
		if ("1003".equals(compTypeNum) && !PubMethod.isEmpty(webCompId)) {
			
			BasCompInfo compInfo =redisService.get("compInfofindById", String.valueOf(webCompId), BasCompInfo.class);
			if (PubMethod.isEmpty(compInfo)) {
				compInfo = this.basCompInfoMapperV2.findById(webCompId);
				redisService.put("compInfofindById", String.valueOf(webCompId), compInfo);
				
			}
			Long longID=IdWorker.getIdWorker().nextId();
			System.out.println("..........新的信息compid:"+longID);
			//1.插入一条站点数据
			compInfo.setCompId(longID);
			compInfo.setCompName(compName);
			compInfo.setBelongToNetId(Long.parseLong(belongToNetId)); //关联的网络
			compInfo.setCompAddress(county);  //地址
			compInfo.setRelationCompId(longID);  //从网络信息生成的,标记被自己领用
			compInfo.setBelongToCompId((long) -1);
			compInfo.setCompTypeNum("1006");  //加盟公司
			compInfo.setCompStatus(Short.parseShort("-1"));//公司状态 -1:创建,0:提交待审核,1:审核通过,2: 审核不通过4:再次提交待审核',
			compInfo.setCompNum(null);    //公司标识代码
			compInfo.setCompShort(null);  //公司简称
			compInfo.setFirstLetter(PiyinUtil.cn2py(compName.substring(0, 1)).toUpperCase());
			compInfo.setCompAddressId(Long.valueOf(addressId));  
			compInfo.setRegistFlag((short)1);// 已注册
			compInfo.setCompRegistWay((short)6);  //网点注册方式 6：手机端 
			compInfo.setWriteSendStatus((short) 0);
			compInfo.setGoodsPaymentStatus((short) 0);
			compInfo.setCreateTime(new Date());
			compInfo.setModifiyTime(new Date());
			compInfo.setTaoGoodsPaymentStatus((short) -1);
			compInfo.setCreateUser(Long.parseLong(member_id));
			basCompInfoMapperV2.insert(compInfo);
			basCompInfoMapperV2.updateCompTypeNum(longID, webCompId);  //2.更新原网络抓取站点的relation_comp_id
			System.out.println("..........更新成功");
//			// 3.插入 到bas_employeeaudit 1条数据(归属通过信息)
//			basCompInfoMapperV2.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), Long.parseLong(member_id), 1L, longID,2,1, new Date());//插入归属审核是通过
			doGsInfo(member_id.toString(), roleType, longID.toString());//---2016年4月27日11:21:49--by hu.zhao//
			//----------如果存在修改此人的身份和快递认证的 compid和角色id--------start---//
			int sf = this.compInfoMapper.queryCountSF(String.valueOf(member_id));
			if(sf > 0){
				//修改 身份认证信息的站点id和角色
				basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(member_id),longID,Integer.valueOf(roleType));
			}
			int kd = this.compInfoMapper.queryCountKD(String.valueOf(member_id));
			if (kd > 0) {
				//修改 快递认证信息的站点id和角色
				basCompInfoMapperV2.updateKDAuditInfo(Long.valueOf(member_id),longID,Integer.valueOf(roleType));
			}
			//----------如果存在修改此人的身份和快递认证的 compid和角色id--------end---//	
			//初始化身份和快递认证信息
			this.initExpressAudit(member_id, String.valueOf(longID), roleType);
			Map<String, Object> map = new HashMap<String, Object>();  //4.插入关系表：一个站点有一个站长
			map.put("id", IdWorker.getIdWorker().nextId());
			map.put("member_id", member_id);
			map.put("comp_id", longID);
			map.put("role_id", 1);//1：站长
			map.put("area_color", "#c2c2c2");
			map.put("employee_work_status_flag", 1);
			map.put("review_task_receiving_flag", 1);
			basEmployeeRelationMapperV2.insertBasEmployeeRelation(map);
			//将实名更新
			basCompInfoMapperV2.updateMemberNameOfmemberInfo(memberName, Long.valueOf(member_id));
			redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
			redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
			redisService.removeAll("relationInfo-memberId-cache");
			redisService.removeAll("expressInfo-memberId-cache");
			redisService.removeAll("compCache");
			redisService.removeAll("compImageCache");
			redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
			redisService.removeAll("queryCompInfoByFirstLetterCache");//清缓存
			redisService.removeAll("queryRelationCompIdOfBasCompinfo"); //站长领用站点后清除这个缓存
			resultMap.put("compId", longID);
			
		//1006/1050没有站长-->直接加入此站点
		}else if("1006".equals(compTypeNum) || "1050".equals(compTypeNum) && !PubMethod.isEmpty(webCompId)) {
//			// 3.插入 到bas_employeeaudit 1条数据(归属通过信息)
//			basCompInfoMapperV2.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), Long.parseLong(member_id), 1L, webCompId,2,1, new Date());//插入归属审核是通过
			doGsInfo(member_id.toString(), roleType, webCompId.toString());//---2016年4月27日11:24:00--by hu.zhao//
			//----------如果存在修改此人的身份和快递认证的 compid和角色id--------start---//
			int sf = this.compInfoMapper.queryCountSF(String.valueOf(member_id));
			if(sf > 0){
				//修改 身份认证信息的站点id和角色
				basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(member_id),webCompId,Integer.valueOf(roleType));
			}
			int kd = this.compInfoMapper.queryCountKD(String.valueOf(member_id));
			if (kd > 0) {
				//修改 快递认证信息的站点id和角色
				basCompInfoMapperV2.updateKDAuditInfo(Long.valueOf(member_id),webCompId,Integer.valueOf(roleType));
			}
			//----------如果存在修改此人的身份和快递认证的 compid和角色id--------end---//	
			//初始化身份和快递认证信息
			this.initExpressAudit(member_id, String.valueOf(webCompId), roleType);
			
			Map<String, Object> map = new HashMap<String, Object>();  //4.插入关系表：一个站点有一个站长
			map.put("id", IdWorker.getIdWorker().nextId());
			map.put("member_id", member_id);
			map.put("comp_id", webCompId);
			map.put("role_id", 1);//1：站长
			map.put("area_color", "#c2c2c2");
			map.put("employee_work_status_flag", 1);
			map.put("review_task_receiving_flag", 1);
			basEmployeeRelationMapperV2.insertBasEmployeeRelation(map);
			
			//将实名更新
			basCompInfoMapperV2.updateMemberNameOfmemberInfo(memberName, Long.valueOf(member_id));
			redisService.removeAll("nearCompInfoByCompNameCache");//qingc缓存
			redisService.removeAll("verifyInfo-memberId-cache");  //清楚登陆
			redisService.removeAll("relationInfo-memberId-cache");
			redisService.removeAll("expressInfo-memberId-cache");
			redisService.removeAll("compCache");
			redisService.removeAll("compImageCache");
			redisService.removeAll("nearCompInfoByCompNameCache");//请缓存
			redisService.removeAll("queryCompInfoByFirstLetterCache");//清缓存
			redisService.removeAll("queryRelationCompIdOfBasCompinfo"); //站长领用站点后清除这个缓存
			resultMap.put("compId", webCompId);
		}
		return resultMap;
		
	}
	/**
	 * 查看站点认证信息V3
	 */
	@Override
	public HashMap<String, Object> showSiteInfoV3(String memberId) {
		logger.info("查看站点认证信息V3!!!!!! memberId="+memberId);
		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> map = basEmployeeAuditMapperV2.querySiteInfoByMemberId(memberId);
		if (!PubMethod.isEmpty(map)) {
			resultMap.put("responsible", PubMethod.isEmpty(map.get("responsible")) ? "" : map.get("responsible"));
			resultMap.put("responsibleTel", PubMethod.isEmpty(map.get("responsibleTel")) ? "" : map.get("responsibleTel"));
			resultMap.put("responsibleIdNum", PubMethod.isEmpty(map.get("responsibleIdNum")) ? "" : map.get("responsibleIdNum"));
			resultMap.put("businessLicenseNum", PubMethod.isEmpty(map.get("businessLicenseNum")) ? "" : map.get("businessLicenseNum"));
			resultMap.put("compStatus", PubMethod.isEmpty(map.get("compStatus")) ? "" : map.get("compStatus"));
		}else {
			resultMap.put("responsible","");
			resultMap.put("responsibleTel","");
			resultMap.put("responsibleIdNum","");
			resultMap.put("businessLicenseNum","");
			resultMap.put("compStatus","");
		}
		List<HashMap<String, Object>> imgList = compimageMapperV2.querySiteImage(memberId);
		if (!PubMethod.isEmpty(imgList)) {
			for(HashMap<String, Object> imgMap : imgList){
				resultMap.put("image_type_" + imgMap.get("image_type"),readPath + imgMap.get("image_url"));
			}
		}else {
			resultMap.put("image_type_2","");
			resultMap.put("image_type_8","");
		}
		
		//-----2016年4月23日15:02:32 by hu.zhao-增加审核失败原因---
		String auditReason = "";
		if(!PubMethod.isEmpty(map.get("compId"))){
			List<HashMap<String, Object>> list = basCompInfoMapperV2.queryCompAuditInfo(map.get("compId").toString());
			if(list!=null && list.size()>0){
				auditReason = PubMethod.isEmpty(list.get(0).get("audit_desc")) ? auditReason : list.get(0).get("audit_desc").toString();
			}
		}
		resultMap.put("auditReason", auditReason);
		//-----2016年4月23日15:02:32 by hu.zhao----
		
		return resultMap;
	}
	/**
	 * 充值通讯费赠送奖励推送
	 */
	@Override
	public void sendSmsByRecharge(Double money, Long memberId) {
		sendNoticeService.sendSmsByRecharge(money,memberId);
	}
	
	
	//根据memberId查询accountId
	@Override
	public String getAccountIdByMemberId(String memberId) {
		String accountId = "";
		String url = payUrl;
		String methodName = "ws/account/get/memberId";
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", String.valueOf(memberId));
		logger.info("publicapi 使用外部系统memberId查询账户接口: url =" + url + " methodName="	+ methodName + " map=" + map);
		String redAccountId = redisService.get("getAccountIdByMemberId", "getAccountIdByMemberId", String.class);
		if(PubMethod.isEmpty(redAccountId)){
			String result = this.Post(url + methodName, map);
			logger.info("publicapi 使用外部系统memberId查询账户接口: result =" + result);
			JSONObject res = JSON.parseObject(result);
			if(res.get("success").toString().equals("false")){
				throw new ServiceException("财务异常，查询账户Id失败");
			}else{
				Map resMap = (Map) res.get("data");
				accountId = (String) resMap.get("account_id");
				redisService.put("getAccountIdByMemberId", "getAccountIdByMemberId", accountId);
			}
			logger.info("外部系统通过memberId查询账户accountId："+accountId);
			return accountId;
		}else{
			//不为空直接把缓存中的accountId返回回去
			return redAccountId;
		}
	}
	@Override
	public Object parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @MethodName: net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl.java.changeGsInfo 
	 * @Description: TODO( 如果存在归属信息，则更新---解决审核信息错乱的问题 ) 
	 * @param @param memberId
	 * @param @param roleId
	 * @param @param compId   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-4-27
	 * @auth zhaohu
	 */
	public void doGsInfo(String memberId,String roleId,String compId) {
		String status = "0";
		BasEmployeeAudit gsAudit = basEmployeeAuditMapperV2.queryGsAuditInfo(memberId);//归属认证
		//存在归属信息
		if(!PubMethod.isEmpty(gsAudit)){
			//修改 归属认证信息的站点id和角色
			//如果是快递员、后勤、店员角色，归属默认是待审核状体0，站长和店长角色默认是通过状态1
			if("-1".equals(roleId) || "0".equals(roleId) || "3".equals(roleId)){
				basEmployeeAuditMapperV2.updateGSAuditInfo(memberId,compId,roleId,new Date(),status);
			}else if("1".equals(roleId) || "2".equals(roleId)) {
				status = "1";
				basEmployeeAuditMapperV2.updateGSAuditInfo(memberId,compId,roleId,new Date(),status);
			}
			//修改人员关系表bas_employee_relation
			basEmployeeAuditMapperV2.updateEmployeeRelation(memberId,compId,roleId);
		}else {
			if("-1".equals(roleId) || "0".equals(roleId) || "3".equals(roleId)){
				basCompInfoMapperV2.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), Long.parseLong(memberId), Long.parseLong(roleId), Long.parseLong(compId),2,Integer.valueOf(status), new Date());//插入归属审核
			}else if("1".equals(roleId) || "2".equals(roleId)) {
				status = "1";
				basCompInfoMapperV2.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), Long.parseLong(memberId), Long.parseLong(roleId), Long.parseLong(compId),2,Integer.valueOf(status), new Date());//插入归属审核
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
