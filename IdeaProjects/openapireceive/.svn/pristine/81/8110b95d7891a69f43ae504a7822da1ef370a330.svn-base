package net.okdi.apiV2.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompimage;
import net.okdi.api.service.SendNoticeService;
import net.okdi.apiV1.dao.BasCompimageMapperV1;
import net.okdi.apiV2.dao.BasCompInfoMapperV2;
import net.okdi.apiV2.dao.BasPlaintInfoMapperV2;
import net.okdi.apiV2.service.ExpressRegisterService;
import net.okdi.apiV2.service.PlaintSiteService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.annotation.JSONField;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * 申诉--申诉站点,申诉营业分部
 * @author 郑炯
 * @date 2015-12-03 22:01:50
 */
@Service
public class PlaintSiteServiceImpl implements PlaintSiteService {
	public static final Log logger = LogFactory.getLog(PlaintSiteServiceImpl.class);
	@Autowired
	private BasPlaintInfoMapperV2 basPlaintInfoMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Value("${meImgPath}")
	public String headPath;
	@Value("${frontImgPath}")
	public String frontPath;
	@Value("${backImgPath}")
	public String backPath;
	@Value("${inHandImgPath}")
	public String inHandPath;//手持身份证照
	@Value("${headImgPath}")
	public String headImgPath; 
	
	@Value("${compPic.readPath}")
	public String readPath;
	
	@Autowired
	private ExpressRegisterService registerService;
	@Autowired
	private EhcacheService ehcacheService;
	//推送
	@Autowired
	private SendNoticeService sendNoticeService;
	
	//推送
	@Autowired
	private BasCompInfoMapperV2 basCompInfoMapperV2;
	
