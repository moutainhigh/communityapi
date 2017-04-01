package net.okdi.apiV2.controller;

import java.util.Map;

import net.okdi.apiV2.entity.TelephoneRelation;
import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/marketManage")
public class TelephoneRewardController extends BaseController {

	
	@Autowired
	private TelephoneRewardService telephoneRewardService;
	
	/**
	 * 登录领奖励
	 * @param phone
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loginReceiveRewardNum", method = { RequestMethod.POST, RequestMethod.GET })
	public String loginReceiveRewardNum(String phone){
		if(PubMethod.isEmpty(phone)){
			return paramsFailure("001", "phone 不能为空");
		}
		String result = telephoneRewardService.loginReceiveRewardNum(phone);
		return jsonSuccess(result);
	}
	
	/**
	 * 获取奖励数量和奖励价格
	 * @param phone 手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRewardNumDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRewardNumDetail(String phone){
		
		TelephoneRelation result = telephoneRewardService.queryRewardNumDetail(phone);
		return jsonSuccess(result);
	}
	
	/**
	 * 计算减去奖励数量和奖励金额
	 * @param phone 手机号
	 * @param id ID号
	 * @param num 使用数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/calculateMinusRewardNumber", method = { RequestMethod.POST, RequestMethod.GET })
	public String calculateMinusRewardNumber(String phone, String id, String num){
		
		String result = telephoneRewardService.calculateMinusRewardNumber(phone, id, num);
		return jsonSuccess(result);
	}
	/**
	 * 查询群发通知多少条,和电话多少条, 优惠奖励多少条
	 * @param memberId 用户memberid
	 * @param phone 电话
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSMSOrPhoneNum", method = { RequestMethod.POST, RequestMethod.GET })
	public String getSMSOrPhoneNum(String accountId, String phone){
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("001", "accountId 不能为空");
		}
		if(PubMethod.isEmpty(phone)){
			return paramsFailure("002", "phone 不能为空");
		}
		Map<String,Object> result = telephoneRewardService.getSMSOrPhoneNum(accountId, phone);
		
		return jsonSuccess(result);
	}
	
	/**
	 * 注册领奖励
	 * @param memberId 人员memberId
	 * @param accountId 账户accountId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/registerGetReward", method = { RequestMethod.POST, RequestMethod.GET })
	public String registerGetReward(String memberId, String accountId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空");
		}
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("002", "accountId 不能为空");
		}
		String result = telephoneRewardService.registerGetReward(memberId, accountId);
		return result;
	}
	
	/**
	 * 通过memberid得到所属地区，所属站点，这是哪个地区收费的策略
	 * @param memberId 人员memberId
	 * @param accountId 账户accountId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getStrategy", method = { RequestMethod.POST, RequestMethod.GET })
	public String getStrategy(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空");
		}
		
		Map result = telephoneRewardService.getStrategy(memberId);
		return jsonSuccess(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryIsGetReward", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryIsGetReward(String phone){
		if(PubMethod.isEmpty(phone)){
			return paramsFailure("001", "phone 不能为空");
		}
		
		String result = telephoneRewardService.queryIsGetReward(phone);
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/queryIsGetRegister", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryIsGetRegister(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空");
		}
		
		String result = telephoneRewardService.queryIsGetRegister(memberId);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/queryPhoneByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryPhoneByMemberId(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空");
		}
		
		String result = telephoneRewardService.queryPhoneByMemberId(memberId);
		return result;
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/updateLoginAndRegStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateLoginAndRegStatus(String phone, String memberId){
		if(PubMethod.isEmpty(phone)){
			return paramsFailure("001", "phone 不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空");
		}
		
		String result = telephoneRewardService.updateLoginAndRegStatus(phone, memberId);
		return result;
	}*/
	
	/**
	 * 修改登录领奖励的状态
	 * @param phone 用户手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateLoginStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateLoginStatus(String phone){
		if(PubMethod.isEmpty(phone)){
			return paramsFailure("001", "phone 不能为空");
		}
		String result = telephoneRewardService.updateLoginStatus(phone);
		return result;
	}
	
	/**
	 * 修改注册领奖励的状态
	 * @param memberId 用户memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRegisterStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateRegisterStatus(String memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空");
		}
		
		String result = telephoneRewardService.updateRegisterStatus(memberId);
		return result;
	}
	
	
}
