package net.okdi.apiV1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV1.service.OutletsService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/outlets")
public class OutletsController extends BaseController{
	
	@Autowired
	private OutletsService outletsService;
	

	@ResponseBody
	@RequestMapping(value = "/queryOpenSiteDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryOpenSiteDetail(Long compid){
		 try {
			   if(PubMethod.isEmpty(compid)) {
				   return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.saveSiteOpen.001", "compid不能为空");
			   }
	           Map<String,Object> map = this.outletsService.queryOpenSiteDetail(compid);
	           return jsonSuccess(map);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return jsonFailure(e);
	        }
	}
	
	
	

	@ResponseBody
	@RequestMapping(value = "/insertBasCompinfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String insertBasCompinfo(String comp_name, String comp_address,
			String responsible_telephone, String phone, String roleid,
			String longitude, String latitude ,String member_id,String responsible, String agentType) {
		try {
			Long k = outletsService.insertBasCompinfo(comp_name,
					comp_address, responsible_telephone, phone, roleid,
					longitude, latitude,member_id,responsible, agentType);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("compId", k);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveSiteOpen", method = { RequestMethod.POST,RequestMethod.GET })
	public String saveSiteOpen(Long compid, Long roleid, Long memberid, String managerId, String mobile) {
		try {
			String result = outletsService.saveSiteOpen(compid, roleid, memberid, managerId, mobile);
			return jsonSuccess(result);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/invite", method = { RequestMethod.POST,RequestMethod.GET })
	public String invite(String fromMemberId, String fromMemberPhone,
			Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId, Integer flag) {
		if (PubMethod.isEmpty(fromMemberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.invite.001", "fromMemberId不能为空");
		}
		if (PubMethod.isEmpty(fromMemberPhone)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.invite.002", "fromMemberPhone不能为空");
		}
		if (PubMethod.isEmpty(fromMemberRoleid)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.invite.003", "fromMemberRoleid不能为空");
		}
		if (PubMethod.isEmpty(toMemberPhone)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.invite.004", "toMemberPhone不能为空");
		}
		if (PubMethod.isEmpty(toRoleId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.invite.005", "toRoleId不能为空");
		}
		if (PubMethod.isEmpty(flag)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.invite.006", "flag不能为空");
		}
		try {
			outletsService.invite(fromMemberId, fromMemberPhone, fromMemberRoleid, toMemberPhone, toRoleId, flag);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/findShopowner", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String findShopowner(String memberTelephone) {
		try {
			if (PubMethod.isEmpty(memberTelephone)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.findShopowner.001", "telephone不能为空");
			}
			Map<String,Object> map = outletsService.findShopowner(memberTelephone);
			if(map!=null){
				return jsonSuccess(map);
			}else{
				return jsonFailure();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/apply", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String apply(Long roleid, Long memberid, Long compId){
		if (PubMethod.isEmpty(roleid)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.apply.001", "roleid不能为空");
		}
		if (PubMethod.isEmpty(memberid)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.apply.002", "memberid不能为空");
		}
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.apply.003", "compId不能为空");
		}
		try {
			outletsService.apply(roleid, memberid, compId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/siteAuditNotThrough", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String siteAuditNotThrough(Integer isn, Long keyId, String refuseDesc, String memberId, String mobile){
		if(PubMethod.isEmpty(isn)){
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.siteAuditNotThrough.001", "请选择原因!!!");
		}
		if (PubMethod.isEmpty(keyId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.siteAuditNotThrough.002", "keyId不能为空");
		}
		try {
			outletsService.siteAuditNotThrough(isn,keyId, refuseDesc, memberId, mobile);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/siteAuditThrough", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String siteAuditThrough(Long keyId, String remark, String memberId, String mobile){
		
		try {
			if (PubMethod.isEmpty(keyId)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.siteAuditThrough.001", "keyId不能为空");
			}
			outletsService.siteAuditThrough(keyId, remark, memberId, mobile);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryMemberPhoneByMemberId", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String queryMemberPhoneByMemberId(Long memberId){
		try {
			if(PubMethod.isEmpty(memberId)){
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.OutletsController.queryMemberPhoneByMemberId.001", "memberId不能为空");
			}
			//首先根据memberId去查询该身份证下面有多少个注册的手机号
			List list = outletsService.queryMemberPhoneByMemberId(memberId);
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 查找需要审核的代收点信息(运营平台) 传入参数：netId（ 快递网络ID ） compType(网点类型1：营业分布，2：站点)
	 * (beginTime)开始申请日期 (endTime)开始申请日期, status(0：待审核，1:通过，2：拒绝)
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllAudit", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String queryAllAudit(String memberStatus, String currentPage,
			String pageSize, String memberName, String mobile, String idNum,
			String roleId, String registerStartTime, String registerEndTime, String provinceId) {
		 Map<String, Object> resultMap;
		 if(PubMethod.isEmpty(currentPage)){
			 currentPage=1+"";
			}
			if(PubMethod.isEmpty(pageSize)){
				pageSize=15+"";
			}
		try {
			resultMap = outletsService.queryAllAudit(memberStatus, currentPage,
					pageSize, memberName, mobile, idNum, roleId,
					registerStartTime, registerEndTime, provinceId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		return jsonSuccess(resultMap);
	}
	@ResponseBody
	@RequestMapping(value = "/exportExcelData", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String exportExcelData(String memberStatus, String memberName, String mobile, String idNum,
			String roleId, String registerStartTime, String registerEndTime, String province) {
		List<Map<String,Object>> resultMap;
		try {
			resultMap = outletsService.exportExcelData(memberStatus, memberName, mobile, idNum, roleId,
					registerStartTime, registerEndTime, province);
			//return resultMap+"";
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		return jsonSuccess(resultMap);
	}
	@ResponseBody
	@RequestMapping(value = "/queryAttributionAuditState", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String queryAttributionAuditState(String memberStatus, String memberName, String mobile, String idNum,
			String roleId, String registerStartTime, String registerEndTime, String province) {
		Map<String,Map<String,Object>> resultMap;
		try {
			resultMap = outletsService.queryAttributionAuditState(memberStatus, memberName, mobile, idNum, roleId,
					registerStartTime, registerEndTime, province);
			//return resultMap+"";
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		return jsonSuccess(resultMap);
	}

	//查询详情
	@ResponseBody
	@RequestMapping(value = "/queryByMemberId", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String queryByMemberId(String memberId,String memStatus) {

		return jsonSuccess(outletsService.queryByMemberId(memberId ,memStatus));
	}
	
	//店长审核店员
	@ResponseBody
	@RequestMapping(value = "/managerAauditShopAssistant", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String managerAauditShopAssistant(Long memberId, Long compId, String roleId,String moblie, String auditOpinion, Long auditUser){
		try {
			outletsService.managerAauditShopAssistant(memberId, compId, roleId, moblie, auditOpinion, auditUser);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
