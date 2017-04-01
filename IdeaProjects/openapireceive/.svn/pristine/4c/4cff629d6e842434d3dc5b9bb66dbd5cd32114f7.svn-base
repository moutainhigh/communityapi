package net.okdi.apiV1.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.service.DicAddressService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.apiV1.dao.BasCompAuditMapperV1;
import net.okdi.apiV1.dao.BasCompBusinessMapperV1;
import net.okdi.apiV1.dao.BasCompInfoMapperV1;
import net.okdi.apiV1.dao.BasCompimageMapperV1;
import net.okdi.apiV1.dao.BasEmployeeRelationMapperV1;
import net.okdi.apiV1.dao.MemberInfoMapperV1;
import net.okdi.apiV1.service.OutletsService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class OutletsServiceImpl implements OutletsService {

	@Autowired
	private BasCompInfoMapperV1 compInfoMapper;
	@Autowired
	private BasEmployeeRelationMapperV1 basEmployeeRelationMapper;
	@Autowired
	private MemberInfoMapperV1 memberInfoMapper;
	@Autowired
	private BasCompBusinessMapperV1 basCompBusinessMapper;
	@Autowired
	private DicAddressService parseService;
	@Autowired
	private BasCompimageMapperV1 basCompimageMapper;
	@Autowired
	private BasCompAuditMapperV1 basCompAuditMapper;
	@Autowired
	private NoticeHttpClient noticeHttpClient;
	@Value("${life_http_url}")
	private String lifeHttpUrl;
	@Value("${shortlink_http_url}")
	private String shortLinkUrl;//获取短链接KEY
	@Value("${longlink_http_url}")
	private String longLinkUrl;//短网址调转到长网址
	
	@Autowired
	private SendNoticeService sendNoticeService;
	/*
	 * 店员照片
	 */
	@Value("${meImgPath}")
	private String meImgPath;//大头照
	@Value("${frontImgPath}")
	private String frontImgPath;//身份证正面照
	@Value("${inHandImgPath}")
	private String inHandImgPath;//手持身份证照片
	@Autowired
	private EhcacheService ehcacheService;
	
	
	@Autowired
	private RedisService redisService;
	@Value("${compPic.readPath}")
	public String readPath;
	private static SerializerFeature[] features = {
			SerializerFeature.WriteNullNumberAsZero,
			SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.DisableCircularReferenceDetect };

	public Map<String, Object> queryOpenSiteDetail(Long compid) {
		Map<String, Object> map = redisService.get("queryOpenSiteDetailByCompId", compid + "", Map.class);
		if (PubMethod.isEmpty(map)) {
			map = compInfoMapper.queryOpenSiteDetail(compid);
			redisService.put("queryOpenSiteDetailByCompId", compid + "", map);
		}
		return map;
	}

	public List<Map<String, Object>> selectCompinfoByName(String comp_name) {
		List<Map<String, Object>> map = compInfoMapper
				.selectCompinfoByName(comp_name);
		return map;
	}

//	public Map<String, Object> selectMemName(String member_id) {
//		Map<String, Object> map = memberInfoMapper
//				.selectMumberByMumberId(member_id);
//		return map;
//	}

	public Map<String, Object> getAdrressId(String longitude, String latitude) {// 得到地址id
		Map<String, Object> Resultmap = parseService.parseAddrByList(latitude,
				longitude);
		// String addressId = String.valueOf(Resultmap.get("addressId"));
		return Resultmap;
	}

	@Override
	public Long insertBasCompinfo(String comp_name, String comp_address,
			String responsible_telephone, String phone, String roleid,
			String longitude, String latitude, String member_id,String responsible, String agentType) {
		
		Map<String, Object> Resultmap = parseService.parseAddrByList(latitude,longitude);
		Long addressId = Long.parseLong(String.valueOf(Resultmap.get("addressId")));
		// TODO Auto-generated method stub
		List<Map<String, Object>> listName = this
				.selectCompinfoByName(comp_name);
		if (listName.size() != 0) {
			throw new ServiceException(
					"net.okdi.apiV1.service.impl.OutletsServiceImpl.insertBasCompinfo.001",
					"该名称已存在!");
			//代收点已经存在!
		} else {
			Long id = IdWorker.getIdWorker().nextId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("comp_id", id);// 公司id
			map.put("comp_name", comp_name);// 公司名称
			map.put("agentType", agentType);// 公司名称
			try {
				map.put("first_letter", PiyinUtil.getFullNameSpell(comp_name)
						.toUpperCase().subSequence(0, 1));
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}// 公司名首字母
			map.put("regist_flag", 1);// 注册标志 0:未注册,1:已注册
			map.put("comp_type_num", 1040);// 公司分类代码
											// 1000:网络,1003:网络直营站点,1006:加盟公司,1008:加盟公司站点,1030:快递代理点,1050:营业分部

			/*Map<String, Object> map4 = this.getAdrressId(longitude, latitude);
			if (map4 != null) {
				map.put("comp_address_id", map4.get("address_id"));// 公司地址id,待定,应该是前台传入进来
			}else{
				map.put("comp_address_id", "");
			}*/
			map.put("comp_address_id", addressId);
			map.put("comp_address", comp_address);// 公司地址
			map.put("longitude", longitude);// 经度
			map.put("latitude", latitude);// 维度
			map.put("comp_status", 1);// 公司状态 -1:创建,0:提交待审核,1:审核通过,2:
										// 审核不通过4:再次提交待审核
//			map.put("belong_to_net_id", 1501);// 快递加盟公司及站点所属快递网络I -1:代表此字段无意义
			map.put("belong_to_comp_id", -1); // 加盟公司站点所属加盟公司ID -1:代表此字段无意义
			// map.put("relation_comp_id", -1); // 加盟公司站点所属加盟公司ID -1:代表此字段无意义
			map.put("write_send_status", -1); // 快递加盟公司及站点收派业务状态
												// -1:此字段无意义,0:收+派,1:收,2:派
			map.put("goods_payment_status", -1); // 快递加盟公司及站点代收货款业务状态
													// -1:此字段无意义,0:收+派,1:收,2:派,3:不收不派
			map.put("create_time", new Date());
			map.put("comp_telephone", phone);// 公司固定电话
			map.put("comp_mobile", responsible_telephone);// 公司手机号
			map.put("tao_goods_payment_status", -1);// 淘宝认证代收货款业务状态
													// -1:此字段无意义,0:收+派,1:收,2:派,3:不收不派
			map.put("login_flag", 2);// 登录标识
										// -2:未登录过,0:公司处于创建状态时登录过,1:公司处理待审核时登录过,2:审核通过后登录过

			try {
				 compInfoMapper.insertBasCompinfo(map);
				/*
				 * 插入bas_employeeaudit,归属审核表，直接通过
				 */
				map.clear();
				map.put("id", IdWorker.getIdWorker().nextId());
				map.put("member_id", member_id); 
				map.put("audit_comp", id); 
				map.put("application_role_type", roleid); 
				map.put("application_time", new Date()); 
				map.put("audit_item", 2); 
				map.put("audit_opinion", 1);
 				map.put("audit_desc", "店长的归属认证默认通过");
 				basCompAuditMapper.insertbasCompAudit(map);
				
				/*
				 * 插入bas_compbusiness,公司业务表
				 */
				map.clear();
				map.put("responsible_telephone", responsible_telephone);// 负责人手机号
				map.put("responsible", responsible);// 负责人
				map.put("comp_id", id);// 主键ID
				basCompBusinessMapper.insertBusiness(map);
				  
				/*
				 * 插入bas_employee_relation，人员公司关系表
				 */
				map.clear();
				map.put("id", IdWorker.getIdWorker().nextId());
				map.put("member_id", member_id);
				map.put("comp_id", id);
				map.put("role_id", roleid);
				map.put("area_color", "#c2c2c2");
				map.put("employee_work_status_flag", 1);
				map.put("review_task_receiving_flag", 1);
			    basEmployeeRelationMapper.insertBasEmployeeRelation(map);
			    redisService.removeAll("nearCompInfoByCompNameCache");
			  
			} catch (Exception e) {
				new ServiceException(
						"net.okdi.apiV1.service.impl.OutletsServiceImpl.insertBasCompinfo.002",
						"代收点添加失败!");
			}
			//清海峰缓存
			redisService.removeAll("verifyInfo-memberId-cache");
			redisService.removeAll("relationInfo-memberId-cache");
			redisService.removeAll("expressInfo-memberId-cache");
			//删除缓存
			ehcacheService.removeAll("employeeCache");
			//删除缓存
			redisService.removeAll("nearCompInfoByCompNameCache");
			//清除胡宣化的缓存
			redisService.removeAll("findShopowner1xxx");
			redisService.removeAll("findShopowner2xxx");
			redisService.removeAll("findShopowner3xxx");
			return id;
		}
	}

	public Map<String, Object> queryOpenSite(Long compid) {

		return compInfoMapper.queryOpenSite(compid);
	}

	// 店长或者店员入驻代售点
	public String saveSiteOpen(Long compid, Long roleid,Long memberid, String managerId, String mobile) {
		String result = "011";//如果是011,说明是成功的(店长店员都是成功的)
		int mark = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String application_time = dateFormat.format(new Date());
		if (roleid == 2L) { // 店长
			Map<String, Object> map = this.queryOpenSite(compid);
	        if(map==null){
	        	//result = "001";//该compid在库中没有查到数据
	        	throw new ServiceException("net.okdi.apiV1.service.impl.OutletsServiceImpl.saveSiteOpen.001","该【"+compid+"】在库中没有查到数据!!!");
	        	//return result;
	        }
			Long relation_comp_id = (Long) map.get("relation_comp_id");
			if (relation_comp_id != -1L) {
				//result = "002";//代表该代收站已经被领用
				throw new ServiceException("net.okdi.apiV1.service.impl.OutletsServiceImpl.saveSiteOpen.002","该代收点已经被领用!!!");
			} else {
				Map<String, Object> mapI = new HashMap<String, Object>();
				long id = IdWorker.getIdWorker().nextId();
				mapI.put("comp_id", id);// 公司id
				mapI.put("comp_num", map.get("comp_num"));// '公司标识代码',
				mapI.put("comp_short", map.get("comp_short"));// '公司简称'
				mapI.put("comp_name", map.get("comp_name"));// '公司名称',
				mapI.put("first_letter", map.get("first_letter"));// 公司名首字母
				mapI.put("regist_flag", map.get("regist_flag"));// 注册标志 0:未注册,1:已注册
				mapI.put("comp_type_num", 1040);// 公司分类代码,1000:网络,1003:网络直营站点,1006:加盟公司,1008:加盟公司站点,1030:快递代理点,1040:认领的快递代理点,1050:营业分部',
				mapI.put("comp_address_id", map.get("comp_address_id"));// 地址ID
				mapI.put("comp_address", map.get("comp_address"));// 公司所在地址
				mapI.put("longitude", map.get("longitude"));// 经度
				mapI.put("latitude", map.get("latitude"));// 纬度
				mapI.put("comp_url", map.get("comp_url"));// 网址URL
				mapI.put("taobao_url", map.get("taobao_url"));// 淘宝主店URL
				mapI.put("comp_status", 1);// 公司状态:-1:创建,0:提交待审核,1:审核通过,2: 审核不通过4:再次提交待审核
				mapI.put("comp_regist_way", map.get("comp_regist_way"));// 公司注册方式 1:系统建的,2:网站建的,3:导入的,4:淘客数据
				//默认圆通的 1501
				mapI.put("belong_to_net_id", 1501);// 快递加盟公司及站点所属快递网络I-1:代表此字段无意义
				mapI.put("belong_to_comp_id", map.get("belong_to_comp_id"));// 加盟公司站点所属加盟公司ID
												// -1:代表此字段无意义
				// mapI.put("relation_comp_id", id);//领用站点ID -1 无领用关系
				mapI.put("write_send_status", map.get("write_send_status"));// 快递加盟公司及站点收派业务状态-1:此字段无意义,0:收+派,1:收,2:派
				mapI.put("goods_payment_status",map.get("goods_payment_status"));// 快递加盟公司及站点代收货款业务状态-1:此字段无意义,0:收+派,1:收,2:派,3:不收不派
				mapI.put("create_time", new Date());// 创建时间// mapI.put("modifiy_time", map.get("modifiy_time"));//更新时间
				mapI.put("league_time", map.get("league_time"));// 加盟时间
				mapI.put("create_user", map.get("create_user"));// 创建人ID
				mapI.put("internal_user_last_time",map.get("internal_user_last_time"));// 公司业务人员最后一次登录时间
				mapI.put("tao_goods_payment_status",map.get("tao_goods_payment_status"));// 淘宝认证代收货款业务状态-1:此字段无意义,0:收+派,1:收,2:派,3:不收不派
				mapI.put("last_audit_id", map.get("last_audit_id"));// 最后一条审核记录
				mapI.put("comp_mobile", map.get("comp_mobile"));// 公司手机号
				mapI.put("comp_telephone", map.get("comp_telephone"));// 公司固定电话
				mapI.put("comp_email", map.get("comp_email"));// 公司邮箱
				mapI.put("login_flag", map.get("login_flag"));// 登录标识-2:未登录过,0:公司处于创建状态时登录过,1:公司处理待审核时登录过,2:审核通过后登录过
				mapI.put("erp_comp_id", map.get("erp_comp_id"));// 关联ERP公司ID
				// 查询到bas_compinfo 一条数据,
				try {
					mark = compInfoMapper.insertBasCompinfo(mapI);
					//插入到bas_employeeaudit 1.归属通过
					int audit_item1 = 2;
					int audit_opinion1 = 1;
					compInfoMapper.saveEmployeeaudit(IdWorker.getIdWorker().nextId(), memberid, roleid, compid, audit_item1, audit_opinion1, new Date());
					// bas_employee_relation 一条
					map.clear();
					map.put("id", IdWorker.getIdWorker().nextId());
					map.put("member_id", memberid);
					map.put("comp_id", id);
					map.put("role_id", roleid);
					map.put("area_color", "#c2c2c2");
					map.put("employee_work_status_flag", 1);
					map.put("review_task_receiving_flag", 1);
					mark = basEmployeeRelationMapper.insertBasEmployeeRelation(map);
					// 更新
					Date date = new Date();
					SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String strdate = sdate.format(date);
					mark = compInfoMapper.updateBaseCompinfo(id, compid, strdate);
					if (mark == 0) {
						//mapResult.put("result", "店铺入驻成功!!!");
						throw new ServiceException("net.okdi.apiV1.service.impl.OutletsServiceImpl.updateBaseCompinfo.003","该代收点数据更新失败!!!");
					}
				} catch (Exception e) {
					throw new ServiceException(
							"net.okdi.apiV1.service.impl.OutletsServiceImpl.saveSiteOpen.004","数据操作失败!!!");
				}
			}
		}
		if (roleid == 3L) { // 店员
			//先去查询是否已经入驻过,如果入驻过就不在加入了,否则申请加入
			int auditItem = 2;//归属审核
			//查询店员归属审核的状态
			Map auditOpinion = compInfoMapper.queryEmployeeauditByPhone(memberid, auditItem);
			/*if(null == auditOpinion){
				System.out.println("bbbb");
			}*/
			if(null == auditOpinion){
				// 插入 到bas_employeeaudit, 1. 归属待审核
				// 主键id
				long id = IdWorker.getIdWorker().nextId();
				try {
					int audit_item = 2;
					int audit_opinion = 0;
					compInfoMapper.saveEmployeeaudit(id, memberid, roleid, compid, audit_item, audit_opinion, new Date());
					//result = "申请入驻成功,请等店长审核!!!";
				} catch (Exception e) {
					throw new ServiceException(
							"net.okdi.apiV1.service.impl.OutletsServiceImpl.saveSiteOpen.005","店员申请入驻失败!");
				}
			}
			//店员申请入驻（代收站）给店长发送推送
			sendNoticeService.sendPushSMSManagerSuccess(Long.valueOf(managerId), mobile);
		}
		//清海峰缓存
		redisService.removeAll("verifyInfo-memberId-cache");
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
			//清空缓存
		redisService.remove("queryOpenSiteDetailByCompId", compid+"");
		//删除缓存
		ehcacheService.removeAll("employeeCache");
		//删除缓存
		ehcacheService.remove("memberInfoMobilechCache",compid.toString());
		redisService.removeAll("nearCompInfoByCompNameCache");
		//清除胡宣化的缓存
		redisService.removeAll("findShopowner1xxx");
		redisService.removeAll("findShopowner2xxx");
		redisService.removeAll("findShopowner3xxx");
		return result;
	}

	// 通过以下方式邀请店长入住, 微信, 短信
	// 插入关系表中 member_invitation_register
	public void invite(String fromMemberId, String fromMemberPhone,
			Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId, Integer flag) {
		//发送短信
		if(flag.equals(1)){
			 String mark = noticeHttpClient.doSmsSend("02", 0L, toMemberPhone ,
			"您的店员 "+fromMemberPhone+"，邀请您前往好递平台开通代收发包裹服务，狂卷周边客源，抢占无限商机。点击开通："+getShortUrl(), "");
		}
		//1.先去查询是否邀请过该店长
		Long lid = compInfoMapper.queryManagerIsInviteByPhone(fromMemberId, fromMemberPhone, toMemberPhone);
		if(!PubMethod.isEmpty(lid)){
			//2.然后根据两个人(邀请人和被邀请人)的手机号更改时间
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String modityTime = dateFormat.format(date);
			compInfoMapper.ManagerIsInviteByPhone(fromMemberId, fromMemberPhone, toMemberPhone, modityTime);
		}else{
			// 主键id
			long id = IdWorker.getIdWorker().nextId();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String createTime = dateFormat.format(date);
			int isRegister = 0;
			int mark1 = compInfoMapper.saveMemberInvitationRegister(id, createTime,
					fromMemberId, fromMemberPhone, fromMemberRoleid, toMemberPhone,
					toRoleId, null, isRegister);
			if (mark1 == 0) {
				throw new ServiceException("net.okdi.apiV1.service.impl.OutletsServiceImpl.invite","邀请店长入驻失败!!!");
			}
			//插入到bas_employeeaudit, 归属待审核
			long eid = IdWorker.getIdWorker().nextId();
			int audit_item = 2;
			int audit_opinion = 0;
			compInfoMapper.saveEmployeeaudit(eid, Long.valueOf(fromMemberId), Long.valueOf(fromMemberRoleid), null, audit_item, audit_opinion, new Date());
		}
		
	}

	// 店员通过手机号查找店长(找到了)
	public Map<String, Object> findShopowner(String telephone) {
		Map<String, Object> map = redisService.get("findShopownerByTelephone",telephone + "", Map.class);
		if (map==null) {
			map = compInfoMapper.findShopowner(telephone);
			if(!PubMethod.isEmpty(map)){
				String imageUrl = (String) map.get("image_url");
				map.put("image_url", readPath+imageUrl);
				redisService.put("findShopownerByTelephone", telephone + "", map);
			}
		}
		return map;
	}

	// 收派员申请加入
	public void apply(Long roleid, Long memberid, Long compId) {
		long id = IdWorker.getIdWorker().nextId();
		int audit_item = 2;
		int audit_opinion = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String application_time = dateFormat.format(new Date());
		int mark = compInfoMapper.saveEmployeeaudit(id, memberid, roleid,
				compId, audit_item, audit_opinion, new Date());
		if (mark == 0) {
			throw new ServiceException(
					"net.okdi.apiV1.service.impl.OutletsServiceImpl.saveSiteOpen.saveEmployeeaudit",
					"店员入驻店铺失败!!!");
		}
	}

	//店长店员审核拒绝(身份审核)
	public void siteAuditNotThrough(Integer isn, Long keyId, String refuseDesc, String memberId, String mobile) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String auditTime = dateFormat.format(date);
		Integer auditOpinion = 2;
		int mark = compInfoMapper.siteAuditNotThrough(isn, auditOpinion, keyId, refuseDesc, auditTime);
		if(mark == 0){
			throw new ServiceException("net.okdi.apiV1.service.impl.OutletsServiceImpl.siteAuditNotThrough",
					"站点审核不通过失败!!!");
		}
		redisService.removeAll("queryAllAudit");
		//删除缓存
		ehcacheService.removeAll("employeeCache");
		redisService.removeAll("nearCompInfoByCompNameCache");
		//清海峰缓存
		redisService.removeAll("verifyInfo-memberId-cache");
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		//身份认证推送
		sendNoticeService.sendPushSMSRemindIdentityRefuse(Long.valueOf(memberId), mobile,refuseDesc);
		//清除胡宣化的缓存
		redisService.removeAll("findShopowner1xxx");
		redisService.removeAll("findShopowner2xxx");
		redisService.removeAll("findShopowner3xxx");
	}

	//店长店员手机号
	public List queryMemberPhoneByMemberId(Long memberId) {
		List rlist = redisService.get("queryMemberPhoneByMemberId", memberId+"", List.class);
		if(PubMethod.isEmpty(rlist)){
			rlist = compInfoMapper.queryMemberPhoneByMemberId(memberId);
			if(!PubMethod.isEmpty(rlist)){
				redisService.put("queryMemberPhoneByMemberId", memberId+"", rlist);
			}
		}
		return rlist;
	}

	//店长店员审核通过(身份审核)
	public void siteAuditThrough(Long keyId, String remark, String memberId, String mobile) {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String auditTime = dateFormat.format(date);
		Integer isn = null;
		Integer auditOpinion = 1;
		int mark = compInfoMapper.siteAuditNotThrough(isn, auditOpinion, keyId, remark, auditTime);
		if(mark == 0){
			throw new ServiceException("net.okdi.apiV1.service.impl.OutletsServiceImpl.siteAuditNotThrough",
					"站点审核通过失败!!!");
		}
		redisService.removeAll("queryAllAudit");
		//删除缓存
		ehcacheService.removeAll("employeeCache");
		//删除缓存
		redisService.removeAll("nearCompInfoByCompNameCache");
		//清海峰缓存
		redisService.removeAll("verifyInfo-memberId-cache");
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		//清除胡宣化的缓存
		redisService.removeAll("findShopowner1xxx");
		redisService.removeAll("findShopowner2xxx");
		redisService.removeAll("findShopowner3xxx");
		//身份认证推送
		sendNoticeService.sendPushSMSRemindIdentitySuccess(Long.valueOf(memberId), mobile);
	}
	private String getShortUrl() {
		Map<String, String> map=new HashMap<>();
		map.put("sys", "1");
		map.put("text", lifeHttpUrl);
		String shortKey=noticeHttpClient.Post(shortLinkUrl, map);
		return longLinkUrl+shortKey;
	}
	@Override
	public Map<String, Object> queryAllAudit(String memberStatus,
			String currentPage, String pageSize, String memberName,
			String mobile, String idNum, String roleId,
			String registerStartTime, String registerEndTime, String provinceId) {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberStatus", memberStatus);
			map.put("currentPage", (Integer.parseInt(currentPage)-1)*(Integer.parseInt(pageSize)));
			map.put("pageSize", Integer.parseInt(pageSize));
			map.put("memberName", memberName);
			map.put("mobile", mobile);
			map.put("idNum", idNum);
			map.put("roleId", roleId);
			map.put("registerStartTime", registerStartTime);
			map.put("registerEndTime", registerEndTime);
			map.put("provinceId", provinceId);
			if(memberStatus == "" && memberName == "" && mobile == "" && idNum == "" 
					&& registerStartTime =="" && registerEndTime == ""){
				//接入缓存
				redisService.removeAll("queryAllAudit");
				Map<String,Object> redisMap = redisService.get("queryAllAudit", memberStatus+"-"+roleId, Map.class);
				if(PubMethod.isEmpty(redisMap)){
					List<Map<String, Object>> listAudit = memberInfoMapper.queryAllAudit(map);
					Map<String, Object> resultMap = new HashMap<String, Object>();
					Integer courierAuditCount = memberInfoMapper.queryAllAuditCount(map);
					resultMap.put("listAudit", listAudit);
					resultMap.put("total", courierAuditCount);
					if(courierAuditCount%Integer.parseInt(pageSize)>0){
						resultMap.put("totalPage", courierAuditCount/Integer.parseInt(pageSize)+1);			
					}else{
						resultMap.put("totalPage", courierAuditCount/Integer.parseInt(pageSize));			
					}
					redisService.put("queryAllAudit", memberStatus+"-"+roleId, resultMap);
					return resultMap;
				}else{
					return redisMap;
				}
			}else {
				List<Map<String, Object>> listAudit = memberInfoMapper.queryAllAudit(map);
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Integer courierAuditCount = memberInfoMapper.queryAllAuditCount(map);
				resultMap.put("listAudit", listAudit);
				resultMap.put("total", courierAuditCount);
				if(courierAuditCount%Integer.parseInt(pageSize)>0){
					resultMap.put("totalPage", courierAuditCount/Integer.parseInt(pageSize)+1);			
				}else{
					resultMap.put("totalPage", courierAuditCount/Integer.parseInt(pageSize));			
				}
				return resultMap;
			}
		
	}

	@Override
	public Map<String, Object> queryByMemberId(String memberId,String memStatus) {
		// TODO Auto-generated method stub
		Map<String, Object>map  = memberInfoMapper.queryByMemberId(memberId,memStatus);
		Map<String, Object>map2  = memberInfoMapper.queryByMemberId2(memberId,memStatus);
		if(map2==null){
			map.put("application_role_type", -20);
			map.put("comp_name", -20);
			map.put("audit_opinion2", -20);	
		}else{
			map.put("application_role_type", map2.get("application_role_type"));
			map.put("comp_name", map2.get("comp_name"));
			map.put("audit_opinion2", map2.get("audit_opinion"));	
		}
		map.put("meImgPath", meImgPath+memberId+".jpg");
		map.put("frontImgPath", frontImgPath+memberId+".jpg");
		map.put("inHandImgPath", inHandImgPath+memberId+".jpg");
 		map.put("audit_user", "");
		return map;
	}

	@Override
	public List<Map<String, Object>> queryImageByMemberId(String memberId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listImage = basCompimageMapper.queryImageByMemberId(memberId);
		return listImage;
	}

	@Override
	public Map<String, Object> queryAuditPeopleByMemberId(String memberId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = memberInfoMapper.queryAuditPeopleByMemberId(memberId);
		return map;
	}

	@Override
	public List<Map<String,Object>> exportExcelData(String memberStatus,
			String memberName, String mobile, String idNum, String roleId,
			String registerStartTime, String registerEndTime, String province) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberStatus", memberStatus);
		map.put("memberName", memberName);
		map.put("mobile", mobile);
		map.put("idNum", idNum);
		map.put("roleId", roleId);
		map.put("registerStartTime", registerStartTime);
		map.put("registerEndTime", registerEndTime);
		map.put("provinceId", province);
		List<Map<String,Object>> listMap = memberInfoMapper.queryAuditItemAll(map);
		//==============================================================================
		return listMap;
	}

	//归属审核的(店长审核店员)
	public String managerAauditShopAssistant(Long memberId, Long compId,
			String roleId,String moblie, String auditOpinion, Long auditUser) {
		int mark = 0;
		if(auditOpinion.equals("1")){//通过
			//推送
			sendNoticeService.sendPushSMSRemindSuccess(memberId, moblie);
			//修改审核表中审核通过
			compInfoMapper.updateEmployeeAudit(memberId,compId,roleId,auditOpinion, auditUser);
			//插入一条 relation 关系表
			long id = IdWorker.getIdWorker().nextId();
			mark = compInfoMapper.saveEmployeeRelation(id, memberId, compId, Long.valueOf(roleId));
			if(mark == 0){
				throw new ServiceException(
						"net.okdi.apiV1.service.impl.OutletsServiceImpl.managerAauditShopAssistant",
						"插入数据失败!!!");
			}
		}
		if(auditOpinion.equals("2")){//拒绝
			//推送
			sendNoticeService.sendPushSMSRemindRefuse(memberId, moblie);
			String roleType = "3";
			//同时删掉记录(归属的)
			String auditItem = "2";
			compInfoMapper.removeEmployeeAudit(memberId, compId, roleType, auditItem);
		}
		//清空缓存
		redisService.removeAll("queryOpenSiteDetailByCompId");
		//删除缓存
		ehcacheService.removeAll("employeeCache");
		//删除缓存
		ehcacheService.removeAll("memberInfoMobilechCache");
		redisService.removeAll("nearCompInfoByCompNameCache");
		//清海峰缓存
		redisService.removeAll("verifyInfo-memberId-cache");
		redisService.removeAll("relationInfo-memberId-cache");
		redisService.removeAll("expressInfo-memberId-cache");
		//清除胡宣化的缓存
		redisService.removeAll("findShopowner1xxx");
		redisService.removeAll("findShopowner2xxx");
		redisService.removeAll("findShopowner3xxx");
		return mark+"";
	}

	@Override
	public Map<String, Map<String, Object>> queryAttributionAuditState(
			String memberStatus, String memberName, String mobile,
			String idNum, String roleId, String registerStartTime,
			String registerEndTime, String province) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberStatus", memberStatus);
		map.put("memberName", memberName);
		map.put("mobile", mobile);
		map.put("idNum", idNum);
		map.put("roleId", roleId);
		map.put("registerStartTime", registerStartTime);
		map.put("registerEndTime", registerEndTime);
		map.put("provinceId", province);
		List<Map<String,Object>> listMap = memberInfoMapper.queryAttributionAuditState(map);
		Map<String,Map<String,Object>> twoMap = new HashMap<String,Map<String,Object>>();
		Map<String,Object> map1 = null;
		for(Map<String,Object> map2 : listMap){
			map1 = new HashMap<String,Object>();
			String member_id = map2.get("member_id")+"";
			String audit_opinion = map2.get("audit_opinion")+"";
			String application_role_type = map2.get("application_role_type")+"";
			map1.put("audit_opinion", audit_opinion);
			map1.put("application_role_type", application_role_type);
			twoMap.put(member_id, map1);
			map1 = null;
		}
		return twoMap;
	}
	
}
