package net.okdi.apiV2.controller;

import java.util.Map;

import net.okdi.apiV2.service.QueryInvitedStatusAndStationService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 
 * @Method 
 * @Description  查询快递员状态以及所属快递站点
 * @return
 * @author AiJun.Han
 * @data 2015-12-3 上午11:18:47
 */
@Controller
@RequestMapping("/queryInvitedStatusAndStation")
public class QueryInvitedStatusAndStationController extends BaseController{
	@Autowired
	private QueryInvitedStatusAndStationService queryInvitedStatusAndStationService;
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description  查询身份认证和快递认证状态
	 * @data 2015-12-15 下午2:11:08
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/invitedStatusAndStation", method = { RequestMethod.POST, RequestMethod.GET })
	public String invitedStatusAndStation(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.QueryInvitedStatusAndStationController.invitedStatusAndStation.001", "memberId不能为空");
		}
		try {
			Map<String,Object> map=this.queryInvitedStatusAndStationService.queryInvitedStatusAndStation(memberId);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description  判断是不是快递员
	 * @data 2015-12-15 下午12:04:24
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ifCourie", method = { RequestMethod.POST, RequestMethod.GET })
	public String ifCourie(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.QueryInvitedStatusAndStationController.invitedStatusAndStation.001", "memberId不能为空");
		}
		try {
			boolean flag=this.queryInvitedStatusAndStationService.ifIsCourier(memberId);
			return jsonSuccess(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description 判断是否选择了角色
	 * @data 2015-12-15 下午12:44:44
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ifOwnershipAudit", method = { RequestMethod.POST, RequestMethod.GET })
	public String ifOwnershipAudit(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.QueryInvitedStatusAndStationController.invitedStatusAndStation.001", "memberId不能为空");
		}
		try {
			boolean flag=this.queryInvitedStatusAndStationService.ifOwnershipAudit(memberId);
			return jsonSuccess(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description 查询申请角色类型
	 * @data 2015-12-21 上午11:44:36
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ifIsAssistantOrManager", method = { RequestMethod.POST, RequestMethod.GET })
	public String ifIsAssistantOrManager(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.QueryInvitedStatusAndStationController.invitedStatusAndStation.001", "memberId不能为空");
		}
		try {
			int i=this.queryInvitedStatusAndStationService.ifIsAssistantOrManager(memberId);
			return jsonSuccess(i);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description 查询代收点名称
	 * @data 2015-12-21 下午12:02:10
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCompName", method = { RequestMethod.POST, RequestMethod.GET })
	public String findCompName(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.QueryInvitedStatusAndStationController.invitedStatusAndStation.001", "memberId不能为空");
		}
		try {
			String compName=this.queryInvitedStatusAndStationService.findCompName(memberId);
			return jsonSuccess(compName);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
}
