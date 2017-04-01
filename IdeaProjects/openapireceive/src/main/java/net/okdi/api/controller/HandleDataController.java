package net.okdi.api.controller;

import net.okdi.api.service.HandleDataService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/handleDataInfo")
public class HandleDataController extends BaseController{

	@Autowired
	private HandleDataService handleDataService;
	@ResponseBody
	@RequestMapping(value="/handleData", method = { RequestMethod.GET , RequestMethod.POST })
	public String handleData(){
		try {
			this.handleDataService.handleData();
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value="/crackPassword", method = { RequestMethod.GET , RequestMethod.POST })
	public String crackPassword(String password){
		try {
			return jsonSuccess(this.handleDataService.crackPassword(password));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
