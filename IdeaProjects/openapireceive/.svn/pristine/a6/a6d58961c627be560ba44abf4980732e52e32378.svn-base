package net.okdi.apiV2.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sun.util.logging.resources.logging;

import com.amssy.common.util.primarykey.IdWorker;
import com.rabbitmq.tools.Tracer.Logger;

import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.api.service.DicAddressService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.apiV1.dao.BasCompAuditMapperV1;
import net.okdi.apiV1.dao.BasCompBusinessMapperV1;
import net.okdi.apiV1.dao.BasCompInfoMapperV1;
import net.okdi.apiV1.dao.BasEmployeeRelationMapperV1;
import net.okdi.apiV2.dao.BasCompInfoMapperV2;
import net.okdi.apiV2.dao.BasEmployeeAuditMapperV2;
import net.okdi.apiV2.dao.CollectionMapper;
import net.okdi.apiV2.service.CollectionService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service
public class CollectionServiceImpl implements CollectionService {
	@Value("${meImgPath}")
	private String meImgPath;// 大头照
	@Value("${frontImgPath}")
	private String frontImgPath;// 身份证正面照
	@Value("${inHandImgPath}")
	private String inHandImgPath;// 手持身份证照片
	@Autowired
	private RedisService redisService;
	@Autowired
	private CollectionMapper collectionMapper;
	@Autowired
	private BasCompInfoMapperV1 compInfoMapper;

	@Autowired
	private SendNoticeService sendNoticeService;
	@Autowired
	private DicAddressService parseService;
	@Autowired
	private BasCompAuditMapperV1 basCompAuditMapper;
	@Autowired
	private BasEmployeeRelationMapperV1 basEmployeeRelationMapper;
	@Autowired
	private BasCompBusinessMapperV1 basCompBusinessMapper;
	@Autowired
	private BasEmployeeAuditMapperV2 basEmployeeAuditMapperV2;
	@Autowired
	private BasCompInfoMapperV2 basCompInfoMapperV2;//站点信息表
	public static final Log logger = LogFactory.getLog(CollectionServiceImpl.class);
	private Map<String ,Object>map = new HashMap<String,Object>();
	@Override
	public Map<String, Object> getMemberInfo(String memberId) {
		// TODO Auto-generated method stub
	//	map = this.redisService.get("getMemberInfoCollection", memberId, Map.class);
		//if(PubMethod.isEmpty(map)){	
			map = collectionMapper.getMeberInfoByMemberId(memberId);
			if (PubMethod.isEmpty(map)) {// 没有身份认证的
				map = new HashMap<String,Object>();
				map.put("memberName", null);
				map.put("idNum", null);
				map.put("auditId", null);
				map.put("compId", null);
				map.put("meImgPath", null);
				map.put("frontImgPath", null);
				map.put("inHandImgPath", null);
				map.put("memberId", memberId);
			} else {// 有身份认证的
				map.put("memberId", memberId);
				map.put("meImgPath", meImgPath + memberId + ".jpg");
				map.put("frontImgPath", frontImgPath + memberId + ".jpg");
				map.put("inHandImgPath", inHandImgPath + memberId + ".jpg");
			}
			//this.redisService.put("getMemberInfoCollection", memberId, Map.class);
		//}
		return map;
	}

	@Override
	public Map<String,Object> isHasCollection(String compName, String compAddress) {
		// TODO Auto-generated method stub
		map = collectionMapper.isHasCollection(compName, compAddress);
		if(PubMethod.isEmpty(map)){
			map = new HashMap<String,Object>();
			map.put("compId", null);
			map.put("type", 0);
		}else{
			Object obj = map.get("compId");
			Integer num = collectionMapper.findType(obj.toString());
			if(num==0){//店员
				map.put("type", 3);
			}else{//店长
				map.put("type", 2);
			}
		}
		return map;
	}

