package net.okdi.apiV1.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.DicAddressMapper;
import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddress;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.service.SendNoticeService;
import net.okdi.apiV1.dao.BasEmployeeAuditMapperV1;
import net.okdi.apiV1.dao.BasEmployeeRelationMapperV1;
import net.okdi.apiV1.dao.BasNetInfoMapperV1;
import net.okdi.apiV1.dao.MemberInfoMapperV1;
import net.okdi.apiV1.service.ExpressUserService;
import net.okdi.apiV1.vo.VoUserAudit;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class ExpressUserServiceImpl implements ExpressUserService {
    
	public static final Log logger = LogFactory.getLog(ExpressUserServiceImpl.class);
	
	@Autowired
	BasEmployeeAuditMapperV1 basEmployeeAuditMapperV1;	//快递员审核表
	@Autowired
	MemberInfoMapperV1 memberInfoMapperV1;//人员信息表
	@Autowired
	BasEmployeeRelationMapperV1 basEmployeeRelationMapperV1;//人员-站点关系表
	@Autowired
	BasNetInfoMapperV1 basNetInfoMapperV1;//快递网络信息表
	@Autowired
	DicAddressMapper dicAddressMapper;//省份信息
	@Autowired
	private RedisService redisService;
	@Autowired
	private SendNoticeService sendNoticeService;//短信推送
	@Value("${meImgPath}")
	public String headPath;
	@Value("${frontImgPath}")
	public String frontPath;
	@Value("${backImgPath}")
	public String backPath;
	@Value("${inHandImgPath}")
	public String inHandPath;
	@Value("${headImgPath}")
	public String headImgPath;
	
	@Autowired
	private NoticeHttpClient noticeHttpClient;
	
	/**
	 * 注册--修改人员信息和审核信息--姓名、身份账号、照片
	 */
	@Override
	public void updateMemberInfoAndAudit(String memberName, String idNum,String memberId,String compId,String roleType) {
		if (PubMethod.isEmpty(memberName)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.updateMemberInfoAndAudit.001", "memberName参数异常");
		}else if (PubMethod.isEmpty(idNum)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.updateMemberInfoAndAudit.002", "idNum参数异常");
		}else if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.updateMemberInfoAndAudit.003", "memberId参数异常");
		}else if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.updateMemberInfoAndAudit.004", "compId参数异常");
		}else if (PubMethod.isEmpty(roleType)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.updateMemberInfoAndAudit.005", "roleType参数异常");
		}
		//修改人员基本信息
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("memberName", memberName);
		param.put("idNum", idNum);
		param.put("memberId", memberId);
		param.put("compId", compId);
		param.put("roleType", roleType);
		//修改名字、身份证号码
		memberInfoMapperV1.updateMemberInfo(param);
		//通过memberid去查询身份审核 如果没有 新建数据--有的话讲-1置成0待审核
		List<BasEmployeeAudit> audit = basEmployeeAuditMapperV1.queryShenFenAuditInfo(param);
		if (audit!=null&&audit.size()>0) {
			basEmployeeAuditMapperV1.updateAuditShenFen(param);
		}else {
			//保存审核信息==身份认证
			BasEmployeeAudit auditShenfen = new BasEmployeeAudit();
			auditShenfen.setId(IdWorker.getIdWorker().nextId());
			auditShenfen.setMemberId(Long.valueOf(memberId));
			auditShenfen.setAuditItem((short) 1);		//1:身份审核,2:归属审核
			auditShenfen.setAuditOpinion((short) 0);	//1:审核通过,2:审核未通过 0待审核
			auditShenfen.setAuditComp(Long.valueOf(compId));
			auditShenfen.setApplicationRoleType(Short.valueOf(roleType));//1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
			basEmployeeAuditMapperV1.insertAuditInfo(auditShenfen);//身份
		}
		//清理缓存--实名认证
		this.redisService.remove("verifyInfo-memberId-cache", String.valueOf(memberId));
		this.redisService.remove("findShopowner1xxx", memberId+roleType);
		this.redisService.remove("findShopowner2xxx", memberId+roleType);
		this.redisService.remove("findShopowner3xxx", memberId+roleType);
		this.redisService.removeAll("memberInfo-mobile-cache");
		this.redisService.remove("employeeCache", compId);//清理人员列表缓存--名字改变
	}

	/**
	 * 跳过身份认证
	 */
	@Override
	public void updateMemberInfoAndAuditJump(String memberId,String compId,String roleType,String memberName) {
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("memberId", memberId);
		param.put("compId", compId);
		param.put("roleType", roleType);
		//通过memberid去查询身份审核 如果没有 新建数据--有的话讲-1置成0待审核
		List<BasEmployeeAudit> audit = basEmployeeAuditMapperV1.queryShenFenAuditInfo(param);
		if (audit!=null&&audit.size()>0) {
			basEmployeeAuditMapperV1.updateAuditShenFen2(param);
		}else {
			//保存审核信息==未完善 身份认证
			BasEmployeeAudit auditShenfen = new BasEmployeeAudit();
			auditShenfen.setId(IdWorker.getIdWorker().nextId());
			auditShenfen.setMemberId(Long.valueOf(memberId));
			auditShenfen.setAuditItem((short) 1);		//1:身份审核,2:归属审核
			auditShenfen.setAuditOpinion((short) -1);	//1:审核通过,2:审核未通过 0待审核 -1未完善
			auditShenfen.setAuditComp(Long.valueOf(compId));
			auditShenfen.setApplicationRoleType(Short.valueOf(roleType));//1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
			basEmployeeAuditMapperV1.insertAuditInfo(auditShenfen);//身份
		}
		if(!PubMethod.isEmpty(memberName)){
			HashMap<String,String> memberParam = new HashMap<String,String>();
			memberParam.put("memberName", memberName);
			memberParam.put("memberId", memberId);
			//修改名字、身份证号码
			memberInfoMapperV1.updateMemberInfo(memberParam);
		}
		//清理缓存--实名认证
		this.redisService.remove("verifyInfo-memberId-cache", String.valueOf(memberId));
		this.redisService.remove("findShopowner1xxx", memberId+roleType);
		this.redisService.remove("findShopowner2xxx", memberId+roleType);
		this.redisService.remove("findShopowner3xxx", memberId+roleType);
		this.redisService.removeAll("memberInfo-mobile-cache");
	}
	/**
	 * 收派员审核列表信息
	 */
	@Override
	public Page queryUserAuditList(Integer auditStatus,
			String memberName, String memberPhone, String idNum, String roleId,
			String netId, String startTime,String endTime,Integer currentPage,Integer pageSize,String realNameStatus,String expressStatus,String provinceId,String sureStatus ,String auditItem) {
		if (PubMethod.isEmpty(auditStatus)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.queryUserAuditList.001", "auditStatus参数异常");
		}
		if (PubMethod.isEmpty(currentPage)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.queryUserAuditList.002", "currentPage参数异常");
		}
		if (PubMethod.isEmpty(pageSize)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.queryUserAuditList.003", "pageSize参数异常");
		}
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		
		if (realNameStatus == "") {
			realNameStatus = null;
		}
		if (expressStatus == "") {
			expressStatus = null;
		}
		if (sureStatus == "") {
			sureStatus = null;
		}
		HashMap<String, Object> params = new HashMap<>();
		params.put("auditStatus", auditStatus);
		params.put("memberName", memberName);
		params.put("memberPhone", memberPhone);
		params.put("idNum", idNum);
		params.put("roleId", roleId);
		params.put("netId", netId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("realNameStatus", realNameStatus);
		params.put("expressStatus", expressStatus);
		params.put("sureStatus", sureStatus);
		params.put("provinceId", provinceId);
		params.put("auditItem", auditItem.toString());
		params.put("page", page);
		List<VoUserAudit> list = memberInfoMapperV1.queryUserAuditList(params);
		List<VoUserAudit> newList = new ArrayList<>();
		for (VoUserAudit vo : list) {
			String phone = vo.getMemberPhone();
			String phoneStr = "";//15088882211
			if (phone!=null && !"".equals(phone) && phone.getBytes().length == 11) {
				phoneStr = phone.substring(0, 3)+"***"+phone.substring(6);//加密手机号
			}
			VoUserAudit voo = new VoUserAudit();
			BeanUtils.copyProperties(vo, voo);
			voo.setMemberPhone(phoneStr);
			voo.setMemberPhoneReal(phone);
			
			newList.add(voo);
		}
		Integer count = memberInfoMapperV1.queryCount(params);
		page.setItems(newList);
		page.setTotal(count);
		return page;
	}

	/**
	 * 收派员审核信息详情--身份、归属认证、快递员认证
	 */
	@Override
	public HashMap<String, Object> queryUserDetail(String memberId, String compId) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.queryUserDetail.001", "memberId参数异常");
		}
