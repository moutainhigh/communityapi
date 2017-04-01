package net.okdi.apiV2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.apiV2.service.StatisticalNumberService;
import net.okdi.core.base.BaseController;

@Controller
@RequestMapping("/statisticalNumber")
public class StatisticalNumber extends BaseController {

	
	@Autowired
	private StatisticalNumberService statisticalNumberService;
	@ResponseBody
	@RequestMapping(value = "/statisticalNumber", method = { RequestMethod.POST,RequestMethod.GET })
	public String statisticalNumber(){
		
		List<Map<String,Object>> result = statisticalNumberService.statisticalNumber();
		System.out.println(">>>>>>>>>>>>>>>>>>: "+result);
		return jsonSuccess(result);
	}
}