	@Override
	public String insertClerkAudit1(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		this.deleteKdInfo(memberId);//删除快递认证
		// TODO Auto-generated method stub
		Map<String, Object> Resultmap = parseService.parseAddrByList(latitude,
				longitude);
		Long addressId = Long.parseLong(String.valueOf(Resultmap
				.get("addressId")));
		Long id = IdWorker.getIdWorker().nextId();
		map = new HashMap<String, Object>();
		map.put("comp_id", id);// 公司id
		map.put("comp_name", compName);// 公司名称
		try {
			map.put("first_letter", PiyinUtil.getFullNameSpell(compName)
					.toUpperCase().subSequence(0, 1));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}// 公司名首字母
		map.put("regist_flag", 1);// 注册标志 0:未注册,1:已注册
		map.put("comp_type_num", 1040);// 公司分类代码
										// 1000:网络,1003:网络直营站点,1006:加盟公司,1008:加盟公司站点,1040:快递代理点,1050:营业分部

		map.put("comp_address_id", addressId);
		map.put("comp_address", compAddress);// 公司地址
		map.put("longitude", longitude);// 经度
		map.put("latitude", latitude);// 维度
		map.put("comp_status", 1);// 公司状态 -1:创建,0:提交待审核,1:审核通过,2:
									// 审核不通过4:再次提交待审核
		map.put("belong_to_net_id", 1501);// 快递加盟公司及站点所属快递网络I -1:代表此字段无意义
		map.put("belong_to_comp_id", -1); // 加盟公司站点所属加盟公司ID -1:代表此字段无意义
		// map.put("relation_comp_id", -1); // 加盟公司站点所属加盟公司ID -1:代表此字段无意义
		map.put("write_send_status", -1); // 快递加盟公司及站点收派业务状态
											// -1:此字段无意义,0:收+派,1:收,2:派
		map.put("goods_payment_status", -1); // 快递加盟公司及站点代收货款业务状态
												// -1:此字段无意义,0:收+派,1:收,2:派,3:不收不派
		map.put("create_time", new Date());
		map.put("comp_telephone", null);// 公司固定电话
		map.put("comp_mobile", null);// 公司手机号
		map.put("tao_goods_payment_status", -1);// 淘宝认证代收货款业务状态
												// -1:此字段无意义,0:收+派,1:收,2:派,3:不收不派
		map.put("login_flag", 2);// 登录标识
									// -2:未登录过,0:公司处于创建状态时登录过,1:公司处理待审核时登录过,2:审核通过后登录过
		try {
			compInfoMapper.insertBasCompinfo(map);
			
			/*
			 * 插入bas_compbusiness,公司业务表
			 */
			map= new HashMap<String,Object>();
			map.put("responsible_telephone", null);// 负责人手机号
			map.put("responsible", null);// 负责人
			map.put("comp_id", id);// 主键ID
			basCompBusinessMapper.insertBusiness(map);

			if (PubMethod.isEmpty(auditId)) {
				//this.collectionMapper.delAutidBymemberId(memberId);//删除一些未完善和拒绝的信息
				
				/*
				 * 插入bas_employeeaudit,归属审核表，待审核
				 */
//				map= new HashMap<String,Object>();
//				map.put("id", IdWorker.getIdWorker().nextId());
//				map.put("member_id", memberId);
//				map.put("audit_comp", id);
//				map.put("application_role_type", roleId);
//				map.put("application_time", new Date());
//				map.put("audit_item", 2);
//				map.put("audit_opinion", 0);
//				map.put("audit_desc", " (店员)创建代收点归属认证待审核");
//				basCompAuditMapper.insertbasCompAudit(map);
				//---2016年5月5日14:26:56--by hu.zhao...解决更换快递代收业务审核信息问题
				doGsInfo(memberId, roleId, id.toString());//处理归属
				doSfInfo(memberId, roleId, id.toString());//处理实名
				//---2016年5月5日14:27:00--by hu.zhao...解决更换快递代收业务审核信息问题
				/*
				 * 提交身份信息，状态为待认证状态
				 */
//				map= new HashMap<String,Object>();
//				map.put("id", IdWorker.getIdWorker().nextId());
//				map.put("member_id", memberId);
//				map.put("audit_comp", id);
//				map.put("application_role_type", roleId);
//				map.put("application_time", new Date());
//				map.put("audit_item", 1);
//				map.put("audit_opinion", 0);
//				map.put("audit_desc", "");
//				basCompAuditMapper.insertbasCompAudit(map);
				/*
				 * 更新member_info 表，将身份证号更新进去
				 */
				this.collectionMapper.updateIdentity(idNum, memberName, memberId);
				

			} else {
				/*
				 * 修改身份归属认证信息,状态不做处理
				 */
				map= new HashMap<String,Object>();
				map.put("auditId", auditId);
				map.put("audit_comp", id);
				map.put("roleId", roleId);
				map.put("audit_desc", "");
				this.collectionMapper.updateIdentityAudit(map);
				this.collectionMapper.updatebasemployeerelation(compId, roleId, memberId);
			}

		} catch (Exception e) {
			new ServiceException(
					"net.okdi.apiV2.service.impl.CollectionServiceImpl.insertClerkAudit1.001",
					"代收点添加失败!");
		}
		logger.info("店员提交代收业务(代收点不存在)！");
		this.redisService.removeAll("findShopowner1xxx");
		this.redisService.removeAll("findShopowner2xxx");
		this.redisService.removeAll("findShopowner3xxx");
		this.redisService.remove("findIdentity", idNum);
		this.redisService.remove("getMemberInfoCollection", memberId);
		return id.toString();
	}


