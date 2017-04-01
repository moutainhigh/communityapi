package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.ProblemPackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/problemPackage")
public class ProblemPackageController extends BaseController{

	@Autowired
	private ProblemPackageService problemPackageService;
	
	/**
	 * 
	 * @Description: 查询异常件列表
	 * @param actualSendMember
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-21
	 */
	@ResponseBody
	@RequestMapping(value="/queryProblemPackageList" ,method={RequestMethod.GET,RequestMethod.POST})
	public String queryProblemPackageList(Long actualSendMember) {
		try {
			if(PubMethod.isEmpty(actualSendMember)){
				return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.queryProblemPackageList.001", "actualSendMember不能为null");
			}
			 return this.jsonSuccess(problemPackageService.queryProblemPackageList(actualSendMember)); //问题件的派件包裹和地址 
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 异常未签收--重派
	 * @param parIds	包裹id，逗号隔开
	 * @param memberId  操作人memberId
	 * @param memberPhone 操作人手机号
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-21
	 */
	@ResponseBody
	@RequestMapping(value="/probPackAssignAgain" , method={RequestMethod.GET,RequestMethod.POST})
	public String probPackAssignAgain(String parIds,Long memberId,String memberPhone){
		if(PubMethod.isEmpty(parIds)){
			return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.probPackAssignAgain.01", "parIds不能为空!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.probPackAssignAgain.02", "memberId不能为空!");
		}
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.probPackAssignAgain.03", "memberPhone不能为空!");
		}
		try {
			problemPackageService.probPackAssignAgain(parIds,memberId,memberPhone);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * 
	 * @Description: 异常未签收--退回站点(批量)
	 * @param parIds	包裹id，逗号隔开
	 * @param memberId  操作人memberId
	 * @param memberPhone 操作人手机号
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-21
	 */
	@ResponseBody
	@RequestMapping(value="/probPackBackComp" , method={RequestMethod.GET,RequestMethod.POST})
	public String probPackBackComp(String parIds){
		if(PubMethod.isEmpty(parIds)){
			return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.probPackBackComp.01", "parIds不能为空!");
		}
		try {
			problemPackageService.probPackBackComp(parIds);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
}
