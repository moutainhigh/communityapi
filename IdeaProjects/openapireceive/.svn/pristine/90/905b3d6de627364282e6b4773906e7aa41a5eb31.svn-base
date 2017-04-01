package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompAuditMapper;
import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasCompbusinessMapper;
import net.okdi.api.dao.BasCompimageMapper;
import net.okdi.api.dao.BasExpressPriceMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.DicAddressaidMapper;
import net.okdi.api.dao.ExpCompAreaAddressMapper;
import net.okdi.api.dao.ExpCompElectronicFenceMapper;
import net.okdi.api.dao.ExpExceedareaAddressMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasCompbusiness;
import net.okdi.api.entity.BasCompimage;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.entity.ExpCompAreaAddress;
import net.okdi.api.entity.ExpCompElectronicFence;
import net.okdi.api.entity.ExpExceedareaAddress;
import net.okdi.api.service.BusinessBranchService;
import net.okdi.api.service.CompInfoService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.vo.VO_CompInfo;
import net.okdi.api.vo.VO_NetInfo;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.CheckCID;
import net.okdi.core.util.DistanceUtil;
import net.okdi.core.util.ElectronicFenceUtil;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.VerifyUtil;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * 
 * @ClassName CompInfoServiceImpl
 * @Description 网点信息
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
@Service
public class CompInfoServiceImpl extends BaseServiceImpl<BasCompInfo> implements CompInfoService {
	@Autowired
	private BasCompInfoMapper compInfoMapper;
	@Autowired
	private BasCompbusinessMapper compbusinessInfoMapper;
	@Autowired
	private BasCompimageMapper compimageMapper;
	@Autowired
	private BasNetInfoMapper netInfoMapper;
	@Autowired
	private ExpCompAreaAddressMapper expCompAreaAddressMapper;
	@Autowired
	private ExpExceedareaAddressMapper expExceedareaAddressMapper;
	@Autowired
	private ExpCompElectronicFenceMapper expCompElectronicFenceMapper;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private BasCompbusinessMapper basCompbusinessMapper;
	@Autowired
	private BasCompAuditMapper basCompAuditMapper;
	@Autowired
	private DicAddressaidMapper dicAddressaidMapper;
	@Autowired
	private BasExpressPriceMapper basExpressPriceMapper;
	@Autowired
	private BusinessBranchService businessBranchService;
	@Autowired
	private NoticeHttpClient noticeHttpClient;
	@Autowired
	private ConstPool constPool;
	
	@Autowired
	SendNoticeService sendNoticeService;
	
	@Value("${wuliu.read.picture.path}")
	private String pictrueReadUrl; 
	
	@Value("${wuliu.comppicture.filename}")
	private String readFilename; 

	@Value("${compPic.readPath}")
	public String readPath;

	@Override
	public BaseDao getBaseDao() {
		return compInfoMapper;
	}

