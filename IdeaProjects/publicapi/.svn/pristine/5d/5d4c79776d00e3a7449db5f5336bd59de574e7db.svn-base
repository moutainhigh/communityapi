package net.okdi.mob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.OkdiAccountInfoService;

@Controller
@RequestMapping("/okdiAccountInfo")
public class OkdiAccountInfoController extends BaseController{
	
	@Autowired
	OkdiAccountInfoService okdiAccountInfoService;
	
	//查询是否开通好递账户
	@ResponseBody
	@RequestMapping(value = "/isHaveOkdiAccount", method = { RequestMethod.POST })
	public String isHaveOkdiAccount(String accountType,String memberId,String shopId ){
		if(PubMethod.isEmpty(accountType)){
			return paramsFailure();
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		if(PubMethod.isEmpty(shopId)){
			return paramsFailure();
		}
		try {
			okdiAccountInfoService.isHaveOkdiAccount(accountType,memberId,shopId );
		} catch(Exception e){
			jsonFailure(e);	
		}
		return jsonSuccess(null);
	}
	
	//开通好递账户
	@ResponseBody
	@RequestMapping(value = "/createOkdiAccount", method = { RequestMethod.POST })
	public String createOkdiAccount(String accountType,String memberId,String shopId,String accountCard,String userPhone,String nick){
		if(PubMethod.isEmpty(accountType)){
			return paramsFailure();
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		if(PubMethod.isEmpty(shopId)){
			return paramsFailure();
		}
		try {
			okdiAccountInfoService.createOkdiAccount(accountType,memberId,shopId,accountCard,userPhone,nick);
		} catch(Exception e){
			jsonFailure(e);	
		}
		return jsonSuccess(null);
	}
	
	//查询该账户是否绑定银行卡或微信账户
	@ResponseBody
	@RequestMapping(value = "/isHaveBankOrWechat", method = { RequestMethod.POST })
	public String isHaveBankOrWechat(String accountId,String flag){
		if(PubMethod.isEmpty(flag)){
			return paramsFailure();
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure();
		}
		try {
			okdiAccountInfoService.isHaveBankOrWechat(accountId,flag);
		} catch(Exception e){
			jsonFailure(e);	
		}
		return jsonSuccess(null);
	}
	
	//创建微信账号
	@ResponseBody
	@RequestMapping(value = "/createOkdiWechatInfo", method = { RequestMethod.POST })
	public String createOkdiWechatInfo(String accountId,String wechatCard){
		try {
			okdiAccountInfoService.createOkdiWechatInfo(accountId,wechatCard);
		} catch(Exception e){
			jsonFailure(e);
		}
		return jsonSuccess(null);
	}
	
	//删除微信账号
	@ResponseBody
	@RequestMapping(value = "/deleteOkdiWechatInfo", method = { RequestMethod.POST })
	public String deleteOkdiWechatInfo(String id){
		try {
			okdiAccountInfoService.deleteOkdiWechatInfo(id);
		} catch(Exception e){
			jsonFailure(e);	
		}
		return jsonSuccess(null);
	}
	
	//查询微信账号
	@ResponseBody
	@RequestMapping(value = "/queryOkdiWechatInfo", method = { RequestMethod.POST })
	public String queryOkdiWechatInfo(String accountType,String memberId,String shopId){
		if(PubMethod.isEmpty(accountType)){
			return paramsFailure();
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		if(PubMethod.isEmpty(shopId)){
			return paramsFailure();
		}
		try {
			okdiAccountInfoService.queryOkdiWechatInfo(accountType,memberId,shopId);
		} catch(Exception e){
			jsonFailure(e);	
		}
		return jsonSuccess(null);
	}
}