	@Override
	public String savePlaintSiteInfo(Long memberId, Long compId,
			String compName, Short compType, String plaintPhone,String headPhone,
			String responsible, String idNum, String business, String netId,
			String businessLicenseImg, String shopImg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("compId", compId);//compId 为真实的compId
		map.put("compName", compName);
		map.put("compType", compType);
		map.put("plaintPhone", plaintPhone);
		map.put("responsible", responsible);
		map.put("idNum", idNum);
		map.put("business", business);
		map.put("headPhone", headPhone);
		Date date = new Date();
		map.put("createTime", date);
		long id = IdWorker.getIdWorker().nextId();
		map.put("id", id);
		map.put("status", 0);//待处理
		try {
			//申请人的信息插入到 bas_plaintinfo 表中
			basPlaintInfoMapper.savePlaintSiteInfo(map);
			
			//保存申诉认证照片
			this.savePlaintImage(id, businessLicenseImg, shopImg);
			//把申诉人信息插入到虚拟网点中建立关系
			this.saveVirtualNet(netId, memberId);
			//清空redis缓存
			redisService.removeAll("queryPlaintSiteInfo");
			return "001";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "002";
		}
	}
	/**
	 * 根据netId查询虚拟站点
	 * 同时把当前的信息插入到虚拟网点中
	 */
	public void saveVirtualNet(String netId, Long memberId){
		Map<String,Object> virtualMap = basPlaintInfoMapper.queryVirtualSite(netId);
		//插入到审核表中,申诉人与虚拟站点建立关系
		String compid = virtualMap.get("comp_id")+"";
		//插入到审核表中
		Map<String,Object> auditMap = new HashMap<String,Object>();
		long aid = IdWorker.getIdWorker().nextId();
		auditMap.put("id", aid);//主键
		auditMap.put("memberId", memberId);//申请人id
		auditMap.put("auditComp", compid);//虚拟站点id
		auditMap.put("applicationRoleType", 0);//角色类型
		auditMap.put("applicationTime", new Date());//注册时间
		auditMap.put("auditItem", 2);//归属审核
		auditMap.put("auditOpinion", -1);//未认证
		//把申请人的信息插入到审核表中
		basPlaintInfoMapper.saveBasEmployeeAudit(auditMap);
		//初始化身份和快递 虚拟的compid
		registerService.initExpressAudit(memberId+"", compid, "0");
	}
	/**
	 * 保存认证照片
	 * 根据申诉id进行保存照片
	 */
	public void savePlaintImage(Long id, String businessLicenseImg, String shopImg){
		basPlaintInfoMapper.deleteImageByPlaintId(id);//2.删除原来保存的照片
		logger.info("..........保存认证照片开始");
		List<BasCompimage> compimageList = new ArrayList<BasCompimage>();  //2.保存认证照片
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasCompimage compimage = new BasCompimage();
			BasCompimage compimage1 = new BasCompimage();
			BasCompimage compimage2 = new BasCompimage();
			/*compimage.setId(IdWorker.getIdWorker().nextId());
			compimage.setCompId(null);//对于申诉的compId 首先置空
			compimage.setPlaintId(id);//申诉id
			compimage.setCreateTime(dateFormat.format(new Date()));//创建时间
			compimage.setDisabled((short)1);//停用
			compimage.setImageType(Short.parseShort("5"));//图片类型 1:LOGO, 2:营业执照   5:手持身份证正面照片 8:站点门店照片
			compimage.setImageDetail("手持身份证正面照片");
			compimage.setImageUrl(frontImg);//memberId的jpg照片
			compimageList.add(compimage);
			*/
			compimage1.setId(IdWorker.getIdWorker().nextId());
			compimage1.setCompId(null);//对于申诉的compId 首先置空
			compimage1.setPlaintId(id);//申诉id
			compimage.setCreateTime(dateFormat.format(new Date()));//创建时间
			compimage.setDisabled((short)1);//停用
			compimage1.setImageType(Short.parseShort("2"));//图片类型 1:LOGO, 2:营业执照   5:负责人身份证正面照 8:站点门店照片
			compimage1.setImageDetail("营业执照");
			logger.info("营业执照holdImg:"+businessLicenseImg);
			compimage1.setImageUrl(businessLicenseImg);//memberId的jpg照片
			compimageList.add(compimage1);
			
			compimage2.setId(IdWorker.getIdWorker().nextId());
			compimage2.setCompId(null);//对于申诉的compId 首先置空
			compimage2.setPlaintId(id);//申诉id
			System.out.println("创建时间:"+dateFormat.format(new Date()));
			compimage.setCreateTime(dateFormat.format(new Date()));//创建时间
			compimage.setDisabled((short)1);//停用
			compimage2.setImageType(Short.parseShort("8"));//图片类型 1:LOGO, 2:营业执照   5:负责人身份证正面照 8:站点门店照片
			compimage2.setImageDetail("站点门店照片");
			compimage2.setImageUrl(shopImg);//memberId的jpg照片
			compimageList.add(compimage2);
			
			basPlaintInfoMapper.saveCompImageBatch(compimageList);
	}
	
	/**
	 * 查询申诉列表信息
	 */
	@Override
	public Map<String,Object> queryPlaintSiteInfo(String currentPage,String pageSize,String startTime, String endTime,
			String plaintPhone, String status) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("plaintPhone", plaintPhone);
		map.put("status", status);
		Page page = new Page();
		page.setCurrentPage(Integer.valueOf(currentPage));
		page.setPageSize(Integer.valueOf(pageSize));
		map.put("page", page);
		//Map<String,Object> redisMap = redisService.get("queryPlaintSiteInfo", plaintPhone+"-"+status, Map.class);
		//if(PubMethod.isEmpty(redisMap)){
			//查询出所有的数据
			List<Map<String,Object>> listMap = basPlaintInfoMapper.queryPlaintSiteInfo(map);
			//存放的是申诉id对应的站长的手机号
			Map<String,Object> mapIdPhone = this.querySitePhoneByPlaintPhone(listMap);
			//查询总条数
			Long totalCount = basPlaintInfoMapper.queryPlaintSiteInfoCount(map);
			for(Map<String,Object> map1 : listMap){
				String id = map1.get("id")+"";//获取map中存放的申诉id
				String sitePhone = mapIdPhone.get(id)+"";//根据申诉id获取该站点下的站长手机号
				if("null".equals(sitePhone)){
					sitePhone = "";
				}
				map1.put("sitePhone", sitePhone);//
				
			}
			resultMap.put("listAudit", listMap);
			resultMap.put("total", totalCount);
			if(totalCount%Integer.parseInt(pageSize)>0){
				resultMap.put ("totalPage", totalCount/Integer.parseInt(pageSize)+1);			
			}else{
				resultMap.put("totalPage", totalCount/Integer.parseInt(pageSize));			
			}
			//redisService.put("queryPlaintSiteInfo", plaintPhone+"-"+status, resultMap);
		//}else{
			//缓存中不为空,返回redis缓存中的数据
			//return redisMap;
		//}
		
