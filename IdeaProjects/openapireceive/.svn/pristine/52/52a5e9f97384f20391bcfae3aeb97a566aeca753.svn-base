package net.okdi.api.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.okdi.api.dao.BasCompEmployeeResumeMapper;
import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasEmployeeAuditMapper;
import net.okdi.api.dao.BasEmployeeRelationMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.ExpCompRelationMapper;
import net.okdi.api.dao.MemberAddressInfoMapper;
import net.okdi.api.dao.MemberCommInfoMapper;
import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.dao.MemberTagMapper;
import net.okdi.api.dao.QueryUserInfoMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.api.entity.BasEmployeeRelation;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.MemberCommInfo;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.MemberInvitationRelation;
import net.okdi.api.service.AreaElectronicFenceService;
import net.okdi.api.service.CourierService;
import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.MemberInvitationService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.RobInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.vo.VO_MemberInfo;
import net.okdi.apiV1.entity.PushMessageInfoItem;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.VerifyUtil;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class MemberInfoServiceImpl extends BaseServiceImpl<MemberInfo> implements MemberInfoService {

	public static final String[] flag = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
			"b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z" };
	
	private static final String CONTENT = "想注册为您本站收派员，请在好递网站点专区中进行认证操作http://www.okdi.net/express.jsp";
	public static final Log logger = LogFactory.getLog(MemberInfoServiceImpl.class);
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private MemberCommInfoMapper memberCommInfoMapper;
	@Autowired
	private BasEmployeeRelationMapper basEmployeeRelationMapper;
	@Autowired
	private BasCompEmployeeResumeMapper basCompEmployeeResumeMapper;
	@Autowired
	private BasEmployeeAuditMapper basEmployeeAuditMapper;
	@Autowired
	private ExpCompRelationMapper expCompRelationMapper;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ConstPool constPool;
	@Autowired
	NoticeHttpClient noticeHttpClient;
	@Autowired
	private AreaElectronicFenceService areaElectronicFenceService;
	@Autowired
	private MemberAddressInfoMapper memberAddressInfoMapper;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private SendNoticeService sendNoticeService;
	@Autowired
	private CourierService courierService;
	@Autowired
	private ExpCustomerInfoService expCustomerInfoService;
	@Autowired
	private ParcelInfoService parcelInfoService;
	@Autowired
	private MemberTagMapper memberTagMapper;
	@Autowired
	private RobInfoService robInfoService;
	@Autowired
	private MemberInvitationService memberInvitationService;
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;//站点
	@Autowired
	private BasNetInfoMapper basNetInfoMapper;//网络
	@Autowired
	private QueryUserInfoMapper userInfoMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public BaseDao getBaseDao() {
		return null;
	}

	/**
	 * 
	 * @Method: parseResult
	 * @Description: TODO
	 * @param info
	 * @return
	 * @see net.okdi.core.sms.AbstractHttpClient#parseResult(java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public String parseResult(String info) {
		return info;
	}

	/**
	 * 
	 * @Method: doAddMemberToComp
	 * @Description: 网点资料注册的时候添加到本站点一条初始化的收派员记录
	 * @param memberId
	 *            人员id
	 * @param compId
	 *            网点id
	 * @return {"data":"","success":true}
	 * @throws ServiceException
	 * @see net.okdi.api.service.MemberInfoService#doAddMemberToComp(java.lang.Long,
	 *      java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public MemberInfo doAddMemberToComp(Long memberId, Long compId) {
		JSONObject jo = queryMemberInfoFromUCenter(memberId);
		Map<String, Object> map = new HashMap<String, Object>();
		MemberInfo memberInfo = null;
		// 先查询memberId是否已经与comId绑定关系，没有则添加 有的话需要更新关系表数据
		List<BasEmployeeRelation> list = queryCountById(memberId, null, null);
		map.put("memberId", memberId);
		map.put("id", IdWorker.getIdWorker().nextId());
		map.put("compId", compId);
		map.put("roleId", 1);
		map.put("areaColor", "#c2c2c2");
		map.put("memberName", "管理员");// jo.get("realname").toString());
		map.put("idNum", jo.get("idNum").toString());
		map.put("createTime", new Date());
		map.put("memberPhone", jo.get("mobile"));
		map.put("memberSource", 1);
//		map.put("verifyId", IdWorker.getIdWorker().nextId());
//		map.put("relationId", IdWorker.getIdWorker().nextId());
		// 先判断memberInfo是否存在
		int n = this.memberInfoMapper.queryCount(memberId);
		if (n == 0) {
			this.memberInfoMapper.insertMemberInfo(map);// 添加memberInfo表
		} else {
			MemberInfo mi = memberInfoMapper.getMemberInfoById(memberId);
			if(!PubMethod.isEmpty(mi.getMemberName()))
				map.put("memberName", mi.getMemberName());
			//同步通行证 修改姓名
			updateUcenter(memberId,"管理员");
			this.memberInfoMapper.editMemberInfo(map);// 修改memberInfo表
		}
		//modify by zhaishihe 20150521,cause repeat data
		//this.basEmployeeAuditMapper.deleteRelationLog(memberId);
		this.basEmployeeAuditMapper.deleteRelationLogByMemberId(memberId);
		
		this.basEmployeeAuditMapper.doAddRelation(map);
		if (!PubMethod.isEmpty(list)
				&& !list.get(0).getCompId().toString().equals(compId.toString())) {
			throw new ServiceException(0, "openapi.MemberInfoServiceImpl.doAddMemberToComp.001",
					"该人员已于其他站点建立关系 ");

		} else if (PubMethod.isEmpty(list)) {// 如果关系表中没有该member的关联关系
			this.basEmployeeRelationMapper.doAddRelation(map);
			int count =this.memberInvitationService.updateregisterFlag(memberId);
			if(count == 1){
				this.memberInvitation(memberId, compId, jo.get("mobile").toString(), map.get("memberName").toString());
			}
			return memberInfo;
		}
		
		return memberInfo;
	}

	/**
	 * 
	 * @Method: queryCountById
	 * @Description: 查询bas_employee_relation关系表中是否存在memberId与comId的记录(-1 后勤0
	 *               收派员1 站长)
	 * @param memberId
	 *            网点id
	 * @param roleId
	 *            角色id
	 * @param compId
	 *            网点id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#queryCountById(java.lang.Long,
	 *      java.lang.Short, java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public List<BasEmployeeRelation> queryCountById(Long memberId, Short roleId, Long compId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		map.put("compId", compId);
		List<BasEmployeeRelation> list = basEmployeeRelationMapper.queryCountById(map);
		return list;
	}

	/**
	 * 
	 * @Method: queryMemberInfoFromUCenter
	 * @Description: 通过memberId去通行证查询人员的资料数据
	 * @param memberId
	 *            人员id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#queryMemberInfoFromUCenter(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public JSONObject queryMemberInfoFromUCenter(Long memberId) {
		Map<String, String> map = new HashMap<String, String>();
		String url = constPool.getUserCenterUrl();
		map.put("memberId", memberId + "");
		String response = Post(url, map);
		response = new DesEncrypt().convertPwd(response, "");
		if (PubMethod.isEmpty(response)) {
			throw new ServiceException(0,
					"openapi.MemberInfoServiceImpl.queryMemberInfoFromUCenter.001",
					"站点调用通行证接口读取注册用户资料失败 ");
		}
		List<JSONObject> jsonObjectList = JSON.parseArray(response, JSONObject.class);
		JSONObject jsonObject = jsonObjectList.get(0);
		return jsonObject;
	}

	/**
	 * 
	 * @Method: doEditComp
	 * @Description: 修改人员所在站点 从旧站点修改到新站点
	 * @param compIdOld
	 *            旧网点id
	 * @param compIdNew
	 *            新网点id
	 * @return {"data":"","success":true}
	 * @throws ServiceException
	 * @see net.okdi.api.service.MemberInfoService#doEditComp(java.lang.Long,
	 *      java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public int doEditComp(Long compIdOld, Long compIdNew) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compIdOld", compIdOld);
		map.put("compIdNew", compIdNew);
		int a = basEmployeeRelationMapper.doEditComp(map);
//		if(a ==0){
//			throw new ServiceException(0,
//					"openapi.MemberInfoServiceImpl.doEditComp.001",
//					"修改该网点归属关系失败 ");
//		}
		int b = basEmployeeAuditMapper.doUpdateComp(map);
//		if(b ==0){
//			throw new ServiceException(0,
//					"openapi.MemberInfoServiceImpl.doUpdateComp.001",
//					"修改该网点审核关系失败 ");
//		}
		return 1;
	}

	/**
	 * 
	 * @Method: queryMemberResume
	 * @Description: 查询人员履历信息
	 * @param memberId
	 *            人员id
	 * @return { "compAddress": "北京-海淀区-东小口镇|最最一", "compId": 13867330511306752,
	 *         "compName": "最最一", "compTelephone": "010-11111111", "createTime":
	 *         "2014-11-03", "employeeUserName": "通通通", "employee_user_id":
	 *         13954038301985792, "endTime": "2014-11-08", "netId": 2071,
	 *         "netName": "微特派" }
	 * @see net.okdi.api.service.MemberInfoService#queryMemberResume(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public List<Map<String, Object>> queryMemberResume(Long memberId) {
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		list1 = ehcacheService.get("resumCache", String.valueOf(memberId), ArrayList.class);
		if(PubMethod.isEmpty(list1)){
			List<Map<String, Object>> list = basCompEmployeeResumeMapper.queryMemberResume(memberId);
			list1=new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> mapFor = this.memberInfoMapper.queryRegTime(memberId);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map1 = new HashMap<String, Object>();
				Map<String, Object> map = list.get(i);
				map1.put("compId", map.get("compId"));
				map1.put("compName", map.get("compName"));
				BasCompInfo basCompInfo = this.robInfoService.queryBasCompInfo(Long.parseLong(String.valueOf(map.get("compId"))));
				map1.put("compTelephone", basCompInfo.getCompTelephone());
				map1.put("compAddress", basCompInfo.getCompAddress());
				map1.put("employeeUserName", map.get("employeeUserName"));
				map1.put("employee_user_id", map.get("employee_user_id"));
				map1.put("netId", map.get("netId"));
				map1.put("netName", map.get("netName"));
				map1.put("createTime", map.get("createTime"));
				if (i == list.size() - 1) {
					map1.put("endTime", "今");
				} else {
					map = list.get(++i);
					map1.put("endTime", map.get("createTime"));
				}
				list1.add(map1);
			}
			list1.addAll(mapFor);
			ehcacheService.put("resumCache", String.valueOf(memberId), list1);
			ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		}
		return list1;
	}

	/**
	 * 
	 * @Method: validateTel
	 * @Description: 手机号注册验证(通过手机号去通行证查询 )
	 * @param associatedNumber
	 *            手机号
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#validateTel(java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public JSONObject validateTel(String associatedNumber) {
		Map<String, String> map = new HashMap<String, String>();
		String url = constPool.getValidateCenterUrl();
		map.put("mobile", associatedNumber);
		String response = Post(url, map);
		if (PubMethod.isEmpty(response)) {
			throw new ServiceException("openapi.MemberInfoServiceImpl.validateTel.001",
					"手机号注册验证返回json异常 ");
		}
		JSONObject result = JSONObject.parseObject(response);
		return result;
	}

	/**
	 * 
	 * @Method: regTel
	 * @Description: 如果手机号在通行证没有注册那么用手机号调用接口注册生成memberId返回
	 * @param associatedNumber
	 *            手机号
	 * @param password密码
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#regTel(java.lang.String,
	 *      java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public String regTel(String associatedNumber, String password,String memberName) {
		Map<String, String> map = new HashMap<String, String>();
		String url = constPool.getExpCenterUrl();
		map.put("mobile", associatedNumber);
		map.put("password", password);
		map.put("realname", memberName);
		map.put("source", "1");
		String response = Post(url, map);
		if (PubMethod.isEmpty(response)) {
			throw new ServiceException("openapi.MemberInfoServiceImpl.regTel.001",
					"手机号获取memberId异常 ");
		}
		JSONObject result = JSONObject.parseObject(response);
		return result.get("memberId").toString();
	}

	/**
	 * 
	 * @Method: addMemberInfo
	 * @Description: 提供给站点的添加收派员接口
	 * @param compId
	 *            网点id
	 * @param associatedNumber
	 *            手机号
	 * @param memberName
	 *            人员姓名
	 * @param roleId
	 *            角色id
	 * @param areaColor
	 *            片区颜色
	 * @param userId
	 *            用户id
	 * @throws ServiceException
	 * @see net.okdi.api.service.MemberInfoService#addMemberInfo(java.lang.Long,
	 *      java.lang.String, java.lang.String, java.lang.Short,
	 *      java.lang.String, java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public void addMemberInfo(Long compId, String associatedNumber, String memberName,
			Short roleId, String areaColor, Long userId, Short memberSource) {
		Map<String, Object> map = new HashMap<String, Object>();
		String operateFlag = "";
		Long memberId = null;
		String idNum = "";
		// 1判断手机号是否在通行证注册
		JSONObject jsonOb1 = validateTel(associatedNumber);
		StringBuffer idenCode = new StringBuffer(); // 生成密码
		if (jsonOb1.get("registered").toString().equals("true")) {// 如果注册获取memberId所有资料
			operateFlag = "1";
			memberId = Long.parseLong(jsonOb1.get("memberId").toString());
			JSONObject jsonOb2 = this.queryMemberInfoFromUCenter(memberId);
			idNum = jsonOb2.get("idNum").toString();
			updateUcenter(memberId,memberName);
		} else {// 如果没有在通行证注册需要先用手机号调用通行证接口来生成memberId
			operateFlag = "0";
			Random ran = new Random();
			for (int i = 0; i < 6; i++) {
				idenCode.append(flag[ran.nextInt(36)]);
			}
			//注册进登录手机号，且注册进去用户okdi+_user中
			memberId = Long.parseLong(regTel(associatedNumber, idenCode.toString(),memberName));
			idNum = null;
		}
		String content = this.addContent(queryCompNameById(compId).get(0).get("compName")
				.toString(), roleId, operateFlag, associatedNumber, idenCode.toString());
		noticeHttpClient.doSmsSend("02", 0L, associatedNumber, content, String.valueOf(compId));
		// 需要判断是第一次加入还是删除离职之后又重新加入的因为离职之后memberinfo表数据还在
		int k = this.memberInfoMapper.queryIsOne(associatedNumber);
		// 插入memberInfo表 bas_employeeRelation表 履历表
		map = setMap(memberId, memberName, idNum, compId, roleId, areaColor, associatedNumber,
				memberSource, userId);
		if (k == 0) {//
			this.memberInfoMapper.insertMemberInfo(map);// 插入member_info表
		} else {
			//同步通行证 修改姓名
			updateUcenter(memberId,memberName);
			this.memberInfoMapper.editMemberInfo(map);// 插入member_info表
		}
		this.basEmployeeRelationMapper.insertCompInfo(map);// 插入basEmployeeRelation表
		this.basCompEmployeeResumeMapper.doAddResum(map);// 插入履历表
		int p = this.basEmployeeAuditMapper.getAuditCount(memberId);
		if(p ==0){
			this.basEmployeeAuditMapper.doAddAudit(map);//插入日志表中归属关系记录
			//---------2015年11月28日10:59:23 begin-----------
			this.basEmployeeAuditMapper.updateSFAuditInfo(map);//修改 身份认证信息的站点id--compId
			this.basEmployeeAuditMapper.updateKDAuditInfo(map);//修改 快递认证信息的站点id--compId
			//---------2015年11月28日10:59:23 end-----------
		}else{
			this.basEmployeeAuditMapper.editAuditInfo(memberId,roleId,compId);
		}
		int num = this.basEmployeeAuditMapper.getVerifyAudit(memberId);
		if(num ==0){//如果没有该人员的身份审核记录则增加
			this.basEmployeeAuditMapper.doSaveAudit(map);
		}
		
		int count =this.memberInvitationService.updateregisterFlag(memberId);
		if(count == 1){
			this.memberInvitation(memberId, compId, associatedNumber, memberName);
		}else{
			String result = noticeHttpClient.updateIsPromoFlag((short)3, memberId, (short)4, (short)0);
			if(PubMethod.isEmpty(result) || !"true".equals(JSON.parseObject(result).get("success").toString())){
				throw new ServiceException("openapi.MemberInfoServiceImpl.addMemberInfo.001", "邀请奖励操作异常");
			}
		}
		
		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		ehcacheService.put("employeeCache", compId.toString(), list);
		ehcacheService.remove("resumCache", String.valueOf(memberId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
		ehcacheService.remove("memberInfochCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		ehcacheService.remove("employeeRelationCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoMobilechCache", String.valueOf(compId));
	}

	private Map<String, Object> setMap(Long memberId, String memberName, String idNum, Long compId,
			Short roleId, String areaColor, String associatedNumber, Short memberSource, Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", compId);
		map.put("memberId", memberId);
		map.put("memberName", memberName);
		map.put("resumId", IdWorker.getIdWorker().nextId());
		map.put("relationId", IdWorker.getIdWorker().nextId());
		map.put("tagId", IdWorker.getIdWorker().nextId());
		map.put("commId", IdWorker.getIdWorker().nextId());
		map.put("auditId", IdWorker.getIdWorker().nextId());
		map.put("auditVerifId", IdWorker.getIdWorker().nextId());
		map.put("createTime", new Date());
		map.put("memberPhone", associatedNumber);
		map.put("idNum", idNum);
		map.put("roleId", roleId);
		map.put("userId", userId);
		map.put("areaColor", areaColor);
		map.put("memberSource", memberSource);
		map.put("compName", PubMethod.isEmpty(queryCompNameById(compId)) ? null
				: queryCompNameById(compId).get(0).get("compName"));
		map.put("netId",
				PubMethod.isEmpty(queryCompNameById(compId)) ? null : queryCompNameById(compId)
						.get(0).get("netId"));
		map.put("areaColor", areaColor);
		map.put("employeeOnTheJobFlag", 1);
		return map;
	}

	/**
	 * 
	 * @Method: addContent
	 * @Description:连接短信内容
	 * @param compName
	 *            网点名称
	 * @param roleId
	 *            角色id
	 * @param operateFlag
	 *            操作类型
	 * @param telNum
	 *            手机号
	 * @param password
	 *            密码
	 * @return
	 * @since jdk1.6
	 */
	private String addContent(String compName, Short roleId, String operateFlag, String telNum,
			String password) {
		String content = "";
		String roleName = "";
		if (roleId == 0) {// 收派员
			roleName = "收派员";
		} else if (roleId == -1) {// 后勤
			roleName = "后勤";
		} else if (roleId == -2) {// 站长
			roleName = "站长";
		}else if (roleId == 2) {// 2 代收站店长 3 代收站店员'
			roleName = "店长";
		} else if (roleId == 3) {
			roleName = "店员";
		}
		if(roleId==0||roleId==-1||roleId==-2){
			if ("1".equals(operateFlag)) {// 在通行证注册
				content = "您好：[" + compName + "]已经将您添加为本网点：[" + roleName + "]。"
						+ "如果您是站长、后勤则您注册的账户与密码可登陆好递网点与好递快递员；"
						+ "如果您是收派员则只能登陆好递快递员。[请登录www.okdi.net下载好递快递员]";
			} else if ("0".equals(operateFlag)) {
				content = "【好递】您好:站点" + compName + "已经将您添加为[" + roleName + "]。账号:" + telNum + ",初始密码为："+ password + "。[请登录www.okdi.net下载好递快递员]";
			}
		}else{ //店长和店员
			if ("1".equals(operateFlag)) {// 在通行证注册 
				content = "【好递】恭喜！店长已经通过您的入店申请，可登录好递快递员查看";
			} else if ("0".equals(operateFlag)) {
				content = "【好递】您好:便利超市已经将您添加为["+roleName+"]。账号:" + telNum + ",初始密码为："
						+ password + "。[请登录www.okdi.net下载好递快递员]。";
			}
		}
		
		return content;
	}

	/**
	 * 
	 * @Method: queryCompNameById
	 * @Description: 通过compId获取网点资料
	 * @param compId
	 *            网点id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#queryCompNameById(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public List<Map<String, Object>> queryCompNameById(Long compId) {
		List<Map<String, Object>> list = this.memberInfoMapper.queryCompNameById(compId);
		return list;
	}

	/**
	 * 
	 * @Method: getMemberInfoById
	 * @Description: 点击姓名 反显右侧信息
	 * @param memberId
	 *            人员id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#getMemberInfoById(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public MemberInfo getMemberInfoById(Long memberId) {
		MemberInfo memberInfo = null;
		memberInfo = this.ehcacheService.get("memberInfochCache", String.valueOf(memberId), MemberInfo.class);
		if(PubMethod.isEmpty(memberInfo)){
			memberInfo = this.memberInfoMapper.getMemberInfoById(memberId);
			this.ehcacheService.put("memberInfochCache",  String.valueOf(memberId), memberInfo);
			ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		}
		//2015 09 24 yangkai 加头像
	    memberInfo.setMemberDetaileDisplay(String.valueOf(constPool.getHeadImgPath()+memberInfo.getMemberId()+".jpg")); //人员头像
		return memberInfo;
	}

	/**
	 * 
	 * @Method: doEditMemberInfo
	 * @Description: 修改右侧人员信息
	 * @param memberId
	 *            人员id
	 * @param roleId
	 *            角色id
	 * @param employeeWorkStatusFlag
	 *            工作标志
	 * @param areaColor
	 *            片区颜色
	 * @see net.okdi.api.service.MemberInfoService#doEditMemberInfo(java.lang.Long,
	 *      java.lang.Short, java.lang.Short, java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public void doEditMemberInfo(Long compId, Long memberId, Short roleId,
			Short employeeWorkStatusFlag, String areaColor) {
		if(employeeWorkStatusFlag !=1&&employeeWorkStatusFlag !=2){
			throw new ServiceException("openapi.MemberInfoServiceImpl.doEditMemberInfo.002","工作状态传入参数错误");
		}
		if(roleId !=0&&roleId !=-1&&roleId !=-2){
			throw new ServiceException("openapi.MemberInfoServiceImpl.doEditMemberInfo.003","角色标识传入参数错误");
		}
		if(!VerifyUtil.isColor(areaColor)){
			throw new ServiceException("openapi.MemberInfoServiceImpl.doEditMemberInfo.004","颜色色值传入参数错误");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		map.put("employeeWorkStatusFlag", employeeWorkStatusFlag);
		map.put("areaColor", areaColor);
		int a = this.basEmployeeRelationMapper.doEditMemberInfo(map);
		if(a ==0 ){
			throw new ServiceException("openapi.MemberInfoServiceImpl.doEditMemberInfo.001",
					"修改人员归属信息异常  ");
		}
		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		ehcacheService.removeAll("userInfoCache");
		ehcacheService.put("employeeCache", compId.toString(), list);
		ehcacheService.remove("resumCache", String.valueOf(memberId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
		ehcacheService.remove("memberInfochCache", String.valueOf(memberId));
		ehcacheService.remove("employeeRelationCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
	}

	/**
	 * 
	 * @Method: deleteMemberInfo
	 * @Description: 站点添加的收派员的删除操作(先删除关系表 同事日志表中写一条日志记录 )
	 * @param memberId
	 *            人员id
	 * @param compId
	 *            网点id
	 * @param memberName
	 *            人员姓名
	 * @param memberPhone
	 *            人员手机号
	 * @throws ServiceException
	 * @see net.okdi.api.service.MemberInfoService#deleteMemberInfo(java.lang.Long,
	 *      java.lang.Long, java.lang.String, java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public void deleteMemberInfo(Long userId,Long memberId, Long compId, String memberName, String memberPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
//		List<Map<String,Object>> list1 =new ArrayList<Map<String,Object>>();
		//modify by zhaishihe 20150521,cause repeat data
		//int c = this.basEmployeeAuditMapper.deleteRelationLog(memberId);
		
		int roleId = basEmployeeAuditMapper.queryRolebyMemberId(memberId);
		
		this.basEmployeeAuditMapper.deleteRelationLogByMemberId(memberId);
		
		int a = this.basEmployeeRelationMapper.deleteMemberInfo(memberId);//删除归属关系表中的记录
//		if(a ==0 ){
//			throw new ServiceException("openapi.MemberInfoServiceImpl.deleteMemberInfo.001",
//					"删除人员关系异常");
//		}
		map = createMap(memberId, compId, memberName);
		int b = this.basCompEmployeeResumeMapper.doAddResum(map);
//		if(b ==0){
//			throw new ServiceException("openapi.MemberInfoServiceImpl.deleteMemberInfo.002",
//					"插入履历关系异常");
//		}
		this.areaElectronicFenceService.removeAreaMember(compId, memberId);
		this.parcelInfoService.deleteParcelConnectionByMemberId(memberId);
/*		String content = "亲爱的接单王用户，很遗憾您已从所在站点离职，请选择新任职站点！";
		noticeHttpClient.doSmsSend("02", 0L, memberPhone, content, String.valueOf(compId));*/
		if(!userId.equals(memberId)){
			
			
			this.sendNoticeService.quitToExpMember(memberId, memberPhone,roleId);
		}