	@Override
	public String insertClerkAudit2(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		// TODO Auto-generated method stub
		this.deleteKdInfo(memberId);//删除快递认证
        //查询有没有店长，有的话，发短信，没有就算了
		map= new HashMap<String,Object>();
		map = this.collectionMapper.findShopkeeperByCompId(compId);
		if(!PubMethod.isEmpty(map)){
			//店员申请入驻（代收站）给店长发送推送
			Long managerId =   (Long)map.get("shopkeepermemberId");
			Object mobile = map.get("mobile");
			sendNoticeService.sendPushSMSManagerSuccess(managerId, (String)mobile);
		}
		
		/*
		 * 插入bas_employeeaudit,归属审核表，待审核
		 */
//		map = new HashMap<String, Object>();
//		map= new HashMap<String,Object>();
//		map.put("id", IdWorker.getIdWorker().nextId());
//		map.put("member_id", memberId);
//		map.put("audit_comp", compId);
//		map.put("application_role_type", roleId);
//		map.put("application_time", new Date());
//		map.put("audit_item", 2);
//		map.put("audit_opinion", 0);
//		map.put("audit_desc", " 存在代收点(店员)归属认待审核");
//		basCompAuditMapper.insertbasCompAudit(map);
		//---2016年5月5日14:26:56--by hu.zhao...解决更换快递代收业务审核信息问题
		doGsInfo(memberId, roleId, compId);//处理归属
		//---2016年5月5日14:27:00--by hu.zhao...解决更换快递代收业务审核信息问题
		if (PubMethod.isEmpty(auditId)) {
			//this.collectionMapper.delAutidBymemberId(memberId);
//			/*
//			 * (以前未提交身份认证的)身份认证等待认证
//			 */
//			map= new HashMap<String,Object>();
//			map.put("id", IdWorker.getIdWorker().nextId());
//			map.put("member_id", memberId);
//			map.put("audit_comp", compId);
//			map.put("application_role_type", roleId);
//			map.put("application_time", new Date());
//			map.put("audit_item", 1);
//			map.put("audit_opinion", 0);
//			map.put("audit_desc", "");
//			basCompAuditMapper.insertbasCompAudit(map);
			//---2016年5月5日14:26:56--by hu.zhao...解决更换快递代收业务审核信息问题
			doSfInfo(memberId, roleId, compId);//处理实名
			//---2016年5月5日14:27:00--by hu.zhao...解决更换快递代收业务审核信息问题
			/*
			 * 更新member_info 表，将身份证号更新进去
			 */
			this.collectionMapper.updateIdentity(idNum, memberName, memberId);

		} else {
			/*
			 * (店员)更新身份归属认证
			 */
			map= new HashMap<String,Object>();
			map.put("auditId", auditId);
			map.put("audit_comp", compId);
			map.put("roleId", roleId);
			map.put("audit_opinion", 0);
			map.put("audit_desc", "");
			this.collectionMapper.updateIdentityAudit(map);
			this.collectionMapper.updateIdentityAudit2(map);//更新归属
			this.collectionMapper.updatebasemployeerelation(compId, roleId, memberId);
		}
		this.redisService.removeAll("findShopowner1xxx");
		this.redisService.removeAll("findShopowner2xxx");
		this.redisService.removeAll("findShopowner3xxx");
		this.redisService.remove("findIdentity", idNum);
		this.redisService.remove("getMemberInfoCollection", memberId);
		return compId;
	}

