package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.ExceptionsNoSignService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("exceptionsNoSignController")
@RequestMapping("exceptionsNoSign")
public class ExceptionsNoSignController extends BaseController{

	@Autowired
	private ExceptionsNoSignService exceptionsNoSignService;
	
	@ResponseBody
	@RequestMapping("query")
	public String query(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.ExceptionsNoSignController.query.memberId","快递员id不能为空");
		}
		try {
			return jsonSuccess(this.exceptionsNoSignService.query(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
