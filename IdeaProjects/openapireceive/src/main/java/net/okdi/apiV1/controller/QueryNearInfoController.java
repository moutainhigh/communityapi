package net.okdi.apiV1.controller;

import net.okdi.apiV1.service.QueryNearInfoService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/queryNearInfo/")
public class QueryNearInfoController extends BaseController{

	@Autowired
	private QueryNearInfoService queryNearInfoService;
	
	//查询附近站点 通过不同的角色id
	//如果是站长角色(1) 查询5公里范围未被领用的1003的站点，如果是(0)收派员或(-1)后勤 查询系统中被领用的或者入驻的待审核和审核通过的站点
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoByRoleId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByRoleId(Double longitude,Double latitude,String roleId){
		
		try {
			return jsonSuccess(this.queryNearInfoService.queryCompInfoByRoleId( longitude, latitude, roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	/**
	 * 
	 * @Description: 微信端查询附近站点
	 * @param longitude
	 * @param latitude
	 * @param roleId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-11
	 */
	@ResponseBody
	@RequestMapping(value = "/queryWechatCompInfoByRoleId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryWechatCompInfoByRoleId(Double longitude,Double latitude){
		
		try {
			return jsonSuccess(this.queryNearInfoService.queryWechatCompInfoByRoleId( longitude, latitude));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoByCompName", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByCompName(Double longitude, Double latitude, String roleId,String compName){
		
		try {
			return jsonSuccess(this.queryNearInfoService.queryCompInfoByCompName(longitude, latitude, roleId, compName));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/queryVerifyCode", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryVerifyCode(String mobile, String randomCode){
		
		try {
			return jsonSuccess(this.queryNearInfoService.queryVerifyCode(mobile, randomCode));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryShareInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryShareInfo(String compTypeNum){
		
		try {
			return jsonSuccess(this.queryNearInfoService.queryShareInfo(compTypeNum));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/getValidationStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public String getValidationStatus(String mobile){
		
		try {
			return jsonSuccess(this.queryNearInfoService.getValidationStatus(mobile));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/invitationIntoCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String invitationIntoCompInfo(String fromMemberPhone,String toMemberPhone,String invitationType){
		
		try {
			this.queryNearInfoService.invitationIntoCompInfo(fromMemberPhone,toMemberPhone,invitationType);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/invitationHaveNetInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String invitationHaveNetInfo(Long memberId,Long netId,Short roleId){
		
		try {
			return jsonSuccess(this.queryNearInfoService.invitationHaveNetInfo(memberId,netId,roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/initVirtualCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String initVirtualCompInfo(){
		
		try {
			this.queryNearInfoService.initVirtualCompInfo();
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/queryRoleInfoAndRaltion", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRoleInfoAndRaltion(Long memberId){
		try {
			return jsonSuccess(this.queryNearInfoService.queryRoleInfoAndRaltion(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryAuthenticationInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAuthenticationInfo(String mobile){
		try {
			return jsonSuccess(this.queryNearInfoService.queryAuthenticationInfo(mobile));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/deleteWrongData", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteWrongData(Long auditId){
		try {
			this.queryNearInfoService.deleteWrongData(auditId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/deleteRedisInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteRedisInfo(String redisName,String key){
		try {
			this.queryNearInfoService.deleteRedisInfo(redisName,key);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryPasswordByMobile", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryPasswordByMobile(String mobile){
		try {
			return jsonSuccess(this.queryNearInfoService.queryPasswordByMobile(mobile));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryWrongDataByMobile", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryWrongDataByMobile(String mobile){
		try {
			return jsonSuccess(this.queryNearInfoService.queryWrongDataByMobile(mobile));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryUnReadMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryUnReadMessage(String mobile){
		try {
			return jsonSuccess(this.queryNearInfoService.queryUnReadMessage(mobile));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
