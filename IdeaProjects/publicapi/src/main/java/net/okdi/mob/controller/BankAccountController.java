package net.okdi.mob.controller;

import net.okdi.core.base.BaseController;
import net.okdi.mob.service.BankAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bankAccount")
public class BankAccountController extends BaseController{
	
	@Autowired
	BankAccountService bankAccountService;
	
	//查询我的银行卡
	@ResponseBody
	@RequestMapping(value = "/findBankCark", method = { RequestMethod.POST })
	public String findBankCark(String accountId){
		try {
			bankAccountService.findBankCark(accountId);
		} catch(Exception e){
			jsonFailure(e);	
		}
		return jsonSuccess(null);
	}
	
	//删除银行卡
	@ResponseBody
	@RequestMapping(value = "/deleteBankCark", method = { RequestMethod.POST })
	public String deleteBankCark(String accountId,String id){
		try {
			bankAccountService.deleteBankCark(accountId,id);
		} catch(Exception e){
			jsonFailure(e);
		}
		return jsonSuccess(null);
	}
	
	//添加银行卡
	@ResponseBody
	@RequestMapping(value = "/insertBankCark", method = { RequestMethod.POST })
	public String insertBankCark(String accountId,String bankName,String memberName,String bankCard,String idNum,String memberPhone){
		try {
			bankAccountService.insertBankCark(accountId,bankName,memberName,bankCard,idNum,memberPhone);
		} catch(Exception e){
			jsonFailure(e);
		}
		return jsonSuccess(null);
	}
}