	@Override
	public String insertShopkeeperAudit1(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String phone) {
		// TODO Auto-generated method stub
		this.deleteKdInfo(memberId);//删除快递认证
		Map<String, Object> Resultmap = parseService.parseAddrByList(latitude,
				longitude);
		Long addressId = Long.parseLong(String.valueOf(Resultmap
				.get("addressId")));
		Long id = IdWorker.getIdWorker().nextId();
		map = new HashMap<String, Object>();
		map.put("comp_id", id);// 公司id
		map.put("comp_name", compName);// 公司名称
		try {
			map.put("first_letter", PiyinUtil.getFullNameSpell(compName)
					.toUpperCase().subSequence(0, 1));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}// 公司名首字母
		map.put("regist_flag", 1);// 注册标志 0:未注册,1:已注册
		map.put("comp_type_num", 1040);// 公司分类代码
										// 1000:网络,1003:网络直营站点,1006:加盟公司,1008:加盟公司站点,1040:快递代理点,1050:营业分部

		map.put("comp_address_id", addressId);
		map.put("comp_address", compAddress);// 公司地址
		map.put("longitude", longitude);// 经度
		map.put("latitude", latitude);// 维度
		map.put("comp_status", 1);// 公司状态 -1:创建,0:提交待审核,1:审核通过,2:
									// 审核不通过4:再次提交待审核
//		map.put("belong_to_net_id", 1501);// 快递加盟公司及站点所属快递网络I -1:代表此字段无意义
		map.put("belong_to_comp_id", -1); // 加盟公司站点所属加盟公司ID -1:代表此字段无意义
		// map.put("relation_comp_id", -1); // 加盟公司站点所属加盟公司ID -1:代表此字段无意义
		map.put("write_send_status", -1); // 快递加盟公司及站点收派业务状态
											// -1:此字段无意义,0:收+派,1:收,2:派
		map.put("goods_payment_status", -1); // 快递加盟公司及站点代收货款业务状态
												// -1:此字段无意义,0:收+派,1:收,2:派,3:不收不派
		map.put("create_time", new Date());
		map.put("comp_telephone", null);// 公司固定电话
		map.put("comp_mobile", null);// 公司手机号
		map.put("tao_goods_payment_status", -1);// 淘宝认证代收货款业务状态
												// -1:此字段无意义,0:收+派,1:收,2:派,3:不收不派
		map.put("login_flag", 2);// 登录标识
									// -2:未登录过,0:公司处于创建状态时登录过,1:公司处理待审核时登录过,2:审核通过后登录过
		try {
			compInfoMapper.insertBasCompinfo(map);
			/*
			 * 删除原有的关系业务
			 */
			//this.collectionMapper.delBascompbusiness(memberId);
			/*
			 * 插入bas_compbusiness,公司业务表
			 */
			map= new HashMap<String,Object>();
			map.put("responsible_telephone", phone);// 负责人手机号
			map.put("responsible", memberName);// 负责人
			map.put("comp_id", id);// 主键ID
			basCompBusinessMapper.insertBusiness(map);
			//this.collectionMapper.Delbasemployeerelation(memberId);//删除关系表
 			if (PubMethod.isEmpty(auditId)) {
 				
 				/*
 				 * 插入bas_employee_relation，人员公司关系表
 				 */
 				map= new HashMap<String,Object>();
 				map.put("id", IdWorker.getIdWorker().nextId());
 				map.put("member_id", memberId);
 				map.put("comp_id", id);
 				map.put("role_id", roleId);
 				map.put("area_color", "#c2c2c2");
 				map.put("employee_work_status_flag", 1);
 				map.put("review_task_receiving_flag", 1);
 				basEmployeeRelationMapper.insertBasEmployeeRelation(map);
 				
				//this.collectionMapper.delAutidBymemberId(memberId);
				/*
				 * 插入bas_employeeaudit,归属审核表，直接通过
				 */
//				map= new HashMap<String,Object>();
//				map.put("id", IdWorker.getIdWorker().nextId());
//				map.put("member_id", memberId);
//				map.put("audit_comp", id);
//				map.put("application_role_type", roleId);
//				map.put("application_time", new Date());
//				map.put("audit_item", 2);
//				map.put("audit_opinion", 1);
//				map.put("audit_desc", " (店长)创建代收点归属认证默认通过");
//				basCompAuditMapper.insertbasCompAudit(map);
//				/*
//				 * 提交身份信息，状态为待认证状态
//				 */
//				map= new HashMap<String,Object>();
//				map.put("id", IdWorker.getIdWorker().nextId());
//				map.put("member_id", memberId);
//				map.put("audit_comp", id);
//				map.put("application_role_type", roleId);
//				map.put("application_time", new Date());
//				map.put("audit_item", 1);
//				map.put("audit_opinion", 0);
//				map.put("audit_desc", "");
//				basCompAuditMapper.insertbasCompAudit(map);
 				doGsInfo(memberId, roleId, id.toString());//处理归属
				doSfInfo(memberId, roleId, id.toString());//处理实名
				/*
				 * 更新member_info 表，将身份证号更新进去
				 */
				this.collectionMapper.updateIdentity(idNum, memberName, memberId);

			} else {
				/*
				 * 修改身份认证信息,状态不做处理
				 */
				map= new HashMap<String,Object>();
				map.put("auditId", auditId);
				map.put("audit_comp", id);
				map.put("roleId", roleId);
				map.put("audit_desc", "");
				this.collectionMapper.updateIdentityAudit(map);
				this.collectionMapper.updatebasemployeerelation(compId, roleId, memberId);

			}
			

		} catch (Exception e) {
			new ServiceException(
					"net.okdi.apiV2.service.impl.CollectionServiceImpl.insertClerkAudit1.001",
					"代收点添加失败!");
		}
		this.redisService.removeAll("findShopowner1xxx");
		this.redisService.removeAll("findShopowner2xxx");
		this.redisService.removeAll("findShopowner3xxx");
		this.redisService.remove("findIdentity", idNum);
		this.redisService.remove("getMemberInfoCollection", memberId);
		return id.toString();
	}

