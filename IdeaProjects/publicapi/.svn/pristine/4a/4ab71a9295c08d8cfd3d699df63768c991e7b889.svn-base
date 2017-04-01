package net.okdi.api.controller;

import net.okdi.api.service.HandleDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/handleDataInfo")
public class HandleDataController {

	@Autowired
	private HandleDataService handleDataService;
	@ResponseBody
	@RequestMapping(value="/handleData", method = { RequestMethod.GET , RequestMethod.POST })
	public String handleData(){
		return this.handleDataService.handleData();
	}
}
