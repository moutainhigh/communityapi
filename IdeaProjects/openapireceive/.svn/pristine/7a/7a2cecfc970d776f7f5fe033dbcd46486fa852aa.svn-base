package net.okdi.apiV4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.apiV4.service.CompIdAndNetIdService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

@Controller("compIdAndNetIdController")
@RequestMapping("/compIdAndNetId")
public class CompIdAndNetIdController extends BaseController{

	@Autowired
	CompIdAndNetIdService compIdAndNetIdService;
	@ResponseBody
	@RequestMapping("query")
	public String queryComIdAndNetIdByMemberId(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId不能为空");
		}
		try {
			return jsonSuccess(compIdAndNetIdService.queryComIdAndNetIdByMemberId(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