	@Override
	public Map<String,Object> insertShopkeeperAudit2(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		// TODO Auto-generated method stub
		this.deleteKdInfo(memberId);//删除快递认证
		//查看是否有店长入驻，有的话，退出，没有的话插入
		map = new HashMap<String,Object>();
		map = this.collectionMapper.findShopkeeperByCompId(compId);
		if(PubMethod.isEmpty(map)){//那就直接入驻
			try {
				
				/*
				 * 把该店铺下的所有店员统一通过归宿审核
				 */
				  
				String phone = this.collectionMapper.selectPhoneByMemberId(memberId);
				this.collectionMapper.delBascompbusiness(memberId,compId);
				/*
				 * 插入bas_compbusiness,公司业务表
				 */
				map= new HashMap<String,Object>();
				map.put("responsible_telephone", phone);// 负责人手机号
				map.put("responsible", memberName);// 负责人
				map.put("comp_id", compId);// 主键ID
				basCompBusinessMapper.insertBusiness(map);
				/*
				 * 插入bas_employee_relation，人员公司关系表
				 */
				map= new HashMap<String,Object>();
				map.put("id", IdWorker.getIdWorker().nextId());
				map.put("member_id", memberId);
				map.put("comp_id", compId);
				map.put("role_id", roleId);
				map.put("area_color", "#c2c2c2");
				map.put("employee_work_status_flag", 1);
				map.put("review_task_receiving_flag", 1);
				basEmployeeRelationMapper.insertBasEmployeeRelation(map);
				/*
				 * 插入bas_employeeaudit,归属审核表，直接通过
				 */
//				map= new HashMap<String,Object>();
//				map.put("id", IdWorker.getIdWorker().nextId());
//				map.put("member_id", memberId);
//				map.put("audit_comp", compId);
//				map.put("application_role_type", roleId);
//				map.put("application_time", new Date());
//				map.put("audit_item", 2);
//				map.put("audit_opinion", 1);
//				map.put("audit_desc", " (店长)归属认证默认通过");
//				basCompAuditMapper.insertbasCompAudit(map);
				doGsInfo(memberId, roleId, compId);//处理归属
				if (PubMethod.isEmpty(auditId)) {
					//this.collectionMapper.delAutidBymemberId(memberId);

					/*
					 * 提交身份信息，状态为待认证状态
					 */
//					map= new HashMap<String,Object>();
//					map.put("id", IdWorker.getIdWorker().nextId());
//					map.put("member_id", memberId);
//					map.put("audit_comp", compId);
//					map.put("application_role_type", roleId);
//					map.put("application_time", new Date());
//					map.put("audit_item", 1);
//					map.put("audit_opinion", 0);
//					map.put("audit_desc", "");
//					basCompAuditMapper.insertbasCompAudit(map);
					doSfInfo(memberId, roleId, compId);//处理身份
					/*
					 * 更新member_info 表，将身份证号更新进去
					 */
					this.collectionMapper.updateIdentity(idNum, memberName, memberId);

				} else {
					/*
					 * 修改身份认证信息,状态不做处理
					 */
					map= new HashMap<String,Object>();
					map.put("auditId", auditId);
					map.put("audit_comp", compId);
					map.put("roleId", roleId);
					map.put("audit_opinion", 1);//被另一个店拒绝的店员，申请店长，归属默认通过
					map.put("audit_desc", "");
					this.collectionMapper.updateIdentityAudit(map);
					this.collectionMapper.updateIdentityAudit2(map);
				}

			} catch (Exception e) {
				new ServiceException(
						"net.okdi.apiV2.service.impl.CollectionServiceImpl.insertClerkAudit1.001",
						"代收点添加失败!");
			}
			map= new HashMap<String,Object>();
			map.put("compId", compId);
			map.put("isInsert", 1);//插入成功
		}else{//提示，已经有boss了,不能入驻
			map= new HashMap<String,Object>();
			map.put("compId", compId);
			map.put("isInsert", 0);//代收点已经有店长了
		}
		this.redisService.removeAll("findShopowner1xxx");
		this.redisService.removeAll("findShopowner2xxx");
		this.redisService.removeAll("findShopowner3xxx");
		this.redisService.remove("findIdentity", idNum);
		this.redisService.remove("getMemberInfoCollection", memberId);
		return map;
	}