//		if (PubMethod.isEmpty(compId)) {
//			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.queryUserDetail.002", "compId参数异常");
//		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		//身份验证 信息
		MemberInfo info = memberInfoMapperV1.selectByPrimaryKey(memberId);
		if (info != null) {
			resultMap.put("memberName", info.getMemberName());
			resultMap.put("memberPhone", info.getMemberPhone());
			resultMap.put("idNum", info.getIdNum());
		}else {
			resultMap.put("memberName", "");
			resultMap.put("memberPhone", "");
			resultMap.put("idNum", "");
		}
		resultMap.put("headPic", headPath+memberId+".jpg");
		resultMap.put("frontPic", frontPath+memberId+".jpg");
		resultMap.put("inHandPic", inHandPath+memberId+".jpg");
		
		//归属认证信息
		if (compId!=null && compId != "") {
			HashMap<String, Object> compMap = memberInfoMapperV1.queryCompInfo(compId);
			if (compMap!=null) {
				resultMap.put("netName", compMap.get("netName"));
				resultMap.put("compName", compMap.get("compName"));
				if ("未标记站点".equals(compMap.get("compName"))) {
					Map map = memberInfoMapperV1.querySiteNameByMemberId(memberId);
					if (!PubMethod.isEmpty(map)) {
						if (!PubMethod.isEmpty(map.get("comp_name"))) {
							resultMap.put("compName", map.get("comp_name"));
						}else {
							resultMap.put("compName", "");
						}
					}else {
						resultMap.put("compName", "");
					}
				}
				resultMap.put("compStatus", compMap.get("compStatus"));//站点认证状态
				resultMap.put("compId", compMap.get("compId"));
				resultMap.put("netId", compMap.get("netId"));
			}
			else {
				resultMap.put("netName", "");
				resultMap.put("compName", "");
				resultMap.put("compStatus", "");
				resultMap.put("compId", "");
				resultMap.put("netId", "");
			}
		}else {
			resultMap.put("netName", "");
			resultMap.put("compName", "");
			resultMap.put("compStatus", "");
			resultMap.put("compId", "");
			resultMap.put("netId", "");
		}
		BasEmployeeAudit memberMapSF = basEmployeeAuditMapperV1.queryRealNameStatus(memberId);//收派员身份认证状态
		if (memberMapSF!=null) {
			resultMap.put("memberStatusSF", memberMapSF.getAuditOpinion());
			resultMap.put("roleType", memberMapSF.getApplicationRoleType());
		}else {
			resultMap.put("memberStatusSF", "");
			resultMap.put("roleType", "");
		}
		BasEmployeeAudit memberMapKD = basEmployeeAuditMapperV1.queryExpressStatus(memberId);//快递员认证状态
		if (memberMapKD!=null) {
			resultMap.put("memberStatusKD", memberMapKD.getAuditOpinion());
			resultMap.put("expressNum", memberMapKD.getApplicationDesc());////查询快的认证 的快递单号
		}else {
			resultMap.put("memberStatusKD", "");
			resultMap.put("expressNum", "");
		}
		HashMap<String, Object> memberMap = memberInfoMapperV1.queryMemberinfo(memberId);//收派员归属认证信息
		if (memberMap!=null) {
			resultMap.put("memberStatus", memberMap.get("audit_opinion"));//归属认证状态
			resultMap.put("roleType", memberMap.get("application_role_type"));
			resultMap.put("memberId", memberMap.get("member_id"));
		}else {
			resultMap.put("memberStatus", "");
			resultMap.put("memberId", memberId);
		}
		//----2015年11月21日18:16:02  客服确认快递员通过------//
		BasEmployeeAudit sureExpress = basEmployeeAuditMapperV1.querySureExpress(memberId);//客服确认快递员通过----4
		if (sureExpress!=null) {//已存在
			resultMap.put("sureStatus", PubMethod.isEmpty(sureExpress.getAuditOpinion()) ? "" : sureExpress.getAuditOpinion());
		}else {
			resultMap.put("sureStatus", "");
		}
		return resultMap;
	}

	/**
	 * 验证身份证号是否重复注册
	 */
	@Override
	public HashMap<String, Object> isIdNumRepeat(String idNum,String memberPhone) {
		if (PubMethod.isEmpty(idNum)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.isIdNumRepeat.001", "idNum参数异常");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<MemberInfo> list = memberInfoMapperV1.isIdNumRepeat(idNum,memberPhone);
		if (list!=null && list.size()>0) {
			//重复
			resultMap.put("yesOrNo", "yes");
			List<String> num = new ArrayList<String>();
			for (MemberInfo info : list) {
				if (info!=null) {
					num.add(info.getMemberPhone());
				}
			}
			resultMap.put("phones", num);
		}else {
			resultMap.put("phones", new ArrayList<>());
			resultMap.put("yesOrNo", "no");
		}
		
		return resultMap;
	}

	/**
	 * 归属认证审核
	 */
	@Override
	public void guishuAudit(String memberId,String status) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.guishuAudit.001", "memberId参数异常");
		}
		if (PubMethod.isEmpty(status)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.guishuAudit.002", "status参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("status", status);
		basEmployeeAuditMapperV1.guishuAudit(param);
		
	}

	/**
	 * 运营平台审核收派员
	 */
	@Override
	public HashMap<String, Object> checkMemberInfo(String memberId, String compId, String status,String remark,String reasonNum,String auditType,String mob) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.checkMemberInfo.001", "memberId参数异常");
		}
		if (PubMethod.isEmpty(status)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.checkMemberInfo.002", "status参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("compId", compId);
		param.put("status", status);
		param.put("remark", remark);
		param.put("reasonNum", reasonNum);
		param.put("auditTime", new Date());
		/**
		 * reasonNum：1图片模糊不清2经人工电话核实，与实际情况不符3其他		auditType：1实名认证3快递员认证	status：1通过2拒绝
		 */
	//实名认证审核
		if ("1".equals(auditType)) {
			if ("1".equals(status)) {
				//审核通过---改audit状态为1-且存备注信息
				basEmployeeAuditMapperV1.passCheckSM(param);
				//推送
				sendNoticeService.passRealNameCheck(Long.valueOf(memberId), mob);
			}else if ("2".equals(status)) {
				//审核拒绝---改audit状态为2-存备注，存拒绝num
				basEmployeeAuditMapperV1.refuseCheckSM(param);
				//推送
				sendNoticeService.refuseRealNameCheck(Long.valueOf(memberId), mob);
			}
			//清理缓存--实名认证
			this.redisService.remove("verifyInfo-memberId-cache", String.valueOf(memberId));
	//快递员认证审核
		}else if ("3".equals(auditType)) {
			if ("1".equals(status)) {
				//审核通过---改audit状态为1-且存备注信息
				basEmployeeAuditMapperV1.passCheckKD(param);
				//推送
				sendNoticeService.passExpressCheck(Long.valueOf(memberId), mob);
			}else if ("2".equals(status)) {
				//审核拒绝---改audit状态为2-存备注，存拒绝num
				basEmployeeAuditMapperV1.refuseCheckKD(param);
				//推送
				sendNoticeService.refuseExpressCheck(Long.valueOf(memberId), mob,remark);
			}
			//清理缓存-快递认证
			this.redisService.remove("expressInfo-memberId-cache", String.valueOf(memberId));
		}
		
		this.redisService.removeAll("findShopowner1xxx");
		this.redisService.removeAll("findShopowner2xxx");
		this.redisService.removeAll("findShopowner3xxx");
		
		//审核完 返回 两个认证状态
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		BasEmployeeAudit audit1 = basEmployeeAuditMapperV1.queryRealNameStatus(memberId);
		if (audit1!=null) {
			resultMap.put("realNameStatus", audit1.getAuditOpinion());
		}
		BasEmployeeAudit audit2 = basEmployeeAuditMapperV1.queryExpressStatus(memberId);
		if (audit2!=null) {
			resultMap.put("expressStatus", audit2.getAuditOpinion());
		}
		return resultMap;
	}

	/**
	 * 获取快递网络信息
	 */
	@Override
	public List<HashMap<String, String>> queryNetInfo() {
		List<BasNetInfo> list = basNetInfoMapperV1.queryNetInfo();
		List<HashMap<String, String>> list2 = new ArrayList<HashMap<String, String>>();
		for (BasNetInfo info : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("netId", info.getNetId().toString());
			map.put("netName", info.getNetName().toString());
			list2.add(map);
		}
		return list2;
	}
	/**
	 * 获取省份信息
	 */
	@Override
	public List<HashMap<String, String>> queryProvinceInfo() {
		List<DicAddress> list = dicAddressMapper.queryProvinceInfo();
		List<HashMap<String, String>> list2 = new ArrayList<HashMap<String, String>>();
		for (DicAddress info : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("provinceId", info.getAddressId().toString());
			map.put("provinceName", info.getAddressName().toString());
			list2.add(map);
		}
		return list2;
	}
	
	/**
	 * 快递认证
	 */
	@Override
	public void saveExpressAudit(String memberId, String expressNumStr,String compId,String roleType) {
		logger.info("saveExpressAudit开始执行！！！！！！！！！！！");
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.saveExpressAudit.001", "memberId参数异常");
		}
		if (PubMethod.isEmpty(expressNumStr)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.saveExpressAudit.002", "expressNumStr参数异常");
		}
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.saveExpressAudit.003", "compId参数异常");
		}
		if (PubMethod.isEmpty(roleType)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.saveExpressAudit.004", "roleType参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("expressNumStr", expressNumStr);
		param.put("id", IdWorker.getIdWorker().nextId());
		param.put("compId", compId);
		param.put("roleType", roleType);
		BasEmployeeAudit expressStatus = basEmployeeAuditMapperV1.queryExpressStatus(memberId);
		//已存在 update--0待审核 不存在 save 0
		if (expressStatus!=null) {
			logger.info("exptress不为空");
			basEmployeeAuditMapperV1.updateExpressAudit(param);//0
		}else {
			logger.info("exptress为空");
			basEmployeeAuditMapperV1.saveExpressAudit(param);//0
		}
		//清理缓存-快递认证
		this.redisService.remove("expressInfo-memberId-cache", String.valueOf(memberId));
		this.redisService.remove("findShopowner1xxx", memberId+roleType);
		this.redisService.remove("findShopowner2xxx", memberId+roleType);
		this.redisService.remove("findShopowner3xxx", memberId+roleType);
	}
	
	/**
	 * 快递认证跳过
	 */
	@Override
	public void saveExpressAuditJump(String memberId, String compId,String roleType) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.saveExpressAuditJump.001", "memberId参数异常");
		}
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.saveExpressAuditJump.002", "compId参数异常");
		}
		if (PubMethod.isEmpty(roleType)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.saveExpressAuditJump.003", "roleType参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("id", IdWorker.getIdWorker().nextId());
		param.put("compId", compId);
		param.put("roleType", roleType);
		BasEmployeeAudit expressStatus = basEmployeeAuditMapperV1.queryExpressStatus(memberId);
		//存在update-1 不存在save -1
		if (expressStatus!=null) {
			basEmployeeAuditMapperV1.updateExpressAudit2(param);//-1
		}else {
			basEmployeeAuditMapperV1.saveExpressAuditJump(param);//-1
		}
		//清理缓存-快递认证
		this.redisService.remove("expressInfo-memberId-cache", String.valueOf(memberId));
		this.redisService.remove("findShopowner1xxx", memberId+roleType);
		this.redisService.remove("findShopowner2xxx", memberId+roleType);
		this.redisService.remove("findShopowner3xxx", memberId+roleType);
	}

	/**
	 * 查询快递认证信息
	 */
	@Override
	public HashMap<String, Object> queryExpressAuditStatusByMemberId(String memberId) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.queryExpressAuditStatusByMemberId.001", "memberId参数异常");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		BasEmployeeAudit auditObj = basEmployeeAuditMapperV1.queryExpressStatus(memberId);
		if (auditObj!=null) {
			String applicationDesc = auditObj.getApplicationDesc();
			Short auditStatus = auditObj.getAuditOpinion();
			Long compId = auditObj.getAuditComp();
			Short roleType = auditObj.getApplicationRoleType();
			String auditDesc = auditObj.getAuditDesc();
			resultMap.put("expressNum", applicationDesc!=null ? applicationDesc : "");
			//-1 为完善 0 待审核  1通过 2拒绝
			resultMap.put("auditStatus", auditStatus!=null ? auditStatus : "");
			resultMap.put("compId", compId!=null ? compId : "");
			resultMap.put("roleType", roleType!=null ? roleType : "");
			resultMap.put("memberId", auditObj.getMemberId());
			
			//------2016年4月23日10:32:07 增加审核拒绝原因 by hu.zhao、//
			//审核拒绝原因 1:身份证与本人不一致2:照片不清楚(单号不符),3:其他4.客服确认快递员'
			//状态为拒绝的时候才返回原因，否则返回空
			if("2".equals(String.valueOf(auditStatus))){
				resultMap.put("auditRejectReason", auditDesc);
			}else {
				resultMap.put("auditRejectReason", "");
			}
			//------2016年4月23日10:32:07 增加审核拒绝原因 by hu.zhao、//
			
		}else {
			HashMap<String, Object> auditInfo = basEmployeeAuditMapperV1.queryAddressAuditInfo(memberId);
			Long memberIdStr = (Long) auditInfo.get("member_id");
			Long compId = (Long)auditInfo.get("audit_comp");
			Integer roleType =  (Integer)auditInfo.get("application_role_type");
			resultMap.put("auditStatus", "-1");
			resultMap.put("expressNum", "");
			resultMap.put("compId", compId!=null ? compId : "");
			resultMap.put("roleType", roleType!=null ? roleType : "");
			resultMap.put("memberId", memberIdStr!=null ? memberIdStr : "");
			resultMap.put("auditRejectReason", "");
		}
		return resultMap;
	}

	/**
	 * 查询身份认证信息
	 */
	@Override
	public HashMap<String, Object> queryRealNameAuditInfo(String memberId) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.queryRealNameAuditInfo.001", "memberId参数异常");
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> map = basEmployeeAuditMapperV1.queryRealNameAuditInfo(memberId);
		//已经产生认证信息
		if (map!=null) {
			String memberName =  (String) map.get("member_name");
			String idNum =  (String) map.get("id_num");
			String memberPhone = (String) map.get("member_phone");
			Long memberIdStr = (Long) map.get("member_id");
			Long compId = (Long)map.get("audit_comp");
			Integer roleType =  (Integer)map.get("application_role_type");
			Integer auditStatus =  (Integer)map.get("audit_opinion");
			resultMap.put("memberName", memberName!=null ? memberName : "");
			resultMap.put("idNum", idNum!=null ? idNum : "");
			resultMap.put("memberPhone", memberPhone!=null ? memberPhone : "");
			resultMap.put("memberId", memberIdStr!=null ? memberIdStr : "");
			resultMap.put("compId", compId!=null ? compId : "");
			resultMap.put("roleType", roleType!=null ? roleType : "");
			resultMap.put("auditStatus", auditStatus!=null ? auditStatus : "");
			//type 图片类型：contact：联系人 front：身份证前身照  back：身份证背面照 inHand：手持 head：接单王头像 photoPar:包裹拍照 me:本人照
			resultMap.put("headPic", headImgPath+memberId+".jpg");//头像
			resultMap.put("mePic", headPath+memberId+".jpg");//本人照
			resultMap.put("frontPic", frontPath+memberId+".jpg");//身份证正面
			resultMap.put("backPic", backPath+memberId+".jpg");//身份证反面
			resultMap.put("inHandPic", inHandPath+memberId+".jpg");//手持身份证
			//------2016年4月23日10:32:07 增加审核拒绝原因 by hu.zhao、//
			//审核拒绝原因 1:身份证与本人不一致2:照片不清楚(单号不符),3:其他4.客服确认快递员'
			//状态为拒绝的时候才返回原因，否则返回空
			if("2".equals(map.get("audit_opinion").toString())){
				resultMap.put("auditRejectReason", map.get("audit_desc"));
			}else {
				resultMap.put("auditRejectReason", "");
			}
			//------2016年4月23日10:32:07 增加审核拒绝原因 by hu.zhao、//
			//没有产生认证信息
		}else {
			HashMap<String, Object> addressAuditInfo = basEmployeeAuditMapperV1.queryAddressAuditInfo(memberId);
			if (addressAuditInfo != null) {
				String memberName =  (String) addressAuditInfo.get("member_name");
				String idNum =  (String) addressAuditInfo.get("id_num");
				String memberPhone = (String) addressAuditInfo.get("member_phone");
				Long memberIdStr = (Long) addressAuditInfo.get("member_id");
				Long compId = (Long)addressAuditInfo.get("audit_comp");
				Integer roleType =  (Integer)addressAuditInfo.get("application_role_type");
				resultMap.put("memberName", memberName!=null ? memberName : "");
				resultMap.put("idNum", idNum!=null ? idNum : "");
				resultMap.put("memberPhone", memberPhone!=null ? memberPhone : "");
				resultMap.put("memberId", memberIdStr!=null ? memberIdStr : "");
				resultMap.put("compId", compId!=null ? compId : "");
				resultMap.put("roleType", roleType!=null ? roleType : "");
				resultMap.put("auditStatus", "-1");
				//type 图片类型：contact：联系人 front：身份证前身照  back：身份证背面照 inHand：手持 head：接单王头像 photoPar:包裹拍照 me:本人照
				resultMap.put("headPic", headImgPath+memberId+".jpg");//头像
				resultMap.put("mePic", headPath+memberId+".jpg");//本人照
				resultMap.put("frontPic", frontPath+memberId+".jpg");//身份证正面
				resultMap.put("backPic", backPath+memberId+".jpg");//身份证反面
				resultMap.put("inHandPic", inHandPath+memberId+".jpg");//手持身份证
				resultMap.put("auditRejectReason", "");
			}
		}
		return resultMap;
	}
	/**
	 * 查询归属认证状态
	 */
	@Override
	public HashMap<String, Object> queryAddressAuditInfo(String memberId) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> map = basEmployeeAuditMapperV1.queryAddressAuditInfo(memberId);
		if (map!=null) {
			Integer addressStatus = (Integer) map.get("audit_opinion");
			resultMap.put("addressStatus", addressStatus);
		}else {
			resultMap.put("addressStatus", "");
		}
		return resultMap;
	}
	/**
	 * @Description 为邀请者推送消息
	 */
	@Override
	public void sendMsgToInviter(String memberId, String memberPhone) {
		//推送
		sendNoticeService.sendMsgToInviter(Long.valueOf(memberId), memberPhone);
	}
	/**
	 * 为被邀请者推送消息
	 */
	@Override
	public void sendMsgToInvitee(String memberId, String memberPhone) {
		//推送
		sendNoticeService.sendMsgToInvitee(Long.valueOf(memberId), memberPhone);
	}
    
	
   /** @Description: TODO( 运营平台强制审核收派员通过（实为身份审核） ) */
	@Override
	public HashMap<String, Object> forceCheckMemberInfo(String memberId,String compId, String roleType,String status, String remark, String auditType,
			String mob) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.forceCheckMemberInfo.001", "memberId参数异常");
		}
		if (PubMethod.isEmpty(status)) {
			throw new ServiceException("net.okdi.apiV1.serviceImpl.ExpressUserServiceImpl.forceCheckMemberInfo.002", "status参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id",IdWorker.getIdWorker().nextId());
		param.put("memberId", memberId);
		param.put("compId", compId);
		param.put("roleType", roleType);
		param.put("status", status);
		param.put("remark", remark);
		param.put("auditTime", new Date());
		
		/**
		 * reasonNum：1图片模糊不清2经人工电话核实，与实际情况不符3其他		auditType：3快递员认证	status：1通过
		 */
	 if ("3".equals(auditType)) {
			if ("1".equals(status)) {
				//先删除再插入
				basEmployeeAuditMapperV1.deleExpressAudit(param);
				//审核通过---改audit状态为1-且存备注信息
				basEmployeeAuditMapperV1.insertBasEmployeeAudit(param);
				//推送
				sendNoticeService.passExpressCheck(Long.valueOf(memberId), mob);
			}
			
			//清理缓存-快递认证
			this.redisService.remove("expressInfo-memberId-cache", String.valueOf(memberId));
		}
		
		this.redisService.removeAll("findShopowner1xxx");
		this.redisService.removeAll("findShopowner2xxx");
		this.redisService.removeAll("findShopowner3xxx");
		
		//审核完 返回 两个认证状态
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		BasEmployeeAudit audit1 = basEmployeeAuditMapperV1.queryRealNameStatus(memberId);
		if (audit1!=null) {
			resultMap.put("realNameStatus", audit1.getAuditOpinion());
		}
		BasEmployeeAudit audit2 = basEmployeeAuditMapperV1.queryExpressStatus(memberId);
		if (audit2!=null) {
			resultMap.put("expressStatus", audit2.getAuditOpinion());
		}
		return resultMap;
	}

	/**
	 * 通过手机号集合查询memberid集合
	 */
	@Override
	public HashMap<String, Object> queryMemberInfoByMemberPhones(String memberPhones) {
		HashMap<String, Object> resultMap = new HashMap<>();
		if (PubMethod.isEmpty(memberPhones)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.queryMemberInfoByMemberPhones.001", "memberPhones参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		String[] memberPhonesArry = memberPhones.split(",");
		param.put("memberPhonesArry", memberPhonesArry);
		List<MemberInfo> info = this.memberInfoMapperV1.queryMemberInfoByMemberPhones(param);
		StringBuffer sbf = new StringBuffer();
		String memberIds = "";
		if (info!=null && info.size()>0) {
			for (MemberInfo memberInfo : info) {
				if (!PubMethod.isEmpty(memberInfo.getMemberId())) {
					sbf.append(memberInfo.getMemberId());
					sbf.append(",");
				}
			}
			memberIds=sbf.substring(0, sbf.length()-1);
		}
		resultMap.put("memberIds", memberIds);
		return resultMap;
	}

	@Override
	public List queryRepeatOrder(String orderArr, String netId,String phone) {
		  List retList = new ArrayList();
		   String[] strArr = orderArr.split(",");
		   Arrays.sort(strArr);    //1.三个单号排序
		   for(String str:strArr){
		    
				  List<Map<String, Object>> orderAndMemberIdAndPhone = basEmployeeAuditMapperV1.queryRepeatOrderByOrderAndNetId(str,netId,phone);
				 //System.out.println("集合:"+orderAndMemberIdAndPhone.toString());
				for(Map map:orderAndMemberIdAndPhone){
					  Map ucenterParam = new HashMap();
					      ucenterParam.put("memberId", map.get("member_id"));
					    String ret = noticeHttpClient.queryPromo(ucenterParam);//去ucenter查询这个memberid是谁(哪个手机号)邀请来的
					//    logger.info("ucenter返回结果:"+ret);
					 JSONObject jsonb =  JSON.parseObject(ret);
					 JSONObject date = jsonb.getJSONObject("data");
					// logger.info("date:"+date.toString());	 
					 String error = date.get("error")+"";
					// logger.info("error:"+error);	 
					  if(PubMethod.isEmpty(date.get("error"))){ //空说明有邀请关系
						  String invitePhone = date.get("invitePhone")+"";
						  map.put("invitePhone", invitePhone);
					  }else{
						  map.put("invitePhone", "");
					  }
					 
				  retList.add(map);
				}
			
			   
		   }  
		   
		   logger.info("最后返回结果:"+retList.toString());
		return retList;
	}
		
	/**
	 * 客服操作快递员标记--待确认按钮
	 */
	@Override
	public void waitSureOper(String memberId, String compId,String roleType) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", IdWorker.getIdWorker().nextId());
		paramMap.put("memberId", memberId);
		paramMap.put("compId", compId);
		paramMap.put("roleType", roleType);
		paramMap.put("applicationTime", new Date());
		paramMap.put("auditTime", new Date());
		basEmployeeAuditMapperV1.waitSureOper(paramMap);
	}
	/**
	 * 客服操作快递员标记--确认是快递员 按钮
	 */
	@Override
	public void sureOper(String memberId, String compId, String roleType) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", IdWorker.getIdWorker().nextId());
		paramMap.put("memberId", memberId);
		paramMap.put("compId", compId);
		paramMap.put("roleType", roleType);
		paramMap.put("applicationTime", new Date());
		paramMap.put("auditTime", new Date());
		BasEmployeeAudit sureExpress = basEmployeeAuditMapperV1.querySureExpress(memberId);
		//非空-->修改状态为1；为空-->直接新建状态为1的数据
		if (!PubMethod.isEmpty(sureExpress)) {
			basEmployeeAuditMapperV1.updateSureStatus(paramMap);
		}else {
			basEmployeeAuditMapperV1.saveNewSureOper(paramMap);
		}
	}
	
	
	
	
	
	
	
}
