package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompbusinessMapper;
import net.okdi.api.dao.ExpCompAuditLogMapper;
import net.okdi.api.dao.ExpCompRelationMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasCompbusiness;
import net.okdi.api.entity.ExpCompAuditLog;
import net.okdi.api.entity.ExpCompRelation;
import net.okdi.api.service.BusinessBranchService;
import net.okdi.api.service.RobInfoService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

@Service
public class BusinessBranchServiceImpl extends BaseServiceImpl<ExpCompRelation> implements BusinessBranchService{

	private static final String CONTENT = "正在使用好递站点系统，向您发出关联邀请，请点击链接查看http://www.okdi.net/express.jsp";
	@Autowired
	private ExpCompRelationMapper expCompRelationMapper;
	@Autowired
	private BasCompbusinessMapper basCompbusinessMapper;
	@Autowired
	private ExpCompAuditLogMapper expCompAuditLogMapper;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private RobInfoService robInfoService;
	@Autowired
	NoticeHttpClient noticeHttpClient;
	@Override
	public BaseDao getBaseDao() {
		return null;
	}

	@Override
	public Map<String, Object> queryCompInfoByParentCompId(String compName, String memberPhone, Long parentCompId,Long netId) {
		Map<String,Object> map = new HashMap<String, Object>();
		BasCompInfo basCompInfo = this.robInfoService.queryBasCompInfo(parentCompId); 
		String compAddress = basCompInfo.getCompAddress().substring(0, 2);
		map = this.queryCompInfo(compName, memberPhone,compAddress,netId);//查询出营业分部的基本信息
		if(!PubMethod.isEmpty(map)){//查询出信息需要判断是否已经建立关联或者处于待审核状态
			Long compId = Long.parseLong(String.valueOf(map.get("compId")));
			BasCompbusiness basCompbusiness = this.queryBasCompbusiness(compId);
			map.put("responsible", basCompbusiness.getResponsible());//负责人
			map.put("responsibleTelephone", basCompbusiness.getResponsibleTelephone());//负责人手机号
			
			if(this.isRightConditions(compId) == false){//如果不符合条件
				map = null;
			}
		}
		return map;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>营业分部查询站点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-10 下午3:56:08</dd>
	 * @param compName
	 * @param memberPhone
	 * @param compId
	 * @param netId
	 * @return
	 * @since v1.0
	 */
	@Override
	public Map<String, Object> queryCompInfoByCompId(String compName, String memberPhone, Long compId, Long netId) {
		Map<String,Object> map = new HashMap<String, Object>();
		BasCompInfo basCompInfo = this.robInfoService.queryBasCompInfo(compId); 
		String compAddress = basCompInfo.getCompAddress().substring(0, 2);
		map = this.getCompInfo(compName, memberPhone,compAddress,netId);//查询出站点的基本信息
		if(!PubMethod.isEmpty(map)){
			Long parentCompId = Long.parseLong(String.valueOf(map.get("parentCompId")));
			BasCompbusiness basCompbusiness = this.queryBasCompbusiness(parentCompId);
			map.put("responsible", basCompbusiness.getResponsible());//负责人
			map.put("responsibleTelephone", basCompbusiness.getResponsibleTelephone());//负责人手机号
			if(this.isCorrectConditions(parentCompId,compId) == false){
				map = null;
			}
		}
		return map;
	}

	public boolean isCorrectConditions(Long parentCompId,Long compId) {
		boolean isCorrectConditions = true;
		ExpCompAuditLog expCompAuditLog = this.ehcacheService.get("expLogCache", String.valueOf(parentCompId), ExpCompAuditLog.class);
		ExpCompRelation expCompRelation = this.ehcacheService.get("expRelationCache", String.valueOf(parentCompId), ExpCompRelation.class);
		if(PubMethod.isEmpty(expCompAuditLog)){
			expCompAuditLog = this.expCompAuditLogMapper.queryAuditLogByCompId(parentCompId,compId);
			this.ehcacheService.put("expLogCache", String.valueOf(parentCompId), expCompAuditLog);
		}
		if(PubMethod.isEmpty(expCompRelation)){
			expCompRelation =  this.expCompRelationMapper.queryRelationByCompId(parentCompId);
			this.ehcacheService.put("expRelationCache", String.valueOf(parentCompId), expCompRelation);
		}
		if(!PubMethod.isEmpty(expCompAuditLog) && String.valueOf(expCompAuditLog.getCompId()).equals(String.valueOf(compId))){
			isCorrectConditions = false;
		}
		if(!PubMethod.isEmpty(expCompRelation) && String.valueOf(expCompRelation.getCompId()).equals(String.valueOf(compId))){
			isCorrectConditions = false;
		}
		return isCorrectConditions;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getCompInfo(String compName, String memberPhone, String compAddress, Long netId) {
		Map<String,Object> map = new HashMap<String, Object>();
		String cacheKey = compName+"-"+memberPhone;
		map = this.ehcacheService.get("expCompInfoCache", cacheKey, Map.class);
		if(PubMethod.isEmpty(map)){
			map = this.expCompAuditLogMapper.getCompInfo(compName, memberPhone,compAddress,netId);
		}
		if(!PubMethod.isEmpty(map)){
			map.put("compAddress", String.valueOf(map.get("compAddress")).replace("-", "").replace("|", ""));
		}
		
		return map;
	}
	public boolean isRightConditions(Long compId) {
		boolean isRightConditions = true;
		ExpCompAuditLog expCompAuditLog = this.ehcacheService.get("expLogCache", String.valueOf(compId), ExpCompAuditLog.class);
		if(PubMethod.isEmpty(expCompAuditLog)){
			expCompAuditLog = this.expCompAuditLogMapper.queryAuditLogByCompId(null,compId);
			this.ehcacheService.put("expLogCache", String.valueOf(compId), expCompAuditLog);
		}
		ExpCompRelation expCompRelation = this.ehcacheService.get("expRelationCache", String.valueOf(compId), ExpCompRelation.class);
		if(PubMethod.isEmpty(expCompRelation)){
			expCompRelation =  this.expCompRelationMapper.queryRelationByCompId(compId);
			this.ehcacheService.put("expRelationCache", String.valueOf(compId), expCompRelation);
		}
		if((!PubMethod.isEmpty(expCompAuditLog)&&(expCompAuditLog.getStatus() !=3 &&expCompAuditLog.getStatus() !=2)|| !PubMethod.isEmpty(expCompRelation))){//如果该营业分部在日志表中处于待审核或者已通过状态或者关系表中存在
			isRightConditions = false;
		}
		return isRightConditions;
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryCompInfo(String compName, String memberPhone, String compAddress, Long netId) {
		Map<String,Object> map = new HashMap<String, Object>();
		String cacheKey = compName+"-"+memberPhone;
		map = this.ehcacheService.get("expCompInfoCache", cacheKey, Map.class);
		if(PubMethod.isEmpty(map)){
			map = this.expCompAuditLogMapper.queryCompInfo(compName, memberPhone,compAddress,netId);
		}
		if(!PubMethod.isEmpty(map)){
			map.put("compAddress", String.valueOf(map.get("compAddress")).replace("-", "").replace("|", ""));
		}
		return map;
	}
	@Override
	public String parseResult(String info) {
		return info;
	}
	@Override
	public void sendInvitationMessage(String compTypeNum,String memebrPhone, String compName,Long compId) {
		if("1050".equals(compTypeNum)){
			updateCompRelation(compId, null);
		}
		String content = compName+CONTENT;
		noticeHttpClient.doSmsSend("02", 0L, memebrPhone, content, String.valueOf(compId));
	}
	@Override
	public Map<String,Object> queryCompRelationInfo(Long compId, Long userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		ExpCompAuditLog expCompAuditLog = this.expCompAuditLogMapper.queryCompRelationInfo(compId,userId);
		if(PubMethod.isEmpty(expCompAuditLog)){
			map.put("compId", expCompAuditLog.getCompId());
			BasCompInfo basCompInfo = this.robInfoService.queryBasCompInfo(expCompAuditLog.getCompId());
			map.put("compName", basCompInfo.getCompName());
			map.put("status", expCompAuditLog.getStatus());
			map.put("parentCompId", expCompAuditLog.getParentCompId());
			map.put("logId", expCompAuditLog.getId());
		}
		return map;
	}
	@Override
	public void updateCompRelation(Long compId,Long parentCompId) {
		//先判断该营业分部是否已被添加
		ExpCompRelation expCompRelation = this.ehcacheService.get("expRelationCache", String.valueOf(compId), ExpCompRelation.class);
		if(PubMethod.isEmpty(expCompRelation)){
			expCompRelation =  this.expCompRelationMapper.queryRelationByCompId(compId);
			this.ehcacheService.put("expRelationCache", String.valueOf(compId), expCompRelation);
		}
		if(!PubMethod.isEmpty(expCompRelation)){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.createCompRelation.002","此营业分部已经被其他站点关联");
		}
		this.expCompAuditLogMapper.deleteCompRelation(compId);
		clearCache();
	}

	@Override
	public void deleteCompRelation(Long logId, Long compId, Long parentCompId) {
		ExpCompAuditLog expCompAuditLog = this.ehcacheService.get("expLogCacheByLogId", String.valueOf(logId), ExpCompAuditLog.class);
		if(PubMethod.isEmpty(expCompAuditLog)){
			expCompAuditLog = this.expCompAuditLogMapper.findById(logId);
			this.ehcacheService.put("expLogCacheByLogId", String.valueOf(logId), expCompAuditLog);
		}
		if(PubMethod.isEmpty(expCompAuditLog)){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.deleteCompRelation.001","通过logId查询日志表异常");	
		}
		if(expCompAuditLog.getStatus() != 1){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.deleteCompRelation.002","通过logId查询日志表所对应状态不为已通过");		
		}
		//解除关系 先日志表中修改状态 之后删除关联表
		this.expCompAuditLogMapper.updateCompRelation(logId);
		this.expCompRelationMapper.deleteCompRelation(compId,parentCompId);
		clearCache();
	}
	@Override
	public ExpCompRelation queryCompRelationByBusinessId(Long compId) {
		if(PubMethod.isEmpty(compId)){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.queryCompRelationByBusinessId.001", "compId营业分部id不能为空 ");
		}
		return expCompRelationMapper.queryCompRelationByBusinessId(compId);
	}
	@Override
	public void createCompRelation(Long userId,Long parentCompId,Long compId,Long netId) {
		//先判断该营业分部是否已被添加
		ExpCompRelation expCompRelation = this.ehcacheService.get("expRelationCache", String.valueOf(compId), ExpCompRelation.class);
		if(PubMethod.isEmpty(expCompRelation)){
			expCompRelation =  this.expCompRelationMapper.queryRelationByCompId(compId);
			this.ehcacheService.put("expRelationCache", String.valueOf(compId), expCompRelation);
		}
		if(!PubMethod.isEmpty(expCompRelation)){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.createCompRelation.002","此营业分部已经被其他站点关联");
		}
		//如果当前登录的角色是站点 直接关联营业分部不需要营业分部审核，日志表和关系表各一条
		this.expCompRelationMapper.doAddRelation(IdWorker.getIdWorker().nextId(),netId,compId,parentCompId,new Date());
		ExpCompAuditLog expCompAuditLog = new ExpCompAuditLog();
		expCompAuditLog.setId(IdWorker.getIdWorker().nextId());
		expCompAuditLog.setCompId(compId);
		expCompAuditLog.setParentCompId(parentCompId);
		expCompAuditLog.setCreateUserId(userId);
		expCompAuditLog.setCreateTime(new Date());
		int n = this.expCompAuditLogMapper.updateAuditStatus(expCompAuditLog);
		if( n == 0){
			int m = this.expCompAuditLogMapper.doAddAuditLog(expCompAuditLog);
			if(m == 0 ){
				throw new ServiceException("openapi.BusinessBranchServiceImpl.createCompRelation.001","站点关联营业分部插入日志表异常");
			}
		}
		clearCache();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryConsumingBranch(Long parentCompId) {
		//先从站点营业分部关系表的缓存中取出该站点下所有的营业分部id
		List<Map<String,Object>> listExpRelation = this.ehcacheService.get("expRelationListCache", String.valueOf(parentCompId), ArrayList.class);
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		if(PubMethod.isEmpty(listExpRelation)){//如果缓存取出来的为空 则需要去数据库中查出来
			listExpRelation = this.expCompRelationMapper.selectRelation(parentCompId);
			this.ehcacheService.put("expRelationListCache", String.valueOf(parentCompId), listExpRelation);
		}
		listResult = this.queryConsumingBranchByCompId(listExpRelation);
		return listResult;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryUnConsumingBranch(Long parentCompId) {
		//先从站点营业分部日志表的缓存中取出该站点下所有的营业分部id
		List<Map<String,Object>> listExpLog = this.ehcacheService.get("expLogListCache", String.valueOf(parentCompId), ArrayList.class);
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		if(PubMethod.isEmpty(listExpLog)){//如果缓存取出来的为空 则需要去数据库中查出来
			listExpLog = this.expCompAuditLogMapper.selectLog(parentCompId);
			this.ehcacheService.put("expLogListCache", String.valueOf(parentCompId), listExpLog);
		}
		listResult = this.queryConsumingBranchByCompId(listExpLog);
		return listResult;
	}

	private List<Map<String, Object>> queryConsumingBranchByCompId(List<Map<String, Object>> listExpRelation) {
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : listExpRelation){
			Map<String,Object> mapResult = new HashMap<String,Object>();
			Long compId = Long.parseLong(String.valueOf(map.get("compId")));//营业分部id
			BasCompInfo BasCompInfo = this.robInfoService.queryBasCompInfo(compId);
			BasCompbusiness basCompbusiness = this.queryBasCompbusiness(compId);
			mapResult.put("compName", BasCompInfo.getCompName());//营业分部名称
			mapResult.put("responsible", basCompbusiness.getResponsible());//负责人
			mapResult.put("responsiblePhone", basCompbusiness.getResponsibleTelephone());//负责人手机号
			mapResult.put("compAddress", BasCompInfo.getCompAddress().replace("-", "").replace("|", ""));//地址
			mapResult.put("areaColor", map.get("areaColor"));//片区颜色
			mapResult.put("relationId", map.get("relationId"));//关系表id
			mapResult.put("compId", map.get("compId"));//营业分部id
			mapResult.put("logId", map.get("logId"));//日志id
			mapResult.put("status", map.get("status"));//状态
			listResult.add(mapResult);
		}
		return listResult;
	}

	@Override
	public void auditBranch(Long logId, Short flag, Long compId, Long parentCompId,Long netId,Long userId,String refuseDesc) {
		//首先判断该条待审核记录是否还存在
		ExpCompAuditLog expCompAuditLog = this.expCompAuditLogMapper.findById(logId);
		if(PubMethod.isEmpty(expCompAuditLog)){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.auditBranch.001","该条日志已经不存在");
		}else if(expCompAuditLog.getStatus() != 0){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.auditBranch.002","状态已经发生改变");
		}
		if(flag ==1 ){// 审核通过 
			this.auditAgress(logId,compId,parentCompId);//修改日志表中待审核状态为已通过
			this.relationAgress(netId,compId,parentCompId);//关系表中插入一条新的记录 
		}else if(flag == 2){//审核拒绝
			this.auditRefuse(logId,compId,parentCompId,refuseDesc);//修改日志表中审核状态为拒绝
		}
		clearCache();
		
	}

	private void auditRefuse(Long logId, Long compId, Long parentCompId,String refuseDesc) {
		ExpCompAuditLog expCompAuditLog = new ExpCompAuditLog();
		expCompAuditLog.setCompId(compId);
		expCompAuditLog.setParentCompId(parentCompId);
		expCompAuditLog.setCreateCompId(parentCompId);
		expCompAuditLog.setRefuseDesc(refuseDesc);
		int n = this.expCompAuditLogMapper.auditRefuse(expCompAuditLog);
		if(n == 0){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.auditRefuse.001","审核通过操作修改日志表状态异常");	
		}
		clearCache();
	}

	private void relationAgress(Long netId, Long compId, Long parentCompId) {
		ExpCompRelation expCompRelation = new ExpCompRelation();
		expCompRelation.setId(IdWorker.getIdWorker().nextId());
		expCompRelation.setParentNetId(netId);
		expCompRelation.setCompId(compId);
		expCompRelation.setParentCompId(parentCompId);
		expCompRelation.setAreaColor("#c2c2c2");
		expCompRelation.setCreateTime(new Date());
		expCompRelation.setRelationStatus((byte)1);
		expCompRelation.setSerialNumber((byte)1);
		expCompRelation.setCompLevel((byte)1);
		this.expCompRelationMapper.insert(expCompRelation);
		clearCache();
		
	}

	private void auditAgress(Long logId, Long compId, Long parentCompId) {
		ExpCompAuditLog expCompAuditLog = new ExpCompAuditLog();
		expCompAuditLog.setCompId(compId);
		expCompAuditLog.setParentCompId(parentCompId);
		expCompAuditLog.setCreateCompId(parentCompId);
		int n = this.expCompAuditLogMapper.auditAgress(expCompAuditLog);
		if(n == 0){
			
			throw new ServiceException("openapi.BusinessBranchServiceImpl.auditAgress.001","审核通过操作修改日志表状态异常");
		}
		clearCache();
	}

	@Override
	public void relieveRelation(Long relationId, Long compId, Long parentCompId) {
		ExpCompRelation expCompRelation =  this.expCompRelationMapper.findById(relationId);
		if(PubMethod.isEmpty(expCompRelation)){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.relieveRelation.001","站点解除营业分部通过关系id查询记录异常");	
		}
		int m = this.expCompRelationMapper.deleteById(relationId);
		if(m == 0 ){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.relieveRelation.002","站点解除营业分部删除关系表异常");	
		}
		int n = this.expCompAuditLogMapper.relieveLog(expCompRelation);
		if(n == 0 ){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.relieveRelation.003","站点解除营业分部修改日志表异常");	
		}
		clearCache();
	}

	@Override
	public void editAreaColor(Long relationId, String areaColor,Long parentCompId) {
		int m = this.expCompRelationMapper.editAreaColor(relationId,areaColor);
		if(m == 0 ){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.editAreaColor.001","管理营业分部修改片区颜色异常");	
		}
		ehcacheService.remove("expRelationCache", String.valueOf(parentCompId));
		ehcacheService.remove("areaFenceCache", String.valueOf(parentCompId));
		ehcacheService.remove("memberAndBranchCache", String.valueOf(parentCompId));
		clearCache();
	}

	@Override
	public Map<String,Object> queryRelationInfo(Long compId) {
		Map<String,Object> map = new HashMap<String,Object>();
		ExpCompAuditLog expCompAuditLog = this.ehcacheService.get("expLogCache", String.valueOf(compId), ExpCompAuditLog.class);
		if(PubMethod.isEmpty(expCompAuditLog)){
			expCompAuditLog = this.expCompAuditLogMapper.queryAuditLog(compId);
			this.ehcacheService.put("expLogCache", String.valueOf(compId), expCompAuditLog);
		}
		if(!PubMethod.isEmpty(expCompAuditLog)){
			Long parentCompId = expCompAuditLog.getParentCompId();
			BasCompInfo basCompInfo = this.robInfoService.queryBasCompInfo(parentCompId);
			map.put("parentCompId", parentCompId);//站点id
			map.put("compName", basCompInfo.getCompName());//站点名称
			map.put("status", expCompAuditLog.getStatus());//日志表中的审核状态
			map.put("compId", compId);//营业分部id
			map.put("logId", expCompAuditLog.getId());//日志id
		}
		return map;
	}

	@Override
	public BasCompbusiness queryBasCompbusiness(Long compId) {
		BasCompbusiness basCompbusiness = this.ehcacheService.get("compBusinessCache", String.valueOf(compId), BasCompbusiness.class);
		if(PubMethod.isEmpty(basCompbusiness)){
			basCompbusiness = this.basCompbusinessMapper.findById(compId);
			this.ehcacheService.put("compBusinessCache", String.valueOf(compId), basCompbusiness);
		}
		return basCompbusiness;
	}

	@Override
	public ExpCompAuditLog queryStatus(Long parentCompId, Long compId) {
		ExpCompAuditLog expCompAuditLog = this.expCompAuditLogMapper.queryStatus(parentCompId,compId);
		return expCompAuditLog;
	}

	@Override
	public void addCompRelation(Long logId,Long userId, Long parentCompId, Long compId, Long netId) {
		updateCompRelation(compId, parentCompId);//如果出问题 回退
		ExpCompAuditLog expCompAuditLog1 = this.ehcacheService.get("expLogCache", String.valueOf(compId), ExpCompAuditLog.class);
		if(PubMethod.isEmpty(expCompAuditLog1)){
			expCompAuditLog1 = this.expCompAuditLogMapper.queryAuditLog(compId);
			this.ehcacheService.put("expLogCache", String.valueOf(compId), expCompAuditLog1);
		}
		if((!PubMethod.isEmpty(expCompAuditLog1)&&(expCompAuditLog1.getStatus() !=3 &&expCompAuditLog1.getStatus() !=2))){
			throw new ServiceException("openapi.BusinessBranchServiceImpl.addCompRelation.002","申请之前已有关联信息");
		} 
		ExpCompAuditLog expCompAuditLog = new ExpCompAuditLog();
		expCompAuditLog.setId(IdWorker.getIdWorker().nextId());
		expCompAuditLog.setCompId(compId);
		expCompAuditLog.setParentCompId(parentCompId);
		expCompAuditLog.setCreateCompId(parentCompId);
		expCompAuditLog.setCreateUserId(userId);
		expCompAuditLog.setCreateTime(new Date());
		int m = this.expCompAuditLogMapper.doUpdateAuditStatus(parentCompId,compId);
		if(m == 0 ){
			int n = this.expCompAuditLogMapper.doInsertAuditLog(expCompAuditLog);
			if(n == 0){
				throw new ServiceException("openapi.BusinessBranchServiceImpl.addCompRelation.001","营业分部关联站点添加日志表异常");		
			}
		}
		clearCache();
		
	}
	

	@Override
	public int isThrough(Long compId) {
		int flag = 0;
		ExpCompRelation expCompRelation = this.ehcacheService.get("expRelationCache", String.valueOf(compId), ExpCompRelation.class);
		if(PubMethod.isEmpty(expCompRelation)){
			expCompRelation =  this.expCompRelationMapper.queryRelationByCompId(compId);
			this.ehcacheService.put("expRelationCache", String.valueOf(compId), expCompRelation);
		}
		if(PubMethod.isEmpty(expCompRelation)){
			flag = 0;
		}else if(!PubMethod.isEmpty(expCompRelation)){
			flag = 1;
		}
		return flag;
	}
	public void  clearCache(){
		this.ehcacheService.removeAll("expCompInfoCache");
		this.ehcacheService.removeAll("expLogCache");
		this.ehcacheService.removeAll("expRelationCache");
		this.ehcacheService.removeAll("expRelationListCache");
		this.ehcacheService.removeAll("expLogListCache");
		this.ehcacheService.removeAll("expLogCacheByLogId");
		this.ehcacheService.removeAll("memberAndBranchCache");
	}

}