//		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		expCustomerInfoService.deleteByMemberId(compId, memberId);
		ehcacheService.removeAll("employeeCache");//删除人员列表的缓存
		ehcacheService.remove("employeeCache", compId.toString());
		ehcacheService.remove("resumCache", String.valueOf(memberId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
		ehcacheService.removeAll("memberInfoMobilechCache");//删除人员列表的个数
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
	 * @Method: checkTel
	 * @Description: 站点添加手机号的时候检查是否存在
	 * @param associatedNumber
	 *            手机号
	 * @param
	 * @return int
	 * @throws
	 */
	@Override
	public int checkTel(String associatedNumber, Long compId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("associatedNumber", associatedNumber);
		map.put("compId", compId);
		int flag = 2;
		int a = this.memberCommInfoMapper.checkTel(map);
		if (a == 0) {// 数据库中不存在这个手机号
			flag = 2;
		} else if (a > 0) {// 数据中存在这个手机号的记录需要判定是否绑定公司以及是本公司还是外部公司
			MemberCommInfo memberCommInfo = this.memberCommInfoMapper.checkTelInfo(map);
			if (memberCommInfo != null && memberCommInfo.getCompid() !=null) {
				if (memberCommInfo.getCompid().equals(compId)) {
					flag = 1;// 本站点存在
				} else {
					flag = 5;// 非本站点存在
				}
			}else{//需要判断是待审核人员还是离职人员 待审核人员 日志表中有一条待审核记录 离职人员 日志表中没有归属记录 
				BasEmployeeAudit basEmployeeAudit = this.basEmployeeAuditMapper.queryRecorde(associatedNumber);
				if(!PubMethod.isEmpty(basEmployeeAudit)){
					if(basEmployeeAudit.getAuditComp().equals(compId)){
						flag = 1;// 本站点存在
					}else{
						flag = 5;// 非本站点存在
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 
	 * @Method: doAuditMember
	 * @Description: 审核人员
	 * @param flag
	 *            1:通过 2:拒绝
	 * @param compId
	 *            网点id
	 * @param memberId
	 *            人员id
	 * @return
	 * @see net.okdi.express.memberManage.service.MemberInfoService#doAuditMember(java.lang.Short,
	 *      java.lang.Long, java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public BasEmployeeRelation doAuditMember(Long logId,String memberPhone, Long userId, Short flag, Long compId,
			String memberName, Long memberId, String refuseDesc, String areaColor, Short roleId) {
		BasEmployeeRelation basEmployeeRelation = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map = this.setMap(logId, refuseDesc, userId, flag, compId, memberId, areaColor, roleId,
				memberName);
		int k = this.basEmployeeAuditMapper.updateStatus(map);
		logger.info("===================方法开始"+k);
		if (k == 0) {
			throw new ServiceException("openapi.MemberInfoServiceImpl.001",
					"更新状态异常 ");
		}
		// this.basEmployeeAuditMapper.saveAudit(map);// 日志表中插入一条记录
		if (flag == 1) {// 关系表中写入一条记录，履历表中也要写入一条
			logger.info("===================flag:"+flag);
			int a = this.basEmployeeRelationMapper.doAddRelation(map);// 插入关系表
			if(a ==0){
				throw new ServiceException("openapi.MemberInfoServiceImpl.doAuditMember.002",
						"插入归属关系异常");
			}
			int b = this.basCompEmployeeResumeMapper.doAddResum(map);// 插入履历表
			if(b ==0){
				throw new ServiceException("openapi.MemberInfoServiceImpl.doAuditMember.003",
						"插入履历异常");
			}
			this.memberInfoMapper.updateSource(memberId);
		
			this.sendNoticeService.passRelationToExp(memberId, memberPhone);
		}else{//拒绝操作 需要给人员的手机号发短信
			logger.info("===================flag:"+flag);
			logger.info("===================参数"+memberId+memberPhone);
			this.memberInfoMapper.deleteAuditByMemberId(memberId);
			this.sendNoticeService.refuseRelationToExp(memberId, memberPhone);
		}
		logger.info("===================方法结束");
		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		ehcacheService.put("employeeCache", compId.toString(), list);
		ehcacheService.remove("memberInfochCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(compId));
		ehcacheService.remove("memberInfoMobilechCache", String.valueOf(compId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
		ehcacheService.remove("siteMemberListCache", String.valueOf(compId));//删除查询本站点快递员列表缓存----(马建鑫)
		redisService.removeAll("verifyInfo-memberId-cache");
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		redisService.removeAll("findShopowner1xxx");
		redisService.removeAll("findShopowner2xxx");
		redisService.removeAll("findShopowner3xxx");
		redisService.removeAll("queryResponsibles"); //删除归属信息缓存
		return basEmployeeRelation;
	}

	private Map<String, Object> setMap(Long logId, String refuseDesc, Long userId, Short flag,
			Long compId, Long memberId, String areaColor, Short roleId, String memberName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", IdWorker.getIdWorker().nextId());
		map.put("compId", compId);
		map.put("compName", queryCompNameById(compId).get(0).get("compName"));
		map.put("netId", queryCompNameById(compId).get(0).get("netId"));
		map.put("memberId", memberId);
		map.put("flag", flag);
		map.put("logId", logId);
		map.put("memberName", memberName);
		map.put("areaColor", areaColor);
		map.put("refuseDesc", refuseDesc);
		map.put("auditId", IdWorker.getIdWorker().nextId());
		map.put("userId", userId);
		map.put("auditTime", new Date());
		map.put("createTime", new Date());
		map.put("roleId", roleId);
		map.put("resumId", IdWorker.getIdWorker().nextId());
		if (PubMethod.isEmpty(areaColor)) {
			map.put("areaColor", "#c2c2c2");
		} else {
			map.put("areaColor", areaColor);
		}
		return map;
	}

	/**
	 * 
	 * @Method: queryMemberIdByCompId
	 * @Description: 通过compId获取关系表中的memberId
	 * @param compId
	 *            网点id
	 * @return
	 * @see net.okdi.express.memberManage.service.MemberInfoService#queryMemberIdByCompId(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public Long queryMemberIdByCompId(Long compId) {
		BasEmployeeRelation basEmployeeRelation = this.basEmployeeRelationMapper
				.queryMemberIdByCompId(compId);
		return basEmployeeRelation.getMemberId();
	}

	/**
	 * 
	 * @Method: getMemberInfoByCompId
	 * @Description: 查询站点下的营业分部和收派员
	 * @param compId
	 *            网点id
	 * @return
	 * @see net.okdi.express.memberManage.service.MemberInfoService#getMemberInfoByCompId(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public List<Map<String, Object>> getMemberInfoByCompId(Long parentId) {
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		list1 =ehcacheService.get("memberAndBranchCache", String.valueOf(parentId), ArrayList.class);
		if(PubMethod.isEmpty(list1)){
			list1 = this.basEmployeeRelationMapper
					.getMemInfoByCompId(parentId);// 查人员
			if(!PubMethod.isEmpty(list1)){
				List<Map<String, Object>> list2 = this.expCompRelationMapper.getMemInfo(parentId);// 查营业分部
				if (!PubMethod.isEmpty(list2)) {
					list1.addAll(list2);
				}
			}
			ehcacheService.put("memberAndBranchCache", String.valueOf(parentId), list1);
		}
		return list1;
	}

	/**
	 * 
	 * @Method: CompFlag
	 * @Description: 通过compId判断网点是站点还是营业分部
	 * @param compId
	 *            网点id
	 * @return
	 * @see net.okdi.express.memberManage.service.MemberInfoService#CompFlag(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public int CompFlag(Long compId) {
		// Base
		return 0;
	}

	/**
	 * 
	 * @Method: queryUnAudit
	 * @Description: 人员列表中手机端来源的人员
	 * @param compId
	 *            网点id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#queryUnAudit(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public List<Map<String, Object>> queryUnAuditMemberInfo(Long compId) {
		List<Map<String, Object>> list = this.memberInfoMapper.queryUnAuditMemberInfo(compId);
		return list;
	}


     /**
	 * 
	 * @Method: queryMemberForComp
	 * @Description: 查询站点或营业分部下的人员列表
	 * @param compId
	 *            网点id
	 * @param roleId
	 *            角色id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#queryMemberForComp(java.lang.Long,
	 *      java.lang.Short)
	 * @since jdk1.6
	 */
	@Override
	public List<Map<String, Object>> queryMemberForComp(Long compId, Long roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", compId);
		map.put("roleId", roleId);
		List<Map<String, Object>> list = this.basEmployeeRelationMapper.queryMemberForComp(map);
		return list;
	}

	/**
	 * 
	 * @Method: editMemberName
	 * @Description: 网点资料修改名字 同时更新缓存
	 * @param compId
	 *            网点id
	 * @param memberId
	 *            人员id
	 * @param memberName
	 *            人员姓名
	 * @throws ServiceException
	 * @see net.okdi.api.service.MemberInfoService#editMemberName(java.lang.Long,
	 *      java.lang.Long, java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public void editMemberName(Long compId, Long memberId, String memberName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		if (PubMethod.isEmpty(memberName)) {
			memberName = "管理员";
		}
		map.put("memberName", memberName);
		this.memberInfoMapper.editMemberName(map);
		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		ehcacheService.put("employeeCache", compId.toString(), list);
		ehcacheService.remove("resumCache", String.valueOf(memberId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
		ehcacheService.remove("memberInfochCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		ehcacheService.removeAll("customerInfoCache");
	}

	/**
	 * 
	 * @Method: getMemLocal
	 * @Description: 查找该站点下的收派员的经纬度
	 * @param compId
	 *            网点id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#getMemLocal(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public List<Map<String, Object>> getMemLocal(Long compId) {
		List<Map<String, Object>> list = this.memberAddressInfoMapper.getMemLocal(compId);
		return list;
	}

	/**
	 * 
	 * @Method: verifyIdentity
	 * @Description: 手机端的身份验证接口
	 * @param compId
	 *            网点id
	 * @param memberId
	 *            人员id
	 * @param refuseDesc
	 *            拒绝原因
	 * @param flag
	 *            1通过 2拒绝
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#verifyIdentity(java.lang.Long,
	 *      java.lang.Long, java.lang.String, java.lang.Short)
	 * @since jdk1.6
	 */
	@Override
	public void verifyIdentity(Long compId,Long memberId, Short flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("flag", flag);
		map.put("compId", compId);
		map.put("auditTime", new Date());
		int a = this.memberInfoMapper.verifyIdentity(map);
		//需求变更 客服系统不写auditLog日志表了 数据再openapi中修改所以需要加个方法
		int b = this.basEmployeeAuditMapper.editAudit(map);
		if(b ==0){
			throw new ServiceException("openapi.MemberInfoServiceImpl.verifyIdentity.001",
					"人员身份验证审核日志表操作异常");
		}
		if(a ==0 ){
			throw new ServiceException("openapi.MemberInfoServiceImpl.verifyIdentity.002",
					"人员信息身份验证操作异常");
		}
		String memberPhone = getMemberInfoById(memberId).getMemberPhone();
		if(flag ==2){//如果是身份验证拒绝 需要发短信
			//noticeHttpClient.doSmsSend("02", 0L, memberPhone, refuseVerifyContent, String.valueOf(compId));
			this.sendNoticeService.refuseToExp(memberId, memberPhone);
		}
	}


	/**
	 * 
	 * @Method: verifyRelation
	 * @Description: 客服归属验证接口
	 * @param memberId
	 *            人员id
	 * @param compId
	 *            网点id
	 * @param roleId
	 *            角色id 0 收派员 -1 后勤 -2 站长
	 * @param flag
	 *            1通过2拒绝
	 * @see net.okdi.api.service.MemberInfoService#verifyRelation(java.lang.Long,
	 *      java.lang.Long, java.lang.Short, java.lang.Short)
	 * @since jdk1.6
	 */
	@Override
	public void verifyRelation(Long memberId, Long compId, Short roleId, Short flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		map.put("compId", compId);
		map.put("flag", flag);
		map.put("createTime", new Date());
		map.put("relationId", IdWorker.getIdWorker().nextId());
		if (flag == 1) {
			this.basEmployeeRelationMapper.insertCompInfo(map);
			int a = this.basEmployeeAuditMapper.editRelation(map);
			if(a ==0 ){
				throw new ServiceException("openapi.MemberInfoServiceImpl.verifyRelation.001",
						"人员归属验证审核日志表操作异常");
			}
			map1 = this.setMap(null, null, null, flag, compId, memberId, null, roleId,
					null);
			int b = this.basCompEmployeeResumeMapper.doAddResum(map1);// 插入履历表
			if(b ==0){
				throw new ServiceException("openapi.MemberInfoServiceImpl.doAuditMember.003",
						"插入履历异常");
			}
			String memberPhone = getMemberInfoById(memberId).getMemberPhone();
			this.sendNoticeService.passRelationToExp(memberId, memberPhone);
		}else if(flag ==2){
			int b = this.basEmployeeAuditMapper.editRelation(map);
			if(b ==0){
				throw new ServiceException("openapi.MemberInfoServiceImpl.verifyRelation.001",
						"人员归属验证审核日志表操作异常");
			}
			String memberPhone = getMemberInfoById(memberId).getMemberPhone();
			this.sendNoticeService.refuseRelationToExp(memberId, memberPhone);
		}
		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		ehcacheService.put("employeeCache", compId.toString(), list);
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoMobilechCache", String.valueOf(compId));
		ehcacheService.remove("resumCache", String.valueOf(memberId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
	}

	/**
	 * 
	 * @Method: memberInfoToComp
	 * @Description: 手机端注册接口
	 * @param memberId
	 *            人员id
	 * @param compId
	 *            网点id
	 * @param memberName
	 *            人员姓名
	 * @param roleId
	 *            角色id
	 * @param memberPhone
	 *            人员电话
	 * @throws ServiceException
	 * @see net.okdi.api.service.MemberInfoService#memberInfoToComp(java.lang.Long,
	 *      java.lang.Long, java.lang.String, java.lang.Short, java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public void memberInfoToComp(Long memberId, Long compId, String memberName, Short roleId,
			String memberPhone, String applicationDesc) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		map.put("compId", compId);
		map.put("memberName", memberName);
		map.put("memberPhone", memberPhone);
		map.put("auditId", IdWorker.getIdWorker().nextId());
		map.put("auditId1", IdWorker.getIdWorker().nextId());
		map.put("createTime", new Date());
		map.put("memberSource", 0);
		map.put("applicationDesc", applicationDesc);
		int m = this.memberInfoMapper.queryCount(memberId);
		if (m == 0) {
			this.memberInfoMapper.insertMemberInfo(map);
		} else {
			//同步通行证 修改姓名
			updateUcenter(memberId,memberName);
			this.memberInfoMapper.editMemberInfo(map);
		}
		// 判断该memberid是否与其他的站点现在处于归属验证待审核状态
		int n = this.basEmployeeAuditMapper.getAuditCount(memberId);
		if (n == 0) {
			this.basEmployeeAuditMapper.doAddAudit(map);
		} else if (n == 1) {// 修改
			this.basEmployeeAuditMapper.doEditAudit(map);
		} else {
			throw new ServiceException("openapi.MemberInfoServiceImpl.memberInfoToComp.001",
					"该memberId在审核记录表中有多条归属验证待审核记录  ");
		}
		// 查询日志表中是否有身份验证的待审核状态
		int k = this.basEmployeeAuditMapper.getVerifyAudit(memberId);
		if (k == 0) {// 新增
			this.basEmployeeAuditMapper.doInsertAudit(map);
		} else if (k == 1) {// 修改
			this.basEmployeeAuditMapper.doUpdatetAudit(map);
		} else {
			throw new ServiceException("openapi.MemberInfoServiceImpl.memberInfoToComp.002",
					"该memberI在审核记录表中有多条身份验证待审核记录  ");
		}

		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		ehcacheService.put("employeeCache", compId.toString(), list);
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoMobilechCache", String.valueOf(compId));
	}

	/**
	 * 
	 * @Method: SynDataFromUcenter
	 * @Description: 通行证同步数据到api
	 * @param memberId
	 *            人员id
	 * @param memberName
	 *            人员姓名
	 * @param gender
	 *            0男1女
	 * @param birthday
	 *            生日
	 * @param idNum
	 *            身份证
	 * @param memberPhone
	 *            人员手机号
	 * @param memberAddressId
	 *            地址id
	 * @param memberDetaileDisplay
	 *            地址文字描述
	 * @param memberDetailedAddress
	 *            详细地址
	 * @throws ServiceException
	 * @see net.okdi.api.service.MemberInfoService#SynDataFromUcenter(java.lang.Long,
	 *      java.lang.String, java.lang.String, java.util.Date,
	 *      java.lang.String, java.lang.String, java.lang.Long,
	 *      java.lang.String, java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public void SynDataFromUcenter(Long memberId, String memberName, String idNum,
			String memberPhone, Long memberAddressId, String memberDetaileDisplay,
			String memberDetailedAddress,Short resource) throws ServiceException {
		VO_MemberInfo vo = new VO_MemberInfo(null,null, memberId, memberName, null, null, null, null,
				null, resource, null, memberPhone, idNum, memberAddressId, memberDetaileDisplay,
				memberDetailedAddress, new Date(),null,null);
		if (this.memberInfoMapper.queryCount(memberId) == 0) {// 没有则增加
			this.memberInfoMapper.saveMemberInfo(vo);
		} else {
			int a = this.memberInfoMapper.updateMemberInfo(vo);
			if(a ==0){
				throw new ServiceException("openapi.MemberInfoServiceImpl.SynDataFromUcenter.001",
						"通行证同步数据更新操作异常");
			}
		}
		
		ehcacheService.remove("memberDefulatAddress", String.valueOf(memberId));
		ehcacheService.remove("memberInfochCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		ehcacheService.removeAll("employeeCache");
		this.ehcacheService.removeAll("memberInfoMobilechCache");
	}

	/**
	 * 
	 * @Method: queryEmployeeCache
	 * @Description: 查询人员
	 * @param compId
	 *            网点id
	 * @return { "areaColor": "#c2c2c2", "compId": 13867330511306752,
	 *         "employeeWorkStatusFlag": 1, "id_num": "", "memberId":
	 *         13867278975369216, "memberName": "管理员", "memberPhone":
	 *         "13177770001", "memberSource": 1, "roleId": 1 }
	 * @see net.okdi.api.service.MemberInfoService#queryEmployeeCache(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public List<VO_MemberInfo> queryEmployeeCache(Long compId) {
		List<VO_MemberInfo> list = this.memberInfoMapper.queryEmployeeCache(compId);
		return list;
	}

	/**
	 * 
	 * @Method: removeMemberInfo
	 * @Description: 拒绝之后的人员删除操作
	 * @param logId
	 *            日志id
	 * @see net.okdi.api.service.MemberInfoService#removeMemberInfo(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public void removeMemberInfo(Long compId,Long logId) {
		int a = this.basEmployeeAuditMapper.removeMemberInfo(logId);
		if(a ==0){
			throw new ServiceException("openapi.MemberInfoServiceImpl.removeMemberInfo.001",
					"拒绝之后的人员删除操作异常");
		}
		ehcacheService.remove("memberInfoMobilechCache", String.valueOf(compId));
	}

	/**
	 * 
	 * @Method: getMemberAddress
	 * @Description: 查询指定人员的地址位置
	 * @param memberId
	 *            人员id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#getMemberAddress(java.lang.Long)
	 * @since jdk1.6
	 */
	@Override
	public List<Map<String, Object>> getMemberAddress(Long memberId) {
		List<Map<String, Object>> list = this.memberInfoMapper.getMemberAddress(memberId);
		return list;
	}

	@Override
	public List<Map<String, Object>> getAuditInfo(Long memberId) {
		List<Map<String, Object>> list = this.memberInfoMapper.getAuditInfo(memberId);
		return list;
	}

	/**
	 * 
	 * @Method: getValidationStatus
	 * @param memberId
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#getValidationStatus(java.lang.Long)
	 */
	@Override
	public Map<String, Object> getValidationStatus(Long memberId) {
		System.out.println(memberId);
		Map<String, Object> map = this.memberInfoMapper.getValidationStatus(memberId);
		System.out.println(map);
		// 如果日志表中最新的一条归属验证记录状态
		Map<String, Object> mapV = this.memberInfoMapper.getRelationInfo(memberId);
		try {
			if (!"1".equals(String.valueOf(map.get("relationFlag")))) {//如果不是归属通过的状态需要查询日志表,但是不能为通过状态
				map.put("relationFlag", PubMethod.isEmpty(mapV)?"":String.valueOf(mapV.get("auditOpnion")));//归属认证状态
				map.put("compId", PubMethod.isEmpty(mapV)?"":String.valueOf(mapV.get("compId")));
				map.put("roleId", PubMethod.isEmpty(mapV)?"":String.valueOf(mapV.get("roleId")));
			}else{
				map.put("compId", PubMethod.isEmpty(map)?"":String.valueOf(map.get("compId")));
				map.put("roleId", PubMethod.isEmpty(map)?"":String.valueOf(map.get("roleId")));
			}
			if(PubMethod.isEmpty(map.get("compId")) || PubMethod.isEmpty(map.get("compId").toString())){
				map.put("compStatus", "");
			}else{
				BasCompInfo loginCompInfo = ehcacheService.get("compCache", map.get("compId").toString(), BasCompInfo.class);
				if (PubMethod.isEmpty(loginCompInfo)) {
					loginCompInfo = this.basCompInfoMapper.findById(Long.valueOf(map.get("compId").toString()));
					ehcacheService.put("compCache", map.get("compId").toString(), loginCompInfo);
				}
				map.put("compStatus", loginCompInfo.getCompStatus());//-1创建 0提交待审核 1审核通过 2审核失败
			}
			map.put("realNameFlag", PubMethod.isEmpty(map.get("realNameFlag"))?"":map.get("realNameFlag"));
			map.put("veriFlag", PubMethod.isEmpty(map.get("realNameFlag"))?"":map.get("realNameFlag"));
		} catch (Exception e) {
			logger.error("收派员查询关系报错原因:"+e.getCause());
			logger.error("收派员查询关系报错异常信息："+e);
		}

		return map;
	}

	/**
	 * 查询站长电话
	 * 
	 * @Method: getMasterPhone
	 * @param compId
	 *            网点id
	 * @return
	 * @throws ServiceException
	 * @see net.okdi.api.service.MemberInfoService#getMasterPhone(java.lang.Long)
	 */
	@Override
	public Map<String, Object> getMasterPhone(Long compId){
		Map<String, Object> map = this.memberInfoMapper.getMasterPhone(compId);
		return map;
	}

	/**
	 * 查询该站点下的手机端来源的收派员
	 * 
	 * @Method: loadMemberInfo
	 * @param compId
	 *            网点id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#loadMemberInfo(java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> loadMemberInfo(Long compId) {
		List<Map<String, Object>> listA = this.ehcacheService.get("memberInfoMobilechCache", String.valueOf(compId), ArrayList.class);
		if(PubMethod.isEmpty(listA)){
			listA = this.memberInfoMapper.loadMemberInfo(compId);
			this.ehcacheService.put("memberInfoMobilechCache", String.valueOf(compId), listA);
		}
		return listA;
	}
	/**
	 * 
	 * @Method: mobileRegistration 
	 * @param memberId
	 * @param compId
	 * @param roleId
	 * @param applicationDesc
	 * @param flag 
	 * @see net.okdi.api.service.MemberInfoService#mobileRegistration(java.lang.Long, java.lang.Long, java.lang.Short, java.lang.String, java.lang.Short)
	 */
	@Override
	public void mobileRegistration(Long memberId,String memberName,String memberPhone,String idNum, Long compId, Short roleId,
			String applicationDesc, Short flag, String memberSourceFlag ,String stationPhone) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		VO_MemberInfo vo = new VO_MemberInfo(IdWorker.getIdWorker().nextId(), compId, memberId, 
				memberName, roleId, null, null, null, null, (short)0, "#c2c2c2", memberPhone, 
				idNum, null, null, null, new Date(), applicationDesc,memberSourceFlag);
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.MemberInfoServiceImpl.mobileRegistration.001",
					"手机端注册memberId不能为空");
		}
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.MemberInfoServiceImpl.mobileRegistration.002",
					"手机端注册compId不能为空");
		}
		if (PubMethod.isEmpty(roleId)) {
			throw new ServiceException("openapi.MemberInfoServiceImpl.mobileRegistration.003",
					"手机端注册roleId不能为空");
		}
		if (PubMethod.isEmpty(flag)) {
			throw new ServiceException("openapi.MemberInfoServiceImpl.mobileRegistration.004",
					"手机端注册flag不能为空");
		}
		//在进行归属信息之前插入,避免客服审批通过在relation表中增加数据时，抛出异常
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		map.put("compId", compId);
		map.put("memberName", memberName);
		map.put("memberPhone", memberPhone);
 		map.put("auditId", IdWorker.getIdWorker().nextId());
		map.put("createTime", new Date());
		map.put("memberSource", 0);
		map.put("idNum", idNum);
		map.put("associatedNumber", memberPhone);
		//在memberInfo表中添加人员信息
			int m = this.memberInfoMapper.queryCount(memberId);
			if (m == 0) {
				this.memberInfoMapper.insertMemberInfo(map);
			} else {
				//同步通行证 修改姓名
				if(flag == 2){
					updateUcenter(memberId,memberName);
					this.memberInfoMapper.editMemberInfo(map);
				}
			}
//		int a = this.memberCommInfoMapper.checkMemberPhone(map);
//		if(a !=0){
//			throw new ServiceException("openapi.MemberInfoServiceImpl.mobileRegistration.006",
//					"手机号已存在");
//		}
		if(flag ==1){// 第一步调用新增一条归属关系
			int k = this.basEmployeeAuditMapper.getAuditCount(memberId);
			logger.info("手机端注册的时候查看那该人员的归属关系===kkk=="+k);
			if(k ==0){
				this.basEmployeeAuditMapper.mobileRegisterFirstStep(vo);
				logger.info("手机端注册的时候查看那该人员的归属关系增加方法");
				
				int count =this.memberInvitationService.updateregisterFlag(memberId);
				if(count == 1){
					this.memberInvitation(memberId, compId, memberPhone, memberName);
				}else{
					String result = noticeHttpClient.updateIsPromoFlag((short)3, memberId, (short)4, (short)0);
					if(PubMethod.isEmpty(result) || !"true".equals(JSON.parseObject(result).get("success").toString())){
						throw new ServiceException("openapi.MemberInfoServiceImpl.mobileRegistration.005", "邀请奖励操作异常");
					}
				}
			}else if( k ==1){
				logger.info("手机端注册的时候查看那该人员的归属关系修改方法");
			//归属认证不能修改,但在客服审核之前可以提供修改
				this.basEmployeeAuditMapper.mobileRegisterFirstStepEdit(vo);
			}
			/*Map<String,Object> resultMemberInfo =  memberInfoMapper.queryMemberNameByMemberId(memberId);
			if(!PubMethod.isEmpty(resultMemberInfo)){
				if(("-1".equals(memberSourceFlag) && "".equals(resultMemberInfo.get("memberName")+"")) || (roleId  == -1 && "".equals(resultMemberInfo.get("memberName")))){
					this.memberInfoMapper.updateName(vo);
				}
				
			}*/
			this.basEmployeeAuditMapper.updateComp(compId, memberId);
			if( !PubMethod.isEmpty(stationPhone) && memberSourceFlag.equals("0") ){
				 noticeHttpClient.doSmsSend("02", 0L, stationPhone , "您好，"+memberPhone+CONTENT, String.valueOf(compId));
			}
		} else if (flag == 2) {// 第二部调用 新增一条身份关系
			if (PubMethod.isEmpty(memberName)) {
				throw new ServiceException("openapi.MemberInfoServiceImpl.mobileRegistration.005",
						"手机端注册memberName不能为空");
			}
			//身份认证,认证之后不能修改,在客服未审核之前可以提供修改
			int count = this.basEmployeeAuditMapper.getVerifyAudit(memberId);
			if(count ==0){
				this.basEmployeeAuditMapper.mobileRegisterSecondStep(vo);
			}else if( count ==1){
				this.basEmployeeAuditMapper.mobileRegisterSecondStepEdit(vo);
			}
			//在memberInfo表中把身份状态设置为待审核
			this.memberInfoMapper.doUpdateMemberInfo(vo);
		}
		Short verifFlag = this.queryVerifFlag(memberId);
		if(verifFlag!=null&&verifFlag==1)
		{
			Map<String,String> ucenterParam = new HashMap<String,String>();
			ucenterParam.put("memberId", memberId+"");
			ucenterParam.put("phone", memberPhone);
			this.updatePromo(ucenterParam);
		}
		ehcacheService.remove("memberInfochCache", String.valueOf(compId));
		ehcacheService.remove("memberInfoForFhwCache", memberPhone);
		ehcacheService.remove("memberInfoMobilechCache", String.valueOf(compId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
	}
	public void memberInvitation(Long memberId,Long compId,String memberPhone,String memberName){
		MemberInvitationRelation memberInvitationRelation = this.memberInvitationService.getRelation(memberId);
		BasCompInfo basCompInfo = ehcacheService.get("compCache", PubMethod.isEmpty(compId)?null:compId.toString(), BasCompInfo.class);
		if(PubMethod.isEmpty(basCompInfo)) {
			basCompInfo = basCompInfoMapper.findById(compId);
		}
		BasNetInfo basNetInfo = this.ehcacheService.get("netCache", String.valueOf(basCompInfo.getBelongToNetId()), BasNetInfo.class);
		if(PubMethod.isEmpty(basNetInfo)){
			basNetInfo = this.basNetInfoMapper.findById(basCompInfo.getBelongToNetId());
			this.ehcacheService.put("netCache", String.valueOf(basCompInfo.getBelongToNetId()), basNetInfo);
		}
		//???????回调电商管家接口
		String response = this.noticeHttpClient.memberInvitation(memberPhone, memberName, basNetInfo.getNetName(), basCompInfo.getCompName(), basCompInfo.getBelongToNetId(), compId, memberInvitationRelation.getShopId());
		Map<String,Object> result = JSON.parseObject(response);
		if(PubMethod.isEmpty(result) || !"success".equals(result.get("RESULT").toString())){
			throw new ServiceException("openapi.MemberInfoServiceImpl.mobileRegistration.006", "注册收派员，回调ERP异常");
		}
	}
	@Override
	public List<Map<String, Object>> queryMemberInfoForFhw(String memberPhone) {
		List<Map<String,Object>> list = this.ehcacheService.get("memberInfoForFhwCache", memberPhone, ArrayList.class);
		if(PubMethod.isEmpty(list)){
			list = this.memberInfoMapper.queryMemberInfoByMemberPhone(memberPhone);
			this.ehcacheService.put("memberInfoForFhwCache", memberPhone, list);
		}
		return list;
	}

	@Override
	public Map<String, Object> findMemberInfoFromAudit(Long memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Long compId = null;String memberName = "";
		List<Map<String,Object>> list = this.getAuditInfo(memberId);
		for(Map<String,Object> map1 :list){
			compId = Long.parseLong(String.valueOf(map1.get("compId"))); 
		}
		MemberInfo memberInfo = this.getMemberInfoById(memberId);
		memberName = memberInfo.getMemberName();
		map.put("compId", compId);
		map.put("memberName", memberName);
		return map;
	}
	/**
	 * 
	 * @Method: updateUcenter 
	 * @param memberId
	 * @param memberName 
	 * @see net.okdi.api.service.MemberInfoService#updateUcenter(java.lang.Long, java.lang.String)
	 */
	@Override
	public void updateUcenter(Long memberId, String memberName) {
		Map<String, String> map = new HashMap<String, String>();
		String url = constPool.getUpdCenterUrl();
		map.put("memberId", String.valueOf(memberId));
		map.put("realname", memberName);
		String response = Post(url, map);
	}

	@Override
	public List<Map<String, Object>> queryMemberCoordinate(Long compId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listm = this.queryMemberForComp(compId,null);
		if(!PubMethod.isEmpty(listm)){
			for(Map<String,Object> mapm :listm){
				//courierService.saveOnLineMember(999L, "贺海峰测试", 14460135155171328L, "啊啊啊", 10086L, "测试履历", "13111111129", 116.11d, 40.086012d, "备注");
				Map<String,Object> map = this.ehcacheService.get("onLineMember", String.valueOf(mapm.get("memberId")), Map.class);
				if(!PubMethod.isEmpty(map)){
					mapResult.put("memberId", map.get("memberId"));
					mapResult.put("memberName", this.getMemberInfoById(Long.parseLong(String.valueOf(map.get("memberId")))).getMemberName());
					mapResult.put("longitude", map.get("lng"));
					mapResult.put("latitude", map.get("lat"));
					listResult.add(mapResult);
				}
			}	
		}
		return listResult;
	}

	@Override
	public Long getMemberId(String memberPhone) {
		return this.memberTagMapper.getMemberId(memberPhone);
	}

	@Override
	public void editCompName(Long compId,String compName) {
		this.basCompEmployeeResumeMapper.editCompName(compId,compName);
		this.ehcacheService.removeAll("resumCache");
		
	}

	@Override
	public Map<String, Object> findMemberById(Long memberId) {
//		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> allmap=new HashMap<String, Object>();
		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,Object> map1=new HashMap<String, Object>();
		map=memberInfoMapper.getInfoByMemberId(memberId);
		if(PubMethod.isEmpty(map.get("verifFlag"))){
			map.put("verifFlag", "0");
		}
		map1=memberInfoMapper.getCompInfoByMemberId(memberId);
		map.put("frontURL", "http://publicapi.okdit.net/nfs_data/mob/id/front/"+memberId+".jpg");
		map.put("backURL", "http://publicapi.okdit.net/nfs_data/mob/id/back/"+memberId+".jpg");
		map.put("inHandURL", "http://publicapi.okdit.net/nfs_data/mob/id/inHand/"+memberId+".jpg");
		allmap.put("map1", map);
		allmap.put("map2", map1);
//		list.add(map);
//		list.add(map1);
		return allmap;
	}

	@Override
	public Map<String, Object> findMemberAll(Integer pageNum,
			Integer pageSize, String memberName, String memberPhone,
			String netId, Short roleType, String compType, String compName,
			Short opinion, String beginTime, String endTime,String status) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		int total=0;
		//待认证收派员
		if(status.equals("1")){
			list=memberInfoMapper.findMemberPinding(pageNum*pageSize, pageSize, memberName, 
					memberPhone, netId, roleType, compType, compName,
					 opinion, beginTime, endTime);
			total=memberInfoMapper.getCountTotalPending(memberName, 
					memberPhone, netId, roleType, compType, compName,
					 opinion, beginTime, endTime);
		}
		//全部人员
		if(status.equals("2")){
			list=memberInfoMapper.findMemberAll(pageNum*pageSize, pageSize, memberName, 
					memberPhone, netId, roleType, compType, compName,
					 opinion, beginTime, endTime);
			total=memberInfoMapper.getCountTotalAll(memberName, 
					memberPhone, netId, roleType, compType, compName,
					 opinion, beginTime, endTime);
		}
		int allMemberNum=memberInfoMapper.getallMemberNum();
		int unPassNum=memberInfoMapper.getUnPassNum();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("allMemberNum", allMemberNum);
		map.put("unPassNum", unPassNum);
		map.put("record", list);
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		map.put("total", total);
		
		return map;
	}
	@Override
	public void verifyRelationForOperate(String phone){
		Long memberId = this.getMemberId(phone);
		if(PubMethod.isEmpty(memberId)){
			throw new ServiceException("openapi.MemberInfoServiceImpl.verifyRelationForOperate.001","地推归属审核，收派员不存在");
		}
		Map<String, Object> mapV = this.memberInfoMapper.getRelationInfo(memberId);
		if(PubMethod.isEmpty(mapV) || PubMethod.isEmpty((mapV.get("compId"))) || PubMethod.isEmpty((mapV.get("roleId")))){
			throw new ServiceException("openapi.MemberInfoServiceImpl.verifyRelationForOperate.002","地推归属审核，收派员归属关系不存在");
		}
		Long compId = Long.parseLong(String.valueOf(mapV.get("compId")));//站点id
		this.verifyRelation(memberId, compId, Short.parseShort(mapV.get("roleId").toString()), (short)1);
	}
	
    /**
	 * 
	 * @Method: queryMemberForComp
	 * @Description: 查询站点或营业分部下的人员列表
	 * @param compId
	 *            网点id
	 * @param roleId
	 *            角色id
	 * @return
	 * @see net.okdi.api.service.MemberInfoService#queryMemberForComp(java.lang.Long,
	 *      java.lang.Short)
	 * @since jdk1.6
	 */
	@Override
	public List<Map<String, Object>> querySiteMemberList(Long compId, Long roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", compId);
		map.put("roleId", roleId);
		List<Map<String, Object>> list = this.basEmployeeRelationMapper.queryMemberForComp(map);
		return list;
	}
	
	/**
	 * 
	 * @Method: doEditMemberInfo
	 * @Description: 修改右侧人员信息
	 * @param memberId
	 *            人员id
	 * @param roleId
	 *            角色id
	 * @param employeeWorkStatusFlag
	 *            工作标志
	 * @param areaColor
	 *            片区颜色
	 * @see net.okdi.api.service.MemberInfoService#doEditMemberInfo(java.lang.Long,
	 *      java.lang.Short, java.lang.Short, java.lang.String)
	 * @since jdk1.6
	 */
	@Override
	public void updateMemberInfo(Long compId, Long memberId, Short roleId,
			Short employeeWorkStatusFlag, String areaColor,Long userId) {
		if(employeeWorkStatusFlag !=1&&employeeWorkStatusFlag !=2){
			throw new ServiceException("openapi.MemberInfoServiceImpl.updateMemberInfo.001","工作状态传入参数错误");
		}
		if(!VerifyUtil.isColor(areaColor)){
			throw new ServiceException("openapi.MemberInfoServiceImpl.updateMemberInfo.003","颜色色值传入参数错误");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		map.put("employeeWorkStatusFlag", employeeWorkStatusFlag);
		map.put("areaColor", areaColor);
		int a = this.basEmployeeRelationMapper.doEditMemberInfo(map);
		int a2 = this.basEmployeeRelationMapper.editBasEmployeeaudit(map);
		if (a == 0 || a2 == 0) {
			throw new ServiceException("openapi.MemberInfoServiceImpl.updateMemberInfo.004","修改人员归属信息异常  ");
		}
		Map<String,Object> memberInfoMap = this.basEmployeeRelationMapper.findMemberInfoById(memberId);
		if(roleId == 1){ //如果站长设置另一个人为站长，那么我们就把原来的站长降级为收派员
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("memberId", userId);
			map1.put("roleId", 0); //0 收派员       -1 后勤    1 站长 
			map1.put("employeeWorkStatusFlag", employeeWorkStatusFlag);
			map1.put("areaColor", areaColor);
			int b = this.basEmployeeRelationMapper.doEditMemberInfo(map1);
			int b2 = this.basEmployeeRelationMapper.editBasEmployeeaudit(map1);
//			memberInfoMap.put("compId", compId);
//			this.basEmployeeRelationMapper.updateBascompbusiness(memberInfoMap);
			
			if (b == 0 || b2 == 0) {
				throw new ServiceException("openapi.MemberInfoServiceImpl.updateMemberInfo.005","修改人员归属信息异常  ");
			}
			try {
				MemberInfo memberInfo=new MemberInfo();
				memberInfo=this.getMemberInfoById(memberId);
				sendNoticeService.updateRoleInfo(memberId,userId,memberInfo.getMemberPhone());				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(roleId == 2){
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("memberId", userId);
			map1.put("roleId", 3); //0 收派员       -1 后勤    1 站长 
			map1.put("employeeWorkStatusFlag", employeeWorkStatusFlag);
			map1.put("areaColor", areaColor);
			int b = this.basEmployeeRelationMapper.doEditMemberInfo(map1);
			int b2 = this.basEmployeeRelationMapper.editBasEmployeeaudit(map1);
			memberInfoMap.put("compId", compId);
			this.basEmployeeRelationMapper.updateBascompbusiness(memberInfoMap);
			if (b == 0 || b2 == 0) {
				throw new ServiceException("openapi.MemberInfoServiceImpl.updateMemberInfo.005","修改人员归属信息异常  ");
			}
			try {
				MemberInfo memberInfo=new MemberInfo();
				memberInfo=this.getMemberInfoById(memberId);
				sendNoticeService.updateRoleInfo(memberId,userId,memberInfo.getMemberPhone());				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		List<VO_MemberInfo> list = this.queryEmployeeCache(compId);
		ehcacheService.removeAll("userInfoCache");
		ehcacheService.put("employeeCache", compId.toString(), list);
		ehcacheService.remove("resumCache", String.valueOf(memberId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(compId));
		ehcacheService.remove("memberInfochCache", String.valueOf(memberId));
		ehcacheService.remove("employeeRelationCache", String.valueOf(memberId));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
		ehcacheService.remove("resumCache", String.valueOf(userId));
		ehcacheService.remove("memberInfochCache", String.valueOf(userId));
		ehcacheService.remove("employeeRelationCache", String.valueOf(userId));
		ehcacheService.remove("memberInfoForFhwCache", String.valueOf(userId));
		redisService.removeAll("verifyInfo-memberId-cache");
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		redisService.removeAll("findShopowner1xxx");
		redisService.removeAll("findShopowner2xxx");
		redisService.removeAll("findShopowner3xxx");
	}
	@Override
	public Long getMemberIdByMemberPhone(String memberPhone){
		return this.memberInfoMapper.findMemberIdByMemberPhone(memberPhone);
		
	}
		
	@Override
	public Long queryBasEmployeeAudit(long memberId) {
	 return this.basEmployeeAuditMapper.findBasEmployeeAuditByMemberId(memberId);
	
	}
	@Override
	public Long queryBasEmployeeRelation(long memberId){
		return this.basEmployeeRelationMapper.findByMemberId(memberId);
	}
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description 根据memberId查询
	 * @data 2015-10-7 上午11:19:49
	 * @return
	 * @param memberId
	 * @return
	 */
	
	public boolean ifCourier(String memberPhone){
		
		
		Long memberId=getMemberIdByMemberPhone(memberPhone);
		if(PubMethod.isEmpty(memberId)){
			return false;
		}
		Long id=queryBasEmployeeAudit(memberId);
		if(PubMethod.isEmpty(id)){
			if(PubMethod.isEmpty(queryBasEmployeeRelation(memberId))){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	/**
	 * 
	 * @Method: queryCourierAuditList
	 * @Description: 查询收派员审核列表
	 * @param memberName 收派员姓名
	 * @param phone 手机号
	 * @param startTime 注册起始时间
	 * @param endTime 注册结束时间
	 * @param auditType 审核类型  0.待审核 1.通过 2.拒绝
	 * @param pageNo 页码
	 * @param pageSize 每页显示条数
	 * @since jdk1.6
	 */
	@Override
	public Map<String,Object> queryCourierAuditList(String memberName, String phone, String startTime, String endTime,
			Short auditType,Integer pageNo,Integer pageSize){
		if(auditType !=0 && auditType !=1 && auditType !=2){
			throw new ServiceException("openapi.MemberInfoServiceImpl.queryCourierAuditList.001","审核类型参数错误");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberName", memberName);
		map.put("phone", phone);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("auditType", auditType);
		map.put("pageNo", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		List<Map<String,Object>> courierAuditList=new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		courierAuditList = this.memberInfoMapper.queryCourierAuditList(map);
		int courierAuditCount = this.memberInfoMapper.queryCourierAuditCount(map);
		resultMap.put("courierAuditList", courierAuditList);
		resultMap.put("total", courierAuditCount);
		if(courierAuditCount%pageSize>0){
			resultMap.put("totalPage", courierAuditCount/pageSize+1);			
		}else{
			resultMap.put("totalPage", courierAuditCount/pageSize);			
		}
		return resultMap;
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询待审核人员信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>kai.yang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-07 上午10:44:04</dd>
	 * @param memberId 用户id
	 * @since v1.0
	 */
	@Override
	public Map<String,Object> queryCourierAuditInfo(Long memberId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = this.memberInfoMapper.queryCourierAuditInfo(memberId);
		resultMap.put("headImage",String.valueOf(constPool.getHeadImgPath()+memberId+".jpg"));
		resultMap.put("frontImage",String.valueOf(constPool.getFrontImgPath()+memberId+".jpg"));
		resultMap.put("inHandImage",String.valueOf(constPool.getInHandImgPath()+memberId+".jpg"));
		return resultMap;
	}
	@Override
	public List<String> queryMemberByPhone(String memberPhone){
		List<String> list=new ArrayList<String>();
		list = this.memberInfoMapper.queryMemberByPhone(memberPhone);
		for(String memberIds:list){
			Long memberId=Long.valueOf(memberIds);
			this.memberInfoMapper.deleteResumeByMemberId(memberId);
			System.out.println("删除履历表成功");
			this.memberInfoMapper.deleteAuditByMemberId(memberId);
			System.out.println("删除审核表成功");
			this.memberInfoMapper.deleteRelationalByMemberId(memberId);
			System.out.println("删除关系表成功");
			this.memberInfoMapper.deleteInfoByMemberId(memberId);
			System.out.println("删除人员信息表成功");
			MemberInfo memberInfo=new MemberInfo();
			memberInfo=this.memberInfoMapper.getMemberInfoById(memberId);
			if(memberInfo!=null){
				ehcacheService.remove("employeeCache", memberInfo.getCompId().toString());
				ehcacheService.remove("memberAndBranchCache", memberInfo.getCompId().toString());
				ehcacheService.remove("memberInfoMobilechCache", memberInfo.getCompId().toString());
			}
			ehcacheService.remove("resumCache", String.valueOf(memberId));
			ehcacheService.remove("memberInfochCache", String.valueOf(memberId));
			ehcacheService.remove("memberInfoForFhwCache", String.valueOf(memberId));
			ehcacheService.remove("employeeRelationCache", String.valueOf(memberId));
			System.out.println("删除缓存成功");
		}
		return list;
	}

	/**
	 * 	修改审核状态
	 */
	@Override
	public void updateAuditOpinion(Long memberId, Short auditOpinion,
			Short auditRejectReason) {
		MemberInfo memberInfo = this.getMemberInfoById(memberId);	//根据memberId 查memberPhone
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("memberId", memberId);
		param.put("auditOpinion", auditOpinion);
		param.put("auditRejectReason", auditRejectReason);
		
		if(auditOpinion!=1)
		{
			basEmployeeAuditMapper.updateAuditOpinion(param);
			memberInfoMapper.updateVerifFlag(param);
			//调用ucenter
			Map<String,String> paramString = new HashMap<String,String>();
			paramString.put("memberId", memberId+"");
			paramString.put("identityStatus", String.valueOf(auditOpinion+1));
			String result = noticeHttpClient.updateUserIdentityStatus(paramString);
			if(Integer.valueOf(result)!=1)
			{
				throw new  ServiceException();
			}
		}else{
			//调用ucenter
			Map<String,String> paramString = new HashMap<String,String>();
			paramString.put("memberId", memberId+"");
			paramString.put("identityStatus", String.valueOf(auditOpinion+1));
			String result = noticeHttpClient.updateUserIdentityStatus(paramString);
			if(Integer.valueOf(result)!=1)
			{
				throw new  ServiceException();
			}
			basEmployeeAuditMapper.updateAuditOpinion(param);
			memberInfoMapper.updateVerifFlag(param);
			Boolean flag = this.ifCourier(memberInfo.getMemberPhone());
			if(flag)
			{
				Map<String,String> ucenterParam = new HashMap<String,String>();
				ucenterParam.put("memberId", memberId+"");
				ucenterParam.put("phone", memberInfo.getMemberPhone());
				this.updatePromo(ucenterParam);
			}
			
		}
	}

	/**
	 * 	查询人员身份验证标识
	 */
	@Override
	public Short queryVerifFlag(Long memberId) {
		return memberInfoMapper.queryVerifFlag(memberId);
	}

	/**
	 * 	调用ucenter
	 * @param ucenterParam
	 */
	public void updatePromo(Map<String,String> ucenterParam)
	{
		String result = noticeHttpClient.updatePromo(ucenterParam);
		System.out.println(result);
	}

	/**
	 * 根据手机号查询用户相关信息
	 * @Method: getUserInfo 
	 * @param memberPhone
	 * @return 
	 * @see net.okdi.api.service.MemberInfoService#getUserInfo(java.lang.String)
	 */
	@Override
	public Map<String, Object> getUserInfo(Long memberPhone) {
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("memberId", null);
		Map<String, Object> result = userInfoMapper.getUserInfo(memberPhone);
		String memberId = String.valueOf(result.get("member_id"));
		if(memberId != null && memberId.length() > 0){
			resp.put("memberId", memberId);
			resp.put("memberSource", result.get("member_source"));
			resp.put("registerTime", result.get("create_time"));
			resp.put("memberName", result.get("member_name"));
			resp.put("idNum", result.get("id_num"));
			resp.put("memberSort", result.get("application_role_type"));
			resp.put("netName", result.get("net_name"));
			resp.put("memberStation", result.get("comp_name"));
			resp.put("stationType", result.get("agent_type"));
			resp.put("stationStatus", result.get("comp_status"));
			resp.put("compId", result.get("comp_id"));
			List<String> options1 = userInfoMapper.userIdConfirm(Long.parseLong(memberId), (short)1);//1:身份审核(实名审核)
			if(options1 != null && options1.size() > 0){
				resp.put("idStatus", options1.get(0));
			}else{
				resp.put("idStatus", "");
			}
			List<String> options2 = userInfoMapper.userIdConfirm(Long.parseLong(memberId), (short)2);//2:归属审核
			if(options2 != null && options2.size() > 0){
				resp.put("ownerShipStatus", options2.get(0));
			}else{
				resp.put("ownerShipStatus", "");
			}
			List<String>  options3 = userInfoMapper.userIdConfirm(Long.parseLong(memberId), (short)3);//3.快递员认证审核
			if(options3 != null && options3.size() > 0){
				resp.put("expressStatus", options3.get(0));
			}else{
				resp.put("expressStatus", "");
			}
			List<String> options4 = userInfoMapper.userIdConfirm(Long.parseLong(memberId), (short)4);//4.客服确认快递员
			if(options4 != null && options4.size() > 0){
				resp.put("isCourier", options4.get(0));
			}else{
				resp.put("isCourier", "");
			}
		}
		return resp;
	}

	@Override
	public String clearNormalRoleInfo(Long memberId, Long compId,String memberName, String memberPhone, Short roleId) {
		//普通员工/无下属员工站长或店长清除角色
		//清除relation表，更新employeeaudi表
		this.userInfoMapper.clearNormalRole(memberId, compId);//删除
		this.userInfoMapper.clearMemberInfoRole(memberId); //更新
		this.sendNoticeService.quitToExpMember(memberId, memberPhone, roleId);
		return "ok";
	}

	@Override
	public String clearStationRoleInfo(Long oldMemberId, Long newMemberId, Long compId, Short roleId,String memberName, String memberPhone) {
		//站长/店长清除角色
		//清除站长的relation，更新站长的employeeaudit
		//更改普通员工的relation，更改普通员工的employeeaudit
		this.userInfoMapper.clearNormalRole(oldMemberId, compId); //删除店长relation
		this.userInfoMapper.clearMemberInfoRole(oldMemberId);//更新店长employeeaudit
		this.userInfoMapper.clearStationRole(roleId, newMemberId, compId);//更新普通用户relation
		this.userInfoMapper.roleTransfer(newMemberId, roleId);//更新普通用户employeeaudit
		this.sendNoticeService.quitToExpMember(oldMemberId, memberPhone, roleId);
		return "ok";
	}

	@Override
	public List<Map<String, Object>> queryNormalRoles(Long compId) {
		//查询站长/店长下属员工
		return this.userInfoMapper.queryStationUserInfo(compId);
	}

	@Override
	public Map<String, Object> validTaskHandler(Long memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
//		Long result = this.userInfoMapper.validTaskHandler(memberId);
		Query query = new Query();
		query.addCriteria(Criteria.where("actorMemberId").is(memberId));
		query.addCriteria(Criteria.where("taskStatus").is(1));
		query.addCriteria(Criteria.where("taskFlag").is(1));
		
		Date endTime = new Date();

		Calendar calendar = new GregorianCalendar();

		calendar.setTime(endTime);

		calendar.add(calendar.DATE,-3);//把日期往前增加3天.整数往后推,负数往前移动

		Date startTime=calendar.getTime(); 

		//更新时间
		query.addCriteria(Criteria.where("createTime").gte(startTime).lte(endTime));
		Long result = mongoTemplate.count(query, ParTaskInfo.class);
		map.put("count", result+"");
		return map;
	}

	@Override
	public Map<String, Object> validPlaintInfo(Long memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Long result = this.userInfoMapper.validPlaintInfo(memberId);
		map.put("count", result+"");
		return map;
	}
}
