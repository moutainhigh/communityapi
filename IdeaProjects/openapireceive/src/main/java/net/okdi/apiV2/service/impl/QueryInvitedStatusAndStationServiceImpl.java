package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.apiV2.dao.BasCompInfoMapperV2;
import net.okdi.apiV2.dao.BasEmployeeAuditMapperV2;
import net.okdi.apiV2.service.ExpressRegisterService;
import net.okdi.apiV2.service.QueryInvitedStatusAndStationService;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryInvitedStatusAndStationServiceImpl implements QueryInvitedStatusAndStationService{

	public static final Log logger = LogFactory.getLog(QueryInvitedStatusAndStationServiceImpl.class);
	@Autowired
	BasEmployeeAuditMapperV2 basEmployeeAuditMapperV2;
	@Autowired
	BasCompInfoMapperV2 basCompInfoMapperV2;
	@Autowired
	ExpressRegisterService expressRegisterService;
	
	/**
	 * 查询快递员状态
	 */
	@Override
	public Map<String,Object> queryInvitedStatusAndStation(String memberId){
		logger.info("QueryInvitedStatusAndStationServiceImpl   查询快递员状态");
		BasEmployeeAudit bas=this.basEmployeeAuditMapperV2.queryAuditComp(memberId);
		
		//String netNameString=this.basCompInfoMapperV2.queryNetName(String.valueOf(bas.getAuditComp()));
		Map<String,Object> map=expressRegisterService.queryAuditStatus(memberId);
		
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(!PubMethod.isEmpty(map.get("memberStatusSF"))){
			resultMap.put("realNameStatus", map.get("memberStatusSF"));//实名认证状态 -1 未完善/未提交 0 待审核  1通过 2拒绝	
		}else{
			resultMap.put("realNameStatus", "");//实名认证状态 -1 未完善/未提交 0 待审核  1通过 2拒绝
		}
		if(!PubMethod.isEmpty(map.get("memberStatusKD"))){
			resultMap.put("courierStatus", map.get("memberStatusKD")); //快递认证状态 -1 未完善/未提交 0 待审核  1通过 2拒绝	
		}else{
			resultMap.put("courierStatus", ""); //快递认证状态 -1 未完善/未提交 0 待审核  1通过 2拒绝
		}
		if(bas!=null){
		if(!PubMethod.isEmpty(bas.getAuditComp())){
			resultMap.put("netName", this.basCompInfoMapperV2.queryNetName(String.valueOf(bas.getAuditComp())));
		}else{   //快递网络名
			resultMap.put("netName","");
		}
		}else{
			resultMap.put("netName","");
		}				  
		
		return resultMap;
	}
	/**
	 * 判断是不是快递员
	 */
	@Override
	public boolean ifIsCourier(String memberId){
		int i=this.basEmployeeAuditMapperV2.ifCourie(memberId);
		
		if(i>0){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否选择了角色
	 */
	@Override
	public boolean ifOwnershipAudit(String memberId){
		int i=this.basEmployeeAuditMapperV2.ifOwnershipAudit(memberId);
		if(i>0){
			return true;
		}
		return false;
	}
	/**
	 * 查询申请角色类型
	 */
	@Override
	public int  ifIsAssistantOrManager(String memberId){
	Integer i=this.basEmployeeAuditMapperV2.ifIsAssistantOrManager(memberId);
	return i;
	} 
	@Override
	public String findCompName(String memberId){
		BasEmployeeAudit bas=this.basEmployeeAuditMapperV2.queryAuditComp(memberId);
		String comName=this.basCompInfoMapperV2.queryCompName(String.valueOf(bas.getAuditComp()));
		return comName;
	}
}