	@Override
	public Map<String, Object> findIdentity(String idNum) {
		// TODO Auto-generated method stub
			map = this.collectionMapper.findIdentity(idNum);
			if(PubMethod.isEmpty(map)){
				map = new HashMap<String, Object>();
				map.put("memberId", 0);
			}
		
		return map;
	}
	
	
	
	/**
	 * @MethodName: net.okdi.apiV2.service.impl.CollectionServiceImpl.java.deleteKdInfo 
	 * @Description: TODO(如果是快递业务转到代收业务，删除生成的快递认证审核信息) 
	 * @param @param compId
	 * @param @param memberId
	 * @param @param roleType   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-4-27
	 * @auth zhaohu
	 */
	public void deleteKdInfo(String memberId) {
		basEmployeeAuditMapperV2.deleteKdAuditInfo(memberId);
		logger.info("快递业务转代收业务，删除快递认证信息！业务转来转去，可耻！不嫌烦吗？memberId="+memberId);
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
	/**
	 * @MethodName: net.okdi.apiV2.service.impl.CollectionServiceImpl.java.doSfInfo 
	 * @Description: TODO(如果存在归属信息，则更新;else 保存) 
	 * @param @param memberId
	 * @param @param roleId
	 * @param @param string   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2016-5-5
	 * @auth zhaohu
	 */
	private void doSfInfo(String memberId, String roleId, String compId) {
		int sf = this.compInfoMapper.queryCountSF(memberId);
		if(sf > 0){
			basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(memberId),Long.parseLong(compId),Integer.valueOf(roleId));
		}else {
			basCompInfoMapperV2.saveSfAuditInfo(IdWorker.getIdWorker().nextId(), Long.parseLong(memberId), Long.parseLong(roleId), Long.parseLong(compId),1,0, new Date());//插入身份审核
		}
	}
	
	
}