		return resultMap;
	}
	
	/**
	 * 根据申诉者的id查询该站点下真正的站长手机号
	 */
	public Map<String,Object> querySitePhoneByPlaintPhone(List<Map<String,Object>> listMap){
		//存申诉者id 和对应的真正站长的手机号
		Map<String,Object> map = new HashMap<String,Object>();
		for(Map<String,Object> mapId : listMap){
			String lid = mapId.get("id")+"";
			Map<String,Object> lMap = basPlaintInfoMapper.querySitePhoneByPlaintId(Long.valueOf(lid));
			String memberPhone = lMap.get("member_phone")+"";//该站点下真正的站长手机号
			//String plaintPhone = lMap.get("plaint_phone")+"";//申诉者手机号
			map.put(lid+"", memberPhone);
		}
		return map;
	}
	//查询申诉人的详情
	@Override
	public Map<String, Object> queryPlaintSiteInfoDetail(String id) {
		String showPath = readPath;
		// TODO Auto-generated method stub
		Map<String,Object> map = basPlaintInfoMapper.queryPlaintSiteInfoDetail(id);
		String compId = map.get("compId")+"";
		Long virCompId = basPlaintInfoMapper.queryVirtualCompIdByCompId(compId);
		map.put("virCompId", virCompId);
		//查询站点下的站长手机号
		Map<String,Object> lMap = basPlaintInfoMapper.querySitePhoneByPlaintId(Long.valueOf(id));
		map.put("sitePhone", lMap.get("member_phone"));//站点下的原站长手机号
		map.put("omemberId", lMap.get("omemberId"));//原站长的memberid
		List<Map<String,Object>> listCompimage = basPlaintInfoMapper.queryBasCompImageInfo(id);
		logger.info("图片路径::>>>>>>>>>>>>>>>>>:::::::"+showPath);
		for(Map<String,Object> tempMap : listCompimage){
			//营业执照
			String imageType = tempMap.get("image_type")+"";
			String imageUrl = tempMap.get("image_url")+"";
			logger.info("图片类型::>>>>>>>>::"+imageType+" 图片名称::>>>>>>>>::"+imageUrl);
			if (imageType.equals("2")) {
				//map.put("holdImg", temp.getImageUrl());
				map.put("holdImgUrl", showPath + imageUrl);
			}
			//手持身份证
			if (imageType.equals("5")) {
				//map.put("frontImg", temp.getImageUrl());
				map.put("frontImgUrl", showPath + imageUrl);
			}
			//站点门店照
			if (imageType.equals("8")) {
				//map.put("reverseImg", temp.getImageUrl());
				map.put("reverseImgUrl", showPath + imageUrl);
			}
		}
		return map;
	}
	
	/**
	 * 查询虚拟站点下的收派员记录是否存在
	 */
	public String queryIsNotHaveBasEmployee(String compId, String memberId){
		//1.根据compId查询虚拟站点下是否还有这一条消息 有,流程继续走  或 无 流程不向下走
		//根据真实的compId查询到该网络下的虚拟站点的compId
		Long virCompId = basPlaintInfoMapper.queryVirtualCompIdByCompId(compId);
		Map<String,Object> isMap = basPlaintInfoMapper.queryIsNotHave(memberId, virCompId);
		if(PubMethod.isEmpty(isMap)){//虚拟站点下的记录已经为空,流程结束
			return "001";
		}
		/**
		 * 点击同意更换站长时,在判断该站点下的站长是否已经离职
		 */
		Map<String,Object> iswMap = basPlaintInfoMapper.queryWebmasterISNotDeparture(compId);
		if(PubMethod.isEmpty(iswMap)){//为空,说明原站长离职了
			return "003";
		}
		return "002";//002
	}
	
	/**
	 * 没有
	 */
	/**
	 * 同意更换站长,驳回
	 * 对于以下的SQL 位置不能变化,切记!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * flag:1:同意更换站长 0:驳回
	 */
	@Override
	public String agreedReplaceSite(String id, String compId, String memberId, String flag,
			String auditUser, String desc, String idNum, String responsible, String responsibleTelephone, String business) {
		// TODO Auto-generated method stub
		try {
			Map<String,Object> map = basPlaintInfoMapper.queryCompNameById(id);
			String plaintPhone = map.get("plaint_phone")+"";
			String compName = map.get("comp_name")+"";
			String compType = map.get("comp_type")+"";
			if("1".endsWith(compType)){
				compType = "站点";
			}
			if("2".equals(compType)){
				compType = "营业分部";
			}
			Long userId = Long.valueOf(auditUser);
			/*//查询原站长的memberId用于发送推送
			Map<String,Object> mMap = basPlaintInfoMapper.queryMemberIdByCompId(compId);
			String oldMemberId = mMap.get("member_id")+"";
			//查询原站长的手机号
			Map<String,Object> pMap = basPlaintInfoMapper.queryOldMemberPhoneByCompId(oldMemberId);
			String oldMemberPhone = pMap.get("member_phone")+"";*/
			if(flag.equals("1")){//同意更换站长
				//查询原站长的memberId和memberPhone 用于推送
				Map<String,Object> oldMap = basPlaintInfoMapper.queryOldMemberIdAndMemberPhoneByCompId(compId);
				String oldMemberId = oldMap.get("member_id")+"";
				String oldMemberPhone = oldMap.get("member_phone")+"";
				Date date = new Date();
				flag = "1";//同意
				//根据真实的compId查询到该网络下的虚拟站点的compId
				Long virCompId = basPlaintInfoMapper.queryVirtualCompIdByCompId(compId);
				//1.更新bas_complaint表中的信息
				basPlaintInfoMapper.updateBasPlaintInfoById(id,flag,userId,desc,date);
				//2.把当前的申请人更改为当前站点下的站长
				//2.1 把bas_employeeaudit 更改为收派员 
				basPlaintInfoMapper.updateBasEmployeeAuditByCompId(compId);
				//2.2 把bas_employee_relation 原先的站长更改为收派员
				basPlaintInfoMapper.basEmployeeRelationByCompId(compId);
				//2.3 把bas_employeeaudit建的虚拟的信息给删除掉(删掉归属的信息,身份和快递的更新)
				basPlaintInfoMapper.deleteBasEmployeeAuditByMemberId(virCompId, memberId);
				//插入到审核表中
				Map<String,Object> auditMap = new HashMap<String,Object>();
				long aid = IdWorker.getIdWorker().nextId();
				auditMap.put("id", aid);//主键
				auditMap.put("memberId", memberId);//申请人id
				auditMap.put("auditComp", compId);//真实站点id
				auditMap.put("applicationRoleType", 1);//角色类型 站长
				auditMap.put("applicationTime", new Date());//注册时间
				auditMap.put("auditItem", 2);//归属审核
				auditMap.put("auditOpinion", 1);//通过
				//把申请人的信息插入到审核表中
				basPlaintInfoMapper.saveBasEmployeeAudit(auditMap);
				//2.2 同时还要插入bas_employee_relation 建立关系
				long eid = IdWorker.getIdWorker().nextId();
				Map<String,Object> emap = new HashMap<String,Object>();
				emap.put("id", eid);
				emap.put("compId", compId);
				emap.put("memberId", memberId);
				emap.put("roleId", 1);
				basPlaintInfoMapper.saveEmployeeRelation(emap);
				//更新身份
				basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(memberId),Long.valueOf(compId),1);
				//更新快递
				basCompInfoMapperV2.updateKDAuditInfo(Long.valueOf(memberId),Long.valueOf(compId),1);
				
				//5 更改bas_compimage表中原站长的图片给停用
				basPlaintInfoMapper.updateBasCompimageByCompId(compId);
				//5.1更改bas_compimage表中的信息申诉人的信息给启用
				basPlaintInfoMapper.updateBasCompimageById(id,compId);
				//同意,更改bas_compinfo表中的数据的状态
				//basPlaintInfoMapper.updateBasCompinfoStatusByCompId(compId);
				//6.插入member_info表中身份证号
				//basPlaintInfoMapper.updateMemberInfoByMemberId(memberId, idNum);
				//7.更改bas_compbusiness表中负责人的信息
				//7.1查询是否有这个记录
				Map<String,Object> ismap = basPlaintInfoMapper.queryIsNotBasCompBussiness(compId);
				if(PubMethod.isEmpty(ismap)){
					//7.2如果没有这个记录,就插入
					basPlaintInfoMapper.saveBasCompBussiness(compId,responsible, responsibleTelephone, idNum, business);
				}else{
					//7.2如果有这个记录,就更新
					basPlaintInfoMapper.updateBasCompbusiness(compId, responsible, responsibleTelephone, idNum, business);
				}
				//推送
				//1.首先申诉成功,给新站长发送推送
				sendNoticeService.sendAppealNewWebmasterSuccess(compName, memberId, plaintPhone, compType);
				//2.给原站长发送推送通知
				sendNoticeService.sendAppealOldWebmasterSuccess(compName, oldMemberId, oldMemberPhone, compType);
				//清楚缓存
				ehcacheService.removeAll("employeeCache");//清除人员列表缓存
				ehcacheService.removeAll("memberInfoMobilechCache");//清除人员列表缓存
				redisService.removeAll("findShopowner3xxx");//清除站长缓存
				redisService.removeAll("verifyInfo-memberId-cache");
				redisService.removeAll("relationInfo-memberId-cache");
				redisService.removeAll("expressInfo-memberId-cache");
				return "001";
			}else if(flag.equals("0")){//驳回,不同意更换站长
				//根据真实的compId查询到该网络下的虚拟站点的compId
				Long virCompId = basPlaintInfoMapper.queryVirtualCompIdByCompId(compId);
				//查询该申诉人是否还在虚拟站点下,如果在驳回就发推送,如果不在不发推送
				Map<String,Object> isMap = basPlaintInfoMapper.queryIsNotHave(memberId, virCompId);
				//1.更改 bas_plaintinfo 表中的信息
				flag = "2";//驳回
				Date date = new Date();
				basPlaintInfoMapper.updateBasPlaintInfoById(id,flag,userId,desc,date);
				//2.把当前申诉人虚拟站点下的关系信息给删掉, 删除虚拟下的快递和身份记录
				basPlaintInfoMapper.deleteBasEmployeeAuditByMemberId(virCompId,memberId);
				//推送
				if(!PubMethod.isEmpty(isMap)){//虚拟站点下的记录已经为空,流程结束
					//1.站长申诉失败,发送推送
					sendNoticeService.sendAppealNewWebmasterFailure(compName, memberId, plaintPhone, compType, desc);
				}
				//清楚缓存
				ehcacheService.removeAll("employeeCache");//清除人员列表缓存
				ehcacheService.removeAll("memberInfoMobilechCache");//清除人员列表缓存
				redisService.removeAll("findShopowner3xxx");//清除站长缓存
				redisService.removeAll("verifyInfo-memberId-cache");
				redisService.removeAll("relationInfo-memberId-cache");
				redisService.removeAll("expressInfo-memberId-cache");
				return "002";
			}else if(flag.equals("2")){//
				//同意,更改bas_compinfo表中的数据的状态
				//basPlaintInfoMapper.updateBasCompinfoStatusByCompId(compId);
				
				//根据真实的compId查询到该网络下的虚拟站点的compId
				Long virCompId = basPlaintInfoMapper.queryVirtualCompIdByCompId(compId);
				//1.更新bas_complaint表中的信息
				flag = "1";
				Date date = new Date();
				basPlaintInfoMapper.updateBasPlaintInfoById(id,flag,userId,desc,date);
				//2.把当前的申请人更改为当前站点下的站长
				//2.1 把bas_employeeaudit 更改为收派员 
				//basPlaintInfoMapper.updateBasEmployeeAuditByCompId(compId);
				//2.2 把bas_employee_relation 原先的站长更改为收派员
				//basPlaintInfoMapper.basEmployeeRelationByCompId(compId);
				//2.3 把bas_employeeaudit建的虚拟的信息给删除掉(删掉归属的信息)
				basPlaintInfoMapper.deleteBasEmployeeAuditByMemberId(virCompId, memberId);
				//插入到审核表中
				Map<String,Object> auditMap = new HashMap<String,Object>();
				long aid = IdWorker.getIdWorker().nextId();
				auditMap.put("id", aid);//主键
				auditMap.put("memberId", memberId);//申请人id
				auditMap.put("auditComp", compId);//真实站点id
				auditMap.put("applicationRoleType", 1);//角色类型 站长
				auditMap.put("applicationTime", new Date());//注册时间
				auditMap.put("auditItem", 2);//归属审核
				auditMap.put("auditOpinion", 1);//通过
				//把申请人的信息插入到审核表中
				basPlaintInfoMapper.saveBasEmployeeAudit(auditMap);
				//2.2 同时还要插入bas_employee_relation 建立关系
				long eid = IdWorker.getIdWorker().nextId();
				Map<String,Object> emap = new HashMap<String,Object>();
				emap.put("id", eid);
				emap.put("compId", compId);
				emap.put("memberId", memberId);
				emap.put("roleId", 1);
				basPlaintInfoMapper.saveEmployeeRelation(emap);
				//身份和快递的更新
				//更新身份
				basCompInfoMapperV2.updateSFAuditInfo(Long.valueOf(memberId),Long.valueOf(compId),1);
				//更新快递
				basCompInfoMapperV2.updateKDAuditInfo(Long.valueOf(memberId),Long.valueOf(compId),1);
				
				//5 更改bas_compimage表中原站长的图片给停用
				basPlaintInfoMapper.updateBasCompimageByCompId(compId);
				//5.1更改bas_compimage表中的信息申诉人的信息给启用
				basPlaintInfoMapper.updateBasCompimageById(id,compId);
				//7.更改bas_compbusiness表中负责人的信息
				//7.1查询是否有这个记录
				Map<String,Object> ismap = basPlaintInfoMapper.queryIsNotBasCompBussiness(compId);
				if(PubMethod.isEmpty(ismap)){
					//7.2如果没有这个记录,就插入
					basPlaintInfoMapper.saveBasCompBussiness(compId,responsible, responsibleTelephone, idNum, business);
				}else{
					//7.2如果有这个记录,就更新
					basPlaintInfoMapper.updateBasCompbusiness(compId, responsible, responsibleTelephone, idNum, business);
				}
				//推送
				//1.首先申诉成功,给新站长发送推送
				sendNoticeService.sendAppealNewWebmasterSuccess(compName, memberId, plaintPhone, compType);
				//清楚缓存
				ehcacheService.removeAll("employeeCache");//清除人员列表缓存
				ehcacheService.removeAll("memberInfoMobilechCache");//清除人员列表缓存
				redisService.removeAll("findShopowner3xxx");//清除站长缓存
				redisService.removeAll("verifyInfo-memberId-cache");
				redisService.removeAll("relationInfo-memberId-cache");
				redisService.removeAll("expressInfo-memberId-cache");
				return "003";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "004";//003
		}
		return null;
	}
	
	
}