	/**
	 * 
	 * @Method: getPromptComp 
	 * @Description: 获取网点提示信息
	 * @param netId 网络ID
	 * @param compName 网点名称关键字
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param addressId 地址ID（四级）
	 * @return List<Map<String, Object>>
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#getPromptComp(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public List getPromptComp(Long netId, String compName, String compTypeNum,Long addressId) throws ServiceException {
		Map<String, Object> paras = new HashMap<String, Object>();
		if (PubMethod.isEmpty(netId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getPromptComp.001", "获取网点提示信息,netId参数异常");
		}else if (PubMethod.isEmpty(addressId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getPromptComp.002", "获取网点提示信息,addressId参数异常");
		}else if (!"1006".equals(compTypeNum) && !"1050".equals(compTypeNum)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getPromptComp.003", "获取网点提示信息,compTypeNum参数异常");
		}
		if (PubMethod.isEmpty(compName)) {
			return null;
		}
		DicAddressaid address = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
		if(PubMethod.isEmpty(address)){
			address = this.dicAddressaidMapper.findById(addressId);
		}
		if(!PubMethod.isEmpty(address) && address.getAddressLevel()>=5){
			addressId=address.getCountyId();
		}

		/*List list = ehcacheService.get("promptCompCache",netId+"-"+compName+"-"+compTypeNum+"-"+addressId, ArrayList.class);
		if(ehcacheService.getByKey("promptCompCache",netId+"-"+compName+"-"+compTypeNum+"-"+addressId)){
			return list;
		}*/
		paras.put("netId", netId);
		paras.put("compName", compName.trim());
		paras.put("compTypeNum", compTypeNum);
		paras.put("addressId", addressId);
		List<Map<String,Object>> promptComp = this.compInfoMapper.getPromptComp(paras);
		ehcacheService.put("promptCompCache",netId+"-"+compName+"-"+compTypeNum+"-"+addressId, promptComp);
		return promptComp;
	}
	/**
	 * 
	 * @Method: getPromptCompForMobile 
	 * @Description: 获取注册网点列表（手机端）
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param addressId 地址ID
	 * @param roleId 注册角色 -1 后勤 0收派员 1站长
	 * @return List<Map<String, Object>>
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#getPromptCompForMobile(java.lang.Long, java.lang.String, java.lang.Long, java.lang.Short) 
	 * @since jdk1.6
	 */
	@Override
	public List getPromptCompForMobile(Long netId, String compTypeNum,Long addressId,Short roleId) throws ServiceException {
		Map<String, Object> paras = new HashMap<String, Object>();
		if (PubMethod.isEmpty(netId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getPromptCompForMobile.001", "获取网点提示信息,netId参数异常");
		}else if (PubMethod.isEmpty(addressId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getPromptCompForMobile.002", "获取网点提示信息,addressId参数异常");
		}else if (!"1006".equals(compTypeNum) && !"1050".equals(compTypeNum)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getPromptCompForMobile.003", "获取网点提示信息,compTypeNum参数异常");
		}else if (roleId!=-1&& roleId!=0 && roleId!=1) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getPromptCompForMobile.004", "获取网点提示信息,roleId参数异常");
		}
		DicAddressaid address = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
		if(PubMethod.isEmpty(address)){
			address = this.dicAddressaidMapper.findById(addressId);
		}
		if(!PubMethod.isEmpty(address) && address.getAddressLevel()>=5){
			addressId=address.getCountyId();
		}
		String type="canuse";
		if(roleId==-1|| roleId==0){
			type="all";
		}
		/*List list = ehcacheService.get("promptCompCache","mobile-"+netId+"-"+"-"+compTypeNum+"-"+addressId+"-"+type, ArrayList.class);
		if(ehcacheService.getByKey("promptCompCache","mobile-"+netId+"-"+"-"+compTypeNum+"-"+addressId+"-"+type)){
			return list;
		}*/
		paras.put("netId", netId);
		paras.put("compTypeNum", compTypeNum);
		paras.put("addressId", addressId);
		paras.put("roleId", roleId);
		List<Map<String,Object>> promptComp = this.compInfoMapper.getPromptCompForMobile(paras);
		ehcacheService.put("promptCompCache","mobile-"+netId+"-"+"-"+compTypeNum+"-"+addressId+"-"+type, promptComp);
		return promptComp;
	}
	/**
	 * 
	 * @Method: getSameCompName 
	 * @Description: 获取同网络下重名网点信息(1006已注册的网点信息 1050已注册网点及未领用1003)
	 * @param loginCompId 登录网点ID
	 * @param netId 网络ID
	 * @param compName 网点名称 
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param flag 查询标识 0 不匹配1003未被领用数据 1匹配1003未被领用数据
	 * @return Map<String, Object>
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#getSameCompName(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Short) 
	 * @since jdk1.6
	 */
	@Override
	public Map<String, Object> getSameCompName(Long loginCompId, Long netId, String compName, String compTypeNum, Short flag) throws ServiceException {
		Map<String, Object> paras = new HashMap<String, Object>();
		if (PubMethod.isEmpty(netId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getSameCompName.001", "获取同网络下重名网点信息,netId参数异常");
		}else if (!"1006".equals(compTypeNum) && !"1050".equals(compTypeNum)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.getSameCompName.002", "获取同网络下重名网点信息,compTypeNum参数异常");
		}
		if(PubMethod.isEmpty(compName)){
			return null;
		}
		flag=(PubMethod.isEmpty(flag) || (flag != 0 && flag != 1)) ? 1 : flag;
		paras.put("compId", loginCompId);
		paras.put("netId", netId);
		paras.put("compName", PubMethod.isEmpty(compName)?"":compName.trim());
		paras.put("compTypeNum", compTypeNum);
		paras.put("flag", flag);
		if(flag==1){
			compTypeNum = "1050";
		}
		Map map = ehcacheService.get("sameCompNameCache",compTypeNum+"-"+netId+"-"+compName, Map.class);
		if(!PubMethod.isEmpty(map)){
			return map;
		}
//		if(ehcacheService.getByKey("sameCompNameCache",compTypeNum+"-"+netId+"-"+compName)){
//			return map;
//		}
		List<Map<String, Object>> compList = compInfoMapper.getSameCompName(paras);
		if (PubMethod.isEmpty(compList)) {
			ehcacheService.put("sameCompNameCache",compTypeNum+"-"+netId+"-"+compName, "");
			return null;
		} else {
			ehcacheService.put("sameCompNameCache",compTypeNum+"-"+netId+"-"+compName, compList.get(0));
			return compList.get(0);
		}
	}
	/**
	 * 
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
		Map map = ehcacheService.get("sameCompNameCache",netId+"-"+compName, Map.class);
		if(ehcacheService.getByKey("sameCompNameCache",netId+"-"+compName)){
			return map;
		}
		List<Map<String, Object>> compList = compInfoMapper.getSameCompNameForMobile(paras);
		if (PubMethod.isEmpty(compList)) {
			ehcacheService.put("sameCompNameCache",netId+"-"+compName,"");
			return null;
		} else {
			ehcacheService.put("sameCompNameCache",netId+"-"+compName, compList.get(0));
			return compList.get(0);
		}
	}
	/**
	 * 
	 * @Method: saveCompInfo 
	 * @Description: 保存未注册网点信息
	 * @param memberId 创建人ID
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部 
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @param compRegistWay 注册方式 6 手机端
	 * @return BasCompInfo
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#saveCompInfo(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Short) 
	 * @since jdk1.6
	 */
	public BasCompInfo saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Long addressId, String address, Short compRegistWay) throws ServiceException{
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.001", "保存公司信息参数异常,memberId参数异常");
		}else if (PubMethod.isEmpty(netId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.002", "保存公司信息参数异常,netId参数异常");
		}else if (!"1006".equals(compTypeNum) && !"1050".equals(compTypeNum)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.003", "保存公司信息参数异常,compTypeNum参数异常");
		} else if (PubMethod.isEmpty(compName)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.004", "保存公司信息参数异常,compName参数异常");
		}else if (PubMethod.isEmpty(compTelephone)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.005", "保存公司信息参数异常,compTelephone参数异常");
		}else if (PubMethod.isEmpty(addressId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.006", "保存公司信息参数异常,addressId参数异常");
		}else if (PubMethod.isEmpty(address)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.007", "保存公司信息参数异常,address参数异常");
		}else if (!VerifyUtil.isMobile(compTelephone)&& !VerifyUtil.isTel(compTelephone)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.008", "保存公司信息参数异常,compTelephone参数异常");
		}
		Map<String, Object> sameComp=this.getSameCompNameForMobile(netId, compName);
		if(!PubMethod.isEmpty(sameComp)){
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.009", "网点名称已存在");
		}
		BasCompInfo compInfo=new BasCompInfo();
		compInfo.setCompId(IdWorker.getIdWorker().nextId());
		compInfo.setRelationCompId(-1L);
		this.setCompInfo(compInfo, memberId, netId, compTypeNum, null, compName, compTelephone, addressId, 
				address, null, null, (short)6, (short)0);
		int count = this.compInfoMapper.insert(compInfo);
		if(count<1){
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompInfo.009", "保存公司信息参数异常,存在重名网点禁止新增");
		}
		DicAddressaid addressAid = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
		if(PubMethod.isEmpty(address)){
			addressAid = this.dicAddressaidMapper.findById(addressId);
		}
		if(!PubMethod.isEmpty(addressAid) && addressAid.getAddressLevel()>=5){
			addressId=addressAid.getCountyId();
		}
		ehcacheService.put("compCache", compInfo.getCompId().toString(),compInfo);
		ehcacheService.remove("promptCompCache",netId+"-"+compName+"-"+compTypeNum+"-"+addressId);
		ehcacheService.remove("promptCompCache","mobile-"+netId+"-"+"-"+compTypeNum+"-"+addressId+"-canuse");
		ehcacheService.remove("promptCompCache","mobile-"+netId+"-"+"-"+compTypeNum+"-"+addressId+"-all");
		
		//ehcacheService.remove("sameCompNameCache","1006-"+loginCompInfo.getBelongToNetId()+"-"+compName);
		//ehcacheService.remove("sameCompNameCache","1050-"+loginCompInfo.getBelongToNetId()+"-"+compName);
		ehcacheService.remove("sameCompNameCache",netId+"-"+compName);
		
		
		return compInfo;
	}
	/**
	 * 
	 * @Method: saveOrUpdateCompBasicInfo 
	 * @Description: 保存/更新网点基础信息
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录公司ID
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param useCompId 领用网点ID
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @param longitude 网点经度
	 * @param latitude 网点纬度
	 * @param compRegistWay 网点注册方式 5站点 6手机端
	 * @return BasCompInfo
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#saveOrUpdateCompBasicInfo(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double, java.lang.Double, java.lang.Short) 
	 * @since jdk1.6
	 */
	@Override
	public BasCompInfo saveOrUpdateCompBasicInfo(Long loginMemberId, Long loginCompId, Long netId, String compTypeNum, Long useCompId, String compName, String compTelephone,
			Long addressId, String address, Double longitude, Double latitude, Short compRegistWay){
		Long newCompId=null;
		if(!PubMethod.isEmpty(compTelephone) && !VerifyUtil.isTel(compTelephone) &&!VerifyUtil.isMobile(compTelephone)){
			throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.005", "compTelephone参数格式异常");
		}else if(PubMethod.isEmpty(compTelephone)){
			throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.007", "compTelephone参数非空异常");
		}
		if(PubMethod.isEmpty(netId) || PubMethod.isEmpty(compTypeNum) || PubMethod.isEmpty(addressId) || PubMethod.isEmpty(address) || PubMethod.isEmpty(compRegistWay)){
			throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.008", "netId、compTypeNum、addressId、address、compRegistWay参数非空异常");
		}
		if(PubMethod.isEmpty(compName)){
			throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.006", "compName参数非空异常");
		}
		if (PubMethod.isEmpty(loginMemberId)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.001", "获取登录用户信息异常");
		}
		BasCompInfo loginCompInfo = null;
		String oldCompName = "";
		if (!PubMethod.isEmpty(loginCompId)) {// 查询登录网点信息
			loginCompInfo = ehcacheService.get("compCache", loginCompId.toString(), BasCompInfo.class);
			if (PubMethod.isEmpty(loginCompInfo)) {
				loginCompInfo = this.compInfoMapper.findById(loginCompId);
				ehcacheService.put("compCache", loginCompId.toString(), loginCompInfo);
			}
			if (PubMethod.isEmpty(loginCompInfo)) {
				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.002", "获取登录网点信息异常");
			}
			if (!String.valueOf(netId).equals(String.valueOf(loginCompInfo.getBelongToNetId()))){
				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.009", "网络禁止修改");
			}
			oldCompName = loginCompInfo.getCompName();
		}
		if (!PubMethod.isEmpty(loginCompInfo) // 网点为0待审核或1审核通过
				&& ("0".equals(loginCompInfo.getCompStatus().toString()) || "1".equals(loginCompInfo.getCompStatus().toString()))) {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.003", "网点所处状态禁止修改信息");
		}
		Map<String, Object> sameCompInfo = this.getSameCompName(loginCompId, netId, compName, compTypeNum, (short) 0);
		if (!PubMethod.isEmpty(sameCompInfo)) {// 存在重名网点 禁止修改
			throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.004", "存在重名网点禁止修改");
		}
		Map<String,BasCompInfo> map=this.saveOrUpdateCompInfo(loginCompInfo, loginMemberId, netId, compTypeNum, useCompId, compName, compTelephone, addressId, address, longitude,
				latitude, compRegistWay);
		BasCompInfo compInfo = map.get("loginCompInfo");
		// 保存人员信息
		newCompId=compInfo.getCompId();
		if(PubMethod.isEmpty(loginCompId)){//新注册 添加人员
			this.memberInfoService.doAddMemberToComp(loginMemberId, compInfo.getCompId());
			String result = noticeHttpClient.updateIsPromoFlag((short)3, compInfo.getCreateUser(), (short)4, (short)0);
			if(PubMethod.isEmpty(result) || !"true".equals(JSON.parseObject(result).get("success").toString())){
				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.005", "邀请奖励操作异常");
			}
		}
		ehcacheService.put("compCache", newCompId.toString(), compInfo);
		BasCompInfo oldRelationCompInfo= map.get("oldRelationCompInfo");;
		BasCompInfo deleteCompInfo= map.get("deleteCompInfo");;
		BasCompInfo newRelationCompInfo= map.get("newRelationCompInfo");;
		if(!PubMethod.isEmpty(deleteCompInfo)){
			this.memberInfoService.doEditComp(deleteCompInfo.getCompId(), compInfo.getCompId());
			ehcacheService.remove("compCache", deleteCompInfo.getCompId().toString());
		}
		if(!PubMethod.isEmpty(oldRelationCompInfo)){
			ehcacheService.put("compCache", oldRelationCompInfo.getCompId().toString(), oldRelationCompInfo);
		}
		if(!PubMethod.isEmpty(newRelationCompInfo)){
			ehcacheService.put("compCache", newRelationCompInfo.getCompId().toString(), newRelationCompInfo);
		}
		DicAddressaid addressAid = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
		if(PubMethod.isEmpty(address)){
			addressAid = this.dicAddressaidMapper.findById(addressId);
		}
		if(!PubMethod.isEmpty(addressAid) && addressAid.getAddressLevel()>=5){
			addressId=addressAid.getCountyId();
		}
		ehcacheService.remove("promptCompCache",netId+"-"+compName+"-"+compTypeNum+"-"+addressId);
		ehcacheService.remove("promptCompCache","mobile-"+netId+"-"+"-"+compTypeNum+"-"+addressId+"-canuse");
		ehcacheService.remove("promptCompCache","mobile-"+netId+"-"+"-"+compTypeNum+"-"+addressId+"-all");
		if (!PubMethod.isEmpty(oldCompName) && !oldCompName.equals(compName)){
			memberInfoService.editCompName(loginCompId, compName);
			ehcacheService.remove("sameCompNameCache","1006-"+loginCompInfo.getBelongToNetId()+"-"+oldCompName);
			ehcacheService.remove("sameCompNameCache","1050-"+loginCompInfo.getBelongToNetId()+"-"+oldCompName);
			ehcacheService.remove("sameCompNameCache",loginCompInfo.getBelongToNetId()+"-"+oldCompName);
			ehcacheService.remove("sameCompNameCache","1006-"+loginCompInfo.getBelongToNetId()+"-"+compName);
			ehcacheService.remove("sameCompNameCache","1050-"+loginCompInfo.getBelongToNetId()+"-"+compName);
			ehcacheService.remove("sameCompNameCache",loginCompInfo.getBelongToNetId()+"-"+compName);
		}else if(PubMethod.isEmpty(oldCompName)){
			ehcacheService.remove("sameCompNameCache","1006-"+netId+"-"+compName);
			ehcacheService.remove("sameCompNameCache","1050-"+netId+"-"+compName);
			ehcacheService.remove("sameCompNameCache",netId+"-"+compName);
		}
		ehcacheService.removeAll("resumCache");
		return compInfo;
//		} catch (ServiceException e) {
//			//更换人员新旧归属关系异常 新增的人员缓存移除  不会同时出现注释
//			if(!PubMethod.isEmpty(newCompId)){
//				ehcacheService.put("employeeCache", newCompId.toString(), memberInfoService.queryEmployeeCache(newCompId));
//			}
//			e.printStackTrace();
//			throw e;
//		}
	}

	/**
	 * 
	 * @Method: saveOrUpdateCompInfo 
	 * @Description: 保存更新公司信息表数据
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录公司ID
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param useCompId 领用网点ID
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @param longitude 网点经度
	 * @param latitude 网点纬度
	 * @param compRegistWay 网点注册方式 5站点 6手机端
	 * @return Map<String,BasCompInfo>
	 * @throws ServiceException
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午11:02:56
	 * @since jdk1.6
	 */
	public Map<String,BasCompInfo> saveOrUpdateCompInfo(BasCompInfo loginCompInfo, Long loginMemberId, Long netId, String compTypeNum, Long useCompId, String compName, String compTelephone,
			Long addressId, String address, Double longitude, Double latitude, Short compRegistWay) throws ServiceException {
		Map<String,BasCompInfo> map=new HashMap<String,BasCompInfo>();
		if (PubMethod.isEmpty(loginCompInfo)) {
			loginCompInfo = new BasCompInfo();
		} else if (!"-1".equals(loginCompInfo.getRelationCompId().toString())) {// 原有数据是领用的
			// 更新领用关系
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("compId", loginCompInfo.getRelationCompId());
			paras.put("relationCompId", -1L);
			this.compInfoMapper.updateRelationCompId(paras);
			BasCompInfo oldRelationCompInfo = ehcacheService.get("compCache", loginCompInfo.getRelationCompId().toString(), BasCompInfo.class);
			if (PubMethod.isEmpty(oldRelationCompInfo)) {
				oldRelationCompInfo=this.compInfoMapper.findById(loginCompInfo.getRelationCompId());
				ehcacheService.put("compCache", loginCompInfo.getRelationCompId().toString(), oldRelationCompInfo);
			}
			oldRelationCompInfo.setRelationCompId(-1L);
			map.put("oldRelationCompInfo", oldRelationCompInfo);
		}
//		if (PubMethod.isEmpty(useCompId)) {// 领用网点ID
//			Map<String, Object> useCompMap = this.getUseCompInfo(netId, compName, compTypeNum);
//			if (!PubMethod.isEmpty(useCompMap)) {
//				useCompId = Long.parseLong(useCompMap.get("compId").toString());
//			}
//		}
		BasCompInfo tempCompInfo = null;
		if (PubMethod.isEmpty(useCompId)) {// 新建网点数据
			if(!compName.equals(loginCompInfo.getCompName()) || !String.valueOf(netId).equals(String.valueOf(loginCompInfo.getBelongToNetId()))){
				Map<String, Object> sameComp=this.getSameCompNameForMobile(netId, compName);
				if(!PubMethod.isEmpty(sameComp)){
					throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompInfo.003", "网点名称已存在");
				}
			}
			loginCompInfo.setRelationCompId((long) -1);
		} else {// 修改 1006、1050未注册数据（手机端）或领用1003
			BasCompInfo sysbasComp = ehcacheService.get("compCache", useCompId.toString(), BasCompInfo.class);
			if (PubMethod.isEmpty(sysbasComp)) {
				sysbasComp=this.compInfoMapper.findById(useCompId);// 查出要领用或修改的网点数据
				ehcacheService.put("compCache", useCompId.toString(), sysbasComp);
			}
			if ("1003".equals(sysbasComp.getCompTypeNum().toString()) && "1006".equals(compTypeNum)) {// 领用1003
				loginCompInfo.setRelationCompId((long) sysbasComp.getCompId());
			} else if (("1006".equals(sysbasComp.getCompTypeNum().toString()) && "1006".equals(compTypeNum)) || "1050".equals(sysbasComp.getCompTypeNum().toString())
					&& "1050".equals(compTypeNum)) {// 修改1006或1050未注册数据
				if (PubMethod.isEmpty(loginCompInfo.getCompId())) {
					loginCompInfo = sysbasComp;
				} else {
					/*// 调用更改人员关系方法
					this.memberInfoService.doEditComp(sysbasComp.getCompId(), loginCompInfo.getCompId());*/
					tempCompInfo = sysbasComp;
					/*this.compInfoMapper.delete(sysbasComp.getCompId());
					map.put("deleteCompInfo", sysbasComp);*/
				}
				loginCompInfo.setRelationCompId((long) -1);
			} else {
				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompInfo.001", "领用网点信息类型异常");
			}
		}
		this.setCompInfo(loginCompInfo, loginMemberId, netId, compTypeNum, useCompId, compName, compTelephone, addressId, address, longitude, latitude, compRegistWay,(short)1);
		if (!PubMethod.isEmpty(loginCompInfo.getCompId())) {
			this.compInfoMapper.update(loginCompInfo);
		} else {
			loginCompInfo.setCompId(IdWorker.getIdWorker().nextId());
			int count=this.compInfoMapper.insert(loginCompInfo);
			if(count<1){
				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompInfo.002", "存在重名网点禁止新增");
			}
			if(!PubMethod.isEmpty(useCompId)){
				// 调用更改人员关系方法
				this.memberInfoService.doEditComp(useCompId, loginCompInfo.getCompId());
			}
		}
		if(!PubMethod.isEmpty(tempCompInfo)){
			this.compInfoMapper.delete(tempCompInfo.getCompId());
			map.put("deleteCompInfo", tempCompInfo);
		}
		if (!"-1".equals(loginCompInfo.getRelationCompId().toString())) {// 更新领用关系
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("compId", loginCompInfo.getRelationCompId());
			paras.put("relationCompId", loginCompInfo.getCompId());
			this.compInfoMapper.updateRelationCompId(paras);
			BasCompInfo newRelationCompInfo = ehcacheService.get("compCache", loginCompInfo.getRelationCompId().toString(), BasCompInfo.class);
			if (PubMethod.isEmpty(newRelationCompInfo)) {
				newRelationCompInfo=this.compInfoMapper.findById(loginCompInfo.getRelationCompId());
				ehcacheService.put("compCache", loginCompInfo.getRelationCompId().toString(), newRelationCompInfo);
			}
			newRelationCompInfo.setRelationCompId(loginCompInfo.getCompId());
			map.put("newRelationCompInfo", newRelationCompInfo);
		}
//			ehcacheService.put("compCache",loginCompInfo.getCompId().toString(),loginCompInfo);
		map.put("loginCompInfo", loginCompInfo);
		return map;
	}

//	/**
//	 * 
//	 * @Method: getUseCompInfo 
//	 * @Description: 获取同网络下同名可被领用网点
//	 * @param netId 网络ID
//	 * @param compName 网点名称
//	 * @param compTypeNum 网点类型1006站点 1050营业分部
//	 * @return Map<String,Object>
//	 * @throws ServiceException
//	 * @author ShiHe.Zhai
//	 * @date 2014-11-11 上午11:07:25
//	 * @since jdk1.6
//	 */
//	public Map<String, Object> getUseCompInfo(Long netId, String compName, String compTypeNum) throws ServiceException {
//		if (PubMethod.isEmpty(netId) || PubMethod.isEmpty(compTypeNum) || PubMethod.isEmpty(compName) || (!"1006".equals(compTypeNum) && !"1050".equals(compTypeNum))) {
//			throw new ServiceException("openapi.CompInfoServiceImpl.getUseCompInfo.001", "获取同网络下同名可被领用网点,参数异常");
//		}
//		Map<String, Object> paras = new HashMap<String, Object>();
//		paras.put("netId", netId);
//		paras.put("compName", compName.trim());
//		paras.put("compTypeNum", compTypeNum);
//		List<Map<String, Object>> compList = this.compInfoMapper.getUseCompInfo(paras);
//		if (PubMethod.isEmpty(compList)) {
//			return null;
//		} else {
//			return compList.get(0);
//		}
//	}

	/**
	 * 
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
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午11:12:19
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

	/**
	 * 
	 * @Method: saveOrUpdateCompVerifyInfo 
	 * @Description: 保存/更新网点认证信息
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录公司ID
	 * @param responsible 负责人姓名
	 * @param responsibleTelephone 负责人电话
	 * @param responsibleNum 负责人身份证号
	 * @param businessLicenseImg 营业执照
	 * @param expressLicenseImg 快递许可证
	 * @param frontImg 负责人身份证正面
	 * @param reverseImg 负责人身份证反面
	 * @param holdImg 手持身份证
	 * @param firstSystemImg 系统截图一
	 * @param secondSystemImg 系统截图二
	 * @param thirdSystemImg 系统截图三
	 * @param verifyType 认证方式 3营业及许可证 2证件及截图
	 * @param compStatus 网点状态 -1创建 0提交待审核 1审核通过 2审核失败
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#saveOrUpdateCompVerifyInfo(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Short, java.lang.Short) 
	 * @since jdk1.6
	 */
	@Override
	public void saveOrUpdateCompVerifyInfo(Long loginMemberId,Long loginCompId,String responsible, String responsibleTelephone, String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg, String secondSystemImg, String thirdSystemImg, Short verifyType,
			Short compStatus) throws ServiceException {
		if (PubMethod.isEmpty(loginCompId)||PubMethod.isEmpty(loginMemberId)|| (!PubMethod.isEmpty(responsibleNum)&& !CheckCID.isIdCard(responsibleNum))||(!PubMethod.isEmpty(responsibleTelephone) && !VerifyUtil.isMobile(responsibleTelephone))) {// 查询登录网点信息
			throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.001", "保存认证信息参数异常");
		} else {
			if (compStatus == 0) {
				if (PubMethod.isEmpty(responsible) || PubMethod.isEmpty(responsibleTelephone) || PubMethod.isEmpty(responsibleNum)) {
					throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.002", "资料未完善禁提交");
				} else if (verifyType == 3 && (PubMethod.isEmpty(businessLicenseImg) || PubMethod.isEmpty(expressLicenseImg))) {
					throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.003", "资料未完善禁提交");
				} else if (verifyType == 2 && (PubMethod.isEmpty(frontImg) || PubMethod.isEmpty(reverseImg) || PubMethod.isEmpty(holdImg) || PubMethod.isEmpty(firstSystemImg))) {
					throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.004", "资料未完善禁提交");
				}
			}
			BasCompInfo loginCompInfo = ehcacheService.get("compCache", loginCompId.toString(), BasCompInfo.class);
			if (PubMethod.isEmpty(loginCompInfo)) {
				loginCompInfo = this.compInfoMapper.findById(loginCompId);
//				ehcacheService.put("compCache", loginCompId.toString(), loginCompInfo);
			}
			if (PubMethod.isEmpty(loginCompInfo)) {
				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.005", "保存认证获取网点异常");
			} else if (!"-1".equals(loginCompInfo.getCompStatus().toString()) && !"2".equals(loginCompInfo.getCompStatus().toString())) {
				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.006", "网点状态禁止修改信息");
			}
			BasCompbusiness compbusinessInfo = ehcacheService.get("compBusinessCache", loginCompId.toString(),BasCompbusiness.class);
			if (PubMethod.isEmpty(compbusinessInfo)) {
				compbusinessInfo = this.compbusinessInfoMapper.findById(loginCompId);
//				ehcacheService.put("compBusinessCache", loginCompId.toString(),compbusinessInfo);
			} 
			if (PubMethod.isEmpty(compbusinessInfo)) {
				compbusinessInfo = new BasCompbusiness();
			}
			String oldResponsible = compbusinessInfo.getResponsible();
			compbusinessInfo.setResponsible(responsible);
			compbusinessInfo.setResponsibleIdNum(responsibleNum);
			compbusinessInfo.setResponsibleTelephone(responsibleTelephone);
			compbusinessInfo.setVerifyImageType(verifyType);
			if (PubMethod.isEmpty(compbusinessInfo.getCompId())) {// 新增
				compbusinessInfo.setCompId(loginCompId);
				this.compbusinessInfoMapper.insert(compbusinessInfo);
			} else {// 更新
				this.compbusinessInfoMapper.update(compbusinessInfo);
			}
			List<BasCompimage> compimageList = this.saveCompImageInfo(loginCompId, businessLicenseImg, expressLicenseImg, frontImg, reverseImg, holdImg, firstSystemImg, secondSystemImg, thirdSystemImg,
					verifyType);
			if(compStatus!=loginCompInfo.getCompStatus()){
				Map<String, Object> paras = new HashMap<String, Object>();
				paras.put("compId", loginCompId);
				paras.put("compStatus", compStatus);
				this.compInfoMapper.updateCompStatus(paras);// 更新公司审核状态
			}
			//2015.03.23 18:06 韩雪需求变更-完善负责人信息 不同步站长姓名
//			if(!responsible.equals(oldResponsible)){
//				this.memberInfoService.editMemberName(loginCompId,loginMemberId, responsible);
//			}
			if(compStatus!=loginCompInfo.getCompStatus()){
				loginCompInfo.setCompStatus(compStatus);
				ehcacheService.put("compCache", loginCompId.toString(),loginCompInfo);
			}
			ehcacheService.put("compBusinessCache", loginCompId.toString(),compbusinessInfo);
			ehcacheService.put("compImageCache", loginCompId.toString(),compimageList);
		}
	}

	/**
	 * 
	 * @Method: saveCompImageInfo 
	 * @Description: 保存图片信息
	 * @param loginCompId 网点ID
	 * @param businessLicenseImg 营业执照
	 * @param expressLicenseImg 快递许可证
	 * @param frontImg 负责人身份证正面
	 * @param reverseImg 负责人身份证反面
	 * @param holdImg 手持身份证
	 * @param firstSystemImg 系统截图一
	 * @param secondSystemImg 系统截图二
	 * @param thirdSystemImg 系统截图三
	 * @param verifyType 认证方式 3营业及许可证 2证件及截图
	 * @throws ServiceException
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午11:17:14
	 * @since jdk1.6
	 */
	public List<BasCompimage> saveCompImageInfo(Long loginCompId, String businessLicenseImg, String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg,
			String secondSystemImg, String thirdSystemImg, Short verifyType) throws ServiceException {
		this.compimageMapper.deleteByCompId(loginCompId);
		List<BasCompimage> compimageList = new ArrayList<BasCompimage>();
		if (verifyType == 3) {// 3营业及许可证
			if (!PubMethod.isEmpty(businessLicenseImg)) {
				compimageList.add(this.setCompImageInfo(loginCompId, (short) 2, (short) 1, "营业执照", businessLicenseImg));
			}
			if (!PubMethod.isEmpty(expressLicenseImg)) {
				compimageList.add(this.setCompImageInfo(loginCompId, (short) 4, (short) 1, "快递许可证", expressLicenseImg));
			}
		} else if (verifyType == 2) {// 2证件及截图
			if (!PubMethod.isEmpty(frontImg)) {
				compimageList.add(this.setCompImageInfo(loginCompId, (short) 5, (short) 1, "负责人身份证正面照", frontImg));
			}
			if (!PubMethod.isEmpty(reverseImg)) {
				compimageList.add(this.setCompImageInfo(loginCompId, (short) 8, (short) 1, "负责人身份证反面照", reverseImg));
			}
			if (!PubMethod.isEmpty(holdImg)) {
				compimageList.add(this.setCompImageInfo(loginCompId, (short) 9, (short) 1, "负责人手持身份证照", holdImg));
			}
			if (!PubMethod.isEmpty(firstSystemImg)) {
				compimageList.add(this.setCompImageInfo(loginCompId, (short) 14, (short) 1, "系统截图", firstSystemImg));
			}
			if (!PubMethod.isEmpty(secondSystemImg)) {
				compimageList.add(this.setCompImageInfo(loginCompId, (short) 14, (short) 2, "系统截图", secondSystemImg));
			}
			if (!PubMethod.isEmpty(thirdSystemImg)) {
				compimageList.add(this.setCompImageInfo(loginCompId, (short) 14, (short) 3, "系统截图", thirdSystemImg));
			}
		} else {
			throw new ServiceException("openapi.CompInfoServiceImpl.saveCompImageInfo.001", "认证类型异常");
		}
		if (!PubMethod.isEmpty(compimageList)) {
			this.compimageMapper.saveCompImageBatch(compimageList);
		}
		return compimageList;
	}

	/**
	 * 
	 * @Method: setCompImageInfo 
	 * @Description: 设置图片对象
	 * @param compId 网点ID
	 * @param imageType 图片类型
	 * @param imageSequeNum 图片序号
	 * @param detail 图片描述
	 * @param imageUrl 图片名称
	 * @return BasCompimage
	 * @throws ServiceException
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午11:17:54
	 * @since jdk1.6
	 */
	public BasCompimage setCompImageInfo(Long compId, Short imageType, Short imageSequeNum, String detail, String imageUrl){
		BasCompimage compimage = new BasCompimage();
		compimage.setId(IdWorker.getIdWorker().nextId());
		compimage.setImageUse("网点认证");
		compimage.setCompId(compId);
		compimage.setImageType(imageType);
		compimage.setImageSequeNum(imageSequeNum);
		compimage.setImageSynopsis(detail);
		compimage.setImageDetail(detail);
		compimage.setImageUrl(imageUrl);
		return compimage;
	}

	/**
	 * 
	 * @Method: queryCompBasicInfo 
	 * @Description: 查询网点基础信息
	 * @param loginCompId 网点ID
	 * @return Map<String, Object>
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#queryCompBasicInfo(java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public Map<String, Object> queryCompBasicInfo(Long compId) throws ServiceException {
		if (PubMethod.isEmpty(compId)) {
			return null;
		}
		BasCompInfo compInfo = ehcacheService.get("compCache", compId.toString(), BasCompInfo.class);
		if (PubMethod.isEmpty(compInfo)) {
			compInfo = this.compInfoMapper.findById(compId);    //1.查询站点信息
			ehcacheService.put("compCache", compId.toString(), compInfo);
		}
		if (PubMethod.isEmpty(compInfo)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.queryCompBasicInfo.001", "获取网点基础信息参数异常");
		}
		BasNetInfo netInfo = ehcacheService.get("netCache", compInfo.getBelongToNetId().toString(), BasNetInfo.class);
		if (PubMethod.isEmpty(netInfo)) {
			netInfo=this.netInfoMapper.findById(compInfo.getBelongToNetId()); //2.查询快递名称
		}
		//------------start-----------
		BasCompbusiness compbusiness = ehcacheService.get("compBusinessCache", compId.toString(),BasCompbusiness.class);
		if (PubMethod.isEmpty(compbusiness) && !ehcacheService.getByKey("compBusinessCache", compId.toString())) {
			compbusiness = this.compbusinessInfoMapper.findById(compId);   //3.查询负责人信息
			ehcacheService.put("compBusinessCache", compId.toString(),compbusiness);
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
		Map map=new HashMap();
		try {
			 map = basCompAuditMapper.queryCompaudit(String.valueOf(compId));  //4.查询站长信息
		} catch (Exception e) {
			System.out.println("-===============yunxingshiyic");
		}
		
		
		
		compInfoMap.put("compId", compInfo.getCompId());
		compInfoMap.put("netId", compInfo.getBelongToNetId());
		compInfoMap.put("netName", netInfo.getNetName());
		compInfoMap.put("compTypeNum", compInfo.getCompTypeNum());
		compInfoMap.put("useCompId", "-1".equals(compInfo.getRelationCompId().toString()) ? null : compInfo.getRelationCompId());
		compInfoMap.put("compName", compInfo.getCompName());
		compInfoMap.put("compTelephone", (PubMethod.isEmpty(compInfo.getCompTelephone()) || "-".equals(compInfo.getCompTelephone())) ? compInfo.getCompMobile() : compInfo
				.getCompTelephone());
		compInfoMap.put("addressId", compInfo.getCompAddressId());
		compInfoMap.put("address", compInfo.getCompAddress());
		compInfoMap.put("longitude", compInfo.getLongitude());
		compInfoMap.put("latitude", compInfo.getLatitude());
		compInfoMap.put("compStatus", compInfo.getCompStatus());
		if(!PubMethod.isEmpty(map)){
			compInfoMap.put("memberName", PubMethod.isEmpty(map.get("memberName"))?"":map.get("memberName")); //站长姓名
			compInfoMap.put("memberPhone", PubMethod.isEmpty(map.get("memberPhone"))?"":map.get("memberPhone") ); //站长手机号
			compInfoMap.put("createTime",PubMethod.isEmpty(map.get("createTime"))?"":map.get("createTime") ); //注册时间
			compInfoMap.put("memberId",PubMethod.isEmpty(map.get("memberId"))?"":map.get("memberId") ); //memberId
		}else{
			compInfoMap.put("memberName", ""); //站长姓名
			compInfoMap.put("memberPhone",""); //站长手机号
			compInfoMap.put("createTime", ""); //注册时间
			compInfoMap.put("memberId", ""); //memberId
		}
		return compInfoMap;
	}

	/**
	 * 
	 * @Method: queryCompVerifyInfo 
	 * @Description: 查询网点认证信息
	 * @param loginCompId 网点ID
	 * @return Map<String, Object>
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#queryCompVerifyInfo(java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public Map<String, Object> queryCompVerifyInfo(Long compId){
		if (PubMethod.isEmpty(compId)) {
			return null;
		}
		BasCompbusiness compbusiness = ehcacheService.get("compBusinessCache", compId.toString(),BasCompbusiness.class);
		if (PubMethod.isEmpty(compbusiness) && !ehcacheService.getByKey("compBusinessCache", compId.toString())) {
			compbusiness = this.compbusinessInfoMapper.findById(compId);
			ehcacheService.put("compBusinessCache", compId.toString(),compbusiness);
		} 
		if (PubMethod.isEmpty(compbusiness)) {
			return null;
		}
		Map<String, Object> compVerifyInfoMap = new HashMap<String, Object>();
		compVerifyInfoMap.put("responsible", compbusiness.getResponsible());
		compVerifyInfoMap.put("responsibleIdNum", compbusiness.getResponsibleIdNum());
		compVerifyInfoMap.put("responsibleTelephone", compbusiness.getResponsibleTelephone());
		compVerifyInfoMap.put("verifyType", compbusiness.getVerifyImageType());
		// compVerifyInfoMap.put("businessLicenseImg",null);
		// compVerifyInfoMap.put("businessLicenseImgUrl",null);
		// compVerifyInfoMap.put("expressLicenseImg",null);
		// compVerifyInfoMap.put("expressLicenseImgUrl",null);
		// compVerifyInfoMap.put("frontImg",null);
		// compVerifyInfoMap.put("frontImgUrl",null);
		// compVerifyInfoMap.put("reverseImg",null);
		// compVerifyInfoMap.put("reverseImgUrl",null);
		// compVerifyInfoMap.put("holdImg",null);
		// compVerifyInfoMap.put("holdImgUrl",null);
		// compVerifyInfoMap.put("firstSystemImg",null);
		// compVerifyInfoMap.put("firstSystemImgUrl",null);
		// compVerifyInfoMap.put("secondSystemImg",null);
		// compVerifyInfoMap.put("secondSystemImgUrl",null);
		// compVerifyInfoMap.put("thirdSystemImg",null);
		// compVerifyInfoMap.put("thirdSystemImgUrl",null);
		String auditDesc = ehcacheService.get("compAuditCache", compId.toString(),String.class);
		if(PubMethod.isEmpty(auditDesc) && !ehcacheService.getByKey("compAuditCache", compId.toString())){
			auditDesc = this.compInfoMapper.queryCompAuditDesc(compId);
			ehcacheService.put("compAuditCache", compId.toString(),PubMethod.isEmpty(auditDesc) ? "-" : auditDesc);
		}
		compVerifyInfoMap.put("auditDesc",auditDesc);
		List<Object> cacheList = ehcacheService.get("compImageCache", compId.toString(),ArrayList.class);
		List<BasCompimage> compimageList = new ArrayList<BasCompimage>();
		if(PubMethod.isEmpty(cacheList) && !ehcacheService.getByKey("compImageCache", compId.toString())){
			compimageList = this.compimageMapper.queryCompimage(compId);
			ehcacheService.put("compImageCache", compId.toString(),compimageList);
		}else if(!PubMethod.isEmpty(cacheList)){
			for (Object temp : cacheList) {
				compimageList.add(JSON.parseObject(temp.toString(), BasCompimage.class));
			}
		}
		String showPath = readPath;
		for (BasCompimage temp : compimageList) {
			if (2 == temp.getImageType()) {
				compVerifyInfoMap.put("holdImg", temp.getImageUrl());
				compVerifyInfoMap.put("holdImgUrl", showPath + temp.getImageUrl());
			} else if (4 == temp.getImageType()) {
				compVerifyInfoMap.put("expressLicenseImg", temp.getImageUrl());
				compVerifyInfoMap.put("expressLicenseImgUrl", showPath + temp.getImageUrl());
			}
			if (5 == temp.getImageType()) {
				compVerifyInfoMap.put("frontImg", temp.getImageUrl());
				compVerifyInfoMap.put("frontImgUrl", showPath + temp.getImageUrl());
			} else if (8 == temp.getImageType()) {
				compVerifyInfoMap.put("reverseImg", temp.getImageUrl());
				compVerifyInfoMap.put("reverseImgUrl", showPath + temp.getImageUrl());
			} else if (9 == temp.getImageType()) {
				compVerifyInfoMap.put("holdImg", temp.getImageUrl());
				compVerifyInfoMap.put("holdImgUrl", showPath + temp.getImageUrl());
			} else if (14 == temp.getImageType() && !PubMethod.isEmpty(temp.getImageSequeNum()) && 1 == temp.getImageSequeNum()) {
				compVerifyInfoMap.put("firstSystemImg", temp.getImageUrl());
				compVerifyInfoMap.put("firstSystemImgUrl", showPath + temp.getImageUrl());
			} else if (14 == temp.getImageType() && !PubMethod.isEmpty(temp.getImageSequeNum()) && 2 == temp.getImageSequeNum()) {
				compVerifyInfoMap.put("secondSystemImg", temp.getImageUrl());
				compVerifyInfoMap.put("secondSystemImgUrl", showPath + temp.getImageUrl());
			} else if (14 == temp.getImageType() && !PubMethod.isEmpty(temp.getImageSequeNum()) && 3 == temp.getImageSequeNum()) {
				compVerifyInfoMap.put("thirdSystemImg", temp.getImageUrl());
				compVerifyInfoMap.put("thirdSystemImgUrl", showPath + temp.getImageUrl());
			} else if (14 == temp.getImageType() && PubMethod.isEmpty(compVerifyInfoMap.get("firstSystemImg"))) {
				compVerifyInfoMap.put("firstSystemImg", temp.getImageUrl());
				compVerifyInfoMap.put("firstSystemImgUrl", showPath + temp.getImageUrl());
			}
		}
		return compVerifyInfoMap;
	}
	/**
	 * 
	 * @Method: queryCompVerifyInfo 
	 * @Description: 查询网点认证信息
	 * @param loginCompId 网点ID
	 * @return Map<String, Object>
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#queryCompVerifyInfo(java.lang.Long) 
	 * @since jdk1.6
	 */
	/*@Override
	public Map<String, Object> queryCompVerifyInfo2(Long loginCompId){
		if (PubMethod.isEmpty(loginCompId)) {
			return null;
		}
		BasCompbusiness compbusiness = ehcacheService.get("compBusinessCache", loginCompId.toString(),BasCompbusiness.class);
		if (PubMethod.isEmpty(compbusiness) && !ehcacheService.getByKey("compBusinessCache", loginCompId.toString())) {
			compbusiness = this.compbusinessInfoMapper.findById(loginCompId);
			ehcacheService.put("compBusinessCache", loginCompId.toString(),compbusiness);
		} 
		if (PubMethod.isEmpty(compbusiness)) {
			return null;
		}
		Map<String, Object> compVerifyInfoMap = new HashMap<String, Object>();
		compVerifyInfoMap.put("responsible", compbusiness.getResponsible());
		compVerifyInfoMap.put("responsibleIdNum", compbusiness.getResponsibleIdNum());
		compVerifyInfoMap.put("responsibleTelephone", compbusiness.getResponsibleTelephone());
		compVerifyInfoMap.put("verifyType", compbusiness.getVerifyImageType());
		
		String auditDesc = ehcacheService.get("compAuditCache", loginCompId.toString(),String.class);
		if(PubMethod.isEmpty(auditDesc) && !ehcacheService.getByKey("compAuditCache", loginCompId.toString())){
			auditDesc = this.compInfoMapper.queryCompAuditDesc(loginCompId);
			ehcacheService.put("compAuditCache", loginCompId.toString(),PubMethod.isEmpty(auditDesc) ? "-" : auditDesc);
		}
		compVerifyInfoMap.put("auditDesc",auditDesc);
		List<Object> cacheList = ehcacheService.get("compImageCache", loginCompId.toString(),ArrayList.class);
		List<BasCompimage> compimageList = new ArrayList<BasCompimage>();
		if(PubMethod.isEmpty(cacheList) && !ehcacheService.getByKey("compImageCache", loginCompId.toString())){
			compimageList = this.compimageMapper.queryCompimage(loginCompId);
			ehcacheService.put("compImageCache", loginCompId.toString(),compimageList);
		}else if(!PubMethod.isEmpty(cacheList)){
			for (Object temp : cacheList) {
				compimageList.add(JSON.parseObject(temp.toString(), BasCompimage.class));
			}
		}
		String showPath = readPath;
		for (BasCompimage temp : compimageList) {
			if (2 == temp.getImageType()) {
				compVerifyInfoMap.put("businessLicenseImg", temp.getImageUrl());
				compVerifyInfoMap.put("businessLicenseImgUrl", showPath + temp.getImageUrl());
			} else if (4 == temp.getImageType()) {
				compVerifyInfoMap.put("expressLicenseImg", temp.getImageUrl());
				compVerifyInfoMap.put("expressLicenseImgUrl", showPath + temp.getImageUrl());
			}
			if (5 == temp.getImageType()) {
				compVerifyInfoMap.put("frontImg", temp.getImageUrl());
				compVerifyInfoMap.put("frontImgUrl", showPath + temp.getImageUrl());
			} else if (8 == temp.getImageType()) {
				compVerifyInfoMap.put("reverseImg", temp.getImageUrl());
				compVerifyInfoMap.put("reverseImgUrl", showPath + temp.getImageUrl());
			} else if (9 == temp.getImageType()) {
				compVerifyInfoMap.put("holdImg", temp.getImageUrl());
				compVerifyInfoMap.put("holdImgUrl", showPath + temp.getImageUrl());
			} else if (14 == temp.getImageType() && !PubMethod.isEmpty(temp.getImageSequeNum()) && 1 == temp.getImageSequeNum()) {
				compVerifyInfoMap.put("firstSystemImg", temp.getImageUrl());
				compVerifyInfoMap.put("firstSystemImgUrl", showPath + temp.getImageUrl());
			} else if (14 == temp.getImageType() && !PubMethod.isEmpty(temp.getImageSequeNum()) && 2 == temp.getImageSequeNum()) {
				compVerifyInfoMap.put("secondSystemImg", temp.getImageUrl());
				compVerifyInfoMap.put("secondSystemImgUrl", showPath + temp.getImageUrl());
			} else if (14 == temp.getImageType() && !PubMethod.isEmpty(temp.getImageSequeNum()) && 3 == temp.getImageSequeNum()) {
				compVerifyInfoMap.put("thirdSystemImg", temp.getImageUrl());
				compVerifyInfoMap.put("thirdSystemImgUrl", showPath + temp.getImageUrl());
			} else if (14 == temp.getImageType() && PubMethod.isEmpty(compVerifyInfoMap.get("firstSystemImg"))) {
				compVerifyInfoMap.put("firstSystemImg", temp.getImageUrl());
				compVerifyInfoMap.put("firstSystemImgUrl", showPath + temp.getImageUrl());
			}
		}
		return compVerifyInfoMap;
	}*/
	/**
	 * 
	 * @Method: updateCompInfo 
	 * @Description: 更新网点信息（审核通过后）
	 * @param loginCompId 登录网点ID
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 地址ID
	 * @param address 网点详细地址
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param responsibleTelephone 负责人电话
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#updateCompInfo(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Double, java.lang.Double, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public void updateCompInfo(Long loginCompId, String compName, String compTelephone, Long addressId, String address, Double longitude, Double latitude,
			String responsibleTelephone) throws Exception {
		Long relationCompId=null;
		if (PubMethod.isEmpty(loginCompId) || PubMethod.isEmpty(compName)||PubMethod.isEmpty(responsibleTelephone)||PubMethod.isEmpty(compTelephone) ||(!VerifyUtil.isMobile(compTelephone)&& !VerifyUtil.isTel(compTelephone))
				|| !VerifyUtil.isMobile(responsibleTelephone)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.updateCompInfo.001", "更新网点信息参数异常");
		}
		BasCompInfo loginCompInfo = ehcacheService.get("compCache", loginCompId.toString(), BasCompInfo.class);
		if (PubMethod.isEmpty(loginCompInfo)) {
			loginCompInfo = this.compInfoMapper.findById(loginCompId);
			ehcacheService.put("compCache", loginCompId.toString(), loginCompInfo);
		}
		String oldCompName = loginCompInfo.getCompName();
		if (!compName.equals(oldCompName)) {// 修改名称
			Map<String, Object> sameComp=this.getSameCompNameForMobile(loginCompInfo.getBelongToNetId(), compName);
			if(!PubMethod.isEmpty(sameComp)){
				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompInfo.003", "网点名称已存在");
			}
		}
		if (!compName.equals(oldCompName)) {// 修改名称
			Map<String, Object> sameCompInfo = this.getSameCompName(loginCompId, loginCompInfo.getBelongToNetId(), compName, loginCompInfo.getCompTypeNum(), (short) 1);
			if (!PubMethod.isEmpty(sameCompInfo)) {// 存在重名网点 禁止修改
				throw new ServiceException("openapi.CompInfoServiceImpl.updateCompInfo.002", "存在重名网点禁止修改");
			}
			if (!"-1".equals(loginCompInfo.getRelationCompId().toString())) {// 更新领用关系
				Map<String, Object> paras = new HashMap<String, Object>();
				paras.put("compId", loginCompInfo.getRelationCompId());
				paras.put("relationCompId", -1);
				this.compInfoMapper.updateRelationCompId(paras);
				// 清除片区数据
				this.expCompAreaAddressMapper.deleteCompAreaAddress(loginCompId);
				this.expExceedareaAddressMapper.deleteExceedareaAddress(loginCompId);
				relationCompId=loginCompInfo.getRelationCompId();
			}
		}
		loginCompInfo.setRelationCompId(-1L);
		loginCompInfo.setCompName(compName);
		loginCompInfo.setFirstLetter(PiyinUtil.cn2py(compName.substring(0, 1)).toUpperCase());
		loginCompInfo.setCompTelephone(compTelephone);
		loginCompInfo.setCompAddressId(addressId);
		loginCompInfo.setCompAddress(address);
		loginCompInfo.setLongitude(PubMethod.isEmpty(longitude) ? null : BigDecimal.valueOf(longitude));
		loginCompInfo.setLatitude(PubMethod.isEmpty(latitude) ? null : BigDecimal.valueOf(latitude));
		this.compInfoMapper.update(loginCompInfo);
		BasCompbusiness compbusiness = ehcacheService.get("compBusinessCache", loginCompId.toString(),BasCompbusiness.class);
		if (PubMethod.isEmpty(compbusiness)) {
			compbusiness = this.compbusinessInfoMapper.findById(loginCompId);
		} 
		if(!responsibleTelephone.equals(compbusiness.getResponsibleTelephone())){
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("compId", loginCompId);
			paras.put("responsibleTelephone", responsibleTelephone);
			this.compbusinessInfoMapper.updateTelephone(paras);
		}
//		if (!compName.equals(oldCompName)) {// 修改名称
////			Map<String, Object> useCompMap = this.getUseCompInfo(loginCompInfo.getBelongToNetId(), compName, loginCompInfo.getCompTypeNum());
////			if (!PubMethod.isEmpty(useCompMap)) {// 存在未注册的同名数据（手机端）
////				Long useCompId = Long.parseLong(useCompMap.get("compId").toString());
////				// 调用更改人员关系方法
////				this.compInfoMapper.delete(useCompId);
////				this.memberInfoService.doEditComp(useCompId, loginCompId);
////				ehcacheService.remove("compCache", useCompId.toString());
////			}
//			Map<String, Object> sameComp=this.getSameCompNameForMobile(loginCompInfo.getBelongToNetId(), compName);
//			if(!PubMethod.isEmpty(sameComp)){
//				throw new ServiceException("openapi.CompInfoServiceImpl.saveOrUpdateCompInfo.003", "网点名称已存在");
//			}
//		}
		ehcacheService.put("compCache", loginCompId.toString(),loginCompInfo);
		if(!PubMethod.isEmpty(relationCompId)){
			BasCompInfo relationCompInfo = ehcacheService.get("compCache", relationCompId.toString(), BasCompInfo.class);
			relationCompInfo.setRelationCompId(-1L);
			ehcacheService.put("compCache", relationCompId.toString(),relationCompInfo);
		}
		if(!responsibleTelephone.equals(compbusiness.getResponsibleTelephone())){
			compbusiness.setResponsibleTelephone(responsibleTelephone);
			ehcacheService.put("compBusinessCache", loginCompId.toString(),compbusiness);
		}
		if (!compName.equals(oldCompName)) {
			memberInfoService.editCompName(loginCompId, compName);
			
			DicAddressaid addressaid = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
			if(PubMethod.isEmpty(addressaid)){
				addressaid = this.dicAddressaidMapper.findById(addressId);
			}
			if(!PubMethod.isEmpty(addressaid) && addressaid.getAddressLevel()>=5){
				addressId=addressaid.getCountyId();
			}
			ehcacheService.remove("promptCompCache",loginCompInfo.getBelongToNetId()+"-"+oldCompName+"-"+loginCompInfo.getCompTypeNum()+"-"+addressId);
			ehcacheService.remove("promptCompCache","mobile-"+loginCompInfo.getBelongToNetId()+"-"+"-"+loginCompInfo.getCompTypeNum()+"-"+addressId+"-canuse");
			ehcacheService.remove("promptCompCache","mobile-"+loginCompInfo.getBelongToNetId()+"-"+"-"+loginCompInfo.getCompTypeNum()+"-"+addressId+"-all");
			
		ehcacheService.remove("sameCompNameCache","1006-"+loginCompInfo.getBelongToNetId()+"-"+oldCompName);
		ehcacheService.remove("sameCompNameCache","1050-"+loginCompInfo.getBelongToNetId()+"-"+oldCompName);
		ehcacheService.remove("sameCompNameCache",loginCompInfo.getBelongToNetId()+"-"+oldCompName);
		ehcacheService.remove("sameCompNameCache","1006-"+loginCompInfo.getBelongToNetId()+"-"+compName);
		ehcacheService.remove("sameCompNameCache","1050-"+loginCompInfo.getBelongToNetId()+"-"+compName);
		ehcacheService.remove("sameCompNameCache",loginCompInfo.getBelongToNetId()+"-"+compName);
		ehcacheService.removeAll("memberAndBranchCache");
		}
		ehcacheService.removeAll("resumCache");
	}
	/**
	 * 
	 * @Method: updateCompStatus 
	 * @Description: 更新网点状态
	 * @param compId 网点ID
	 * @param compStatus 网点状态 -1创建 0提交待审核 1审核成功 2审核失败
	 * @param auditId 最后一次审核ID
	 * @throws ServiceException 
	 * @see net.okdi.api.service.CompInfoService#updateCompStatus(java.lang.Long, java.lang.Short, java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public void updateCompStatus(Long compId, Short compStatus, Long auditId) throws ServiceException {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(auditId) || PubMethod.isEmpty(compStatus) || (compStatus != 1 && compStatus != 2)) {
			throw new ServiceException("openapi.CompInfoServiceImpl.updateCompStatus.001", "审核站点参数异常");
		}
		BasCompInfo compInfo = ehcacheService.get("compCache", compId.toString(), BasCompInfo.class);
		if (PubMethod.isEmpty(compInfo)) {
			 compInfo = this.compInfoMapper.findById(compId);
			ehcacheService.put("compCache", compId.toString(), compInfo);
		}
		if (PubMethod.isEmpty(compInfo) || compInfo.getCompStatus() != 0) {
			throw new ServiceException("openapi.CompInfoServiceImpl.updateCompStatus.002", "审核站点站点信息异常");
		}
		Map<String, Object> paras = new HashMap<String, Object>();
		Date date=new Date();
		paras.put("compId", compId);
		paras.put("compStatus", compStatus);
		paras.put("auditId", auditId);
		paras.put("auditDate", date);
		this.compInfoMapper.updateCompStatus(paras);// 更新公司审核状态
		if (compStatus == 1 && !"-1".equals(compInfo.getRelationCompId())) {// 拷贝数据
			List<ExpCompAreaAddress> compAreaAddressList = this.expCompAreaAddressMapper.queryCompAreaAddress(compInfo.getRelationCompId());
			for (ExpCompAreaAddress expCompAreaAddress : compAreaAddressList) {
				expCompAreaAddress.setCompId(compId);
				expCompAreaAddress.setCreatedTime(new Date());
			}
			if (!PubMethod.isEmpty(compAreaAddressList)) {
				this.expCompAreaAddressMapper.saveBatch(compAreaAddressList);
			}
			List<ExpExceedareaAddress> exceedareaAddressList = this.expExceedareaAddressMapper.queryExceedareaAddress(compInfo.getRelationCompId());
			for (ExpExceedareaAddress expExceedareaAddress : exceedareaAddressList) {
				expExceedareaAddress.setCompId(compId);
				expExceedareaAddress.setCreatedTime(new Date());
			}
			if (!PubMethod.isEmpty(exceedareaAddressList)) {
				this.expExceedareaAddressMapper.saveBatch(exceedareaAddressList);
			}
			List<ExpCompElectronicFence> expCompElectronicFenceList=this.expCompElectronicFenceMapper.queryCompFence(compInfo.getRelationCompId());
			for (ExpCompElectronicFence expCompElectronicFence : expCompElectronicFenceList) {
				expCompElectronicFence.setId(IdWorker.getIdWorker().nextId());
				expCompElectronicFence.setCompId(compId);
			}
			if (!PubMethod.isEmpty(expCompElectronicFenceList)) {
				this.expCompElectronicFenceMapper.saveBatch(expCompElectronicFenceList);
			}
		}
		compInfo.setCompStatus(compStatus);
		compInfo.setLastAuditId(auditId);
		compInfo.setLeagueTime(date);
		ehcacheService.put("compCache", compId.toString(),compInfo);
		ehcacheService.remove("compAuditCache", compId.toString());
		if(compStatus == 2){
			businessBranchService.clearCache();
			//推送
			try{
				if(!PubMethod.isEmpty(compInfo.getCompId())){
					Map<String, Object> masterInfo = memberInfoService.getMasterPhone(compInfo.getCompId());
					
					sendNoticeService.refuseNetDotAuthenticat(Long.valueOf(masterInfo.get("memberId")+""), masterInfo.get("memberPhone").toString(),compStatus,null);
				}
			}catch(Exception e){
				
			}
		}
		if(compStatus == 1){
			businessBranchService.clearCache();
			//推送
			try{
				if(!PubMethod.isEmpty(compInfo.getCompId())){
					Map<String, Object> masterInfo = memberInfoService.getMasterPhone(compInfo.getCompId());
					
					sendNoticeService.successNetDotAuthenticat(Long.valueOf(masterInfo.get("memberId")+""), masterInfo.get("memberPhone").toString(),compStatus);
				}
			}catch(Exception e){
				
			}
		}
		
	}

	/**
	 * 
	 * @Description: 根据经纬度获取网络下的站点信息
	 * @author feng.wang
	 * @date 2014-11-1下午2:23:17
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 */
	@Override
	public Map getExpSites(String longitude, String latitude,Long netId) {
		Map<String, Object> data = new HashMap<String, Object>();
		//根据经纬度查询电子围栏信息
		List fenceList=this.ehcacheService.get("compElectronicFenceCache", longitude+"_"+latitude,List.class);
		List<ExpCompElectronicFence> listElectronicFence=null;
		if(fenceList!=null&&fenceList.size()>0){
			listElectronicFence=JSON.parseArray(fenceList.toString(), ExpCompElectronicFence.class);
		}
		if(listElectronicFence==null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("longitude", longitude);
			params.put("latitude", latitude);
			listElectronicFence=this.expCompElectronicFenceMapper.getListByLonLat(params);
			this.ehcacheService.put("compElectronicFenceCache", longitude+"_"+latitude, listElectronicFence);
		}
		if (listElectronicFence == null || listElectronicFence.size() == 0) {
			return null;
		}
		List<VO_CompInfo> compList = new ArrayList<VO_CompInfo>();
		BasCompInfo compInfo = null;
		BasNetInfo netInfo = null;
		List<VO_NetInfo> netList = new ArrayList<VO_NetInfo>();
		for (int i = 0; i < listElectronicFence.size(); i++) {
			ExpCompElectronicFence entity = listElectronicFence.get(i);
			// 判断是否在派送范围之内
			boolean ifExit = ElectronicFenceUtil.pointInPolygon(Double.valueOf(longitude), Double.valueOf(latitude), entity.getLongitudeStr(), entity.getLatitudeStr());
			if (ifExit) {//如果在电子围栏覆盖范围之内
				//先从缓存俩面读取公司信息和网络信息
				compInfo = this.ehcacheService.get("compCache", entity.getCompId().toString(), BasCompInfo.class);
				netInfo = this.ehcacheService.get("netCache", entity.getNetId().toString(), BasNetInfo.class);
				if (compInfo == null) {//缓存里面没有从数据库里面读取公司信息
					compInfo = this.compInfoMapper.findById(entity.getCompId());
				}
				if(compInfo==null){//公司信息为空执行下次循环
					continue;
				}
				if(compInfo.getCompStatus()!=1){//公司信息为空执行下次循环
					continue;
				}
				if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
					netInfo = this.netInfoMapper.findById(entity.getNetId());
				}
				//添加网络信息
				// 添加网络信息
				VO_NetInfo voNetInfo = new VO_NetInfo();
				voNetInfo.setId(netInfo.getNetId().toString());
				voNetInfo.setName(netInfo.getNetName());
				netList.add(voNetInfo);
				
				if(netId!=null){//网络条件查询过滤   必须放在添加网络信息之后
					if(!compInfo.getBelongToNetId().equals(netId)){
						continue;
					}
				}
				//查询公司负责人信息
				BasCompbusiness	compbusiness = this.ehcacheService.get("compBusinessCache", entity.getCompId().toString(), BasCompbusiness.class);
				if (compbusiness == null) {
					compbusiness = basCompbusinessMapper.findById(entity.getCompId());
					this.ehcacheService.put("compBusinessCache", entity.getCompId().toString(), compbusiness==null?"":JSON.toJSONString(compbusiness));
				}
				if(compbusiness==null){
					compbusiness=new BasCompbusiness();
				}
				// 算距离
				Double distance = getDistance(compInfo.getLatitude()==null?null:compInfo.getLatitude().doubleValue(), compInfo.getLongitude()==null?null:compInfo.getLongitude().doubleValue(), Double.valueOf(latitude), Double.valueOf(longitude));
				compList.add(getCompInfo(compbusiness, compInfo, netInfo, distance==null?null:BigDecimal.valueOf(distance)));
			}

		}

		data.put("netList", getNetList(netList));
		data.put("expCompList", getCompList(compList));
		return data;
	}

	/**
	 * 
	 * @Description: 获取网络LIST
	 * @author feng.wang
	 * @date 2014-11-1下午15:23:17
	 */
	// 网络信息去重复
	private static List<VO_NetInfo> getNetList(List<VO_NetInfo> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			int n = 1;
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getId().equals(list.get(i).getId())) {
					n++;
					list.remove(j);
				}
			}
			list.get(i).setName(list.get(i).getName());
			list.get(i).setTotal(n);
		}
		// 按照总数进行排序
		Collections.sort(list, new Comparator<VO_NetInfo>() {
			public int compare(VO_NetInfo a1, VO_NetInfo a2) {
				BigDecimal total1 = BigDecimal.valueOf(Long.valueOf(a1.getTotal()));
				BigDecimal total2 = BigDecimal.valueOf(Long.valueOf(a2.getTotal()));
				return total2.compareTo(total1);
			}
		});
		return list;
	}
	
	//公司信息去重复
	private static List<VO_CompInfo> removeDuplicate(List<VO_CompInfo> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getCompId().equals(list.get(i).getCompId())) {
					list.remove(j);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 公司信息排序
	 * @author feng.wang
	 * @date 2014-11-1下午15:23:17
	 */
	private List<VO_CompInfo> getCompList(List<VO_CompInfo> list) {
		//公司信息去重
		removeDuplicate(list);
		// 按照距离排序
		Collections.sort(list, new Comparator<VO_CompInfo>() {
			public int compare(VO_CompInfo a1, VO_CompInfo a2) {

				BigDecimal distance1 = a1.getDistance();
				BigDecimal distance2 = a2.getDistance();

				if (distance1 == null)
					distance1 = BigDecimal.valueOf(999999999.0);
				if (distance2 == null)
					distance2 = BigDecimal.valueOf(999999999.0);
				return distance1.compareTo(distance2);
			}
		});
		// 按照认证状态再排序
		Collections.sort(list, new Comparator<VO_CompInfo>() {
			public int compare(VO_CompInfo a1, VO_CompInfo a2) {
				BigDecimal cooperationLogo1 = BigDecimal.valueOf(Long.valueOf(a1.getCooperationLogo()));
				BigDecimal cooperationLogo2 = BigDecimal.valueOf(Long.valueOf(a2.getCooperationLogo()));
				return cooperationLogo2.compareTo(cooperationLogo1);
			}
		});
		
		return list;
	}

	/**
	 * 
	 * @Description: 返回公司相关信息
	 * @author feng.wang
	 * @date 2014-11-1下午16:23:17
	 */
	private VO_CompInfo getCompInfo(BasCompbusiness compbusiness, BasCompInfo compInfo, BasNetInfo netInfo, BigDecimal distance) {
		String cooperationLogo = "";
		if ("1003".equals(compInfo.getCompTypeNum())) {
			cooperationLogo = "0";
		} else {
			cooperationLogo = "1";
		}
		VO_CompInfo voCompInfo = new VO_CompInfo();
		voCompInfo.setCompId(compInfo.getCompId());//公司ID
		voCompInfo.setCooperationLogo(cooperationLogo);//合作关系
		voCompInfo.setNetId(netInfo.getNetId());//网络ID
		voCompInfo.setNetName(netInfo.getNetName());//网络名称
		voCompInfo.setNetTel(netInfo.getTelephone());//网络电话
		voCompInfo.setCompName(compInfo.getCompName());//公司名称
		voCompInfo.setCompAddress(compInfo.getCompAddress());//公司地址
		voCompInfo.setCompAddressId(compInfo.getCompAddressId());//公司地址ID
		voCompInfo.setDistance(distance);//距离
		voCompInfo.setLongitude(compInfo.getLongitude());//公司经度
		voCompInfo.setLatitude(compInfo.getLatitude());//公司纬度
		voCompInfo.setResponsibleTelephone(compbusiness.getResponsibleTelephone());//公司负责人电话
		voCompInfo.setResponsible(compbusiness.getResponsible());//公司负责人
		if (PubMethod.isEmpty(compInfo.getCompMobile())) {// 公司手机
			voCompInfo.setCompMobile(compbusiness.getResponsibleTelephone());// 公司手机为空显示负责人手机
		} else {
			voCompInfo.setCompMobile(compInfo.getCompMobile());// 公司手机
		}
		if (PubMethod.isEmpty(compInfo.getCompTelephone())) {// 公司座机
			voCompInfo.setCompTelephone(netInfo.getTelephone());// 公司座机为空显示网络400电话
		} else {
			voCompInfo.setCompTelephone(compInfo.getCompTelephone());// 公司座机
		}
		return voCompInfo;
	}

	/**
	 * 获取坐标间距离
	 * 
	 * @param voParcelinfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Double getDistance(Double lat, Double lng, Double Latitude, Double Longitude) {

		if (!PubMethod.isEmpty(lat) && !PubMethod.isEmpty(lng) && !PubMethod.isEmpty(Latitude) && !PubMethod.isEmpty(Longitude))
			return DistanceUtil.getDistance(lat, lng, Latitude, Longitude) / 1000;
		else
			return null;
	}

	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @Description: 公司信息获取公司派送范围
	 * @author feng.wang
	 * @date 2014-11-3下午13:23:17
	 * @param compId
	 *            公司ID
	 */
	@Override
	public List getCompareaList(Long compId) {
		List list=(List) this.ehcacheService.get("compSendRangeCache", compId+"yes",List.class);
		if(list==null){
			list=this.expCompAreaAddressMapper.getCompareaList(compId);
			this.ehcacheService.put("compSendRangeCache", compId+"yes", list);
		}
		return list;
	}
	
	/**
	 * 
	 * @Description: 根据公司ID获取公司不派送范围
	 * @author feng.wang
	 * @date 2014-11-3下午13:40:17
	 * @param compId
	 *            公司ID
	 */
	@Override
	public List getExceedareaList(Long compId) {
		List list=(List) this.ehcacheService.get("compSendRangeCache", compId+"no",List.class);
		if(list==null){
			list=this.expExceedareaAddressMapper.getExceedareaList(compId);
			this.ehcacheService.put("compSendRangeCache", compId+"no", list);
		}
		return list;
	}

	/**
	 * @Method: getExceedareaList
	 * @Description: 根据经纬度和查询方圆几公里站点信息
	 * @param latitude
	 *            地址纬度
	 * @param longitude
	 *            地址经度
	 * @return
	 * @since jdk1.6
	 */
	@Override
	public List getNearSitsList(String latitude, String longitude) {
		//类型转换
		Double lat=Double.valueOf(latitude);
		Double lng=Double.valueOf(longitude);
		//算对角线经纬度
		int dis=5;//设置5公里范围内
		double EARTH_RADIUS = 6378.137 * 1000;
		double dlng = Math.abs(2 * Math.asin(Math.sin(dis*1000 / (2 * EARTH_RADIUS)) / Math.cos(lat)));
		dlng = dlng*180.0/Math.PI;        //弧度转换成角度
		double dlat = Math.abs(dis*1000 / EARTH_RADIUS);
		dlat = dlat*180.0/Math.PI;     //弧度转换成角度
		double minLat=lat - dlat;
		double maxLat=lat + dlat;
		double minLng=lng - dlng;
		double maxLng=lng + dlng;
		//先从缓存中取数据缓存中没有从数据库里面取
		List list=this.ehcacheService.get("nearSiteCache", latitude+"_"+longitude,List.class);
		if(list!=null&&list.size()>0){
			//缓存中有数据直接返回数据
			return list;
		}else{
			//组装参数
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("minLat", minLat);
			map.put("maxLat", maxLat);
			map.put("minLng", minLng);
			map.put("maxLng", maxLng);
			list=this.expCompAreaAddressMapper.getNearSite(map);
			List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			//取方圆5公里的数据
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> compMap=(Map<String, Object>) list.get(i);
					double distance=this.getDistance(lat, lng, Double.parseDouble(compMap.get("lat").toString()), Double.parseDouble(compMap.get("lng").toString()))/1000;
					if(distance<=dis){
						compMap.put("distance", distance);
						dataList.add(compMap);
					}
				}
			}
			//按照距离排序
			Collections.sort(dataList, new ComparatorList());
			List<Map<String,Object>> newList=new ArrayList<Map<String,Object>>();
			//只取前六条数据
			if(dataList!=null&&dataList.size()>6){
				for(int i=0;i<6;i++){
					Map<String,Object> newMap=(Map<String, Object>) list.get(i);
					String compTypeNum=(String) newMap.get("compTypeNum");
					if("1003".equals(compTypeNum)){
						Long belongToNetId=(Long) newMap.get("belongToNetId");
						BasNetInfo netInfo = ehcacheService.get("netCache", belongToNetId.toString(), BasNetInfo.class);
						if (PubMethod.isEmpty(netInfo)) {
							netInfo=this.netInfoMapper.findById(belongToNetId);
						}
						newMap.put("contactTel", netInfo.getTelephone());
					}else{
						String compMob=(String) newMap.get("compMob");
						String compTel=(String) newMap.get("compTel");
						if(!PubMethod.isEmpty(compMob)){
							newMap.put("contactTel", compMob);
						}else{
							newMap.put("contactTel", compTel);
						}
					}
					newList.add(newMap);
				}
				//放入缓存
				this.ehcacheService.put("nearSiteCache", latitude+"_"+longitude, newList);
				return newList;
			}else{
				//放入缓存
				this.ehcacheService.put("nearSiteCache", latitude+"_"+longitude, dataList);
				return dataList;
			}
		}
	}
	
	public class ComparatorList implements Comparator{
		public int compare(Object arg0, Object arg1) {
			  int flag;
			  Map map0=(Map)arg0;
			  Map map1=(Map)arg1;
			  double distance0=Double.parseDouble(map0.get("distance").toString());
			  double distance1=Double.parseDouble(map1.get("distance").toString());
			  if(distance0>distance1)
				  flag=-1;
			  else if(distance0<distance1)
				  flag=1;
			  else
				  flag=0;
			  return flag;
			 }
	}
	
	/** 
     * @Method: getNewCompList 
     * @Description: 查询最新加盟站点
     * @param total 总数
     * @return
     * @since jdk1.6
    */
	@Override
    public List<VO_CompInfo> getNewCompList(Long total) {
		List<VO_CompInfo> compList=new ArrayList<VO_CompInfo>();
		List list=this.basCompAuditMapper.getNewCompauditList(total);
		String tempPath=pictrueReadUrl+readFilename; //图片路径
		for(int i=0;i<list.size();i++){
			Long compId=(Long) list.get(i);
			BasCompInfo	compInfo = this.ehcacheService.get("compCache", compId.toString(), BasCompInfo.class);
			if (compInfo == null) {//缓存里面没有从数据库里面读取公司信息
				compInfo = this.compInfoMapper.findById(compId);
			}
			BasNetInfo netInfo = this.ehcacheService.get("netCache", compInfo.getBelongToNetId().toString(), BasNetInfo.class);
			if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
				netInfo = this.netInfoMapper.findById(compInfo.getBelongToNetId());
			}
			VO_CompInfo voComp=new VO_CompInfo();
			voComp.setCompId(compInfo.getCompId());
			// edit by zmn 2014.12.4
			voComp.setProvinceId(Long.parseLong((compInfo.getCompAddressId()+"").substring(0,2)));
			// edit by zmn 2014.12.4
			voComp.setCompName(compInfo.getCompName());
			voComp.setImageUrl(tempPath+netInfo.getNetId().toString()+".png");//网络LOGO的URL
			voComp.setNetId(netInfo.getNetId());
			voComp.setNetName(netInfo.getNetName());
			compList.add(voComp);
			
		}
        return compList;
    }

    /** 
     * @Method: getExpCompElectronicLaction 
     * @Description: 根据公司ID获取电子围栏信息
     * @param compId 公司ID
     * @return
     * @since jdk1.6
    */
	@Override
	public List<ExpCompElectronicFence> getExpCompElectronicLaction(Long compId) {
		List list=this.ehcacheService.get("compFenceCache", compId.toString(),List.class);
		List<ExpCompElectronicFence> fenceList=null;
		if(list!=null&&list.size()>0){
			fenceList=JSON.parseArray(list.toString(), ExpCompElectronicFence.class);
		}
		if(fenceList==null){
			fenceList=this.expCompElectronicFenceMapper.getExpCompElectronicLaction(compId);
			this.ehcacheService.put("compFenceCache", compId.toString(), list);
		}
		return fenceList;
	}

	/** 
     * @Method: getSiteDetail 
     * @Description: 根据公司ID获取站点详情相关信息
     * @param compId 公司ID
     * @return
     * @since jdk1.6
    */
	@Override
	public VO_CompInfo getSiteDetail(Long compId){
		BasCompInfo	compInfo = this.ehcacheService.get("compCache", compId.toString(), BasCompInfo.class);
		if (compInfo == null) {//缓存里面没有从数据库里面读取公司信息
			compInfo = this.compInfoMapper.findById(compId);
		}
		BasNetInfo netInfo = this.ehcacheService.get("netCache", compInfo.getBelongToNetId().toString(), BasNetInfo.class);
		if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
			netInfo = this.netInfoMapper.findById(compInfo.getBelongToNetId());
		}
		String tempPath=pictrueReadUrl+readFilename; //图片路径
		//查询公司负责人信息
		BasCompbusiness	compbusiness = this.ehcacheService.get("compBusinessCache", compId.toString(), BasCompbusiness.class);
		if (compbusiness == null) {
			compbusiness = basCompbusinessMapper.findById(compId);
			this.ehcacheService.put("compBusinessCache", compId.toString(), compbusiness==null?"":JSON.toJSONString(compbusiness));
		}
		VO_CompInfo voCompInfo=new VO_CompInfo();
		voCompInfo.setCompId(compId);
		voCompInfo.setCompName(compInfo.getCompName());
		voCompInfo.setCompAddress(compInfo.getCompAddress());
		voCompInfo.setNetId(netInfo.getNetId());
		voCompInfo.setNetName(netInfo.getNetName());
		voCompInfo.setImageUrl(tempPath+netInfo.getNetId().toString()+".png");//网络LOGO的URL
		voCompInfo.setResponsible(compbusiness==null?"":compbusiness.getResponsible());
		if (PubMethod.isEmpty(compInfo.getCompMobile())) {// 公司手机
			voCompInfo.setCompMobile(compbusiness==null?"":compbusiness.getResponsibleTelephone());// 公司手机为空显示负责人手机
		} else {
			voCompInfo.setCompMobile(compInfo.getCompMobile());// 公司手机
		}
		if (PubMethod.isEmpty(compInfo.getCompTelephone())) {// 公司座机
			voCompInfo.setCompTelephone(netInfo.getTelephone());// 公司座机为空显示网络400电话
		} else {
			voCompInfo.setCompTelephone(compInfo.getCompTelephone());// 公司座机
		}
		voCompInfo.setLongitude(compInfo.getLongitude());
		voCompInfo.setLatitude(compInfo.getLatitude());
		if(!PubMethod.isEmpty(compInfo.getCompAddressId())){
			Long provinceId;
			DicAddressaid addressaid = this.ehcacheService.get("addressCache", compInfo.getCompAddressId().toString(), DicAddressaid.class);
			if (addressaid == null) {
				provinceId=this.dicAddressaidMapper.getProvinceId(compInfo.getCompAddressId());
			}else{
				provinceId=addressaid.getProvinceId();
			}
			voCompInfo.setProvinceId(provinceId);
		}
		if("1003".equals(compInfo.getCompTypeNum())){
			voCompInfo.setCooperationLogo("0");
		}else{
			voCompInfo.setCooperationLogo("1");
		}
		
		return voCompInfo;
	}

	@Override
	public Map<String, Object> queryAllCompInfo(Integer pageNum,Integer pageSize,String netId, Short compType,
			String compName, String memberPhone, Short createType,
			 String beginTime, String endTime, Short status,String province) {
		List<Map<String, Object>> list=compInfoMapper.queryAllCompInfo((pageNum-1)*pageSize,pageSize,netId, compType, compName,
				memberPhone, createType,  beginTime, endTime, status,province);
		if(!list.isEmpty()){
			for(Map map:list){
				String loginId=(String)map.get("loginId");
				if(!PubMethod.isEmpty(loginId)){
					StringBuffer sb=new StringBuffer(loginId);
					sb.replace(3, 6, "***");
				//	String phone =(String)map.get("loginId");
					System.out.println("shouji================="+sb.toString());
					map.put("loginId", sb.toString());
				}
					
				
			}
		}
		
		int total=compInfoMapper.getTotalNum2(netId, compType, compName,
				memberPhone, createType,  beginTime, endTime, status,province);
		Map<String,Object> num=compInfoMapper.getNum();
		Map<String, Object> map=new HashMap<String, Object>();
	    map.put("total", total);
	    map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		map.put("record", list);
		map.put("num", num);
		return map;
	}
	
	@Override
	public Map<String, Object> expBasCompInfoList(Integer pageNum,Integer pageSize,String netId, Short compType,
			String compName, String memberPhone, Short createType,
			 String beginTime, String endTime, Short status,String province) {
		List<Map<String, Object>> list=compInfoMapper.expBasCompInfoList(netId, compType, compName,
				memberPhone, createType,  beginTime, endTime, status,province);
		
		int total=compInfoMapper.getTotalNum2(netId, compType, compName,
				memberPhone, createType,  beginTime, endTime, status,province);
		Map<String, Object> map=new HashMap<String, Object>();
	    map.put("total", total);
		map.put("record", list);
		return map;
	}
	

	@Override
	public Map<String, Object> queryMemberByCompId(Long compId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map=compInfoMapper.queryMemberByCompId(compId);
		if(PubMethod.isEmpty(map)){
			map=new HashMap<String, Object>();
			map.put("memberPhone", "");
			map.put("compTelePhone", "");
		}
		return map;
	}
}
